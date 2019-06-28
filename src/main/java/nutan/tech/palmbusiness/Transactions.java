package nutan.tech.palmbusiness;

import nutan.tech.models.IndirectIncomeModel;
import nutan.tech.models.PaymentReceiptModel;
import nutan.tech.utilities.DatabaseFunctions;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
public class Transactions {

    private DatabaseFunctions databaseFunctions;

    @GET
    @Path("/indirect_income/{enterprise_id}/{account_id}")
    public List<IndirectIncomeModel> getAllIndirectIncome(@PathParam("enterprise_id") String enterpriseId, @PathParam("account_id") String creditedAccountId) {

        List<IndirectIncomeModel> indirectIncomeList = new ArrayList<>();
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT db_palm_business.indirect_income.indirect_income_id, db_palm_business.indirect_income.enterprise_id, db_palm_business.indirect_income.entry_date, db_palm_business.indirect_income.indirect_income, db_palm_business.indirect_income.amount, db_palm_business.indirect_income.credited_account_id, db_palm_business.indirect_income.payment_mode_code, db_palm_business.bank_payment_modes.payment_mode, db_palm_business.indirect_income.description FROM db_palm_business.indirect_income LEFT JOIN db_palm_business.bank_payment_modes ON db_palm_business.indirect_income.payment_mode_code = db_palm_business.bank_payment_modes.payment_mode_code WHERE db_palm_business.indirect_income.enterprise_id = '" + enterpriseId + "' AND db_palm_business.indirect_income.credited_account_id = '" + creditedAccountId + "' ORDER BY db_palm_business.indirect_income.entry_date";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                String indirectIncomeId = resultSet.getString("indirect_income_id");
                String entryDate = resultSet.getString("entry_date");
                String indirectIncome = resultSet.getString("indirect_income");
                double amount = resultSet.getDouble("amount");
                creditedAccountId = resultSet.getString("credited_account_id");
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

    @GET
    @Path("{enterprise_id}/{party_code}/{account_id}")
    public List<PaymentReceiptModel> getPaymentReceipt(@PathParam("enterprise_id") String enterpriseId, @PathParam("party_code") int partyCode, @PathParam("account_id") String accountId) {

        List<PaymentReceiptModel> paymentReceiptList = new ArrayList<>();
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT db_palm_business.payments_receipts.payment_receipt_id, db_palm_business.payments_receipts.enterprise_id, db_palm_business.payments_receipts.entry_date, db_palm_business.party.party AS party_value, db_palm_business.movers.mover AS mover_value, db_palm_business.payments_receipts.invoice_number, db_palm_business.payments_receipts.vendor_customer, (CASE WHEN db_palm_business.payments_receipts.mover_code = 1 THEN db_palm_business.vendors.enterprise_name WHEN (db_palm_business.payments_receipts.mover_code = 2 AND db_palm_business.customers.enterprise_name IS NOT NULL) THEN db_palm_business.customers.enterprise_name WHEN (db_palm_business.payments_receipts.mover_code = 2 AND db_palm_business.customers.person_name IS NOT NULL) THEN db_palm_business.customers.person_name END) AS name, (CASE WHEN (db_palm_business.payments_receipts.payed_via_bank = TRUE) THEN  db_palm_business.bank_payment_modes.payment_mode WHEN (db_palm_business.payments_receipts.payed_via_cash = TRUE) THEN 'CASH' END)AS payment_mode, db_palm_business.payments_receipts.account_id, (CASE WHEN db_palm_business.bank_accounts.bank_name IS NULL OR db_palm_business.bank_accounts.bank_name = '' THEN 'CASH' ELSE db_palm_business.bank_accounts.bank_name END) AS account_name, db_palm_business.payments_receipts.amount, db_palm_business.payments_receipts.payed_via_bank, db_palm_business.payments_receipts.payed_via_cash, db_palm_business.payments_receipts.description FROM payments_receipts LEFT JOIN vendors ON payments_receipts.vendor_customer = vendors.vendor_id LEFT JOIN customers ON payments_receipts.vendor_customer = customers.customer_id LEFT JOIN party ON payments_receipts.party_code = party.party_code LEFT JOIN movers ON payments_receipts.mover_code = movers.mover_code LEFT JOIN bank_payment_modes ON payments_receipts.bank_payment_mode = bank_payment_modes.payment_mode_code LEFT JOIN db_palm_business.bank_accounts ON db_palm_business.payments_receipts.account_id = db_palm_business.bank_accounts.bank_account_id WHERE payments_receipts.enterprise_id = '" + enterpriseId + "' AND payments_receipts.party_code = " + partyCode + " AND payments_receipts.account_id = '" + accountId + "' ORDER BY db_palm_business.payments_receipts.entry_date ASC";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                String paymentReceiptId = resultSet.getString("payment_receipt_id");
                String entryDate = resultSet.getString("entry_date");
                String partyValue = resultSet.getString("party_value");
                String moverValue = resultSet.getString("mover_value");
                String invoiceNumber = resultSet.getString("invoice_number");
                String idCustomerVendor = resultSet.getString("vendor_customer");
                String name = resultSet.getString("name");
                String paymentMode = resultSet.getString("payment_mode");
                accountId = resultSet.getString("account_id");
                String accountName = resultSet.getString("account_name");
                double amount = resultSet.getDouble("amount");
                boolean paidViaBank = resultSet.getBoolean("payed_via_bank");
                boolean paidViaCash = resultSet.getBoolean("payed_via_cash");
                String description = resultSet.getString("description");

                PaymentReceiptModel model = new PaymentReceiptModel(paidViaBank, paidViaCash, paymentReceiptId, enterpriseId, entryDate, idCustomerVendor, invoiceNumber, accountId, description, partyValue, moverValue, name, paymentMode, accountName, amount);
                paymentReceiptList.add(model);
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return paymentReceiptList;
    }

}
