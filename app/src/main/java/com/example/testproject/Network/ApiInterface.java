package com.example.testproject.Network;

import com.example.testproject.model.Root.RootModelOne;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.model.RootOneResModel;
import com.example.testproject.model.query.RootQueryModel;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
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

   @POST("commoditycategory/filter?pageno=no")
   Call<RootOneResModel> commodityCategoryFilter( @Body JsonObject request);

    @POST("farmerCrop/filter")
    Call<RootOneResModel> farmerCropDetaile(@Body JsonObject request);

    @POST("commodityvariety/filter")
    Call<RootOneResModel> getVarietyList(@Body JsonObject id);

 @POST("cropseason/filter")
 Call<RootOneResModel> getFamerSeasonList(@Body JsonObject id);

    @POST("farmerCrop")
    Call<RootModelOne> addFarmerCrop(@Body JsonObject request);

    @POST("commodity/filter")
    Call<RootOneResModel> getFarmerCroplist(@Body JsonObject id);

    @PUT("farmer")
    Call<RootModelOne> editprofileUser(@Body JsonObject request);

    @POST("query/filter")
    Call<RootQueryModel> queriesListRequest(@Body JsonObject request, @Query("pageno") String pageno);

    @POST("feedback/filter")
    Call<RootQueryModel> feedbacklistRequest(@Body JsonObject request,@Query("pageno") String mobile);


    @POST("notificationLog/filter")
    Call<RootOneResModel> getNotificationList( @Body JsonObject request,@Query("pageno") String pageno);

    @GET("query")
    Call<RootOneModel> getSingleQuery(@Query("id") String id);

    @POST("common/docsupload/{path}")
    Call<RootQueryModel> uploadQueryImage(@Path("path") String path, @Part List<MultipartBody.Part> file);

    @PUT("query")
    Call<RootOneModel> updateQueryRequest(@Body JsonObject weatherJSONRequest);

    @POST("query")
    Call<RootQueryModel> sendAddQueryRequest(@Body JsonObject weatherJSONRequest);


}
