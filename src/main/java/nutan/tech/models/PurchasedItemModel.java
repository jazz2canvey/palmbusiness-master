package nutan.tech.models;

public class PurchasedItemModel {

    private String item_id, enterprise_id, vendor_id, invoice_no, measurement, hsn_o_sac;
    private int item_type_code, discount_type_code;
    private double purchase_price, units, sgst, cgst, igst, taken_discount, total_amount;

    public PurchasedItemModel() {

    }

    public PurchasedItemModel(String item_id, String enterprise_id, String vendor_id, String invoice_no, String hsn_o_sac, String measurement, int item_type_code, int discount_type_code, double purchase_price, double units, double sgst, double cgst, double igst, double taken_discount, double total_amount) {
        this.item_id = item_id;
        this.enterprise_id = enterprise_id;
        this.vendor_id = vendor_id;
        this.invoice_no = invoice_no;
        this.hsn_o_sac = hsn_o_sac;
        this.measurement = measurement;
        this.item_type_code = item_type_code;
        this.discount_type_code = discount_type_code;
        this.purchase_price = purchase_price;
        this.units = units;
        this.sgst = sgst;
        this.cgst = cgst;
        this.igst = igst;
        this.taken_discount = taken_discount;
        this.total_amount = total_amount;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
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

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getHsn_o_sac() {
        return hsn_o_sac;
    }

    public void setHsn_o_sac(String hsn_o_sac) {
        this.hsn_o_sac = hsn_o_sac;
    }

    public int getItem_type_code() {
        return item_type_code;
    }

    public void setItem_type_code(int item_type_code) {
        this.item_type_code = item_type_code;
    }

    public int getDiscount_type_code() {
        return discount_type_code;
    }

    public void setDiscount_type_code(int discount_type_code) {
        this.discount_type_code = discount_type_code;
    }

    public double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    public double getSgst() {
        return sgst;
    }

    public void setSgst(double sgst) {
        this.sgst = sgst;
    }

    public double getCgst() {
        return cgst;
    }

    public void setCgst(double cgst) {
        this.cgst = cgst;
    }

    public double getIgst() {
        return igst;
    }

    public void setIgst(double igst) {
        this.igst = igst;
    }

    public double getTaken_discount() {
        return taken_discount;
    }

    public void setTaken_discount(double taken_discount) {
        this.taken_discount = taken_discount;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
}
