package com.example.testproject.Network;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.testproject.R;
import com.example.testproject.util.RealPathUtil;
import com.example.testproject.util.SharedPreferenceHelper;

import com.example.testproject.model.RootOneModel;
import com.example.testproject.util.AppConstants;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.io.TextStreamsKt;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiManager {


    private Context mContext;
    private ProgressDialog dialog;
    private ApiResponseInterface mApiResponseInterface;
    private String jsessionid,jsessionidd = "";


    public ApiManager(Context context, ApiResponseInterface apiResponseInterface) {
        this.mContext = context;
        this.mApiResponseInterface = apiResponseInterface;
       dialog = new ProgressDialog(mContext);
    }

    public void getDefault() {
//        showDialog("Loding");

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.getDefault();
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.LOGIN_REQUEST);
                } else {

                    if (response.body() != null) {
                        mApiResponseInterface.isError(response.body().getResponse().getMessage());

                    } else {
                        mApiResponseInterface.isError("Login API TimeOut Please contact to Administrator");

                    }
                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }

    //==========================



    public void loginbymobile(JsonObject object) {
        showDialog("Sending Otp");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.loginbymobile(object);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.LOGIN_REQUEST);
                } else {

                    if (response.body() != null) {
                        mApiResponseInterface.isError(response.body().getResponse().getMessage());

                    } else {
                        mApiResponseInterface.isError("Login API TimeOut Please contact to Administrator");

                    }
                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }



