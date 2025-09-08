package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestSalesOrderA {
		private SalesOrder o;
		private Customer c;
		private Product prod;
		private Price p;
		private ProductCategory pc;
	    private UnitConverter uc;
	    

		
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		o = new SalesOrder();
		Location l = new Location("Oesteraagade 27", "Aalborg", "9000");
		c = new Customer("32299687", "Restaurant Flammen", "44223356", "flammen@gmail.dk", l, "kg", 0);
		pc = new ProductCategory("Apple");
		LocalDate date = LocalDate.of(2024, 11, 28);
		p = new Price(date, new BigDecimal("3.38"), null);
		uc=new UnitConverter("bundle", 0.5,10.0);
		prod = new Product(1, "Lady Pink", pc, 500, p,uc);
	}
	

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSalesOrderCreate() {
		assertEquals(0, o.getOrderNo());
		LocalDate expectedDate = LocalDate.of(2024, 12, 9);
		assertEquals(expectedDate, o.getDate());
		assertEquals("Pending", o.getOrderStatus());
		assertEquals("Standard", o.getSalesType());
	}

	@Test
	void testAddExistingCustomerToOrder() {
		assertTrue( o.addCustomerToOrder(c));
	}

	@Test
	void testAddCustomerWithAnonExistingCustomer() {
		assertFalse( o.addCustomerToOrder(null));
	}

	@Test
	void testAddAnExistingProductWithAnAvailableQuantityToOrder() throws Exception {
		SalesOrderLine ol = new SalesOrderLine(prod, 50);
		o.addCustomerToOrder(c);
		assertTrue( o.addProductToOrder(prod, 100));
		assertEquals(ol, o.getOrderLines().get(0));

	}

	@Test
	void testAddANonExistingProduct() throws Exception {
		assertFalse( o.addProductToOrder(null, 100));

	}

	
	@Test
	void testAddAnExistingProductWithNoSuchQuantity() throws Exception {
		o.addCustomerToOrder(c);
		assertFalse( o.addProductToOrder(prod, 1100));

	}


}
