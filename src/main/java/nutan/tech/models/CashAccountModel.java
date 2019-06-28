package nutan.tech.models;

public class CashAccountModel {

    private String cash_account_id, enterprise_id, account_holder, locality, state, country, zip_pin_code;
    private double opening_balance;

    public CashAccountModel() {

    }

    public CashAccountModel(String cash_account_id, String enterprise_id, String account_holder, String locality, String state, String country, String zip_pin_code, double opening_balance) {

        this.cash_account_id = cash_account_id;
        this.enterprise_id = enterprise_id;
        this.account_holder = account_holder;
        this.locality = locality;
        this.state = state;
        this.country = country;
        this.zip_pin_code = zip_pin_code;
        this.opening_balance = opening_balance;
    }

    public String getCash_account_id() {
        return cash_account_id;
    }

    public void setCash_account_id(String cash_account_id) {
        this.cash_account_id = cash_account_id;
    }

    public String getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(String enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    public String getAccount_holder() {
        return account_holder;
    }

    public void setAccount_holder(String account_holder) {
        this.account_holder = account_holder;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip_pin_code() {
        return zip_pin_code;
    }

    public void setZip_pin_code(String zip_pin_code) {
        this.zip_pin_code = zip_pin_code;
    }

    public double getOpening_balance() {
        return opening_balance;
    }

    public void setOpening_balance(double opening_balance) {
        this.opening_balance = opening_balance;
    }
}
