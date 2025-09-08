package databaselayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Acts as a filler for the database, so we can drop from Java, instead of
 * executing SQL-scripts in the SSMS
 */

public class DBCleaner {
	private Connection connection;

	public DBCleaner() throws DataAccessException, SQLException {
		connection = DBConnection.getInstance().getConnection();
		if (connection == null || connection.isClosed()) {
			connection = DBConnection.getInstance().getConnection();
			if (connection == null || connection.isClosed()) {
				throw new DataAccessException("Connection is closed or unavailable");
			}
		}
	}

	public void cleanAndRecreateDatabase() throws DataAccessException {
		try {
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			System.out.println("Dropping all tables...");
			dropAllTables();

			System.out.println("Creating all tables...");
			createAllTables();

			System.out.println("Populating tables with data...");
			populateTables();

			connection.commit();
			System.out.println("Database successfully recreated!");
		} catch (SQLException e) {
			System.err.println("Error occurred during database reset. Rolling back changes...");
			try {
				connection.rollback();
			} catch (SQLException rollbackEx) {
				throw new DataAccessException("Error during rollback", rollbackEx);
			}
			throw new DataAccessException("Failed to clean and recreate the database", e);
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new DataAccessException("Error resetting auto-commit", e);
			}
		}
	}

	public void dropAllTables() throws SQLException {
		String dropTablesSQL = """
				DROP TABLE IF EXISTS PACKING_LIST_ORDER;
				DROP TABLE IF EXISTS STANDING_SALES_ORDER;
				DROP TABLE IF EXISTS RESTOCK_ORDERLINE;
				DROP TABLE IF EXISTS SALES_ORDERLINE;
				DROP TABLE IF EXISTS PRICE;
				DROP TABLE IF EXISTS PRODUCT;
				DROP TABLE IF EXISTS UNIT_CONVERTER;
				DROP TABLE IF EXISTS PRODUCT_CATEGORY;
				DROP TABLE IF EXISTS RESTOCK;
				DROP TABLE IF EXISTS SUPPLIER;
				DROP TABLE IF EXISTS SALES_ORDER;
				DROP TABLE IF EXISTS CUSTOMER;
				DROP TABLE IF EXISTS PACKING_LIST;
				DROP TABLE IF EXISTS COMPANY;
				DROP TABLE IF EXISTS [LOCATION];
				DROP TABLE IF EXISTS CITY_ZIP_CODE;
				""";

		executeQuery(dropTablesSQL);
	}

	public void createAllTables() throws SQLException {
		String createTablesSQL = """
				CREATE TABLE CITY_ZIP_CODE (
				    Zip_code INT PRIMARY KEY,
				    City VARCHAR(100) NOT NULL
				);

				CREATE TABLE [LOCATION] (
				    Location_id INT IDENTITY(1,1) PRIMARY KEY,
				    Address VARCHAR(100) NOT NULL,
				    Zip_code_FK INT NOT NULL DEFAULT 0,
				    FOREIGN KEY (Zip_code_FK) REFERENCES CITY_ZIP_CODE(Zip_code) ON UPDATE CASCADE ON DELETE SET DEFAULT
				);

				CREATE TABLE COMPANY (
				    Cvr VARCHAR(100) PRIMARY KEY,
				    [Name] VARCHAR(100) NOT NULL,
				    Phone_no VARCHAR(100),
				    Email VARCHAR(100),
				    Company_type VARCHAR(100),
				    Location_id_FK INT NOT NULL DEFAULT 0,
				    FOREIGN KEY (Location_id_FK) REFERENCES [LOCATION](Location_id) ON UPDATE CASCADE ON DELETE SET DEFAULT
				);

				CREATE TABLE SUPPLIER (
				    Cvr_FK VARCHAR(100) PRIMARY KEY,
				    Country VARCHAR(100) NOT NULL,
				    FOREIGN KEY (Cvr_FK) REFERENCES COMPANY(Cvr) ON UPDATE CASCADE ON DELETE CASCADE
				);

				CREATE TABLE PACKING_LIST (
				    List_id INT IDENTITY(1,1) PRIMARY KEY,
				    List_name VARCHAR(100) NOT NULL
				);

				CREATE TABLE CUSTOMER (
				    Preferred_unit VARCHAR(100),
				    Cvr_FK VARCHAR(100) PRIMARY KEY,
				    List_id_FK INT NOT NULL DEFAULT 0,
				    FOREIGN KEY (Cvr_FK) REFERENCES COMPANY(Cvr) ON UPDATE CASCADE ON DELETE CASCADE,
				    FOREIGN KEY (List_id_FK) REFERENCES PACKING_LIST(List_id) ON UPDATE CASCADE ON DELETE SET DEFAULT
				);

				CREATE TABLE SALES_ORDER (
				    Sales_order_no INT IDENTITY(1,1) PRIMARY KEY,
				    [Date] DATE NOT NULL,
				    Total_price MONEY,
				    Order_status VARCHAR(100) NOT NULL,
				    Sales_type VARCHAR(100),
				    Cvr_FK VARCHAR(100) NOT NULL DEFAULT 'Missing CVR',
				    FOREIGN KEY (Cvr_FK) REFERENCES CUSTOMER(Cvr_FK) ON UPDATE CASCADE ON DELETE SET DEFAULT
				);

				CREATE TABLE PACKING_LIST_ORDER (
				    Packing_list_id INT NOT NULL,
				    Sales_order_no INT NOT NULL,
				    PRIMARY KEY (Packing_list_id, Sales_order_no),
				    FOREIGN KEY (Packing_list_id) REFERENCES PACKING_LIST(List_id),
				    FOREIGN KEY (Sales_order_no) REFERENCES SALES_ORDER(Sales_order_no)
				);

				CREATE TABLE PRODUCT_CATEGORY (
				   [Name] VARCHAR(100) PRIMARY KEY
				);

				CREATE TABLE UNIT_CONVERTER (
				    Converter_id INT IDENTITY(1,1) PRIMARY KEY,
				    Conversion_type VARCHAR(50) NOT NULL,
				    Conversion_rate DECIMAL(10, 2) NOT NULL,
				    Box_weight DECIMAL (10, 2) NOT NULL
				);

				CREATE TABLE [PRODUCT] (
				    Product_id INT IDENTITY(1,1) PRIMARY KEY,
				    [Name] VARCHAR(100) NOT NULL,
				    Name_FK VARCHAR(100) DEFAULT 'No Category',
				    Stock INT NOT NULL,
				    Converter_id_FK INT DEFAULT 0,
				    FOREIGN KEY (Name_FK) REFERENCES PRODUCT_CATEGORY([Name]) ON UPDATE CASCADE ON DELETE SET DEFAULT,
				    FOREIGN KEY (Converter_id_FK) REFERENCES UNIT_CONVERTER(Converter_id) ON UPDATE CASCADE ON DELETE SET DEFAULT
				);

				CREATE TABLE PRICE (
				    [Date] DATE NOT NULL,
				    Purchase_price MONEY NOT NULL,
				    Sales_price MONEY NOT NULL,
				    Product_id_FK INT NOT NULL,
				    PRIMARY KEY ([Date], Product_id_FK),
				    FOREIGN KEY (Product_id_FK) REFERENCES PRODUCT(Product_id) ON UPDATE CASCADE ON DELETE CASCADE
				);

				CREATE TABLE SALES_ORDERLINE (
				    Sales_orderline_id INT IDENTITY(1,1) PRIMARY KEY,
				    Quantity DECIMAL(10, 2) NOT NULL,
				    Product_id_FK INT NOT NULL,
				    Sales_order_no_FK INT NOT NULL,
				    FOREIGN KEY (Product_id_FK) REFERENCES [PRODUCT](Product_id),
				    FOREIGN KEY (Sales_order_no_FK) REFERENCES SALES_ORDER(Sales_order_no) ON UPDATE CASCADE ON DELETE CASCADE
				);
				""";

		executeQuery(createTablesSQL);
	}

	public void populateTables() throws SQLException {
		String populateDataSQL = """
				INSERT INTO CITY_ZIP_CODE (Zip_code, City) VALUES (9000, 'Aalborg');

				INSERT INTO LOCATION (Address, Zip_code_FK) VALUES
				    ('Ved Stranden 9', 9000),
				    ('Strandvejen 4', 9000),
				    ('Østerågade 27', 9000);

				INSERT INTO COMPANY (Cvr, Name, Phone_no, Email, Company_type, Location_id_FK) VALUES
				    ('26689120', 'Nordic Supplies', '12345678', 'info@nordic.dk', 'Supplier', 1),
				    ('40626247', 'Restaurant Applaus', '71728181', 'info@applaus.dk', 'Customer', 1),
				    ('31769809', 'Restaurant Fusion', '35123331', 'contact@fusion.dk', 'Customer', 2),
				    ('32299687', 'Restaurant Flammen', '35266368', 'contact@flammen.dk', 'Customer', 3);

				INSERT INTO PACKING_LIST (List_name) VALUES
				    ('Byen Packing'),
				    ('Nord Packing');

				INSERT INTO CUSTOMER (Preferred_unit, Cvr_FK, List_id_FK) VALUES
				    ('piece', '40626247', 1),
				    ('bundle', '31769809', 2),
				    ('weight', '32299687', 2);

				INSERT INTO SALES_ORDER ([Date], Total_price, Order_status, Sales_type, Cvr_FK) VALUES
				    ('2024-12-01', 500.00, 'READY_FOR_PACKING', 'Standard', '40626247'),
				    ('2024-12-02', 750.00, 'READY_FOR_PACKING', 'Standard', '31769809'),
				    ('2024-12-03', 1000.00, 'READY_FOR_PACKING', 'Standard', '32299687');

				INSERT INTO PACKING_LIST_ORDER (Packing_list_id, Sales_order_no) VALUES
				    (1, 1),
				    (1, 2),
				    (2, 3);

				INSERT INTO PRODUCT_CATEGORY (Name) VALUES
				    ('Banan'),
				    ('Æble'),
				    ('Kiwi');

				INSERT INTO UNIT_CONVERTER (Conversion_type, Conversion_rate, Box_weight) VALUES
				    ('bundle', 0.5, 10.0),
				    ('piece', 0.2, 10.0);

				INSERT INTO PRODUCT ([Name], Name_FK, Stock, Converter_id_FK) VALUES
				    ('Lady Pink', 'Æble', 500, 2),
				    ('Granny Smith', 'Æble', 300, 2),
				    ('Blue Java', 'Banan', 200, 1),
				    ('Kiwi Green', 'Kiwi', 150, 1);

				INSERT INTO PRICE ([Date], Purchase_price, Sales_price, Product_id_FK) VALUES
				    ('2024-12-01', 3.00, 5.00, 1),
				    ('2024-12-02', 3.50, 5.50, 2),
				    ('2024-12-01', 2.00, 3.00, 3),
				    ('2024-12-02', 4.00, 6.00, 4);

				INSERT INTO SALES_ORDERLINE (Quantity, Product_id_FK, Sales_order_no_FK) VALUES
				    (5, 1, 1),
				    (10, 2, 2),
				    (8, 3, 3);
				""";
		executeQuery(populateDataSQL);
	}

	private void executeQuery(String query) throws SQLException {
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.executeUpdate();
		}
	}
}
