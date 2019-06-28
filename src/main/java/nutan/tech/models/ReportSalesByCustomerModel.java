package nutan.tech.models;

public class ReportSalesByCustomerModel {

    private String customer_id, customer_name;
    private double invoice_count, total_sales_amount;

    public ReportSalesByCustomerModel() {

    }

    public ReportSalesByCustomerModel(String customer_id, String customer_name, double invoice_count, double total_sales_amount) {

        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.invoice_count = invoice_count;
        this.total_sales_amount = total_sales_amount;
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

    public double getInvoice_count() {
        return invoice_count;
    }

    public void setInvoice_count(double invoice_count) {
        this.invoice_count = invoice_count;
    }

    public double getTotal_sales_amount() {
        return total_sales_amount;
    }

    public void setTotal_sales_amount(double total_sales_amount) {
        this.total_sales_amount = total_sales_amount;
    }
}
