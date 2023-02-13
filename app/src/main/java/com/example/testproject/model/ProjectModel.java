package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectModel {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("activeStatus")
    @Expose
    public Boolean activeStatus;
    @SerializedName("uniqueId")
    @Expose
    public String uniqueId;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("budget")
    @Expose
    public Integer budget;
    @SerializedName("nationalLevel")
    @Expose
    public Boolean nationalLevel;
    @SerializedName("organisation")
    @Expose
    public String organisation;
    @SerializedName("startDate")
    @Expose
    public String startDate;
    @SerializedName("endDate")
    @Expose
    public String endDate;
    @SerializedName("version")
    @Expose
    public Integer version;
    @SerializedName("remarks")
    @Expose
    public String remarks;
    @SerializedName("on")
    @Expose
    public String on;
    @SerializedName("by")
    @Expose
    public String by;
    @SerializedName("scenario")
    @Expose
    public String scenario;
    public String orgnisationName;
    public String orgnisationId;
    public String isSingle;
    public String knowledgedomainId;
    public String knowledgedomainName;
    public String projectId;
    public String projectName;
}
