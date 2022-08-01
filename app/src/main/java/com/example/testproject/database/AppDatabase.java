package com.example.testproject.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.testproject.BuildConfig;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.database.Dao.RoleDao;
import com.example.testproject.database.convertor.FarmerListConverter;
import com.example.testproject.database.convertor.RoleConvertor;
import com.example.testproject.database.convertor.StateModelConvertor;
import com.example.testproject.model.FarmerDataModel;
import com.example.testproject.model.RoleModel;

@Database(entities = {FarmerDataModel.class, RoleModel.class}, version = 1)
@TypeConverters({FarmerListConverter.class, RoleConvertor.class, StateModelConvertor.class})

public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;
    public abstract FarmerDao farmerDao();
    public abstract RoleDao roleDao();


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
