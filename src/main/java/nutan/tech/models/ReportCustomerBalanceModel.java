package nutan.tech.models;

public class ReportCustomerBalanceModel {

    private String customer_id, customer_name;
    private double receivable_amount, received_amount;

    public ReportCustomerBalanceModel() {

    }

    public ReportCustomerBalanceModel(String customer_id, String customer_name, double receivable_amount, double received_amount) {

        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.receivable_amount = receivable_amount;
        this.received_amount = received_amount;
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

	public double getReceivable_amount() {
		return receivable_amount;
	}

	public void setReceivable_amount(double receivable_amount) {
		this.receivable_amount = receivable_amount;
	}

	public double getReceived_amount() {
		return received_amount;
	}

	public void setReceived_amount(double received_amount) {
		this.received_amount = received_amount;
	}

}
