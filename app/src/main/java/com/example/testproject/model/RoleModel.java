package com.example.testproject.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "role")
public class RoleModel {

    @PrimaryKey(autoGenerate = true)
    // variable for our id.
    private int ids;

    private String role;
    private boolean isFarmer=true;

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isFarmer() {
        return isFarmer;
    }

    public void setFarmer(boolean farmer) {
        isFarmer = farmer;
    }
}
