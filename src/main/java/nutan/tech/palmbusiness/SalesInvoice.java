package nutan.tech.palmbusiness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nutan.tech.creator.SalesInvoiceCreator;
import nutan.tech.listmodel.SalesInvoiceListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.SalesInvoiceModel;
import nutan.tech.utilities.APIUtilities;
import nutan.tech.utilities.DatabaseFunctions;

@Path("/sales_invoice")
@Produces(MediaType.APPLICATION_JSON)
public class SalesInvoice {

	private DatabaseFunctions databaseFunctions;
	
	@POST
	@Path("{enterprise_id}/{invoice_number}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> newSalesInvoice(@PathParam("enterprise_id") final String enterpriseId, @PathParam("invoice_number") final String invoiceNumber, final SalesInvoiceListModel salesInvoicesList) {
		
		final List<CallResultModel> callResultModelList = new ArrayList<>();

		Thread insert2Database = new Thread(new Runnable() {
					
			@Override
			public void run() {
			
				List<SalesInvoiceModel> salesInvoiceModelList = SalesInvoiceCreator.getSalesInvoices();
				salesInvoiceModelList.addAll(salesInvoicesList.getSalesInvoicesList());
				
				databaseFunctions = new DatabaseFunctions();
				Connection connection = databaseFunctions.connect2DB();
				PreparedStatement preparedStmt = null;
				
				String query = "INSERT INTO db_palm_business.sales_invoices (sales_invoice_id, enterprise_id, customer_id, invoice_number, reference, status, is_rate_including_gst, entry_date, due_date, billing_address, shipping_address, state_province, country, discount_type_code, given_discount, total_amount, msg_to_customer)"
				        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						
				try {
					preparedStmt = connection.prepareStatement(query);
					connection.setAutoCommit(false);

					for (SalesInvoiceModel salesInvoiceModel : salesInvoiceModelList) {

						String salesInvoiceId = "SI" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);
						preparedStmt.setString(1, salesInvoiceId);
						preparedStmt.setString(2, salesInvoiceModel.getEnterprise_id());
						preparedStmt.setString(3, salesInvoiceModel.getCustomer_id());
						preparedStmt.setString(4, salesInvoiceModel.getInvoice_number());
						preparedStmt.setString(5, salesInvoiceModel.getReference());
						preparedStmt.setBoolean(6, salesInvoiceModel.isStatus());
						preparedStmt.setBoolean(7, salesInvoiceModel.isIs_rate_including_gst());
						preparedStmt.setString(8, salesInvoiceModel.getEntry_date());
						preparedStmt.setString(9, salesInvoiceModel.getDue_date());
						preparedStmt.setString(10, salesInvoiceModel.getBilling_address());
						preparedStmt.setString(11, salesInvoiceModel.getShipping_address());
						preparedStmt.setString(12, salesInvoiceModel.getState_province());
						preparedStmt.setString(13, salesInvoiceModel.getCountry());
						preparedStmt.setInt(14, salesInvoiceModel.getDiscount_type_code());
						preparedStmt.setDouble(15, salesInvoiceModel.getGiven_discount());
						preparedStmt.setDouble(16, salesInvoiceModel.getTotal_amount());
						preparedStmt.setString(17, salesInvoiceModel.getMsg_to_customer());
						preparedStmt.addBatch();
					}    
					
					preparedStmt.executeBatch();
					connection.commit();
					callResultModelList.add(new CallResultModel(false, true));
					databaseFunctions.closeDBOperations(connection, preparedStmt, null);
				
				} catch (SQLException e) {
					e.printStackTrace();
					callResultModelList.add(new CallResultModel(true, false, "Oops! Something Went Wrong, Try Again"));
					databaseFunctions.closeDBOperations(connection, preparedStmt, null);
				}
			}
		});
		
		Thread isExistThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				databaseFunctions = new DatabaseFunctions();
				Connection connection = databaseFunctions.connect2DB();

				String getQuery = "SELECT * FROM db_palm_business.sales_invoices where db_palm_business.sales_invoices.enterprise_id = '" + enterpriseId + "' AND db_palm_business.sales_invoices.invoice_number = '" + invoiceNumber + "'";

