package databaselayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import model.Customer;
import model.Location;
import model.PackingList;
import model.SalesOrder;

/**
 * Handles database operations related to customers and companies. Includes
 * methods for finding customers by CVR, retrieving customers on packing lists,
 * and mapping customer data from the database.
 */

public class CompanyDB implements CompanyDBIF {
	private static final String selectAll = """
			    SELECT co.CVR, co.Name, co.Phone_no, co.Email, l.Address, cz.City, cz.Zip_code, c.Preferred_unit, c.List_id_FK
			    FROM COMPANY co
			    JOIN CUSTOMER c ON co.CVR = c.Cvr_fk
			    JOIN LOCATION l ON co.Location_id_FK = l.Location_id
			    JOIN CITY_ZIP_CODE cz ON l.Zip_code_FK = cz.Zip_code
			    WHERE c.Cvr_fk = ?
			""";
	private static final String selectCustomerBasicInfo = """
			    SELECT co.CVR, co.Name
			    FROM COMPANY co
			    JOIN CUSTOMER c ON co.CVR = c.Cvr_fk
			    WHERE co.CVR = ?
			""";
	private static final String findCustomerByCVRQ = selectAll;

	private PreparedStatement findBasicCustomerByCVR;
	private PreparedStatement findCustomerByCVR;
	private Connection con;

	public CompanyDB() throws DataAccessException, SQLException {
		con = DBConnection.getInstance().getConnection();
		try {
			findCustomerByCVR = con.prepareStatement(findCustomerByCVRQ, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			throw new DataAccessException("Couldn’t connect to the database", e);
		}
	}

	@Override
	public Customer findCustomerByCVR(String CVR) throws DataAccessException {
		Customer c = null;
		try {
			findCustomerByCVR.setString(1, CVR);
			ResultSet rs = findCustomerByCVR.executeQuery();
			if (rs.next()) {
				c = buildObject(rs);
			} else {
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Failed to find the customer with the given CVR number", e);
		}
		return c;
	}

	// Maps a ResultSet to a Customer object by extracting relevant fields from the
	// database.
	private Customer buildObject(ResultSet rs) throws DataAccessException {
		Customer c = null;
		try {
			String CVR = rs.getString("CVR");
			String name = rs.getString("Name");
			String phoneNo = rs.getString("Phone_no");
			String email = rs.getString("Email");
			String address = rs.getString("Address");
			String city = rs.getString("City");
			String zipcode = rs.getString("Zip_code");
			String preferredUnit = rs.getString("Preferred_unit");
			int packingListId = rs.getInt("List_id_FK");

			System.out.println("Mapping customer: CVR=" + CVR + ", Name=" + name);

			Location location = new Location(address, city, zipcode);
			c = new Customer(CVR, name, phoneNo, email, location, preferredUnit, packingListId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Failed to retrieve customer object", e);
		}
		return c;
	}

	public Set<Customer> getCustomerOnPackingList(PackingList packingList) throws DataAccessException, SQLException {
		Set<Customer> customers = new HashSet<>();

		Connection connection = DBConnection.getInstance().getConnection();

		String query = """
				    SELECT DISTINCT c.Cvr, c.Preferred_unit, co.Name, co.Phone_no, co.Email, l.Address, l.City, l.Zip_code
				    FROM PACKING_LIST pl
				    JOIN PACKING_LIST_ORDER plo ON pl.List_id = plo.Packing_list_id
				    JOIN SALES_ORDER so ON plo.Sales_order_no = so.Sales_order_no
				    JOIN CUSTOMER c ON so.Cvr_FK = c.Cvr_FK
				    JOIN COMPANY co ON c.Cvr_FK = co.Cvr
				    JOIN LOCATION l ON co.Location_id_FK = l.Location_id
				    WHERE pl.List_name = ?
				""";

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, packingList.getName());

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Location location = new Location(rs.getString("Address"), rs.getString("City"),
							rs.getString("Zip_code"));

					Customer customer = new Customer(rs.getString("Cvr"), rs.getString("Name"),
							rs.getString("Phone_no"), rs.getString("Email"), location, rs.getString("Preferred_unit"),
							rs.getInt("List_id"));

					customers.add(customer);
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException("Error fetching customers on packing list: " + packingList.getName(), e);
		}

		return customers;
	}

	public Customer getCustomerBySalesOrder(SalesOrder salesOrder) throws DataAccessException {
		String CustomerCVR = salesOrder.getCustomer().getCvr();
		return findCustomerByCVR(CustomerCVR);
	}
}
