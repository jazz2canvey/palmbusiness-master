package nutan.tech.models;

public class EnterpriseAddressModel {

    private String enterprise_id, billing_address, shipping_address, state_province, country;

    public EnterpriseAddressModel() {

    }

    public EnterpriseAddressModel(String enterprise_id, String billing_address, String shipping_address, String state_province, String country) {

        this.enterprise_id = enterprise_id;
        this.billing_address = billing_address;
        this.shipping_address = shipping_address;
        this.state_province = state_province;
        this.country = country;
    }

    public String getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(String enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    public String getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(String billing_address) {
        this.billing_address = billing_address;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getState_province() {
        return state_province;
    }

    public void setState_province(String state_province) {
        this.state_province = state_province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
