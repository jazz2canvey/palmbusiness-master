package nutan.tech.models;

public class LoginModel {

	private int phone_type_code, gst_scheme_id;
	private double composition_charge;
	private boolean logged_in;
	private String enterprise_id, enterprise_logo, enterprise_name, fiscal_year, billing_address, shipping_address, state_province, country, area_code, number, email_main, email_ccc, website, gstin;

	public LoginModel() {
		
	}

	public LoginModel(int phone_type_code, int gst_scheme_id, double composition_charge, String enterprise_id, String enterprise_logo, String enterprise_name, String fiscal_year, String billing_address, String shipping_address, String state_province, String country, String area_code, String number, String email_main, String email_ccc, String website, String gstin, boolean logged_in) {

		this.phone_type_code = phone_type_code;
		this.gst_scheme_id = gst_scheme_id;
		this.composition_charge = composition_charge;
		this.enterprise_id = enterprise_id;
		this.enterprise_logo = enterprise_logo;
		this.enterprise_name = enterprise_name;
		this.fiscal_year = fiscal_year;
		this.billing_address = billing_address;
		this.shipping_address = shipping_address;
		this.state_province = state_province;
		this.country = country;
		this.area_code = area_code;
		this.number = number;
		this.email_main = email_main;
		this.email_ccc = email_ccc;
		this.website = website;
		this.gstin = gstin;
		this.logged_in = logged_in;
	}

	public int getPhone_type_code() {
		return phone_type_code;
	}

	public void setPhone_type_code(int phone_type_code) {
		this.phone_type_code = phone_type_code;
	}

	public int getGst_scheme_id() {
		return gst_scheme_id;
	}

	public void setGst_scheme_id(int gst_scheme_id) {
		this.gst_scheme_id = gst_scheme_id;
	}

	public double getComposition_charge() {
		return composition_charge;
	}

	public void setComposition_charge(double composition_charge) {
		this.composition_charge = composition_charge;
	}

	public String getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(String enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public String getEnterprise_logo() {
		return enterprise_logo;
	}

	public void setEnterprise_logo(String enterprise_logo) {
		this.enterprise_logo = enterprise_logo;
	}

	public String getEnterprise_name() {
		return enterprise_name;
	}

	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
	}

	public String getFiscal_year() {
		return fiscal_year;
	}

	public void setFiscal_year(String fiscal_year) {
		this.fiscal_year = fiscal_year;
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

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getEmail_main() {
		return email_main;
	}

	public void setEmail_main(String email_main) {
		this.email_main = email_main;
	}

	public String getEmail_ccc() {
		return email_ccc;
	}

	public void setEmail_ccc(String email_ccc) {
		this.email_ccc = email_ccc;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public boolean isLogged_in() {
		return logged_in;
	}

	public void setLogged_in(boolean logged_in) {
		this.logged_in = logged_in;
	}


	
}
