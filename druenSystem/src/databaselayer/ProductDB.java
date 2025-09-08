package databaselayer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import model.Price;
import model.Product;
import model.ProductCategory;
import model.UnitConverter;

/**
 * Handles database operations for products, including: - Searching products by
 * name or ID. - Retrieving products related to sales orders. - Managing stock
 * updates with optional negative stock checks. - Fetching product prices and
 * building product objects from database results.
 */

public class ProductDB implements ProductDBIF {
	private static final String SELECT_ALL_PRODUCTS = """
			    SELECT * FROM PRODUCT p
			    JOIN UNIT_CONVERTER u ON p.Converter_id_FK = u.Converter_id
			""";
	private static final String FIND_PRODUCT_BY_NAME = SELECT_ALL_PRODUCTS + " WHERE p.Name LIKE ?";
	private static final String GET_PRODUCTS_ON_ORDER = SELECT_ALL_PRODUCTS + """
			    JOIN SALES_ORDERLINE so ON p.Product_id = so.Product_id_FK
			    WHERE so.Sales_order_no_FK = ?
			""";
	private static final String GET_PRICES_FOR_PRODUCT = """
			    SELECT pr.Date, pr.Sales_price, pr.Purchase_price
			    FROM PRICE pr
			    WHERE pr.Product_id_FK = ?
			""";

	private PreparedStatement findProductByNameStatement;
	private PreparedStatement getProductsOnOrderStatement;
	private PreparedStatement getPricesForProductStatement;

	private DBConnection dbConnection;

	public ProductDB() throws DataAccessException {
		try {
			dbConnection = DBConnection.getInstance();
			Connection connection = dbConnection.getConnection();

			if (connection == null || connection.isClosed()) {
				throw new SQLException("Database connection is closed or unavailable.");
			}

			findProductByNameStatement = connection.prepareStatement(FIND_PRODUCT_BY_NAME);
			getPricesForProductStatement = connection.prepareStatement(GET_PRICES_FOR_PRODUCT);
		} catch (SQLException e) {
			throw new DataAccessException("Error initializing ProductDB", e); // Exception wrapping
		}
	}

	// Updates the stock of a product by deducting a specified quantity.
	// Ensures sufficient stock if negative stock is not allowed.
	// 1. Retrieves the current stock level for the given product ID.
	// 2. Checks if stock can be deducted without going negative (if not allowed).
	// 3. Updates the stock in the database and commits the transaction if
	// successful.
	// 4. Rolls back the transaction if the update fails.

	public boolean updateStock(int productId, double quantityToDeduct, boolean allowNegativeStock)
			throws DataAccessException {
		String sqlSelectStock = "SELECT Stock FROM PRODUCT WHERE Product_id = ?;";
		String sqlUpdateStock = "UPDATE PRODUCT SET Stock = ? WHERE Product_id = ?";

		try (Connection conn = dbConnection.getConnection();
				PreparedStatement psSelect = conn.prepareStatement(sqlSelectStock);
				PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateStock)) {

			conn.setAutoCommit(false);

			double currentStock = 0;

			psSelect.setInt(1, productId);
			try (ResultSet rs = psSelect.executeQuery()) {
				if (rs.next()) {
					currentStock = rs.getDouble("Stock");
					System.out.println("Nuværende lagerbeholdning for produkt ID " + productId + ": " + currentStock);
				} else {
					System.out.println("Produkt med ID " + productId + " blev ikke fundet.");
					return false;
				}
			}

			if (currentStock - quantityToDeduct < 0 && !allowNegativeStock) {
				System.out.println("Ikke tilstrækkelig lagerbeholdning for produkt ID " + productId
						+ ". Nuværende beholdning: " + currentStock);
				return false;
			}

			psUpdate.setDouble(1, currentStock - quantityToDeduct);
			psUpdate.setInt(2, productId);
			int rowsUpdated = psUpdate.executeUpdate();
			System.out.println("Antal rækker opdateret i lageropdatering: " + rowsUpdated);

			if (rowsUpdated == 1) {
				conn.commit();
				conn.setAutoCommit(true);
				return true;
			} else {
				conn.rollback();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Fejl ved opdatering af lager for produkt ID: " + productId, e);
		}
	}

