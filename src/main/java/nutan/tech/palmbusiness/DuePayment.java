package nutan.tech.palmbusiness;

import nutan.tech.models.DueModel;
import nutan.tech.utilities.DatabaseFunctions;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Path("/due_payment")
@Produces(MediaType.APPLICATION_JSON)
public class DuePayment {

    private DatabaseFunctions databaseFunctions;

    @GET
    @Path("/payment/{enterprise_id}")
    public List<DueModel> getAllPaymentDues(@PathParam("enterprise_id") String enterpriseId) {

        List<DueModel> duePaymentList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

//        SELECT db_palm_business.purchase_invoice.purchase_invoice_id AS invoice_id, db_palm_business.purchase_invoice.entry_date, db_palm_business.purchase_invoice.due_date, db_palm_business.purchase_invoice.invoice_number, db_palm_business.purchase_invoice.total_amount, COALESCE(SUM(db_palm_business.payments_receipts.amount), 0) AS payed_amount, (db_palm_business.purchase_invoice.total_amount - COALESCE(SUM(db_palm_business.payments_receipts.amount), 0)) AS current_balance FROM db_palm_business.purchase_invoice LEFT JOIN db_palm_business.payments_receipts ON db_palm_business.purchase_invoice.enterprise_id = db_palm_business.payments_receipts.enterprise_id AND db_palm_business.purchase_invoice.vendor_id = db_palm_business.payments_receipts.vendor_customer AND db_palm_business.purchase_invoice.invoice_number = db_palm_business.payments_receipts.invoice_number WHERE db_palm_business.purchase_invoice.vendor_id = 'VEN1127235446GT' GROUP BY db_palm_business.payments_receipts.invoice_number ORDER BY db_palm_business.purchase_invoice.entry_date

        String query = "SELECT db_palm_business.purchase_invoice.purchase_invoice_id AS invoice_id, db_palm_business.purchase_invoice.vendor_id AS id, db_palm_business.vendors.enterprise_name AS name, db_palm_business.purchase_invoice.entry_date, db_palm_business.purchase_invoice.invoice_number, db_palm_business.purchase_invoice.total_amount, COALESCE(SUM(db_palm_business.payments_receipts.amount), 0) AS payed_amount, (db_palm_business.purchase_invoice.total_amount - COALESCE(SUM(db_palm_business.payments_receipts.amount), 0)) AS remaining_balance FROM db_palm_business.purchase_invoice LEFT JOIN db_palm_business.vendors ON db_palm_business.purchase_invoice.vendor_id = db_palm_business.vendors.vendor_id LEFT JOIN db_palm_business.payments_receipts ON db_palm_business.purchase_invoice.invoice_number = db_palm_business.payments_receipts.invoice_number AND db_palm_business.purchase_invoice.vendor_id = db_palm_business.payments_receipts.vendor_customer WHERE db_palm_business.purchase_invoice.enterprise_id = '" + enterpriseId + "' GROUP BY db_palm_business.purchase_invoice.invoice_number";

        System.out.println("Query: " + query);
        
        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {

                String purchaseInvoiceId = resultSet.getString("invoice_id");
                String vendorId = resultSet.getString("id");
                String enterpriseName = resultSet.getString("name");
                String entryDate = resultSet.getString("entry_date");
                String invoiceNumber = resultSet.getString("invoice_number");
                double totalAmount = resultSet.getDouble("total_amount");
                double payedAmount = resultSet.getDouble("payed_amount");
                double remainingBalance = resultSet.getDouble("remaining_balance");

                duePaymentList.add(new DueModel(purchaseInvoiceId, vendorId, enterpriseName, entryDate, invoiceNumber, totalAmount, payedAmount, remainingBalance));
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return duePaymentList;
    }

    @GET
    @Path("/receipt/{enterprise_id}")
    public List<DueModel> getAllReceiptDues(@PathParam("enterprise_id") String enterpriseId) {

        List<DueModel> duePaymentList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT db_palm_business.sales_invoices.sales_invoice_id AS invoice_id, db_palm_business.sales_invoices.customer_id AS  id , (CASE WHEN db_palm_business.customers.enterprise_name != '' THEN db_palm_business.customers.enterprise_name ELSE db_palm_business.customers.person_name END) AS name, db_palm_business.sales_invoices.entry_date, db_palm_business.sales_invoices.invoice_number, db_palm_business.sales_invoices.total_amount, COALESCE(SUM(db_palm_business.payments_receipts.amount), 0) AS payed_amount, (db_palm_business.sales_invoices.total_amount - COALESCE(SUM(db_palm_business.payments_receipts.amount), 0)) AS remaining_balance FROM db_palm_business.sales_invoices LEFT JOIN db_palm_business.customers ON db_palm_business.sales_invoices.customer_id = db_palm_business.customers.customer_id LEFT JOIN db_palm_business.payments_receipts ON db_palm_business.sales_invoices.invoice_number = db_palm_business.payments_receipts.invoice_number AND db_palm_business.sales_invoices.customer_id = db_palm_business.payments_receipts.vendor_customer WHERE db_palm_business.sales_invoices.enterprise_id = '" + enterpriseId + "' GROUP BY db_palm_business.sales_invoices.invoice_number";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {

                String invoiceId = resultSet.getString("invoice_id");
                String customerId = resultSet.getString("id");
                String customerName = resultSet.getString("name");
                String entryDate = resultSet.getString("entry_date");
                String invoiceNumber = resultSet.getString("invoice_number");
                double totalAmount = resultSet.getDouble("total_amount");
                double payedAmount = resultSet.getDouble("payed_amount");
                double remainingBalance = resultSet.getDouble("remaining_balance");

                duePaymentList.add(new DueModel(invoiceId, customerId, customerName, entryDate, invoiceNumber, totalAmount, payedAmount, remainingBalance));
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return duePaymentList;
    }

}
