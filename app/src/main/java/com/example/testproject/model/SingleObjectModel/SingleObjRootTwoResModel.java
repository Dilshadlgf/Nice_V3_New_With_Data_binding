package com.example.testproject.model.SingleObjectModel;

public class SingleObjRootTwoResModel {

    private int statusCode;
    private String message;
    private SingleObjRootDataModel data;

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

    public SingleObjRootDataModel getData() {
        return data;
    }

    public void setData(SingleObjRootDataModel data) {
        this.data = data;
    }
}
