package com.example.testproject.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.testproject.model.livestock.RefLiveStockModel;

import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "WeatherStateModel")
public class WeatherStateModel {

    @PrimaryKey(autoGenerate = true)
    private int dbid;

    private String id;
    private String name;
    private String uniqueId;
    private Boolean activeStatus;
    private String state;
    private String status;
  //  private Created created;
    private String createdDate;
    private String date;
    private WeatherDataModel weatherData;
    private RefLiveStockModel ref;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public int getDbid() {
        return dbid;
    }

    public void setDbid(int dbid) {
        this.dbid = dbid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public Created getCreated() {
//        return created;
//    }

 //   public void setCreated(Created created) {
//        this.created = created;
//    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public WeatherDataModel getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(WeatherDataModel weatherData) {
        this.weatherData = weatherData;
    }

    public RefLiveStockModel getRef() {
        return ref;
    }

    public void setRef(RefLiveStockModel ref) {
        this.ref = ref;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
