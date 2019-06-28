package nutan.tech.palmbusiness;

import nutan.tech.models.ReportExpenseByCategoryModel;
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

@Path("/expense_by_category")
public class ReportsExpenseByCategory {

    DatabaseFunctions databaseFunctions;

    @GET
    @Path("{enterprise_id}/{start_range}/{end_range}")
    public List<ReportExpenseByCategoryModel> getReportByExpenseCategory(@PathParam("enterprise_id") String enterpriseId, @PathParam("start_range") String startRange, @PathParam("end_range") String endRange) {

        List<ReportExpenseByCategoryModel> reportExpenseByCategoryList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT db_palm_business.expenses.expense_head, COUNT(db_palm_business.expenses.expense_head) AS total_count, SUM(db_palm_business.expenses.amount) AS total_amount FROM db_palm_business.expenses WHERE db_palm_business.expenses.enterprise_id = '" + enterpriseId + "' AND db_palm_business.expenses.entry_date BETWEEN '" + startRange + "' AND '" + endRange + "' GROUP BY db_palm_business.expenses.expense_head";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())	{

                String expenseHead = resultSet.getString("expense_head");
                double totalCount = resultSet.getDouble("total_count");
                double totalAmount = resultSet.getDouble("total_amount");

                reportExpenseByCategoryList.add(new ReportExpenseByCategoryModel(expenseHead, totalCount, totalAmount));
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);

        } catch (SQLException e) {

            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return reportExpenseByCategoryList;
    }

}
