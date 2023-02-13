package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContentModel {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("author")
    @Expose
    public String author;
    @SerializedName("content")
    @Expose
    public String content;
    @SerializedName("linkType")
    @Expose
    public String linkType;
    @SerializedName("contentTitle")
    @Expose
    public String contentTitle;
    @SerializedName("document")
    @Expose
    public String document;
    @SerializedName("dateCreated")
    @Expose
    public String dateCreated;
    @SerializedName("ignoredIndex")
    @Expose
    public List<String> ignoredIndex;
//    @SerializedName("indexingData")
//    @Expose
//    public IndexingData indexingData;
    @SerializedName("knowledgeDomain")
    @Expose
    public String knowledgeDomain;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("subDomain")
    @Expose
    public String subDomain;
    @SerializedName("subTopic")
    @Expose
    public String subTopic;
//    @SerializedName("timeApplicable")
//    @Expose
//    public TimeApplicable timeApplicable;
    @SerializedName("topic")
    @Expose
    public String topic;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("version")
    @Expose
    public Integer version;
    @SerializedName("recordId")
    @Expose
    public String recordId;
    @SerializedName("dateReviewed")
    @Expose
    public String dateReviewed;
    @SerializedName("reviewedBy")
    @Expose
    public String reviewedBy;
    @SerializedName("organisation")
    @Expose
    public String organisation;
    @SerializedName("compendium")
    @Expose
    public String compendium;
    @SerializedName("project")
    @Expose
    public String project;
    @SerializedName("farmerViewCount")
    @Expose
    public Integer farmerViewCount;
    @SerializedName("usersViewCount")
    @Expose
    public Integer usersViewCount;
    @SerializedName("guestUsersViewCount")
    @Expose
    public Integer guestUsersViewCount;
    @SerializedName("ref")
    @Expose
    public RefModel ref;

}
