package com.example.testproject.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class VillageDBModel {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("village")
    @Expose
    private String village;


    @SerializedName("villageId")
    @Expose
    private String villageId;

    @SerializedName("gramPanchayat")
    @Expose
    private String gramPanchayat;


    @SerializedName("gramPanchayatId")
    @Expose
    private String gramPanchayatId;

    @SerializedName("block")
    @Expose
    private String block;

    @SerializedName("blockId")
    @Expose
    private String blockId;

    @SerializedName("district")
    @Expose
    private String district;

    @SerializedName("districtId")
    @Expose
    private String districtID;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("stateId")
    @Expose
    private String stateId;


    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    public String getGramPanchayat() {
        return gramPanchayat;
    }

    public void setGramPanchayat(String gramPanchayat) {
        this.gramPanchayat = gramPanchayat;
    }

    public String getGramPanchayatId() {
        return gramPanchayatId;
    }

    public void setGramPanchayatId(String gramPanchayatId) {
        this.gramPanchayatId = gramPanchayatId;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(String districtID) {
        this.districtID = districtID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }


}
