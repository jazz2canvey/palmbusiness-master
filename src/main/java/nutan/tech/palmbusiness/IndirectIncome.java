package nutan.tech.palmbusiness;

import nutan.tech.creator.IndirectIncomeCreator;
import nutan.tech.listmodel.IndirectIncomeListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.IndirectIncomeModel;
import nutan.tech.utilities.APIUtilities;
import nutan.tech.utilities.DatabaseFunctions;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Path("/indirect_income")
@Produces(MediaType.APPLICATION_JSON)
public class IndirectIncome {

    private DatabaseFunctions databaseFunctions;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public List<CallResultModel> newIndirectIncome(final IndirectIncomeListModel indirectIncomeList) {

        final List<CallResultModel> callResultModelList = new ArrayList<>();
        List<IndirectIncomeModel> indirectIncomeModelList = IndirectIncomeCreator.getIndirectIncomes();
        indirectIncomeModelList.addAll(indirectIncomeList.getIndirectIncomeList());

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        PreparedStatement preparedStmt = null;

        String query = "INSERT INTO db_palm_business.indirect_income (indirect_income_id, enterprise_id, entry_date, indirect_income, amount, credited_account_id, payment_mode_code, description)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStmt = connection.prepareStatement(query);
            connection.setAutoCommit(false);

            for (IndirectIncomeModel indirectIncomeModel : indirectIncomeModelList) {

            	System.out.println("Payment Mode Code: " + indirectIncomeModel.getPayment_mode_code());
            	
                String indirectIncomeId = "IDI" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);
                preparedStmt.setString(1, indirectIncomeId);
                preparedStmt.setString(2, indirectIncomeModel.getEnterprise_id());
                preparedStmt.setString(3, indirectIncomeModel.getEntry_date());
                preparedStmt.setString(4, indirectIncomeModel.getIndirect_income());
                preparedStmt.setDouble(5, indirectIncomeModel.getAmount());
                preparedStmt.setString(6, indirectIncomeModel.getCredited_account_id());
                preparedStmt.setInt(7, indirectIncomeModel.getPayment_mode_code());
                preparedStmt.setString(8, indirectIncomeModel.getDescription());
                preparedStmt.addBatch();
            }

            preparedStmt.executeBatch();
            connection.commit();
            callResultModelList.add(new CallResultModel(false, true));
            databaseFunctions.closeDBOperations(connection, preparedStmt, null);

        } catch (SQLException e) {
            e.printStackTrace();
            callResultModelList.add(new CallResultModel(true, false, "Oops! Something Went Wrong, Try Again"));
            databaseFunctions.closeDBOperations(connection, preparedStmt, null);
        }

        return callResultModelList;
    }

    @GET
    @Path("{enterprise_id}")
    public List<IndirectIncomeModel> getAllIndirectIncome(@PathParam("enterprise_id") String enterpriseId) {

        List<IndirectIncomeModel> indirectIncomeList = new ArrayList<>();
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT db_palm_business.indirect_income.indirect_income_id, db_palm_business.indirect_income.enterprise_id, db_palm_business.indirect_income.entry_date, db_palm_business.indirect_income.indirect_income, db_palm_business.indirect_income.amount, db_palm_business.indirect_income.credited_account_id, db_palm_business.indirect_income.payment_mode_code, db_palm_business.bank_payment_modes.payment_mode, db_palm_business.indirect_income.description FROM db_palm_business.indirect_income LEFT JOIN db_palm_business.bank_payment_modes ON db_palm_business.indirect_income.payment_mode_code = db_palm_business.bank_payment_modes.payment_mode_code WHERE db_palm_business.indirect_income.enterprise_id = '" + enterpriseId + "' ORDER BY db_palm_business.indirect_income.entry_date";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                String indirectIncomeId = resultSet.getString("indirect_income_id");
                String entryDate = resultSet.getString("entry_date");
                String indirectIncome = resultSet.getString("indirect_income");
                double amount = resultSet.getDouble("amount");
                String creditedAccountId = resultSet.getString("credited_account_id");
                int paymentModeCode = resultSet.getInt("payment_mode_code");
                String paymentMode = resultSet.getString("payment_mode");
                String description = resultSet.getString("description");

                IndirectIncomeModel model = new IndirectIncomeModel(indirectIncomeId, enterpriseId, entryDate, indirectIncome, creditedAccountId, description, paymentModeCode, paymentMode, amount);
                indirectIncomeList.add(model);
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return indirectIncomeList;
    }

    @PUT
    @Path("{enterprise_id}/{indirect_income_id}")
    public List<CallResultModel> updateIndirectIncome(@PathParam("enterprise_id") String enterpriseId, @PathParam("indirect_income_id") String indirectIncomeId, IndirectIncomeModel indirectIncomeModel) {

        List<CallResultModel> callResultModelList = new ArrayList<>();
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        String query = "UPDATE db_palm_business.indirect_income SET db_palm_business.indirect_income.enterprise_id = ?, db_palm_business.indirect_income.entry_date = ?, db_palm_business.indirect_income.indirect_income = ?, db_palm_business.indirect_income.amount = ?, db_palm_business.indirect_income.credited_account_id = ?, db_palm_business.indirect_income.payment_mode_code = ?, db_palm_business.indirect_income.description = ? WHERE db_palm_business.indirect_income.enterprise_id = '" + enterpriseId + "' AND  db_palm_business.indirect_income.indirect_income_id = '" + indirectIncomeId + "'";

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, indirectIncomeModel.getEnterprise_id());
            preparedStmt.setString (2, indirectIncomeModel.getEntry_date());
            preparedStmt.setString (3, indirectIncomeModel.getIndirect_income());
            preparedStmt.setDouble (4, indirectIncomeModel.getAmount());
            preparedStmt.setString(5, indirectIncomeModel.getCredited_account_id());
            preparedStmt.setInt(6, indirectIncomeModel.getPayment_mode_code());
            preparedStmt.setString (7, indirectIncomeModel.getDescription());

            // execute the preparedStatement
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
    @Path("{enterprise_id}/{indirect_income_id}")
    public List<CallResultModel> deleteCustomer(@PathParam("enterprise_id") String enterpriseId, @PathParam("indirect_income_id") String indirectIncomeId) {

        List<CallResultModel> callResultModelList = new ArrayList<>();
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        String query = "DELETE FROM db_palm_business.indirect_income WHERE db_palm_business.indirect_income.enterprise_id = ? AND db_palm_business.indirect_income.indirect_income_id =  ?";

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, enterpriseId);
            preparedStmt.setString (2, indirectIncomeId);

            // execute the preparedStatement
            preparedStmt.executeUpdate();

            callResultModelList.add(new CallResultModel(false, true, "Deleted Successfully."));

        } catch (SQLException e) {
            e.printStackTrace();
            callResultModelList.add(new CallResultModel(true, false, "Failed to Delete Entry."));
            databaseFunctions.closeDBOperations(connection, preparedStmt, null);
        }

        return callResultModelList;
    }

}
