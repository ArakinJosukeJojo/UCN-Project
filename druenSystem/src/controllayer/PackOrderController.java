package controllayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import databaselayer.DataAccessException;
import databaselayer.SalesOrderDB;
import databaselayer.SalesOrderMonitor;
import model.PackingList;
import model.Product;
import model.SalesOrder;
import model.SalesOrderLine;

/**
 * Manages packing orders by interacting with sales orders and products. Handles
 * packing lists, updates orders, discards products, and monitors sales orders.
 */
public class PackOrderController {
	private SalesOrderController salesOrderController;
	private SalesOrderMonitor som;
	private List<SalesOrder> currentPackingList;

	public PackOrderController() throws DataAccessException, SQLException {
		salesOrderController = new SalesOrderController();
		som = new SalesOrderMonitor(new SalesOrderDB());
		Thread monitorThread = new Thread(som);
		monitorThread.start();
	}

	public List<PackingList> viewPackingLists() throws SQLException {
		return salesOrderController.viewAllPackingLists();
	}

	public PackingList selectPackingList(PackingList packingList) throws SQLException {
		packingList = salesOrderController.selectPackingList(packingList);
		currentPackingList = packingList.getSalesOrders();
		return packingList;
	}

	public boolean selectOrder(SalesOrder salesOrder) throws SQLException {
		return salesOrderController.selectOrder(salesOrder);
	}

	public boolean updateOrder() throws SQLException {
		return salesOrderController.updateOrder();
	}

	public boolean discardProducts(Product product, double quantity) {
		ProductController productController = new ProductController();
		return productController.discardProducts(product, quantity);
	}

	public boolean updateOrderLine(SalesOrderLine so, double quantity) throws SQLException, DataAccessException {
		return salesOrderController.updateOrderLine(so, quantity);
	}

	public void stopMonitor() {
		som.stopMonitoring();
	}

	public SalesOrderMonitor getMonitor() {
		return som;
	}

	public SalesOrderController getSalesOrderController() {
		return salesOrderController;
	}

	public List<SalesOrder> getCurrentPackingList() {
		if (currentPackingList == null) {
			currentPackingList = new ArrayList<SalesOrder>();
		}
		return currentPackingList;
	}
}
