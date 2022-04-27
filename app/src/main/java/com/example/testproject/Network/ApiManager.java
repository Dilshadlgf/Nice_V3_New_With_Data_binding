package com.example.testproject.Network;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.testproject.Util.SharedPreferenceHelper;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.model.RootOneResModel;
import com.example.testproject.model.query.RootQueryModel;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager {


    private Context mContext;
   // private String jsessionid,jsessionidd = "";
    private ProgressDialog dialog;
    private ApiResponseInterface mApiResponseInterface;

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
        showDialog("Queries List");
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
                        mApiResponseInterface.isError("Queries are not available");
                    } else {
                        mApiResponseInterface.isError("Queries List API TimeOut Please contact to Administrator");
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
