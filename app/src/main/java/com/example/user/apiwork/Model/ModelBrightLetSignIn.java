package com.example.user.apiwork.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelBrightLetSignIn {

    @SerializedName("request")
    @Expose
    String request;

    @SerializedName("customer_type")
    @Expose
    String customer_type;

    @SerializedName("customer_email")
    @Expose
    String customer_email;

    @SerializedName("customer_password")
    @Expose
    String customer_password;

    @SerializedName("action")
    @Expose
    private String action;

    @SerializedName("customer_profile_image")
    private String customer_profile_image;

    public String getAction() {
        return action;
    }

    public String getCustomer_profile_image() {
        return customer_profile_image;
    }

}
