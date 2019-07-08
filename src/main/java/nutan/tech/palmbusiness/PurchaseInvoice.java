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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import nutan.tech.creator.PurchaseInvoiceCreator;
import nutan.tech.listmodel.PurchaseInvoicesListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.PurchaseInvoiceModel;
import nutan.tech.utilities.APIUtilities;
import nutan.tech.utilities.DatabaseFunctions;

@Path("/purchase_invoice")
@Produces(MediaType.APPLICATION_JSON)
public class PurchaseInvoice {

	DatabaseFunctions databaseFunctions;
	
	@POST
	@Path("{enterprise_id}/{vendor_id}/{invoice_number}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> newPurchaseInvoice(@PathParam("enterprise_id") final String enterpriseId, @PathParam("vendor_id") final String vendorId, @PathParam("invoice_number") final String invoiceNumber, final PurchaseInvoicesListModel purchaseInvoicesList) {
		
		final List<CallResultModel> callResultModelList = new ArrayList<>();
		
		Thread insert2Database = new Thread(
				new Runnable() {
					
					@Override
					public void run() {

						List<PurchaseInvoiceModel> purchaseInvoicesModelList = PurchaseInvoiceCreator.getPurchaseInvoices();
						purchaseInvoicesModelList.addAll(purchaseInvoicesList.getPurchaseInvoicesList());
						
						databaseFunctions = new DatabaseFunctions();
						Connection connection = databaseFunctions.connect2DB();
						PreparedStatement preparedStmt = null;
						
						String query = " INSERT INTO db_palm_business.purchase_invoice (purchase_invoice_id, enterprise_id, vendor_id, invoice_number, reference, status, is_rate_including_gst, entry_date, due_date, discount_type_code, taken_discount, total_amount, reverse_charge, description)"
						        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

						try {
							preparedStmt = connection.prepareStatement(query);
							connection.setAutoCommit(false);

							for (PurchaseInvoiceModel purchaseInvoiceModel : purchaseInvoicesModelList) {

								String purchaseInvoiceId = "PI" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);
								preparedStmt.setString(1, purchaseInvoiceId);
								preparedStmt.setString(2, purchaseInvoiceModel.getEnterprise_id());
								preparedStmt.setString(3, purchaseInvoiceModel.getVendor_id());
								preparedStmt.setString(4, purchaseInvoiceModel.getInvoice_number());
								preparedStmt.setString(5, purchaseInvoiceModel.getReference());
								preparedStmt.setBoolean(6, purchaseInvoiceModel.isStatus());
								preparedStmt.setBoolean(7, purchaseInvoiceModel.isIs_rate_including_gst());
								preparedStmt.setString(8, purchaseInvoiceModel.getEntry_date());
								preparedStmt.setString(9, purchaseInvoiceModel.getDue_date());
								preparedStmt.setInt(10, purchaseInvoiceModel.getDiscount_type_code());
								preparedStmt.setDouble(11, purchaseInvoiceModel.getTaken_discount());
								preparedStmt.setDouble(12, purchaseInvoiceModel.getTotal_amount());
								preparedStmt.setBoolean(13, purchaseInvoiceModel.isReverse_charge());
								preparedStmt.setString(14, purchaseInvoiceModel.getDescription());
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
			    String getQuery = "SELECT * FROM db_palm_business.purchase_invoice WHERE db_palm_business.purchase_invoice.enterprise_id = '" + enterpriseId + "' AND db_palm_business.purchase_invoice.vendor_id = '" + vendorId + "' AND db_palm_business.purchase_invoice.invoice_number = '" + invoiceNumber + "'";
			    
				try {
					
					Statement st = connection.createStatement();
				    ResultSet resultSet = st.executeQuery(getQuery);

				    if (resultSet.isBeforeFirst()) {

						callResultModelList.add(new CallResultModel(true, false, "This Vendor, Invoice Number Already Exist"));
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
	
	@PUT
	@Path("{purchase_invoice_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> modifyPurchaseInvoice(@PathParam("purchase_invoice_id") String purchaseInvoiceId, PurchaseInvoiceModel purchaseInvoiceModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE db_palm_business.purchase_invoice SET invoice_number = ?, reference = ?, status = ?, entry_date = ?, due_date = ?, discount_type_code = ?, taken_discount = ?, total_amount = ?, reverse_charge = ?, description = ? WHERE db_palm_business.purchase_invoice.purchase_invoice_id = '" + purchaseInvoiceId + "'";
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);

			preparedStmt.setString (1, purchaseInvoiceModel.getInvoice_number());
			preparedStmt.setString (2, purchaseInvoiceModel.getReference());
			preparedStmt.setBoolean (3, purchaseInvoiceModel.isStatus());
		    preparedStmt.setString (4, purchaseInvoiceModel.getEntry_date());
		    preparedStmt.setString (5, purchaseInvoiceModel.getDue_date());
			preparedStmt.setInt (6, purchaseInvoiceModel.getDiscount_type_code());
			preparedStmt.setDouble (7, purchaseInvoiceModel.getTaken_discount());
			preparedStmt.setDouble (8, purchaseInvoiceModel.getTotal_amount());
		    preparedStmt.setBoolean (9, purchaseInvoiceModel.isReverse_charge());
		    preparedStmt.setString (10, purchaseInvoiceModel.getDescription());
				    
		    // execute the preparedstatement
		    preparedStmt.executeUpdate();
		    
			callResultModelList.add(new CallResultModel(false, true));
		
		} catch (SQLException e) {
			e.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false));
		}

		databaseFunctions.closeDBOperations(connection, preparedStmt, null);
		return callResultModelList;
	}	
	
	@GET
	@Path("{enterprise_id}")
	public List<PurchaseInvoiceModel> getAllPurchaseInvoices(@PathParam("enterprise_id") String enterpriseId) {

		List<PurchaseInvoiceModel> purchaseInvoiceList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		Set<String> keys = new HashSet<>();
		MultivaluedHashMap<String, PurchaseInvoiceModel> map = new MultivaluedHashMap<>();
		Statement statement = null;
		ResultSet resultSet = null;

		String query = "SELECT db_palm_business.purchase_invoice.purchase_invoice_id, db_palm_business.purchase_invoice.enterprise_id, db_palm_business.purchase_invoice.vendor_id, db_palm_business.vendors.enterprise_name AS vendor_name,  db_palm_business.purchase_invoice.invoice_number, db_palm_business.purchase_invoice.reference, db_palm_business.purchase_invoice.status, db_palm_business.purchase_invoice.is_rate_including_gst, db_palm_business.purchase_invoice.entry_date, db_palm_business.purchase_invoice.due_date, db_palm_business.purchase_invoice.discount_type_code, db_palm_business.purchase_invoice.taken_discount, db_palm_business.purchase_invoice.total_amount, db_palm_business.purchase_invoice.reverse_charge, db_palm_business.purchase_invoice.description FROM db_palm_business.purchase_invoice LEFT JOIN db_palm_business.vendors ON db_palm_business.purchase_invoice.vendor_id = db_palm_business.vendors.vendor_id WHERE db_palm_business.purchase_invoice.enterprise_id = '" + enterpriseId + "' ORDER BY db_palm_business.purchase_invoice.entry_date";

	    try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			if (resultSet.isBeforeFirst()) {

				while (resultSet.next()) {

					String purchaseInvoiceId = resultSet.getString("purchase_invoice_id");
					String vendorId = resultSet.getString("vendor_id");
					String vendorName = resultSet.getString("vendor_name");
					String invoiceNumber = resultSet.getString("invoice_number");
					String reference = resultSet.getString("reference");
					boolean status = Boolean.parseBoolean(resultSet.getString("status"));
					boolean isRateIncludingGST = resultSet.getBoolean("is_rate_including_gst");
					String entryDate = resultSet.getString("entry_date");
					String dueDate = resultSet.getString("due_date");
					int discountTypeCode = Integer.parseInt(resultSet.getString("discount_type_code"));
					double takenDiscount = Double.parseDouble(resultSet.getString("taken_discount"));
					double totalAmount = Double.parseDouble(resultSet.getString("total_amount"));
					boolean reverseCharge = Boolean.parseBoolean(resultSet.getString("reverse_charge"));
					String description = resultSet.getString("description");

					PurchaseInvoiceModel model = new PurchaseInvoiceModel(purchaseInvoiceId, vendorId, vendorName, invoiceNumber, reference, status, isRateIncludingGST, entryDate, dueDate, discountTypeCode, takenDiscount, totalAmount, reverseCharge, description);
					purchaseInvoiceList.add(model);
				}

			} else {

				return purchaseInvoiceList;
			}

		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		
		statement.close();

	} catch (SQLException e) {
		e.printStackTrace();
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		return null;
	}
		return purchaseInvoiceList;
	}

	@DELETE
	@Path("{purchase_invoice_id}/{invoice_number}")
	public List<CallResultModel> deleteItem(@PathParam("purchase_invoice_id") String purchaseInvoiceId, @PathParam("invoice_number") String invoiceNumber) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();

        String purchasedItemsQuery = "SELECT db_palm_business.purchased_items.item_id, SUM(db_palm_business.purchased_items.quantity) AS total_purchased_qty, db_palm_business.purchased_items.quantity FROM db_palm_business.purchased_items WHERE db_palm_business.purchased_items.invoice_no = '" + invoiceNumber + "' GROUP BY item_id";

		String query = "DELETE FROM db_palm_business.purchase_invoice WHERE db_palm_business.purchase_invoice.purchase_invoice_id = ?";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, purchaseInvoiceId);

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

//  SELECT purchased_items.item_id, (SELECT quantity FROM purchased_items WHERE invoice_no = 'INV-00002') AS invoice_qty, (SELECT SUM(quantity) FROM purchased_items WHERE invoice_no '

//    SELECT item_id, (SELECT SUM(quantity) FROM purchased_items WHERE purchased_items.item_id = item_id) AS total_purchased_qty, (SELECT quantity FROM purchased_items WHERE purchased_items.invoice_no = 'INV-00001') AS invoice_item_qty, (SELECT SUM(sold_items.quantity) FROM sold_items WHERE sold_items.item_id = purchased_items.item_id) AS total_sold_qty FROM db_palm_business.purchased_items GROUP BY item_id

//  SELECT (SELECT item_id FROM purchased_items WHERE invoice_no = 'INV-00001') AS invoice_item_id, (SELECT SUM(quantity) FROM purchased_items WHERE purchased_items.item_id = invoice_item_id) AS total_purchased_qty, (SELECT quantity FROM purchased_items WHERE purchased_items.invoice_no = 'INV-00001') AS invoice_item_qty, (SELECT SUM(sold_items.quantity) FROM sold_items WHERE sold_items.item_id = purchased_items.item_id) AS total_sold_qty FROM db_palm_business.purchased_items GROUP BY invoice_item_id
}
