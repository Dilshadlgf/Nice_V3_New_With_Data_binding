package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CropDataModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("activeStatus")
    @Expose
    private Boolean activeStatus;
    @SerializedName("area")
    @Expose
    private String area;

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    @SerializedName("scientificName")
    @Expose
    private String scientificName;
    @SerializedName("crop")
    @Expose
    private String crop;
    @SerializedName("interCrop")
    @Expose
    private String interCrop;
    @SerializedName("farmer")
    @Expose
    private String farmer;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("irrigation")
    @Expose
    private String irrigation;
    @SerializedName("season")
    @Expose
    private String season;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("veriety")
    @Expose
    private String veriety;
    @SerializedName("yeild")
    @Expose
    private Integer yeild;
    @SerializedName("yieldUnit")
    @Expose
    private Integer yieldUnit;
    @SerializedName("inputCost")
    @Expose
    private Integer inputCost;
    @SerializedName("yieldValue")
    @Expose
    private Integer yieldValue;
    @SerializedName("consumption")
    @Expose
    private String consumption;
    @SerializedName("completedDate")
    @Expose
    private String completedDate;

    @SerializedName("ref")
    private CropRef ref;



    @SerializedName("created")
    private CreatedOn created;


    @SerializedName("commonName")
    @Expose
    private String commonName;

    public CreatedOn getCreated() {
        return created;
    }

    public void setCreated(CreatedOn created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getInterCrop() {
        return interCrop;
    }

    public void setInterCrop(String interCrop) {
        this.interCrop = interCrop;
    }

    public String getFarmer() {
        return farmer;
    }

    public void setFarmer(String farmer) {
        this.farmer = farmer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIrrigation() {
        return irrigation;
    }

    public void setIrrigation(String irrigation) {
        this.irrigation = irrigation;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getVeriety() {
        return veriety;
    }

    public void setVeriety(String veriety) {
        this.veriety = veriety;
    }

    public Integer getYeild() {
        return yeild;
    }

    public void setYeild(Integer yeild) {
        this.yeild = yeild;
    }

    public Integer getYieldUnit() {
        return yieldUnit;
    }

    public void setYieldUnit(Integer yieldUnit) {
        this.yieldUnit = yieldUnit;
    }

    public Integer getInputCost() {
        return inputCost;
    }

    public void setInputCost(Integer inputCost) {
        this.inputCost = inputCost;
    }

    public Integer getYieldValue() {
        return yieldValue;
    }

    public void setYieldValue(Integer yieldValue) {
        this.yieldValue = yieldValue;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public CropRef getRef() {
        return ref;
    }

    public void setRef(CropRef ref) {
        this.ref = ref;
    }

}
