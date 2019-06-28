package nutan.tech.palmbusiness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nutan.tech.creator.ExpenseCreator;
import nutan.tech.listmodel.ExpensesListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.ExpensePaymentModel;
import nutan.tech.models.ExpensesModel;
import nutan.tech.utilities.APIUtilities;
import nutan.tech.utilities.DatabaseFunctions;

@Path("/expense")
@Produces(MediaType.APPLICATION_JSON)
public class Expense {

	DatabaseFunctions databaseFunctions;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> newExpense(final ExpensesListModel expensesList) {

			final List<CallResultModel> callResultModelList = new ArrayList<>();
			List<ExpensesModel> expensesModelList = ExpenseCreator.getExpenses();
			expensesModelList.addAll(expensesList.getExpensesList());

			databaseFunctions = new DatabaseFunctions();
			Connection connection = databaseFunctions.connect2DB();
			PreparedStatement preparedStmt = null;

			String query = "INSERT INTO db_palm_business.expenses (expense_id, enterprise_id, entry_date, expense_type_code, expense_head, expense_name, amount, description)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			try {
				preparedStmt = connection.prepareStatement(query);
				connection.setAutoCommit(false);

				for (ExpensesModel expensesModel : expensesModelList) {

					String expenseId = "EXP" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);
					preparedStmt.setString(1, expenseId);
					preparedStmt.setString(2, expensesModel.getEnterprise_id());
					preparedStmt.setString(3, expensesModel.getEntry_date());
					preparedStmt.setInt(4, expensesModel.getExpense_type_code());
					preparedStmt.setString(5, expensesModel.getExpense_head());
					preparedStmt.setString(6, expensesModel.getExpense_name());
					preparedStmt.setDouble(7, expensesModel.getAmount());
					preparedStmt.setString(8, expensesModel.getDescription());
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
	public List<ExpensesModel> getAllExpenses(@PathParam("enterprise_id") String enterpriseId) {
		
		List<ExpensesModel> expensesList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		Statement statement = null;
		ResultSet resultSet = null;

		String query = "SELECT db_palm_business.expenses.expense_id, db_palm_business.expenses.enterprise_id, db_palm_business.expenses.entry_date, db_palm_business.expenses.expense_type_code, db_palm_business.expense_types.expense_title, db_palm_business.expenses.expense_head, db_palm_business.expenses.expense_name, db_palm_business.expenses.amount, db_palm_business.expenses.description FROM db_palm_business.expenses LEFT JOIN expense_types ON db_palm_business.expenses.expense_type_code = db_palm_business.expense_types.expense_type_code WHERE db_palm_business.expenses.enterprise_id = '" + enterpriseId + "' ORDER BY db_palm_business.expenses.entry_date";

	    try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
		
			while (resultSet.next())
			  {
				String expenseId = resultSet.getString("expense_id");
				String entryDate = resultSet.getString("entry_date");
				int expenseTypeCode = resultSet.getInt("expense_type_code");
				String expenseTitle = resultSet.getString("expense_title");
				String expenseHead = resultSet.getString("expense_head");
				String expenseName = resultSet.getString("expense_name");
				double amount = resultSet.getDouble("amount");
				String description = resultSet.getString("description");

				ExpensesModel model = new ExpensesModel(expenseTypeCode, expenseId, enterpriseId, entryDate, expenseTitle, expenseHead, expenseName, amount, description);
				expensesList.add(model);
			  }
		
			databaseFunctions.closeDBOperations(connection, statement, resultSet);

			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}

		return expensesList;
	}
	
	@GET
	@Path("{enterprise_id}/{expense_type}")
	public List<ExpensesModel> getAllExpensesByType(@PathParam("enterprise_id") String enterpriseId, @PathParam("expense_type") int expenseType) {
		
		List<ExpensesModel> expensesList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		Statement statement = null;
		ResultSet resultSet = null;

		String query = "SELECT db_palm_business.expenses.expense_id, db_palm_business.expenses.enterprise_id, db_palm_business.expenses.entry_date, db_palm_business.expenses.expense_type_code, db_palm_business.expense_types.expense_title, db_palm_business.expenses.expense_head, db_palm_business.expenses.expense_name, db_palm_business.expenses.amount, db_palm_business.expenses.description FROM db_palm_business.expenses LEFT JOIN expense_types ON db_palm_business.expenses.expense_type_code = db_palm_business.expense_types.expense_type_code WHERE db_palm_business.expenses.enterprise_id = '" + enterpriseId + "' AND db_palm_business.expenses.expense_type_code = " + expenseType + " ORDER BY db_palm_business.expenses.entry_date";

	    try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
		
			while (resultSet.next())
			  {
				String expenseId = resultSet.getString("expense_id");
				String entryDate = resultSet.getString("entry_date");
				int expenseTypeCode = resultSet.getInt("expense_type_code");
				String expenseTitle = resultSet.getString("expense_title");
				String expenseHead = resultSet.getString("expense_head");
				String expenseName = resultSet.getString("expense_name");
				double amount = resultSet.getDouble("amount");
				String description = resultSet.getString("description");

				ExpensesModel model = new ExpensesModel(expenseTypeCode, expenseId, enterpriseId, entryDate, expenseTitle, expenseHead, expenseName, amount, description);
				expensesList.add(model);
			  }
		
			databaseFunctions.closeDBOperations(connection, statement, resultSet);

			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}

