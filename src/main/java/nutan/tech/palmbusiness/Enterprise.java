package nutan.tech.palmbusiness;

import nutan.tech.models.*;
import nutan.tech.utilities.APIUtilities;
import nutan.tech.utilities.DatabaseFunctions;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Path("/enterprise_registration")
@Produces(MediaType.APPLICATION_JSON)
public class Enterprise {

    private DatabaseFunctions databaseFunctions;

    @POST
    public List<CallResultModel> registerEnterprise(@FormDataParam("logo") InputStream inputStream,
                                                    @FormDataParam("logo") FormDataContentDisposition contentDisposition,
                                                    @FormDataParam("enterpriseModel") FormDataBodyPart jsonData) {

        final List<CallResultModel> callResultModelList = new ArrayList<>();

        jsonData.setMediaType(MediaType.APPLICATION_JSON_TYPE);
        EnterpriseModel enterpriseModel = jsonData.getValueAs(EnterpriseModel.class);

        final String enterpriseId = "ENP" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);
        String directoryPath;

        if (inputStream != null) {

            String currentDirectory = System.getProperty("user.dir");
            new File(currentDirectory + "/enterprise_logos").mkdirs();
            directoryPath = currentDirectory + "/enterprise_logos/" + enterpriseId + ".png";

//            directoryPath = "http://jws-app-palmbusiness.1d35.starter-us-east-1.openshiftapps.com/" + enterpriseId + ".png";
//            directoryPath = "/Users/jawedtahasildar/eclipse-workspace/palmbusiness/target/" + enterpriseId + ".png";
//            directoryPath = "http://jws-app-palmbusiness.1d35.starter-us-east-1.openshiftapps.com/palmbusiness-master/enterprise_logos/" + enterpriseId + ".png";

            APIUtilities.writeToFile(inputStream, directoryPath);
        } else {

            directoryPath = enterpriseModel.getEnterprise_logo();
        }

        String[] givenFiscal = enterpriseModel.getFiscal_year().split(" ");
        int monthIndex = Arrays.asList(APIUtilities.months).indexOf(givenFiscal[1]);

        enterpriseModel.setFiscal_year("0000" + "-" + (monthIndex + 1) + "-" + givenFiscal[0]);
        enterpriseModel.setEnterprise_logo(directoryPath);

        final EnterpriseModel model = enterpriseModel;

