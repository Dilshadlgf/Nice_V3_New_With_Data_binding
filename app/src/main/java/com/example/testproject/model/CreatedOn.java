package com.example.testproject.model;

import com.google.gson.annotations.SerializedName;

public class CreatedOn {

    @SerializedName("on")
    private String on;

    @SerializedName("scenario")
    private String scenario;

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }
}
