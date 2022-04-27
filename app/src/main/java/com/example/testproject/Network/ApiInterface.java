package com.example.testproject.Network;

import com.example.testproject.model.RootOneModel;
import com.example.testproject.model.RootOneResModel;
import com.example.testproject.model.query.RootQueryModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("productconfig/getdefault")
    Call<RootOneModel> getDefault();

    @POST("farmer/auth/generateotp")
    Call<RootOneModel> loginbymobile(@Body JsonObject jsonObject );

   @POST("farmer/auth/otplogin/validateotp")
    Call<RootOneModel> mobileNoValidate(@Body JsonObject jsonObject);

    @POST("content/filter")
    Call<RootOneModel> searchContentList( @Body JsonObject request,@Query("pageno") String pageno);

    @GET("content")
    Call<RootOneResModel>  searchContentsListDetailRequest( @Query("id") String id);


    @GET("farmer")
    Call<RootOneModel> getProfile( @Query("id") String id);

    @POST("query/filter")
    Call<RootQueryModel> queriesListRequest(@Body JsonObject jsonObject, @Query("pageno") String pageno);

}
