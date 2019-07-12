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

import nutan.tech.models.CallResultModel;
import nutan.tech.models.CreateItemModel;
import nutan.tech.models.ItemsModel;
import nutan.tech.utilities.APIUtilities;
import nutan.tech.utilities.DatabaseFunctions;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
public class Items {

	private DatabaseFunctions databaseFunctions;
	
	@POST
	@Path("/create_item")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> createItem(final CreateItemModel createItemModel) throws InterruptedException {

		final List<CallResultModel> callResultModelList = new ArrayList<>();

		Thread isExistThread = new Thread(new Runnable() {

			@Override
			public void run() {

				databaseFunctions = new DatabaseFunctions();
				Connection connection = databaseFunctions.connect2DB();
				String getQuery = "SELECT db_palm_business.items.item_name FROM db_palm_business.items WHERE db_palm_business.items.item_name = '" + createItemModel.getItem_name() + "'";

				try {
					String itemName = null;
					Statement st = connection.createStatement();
					ResultSet resultSet = st.executeQuery(getQuery);

					while (resultSet.next()) {

						itemName = resultSet.getString("item_name");
					}

					st.close();

					databaseFunctions.closeDBOperations(connection, st, null);

					if (itemName != null && itemName.matches(createItemModel.getItem_name())) {
						callResultModelList.add(new CallResultModel(true, false, "Item Already Exist"));
						return;
					}

				} catch (SQLException e) {
					e.printStackTrace();
					callResultModelList.add(new CallResultModel(true, false, "Failed to Create Item, Try Again"));
					return;
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
						String query = "INSERT INTO db_palm_business.items (item_id, enterprise_id, item_name, measurement, item_type_code, item_description)"
								+ " VALUES (?, ?, ?, ?, ?, ?)";

						PreparedStatement preparedStmt = null;
						try {
							preparedStmt = connection.prepareStatement(query);

							String itemId = "ITM" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);
							preparedStmt.setString(1, itemId);
							preparedStmt.setString (2, createItemModel.getEnterprise_id());
							preparedStmt.setString (3, createItemModel.getItem_name());
							preparedStmt.setString(4, createItemModel.getMeasurement());;
							preparedStmt.setInt (5, createItemModel.getItem_type_code());
							preparedStmt.setString (6, createItemModel.getItem_description());

							// execute the preparedstatement
							preparedStmt.execute();

							String getQuery = "SELECT db_palm_business.items.item_id FROM db_palm_business.items WHERE db_palm_business.items.item_id = '" + itemId + "'";

							Statement st = connection.createStatement();
							ResultSet rs = st.executeQuery(getQuery);

							while (rs.next()) {

								callResultModelList.add(new CallResultModel(false, true, "Item Created Successfully..."));
							}
							st.close();

							databaseFunctions.closeDBOperations(connection, preparedStmt, null);

						} catch (SQLException e) {
							e.printStackTrace();
							if (e.toString().contains("Duplicate entry")) {

								callResultModelList.add(new CallResultModel(true, false, "Item Already Exist..."));
								databaseFunctions.closeDBOperations(connection, preparedStmt, null);
							} else {

								callResultModelList.add(new CallResultModel(true, false, e.toString()));
								databaseFunctions.closeDBOperations(connection, preparedStmt, null);
							}
						}

					}
				});

		isExistThread.start();
		isExistThread.join();

		if (callResultModelList.isEmpty()) {

			insert2Database.start();
			insert2Database.join();

		}

		return callResultModelList;

	}

	@PUT
	@Path("/created_item/{enterprise_id}/{item_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> modifyCreatedItem(@PathParam("enterprise_id") String enterpriseId, @PathParam("item_id") String itemId, CreateItemModel createItemModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE db_palm_business.items SET item_name = ?, measurement = ?, item_type_code = ?, item_description = ? WHERE db_palm_business.items.enterprise_id = '" + enterpriseId + "' AND db_palm_business.items.item_id = '" + itemId + "'";
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, createItemModel.getItem_name());
			preparedStmt.setString (2, createItemModel.getMeasurement());
			preparedStmt.setInt (3, createItemModel.getItem_type_code());
			preparedStmt.setString (4, createItemModel.getItem_description());

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
	
