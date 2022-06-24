package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InterCropModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("function")
    @Expose
    private String function;
    @SerializedName("insects")
    @Expose
    private Object insects;
    @SerializedName("diseases")
    @Expose
    private Object diseases;

    @SerializedName("commonName")
    private String commonName;

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    @SerializedName("created")
    private CreatedOn created;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Object getInsects() {
        return insects;
    }

    public void setInsects(Object insects) {
        this.insects = insects;
    }

    public Object getDiseases() {
        return diseases;
    }

    public void setDiseases(Object diseases) {
        this.diseases = diseases;
    }
}
