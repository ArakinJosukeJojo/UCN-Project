package databaselayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DataFill {

	private Connection connection;

	public DataFill() {
		try {
			connection = DBConnection.getUniqueInstance().getConnection();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		try {
			if (connection == null || connection.isClosed()) {
				connection = DBConnection.getUniqueInstance().getConnection();
				if (connection == null || connection.isClosed()) {
					throw new DataAccessException("Connection is closed or unavailable");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	public void cleanAndRecreateDatabase() throws DataAccessException {
		try {
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			System.out.println("Dropping table for characters...");
			dropData();
			System.out.println("Dropping table successful!");

			System.out.println("Creating table for characters and monsters...");
			datafill();
			monfill();
			equipfill();
			partyfill();
			System.out.println("Creating tables successful!");

			System.out.println("Populating tables with characters...");
			populateCharacterTable();
			System.out.println("Populating tables successful!");

			System.out.println("Populating tables with monsters...");
			populateMonsterTable();
			System.out.println("Populating tables successful!");

			System.out.println("Populating tables with equipments...");
			populateEquipmentTable();
			System.out.println("Populating tables successful!");

			System.out.println("Populating tables with parties...");
			populatePartyTable();
			System.out.println("Populating tables successful!");

			connection.commit();
			System.out.println("Database successfully created!");
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

	public void dropData() throws DataAccessException, SQLException {
		String dropTablesSQL = """
				DROP TABLE IF EXISTS characters;
				DROP TABLE IF EXISTS monsters;
				DROP TABLE IF EXISTS equipments;
				DROP TABLE IF EXISTS party
				""";

		try (Statement stmt = connection.createStatement()) {
			stmt.execute(dropTablesSQL);
		}
	}

	public void datafill() throws DataAccessException, SQLException {
		String createTablesSQL = "CREATE TABLE characters (" + "name VARCHAR(50) PRIMARY KEY NOT NULL, "
				+ "age INT NULL, " + "military_rank VARCHAR(100) NOT NULL, " + "location VARCHAR(50) NOT NULL, "
				+ "occupation VARCHAR(50) NOT NULL, " + "currency DOUBLE PRECISION NULL" + ");";
		executeQuery(createTablesSQL);
	}

	public void monfill() throws DataAccessException, SQLException {
		String createMonsterTablesSQL = "CREATE TABLE monsters (" + "name VARCHAR(50) PRIMARY KEY NOT NULL, "
				+ "lifespan INT NULL, " + "classification VARCHAR(100) NOT NULL, "
				+ "average_size DOUBLE PRECISION NOT NULL, " + "habitat VARCHAR(50) NOT NULL, "
				+ "danger_level VARCHAR(50) NOT NULL" + ");";
		executeQuery(createMonsterTablesSQL);
	}

	public void equipfill() throws DataAccessException, SQLException {
		String createEquipmentTableSQL = "CREATE TABLE equipments (" + "name VARCHAR(50) PRIMARY KEY NOT NULL, "
				+ "category VARCHAR(10) NOT NULL, " + "stock INT NULL, " + "price DOUBLE PRECISION NULL" + ");";
		executeQuery(createEquipmentTableSQL);
	}

	public void partyfill() throws DataAccessException, SQLException {
		String createPartyTableSQL = "CREATE TABLE party (" + "Party_List_id INT IDENTITY (1,1) PRIMARY KEY, "
				+ "PARTY_SIZE INT NOT NULL" + ");";
		executeQuery(createPartyTableSQL);
	}

	public void populateCharacterTable() throws DataAccessException, SQLException {
		String populateTableSQL = "INSERT INTO characters VALUES ('Arakin','19','Recruit','Bastion','Farmer','20');"
				+ "INSERT INTO characters VALUES ('Eira','19','Recruit','Bastion','Urchin','10');"
				+ "INSERT INTO characters VALUES ('Idalia','19','Recruit','Bastion','Priestess','4');"
				+ "INSERT INTO characters VALUES('Rave','22','Recruit','Red Mountains','Hunter','0');"
				+ "INSERT INTO characters VALUES ('Brie','24','Recruit','Unknown','Lancer','300');"
				+ "INSERT INTO characters VALUES ('Svensker','102','Recruit','Heim','Mercenary','500');";
		executeQuery(populateTableSQL);
	}

	public void populateMonsterTable() throws DataAccessException, SQLException {
		String populateMonsterTableSQL = "INSERT INTO monsters VALUES ('Orc','100','Brute Humanoid','8.2','Arid terrain','High');"
				+ "INSERT INTO monsters VALUES ('Dragon','3000','Dragonoid','75.0','Mountains','Terrifying');"
				+ "INSERT INTO monsters VALUES ('Goblin','30','Brute Humanoid','3.3','Cave','Low');"
				+ "INSERT INTO monsters VALUES('Elves','500','Savage Humanoid','5.2','Forests','Medium');"
				+ "INSERT INTO monsters VALUES ('Dwarves','450','Savage Humanoid','4.3','Mountain Caves','High');"
				+ "INSERT INTO monsters VALUES ('Ogres','80','Brute Humanoid','10.3','Arid terrain','High');";
		executeQuery(populateMonsterTableSQL);
	}

	public void populateEquipmentTable() throws DataAccessException, SQLException {
		String populateEquipmentTableSQL = "INSERT INTO equipments VALUES ('Bread','FOODS','10','2.0')";
		executeQuery(populateEquipmentTableSQL);
	}

	public void populatePartyTable() throws DataAccessException, SQLException {
		// Don't insert into the IDENTITY column; let SQL Server generate it
		String insertPartySQL = "INSERT INTO party (PARTY_SIZE) VALUES (4);";
		executeQuery(insertPartySQL);
	}

	private void executeQuery(String query) throws SQLException {
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.executeUpdate();
		}
	}

}
