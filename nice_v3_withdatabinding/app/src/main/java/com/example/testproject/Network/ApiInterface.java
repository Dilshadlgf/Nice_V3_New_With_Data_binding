package com.example.testproject.Network;

import com.example.testproject.model.RootOneModel;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET("productconfig/getdefault")
    Call<RootOneModel> getDefault();

    @POST("farmer/auth/generateotp")
    Call<RootOneModel> loginbymobile(@Body JsonObject jsonObject );

   @POST("farmer/auth/otplogin/validateotp")
    Call<RootOneModel> mobileNoValidate(@Body JsonObject jsonObject);

    @POST("user/auth?platform=mobile")
    Call<RootOneModel> userLogin(@Body JsonObject request);

    @GET("common/uniquenesscheck?from=farmer&key=mobileNumber")
    Call<RootOneModel> mobnovalidRequest(@Query("value") String mobile);

    @POST("{path}")
    Call<JsonObject> commonOnePathApi(@Path("path")String path, @Body JsonObject request);

    @PUT("{path}")
    Call<JsonObject> commonOnePathApiPut(@Path("path")String path, @Body JsonObject request);

    @GET("{path}")
    Call<JsonObject> commonOnePathApiGet(@Path("path")String path, @QueryMap Map<String, String> options);

    @Multipart
    @POST("common/docupload/{path}")
    Call<JsonObject> singlefileRequest(@Part MultipartBody.Part file,@Path("path") String path);


    @POST("farmer/registration/generateotp")
    Call<RootOneModel> registrationWithOtp(@Path("path") String path,@Body JsonObject request);

    @POST("farmer/registration/validateotp")
    Call<RootOneModel> registrationValidateOtp(@Path("path") String path,@Body JsonObject request);

    @POST("content/filter")
    Call<RootOneModel> getSearchContentsListpostercontent(@Body JsonObject request,@Query("pageno") String pageno);

    @GET("{path}/{path2}/{path3}")
    Call<JsonObject> commonApiWithThreePathGet(@Path("path")String path1, @Path("path2")String path2,@Path("path3")String path3, @QueryMap Map<String, String> options);

    @PUT("{path}/{path2}/{path3}?pageno=no")
    Call<JsonObject> commonApiWithThreePathPut(@Path("path")String path1, @Path("path2")String path2,@Path("path3")String path3,@Body JsonObject jsonObject, @QueryMap Map<String, String> options);

    @DELETE("{path}/{path2}/{path3}")
    Call<JsonObject> commonApiWithThreePathDelete(@Path("path")String path1, @Path("path2")String path2,@Path("path3")String path3, @QueryMap Map<String, String> options);

    @POST("{path}/{path2}/{path3}?pageno=no")
    Call<JsonObject> commonApiWithThreePathPost(@Path("path")String path1, @Path("path2")String path2,@Path("path3")String path3,@Body JsonObject jsonObject);

    @PUT("{path}/{path2}")
    Call<JsonObject> commonApiWithTwoPathPut(@Path("path")String path1, @Path("path2")String path2, @Body JsonObject request, @Query("pageno") String pageno);

    @POST("dashboard/query/count")
    Call<JsonObject> getQueryCount(@Body JsonObject request);

    @POST("dashboard/content/{path}/count")
    Call<JsonObject> getContentCount(@Path("path")String path, @Body JsonObject request);

    @GET("common/uniqueness")
    Call<RootOneModel> checkMobUniqueness(@Query("from") String from ,@Query("key") String key,@Query("value") String value);

    @GET("{path}/{path2}")
    Call<JsonObject> commonApiWithTwoPathGet(@Path("path")String path1, @Path("path2")String path2, @QueryMap Map<String, String> options);

    @GET("user/checkuniqckness/username")
    Call<JsonObject> checkUserUniqueness(@Query("userName") String username );

    @POST("{path}/{path2}")
    Call<JsonObject> commonApiWithTwoPath(@Path("path")String path1, @Path("path2")String path2, @Body JsonObject request, @Query("pageno") String pageno);

    @POST("farmer")
    Call<RootOneModel> farmeregistration(@Body JsonObject request);

    @PUT("query/resolveuser")
    Call<JsonObject> querySolution(@Body JsonObject request);
    @POST("{path}/filter?pageno=no")
    Call<RootOneModel> geoFilter(@Path("path") String path,@Body JsonObject weatherJSONRequest);

    @POST("content/filter")
    Call<RootOneModel> searchContentList( @Body JsonObject request,@Query("pageno") String pageno);

    @POST("feedback")
    Call<RootOneModel> addFeedbackRequest(@Body JsonObject feedbackJSONRequest);

    @POST("farmerLiveStock/filter?pageno=no")
    Call<RootOneModel> LiveStockrequest( @Body JsonObject request);

    @POST("commodity/filter?pageno=no")
    Call<RootOneModel> getLiveStockList( @Body JsonObject request);

    @PUT("farmerLiveStock")
    Call<RootOneModel> updateLivestock(@Body JsonObject request);


 @POST("commodityvariety/filter?pageno=no")
 Call<RootOneModel> varietyList( @Body JsonObject request);


 @DELETE("farmerLiveStock/status/delete")
 Call<RootOneModel> deleteLiveStock(@Query("id") String token);



 @POST("commoditystage/filter?pageno=no")
 Call<RootOneModel> stageList( @Body JsonObject request);



 @GET("content")
    Call<RootOneModel>  searchContentsListDetailRequest( @Query("id") String id);


    @GET("farmer")
    Call<RootOneModel> getProfile( @Query("id") String id);

    @POST("apptoken/login")
    Call<RootOneModel> sendFbToken(@Body JsonObject request);

    @DELETE("apptoken/logout")
    Call<RootOneModel> deleteFbToken(@Query("id") String id);

    @POST("commoditycategory/filter?pageno=no")
   Call<RootOneModel> commodityCategoryFilter( @Body JsonObject request);

    @POST("farmerLiveStock")
    Call<RootOneModel> addLivestock(@Body JsonObject request);


    @POST("farmerCrop/filter")
    Call<RootOneModel> farmerCropDetaile(@Body JsonObject request);

    @POST("commodityvariety/filter")
    Call<RootOneModel> getVarietyList(@Body JsonObject id);

 @POST("cropseason/filter")
 Call<RootOneModel> getFamerSeasonList(@Body JsonObject id);

    @POST("farmerCrop")
    Call<RootOneModel> addFarmerCrop(@Body JsonObject request);

    @POST("commodity/filter")
    Call<RootOneModel> getFarmerCroplist(@Body JsonObject id);

    @PUT("farmer")
    Call<RootOneModel> editprofileUser(@Body JsonObject request);

    @POST("query/filter")
    Call<RootOneModel> queriesListRequest(@Body JsonObject request, @Query("pageno") String pageno);

    @POST("feedback/filter")
    Call<RootOneModel> feedbacklistRequest(@Body JsonObject request,@Query("pageno") String mobile);


    @POST("notificationLog/filter")
    Call<RootOneModel> getNotificationList( @Body JsonObject request,@Query("pageno") String pageno);

    @GET("query")
    Call<RootOneModel> getSingleQuery(@Query("id") String id);

    @POST("common/docsupload/{path}")
    Call<RootOneModel> uploadQueryImage(@Path("path") String path, @Part List<MultipartBody.Part> file);

    @Multipart
    @POST("common/docsupload/{path}")
    Call<JsonObject> multiimageRequest(@Part List<MultipartBody.Part> file,@Path("path") String path);
    @PUT("query")
    Call<RootOneModel> updateQueryRequest(@Body JsonObject weatherJSONRequest);

    @POST("query")
    Call<RootOneModel> sendAddQueryRequest(@Body JsonObject weatherJSONRequest);

    @POST("state/filter?pageno=no")
    Call<RootOneModel> stateWeather( @Body JsonObject request);

    @POST("stateWeatherData/filter")
    Call<RootOneModel> getWeatherData( @Body JsonObject request,@Query("pageno") String pageno);
    @GET("stateWeatherData/currentdate")
    Call<RootOneModel> getCurrentWeatherData( @Query("id") String token);
}
