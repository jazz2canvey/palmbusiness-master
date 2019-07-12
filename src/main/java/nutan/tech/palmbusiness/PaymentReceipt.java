package nutan.tech.palmbusiness;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import nutan.tech.creator.PaymentReceiptCreator;
import nutan.tech.listmodel.PaymentReceiptListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.PaymentReceiptModel;
import nutan.tech.utilities.APIUtilities;
import nutan.tech.utilities.DatabaseFunctions;

@Path("/payment_receipt")
public class PaymentReceipt {

	private DatabaseFunctions databaseFunctions;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> newItem(PaymentReceiptListModel paymentReceiptList) {

		List<CallResultModel> callResultModelList = new ArrayList<>();
		List<PaymentReceiptModel> paymentReceiptModelList = PaymentReceiptCreator.getPaymentReceipt();
		paymentReceiptModelList.addAll(paymentReceiptList.getPaymentReceiptList());
		
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		PreparedStatement preparedStmt = null;
		
		String query = "INSERT INTO db_palm_business.payments_receipts (payment_receipt_id, enterprise_id, entry_date, party_code, mover_code, vendor_customer, invoice_number, amount, payed_via_bank, payed_via_cash, bank_payment_mode, account_id, description)"
		        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			preparedStmt = connection.prepareStatement(query);
			connection.setAutoCommit(false);

			for (PaymentReceiptModel paymentReceiptModel : paymentReceiptModelList) {

				String paymentReceiptId = "PRE" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);

				preparedStmt.setString(1, paymentReceiptId);
				preparedStmt.setString(2, paymentReceiptModel.getEnterprise_id());
				preparedStmt.setString(3, paymentReceiptModel.getEntry_date());
				preparedStmt.setInt(4, paymentReceiptModel.getParty_code());
				preparedStmt.setInt(5, paymentReceiptModel.getMover_code());
				preparedStmt.setString(6, paymentReceiptModel.getVendor_customer());
				preparedStmt.setString(7, paymentReceiptModel.getInvoice_number());
				preparedStmt.setDouble(8, paymentReceiptModel.getAmount());
				preparedStmt.setBoolean(9, paymentReceiptModel.isPayed_via_bank());
				preparedStmt.setBoolean(10, paymentReceiptModel.isPayed_via_cash());
				preparedStmt.setInt(11, paymentReceiptModel.getBank_payment_mode());
				preparedStmt.setString(12, paymentReceiptModel.getAccount_id());
				preparedStmt.setString(13, paymentReceiptModel.getDescription());
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
	
	@GET
	@Path("{enterprise_id}/{party_code}")
	public List<PaymentReceiptModel> getPaymentReceipt(@PathParam("enterprise_id") String enterpriseId, @PathParam("party_code") int partyCode) {
		
		List<PaymentReceiptModel> paymentReceiptList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		Statement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT db_palm_business.payments_receipts.payment_receipt_id, db_palm_business.payments_receipts.enterprise_id, db_palm_business.payments_receipts.entry_date, db_palm_business.party.party AS party_value, db_palm_business.movers.mover AS mover_value, db_palm_business.payments_receipts.invoice_number, db_palm_business.payments_receipts.vendor_customer, (CASE WHEN db_palm_business.payments_receipts.mover_code = 1 THEN db_palm_business.vendors.enterprise_name WHEN (db_palm_business.payments_receipts.mover_code = 2 AND db_palm_business.customers.enterprise_name IS NOT NULL) THEN db_palm_business.customers.enterprise_name WHEN (db_palm_business.payments_receipts.mover_code = 2 AND db_palm_business.customers.person_name IS NOT NULL) THEN db_palm_business.customers.person_name END) AS name, (CASE WHEN (db_palm_business.payments_receipts.payed_via_bank = TRUE) THEN  db_palm_business.bank_payment_modes.payment_mode WHEN (db_palm_business.payments_receipts.payed_via_cash = TRUE) THEN 'CASH' END)AS payment_mode, db_palm_business.payments_receipts.account_id, (CASE WHEN db_palm_business.bank_accounts.bank_name IS NULL OR db_palm_business.bank_accounts.bank_name = '' THEN 'CASH' ELSE db_palm_business.bank_accounts.bank_name END) AS account_name, db_palm_business.payments_receipts.amount, db_palm_business.payments_receipts.payed_via_bank, db_palm_business.payments_receipts.payed_via_cash, db_palm_business.payments_receipts.description FROM payments_receipts LEFT JOIN vendors ON payments_receipts.vendor_customer = vendors.vendor_id LEFT JOIN customers ON payments_receipts.vendor_customer = customers.customer_id LEFT JOIN party ON payments_receipts.party_code = party.party_code LEFT JOIN movers ON payments_receipts.mover_code = movers.mover_code LEFT JOIN bank_payment_modes ON payments_receipts.bank_payment_mode = bank_payment_modes.payment_mode_code LEFT JOIN db_palm_business.bank_accounts ON db_palm_business.payments_receipts.account_id = db_palm_business.bank_accounts.bank_account_id WHERE payments_receipts.enterprise_id = '" + enterpriseId + "' AND payments_receipts.party_code = " + partyCode + "  ORDER BY db_palm_business.payments_receipts.entry_date ASC";

	    try {

		statement = connection.createStatement();
		resultSet = statement.executeQuery(query);

		while (resultSet.next()) {

	      	  String paymentReceiptId = resultSet.getString("payment_receipt_id");
			  String entryDate = resultSet.getString("entry_date");
			  String partyValue = resultSet.getString("party_value");
			  String moverValue = resultSet.getString("mover_value");
			  String invoiceNumber = resultSet.getString("invoice_number");
			  String idCustomerVendor = resultSet.getString("vendor_customer");
			  String name = resultSet.getString("name");
			  String paymentMode = resultSet.getString("payment_mode");
			  String accountId = resultSet.getString("account_id");
			  String accountName = resultSet.getString("account_name");
			  double amount = resultSet.getDouble("amount");
			  boolean paidViaBank = resultSet.getBoolean("payed_via_bank");
			  boolean paidViaCash = resultSet.getBoolean("payed_via_cash");
			  String description = resultSet.getString("description");

			  PaymentReceiptModel model = new PaymentReceiptModel(paidViaBank, paidViaCash, paymentReceiptId, enterpriseId, entryDate, idCustomerVendor, invoiceNumber, accountId, description, partyValue, moverValue, name, paymentMode, accountName, amount);
			  paymentReceiptList.add(model);
	      }
		
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		
		statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}
	
	    return paymentReceiptList;
	}
	
	@PUT
	@Path("{enterprise_id}/{payment_receipt_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> modifyPaymentReceipt(@PathParam("enterprise_id") String enterpriseId, @PathParam("payment_receipt_id") String paymentReceiptId, PaymentReceiptModel paymentReceiptModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();

		String query = "UPDATE db_palm_business.payments_receipts SET db_palm_business.payments_receipts.entry_date = ?, db_palm_business.payments_receipts.party_code = ?, db_palm_business.payments_receipts.mover_code = ?, db_palm_business.payments_receipts.vendor_customer = ?, db_palm_business.payments_receipts.invoice_number = ?, db_palm_business.payments_receipts.amount = ?, db_palm_business.payments_receipts.payed_via_bank = ?, db_palm_business.payments_receipts.payed_via_cash = ?, db_palm_business.payments_receipts.bank_payment_mode = ?, db_palm_business.payments_receipts.account_id = ?, db_palm_business.payments_receipts.description = ? WHERE db_palm_business.payments_receipts.enterprise_id = '" + enterpriseId + "' AND db_palm_business.payments_receipts.payment_receipt_id = '" + paymentReceiptId + "'";

		PreparedStatement preparedStmt = null;

		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, paymentReceiptModel.getEntry_date());
			preparedStmt.setInt (2, paymentReceiptModel.getParty_code());
			preparedStmt.setInt (3, paymentReceiptModel.getMover_code());
		    preparedStmt.setString (4, paymentReceiptModel.getVendor_customer());
		    preparedStmt.setString (5, paymentReceiptModel.getInvoice_number());
		    preparedStmt.setDouble (6, paymentReceiptModel.getAmount());
		    preparedStmt.setBoolean (7, paymentReceiptModel.isPayed_via_bank());
		    preparedStmt.setBoolean (8, paymentReceiptModel.isPayed_via_cash());
		    preparedStmt.setInt (9, paymentReceiptModel.getBank_payment_mode());
			preparedStmt.setString (10, paymentReceiptModel.getAccount_id());
		    preparedStmt.setString (11, paymentReceiptModel.getDescription());

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
	
	@DELETE
	@Path("{enterprise_id}/{payment_receipt_id}")
	public List<CallResultModel> deleteItem(@PathParam("enterprise_id") String enterpriseId, @PathParam("payment_receipt_id") String paymentReceiptId) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "DELETE FROM db_palm_business.payments_receipts WHERE enterprise_id =  ? AND payment_receipt_id = ?";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);

			preparedStmt.setString (1, enterpriseId);
			preparedStmt.setString (2, paymentReceiptId);

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
