package com.example.testproject.model.livestock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LiveStockDataModel {

    public String getStages() {
        return stages;
    }

    public void setStages(String stages) {
        this.stages = stages;
    }

    public String getSeasons() {
        return seasons;
    }

    public void setSeasons(String seasons) {
        this.seasons = seasons;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("scientificName")
    @Expose
    private String scientificName;

    @SerializedName("commonName")
    @Expose
    private String commonName;

    @SerializedName("name")
    @Expose
    private String name;


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<VarietyModel> getVariety() {
        return variety;
    }

    public void setVariety(List<VarietyModel> variety) {
        this.variety = variety;
    }

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("variety")
    @Expose
    private List<VarietyModel> variety;
    @SerializedName("stage")
    @Expose
    private String stages;
    @SerializedName("seasons")
    @Expose
    private String seasons;

    @SerializedName("quantity")
    @Expose
    private String quantity;



    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
