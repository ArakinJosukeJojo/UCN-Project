package databaselayer;

import java.util.ArrayList;
import java.util.List;

import guilayer.DatabaseObserver;

/**
 * Monitors the sales order count in the database and notifies registered
 * observers when changes occur. Runs continuously as a separate thread to check
 * for updates.
 * 
 * - Observers are notified whenever the count of remaining orders changes. -
 * Monitoring can be stopped by calling stopMonitoring(). - Uses a sleep
 * interval (3 seconds) between database checks to reduce load.
 */

public class SalesOrderMonitor implements Runnable {
	private final SalesOrderDB salesOrderDB;
	private final List<DatabaseObserver> observers = new ArrayList<>();
	private boolean running = true;
	private int lastKnownOrderCount = -1;

	public SalesOrderMonitor(SalesOrderDB salesOrderDB) {
		this.salesOrderDB = salesOrderDB;
	}

	public void addObserver(DatabaseObserver observer) {
		observers.add(observer);

		if (lastKnownOrderCount != -1) {
			observer.update(lastKnownOrderCount);
		}
	}

	public void stopMonitoring() {
		running = false;
	}

	private void notifyObservers(int remainingOrders) {
		for (DatabaseObserver observer : observers) {
			observer.update(remainingOrders);
		}
	}

	@Override
	public void run() {
		System.out.println("SalesOrderMonitor thread started.");
		while (running) {
			try {
				int currentOrderCount = salesOrderDB.getRemainingOrdersCount();
				if (currentOrderCount != lastKnownOrderCount) {
					lastKnownOrderCount = currentOrderCount;
					System.out.println("Order count changed. Notifying observers...");
					notifyObservers(currentOrderCount);
				}
				Thread.sleep(3000);
			} catch (InterruptedException | DataAccessException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}
