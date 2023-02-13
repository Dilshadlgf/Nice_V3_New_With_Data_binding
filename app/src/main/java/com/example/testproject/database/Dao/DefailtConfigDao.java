package com.example.testproject.database.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testproject.model.ProductconfigModel;

@Dao
public interface DefailtConfigDao {

    @Query("Select * from `default`")
    ProductconfigModel getproductconfig();


    @Delete
    void deleteproductconfig(ProductconfigModel productconfigModel);


    @Insert(onConflict = REPLACE)
    void insertproductconfig(ProductconfigModel productconfigModel);

    @Update
    void updateproductconfig(ProductconfigModel productconfigModel);
}
