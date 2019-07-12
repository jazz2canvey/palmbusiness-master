package nutan.tech.palmbusiness;

import nutan.tech.creator.OrderedItemsCreator;
import nutan.tech.listmodel.OrderedItemsListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.OrderedItemsModel;
import nutan.tech.utilities.DatabaseFunctions;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/order_items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderedItems {

    private DatabaseFunctions databaseFunctions;

    @POST
    public List<CallResultModel> newOrderedItem(OrderedItemsListModel orderedItemsList) {

        List<CallResultModel> callResultModelList = new ArrayList<>();
        List<OrderedItemsModel> orderedItemsModelList = OrderedItemsCreator.getOrderedItems();
        orderedItemsModelList.addAll(orderedItemsList.getOrderedItemsList());

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        PreparedStatement preparedStmt;

        String query = "INSERT INTO db_palm_business.ordered_items (item_id, enterprise_id, vendor_id, invoice_number, rate_per_item, hsn_o_sac, quantity, total_amount)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);
            preparedStmt = connection.prepareStatement(query);

            for (OrderedItemsModel orderedItemsModel : orderedItemsModelList) {

                preparedStmt.setString(1, orderedItemsModel.getItem_id());
                preparedStmt.setString(2, orderedItemsModel.getEnterprise_id());
                preparedStmt.setString(3, orderedItemsModel.getVendor_id());
                preparedStmt.setString(4, orderedItemsModel.getInvoice_number());
                preparedStmt.setDouble(5, orderedItemsModel.getRate_per_item());
                preparedStmt.setString(6, orderedItemsModel.getHsn_o_sac());
                preparedStmt.setDouble(7, orderedItemsModel.getQuantity());
                preparedStmt.setDouble(8, orderedItemsModel.getTotal_amount());
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
    @Path("{enterprise_id}/{sr_no}")
    public List<CallResultModel> modifyOrderedItem (@PathParam("enterprise_id") String enterpriseId, @PathParam("sr_no") int srNo, OrderedItemsModel orderedItemsModel) {

        List<CallResultModel> callResultModelList = new ArrayList<>();
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        String query = "UPDATE db_palm_business.ordered_items SET item_id = ?, vendor_id = ?, invoice_number = ?, rate_per_item = ?, quantity = ?, total_amount = ? WHERE db_palm_business.ordered_items.enterprise_id = '" + enterpriseId + "' AND db_palm_business.ordered_items.sr_no = " + srNo;

        PreparedStatement preparedStmt = null;

        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, orderedItemsModel.getItem_id());
            preparedStmt.setString (2, orderedItemsModel.getVendor_id());
            preparedStmt.setString (3, orderedItemsModel.getInvoice_number());
            preparedStmt.setDouble (4, orderedItemsModel.getRate_per_item());
            preparedStmt.setDouble (5, orderedItemsModel.getQuantity());
            preparedStmt.setDouble (6, orderedItemsModel.getTotal_amount());

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
    @Path("{enterprise_id}/{order_number}")
    public List<OrderedItemsModel> getItemsByEnterpriseAndOrderNumber(@PathParam("enterprise_id") String enterpriseId, @PathParam("order_number") String orderNumber) {

        List<OrderedItemsModel> orderedItemsList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = databaseFunctions.connect2DB();

        String query = "SELECT db_palm_business.ordered_items.item_id, db_palm_business.items.item_name, db_palm_business.ordered_items.vendor_id, db_palm_business.ordered_items.invoice_number, db_palm_business.ordered_items.rate_per_item, db_palm_business.ordered_items.hsn_o_sac, db_palm_business.ordered_items.quantity, db_palm_business.ordered_items.total_amount FROM db_palm_business.ordered_items LEFT JOIN db_palm_business.items ON db_palm_business.ordered_items.item_id = db_palm_business.items.item_id WHERE db_palm_business.ordered_items.enterprise_id = '" + enterpriseId + "' AND db_palm_business.ordered_items.invoice_number = '" + orderNumber + "'";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.isBeforeFirst()) {

                while (resultSet.next()) {

                    String itemId = resultSet.getString("item_id");
                    String itemName = resultSet.getString("item_name");
                    String vendorId = resultSet.getString("vendor_id");
                    String invoiceNumber = resultSet.getString("invoice_number");
                    double ratePerItem = resultSet.getDouble("rate_per_item");
                    double quantity = resultSet.getDouble("quantity");
                    String hsnSac = resultSet.getString("hsn_o_sac");
                    double totalAmount = resultSet.getDouble("total_amount");

                    OrderedItemsModel orderedItemsModel = new OrderedItemsModel(ratePerItem, quantity, totalAmount, itemId, itemName, enterpriseId, vendorId, invoiceNumber, hsnSac);
                    orderedItemsList.add(orderedItemsModel);
                }
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return orderedItemsList;
    }

    @DELETE
    @Path("{enterprise_id}/{sr_no}")
    public List<CallResultModel> deleteOrderedItem (@PathParam("enterprise_id") String enterpriseId, @PathParam("sr_no") int srNo) {

        List<CallResultModel> callResultModelList = new ArrayList<>();
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        String query = "DELETE FROM db_palm_business.ordered_items WHERE db_palm_business.ordered_items.enterprise_id =  ? AND db_palm_business.ordered_items.sr_no = ?";

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, enterpriseId);
            preparedStmt.setInt (2, srNo);

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
