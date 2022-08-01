package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CropSeasonDataModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("activestatus")
    @Expose
    private Boolean activestatus;
    @SerializedName("enddate")
    @Expose
    private Integer enddate;
    @SerializedName("endmonth")
    @Expose
    private Integer endmonth;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("startdate")
    @Expose
    private Integer startdate;
    @SerializedName("startmonth")
    @Expose
    private Integer startmonth;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("status")
    @Expose
    private String status;



    @SerializedName("yearPlusOne")
    @Expose
    private Boolean yearPlusOne;

    @SerializedName("version")
    @Expose
    private Integer version;

    @SerializedName("ref")
    private FarmerRefModel ref;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getActivestatus() {
        return activestatus;
    }

    public void setActivestatus(Boolean activestatus) {
        this.activestatus = activestatus;
    }

    public Integer getEnddate() {
        return enddate;
    }

    public void setEnddate(Integer enddate) {
        this.enddate = enddate;
    }

    public Integer getEndmonth() {
        return endmonth;
    }

    public void setEndmonth(Integer endmonth) {
        this.endmonth = endmonth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStartdate() {
        return startdate;
    }

    public void setStartdate(Integer startdate) {
        this.startdate = startdate;
    }

    public Integer getStartmonth() {
        return startmonth;
    }

    public void setStartmonth(Integer startmonth) {
        this.startmonth = startmonth;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getYearPlusOne() {
        return yearPlusOne;
    }

    public void setYearPlusOne(Boolean yearPlusOne) {
        this.yearPlusOne = yearPlusOne;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public FarmerRefModel getRef() {
        return ref;
    }

    public void setRef(FarmerRefModel ref) {
        this.ref = ref;
    }
}