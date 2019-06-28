package nutan.tech.models;

public class PaymentsReceivedModel {

    private String entry_date, customer_id, customer_name, invoice_number, payment_mode, account_name;
    private double amount;

    public PaymentsReceivedModel() {

    }

    public PaymentsReceivedModel(String entry_date, String customer_id, String customer_name, String invoice_number, String payment_mode, String account_name, double amount) {

        this.entry_date = entry_date;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.invoice_number = invoice_number;
        this.payment_mode = payment_mode;
        this.account_name = account_name;
        this.amount = amount;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
