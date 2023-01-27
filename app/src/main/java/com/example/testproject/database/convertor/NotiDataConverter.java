package com.example.testproject.database.convertor;

import androidx.room.TypeConverter;

import com.example.testproject.model.NotificationDataModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class NotiDataConverter {

    @TypeConverter
    public static NotificationDataModel fromNotification(String value) {
        Type listType = new TypeToken<NotificationDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromNotification(NotificationDataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

//    @TypeConverter
//    public static District fromDist(String value) {
//        Type listType = new TypeToken<District>() {}.getType();
//        return new Gson().fromJson(value, listType);
//    }
//    @TypeConverter
//    public static String fromNotification(District list) {
//        Gson gson = new Gson();
//        String json = gson.toJson(list);
//        return json;
//    }
}
