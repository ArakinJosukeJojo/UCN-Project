package controllayer;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import databaselayer.DBCleaner;
import databaselayer.DataAccessException;
import databaselayer.ProductDB;
import model.OrderStatus;
import model.PackingList;
import model.Product;
import model.ProductCategory;
import model.SalesOrder;
import model.SalesOrderLine;
import model.UnitConverter;

class PackOrderControllerTest {
	
	 private SalesOrderController salesOrderController;
	    private DBCleaner dbCleaner;
	    private PackOrderController poc;
	    private ProductController pc;

	    @BeforeEach
	    void setUp() throws SQLException, DataAccessException {
	        dbCleaner = new DBCleaner();
	        dbCleaner.cleanAndRecreateDatabase();
	        poc=new PackOrderController();
	        salesOrderController=poc.getSalesOrderController();
	    }


	 @Test
	    void testViewAllPackingLists() throws SQLException {
		 //Act
	        List<PackingList> packingLists = poc.viewPackingLists();
       //Assert
	        assertNotNull(packingLists);
	        assertEquals(2, packingLists.size());
	    }

	    @Test
	    void testSelectOrder() throws SQLException {
	    	//Arrange
	        salesOrderController.createSalesOrder();
	        salesOrderController.addCustomerToOrder("40626247");

	        SalesOrder order = salesOrderController.getCurrentOrder();
	        order.setOrderNo(1);
            //Act
	        boolean result = poc.selectOrder(order);
	        //Assert
	        assertTrue(result);
	        assertNotNull(salesOrderController.getCurrentOrder());
	        assertEquals(1, salesOrderController.getCurrentOrder().getOrderNo());
	    }
	    @Test
	    void testUpdateOrderLine() throws Exception {
	    	//Arrange
	        salesOrderController.createSalesOrder();
	        salesOrderController.addCustomerToOrder("40626247");
	        Product product = salesOrderController.findProduct("Lady Pink").get(0);
	        salesOrderController.addProductToOrder(product, 5);
            SalesOrder order=salesOrderController.getCurrentOrder();
            poc.selectOrder(order);
	        SalesOrderLine orderLine = order.getOrderLines().get(0);
	        //Act
	        boolean result=poc.updateOrderLine(orderLine, 10);
	        //Assert
	        assertTrue(result);
	        assertEquals(10.0, orderLine.getQuantity());
	    }
	    @Test
	    void testDiscardProductOrderLine()throws DataAccessException {
	    	//Arrange
	        Product product = new Product(1, "Lady Pink", new ProductCategory("Æble"), 500, new UnitConverter("piece", 0.2, 10.0));
	        double quantityToDiscard = 100;
            //Act
	        boolean success = poc.discardProducts(product, quantityToDiscard);
            //Assert
	        assertTrue(success);

	        ProductDB productDB = new ProductDB();
	        Product updatedProduct = productDB.findProductById(product.getProductId());  
	        assertEquals(400, updatedProduct.getStock());
	    }
	    @Test
	    void testUpdateOrder() throws Exception {
            //Arrange
	        SalesOrder so=salesOrderController.createSalesOrder();
	        salesOrderController.addCustomerToOrder("40626247");
	        Product product = salesOrderController.findProduct("Lady Pink").get(0);
	        salesOrderController.addProductToOrder(product, 5);
	        salesOrderController.getCurrentOrder().setOrderNo(1); 
	        salesOrderController.getCurrentOrder().setOrderStatus(OrderStatus.READY_FOR_PACKING);
	        //Act
	        boolean result = poc.updateOrder();
	        //Assert
	        assertTrue(result);
	        assertEquals(OrderStatus.READY_FOR_PACKING, salesOrderController.getCurrentOrder().getOrderStatus());
	        assertEquals(25.0,so.getTotalPrice().doubleValue(),0.0001);
	    }

	    @Test
	    void testSelectPackingList() throws Exception {
	      //Arrange
	        List<PackingList> packingLists = poc.viewPackingLists();
	        PackingList packingList = packingLists.stream()
	                .filter(pl -> "Byen Packing".equals(pl.getName()))
	                .findFirst()
	                .orElseThrow(() -> new Exception("Pakkeliste med navnet 'Byen Packing' blev ikke fundet"));
            //Act
	        PackingList result = poc.selectPackingList(packingList);
           //Assert
	        assertNotNull(result);
	        assertEquals("Byen Packing", result.getName());
	        assertFalse(result.getSalesOrders().isEmpty());
	        assertEquals(2, result.getSalesOrders().size());
	    }



}
