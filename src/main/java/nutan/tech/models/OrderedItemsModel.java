package nutan.tech.models;

public class OrderedItemsModel {

    private double rate_per_item, quantity, total_amount;
    private String item_id, item_name, enterprise_id, vendor_id, invoice_number, hsn_o_sac;

    public OrderedItemsModel() {

    }

    public OrderedItemsModel(double rate_per_item, double quantity, double total_amount, String item_id, String enterprise_id, String vendor_id, String invoice_number, String hsn_o_sac) {
        this.rate_per_item = rate_per_item;
        this.quantity = quantity;
        this.total_amount = total_amount;
        this.item_id = item_id;
        this.enterprise_id = enterprise_id;
        this.vendor_id = vendor_id;
        this.invoice_number = invoice_number;
        this.hsn_o_sac = hsn_o_sac;
    }

    public OrderedItemsModel(double rate_per_item, double quantity, double total_amount, String item_id, String item_name, String enterprise_id, String vendor_id, String invoice_number, String hsn_o_sac) {

        this.rate_per_item = rate_per_item;
        this.quantity = quantity;
        this.total_amount = total_amount;
        this.item_id = item_id;
        this.item_name = item_name;
        this.enterprise_id = enterprise_id;
        this.vendor_id = vendor_id;
        this.invoice_number = invoice_number;
        this.hsn_o_sac = hsn_o_sac;
    }

    public double getRate_per_item() {
        return rate_per_item;
    }

    public void setRate_per_item(double rate_per_item) {
        this.rate_per_item = rate_per_item;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(String enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public String getHsn_o_sac() {
        return hsn_o_sac;
    }

    public void setHsn_o_sac(String hsn_o_sac) {
        this.hsn_o_sac = hsn_o_sac;
    }
}
