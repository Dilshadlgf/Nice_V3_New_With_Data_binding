package com.example.testproject.ui.Activity;

import androidx.databinding.ViewDataBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testproject.Util.AppConstants;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.databinding.ActivityMainBinding;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.ui.Activity.farmer.FarmerLoginActivity;
import com.example.testproject.ui.Activity.farmer.FarmerMainActivity;
import com.example.testproject.ui.Activity.user.UserLoginActivity;
import com.example.testproject.ui.base.BaseActivity;

import retrofit2.Call;

public class MainSplashActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private ApiResponseInterface mInterFace;
    private Call<RootOneModel> call;
    private ApiManager  mApiManager;

    @Override
    protected void init() {
        layoutId=R.layout.activity_main;
    }

    @Override
    protected void setUpUi(Bundle savedInstanceState, ViewDataBinding viewDataBinding) {
        binding=(ActivityMainBinding) viewDataBinding;
        setUpNetWork();

            binding.layFarmer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        startActivity(new Intent(MainSplashActivity.this, FarmerLoginActivity.class));
                }

            });
            binding.layUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainSplashActivity.this, UserLoginActivity.class));
                    finish();

                }
            });

        mApiManager.getDefault();


     }

    private void setUpNetWork() {

        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {

            }


        };
        mApiManager = new ApiManager(this, mInterFace);
    }

    @Override
    public void onBackPressed() {
        showDialog(MainSplashActivity.this,"Do you want to exit",true,true, AppConstants.DIALOG_LOGIN_BACK_ID);
    }

    @Override
    public void okDialogClick(int Id) {
        if (Id==AppConstants.DIALOG_LOGIN_BACK_ID){
            finish();
        }
    }

    @Override
    public void cancelDialogClick(int Id) {

    }
}