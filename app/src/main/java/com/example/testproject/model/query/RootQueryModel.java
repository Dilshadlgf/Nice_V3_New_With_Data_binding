package com.example.testproject.model.query;

import com.google.gson.annotations.SerializedName;

public class RootQueryModel {
    @SerializedName("response")
    private RootQueryModelTwo response;

    public RootQueryModelTwo getResponse() {
        return response;
    }

    public void setResponse(RootQueryModelTwo response) {
        this.response = response;
    }
}
