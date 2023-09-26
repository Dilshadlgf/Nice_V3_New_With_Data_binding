package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LivestockModel {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("activeStatus")
    @Expose
    public Boolean activeStatus;
    @SerializedName("liveStock")
    @Expose
    public String liveStock;
    public String name;
    public String icon;
    @SerializedName("farmer")
    @Expose
    public String farmer;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("stage")
    @Expose
    public String stage;
    @SerializedName("version")
    @Expose
    public Integer version;
    @SerializedName("quantity")
    @Expose
    public Integer quantity;
    @SerializedName("veriety")
    @Expose
    public String veriety;
    @SerializedName("ref")
    @Expose
    public RefModel ref;

    public String commonName;
    public String scientificName;
    public String category;
    public String function;

    public ProjectModel state;
    public ProjectModel district;
    public ProjectModel block;
    public ProjectModel gramPanchayat;
    public ProjectModel village;
    public UserModel assignedTo;
    public UserModel resolvedBy;
    public UserModel createdByFarmer;
    public UserModel createdByUser;

}
