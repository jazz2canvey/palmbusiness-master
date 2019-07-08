package nutan.tech.models;

public class SalesInvoiceModel {

	private int discount_type_code;
	private double given_discount, total_amount;
	private boolean status, is_rate_including_gst;
	private String discount_type, sales_invoice_id, enterprise_id, customer_id, customer_name, invoice_number, reference, entry_date, due_date, billing_address, shipping_address, state_province, country, msg_to_customer;

	public SalesInvoiceModel() {
		
	}

	public SalesInvoiceModel(int discount_type_code, double given_discount, double total_amount, boolean status, boolean is_rate_including_gst, String sales_invoice_id, String enterprise_id, String customer_id, String invoice_number, String reference, String entry_date, String due_date, String billing_address, String shipping_address, String state_province, String country, String msg_to_customer) {

		this.discount_type_code = discount_type_code;
		this.given_discount = given_discount;
		this.total_amount = total_amount;
		this.status = status;
		this.is_rate_including_gst = is_rate_including_gst;
		this.sales_invoice_id = sales_invoice_id;
		this.enterprise_id = enterprise_id;
		this.customer_id = customer_id;
		this.invoice_number = invoice_number;
		this.reference = reference;
		this.entry_date = entry_date;
		this.due_date = due_date;
		this.billing_address = billing_address;
		this.shipping_address = shipping_address;
		this.state_province = state_province;
		this.country = country;
		this.msg_to_customer = msg_to_customer;
	}

	public SalesInvoiceModel(int discount_type_code, double given_discount, double total_amount, boolean status, boolean is_rate_including_gst, String sales_invoice_id, String enterprise_id, String customer_id, String customer_name, String invoice_number, String reference, String entry_date, String due_date, String billing_address, String shipping_address, String state_province, String country, String msg_to_customer) {

		this.discount_type_code = discount_type_code;
		this.given_discount = given_discount;
		this.total_amount = total_amount;
		this.status = status;
		this.is_rate_including_gst = is_rate_including_gst;
		this.sales_invoice_id = sales_invoice_id;
		this.enterprise_id = enterprise_id;
		this.customer_id = customer_id;
		this.customer_name = customer_name;
		this.invoice_number = invoice_number;
		this.reference = reference;
		this.entry_date = entry_date;
		this.due_date = due_date;
		this.billing_address = billing_address;
		this.shipping_address = shipping_address;
		this.state_province = state_province;
		this.country = country;
		this.msg_to_customer = msg_to_customer;
	}

	public SalesInvoiceModel(double given_discount, double total_amount, boolean status, boolean is_rate_including_gst, String discount_type, String sales_invoice_id, String enterprise_id, String customer_id, String customer_name, String invoice_number, String reference, String entry_date, String due_date, String msg_to_customer) {

		this.given_discount = given_discount;
		this.total_amount = total_amount;
		this.status = status;
		this.is_rate_including_gst = is_rate_including_gst;
		this.discount_type = discount_type;
		this.sales_invoice_id = sales_invoice_id;
		this.enterprise_id = enterprise_id;
		this.customer_id = customer_id;
		this.customer_name = customer_name;
		this.invoice_number = invoice_number;
		this.reference = reference;
		this.entry_date = entry_date;
		this.due_date = due_date;
		this.msg_to_customer = msg_to_customer;
	}

	public int getDiscount_type_code() {
		return discount_type_code;
	}

	public void setDiscount_type_code(int discount_type_code) {
		this.discount_type_code = discount_type_code;
	}

	public double getGiven_discount() {
		return given_discount;
	}

	public void setGiven_discount(double given_discount) {
		this.given_discount = given_discount;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isIs_rate_including_gst() {
		return is_rate_including_gst;
	}

	public void setIs_rate_including_gst(boolean is_rate_including_gst) {
		this.is_rate_including_gst = is_rate_including_gst;
	}

	public String getDiscount_type() {
		return discount_type;
	}

	public void setDiscount_type(String discount_type) {
		this.discount_type = discount_type;
	}

	public String getSales_invoice_id() {
		return sales_invoice_id;
	}

	public void setSales_invoice_id(String sales_invoice_id) {
		this.sales_invoice_id = sales_invoice_id;
	}

	public String getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(String enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(String entry_date) {
		this.entry_date = entry_date;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
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

	public String getMsg_to_customer() {
		return msg_to_customer;
	}

	public void setMsg_to_customer(String msg_to_customer) {
		this.msg_to_customer = msg_to_customer;
	}
}
