package com.example.testproject.database.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.testproject.model.FarmerDataModel;

import java.util.List;

@Dao
public interface FarmerDao {

    @Query("Select * from farmer")
    FarmerDataModel getFarmer();


    @Query("SELECT * FROM farmer WHERE id IN (SELECT MIN(id) FROM farmer GROUP BY mobileNumber )")
    List<FarmerDataModel> getAllDistinctuseres();


//   @Query("SELECT DISTINCT mobileNumber FROM farmer")
//   List<FarmerListDataModel> getAllDistinctusers();


    @Query("DELETE FROM farmer")
    void deleteFarmer();


    @Insert(onConflict = REPLACE)
    void insertFarmerResponse(FarmerDataModel listDataModel);

}
