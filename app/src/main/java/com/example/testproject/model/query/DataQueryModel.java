package com.example.testproject.model.query;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataQueryModel {

    @SerializedName("data")
    private List<QueryResponseDataNumModel> data;

    @SerializedName("pagination")
    private PaginationModel paginationModel;

    public List<QueryResponseDataNumModel> getData() {
        return data;
    }

    public void setData(List<QueryResponseDataNumModel> data) {
        this.data = data;
    }

    public PaginationModel getPaginationModel() {
        return paginationModel;
    }

    public void setPaginationModel(PaginationModel paginationModel) {
        this.paginationModel = paginationModel;
    }
}
