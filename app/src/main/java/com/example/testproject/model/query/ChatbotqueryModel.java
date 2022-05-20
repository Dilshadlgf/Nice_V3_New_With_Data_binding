package com.example.testproject.model.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Logikoof Technologies on 13-07-2021.
 */
public class ChatbotqueryModel {
    public chatbotresponseModel getResponse() {
        return response;
    }
    public void setResponse(chatbotresponseModel response) {
        this.response = response;
    }
    @SerializedName("response")
    @Expose
    public chatbotresponseModel response;


}
