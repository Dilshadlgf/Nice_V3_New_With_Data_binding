package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeasonModel {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("activestatus")
    @Expose
    public Boolean activestatus;
    @SerializedName("enddate")
    @Expose
    public Integer enddate;
    @SerializedName("endmonth")
    @Expose
    public Integer endmonth;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("startdate")
    @Expose
    public Integer startdate;
    @SerializedName("startmonth")
    @Expose
    public Integer startmonth;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("created")
    @Expose
    public Object created;
    @SerializedName("version")
    @Expose
    public Integer version;
    @SerializedName("yearPlusOne")
    @Expose
    public Boolean yearPlusOne;

}
