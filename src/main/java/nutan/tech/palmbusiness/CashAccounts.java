package nutan.tech.palmbusiness;

import nutan.tech.models.BankAndCashAccountsModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.CashAccountModel;
import nutan.tech.utilities.APIUtilities;
import nutan.tech.utilities.DatabaseFunctions;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Path("/cash_accounts")
public class CashAccounts {

    DatabaseFunctions databaseFunctions;

    private boolean isExist = false;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public List<CallResultModel> cashAccountCreateModify(final CashAccountModel cashAccountModel) throws InterruptedException {

        final List<CallResultModel> callResultModelList = new ArrayList<>();

        Thread isExistThread = new Thread(new Runnable() {

            @Override
            public void run() {

                databaseFunctions = new DatabaseFunctions();
                Connection connection = databaseFunctions.connect2DB();
                String getQuery = "SELECT * FROM db_palm_business.cash_accounts WHERE db_palm_business.cash_accounts.enterprise_id = '" + cashAccountModel.getEnterprise_id() + "'";

                try {
                    Statement st = connection.createStatement();
                    ResultSet resultSet = st.executeQuery(getQuery);

                    isExist = resultSet.isBeforeFirst();

                    databaseFunctions.closeDBOperations(connection, st, null);

                } catch (SQLException e) {
                    e.printStackTrace();
                    callResultModelList.add(new CallResultModel(true, false, "Failed to Create Item, Try Again"));
                }
            }
        });

        Thread insert2Database = new Thread(
                new Runnable() {

                    @Override
                    public void run() {

                        databaseFunctions = new DatabaseFunctions();
                        Connection connection = databaseFunctions.connect2DB();
                        String query = "INSERT INTO db_palm_business.cash_accounts (cash_account_id, enterprise_id, account_holder, locality, state, country, zip_pin_code, opening_balance)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                        PreparedStatement preparedStmt = null;
                        try {
                            preparedStmt = connection.prepareStatement(query);

                            String cashAccountId = "CAC" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);
                            preparedStmt.setString(1, cashAccountId);
                            preparedStmt.setString (2, cashAccountModel.getEnterprise_id());
                            preparedStmt.setString (3, cashAccountModel.getAccount_holder());
                            preparedStmt.setString(4, cashAccountModel.getLocality());
                            preparedStmt.setString (5, cashAccountModel.getState());
                            preparedStmt.setString (6, cashAccountModel.getCountry());
                            preparedStmt.setString (7, cashAccountModel.getZip_pin_code());
                            preparedStmt.setDouble (8, cashAccountModel.getOpening_balance());

                            // execute the preparedstatement
                            preparedStmt.execute();

                            String getQuery = "SELECT db_palm_business.cash_accounts.cash_account_id FROM db_palm_business.cash_accounts WHERE db_palm_business.cash_accounts.enterprise_id = '" + cashAccountModel.getEnterprise_id() + "' AND db_palm_business.cash_accounts.cash_account_id = '" + cashAccountId + "'";

                            Statement st = connection.createStatement();
                            ResultSet rs = st.executeQuery(getQuery);

                            while (rs.next()) {

                                callResultModelList.add(new CallResultModel(false, true, "Cash Account has been Setup Successfully..."));
                            }

                            st.close();
                            databaseFunctions.closeDBOperations(connection, preparedStmt, null);

                        } catch (SQLException e) {
                            e.printStackTrace();

                            callResultModelList.add(new CallResultModel(true, false, "Failed to Setup Bank Account."));
                            databaseFunctions.closeDBOperations(connection, preparedStmt, null);
                        }

                    }
                });

        Thread update2Database = new Thread(
                new Runnable() {

                    @Override
                    public void run() {

                        databaseFunctions = new DatabaseFunctions();
                        Connection connection = databaseFunctions.connect2DB();

                        String query = "UPDATE db_palm_business.cash_accounts SET enterprise_id = ?, account_holder = ?, locality = ?, state = ?, country = ?, zip_pin_code = ?, opening_balance = ? WHERE db_palm_business.cash_accounts.enterprise_id = '" + cashAccountModel.getEnterprise_id() + "'";

                        PreparedStatement preparedStmt = null;
                        try {
                            preparedStmt = connection.prepareStatement(query);

                            preparedStmt.setString (1, cashAccountModel.getEnterprise_id());
                            preparedStmt.setString (2, cashAccountModel.getAccount_holder());
                            preparedStmt.setString (3, cashAccountModel.getLocality());
                            preparedStmt.setString (4, cashAccountModel.getState());
                            preparedStmt.setString (5, cashAccountModel.getCountry());
                            preparedStmt.setString (6, cashAccountModel.getZip_pin_code());
                            preparedStmt.setDouble (7, cashAccountModel.getOpening_balance());

                            // execute the preparedstatement
                            preparedStmt.executeUpdate();

                            callResultModelList.add(new CallResultModel(false, true));

                        } catch (SQLException e) {
                            e.printStackTrace();
                            callResultModelList.add(new CallResultModel(true, false));
                            databaseFunctions.closeDBOperations(connection, preparedStmt, null);
                        }

                    }
                });

