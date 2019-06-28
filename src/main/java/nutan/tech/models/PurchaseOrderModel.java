package nutan.tech.models;

public class PurchaseOrderModel {

	private String purchase_order_id, enterprise_id, vendor_id, vendor_name, invoice_number, entry_date, to_address, billing_address, shipping_address, state_province, country, note;
	private double total_amount;
	private boolean status;

	public PurchaseOrderModel() {
		
	}

	public PurchaseOrderModel(String purchase_order_id, String enterprise_id, String vendor_id, String vendor_name, String invoice_number, String entry_date, String to_address, String billing_address, String shipping_address, String state_province, String country, String note, double total_amount, boolean status) {
		this.purchase_order_id = purchase_order_id;
		this.enterprise_id = enterprise_id;
		this.vendor_id = vendor_id;
		this.vendor_name = vendor_name;
		this.invoice_number = invoice_number;
		this.entry_date = entry_date;
		this.to_address = to_address;
		this.billing_address = billing_address;
		this.shipping_address = shipping_address;
		this.state_province = state_province;
		this.country = country;
		this.note = note;
		this.total_amount = total_amount;
		this.status = status;
	}

	public String getPurchase_order_id() {
		return purchase_order_id;
	}

	public void setPurchase_order_id(String purchase_order_id) {
		this.purchase_order_id = purchase_order_id;
	}

	public String getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(String enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public String getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}

	public String getVendor_name() {
		return vendor_name;
	}

	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public String getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(String entry_date) {
		this.entry_date = entry_date;
	}

	public String getTo_address() {
		return to_address;
	}

	public void setTo_address(String to_address) {
		this.to_address = to_address;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
}
