package databaselayer;

import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.Customer;
import model.OrderStatus;
import model.PackingList;
import model.Product;
import model.SalesOrder;
import model.SalesOrderLine;

/**
 * Handles database operations for sales orders, including: - Saving and
 * retrieving sales orders and related details. - Managing packing lists, order
 * lines, and product quantities. - Verifying and updating order statuses. -
 * Fetching quantities for products in specific orders.
 */

public class SalesOrderDB implements SalesOrderDBIF {

	private static final String insertSalesOrderQ = "INSERT INTO SALES_ORDER (Date, Total_price, Order_status, Sales_type, CVR_FK) VALUES (?, ?, ?, ?, ?)";
	private PreparedStatement insertSalesOrderStatement;
	private static final String insertSalesOrderLineQ = "INSERT INTO SALES_ORDERLINE (Quantity, Product_id_FK, Sales_order_no_FK) VALUES (?, ?, ?)";
	private PreparedStatement insertSalesOrderLineStatement;
	private static final String getRemainingOrdersQ = "SELECT COUNT(DISTINCT Sales_order_no) FROM PACKING_LIST_ORDER";
	private PreparedStatement getRemainingOrdersStatement;
	private static final String insertOrderIntoPackingListQ = "INSERT INTO PACKING_LIST_ORDER (Packing_list_id, Sales_order_no) VALUES (?, ?)";
	private PreparedStatement insertOrderIntoPackingListStatement;
	private final DBConnection dbConnection;

	public SalesOrderDB() throws DataAccessException, SQLException {
		dbConnection = DBConnection.getInstance();
		insertSalesOrderStatement = dbConnection.getConnection().prepareStatement(insertSalesOrderQ,
				Statement.RETURN_GENERATED_KEYS);
		getRemainingOrdersStatement = dbConnection.getConnection().prepareStatement(getRemainingOrdersQ);
		insertSalesOrderLineStatement = dbConnection.getConnection().prepareStatement(insertSalesOrderLineQ);
		insertOrderIntoPackingListStatement = dbConnection.getConnection()
				.prepareStatement(insertOrderIntoPackingListQ);
	}

	// Saves a sales order and its details to the database.
	// 1. Inserts the sales order into the SALES_ORDER table and retrieves the
	// generated ID.
	// 2. Links the sales order to a packing list using the retrieved ID.
	// 3. Iterates through the sales order lines, inserting each line into the
	// SALES_ORDERLINE table.
	// 4. Updates the stock of each product in the order and reflects the new stock
	// level.
	// 5. Returns true if the operation is successful; otherwise, throws a
	// DataAccessException.
	public boolean saveSalesOrder(SalesOrder salesOrder) throws DataAccessException {
		boolean result = false;
		int salesNo = 0;
		int packingListId = -1;
		ProductDB productDB = new ProductDB();

		try {
			insertSalesOrderStatement.setDate(1, Date.valueOf(salesOrder.getDate()));
			insertSalesOrderStatement.setBigDecimal(2, salesOrder.getTotalPrice());
			salesOrder.setOrderStatus(OrderStatus.READY_FOR_PACKING);
			insertSalesOrderStatement.setString(3, salesOrder.getOrderStatus().name());
			insertSalesOrderStatement.setString(4, salesOrder.getSalesType());
			insertSalesOrderStatement.setString(5, salesOrder.getCustomer().getCvr());
			salesNo = dbConnection.executeInsertWithIdentity(insertSalesOrderStatement);
			packingListId = salesOrder.getCustomer().getPackingListId();
			insertOrderIntoPackingListStatement.setInt(1, packingListId);
			insertOrderIntoPackingListStatement.setInt(2, salesNo);
			insertOrderIntoPackingListStatement.execute();
			for (SalesOrderLine orderLine : salesOrder.getOrderLines()) {
				double quantity = orderLine.getQuantity();
				Product product = orderLine.getProduct();

				insertSalesOrderLineStatement.setDouble(1, quantity);
				insertSalesOrderLineStatement.setInt(2, product.getProductId());
				insertSalesOrderLineStatement.setInt(3, salesNo);
				insertSalesOrderLineStatement.execute();

				double newStock = product.getStock() - quantity;
				productDB.updateStock(product.getProductId(), newStock, false);
				product.setStock(newStock);
			}

			result = true;
		} catch (SQLException e) {
			throw new DataAccessException("Fejl ved at gemme ordren:", e);
		}

		return result;
	}
	// Retrieves all packing lists from the database in alphabetical order by name.
	// Executes a query to fetch all records from the PACKING_LIST table.
	// Maps each result to a PackingList object using the buildPackingForAllList
	// method.
	// Adds each PackingList to the list and returns the complete list.

