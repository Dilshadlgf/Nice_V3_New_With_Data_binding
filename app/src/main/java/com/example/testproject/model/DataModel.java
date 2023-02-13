package com.example.testproject.model;

import com.example.testproject.Util.JsonMyUtils;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataModel {
    public JsonElement data;
    public JsonElement farmer;
    public String duplicate;
    public String otp;
    public JsonElement user;
    public JsonElement contentDissiminateUserAndFarmer;
    public JsonElement uri;

    public JsonElement  commodity;
    public JsonElement productconfig;
    public JsonElement  variety;
    @SerializedName("stage")
    public JsonElement stage;

    @SerializedName("category")
    public JsonElement liveStockCategory;

    @SerializedName("farmerCrop")
    public JsonElement farmerCrop;

    @SerializedName("farmerLiveStock")
    public JsonElement farmerLiveStock;

    @SerializedName("stateweatherdata")
    private JsonElement stateWeatherModels;

    public JsonElement getStateWeatherModels() {
        return stateWeatherModels;
    }

    public void setStateWeatherModels(JsonElement stateWeatherModels) {
        this.stateWeatherModels = stateWeatherModels;
    }

    @SerializedName("content")
    public JsonElement content;

    @SerializedName("notificationlog")
    private List<NotificationDataModel> notificationlog;

    public List<NotificationDataModel> getNotificationlog() {
        return notificationlog;
    }

    public void setNotificationlog(List<NotificationDataModel> notificationlog) {
        this.notificationlog = notificationlog;
    }

    private PaginationModel pagination;

    public PaginationModel getPagination() {
        return pagination;
    }

    public void setPagination(PaginationModel pagination) {
        this.pagination = pagination;
    }
}
