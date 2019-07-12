package nutan.tech.palmbusiness;

import nutan.tech.models.ReportSalesByCustomerModel;
import nutan.tech.models.ReportSalesByItemModel;
import nutan.tech.models.SalesInvoiceModel;
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

@Path("/sales_report")
public class ReportsOnSales {

    private DatabaseFunctions databaseFunctions;

    @GET
    @Path("/sales_by_customer/{enterprise_id}/{start_range}/{end_range}")
    public List<ReportSalesByCustomerModel> getReportSalesByCustomer(@PathParam("enterprise_id") String enterpriseId, @PathParam("start_range") String startRange, @PathParam("end_range") String endRange) {

        List<ReportSalesByCustomerModel> reportSalesByCustomerList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT db_palm_business.sales_invoices.customer_id, (CASE WHEN db_palm_business.customers.enterprise_name != '' THEN db_palm_business.customers.enterprise_name ELSE db_palm_business.customers.person_name END) AS customer_name, COUNT(db_palm_business.sales_invoices.invoice_number) AS invoice_count, SUM((CASE WHEN db_palm_business.sales_invoices.discount_type_code = 1 THEN (db_palm_business.sales_invoices.total_amount - db_palm_business.sales_invoices.given_discount) WHEN sales_invoices.discount_type_code = 2 THEN (db_palm_business.sales_invoices.total_amount - ((db_palm_business.sales_invoices.given_discount / 100) * db_palm_business.sales_invoices.total_amount)) ELSE total_amount END)) AS total_sales_amount FROM db_palm_business.sales_invoices LEFT JOIN db_palm_business.customers ON db_palm_business.sales_invoices.customer_id = db_palm_business.customers.customer_id WHERE db_palm_business.sales_invoices.enterprise_id = '" + enterpriseId + "' AND db_palm_business.sales_invoices.entry_date BETWEEN '" + startRange + "' AND '" + endRange + "' GROUP BY db_palm_business.sales_invoices.customer_id";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())	{

                String customerId = resultSet.getString("customer_id");
                String customerName = resultSet.getString("customer_name");
                double invoiceCount = resultSet.getDouble("invoice_count");
                double totalSalesAmount = resultSet.getDouble("total_sales_amount");

                reportSalesByCustomerList.add(new ReportSalesByCustomerModel(customerId, customerName, invoiceCount, totalSalesAmount));
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);

        } catch (SQLException e) {

            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return reportSalesByCustomerList;
    }

    @GET
    @Path("/sales_by_item/{enterprise_id}/{start_range}/{end_range}")
    public List<ReportSalesByItemModel> getReportSalesByItem(@PathParam("enterprise_id") String enterpriseId, @PathParam("start_range") String startRange, @PathParam("end_range") String endRange) {

        List<ReportSalesByItemModel> reportSalesByItemList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT db_palm_business.sold_items.item_id, db_palm_business.items.item_name, SUM(db_palm_business.sold_items.quantity) AS total_quantity, db_palm_business.sold_items.measurement, SUM(db_palm_business.sold_items.total_amount) AS total_amount FROM db_palm_business.sold_items LEFT JOIN db_palm_business.items ON db_palm_business.sold_items.item_id = db_palm_business.items.item_id LEFT JOIN sales_invoices ON sold_items.invoice_number = sales_invoices.invoice_number WHERE db_palm_business.sold_items.enterprise_id = '" + enterpriseId + "' AND db_palm_business.sales_invoices.entry_date BETWEEN '" + startRange + "' AND '" + endRange + "' GROUP BY db_palm_business.sold_items.item_id";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())	{

                String itemId = resultSet.getString("item_id");
                String itemName = resultSet.getString("item_name");
                String measurement = resultSet.getString("measurement");
                double totalQuantity = resultSet.getDouble("total_quantity");
                double totalAmount = resultSet.getDouble("total_amount");

                reportSalesByItemList.add(new ReportSalesByItemModel(itemId, itemName, measurement, totalQuantity, totalAmount));
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);

        } catch (SQLException e) {

            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return reportSalesByItemList;
    }

    @GET
    @Path("/sales_to_a_customer/{enterprise_id}/{customer_id}")
    public List<SalesInvoiceModel> getReportSalesByCustomer(@PathParam("enterprise_id") String enterpriseId, @PathParam("customer_id") String customerId) {

        List<SalesInvoiceModel> salesInvoiceList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = databaseFunctions.connect2DB();

        String query = "SELECT db_palm_business.sales_invoices.sales_invoice_id, db_palm_business.customers.customer_id, (CASE WHEN db_palm_business.customers.enterprise_name != '' THEN db_palm_business.customers.enterprise_name ELSE db_palm_business.customers.person_name END) AS customer_name, db_palm_business.sales_invoices.invoice_number, db_palm_business.sales_invoices.reference, db_palm_business.sales_invoices.status, db_palm_business.sales_invoices.is_rate_including_gst, db_palm_business.sales_invoices.entry_date, db_palm_business.sales_invoices.due_date, db_palm_business.discount_types.discount_type, db_palm_business.sales_invoices.given_discount, db_palm_business.sales_invoices.total_amount, db_palm_business.sales_invoices.msg_to_customer FROM db_palm_business.sales_invoices LEFT JOIN db_palm_business.customers ON db_palm_business.sales_invoices.customer_id = db_palm_business.customers.customer_id LEFT JOIN db_palm_business.discount_types ON db_palm_business.sales_invoices.discount_type_code = db_palm_business.discount_types.discount_type_code WHERE db_palm_business.sales_invoices.enterprise_id = '" + enterpriseId + "' AND db_palm_business.sales_invoices.customer_id = '" + customerId + "' ORDER BY db_palm_business.sales_invoices.entry_date";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())	{

                String saleInvoiceId = resultSet.getString("sales_invoice_id");
                String customerName = resultSet.getString("customer_name");
                String invoiceNumber = resultSet.getString("invoice_number");
                String reference = resultSet.getString("reference");
                boolean status = resultSet.getBoolean("status");
                boolean isRateIncludingGst = resultSet.getBoolean("is_rate_including_gst");
                String entryDate = resultSet.getString("entry_date");
                String dueDate = resultSet.getString("due_date");
                String discountType = resultSet.getString("discount_type");
                double givenDiscount = resultSet.getDouble("given_discount");
                double totalAmount = resultSet.getDouble("total_amount");
                String msgToCustomer = resultSet.getString("msg_to_customer");

                SalesInvoiceModel model = new SalesInvoiceModel(givenDiscount, totalAmount, status, isRateIncludingGst, discountType, saleInvoiceId, enterpriseId, customerId, customerName, invoiceNumber, reference, entryDate, dueDate, msgToCustomer);

                salesInvoiceList.add(model);
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);

        } catch (SQLException e) {

            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return salesInvoiceList;
    }

}
