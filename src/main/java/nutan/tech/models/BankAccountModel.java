package nutan.tech.models;

public class BankAccountModel {

    private String bank_account_id, enterprise_id, bank_name, account_holder, account_number, ifsc_code, branch, state, country, zip_pin_code;
    private double opening_balance;

    private boolean isExist = false;
    private String message = null;

    public BankAccountModel() {

    }

    public BankAccountModel(boolean isExist, String message) {

        this.isExist = isExist;
        this.message = message;
    }

    public BankAccountModel(String bank_account_id, String enterprise_id, String bank_name, String account_holder, String account_number, String ifsc_code, double opening_balance, String branch, String state, String country, String zip_pin_code) {

        this.bank_account_id = bank_account_id;
        this.enterprise_id = enterprise_id;
        this.bank_name = bank_name;
        this.account_holder = account_holder;
        this.account_number = account_number;
        this.ifsc_code = ifsc_code;
        this.opening_balance = opening_balance;
        this.branch = branch;
        this.state = state;
        this.country = country;
        this.zip_pin_code = zip_pin_code;
    }

    public String getBank_account_id() {
        return bank_account_id;
    }

    public void setBank_account_id(String bank_account_id) {
        this.bank_account_id = bank_account_id;
    }

    public String getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(String enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_holder() {
        return account_holder;
    }

    public void setAccount_holder(String account_holder) {
        this.account_holder = account_holder;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public double getOpening_balance() {
        return opening_balance;
    }

    public void setOpening_balance(double opening_balance) {
        this.opening_balance = opening_balance;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
