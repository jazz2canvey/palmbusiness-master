package nutan.tech.palmbusiness;

import nutan.tech.models.*;
import nutan.tech.utilities.APIUtilities;
import nutan.tech.utilities.DatabaseFunctions;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Path("/bank_accounts")
public class BankAccounts {

    DatabaseFunctions databaseFunctions;

    private BankAccountModel model = new BankAccountModel(false, "Bank Account Not Exist");

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public BankAccountModel createBankAccount(final BankAccountModel bankAccountModel) throws InterruptedException {

        Thread isExistThread = new Thread(new Runnable() {

            @Override
            public void run() {

                databaseFunctions = new DatabaseFunctions();
                Connection connection = databaseFunctions.connect2DB();
                String getQuery = "SELECT * FROM db_palm_business.bank_accounts WHERE db_palm_business.bank_accounts.enterprise_id = '" + bankAccountModel.getEnterprise_id() + "'";
                Statement statement = null;
                ResultSet resultSet = null;

                try {
                    String bankName, accountHolder, accountNumber;
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery(getQuery);

                    while (resultSet.next()) {

                        bankName = resultSet.getString("bank_name");
                        accountHolder = resultSet.getString("account_holder");
                        accountNumber = resultSet.getString("account_number");

                        if (bankName != null && accountHolder != null && accountNumber != null) {

                            if (bankAccountModel.getBank_name().matches(bankName) && bankAccountModel.getAccount_holder().matches(accountHolder) && bankAccountModel.getAccount_number().matches(accountNumber)) {

                                model = new BankAccountModel(true, "Bank Account Already Exist");
                                databaseFunctions.closeDBOperations(connection, statement, null);
                                return;
                            }
                        }
                    }

                    databaseFunctions.closeDBOperations(connection, statement, resultSet);

                } catch (SQLException e) {
                    e.printStackTrace();
                    databaseFunctions.closeDBOperations(connection, statement, resultSet);
                }
            }
        });

        Thread insert2Database = new Thread(
                new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        databaseFunctions = new DatabaseFunctions();
                        Connection connection = databaseFunctions.connect2DB();
                        String query = "INSERT INTO db_palm_business.bank_accounts (bank_account_id, enterprise_id, bank_name, account_holder, account_number, ifsc_code, opening_balance, branch, state, country, zip_pin_code)"
                                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                        PreparedStatement preparedStmt = null;
                        ResultSet rs = null;
                        try {
                            preparedStmt = connection.prepareStatement(query);

                            String bankAccountId = "BAC" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);
                            preparedStmt.setString(1, bankAccountId);
                            preparedStmt.setString (2, bankAccountModel.getEnterprise_id());
                            preparedStmt.setString (3, bankAccountModel.getBank_name());
                            preparedStmt.setString(4, bankAccountModel.getAccount_holder());
                            preparedStmt.setString (5, bankAccountModel.getAccount_number());
                            preparedStmt.setString (6, bankAccountModel.getIfsc_code());
                            preparedStmt.setDouble (7, bankAccountModel.getOpening_balance());
                            preparedStmt.setString (8, bankAccountModel.getBranch());
                            preparedStmt.setString(9, bankAccountModel.getState());
                            preparedStmt.setString (10, bankAccountModel.getCountry());
                            preparedStmt.setString (11, bankAccountModel.getZip_pin_code());

                            // execute the preparedstatement
                            preparedStmt.execute();

                            String getQuery = "SELECT * FROM db_palm_business.bank_accounts WHERE db_palm_business.bank_accounts.bank_account_id = '" + bankAccountId + "'";

                            Statement st = connection.createStatement();
                            rs = st.executeQuery(getQuery);

                            while (rs.next()) {

                                model = new BankAccountModel(rs.getString("bank_account_id"), rs.getString("enterprise_id"), rs.getString("bank_name"), rs.getString("account_holder"), rs.getString("account_number"), rs.getString("ifsc_code"), rs.getDouble("opening_balance"), rs.getString("branch"), rs.getString("state"), rs.getString("country"), rs.getString("zip_pin_code"));
                            }

                            st.close();
                            databaseFunctions.closeDBOperations(connection, preparedStmt, rs);

                        } catch (SQLException e) {
                            e.printStackTrace();
                                databaseFunctions.closeDBOperations(connection, preparedStmt, rs);
                        }

                    }
                });

        isExistThread.start();
        isExistThread.join();

        if (!model.isExist()) {

            insert2Database.start();
            insert2Database.join();
        }

        return model;

    }

    @PUT
    @Path("{enterprise_id}/{bank_account_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<CallResultModel> modifyBankAccount(@PathParam("enterprise_id") String enterpriseId, @PathParam("bank_account_id") String bankAccountId, BankAccountModel bankAccountModel) {

        List<CallResultModel> callResultModelList = new ArrayList<>();
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        String query = "UPDATE db_palm_business.bank_accounts SET bank_name = ?, account_holder = ?, account_number = ?, ifsc_code = ?, opening_balance = ?, branch = ?, state = ?, country = ?, zip_pin_code = ? WHERE db_palm_business.bank_accounts.enterprise_id = '" + enterpriseId + "' AND db_palm_business.bank_accounts.bank_account_id = '" + bankAccountId + "'";

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, bankAccountModel.getBank_name());
            preparedStmt.setString (2, bankAccountModel.getAccount_holder());
            preparedStmt.setString (3, bankAccountModel.getAccount_number());
            preparedStmt.setString (4, bankAccountModel.getIfsc_code());
            preparedStmt.setDouble (5, bankAccountModel.getOpening_balance());
            preparedStmt.setString (6, bankAccountModel.getBranch());
            preparedStmt.setString (7, bankAccountModel.getState());
            preparedStmt.setString (8, bankAccountModel.getCountry());
            preparedStmt.setString (9, bankAccountModel.getZip_pin_code());

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
    @Path("/bank_account/{bank_account_id}")
    public List<BankAccountModel> getBankAccount(@PathParam("bank_account_id") String bankAccountID) {

        List<BankAccountModel> bankAccountList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM db_palm_business.bank_accounts WHERE db_palm_business.bank_accounts.bank_account_id = '" + bankAccountID + "'";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                String bankAccountId = resultSet.getString("bank_account_id");
                String enterpriseId = resultSet.getString("enterprise_id");
                String bankName = resultSet.getString("bank_name");
                String accountHolder = resultSet.getString("account_holder");
                String accountNumber = resultSet.getString("account_number");
                String ifscCode = resultSet.getString("ifsc_code");
                double openingBalance = resultSet.getDouble("opening_balance");
                String branch = resultSet.getString("branch");
                String state = resultSet.getString("state");
                String country = resultSet.getString("country");
                String pinCode = resultSet.getString("zip_pin_code");

                bankAccountList.add(new BankAccountModel(bankAccountId, enterpriseId, bankName, accountHolder, accountNumber, ifscCode, openingBalance, branch, state, country, pinCode));
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return bankAccountList;
    }

    @GET
    @Path("{enterprise_id}")
    public List<BankAccountModel> getAllBankAccounts(@PathParam("enterprise_id") String enterpriseId) {

        List<BankAccountModel> bankAccountList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM db_palm_business.bank_accounts WHERE db_palm_business.bank_accounts.enterprise_id = '" + enterpriseId + "'";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                String bankAccountId = resultSet.getString("bank_account_id");
                String bankName = resultSet.getString("bank_name");
                String accountHolder = resultSet.getString("account_holder");
                String accountNumber = resultSet.getString("account_number");
                String ifscCode = resultSet.getString("ifsc_code");
                double openingBalance = resultSet.getDouble("opening_balance");
                String branch = resultSet.getString("branch");
                String state = resultSet.getString("state");
                String country = resultSet.getString("country");
                String pinCode = resultSet.getString("zip_pin_code");

                bankAccountList.add(new BankAccountModel(bankAccountId, enterpriseId, bankName, accountHolder, accountNumber, ifscCode, openingBalance, branch, state, country, pinCode));
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return bankAccountList;
    }

    @GET
    @Path("all_accounts/{enterprise_id}")
    public List<BankAndCashAccountsModel> getAllAccountsBalance(@PathParam("enterprise_id") String enterpriseId) {

        List<BankAndCashAccountsModel> outputList = new ArrayList<>();

        databaseFunctions = new DatabaseFunctions();
        Connection connection = null;
        Statement statement = null;

///        String getCashAccountStatusQuery = "SELECT cash_accounts.*, (SELECT SUM(indirect_income.amount) FROM indirect_income WHERE indirect_income.enterprise_id = '" + enterpriseId + "' AND indirect_income.credited_account_id = cash_accounts.cash_account_id) AS total_indirect_income, (SELECT SUM(payments_receipts.amount) FROM payments_receipts WHERE payments_receipts.enterprise_id = '" + enterpriseId + "' AND payments_receipts.party_code = '1' AND cash_accounts.cash_account_id = payments_receipts.account_id) AS total_payment, (SELECT SUM(payments_receipts.amount) FROM payments_receipts WHERE payments_receipts.enterprise_id = '" + enterpriseId + "' AND payments_receipts.party_code = '2' AND cash_accounts.cash_account_id = payments_receipts.account_id) AS total_receipt, (SELECT SUM(db_palm_business.contra_voucher.amount) FROM contra_voucher WHERE contra_voucher.enterprise_id = '" + enterpriseId + "' AND contra_voucher.giver = cash_accounts.cash_account_id) AS total_outgoing_amount, (SELECT SUM(db_palm_business.contra_voucher.amount) FROM contra_voucher WHERE contra_voucher.enterprise_id = '" + enterpriseId + "' AND contra_voucher.receiver = cash_accounts.cash_account_id) AS total_incoming_amount FROM cash_accounts WHERE cash_accounts.enterprise_id = '" + enterpriseId + "'";
        String getBankAccountStatusQuery = "SELECT bank_accounts.*, SUM(indirect_income.amount) AS total_indirect_income, (SELECT SUM(payments_receipts.amount) FROM payments_receipts WHERE payments_receipts.party_code = 1 AND bank_accounts.bank_account_id = payments_receipts.account_id) AS total_payments_made, (SELECT SUM(payments_receipts.amount) FROM payments_receipts WHERE payments_receipts.party_code = 2 AND bank_accounts.bank_account_id = payments_receipts.account_id) AS total_receipts_received, (SELECT SUM(contra_voucher.amount) FROM contra_voucher WHERE contra_voucher.contra_type_code = 1 AND bank_accounts.bank_account_id = contra_voucher.receiver) AS total_incoming_amount, (SELECT SUM(contra_voucher.amount) FROM contra_voucher WHERE contra_voucher.contra_type_code = 2 AND bank_accounts.bank_account_id = contra_voucher.giver) AS total_outgoing_amount FROM bank_accounts LEFT JOIN indirect_income ON bank_accounts.enterprise_id = bank_accounts.enterprise_id AND bank_accounts.bank_account_id = indirect_income.credited_account_id WHERE bank_accounts.enterprise_id = '" + enterpriseId + "' GROUP BY bank_accounts.bank_account_id";

        ResultSet cashAccountsRS = null;
        ResultSet bankAccountsRS = null;

        try {

            connection = databaseFunctions.connect2DB();
            statement = connection.createStatement();
            bankAccountsRS = statement.executeQuery(getBankAccountStatusQuery);

            while (bankAccountsRS.next())
            {
                String bankAccountId = bankAccountsRS.getString("bank_account_id");
                String bankName = bankAccountsRS.getString("bank_name");
                String accountHolder = bankAccountsRS.getString("account_holder");
                String accountNumber = bankAccountsRS.getString("account_number");
                String ifscCode = bankAccountsRS.getString("ifsc_code");
                double openingBalance = bankAccountsRS.getDouble("opening_balance");
                String branch = bankAccountsRS.getString("branch");
                String state = bankAccountsRS.getString("state");
                String country = bankAccountsRS.getString("country");
                String pinCode = bankAccountsRS.getString("zip_pin_code");
                double totalIndirectIncome = bankAccountsRS.getDouble("total_indirect_income");
                double totalPaymentsMade = bankAccountsRS.getDouble("total_payments_made");
                double totalReceiptsReceived = bankAccountsRS.getDouble("total_receipts_received");
                double totalIncomingAmount = bankAccountsRS.getDouble("total_incoming_amount");
                double totalOutgoingAmount = bankAccountsRS.getDouble("total_outgoing_amount");

                double availableAmount = (openingBalance + totalIndirectIncome + totalReceiptsReceived + totalIncomingAmount) - (totalPaymentsMade + totalOutgoingAmount);

                BankAndCashAccountsModel model = new BankAndCashAccountsModel(bankAccountId, bankName, accountNumber, ifscCode, branch, "BANK", enterpriseId, accountHolder, state, country, pinCode, openingBalance, availableAmount);

                outputList.add(model);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, bankAccountsRS);
            return null;
        }

        databaseFunctions.closeDBOperations(connection, statement, cashAccountsRS);
        databaseFunctions.closeDBOperations(connection, statement, bankAccountsRS);

        return outputList;
    }

}
