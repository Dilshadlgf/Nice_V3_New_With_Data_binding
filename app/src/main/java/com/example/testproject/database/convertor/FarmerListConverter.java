package com.example.testproject.database.convertor;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class FarmerListConverter {
//    @TypeConverter
//    public static List<livestoks> fromFarmerListDataModelString(String value) {
//        Type listType = new TypeToken<List<livestoks>>() {}.getType();
//        return new Gson().fromJson(value, listType);
//    }
//    @TypeConverter
//    public static String fromFarmerListDataModel(List<livestoks> list) {
//        Gson gson = new Gson();
//        String json = gson.toJson(list);
//        return json;
//    }

    @TypeConverter
    public static Object fromFarmerObject(String value) {
        Type listType = new TypeToken<Object>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObject(Object list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

//    @TypeConverter
//    public static FarmerRefModel fromFarmerRefModel(String value) {
//        Type listType = new TypeToken<FarmerRefModel>() {}.getType();
//        return new Gson().fromJson(value, listType);
//    }
//    @TypeConverter
//    public static String fromFarmerRefModel(FarmerRefModel list) {
//        Gson gson = new Gson();
//        String json = gson.toJson(list);
//        return json;
//    }
}
