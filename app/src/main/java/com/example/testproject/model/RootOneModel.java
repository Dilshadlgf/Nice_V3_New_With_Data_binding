package com.example.testproject.model;

import com.google.gson.annotations.SerializedName;

public class RootOneModel {

    @SerializedName("response")
    private RootTwoModel response;


    public RootTwoModel getResponse() {
        return response;
    }

    public void setResponse(RootTwoModel response) {
        this.response = response;
    }
}