	@PUT
	@Path("{enterprise_id}/{item_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> modifyItem(@PathParam("enterprise_id") String enterpriseId, @PathParam("item_id") String itemId, ItemsModel itemsModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE db_palm_business.items SET item_name = ?, measurement = ?, item_type_code = ?, item_description = ? WHERE db_palm_business.items.enterprise_id = '" + enterpriseId + "' AND db_palm_business.items.item_id = '" + itemId + "'";
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, itemsModel.getItem_name());
			preparedStmt.setString (2, itemsModel.getMeasurement());
			preparedStmt.setInt (3, itemsModel.getItem_type_code());
			preparedStmt.setString (4, itemsModel.getItem_description());

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
	@Path("/items_available/{enterprise_id}")
	public List<CreateItemModel> getAllItemsWithQuantity(@PathParam("enterprise_id") String enterpriseId) {
		
		List<CreateItemModel> createItemList = new ArrayList<>();
		
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		Statement statement = null;
		ResultSet resultSet = null;

	    String query = "SELECT items.item_id, items.item_name, items.measurement, items.item_type_code, items.item_description, (SELECT SUM(purchased_items.quantity) FROM purchased_items WHERE items.item_id = purchased_items.item_id AND purchased_items.enterprise_id = '" + enterpriseId + "') purchased_quantity, (SELECT SUM(sold_items.quantity) FROM sold_items WHERE items.item_id = sold_items.item_id AND sold_items.enterprise_id = '" + enterpriseId + "') sold_quantity, ((SELECT SUM(purchased_items.quantity) FROM purchased_items WHERE items.item_id = purchased_items.item_id AND purchased_items.enterprise_id = '" + enterpriseId + "') - (SELECT SUM(sold_items.quantity) FROM sold_items WHERE items.item_id = sold_items.item_id AND sold_items.enterprise_id = '" + enterpriseId + "')) available_quantity FROM items WHERE items.enterprise_id = '" + enterpriseId + "'";

	    try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
		
			while (resultSet.next())
			  {

			  	String itemId = resultSet.getString("item_id");
			  	String itemName = resultSet.getString("item_name");
			  	String measurement = resultSet.getString("measurement");
			  	int itemTypeCode = resultSet.getInt("item_type_code");
			  	String itemDescription = resultSet.getString("item_description");
			  	double purchasedQuantity = resultSet.getDouble("purchased_quantity");
			  	double soldQuantity = resultSet.getDouble("sold_quantity");
			  	double availableQuantity = resultSet.getDouble("available_quantity");

//	int item_type_code, double purchased_quantity, double sold_quantity, double available_quantity, String item_id, String enterprise_id, String item_name, String measurement, String item_description

				  createItemList.add(new CreateItemModel(itemTypeCode, purchasedQuantity, soldQuantity, availableQuantity, itemId, enterpriseId, itemName, measurement, itemDescription));
			  }

			databaseFunctions.closeDBOperations(connection, statement, resultSet);

			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}

