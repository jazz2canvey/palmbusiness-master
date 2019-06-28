package nutan.tech.models;

public class EstimateInvoiceModel {

    private double total_amount;
    private String estimate_invoice_id, enterprise_id, customer_id, customer_name, invoice_number, entry_date, valid_till, from_address, to_address, state_province, country, note;
    private boolean status;

    public EstimateInvoiceModel() {

    }

    public EstimateInvoiceModel(double total_amount, String estimate_invoice_id, String enterprise_id, String customer_id, String customer_name, String invoice_number, String entry_date, String valid_till, String from_address, String to_address, String state_province, String country, String note, boolean status) {
        this.total_amount = total_amount;
        this.estimate_invoice_id = estimate_invoice_id;
        this.enterprise_id = enterprise_id;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.invoice_number = invoice_number;
        this.entry_date = entry_date;
        this.valid_till = valid_till;
        this.from_address = from_address;
        this.to_address = to_address;
        this.state_province = state_province;
        this.country = country;
        this.note = note;
        this.status = status;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public String getEstimate_invoice_id() {
        return estimate_invoice_id;
    }

    public void setEstimate_invoice_id(String estimate_invoice_id) {
        this.estimate_invoice_id = estimate_invoice_id;
    }

    public String getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(String enterprise_id) {
        this.enterprise_id = enterprise_id;
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

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getValid_till() {
        return valid_till;
    }

    public void setValid_till(String valid_till) {
        this.valid_till = valid_till;
    }

    public String getFrom_address() {
        return from_address;
    }

    public void setFrom_address(String from_address) {
        this.from_address = from_address;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public String getState_province() {
        return state_province;
    }

    public void setState_province(String state_province) {
        this.state_province = state_province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
