package com.example.testproject.database.convertor;

import androidx.room.TypeConverter;

import com.example.testproject.model.WeatherDataModel;
import com.example.testproject.model.WeatherFeelsLikeModel;
import com.example.testproject.model.WeatherStateModel;
import com.example.testproject.model.WeatherTemperatureModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class WeatherDetailsConverter {
    @TypeConverter
    public static WeatherStateModel fromString(String value) {
        Type listType = new TypeToken<WeatherStateModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromWeatherDetailsResponseDataModel(WeatherStateModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static Map<String,Object> fromStringToObject(String value) {
        Type listType = new TypeToken<Map<String,Object>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToString(Map<String,Object> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static WeatherDataModel fromFarmerObject(String value) {
        Type listType = new TypeToken<WeatherDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObject(WeatherDataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static WeatherTemperatureModel fromTempObject(String value) {
        Type listType = new TypeToken<WeatherTemperatureModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObject(WeatherTemperatureModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static WeatherFeelsLikeModel fromFeellikeObject(String value) {
        Type listType = new TypeToken<WeatherFeelsLikeModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObject(WeatherFeelsLikeModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
