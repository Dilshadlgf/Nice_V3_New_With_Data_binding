package com.example.testproject.model;

import com.example.testproject.model.livestock.RefLiveStockModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataModel {

    @SerializedName("productconfig")
    private Productconfig productconfig;

    @SerializedName("otp")
    private String otp;


    @SerializedName("content")
    private List<ContentModel> content;

    @SerializedName("pagination")
    private Pagination pagination;

    @SerializedName("data")
    private FarmerDataModel data;

    @SerializedName("farmer")
    private FarmerDataModel farmer;


    @SerializedName("farmerLiveStock")
    private LivestocksArrayModel farmerLiveStock;

    @SerializedName("duplicate")
    private String duplicate;

    private String status;

    private String query;

    private CreatedOn createdOn;

    private String solution;

    private RefLiveStockModel ref;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public CreatedOn getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(CreatedOn createdOn) {
        this.createdOn = createdOn;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public RefLiveStockModel getRef() {
        return ref;
    }

    public void setRef(RefLiveStockModel ref) {
        this.ref = ref;
    }

    public FarmerDataModel getFarmer() {
        return farmer;
    }

    public void setFarmer(FarmerDataModel farmer) {
        this.farmer = farmer;
    }

    public LivestocksArrayModel getFarmerLiveStock() {
        return farmerLiveStock;
    }

    public void setFarmerLiveStock(LivestocksArrayModel farmerLiveStock) {
        this.farmerLiveStock = farmerLiveStock;
    }

    public String getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(String duplicate) {
        this.duplicate = duplicate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ContentModel> getContent() {
        return content;
    }


    public void setContent(List<ContentModel> content) {
        this.content = content;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Productconfig getProductconfig() {
        return productconfig;
    }

    public FarmerDataModel getData() {
        return data;
    }

    public void setData(FarmerDataModel data) {
        this.data = data;
    }

    public void setProductconfig(Productconfig productconfig) {
        this.productconfig = productconfig;


    }
}
