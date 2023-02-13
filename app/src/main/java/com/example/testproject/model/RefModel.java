package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefModel {

    @SerializedName("knowledgeDomain")
    @Expose
    public ProjectModel knowledgeDomain;
    @SerializedName("organisation")
    @Expose
    public ProjectModel organisation;
    @SerializedName("orgnisation")
    @Expose
    public ProjectModel orgnisation;
    @SerializedName("project")
    @Expose
    public ProjectModel project;
    @SerializedName("subDomain")
    @Expose
    public ProjectModel subDomain;
    @SerializedName("subTopic")
    @Expose
    public ProjectModel subTopic;
    @SerializedName("topic")
    @Expose
    public ProjectModel topic;
    @SerializedName("state")
    @Expose
    public AddressModel state;
    @SerializedName("district")
    @Expose
    public AddressModel district;
    @SerializedName("block")
    @Expose
    public AddressModel block;
    @SerializedName("season")
    @Expose
    public SeasonModel season;
    @SerializedName("stage")
    @Expose
    public CommodityModel stage;
    @SerializedName("village")
    @Expose
    public AddressModel village;
    @SerializedName("gram_panchayat")
    @Expose
    public AddressModel gramPanchayat;
    @SerializedName("function")
    @Expose
    public CommodityModel function;
    @SerializedName("soil_type")
    @Expose
    public CommodityModel soilType;
    @SerializedName("market")
    @Expose
    public AddressModel market;
    @SerializedName("variety")
    @Expose
    public CommodityModel variety;
    @SerializedName("author")
    @Expose
    public UserModel author;
    @SerializedName("reviewedBy")
    @Expose
    public UserModel reviewedBy;
    @SerializedName("category")
    @Expose
    public CommodityModel category;
    @SerializedName("commodity")
    @Expose
    public CommodityModel commodity;

    @SerializedName("subVariety")
    @Expose
    public CommodityModel subVariety;

    public UserModel createdByFarmer;

    public UserModel createdByUser;

    public UserModel resolvedBy;

    public UserModel farmer;

    public UserModel assignedTo;

    public CropModel interCrop;

    public CropModel crop;

    public LivestockModel liveStock;



}
