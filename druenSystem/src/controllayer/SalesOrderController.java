package controllayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import databaselayer.DataAccessException;
import databaselayer.SalesOrderDB;
import model.Customer;
import model.PackingList;
import model.Product;
import model.SalesOrder;
import model.SalesOrderLine;

/**
 * Manages sales order operations, including creating, updating, and saving
 * orders, handling packing lists, managing order lines, and interacting with
 * customers and products.
 */
public class SalesOrderController {
	private SalesOrderDB salesOrderDB;
	private SalesOrder currentOrder;

	public SalesOrderController() throws SQLException {
		try {
			salesOrderDB = new SalesOrderDB();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	public SalesOrder createSalesOrder() {
		currentOrder = new SalesOrder();
		return currentOrder;
	}

	public boolean addCustomerToOrder(String cvr) {
		CompanyController cc = new CompanyController();
		Customer customer = cc.findCustomerByCVR(cvr);
		return currentOrder.addCustomerToOrder(customer);
	}

	public List<Product> findProduct(String productName) {
		List<Product> products = new ArrayList<>();
		ProductController pc = new ProductController();
		products = pc.findProductByName(productName);
		return products;
	}

	public boolean addProductToOrder(Product p, double quantity) throws Exception {
		return currentOrder.addProductToOrder(p, quantity);
	}

	public boolean saveOrder() {
		boolean result = false;
		try {
			result = salesOrderDB.saveSalesOrder(currentOrder);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return result;
	}

	public SalesOrder getCurrentOrder() {
		return currentOrder;
	}

	public List<PackingList> viewAllPackingLists() {
		List<PackingList> packingLists = new ArrayList<>();
		try {
			packingLists = salesOrderDB.getAllPackingLists();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return packingLists;
	}

	public PackingList selectPackingList(PackingList packingList) throws SQLException {
		try {
			packingList = salesOrderDB.getPackingList(packingList);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return packingList;
	}

	public boolean updateOrderLine(SalesOrderLine so, double quantity) throws SQLException, DataAccessException {
		boolean updated = false;
		if (currentOrder != null) {
			updated = currentOrder.updateOrderLine(so.getProduct(), quantity);
			if (updated) {
				salesOrderDB.updateOrderLine(so, quantity);
			}
		}
		return updated;
	}

	public boolean removeOrderLine(SalesOrderLine so) {
		return currentOrder.removeOrderLine(so);
	}

	public boolean selectOrder(SalesOrder salesOrder) throws SQLException {
		boolean result = false;
		try {
			result = salesOrderDB.getSalesOrder(salesOrder);
			if (result) {
				currentOrder = salesOrder;
				salesOrderDB.updateSalesOrderStatus(currentOrder);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean updateOrder() {
		boolean result = false;
		currentOrder.calculateTotalPrice();
		try {
			result = salesOrderDB.updateOrder(currentOrder);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return result;
	}
}
