package controllayer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import databaselayer.DBCleaner;
import databaselayer.DBConnection;
import databaselayer.IllegalCustomerException;
import model.Customer;
import model.IllegalUnitConverterException;
import model.Price;
import model.Product;
import model.SalesOrder;

class CompanyControllerTest {

	private static CompanyController companyController;
	private SalesOrderController soc;
	private CompanyController cc;
	private SalesOrder o;
	private ProductController pc;
	
	@BeforeEach
	void setUp() throws SQLException {
		companyController = new CompanyController();
		soc = new SalesOrderController();
		o = soc.createSalesOrder();
		
	}
	@BeforeAll
	static void initDatabase() {
		try {
			DBCleaner dbCleaner = new DBCleaner();
			dbCleaner.dropAllTables();
			 dbCleaner.createAllTables();
	            dbCleaner.populateTables();
		}  catch (Exception e) {
            e.printStackTrace();
            fail("Failed to initialize the database before all tests: " + e.getMessage());
        }
    }
	@AfterAll
	static void tearDown() {
		try {
			DBCleaner dbCleaner = new DBCleaner();
			dbCleaner.dropAllTables();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed to clean the database after all tests: " + e.getMessage());
		}
	}
	@Test
	void testSaveOrderAddingAllTheInfoNeeded() throws Exception {
		soc.addCustomerToOrder("40626247");
		Product p = soc.findProduct("Lady Pink").get(0);
		assertEquals(500, p.getStock());
		Product z = soc.findProduct("Granny Smith").get(0);
		assertEquals(300, z.getStock());
		assertTrue(soc.addProductToOrder(p, 100));
		assertFalse(soc.addProductToOrder(z, 2000));
		boolean b = soc.saveOrder();
		assertTrue(b);
		Product d = soc.findProduct("Lady Pink").get(0);
		Product n = soc.findProduct("Granny Smith").get(0);
		assertEquals(300, n.getStock());
		assertEquals(480, d.getStock());
		assertFalse(o.getOrderLines().isEmpty());
		assertEquals(p, o.getOrderLines().get(0).getProduct());
		assertEquals("Ready for Packing", o.getOrderStatus());

	}
	
	
	@Test
	void testFindCustomerByCVR_ValidCVR() {
		String validCVR = "40626247";
		Customer customer = companyController.findCustomerByCVR(validCVR);
		assertNotNull(customer, "Customer should not be null");
		assertEquals("Restaurant Applaus", customer.getName(), "Customer name should match");
		assertEquals("71728181", customer.getPhoneNo(), "Customer phone number should match");
		assertEquals("info@applaus.dk", customer.getEmail(), "Customer email should match");
		assertNotNull(customer.getLocation(), "Customer location should not be null");
		assertEquals("Ved Stranden 9", customer.getLocation().getAddress(), "Customer address should match");
	} 
	

		
		@Test
		void testSaveOrderWithACustomerAndNonProduct() {
			soc.addCustomerToOrder("40626247");
			boolean b=soc.saveOrder();
			assertTrue(b);
	        assertEquals("Ready for Packing",o.getOrderStatus());
	}

		@Test
		void testAddProductToOrder() throws Exception {
			soc.addCustomerToOrder("40626247");
			Product p = soc.findProduct("Lady Pink").get(0);
			boolean b = soc.addProductToOrder(p, 10);
			String name = o.getOrderLines().get(0).getProduct().getName();
			assertTrue(b);
			assertEquals("Lady Pink", name);
		}

		@Test

		void testAddNonExistingProduct() throws IllegalCustomerException, IllegalUnitConverterException {
			soc.addCustomerToOrder("40626247"); 
			Price p=null;

			Product p1 = new Product(0, "name", null, 500, p, null);
			assertThrows(IllegalUnitConverterException.class,()->{
				boolean b=soc.addProductToOrder(p1, 4);
			});
			
		}
	}
