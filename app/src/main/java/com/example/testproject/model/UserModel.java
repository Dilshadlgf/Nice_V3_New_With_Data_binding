package com.example.testproject.model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "user")
public class UserModel {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("userName")
    @Expose
    public String userName;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("lastname")
    @Expose
    public String lastname;
    @SerializedName("project")
    @Expose
    public List<String> project;
    @SerializedName("alternateNumber")
    @Expose
    public String alternateNumber;
    @SerializedName("fatherName")
    @Expose
    public String fatherName;
    @SerializedName("father_husbandName")
    @Expose
    public String fatherHusbandName;
    @SerializedName("state")
    @Expose
    public String state;

    public String stateId;
    @SerializedName("stateCode")
    @Expose
    public String stateCode;
    @SerializedName("district")
    @Expose
    public String district;
    @SerializedName("districtCode")
    @Expose
    public String districtCode;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("pinCode")
    @Expose
    public Integer pinCode;
    @SerializedName("spouseName")
    @Expose
    public String spouseName;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("mobileNumber")
    @Expose
    public String mobileNumber;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("dateOfBirth")
    @Expose
    public String dateOfBirth;
    @SerializedName("createdDate")
    @Expose
    public String createdDate;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("profile")
    @Expose
    public String profile;
    @SerializedName("organisationId")
    @Expose
    public String organisationId;
    @SerializedName("confirmpassword")
    @Expose
    public String confirmpassword;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("designation")
    @Expose
    public String designation;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("updatedLog")
    @Expose
    public Object updatedLog;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("managerId")
    @Expose
    public String managerId;
    @SerializedName("bloodGroup")
    @Expose
    public String bloodGroup;
    @SerializedName("experience")
    @Expose
    public Integer experience;
    @SerializedName("educationalQualification")
    @Expose
    public String educationalQualification;
    @SerializedName("knowledgeDomains")
    @Expose
    public Object knowledgeDomains;
    @SerializedName("subDomains")
    @Expose
    public Object subDomains;
    @SerializedName("organisation")
    @Expose
    public String organisation;
    @SerializedName("officenumber")
    @Expose
    public String officenumber;
    @SerializedName("officeaddress")
    @Expose
    public String officeaddress;
    @SerializedName("organisationNatureofBusiness")
    @Expose
    public String organisationNatureofBusiness;
    @SerializedName("kdExpertise")
    @Expose
    public String kdExpertise;
    @SerializedName("mailNotify")
    @Expose
    public Boolean mailNotify;
    @SerializedName("smsNotify")
    @Expose
    public Boolean smsNotify;
    @SerializedName("isSelfRegistration")
    @Expose
    public Boolean isSelfRegistration;
    @SerializedName("languageExpertise")
    @Expose
    public String languageExpertise;
    @SerializedName("languagesKnown")
    @Expose
    public Object languagesKnown;
    @SerializedName("viewLanguage")
    @Expose
    public String viewLanguage;
    @SerializedName("subjectExpertise")
    @Expose
    public String subjectExpertise;
    @SerializedName("occupation")
    @Expose
    public String occupation;
    @SerializedName("userType")
    @Expose
    public String userType;
    @SerializedName("proofType")
    @Expose
    public String proofType;
    @SerializedName("proofNo")
    @Expose
    public String proofNo;
    @SerializedName("userOrg")
    @Expose
    public String userOrg;
    @SerializedName("village")
    @Expose
    public String village;
    @SerializedName("villageCode")
    @Expose
    public String villageCode;
    @SerializedName("smsCount")
    @Expose
    public Integer smsCount;
    @SerializedName("block")
    @Expose
    public String block;
    @SerializedName("blockCode")
    @Expose
    public String blockCode;
    @SerializedName("grampanchayat")
    @Expose
    public String grampanchayat;
    @SerializedName("grampanchayatCode")
    @Expose
    public String grampanchayatCode;
    public String rating;
    public String date;
    public String feedback;
    public boolean isFarmer;
    public boolean isUser;

    public int liveStockCount;

    public RefModel ref;

    @SerializedName("vacantArea")
    @Expose
    public Integer vacantArea;

    @SerializedName("cropCount")
    @Expose
    public Integer cropCount;

    @SerializedName("cultivatedArea")
    public Integer cultivatedArea;

    @SerializedName("totalLand")
    @Expose
    public Integer totalLand;

    public String uniqueId;

    public String queryType;

    public ProjectModel createdOn;
    public String query;
    public String resolvedBy;
    public String resolvedDate;
    public String solution;
    public String createdType;
    public String assignedDate;
    public List<String> images;




}
