package nutan.tech.models;

public class ReportVendorBalanceModel {

    private String vendor_id, vendor_name;
    private double payable_amount, paid_amount;

    public ReportVendorBalanceModel() {

    }

    public ReportVendorBalanceModel(String vendor_id, String vendor_name, double payable_amount, double paid_amount) {

        this.vendor_id = vendor_id;
        this.vendor_name = vendor_name;
        this.payable_amount = payable_amount;
        this.paid_amount = paid_amount;
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

    public double getPayable_amount() {
        return payable_amount;
    }

    public void setPayable_amount(double payable_amount) {
        this.payable_amount = payable_amount;
    }

    public double getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(double paid_amount) {
        this.paid_amount = paid_amount;
    }
}
