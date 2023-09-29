package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressModel {

    @SerializedName("id")
    @Expose
    public String id;

    public String stateId;

    @SerializedName("uniqueId")
    @Expose
    public String uniqueId;
    @SerializedName("gramPanchayat")
    @Expose
    public String gramPanchayat;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("activeStatus")
    @Expose
    public Boolean activeStatus;
//    @SerializedName("createdOn")
//    @Expose
//    public CreatedOn__8 createdOn;
    @SerializedName("location")
    @Expose
    public LocationModel location;
    @SerializedName("population")
    @Expose
    public Integer population;
    @SerializedName("villageHead")
    @Expose
    public String villageHead;
    @SerializedName("commiteeDetails")
    @Expose
    public String commiteeDetails;
    @SerializedName("schoolName")
    @Expose
    public String schoolName;
    @SerializedName("fieldAgent")
    @Expose
    public String fieldAgent;
    @SerializedName("version")
    @Expose
    public Integer version;

    public RefModel ref;

}
