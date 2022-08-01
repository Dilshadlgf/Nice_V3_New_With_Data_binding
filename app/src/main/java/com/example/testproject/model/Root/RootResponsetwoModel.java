package com.example.testproject.model.Root;

import com.example.testproject.model.DataModel;
import com.google.gson.annotations.SerializedName;

public class RootResponsetwoModel {
    @SerializedName("statusCode")
    public int statusCode;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public DataModelOne data;
}
