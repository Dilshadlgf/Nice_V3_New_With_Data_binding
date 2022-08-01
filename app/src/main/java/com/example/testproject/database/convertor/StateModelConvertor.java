package com.example.testproject.database.convertor;

import androidx.room.TypeConverter;

import com.example.testproject.model.FarmerRefModel;
import com.example.testproject.model.RoleModel;
import com.example.testproject.model.StateDataModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class StateModelConvertor {


    @TypeConverter
    public static StateDataModel fromStateDataModel(String value) {
        Type listType = new TypeToken<StateDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromStateDataModel(StateDataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
