package nutan.tech.models;

public class PaymentReceiptModel {

	private int party_code, mover_code, bank_payment_mode;
	private double amount;
	private boolean payed_via_bank, payed_via_cash;
	private String payment_receipt_id, enterprise_id, entry_date, vendor_customer, invoice_number, account_id, description;

	private String party_value, mover_value, name, payment_mode, account_name;

	public PaymentReceiptModel() {
		
	}

	public PaymentReceiptModel(int party_code, int mover_code, int bank_payment_mode, double amount, boolean payed_via_bank, boolean payed_via_cash, String payment_receipt_id, String enterprise_id, String entry_date, String vendor_customer, String invoice_number, String account_id, String description) {
		this.party_code = party_code;
		this.mover_code = mover_code;
		this.bank_payment_mode = bank_payment_mode;
		this.amount = amount;
		this.payed_via_bank = payed_via_bank;
		this.payed_via_cash = payed_via_cash;
		this.payment_receipt_id = payment_receipt_id;
		this.enterprise_id = enterprise_id;
		this.entry_date = entry_date;
		this.vendor_customer = vendor_customer;
		this.invoice_number = invoice_number;
		this.account_id = account_id;
		this.description = description;
	}

	public PaymentReceiptModel(boolean payed_via_bank, boolean payed_via_cash, String payment_receipt_id, String enterprise_id, String entry_date, String vendor_customer, String invoice_number, String account_id, String description, String party_value, String mover_value, String name, String payment_mode, String account_name, double amount) {

		this.payed_via_bank = payed_via_bank;
		this.payed_via_cash = payed_via_cash;
		this.payment_receipt_id = payment_receipt_id;
		this.enterprise_id = enterprise_id;
		this.entry_date = entry_date;
		this.vendor_customer = vendor_customer;
		this.invoice_number = invoice_number;
		this.account_id = account_id;
		this.description = description;
		this.party_value = party_value;
		this.mover_value = mover_value;
		this.name = name;
		this.payment_mode = payment_mode;
		this.account_name = account_name;
		this.amount = amount;
	}

	public int getParty_code() {
		return party_code;
	}

	public void setParty_code(int party_code) {
		this.party_code = party_code;
	}

	public int getMover_code() {
		return mover_code;
	}

	public void setMover_code(int mover_code) {
		this.mover_code = mover_code;
	}

	public int getBank_payment_mode() {
		return bank_payment_mode;
	}

	public void setBank_payment_mode(int bank_payment_mode) {
		this.bank_payment_mode = bank_payment_mode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isPayed_via_bank() {
		return payed_via_bank;
	}

	public void setPayed_via_bank(boolean payed_via_bank) {
		this.payed_via_bank = payed_via_bank;
	}

	public boolean isPayed_via_cash() {
		return payed_via_cash;
	}

	public void setPayed_via_cash(boolean payed_via_cash) {
		this.payed_via_cash = payed_via_cash;
	}

	public String getPayment_receipt_id() {
		return payment_receipt_id;
	}

	public void setPayment_receipt_id(String payment_receipt_id) {
		this.payment_receipt_id = payment_receipt_id;
	}

	public String getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(String enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public String getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(String entry_date) {
		this.entry_date = entry_date;
	}

	public String getVendor_customer() {
		return vendor_customer;
	}

	public void setVendor_customer(String vendor_customer) {
		this.vendor_customer = vendor_customer;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParty_value() {
		return party_value;
	}

	public void setParty_value(String party_value) {
		this.party_value = party_value;
	}

	public String getMover_value() {
		return mover_value;
	}

	public void setMover_value(String mover_value) {
		this.mover_value = mover_value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
