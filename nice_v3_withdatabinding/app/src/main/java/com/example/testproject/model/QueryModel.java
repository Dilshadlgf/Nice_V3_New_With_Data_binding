package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QueryModel {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("query")
    @Expose
    public String query;
    @SerializedName("uniqueId")
    @Expose
    public String uniqueId;
    @SerializedName("queryType")
    @Expose
    public String queryType;
    @SerializedName("activeStatus")
    @Expose
    public Boolean activeStatus;
    @SerializedName("isSolutionSent")
    @Expose
    public Boolean isSolutionSent;

    @SerializedName("createdBy")
    @Expose
    public String createdBy;
    @SerializedName("createdType")
    @Expose
    public String createdType;
    @SerializedName("solution")
    @Expose
    public String solution;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("resolvedDate")
    @Expose
    public String resolvedDate;
    @SerializedName("contentId")
    @Expose
    public String contentId;
    @SerializedName("knowledgeDomain")
    @Expose
    public String knowledgeDomain;
    @SerializedName("subDomain")
    @Expose
    public String subDomain;
    @SerializedName("gramPanchayat")
    @Expose
    public String gramPanchayat;
    @SerializedName("village")
    @Expose
    public String village;
    @SerializedName("version")
    @Expose
    public Integer version;
    @SerializedName("assignedTo")
    @Expose
    public String assignedTo;
    @SerializedName("assignedDate")
    @Expose
    public Object assignedDate;
    @SerializedName("resolvedBy")
    @Expose
    public String resolvedBy;
    @SerializedName("rating")
    @Expose
    public String rating;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("block")
    @Expose
    public String block;
    @SerializedName("district")
    @Expose
    public String district;
    @SerializedName("images")
    @Expose
    public List<String> images;
    @SerializedName("farmer")
    @Expose
    public String farmer;
    @SerializedName("ref")
    @Expose
    public RefModel ref;

}
