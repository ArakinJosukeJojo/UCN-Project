package databaselayer;

import model.Customer;

/**
 * Interface for database operations related to customers. Declares a method to
 * find a customer by their CVR number.
 */

public interface CompanyDBIF {
	public Customer findCustomerByCVR(String CVR) throws DataAccessException;

}