	@Override
	public List<Product> findProductByName(String productName) throws DataAccessException {
		List<Product> products = new ArrayList<>();
		try {
			findProductByNameStatement.setString(1, "%" + productName + "%");
			ResultSet rs = findProductByNameStatement.executeQuery();
			products.addAll(buildObjects(rs, new ArrayList<>()));
		} catch (SQLException e) {
			throw new DataAccessException("Fejl ved søgning efter produkt ved navn: " + productName, e);
		}
		return products;
	}

	// Retrieves all products associated with a specific sales order.
	// 1. Executes a query to fetch product details for the given sales order
	// number.
	// 2. Uses buildObjects to map the ResultSet into a LinkedHashSet of Product
	// objects.
	// 3. Ensures that products are returned in the order defined by the query.
	public Set<Product> getProductsOnOrder(int salesOrderNo) throws DataAccessException {
		Set<Product> products = new LinkedHashSet<>();
		String query = """
				    SELECT p.Product_id, p.Name, p.Name_FK, p.Stock, uc.Conversion_type, uc.Conversion_rate, uc.Box_weight
				    FROM SALES_ORDERLINE sol
				    JOIN PRODUCT p ON sol.Product_id_FK = p.Product_id
				    JOIN UNIT_CONVERTER uc ON p.Converter_id_FK = uc.Converter_id
				    WHERE sol.Sales_order_no_FK = ?
				    ORDER BY sol.Sales_orderline_id
				""";

		try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
			ps.setInt(1, salesOrderNo);
			try (ResultSet rs = ps.executeQuery()) {
				products.addAll(buildObjects(rs, new LinkedHashSet<>()));
			}
		} catch (SQLException e) {
			throw new DataAccessException("Fejl ved hentning af produkter for salgsordre nr.: " + salesOrderNo, e);
		}
		return products;
	}

	// The <T extends Collection<Product>> is a generic type constraint that allows
	// this method
	// to work with any collection type (Like List, Set) that can hold Product
	// objects.
	private <T extends Collection<Product>> T buildObjects(ResultSet rs, T collection)
			throws SQLException, DataAccessException {
		while (rs.next()) {
			collection.add(buildObject(rs));
		}
		return collection;
	}

	private Product buildObject(ResultSet rs) throws SQLException, DataAccessException {
		int id = rs.getInt("Product_id");
		String name = rs.getString("Name");
		String category = rs.getString("Name_FK");
		double stock = rs.getDouble("Stock");
		String conversionType = rs.getString("Conversion_type");
		double conversionRate = rs.getDouble("Conversion_rate");
		double boxWeight = rs.getDouble("Box_weight");

		ProductCategory productCategory = new ProductCategory(category);
		UnitConverter unitConverter = new UnitConverter(conversionType, conversionRate, boxWeight);

		Product product = new Product(id, name, productCategory, stock, unitConverter);
		product.setPrice(getPricesForProduct(id));

		return product;
	}

	public Product findProductById(int productId) throws DataAccessException {
		String sql = SELECT_ALL_PRODUCTS + " WHERE p.Product_id = ?";
		Product product = null;

		try (Connection conn = DBConnection.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, productId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					product = buildObject(rs);
				} else {
					throw new DataAccessException("Produkt med ID " + productId + " ikke fundet.");
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException("Fejl ved hentning af produkt efter ID: " + productId, e);
		}

		return product;
	}

	private Collection<Price> getPricesForProduct(int productId) throws DataAccessException {
		Collection<Price> prices = new ArrayList<>();
		try {
			getPricesForProductStatement.setInt(1, productId);
			ResultSet rs = getPricesForProductStatement.executeQuery();

			while (rs.next()) {
				Date sqlDate = rs.getDate("Date");
				LocalDate priceDate = sqlDate.toLocalDate();
				BigDecimal salesPrice = rs.getBigDecimal("Sales_price");
				BigDecimal purchasePrice = rs.getBigDecimal("Purchase_price");

				prices.add(new Price(priceDate, salesPrice, purchasePrice));
			}
		} catch (SQLException e) {
			throw new DataAccessException("Fejl ved hentning af priser for produkt ID: " + productId, e);
		}
		return prices;
	}
}
