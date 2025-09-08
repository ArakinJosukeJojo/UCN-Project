package databaselayer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

class DBConnectionTest {

	@Test
	public void testGetConnection() {
		try {
			DBConnection connectionInstance = DBConnection.getUniqueInstance();
			Connection c = connectionInstance.getConnection();
			assertNotNull(c, "Connection should not be null");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Issues with DBConnection: " + e.getMessage());
		}
	}

}
