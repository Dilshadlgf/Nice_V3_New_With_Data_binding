package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RootTwoResModel {
    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    @Expose
    private DataModel2 dataModel2;

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

    public DataModel2 getDataModel2() {
        return dataModel2;
    }

    public void setDataModel2(DataModel2 dataModel2) {
        this.dataModel2 = dataModel2;
    }
}
