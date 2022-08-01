package com.example.testproject.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class FarmerListModel implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    @SerializedName("farmers")
    @Expose
    public List<FarmerListDataModel>farmers = null;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<FarmerListDataModel> getFarmers() {
        return farmers;
    }

    public void setFarmers(List<FarmerListDataModel> farmers) {
        this.farmers = farmers;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
