package databaselayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	// Singleton
	private static DBConnection uniqueInstance;

	private Connection connection;

	private static final String DBNAME = "Bastion";
	private static final String SERVERNAME = "DAVIDPC";
	// private static final String SERVERNAME = "\\hildur.ucn.dk";
	private static final String PORTNUMBER = "1433";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "JosukeHigashikata0911";

	// Constructor skal være private.Vi skal få fat i connection her.
	private DBConnection() throws Exception {
		String urlString = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;encrypt=false", SERVERNAME, PORTNUMBER,
				DBNAME);
		try {
			connection = DriverManager.getConnection(urlString, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException();
		}
	}

	// Vi skal tænke some en get metode.
	public static synchronized DBConnection getUniqueInstance() throws DataAccessException {

		if (uniqueInstance == null) {
			try {
				uniqueInstance = new DBConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return uniqueInstance;
	}

	// Vi har denne class på grund af database.
	// Den skaber en forbindelse til databasen.
	// Connection objekt er allerede i autoCommit, så den udfylder transactioner
	// uden at man skal til at kalde en metode. Den gemmer selv data.
	// Men skulle den autoCommit være slukket, så skal man bruge en metode
	// ellers ville dataerne ikke blive gemt.
	public Connection getConnection() {
		return connection;
	}

	// TODO make a proper exception, DataAccessException
	public void startTransaction() throws DataAccessException {

		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void commitTransaction() throws DataAccessException {

		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Det vil altid blive executed. Selv hvis det går galt.
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void rollbackTransaction() throws DataAccessException {

		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}