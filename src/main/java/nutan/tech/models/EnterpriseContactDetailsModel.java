package nutan.tech.models;

public class EnterpriseContactDetailsModel {

    private int phone_type_code;
    private String enterprise_id, phone_type, area_code, number, email_main, email_ccc, website;

    public EnterpriseContactDetailsModel() {

    }

    public EnterpriseContactDetailsModel(String enterprise_id, int phone_type_code, String area_code, String number, String email_main, String email_ccc, String website) {

        this.enterprise_id = enterprise_id;
        this.phone_type_code = phone_type_code;
        this.area_code = area_code;
        this.number = number;
        this.email_main = email_main;
        this.email_ccc = email_ccc;
        this.website = website;
    }

    public EnterpriseContactDetailsModel(int phone_type_code, String phone_type, String area_code, String number, String email_main, String email_ccc, String website) {

        this.phone_type_code = phone_type_code;
        this.phone_type = phone_type;
        this.area_code = area_code;
        this.number = number;
        this.email_main = email_main;
        this.email_ccc = email_ccc;
        this.website = website;
    }

    public String getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(String enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    public String getPhone_type() {
        return phone_type;
    }

    public void setPhone_type(String phone_type) {
        this.phone_type = phone_type;
    }

    public int getPhone_type_code() {
        return phone_type_code;
    }

    public void setPhone_type_code(int phone_type_code) {
        this.phone_type_code = phone_type_code;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail_main() {
        return email_main;
    }

    public void setEmail_main(String email_main) {
        this.email_main = email_main;
    }

    public String getEmail_ccc() {
        return email_ccc;
    }

    public void setEmail_ccc(String email_ccc) {
        this.email_ccc = email_ccc;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
