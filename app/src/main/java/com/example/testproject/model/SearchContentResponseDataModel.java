package com.example.testproject.model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Contentdetail")
public class SearchContentResponseDataModel {


    @NonNull
    public Integer getMid() {
        return mid;
    }

    public void setMid(@NonNull Integer mid) {
        this.mid = mid;
    }

    @NonNull
    @PrimaryKey
    @SerializedName("mid")
    @Expose
    private Integer mid;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("contentUrl")
    @Expose
    private String contentUrl;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("recordId")
    @Expose
    private String recordId;
    @SerializedName("content")
    @Expose
    private String content;


    private String dateCreated;

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    @SerializedName("contentTitle")
    @Expose
    private String contentTitle;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    @SerializedName("document")
    @Expose
    private String document;
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("linkType")
    @Expose
    private String linkType;

//    public IndexingData getIndexingData() {
//        return indexingData;
//    }
//
//    public void setIndexingData(IndexingData indexingData) {
//        this.indexingData = indexingData;
//    }
//
//    @SerializedName("indexingData")
//    @Expose
//    private IndexingData indexingData;

    public String project;
    public JsonElement organisation;

    public RefModel getRef() {
        return Ref;
    }

    public void setRef(RefModel ref) {
        Ref = ref;
    }

    @SerializedName("ref")
    @Expose
    private RefModel Ref;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