	public List<PackingList> getAllPackingLists() throws SQLException {
		List<PackingList> packingLists = new ArrayList<>();
		String query = "SELECT * FROM PACKING_LIST ORDER BY List_name ASC";
		try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				packingLists.add(buildPackingForAllList(rs));
			}
		}
		return packingLists;
	}

	private boolean verifySalesOrder(SalesOrder salesOrder) throws DataAccessException {
		boolean valid = false;
		String verifyQuery = """
				    SELECT so.Sales_order_no, so.Date, so.Total_price, so.Order_status, so.Sales_type, so.Cvr_FK
				    FROM SALES_ORDER so
				    WHERE so.Sales_order_no = ?
				""";

		try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(verifyQuery)) {
			ps.setInt(1, salesOrder.getOrderNo());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					valid = true;
					salesOrder.setOrderNo(rs.getInt("Sales_order_no"));
					salesOrder.setDate(rs.getDate("Date").toLocalDate());

					String orderStatusString = rs.getString("Order_status");
					OrderStatus orderStatus = OrderStatus.valueOf(orderStatusString);
					System.out.println("Initial Order Status: " + orderStatus);

					if (orderStatus == OrderStatus.READY_FOR_PACKING) {
						salesOrder.setOrderStatus(OrderStatus.BEING_PACKED);
						System.out.println("Updating Order Status to BEING_PACKED...");
						boolean statusUpdated = updateSalesOrderStatus(salesOrder);
						System.out.println("Order status update success: " + statusUpdated);
					} else {
						salesOrder.setOrderStatus(orderStatus);
					}

					String cvr = rs.getString("Cvr_FK");
					if (!salesOrder.getCustomer().getCvr().equals(cvr)) {
						CompanyDB companyDB = new CompanyDB();
						Customer customer = companyDB.findCustomerByCVR(cvr);
						salesOrder.setCustomer(customer);
					}
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException(
					"Fejl ved verifikation af salgsordre med ordrenummer: " + salesOrder.getOrderNo(), e);
		}
		return valid;
	}

	public boolean getSalesOrder(SalesOrder salesOrder) throws DataAccessException, SQLException {
		boolean result = false;
		if (verifySalesOrder(salesOrder)) {
			fetchProductsForSalesOrder(salesOrder);
			result = true;
		}
		return result;
	}

	private void fetchProductsForSalesOrder(SalesOrder salesOrder) throws DataAccessException {
		ProductDB productDB = new ProductDB();
		Set<Product> products = productDB.getProductsOnOrder(salesOrder.getOrderNo());

		for (Product product : products) {
			double quantity = fetchQuantityForProductInOrder(salesOrder.getOrderNo(), product.getProductId());

			try {
				salesOrder.addProductToOrder(product, quantity);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	public boolean updateSalesOrderStatus(SalesOrder salesOrder) throws DataAccessException {
		String updateStatusQuery = "UPDATE SALES_ORDER SET Order_status = ? WHERE Sales_order_no = ?";

		try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(updateStatusQuery)) {
			ps.setString(1, salesOrder.getOrderStatus().name());
			ps.setInt(2, salesOrder.getOrderNo());
			int rowsUpdated = ps.executeUpdate();
			System.out.println("Rows updated in updateSalesOrderStatus: " + rowsUpdated);
			return rowsUpdated > 0;
		} catch (SQLException e) {
			throw new DataAccessException("Fejl ved opdatering af ordre status: " + salesOrder.getOrderNo(), e);
		}
	}
	// Updates the status and total price of a specific sales order in the database.
	// Executes an UPDATE query with the new order status and total price for the
	// given order number.
	// Returns true if the update is successful (rowsUpdated > 0); otherwise, throws
	// a DataAccessException on failure.

	public boolean updateOrder(SalesOrder currentOrder) throws DataAccessException {
		boolean result = false;
		try {
			String updateQuery = "UPDATE SALES_ORDER SET Order_status = ?, Total_price = ? WHERE Sales_order_no = ?";
			try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(updateQuery)) {
				ps.setString(1, currentOrder.getOrderStatus().name());
				ps.setBigDecimal(2, currentOrder.getTotalPrice());
				ps.setInt(3, currentOrder.getOrderNo());
				int rowsUpdated = ps.executeUpdate();
				result = rowsUpdated > 0;
			}
		} catch (SQLException e) {
			throw new DataAccessException("Fejl ved opdatering af ordrer: " + currentOrder.getOrderNo(), e);
		}
		return result;
	}

	public boolean updateOrderLine(SalesOrderLine so, double newQuantity) throws SQLException, DataAccessException {
		boolean result = false;
		ProductDB pdb = new ProductDB();
		double oldQuantity = so.getQuantity();
		Product p = so.getProduct();
		String query = "UPDATE SALES_ORDER_LINE SET Quantity = ? WHERE Sales_Orderline_id = ?";
		try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
			ps.setInt(1, so.getOrderLineId());
			ps.setDouble(2, newQuantity);
			result = true;
		}
		if (result) {
			pdb.updateStock(p.getProductId(), newQuantity - oldQuantity, true);
		}
		return result;
	}

	private double fetchQuantityForProductInOrder(int salesOrderNo, int productId) throws DataAccessException {
		double quantity = 0.0;
		String query = """
				    SELECT Quantity
				    FROM SALES_ORDERLINE
				    WHERE Sales_order_no_FK = ? AND Product_id_FK = ?
				""";

		try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
			ps.setInt(1, salesOrderNo);
			ps.setInt(2, productId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					quantity = rs.getDouble("Quantity");
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException(
					"Fejl ved hentning af mængde for produkt ID: " + productId + " i salgsordre nr.: " + salesOrderNo,
					e);
		}

		return quantity;
	}

	@Override
	public PackingList getPackingList(PackingList packingList) throws DataAccessException, SQLException {
		CompanyDB cdb = new CompanyDB();
		String query = """
				    SELECT pl.List_id, pl.List_name, so.Sales_order_no, so.Order_status, so.Date, so.Cvr_FK
				    FROM PACKING_LIST pl
				    JOIN PACKING_LIST_ORDER plo ON pl.List_id = plo.Packing_list_id
				    JOIN SALES_ORDER so ON plo.Sales_order_no = so.Sales_order_no
				    WHERE pl.List_name = ?
				""";

		try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
			ps.setString(1, packingList.getName());

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Customer c = cdb.findCustomerByCVR(rs.getString("Cvr_FK"));
					int salesNo = rs.getInt("Sales_order_no");
					String status = rs.getString("Order_status");
					LocalDate date = rs.getDate("Date").toLocalDate();
					SalesOrder salesOrder = new SalesOrder(salesNo, date, null, OrderStatus.valueOf(status));
					salesOrder.addCustomerToOrder(c);
					packingList.addSalesOrder(salesOrder);
				}
			}
		} catch (

		SQLException e) {
			throw new DataAccessException("Fejl ved hentning af pakkeliste med tilknyttede salgsordrer.", e);
		}

		return packingList;
	}

	private PackingList buildPackingForAllList(ResultSet rs) throws SQLException {
		String name = rs.getString("List_name");
		return new PackingList(name);
	}

	public int getRemainingOrdersCount() throws DataAccessException {
		int result = 0;
		try (ResultSet rs = getRemainingOrdersStatement.executeQuery()) {
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DataAccessException("Error fetching count of remaining orders for packing", e);
		}
		return result;
	}

}