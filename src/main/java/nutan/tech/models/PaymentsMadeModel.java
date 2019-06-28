package nutan.tech.models;

public class PaymentsMadeModel {

    private String entry_date, vendor_id, vendor_name, invoice_number, payment_mode, account_name;
    private double amount;

    public PaymentsMadeModel() {

    }

    public PaymentsMadeModel(String entry_date, String vendor_id, String vendor_name, String invoice_number, String payment_mode, String account_name, double amount) {

        this.entry_date = entry_date;
        this.vendor_id = vendor_id;
        this.vendor_name = vendor_name;
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

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
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
