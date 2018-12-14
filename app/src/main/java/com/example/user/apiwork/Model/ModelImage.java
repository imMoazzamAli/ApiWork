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

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
    }

    public String getCustomer_api_key() {
        return customer_api_key;
    }

    public void setCustomer_api_key(String customer_api_key) {
        this.customer_api_key = customer_api_key;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
