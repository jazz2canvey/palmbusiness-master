package nutan.tech.palmbusiness;

import nutan.tech.models.ReportContraVoucherModel;
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

@Path("/contra_voucher_report")
public class ReportsContraVochers {

    DatabaseFunctions databaseFunctions;

    @GET
    @Path("/{enterprise_id}/{contra_voucher_type}/{start_range}/{end_range}")
    public List<ReportContraVoucherModel> getReportByContraVoucherType(@PathParam("enterprise_id") String enterpriseId, @PathParam("contra_voucher_type") int contraVoucherType, @PathParam("start_range") String startRange, @PathParam("end_range") String endRange) {

        List<ReportContraVoucherModel> reportContraVoucherList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT db_palm_business.contra_voucher.contra_voucher_id, db_palm_business.contra_voucher.entry_date, db_palm_business.contra_types.contra_title, db_palm_business.bank_payment_modes.payment_mode, db_palm_business.contra_voucher.giver, db_palm_business.contra_voucher.receiver, db_palm_business.contra_voucher.amount, db_palm_business.contra_voucher.note FROM db_palm_business.contra_voucher LEFT JOIN db_palm_business.contra_types ON db_palm_business.contra_voucher.contra_type_code = db_palm_business.contra_types.contra_type_code LEFT JOIN db_palm_business.bank_payment_modes ON db_palm_business.contra_voucher.payment_mode = db_palm_business.bank_payment_modes.payment_mode_code WHERE db_palm_business.contra_voucher.enterprise_id = '" + enterpriseId + "' AND db_palm_business.contra_voucher.contra_type_code = '" + contraVoucherType + "' AND db_palm_business.contra_voucher.entry_date BETWEEN '" + startRange + "' AND '" + endRange + "' ORDER BY db_palm_business.contra_voucher.entry_date";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())	{

                String contraVoucherId = resultSet.getString("contra_voucher_id");
                String entryDate = resultSet.getString("entry_date");
                String contraTitle = resultSet.getString("contra_title");
                String paymentMode = resultSet.getString("payment_mode");
                String giver = resultSet.getString("giver");
                String receiver = resultSet.getString("receiver");
                double amount = resultSet.getDouble("amount");
                String note = resultSet.getString("note");

                ReportContraVoucherModel model = new ReportContraVoucherModel(contraVoucherId, entryDate, contraTitle, paymentMode, giver, receiver, note, amount);

                reportContraVoucherList.add(model);
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);

        } catch (SQLException e) {

            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return reportContraVoucherList;
    }

}
