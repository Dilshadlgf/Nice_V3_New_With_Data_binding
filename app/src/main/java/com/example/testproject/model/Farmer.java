package com.example.testproject.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Farmer {

    @SerializedName("id")
    private String id;

    @SerializedName("alternateNumber")
    private String alternateNumber;

    @SerializedName("aadhaarNumber")
    private String aadhaarNumber;

    @SerializedName("education")
    private String education;

    @SerializedName("fatherName")
    private String fatherName;

    @SerializedName("currentLiveStocks")
    private String currentLiveStocks;

    @SerializedName("currentcrops")
    private String currentCrops;

    @SerializedName("cultivationPractice")
    private String cultivationPractice;

    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private String gender;

    @SerializedName("dateOfBirth")
    private String dateOfBirth;

    @SerializedName("status")
    private String status;

    @SerializedName("distict")
    private String distict;

    @SerializedName("gramPanchayat")
    private String gramPanchayat;

    @SerializedName("isMemberInvolvedInCbo")
    private Boolean isMemberInvolvedInCbo;

    @SerializedName("activeStatus")
    private Boolean activeStatus;

    @SerializedName("isSMS")
    private Boolean isSMS;

    @SerializedName("feminineMobile")
    private Boolean feminineMobile;

    @SerializedName("creditAvailed")
    private Boolean creditAvailed;

    @SerializedName("hasKitchenGarden")
    private Boolean hasKitchenGarden;

    @SerializedName("likeToReceiveSMS")
    private Boolean likeToReceiveSMS;

    @SerializedName("likeToReceiveVoicecall")
    private Boolean likeToReceiveVoicecall;

    @SerializedName("hasCredits")
    private Boolean hasCredits;

    @SerializedName("memberOfMGNREGNA")
    private Boolean memberOfMGNREGNA;

    @SerializedName("isPhysicallyDisabled")
    private Boolean isPhysicallyDisabled;

    @SerializedName("isAnyMmemberOfFamilyInCBO")
    private Boolean isAnyMmemberOfFamilyInCBO;

    @SerializedName("createdOn")
    private CreatedOn createdOn;

    @SerializedName("block")
    private String block;

    @SerializedName("version")
    private Float version;

    @SerializedName("isVoiceSMS")
    private Boolean isVoiceSMS;

    @SerializedName("kitchenGarden")
    private Boolean kitchenGarden;

    @SerializedName("leasedInIrrigated")
    private Integer leasedInIrrigated;

    @SerializedName("leasedInRainfed")
    private  Integer leasedInRainfed;

    @SerializedName("leasedOutIrrigated")
    private Integer leasedOutIrrigated;


    @SerializedName("leasedOutRainfed")
    private Integer leasedOutRainfed;

    @SerializedName("membershipInMgnrega")
    private Boolean membershipInMgnrega;

    @SerializedName("mobileNumber")
    private String mobileNumber;

    @SerializedName("landLineNumber")
    private String landLineNumber;

    @SerializedName("ownedIrrigated")
    private Integer ownedIrrigated;

    @SerializedName("ownedRainfed")
    private Integer ownedRainfed;

    @SerializedName("soilType")
    private String soilType;

    @SerializedName("spouseName")
    private String spouseName;

    @SerializedName("state")
    private String state;

    @SerializedName("totalLand")
    private Integer totalLand;

    @SerializedName("totalMobiles")
    private Integer totalMobiles;

    @SerializedName("village")
    private String village;

    @SerializedName("yearlyIncome")
    private String yearlyIncome;

    @SerializedName("farmerOrg")
    private String farmerOrg;

    @SerializedName("farmerID")
    private String farmerID;

    @SerializedName("doorNo")
    private String doorNo;

    @SerializedName("landMark")
    private String landMark;

    @SerializedName("street")
    private String street;


    // create setter And getter Method

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

    public String getCurrentLiveStocks() {
        return currentLiveStocks;
    }

    public void setCurrentLiveStocks(String currentLiveStocks) {
        this.currentLiveStocks = currentLiveStocks;
    }

    public String getCurrentCrops() {
        return currentCrops;
    }

    public void setCurrentCrops(String currentCrops) {
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

    public String getDistict() {
        return distict;
    }

    public void setDistict(String distict) {
        this.distict = distict;
    }

    public String getGramPanchayat() {
        return gramPanchayat;
    }

    public void setGramPanchayat(String gramPanchayat) {
        this.gramPanchayat = gramPanchayat;
    }

    public Boolean getMemberInvolvedInCbo() {
        return isMemberInvolvedInCbo;
    }

    public void setMemberInvolvedInCbo(Boolean memberInvolvedInCbo) {
        isMemberInvolvedInCbo = memberInvolvedInCbo;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Boolean getSMS() {
        return isSMS;
    }

    public void setSMS(Boolean SMS) {
        isSMS = SMS;
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

    public Float getVersion() {
        return version;
    }

    public void setVersion(Float version) {
        this.version = version;
    }

    public Boolean getVoiceSMS() {
        return isVoiceSMS;
    }

    public void setVoiceSMS(Boolean voiceSMS) {
        isVoiceSMS = voiceSMS;
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



  /*  private String id;
    private float version;
    private String name;
    private String fatherName;
    private String spouseName;
    private String dateOfBirth;
    private String gender;
    private String education;
    private String landLine = null;
    private String adharNumber;
    private String mobileNumber;
    private String alternateNumber = null;
    private boolean feminineMobile;
    private String womenNumber = null;
    private float totalMobiles;
    private String yearlyIncome;
    private String doorNo;
    private String street;
    private String landMark;
    Village VillageObject;

    private String cultivationPractice = null;
    private boolean membershipInMgnrega;
    private boolean creditAvailed;
    ArrayList<Object> credits = new ArrayList<Object>();
    private boolean isMemberInvolvedInCbo;
    private boolean isSMS;
    private boolean isVoiceSMS;
    ArrayList<Object> memberNameInvolvedInCbo = new ArrayList<Object>();
    private boolean kitchenGarden;

    ArrayList<Object> projects = new ArrayList<Object>();
    private float totalLand;
    ArrayList<Object> assets = new ArrayList<Object>();
    ArrayList<Object> preferredMarkets = new ArrayList<Object>();
    SoilType SoilTypeObject;
    private boolean activeStatus;
    private String createdBy;
    ArrayList<Object> farmerCrops = new ArrayList<Object>();
    ArrayList<Object> farmerLiveStocks = new ArrayList<Object>();
    ArrayList<Object> queries = new ArrayList<Object>();
    ArrayList<Object> feedbacks = new ArrayList<Object>();

    private boolean isDisabled;
    private String disability;


    // Getter Methods

    public String getId() {
        return id;
    }

    public float getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getEducation() {
        return education;
    }

    public String getLandLine() {
        return landLine;
    }

    public String getAdharNumber() {
        return adharNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAlternateNumber() {
        return alternateNumber;
    }

    public boolean getFeminineMobile() {
        return feminineMobile;
    }

    public String getWomenNumber() {
        return womenNumber;
    }

    public float getTotalMobiles() {
        return totalMobiles;
    }

    public String getYearlyIncome() {
        return yearlyIncome;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public String getStreet() {
        return street;
    }

    public String getLandMark() {
        return landMark;
    }

    public Village getVillage() {
        return VillageObject;
    }

    public GramPanchayat getGramPanchayat() {
        return GramPanchayatObject;
    }

    public Block getBlock() {
        return BlockObject;
    }

    public District getDistrict() {
        return DistrictObject;
    }

    public State getState() {
        return StateObject;
    }

    public String getCultivationPractice() {
        return cultivationPractice;
    }

    public boolean getMembershipInMgnrega() {
        return membershipInMgnrega;
    }

    public boolean getCreditAvailed() {
        return creditAvailed;
    }

    public boolean getIsMemberInvolvedInCbo() {
        return isMemberInvolvedInCbo;
    }

    public boolean getIsSMS() {
        return isSMS;
    }

    public boolean getIsVoiceSMS() {
        return isVoiceSMS;
    }

    public boolean getKitchenGarden() {
        return kitchenGarden;
    }

    public FarmerOrg getFarmerOrg() {
        return FarmerOrgObject;
    }

    public float getTotalLand() {
        return totalLand;
    }

    public SoilType getSoilType() {
        return SoilTypeObject;
    }

    public boolean getActiveStatus() {
        return activeStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Caste getCaste() {
        return CasteObject;
    }

    public boolean getIsDisabled() {
        return isDisabled;
    }

    public String getDisability() {
        return disability;
    }

    // Setter Methods

    public void setId( String id ) {
        this.id = id;
    }

    public void setVersion( float version ) {
        this.version = version;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void setFatherName( String fatherName ) {
        this.fatherName = fatherName;
    }

    public void setSpouseName( String spouseName ) {
        this.spouseName = spouseName;
    }

    public void setDateOfBirth( String dateOfBirth ) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender( String gender ) {
        this.gender = gender;
    }

    public void setEducation( String education ) {
        this.education = education;
    }

    public void setLandLine( String landLine ) {
        this.landLine = landLine;
    }

    public void setAdharNumber( String adharNumber ) {
        this.adharNumber = adharNumber;
    }

    public void setMobileNumber( String mobileNumber ) {
        this.mobileNumber = mobileNumber;
    }

    public void setAlternateNumber( String alternateNumber ) {
        this.alternateNumber = alternateNumber;
    }

    public void setFeminineMobile( boolean feminineMobile ) {
        this.feminineMobile = feminineMobile;
    }

    public void setWomenNumber( String womenNumber ) {
        this.womenNumber = womenNumber;
    }

    public void setTotalMobiles( float totalMobiles ) {
        this.totalMobiles = totalMobiles;
    }

    public void setYearlyIncome( String yearlyIncome ) {
        this.yearlyIncome = yearlyIncome;
    }

    public void setDoorNo( String doorNo ) {
        this.doorNo = doorNo;
    }

    public void setStreet( String street ) {
        this.street = street;
    }

    public void setLandMark( String landMark ) {
        this.landMark = landMark;
    }

    public void setVillage( Village villageObject ) {
        this.VillageObject = villageObject;
    }

    public void setGramPanchayat( GramPanchayat gramPanchayatObject ) {
        this.GramPanchayatObject = gramPanchayatObject;
    }

    public void setBlock( Block blockObject ) {
        this.BlockObject = blockObject;
    }

    public void setDistrict( District districtObject ) {
        this.DistrictObject = districtObject;
    }

    public void setState( State stateObject ) {
        this.StateObject = stateObject;
    }

    public void setCultivationPractice( String cultivationPractice ) {
        this.cultivationPractice = cultivationPractice;
    }

    public void setMembershipInMgnrega( boolean membershipInMgnrega ) {
        this.membershipInMgnrega = membershipInMgnrega;
    }

    public void setCreditAvailed( boolean creditAvailed ) {
        this.creditAvailed = creditAvailed;
    }

    public void setIsMemberInvolvedInCbo( boolean isMemberInvolvedInCbo ) {
        this.isMemberInvolvedInCbo = isMemberInvolvedInCbo;
    }

    public void setIsSMS( boolean isSMS ) {
        this.isSMS = isSMS;
    }

    public void setIsVoiceSMS( boolean isVoiceSMS ) {
        this.isVoiceSMS = isVoiceSMS;
    }

    public void setKitchenGarden( boolean kitchenGarden ) {
        this.kitchenGarden = kitchenGarden;
    }

    public void setFarmerOrg( FarmerOrg farmerOrgObject ) {
        this.FarmerOrgObject = farmerOrgObject;
    }

    public void setTotalLand( float totalLand ) {
        this.totalLand = totalLand;
    }

    public void setSoilType( SoilType soilTypeObject ) {
        this.SoilTypeObject = soilTypeObject;
    }

    public void setActiveStatus( boolean activeStatus ) {
        this.activeStatus = activeStatus;
    }

    public void setCreatedBy( String createdBy ) {
        this.createdBy = createdBy;
    }

    public void setCaste( Caste casteObject ) {
        this.CasteObject = casteObject;
    }

    public void setIsDisabled( boolean isDisabled ) {
        this.isDisabled = isDisabled;
    }

    public void setDisability( String disability ) {
        this.disability = disability;
    }*/
}
