package databaselayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {

    private Connection connection = null;
    private static DBConnection dBConnection;

    private static final String DBNAME = "DMA-CSD-V24_10461215";
    private static final String SERVERNAME = "hildur.ucn.dk";
    private static final int PORTNUMBER = 1433;
    private static final String USERNAME = "DMA-CSD-V24_10461215";
    private static final String PASSWORD = "Password1!";

    private DBConnection() throws SQLException {
        String urlString = String.format("jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=false", SERVERNAME, PORTNUMBER, DBNAME);
        try {
            connection = DriverManager.getConnection(urlString, USERNAME, PASSWORD);
            if (connection == null || connection.isClosed()) {
                throw new SQLException("Connection could not be established.");
            }
        } catch (SQLException e) {
            throw new SQLException("Error establishing the database connection", e);
        }
    }

    public static synchronized DBConnection getInstance() throws SQLException {
        if (dBConnection == null) {
            dBConnection = new DBConnection();
        }
        return dBConnection;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String urlString = String.format("jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=false", SERVERNAME, PORTNUMBER, DBNAME);
            connection = DriverManager.getConnection(urlString, USERNAME, PASSWORD);
        }
        return connection;
    }

    public int executeInsertWithIdentity(PreparedStatement ps) throws DataAccessException {
        int key = -1;
        try {
            int res = ps.executeUpdate();
            if (res > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                key = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Could not execute insert", e);
        }
        return key;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
