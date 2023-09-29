package com.example.testproject.model;

import com.google.gson.JsonElement;

public class RootTwoModelJsonElement {
    private String message;
    private int statusCode;
    private JsonElement data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}
