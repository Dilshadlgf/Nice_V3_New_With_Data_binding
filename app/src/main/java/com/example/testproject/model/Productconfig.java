package com.example.testproject.model;

import com.google.gson.annotations.SerializedName;

public class Productconfig {

    @SerializedName("isUser")
    private String isUser;

    @SerializedName("isDealer")
    private String isDealer;

    @SerializedName("isFarmer")
    private String isFarmer;

    public String getIsUser() {
        return isUser;
    }

    public void setIsUser(String isUser) {
        this.isUser = isUser;
    }

    public String getIsDealer() {
        return isDealer;
    }

    public void setIsDealer(String isDealer) {
        this.isDealer = isDealer;
    }

    public String getIsFarmer() {
        return isFarmer;
    }

    public void setIsFarmer(String isFarmer) {
        this.isFarmer = isFarmer;
    }
}