//=======================================================================

    public void mobileNoValidate(JsonObject object) {
        showDialog("Sing In");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.mobileNoValidate(object);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.Validate);
                } else {

                    if (response.body() != null) {
                        mApiResponseInterface.isError(response.body().getResponse().getMessage());

                    } else {
                        mApiResponseInterface.isError("Login API TimeOut Please contact to Administrator");

                    }
                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void userLogin(JsonObject jsonObject)
    {
        showDialog("Loading");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.userLogin(jsonObject);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {

                closeDialog();
                if (response.body() != null && response.body().getResponse().getData()!=null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.LOGIN_REQUEST);

                } else {
                    if(response.errorBody()!=null){
                        String msg="";
                        try {
                            msg=response.errorBody().string();
                            if(msg.contains("not") && msg.contains("found") && msg.contains("Invalid")){
                                msg="user not found or invalid login details" ;
                            }else {
                                msg="try again";
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mApiResponseInterface.isError("Error:"+msg);
                    }else{
                        mApiResponseInterface.isError("Error: try again");
                    }

                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Please try later");
//                    mApiResponseInterface.isError("002: Server not responding ");
                }
            }
        });
    }
    public void submitQuerySolution(JsonObject jsonObject,int reqCode) {
        showDialog("Loading...");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.querySolution( jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                closeDialog();


                if (response.body() != null) {
                    closeDialog();

                    mApiResponseInterface.isSuccess(response.body(), reqCode);

                } else {
                    mApiResponseInterface.isError("Error from Server");

                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError(mContext.getString(R.string.server_not_responding01));
                } else {
                    mApiResponseInterface.isError(mContext.getString(R.string.server_not_responding02));
                }
            }
        });
    }
    public void uploadFilesRequest(Context mContext, List<Uri> arrayList,String path, int resCode) {
        showDialog("Uploading Images");

        new Thread(new Runnable() {
            @Override
            public void run() {
                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.setType(MultipartBody.FORM);
                boolean hasAnyFile=false;
                // Multiple Images

                for (int i = 0; i < arrayList.size(); i++) {

                    if(arrayList.get(i).getPath().equals("") ){
                        continue;
                    }
                    hasAnyFile=true;
                    String realPath= RealPathUtil.getRealPath(mContext,arrayList.get(i));
                    if(realPath==null){
                        realPath= RealPathUtil.copyFileToInternalStorage(mContext,arrayList.get(i),"mytempfile");

                    }
                    if(realPath==null){
                        continue;
                    }
                    File biodataFile = new File(realPath);
                    byte[] fillArr=null;
                    if(RealPathUtil.isImageFile(biodataFile.getPath()) || RealPathUtil.getExtension(realPath).equalsIgnoreCase("jpg") || RealPathUtil.getExtension(realPath).equalsIgnoreCase("png")) {
                        Bitmap bitmap = null;
                        try {
//                            bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), arrayList.get(i));
                            bitmap=RealPathUtil.compressImage(realPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        if (bitmap == null) {
                            bitmap = BitmapFactory.decodeFile(biodataFile.getAbsolutePath());
                        }
                        if(bitmap==null){
                            closeDialog();
                            mApiResponseInterface.isError("Something went wrong. Check attachment");
                            return;
                        }
                        bitmap.compress(Bitmap.CompressFormat.WEBP, 50, bos);
                        fillArr=bos.toByteArray();
                    }

                    try {
                        fillArr= RealPathUtil.fullyReadFileToBytes(biodataFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    RequestBody compressbody = RequestBody.create(MediaType.parse("multipart/form-data"), fillArr);
                    builder.addFormDataPart("uploadfiles", biodataFile.getName(), compressbody);
                }


                if(!hasAnyFile){
                    closeDialog();
                    mApiResponseInterface.isError("Something went wrong. Check attachment");
                    return;
                }

                List<MultipartBody.Part> requestBody = builder.build().parts();


                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<JsonObject> call = apiService.multiimageRequest(requestBody,path);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        closeDialog();
                        if (response.body() != null) {
                            mApiResponseInterface.isSuccess(response.body(), resCode);
                        } else {
                            mApiResponseInterface.isError("Failed");

                        }


                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        closeDialog();
                        if(t instanceof IOException)
                        {
                            mApiResponseInterface.isError(mContext.getString(R.string.server_not_responding01));
                        }
                        else
                        {
                            mApiResponseInterface.isError("Please Contact to Administrator");
                        }

                    }
                });
            }
        }).start();
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void getweatherStateData(String token) {
//        showDialog("Weather Loading");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.getCurrentWeatherData(token);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();

                if (response.body() != null) {

                    mApiResponseInterface.isSuccess(response.body(), AppConstants.WeatherAlert_LIST_REQUEST);

                } else {
                    mApiResponseInterface.isError("Weather data not available");
                }

            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("002: Server not responding ");
                }
            }
        });
    }

    ///////=======================================================

    //++++++++++++++++++++++++++++++++++++++++++
    public void sendOrDeleteFbToken(JsonObject jsonObject,String id,boolean isSendToken) {
//        showDialog("Loading");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call;
        if(isSendToken){
            call = apiService.sendFbToken(jsonObject);
        }else {
            call = apiService.deleteFbToken(id);
        }

        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.SendFbToken);

                } else {
                    mApiResponseInterface.isSuccess(null, AppConstants.SendFbToken);

                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                mApiResponseInterface.isSuccess(null, AppConstants.SendFbToken);
//                if (t instanceof IOException) {
//                    mApiResponseInterface.isError("Internet is not Connected");
//                } else {
//                    mApiResponseInterface.isError("Please contact to Administrator");
//                }
            }
        });
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++
    public void searchContentList(String token, JsonObject jsonObject,int callType) {
        showDialog("Loding");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.searchContentList(jsonObject,token);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();

                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), callType);

                } else {
                    if (SharedPreferenceHelper.getSharedPreferenceBoolean(mContext, "Farmer", false) == true) {
                        mApiResponseInterface.isError("Contents are not available");
                    } else {
                        mApiResponseInterface.isError("Content API TimeOut Please contact to Administrator");
                    }
                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }

    //===============================++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void addFeedbackRequest( JsonObject jsonObject) {
        showDialog("Adding Feedback");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.addFeedbackRequest( jsonObject);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.ADD_FEEDBACK_REQUEST);

                } else {
                    mApiResponseInterface.isError("Add Feedback , TimeOut try later");
//                    mApiResponseInterface.isError("Feedback saved successfully");

                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("002: Server not responding ");
                }
            }
        });
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void LiveStockRequest(JsonObject jsonObject) {
        showDialog("Livestock");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.LiveStockrequest(jsonObject);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.LiveSTOCKREQUEST);

                } else {
                    mApiResponseInterface.isError("Livestock API TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }
    //+++++++++++++++++++++++++++++++++++++============================
    public void getLiveStockList(JsonObject jsonObject) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.getLiveStockList(jsonObject);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.LiveSTOCKREQUEST);

                } else {
                    mApiResponseInterface.isError("Livestock API TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }

    //+++++++++++++++++++++++++++++++++++====================================
    public void UpdateLivestock(String token, JsonObject updateApproveRequest) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.updateLivestock( updateApproveRequest);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.UpdatreLivesstock);

                } else {
                    mApiResponseInterface.isError("Update Livestock API TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }

    //+++++++++++++++++++______________________++++++++++++++++++++++++
    public void varietyList(JsonObject jsonObject) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.varietyList(jsonObject);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.VarietyListReq);

                } else {
                    mApiResponseInterface.isError("Livestock API TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }

//+++++++++++++++++++++++++++++++++++=============================
public void commonOnePathApi(String path, JsonObject jsonObject, HashMap queryMap, int reqCode, int method) {
    showDialog("Loading...");
    ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);
    Call<JsonObject> call=null;
    if(method==AppConstants.METHOD_POST){
        call = apiService.commonOnePathApi(path, jsonObject);
    }else if(method==AppConstants.METHOD_PUT){
        call = apiService.commonOnePathApiPut(path, jsonObject);
    }else if(method==AppConstants.METHOD_GET){
        call = apiService.commonOnePathApiGet(path, queryMap);
    }

    call.enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
            closeDialog();


            if (response.body() != null) {
                closeDialog();

                mApiResponseInterface.isSuccess(response.body(), reqCode);

            } else {
                mApiResponseInterface.isError("Error from Server");

            }
        }


        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
            closeDialog();
            if (t instanceof IOException) {
                mApiResponseInterface.isError("Internet is not Connected");
            } else {
                mApiResponseInterface.isError("002: Server not responding ");
            }
        }
    });
}

    //++++++++++++++++++++++++++++++++++++++++++++
