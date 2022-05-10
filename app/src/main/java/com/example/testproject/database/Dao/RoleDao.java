package com.example.testproject.database.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.testproject.model.FarmerDataModel;
import com.example.testproject.model.RoleModel;

import java.util.List;

@Dao
public interface RoleDao {
    @Query("Select * from role")
    RoleModel getRole();

    @Query("DELETE FROM role")
    void deleteRoleModel();


    @Insert(onConflict = REPLACE)
    void insertRoleResponse(RoleModel listDataModel);
}
