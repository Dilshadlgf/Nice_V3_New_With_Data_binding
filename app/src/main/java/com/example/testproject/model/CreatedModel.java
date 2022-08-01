package com.example.testproject.model;

import com.google.gson.annotations.SerializedName;

public class CreatedModel {

    private String on;
    private  String by;
    private String scenario;

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }
}
