package com.example.testproject.model.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Logikoof Technologies on 14-07-2021.
 */
public class ChatborddataModel {
    public ArrayList<ChatbotquerydataModel> getQuery() {
        return query;
    }

    public void setQuery(ArrayList<ChatbotquerydataModel> query) {
        this.query = query;
    }

    @SerializedName("query")
    @Expose
    private ArrayList<ChatbotquerydataModel> query = null;

}