    return createItemList;
	    
	}

	@GET
	@Path("{enterprise_id}")
	public List<ItemsModel> getAllItems(@PathParam("enterprise_id") String enterpriseId) {

		List<ItemsModel> itemsList = new ArrayList<>();

		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		Statement statement = null;
		ResultSet resultSet = null;

//		String query = "SELECT db_palm_business.items.item_id, db_palm_business.items.item_name, db_palm_business.items.measurement, db_palm_business.items.item_type_code, db_palm_business.items.item_description, (SELECT db_palm_business.purchased_items.hsn_o_sac FROM db_palm_business.purchased_items WHERE db_palm_business.items.item_id = db_palm_business.purchased_items.item_id AND db_palm_business.purchased_items.enterprise_id = '" + enterpriseId + "') AS hsn_o_sac, (SELECT SUM(db_palm_business.purchased_items.quantity) FROM db_palm_business.purchased_items WHERE db_palm_business.items.item_id = db_palm_business.purchased_items.item_id AND db_palm_business.purchased_items.enterprise_id = '" + enterpriseId + "') AS purchased_quantity, (SELECT SUM(db_palm_business.sold_items.quantity) FROM db_palm_business.sold_items WHERE items.item_id = sold_items.item_id AND sold_items.enterprise_id = '" + enterpriseId + "') AS sold_quantity FROM items WHERE items.enterprise_id = '" + enterpriseId + "' GROUP BY item_id";

		String query = "SELECT db_palm_business.items.*, (SELECT SUM(db_palm_business.purchased_items.quantity) FROM db_palm_business.purchased_items WHERE db_palm_business.items.item_id = db_palm_business.purchased_items.item_id AND db_palm_business.purchased_items.enterprise_id = '" + enterpriseId + "') AS purchased_quantity, (SELECT SUM(db_palm_business.sold_items.quantity) FROM db_palm_business.sold_items WHERE items.item_id = sold_items.item_id AND sold_items.enterprise_id = '" + enterpriseId + "') AS sold_quantity FROM items WHERE items.enterprise_id = '" + enterpriseId + "' GROUP BY item_id";

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next())
			{
				String itemId = resultSet.getString("item_id");
				String itemName = resultSet.getString("item_name");
				String measurement = resultSet.getString("measurement");
				int itemTypeCode = resultSet.getInt("item_type_code");
				String itemDescription = resultSet.getString("item_description");
//				String codeHsnSac = resultSet.getString("hsn_o_sac");
				double purchasedQuantity = resultSet.getDouble("purchased_quantity");
				double soldQuantity = resultSet.getDouble("sold_quantity");
				double availableQuantity = purchasedQuantity - soldQuantity;

				itemsList.add(new ItemsModel(itemTypeCode, purchasedQuantity, soldQuantity, availableQuantity, itemId, enterpriseId, itemName, measurement, null, itemDescription));
			}

			databaseFunctions.closeDBOperations(connection, statement, resultSet);

			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}

		return itemsList;

	}

	@GET
	@Path("{enterprise_id}/{item_id}")
	public List<ItemsModel> getItem(@PathParam("enterprise_id") String enterpriseId, @PathParam("item_id") String itemId) {
		
		List<ItemsModel> itemsList = new ArrayList<>();
		
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
	    	Statement statement = null;
		ResultSet resultSet = null;

		String query = "SELECT db_palm_business.items.item_id, db_palm_business.items.item_name, db_palm_business.items.measurement, db_palm_business.items.item_type_code, db_palm_business.items.item_description, (SELECT db_palm_business.purchased_items.hsn_o_sac FROM db_palm_business.purchased_items WHERE db_palm_business.items.item_id = db_palm_business.purchased_items.item_id AND db_palm_business.purchased_items.enterprise_id = '" + enterpriseId + "') AS hsn_o_sac, (SELECT SUM(db_palm_business.purchased_items.quantity) FROM db_palm_business.purchased_items WHERE db_palm_business.items.item_id = db_palm_business.purchased_items.item_id AND db_palm_business.purchased_items.enterprise_id = '" + enterpriseId + "') AS purchased_quantity, (SELECT SUM(db_palm_business.sold_items.quantity) FROM db_palm_business.sold_items WHERE db_palm_business.items.item_id = db_palm_business.sold_items.item_id AND db_palm_business.sold_items.enterprise_id = '" + enterpriseId + "') AS sold_quantity FROM db_palm_business.items WHERE db_palm_business.items.enterprise_id = '" + enterpriseId + "'  AND db_palm_business.items.item_id = '" + itemId + "' GROUP BY db_palm_business.items.item_id";

	    try {

	    	statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next())
			{
				String itemName = resultSet.getString("item_name");
				String measurement = resultSet.getString("measurement");
				int itemTypeCode = resultSet.getInt("item_type_code");
				String itemDescription = resultSet.getString("item_description");
				String codeHsnSac = resultSet.getString("hsn_o_sac");
				double purchasedQuantity = resultSet.getDouble("purchased_quantity");
				double soldQuantity = resultSet.getDouble("sold_quantity");
				double availableQuantity = purchasedQuantity - soldQuantity;

				itemsList.add(new ItemsModel(itemTypeCode, purchasedQuantity, soldQuantity, availableQuantity, itemId, enterpriseId, itemName, measurement, codeHsnSac, itemDescription));
			}

			databaseFunctions.closeDBOperations(connection, statement, resultSet);

			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}

		return itemsList;
	    
	}

	@DELETE
	@Path("{enterprise_id}/{item_id}")
	public List<CallResultModel> deleteItem(@PathParam("enterprise_id") String enterpriseId, @PathParam("item_id") String itemId) {

		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();

		String query = "DELETE FROM db_palm_business.items WHERE db_palm_business.items.enterprise_id =  ? AND db_palm_business.items.item_id = ?";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);

			preparedStmt.setString (1, enterpriseId);
			preparedStmt.setString (2, itemId);

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
