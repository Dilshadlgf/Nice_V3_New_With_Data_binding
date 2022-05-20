package com.example.testproject.ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testproject.R;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.RoleDao;
import com.example.testproject.databinding.ActivityLoginBinding;
import com.example.testproject.model.RoleModel;
import com.example.testproject.ui.Fragment.Farmer.DashboardFragment;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.Util.AppConstants;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;

public class FarmerLoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private Retrofit retrofit;
    private ApiResponseInterface mInterFace;
    private Call<RootOneModel> call;
    private ApiManager  mApiManager;
    private int count=0;
    private RoleDao roleDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        roleDao= AppDatabase.getInstance(this).roleDao();

            binding.singin.setOnClickListener(new View.OnClickListener() {
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



        setUpNetWork();

    }

    private void setUpNetWork() {



        mInterFace = new ApiResponseInterface() {


            @Override
            public void isError(String errorCode) {

                startActivity(new Intent(FarmerLoginActivity.this, FarmerLoginActivity.class));


            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {

                RootOneModel rootOneModel=(RootOneModel) response;

                if(ServiceCode==AppConstants.LOGIN_REQUEST){

                    binding.layoutPassword.setVisibility(View.VISIBLE);

                }

                if (ServiceCode==AppConstants.Validate)
                {
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