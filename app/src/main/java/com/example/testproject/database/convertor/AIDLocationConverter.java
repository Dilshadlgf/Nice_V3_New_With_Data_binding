package com.example.testproject.database.convertor;

import androidx.room.TypeConverter;

import com.example.testproject.model.AIDLocationResponseDataModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AIDLocationConverter {
    @TypeConverter
    public static List<AIDLocationResponseDataModel> fromString(String value) {
        Type listType = new TypeToken<List<AIDLocationResponseDataModel>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromArrayList(List<AIDLocationResponseDataModel> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
