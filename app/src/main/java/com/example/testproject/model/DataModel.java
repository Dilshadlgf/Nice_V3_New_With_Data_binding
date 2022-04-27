package com.example.testproject.model;

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
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setProductconfig(Productconfig productconfig) {
        this.productconfig = productconfig;


    }
}
