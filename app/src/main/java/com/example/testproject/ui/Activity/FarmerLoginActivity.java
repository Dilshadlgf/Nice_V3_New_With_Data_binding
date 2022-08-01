package com.example.testproject.ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.example.testproject.R;
import com.example.testproject.Util.LocaleHelper;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.database.Dao.RoleDao;
import com.example.testproject.databinding.LoginActivityFarmerBinding;
import com.example.testproject.model.FarmerDataModel;
import com.example.testproject.model.RoleModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.Util.AppConstants;
import com.google.gson.JsonObject;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Retrofit;

public class FarmerLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private LoginActivityFarmerBinding binding;
    private Retrofit retrofit;
    private ApiResponseInterface mInterFace;
    private Call<RootOneModel> call;
    private ApiManager  mApiManager;
    private int count=0;
    private RoleDao roleDao;
    private FarmerDao farmerDao;
    boolean click = true;
    private String languageCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity_farmer);
//       ((GifDrawable) binding.gif.getDrawable()).start();
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
        binding.ivenglish.setOnClickListener(this);
        binding.ivhindi.setOnClickListener(this);
        binding.ivmarathi.setOnClickListener(this);
        binding.ivtamil.setOnClickListener(this);


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
                    binding.layoutPassword.setVisibility(View.VISIBLE);
                    binding.btnLogin.setText("Loging");
                    binding.etUsername.setBackgroundResource(R.drawable.edit);

                }

                if (ServiceCode==AppConstants.Validate)
                {
                    RootOneModel rootOneModel=(RootOneModel) response;
                    FarmerDataModel farmerModel=rootOneModel.getResponse().getData().getFarmer();
//                    farmerModel.setStateId(farmerModel.getRef().);
                    farmerDao.insertFarmerResponse(farmerModel);

                    RoleModel model=new RoleModel();
                    model.setFarmer(true);
                    roleDao.insertRoleResponse(model);
                    startActivity(new Intent(FarmerLoginActivity.this, FarmerMainActivity.class));
                }

            }
        };
        mApiManager = new ApiManager(this, mInterFace);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivenglish:
                if (click == true) {
                    languageCode = "en";
                    LocaleHelper.setLocale(getApplicationContext(), languageCode);
                    setlocale("en");

                    binding.ivenglish.setBackgroundResource(R.drawable.selecteng);
                    binding.ivhindi.setBackgroundResource(R.drawable.deselecthindi);
                    binding.ivmarathi.setBackgroundResource(R.drawable.deselectmarathi);
                    binding.ivtamil.setBackgroundResource(R.drawable.deselecttamil);

//                    binding.btnLogin.setText("LOGIN");
                    binding.etUsername.setHint("Enter Username/Mobile Number");
                    binding.btnLogin.setText("Next");
                    binding.btnRegistration.setText("Registration");
                    binding.tvselect.setText("Select your language");

                }
                break;
            case R.id.ivhindi:
                if (click == true) {
                    languageCode = "hi";
                    LocaleHelper.setLocale(getApplicationContext(), languageCode);

                    binding.ivhindi.setBackgroundResource(R.drawable.selecthindi);
                    binding.ivmarathi.setBackgroundResource(R.drawable.deselectmarathi);
                    binding.ivtamil.setBackgroundResource(R.drawable.deselecttamil);
                    binding.ivenglish.setBackgroundResource(R.drawable.deselecteng);

                    setlocale("hi");

                    //binding.btnLogin.setText("लॉगिन करें");
                    binding.etUsername.setHint("उपयोगकर्ता नाम/मोबाइल नंबर दर्ज करें");
                    binding.btnLogin.setText("अगला");
                    binding.btnRegistration.setText("पंजीकरण");
                    binding.tvselect.setText("अपनी भाषा चुनें");
                }
                break;
            case R.id.ivmarathi:
                if (click == true) {
                    languageCode = "mr";
                    LocaleHelper.setLocale(getApplicationContext(), languageCode);
                    setlocale("mr");

                    binding.ivmarathi.setBackgroundResource(R.drawable.selectmarathi);
                    binding.ivtamil.setBackgroundResource(R.drawable.deselecttamil);
                    binding.ivhindi.setBackgroundResource(R.drawable.deselecthindi);
                    binding.ivenglish.setBackgroundResource(R.drawable.deselecteng);

                    //binding.btnLogin.setText("लॉगिन");
                    binding.etUsername.setHint("वापरकर्तानाव/मोबाइल क्रमांक प्रविष्ट करा");
                    binding.btnLogin.setText("पुढे");
                    binding.btnRegistration.setText("नोंदणी");
                    binding.tvselect.setText("आपली भाषा निवडा");
                }
                break;
            case R.id.ivtamil:
                if (click == true) {
                    languageCode = "ta";
                    LocaleHelper.setLocale(getApplicationContext(), languageCode);
                    setlocale("ta");

                    binding.ivtamil.setBackgroundResource(R.drawable.selecttamil);
                    binding.ivmarathi.setBackgroundResource(R.drawable.deselectmarathi);
                    binding.ivhindi.setBackgroundResource(R.drawable.deselecthindi);
                    binding.ivenglish.setBackgroundResource(R.drawable.deselecteng);

                    //binding.btnLogin.setText("உள்நுழைவு");
                    binding.etUsername.setHint("பயனர்பெயர் / மொபைல் எண்ணை உள்ளிடுக");
                    binding.btnLogin.setText("அடுத்து");
                    binding.btnRegistration.setText("பதிவு");
                    binding.tvselect.setText("உங்கள் மொழியைத் தேர்ந்தெடுக்கவும்");
                }
                break;

        }

    }
    private void setlocale(String lang) {
        Locale locale = new Locale(lang);
        locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
        editor.putString("my lang", lang);
        editor.apply();
    }

}