package nutan.tech.models;

public class ExpenseItemsModel {

	int expense_item_id, item_type;
	float spent_price, sgst, cgst, igst;
	String item_name, hsn_o_sac;
	
	public ExpenseItemsModel() {
		
	}
	
	public ExpenseItemsModel(int expense_item_id, int item_type, float spent_price, float sgst, float cgst, float igst, String item_name, String hsn_o_sac) {
		
		this.expense_item_id = expense_item_id;
		this.item_type = item_type;
		this.spent_price = spent_price;
		this.sgst = sgst;
		this.cgst = cgst;
		this.igst = igst;
		this.item_name = item_name;
		this.hsn_o_sac = hsn_o_sac;		
	}

	public int getExpense_item_id() {
		return expense_item_id;
	}

	public void setExpense_item_id(int expense_item_id) {
		this.expense_item_id = expense_item_id;
	}

	public int getItem_type() {
		return item_type;
	}

	public void setItem_type(int item_type) {
		this.item_type = item_type;
	}

	public float getSpent_price() {
		return spent_price;
	}

	public void setSpent_price(float spent_price) {
		this.spent_price = spent_price;
	}

	public float getSgst() {
		return sgst;
	}

	public void setSgst(float sgst) {
		this.sgst = sgst;
	}

	public float getCgst() {
		return cgst;
	}

	public void setCgst(float cgst) {
		this.cgst = cgst;
	}

	public float getIgst() {
		return igst;
	}

	public void setIgst(float igst) {
		this.igst = igst;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getHsn_o_sac() {
		return hsn_o_sac;
	}

	public void setHsn_o_sac(String hsn_o_sac) {
		this.hsn_o_sac = hsn_o_sac;
	}
	
}
