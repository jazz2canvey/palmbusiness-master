package nutan.tech.models;

public class CreateItemModel {

	private int item_type_code;
	private double purchased_quantity, sold_quantity, available_quantity;
	private String item_id, enterprise_id, item_name, measurement, item_description;
	
	public CreateItemModel() {
		
	}

	public CreateItemModel(int item_type_code, double purchased_quantity, double sold_quantity, double available_quantity, String item_id, String enterprise_id, String item_name, String measurement, String item_description) {
		this.item_type_code = item_type_code;
		this.purchased_quantity = purchased_quantity;
		this.sold_quantity = sold_quantity;
		this.available_quantity = available_quantity;
		this.item_id = item_id;
		this.enterprise_id = enterprise_id;
		this.item_name = item_name;
		this.measurement = measurement;
		this.item_description = item_description;
	}

	public CreateItemModel(String item_id, String enterprise_id, String item_name, String measurement, int item_type_code, String item_description) {

		this.item_id = item_id;
		this.enterprise_id = enterprise_id;
		this.item_name = item_name;
		this.measurement = measurement;
		this.item_type_code = item_type_code;
		this.item_description = item_description;
	}

	public int getItem_type_code() {
		return item_type_code;
	}

	public void setItem_type_code(int item_type_code) {
		this.item_type_code = item_type_code;
	}

	public double getPurchased_quantity() {
		return purchased_quantity;
	}

	public void setPurchased_quantity(double purchased_quantity) {
		this.purchased_quantity = purchased_quantity;
	}

	public double getSold_quantity() {
		return sold_quantity;
	}

	public void setSold_quantity(double sold_quantity) {
		this.sold_quantity = sold_quantity;
	}

	public double getAvailable_quantity() {
		return available_quantity;
	}

	public void setAvailable_quantity(double available_quantity) {
		this.available_quantity = available_quantity;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(String enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getMeasurement() {
		return measurement;
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}

	public String getItem_description() {
		return item_description;
	}

	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}
}
