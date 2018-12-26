package com.example.user.apiwork.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelBrightLetSignIn {

    @SerializedName("request")
    String request;

    @SerializedName("customer_type")
    String customer_type;

    @SerializedName("customer_email")
    String customer_email;

    @SerializedName("customer_password")
    String customer_password;

    //responses below

    @SerializedName("action")
    private String action;

    @SerializedName("error")
    private String error;

    @SerializedName("customer_profile_image")
    private String customer_profile_image;

    public String getAction() {
        return action;
    }

    public String getError() {
        return error;
    }

    public String getCustomer_profile_image() {
        return customer_profile_image;
    }

}
