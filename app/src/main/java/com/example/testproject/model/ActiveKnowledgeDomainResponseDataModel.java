package com.example.testproject.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ActiveKnowledgeDomainResponseDataModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("activeStatus")
    @Expose
    private Boolean activeStatus;
    @SerializedName("subDomains")
    @Expose
    private List<SubDomainDataModel> subDomains = null;

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

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public List<SubDomainDataModel> getSubDomains() {
        return subDomains;
    }

    public void setSubDomains(List<SubDomainDataModel> subDomains) {
        this.subDomains = subDomains;
    }

}
