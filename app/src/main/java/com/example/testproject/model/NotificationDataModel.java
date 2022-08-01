package com.example.testproject.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "NotificationData")
public class NotificationDataModel {
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }





    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Ndata getData() {
        return data;
    }

    public void setData(Ndata data) {
        this.data = data;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @NonNull
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("body")
    @Expose
    private String body;

    @SerializedName("tittle")
    @Expose
    private String tittle;
    private String sentDate;
    private String message;

    private Ndata data;

    public class Ndata {
        public String getQuery_Type() {
            return Query_Type;
        }

        public void setQuery_Type(String query_Type) {
            Query_Type = query_Type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String Query_Type;
        private String id;
    }
}
