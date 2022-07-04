package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataModel2 {

    @SerializedName("content")
    @Expose
    private SearchContentResponseDataModel content;

    @SerializedName("farmerCrop")
    private List<CropDataModel> farmerCrop;

    @SerializedName("cropseason")
    private List<CropSeasonDataModel> cropseason;

    @SerializedName("data")
    private List<DataModelTwo> data;

    public List<DataModelTwo> getData() {
        return data;
    }

    public void setData(List<DataModelTwo> data) {
        this.data = data;
    }

    public List<CropSeasonDataModel> getCropseason() {
        return cropseason;
    }

    public void setCropseason(List<CropSeasonDataModel> cropseason) {
        this.cropseason = cropseason;
    }

    @SerializedName("commodity")
    private List<LivestocksArrayModel> commodity;

    @SerializedName("variety")
    private List<varietymodel> variety;

//    public List<varietymodel> getVariety() {
//        return variety;
//    }
//
//    public void setVariety(List<varietymodel> variety) {
//        this.variety = variety;
//    }
//
//    public List<LivestocksArrayModel> getCommodity() {
//        return commodity;
//    }
//
//    public void setCommodity(List<LivestocksArrayModel> commodity) {
//        this.commodity = commodity;
//    }

    public List<CropDataModel> getFarmerCrop() {
        return farmerCrop;
    }

    public void setFarmerCrop(List<CropDataModel> farmerCrop) {
        this.farmerCrop = farmerCrop;
    }

    @SerializedName("category")
    private List<LivestocksArrayModel> liveStockCategory;

    @SerializedName("farmerLiveStock")
    private List<LivestocksArrayModel> farmerLiveStock;

//    @SerializedName("variety")
//    private List<varietymodel> variety;

    @SerializedName("stage")
    private List<stagemodel> stage;

    public List<stagemodel> getStage() {
        return stage;
    }

    public void setStage(List<stagemodel> stage) {
        this.stage = stage;
    }

    public List<varietymodel> getVariety() {
        return variety;
    }

    public void setVariety(List<varietymodel> variety) {
        this.variety = variety;
    }

    public List<LivestocksArrayModel> getFarmerLiveStock() {
        return farmerLiveStock;
    }

    public void setFarmerLiveStock(List<LivestocksArrayModel> farmerLiveStock) {
        this.farmerLiveStock = farmerLiveStock;
    }

    public List<LivestocksArrayModel> getLiveStockCategory() {
        return liveStockCategory;
    }


    public void setLiveStockCategory(List<LivestocksArrayModel> liveStockCategory) {
        this.liveStockCategory = liveStockCategory;
    }

    @SerializedName("notificationlog")
    private List<NotificationDataModel> notificationlog;

    @SerializedName("pagination")
    private Pagination1 pagination1;

//    @SerializedName("commodity")
//    private List<LivestocksArrayModel> commodity;

    public List<LivestocksArrayModel> getCommodity() {
        return commodity;
    }

    public void setCommodity(List<LivestocksArrayModel> commodity) {
        this.commodity = commodity;
    }

    public Pagination1 getPagination1() {
        return pagination1;
    }

    public void setPagination1(Pagination1 pagination1) {
        this.pagination1 = pagination1;
    }

    public List<NotificationDataModel> getNotificationlog() {
        return notificationlog;
    }

    public void setNotificationlog(List<NotificationDataModel> notificationlog) {
        this.notificationlog = notificationlog;
    }

    public SearchContentResponseDataModel getContent() {
        return content;
    }

    public void setContent(SearchContentResponseDataModel content) {
        this.content = content;
    }
}
