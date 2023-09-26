package com.example.testproject.ui.Activity.user;

import androidx.databinding.ViewDataBinding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.util.AppConstants;
import com.example.testproject.util.CommonUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.ActivityUserLoginBinding;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.model.UserModel;
import com.example.testproject.ui.Activity.MainSplashActivity;
import com.example.testproject.ui.base.BaseActivity;
import com.google.gson.JsonObject;

public class UserLoginActivity extends BaseActivity {
    private ActivityUserLoginBinding binding;
    //common res
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private UserDao userDao;

    private LangClickListener langClickListener;
    private String sel_lang;
    @Override
    protected void init() {
        sel_lang= CommonUtils.loadLocalLanguage(getBaseContext());
        layoutId=R.layout.activity_user_login;
    }

    @Override
    protected void setUpUi(Bundle savedInstanceState, ViewDataBinding viewDataBinding) {
        binding=(ActivityUserLoginBinding) viewDataBinding;
        setupNetwork();
        userDao= AppDatabase.getInstance(this).userdao();
        userDao.deleteUserModel();
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLoginApi();
            }
        });
        binding.btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserLoginActivity.this,UserRegistrationActivity.class));
                finish();

            }
        });
        langClickListener=new LangClickListener();
        binding.layenglish.setOnClickListener(langClickListener);
        binding.layhindi.setOnClickListener(langClickListener);
        binding.laymarathi.setOnClickListener(langClickListener);
        binding.laytamil.setOnClickListener(langClickListener);
        setlangButton();
    }
    private void setlangButton(){
        if(sel_lang.equals(binding.layenglish.getTag().toString())){
            binding.layenglish.setBackgroundResource(R.drawable.ract_darkgreen2);
        }else if(sel_lang.equals(binding.layhindi.getTag().toString())){
            binding.layhindi.setBackgroundResource(R.drawable.ract_darkgreen2);
        }else if(sel_lang.equals(binding.laymarathi.getTag().toString())){
            binding.laymarathi.setBackgroundResource(R.drawable.ract_darkgreen2);
        }else if(sel_lang.equals(binding.laytamil.getTag().toString())){
            binding.laytamil.setBackgroundResource(R.drawable.ract_darkgreen2);
        }
    }

    class LangClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            CommonUtils.setLocalLanguage(getBaseContext(),v.getTag().toString());
            recreate();
        }
    }

    private void callLoginApi(){
        String mob=binding.etUser.getText().toString();
        String pwd=binding.etPassword.getText().toString();
        if(mob.isEmpty() || mob.length()<3){
            Toast.makeText(this,"Please enter valid mobile no",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pwd.isEmpty() || pwd.length()<3){
            Toast.makeText(this,"Please enter valid password",Toast.LENGTH_SHORT).show();
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", mob);
        jsonObject.addProperty("password", pwd);
        mApiManager.userLogin(jsonObject);
    }

    private void sendToken(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("userid",userDao.getUserResponse().id);
        jsonObject.addProperty("usertype","User");
        jsonObject.addProperty("registrationtoken",userDao.getUserResponse().token);
        mApiManager.sendOrDeleteFbToken(jsonObject,"",true);
    }

    private void setupNetwork() {
        mInterFace=new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
                Toast.makeText(UserLoginActivity.this,errorCode,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void isSuccess(Object response, int serviceCode) {
                if(serviceCode== AppConstants.LOGIN_REQUEST){
                    RootOneModel rootOneModel = (RootOneModel) response;
                    if(!(rootOneModel.getResponse().getData().user instanceof JsonObject)){
                        Toast.makeText(UserLoginActivity.this,rootOneModel.getResponse().getMessage(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JsonObject jsonObject=rootOneModel.getResponse().getData().user.getAsJsonObject();
                    if(jsonObject!=null ){
                        String sm=jsonObject.toString();
                        UserModel model= (UserModel) CommonUtils.getPojoFromStr(UserModel.class,sm);
//                        UserModel loginModel=new UserModel();
//                        loginModel.id=model.id;
//                        loginModel.name=model.name;
//                        loginModel.userName=model.userName;
//                        loginModel.stateId=model.stateCode;
//                        loginModel.role=model.role;
                        model.isUser=true;
                        model.token=getPreferences(Context.MODE_PRIVATE).getString("fbToken","");
                        userDao.insert(model);
                        sendToken();
                        goToNewActivity();
                    }
                }

            }
        };
        mApiManager=new ApiManager(this,mInterFace);
    }

    private void goToNewActivity(){
        startActivity(new Intent(this, UserFragmentActivity.class));
        finish();
    }
    @Override
    public void okDialogClick(int Id) {

    }

    @Override
    public void cancelDialogClick(int Id) {

    }
    @Override
    public void onBackPressed() {
        Intent loginIntent = new Intent(UserLoginActivity.this, MainSplashActivity.class);
        startActivity(loginIntent);
        finish();
    }
}