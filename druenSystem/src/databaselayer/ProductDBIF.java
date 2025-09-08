package databaselayer;

import java.util.List;
import model.Product;

public interface ProductDBIF {
	public List<Product> findProductByName(String productName) throws DataAccessException;

	boolean updateStock(int productId, double newStock, boolean allowNegativeStock) throws DataAccessException;

}
