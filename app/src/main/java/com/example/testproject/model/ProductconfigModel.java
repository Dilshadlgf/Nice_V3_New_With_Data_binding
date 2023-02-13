package com.example.testproject.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "default")

public class ProductconfigModel {
    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public String logo;
    public String waterMark;
    public String logoWithName;
//    public Email email;
    public String mobile;
    public String phone;
    public String address;
    public String copyrights;
    public String poweredBy;
    public String rights;
    public String status;
    public boolean isdefault;
    public boolean issingleProject;
    public boolean issms;
    public String isUser;
    public String isDealer;
    public String isFarmer;
    public boolean validateUserRegistration;
    public boolean apptoken;
    public boolean weatherAlert;
    public String farmerUpload;
    public String farmerCasteUpload;
    public String farmerSoilUpload;
    public String farmerLandUpload;
    public String detailedContentCreation;
    public String farmerCropUpload;
    public AddressModel state;
    public AddressModel district;
    public ProjectModel orgnisation;
    public ProjectModel project;
    public ProjectModel knowledgedomain;
    public String isLanguageTranslation;
    public int expiryMonth;
}
