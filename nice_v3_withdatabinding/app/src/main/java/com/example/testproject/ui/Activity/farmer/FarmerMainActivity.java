package com.example.testproject.ui.Activity.farmer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.testproject.BuildConfig;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.databinding.LanguageLayoutBinding;
import com.example.testproject.util.AppConstants;
import com.example.testproject.database.AppDatabase;

import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.ActivityFarmerMainBinding;
import com.example.testproject.databinding.HeaderLayoutBinding;
import com.example.testproject.ui.base.BaseActivity;
import com.example.testproject.util.LocaleHelper;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.OutputStream;
import java.util.Locale;

public class FarmerMainActivity extends BaseActivity {

    private ActivityFarmerMainBinding binding;
        private NavController navController;
        private AppBarConfiguration mAppBarConfiguration;
    private ApiManager mApiManager;
    private UserDao userDao;
    private ApiResponseInterface mInterFace;
    private HeaderLayoutBinding headerLayoutBinding;
    private static OutputStream btoutputstream;
    private String printContent = "";
    private String sel_lang;


    @Override
    protected void init() {
        layoutId = R.layout.activity_farmer_main;
        sel_lang= LocaleHelper.getLanguage(getBaseContext());
        if(sel_lang==null){
            sel_lang=LocaleHelper.ENGLISH_LANGUAGE;
            LocaleHelper.setLocale(getBaseContext(), LocaleHelper.ENGLISH_LANGUAGE);
        }

    }

    @Override
    protected void setUpUi(Bundle savedInstanceState, ViewDataBinding viewDataBinding) {
        binding = (ActivityFarmerMainBinding) viewDataBinding;
        setUpNetWork();
        setContentView(binding.getRoot());
        userDao = AppDatabase.getInstance(this).userdao();
        navController = Navigation.findNavController(this, R.id.fragmentContainerView3);
         mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.dashboardfragment, R.id.profileFragment)
                .setOpenableLayout(binding.drawerLayout)
                .build();
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.navBottom, navController);
//        headerLayoutBinding=HeaderLayoutBinding.inflate(LayoutInflater.from(this));
//        binding.navView.addHeaderView(headerLayoutBinding.getRoot());

        binding.topBar.toggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        Log.i("","");

        binding.navBottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboardfragment:
                         navController.navigate(R.id.dashboardfragment);
                         hideIcon();
                         setTittle("Dashboard");
                        break;

                    case R.id.profileFragment:
                        navController.navigate(R.id.profileFragment);

                        break;

                    case R.id.logout:
                        showDialog(FarmerMainActivity.this,"Do you want to logout",true,true,AppConstants.DIALOG_LOGIN_BACK_ID);
                        break;

                }
                return false;
            }
            
        });

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.myprofile:
                        navController.navigate(R.id.profileFragment);
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.commoncontent:
                        navController.navigate(R.id.contentFragment);
                        binding.drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case R.id.commonquery:
                        navController.navigate(R.id.queryTabFragment);
                        binding.drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case R.id.notification:
                        navController.navigate(R.id.notificationListFragment);
                        binding.drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case R.id.logout:
                        showDialog(FarmerMainActivity.this,"Do you want to logout",true,true,AppConstants.DIALOG_LOGIN_BACK_ID);
                        binding.drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case R.id.weather:
                        navController.navigate(R.id.weatherFragment);
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return false;
            }
        });
        if (BuildConfig.FLAVOR.equals("Feeds")) {
            LanguageLayoutBinding headerLayoutBinding=LanguageLayoutBinding.inflate(LayoutInflater.from(this));
            binding.navView.addHeaderView(headerLayoutBinding.getRoot());
            LangClickListener langClickListener=new LangClickListener();
            headerLayoutBinding.ivenglish.setOnClickListener(langClickListener);
            headerLayoutBinding.ivhindi.setOnClickListener(langClickListener);
            headerLayoutBinding.ivmarathi.setOnClickListener(langClickListener);
            headerLayoutBinding.ivtamil.setOnClickListener(langClickListener);

             setlangButton(headerLayoutBinding);
        }

    }
    private void setlangButton(LanguageLayoutBinding headerLayoutBinding){
        if(sel_lang.equals(headerLayoutBinding.ivenglish.getTag().toString())){
            headerLayoutBinding.ivenglish.setBackgroundResource(R.drawable.englishimg);
            headerLayoutBinding.ivhindi.setBackgroundResource(R.drawable.deselecthindi);
        }else if(sel_lang.equals(headerLayoutBinding.ivhindi.getTag().toString())){
            headerLayoutBinding.ivenglish.setBackgroundResource(R.drawable.deselecteng);
            headerLayoutBinding.ivhindi.setBackgroundResource(R.drawable.selecthindi);
        }else if(sel_lang.equals(headerLayoutBinding.ivmarathi.getTag().toString())){
            headerLayoutBinding.ivmarathi.setBackgroundResource(R.drawable.ract_darkgreen2);
        }else if(sel_lang.equals(headerLayoutBinding.ivtamil.getTag().toString())){
            headerLayoutBinding.ivtamil.setBackgroundResource(R.drawable.ract_darkgreen2);
        }
    }


    private void setUpNetWork() {
        mInterFace = new ApiResponseInterface() {

            @Override
            public void isError(String errorCode) {

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if(ServiceCode== AppConstants.SendFbToken){

                }

            }
        };
        mApiManager = new ApiManager(this, mInterFace);

    }

    public void showHideEditIcon(boolean  b){
        if (!b){
            binding.topBar.edit.setVisibility(View.GONE);
            binding.topBar.backBtn.setVisibility(View.VISIBLE);
            binding.topBar.toggleBtn.setVisibility(View.GONE);
        }else {
            binding.topBar.edit.setVisibility(View.VISIBLE);
            binding.topBar.backBtn.setVisibility(View.GONE);
            binding.topBar.toggleBtn.setVisibility(View.VISIBLE);

        }
    }
    public void hideIcon(){
        binding.topBar.edit.setVisibility(View.GONE);
        binding.topBar.backBtn.setVisibility(View.GONE);
        binding.topBar.toggleBtn.setVisibility(View.VISIBLE);
    }
    public void setTittle(String tittle){
        binding.topBar.txtTittle.setText(tittle);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, binding.drawerLayout);
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

    @Override
    public void okDialogClick(int Id) {
        if (Id == AppConstants.DIALOG_LOGIN_BACK_ID) {
            mApiManager.sendOrDeleteFbToken(null,userDao.getUserResponse().id,false);
            AppDatabase.destroyInstance();
            userDao.deleteUserModel();
            startActivity(new Intent(FarmerMainActivity.this, FarmerLoginActivity.class));
            finish();
        }else if (Id==1){
            finish();

        }
    }

    @Override
    public void cancelDialogClick(int Id) {

    }
    public void connect(String message) {
        printContent = message;
//        if (btsocket == null) {
////            languageCode = "hi";
////            LocaleHelper.setLocale(getApplicationContext(), languageCode);
//            Intent BTIntent = new Intent(getApplicationContext(), BTDeviceList.class);
//            this.startActivityForResult(BTIntent, BTDeviceList.REQUEST_CONNECT_BT);
//        } else {

            OutputStream opstream = null;
//            try {
//                opstream = btsocket.getOutputStream();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            btoutputstream = opstream;
//            print_bt();

//        }

    }
    class LangClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            LocaleHelper.setLocale(getBaseContext(),v.getTag().toString());
//            CommonUtils.setLocalLanguage(getBaseContext(),v.getTag().toString());
            recreate();
        }
    }


}
