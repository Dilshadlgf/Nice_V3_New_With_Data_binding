package com.example.testproject.Util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class JsonMyUtils {

    public static JsonArray getOnePremInJsonArr(String s){
        JsonArray jsonArray=new JsonArray();
         jsonArray.add(s);
        return jsonArray;
    }
    public static JsonObject getDataFromJsonObject (JsonObject jsonObject){
        if(jsonObject.has("response") && jsonObject.getAsJsonObject("response")!=null){
            return jsonObject.getAsJsonObject("response").getAsJsonObject("data");
        }
        return null;
    }
    public static <T> List<T> getPojoFromJsonArr (JsonArray jsonArray){
        return new Gson().fromJson(jsonArray.toString(), new TypeToken<List<T>>(){}.getType());}

    public static <T> List<T> getPojoFromJsonArr(JsonArray jsonArray,Class<T> neededClass) {
        Type typeOfT = TypeToken.getParameterized(List.class, neededClass).getType();
        return new Gson().fromJson(jsonArray.toString(), typeOfT);}

    public static <T> Object getPojoFromJsonObj (T className,JsonObject jsonObject){
        return new Gson().fromJson(jsonObject.toString(), (Type) className);}

    public static String getUrlFromSingleFileUpload (JsonObject jsonObject){
        if(jsonObject.has("response") && jsonObject.getAsJsonObject("response")!=null){
            if(jsonObject.getAsJsonObject("response").getAsJsonObject("data")!=null && jsonObject.getAsJsonObject("response").getAsJsonObject("data").get("uri").getAsString()!=null){
                return jsonObject.getAsJsonObject("response").getAsJsonObject("data").get("uri").getAsString();
            }
            return "";
        }
        return "";
    }
    public static boolean isResponseSuccess (JsonObject jsonObject){
        if(jsonObject.has("response") && jsonObject.getAsJsonObject("response")!=null){
            return jsonObject.getAsJsonObject("response").getAsJsonObject("data") != null && jsonObject.getAsJsonObject("response").get("statusCode").getAsInt() == 200;
        }
        return false;
    }

}
