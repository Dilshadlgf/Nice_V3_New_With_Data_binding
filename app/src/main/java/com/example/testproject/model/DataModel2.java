package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModel2 {

    @SerializedName("content")
    @Expose
    private SearchContentResponseDataModel content;

    public SearchContentResponseDataModel getContent() {
        return content;
    }

    public void setContent(SearchContentResponseDataModel content) {
        this.content = content;
    }
}
