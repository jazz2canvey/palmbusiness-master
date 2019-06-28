package nutan.tech.palmbusiness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import nutan.tech.models.CallResultModel;
import nutan.tech.models.CustomerTypeModel;
import nutan.tech.utilities.DatabaseFunctions;

@Path("/customer_type")
public class CustomerType {

	DatabaseFunctions databaseFunctions;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> newCustomerType(CustomerTypeModel customerTypeModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();

		String query = " INSERT INTO palm_business.customer_types_10 (customer_type, customer_type_code)" + " VALUES (?, ?)";
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, customerTypeModel.getCustomer_type());
		    preparedStmt.setInt (2, customerTypeModel.getCustomer_type_code());
				    
		    // execute the preparedstatement
		    preparedStmt.execute();
		    
			callResultModelList.add(new CallResultModel(false, true));
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
		
		} catch (SQLException e) {
			e.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false));
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
		}
		
		return callResultModelList;
	}
	
	@PUT
	@Path("{customer_type_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> modifyCustomerType(@PathParam("customer_type_id") int customerTypeId, CustomerTypeModel customerTypeModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE palm_business.customer_types_10 SET customer_type = ?, customer_type_code = ? WHERE customer_types_10.customer_type_id = " + customerTypeId;
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, customerTypeModel.getCustomer_type());
		    preparedStmt.setInt (2, customerTypeModel.getCustomer_type_code());
				    
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
	@Path("{customer_type_id}")
	public List<CallResultModel> deleteCustomerType(@PathParam("customer_type_id") int customerTypeId) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFunctions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "DELETE FROM palm_business.customer_types_10 WHERE customer_types_10.customer_type_id = ?";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setInt (1, customerTypeId);
				    
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
