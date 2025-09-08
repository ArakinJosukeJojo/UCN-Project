package controllayer;

import databaselayer.DBCleaner;
import databaselayer.DataAccessException;
import model.Customer;
import model.OrderStatus;
import model.PackingList;
import model.Product;
import model.SalesOrder;
import model.SalesOrderLine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SalesOrderControllerTest {

    private SalesOrderController salesOrderController;
    private DBCleaner dbCleaner;

    @BeforeEach
    void setUp() throws SQLException, DataAccessException {
        dbCleaner = new DBCleaner();
        dbCleaner.cleanAndRecreateDatabase();
        salesOrderController = new SalesOrderController();
    }

    @Test
    void testCreateSalesOrder() {
        SalesOrder salesOrder = salesOrderController.createSalesOrder();
        assertNotNull(salesOrder, "SalesOrder burde ikke være null");
        assertEquals(BigDecimal.ZERO, salesOrder.getTotalPrice(), "Ny ordre skal have en totalpris på 0");
    }

    @Test
    void testAddCustomerToOrder() {
        salesOrderController.createSalesOrder();
        boolean result = salesOrderController.addCustomerToOrder("40626247");

        assertTrue(result, "Kunden burde være tilføjet til ordren");
        Customer customer = salesOrderController.getCurrentOrder().getCustomer();
        assertNotNull(customer, "Kunden burde være sat på ordren");
        assertEquals("40626247", customer.getCvr(), "Kundens CVR burde matche den tilføjede kunde");
    }

    @Test
    void testFindProduct() {
        List<Product> products = salesOrderController.findProduct("Lady Pink");

        assertNotNull(products, "Produktsøgning burde returnere en liste");
        assertFalse(products.isEmpty(), "Listen over produkter burde ikke være tom");
        assertEquals("Lady Pink", products.get(0).getName(), "Produktnavnet burde være 'Lady Pink'");
    }

    @Test
    void testAddProductToOrder() throws Exception {
        salesOrderController.createSalesOrder();
        salesOrderController.addCustomerToOrder("40626247");
        Product product = salesOrderController.findProduct("Lady Pink").get(0);

        System.out.println("Produktpris: " + product.getPrice());
        System.out.println("Produktets salgspris: " + product.getPrice().getSalesPrice());

        boolean result = salesOrderController.addProductToOrder(product, 5);
        assertTrue(result, "Produktet burde være tilføjet til ordren");

        SalesOrder order = salesOrderController.getCurrentOrder();
        assertEquals(1, order.getOrderLines().size(), "Ordren burde have én orderline");

        double actualQuantity = order.getOrderLines().get(0).getQuantity();
        assertEquals(5.0, actualQuantity, "Mængden på orderlinen burde være 5");

        BigDecimal expectedTotalPrice = product.getPrice().getSalesPrice().multiply(BigDecimal.valueOf(5));
        assertEquals(0, expectedTotalPrice.compareTo(order.getTotalPrice()), "Totalprisen burde matche forventningen");
    }


    @Test
    void testAddSameProductTwiceToOrder() throws Exception {
        salesOrderController.createSalesOrder();
        salesOrderController.addCustomerToOrder("40626247");
        Product product = salesOrderController.findProduct("Lady Pink").get(0);

        boolean firstAddResult = salesOrderController.addProductToOrder(product, 3);
        assertTrue(firstAddResult, "Første tilføjelse af produkt burde lykkes");

        boolean secondAddResult = salesOrderController.addProductToOrder(product, 2);
        assertTrue(secondAddResult, "Anden tilføjelse af samme produkt burde også lykkes");

        SalesOrder order = salesOrderController.getCurrentOrder();
        double actualQuantity = order.getOrderLines().stream()
            .filter(line -> line.getProduct().equals(product))
            .mapToDouble(line -> line.getQuantity())
            .sum();
        assertEquals(5.0, actualQuantity, "Den samlede mængde for produktet burde være 5");
    }

    @Test
    void testSaveOrder() throws Exception {
        salesOrderController.createSalesOrder();
        salesOrderController.addCustomerToOrder("40626247");
        Product product = salesOrderController.findProduct("Lady Pink").get(0);
        salesOrderController.addProductToOrder(product, 10);

        boolean result = salesOrderController.saveOrder();
        assertTrue(result, "Ordren burde blive gemt i databasen");
    }

    @Test
    void testViewAllPackingLists() {
        List<?> packingLists = salesOrderController.viewAllPackingLists();

        assertNotNull(packingLists, "Packing lists burde ikke være null");
        assertEquals(2, packingLists.size(), "Der burde være præcis 2 pakkelister");
    }

    @Test
    void testSelectOrder() throws SQLException {
        salesOrderController.createSalesOrder();
        salesOrderController.addCustomerToOrder("40626247");

        SalesOrder order = salesOrderController.getCurrentOrder();
        order.setOrderNo(1);

        boolean result = salesOrderController.selectOrder(order);
        assertTrue(result, "Ordren burde kunne vælges");
        assertNotNull(salesOrderController.getCurrentOrder(), "Den aktuelle ordre burde være sat");
        assertEquals(1, salesOrderController.getCurrentOrder().getOrderNo(), "Ordrenummeret burde være 1");
    }
    @Test
    void testUpdateOrderLine() throws Exception {
        salesOrderController.createSalesOrder();
        salesOrderController.addCustomerToOrder("40626247");
        Product product = salesOrderController.findProduct("Lady Pink").get(0);
        salesOrderController.addProductToOrder(product, 5);

        SalesOrderLine orderLine = salesOrderController.getCurrentOrder().getOrderLines().get(0);

        boolean result = salesOrderController.updateOrderLine(orderLine, 10);
        assertTrue(result, "Orderline burde blive opdateret");

        assertEquals(10.0, orderLine.getQuantity(), "Mængden i orderlinen burde være opdateret til 10");
    }
    @Test
    void testRemoveOrderLine() throws Exception {
        salesOrderController.createSalesOrder();
        salesOrderController.addCustomerToOrder("40626247");
        Product product = salesOrderController.findProduct("Lady Pink").get(0);
        salesOrderController.addProductToOrder(product, 5);

        SalesOrderLine orderLine = salesOrderController.getCurrentOrder().getOrderLines().get(0);
        boolean result = salesOrderController.removeOrderLine(orderLine);

        assertTrue(result, "Orderline burde blive fjernet");
        assertEquals(0, salesOrderController.getCurrentOrder().getOrderLines().size(), "Der burde ikke være nogen orderlines tilbage");
    }
    @Test
    void testUpdateOrder() throws Exception {

        salesOrderController.createSalesOrder();
        salesOrderController.addCustomerToOrder("40626247");
        Product product = salesOrderController.findProduct("Lady Pink").get(0);
        salesOrderController.addProductToOrder(product, 5);
        salesOrderController.getCurrentOrder().setOrderNo(1); 
        salesOrderController.getCurrentOrder().setOrderStatus(OrderStatus.READY_FOR_PACKING);
        boolean result = salesOrderController.updateOrder();
        assertTrue(result, "Ordren burde blive opdateret i databasen");
        assertEquals(OrderStatus.READY_FOR_PACKING, salesOrderController.getCurrentOrder().getOrderStatus(), 
            "Ordrestatus burde være opdateret");
    }

    @Test
    void testSelectPackingList() throws Exception {
      
        List<PackingList> packingLists = salesOrderController.viewAllPackingLists();
        PackingList packingList = packingLists.stream()
                .filter(pl -> "Byen Packing".equals(pl.getName()))
                .findFirst()
                .orElseThrow(() -> new Exception("Pakkeliste med navnet 'Byen Packing' blev ikke fundet"));

        PackingList result = salesOrderController.selectPackingList(packingList);

        assertNotNull(result, "Pakkelisten burde ikke være null");
        assertEquals("Byen Packing", result.getName(), "Navnet på pakkelisten burde være 'Byen Packing'");
        assertFalse(result.getSalesOrders().isEmpty(), "Pakkelisten burde have salgsordrer tilknyttet");
        assertEquals(2, result.getSalesOrders().size(), "Pakkelisten 'Byen Packing' burde have præcis 2 salgsordrer");
    }




    
}
