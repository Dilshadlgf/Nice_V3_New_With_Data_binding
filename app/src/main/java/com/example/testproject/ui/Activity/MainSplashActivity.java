package com.example.testproject.ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testproject.model.Productconfig;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.databinding.ActivityMainBinding;
import com.example.testproject.ui.Fragment.Farmer.Dashboard;

import retrofit2.Call;

public class MainSplashActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ApiResponseInterface mInterFace;
    private Call<RootOneModel> call;
    private ApiManager  mApiManager;
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding=DataBindingUtil.setContentView(this, R.layout.activity_main);
         setUpNetWork();

        binding.layfarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              startActivity(new Intent(MainSplashActivity.this, FarmerLoginActivity.class));

             }

        });

        mApiManager.getDefault();



    }

    private void setUpNetWork() {

        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
//                showDialog(LoginActivity.this, errorCode, false, true, 0);
//                Intent dashboardIntent = new Intent(LoginActivity.this, LoginActivity.class);
//                overridePendingTransition(0, 0);
//                startActivity(dashboardIntent);
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


}