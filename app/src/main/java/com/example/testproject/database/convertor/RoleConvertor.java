package com.example.testproject.database.convertor;

import androidx.room.TypeConverter;

import com.example.testproject.model.RoleModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class RoleConvertor {

    @TypeConverter
    public static RoleModel fromRoleModelToObject(String value) {
        Type listType = new TypeToken<RoleModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToRoleModel(RoleModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
