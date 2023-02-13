package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CropModel {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("activeStatus")
    @Expose
    public Boolean activeStatus;
    @SerializedName("area")
    @Expose
    public String area;
    @SerializedName("crop")
    @Expose
    public String crop;
    @SerializedName("interCrop")
    @Expose
    public String interCrop;
    @SerializedName("farmer")
    @Expose
    public String farmer;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("irrigation")
    @Expose
    public String irrigation;
    @SerializedName("season")
    @Expose
    public String season;
    @SerializedName("startDate")
    @Expose
    public String startDate;
    @SerializedName("unit")
    @Expose
    public String unit;
    @SerializedName("variety")
    @Expose
    public String variety;
    @SerializedName("yeild")
    @Expose
    public Integer yeild;
    @SerializedName("ref")
    @Expose
    public RefModel ref;

    public String commonName;
    public String scientificName;
    public String category;



}