		return expensesList;
	}

	@GET
	@Path("/expense_payment/{enterprise_id}")
	public List<ExpensePaymentModel> getAllExpensesPayments(@PathParam("enterprise_id") String enterpriseId) {

		List<ExpensePaymentModel> expensePaymentList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		Statement statement = null;
		ResultSet resultSet = null;

		String query = "SELECT expenses.expense_id, payments_receipts.payment_receipt_id, expenses.enterprise_id, expenses.entry_date, expenses.expense_type_code, expenses.expense_head, expenses.expense_name, expenses.amount, COALESCE(SUM(payments_receipts.amount), 0) AS paid_amount, (expenses.amount - COALESCE(SUM(payments_receipts.amount), 0)) AS remaining_amount, expenses.description FROM expenses LEFT JOIN payments_receipts ON expenses.expense_id = payments_receipts.vendor_customer WHERE expenses.enterprise_id = '" + enterpriseId + "' GROUP BY expenses.expense_id";

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next())
			{
				String expenseId = resultSet.getString("expense_id");
				String paymentReceiptId = resultSet.getString("payment_receipt_id");
				enterpriseId = resultSet.getString("enterprise_id");
				String entryDate = resultSet.getString("entry_date");
				int expenseTypeCode = resultSet.getInt("expense_type_code");
				String expenseHead = resultSet.getString("expense_head");
				String expenseName = resultSet.getString("expense_name");
				double amount = resultSet.getDouble("amount");
				double paidAmount = resultSet.getDouble("paid_amount");
				double remainingAmount = resultSet.getDouble("remaining_amount");
				String description = resultSet.getString("description");

				ExpensePaymentModel model = new ExpensePaymentModel(expenseId, paymentReceiptId, enterpriseId, entryDate, expenseHead, expenseName, description, expenseTypeCode, amount, paidAmount, remainingAmount);
				expensePaymentList.add(model);
			}

			databaseFunctions.closeDBOperations(connection, statement, resultSet);

			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}

		return expensePaymentList;
	}

	@PUT
	@Path("{enterprise_id}/{expense_id}")
	public List<CallResultModel> updateExpenses(@PathParam("enterprise_id") String enterpriseId, @PathParam("expense_id") String expenseId, ExpensesModel expensesModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE db_palm_business.expenses SET db_palm_business.expenses.enterprise_id = ?, db_palm_business.expenses.entry_date = ?, db_palm_business.expenses.expense_type_code = ?, db_palm_business.expenses.expense_head = ?, db_palm_business.expenses.expense_name = ?, expenses.amount = ?, expenses.description = ? WHERE expenses.enterprise_id = '" + enterpriseId + "' and  expenses.expense_id = '" + expenseId + "'";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, expensesModel.getEnterprise_id());
		    preparedStmt.setString (2, expensesModel.getEntry_date());
			preparedStmt.setInt (3, expensesModel.getExpense_type_code());
		    preparedStmt.setString (4, expensesModel.getExpense_head());
		    preparedStmt.setString (5, expensesModel.getExpense_name());
			preparedStmt.setDouble (6, expensesModel.getAmount());
		    preparedStmt.setString (7, expensesModel.getDescription());

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
	@Path("{enterprise_id}/{expense_id}")
	public List<CallResultModel> deleteCustomer(@PathParam("enterprise_id") String enterpriseId, @PathParam("expense_id") String expenseId) throws SQLException {

		int expensesPaidCounts = 0;
		double totalAmountPaid = 0;

		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();

		String query = "SELECT db_palm_business.payments_receipts.payment_receipt_id, db_palm_business.payments_receipts.amount FROM db_palm_business.expenses LEFT JOIN db_palm_business.payments_receipts ON db_palm_business.expenses.expense_id = db_palm_business.payments_receipts.vendor_customer WHERE db_palm_business.expenses.expense_id = '" + expenseId + "'";

		Statement statement = null;
		ResultSet resultset = null;
		PreparedStatement preparedStmt = null;

		try {

			statement = connection.createStatement();
			resultset = statement.executeQuery(query);

			if (resultset.isBeforeFirst()) {

				while (resultset.next()) {

					String paymentReceiptId = resultset.getString("payment_receipt_id");
					double amount = resultset.getDouble("amount");

					if (paymentReceiptId != null)
						expensesPaidCounts++;

					totalAmountPaid = totalAmountPaid + amount;
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultset);
			return null;
		}

		if (expensesPaidCounts > 0 || totalAmountPaid > 0) {

			databaseFunctions.closeDBOperations(connection, statement, resultset);
			statement.close();
			callResultModelList.add(new CallResultModel(true, false, "Cannot delete this expense due to a amount of " + totalAmountPaid + " has been already paid."));
			return callResultModelList;
		}

		try {

			query = "DELETE FROM db_palm_business.expenses WHERE expenses.enterprise_id = ? AND expenses.expense_id =  ?";
			preparedStmt = connection.prepareStatement(query);

			preparedStmt.setString (1, enterpriseId);
			preparedStmt.setString (2, expenseId);

			preparedStmt.executeUpdate();
			callResultModelList.add(new CallResultModel(false, true, "Deleted Successfully..."));

		} catch (SQLException e) {
			e.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false, "Failed to Delete Expense Entry."));
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
		}

		return callResultModelList;
	}
	
}
