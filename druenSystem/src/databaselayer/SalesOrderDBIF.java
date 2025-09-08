package databaselayer;

import java.sql.SQLException;
import java.util.List;
import model.PackingList;
import model.SalesOrder;

/**
 * 
 */
public interface SalesOrderDBIF {
	boolean saveSalesOrder(SalesOrder order) throws DataAccessException, SQLException;

	List<PackingList> getAllPackingLists() throws SQLException;

	boolean getSalesOrder(SalesOrder salesOrder) throws DataAccessException, SQLException;

	PackingList getPackingList(PackingList packingList) throws DataAccessException, SQLException;

	boolean updateOrder(SalesOrder currentOrder) throws DataAccessException;
}
