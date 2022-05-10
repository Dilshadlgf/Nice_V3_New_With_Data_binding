package com.example.testproject.model;
 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class QueryRef   {

    @SerializedName("knowledgeDomain")
    @Expose
    private KnowledgeDomain knowledgeDomain;

    @SerializedName("subDomain")
    @Expose
    private SubDomain subDomain;

    @SerializedName("subTopic")
    @Expose
    private SubTopic subTopic;

    @SerializedName("topic")
    @Expose
    private Topic topic;

    @SerializedName("state")
    @Expose
    private StateDataModel state;

    @SerializedName("district")
    @Expose
    private DistrictDataModel district;

    @SerializedName("createdByFarmer")
    private CreatedByFarmer createdByFarmer;

    public CreatedByFarmer getCreatedByFarmer() {
        return createdByFarmer;
    }

    public void setCreatedByFarmer(CreatedByFarmer createdByFarmer) {
        this.createdByFarmer = createdByFarmer;
    }

    public KnowledgeDomain getKnowledgeDomain() {
        return knowledgeDomain;
    }

    public void setKnowledgeDomain(KnowledgeDomain knowledgeDomain) {
        this.knowledgeDomain = knowledgeDomain;
    }

    public SubDomain getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(SubDomain subDomain) {
        this.subDomain = subDomain;
    }

    public SubTopic getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(SubTopic subTopic) {
        this.subTopic = subTopic;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public StateDataModel getState() {
        return state;
    }

    public void setState(StateDataModel state) {
        this.state = state;
    }

    public DistrictDataModel getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDataModel district) {
        this.district = district;
    }


    public ReviewedBy getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(ReviewedBy reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    @SerializedName("reviewedBy")
    @Expose
    private ReviewedBy reviewedBy;





}
