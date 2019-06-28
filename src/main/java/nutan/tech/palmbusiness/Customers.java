package nutan.tech.palmbusiness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import nutan.tech.creator.CustomerCreator;
import nutan.tech.listmodel.CustomersListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.CustomersModel;
import nutan.tech.utilities.APIUtilities;
import nutan.tech.utilities.DatabaseFunctions;

@Path("/customer")
public class Customers {

	DatabaseFunctions databaseFunctions;
	
	@POST
	@Path("{enterprise_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CustomersModel> newCustomer(@PathParam("enterprise_id") String enterpriseId, CustomersListModel customersList) {
		
		List<CustomersModel> customersModelList = CustomerCreator.getCustomers();
		customersModelList.addAll(customersList.getCustomersList());
		
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();

		PreparedStatement preparedStmt;

		String query = "INSERT INTO db_palm_business.customers (customer_id, enterprise_id, customer_type_code, enterprise_name, person_name, gstin, mobile_number, address_landline, address_email_main, address_email_ccc, billing_address, shipping_address, state_province, country)"
		        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			preparedStmt = connection.prepareStatement(query);
			connection.setAutoCommit(false);

			for (CustomersModel customersModel : customersModelList) {

				String customerId = "CUS" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);

				preparedStmt.setString(1, customerId);
				preparedStmt.setString(2, customersModel.getEnterprise_id());
				preparedStmt.setInt(3, customersModel.getCustomer_type_code());
				preparedStmt.setString(4, customersModel.getEnterprise_name());
				preparedStmt.setString(5, customersModel.getPerson_name());
				preparedStmt.setString(6, customersModel.getGstin());
				preparedStmt.setString(7, customersModel.getMobile_number());
				preparedStmt.setString(8, customersModel.getAddress_landline());
				preparedStmt.setString(9, customersModel.getAddress_email_main());
				preparedStmt.setString(10, customersModel.getAddress_email_ccc());
				preparedStmt.setString(11, customersModel.getBilling_address());
				preparedStmt.setString(12, customersModel.getShipping_address());
				preparedStmt.setString(13, customersModel.getState_province());
				preparedStmt.setString(14, customersModel.getCountry());
				preparedStmt.addBatch();
			}
			
			preparedStmt.executeBatch();
			connection.commit();

			customersModelList = new ArrayList<>();
			String getQuery = "SELECT * FROM db_palm_business.customers WHERE db_palm_business.customers.enterprise_id = '" + enterpriseId + "'";

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(getQuery);

