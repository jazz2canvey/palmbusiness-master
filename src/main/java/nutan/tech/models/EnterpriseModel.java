package nutan.tech.models;

public class EnterpriseModel {

    private String enterprise_id, enterprise_logo, enterprise_name, fiscal_year, username, password;

    public EnterpriseModel() {

    }

    public EnterpriseModel(String enterprise_id, String enterprise_logo, String enterprise_name, String fiscal_year, String username, String password) {

        this.enterprise_id = enterprise_id;
        this.enterprise_logo = enterprise_logo;
        this.enterprise_name = enterprise_name;
        this.fiscal_year = fiscal_year;
        this.username = username;
        this.password = password;
    }

    public String getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(String enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    public String getEnterprise_logo() {
        return enterprise_logo;
    }

    public void setEnterprise_logo(String enterprise_logo) {
        this.enterprise_logo = enterprise_logo;
    }

    public String getEnterprise_name() {
        return enterprise_name;
    }

    public void setEnterprise_name(String enterprise_name) {
        this.enterprise_name = enterprise_name;
    }

    public String getFiscal_year() {
        return fiscal_year;
    }

    public void setFiscal_year(String fiscal_year) {
        this.fiscal_year = fiscal_year;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
