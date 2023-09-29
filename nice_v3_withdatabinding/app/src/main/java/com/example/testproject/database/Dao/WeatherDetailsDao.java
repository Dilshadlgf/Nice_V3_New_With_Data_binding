package com.example.testproject.database.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testproject.model.WeatherModel;

@Dao
public interface WeatherDetailsDao {
    @Query("Select * from weather")
    WeatherModel getWeatherDetailsResponseModel();


    @Delete
    void deleteWeatherDetailsResponseModel(WeatherModel weatherDetailsResponseModel);


    @Insert(onConflict = REPLACE)
    void insertWeatherDetailsResponseModel(WeatherModel weatherDetailsResponseModel);

    @Update
    void updateWeatherDetailsResponseModel(WeatherModel weatherDetailsResponseModel);
}
