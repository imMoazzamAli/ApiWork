package com.example.user.apiwork.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSignIn {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /*private String request, customer_type, customer_email, customer_password;

    public ModelSignIn() {
    }

    public ModelSignIn(String request, String customer_type, String customer_email, String customer_password) {
        this.request = request;
        this.customer_type = customer_type;
        this.customer_email = customer_email;
        this.customer_password = customer_password;
    }

    public String getRequest() {
        return request;
    }

    public String getCustomer_type() {
        return customer_type;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public String getCustomer_password() {
        return customer_password;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public void setCustomer_password(String customer_password) {
        this.customer_password = customer_password;
    }*/
}
