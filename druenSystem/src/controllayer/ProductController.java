package controllayer;

import java.util.ArrayList;
import java.util.List;

import databaselayer.DataAccessException;
import databaselayer.ProductDB;
import databaselayer.ProductDBIF;
import model.Product;

/**
 * Handles product-related operations, such as finding products by name and
 * updating stock when discarding products.
 */

public class ProductController {

	public List<Product> findProductByName(String productName) {
		List<Product> productList = new ArrayList<Product>();
		ProductDBIF p;
		try {
			p = new ProductDB();
			productList = p.findProductByName(productName);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return productList;
	}

	public boolean discardProducts(Product product, double quantity) {
		boolean success = false;
		try {
			ProductDB productDB = new ProductDB();
			success = productDB.updateStock(product.getProductId(), quantity, false);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return success;
	}

}
