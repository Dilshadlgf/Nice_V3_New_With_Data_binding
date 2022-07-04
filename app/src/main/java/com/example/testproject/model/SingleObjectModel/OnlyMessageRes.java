package com.example.testproject.model.SingleObjectModel;

import com.google.gson.annotations.SerializedName;

public class OnlyMessageRes {

    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response {

        @SerializedName("statusCode")
        public Integer statusCode;
        @SerializedName("message")
        public String message;
    }

}
