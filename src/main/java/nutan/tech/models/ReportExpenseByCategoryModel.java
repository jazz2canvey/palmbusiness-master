package nutan.tech.models;

public class ReportExpenseByCategoryModel {

    private String expense_head;
    private double total_count, total_amount;

    public ReportExpenseByCategoryModel() {

    }

    public ReportExpenseByCategoryModel(String expense_head, double total_count, double total_amount) {

        this.expense_head = expense_head;
        this.total_count = total_count;
        this.total_amount = total_amount;
    }

    public String getExpense_head() {
        return expense_head;
    }

    public void setExpense_head(String expense_head) {
        this.expense_head = expense_head;
    }

    public double getTotal_count() {
        return total_count;
    }

    public void setTotal_count(double total_count) {
        this.total_count = total_count;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
}