        isExistThread.start();
        isExistThread.join();

        if (isExist) {

            update2Database.start();
            update2Database.join();

        } else {

            insert2Database.start();
            insert2Database.join();
        }

        return callResultModelList;
    }

    @POST
    @Path("{enterprise_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<BankAndCashAccountsModel> cashAccountCreateGet(final CashAccountModel cashAccountModel, @PathParam("enterprise_id") final String enterpriseId) throws InterruptedException {

    		final List<BankAndCashAccountsModel> bankAndCashAccountsModelList = new ArrayList<>();

        Thread isExistThread = new Thread(new Runnable() {

            @Override
            public void run() {

                String cashAccountId = null;
                databaseFunctions = new DatabaseFunctions();
                Connection connection = databaseFunctions.connect2DB();

                String getQuery = "SELECT db_palm_business.cash_accounts.cash_account_id FROM db_palm_business.cash_accounts WHERE db_palm_business.cash_accounts.enterprise_id = '" + enterpriseId + "'";
                
                try {
                    Statement st = connection.createStatement();
                    ResultSet resultSet = st.executeQuery(getQuery);

                    isExist = resultSet.isBeforeFirst();

                    while (resultSet.next())
                    {
                        cashAccountId = resultSet.getString("cash_account_id");
                    }

//                    String resultQuery = "SELECT db_palm_business.cash_accounts.cash_account_id, db_palm_business.cash_accounts.enterprise_id, db_palm_business.cash_accounts.account_holder, db_palm_business.cash_accounts.locality, db_palm_business.cash_accounts.state, db_palm_business.cash_accounts.country, db_palm_business.cash_accounts.zip_pin_code, db_palm_business.cash_accounts.opening_balance, (SELECT SUM(db_palm_business.indirect_income.amount) FROM db_palm_business.indirect_income WHERE db_palm_business.indirect_income.payment_mode_code = 0 AND db_palm_business.indirect_income.enterprise_id = '" + enterpriseId + "') AS total_indirect_income, (SELECT SUM(payments_receipts.amount) FROM payments_receipts WHERE payments_receipts.bank_payment_mode = 0 AND payments_receipts.enterprise_id = '" + enterpriseId + "' AND payments_receipts.party_code = 2) AS total_receipt_amount, (SELECT SUM(contra_voucher.amount) FROM contra_voucher WHERE contra_voucher.payment_mode = 0 AND contra_voucher.giver = '" + cashAccountId + "' AND contra_voucher.enterprise_id = '" + enterpriseId + "') AS total_contra_amount FROM cash_accounts WHERE cash_accounts.enterprise_id = '" + enterpriseId + "'";
                    String resultQuery = "SELECT cash_accounts.*, (SELECT SUM(indirect_income.amount) FROM indirect_income WHERE indirect_income.enterprise_id = '" + enterpriseId + "' AND indirect_income.credited_account_id = cash_accounts.cash_account_id) AS total_indirect_income, (SELECT SUM(payments_receipts.amount) FROM payments_receipts WHERE payments_receipts.enterprise_id = '" + enterpriseId + "' AND payments_receipts.party_code = '1' AND cash_accounts.cash_account_id = payments_receipts.account_id) AS total_payment, (SELECT SUM(payments_receipts.amount) FROM payments_receipts WHERE payments_receipts.enterprise_id = '" + enterpriseId + "' AND payments_receipts.party_code = '2' AND cash_accounts.cash_account_id = payments_receipts.account_id) AS total_receipt, (SELECT SUM(db_palm_business.contra_voucher.amount) FROM contra_voucher WHERE contra_voucher.enterprise_id = '" + enterpriseId + "' AND contra_voucher.giver = cash_accounts.cash_account_id) AS total_outgoing_amount, (SELECT SUM(db_palm_business.contra_voucher.amount) FROM contra_voucher WHERE contra_voucher.enterprise_id = '" + enterpriseId + "' AND contra_voucher.receiver = cash_accounts.cash_account_id) AS total_incoming_amount FROM cash_accounts WHERE cash_accounts.enterprise_id = '" + enterpriseId + "'";                
                    resultSet = st.executeQuery(resultQuery);

                    while (resultSet.next()) {

/*                        cashAccountId = resultSet.getString("cash_account_id");
                        String enterpriseId = resultSet.getString("enterprise_id");
                        String accountHolder = resultSet.getString("account_holder");
                        String locality = resultSet.getString("locality");
                        String state = resultSet.getString("state");
                        String country = resultSet.getString("country");
                        String pinCode = resultSet.getString("zip_pin_code");
                        double openingBalance = resultSet.getDouble("opening_balance");
                        double totalIndirectIncome = resultSet.getDouble("total_indirect_income");
                        double totalReceiptAmount = resultSet.getDouble("total_receipt_amount");
                        double totalContraAmount = resultSet.getDouble("total_contra_amount");
                        double availableBalance = (totalIndirectIncome + totalReceiptAmount) - totalContraAmount;		*/

                        cashAccountId = resultSet.getString("cash_account_id");
                        String accountHolder = resultSet.getString("account_holder");
                        String locality = resultSet.getString("locality");
                        String state = resultSet.getString("state");
                        String country = resultSet.getString("country");
                        String pinCode = resultSet.getString("zip_pin_code");
                        double openingBalance = resultSet.getDouble("opening_balance");
                        double totalIndirectIncome = resultSet.getDouble("total_indirect_income");
                        double totalPayment = resultSet.getDouble("total_payment");
                        double totalReceipt = resultSet.getDouble("total_receipt");
                        double totalOutGoingAmount = resultSet.getDouble("total_outgoing_amount");
                        double totalInComingAmount = resultSet.getDouble("total_incoming_amount");

                        double availableAmount = (openingBalance + totalIndirectIncome + totalReceipt + totalInComingAmount) - (totalPayment + totalOutGoingAmount);
                    	
                        BankAndCashAccountsModel model = new BankAndCashAccountsModel(cashAccountId, locality, "CASH", enterpriseId, accountHolder, state, country, pinCode, openingBalance, availableAmount);
                        bankAndCashAccountsModelList.add(model);
                    }

                    databaseFunctions.closeDBOperations(connection, st, null);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread insert2Database = new Thread(
                new Runnable() {

                    @Override
                    public void run() {

                        databaseFunctions = new DatabaseFunctions();
                        Connection connection = databaseFunctions.connect2DB();
                        String query = "INSERT INTO db_palm_business.cash_accounts (cash_account_id, enterprise_id, account_holder, locality, state, country, zip_pin_code, opening_balance)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                        PreparedStatement preparedStmt = null;
                        try {
                            preparedStmt = connection.prepareStatement(query);

                            String cashAccountId = "CAC" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);
                            preparedStmt.setString(1, cashAccountId);
                            preparedStmt.setString (2, cashAccountModel.getEnterprise_id());
                            preparedStmt.setString (3, cashAccountModel.getAccount_holder());
                            preparedStmt.setString(4, cashAccountModel.getLocality());
                            preparedStmt.setString (5, cashAccountModel.getState());
                            preparedStmt.setString (6, cashAccountModel.getCountry());
                            preparedStmt.setString (7, cashAccountModel.getZip_pin_code());
                            preparedStmt.setDouble (8, cashAccountModel.getOpening_balance());

                            // execute the preparedstatement
                            preparedStmt.execute();

                            String getQuery = "SELECT * FROM db_palm_business.cash_accounts WHERE db_palm_business.cash_accounts.enterprise_id = '" + enterpriseId + "' AND db_palm_business.cash_accounts.cash_account_id = '" + cashAccountId + "'";

                            Statement st = connection.createStatement();
                            ResultSet resultSet = st.executeQuery(getQuery);

                            while (resultSet.next())
                            {
                                String accountId = resultSet.getString("cash_account_id");
                                String enterpriseId = resultSet.getString("enterprise_id");
                                String accountHolder = resultSet.getString("account_holder");
                                String locality = resultSet.getString("locality");
                                String state = resultSet.getString("state");
                                String country = resultSet.getString("country");
                                String pinCode = resultSet.getString("zip_pin_code");
                                double openingBalance = resultSet.getDouble("opening_balance");

                                BankAndCashAccountsModel bankAndCashAccountsModel = new BankAndCashAccountsModel(accountId, locality, null, enterpriseId, accountHolder, state, country, pinCode, openingBalance, 0);
                                bankAndCashAccountsModelList.add(bankAndCashAccountsModel);
                            }

                            st.close();
                            databaseFunctions.closeDBOperations(connection, preparedStmt, null);

                        } catch (SQLException e) {
                            e.printStackTrace();

                            databaseFunctions.closeDBOperations(connection, preparedStmt, null);
                        }

                    }
                });

        isExistThread.start();
        isExistThread.join();

        if (!isExist) {

            insert2Database.start();
            insert2Database.join();
        }

        return bankAndCashAccountsModelList;
    }
    
    @GET
    @Path("{enterprise_id}")
    public CashAccountModel getCashAccount(@PathParam("enterprise_id") String enterpriseId) {

        CashAccountModel cashAccountModel = null;
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM db_palm_business.cash_accounts WHERE db_palm_business.cash_accounts.enterprise_id = '" + enterpriseId + "'";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                String cashAccountId = resultSet.getString("cash_account_id");
                String accountHolder = resultSet.getString("account_holder");
                String locality = resultSet.getString("locality");
                String state = resultSet.getString("state");
                String country = resultSet.getString("country");
                String pinCode = resultSet.getString("zip_pin_code");
                double openingBalance = resultSet.getDouble("opening_balance");

                cashAccountModel = new CashAccountModel(cashAccountId, enterpriseId, accountHolder, locality, state, country, pinCode, openingBalance);
            }

            databaseFunctions.closeDBOperations(connection, statement, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return cashAccountModel;
    }

}
