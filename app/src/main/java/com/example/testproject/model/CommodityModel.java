package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommodityModel {

    @SerializedName("id")
    @Expose
    public String id;
    public String name;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("function")
    @Expose
    public String function;
    @SerializedName("insects")
    @Expose
    public Object insects;
    @SerializedName("diseases")
    @Expose
    public Object diseases;
}
