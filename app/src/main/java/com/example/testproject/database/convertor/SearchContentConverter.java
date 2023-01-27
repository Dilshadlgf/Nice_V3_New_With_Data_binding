package com.example.testproject.database.convertor;

import android.util.Log;

import androidx.room.TypeConverter;

import com.example.testproject.model.SearchContentResponseDataModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SearchContentConverter {
    @TypeConverter
    public static List<SearchContentResponseDataModel> fromString(String value) {
        Type listType = new TypeToken<List<SearchContentResponseDataModel>>() {}.getType();
        Log.e("value",value);
        return  new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromSearchContentResponseDataModel(List<SearchContentResponseDataModel> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
