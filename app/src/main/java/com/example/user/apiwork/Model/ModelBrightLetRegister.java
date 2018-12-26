package com.example.user.apiwork.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelBrightLetRegister {

    @SerializedName("request")
    @Expose
    String request;

    @SerializedName("customer_type")
    @Expose
    String customer_type;

    @SerializedName("customer_name")
    @Expose
    String customer_name;

    @SerializedName("customer_email")
    @Expose
    String customer_email;

    @SerializedName("customer_password")
    @Expose
    String customer_password;

    @SerializedName("customer_mobile_number")
    @Expose
    String customer_mobile_number;

    @SerializedName("customer_share_data")
    @Expose
    String customer_share_data;

    @SerializedName("remote_address")
    @Expose
    String remote_address;

    @SerializedName("user_agent")
    @Expose
    String user_agent;

    @SerializedName("success")
    @Expose
    String success;

    @SerializedName("error")
    @Expose
    String error;

    public ModelBrightLetRegister(String request, String customer_type, String customer_name, String customer_email, String customer_password, String customer_mobile_number, String customer_share_data, String remote_address, String user_agent) {
        this.request = request;
        this.customer_type = customer_type;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.customer_password = customer_password;
        this.customer_mobile_number = customer_mobile_number;
        this.customer_share_data = customer_share_data;
        this.remote_address = remote_address;
        this.user_agent = user_agent;
    }

    public String getError() {
        return error;
    }

    public String getSuccess() {
        return success;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_password() {
        return customer_password;
    }

    public void setCustomer_password(String customer_password) {
        this.customer_password = customer_password;
    }

    public String getCustomer_mobile_number() {
        return customer_mobile_number;
    }

    public void setCustomer_mobile_number(String customer_mobile_number) {
        this.customer_mobile_number = customer_mobile_number;
    }

    public String getCustomer_share_data() {
        return customer_share_data;
    }

    public void setCustomer_share_data(String customer_share_data) {
        this.customer_share_data = customer_share_data;
    }

    public String getRemote_address() {
        return remote_address;
    }

    public void setRemote_address(String remote_address) {
        this.remote_address = remote_address;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }
}
