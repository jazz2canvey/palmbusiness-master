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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nutan.tech.creator.VendorCreator;
import nutan.tech.listmodel.VendorsListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.VendorModel;
import nutan.tech.utilities.APIUtilities;
import nutan.tech.utilities.DatabaseFunctions;

@Path("/vendor")
@Produces(MediaType.APPLICATION_JSON)
public class Vendor {

	DatabaseFunctions databaseFunctions;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> newVendor(VendorsListModel vendorsList) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		List<VendorModel> vendorsModelList = VendorCreator.getVendors();
		vendorsModelList.addAll(vendorsList.getVendorsList());
		
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();

		String query = " INSERT INTO db_palm_business.vendors (vendor_id, enterprise_id, honorific, full_name, enterprise_name, gstin, email_main, email_ccc, landline, mobile, billing_address, state_province, country, note)"
		        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement preparedStmt = null;
		
		try {
			preparedStmt = connection.prepareStatement(query);
			connection.setAutoCommit(false);

			for (VendorModel vendorModel : vendorsModelList) {

				String vendorId = "VEN" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);

				preparedStmt.setString(1, vendorId);
				preparedStmt.setString(2, vendorModel.getEnterprise_id());
				preparedStmt.setString(3, vendorModel.getHonorific());
				preparedStmt.setString(4, vendorModel.getFull_name());
				preparedStmt.setString(5, vendorModel.getEnterprise_name());
				preparedStmt.setString(6, vendorModel.getGstin());
				preparedStmt.setString(7, vendorModel.getEmail_main());
				preparedStmt.setString(8, vendorModel.getEmail_ccc());
				preparedStmt.setString(9, vendorModel.getLandline());
				preparedStmt.setString(10, vendorModel.getMobile());
				preparedStmt.setString(11, vendorModel.getBilling_address());
				preparedStmt.setString(12, vendorModel.getState_province());
				preparedStmt.setString(13, vendorModel.getCountry());
				preparedStmt.setString(14, vendorModel.getNote());
				preparedStmt.addBatch();
			}
			
			preparedStmt.executeBatch();
			connection.commit();
			callResultModelList.add(new CallResultModel(false, true));
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
			
		} catch (SQLException e) {
			e.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false));
		} 
		
		return callResultModelList;
	}
	
	@PUT
	@Path("/{enterprise_id}/{vendor_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> modifyMyVendor(@PathParam("enterprise_id") String enterpriseId, @PathParam("vendor_id") String vendorId, VendorModel vendorModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE db_palm_business.vendors SET db_palm_business.vendors.honorific = ?, db_palm_business.vendors.full_name = ?, db_palm_business.vendors.enterprise_name = ?, db_palm_business.vendors.gstin = ?, db_palm_business.vendors.email_main = ?, db_palm_business.vendors.email_ccc = ?, db_palm_business.vendors.landline = ?, db_palm_business.vendors.mobile = ?, db_palm_business.vendors.billing_address = ?, db_palm_business.vendors.state_province = ?, db_palm_business.vendors.country = ?, db_palm_business.vendors.note = ? WHERE db_palm_business.vendors.vendor_id = '" + vendorId + "' and db_palm_business.vendors.enterprise_id = '" + enterpriseId + "'";
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);

			preparedStmt.setString(1, vendorModel.getHonorific());
			preparedStmt.setString(2, vendorModel.getFull_name());
			preparedStmt.setString(3, vendorModel.getEnterprise_name());
			preparedStmt.setString(4, vendorModel.getGstin());
			preparedStmt.setString(5, vendorModel.getEmail_main());
			preparedStmt.setString(6, vendorModel.getEmail_ccc());
			preparedStmt.setString(7, vendorModel.getLandline());
			preparedStmt.setString(8, vendorModel.getMobile());
			preparedStmt.setString(9, vendorModel.getBilling_address());
			preparedStmt.setString(10, vendorModel.getState_province());
			preparedStmt.setString(11, vendorModel.getCountry());
			preparedStmt.setString(12, vendorModel.getNote());
				    
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
	@Consumes(MediaType.APPLICATION_JSON)
	public List<VendorModel> getAllVendors(@PathParam("enterprise_id") String enterpriseId) {

		List<VendorModel> vendorsList = new ArrayList<>();
		
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		Statement statement = null;
		ResultSet resultset = null;
		
	    String query = "SELECT * FROM db_palm_business.vendors WHERE db_palm_business.vendors.enterprise_id = '" + enterpriseId + "' ORDER BY db_palm_business.vendors.enterprise_name";
	  
	    try {

			statement = connection.createStatement();
			resultset = statement.executeQuery(query);
			
			while (resultset.next()) {

				vendorsList.add(new VendorModel(resultset.getString("vendor_id"), resultset.getString("enterprise_id"), resultset.getString("honorific"), resultset.getString("full_name"), resultset.getString("enterprise_name"), resultset.getString("gstin"), resultset.getString("email_main"), resultset.getString("email_ccc"), resultset.getString("landline"), resultset.getString("mobile"), resultset.getString("billing_address"), resultset.getString("state_province"), resultset.getString("country"), resultset.getString("note")));
		  	}
			
			databaseFunctions.closeDBOperations(connection, statement, resultset);
			
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultset);
			return null;
		}

        return vendorsList;
	}
	
	@DELETE
	@Path("{enterprise_id}/{vendor_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> deleteVendor(@PathParam("enterprise_id") String enterpriseId, @PathParam("vendor_id") String vendorId) throws SQLException {

		int purchaseInvoicesCount = 0, purchaseOrdersCount = 0;

		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();

		String query = "SELECT purchase_invoices.purchase_invoice_id, purchase_order.purchase_order_id FROM vendors LEFT JOIN purchase_invoices ON vendors.vendor_id = purchase_invoices.vendor_id LEFT JOIN purchase_order ON vendors.vendor_id = purchase_order.vendor_id WHERE vendors.vendor_id = '" + vendorId + "'";

		Statement statement = null;
		ResultSet resultset = null;
		PreparedStatement preparedStmt = null;

		try {

			statement = connection.createStatement();
			resultset = statement.executeQuery(query);

			if (resultset.isBeforeFirst()) {

				while (resultset.next()) {

					String purchaseInvoiceId = resultset.getString("purchase_invoice_id");
					String purchaseOrderId = resultset.getString("purchase_order_id");

					if (purchaseInvoiceId != null)
						purchaseInvoicesCount++;

					if (purchaseOrderId != null)
						purchaseOrdersCount++;
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultset);
			return null;
		}

		if (purchaseInvoicesCount > 0 || purchaseOrdersCount > 0) {

			databaseFunctions.closeDBOperations(connection, statement, resultset);
			statement.close();
			callResultModelList.add(new CallResultModel(true, false, "Vendor cannot be deleted due to " + purchaseInvoicesCount + " Purchase Invoice(s) and " + purchaseOrdersCount + " Purchase Order(s) are Issued."));
			return callResultModelList;
		}

		try {

			query = "DELETE FROM db_palm_business.vendors WHERE db_palm_business.vendors.enterprise_id = ? and db_palm_business.vendors.vendor_id =  ?";
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, enterpriseId);
		    preparedStmt.setString (2, vendorId);
				    
		    preparedStmt.executeUpdate();
			callResultModelList.add(new CallResultModel(false, true, "Deleted Successfully..."));
		
		} catch (SQLException e) {
			e.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false, "Failed to Delete Vendor."));
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
		}
		
		return callResultModelList;
	}
	
}
