package nutan.tech.palmbusiness;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import nutan.tech.creator.SoldItemsCreator;
import nutan.tech.listmodel.SoldItemsListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.SoldItemsModel;
import nutan.tech.utilities.DatabaseFunctions;

@Path("/sold_item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SoldItems {

	DatabaseFunctions databaseFunctions;
	
	@POST
	public List<CallResultModel> newSoldItem(SoldItemsListModel soldItemsList) {

		List<CallResultModel> callResultModelList = new ArrayList<>();
		List<SoldItemsModel> soldItemsModelList = SoldItemsCreator.getSoldItems();
		soldItemsModelList.addAll(soldItemsList.getSoldItemsList());

		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();

		PreparedStatement preparedStmt;

		String query = "INSERT INTO db_palm_business.sold_items (item_id, enterprise_id, customer_id, invoice_number, item_type_code, sale_price, quantity, measurement, sgst, cgst, igst, discount_type_code, given_discount, total_amount, hsn_o_sac)"
		        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			preparedStmt = connection.prepareStatement(query);
			connection.setAutoCommit(false);

			for (SoldItemsModel soldItemsModel : soldItemsModelList) {

				preparedStmt.setString(1, soldItemsModel.getItem_id());
				preparedStmt.setString(2, soldItemsModel.getEnterprise_id());
				preparedStmt.setString(3, soldItemsModel.getCustomer_id());
				preparedStmt.setString(4, soldItemsModel.getInvoice_number());
				preparedStmt.setInt(5, soldItemsModel.getItem_type_code());
				preparedStmt.setDouble(6, soldItemsModel.getSale_price());
				preparedStmt.setDouble(7, soldItemsModel.getQuantity());
				preparedStmt.setString(8, soldItemsModel.getMeasurement());
				preparedStmt.setDouble(9, soldItemsModel.getSgst());
				preparedStmt.setDouble(10, soldItemsModel.getCgst());
				preparedStmt.setDouble(11, soldItemsModel.getIgst());
				preparedStmt.setInt(12, soldItemsModel.getDiscount_type_code());
				preparedStmt.setDouble(13, soldItemsModel.getGiven_discount());
				preparedStmt.setDouble(14, soldItemsModel.getTotal_amount());
				preparedStmt.setString(15, soldItemsModel.getHsn_o_sac());
				preparedStmt.addBatch();
			}

			preparedStmt.executeBatch();
			connection.commit();
			callResultModelList.add(new CallResultModel(false, true));
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);

		} catch (SQLException e) {
			e.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false));
		}

		return callResultModelList;
	}

	@PUT
	@Path("{enterprise_id}/{sr_no}")
	public List<CallResultModel> modifySoldItem (@PathParam("enterprise_id") String enterpriseId, @PathParam("sr_no") int srNo, SoldItemsModel soldItemsModel) {

		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();

		String query = "UPDATE db_palm_business.sold_items SET item_id = ?, customer_id = ?, invoice_number = ?, item_type_code = ?, sale_price = ?, quantity = ?, measurement = ?, sgst = ?, cgst = ?, igst = ?, discount_type_code = ?, given_discount = ?, total_amount = ?, hsn_o_sac = ? WHERE db_palm_business.sold_items.enterprise_id = '" + enterpriseId + "' AND db_palm_business.sold_items.sr_no = " + srNo;

		PreparedStatement preparedStmt = null;

		try {
			preparedStmt = connection.prepareStatement(query);

			preparedStmt.setString (1, soldItemsModel.getItem_id());
			preparedStmt.setString (2, soldItemsModel.getCustomer_id());
			preparedStmt.setString (3, soldItemsModel.getInvoice_number());
			preparedStmt.setInt (4, soldItemsModel.getItem_type_code());
			preparedStmt.setDouble (5, soldItemsModel.getSale_price());
			preparedStmt.setDouble (6, soldItemsModel.getQuantity());
			preparedStmt.setString (7, soldItemsModel.getMeasurement());
			preparedStmt.setDouble (8, soldItemsModel.getSgst());
			preparedStmt.setDouble (9, soldItemsModel.getCgst());
			preparedStmt.setDouble (10, soldItemsModel.getIgst());
			preparedStmt.setInt (11, soldItemsModel.getDiscount_type_code());
			preparedStmt.setDouble (12, soldItemsModel.getGiven_discount());
			preparedStmt.setDouble (13, soldItemsModel.getTotal_amount());
			preparedStmt.setString (14, soldItemsModel.getHsn_o_sac());

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
	@Path("{enterprise_id}/{invoice_number}")
	public List<SoldItemsModel> getItemsByEnterpriseAndInvoiceNumber(@PathParam("enterprise_id") String enterpriseId, @PathParam("invoice_number") String invoiceNumber) {

		List<SoldItemsModel> soldItemsList = new ArrayList<>();

		databaseFunctions = new DatabaseFunctions();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = databaseFunctions.connect2DB();

		String query = "SELECT db_palm_business.sold_items.item_id, db_palm_business.items.item_name, db_palm_business.sold_items.enterprise_id, db_palm_business.sold_items.customer_id, db_palm_business.sold_items.invoice_number, db_palm_business.item_type.item_type, db_palm_business.sold_items.item_type_code, db_palm_business.sold_items.sale_price, db_palm_business.sold_items.quantity, db_palm_business.sold_items.measurement, db_palm_business.sold_items.sgst, db_palm_business.sold_items.cgst, db_palm_business.sold_items.igst, db_palm_business.discount_types.discount_type, db_palm_business.sold_items.discount_type_code, db_palm_business.sold_items.given_discount, db_palm_business.sold_items.total_amount, db_palm_business.sold_items.hsn_o_sac FROM db_palm_business.sold_items LEFT JOIN db_palm_business.items ON db_palm_business.sold_items.item_id = db_palm_business.items.item_id LEFT JOIN db_palm_business.item_type ON db_palm_business.sold_items.item_type_code = db_palm_business.item_type.item_type_code LEFT JOIN db_palm_business.discount_types ON db_palm_business.sold_items.discount_type_code = db_palm_business.discount_types.discount_type_code WHERE db_palm_business.sold_items.enterprise_id = '" + enterpriseId + "' AND db_palm_business.sold_items.invoice_number = '" + invoiceNumber + "'";

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			if (resultSet.isBeforeFirst()) {

				while (resultSet.next()) {

					String itemId = resultSet.getString("item_id");
					String itemName = resultSet.getString("item_name");
					String customerId = resultSet.getString("customer_id");
					String itemType = resultSet.getString("item_type");
					int itemTypeCode = resultSet.getInt("item_type_code");
					double salePrice = resultSet.getDouble("sale_price");
					double quantity = resultSet.getDouble("quantity");
					String measurement = resultSet.getString("measurement");
					double sgst = resultSet.getDouble("sgst");
					double cgst = resultSet.getDouble("cgst");
					double igst = resultSet.getDouble("igst");
					String discountType = resultSet.getString("discount_type");
					int discountTypeCode = resultSet.getInt("discount_type_code");
					double givenDiscount = resultSet.getDouble("given_discount");
					String hsnSac = resultSet.getString("hsn_o_sac");
					double totalAmount = resultSet.getDouble("total_amount");

					SoldItemsModel soldItemsModel = new SoldItemsModel(itemTypeCode, discountTypeCode, salePrice, quantity, sgst, cgst, igst, givenDiscount, totalAmount, itemId, itemName, itemType, discountType, enterpriseId, customerId, invoiceNumber, measurement, hsnSac);
					soldItemsList.add(soldItemsModel);
				}
			}

			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}

		return soldItemsList;
	}

	@DELETE
	@Path("{enterprise_id}/{sr_no}")
	public List<CallResultModel> modifySoldItem (@PathParam("enterprise_id") String enterpriseId, @PathParam("sr_no") int srNo) {

		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();

		String query = "DELETE FROM db_palm_business.sold_items WHERE db_palm_business.sold_items.enterprise_id =  ? AND db_palm_business.sold_items.sr_no = ?";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);

			preparedStmt.setString (1, enterpriseId);
			preparedStmt.setInt (2, srNo);

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