			while (resultSet.next())	{

				String customerId = resultSet.getString("customer_id");
				int customerTypeCode = resultSet.getInt("customer_type_code");
				String enterpriseName = resultSet.getString("enterprise_name");
				String personName = resultSet.getString("person_name");
				String gstin = resultSet.getString("gstin");
				String mobileNumber = resultSet.getString("mobile_number");
				String addressLandline = resultSet.getString("address_landline");
				String addressEmailMain = resultSet.getString("address_email_main");
				String addressEmailCCC = resultSet.getString("address_email_ccc");
				String billingAddress = resultSet.getString("billing_address");
				String shippingAddress = resultSet.getString("shipping_address");
				String stateProvince = resultSet.getString("state_province");
				String country = resultSet.getString("country");

				CustomersModel model = new CustomersModel(customerTypeCode, customerId, enterpriseId, enterpriseName, personName, gstin, mobileNumber, addressLandline, addressEmailMain, addressEmailCCC, billingAddress, shippingAddress, stateProvince, country);
				customersModelList.add(model);
			}
			
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
			databaseFunctions.closeDBOperations(connection, preparedStmt, resultSet);
			
		} catch (SQLException e) {
			e.printStackTrace();
			customersModelList = new ArrayList<>();
		} 
		
		return customersModelList;
	}
	
	@PUT
	@Path("{enterprise_id}/{customer_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> modifyCustomer(@PathParam("enterprise_id") String enterpriseId, @PathParam("customer_id") String customerId, CustomersModel customersModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE db_palm_business.customers SET customers.enterprise_id = ?, customers.customer_type_code = ?, customers.enterprise_name = ?, customers.person_name = ?, customers.gstin = ?, customers.mobile_number = ?, customers.address_landline = ?, customers.address_email_main = ?, customers.address_email_ccc = ?, customers.billing_address = ?, customers.shipping_address = ?, customers.state_province = ?, customers.country = ? WHERE customers.enterprise_id = '" + enterpriseId + "' AND customers.customer_id = '" + customerId + "'";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, customersModel.getEnterprise_id());
		    preparedStmt.setInt (2, customersModel.getCustomer_type_code());
		    preparedStmt.setString (3, customersModel.getEnterprise_name());
		    preparedStmt.setString (4, customersModel.getPerson_name());
		    preparedStmt.setString (5, customersModel.getGstin());
		    preparedStmt.setString (6, customersModel.getMobile_number());
			preparedStmt.setString(7, customersModel.getAddress_landline());
			preparedStmt.setString(8, customersModel.getAddress_email_main());
			preparedStmt.setString(9, customersModel.getAddress_email_ccc());
			preparedStmt.setString(10, customersModel.getBilling_address());
			preparedStmt.setString(11, customersModel.getShipping_address());
			preparedStmt.setString(12, customersModel.getState_province());
			preparedStmt.setString(13, customersModel.getCountry());
				    
		    // execute the preparedstatement
		    preparedStmt.executeUpdate();
		    
			callResultModelList.add(new CallResultModel(false, true));
		
		} catch (SQLException e) {
			e.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false));
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
		}
		
		return callResultModelList;
	}
	
	@GET
	@Path("{enterprise_id}")
	public List<CustomersModel> getCustomers(@PathParam("enterprise_id") String enterpriseId) {
		
		List<CustomersModel> customersList = new ArrayList<>();
		
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
	    	Statement statement = null;
		ResultSet resultSet = null;
		
		String query = "SELECT * FROM db_palm_business.customers WHERE db_palm_business.customers.enterprise_id = '" + enterpriseId + "'";

		System.out.println("Query: " + query);
		
	    try {

		statement = connection.createStatement();
		resultSet = statement.executeQuery(query);

		while (resultSet.next())	{

			String customerId = resultSet.getString("customer_id");
			int customerTypeCode = resultSet.getInt("customer_type_code");
			String enterpriseName = resultSet.getString("enterprise_name");
			String personName = resultSet.getString("person_name");
			String gstin = resultSet.getString("gstin");
			String mobileNumber = resultSet.getString("mobile_number");
			String addressLandline = resultSet.getString("address_landline");
			String addressEmailMain = resultSet.getString("address_email_main");
			String addressEmailCCC = resultSet.getString("address_email_ccc");
			String billingAddress = resultSet.getString("billing_address");
			String shippingAddress = resultSet.getString("shipping_address");
			String stateProvince = resultSet.getString("state_province");
			String country = resultSet.getString("country");

			CustomersModel model = new CustomersModel(customerTypeCode, customerId, enterpriseId, enterpriseName, personName, gstin, mobileNumber, addressLandline, addressEmailMain, addressEmailCCC, billingAddress, shippingAddress, stateProvince, country);
			customersList.add(model);
		}
	
		databaseFunctions.closeDBOperations(connection, statement, resultSet);		

		} catch (SQLException e) {
			
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}		
	
	    return customersList;		
	}

	@GET
	@Path("{enterprise_id}/{customer_type}")
	public List<CustomersModel> getCustomersByType(@PathParam("enterprise_id") String enterpriseId, @PathParam("customer_type") int customerType) {
		
		List<CustomersModel> customersList = new ArrayList<>();
		
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
	    	Statement statement = null;
		ResultSet resultSet = null;
		
		String query = "SELECT * FROM db_palm_business.customers WHERE db_palm_business.customers.enterprise_id = '" + enterpriseId + "' AND db_palm_business.customers.customer_type_code = " + customerType;

	    try {

	    	statement = connection.createStatement();
		resultSet = statement.executeQuery(query);		

		while (resultSet.next())	{

			String customerId = resultSet.getString("customer_id");
			int customerTypeCode = resultSet.getInt("customer_type_code");
			String enterpriseName = resultSet.getString("enterprise_name");
			String personName = resultSet.getString("person_name");
			String gstin = resultSet.getString("gstin");
			String mobileNumber = resultSet.getString("mobile_number");
			String addressLandline = resultSet.getString("address_landline");
			String addressEmailMain = resultSet.getString("address_email_main");
			String addressEmailCCC = resultSet.getString("address_email_ccc");
			String billingAddress = resultSet.getString("billing_address");
			String shippingAddress = resultSet.getString("shipping_address");
			String stateProvince = resultSet.getString("state_province");
			String country = resultSet.getString("country");

			CustomersModel model = new CustomersModel(customerTypeCode, customerId, enterpriseId, enterpriseName, personName, gstin, mobileNumber, addressLandline, addressEmailMain, addressEmailCCC, billingAddress, shippingAddress, stateProvince, country);
			customersList.add(model);
		}
	
		databaseFunctions.closeDBOperations(connection, statement, resultSet);		

		} catch (SQLException e) {
			
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}		
	
	    return customersList;		
	}
	
	@DELETE
	@Path("{enterprise_id}/{customer_id}")
	public List<CallResultModel> deleteCustomer(@PathParam("enterprise_id") String enterpriseId, @PathParam("customer_id") String customerId) throws SQLException {

		int salesInvoicesCount = 0, estimateInvoicesCount = 0;

		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();

		String query = "SELECT sales_invoices.sales_invoice_id, estimate_invoice.estimate_invoice_id FROM customers LEFT JOIN sales_invoices ON customers.customer_id = sales_invoices.customer_id LEFT JOIN estimate_invoice ON customers.customer_id = estimate_invoice.customer_id WHERE customers.customer_id = '" + customerId + "'";

		Statement statement = null;
		ResultSet resultset = null;
		PreparedStatement preparedStmt = null;

		try {

			statement = connection.createStatement();
			resultset = statement.executeQuery(query);

			if (resultset.isBeforeFirst()) {

				while (resultset.next()) {

					String salesInvoiceId = resultset.getString("sales_invoice_id");
					String estimateInvoiceId = resultset.getString("estimate_invoice_id");

					if (salesInvoiceId != null)
						salesInvoicesCount++;

					if (estimateInvoiceId != null)
						estimateInvoicesCount++;
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultset);
			return null;
		}

		if (salesInvoicesCount > 0 || estimateInvoicesCount > 0) {

			databaseFunctions.closeDBOperations(connection, statement, resultset);
			statement.close();
			callResultModelList.add(new CallResultModel(true, false, "Customer cannot be deleted due to " + salesInvoicesCount + " Sales Invoice(s) and " + estimateInvoicesCount + " Estimate(s) are Issued."));
			return callResultModelList;
		}

		try {

			query = "DELETE FROM db_palm_business.customers WHERE customers.customer_id = ? and customers.enterprise_id =  ?";
			preparedStmt = connection.prepareStatement(query);

			preparedStmt.setString (1, customerId);
			preparedStmt.setString (2, enterpriseId);

			preparedStmt.executeUpdate();
			callResultModelList.add(new CallResultModel(false, true, "Deleted Successfully..."));

		} catch (SQLException e) {
			e.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false));
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
		}

		return callResultModelList;
	}
	
}