public void stageList(JsonObject jsonObject) {
    showDialog("");
    ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);
    Call<RootOneModel> call = apiService.stageList(jsonObject);
    call.enqueue(new Callback<RootOneModel>() {
        @Override
        public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
            closeDialog();


            if (response.body() != null) {
                mApiResponseInterface.isSuccess(response.body(), AppConstants.StageListReq);

            } else {
                mApiResponseInterface.isError("Livestock API TimeOut Please contact to Administrator");

            }
        }


        @Override
        public void onFailure(Call<RootOneModel> call, Throwable t) {
            closeDialog();
            if (t instanceof IOException) {
                mApiResponseInterface.isError("Internet is not Connected");
            } else {
                mApiResponseInterface.isError("Response Model Conversion Issue");
            }
        }
    });
}

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void deleteliveStock(String token) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.deleteLiveStock(token);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.DeleteLiveStock);
                } else {

                    mApiResponseInterface.isError("Failed");
                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Please Contact to Administrator");
                }
            }
        });
    }

    //++++++++++++++++++++++++++++++++++========================================
    public void getProfile(String token) {
        showDialog("Loding");

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.getProfile(token);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.PROFILE_REQUEST);
                } else {

                    if (response.body() != null) {
                        mApiResponseInterface.isError(response.body().getResponse().getMessage());

                    } else {
                        mApiResponseInterface.isError("Login API TimeOut Please contact to Administrator");

                    }
                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }





    public void editprofileUser(JsonObject token) {
        showDialog("Loding");

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.editprofileUser(token);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.EDIT_PROFILE_USER);
                } else {

                    if (response.body() != null) {
                        mApiResponseInterface.isError(response.body().getResponse().getMessage());

                    } else {
                        mApiResponseInterface.isError("Login API TimeOut Please contact to Administrator");

                    }
                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }
    //==================================+++++++++++++++++++++================+++++++++++++++++++++

    public void searchContentsListDetailRequest(String id) {
        showDialog("Loding");

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.searchContentsListDetailRequest(id);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.SEARCH_CONTENT_LIST_Detail_REQUEST);
                } else {

                    if (response.body() != null) {
                        mApiResponseInterface.isError(response.body().getResponse().getMessage());

                    } else {
                        mApiResponseInterface.isError("Login API TimeOut Please contact to Administrator");

                    }
                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }

