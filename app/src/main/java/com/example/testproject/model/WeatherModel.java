package com.example.testproject.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
@Entity(tableName = "weather")

public class WeatherModel {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    public String id;
    public int dbid;

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("uniqueId")
    @Expose
    public String uniqueId;
    @SerializedName("activeStatus")
    @Expose
    public Boolean activeStatus;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("createdDate")
    @Expose
    public String createdDate;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("weatherData")
    @Expose
    public WeatherModel weatherData;
    @SerializedName("ref")
    @Expose
    public RefModel ref;


    @SerializedName("dt")
    @Expose
    public Double dt;
    @SerializedName("sunrise")
    @Expose
    public Double sunrise;
    @SerializedName("sunset")
    @Expose
    public Double sunset;
    @SerializedName("moonrise")
    @Expose
    public Double moonrise;
    @SerializedName("moonset")
    @Expose
    public Double moonset;
    @SerializedName("moon_phase")
    @Expose
    public Double moonPhase;
    @SerializedName("temp")
    @Expose
    public TemperatureModel temp;
    @SerializedName("feels_like")
    @Expose
    public TemperatureModel feelsLike;
    @SerializedName("pressure")
    @Expose
    public Double pressure;
    @SerializedName("humidity")
    @Expose
    public Double humidity;
    @SerializedName("dew_point")
    @Expose
    public Double dewPoint;
    @SerializedName("wind_speed")
    @Expose
    public Double windSpeed;
    @SerializedName("wind_deg")
    @Expose
    public Double windDeg;
    @SerializedName("wind_gust")
    @Expose
    public Double windGust;
    @SerializedName("weather")
    @Expose
    public List<TemperatureModel> weather;
    @SerializedName("clouds")
    @Expose
    public Double clouds;
    @SerializedName("uvi")
    @Expose
    public Double uvi;
    public Double rain;

}
