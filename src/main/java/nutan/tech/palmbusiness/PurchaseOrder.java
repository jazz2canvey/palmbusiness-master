package nutan.tech.palmbusiness;

import nutan.tech.creator.PurchaseOrderCreator;
import nutan.tech.listmodel.PurchaseOrdersListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.PurchaseOrderModel;
import nutan.tech.utilities.APIUtilities;
import nutan.tech.utilities.DatabaseFunctions;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Path("/purchase_order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PurchaseOrder {

    private DatabaseFunctions databaseFunctions;

    @POST
    @Path("{enterprise_id}/{invoice_number}")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<CallResultModel> newPurchaseOrder(@PathParam("enterprise_id") final String enterpriseId, @PathParam("invoice_number") final String invoiceNumber, final PurchaseOrdersListModel purchaseOrdersList) {

        final List<CallResultModel> callResultModelList = new ArrayList<>();

        Thread insert2Database = new Thread(new Runnable() {

            @Override
            public void run() {

                List<PurchaseOrderModel> purchaseOrderModelList = PurchaseOrderCreator.getPurchaseOrder();
                purchaseOrderModelList.addAll(purchaseOrdersList.getPurchaseOrdersList());

                databaseFunctions = new DatabaseFunctions();
                Connection connection = databaseFunctions.connect2DB();
                PreparedStatement preparedStmt = null;

                String query = "INSERT INTO db_palm_business.purchase_order (purchase_order_id, enterprise_id, vendor_id, invoice_number, entry_date, status, to_address, billing_address, shipping_address, state_province, country, total_amount, note)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try {
                    preparedStmt = connection.prepareStatement(query);
                    connection.setAutoCommit(false);

                    for (PurchaseOrderModel purchaseOrderModel : purchaseOrderModelList) {

                        String purchaseOrderId = "PO" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);
                        preparedStmt.setString(1, purchaseOrderId);
                        preparedStmt.setString(2, purchaseOrderModel.getEnterprise_id());
                        preparedStmt.setString(3, purchaseOrderModel.getVendor_id());
                        preparedStmt.setString(4, purchaseOrderModel.getInvoice_number());
                        preparedStmt.setString(5, purchaseOrderModel.getEntry_date());
                        preparedStmt.setBoolean(6, purchaseOrderModel.isStatus());
                        preparedStmt.setString(7, purchaseOrderModel.getTo_address());
                        preparedStmt.setString(8, purchaseOrderModel.getBilling_address());
                        preparedStmt.setString(9, purchaseOrderModel.getShipping_address());
                        preparedStmt.setString(10, purchaseOrderModel.getState_province());
                        preparedStmt.setString(11, purchaseOrderModel.getCountry());
                        preparedStmt.setDouble(12, purchaseOrderModel.getTotal_amount());
                        preparedStmt.setString(13, purchaseOrderModel.getNote());
                        preparedStmt.addBatch();
                    }

                    preparedStmt.executeBatch();
                    connection.commit();
                    callResultModelList.add(new CallResultModel(false, true, "Executed Successfully"));
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

                String getQuery = "SELECT * FROM db_palm_business.purchase_order WHERE db_palm_business.purchase_order.enterprise_id = '" + enterpriseId + "' AND db_palm_business.purchase_order.invoice_number = '" + invoiceNumber + "'";

                try {

                    Statement st = connection.createStatement();
                    ResultSet resultSet = st.executeQuery(getQuery);

                    if (resultSet.isBeforeFirst()) {

                        callResultModelList.add(new CallResultModel(true, false, "Order Number Already Used"));
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
    public List<PurchaseOrderModel> getAllOrders(@PathParam("enterprise_id") String enterpriseId) {

        List<PurchaseOrderModel> purchaseOrderList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = databaseFunctions.connect2DB();

        String query = "SELECT db_palm_business.purchase_order.purchase_order_id, db_palm_business.purchase_order.vendor_id, db_palm_business.vendors.enterprise_name AS vendor_name, db_palm_business.purchase_order.invoice_number, db_palm_business.purchase_order.to_address, db_palm_business.purchase_order.billing_address, db_palm_business.purchase_order.shipping_address, db_palm_business.purchase_order.status, db_palm_business.purchase_order.entry_date, db_palm_business.purchase_order.state_province, db_palm_business.purchase_order.country, db_palm_business.purchase_order.total_amount, db_palm_business.purchase_order.note FROM db_palm_business.purchase_order LEFT JOIN db_palm_business.vendors ON db_palm_business.purchase_order.vendor_id = db_palm_business.vendors.vendor_id WHERE db_palm_business.purchase_order.enterprise_id = '" + enterpriseId + "'";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.isBeforeFirst()) {

                while (resultSet.next()) {

                    String purchaseOrderId = resultSet.getString("purchase_order_id");
                    String vendorId = resultSet.getString("vendor_id");
                    String vendorName = resultSet.getString("vendor_name");
                    String invoiceNumber = resultSet.getString("invoice_number");
                    String entryDate = resultSet.getString("entry_date");
                    boolean status = resultSet.getBoolean("status");
                    String toAddress = resultSet.getString("to_address");
                    String billingAddress = resultSet.getString("billing_address");
                    String shippingAddress = resultSet.getString("shipping_address");
                    String stateProvince = resultSet.getString("state_province");
                    String country = resultSet.getString("country");
                    double totalAmount = resultSet.getDouble("total_amount");
                    String note = resultSet.getString("note");

                    PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel(purchaseOrderId, enterpriseId, vendorId, vendorName, invoiceNumber, entryDate, toAddress, billingAddress, shippingAddress, stateProvince, country, note, totalAmount, status);
                    purchaseOrderList.add(purchaseOrderModel);
                }

            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return purchaseOrderList;
    }

}
