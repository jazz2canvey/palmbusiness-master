package nutan.tech.palmbusiness;

import nutan.tech.creator.EstimateItemsCreator;
import nutan.tech.listmodel.EstimateItemsListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.EstimateItemsModel;
import nutan.tech.utilities.DatabaseFunctions;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/estimate_item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstimateItems {

    private DatabaseFunctions databaseFunctions;

    @POST
    public List<CallResultModel> newEstimateItem(EstimateItemsListModel estimateItemsList) {

        List<CallResultModel> callResultModelList = new ArrayList<>();
        List<EstimateItemsModel> estimateItemsModelList = EstimateItemsCreator.getEstimateItems();
        estimateItemsModelList.addAll(estimateItemsList.getEstimateItemsList());

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        PreparedStatement preparedStmt;

        String query = "INSERT INTO db_palm_business.estimate_items (item_id, enterprise_id, customer_id, invoice_number, item_type_code, rate_per_item, quantity, total_amount, hsn_o_sac)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);
            preparedStmt = connection.prepareStatement(query);

            for (EstimateItemsModel estimateItemsModel : estimateItemsModelList) {

                preparedStmt.setString(1, estimateItemsModel.getItem_id());
                preparedStmt.setString(2, estimateItemsModel.getEnterprise_id());
                preparedStmt.setString(3, estimateItemsModel.getCustomer_id());
                preparedStmt.setString(4, estimateItemsModel.getInvoice_number());
                preparedStmt.setInt(5, estimateItemsModel.getItem_type_code());
                preparedStmt.setDouble(6, estimateItemsModel.getRate_per_item());
                preparedStmt.setDouble(7, estimateItemsModel.getQuantity());
                preparedStmt.setDouble(8, estimateItemsModel.getTotal_amount());
                preparedStmt.setString(9, estimateItemsModel.getHsn_o_sac());
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
    @Path("{enterprise_id}/{estimate_number}")
    public List<EstimateItemsModel> getItemsByEnterpriseAndEstimateNumber(@PathParam("enterprise_id") String enterpriseId, @PathParam("estimate_number") String estimateNumber) {

        List<EstimateItemsModel> estimateItemsList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = databaseFunctions.connect2DB();

        String query = "SELECT db_palm_business.estimate_items.item_id, db_palm_business.items.item_name, db_palm_business.estimate_items.customer_id, db_palm_business.estimate_items.invoice_number, db_palm_business.estimate_items.item_type_code, db_palm_business.item_type.item_type, db_palm_business.estimate_items.rate_per_item, db_palm_business.estimate_items.quantity, db_palm_business.estimate_items.total_amount, db_palm_business.estimate_items.hsn_o_sac FROM db_palm_business.estimate_items LEFT JOIN db_palm_business.items ON db_palm_business.estimate_items.item_id = db_palm_business.items.item_id LEFT JOIN db_palm_business.item_type ON db_palm_business.estimate_items.item_type_code = db_palm_business.item_type.item_type_code WHERE db_palm_business.estimate_items.enterprise_id = '" + enterpriseId + "' AND db_palm_business.estimate_items.invoice_number = '" + estimateNumber + "'";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.isBeforeFirst()) {

                while (resultSet.next()) {

                    String itemId = resultSet.getString("item_id");
                    String itemName = resultSet.getString("item_name");
                    String customerId = resultSet.getString("customer_id");
                    String invoiceNumber = resultSet.getString("invoice_number");
                    int itemTypeCode = resultSet.getInt("item_type_code");
                    String itemType = resultSet.getString("item_type");
                    double ratePerItem = resultSet.getDouble("rate_per_item");
                    double quantity = resultSet.getDouble("quantity");
                    String hsnSac = resultSet.getString("hsn_o_sac");
                    double totalAmount = resultSet.getDouble("total_amount");

                    EstimateItemsModel estimateItemsModel = new EstimateItemsModel(itemTypeCode, ratePerItem, quantity, totalAmount, itemId, itemName, itemType, enterpriseId, customerId, invoiceNumber, hsnSac);
                    estimateItemsList.add(estimateItemsModel);
                }
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return estimateItemsList;
    }

}
