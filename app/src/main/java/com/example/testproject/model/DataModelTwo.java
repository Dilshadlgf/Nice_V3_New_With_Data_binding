package com.example.testproject.model;

import com.example.testproject.model.livestock.RefLiveStockModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataModelTwo {

    private String id;
    private String uniqueId;
    private String name;
    private String logo;
    private String waterMark;
    private String logoWithName;
    private String profileImg;

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public RefLiveStockModel getRef() {
        return ref;
    }

    public void setRef(RefLiveStockModel ref) {
        this.ref = ref;
    }

    private RefLiveStockModel ref;

    public String getAlternateNumber() {
        return alternateNumber;
    }

    public void setAlternateNumber(String alternateNumber) {
        this.alternateNumber = alternateNumber;
    }

    private String alternateNumber;

    private String otp;

    private String email;

    private  String village;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @SerializedName("mobileNumber")
    private  String mobileNumber;


    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFarmerID() {
        return farmerID;
    }

    public void setFarmerID(String farmerID) {
        this.farmerID = farmerID;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @SerializedName("fatherName")
    private String fatherName;

    @SerializedName("gender")
    private String gender;

    @SerializedName("farmerID")
    private String farmerID;

    @SerializedName("dateOfBirth")
    private  String dateOfBirth;

    private String mobile;
    private String phone;
    private String address;
    private String copyrights;
    private String poweredBy;
    private String rigth;
    private String status;
    private String isUser;
    private String isDealer;
    private String isFarmer;

    private String date;
    private String feedback;
    private String rating;

    private String query;
    private String queryType;
    private String resolvedBy;
    private String resolvedDate;
    private String solution;
    private String createdType;
    private String assignedDate;

    public String getCreatedType() {
        return createdType;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    public void setCreatedType(String createdType) {
        this.createdType = createdType;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(String resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public String getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(String resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    private CreatedModel created;

    public CreatedOn getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(CreatedOn createdOn) {
        this.createdOn = createdOn;
    }

    private CreatedOn createdOn;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    private List<String> images;

    private Boolean isdefault;
    private Boolean issms;
    private Boolean validateUserRegistration;
    private String farmerUpload;
    private String farmerCasteUpload;
    private String farmerSoilUpload;
    private String farmerLandUpload;
    private String farmerCropUpload;

    @SerializedName("totalLand")
    @Expose
    private Integer totalLand;

    public Integer getTotalLand() {
        return totalLand;
    }

    public void setTotalLand(Integer totalLand) {
        this.totalLand = totalLand;
    }

    public Integer getVacantArea() {
        return vacantArea;
    }

    public void setVacantArea(Integer vacantArea) {
        this.vacantArea = vacantArea;
    }

    public Integer getCropCount() {
        return cropCount;
    }

    public void setCropCount(Integer cropCount) {
        this.cropCount = cropCount;
    }

    public Integer getCultivatedArea() {
        return cultivatedArea;
    }

    public void setCultivatedArea(Integer cultivatedArea) {
        this.cultivatedArea = cultivatedArea;
    }

    public Integer getLiveStockCount() {
        return liveStockCount;
    }

    public void setLiveStockCount(Integer liveStockCount) {
        this.liveStockCount = liveStockCount;
    }

    @SerializedName("vacantArea")
    @Expose
    private Integer vacantArea;

    @SerializedName("cropCount")
    @Expose
    private Integer cropCount;

    @SerializedName("cultivatedArea")
    private Integer cultivatedArea;

    @SerializedName("liveStockCount")
    @Expose
    private Integer liveStockCount;


    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWaterMark() {
        return waterMark;
    }

    public void setWaterMark(String waterMark) {
        this.waterMark = waterMark;
    }

    public String getLogoWithName() {
        return logoWithName;
    }

    public void setLogoWithName(String logoWithName) {
        this.logoWithName = logoWithName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String  email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public String getPoweredBy() {
        return poweredBy;
    }

    public void setPoweredBy(String poweredBy) {
        this.poweredBy = poweredBy;
    }

    public String getRigth() {
        return rigth;
    }

    public void setRigth(String rigth) {
        this.rigth = rigth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CreatedModel getCreated() {
        return created;
    }

    public void setCreated(CreatedModel created) {
        this.created = created;
    }

    public Boolean getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }

    public Boolean getIssms() {
        return issms;
    }

    public void setIssms(Boolean issms) {
        this.issms = issms;
    }

    public Boolean getValidateUserRegistration() {
        return validateUserRegistration;
    }

    public void setValidateUserRegistration(Boolean validateUserRegistration) {
        this.validateUserRegistration = validateUserRegistration;
    }

    public String getFarmerUpload() {
        return farmerUpload;
    }

    public void setFarmerUpload(String farmerUpload) {
        this.farmerUpload = farmerUpload;
    }

    public String getFarmerCasteUpload() {
        return farmerCasteUpload;
    }

    public void setFarmerCasteUpload(String farmerCasteUpload) {
        this.farmerCasteUpload = farmerCasteUpload;
    }

    public String getFarmerSoilUpload() {
        return farmerSoilUpload;
    }

    public void setFarmerSoilUpload(String farmerSoilUpload) {
        this.farmerSoilUpload = farmerSoilUpload;
    }

    public String getFarmerLandUpload() {
        return farmerLandUpload;
    }

    public void setFarmerLandUpload(String farmerLandUpload) {
        this.farmerLandUpload = farmerLandUpload;
    }

    public String getFarmerCropUpload() {
        return farmerCropUpload;
    }

    public void setFarmerCropUpload(String farmerCropUpload) {
        this.farmerCropUpload = farmerCropUpload;
    }

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

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }
}
