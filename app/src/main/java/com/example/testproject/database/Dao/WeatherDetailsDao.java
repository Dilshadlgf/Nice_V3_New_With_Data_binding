package com.example.testproject.database.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testproject.model.WeatherStateModel;


@Dao
public interface WeatherDetailsDao {
    @Query("Select * from WeatherStateModel")
    WeatherStateModel getWeatherDetailsResponseModel();


    @Delete
    void deleteWeatherDetailsResponseModel(WeatherStateModel weatherDetailsResponseModel);


    @Insert(onConflict = REPLACE)
    void insertWeatherDetailsResponseModel(WeatherStateModel weatherDetailsResponseModel);

    @Update
    void updateWeatherDetailsResponseModel(WeatherStateModel weatherDetailsResponseModel);
}
