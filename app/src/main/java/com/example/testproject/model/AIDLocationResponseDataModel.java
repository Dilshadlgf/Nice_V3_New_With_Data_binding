package com.example.testproject.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AIDLocationResponseDataModel implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("location")
    @Expose
    private LocationDataModel location;
    @SerializedName("aidCategory")
    @Expose
    private List<AidCategoryDataModel> aidCategory = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public LocationDataModel getLocation() {
        return location;
    }

    public void setLocation(LocationDataModel location) {
        this.location = location;
    }

    public List<AidCategoryDataModel> getAidCategory() {
        return aidCategory;
    }

    public void setAidCategory(List<AidCategoryDataModel> aidCategory) {
        this.aidCategory = aidCategory;
    }

}
