package com.example.testproject.database.convertor;

import androidx.room.TypeConverter;

import com.example.testproject.model.CreatedOn;
import com.example.testproject.model.QueryRef;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by AB on 7/12/2017.
 */

public class QueriesConverter {
//    @TypeConverter
//    public static QueriesResponseDataModel fromString(String value) {
//        Type listType = new TypeToken<QueriesResponseDataModel>() {}.getType();
//        return new Gson().fromJson(value, listType);
//    }
//    @TypeConverter
//    public static String fromArrayList(QueriesResponseDataModel list) {
//        Gson gson = new Gson();
//        String json = gson.toJson(list);
//        return json;
//    }
    @TypeConverter
    public static CreatedOn fromCreatedOnString(String value) {
        Type listType = new TypeToken<CreatedOn>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromCreatedOn(CreatedOn author) {
        Gson gson = new Gson();
        String json = gson.toJson(author);
        return json;
    }

    @TypeConverter
    public static QueryRef fromQueryRefString(String value) {
        Type listType = new TypeToken<QueryRef>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromQueryRef(QueryRef author) {
        Gson gson = new Gson();
        String json = gson.toJson(author);
        return json;
    }


}
