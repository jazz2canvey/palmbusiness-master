package nutan.tech.models;

public class IndirectIncomeModel {

    private String indirect_income_id, enterprise_id, entry_date, indirect_income, credited_account_id, description, payment_mode;
    private double amount;
    private int payment_mode_code;

    public IndirectIncomeModel() {

    }

    public IndirectIncomeModel(String indirect_income_id, String enterprise_id, String entry_date, String indirect_income, String credited_account_id, String description, double amount, int payment_mode_code) {

        this.indirect_income_id = indirect_income_id;
        this.enterprise_id = enterprise_id;
        this.entry_date = entry_date;
        this.indirect_income = indirect_income;
        this.credited_account_id = credited_account_id;
        this.description = description;
        this.amount = amount;
        this.payment_mode_code = payment_mode_code;
    }

    public IndirectIncomeModel(String indirect_income_id, String enterprise_id, String entry_date, String indirect_income, String credited_account_id, String description, int payment_mode_code, String payment_mode, double amount) {

        this.indirect_income_id = indirect_income_id;
        this.enterprise_id = enterprise_id;
        this.entry_date = entry_date;
        this.indirect_income = indirect_income;
        this.credited_account_id = credited_account_id;
        this.description = description;
        this.payment_mode_code = payment_mode_code;
        this.payment_mode = payment_mode;
        this.amount = amount;
    }

    public String getIndirect_income_id() {
        return indirect_income_id;
    }

    public void setIndirect_income_id(String indirect_income_id) {
        this.indirect_income_id = indirect_income_id;
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

    public String getIndirect_income() {
        return indirect_income;
    }

    public void setIndirect_income(String indirect_income) {
        this.indirect_income = indirect_income;
    }

    public String getCredited_account_id() {
        return credited_account_id;
    }

    public void setCredited_account_id(String credited_account_id) {
        this.credited_account_id = credited_account_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayment_mode_code() {
        return payment_mode_code;
    }

    public void setPayment_mode_code(int payment_mode_code) {
        this.payment_mode_code = payment_mode_code;
    }
}
