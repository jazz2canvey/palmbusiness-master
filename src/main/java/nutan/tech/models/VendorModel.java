package nutan.tech.models;

public class VendorModel {

	private String vendor_id, enterprise_id, honorific, full_name, enterprise_name, gstin, email_main, email_ccc, landline, mobile, billing_address, state_province, country, note;
	
	public VendorModel() {
		
	}

	public VendorModel(String vendor_id, String enterprise_id, String honorific, String full_name, String enterprise_name, String gstin, String email_main, String email_ccc, String landline, String mobile, String billing_address, String state_province, String country, String note) {

		this.vendor_id = vendor_id;
		this.enterprise_id = enterprise_id;
		this.honorific = honorific;
		this.full_name = full_name;
		this.enterprise_name = enterprise_name;
		this.gstin = gstin;
		this.email_main = email_main;
		this.email_ccc = email_ccc;
		this.landline = landline;
		this.mobile = mobile;
		this.billing_address = billing_address;
		this.state_province = state_province;
		this.country = country;
		this.note = note;
	}


	public String getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}

	public String getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(String enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public String getHonorific() {
		return honorific;
	}

	public void setHonorific(String honorific) {
		this.honorific = honorific;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getEnterprise_name() {
		return enterprise_name;
	}

	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
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

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBilling_address() {
		return billing_address;
	}

	public void setBilling_address(String billing_address) {
		this.billing_address = billing_address;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
