package nutan.tech.models;

public class AccountIdAndTotalAmountModel {

    private String account_id;
    private double total_amount;

    public AccountIdAndTotalAmountModel(String account_id, double total_amount) {

        this.account_id = account_id;
        this.total_amount = total_amount;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
}
