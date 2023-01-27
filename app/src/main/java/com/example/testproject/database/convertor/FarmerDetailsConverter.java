package com.example.testproject.database.convertor;

import androidx.room.TypeConverter;

import com.example.testproject.model.FarmerDataModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class FarmerDetailsConverter {
    @TypeConverter
    public static FarmerDataModel fromFarmerDataModelString(String value) {
        Type listType = new TypeToken<FarmerDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromFarmerDataModel(FarmerDataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static List<Object> fromObjectString(String value) {
        Type listType = new TypeToken<List<Object>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObject(List<Object> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
//    @TypeConverter
//    public static String fromfarmerloginDataModel(FarmerLoginResponseDataModel Farmr) {
//        Gson gson = new Gson();
//        String json = gson.toJson(Farmr);
//        return json;
//    }
    }

