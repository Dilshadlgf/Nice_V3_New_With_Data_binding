package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationModel {

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("coordinates")
    @Expose
    public List<Double> coordinates;
}
