package com.example.testproject.model.livestock;

import com.example.testproject.model.FarmerModel;
import com.example.testproject.model.varietymodel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RefLiveStockModel {


    @SerializedName("veriety")
    @Expose
    private varietymodel veriety;

    public FarmerModel getCreatedByFarmer() {
        return createdByFarmer;
    }

    public void setCreatedByFarmer(FarmerModel createdByFarmer) {
        this.createdByFarmer = createdByFarmer;
    }


    private FarmerModel createdByFarmer;
}
