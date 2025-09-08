package controllayer;

import java.sql.SQLException;

import databaselayer.CompanyDB;
import databaselayer.CompanyDBIF;
import databaselayer.DataAccessException;
import model.Customer;

/**
 * Handles operations related to customers, such as finding a customer by CVR.
 * Acts as a link between the application and the database layer.
 */
public class CompanyController {
	private CompanyDBIF companyDB;

	public CompanyController() {
		try {
			companyDB = new CompanyDB();
		} catch (DataAccessException | SQLException e) {
			e.printStackTrace();
		}
	}

	public Customer findCustomerByCVR(String cvr) {
		Customer customer = null;
		try {
			customer = companyDB.findCustomerByCVR(cvr);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return customer;
	}

}
