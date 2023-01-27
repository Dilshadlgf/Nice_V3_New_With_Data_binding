package com.example.testproject.ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.testproject.Util.AppConstants;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.databinding.ActivityMainBinding;
import com.example.testproject.model.Productconfig;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.ui.base.BaseActivity;

import pl.droidsonroids.gif.GifDrawable;
import retrofit2.Call;

public class MainSplashActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private ApiResponseInterface mInterFace;
    private Call<RootOneModel> call;
    private ApiManager  mApiManager;
    private FarmerDao farmerDao;

    @Override
    protected void init() {
        layoutId=R.layout.activity_main;
    }

    @Override
    protected void setUpUi(Bundle savedInstanceState, ViewDataBinding viewDataBinding) {
        binding=(ActivityMainBinding) viewDataBinding;
        setUpNetWork();
        FarmerDao farmerDao= AppDatabase.getInstance(this).farmerDao();


            binding.layfarmer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (farmerDao.getFarmer()==null) {
                        startActivity(new Intent(MainSplashActivity.this, FarmerLoginActivity.class));
                    }else {
                        startActivity(new Intent(MainSplashActivity.this, FarmerMainActivity.class));

                    }
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

                RootOneModel rootOneModel=(RootOneModel) response;
                Productconfig productconfig=rootOneModel.getResponse().getData().getProductconfig();
                if (productconfig.getIsFarmer().equals("Yes"))
                {
                    binding.layfarmer.setVisibility(View.VISIBLE);
                }else
                {
                    binding.layfarmer.setVisibility(View.GONE);
                }

                if (productconfig.getIsDealer().equals("Yes"))
                {
                    binding.laydealer.setVisibility(View.VISIBLE);
                }else
                {
                    binding.laydealer.setVisibility(View.GONE);
                }

                if (productconfig.getIsUser().equals("Yes"))
                {
                    binding.layuser.setVisibility(View.VISIBLE);
                }else
                {
                    binding.layuser.setVisibility(View.GONE);
                }
                binding.progressBar.setVisibility(View.GONE);

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