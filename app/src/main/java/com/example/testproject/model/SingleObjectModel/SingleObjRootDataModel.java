package com.example.testproject.model.SingleObjectModel;

import com.example.testproject.model.DataModelTwo;
import com.example.testproject.model.FarmerModel;
import com.example.testproject.model.LivestocksArrayModel;
import com.example.testproject.model.SearchContentResponseDataModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SingleObjRootDataModel {

    @SerializedName("farmer")
    private FarmerModel farmer;

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


    public SearchContentResponseDataModel getContent() {
        return content;
    }

    public void setContent(SearchContentResponseDataModel content) {
        this.content = content;
    }

    @SerializedName("content")
    private SearchContentResponseDataModel content;

    public FarmerModel getFarmer() {
        return farmer;
    }

    public void setFarmer(FarmerModel farmer) {
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
