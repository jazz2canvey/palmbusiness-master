package nutan.tech.models;

public class CustomerTypeModel {

	int customer_type_id, customer_type_code;
	String customer_type;
	
	public CustomerTypeModel() {
		
	}
	
	public CustomerTypeModel(int customer_type_id, String customer_type, int customer_type_code) {
		
		this.customer_type_id = customer_type_id;
		this.customer_type = customer_type;
		this.customer_type_code = customer_type_code;
	}

	public int getCustomer_type_id() {
		return customer_type_id;
	}

	public void setCustomer_type_id(int customer_type_id) {
		this.customer_type_id = customer_type_id;
	}

	public int getCustomer_type_code() {
		return customer_type_code;
	}

	public void setCustomer_type_code(int customer_type_code) {
		this.customer_type_code = customer_type_code;
	}

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}
	
}
