package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataModel2 {

    @SerializedName("content")
    @Expose
    private SearchContentResponseDataModel content;

    @SerializedName("notificationlog")
    private List<NotificationDataModel> notificationlog;

    @SerializedName("pagination")
    private Pagination1 pagination1;

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
