package com.example.testproject.model.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Logikoof Technologies on 14-07-2021.
 */
public class ChatbotquerydataModel {
    @SerializedName("query")
    @Expose
    private String query ;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    @SerializedName("solution")
    @Expose
    private String solution ;

}
