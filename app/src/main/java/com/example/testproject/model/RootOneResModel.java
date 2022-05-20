package com.example.testproject.model;

import com.google.gson.annotations.SerializedName;

public class RootOneResModel {

    @SerializedName("response")
    private RootTwoResModel response;



    public RootTwoResModel getResponse() {
        return response;
    }

    public void setResponse(RootTwoResModel response) {
        this.response = response;
    }
}
