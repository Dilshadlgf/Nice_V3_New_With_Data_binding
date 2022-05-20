package com.example.testproject.model.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Monika Sharma on 20-11-2020.
 */
public class chatbotresponseModel implements Serializable {
    @SerializedName("data")
    @Expose
public ChatborddataModel data;

    public ChatborddataModel getData() {
        return data;
    }

    public void setData(ChatborddataModel data) {
        this.data = data;
    }

    @SerializedName("message")
        @Expose
        public String message;
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public int getStatusCode() {
            return statusCode;
        }
        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }
        @SerializedName("statusCode")
        @Expose

        public int statusCode;

}


