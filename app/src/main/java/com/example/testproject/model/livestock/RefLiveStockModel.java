package com.example.testproject.model.livestock;

import com.example.testproject.model.DistrictDataModel;
import com.example.testproject.model.FarmerDataModel;
import com.example.testproject.model.FarmerOrg;
import com.example.testproject.model.StateDataModel;
import com.example.testproject.model.VillageDataModel;
import com.example.testproject.model.livestoks;
import com.example.testproject.model.query.AssignedToModel;
import com.example.testproject.model.stagemodel;
import com.example.testproject.model.varietymodel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RefLiveStockModel {

    public livestoks getLiveStock() {
        return liveStock;
    }

    public void setLiveStock(livestoks liveStock) {
        this.liveStock = liveStock;
    }

    public FarmerDataModel getFarmer() {
        return farmer;
    }

    public void setFarmer(FarmerDataModel farmer) {
        this.farmer = farmer;
    }

    public StateDataModel getState() {
        return state;
    }

    public void setState(StateDataModel state) {
        this.state = state;
    }

    public stagemodel getStage() {
        return stage;
    }

    public DistrictDataModel getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDataModel district) {
        this.district = district;
    }

    public VillageDataModel getVillage() {
        return village;
    }

    public void setVillage(VillageDataModel village) {
        this.village = village;
    }

    public void setStage(stagemodel stage) {
        this.stage = stage;
    }

    public varietymodel getVeriety() {
        return veriety;
    }

    public void setVeriety(varietymodel veriety) {
        this.veriety = veriety;
    }

    @SerializedName("liveStock")
    @Expose
    private livestoks liveStock;

    @SerializedName("farmer")
    @Expose
    private FarmerDataModel farmer;

    @SerializedName("stage")
    @Expose
    private stagemodel stage;

    @SerializedName("state")
    @Expose
    private StateDataModel state;

    @SerializedName("district")
    @Expose
    private DistrictDataModel district;

    @SerializedName("village")
    @Expose
    private VillageDataModel village;

    @SerializedName("veriety")
    @Expose
    private varietymodel veriety;

    @SerializedName("assignedTo")
    @Expose
    private AssignedToModel assignedTo;

    @SerializedName("resolvedBy")
    @Expose
    private AssignedToModel resolvedBy;

    @SerializedName("block")
    @Expose
    private FarmerOrg block;

    @SerializedName("gramPanchayat")
    @Expose
    private FarmerOrg gramPanchayat;

    public FarmerOrg getGramPanchayat() {
        return gramPanchayat;
    }

    public void setGramPanchayat(FarmerOrg gramPanchayat) {
        this.gramPanchayat = gramPanchayat;
    }

    public FarmerOrg getBlock() {
        return block;
    }

    public void setBlock(FarmerOrg block) {
        this.block = block;
    }

    public FarmerDataModel getCreatedByFarmer() {
        return createdByFarmer;
    }

    public void setCreatedByFarmer(FarmerDataModel createdByFarmer) {
        this.createdByFarmer = createdByFarmer;
    }

    public AssignedToModel getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(AssignedToModel assignedTo) {
        this.assignedTo = assignedTo;
    }

    public AssignedToModel getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(AssignedToModel resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    private FarmerDataModel createdByFarmer;
}
