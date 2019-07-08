package nutan.tech.models;

public class PurchaseInvoiceModel {

	private int discount_type_code;
	private double taken_discount, total_amount;
	private String purchase_invoice_id, enterprise_id, vendor_id, vendor_name, enterprise_name, invoice_number, reference, entry_date, due_date, description;
	private boolean status, reverse_charge, is_rate_including_gst;

	public PurchaseInvoiceModel() {
		
	}

	public PurchaseInvoiceModel(String purchase_invoice_id, String enterprise_id, String vendor_id, String enterprise_name, String invoice_number, String entry_date, String due_date, int discount_type_code, double taken_discount, double total_amount, String reference, boolean status, boolean is_rate_including_gst, boolean reverse_charge, String description) {

		this.purchase_invoice_id = purchase_invoice_id;
		this.enterprise_id = enterprise_id;
		this.vendor_id = vendor_id;
		this.enterprise_name = enterprise_name;
		this.invoice_number = invoice_number;
		this.entry_date = entry_date;
		this.due_date = due_date;
		this.discount_type_code = discount_type_code;
		this.taken_discount = taken_discount;
		this.total_amount = total_amount;
		this.reference = reference;
		this.status = status;
		this.is_rate_including_gst = is_rate_including_gst;
		this.reverse_charge = reverse_charge;
		this.description = description;
	}

	public PurchaseInvoiceModel(String purchase_invoice_id, String vendor_id, String vendor_name, String invoice_number, String reference, boolean status, boolean is_rate_including_gst, String entry_date, String due_date, int discount_type_code, double taken_discount, double total_amount, boolean reverse_charge, String description) {

		this.purchase_invoice_id = purchase_invoice_id;
		this.vendor_id = vendor_id;
		this.vendor_name = vendor_name;
		this.invoice_number = invoice_number;
		this.reference = reference;
		this.status = status;
		this.is_rate_including_gst = is_rate_including_gst;
		this.entry_date = entry_date;
		this.due_date = due_date;
		this.discount_type_code = discount_type_code;
		this.taken_discount = taken_discount;
		this.total_amount = total_amount;
		this.description = description;

		this.reverse_charge = reverse_charge;
	}

	public int getDiscount_type_code() {
		return discount_type_code;
	}

	public void setDiscount_type_code(int discount_type_code) {
		this.discount_type_code = discount_type_code;
	}

	public double getTaken_discount() {
		return taken_discount;
	}

	public void setTaken_discount(double taken_discount) {
		this.taken_discount = taken_discount;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public String getPurchase_invoice_id() {
		return purchase_invoice_id;
	}

	public void setPurchase_invoice_id(String purchase_invoice_id) {
		this.purchase_invoice_id = purchase_invoice_id;
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

	public String getEnterprise_name() {
		return enterprise_name;
	}

	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public boolean isReverse_charge() {
		return reverse_charge;
	}

	public void setReverse_charge(boolean reverse_charge) {
		this.reverse_charge = reverse_charge;
	}
}
