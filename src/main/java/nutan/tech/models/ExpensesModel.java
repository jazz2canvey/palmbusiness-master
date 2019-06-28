package nutan.tech.models;

public class ExpensesModel {

	private int expense_type_code;
	private double amount;
	private String expense_id, enterprise_id, entry_date, expense_title, expense_head, expense_name, description;

	public ExpensesModel() {
		
	}

	public ExpensesModel(int expense_type_code, String expense_id, String enterprise_id, String entry_date, String expense_head, String expense_name, String description) {

		this.expense_type_code = expense_type_code;
		this.expense_id = expense_id;
		this.enterprise_id = enterprise_id;
		this.entry_date = entry_date;
		this.expense_head = expense_head;
		this.expense_name = expense_name;
		this.description = description;
	}

	public ExpensesModel(int expense_type_code, String expense_id, String enterprise_id, String entry_date, String expense_title, String expense_head, String expense_name, double amount, String description) {

		this.expense_type_code = expense_type_code;
		this.expense_id = expense_id;
		this.enterprise_id = enterprise_id;
		this.entry_date = entry_date;
		this.expense_title = expense_title;
		this.expense_head = expense_head;
		this.expense_name = expense_name;
		this.amount = amount;
		this.description = description;
	}


	public int getExpense_type_code() {
		return expense_type_code;
	}

	public void setExpense_type_code(int expense_type_code) {
		this.expense_type_code = expense_type_code;
	}

	public String getExpense_id() {
		return expense_id;
	}

	public void setExpense_id(String expense_id) {
		this.expense_id = expense_id;
	}

	public String getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(String enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public String getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(String entry_date) {
		this.entry_date = entry_date;
	}

	public String getExpense_title() {
		return expense_title;
	}

	public void setExpense_title(String expense_title) {
		this.expense_title = expense_title;
	}

	public String getExpense_head() {
		return expense_head;
	}

	public void setExpense_head(String expense_head) {
		this.expense_head = expense_head;
	}

	public String getExpense_name() {
		return expense_name;
	}

	public void setExpense_name(String expense_name) {
		this.expense_name = expense_name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
