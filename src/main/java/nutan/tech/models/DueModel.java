package nutan.tech.models;

public class DueModel {

    private String invoice_id, id, name, entry_date, invoice_number;
    private double total_amount, payed_amount, remaining_balance;

    public DueModel() {

    }

    public DueModel(String invoice_id, String id, String name, String entry_date, String invoice_number, double total_amount, double payed_amount, double remaining_balance) {

        this.invoice_id = invoice_id;
        this.id = id;
        this.name = name;
        this.entry_date = entry_date;
        this.invoice_number = invoice_number;
        this.total_amount = total_amount;
        this.payed_amount = payed_amount;
        this.remaining_balance = remaining_balance;
    }


    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(String entry_date) {
		this.entry_date = entry_date;
	}

	public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getPayed_amount() {
        return payed_amount;
    }

    public void setPayed_amount(double payed_amount) {
        this.payed_amount = payed_amount;
    }

    public double getRemaining_balance() {
        return remaining_balance;
    }

    public void setRemaining_balance(double remaining_balance) {
        this.remaining_balance = remaining_balance;
    }
}
