package com.example.testproject.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.testproject.BuildConfig;
import com.example.testproject.database.Dao.DefailtConfigDao;
import com.example.testproject.database.Dao.UserACLDao;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.database.Dao.WeatherDetailsDao;
import com.example.testproject.database.convertor.FarmerListConverter;
import com.example.testproject.model.NotificationDataModel;
import com.example.testproject.model.ProductconfigModel;
import com.example.testproject.model.UserModel;
import com.example.testproject.model.Useracl;
import com.example.testproject.model.WeatherModel;

@Database(entities = {UserModel.class, WeatherModel.class, ProductconfigModel.class, Useracl.class}, version = 1)
@TypeConverters({FarmerListConverter.class})

public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;
    public abstract WeatherDetailsDao weatherDetailsResponseModel();
    public abstract UserDao userdao();
    public abstract DefailtConfigDao defailtConfigDao();

    public abstract UserACLDao getUseraclDao();


    public static AppDatabase getInstance(Context context){

        if(appDatabase == null) {
            if (BuildConfig.DEBUG) {
                // migrate database in case of confilict .
                appDatabase = Room.databaseBuilder(context, AppDatabase.class, "Nicessm-Database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            } else{
                appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "Nicessm-Database")
                        .allowMainThreadQueries()
                        .build();
            }


        }
        return appDatabase;
    }

    public static void destroyInstance() {
        appDatabase = null;

    }
}
