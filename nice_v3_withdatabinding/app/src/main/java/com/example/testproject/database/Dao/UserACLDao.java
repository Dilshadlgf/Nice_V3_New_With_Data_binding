package com.example.testproject.database.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.testproject.model.Useracl;

import java.util.List;

@Dao
public interface UserACLDao {

    @Query("Select * from Useracl")
    List<Useracl> getALLUseracl();

    @Insert
    void insert(Useracl useracl);

    @Update(onConflict = REPLACE)
    void update(Useracl useracl);

    @Query("SELECT * FROM Useracl WHERE uniqueId = :id")
    Useracl getUserAcl(String id);

    @Query("SELECT * FROM Useracl")
    Useracl getUseracl();

    @Query("DELETE FROM Useracl")
    public void deleteALl();
}
