package nutan.tech.models;

public class ReportSalesByItemModel {

    private String item_id, item_name, measurement;
    private double total_quantity, total_amount;

    public ReportSalesByItemModel() {

    }

    public ReportSalesByItemModel(String item_id, String item_name, String measurement, double total_quantity, double total_amount) {

        this.item_id = item_id;
        this.item_name = item_name;
        this.measurement = measurement;
        this.total_quantity = total_quantity;
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

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public double getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(double total_quantity) {
        this.total_quantity = total_quantity;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
}
