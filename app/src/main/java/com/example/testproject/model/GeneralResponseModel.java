package com.example.testproject.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralResponseModel {
    @SerializedName("id")
    @Expose
    private String id;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @SerializedName("code")
    @Expose
    private Integer code;


    @SerializedName("message")
    @Expose
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
