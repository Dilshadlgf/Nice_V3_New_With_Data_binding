package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Farmerlistnewdatamodel implements Serializable {


    public ArrayList<FarmerListDataModel> getFarmers() {
        return farmers;
    }

    public void setFarmers(ArrayList<FarmerListDataModel> farmers) {
        this.farmers = farmers;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @SerializedName("total")
    @Expose
    private float total;
    @SerializedName("farmers")
    @Expose
    private ArrayList<FarmerListDataModel> farmers = null;



}
