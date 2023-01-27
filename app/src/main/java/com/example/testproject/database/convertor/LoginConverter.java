package com.example.testproject.database.convertor;

import androidx.room.TypeConverter;

import com.example.testproject.model.DistrictDataModel;
import com.example.testproject.model.StateDataModel;
import com.example.testproject.model.VillageDataModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika on 7/12/2017.
 */

public class LoginConverter {
    @TypeConverter
    public static List<String> fromString(String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromArrayList(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static StateDataModel fromStateDataModelString(String value) {
        Type listType = new TypeToken<StateDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromStateDataModel(StateDataModel stateDataModel) {
        Gson gson = new Gson();
        String json = gson.toJson(stateDataModel);
        return json;
    }

    @TypeConverter
    public static DistrictDataModel fromDistrictDataModelString(String value) {
        Type listType = new TypeToken<DistrictDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromDistrictDataModel(DistrictDataModel districtDataModel) {
        Gson gson = new Gson();
        String json = gson.toJson(districtDataModel);
        return json;
    }

//    @TypeConverter
//    public static BlockDataModel fromBlockDataModelString(String value) {
//        Type listType = new TypeToken<BlockDataModel>() {}.getType();
//        return new Gson().fromJson(value, listType);
//    }
//    @TypeConverter
//    public static String fromBlockDataModel(BlockDataModel blockDataModel) {
//        Gson gson = new Gson();
//        String json = gson.toJson(blockDataModel);
//        return json;
//    }

//    @TypeConverter
//    public static GramPanchayatDataModel fromGramPanchayatDataModelString(String value) {
//        Type listType = new TypeToken<GramPanchayatDataModel>() {}.getType();
//        return new Gson().fromJson(value, listType);
//    }
//    @TypeConverter
//    public static String fromGramPanchayatDataModel(GramPanchayatDataModel gramPanchayatDataModel) {
//        Gson gson = new Gson();
//        String json = gson.toJson(gramPanchayatDataModel);
//        return json;
//    }

    @TypeConverter
    public static VillageDataModel fromVillageDataModelString(String value) {
        Type listType = new TypeToken<VillageDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromVillageDataModel(VillageDataModel villageDataModel) {
        Gson gson = new Gson();
        String json = gson.toJson(villageDataModel);
        return json;
    }
//    @TypeConverter
//    public static UserorgModel fromUserorgModelString(String value) {
//        Type listType = new TypeToken<UserorgModel>() {}.getType();
//        return new Gson().fromJson(value, listType);
//    }
//    @TypeConverter
//    public static String fromUserorgModel(UserorgModel userorgModel) {
//        Gson gson = new Gson();
//        String json = gson.toJson(userorgModel);
//        return json;
//    }
    @TypeConverter
    public static ArrayList<String> fromFarmerListDataModelString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromFarmerListDataModel(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
