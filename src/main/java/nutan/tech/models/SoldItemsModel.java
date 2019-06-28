package nutan.tech.models;

public class SoldItemsModel {

	private int item_type_code, discount_type_code;
	private double sale_price, quantity, sgst, cgst, igst, given_discount, total_amount;
	private String item_id, item_name, item_type, discount_type, enterprise_id, customer_id, invoice_number, measurement, hsn_o_sac;

	public SoldItemsModel() {
		
	}

	public SoldItemsModel(int item_type_code, int discount_type_code, double sale_price, double quantity, double sgst, double cgst, double igst, double given_discount, double total_amount, String item_id, String enterprise_id, String customer_id, String invoice_number, String measurement, String hsn_o_sac) {
		this.item_type_code = item_type_code;
		this.discount_type_code = discount_type_code;
		this.sale_price = sale_price;
		this.quantity = quantity;
		this.sgst = sgst;
		this.cgst = cgst;
		this.igst = igst;
		this.given_discount = given_discount;
		this.total_amount = total_amount;
		this.item_id = item_id;
		this.enterprise_id = enterprise_id;
		this.customer_id = customer_id;
		this.invoice_number = invoice_number;
		this.measurement = measurement;
		this.hsn_o_sac = hsn_o_sac;
	}

	public SoldItemsModel(int item_type_code, int discount_type_code, double sale_price, double quantity, double sgst, double cgst, double igst, double given_discount, double total_amount, String item_id, String item_name, String item_type, String discount_type, String enterprise_id, String customer_id, String invoice_number, String measurement, String hsn_o_sac) {

		this.item_type_code = item_type_code;
		this.discount_type_code = discount_type_code;
		this.sale_price = sale_price;
		this.quantity = quantity;
		this.sgst = sgst;
		this.cgst = cgst;
		this.igst = igst;
		this.given_discount = given_discount;
		this.total_amount = total_amount;
		this.item_id = item_id;
		this.item_name = item_name;
		this.item_type = item_type;
		this.discount_type = discount_type;
		this.enterprise_id = enterprise_id;
		this.customer_id = customer_id;
		this.invoice_number = invoice_number;
		this.measurement = measurement;
		this.hsn_o_sac = hsn_o_sac;
	}

	public int getItem_type_code() {
		return item_type_code;
	}

	public void setItem_type_code(int item_type_code) {
		this.item_type_code = item_type_code;
	}

	public int getDiscount_type_code() {
		return discount_type_code;
	}

	public void setDiscount_type_code(int discount_type_code) {
		this.discount_type_code = discount_type_code;
	}

	public double getSale_price() {
		return sale_price;
	}

	public void setSale_price(double sale_price) {
		this.sale_price = sale_price;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getSgst() {
		return sgst;
	}

	public void setSgst(double sgst) {
		this.sgst = sgst;
	}

	public double getCgst() {
		return cgst;
	}

	public void setCgst(double cgst) {
		this.cgst = cgst;
	}

	public double getIgst() {
		return igst;
	}

	public void setIgst(double igst) {
		this.igst = igst;
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

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

	public String getDiscount_type() {
		return discount_type;
	}

	public void setDiscount_type(String discount_type) {
		this.discount_type = discount_type;
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

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public String getMeasurement() {
		return measurement;
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}

	public String getHsn_o_sac() {
		return hsn_o_sac;
	}

	public void setHsn_o_sac(String hsn_o_sac) {
		this.hsn_o_sac = hsn_o_sac;
	}
}
