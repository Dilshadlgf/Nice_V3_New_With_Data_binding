package com.example.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FarmerModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("alternateNumber")
    @Expose
    private String alternateNumber;
    @SerializedName("aadhaarNumber")
    @Expose
    private String aadhaarNumber;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("fatherName")
    @Expose
    private String fatherName;
    @SerializedName("currentLiveStocks")
    @Expose
    private Object currentLiveStocks;
    @SerializedName("currentCrops")
    @Expose
    private Object currentCrops;
    @SerializedName("cultivationPractice")
    @Expose
    private String cultivationPractice;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("gramPanchayat")
    @Expose
    private String gramPanchayat;
    @SerializedName("isMemberInvolvedInCbo")
    @Expose
    private Boolean isMemberInvolvedInCbo;
    @SerializedName("activeStatus")
    @Expose
    private Boolean activeStatus;
    @SerializedName("isSMS")
    @Expose
    private Boolean isSMS;
    @SerializedName("feminineMobile")
    @Expose
    private Boolean feminineMobile;
    @SerializedName("creditAvailed")
    @Expose
    private Boolean creditAvailed;
    @SerializedName("hasKitchenGarden")
    @Expose
    private Boolean hasKitchenGarden;
    @SerializedName("likeToReceiveSMS")
    @Expose
    private Boolean likeToReceiveSMS;
    @SerializedName("likeToReceiveVoicecall")
    @Expose
    private Boolean likeToReceiveVoicecall;
    @SerializedName("hasCredits")
    @Expose
    private Boolean hasCredits;
    @SerializedName("memberOfMGNREGNA")
    @Expose
    private Boolean memberOfMGNREGNA;
    @SerializedName("isPhysicallyDisabled")
    @Expose
    private Boolean isPhysicallyDisabled;
    @SerializedName("isAnyMmemberOfFamilyInCBO")
    @Expose
    private Boolean isAnyMmemberOfFamilyInCBO;
    @SerializedName("createdOn")
    @Expose
    private CreatedOn createdOn;
    @SerializedName("block")
    @Expose
    private String block;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("isVoiceSMS")
    @Expose
    private Boolean isVoiceSMS;
    @SerializedName("kitchenGarden")
    @Expose
    private Boolean kitchenGarden;
    @SerializedName("leasedInIrrigated")
    @Expose
    private Integer leasedInIrrigated;
    @SerializedName("leasedInRainfed")
    @Expose
    private Integer leasedInRainfed;
    @SerializedName("leasedOutIrrigated")
    @Expose
    private Integer leasedOutIrrigated;
    @SerializedName("leasedOutRainfed")
    @Expose
    private Integer leasedOutRainfed;
    @SerializedName("membershipInMgnrega")
    @Expose
    private Boolean membershipInMgnrega;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("landLineNumber")
    @Expose
    private String landLineNumber;
    @SerializedName("ownedIrrigated")
    @Expose
    private Integer ownedIrrigated;
    @SerializedName("ownedRainfed")
    @Expose
    private Integer ownedRainfed;
    @SerializedName("soilType")
    @Expose
    private String soilType;
    @SerializedName("spouseName")
    @Expose
    private String spouseName;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("totalLand")
    @Expose
    private Integer totalLand;
    @SerializedName("totalMobiles")
    @Expose
    private Integer totalMobiles;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("yearlyIncome")
    @Expose
    private String yearlyIncome;
    @SerializedName("farmerOrg")
    @Expose
    private String farmerOrg;
    @SerializedName("farmerID")
    @Expose
    private String farmerID;
    @SerializedName("doorNo")
    @Expose
    private String doorNo;

    public Boolean getMemberInvolvedInCbo() {
        return isMemberInvolvedInCbo;
    }

    public void setMemberInvolvedInCbo(Boolean memberInvolvedInCbo) {
        isMemberInvolvedInCbo = memberInvolvedInCbo;
    }

    public Boolean getSMS() {
        return isSMS;
    }

    public void setSMS(Boolean SMS) {
        isSMS = SMS;
    }

    public Boolean getPhysicallyDisabled() {
        return isPhysicallyDisabled;
    }

    public void setPhysicallyDisabled(Boolean physicallyDisabled) {
        isPhysicallyDisabled = physicallyDisabled;
    }

    public Boolean getAnyMmemberOfFamilyInCBO() {
        return isAnyMmemberOfFamilyInCBO;
    }

    public void setAnyMmemberOfFamilyInCBO(Boolean anyMmemberOfFamilyInCBO) {
        isAnyMmemberOfFamilyInCBO = anyMmemberOfFamilyInCBO;
    }

    public Boolean getVoiceSMS() {
        return isVoiceSMS;
    }

    public void setVoiceSMS(Boolean voiceSMS) {
        isVoiceSMS = voiceSMS;
    }

    public int getLiveStockCount() {
        return liveStockCount;
    }

    public void setLiveStockCount(int liveStockCount) {
        this.liveStockCount = liveStockCount;
    }

    public Boolean getDisabled() {
        return isDisabled;
    }

    public void setDisabled(Boolean disabled) {
        isDisabled = disabled;
    }

    @SerializedName("liveStockCount")
    @Expose
    private int liveStockCount;
    @SerializedName("landMark")
    @Expose
    private String landMark;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("preferredMarkets")
    @Expose
    private List<String> preferredMarkets = null;
    @SerializedName("isDisabled")
    @Expose
    private Boolean isDisabled;
    @SerializedName("fertilizerQuantity")
    @Expose
    private Integer fertilizerQuantity;
    @SerializedName("rainDisasterFrom")
    @Expose
    private Integer rainDisasterFrom;
    @SerializedName("rainDisasterTo")
    @Expose
    private Integer rainDisasterTo;
    @SerializedName("rainfallMedFrom")
    @Expose
    private Integer rainfallMedFrom;
    @SerializedName("rainfallMedTo")
    @Expose
    private Integer rainfallMedTo;
    @SerializedName("relativeHumidityDisasterFrom")
    @Expose
    private Integer relativeHumidityDisasterFrom;
    @SerializedName("seedQuantity")
    @Expose
    private Integer seedQuantity;
    @SerializedName("temperatureDisasterFrom")
    @Expose
    private Integer temperatureDisasterFrom;
    @SerializedName("temperatureDisasterTo")
    @Expose
    private Integer temperatureDisasterTo;
    @SerializedName("windDirectionDisasterTo")
    @Expose
    private Integer windDirectionDisasterTo;
    @SerializedName("windDirectionMedFrom")
    @Expose
    private Integer windDirectionMedFrom;
    @SerializedName("relativeHumidityDisasterTo")
    @Expose
    private Integer relativeHumidityDisasterTo;
    @SerializedName("windDirectionMedTo")
    @Expose
    private Integer windDirectionMedTo;
    @SerializedName("windSpeedDisasterFrom")
    @Expose
    private Integer windSpeedDisasterFrom;
    @SerializedName("windSpeedDisasterTo")
    @Expose
    private Integer windSpeedDisasterTo;
    @SerializedName("windSpeedMedFrom")
    @Expose
    private Integer windSpeedMedFrom;
    @SerializedName("windSpeedMedTo")
    @Expose
    private Integer windSpeedMedTo;
    @SerializedName("uniqueId")
    @Expose
    private Integer uniqueId;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("assert")
    @Expose
    private List<String> _assert = null;
    @SerializedName("cast")
    @Expose
    private String cast;
    @SerializedName("isleadwomenhasmobile")
    @Expose
    private Boolean isleadwomenhasmobile;
    @SerializedName("womenMobileNumber")
    @Expose
    private String womenMobileNumber;
    @SerializedName("creditName")
    @Expose
    private String creditName;
    @SerializedName("disabilityType")
    @Expose
    private String disabilityType;
    @SerializedName("memberName")
    @Expose
    private String memberName;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("appRegistrationToken")
    @Expose
    private String appRegistrationToken;
    @SerializedName("pinCode")
    @Expose
    private Integer pinCode;
    @SerializedName("location")

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlternateNumber() {
        return alternateNumber;
    }

    public void setAlternateNumber(String alternateNumber) {
        this.alternateNumber = alternateNumber;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Object getCurrentLiveStocks() {
        return currentLiveStocks;
    }

    public void setCurrentLiveStocks(Object currentLiveStocks) {
        this.currentLiveStocks = currentLiveStocks;
    }

    public Object getCurrentCrops() {
        return currentCrops;
    }

    public void setCurrentCrops(Object currentCrops) {
        this.currentCrops = currentCrops;
    }

    public String getCultivationPractice() {
        return cultivationPractice;
    }

    public void setCultivationPractice(String cultivationPractice) {
        this.cultivationPractice = cultivationPractice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getGramPanchayat() {
        return gramPanchayat;
    }

    public void setGramPanchayat(String gramPanchayat) {
        this.gramPanchayat = gramPanchayat;
    }

    public Boolean getIsMemberInvolvedInCbo() {
        return isMemberInvolvedInCbo;
    }

    public void setIsMemberInvolvedInCbo(Boolean isMemberInvolvedInCbo) {
        this.isMemberInvolvedInCbo = isMemberInvolvedInCbo;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Boolean getIsSMS() {
        return isSMS;
    }

    public void setIsSMS(Boolean isSMS) {
        this.isSMS = isSMS;
    }

    public Boolean getFeminineMobile() {
        return feminineMobile;
    }

    public void setFeminineMobile(Boolean feminineMobile) {
        this.feminineMobile = feminineMobile;
    }

    public Boolean getCreditAvailed() {
        return creditAvailed;
    }

    public void setCreditAvailed(Boolean creditAvailed) {
        this.creditAvailed = creditAvailed;
    }

    public Boolean getHasKitchenGarden() {
        return hasKitchenGarden;
    }

    public void setHasKitchenGarden(Boolean hasKitchenGarden) {
        this.hasKitchenGarden = hasKitchenGarden;
    }

    public Boolean getLikeToReceiveSMS() {
        return likeToReceiveSMS;
    }

    public void setLikeToReceiveSMS(Boolean likeToReceiveSMS) {
        this.likeToReceiveSMS = likeToReceiveSMS;
    }

    public Boolean getLikeToReceiveVoicecall() {
        return likeToReceiveVoicecall;
    }

    public void setLikeToReceiveVoicecall(Boolean likeToReceiveVoicecall) {
        this.likeToReceiveVoicecall = likeToReceiveVoicecall;
    }

    public Boolean getHasCredits() {
        return hasCredits;
    }

    public void setHasCredits(Boolean hasCredits) {
        this.hasCredits = hasCredits;
    }

    public Boolean getMemberOfMGNREGNA() {
        return memberOfMGNREGNA;
    }

    public void setMemberOfMGNREGNA(Boolean memberOfMGNREGNA) {
        this.memberOfMGNREGNA = memberOfMGNREGNA;
    }

    public Boolean getIsPhysicallyDisabled() {
        return isPhysicallyDisabled;
    }

    public void setIsPhysicallyDisabled(Boolean isPhysicallyDisabled) {
        this.isPhysicallyDisabled = isPhysicallyDisabled;
    }

    public Boolean getIsAnyMmemberOfFamilyInCBO() {
        return isAnyMmemberOfFamilyInCBO;
    }

    public void setIsAnyMmemberOfFamilyInCBO(Boolean isAnyMmemberOfFamilyInCBO) {
        this.isAnyMmemberOfFamilyInCBO = isAnyMmemberOfFamilyInCBO;
    }

    public CreatedOn getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(CreatedOn createdOn) {
        this.createdOn = createdOn;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getIsVoiceSMS() {
        return isVoiceSMS;
    }

    public void setIsVoiceSMS(Boolean isVoiceSMS) {
        this.isVoiceSMS = isVoiceSMS;
    }

    public Boolean getKitchenGarden() {
        return kitchenGarden;
    }

    public void setKitchenGarden(Boolean kitchenGarden) {
        this.kitchenGarden = kitchenGarden;
    }

    public Integer getLeasedInIrrigated() {
        return leasedInIrrigated;
    }

    public void setLeasedInIrrigated(Integer leasedInIrrigated) {
        this.leasedInIrrigated = leasedInIrrigated;
    }

    public Integer getLeasedInRainfed() {
        return leasedInRainfed;
    }

    public void setLeasedInRainfed(Integer leasedInRainfed) {
        this.leasedInRainfed = leasedInRainfed;
    }

    public Integer getLeasedOutIrrigated() {
        return leasedOutIrrigated;
    }

    public void setLeasedOutIrrigated(Integer leasedOutIrrigated) {
        this.leasedOutIrrigated = leasedOutIrrigated;
    }

    public Integer getLeasedOutRainfed() {
        return leasedOutRainfed;
    }

    public void setLeasedOutRainfed(Integer leasedOutRainfed) {
        this.leasedOutRainfed = leasedOutRainfed;
    }

    public Boolean getMembershipInMgnrega() {
        return membershipInMgnrega;
    }

    public void setMembershipInMgnrega(Boolean membershipInMgnrega) {
        this.membershipInMgnrega = membershipInMgnrega;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLandLineNumber() {
        return landLineNumber;
    }

    public void setLandLineNumber(String landLineNumber) {
        this.landLineNumber = landLineNumber;
    }

    public Integer getOwnedIrrigated() {
        return ownedIrrigated;
    }

    public void setOwnedIrrigated(Integer ownedIrrigated) {
        this.ownedIrrigated = ownedIrrigated;
    }

    public Integer getOwnedRainfed() {
        return ownedRainfed;
    }

    public void setOwnedRainfed(Integer ownedRainfed) {
        this.ownedRainfed = ownedRainfed;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getTotalLand() {
        return totalLand;
    }

    public void setTotalLand(Integer totalLand) {
        this.totalLand = totalLand;
    }

    public Integer getTotalMobiles() {
        return totalMobiles;
    }

    public void setTotalMobiles(Integer totalMobiles) {
        this.totalMobiles = totalMobiles;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getYearlyIncome() {
        return yearlyIncome;
    }

    public void setYearlyIncome(String yearlyIncome) {
        this.yearlyIncome = yearlyIncome;
    }

    public String getFarmerOrg() {
        return farmerOrg;
    }

    public void setFarmerOrg(String farmerOrg) {
        this.farmerOrg = farmerOrg;
    }

    public String getFarmerID() {
        return farmerID;
    }

    public void setFarmerID(String farmerID) {
        this.farmerID = farmerID;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<String> getPreferredMarkets() {
        return preferredMarkets;
    }

    public void setPreferredMarkets(List<String> preferredMarkets) {
        this.preferredMarkets = preferredMarkets;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public Integer getFertilizerQuantity() {
        return fertilizerQuantity;
    }

    public void setFertilizerQuantity(Integer fertilizerQuantity) {
        this.fertilizerQuantity = fertilizerQuantity;
    }

    public Integer getRainDisasterFrom() {
        return rainDisasterFrom;
    }

    public void setRainDisasterFrom(Integer rainDisasterFrom) {
        this.rainDisasterFrom = rainDisasterFrom;
    }

    public Integer getRainDisasterTo() {
        return rainDisasterTo;
    }

    public void setRainDisasterTo(Integer rainDisasterTo) {
        this.rainDisasterTo = rainDisasterTo;
    }

    public Integer getRainfallMedFrom() {
        return rainfallMedFrom;
    }

    public void setRainfallMedFrom(Integer rainfallMedFrom) {
        this.rainfallMedFrom = rainfallMedFrom;
    }

    public Integer getRainfallMedTo() {
        return rainfallMedTo;
    }

    public void setRainfallMedTo(Integer rainfallMedTo) {
        this.rainfallMedTo = rainfallMedTo;
    }

    public Integer getRelativeHumidityDisasterFrom() {
        return relativeHumidityDisasterFrom;
    }

    public void setRelativeHumidityDisasterFrom(Integer relativeHumidityDisasterFrom) {
        this.relativeHumidityDisasterFrom = relativeHumidityDisasterFrom;
    }

    public Integer getSeedQuantity() {
        return seedQuantity;
    }

    public void setSeedQuantity(Integer seedQuantity) {
        this.seedQuantity = seedQuantity;
    }

    public Integer getTemperatureDisasterFrom() {
        return temperatureDisasterFrom;
    }

    public void setTemperatureDisasterFrom(Integer temperatureDisasterFrom) {
        this.temperatureDisasterFrom = temperatureDisasterFrom;
    }

    public Integer getTemperatureDisasterTo() {
        return temperatureDisasterTo;
    }

    public void setTemperatureDisasterTo(Integer temperatureDisasterTo) {
        this.temperatureDisasterTo = temperatureDisasterTo;
    }

    public Integer getWindDirectionDisasterTo() {
        return windDirectionDisasterTo;
    }

    public void setWindDirectionDisasterTo(Integer windDirectionDisasterTo) {
        this.windDirectionDisasterTo = windDirectionDisasterTo;
    }

    public Integer getWindDirectionMedFrom() {
        return windDirectionMedFrom;
    }

    public void setWindDirectionMedFrom(Integer windDirectionMedFrom) {
        this.windDirectionMedFrom = windDirectionMedFrom;
    }

    public Integer getRelativeHumidityDisasterTo() {
        return relativeHumidityDisasterTo;
    }

    public void setRelativeHumidityDisasterTo(Integer relativeHumidityDisasterTo) {
        this.relativeHumidityDisasterTo = relativeHumidityDisasterTo;
    }

    public Integer getWindDirectionMedTo() {
        return windDirectionMedTo;
    }

    public void setWindDirectionMedTo(Integer windDirectionMedTo) {
        this.windDirectionMedTo = windDirectionMedTo;
    }

    public Integer getWindSpeedDisasterFrom() {
        return windSpeedDisasterFrom;
    }

    public void setWindSpeedDisasterFrom(Integer windSpeedDisasterFrom) {
        this.windSpeedDisasterFrom = windSpeedDisasterFrom;
    }

    public Integer getWindSpeedDisasterTo() {
        return windSpeedDisasterTo;
    }

    public void setWindSpeedDisasterTo(Integer windSpeedDisasterTo) {
        this.windSpeedDisasterTo = windSpeedDisasterTo;
    }

    public Integer getWindSpeedMedFrom() {
        return windSpeedMedFrom;
    }

    public void setWindSpeedMedFrom(Integer windSpeedMedFrom) {
        this.windSpeedMedFrom = windSpeedMedFrom;
    }

    public Integer getWindSpeedMedTo() {
        return windSpeedMedTo;
    }

    public void setWindSpeedMedTo(Integer windSpeedMedTo) {
        this.windSpeedMedTo = windSpeedMedTo;
    }

    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getAssert() {
        return _assert;
    }

    public void setAssert(List<String> _assert) {
        this._assert = _assert;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public Boolean getIsleadwomenhasmobile() {
        return isleadwomenhasmobile;
    }

    public void setIsleadwomenhasmobile(Boolean isleadwomenhasmobile) {
        this.isleadwomenhasmobile = isleadwomenhasmobile;
    }

    public String getWomenMobileNumber() {
        return womenMobileNumber;
    }

    public void setWomenMobileNumber(String womenMobileNumber) {
        this.womenMobileNumber = womenMobileNumber;
    }

    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    public String getDisabilityType() {
        return disabilityType;
    }

    public void setDisabilityType(String disabilityType) {
        this.disabilityType = disabilityType;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAppRegistrationToken() {
        return appRegistrationToken;
    }

    public void setAppRegistrationToken(String appRegistrationToken) {
        this.appRegistrationToken = appRegistrationToken;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }


}
