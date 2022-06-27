package com.example.testproject.Network;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.testproject.Util.RealPathUtil;
import com.example.testproject.Util.SharedPreferenceHelper;
import com.example.testproject.model.FarmerListModel;
import com.example.testproject.model.Farmerlistnewdatamodel;
import com.example.testproject.model.Root.RootModelOne;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.model.RootOneResModel;
import com.example.testproject.model.SingleObjectModel.SingleObjRootOneResModel;
import com.example.testproject.model.livestock.RootLiveStockResponse;
import com.example.testproject.model.query.RootQueryModel;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
   // private String jsessionid,jsessionidd = "";
    private ProgressDialog dialog;
    private ApiResponseInterface mApiResponseInterface;
    private String jsessionid,jsessionidd = "";


    public ApiManager(Context context, ApiResponseInterface apiResponseInterface) {
        this.mContext = context;
        this.mApiResponseInterface = apiResponseInterface;
       dialog = new ProgressDialog(mContext);
    }

    public void getDefault() {
        showDialog("Loding");

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


    ///////=======================================================
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
    public void LiveStockRequest(JsonObject jsonObject) {
        showDialog("Livestock");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneResModel> call = apiService.LiveStockrequest(jsonObject);
        call.enqueue(new Callback<RootOneResModel>() {
            @Override
            public void onResponse(Call<RootOneResModel> call, Response<RootOneResModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.LiveSTOCKREQUEST);

                } else {
                    mApiResponseInterface.isError("Livestock API TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneResModel> call, Throwable t) {
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
        Call<RootOneResModel> call = apiService.getLiveStockList(jsonObject);
        call.enqueue(new Callback<RootOneResModel>() {
            @Override
            public void onResponse(Call<RootOneResModel> call, Response<RootOneResModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.LiveSTOCKREQUEST);

                } else {
                    mApiResponseInterface.isError("Livestock API TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneResModel> call, Throwable t) {
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
        Call<SingleObjRootOneResModel> call = apiService.updateLivestock( updateApproveRequest);
        call.enqueue(new Callback<SingleObjRootOneResModel>() {
            @Override
            public void onResponse(Call<SingleObjRootOneResModel> call, Response<SingleObjRootOneResModel> response) {
                closeDialog();


                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.UpdatreLivesstock);

                } else {
                    mApiResponseInterface.isError("Update Livestock API TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<SingleObjRootOneResModel> call, Throwable t) {
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
        Call<RootOneResModel> call = apiService.varietyList(jsonObject);
        call.enqueue(new Callback<RootOneResModel>() {
            @Override
            public void onResponse(Call<RootOneResModel> call, Response<RootOneResModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.VarietyListReq);

                } else {
                    mApiResponseInterface.isError("Livestock API TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneResModel> call, Throwable t) {
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
public void stageList(JsonObject jsonObject) {
    showDialog("");
    ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);
    Call<RootOneResModel> call = apiService.stageList(jsonObject);
    call.enqueue(new Callback<RootOneResModel>() {
        @Override
        public void onResponse(Call<RootOneResModel> call, Response<RootOneResModel> response) {
            closeDialog();


            if (response.body() != null) {
                mApiResponseInterface.isSuccess(response.body(), AppConstants.StageListReq);

            } else {
                mApiResponseInterface.isError("Livestock API TimeOut Please contact to Administrator");

            }
        }


        @Override
        public void onFailure(Call<RootOneResModel> call, Throwable t) {
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
        Call<RootLiveStockResponse> call = apiService.deleteLiveStock(token);
        call.enqueue(new Callback<RootLiveStockResponse>() {
            @Override
            public void onResponse(Call<RootLiveStockResponse> call, Response<RootLiveStockResponse> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.DeleteLiveStock);
                } else {

                    mApiResponseInterface.isError("Failed");
                }

            }

            @Override
            public void onFailure(Call<RootLiveStockResponse> call, Throwable t) {
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
        Call<RootModelOne> call = apiService.editprofileUser(token);
        call.enqueue(new Callback<RootModelOne>() {
            @Override
            public void onResponse(Call<RootModelOne> call, Response<RootModelOne> response) {
                closeDialog();
                if (response.body() != null && response.body().response.statusCode == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.EDIT_PROFILE_USER);
                } else {

                    if (response.body() != null) {
                        mApiResponseInterface.isError(response.body().response.message);

                    } else {
                        mApiResponseInterface.isError("Login API TimeOut Please contact to Administrator");

                    }
                }

            }

            @Override
            public void onFailure(Call<RootModelOne> call, Throwable t) {
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
        Call<RootOneResModel> call = apiService.searchContentsListDetailRequest(id);
        call.enqueue(new Callback<RootOneResModel>() {
            @Override
            public void onResponse(Call<RootOneResModel> call, Response<RootOneResModel> response) {
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
            public void onFailure(Call<RootOneResModel> call, Throwable t) {
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
    Call<RootQueryModel> call = apiService.queriesListRequest(jsonObject,pageno);
    call.enqueue(new Callback<RootQueryModel>() {
        @Override
        public void onResponse(Call<RootQueryModel> call, Response<RootQueryModel> response) {
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
        public void onFailure(Call<RootQueryModel> call, Throwable t) {
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
        Call<RootQueryModel> call = apiService.feedbacklistRequest(object,pageno);
        call.enqueue(new Callback<RootQueryModel>() {
            @Override
            public void onResponse(Call<RootQueryModel> call, Response<RootQueryModel> response) {
                closeDialog();
                if (response.body() != null) {

                    mApiResponseInterface.isSuccess(response.body(), AppConstants.FeedbacklistRequest);

                } else {
                    mApiResponseInterface.isError("Feedback API TimeOut Please contact to Administrator");
                }

            }


            @Override
            public void onFailure(Call<RootQueryModel> call, Throwable t) {
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
        Call<RootOneResModel> call = apiService.getNotificationList(jsonObject,pageno);
        call.enqueue(new Callback<RootOneResModel>() {
            @Override
            public void onResponse(Call<RootOneResModel> call, Response<RootOneResModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.NotificationListReq);

                } else {
                    mApiResponseInterface.isError("Livestock API TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneResModel> call, Throwable t) {
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
                Call<RootQueryModel> call = apiService.uploadQueryImage("query",requestBody);
                call.enqueue(new Callback<RootQueryModel>() {
                    @Override
                    public void onResponse(Call<RootQueryModel> call, Response<RootQueryModel> response) {
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
                    public void onFailure(Call<RootQueryModel> call, Throwable t) {
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

        Call<Farmerlistnewdatamodel> call = apiService.farmerListt("Mobile", jsessionid, id, token);
        call.enqueue(new Callback<Farmerlistnewdatamodel>() {
            @Override
            public void onResponse(Call<Farmerlistnewdatamodel> call, Response<Farmerlistnewdatamodel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.FARMER_LIST_REQUEST);

                } else {
                    mApiResponseInterface.isError("FarmerList API TimeOut Please contact to Administrator");

                }
            }


            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<Farmerlistnewdatamodel> call, Throwable t) {
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
        Call<Farmerlistnewdatamodel> farmerListt(@Header("X-Requested-With") String header, @Header("Cookie") String Cookie, @Path("id") String id, @Query("token") String token);

        @GET("farmers?activeStatus=true&block=&district=&gramPanchayat=&max=100")
        Call<FarmerListModel> farmerListtsec(@Header("X-Requested-With") String header, @Header("Cookie") String Cookie, @Query("offset") int offset, @Query("state") String state, @Query("village") String village, @Query("token") String token);


        //        https://nicessm.org/CCKNIA/farmers?activeStatus=true&block=&district=&gramPanchayat=&max=10&offset=0&searchText=Moni&state=&village=
        @GET("farmers?activeStatus=true&block=&district=&gramPanchayat=&max=10&offset=0")
        Call<Farmerlistnewdatamodel> farmerListtseach(@Header("X-Requested-With") String header,
                                                      @Header("Cookie") String Cookie,@Header("token") String token,  @Query("searchText") String searchtext,
                                                      @Query("state") String state,
                                                      @Query("village") String village);
//       Call<Farmerlistnewdatamodel> farmerListt(@Url String url) ;
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++=====================================
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
        Call<RootQueryModel> call = apiService.sendAddQueryRequest(jsonObject);

        call.enqueue(new Callback<RootQueryModel>() {
            @Override
            public void onResponse(Call<RootQueryModel> call, Response<RootQueryModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.ADD_QUERY_REQUEST);

                } else {
                    mApiResponseInterface.isError("Add Query: TimeOut Please try later");

                }
            }


            @Override
            public void onFailure(Call<RootQueryModel> call, Throwable t) {
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
        Call<RootOneResModel> call = apiService.commodityCategoryFilter(jsonObject);
        call.enqueue(new Callback<RootOneResModel>() {
            @Override
            public void onResponse(Call<RootOneResModel> call, Response<RootOneResModel> response) {
                closeDialog();


                if (response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.CommodityFilterReq);

                } else {
                    mApiResponseInterface.isError("Livestock API TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneResModel> call, Throwable t) {
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
        Call<RootOneResModel> call = apiService.farmerCropDetaile(id);
        call.enqueue(new Callback<RootOneResModel>() {
            @Override
            public void onResponse(Call<RootOneResModel> call, Response<RootOneResModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(),req);
                } else {

                    mApiResponseInterface.isError("Failed");
                }

            }

            @Override
            public void onFailure(Call<RootOneResModel> call, Throwable t) {
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
        Call<RootOneResModel> call = apiService.getFamerSeasonList(id);
        call.enqueue(new Callback<RootOneResModel>() {
            @Override
            public void onResponse(Call<RootOneResModel> call, Response<RootOneResModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(),req);
                } else {

                    mApiResponseInterface.isError("Failed");
                }

            }

            @Override
            public void onFailure(Call<RootOneResModel> call, Throwable t) {
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
        Call<RootModelOne> call = apiService.addFarmerCrop( updateApproveRequest);
        call.enqueue(new Callback<RootModelOne>() {
            @Override
            public void onResponse(Call<RootModelOne> call, Response<RootModelOne> response) {
                closeDialog();


                if (response.body() != null && response.body().response.statusCode == 200) {
                    mApiResponseInterface.isSuccess(response.body(), req);

                } else {
                    mApiResponseInterface.isError("Issue in Update , Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootModelOne> call, Throwable t) {
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
        Call<RootOneResModel> call = apiService.getVarietyList(id);
        call.enqueue(new Callback<RootOneResModel>() {
            @Override
            public void onResponse(Call<RootOneResModel> call, Response<RootOneResModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(),req);
                } else {

                    mApiResponseInterface.isError("Failed");
                }

            }

            @Override
            public void onFailure(Call<RootOneResModel> call, Throwable t) {
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
        Call<RootOneResModel> call = apiService.getFarmerCroplist(id);
        call.enqueue(new Callback<RootOneResModel>() {
            @Override
            public void onResponse(Call<RootOneResModel> call, Response<RootOneResModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), req);
                } else {

                    mApiResponseInterface.isError("Failed");
                }

            }

            @Override
            public void onFailure(Call<RootOneResModel> call, Throwable t) {
                closeDialog();
                if (t instanceof IOException) {
                    mApiResponseInterface.isError("Internet is not Connected");
                } else {
                    mApiResponseInterface.isError("Please Contact to Administrator");
                }
            }
        });
    }

    public void farmerCropDetaile(JsonObject jsonObject) {
        showDialog("Loading");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RootOneResModel> call = apiService.farmerCropDetaile(jsonObject);
        call.enqueue(new Callback<RootOneResModel>() {
            @Override
            public void onResponse(Call<RootOneResModel> call, Response<RootOneResModel> response) {
                closeDialog();
                if (response.body() != null && response.body().getResponse().getStatusCode() == 200) {
                    mApiResponseInterface.isSuccess(response.body(), AppConstants.FARMER_DETAILS_REQUEST);

                } else {
                    mApiResponseInterface.isError("Farmer Details API TimeOut Please contact to Administrator");

                }
            }


            @Override
            public void onFailure(Call<RootOneResModel> call, Throwable t) {
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
