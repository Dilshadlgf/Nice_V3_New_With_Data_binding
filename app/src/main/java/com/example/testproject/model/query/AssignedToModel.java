package com.example.testproject.model.query;

import com.example.testproject.model.CreatedOn;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignedToModel {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("project")
        @Expose
        private List<String> project = null;
        @SerializedName("alternateNumber")
        @Expose
        private String alternateNumber;
        @SerializedName("fatherName")
        @Expose
        private String fatherName;
        @SerializedName("father_husbandName")
        @Expose
        private String fatherHusbandName;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("stateCode")
        @Expose
        private String stateCode;
        @SerializedName("district")
        @Expose
        private String district;
        @SerializedName("districtCode")
        @Expose
        private String districtCode;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("pinCode")
        @Expose
        private Integer pinCode;
        @SerializedName("spouseName")
        @Expose
        private String spouseName;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("mobileNumber")
        @Expose
        private String mobileNumber;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("dateOfBirth")
        @Expose
        private String dateOfBirth;
        @SerializedName("createdDate")
        @Expose
        private Object createdDate;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("profile")
        @Expose
        private String profile;
        @SerializedName("organisationId")
        @Expose
        private String organisationId;
        @SerializedName("confirmpassword")
        @Expose
        private String confirmpassword;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("createdOn")
        @Expose
        private CreatedOn createdOn;
        @SerializedName("updated")
        @Expose
        private Updated updated;
        @SerializedName("updatedLog")
        @Expose
        private Object updatedLog;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("managerId")
        @Expose
        private String managerId;
        @SerializedName("bloodGroup")
        @Expose
        private String bloodGroup;
//        @SerializedName("collectionLimit")
//        @Expose
        //private CollectionLimitModel collectionLimit;
        @SerializedName("accessPrivilege")
        @Expose
        private AccessPrivilegeModel accessPrivilege;
        @SerializedName("experience")
        @Expose
        private Integer experience;
        @SerializedName("educationalQualification")
        @Expose
        private String educationalQualification;
        @SerializedName("knowledgeDomains")
        @Expose
        private Object knowledgeDomains;
        @SerializedName("subDomains")
        @Expose
        private Object subDomains;
        @SerializedName("organisation")
        @Expose
        private String organisation;
        @SerializedName("officenumber")
        @Expose
        private String officenumber;
        @SerializedName("officeaddress")
        @Expose
        private String officeaddress;
        @SerializedName("organisationNatureofBusiness")
        @Expose
        private String organisationNatureofBusiness;
        @SerializedName("kdExpertise")
        @Expose
        private String kdExpertise;
        @SerializedName("mailNotify")
        @Expose
        private Boolean mailNotify;
        @SerializedName("smsNotify")
        @Expose
        private Boolean smsNotify;
        @SerializedName("isSelfRegistration")
        @Expose
        private Boolean isSelfRegistration;
        @SerializedName("languageExpertise")
        @Expose
        private String languageExpertise;
        @SerializedName("languagesKnown")
        @Expose
        private Object languagesKnown;
        @SerializedName("viewLanguage")
        @Expose
        private String viewLanguage;
        @SerializedName("subjectExpertise")
        @Expose
        private String subjectExpertise;
        @SerializedName("occupation")
        @Expose
        private String occupation;
        @SerializedName("userType")
        @Expose
        private String userType;
        @SerializedName("proofType")
        @Expose
        private String proofType;
        @SerializedName("proofNo")
        @Expose
        private String proofNo;
        @SerializedName("userOrg")
        @Expose
        private String userOrg;
        @SerializedName("village")
        @Expose
        private String village;
        @SerializedName("villageCode")
        @Expose
        private String villageCode;
        @SerializedName("smsCount")
        @Expose
        private Integer smsCount;
        @SerializedName("block")
        @Expose
        private String block;
        @SerializedName("blockCode")
        @Expose
        private String blockCode;
        @SerializedName("grampanchayat")
        @Expose
        private String grampanchayat;
        @SerializedName("grampanchayatCode")
        @Expose
        private String grampanchayatCode;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public List<String> getProject() {
            return project;
        }

        public void setProject(List<String> project) {
            this.project = project;
        }

        public String getAlternateNumber() {
            return alternateNumber;
        }

        public void setAlternateNumber(String alternateNumber) {
            this.alternateNumber = alternateNumber;
        }

        public String getFatherName() {
            return fatherName;
        }

        public void setFatherName(String fatherName) {
            this.fatherName = fatherName;
        }

        public String getFatherHusbandName() {
            return fatherHusbandName;
        }

        public void setFatherHusbandName(String fatherHusbandName) {
            this.fatherHusbandName = fatherHusbandName;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStateCode() {
            return stateCode;
        }

        public void setStateCode(String stateCode) {
            this.stateCode = stateCode;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getDistrictCode() {
            return districtCode;
        }

        public void setDistrictCode(String districtCode) {
            this.districtCode = districtCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Integer getPinCode() {
            return pinCode;
        }

        public void setPinCode(Integer pinCode) {
            this.pinCode = pinCode;
        }

        public String getSpouseName() {
            return spouseName;
        }

        public void setSpouseName(String spouseName) {
            this.spouseName = spouseName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public Object getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(Object createdDate) {
            this.createdDate = createdDate;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getOrganisationId() {
            return organisationId;
        }

        public void setOrganisationId(String organisationId) {
            this.organisationId = organisationId;
        }

        public String getConfirmpassword() {
            return confirmpassword;
        }

        public void setConfirmpassword(String confirmpassword) {
            this.confirmpassword = confirmpassword;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public CreatedOn getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(CreatedOn createdOn) {
            this.createdOn = createdOn;
        }

        public Updated getUpdated() {
            return updated;
        }

        public void setUpdated(Updated updated) {
            this.updated = updated;
        }

        public Object getUpdatedLog() {
            return updatedLog;
        }

        public void setUpdatedLog(Object updatedLog) {
            this.updatedLog = updatedLog;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getManagerId() {
            return managerId;
        }

        public void setManagerId(String managerId) {
            this.managerId = managerId;
        }

        public String getBloodGroup() {
            return bloodGroup;
        }

        public void setBloodGroup(String bloodGroup) {
            this.bloodGroup = bloodGroup;
        }

//        public CollectionLimitModel getCollectionLimit() {
//            return collectionLimit;
//        }
//
//        public void setCollectionLimit(CollectionLimitModel collectionLimit) {
//            this.collectionLimit = collectionLimit;
//        }

        public AccessPrivilegeModel getAccessPrivilege() {
            return accessPrivilege;
        }

        public void setAccessPrivilege(AccessPrivilegeModel accessPrivilege) {
            this.accessPrivilege = accessPrivilege;
        }

        public Integer getExperience() {
            return experience;
        }

        public void setExperience(Integer experience) {
            this.experience = experience;
        }

        public String getEducationalQualification() {
            return educationalQualification;
        }

        public void setEducationalQualification(String educationalQualification) {
            this.educationalQualification = educationalQualification;
        }

        public Object getKnowledgeDomains() {
            return knowledgeDomains;
        }

        public void setKnowledgeDomains(Object knowledgeDomains) {
            this.knowledgeDomains = knowledgeDomains;
        }

        public Object getSubDomains() {
            return subDomains;
        }

        public void setSubDomains(Object subDomains) {
            this.subDomains = subDomains;
        }

        public String getOrganisation() {
            return organisation;
        }

        public void setOrganisation(String organisation) {
            this.organisation = organisation;
        }

        public String getOfficenumber() {
            return officenumber;
        }

        public void setOfficenumber(String officenumber) {
            this.officenumber = officenumber;
        }

        public String getOfficeaddress() {
            return officeaddress;
        }

        public void setOfficeaddress(String officeaddress) {
            this.officeaddress = officeaddress;
        }

        public String getOrganisationNatureofBusiness() {
            return organisationNatureofBusiness;
        }

        public void setOrganisationNatureofBusiness(String organisationNatureofBusiness) {
            this.organisationNatureofBusiness = organisationNatureofBusiness;
        }

        public String getKdExpertise() {
            return kdExpertise;
        }

        public void setKdExpertise(String kdExpertise) {
            this.kdExpertise = kdExpertise;
        }

        public Boolean getMailNotify() {
            return mailNotify;
        }

        public void setMailNotify(Boolean mailNotify) {
            this.mailNotify = mailNotify;
        }

        public Boolean getSmsNotify() {
            return smsNotify;
        }

        public void setSmsNotify(Boolean smsNotify) {
            this.smsNotify = smsNotify;
        }

        public Boolean getIsSelfRegistration() {
            return isSelfRegistration;
        }

        public void setIsSelfRegistration(Boolean isSelfRegistration) {
            this.isSelfRegistration = isSelfRegistration;
        }

        public String getLanguageExpertise() {
            return languageExpertise;
        }

        public void setLanguageExpertise(String languageExpertise) {
            this.languageExpertise = languageExpertise;
        }

        public Object getLanguagesKnown() {
            return languagesKnown;
        }

        public void setLanguagesKnown(Object languagesKnown) {
            this.languagesKnown = languagesKnown;
        }

        public String getViewLanguage() {
            return viewLanguage;
        }

        public void setViewLanguage(String viewLanguage) {
            this.viewLanguage = viewLanguage;
        }

        public String getSubjectExpertise() {
            return subjectExpertise;
        }

        public void setSubjectExpertise(String subjectExpertise) {
            this.subjectExpertise = subjectExpertise;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getProofType() {
            return proofType;
        }

        public void setProofType(String proofType) {
            this.proofType = proofType;
        }

        public String getProofNo() {
            return proofNo;
        }

        public void setProofNo(String proofNo) {
            this.proofNo = proofNo;
        }

        public String getUserOrg() {
            return userOrg;
        }

        public void setUserOrg(String userOrg) {
            this.userOrg = userOrg;
        }

        public String getVillage() {
            return village;
        }

        public void setVillage(String village) {
            this.village = village;
        }

        public String getVillageCode() {
            return villageCode;
        }

        public void setVillageCode(String villageCode) {
            this.villageCode = villageCode;
        }

        public Integer getSmsCount() {
            return smsCount;
        }

        public void setSmsCount(Integer smsCount) {
            this.smsCount = smsCount;
        }

        public String getBlock() {
            return block;
        }

        public void setBlock(String block) {
            this.block = block;
        }

        public String getBlockCode() {
            return blockCode;
        }

        public void setBlockCode(String blockCode) {
            this.blockCode = blockCode;
        }

        public String getGrampanchayat() {
            return grampanchayat;
        }

        public void setGrampanchayat(String grampanchayat) {
            this.grampanchayat = grampanchayat;
        }

        public String getGrampanchayatCode() {
            return grampanchayatCode;
        }

        public void setGrampanchayatCode(String grampanchayatCode) {
            this.grampanchayatCode = grampanchayatCode;
        }




}
