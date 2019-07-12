package nutan.tech.palmbusiness;

import nutan.tech.creator.EstimateInvoiceCreator;
import nutan.tech.listmodel.EstimateInvoiceListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.EstimateInvoiceModel;
import nutan.tech.utilities.APIUtilities;
import nutan.tech.utilities.DatabaseFunctions;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Path("/estimate_invoice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstimateInvoice {

    private DatabaseFunctions databaseFunctions;

    @POST
    @Path("{enterprise_id}/{invoice_number}")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<CallResultModel> newEstimateInvoice(@PathParam("enterprise_id") final String enterpriseId, @PathParam("invoice_number") final String invoiceNumber, final EstimateInvoiceListModel estimateInvoiceList) {

        final List<CallResultModel> callResultModelList = new ArrayList<>();

        Thread insert2Database = new Thread(new Runnable() {

            @Override
            public void run() {

                List<EstimateInvoiceModel> estimateInvoiceModelList = EstimateInvoiceCreator.getEstimateInvoice();
                estimateInvoiceModelList.addAll(estimateInvoiceList.getEstimateInvoiceList());

                databaseFunctions = new DatabaseFunctions();
                Connection connection = databaseFunctions.connect2DB();
                PreparedStatement preparedStmt = null;

                String query = "INSERT INTO db_palm_business.estimate_invoice (estimate_invoice_id, enterprise_id, customer_id, invoice_number, status, entry_date, valid_till, from_address, to_address, state_province, country, total_amount, note)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try {
                    preparedStmt = connection.prepareStatement(query);
                    connection.setAutoCommit(false);

                    String estimateInvoiceId = "EST" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);

                    for (EstimateInvoiceModel estimateInvoiceModel : estimateInvoiceModelList) {

                        preparedStmt.setString(1, estimateInvoiceId);
                        preparedStmt.setString(2, estimateInvoiceModel.getEnterprise_id());
                        preparedStmt.setString(3, estimateInvoiceModel.getCustomer_id());
                        preparedStmt.setString(4, estimateInvoiceModel.getInvoice_number());
                        preparedStmt.setBoolean(5, estimateInvoiceModel.isStatus());
                        preparedStmt.setString(6, estimateInvoiceModel.getEntry_date());
                        preparedStmt.setString(7, estimateInvoiceModel.getValid_till());
                        preparedStmt.setString(8, estimateInvoiceModel.getFrom_address());
                        preparedStmt.setString(9, estimateInvoiceModel.getTo_address());
                        preparedStmt.setString(10, estimateInvoiceModel.getState_province());
                        preparedStmt.setString(11, estimateInvoiceModel.getCountry());
                        preparedStmt.setDouble(12, estimateInvoiceModel.getTotal_amount());
                        preparedStmt.setString(13, estimateInvoiceModel.getNote());
                        preparedStmt.addBatch();
                    }

                    preparedStmt.executeBatch();
                    connection.commit();
                    callResultModelList.add(new CallResultModel(false, true, "Executed Successfully."));
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

                String getQuery = "SELECT * FROM db_palm_business.estimate_invoice WHERE db_palm_business.estimate_invoice.enterprise_id = '" + enterpriseId + "' AND db_palm_business.estimate_invoice.invoice_number = '" + invoiceNumber + "'";

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
    public List<EstimateInvoiceModel> getAllEstimates(@PathParam("enterprise_id") String enterpriseId) {

        List<EstimateInvoiceModel> estimateInvoiceList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = databaseFunctions.connect2DB();

        String query = "SELECT db_palm_business.estimate_invoice.estimate_invoice_id, db_palm_business.estimate_invoice.enterprise_id, db_palm_business.estimate_invoice.customer_id, (CASE WHEN db_palm_business.customers.enterprise_name != '' THEN db_palm_business.customers.enterprise_name ELSE db_palm_business.customers.person_name END) AS customer_name, db_palm_business.estimate_invoice.invoice_number, db_palm_business.estimate_invoice.status, db_palm_business.estimate_invoice.entry_date, db_palm_business.estimate_invoice.valid_till, db_palm_business.estimate_invoice.from_address, db_palm_business.estimate_invoice.to_address, db_palm_business.estimate_invoice.state_province, db_palm_business.estimate_invoice.country, db_palm_business.estimate_invoice.total_amount, db_palm_business.estimate_invoice.note FROM estimate_invoice LEFT JOIN customers ON estimate_invoice.customer_id = customers.customer_id WHERE estimate_invoice.enterprise_id = '" + enterpriseId + "' ORDER BY db_palm_business.estimate_invoice.entry_date";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.isBeforeFirst()) {

                while (resultSet.next()) {

                    String estimateInvoiceId = resultSet.getString("estimate_invoice_id");
                    String customerId = resultSet.getString("customer_id");
                    String customerName = resultSet.getString("customer_name");
                    String invoiceNumber = resultSet.getString("invoice_number");
                    boolean status = resultSet.getBoolean("status");
                    String entryDate = resultSet.getString("entry_date");
                    String validTill = resultSet.getString("valid_till");
                    String fromAddress = resultSet.getString("from_address");
                    String toAddress = resultSet.getString("to_address");
                    String stateProvince = resultSet.getString("state_province");
                    String country = resultSet.getString("country");
                    double totalAmount = resultSet.getDouble("total_amount");
                    String note = resultSet.getString("note");

                    EstimateInvoiceModel estimateInvoiceModel = new EstimateInvoiceModel(totalAmount, estimateInvoiceId, enterpriseId, customerId, customerName, invoiceNumber, entryDate, validTill, fromAddress, toAddress, stateProvince, country, note, status);
                    estimateInvoiceList.add(estimateInvoiceModel);
                }
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return estimateInvoiceList;
    }

}
