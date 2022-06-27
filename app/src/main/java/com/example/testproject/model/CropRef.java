package com.example.testproject.model;

import com.google.gson.annotations.SerializedName;


public class CropRef {


    @SerializedName("LiveStocks")
    private Integer LiveStocks;

    @SerializedName("Crops")
    private Integer Crops;

    @SerializedName("crop")
    private CropDataModel crop;

    @SerializedName("interCrop")
    private InterCropModel interCrop;




    @SerializedName("variety")
    private varietymodel variety;


    @SerializedName("season")
    private SeasonDataModel season;

    public FarmerOrg getFarmer() {
        return farmer;
    }

    public void setFarmer(FarmerOrg farmer) {
        this.farmer = farmer;
    }

    @SerializedName("farmer")
    private FarmerOrg farmer;

    @SerializedName("state")
    private StateDataModel state;


    @SerializedName("commodity")
    private CropDataModel commodity;

    public CropDataModel getCommodity() {
        return commodity;
    }

    public void setCommodity(CropDataModel commodity) {
        this.commodity = commodity;
    }


    public varietymodel getVariety() {
        return variety;
    }

    public void setVariety(varietymodel variety) {
        this.variety = variety;
    }

    public StateDataModel getState() {
        return state;
    }

    public void setState(StateDataModel state) {
        this.state = state;
    }

    public CropDataModel getCrop() {
        return crop;
    }

    public void setCrop(CropDataModel crop) {
        this.crop = crop;
    }

    public InterCropModel getInterCrop() {
        return interCrop;
    }

    public void setInterCrop(InterCropModel interCrop) {
        this.interCrop = interCrop;
    }



    public SeasonDataModel getSeason() {
        return season;
    }

    public void setSeason(SeasonDataModel season) {
        this.season = season;
    }



    public Integer getLiveStocks() {
        return LiveStocks;
    }

    public void setLiveStocks(Integer liveStocks) {
        LiveStocks = liveStocks;
    }

    public Integer getCrops() {
        return Crops;
    }

    public void setCrops(Integer crops) {
        Crops = crops;
    }
}