//============================================+++++++++++++++++++++++++++++++++______________________+++++++++++++
public void queriesListRequest(JsonObject jsonObject,String pageno) {
    showDialog("Loding");
    ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);
    Call<RootOneModel> call = apiService.queriesListRequest(jsonObject,pageno);
    call.enqueue(new Callback<RootOneModel>() {
        @Override
        public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
            closeDialog();

            if (response.body() != null) {
                mApiResponseInterface.isSuccess(response.body(), AppConstants.QUERIES_LIST_REQUEST_FEEDBACK);


            } else {
                if (SharedPreferenceHelper.getSharedPreferenceBoolean(mContext, "Farmer", false) == true) {
                    mApiResponseInterface.isError("Contents are not available");
                } else {
                    mApiResponseInterface.isError("Content API TimeOut Please contact to Administrator");
                }
            }

        }

        @Override
        public void onFailure(Call<RootOneModel> call, Throwable t) {
            closeDialog();
            if (t instanceof IOException) {
                mApiResponseInterface.isError("Internet is not Connected");
            } else {
                mApiResponseInterface.isError("Response Model Conversion Issue");
            }
        }
    });
}


    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++==============================

    public void feedbacklistRequest(JsonObject object,String pageno) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.feedbacklistRequest(object,pageno);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null) {

                    mApiResponseInterface.isSuccess(response.body(), AppConstants.FeedbacklistRequest);

                } else {
                    mApiResponseInterface.isError("Feedback API TimeOut Please contact to Administrator");
                }

            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }

    //============================================================================
    public void getNotificationList(JsonObject jsonObject,String pageno) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.getNotificationList(jsonObject,pageno);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.NotificationListReq);

                } else {
                    mApiResponseInterface.isError("Livestock API TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }
    //==================================+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void getSingleQuery(String id) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<RootOneModel> call = apiService.getSingleQuery(id);


        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.QUERIES_LIST_REQUEST);

                } else {
                    if (SharedPreferenceHelper.getSharedPreferenceBoolean(mContext, "Farmer", false) == true) {
                        mApiResponseInterface.isError("Queries are not available");
                    } else {
                        mApiResponseInterface.isError("Queries  API TimeOut Please contact to Administrator");
                    }


                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }
    //=======================================================================+++++++++++++++++++++

    public void uploadQueryImageRequest(Context mContext, ArrayList<Uri> arrayList, int resCode) {
        showDialog("Uploading Images");

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.setType(MultipartBody.FORM);
                boolean hasAnyFile=false;
                // Multiple Images
                for (int i = 0; i < arrayList.size(); i++) {
                    if(arrayList.get(i).getPath().equals("") || arrayList.get(i).getPath().contains("/documents") ){
                        continue;
                    }
                    hasAnyFile=true;
                    File file = new File(RealPathUtil.getRealPath(mContext,arrayList.get(i)));
                    //Compress Image
//            String[] filePath = {MediaStore.Images.Media.DATA};
//            Cursor c = mContext.getContentResolver().query(arrayList.get(i), filePath, null, null, null);
//            c.moveToFirst();
//            int columnIndex = c.getColumnIndex(filePath[0]);
//            String picturePath = c.getString(columnIndex);
//            c.close();
                    File biodataFile;

                    biodataFile = new File(RealPathUtil.getRealPath(mContext,arrayList.get(i)));

                    Bitmap bitmap=null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(),arrayList.get(i));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    if(bitmap==null) {
                        bitmap = BitmapFactory.decodeFile(biodataFile.getAbsolutePath());
                    }
                    bitmap.compress(Bitmap.CompressFormat.WEBP, 50, bos);

                    RequestBody compressbody = RequestBody.create(MediaType.parse("multipart/form-data"), bos.toByteArray());
                    builder.addFormDataPart("uploadfiles", file.getName(), compressbody);
                }
                if(!hasAnyFile){
                    closeDialog();
                    mApiResponseInterface.isSuccess(null, resCode);
                    return;
                }

                List<MultipartBody.Part> requestBody = builder.build().parts();


                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<RootOneModel> call = apiService.uploadQueryImage("query",requestBody);
                call.enqueue(new Callback<RootOneModel>() {
                    @Override
                    public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                        closeDialog();
                        if (response.body()!=null ) {
                            mApiResponseInterface.isSuccess(response.body(), resCode);
                        }

                        else
                        {
                            mApiResponseInterface.isError("Failed");

                        }


                    }
                    @Override
                    public void onFailure(Call<RootOneModel> call, Throwable t) {
                        closeDialog();
                        if(t instanceof IOException)
                        {
                            mApiResponseInterface.isError("Internet is not Connected");
                        }
                        else
                        {
                            mApiResponseInterface.isError("Please Contact to Administrator");
                        }
                    }
                });
            }
        },500);
    }

    //+++++++++++++++++++++++++++++=====================================================

    public void farmerListRequest(String id, String token) {
        showDialog("Farmer List");
        ApiInterfacee apiService =
                ApiClient.getClient().create(ApiInterfacee.class);

        Call<RootOneModel> call = apiService.farmerListt("Mobile", jsessionid, id, token);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.FARMER_LIST_REQUEST);

                } else {
                    mApiResponseInterface.isError("FarmerList API TimeOut Please contact to Administrator");

                }
            }


            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");

                }
            }
        });
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++===============================

    private interface ApiInterfacee {

        //    @GET()
        @GET("farmerListByUser/{id}")
        Call<RootOneModel> farmerListt(@Header("X-Requested-With") String header, @Header("Cookie") String Cookie, @Path("id") String id, @Query("token") String token);

        @GET("farmers?activeStatus=true&block=&district=&gramPanchayat=&max=100")
        Call<RootOneModel> farmerListtsec(@Header("X-Requested-With") String header, @Header("Cookie") String Cookie, @Query("offset") int offset, @Query("state") String state, @Query("village") String village, @Query("token") String token);


        //        https://nicessm.org/CCKNIA/farmers?activeStatus=true&block=&district=&gramPanchayat=&max=10&offset=0&searchText=Moni&state=&village=
        @GET("farmers?activeStatus=true&block=&district=&gramPanchayat=&max=10&offset=0")
        Call<RootOneModel> farmerListtseach(@Header("X-Requested-With") String header,
                                                      @Header("Cookie") String Cookie,@Header("token") String token,  @Query("searchText") String searchtext,
                                                      @Query("state") String state,
                                                      @Query("village") String village);
//       Call<RootOneModel> farmerListt(@Url String url) ;
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++=====================================
    public void uploadSingleFile(Context mContext, Uri uri,String path, int resCode) {
        showDialog("Uploading files");

        new Thread(new Runnable() {
            @Override
            public void run() {
                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.setType(MultipartBody.FORM);
                boolean hasAnyFile=false;
                // Multiple Images
                hasAnyFile=true;
                String realPath= RealPathUtil.getRealPath(mContext,uri);
                if(realPath==null){
                    realPath= RealPathUtil.copyFileToInternalStorage(mContext,uri,"mytempfile");

                }

                File biodataFile = new File(realPath);
                byte[] fillArr=null;
                if(RealPathUtil.isImageFile(biodataFile.getPath()) || RealPathUtil.getExtension(realPath).equalsIgnoreCase("jpg") || RealPathUtil.getExtension(realPath).equalsIgnoreCase("png")) {
                    Bitmap bitmap = null;
                    try {
//                            bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), arrayList.get(i));
                        bitmap=RealPathUtil.compressImage(realPath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    if (bitmap == null) {
                        bitmap = BitmapFactory.decodeFile(biodataFile.getAbsolutePath());
                    }
                    if(bitmap==null){
                        closeDialog();
                        mApiResponseInterface.isError("Something went wrong. Check attachment");
                        return;
                    }
                    bitmap.compress(Bitmap.CompressFormat.WEBP, 50, bos);
                    fillArr=bos.toByteArray();
                }

                try {
                    fillArr= RealPathUtil.fullyReadFileToBytes(biodataFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                RequestBody compressbody = RequestBody.create(MediaType.parse("multipart/form-data"), fillArr);
                builder.addFormDataPart("uploadfile", biodataFile.getName(), compressbody);



                if(!hasAnyFile){
                    closeDialog();
                    mApiResponseInterface.isError("Something went wrong. Check attachment");
                    return;
                }

                MultipartBody.Part requestBody = builder.build().part(0);


                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<JsonObject> call = apiService.singlefileRequest(requestBody,path);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        closeDialog();
                        if (response.body() != null) {
                            mApiResponseInterface.isSuccess(response.body(), resCode);
                        } else {
                            mApiResponseInterface.isError("Failed");

                        }


                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        closeDialog();
                        if(t instanceof IOException)
                        {
                            mApiResponseInterface.isError("Internet is not Connected");
                        }
                        else
                        {
                            mApiResponseInterface.isError("Please Contact to Administrator");
                        }

                    }
                });
            }
        }).start();
    }

    //+++++++++++++++++++++++++++++++++++++++++=========
    public void stateWeather(JsonObject jsonObject) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.stateWeather(jsonObject);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.StateWeather);

                } else {
                    mApiResponseInterface.isError("WetherData API TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }

    //+++++++++++++==================================================
    public void getWeatherData(JsonObject jsonObject,String pageno) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.getWeatherData(jsonObject,pageno);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.WeatherData);

                } else {
                    mApiResponseInterface.isError("WetherData API TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }

    //++++++++++++++++++++++++++++++++++++++++++++=============
    public void updateQuery(String token, JsonObject jsonObject) {
        showDialog("Adding Query");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<RootOneModel> call = apiService.updateQueryRequest( jsonObject);

        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.UpdateQuery);

                } else {
                    mApiResponseInterface.isError("Add Query: TimeOut Please try later");

                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++==================================

    public void addQueryRequest(String token, JsonObject jsonObject) {
        showDialog("Adding Query");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.sendAddQueryRequest(jsonObject);

        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.ADD_QUERY_REQUEST);

                } else {
                    mApiResponseInterface.isError("Add Query: TimeOut Please try later");

                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }


    public void commodityCategoryFilter(JsonObject jsonObject) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.commodityCategoryFilter(jsonObject);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.CommodityFilterReq);

                } else {
                    mApiResponseInterface.isError("Livestock API TimeOut Please contact to Administrator");

                }
            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }

    public void addLivestock( JsonObject updateApproveRequest) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.addLivestock( updateApproveRequest);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.UpdatreLivesstock);

                } else {
                    mApiResponseInterface.isError("TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }


    public void getFarmerInterCropList(JsonObject id ,int req) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.farmerCropDetaile(id);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(),req);
                } else {

                    mApiResponseInterface.isError("Failed");
                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Please Contact to Administrator");
                }
            }
        });
    }

    public void getFamerSeasonList(JsonObject id,int req) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.getFamerSeasonList(id);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(),req);
                } else {

                    mApiResponseInterface.isError("Failed");
                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Please Contact to Administrator");
                }
            }
        });
    }

    public void addFarmerCrop(JsonObject updateApproveRequest,int req) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.addFarmerCrop( updateApproveRequest);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), req);

                } else {
                    mApiResponseInterface.isError("Issue in Update , Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }
    public void getVarietyList(JsonObject id,int req) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.getVarietyList(id);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(),req);
                } else {

                    mApiResponseInterface.isError("Failed");
                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Please Contact to Administrator");
                }
            }
        });
    }

    public void getFarmerCropList(JsonObject id,int req) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.getFarmerCroplist(id);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), req);
                } else {

                    mApiResponseInterface.isError("Failed");
                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Please Contact to Administrator");
                }
            }
        });
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++===============
    public void MobnovalidRequest(String mob) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.mobnovalidRequest( mob);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();

                /*&& response.body().getCode()==200*/
                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.MobvalidationRequst);

                } else {
                    mApiResponseInterface.isError("Mobile no validationTimeOut. Please try after sometime");

                }
            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model issue");
