package nutan.tech.models;

public class ExpensePaymentModel {

    private String expense_id, payment_receipt_id, enterprise_id, entry_date, expense_head, expense_name, description;
    private int expenseTypeCode;
    private double amount, paid_amount, remaining_amount;

    public ExpensePaymentModel() {

    }

    public ExpensePaymentModel(String expense_id, String payment_receipt_id, String enterprise_id, String entry_date, String expense_head, String expense_name, String description, int expenseTypeCode, double amount, double paid_amount, double remaining_amount) {

        this.expense_id = expense_id;
        this.payment_receipt_id = payment_receipt_id;
        this.enterprise_id = enterprise_id;
        this.entry_date = entry_date;
        this.expense_head = expense_head;
        this.expense_name = expense_name;
        this.description = description;
        this.expenseTypeCode = expenseTypeCode;
        this.amount = amount;
        this.paid_amount = paid_amount;
        this.remaining_amount = remaining_amount;
    }

    public String getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(String expense_id) {
        this.expense_id = expense_id;
    }

    public String getPayment_receipt_id() {
        return payment_receipt_id;
    }

    public void setPayment_receipt_id(String payment_receipt_id) {
        this.payment_receipt_id = payment_receipt_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExpenseTypeCode() {
        return expenseTypeCode;
    }

    public void setExpenseTypeCode(int expenseTypeCode) {
        this.expenseTypeCode = expenseTypeCode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(double paid_amount) {
        this.paid_amount = paid_amount;
    }

    public double getRemaining_amount() {
        return remaining_amount;
    }

    public void setRemaining_amount(double remaining_amount) {
        this.remaining_amount = remaining_amount;
    }
}
