package com.example.testproject.model;

import com.google.gson.annotations.SerializedName;

public class SearchContentResponseDataModel {
    @SerializedName("ref")
    private QueryRef queryRef;

    public QueryRef getQueryRef() {
        return queryRef;
    }

    public void setQueryRef(QueryRef queryRef) {
        this.queryRef = queryRef;
    }
}