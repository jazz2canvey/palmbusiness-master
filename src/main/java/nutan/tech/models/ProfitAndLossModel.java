package nutan.tech.models;

public class ProfitAndLossModel {

    private double purchase_account, sales_account, total_indirect_income, direct_expense_amount, indirect_expense_amount;

    public ProfitAndLossModel() {

    }

    public ProfitAndLossModel(double purchase_account, double sales_account, double total_indirect_income, double direct_expense_amount, double indirect_expense_amount) {

        this.purchase_account = purchase_account;
        this.sales_account = sales_account;
        this.total_indirect_income = total_indirect_income;
        this.direct_expense_amount = direct_expense_amount;
        this.indirect_expense_amount = indirect_expense_amount;
    }

    public double getPurchase_account() {
        return purchase_account;
    }

    public void setPurchase_account(double purchase_account) {
        this.purchase_account = purchase_account;
    }

    public double getSales_account() {
        return sales_account;
    }

    public void setSales_account(double sales_account) {
        this.sales_account = sales_account;
    }

    public double getTotal_indirect_income() {
        return total_indirect_income;
    }

    public void setTotal_indirect_income(double total_indirect_income) {
        this.total_indirect_income = total_indirect_income;
    }

    public double getDirect_expense_amount() {
        return direct_expense_amount;
    }

    public void setDirect_expense_amount(double direct_expense_amount) {
        this.direct_expense_amount = direct_expense_amount;
    }

    public double getIndirect_expense_amount() {
        return indirect_expense_amount;
    }

    public void setIndirect_expense_amount(double indirect_expense_amount) {
        this.indirect_expense_amount = indirect_expense_amount;
    }
}
