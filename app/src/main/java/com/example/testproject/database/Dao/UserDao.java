package com.example.testproject.database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testproject.model.UserModel;

import java.util.List;

@Dao
public interface UserDao {
    @Query("Select * from user")
    List<UserModel> getAllusers();

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */
    @Insert
    void insert(UserModel users);

    @Update
    void update(UserModel users);

    @Query("Select * from user")
    UserModel getUserResponse();

    @Query("DELETE FROM user")

    void deleteUserModel();


}
