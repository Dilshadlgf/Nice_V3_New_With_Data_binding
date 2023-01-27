package com.example.testproject.database.convertor;

import androidx.room.TypeConverter;

import com.example.testproject.model.KnowledgeDomain;
import com.example.testproject.model.ReviewedBy;
import com.example.testproject.model.SubDomain;
import com.example.testproject.model.SubTopic;
import com.example.testproject.model.Topic;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SearchDetailConverter {
//    @TypeConverter
//    public static Search_List_Detail_Model fromSearch_List_Detail_ModelString(String value) {
//        Type listType = new TypeToken<Search_List_Detail_Model>() {}.getType();
//        return new Gson().fromJson(value, listType);
//    }
//    @TypeConverter
//    public static String fromSearch_List_Detail_Model(Search_List_Detail_Model list) {
//        Gson gson = new Gson();
//        String json = gson.toJson(list);
//        return json;
//    }

    @TypeConverter
    public static KnowledgeDomain fromKnowledgeDomainString(String value) {
        Type listType = new TypeToken<KnowledgeDomain>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromKnowledgeDomain(KnowledgeDomain knowledgeDomain) {
        Gson gson = new Gson();
        String json = gson.toJson(knowledgeDomain);
        return json;
    }
    @TypeConverter
    public static SubDomain fromSubDomainString(String value) {
        Type listType = new TypeToken<SubDomain>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromSubDomain(SubDomain subDomain) {
        Gson gson = new Gson();
        String json = gson.toJson(subDomain);
        return json;
    }
    @TypeConverter
    public static Topic fromTopicString(String value) {
        Type listType = new TypeToken<Topic>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromTopic(Topic topic) {
        Gson gson = new Gson();
        String json = gson.toJson(topic);
        return json;
    }
    @TypeConverter
    public static SubTopic fromSubTopicString(String value) {
        Type listType = new TypeToken<SubTopic>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromSubTopic(SubTopic subTopic) {
        Gson gson = new Gson();
        String json = gson.toJson(subTopic);
        return json;
    }
//    @TypeConverter
//    public static TimeApplicable fromTimeApplicableString(String value) {
//        Type listType = new TypeToken<TimeApplicable>() {}.getType();
//        return new Gson().fromJson(value, listType);
//    }
//    @TypeConverter
//    public static String fromTimeApplicable(TimeApplicable timeApplicable) {
//        Gson gson = new Gson();
//        String json = gson.toJson(timeApplicable);
//        return json;
//    }
//    @TypeConverter
//    public static IndexingData fromIndexingDataString(String value) {
//        Type listType = new TypeToken<IndexingData>() {}.getType();
//        return new Gson().fromJson(value, listType);
//    }
//    @TypeConverter
//    public static String fromIndexingData(IndexingData indexingData) {
//        Gson gson = new Gson();
//        String json = gson.toJson(indexingData);
//        return json;
//    }
    @TypeConverter
    public static ReviewedBy fromReviewedByString(String value) {
        Type listType = new TypeToken<ReviewedBy>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromReviewedBy(ReviewedBy reviewedBy) {
        Gson gson = new Gson();
        String json = gson.toJson(reviewedBy);
        return json;
    }
//    @TypeConverter
//    public static Author fromAuthorString(String value) {
//        Type listType = new TypeToken<Author>() {}.getType();
//        return new Gson().fromJson(value, listType);
//    }
//    @TypeConverter
//    public static String fromAuthor(Author author) {
//        Gson gson = new Gson();
//        String json = gson.toJson(author);
//        return json;
//    }
//    @TypeConverter
//    public static Organisation OrganisationString(String value) {
//        Type listType = new TypeToken<Organisation>() {}.getType();
//        return new Gson().fromJson(value, listType);
//    }
//    @TypeConverter
//    public static String fromOrganisation(Organisation organisation) {
//        Gson gson = new Gson();
//        String json = gson.toJson(organisation);
//        return json;
//    }
//    @TypeConverter
//    public static Project ProjectString(String value) {
//        Type listType = new TypeToken<Project>() {}.getType();
//        return new Gson().fromJson(value, listType);
//    }
//    @TypeConverter
//    public static String fromProject(Project project) {
//        Gson gson = new Gson();
//        String json = gson.toJson(project);
//        return json;
//    }
    @TypeConverter
    public static ArrayList<Object> fromObjectString(String value) {
        Type listType = new TypeToken<ArrayList<Object>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObject(ArrayList<Object> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

}
