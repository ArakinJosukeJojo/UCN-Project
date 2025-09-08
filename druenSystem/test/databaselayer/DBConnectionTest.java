package databaselayer;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DBConnectionTest {


		@Test
		public void testGetConnection() {
			try {
				Connection c = DBConnection.getInstance().getConnection();
				assertNotNull(c);
			} catch (Exception e) {
				fail("issues with dbconnection");
			}
		}
	
}