				try {

					Statement st = connection.createStatement();
					ResultSet resultSet = st.executeQuery(getQuery);

					if (resultSet.isBeforeFirst()) {

						callResultModelList.add(new CallResultModel(true, false, "Invoice Number Already Exist"));
					}

				} catch (SQLException e) {
					e.printStackTrace();
					callResultModelList.add(new CallResultModel(true, false, "Oops! Something Went Wrong, Try Again"));
				}

			}
		});
		
		try {
			isExistThread.start();
			isExistThread.join();

			if (callResultModelList.isEmpty()) {
				
				insert2Database.start();				
				insert2Database.join();
			} 
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false, "Oops! Something Went Wrong, Try Again"));
		}
		
		return callResultModelList;
	}

	@GET
	@Path("{enterprise_id}")
	public List<SalesInvoiceModel> getAllInvoices(@PathParam("enterprise_id") String enterpriseId) {
		
		List<SalesInvoiceModel> salesInvoiceList = new ArrayList<>();

		databaseFunctions = new DatabaseFunctions();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = databaseFunctions.connect2DB();

		String query = "SELECT db_palm_business.sales_invoices.sales_invoice_id, db_palm_business.customers.customer_id, (CASE WHEN db_palm_business.customers.enterprise_name != '' THEN db_palm_business.customers.enterprise_name ELSE db_palm_business.customers.person_name END) AS customer_name, db_palm_business.sales_invoices.invoice_number, db_palm_business.sales_invoices.reference, db_palm_business.sales_invoices.status, db_palm_business.sales_invoices.is_rate_including_gst, db_palm_business.sales_invoices.entry_date, db_palm_business.sales_invoices.due_date, db_palm_business.sales_invoices.billing_address, db_palm_business.sales_invoices.shipping_address, db_palm_business.sales_invoices.state_province, db_palm_business.sales_invoices.country, db_palm_business.sales_invoices.discount_type_code, db_palm_business.sales_invoices.given_discount, db_palm_business.sales_invoices.total_amount, db_palm_business.sales_invoices.msg_to_customer FROM db_palm_business.sales_invoices LEFT JOIN db_palm_business.customers ON db_palm_business.sales_invoices.customer_id = db_palm_business.customers.customer_id WHERE db_palm_business.sales_invoices.enterprise_id = '" + enterpriseId + "' ORDER BY db_palm_business.sales_invoices.entry_date";

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			if (resultSet.isBeforeFirst()) {

				while (resultSet.next()) {

					String saleInvoiceId = resultSet.getString("sales_invoice_id");
					String customerId = resultSet.getString("customer_id");
					String customerName = resultSet.getString("customer_name");
					String invoiceNumber = resultSet.getString("invoice_number");
					String reference = resultSet.getString("reference");
					boolean status = resultSet.getBoolean("status");
					boolean isRateIncludingGST = resultSet.getBoolean("is_rate_including_gst");
					String entryDate = resultSet.getString("entry_date");
					String dueDate = resultSet.getString("due_date");
					String billingAddress = resultSet.getString("billing_address");
					String shippingAddress = resultSet.getString("shipping_address");
					String stateProvince = resultSet.getString("state_province");
					String country = resultSet.getString("country");
					int discountTypeCode = resultSet.getInt("discount_type_code");
					double givenDiscount = resultSet.getDouble("given_discount");
					double totalAmount = resultSet.getDouble("total_amount");
					String msgToCustomer = resultSet.getString("msg_to_customer");

					SalesInvoiceModel salesInvoiceModel = new SalesInvoiceModel(discountTypeCode, givenDiscount, totalAmount, status, isRateIncludingGST, saleInvoiceId, enterpriseId, customerId, customerName, invoiceNumber, reference, entryDate, dueDate, billingAddress, shippingAddress, stateProvince, country, msgToCustomer);
					salesInvoiceList.add(salesInvoiceModel);
				}

			} else {

				return salesInvoiceList;
			}

			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}

		return salesInvoiceList;
	}
	
	@DELETE
	@Path("{sales_invoice_id}")
	public List<CallResultModel> deleteSalesInvoice(@PathParam("sales_invoice_id") String salesInvoiceId) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "DELETE FROM db_palm_business.sales_invoices WHERE db_palm_business.sales_invoices.sales_invoice_id = ?";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, salesInvoiceId);
				    
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
	
}
