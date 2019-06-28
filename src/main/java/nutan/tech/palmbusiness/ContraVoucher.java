package nutan.tech.palmbusiness;

import nutan.tech.models.CallResultModel;
import nutan.tech.models.ContraVoucherModel;
import nutan.tech.utilities.APIUtilities;
import nutan.tech.utilities.DatabaseFunctions;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Path("/contra_voucher")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContraVoucher {

    private DatabaseFunctions databaseFunctions;

    @POST
    public List<CallResultModel> newContraVoucher(ContraVoucherModel contraVoucherModel) {

        List<CallResultModel> callResultModelList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        String query = "INSERT INTO db_palm_business.contra_voucher (contra_voucher_id, entry_date, enterprise_id, contra_type_code, payment_mode, giver, receiver, amount, note)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt;
        try {
            preparedStmt = connection.prepareStatement(query);

            String contraVoucherId = "CV" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);
            preparedStmt.setString(1, contraVoucherId);
            preparedStmt.setString(2, contraVoucherModel.getEntry_date());
            preparedStmt.setString(3, contraVoucherModel.getEnterprise_id());
            preparedStmt.setInt(4, contraVoucherModel.getContra_type_code());
            preparedStmt.setInt(5, contraVoucherModel.getPayment_mode());
            preparedStmt.setString(6, contraVoucherModel.getGiver());
            preparedStmt.setString(7, contraVoucherModel.getReceiver());
            preparedStmt.setDouble(8, contraVoucherModel.getAmount());
            preparedStmt.setString(9, contraVoucherModel.getNote());

            preparedStmt.execute();
            callResultModelList.add(new CallResultModel(false, true));
            databaseFunctions.closeDBOperations(connection, preparedStmt, null);

        } catch (SQLException e) {
            e.printStackTrace();
            callResultModelList.add(new CallResultModel(true, false));
        }

        return callResultModelList;
    }

    @PUT
    @Path("{enterprise_id}/{contra_voucher_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<CallResultModel> modifyContraVoucher(@PathParam("enterprise_id") String enterpriseId, @PathParam("contra_voucher_id") String contraVoucherId, ContraVoucherModel contraVoucherModel) {

        List<CallResultModel> callResultModelList = new ArrayList<>();
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        String query = "UPDATE db_palm_business.contra_voucher SET entry_date = ?, enterprise_id = ?, contra_type_code = ?, payment_mode = ?, giver = ?, receiver = ?, amount = ?, note = ? WHERE db_palm_business.contra_voucher.enterprise_id = '" + enterpriseId + "' AND db_palm_business.contra_voucher.contra_voucher_id = '" + contraVoucherId + "'";

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, contraVoucherModel.getEntry_date());
            preparedStmt.setString (2, contraVoucherModel.getEnterprise_id());
            preparedStmt.setInt (3, contraVoucherModel.getContra_type_code());
            preparedStmt.setInt (4, contraVoucherModel.getPayment_mode());
            preparedStmt.setString (5, contraVoucherModel.getGiver());
            preparedStmt.setString (6, contraVoucherModel.getReceiver());
            preparedStmt.setDouble (7, contraVoucherModel.getAmount());
            preparedStmt.setString (8, contraVoucherModel.getNote());

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
    @Path("{enterprise_id}")
    public List<ContraVoucherModel> getContraVouchers(@PathParam("enterprise_id") String enterpriseId) {

        List<ContraVoucherModel> contraVoucherList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT contra_voucher.contra_voucher_id, contra_voucher.entry_date, contra_voucher.enterprise_id, contra_voucher.contra_type_code, contra_types.contra_title, bank_payment_modes.payment_mode, contra_voucher.giver, contra_voucher.receiver, contra_voucher.amount, contra_voucher.note FROM contra_voucher LEFT JOIN contra_types ON contra_voucher.contra_type_code = contra_types.contra_type_code LEFT JOIN bank_payment_modes ON contra_voucher.payment_mode = bank_payment_modes.payment_mode_code WHERE contra_voucher.enterprise_id = '" + enterpriseId + "' ORDER BY db_palm_business.contra_voucher.entry_date ASC";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
            	
                String contraVoucherId = resultSet.getString("contra_voucher_id");
                String entryDate = resultSet.getString("entry_date");
            		int contraTypeCode = resultSet.getInt("contra_type_code");
                String contraTitle = resultSet.getString("contra_title");
                if (contraTitle.equals(""))
                    contraTitle = "DEPOSITED";
                String paymentMode = resultSet.getString("payment_mode");
                String giver = resultSet.getString("giver");
                String receiver = resultSet.getString("receiver");
                double amount = resultSet.getDouble("amount");
                String note = resultSet.getString("note");

                ContraVoucherModel model = new ContraVoucherModel(contraVoucherId, entryDate, contraTypeCode, enterpriseId, giver, receiver, note, contraTitle, paymentMode, amount);
                contraVoucherList.add(model);
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return contraVoucherList;

    }

    @DELETE
    @Path("{enterprise_id}/{contra_voucher_id}")
    public List<CallResultModel> deleteItem(@PathParam("enterprise_id") String enterpriseId, @PathParam("contra_voucher_id") String contraVoucherId) {

        List<CallResultModel> callResultModelList = new ArrayList<>();
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        String query = "DELETE FROM db_palm_business.contra_voucher WHERE db_palm_business.contra_voucher.enterprise_id =  ? AND db_palm_business.contra_voucher.contra_voucher_id = ?";

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, enterpriseId);
            preparedStmt.setString (2, contraVoucherId);

            // execute the preparedstatement
            preparedStmt.executeUpdate();
			callResultModelList.add(new CallResultModel(false, true, "Deleted Successfully..."));

        } catch (SQLException e) {
            e.printStackTrace();
            callResultModelList.add(new CallResultModel(true, false));
            databaseFunctions.closeDBOperations(connection, preparedStmt, null);
        }

        return callResultModelList;
    }

}
