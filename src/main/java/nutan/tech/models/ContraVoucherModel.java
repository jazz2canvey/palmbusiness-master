package nutan.tech.models;

public class ContraVoucherModel {

    private int contra_type_code, payment_mode;
    private String contra_voucher_id, entry_date, enterprise_id, giver, receiver, note;
    private String contra_title, mode_of_payment;
    private double amount;

    public ContraVoucherModel() {

    }

    public ContraVoucherModel(int contra_type_code, int payment_mode, String contra_voucher_id, String entry_date, String enterprise_id, String giver, String receiver, String note, double amount) {

        this.contra_type_code = contra_type_code;
        this.payment_mode = payment_mode;
        this.contra_voucher_id = contra_voucher_id;
        this.entry_date = entry_date;
        this.enterprise_id = enterprise_id;
        this.giver = giver;
        this.receiver = receiver;
        this.note = note;
        this.amount = amount;
    }

    public ContraVoucherModel(String contra_voucher_id, String entry_date, int contra_type_code, String enterprise_id, String giver, String receiver, String note, String contra_title, String mode_of_payment, double amount) {

        this.contra_voucher_id = contra_voucher_id;
        this.entry_date = entry_date;
        this.contra_type_code = contra_type_code;
        this.enterprise_id = enterprise_id;
        this.giver = giver;
        this.receiver = receiver;
        this.note = note;
        this.contra_title = contra_title;
        this.mode_of_payment = mode_of_payment;
        this.amount = amount;
    }

    public int getContra_type_code() {
        return contra_type_code;
    }

    public void setContra_type_code(int contra_type_code) {
        this.contra_type_code = contra_type_code;
    }

    public int getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(int payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getContra_voucher_id() {
        return contra_voucher_id;
    }

    public void setContra_voucher_id(String contra_voucher_id) {
        this.contra_voucher_id = contra_voucher_id;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(String enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    public String getGiver() {
        return giver;
    }

    public void setGiver(String giver) {
        this.giver = giver;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getContra_title() {
        return contra_title;
    }

    public void setContra_title(String contra_title) {
        this.contra_title = contra_title;
    }

    public String getMode_of_payment() {
        return mode_of_payment;
    }

    public void setMode_of_payment(String mode_of_payment) {
        this.mode_of_payment = mode_of_payment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
