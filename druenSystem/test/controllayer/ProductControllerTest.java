package controllayer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import databaselayer.DBCleaner;
import databaselayer.ProductDB;
import databaselayer.DataAccessException;
import model.Product;
import model.ProductCategory;
import model.UnitConverter;

class ProductControllerTest {
    private static ProductController productController;

    @BeforeAll
    static void initDatabase() {
        try {
            DBCleaner dbCleaner = new DBCleaner();
            dbCleaner.dropAllTables();
            dbCleaner.createAllTables();
            dbCleaner.populateTables();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to initialize the database before all tests: " + e.getMessage());
        }
    }

    @BeforeEach
    public void setUp() throws Exception {
        productController = new ProductController();
    }

    @Test
    void testDiscardProducts() throws DataAccessException {
        Product product = new Product(1, "Lady Pink", new ProductCategory("Æble"), 500, new UnitConverter("piece", 0.2, 10.0));
        double quantityToDiscard = 100;

        boolean success = productController.discardProducts(product, quantityToDiscard);

        assertTrue(success, "discardProducts should return true when successful");

        ProductDB productDB = new ProductDB();
        Product updatedProduct = productDB.findProductById(product.getProductId());  
        assertEquals(400, updatedProduct.getStock(), "Stock should be reduced to 400 after discarding 100.");
    }

    @AfterEach
    public void cleanUp() throws Exception {
        // Reset after each test if needed
    }
}
