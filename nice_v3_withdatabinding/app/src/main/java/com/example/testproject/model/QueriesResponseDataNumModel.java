package com.example.testproject.model;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class QueriesResponseDataNumModel {

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    @PrimaryKey
    @Expose
    private Integer mid;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("uniqueId")
    @Expose
    private String uniqueId;
    @SerializedName("queryType")
    @Expose
    private String queryType;
    @SerializedName("activeStatus")
    @Expose
    private Boolean activeStatus;
    @SerializedName("isSolutionSent")
    @Expose
    @Ignore
    private Boolean isSolutionSent;
    @SerializedName("createdOn")
    @Expose
    private CreatedOn createdOn;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdType")
    @Expose
    private String createdType;
    @SerializedName("solution")
    @Expose
    private String solution;
    @Ignore
    public Boolean getSolutionSent() {
        return isSolutionSent;
    }
    @Ignore
    public void setSolutionSent(Boolean solutionSent) {
        isSolutionSent = solutionSent;
    }

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("resolvedDate")
    @Expose
    private String resolvedDate;
    @SerializedName("knowledgeDomain")
    @Expose
    private String knowledgeDomain;
    @SerializedName("subDomain")
    @Expose
    private String subDomain;
    @SerializedName("gramPanchayat")
    @Expose
    private String gramPanchayat;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("assignedTo")
    @Expose
    private String assignedTo;
    @SerializedName("assignedDate")
    @Expose
    private String assignedDate;
    @SerializedName("resolvedBy")
    @Expose
    private String resolvedBy;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("block")
    @Expose
    private String block;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("farmer")
    @Expose
    private String farmer;

    public RefModel getRef() {
        return ref;
    }

    public void setRef(RefModel ref) {
        this.ref = ref;
    }

    @SerializedName("ref")
    @Expose
    private RefModel ref;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Boolean getIsSolutionSent() {
        return isSolutionSent;
    }

    public void setIsSolutionSent(Boolean isSolutionSent) {
        this.isSolutionSent = isSolutionSent;
    }

    public CreatedOn getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(CreatedOn createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedType() {
        return createdType;
    }

    public void setCreatedType(String createdType) {
        this.createdType = createdType;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(String resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public String getKnowledgeDomain() {
        return knowledgeDomain;
    }

    public void setKnowledgeDomain(String knowledgeDomain) {
        this.knowledgeDomain = knowledgeDomain;
    }

    public String getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(String subDomain) {
        this.subDomain = subDomain;
    }

    public String getGramPanchayat() {
        return gramPanchayat;
    }

    public void setGramPanchayat(String gramPanchayat) {
        this.gramPanchayat = gramPanchayat;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(String  assignedDate) {
        this.assignedDate = assignedDate;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(String resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getFarmer() {
        return farmer;
    }

    public void setFarmer(String farmer) {
        this.farmer = farmer;
    }

}
/*date = "2019-12-26T06:00:55Z"
id = "5e044c9737cc632cd00787fb"
images = {ArrayList@13808}  size = 1
query = "Ryhjjkkolvccvbnmmmmmnbv"
queryType = "Farmer"
resolvedByName = "CHAITHRA"
resolvedDate = "2019-12-29T18:30:00Z"
solution = "test query"
villageName = "Rata"
shadow$_klass_ = {Class@13756} "class com.nice.model.QueriesResponseDataNumModel"*/