        Thread isExistThread = new Thread(new Runnable() {

            @Override
            public void run() {

                databaseFunctions = new DatabaseFunctions();
                Connection connection = databaseFunctions.connect2DB();
                Statement statement = null;
                ResultSet resultSet = null;
                String getQuery = "SELECT db_palm_business.enterprises.username FROM db_palm_business.enterprises WHERE db_palm_business.enterprises.username = '" + model.getUsername() + "'";

                try {

                    statement = connection.createStatement();
                    resultSet = statement.executeQuery(getQuery);

                    if (resultSet.isBeforeFirst()) {

                        callResultModelList.add(new CallResultModel(true, false, "Username (EmailId Main) is Already Exist."));
                        databaseFunctions.closeDBOperations(connection, statement, resultSet);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    callResultModelList.add(new CallResultModel(true, false, "Oops! Something Went Wrong, Try Again"));
                    databaseFunctions.closeDBOperations(connection, statement, resultSet);
                }

            }
        });

        Thread insert2Database = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                databaseFunctions = new DatabaseFunctions();
                Connection connection = databaseFunctions.connect2DB();
                String query = "INSERT INTO db_palm_business.enterprises (enterprise_id, enterprise_logo, enterprise_name, fiscal_year, username, password)"
                        + " VALUES (?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStmt = null;
                try {
                    preparedStmt = connection.prepareStatement(query);

                    preparedStmt.setString(1, enterpriseId);
                    preparedStmt.setString (2, model.getEnterprise_logo());
                    preparedStmt.setString (3, model.getEnterprise_name());
                    preparedStmt.setString (4, model.getFiscal_year());
                    preparedStmt.setString (5, model.getUsername());
                    preparedStmt.setString (6, model.getPassword());

                    // execute the preparedstatement
                    preparedStmt.execute();

                    callResultModelList.add(new CallResultModel(false, true, System.getProperty("jboss.server.data.dir") + " " + model.getEnterprise_logo()));
//                    callResultModelList.add(new CallResultModel(false, true, enterpriseId));

                    databaseFunctions.closeDBOperations(connection, preparedStmt, null);

                } catch (SQLException e) {
                    e.printStackTrace();
                    if (e.toString().contains("Duplicate entry")) {

                        callResultModelList.add(new CallResultModel(true, false, "Username Already Registered"));
                        databaseFunctions.closeDBOperations(connection, preparedStmt, null);
                    } else {

                        callResultModelList.add(new CallResultModel(true, false, e.toString()));
                        databaseFunctions.closeDBOperations(connection, preparedStmt, null);
                    }
                }

            }
        });

        try {
            isExistThread.start();
            isExistThread.join();

            if (callResultModelList.isEmpty()) {

                insert2Database.start();
                insert2Database.join();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            callResultModelList.add(new CallResultModel(true, false, "Oops! Something Went Wrong, Try Again"));
        }

        return callResultModelList;
    }

    @POST
    @Path("/enterprise_address")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<CallResultModel> registerEnterpriseAddress(EnterpriseAddressModel enterpriseAddressModel) {

        List<CallResultModel> callResultModelList = new ArrayList<>();
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        String query = "INSERT INTO db_palm_business.enterprise_address (enterprise_id, billing_address, shipping_address, state_province, country)" + " VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, enterpriseAddressModel.getEnterprise_id());
            preparedStmt.setString (2, enterpriseAddressModel.getBilling_address());
            preparedStmt.setString (3, enterpriseAddressModel.getShipping_address());
            preparedStmt.setString (4, enterpriseAddressModel.getState_province());
            preparedStmt.setString (5, enterpriseAddressModel.getCountry());

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

    @POST
    @Path("/enterprise_contact_detail")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<CallResultModel> registerContactDetail(EnterpriseContactDetailsModel enterpriseContactDetailsModel) {

        List<CallResultModel> callResultModelList = new ArrayList<>();
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        String query = "INSERT INTO db_palm_business.enterprise_contact_details (enterprise_id, phone_type_code, area_code, db_palm_business.enterprise_contact_details.number, email_main, email_ccc, website)" + " VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, enterpriseContactDetailsModel.getEnterprise_id());
            preparedStmt.setInt (2, enterpriseContactDetailsModel.getPhone_type_code());
            preparedStmt.setString (3, enterpriseContactDetailsModel.getArea_code());
            preparedStmt.setString (4, enterpriseContactDetailsModel.getNumber());
            preparedStmt.setString (5, enterpriseContactDetailsModel.getEmail_main());
            preparedStmt.setString (6, enterpriseContactDetailsModel.getEmail_ccc());
            preparedStmt.setString (7, enterpriseContactDetailsModel.getWebsite());

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

    @POST
    @Path("/tax_scheme")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<CallResultModel> registerTaxScheme(TAXModel taxModel) {

        List<CallResultModel> callResultModelList = new ArrayList<>();
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        String query = "INSERT INTO db_palm_business.gst_affiliation (enterprise_id, gst_scheme_id, composition_charge, gstin)" + " VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, taxModel.getEnterprise_id());
            preparedStmt.setInt (2, taxModel.getGst_scheme_id());
            preparedStmt.setFloat(3, taxModel.getComposition_charge());
            preparedStmt.setString (4, taxModel.getGstin());

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
    
    @POST
    @Path("/login")
    public LoginModel loginEnterprise(LoginCredentialsModel loginCredentialsModel) {

        LoginModel loginModel = null;

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        Statement statement = null;
        ResultSet resultSet = null;

        String enterpriseId = null;
        String query = "SELECT db_palm_business.enterprises.enterprise_id, db_palm_business.enterprises.enterprise_logo, db_palm_business.enterprises.enterprise_name, db_palm_business.enterprises.fiscal_year, db_palm_business.enterprise_address.billing_address, db_palm_business.enterprise_address.shipping_address, db_palm_business.enterprise_address.state_province, db_palm_business.enterprise_address.country, db_palm_business.enterprise_contact_details.phone_type_code, db_palm_business.enterprise_contact_details.area_code, db_palm_business.enterprise_contact_details.number, db_palm_business.enterprise_contact_details.email_main, db_palm_business.enterprise_contact_details.email_ccc, db_palm_business.enterprise_contact_details.website, db_palm_business.gst_affiliation.gst_scheme_id, db_palm_business.gst_affiliation.composition_charge, db_palm_business.gst_affiliation.gstin FROM db_palm_business.enterprises LEFT JOIN db_palm_business.enterprise_address ON db_palm_business.enterprises.enterprise_id = db_palm_business.enterprise_address.enterprise_id LEFT JOIN db_palm_business.enterprise_contact_details ON db_palm_business.enterprises.enterprise_id = db_palm_business.enterprise_contact_details.enterprise_id LEFT JOIN db_palm_business.gst_affiliation ON db_palm_business.enterprises.enterprise_id = db_palm_business.gst_affiliation.enterprise_id WHERE BINARY db_palm_business.enterprises.username = '" + loginCredentialsModel.getUsername() + "' AND BINARY db_palm_business.enterprises.password = '" + loginCredentialsModel.getPassword() + "'";

        System.out.println("Query: " + query);

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next())	{

                enterpriseId = resultSet.getString("enterprise_id");
                String enterpriseLogo = resultSet.getString("enterprise_logo");
                String enterpriseName = resultSet.getString("enterprise_name");
                String fiscalYear = resultSet.getString("fiscal_year");
                String billingAddress = resultSet.getString("billing_address");
                String shippingAddress = resultSet.getString("shipping_address");
                String stateProvince = resultSet.getString("state_province");
                String country = resultSet.getString("country");
                int phoneTypeCode = resultSet.getInt("phone_type_code");
                String areaCode = resultSet.getString("area_code");
                String number = resultSet.getString("number");
                String emailMain = resultSet.getString("email_main");
                String emailCcc = resultSet.getString("email_ccc");
                String website = resultSet.getString("website");
                int gstSchemeId = resultSet.getInt("gst_scheme_id");
                double compositionCharge = resultSet.getDouble("composition_charge");
                String gstin = resultSet.getString("gstin");

                loginModel = new LoginModel(phoneTypeCode, gstSchemeId, compositionCharge, enterpriseId, enterpriseLogo, enterpriseName, fiscalYear, billingAddress, shippingAddress, stateProvince, country, areaCode, number, emailMain, emailCcc, website, gstin, true);
            }

            if (loginModel != null) {

				String loginQuery = "INSERT INTO db_palm_business.login_status (enterprise_id, logged_in)" + " values (?, ?)";

				PreparedStatement preparedStmt = connection.prepareStatement(loginQuery);

				preparedStmt.setString(1, enterpriseId);
				preparedStmt.setBoolean (2, true);
				preparedStmt.execute();
				databaseFunctions.closeDBOperations(connection, preparedStmt, resultSet);
			}
            
            databaseFunctions.closeDBOperations(connection, statement, resultSet);

        } catch (SQLException e) {

            e.printStackTrace();
            databaseFunctions.closeDBOperations(connection, statement, resultSet);
            return null;
        }

        return loginModel;
    }
    
    @GET
    @Path("/logout/{enterprise_id}")
    public CallResultModel logoutEnterprise(@PathParam("enterprise_id") String enterpriseId) {

        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        PreparedStatement preparedStmt = null;
        CallResultModel model = null;

        try {
			String loginQuery = "INSERT INTO db_palm_business.login_status (enterprise_id, logged_in)" + " values (?, ?)";

			preparedStmt = connection.prepareStatement(loginQuery);

			preparedStmt.setString(1, enterpriseId);
			preparedStmt.setBoolean (2, false);
			preparedStmt.execute();
        
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
			model = new CallResultModel(false, true, "Logged out Successfully...");

        } catch (SQLException e) {

            e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
			model = new CallResultModel(true, false, "Something went wrong...");
        }

        return model;
    }

    @PUT
    @Path("/modify_enterprise/{enterprise_id}")
    public EnterpriseModel modifyEnterprise(@PathParam("enterprise_id") String enterpriseId,
                                            @FormDataParam("logo") InputStream inputStream,
                                            @FormDataParam("logo") FormDataContentDisposition contentDisposition,
                                            @FormDataParam("enterpriseModel") FormDataBodyPart jsonData) {

        jsonData.setMediaType(MediaType.APPLICATION_JSON_TYPE);
        EnterpriseModel enterpriseModel = jsonData.getValueAs(EnterpriseModel.class);
        String directoryPath;

        if (inputStream != null) {

            directoryPath = "/Users/jawedtahasildar/eclipse-workspace/palmbusiness/target/" + enterpriseId + ".png";
            APIUtilities.writeToFile(inputStream, directoryPath);
        } else {
    			
            directoryPath = enterpriseModel.getEnterprise_logo();
        }

        String[] givenFiscal = enterpriseModel.getFiscal_year().split(" ");
        int monthIndex = Arrays.asList(APIUtilities.months).indexOf(givenFiscal[1]);

        enterpriseModel.setFiscal_year("0000" + "-" + String.valueOf(monthIndex + 1) + "-" + givenFiscal[0]);
        enterpriseModel.setEnterprise_logo(directoryPath);

        EnterpriseModel model = null;
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();
        String query;
        
        if (enterpriseModel.getUsername() == null || enterpriseModel.getPassword() == null) {
			
            query = "UPDATE db_palm_business.enterprises SET enterprise_logo = ?, enterprise_name = ?, fiscal_year = ? WHERE db_palm_business.enterprises.enterprise_id = '" + enterpriseId + "'";
		} else {

	        query = "UPDATE db_palm_business.enterprises SET enterprise_logo = ?, enterprise_name = ?, fiscal_year = ?, username = ?, password = ? WHERE db_palm_business.enterprises.enterprise_id = '" + enterpriseId + "'";
		}		

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            if (enterpriseModel.getUsername() == null || enterpriseModel.getPassword() == null) {
            	
                preparedStmt.setString (1, enterpriseModel.getEnterprise_logo());
                preparedStmt.setString (2, enterpriseModel.getEnterprise_name());
                preparedStmt.setString (3, enterpriseModel.getFiscal_year());
            } else {
            	
	            	preparedStmt.setString (1, enterpriseModel.getEnterprise_logo());
	            	preparedStmt.setString (2, enterpriseModel.getEnterprise_name());
	            	preparedStmt.setString (3, enterpriseModel.getFiscal_year());
	            	preparedStmt.setString (4, enterpriseModel.getUsername());
	            	preparedStmt.setString (5, enterpriseModel.getPassword());
            }

            // execute the preparedstatement
            preparedStmt.executeUpdate();

            query = "SELECT * FROM db_palm_business.enterprises WHERE db_palm_business.enterprises.enterprise_id = '" + enterpriseId + "'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.isBeforeFirst()) {

                while (resultSet.next()) {

                    String enterpriseLogo = resultSet.getString("enterprise_logo");
                    String enterpriseName = resultSet.getString("enterprise_name");
                    String fiscalYear = resultSet.getString("fiscal_year");
                    String userName = resultSet.getString("username");
                    String userPassword = resultSet.getString("password");

                    model = new EnterpriseModel(enterpriseId, enterpriseLogo, enterpriseName, fiscalYear, userName, userPassword);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        databaseFunctions.closeDBOperations(connection, preparedStmt, null);
        return model;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/modify_enterprise_address/{enterprise_id}")
    public EnterpriseAddressModel modifyEnterpriseAddress(@PathParam("enterprise_id") String enterpriseId, EnterpriseAddressModel enterpriseAddressModel) {

        EnterpriseAddressModel model = null;
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        String query = "UPDATE db_palm_business.enterprise_address SET billing_address = ?, shipping_address = ?, state_province = ?, country = ? WHERE db_palm_business.enterprise_address.enterprise_id = '" + enterpriseId + "'";

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, enterpriseAddressModel.getBilling_address());
            preparedStmt.setString (2, enterpriseAddressModel.getShipping_address());
            preparedStmt.setString (3, enterpriseAddressModel.getState_province());
            preparedStmt.setString (4, enterpriseAddressModel.getCountry());

            // execute the preparedstatement
            preparedStmt.executeUpdate();

            query = "SELECT * FROM db_palm_business.enterprise_address WHERE db_palm_business.enterprise_address.enterprise_id = '" + enterpriseId + "'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.isBeforeFirst()) {

                while (resultSet.next()) {

                    String billingAddress = resultSet.getString("billing_address");
                    String shippingAddress = resultSet.getString("shipping_address");
                    String stateProvince = resultSet.getString("state_province");
                    String country = resultSet.getString("country");

                    model = new EnterpriseAddressModel(enterpriseId, billingAddress, shippingAddress, stateProvince, country);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        databaseFunctions.closeDBOperations(connection, preparedStmt, null);
        return model;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/modify_enterprise_contact/{enterprise_id}")
    public EnterpriseContactDetailsModel modifyEnterpriseContact(@PathParam("enterprise_id") String enterpriseId, EnterpriseContactDetailsModel enterpriseContactDetailsModel) {

        EnterpriseContactDetailsModel model = null;
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        String query = "UPDATE db_palm_business.enterprise_contact_details SET phone_type_code = ?, area_code = ?, number = ?, email_main = ?, email_ccc = ?, website = ? WHERE db_palm_business.enterprise_contact_details.enterprise_id = '" + enterpriseId + "'";

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setInt (1, enterpriseContactDetailsModel.getPhone_type_code());
            preparedStmt.setString (2, enterpriseContactDetailsModel.getArea_code());
            preparedStmt.setString (3, enterpriseContactDetailsModel.getNumber());
            preparedStmt.setString (4, enterpriseContactDetailsModel.getEmail_main());
            preparedStmt.setString (5, enterpriseContactDetailsModel.getEmail_ccc());
            preparedStmt.setString (6, enterpriseContactDetailsModel.getWebsite());

            // execute the preparedstatement
            preparedStmt.executeUpdate();

            query = "SELECT * FROM db_palm_business.enterprise_contact_details WHERE db_palm_business.enterprise_contact_details.enterprise_id = '" + enterpriseId + "'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.isBeforeFirst()) {

                while (resultSet.next()) {

                    int phoneTypeCode = resultSet.getInt("phone_type_code");
                    String areaCode = resultSet.getString("area_code");
                    String number = resultSet.getString("number");
                    String emailMain = resultSet.getString("email_main");
                    String emailCcc = resultSet.getString("email_ccc");
                    String website = resultSet.getString("website");

                    model = new EnterpriseContactDetailsModel(enterpriseId, phoneTypeCode, areaCode, number, emailMain, emailCcc, website);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        databaseFunctions.closeDBOperations(connection, preparedStmt, null);
        return model;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/modify_enterprise_tax_scheme/{enterprise_id}")
    public TAXModel modifyEnterpriseTaxScheme(@PathParam("enterprise_id") String enterpriseId, TAXModel taxModel) {

        TAXModel model = null;
        databaseFunctions = new DatabaseFunctions();
        Connection connection = databaseFunctions.connect2DB();

        String query = "UPDATE db_palm_business.gst_affiliation SET gst_scheme_id = ?, composition_charge = ?, gstin = ? WHERE db_palm_business.gst_affiliation.enterprise_id = '" + enterpriseId + "'";

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setInt (1, taxModel.getGst_scheme_id());
            preparedStmt.setDouble (2, taxModel.getComposition_charge());
            preparedStmt.setString (3, taxModel.getGstin());

            // execute the preparedstatement
            preparedStmt.executeUpdate();

            query = "SELECT * FROM db_palm_business.gst_affiliation WHERE db_palm_business.gst_affiliation.enterprise_id = '" + enterpriseId + "'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.isBeforeFirst()) {

                while (resultSet.next()) {

                    int gstAffiliationId = resultSet.getInt("gst_affiliation_id");
                    int gstSchemeId = resultSet.getInt("gst_scheme_id");
                    float compositionCharge = resultSet.getFloat("composition_charge");
                    String gstin = resultSet.getString("gstin");

                    model = new TAXModel(gstAffiliationId, gstSchemeId, compositionCharge, enterpriseId, gstin);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        databaseFunctions.closeDBOperations(connection, preparedStmt, null);
        return model;
    }

}
