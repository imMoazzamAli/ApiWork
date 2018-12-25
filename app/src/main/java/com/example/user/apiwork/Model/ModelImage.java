package com.example.user.apiwork.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelImage {

//    https://api-webservices.brightlet.com/api_media_upload?
// request=upload-profile-image&
// customer_id=onqfqQ==&
// customer_type=private-landlord&
// customer_api_key=9e52c2ea3e73465e&
// images=

    @SerializedName("request")
    @Expose
    String request;

    @SerializedName("customer_id")
    @Expose
    String customer_id;

    @SerializedName("customer_type")
    @Expose
    String customer_type;

    @SerializedName("customer_api_key")
    @Expose
    String customer_api_key;

    @SerializedName("images")
    @Expose
    String images;

    @SerializedName("success")
    String success;

    @SerializedName("error")
    String error;

    public String getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

}