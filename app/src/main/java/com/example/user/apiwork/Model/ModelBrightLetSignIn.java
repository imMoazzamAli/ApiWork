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

    request=login
    customer_type=private-landlord
    customer_email = landlord@brightlet.com
    customer_password=Demo_786

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


}
