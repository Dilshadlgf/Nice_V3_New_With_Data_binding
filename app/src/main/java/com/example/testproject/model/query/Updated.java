package com.example.testproject.model.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Updated {

    @SerializedName("on")
    @Expose
    private Object on;
    @SerializedName("by")
    @Expose
    private String by;
    @SerializedName("scenario")
    @Expose
    private String scenario;
    @SerializedName("remarks")
    @Expose
    private String remarks;

    public Object getOn() {
        return on;
    }

    public void setOn(Object on) {
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
