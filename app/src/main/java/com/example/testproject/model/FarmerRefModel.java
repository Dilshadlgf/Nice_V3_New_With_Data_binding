package com.example.testproject.model;

import com.google.gson.annotations.SerializedName;

public class FarmerRefModel {

    @SerializedName("state")
    private StateDataModel state;

    public StateDataModel getState() {
        return state;
    }

    public void setState(StateDataModel state) {
        this.state = state;
    }
}
