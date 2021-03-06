package com.example.testproject.ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testproject.R;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.database.Dao.RoleDao;
import com.example.testproject.databinding.ActivityLoginBinding;
import com.example.testproject.databinding.LoginActivityFarmerBinding;
import com.example.testproject.model.FarmerDataModel;
import com.example.testproject.model.FarmerModel;
import com.example.testproject.model.RoleModel;
import com.example.testproject.ui.Fragment.Farmer.DashboardFragment;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.Util.AppConstants;
import com.google.gson.JsonObject;

import pl.droidsonroids.gif.GifDrawable;
import retrofit2.Call;
import retrofit2.Retrofit;

public class FarmerLoginActivity extends AppCompatActivity {

    private LoginActivityFarmerBinding binding;
    private Retrofit retrofit;
    private ApiResponseInterface mInterFace;
    private Call<RootOneModel> call;
    private ApiManager  mApiManager;
    private int count=0;
    private RoleDao roleDao;
    private FarmerDao farmerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity_farmer);
       ((GifDrawable) binding.gif.getDrawable()).start();
        setUpNetWork();
        roleDao= AppDatabase.getInstance(this).roleDao();
        farmerDao= AppDatabase.getInstance(this).farmerDao();

            binding.btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (count == 0) {
                        count++;
                        JsonObject object = new JsonObject();
                        object.addProperty("mobileNumber", binding.etUsername.getText().toString());
                        mApiManager.loginbymobile(object);
                    } else if (count == 1) {
                        JsonObject object = new JsonObject();
                        object.addProperty("mobileNumber", binding.etUsername.getText().toString());
                        object.addProperty("otp", binding.etPassword.getText().toString());
                        mApiManager.mobileNoValidate(object);
                    }
                }
            });
            binding.btnRegistration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent registrationIntent = new Intent(FarmerLoginActivity.this, FarmerRegistrationActivity.class);
                    startActivity(registrationIntent);
                    finish();
                }
            });





    }

    private void setUpNetWork() {



        mInterFace = new ApiResponseInterface() {


            @Override
            public void isError(String errorCode) {

                startActivity(new Intent(FarmerLoginActivity.this, FarmerLoginActivity.class));


            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {



                if(ServiceCode==AppConstants.LOGIN_REQUEST){

//                    binding.layoutPassword.setVisibility(View.VISIBLE);
                    binding.layUsername.setVisibility(View.GONE);
                    binding.btnLogin.setText("Loging");
                    binding.etUsername.setText("Enter Your Password");
                    binding.etUsername.setBackgroundResource(R.drawable.edit);

                }

                if (ServiceCode==AppConstants.Validate)
                {
                    RootOneModel rootOneModel=(RootOneModel) response;
                    FarmerDataModel farmerModel=rootOneModel.getResponse().getData().getData();
                    farmerDao.insertFarmerResponse(farmerModel);
                    RoleModel model=new RoleModel();
                    model.setFarmer(true);
                    roleDao.insertRoleResponse(model);
                    startActivity(new Intent(FarmerLoginActivity.this, MainSplashActivity.class));
                }

            }
        };
        mApiManager = new ApiManager(this, mInterFace);
    }



}