package nutan.tech.palmbusiness;

import nutan.tech.models.PaymentsMadeModel;
import nutan.tech.models.PaymentsReceivedModel;
import nutan.tech.utilities.DatabaseFunctions;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Path("/payments_made_received")
public class ReportsPaymentsMadeReceived {

    DatabaseFunctions databaseFunctions;

    @GET
    @Path("/payments_made/{enterprise_id}/{start_range}/{end_range}")
    public List<PaymentsMadeModel> getPaymentsMade(@PathParam("enterprise_id") String enterpriseId, @PathParam("start_range") String startRange, @PathParam("end_range") String endRange) {

        List<PaymentsMadeModel> paymentsMadeList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT db_palm_business.payments_receipts.entry_date, db_palm_business.payments_receipts.vendor_customer AS vendor_id, db_palm_business.vendors.enterprise_name AS vendor_name, db_palm_business.payments_receipts.invoice_number, db_palm_business.payments_receipts.amount, (CASE WHEN (db_palm_business.payments_receipts.payed_via_bank = TRUE) THEN db_palm_business.bank_payment_modes.payment_mode WHEN (db_palm_business.payments_receipts.payed_via_cash = TRUE) THEN 'CASH' END)AS payment_mode, (CASE WHEN db_palm_business.bank_accounts.bank_name IS NULL OR db_palm_business.bank_accounts.bank_name = '' THEN 'CASH' ELSE db_palm_business.bank_accounts.bank_name END) AS account_name FROM db_palm_business.payments_receipts LEFT JOIN db_palm_business.vendors ON db_palm_business.payments_receipts.vendor_customer = db_palm_business.vendors.vendor_id LEFT JOIN db_palm_business.bank_payment_modes ON db_palm_business.payments_receipts.bank_payment_mode = db_palm_business.bank_payment_modes.payment_mode_code LEFT JOIN db_palm_business.bank_accounts ON db_palm_business.payments_receipts.account_id = db_palm_business.bank_accounts.bank_account_id WHERE db_palm_business.payments_receipts.enterprise_id = '" + enterpriseId + "' AND db_palm_business.payments_receipts.party_code = 1 AND db_palm_business.payments_receipts.entry_date BETWEEN '" + startRange + "' AND '" + endRange + "' ORDER BY db_palm_business.payments_receipts.entry_date";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())	{

                String entryDate = resultSet.getString("entry_date");
                String vendorId = resultSet.getString("vendor_id");
                String vendorName = resultSet.getString("vendor_name");
                String invoiceNumber = resultSet.getString("invoice_number");
                double amount = resultSet.getDouble("amount");
                String paymentMode = resultSet.getString("payment_mode");
                String accountName = resultSet.getString("account_name");

                PaymentsMadeModel model = new PaymentsMadeModel(entryDate, vendorId, vendorName, invoiceNumber, paymentMode, accountName, amount);

                paymentsMadeList.add(model);
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);

        } catch (SQLException e) {

            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return paymentsMadeList;
    }

    @GET
    @Path("/payments_received/{enterprise_id}/{start_range}/{end_range}")
    public List<PaymentsReceivedModel> getPaymentsReceived(@PathParam("enterprise_id") String enterpriseId, @PathParam("start_range") String startRange, @PathParam("end_range") String endRange) {

        List<PaymentsReceivedModel> paymentsReceivedList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT db_palm_business.payments_receipts.entry_date, db_palm_business.payments_receipts.vendor_customer AS customer_id, (CASE WHEN db_palm_business.customers.enterprise_name != '' THEN db_palm_business.customers.enterprise_name ELSE db_palm_business.customers.person_name END) AS customer_name, db_palm_business.payments_receipts.invoice_number, db_palm_business.payments_receipts.amount, (CASE WHEN (db_palm_business.payments_receipts.payed_via_bank = TRUE) THEN db_palm_business.bank_payment_modes.payment_mode WHEN (db_palm_business.payments_receipts.payed_via_cash = TRUE) THEN 'CASH' END)AS payment_mode, (CASE WHEN db_palm_business.bank_accounts.bank_name IS NULL OR db_palm_business.bank_accounts.bank_name = '' THEN 'CASH' ELSE db_palm_business.bank_accounts.bank_name END) AS account_name FROM db_palm_business.payments_receipts LEFT JOIN db_palm_business.customers ON db_palm_business.payments_receipts.vendor_customer = db_palm_business.customers.customer_id LEFT JOIN db_palm_business.bank_payment_modes ON db_palm_business.payments_receipts.bank_payment_mode = db_palm_business.bank_payment_modes.payment_mode_code LEFT JOIN db_palm_business.bank_accounts ON db_palm_business.payments_receipts.account_id = db_palm_business.bank_accounts.bank_account_id WHERE db_palm_business.payments_receipts.enterprise_id = '" + enterpriseId + "' AND db_palm_business.payments_receipts.party_code = 2 AND db_palm_business.payments_receipts.entry_date BETWEEN '" + startRange + "' AND '" + endRange + "' ORDER BY db_palm_business.payments_receipts.entry_date";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())	{

                String entryDate = resultSet.getString("entry_date");
                String customerId = resultSet.getString("customer_id");
                String customerName = resultSet.getString("customer_name");
                String invoiceNumber = resultSet.getString("invoice_number");
                double amount = resultSet.getDouble("amount");
                String paymentMode = resultSet.getString("payment_mode");
                String accountName = resultSet.getString("account_name");

                PaymentsReceivedModel model = new PaymentsReceivedModel(entryDate, customerId, customerName, invoiceNumber, paymentMode, accountName, amount);

                paymentsReceivedList.add(model);
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);

        } catch (SQLException e) {

            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return paymentsReceivedList;
    }

}
