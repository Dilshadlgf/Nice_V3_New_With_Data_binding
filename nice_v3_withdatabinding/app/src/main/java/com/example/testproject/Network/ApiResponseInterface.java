package com.example.testproject.Network;

public interface ApiResponseInterface {
     void isError(String errorCode);
     void isSuccess(Object response, int ServiceCode);
}
