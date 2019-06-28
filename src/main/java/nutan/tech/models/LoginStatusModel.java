package nutan.tech.models;

public class LoginStatusModel {

    private String enterprise_id;
    private boolean logged_in;

    public LoginStatusModel() {

    }

    public LoginStatusModel(String enterprise_id, boolean logged_in) {

        this.enterprise_id = enterprise_id;
        this.logged_in = logged_in;
    }

    public String getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(String enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    public boolean isLogged_in() {
        return logged_in;
    }

    public void setLogged_in(boolean logged_in) {
        this.logged_in = logged_in;
    }
}