//                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }

    //+++++++++++++++++++++===================================
    public void RegistrationRequest(JsonObject jsonObject) {
        showDialog("Registration");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.farmeregistration(jsonObject);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.REGISTRATION_REQUEST);
                } else {

                    if (response.body() != null) {
                        mApiResponseInterface.isError(response.body().getResponse().getMessage().toString());

                    } else {
                        mApiResponseInterface.isError("Registration API TimeOut Please contact to Administrator");

                    }
                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Mobile number is already exists");
                }
            }
        });
    }

    //++++++++++++++++++++++++++++++================
    public void registrationValidateOtp(String path,JsonObject jsonObject) {
        showDialog("Registration");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.registrationValidateOtp(path,jsonObject);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.REGISTRATION_REQUEST);
                } else {

                    if (response.body() != null) {
                        mApiResponseInterface.isError(response.body().getResponse().getMessage().toString());

                    } else {
                        mApiResponseInterface.isError("Wrong data Or Invalid Otp");

                    }
                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Mobile number is already exists");
                }
            }
        });
    }

    //_____________________________+++++++++++++++++++++
    public void checkMobileNumberUniqueness(String moduleName,String key,String mobNo) {
        showDialog("");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.checkMobUniqueness(moduleName,key,mobNo);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.UniqueNumber);
                } else {

                    mApiResponseInterface.isError("Failed");
                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Please Contact to Administrator");
                }
            }
        });
    }

    //____________________________________________________++++
    public void commonApiWithTwoPathPost(String path1, String path2, JsonObject jsonObject, String pageno, int reqCode) {
        showDialog("Loading...");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<JsonObject> call = apiService.commonApiWithTwoPath(path1,path2,jsonObject,pageno);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                closeDialog();


                if (response.body() != null) {
                    closeDialog();

                    mApiResponseInterface.isSuccess(response.body(), reqCode);

                } else {
                    mApiResponseInterface.isError("Error from Server");

                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("002: Server not responding ");
                }
            }
        });
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++
    public void checkUserNameUniqueness(String userName) {
        showDialog("Loading");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.checkUserUniqueness(userName);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                closeDialog();
                if (response.body() != null ) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.UNIQUENESS);
                } else if(response.errorBody()!=null){
                    JsonObject jsonObject = null;
                    jsonObject = JsonParser.parseString(TextStreamsKt.readText(response.errorBody().charStream())).getAsJsonObject();

                    mApiResponseInterface.isSuccess(jsonObject, AppConstants.UNIQUENESS);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Please Contact to Administrator");
                }
            }
        });
    }

    //+++++++++++++++++++++++++++++++++++++++++++
    public void commonApiWithTwoPathGet(String path1, String path2, String queryKey,String queryValue,int reqCode) {
        showDialog("Loading...");

        Map<String, String> queryData = new HashMap<>();
        queryData.put(queryKey, queryValue);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.commonApiWithTwoPathGet(path1,path2,queryData);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                closeDialog();


                if (response.body() != null) {
                    closeDialog();

                    mApiResponseInterface.isSuccess(response.body(), reqCode);

                } else {
                    mApiResponseInterface.isError("Error from Server");

                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("002: Server not responding ");
                }
            }
        });
    }

    //+++++++++++++++++++++++++++++++++++++++++++++
    public void registrationWithOtp(String path,JsonObject jsonObject) {
        showDialog("Registration");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.registrationWithOtp(path,jsonObject);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.SEND_OTP_REQUEST);
                } else {

                    if (response.body() != null) {
                        mApiResponseInterface.isError(response.body().getResponse().getMessage().toString());

                    } else {
                        mApiResponseInterface.isError("Something wrong please try later");

                    }
                }

            }

            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Mobile number is already exists");
                }
            }
        });
    }

    //+++++++++++++++++++++++++===========================
    public void searchContentsposterListRequestofline(String pageno, JsonObject jsonObject) {
//        showDialog("Contents List");
//        showDialog("Please Wait data is loading..");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.getSearchContentsListpostercontent(jsonObject,pageno);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.SEARCHCONTENTPOSTERCONTENT);

                } else {

                    mApiResponseInterface.isError("Server not responding");

                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {

                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("002: Server not responding ");
                }
            }
        });
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++
    public void commonApiWithThreePath(String path1, String path2, String path3,JsonObject jsonObject, HashMap queries, int reqCode,int method) {
        showDialog("Loading...");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call=null;
        if(method==AppConstants.METHOD_GET){
            call = apiService.commonApiWithThreePathGet(path1,path2,path3,queries);
        }else if(method==AppConstants.METHOD_PUT){
            if(jsonObject==null){
                jsonObject=new JsonObject();
            }
            call = apiService.commonApiWithThreePathPut(path1,path2,path3,jsonObject,queries);
        }else if(method==AppConstants.METHOD_POST){
            call = apiService.commonApiWithThreePathPost(path1,path2,path3,jsonObject);
        }else if(method==AppConstants.METHOD_DELETE){
            call = apiService.commonApiWithThreePathDelete(path1,path2,path3,queries);
        }

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                closeDialog();


                if (response.body() != null) {
                    closeDialog();

                    mApiResponseInterface.isSuccess(response.body(), reqCode);

                } else {
                    mApiResponseInterface.isError("Error from Server");

                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("002: Server not responding ");
                }
            }
        });
    }

    //+++++++=============================================================
    public void contentCount(String path, JsonObject jsonObject,int reqCode) {
        showDialog("Loading...");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.getContentCount( path, jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                closeDialog();


                if (response.body() != null) {
                    closeDialog();

                    mApiResponseInterface.isSuccess(response.body(), reqCode);

                } else {
                    mApiResponseInterface.isError("Weather Details List download Failed");

                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("002: Server not responding ");
                }
            }
        });
    }

    //+=================================================================================
    public void queryCount(JsonObject jsonObject,int reqCode) {
        showDialog("Loading...");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.getQueryCount( jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                closeDialog();


                if (response.body() != null) {
                    closeDialog();

                    mApiResponseInterface.isSuccess(response.body(), reqCode);

                } else {
                    mApiResponseInterface.isError("Error from Server");

                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("002: Server not responding ");
                }
            }
        });
    }

    //+=================
    public void commonApiWithTwoPathPut(String path1, String path2, JsonObject jsonObject, String pageno, int reqCode) {
        showDialog("Loading...");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.commonApiWithTwoPathPut(path1,path2,jsonObject,pageno);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                closeDialog();


                if (response.body() != null) {
                    closeDialog();

                    mApiResponseInterface.isSuccess(response.body(), reqCode);

                } else {
                    mApiResponseInterface.isError("Error from Server");

                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("002: Server not responding ");
                }
            }
        });
    }

    //+==========================================================
    public void geoFilter(String path, JsonObject jsonObject) {
        showDialog("Loading");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<RootOneModel> call = apiService.geoFilter(path, jsonObject);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.GeoFilter);

                } else {
                    mApiResponseInterface.isError("Add Query API TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Response Model Conversion Issue");
                }
            }
        });
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++
    public void farmerCropDetaile(JsonObject jsonObject) {
        showDialog("Loading");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneModel> call = apiService.farmerCropDetaile(jsonObject);
        call.enqueue(new Callback<RootOneModel>() {
            @Override
            public void onResponse(Call<RootOneModel> call, Response<RootOneModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.FARMER_DETAILS_REQUEST);

                } else {
                    mApiResponseInterface.isError("Farmer Details API TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Please contact to Administrator");
                }
            }
        });
    }
    // ========+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private void showDialog(String message) {
        if(message.isEmpty()){
            message="Loading";
        }
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * The purpose of this method is to close the dialog
     */
    private void closeDialog() {
        if (dialog != null && dialog.isShowing()) {
           dialog.dismiss();
        }
    }
  }
