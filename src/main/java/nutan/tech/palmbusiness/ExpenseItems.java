package nutan.tech.palmbusiness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nutan.tech.models.CallResultModel;
import nutan.tech.models.ExpenseItemsModel;
import nutan.tech.utilities.DatabaseFunctions;

@Path("/expense_items")
@Produces(MediaType.APPLICATION_JSON)
public class ExpenseItems {

	DatabaseFunctions databaseFunctions;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/add")
	public List<ExpenseItemsModel> newExpenseItem(final ExpenseItemsModel expenseItemsModel) {
		
		final List<ExpenseItemsModel> expenseItemsList = new ArrayList<>();
		
		Thread insert2Database = new Thread(
				new Runnable() {
					
					@Override
					public void run() {
						
						Timestamp lastModified = new Timestamp(new Date().getTime());
						databaseFunctions = new DatabaseFunctions();
						Connection connection = databaseFunctions.connect2DB();
						String query = " insert into palm_business.expense_items_27 (item_name, item_type, spent_price, sgst, cgst, igst, hsn_o_sac, last_modified)"
						        + " values (?, ?, ?, ?, ?, ?, ?, ?)";

						PreparedStatement preparedStmt = null;
						try {
							preparedStmt = connection.prepareStatement(query);
							
						    preparedStmt.setString (1, expenseItemsModel.getItem_name());
						    preparedStmt.setInt (2, expenseItemsModel.getItem_type());
						    preparedStmt.setFloat (3, expenseItemsModel.getSpent_price());
						    preparedStmt.setFloat (4, expenseItemsModel.getSgst());
						    preparedStmt.setFloat (5, expenseItemsModel.getCgst());
						    preparedStmt.setFloat (6, expenseItemsModel.getIgst());
						    preparedStmt.setString (7, expenseItemsModel.getHsn_o_sac());
						    preparedStmt.setTimestamp(8, lastModified);
								    
						    preparedStmt.execute();
						    
						    String[] dateTime = lastModified.toString().split("\\.");
						    
						    String getQuery = "SELECT * FROM palm_business.expense_items_27 where palm_business.expense_items_27.item_name = '" + expenseItemsModel.getItem_name() + "' and palm_business.expense_items_27.last_modified = " + "\"" + dateTime[0] + "\"";
						    
						    Statement st = connection.createStatement();
						    ResultSet resultSet = st.executeQuery(getQuery);
						    
						      while (resultSet.next())
						      {
						    	  	expenseItemsList.add(new ExpenseItemsModel(resultSet.getInt("expense_item_id"), resultSet.getInt("item_type"), resultSet.getFloat("spent_price"), resultSet.getFloat("sgst"), resultSet.getFloat("cgst"), resultSet.getFloat("igst"), resultSet.getString("item_name"), resultSet.getString("hsn_o_sac")));
						      }
						      st.close();
						    
							databaseFunctions.closeDBOperations(connection, preparedStmt, resultSet);
						
						} catch (SQLException e) {
							e.printStackTrace();
							expenseItemsList.add(new ExpenseItemsModel());
							return;
						}
						
					}
				});

			try {
				insert2Database.start();
				insert2Database.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				expenseItemsList.add(new ExpenseItemsModel());
			}
				
		return expenseItemsList;
	}
	
	@PUT
	@Path("{expenses_item_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> modifyExpensesItem(@PathParam("expenses_item_id") int expensesItemId, ExpenseItemsModel expenseItemsModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE palm_business.expense_items_27 SET item_name = ?, item_type = ?, spent_price = ?, sgst = ?, cgst = ?, igst = ?, hsn_o_sac = ? WHERE palm_business.expense_items_27.expense_item_id = " + expensesItemId;
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, expenseItemsModel.getItem_name());
		    preparedStmt.setInt (2, expenseItemsModel.getItem_type());
		    preparedStmt.setFloat (3, expenseItemsModel.getSpent_price());
		    preparedStmt.setFloat (4, expenseItemsModel.getSgst());
		    preparedStmt.setFloat (5, expenseItemsModel.getCgst());
		    preparedStmt.setFloat (6, expenseItemsModel.getIgst());
		    preparedStmt.setString (7, expenseItemsModel.getHsn_o_sac());
				    
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
