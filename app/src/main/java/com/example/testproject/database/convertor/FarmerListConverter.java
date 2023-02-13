package com.example.testproject.database.convertor;

import androidx.room.TypeConverter;

import com.example.testproject.model.AddressModel;
import com.example.testproject.model.LivestockModel;
import com.example.testproject.model.ProductconfigModel;
import com.example.testproject.model.ProjectModel;
import com.example.testproject.model.RefModel;
import com.example.testproject.model.TemperatureModel;
import com.example.testproject.model.UserModel;
import com.example.testproject.model.WeatherModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class FarmerListConverter {

    @TypeConverter
    public static List<LivestockModel> fromFarmerListDataModelString(String value) {
        Type listType = new TypeToken<List<LivestockModel>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromFarmerListDataModel(List<LivestockModel> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

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
    @TypeConverter
    public static List<WeatherModel> fromWeatherModelString(String value) {
        Type listType = new TypeToken<List<WeatherModel>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromWeatherModel(List<WeatherModel> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static WeatherModel fromWeatherObject(String value) {
        Type listType = new TypeToken<WeatherModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromWeather(WeatherModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static RefModel fromRefObject(String value) {
        Type listType = new TypeToken<RefModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromRef(RefModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static List<TemperatureModel> fromTemperatureModelString(String value) {
        Type listType = new TypeToken<List<TemperatureModel>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromTemperatureModel(List<TemperatureModel> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static TemperatureModel fromTemObject(String value) {
        Type listType = new TypeToken<TemperatureModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromTem(TemperatureModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static List<ProjectModel> fromProjectModelString(String value) {
        Type listType = new TypeToken<List<ProjectModel>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromProjectModel(List<ProjectModel> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static ProjectModel fromProObject(String value) {
        Type listType = new TypeToken<ProjectModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromPro(ProjectModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static List<UserModel> fromUserModelString(String value) {
        Type listType = new TypeToken<List<UserModel>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromUserModel(List<UserModel> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static UserModel fromUserObject(String value) {
        Type listType = new TypeToken<UserModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromUser(UserModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static List<String> fromStringModelString(String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromStringModel(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static List<ProductconfigModel> fromProductconfigModelString(String value) {
        Type listType = new TypeToken<List<ProductconfigModel>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromProductconfigModel(List<ProductconfigModel> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static ProductconfigModel fromProductconfigModelObject(String value) {
        Type listType = new TypeToken<ProductconfigModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromProductconfigModel(ProductconfigModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static AddressModel fromAddressModelObject(String value) {
        Type listType = new TypeToken<AddressModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromAddressModel(AddressModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
