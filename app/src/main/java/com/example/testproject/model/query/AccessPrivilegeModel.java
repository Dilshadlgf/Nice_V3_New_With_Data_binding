package com.example.testproject.model.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccessPrivilegeModel {

    @SerializedName("accessLevel")
    @Expose
    private String accessLevel;
    @SerializedName("districts")
    @Expose
    private Object districts;
    @SerializedName("states")
    @Expose
    private List<String> states = null;
    @SerializedName("villages")
    @Expose
    private Object villages;
    @SerializedName("blocks")
    @Expose
    private Object blocks;
    @SerializedName("grampanchayats")
    @Expose
    private Object grampanchayats;

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Object getDistricts() {
        return districts;
    }

    public void setDistricts(Object districts) {
        this.districts = districts;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public Object getVillages() {
        return villages;
    }

    public void setVillages(Object villages) {
        this.villages = villages;
    }

    public Object getBlocks() {
        return blocks;
    }

    public void setBlocks(Object blocks) {
        this.blocks = blocks;
    }

    public Object getGrampanchayats() {
        return grampanchayats;
    }

    public void setGrampanchayats(Object grampanchayats) {
        this.grampanchayats = grampanchayats;
    }
}
