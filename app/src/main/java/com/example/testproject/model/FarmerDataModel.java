package com.example.testproject.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "farmer")
public class FarmerDataModel {

    @PrimaryKey(autoGenerate = true)
    private int idd;

    private String id;

    @SerializedName("name")
    private String name;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("fatherName")
    private String fatherName;

    @SerializedName("gender")
    private String gender;

    @SerializedName("dateOfBirth")
    private String dateOfBirth;

    @SerializedName("mobileNumber")
    private String mobileNumber;

    @SerializedName("email")
    private String email;

    @SerializedName("village")
    private String village;

    @SerializedName("liveStockCount")
    private String liveStockCount;
    
    @SerializedName("cropCount")
    private String cropCount;

    @SerializedName("cultivatedArea")
    private String cultivatedArea;

    @SerializedName("totalLand")
    private String totalLand;

    @SerializedName("vacantArea")
    private String vacantArea;



    @SerializedName("state")
    private String stateid;

    public String getStateIdd() {
        return stateIdd;
    }

    public void setStateIdd(String stateIdd) {
        this.stateIdd = stateIdd;
    }

    @SerializedName("stateId")
    @Expose
    private String stateIdd;

    @SerializedName("ref")
    @Embedded
    private FarmerRefModel ref;

    public String getStateid() {
        return stateid;
    }

    public void setStateid(String stateid) {
        this.stateid = stateid;
    }

    public FarmerRefModel getRef() {
        return ref;
    }

    public void setRef(FarmerRefModel ref) {
        this.ref = ref;
    }

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLiveStockCount() {
        return liveStockCount;
    }

    public void setLiveStockCount(String liveStockCount) {
        this.liveStockCount = liveStockCount;
    }

    public String getCropCount() {
        return cropCount;
    }

    public void setCropCount(String cropCount) {
        this.cropCount = cropCount;
    }

    public String getCultivatedArea() {
        return cultivatedArea;
    }

    public void setCultivatedArea(String cultivatedArea) {
        this.cultivatedArea = cultivatedArea;
    }

    public String getTotalLand() {
        return totalLand;
    }

    public void setTotalLand(String totalLand) {
        this.totalLand = totalLand;
    }

    public String getVacantArea() {
        return vacantArea;
    }

    public void setVacantArea(String vacantArea) {
        this.vacantArea = vacantArea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }
}
