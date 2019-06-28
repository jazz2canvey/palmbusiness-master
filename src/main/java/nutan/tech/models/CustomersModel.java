package nutan.tech.models;

public class CustomersModel {

	private int customer_type_code;
	private String customer_id, enterprise_id, enterprise_name, person_name, gstin, mobile_number, address_landline, address_email_main, address_email_ccc, billing_address, shipping_address, state_province, country;

	public CustomersModel() {
		
	}

	public CustomersModel(int customer_type_code, String customer_id, String enterprise_id, String enterprise_name, String person_name, String gstin, String mobile_number, String address_landline, String address_email_main, String address_email_ccc, String billing_address, String shipping_address, String state_province, String country) {

		this.customer_type_code = customer_type_code;
		this.customer_id = customer_id;
		this.enterprise_id = enterprise_id;
		this.enterprise_name = enterprise_name;
		this.person_name = person_name;
		this.gstin = gstin;
		this.mobile_number = mobile_number;
		this.address_landline = address_landline;
		this.address_email_main = address_email_main;
		this.address_email_ccc = address_email_ccc;
		this.billing_address = billing_address;
		this.shipping_address = shipping_address;
		this.state_province = state_province;
		this.country = country;
	}

	public int getCustomer_type_code() {
		return customer_type_code;
	}

	public void setCustomer_type_code(int customer_type_code) {
		this.customer_type_code = customer_type_code;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(String enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public String getEnterprise_name() {
		return enterprise_name;
	}

	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
	}

	public String getPerson_name() {
		return person_name;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getAddress_landline() {
		return address_landline;
	}

	public void setAddress_landline(String address_landline) {
		this.address_landline = address_landline;
	}

	public String getAddress_email_main() {
		return address_email_main;
	}

	public void setAddress_email_main(String address_email_main) {
		this.address_email_main = address_email_main;
	}

	public String getAddress_email_ccc() {
		return address_email_ccc;
	}

	public void setAddress_email_ccc(String address_email_ccc) {
		this.address_email_ccc = address_email_ccc;
	}

	public String getBilling_address() {
		return billing_address;
	}

	public void setBilling_address(String billing_address) {
		this.billing_address = billing_address;
	}

	public String getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}

	public String getState_province() {
		return state_province;
	}

	public void setState_province(String state_province) {
		this.state_province = state_province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
