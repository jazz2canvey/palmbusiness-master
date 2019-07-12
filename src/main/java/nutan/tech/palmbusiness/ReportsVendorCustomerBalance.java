package nutan.tech.palmbusiness;

import nutan.tech.models.ReportCustomerBalanceModel;
import nutan.tech.models.ReportVendorBalanceModel;
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

@Path("/vendor_customer_balance_report")
public class ReportsVendorCustomerBalance {

    private DatabaseFunctions databaseFunctions;

    @GET
    @Path("/customer_balance/{enterprise_id}")
    public List<ReportCustomerBalanceModel> getReportCustomerBalance(@PathParam("enterprise_id") String enterpriseId) {

        List<ReportCustomerBalanceModel> reportCustomerBalanceList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT db_palm_business.customers.customer_id, (CASE WHEN db_palm_business.customers.enterprise_name != '' THEN db_palm_business.customers.enterprise_name ELSE db_palm_business.customers.person_name END) AS customer_name, SUM(DISTINCT db_palm_business.sales_invoices.total_amount) AS receivable_amount, SUM(DISTINCT db_palm_business.payments_receipts.amount) AS received_amount FROM db_palm_business.customers LEFT JOIN db_palm_business.sales_invoices ON db_palm_business.customers.customer_id = db_palm_business.sales_invoices.customer_id LEFT JOIN db_palm_business.payments_receipts ON db_palm_business.customers.customer_id = db_palm_business.payments_receipts.vendor_customer WHERE db_palm_business.customers.enterprise_id = '" + enterpriseId + "' GROUP BY db_palm_business.customers.customer_id ORDER BY db_palm_business.customers.last_modified";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())	{

                String customerId = resultSet.getString("customer_id");
                String customerName = resultSet.getString("customer_name");
                double receivableAmount = resultSet.getDouble("receivable_amount");
                double receivedAmount = resultSet.getDouble("received_amount");

                reportCustomerBalanceList.add(new ReportCustomerBalanceModel(customerId, customerName, receivableAmount, receivedAmount));
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);

        } catch (SQLException e) {

            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return reportCustomerBalanceList;
    }

    @GET
    @Path("/vendor_balance/{enterprise_id}")
    public List<ReportVendorBalanceModel> getReportVendorBalance(@PathParam("enterprise_id") String enterpriseId) {

        List<ReportVendorBalanceModel> reportVendorBalanceList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT db_palm_business.vendors.vendor_id, db_palm_business.vendors.enterprise_name AS vendor_name, SUM(DISTINCT db_palm_business.purchase_invoice.total_amount)as payable_amount, SUM(DISTINCT db_palm_business.payments_receipts.amount) AS paid_amount FROM db_palm_business.vendors LEFT JOIN db_palm_business.purchase_invoice ON db_palm_business.vendors.vendor_id = db_palm_business.purchase_invoice.vendor_id LEFT JOIN db_palm_business.payments_receipts ON db_palm_business.vendors.vendor_id = db_palm_business.payments_receipts.vendor_customer WHERE db_palm_business.vendors.enterprise_id = '" + enterpriseId + "' GROUP BY db_palm_business.vendors.vendor_id ORDER BY db_palm_business.vendors.last_modified";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())	{

                String vendorId = resultSet.getString("vendor_id");
                String vendorName = resultSet.getString("vendor_name");
                double payableAmount = resultSet.getDouble("payable_amount");
                double paidAmount = resultSet.getDouble("paid_amount");

                ReportVendorBalanceModel model = new ReportVendorBalanceModel(vendorId, vendorName, payableAmount, paidAmount);

                reportVendorBalanceList.add(model);
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);

        } catch (SQLException e) {

            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return reportVendorBalanceList;
    }

}
