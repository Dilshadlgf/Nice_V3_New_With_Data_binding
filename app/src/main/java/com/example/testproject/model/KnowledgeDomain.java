package com.example.testproject.model;

//import com.example.testproject.model.farmermodel.CreatedOn;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KnowledgeDomain {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("activeStatus")
    @Expose
    private Boolean activeStatus;

    @SerializedName("description")
    @Expose
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
