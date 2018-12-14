package com.example.user.apiwork.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSignInData {

    @SerializedName("data")
    @Expose
    private ModelSignIn data;

    public ModelSignIn getData() {
        return data;
    }

    public void setData(ModelSignIn data) {
        this.data = data;
    }


}
