package com.example.testproject.model.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RootQueryModelTwo {
    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    @Expose
    private DataQueryModel dataQueryModel;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataQueryModel getDataQueryModel() {
        return dataQueryModel;
    }

    public void setDataQueryModel(DataQueryModel dataQueryModel) {
        this.dataQueryModel = dataQueryModel;
    }
}
