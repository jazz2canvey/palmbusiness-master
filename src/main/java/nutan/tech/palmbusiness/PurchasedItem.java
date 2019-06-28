package nutan.tech.palmbusiness;

import nutan.tech.creator.PurchasedItemsCreator;
import nutan.tech.listmodel.PurchasedItemsListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.PurchasedItemModel;
import nutan.tech.utilities.DatabaseFunctions;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/purchased_item")
@Produces(MediaType.APPLICATION_JSON)
public class PurchasedItem {

    DatabaseFunctions databaseFunctions;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public List<CallResultModel> newPurchasedItems(PurchasedItemsListModel purchasedItemsList) {

        List<CallResultModel> callResultModelList = new ArrayList<>();
        List<PurchasedItemModel> purchasedItemModelList = PurchasedItemsCreator.getPurchasedItems();
        purchasedItemModelList.addAll(purchasedItemsList.getPurchasedItemsList());

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        PreparedStatement preparedStmt;

        String query = "INSERT INTO db_palm_business.purchased_items (item_id, enterprise_id, vendor_id, invoice_no, item_type_code, purchase_price, measurement, quantity, sgst, cgst, igst, discount_type_code, taken_discount, total_amount, hsn_o_sac)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStmt = connection.prepareStatement(query);
            connection.setAutoCommit(false);

            for (PurchasedItemModel purchasedItemModel : purchasedItemModelList) {

                preparedStmt.setString(1, purchasedItemModel.getItem_id());
                preparedStmt.setString(2, purchasedItemModel.getEnterprise_id());
                preparedStmt.setString(3, purchasedItemModel.getVendor_id());
                preparedStmt.setString(4, purchasedItemModel.getInvoice_no());
                preparedStmt.setInt(5, purchasedItemModel.getItem_type_code());
                preparedStmt.setDouble(6, purchasedItemModel.getPurchase_price());
                preparedStmt.setString(7, purchasedItemModel.getMeasurement());
                preparedStmt.setDouble(8, purchasedItemModel.getUnits());
                preparedStmt.setDouble(9, purchasedItemModel.getSgst());
                preparedStmt.setDouble(10, purchasedItemModel.getCgst());
                preparedStmt.setDouble(11, purchasedItemModel.getIgst());
                preparedStmt.setInt(12, purchasedItemModel.getDiscount_type_code());
                preparedStmt.setDouble(13, purchasedItemModel.getTaken_discount());
                preparedStmt.setDouble(14, purchasedItemModel.getTotal_amount());
                preparedStmt.setString(15, purchasedItemModel.getHsn_o_sac());
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
    @Consumes(MediaType.APPLICATION_JSON)
    public List<CallResultModel> modifyItem(@PathParam("enterprise_id") String enterpriseId, @PathParam("sr_no") int srNo, PurchasedItemModel purchasedItemModel) {

        List<CallResultModel> callResultModelList = new ArrayList<>();
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        String query = "UPDATE db_palm_business.purchased_items SET item_id = ?, vendor_id = ?, invoice_no = ?, item_type_code = ?, purchase_price = ?, quantity = ?, measurement = ?, sgst = ?, cgst = ?, igst = ?, discount_type_code = ?, taken_discount = ?, total_amount = ?, hsn_o_sac = ? WHERE db_palm_business.purchased_items.enterprise_id = '" + enterpriseId + "' AND db_palm_business.purchased_items.sr_no = " + srNo;

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, purchasedItemModel.getItem_id());
            preparedStmt.setString (2, purchasedItemModel.getVendor_id());
            preparedStmt.setString (3, purchasedItemModel.getInvoice_no());
            preparedStmt.setInt (4, purchasedItemModel.getItem_type_code());
            preparedStmt.setDouble (5, purchasedItemModel.getPurchase_price());
            preparedStmt.setDouble (6, purchasedItemModel.getUnits());
            preparedStmt.setString (7, purchasedItemModel.getMeasurement());
            preparedStmt.setDouble (8, purchasedItemModel.getSgst());
            preparedStmt.setDouble (9, purchasedItemModel.getCgst());
            preparedStmt.setDouble (10, purchasedItemModel.getIgst());
            preparedStmt.setInt (11, purchasedItemModel.getDiscount_type_code());
            preparedStmt.setDouble (12, purchasedItemModel.getTaken_discount());
            preparedStmt.setDouble (13, purchasedItemModel.getTotal_amount());
            preparedStmt.setString (14, purchasedItemModel.getHsn_o_sac());

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
    @Path("{enterprise_id}/{sr_no}")
    public List<CallResultModel> deleteItem(@PathParam("enterprise_id") String enterpriseId, @PathParam("sr_no") int srNo) {

        List<CallResultModel> callResultModelList = new ArrayList<>();
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        String query = "DELETE FROM db_palm_business.purchased_items WHERE db_palm_business.purchased_items.enterprise_id =  ? AND db_palm_business.purchased_items.sr_no = ?";

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
