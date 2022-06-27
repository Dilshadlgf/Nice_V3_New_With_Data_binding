package com.example.testproject.model.Root;

import com.example.testproject.model.CropDataModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataModelOne {
    public String data;
    @SerializedName("farmerCrop")
    private CropDataModel farmerCrop;
}
