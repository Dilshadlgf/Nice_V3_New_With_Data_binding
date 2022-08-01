package com.example.testproject.model.SingleObjectModel;

import com.example.testproject.model.DataModelTwo;
import com.example.testproject.model.FarmerDataModel;
import com.example.testproject.model.LivestocksArrayModel;
import com.example.testproject.model.SearchContentResponseDataModel;
import com.google.gson.annotations.SerializedName;

public class SingleObjRootDataModel {

    @SerializedName("farmer")
    private FarmerDataModel farmer;

    public LivestocksArrayModel getFarmerLiveStock() {
        return farmerLiveStock;
    }

    public void setFarmerLiveStock(LivestocksArrayModel farmerLiveStock) {
        this.farmerLiveStock = farmerLiveStock;
    }

    public DataModelTwo getProductconfig() {
        return productconfig;
    }

    public void setProductconfig(DataModelTwo productconfig) {
        this.productconfig = productconfig;
    }

    @SerializedName("productconfig")
    private DataModelTwo productconfig;

    @SerializedName("farmerLiveStock")
    private LivestocksArrayModel farmerLiveStock;

    public String getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(String duplicate) {
        this.duplicate = duplicate;
    }

    @SerializedName("duplicate")
    private String duplicate;

    @SerializedName("otp")
    private String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public SearchContentResponseDataModel getContent() {
        return content;
    }

    public void setContent(SearchContentResponseDataModel content) {
        this.content = content;
    }

    @SerializedName("content")
    private SearchContentResponseDataModel content;

    public FarmerDataModel getFarmer() {
        return farmer;
    }

    public void setFarmer(FarmerDataModel farmer) {
        farmer = farmer;
    }

    @SerializedName("data")
    private DataModelTwo data;

    public DataModelTwo getData() {
        return data;
    }

    public void setData(DataModelTwo data) {
        this.data = data;
    }
}
