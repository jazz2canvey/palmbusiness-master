package nutan.tech.models;

public class ReportContraVoucherModel {

    private String contra_voucher_id, entry_date, contra_title, payment_mode, giver, receiver, note;
    private double amount;

    public ReportContraVoucherModel() {

    }

    public ReportContraVoucherModel(String contra_voucher_id, String entry_date, String contra_title, String payment_mode, String giver, String receiver, String note, double amount) {

        this.contra_voucher_id = contra_voucher_id;
        this.entry_date = entry_date;
        this.contra_title = contra_title;
        this.payment_mode = payment_mode;
        this.giver = giver;
        this.receiver = receiver;
        this.note = note;
        this.amount = amount;
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

    public String getContra_title() {
        return contra_title;
    }

    public void setContra_title(String contra_title) {
        this.contra_title = contra_title;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
