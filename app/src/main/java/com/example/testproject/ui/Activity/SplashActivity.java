package com.example.testproject.ui.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.databinding.ViewDataBinding;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.util.JsonMyUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.DefailtConfigDao;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.ActivitySplashBinding;

import com.example.testproject.model.ProductconfigModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.model.UserModel;
import com.example.testproject.ui.Activity.farmer.FarmerMainActivity;
import com.example.testproject.ui.Activity.user.UserFragmentActivity;
import com.example.testproject.ui.base.BaseActivity;


public class SplashActivity extends BaseActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private UserDao userDao;
    private DefailtConfigDao defailtConfigDao;
    ApiResponseInterface mInterFace;
    private ApiManager mApiManager;
    private ActivitySplashBinding binding;
    @Override
    protected void init() {
        layoutId=R.layout.activity_splash;
    }

    @Override
    protected void setUpUi(Bundle savedInstanceState, ViewDataBinding viewDataBinding) {
        binding=(ActivitySplashBinding) viewDataBinding;
        userDao=AppDatabase.getInstance(this).userdao();
        defailtConfigDao=AppDatabase.getInstance(this).defailtConfigDao();
        setupNetwork();
        mApiManager.getDefault();
        Handler handler = new Handler();
        handler.postDelayed(() -> {


            Intent loginIntent = null;
            UserModel userModel=userDao.getUserResponse();
           if(userDao==null || userDao.getUserResponse()==null){

               loginIntent = new Intent(SplashActivity.this, MainSplashActivity.class);
           }else if(userDao.getUserResponse().isFarmer){

                loginIntent = new Intent(SplashActivity.this, FarmerMainActivity.class);

                // add data of notification
                Bundle bundle = getIntent().getExtras();
                if(bundle!=null ){
                    String dataType=bundle.getString("notificationType");
                    if(dataType!=null && dataType.equals("ViewSingleQuery")){
                        loginIntent.setData(Uri.parse("/query/"+bundle.get("id").toString()));
                    }else if(dataType!=null && dataType.equals("ViewSingleContent")) {
                        loginIntent.setData(Uri.parse("/content/"+bundle.get("id").toString()));
                    }else if(dataType!=null && dataType.equals("ViewSingleWeatherAlert")){
                        loginIntent.setData(Uri.parse("/weatheralert/"+bundle.get("id").toString()));
                    }else{
                        loginIntent.putExtras(bundle);
                    }
               }
           }else if (userDao.getUserResponse().isUser){
               loginIntent = new Intent(SplashActivity.this, UserFragmentActivity.class);
               Bundle bundle = getIntent().getExtras();
               if(bundle!=null ){
                   String dataType=bundle.getString("notificationType");
                   if(dataType!=null && dataType.equals("ViewSingleQuery")){
                       loginIntent.setData(Uri.parse("/query/"+bundle.get("id").toString()));
                   }else if(dataType!=null && dataType.equals("ViewSingleContent")) {
                       loginIntent.setData(Uri.parse("/content/"+bundle.get("id").toString()));
                   }else if(dataType!=null && dataType.equals("ViewSingleWeatherAlert")){
                       loginIntent.setData(Uri.parse("/weatheralert/"+bundle.get("id").toString()));
                   }else{
                       loginIntent.putExtras(bundle);
                   }
               }
            }

            startActivity(loginIntent);
            finish();
        },3000);
        }


    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
                showDialog(SplashActivity.this, errorCode, false, true, 0);
            }

            private void showDialog(SplashActivity splashActivity, String errorCode, boolean b, boolean b1, int i) {
            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                RootOneModel rootOneModel=(RootOneModel) response;
                ProductconfigModel model = (ProductconfigModel) JsonMyUtils.getPojoFromJsonObj(ProductconfigModel.class, rootOneModel.getResponse().getData().productconfig.getAsJsonObject());
                defailtConfigDao.insertproductconfig(model);
            }
        };

        mApiManager = new ApiManager(this, mInterFace);
    }

    @Override
    public void okDialogClick(int Id) {

    }

    @Override
    public void cancelDialogClick(int Id) {

    }
}
