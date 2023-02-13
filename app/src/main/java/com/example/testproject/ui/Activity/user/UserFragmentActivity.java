package com.example.testproject.ui.Activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.ViewUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.database.Dao.WeatherDetailsDao;
import com.example.testproject.databinding.ActivityUserFragmentBinding;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.ui.Activity.MainSplashActivity;
import com.example.testproject.ui.Activity.farmer.FarmerMainActivity;
import com.example.testproject.ui.views.GooeyMenu;
import com.example.testproject.ui.base.BaseActivity;
import com.google.android.material.navigation.NavigationBarView;

public class UserFragmentActivity extends BaseActivity implements GooeyMenu.GooeyMenuInterface {

    ActivityUserFragmentBinding binding;
    private UserDao  userDao;
    WeatherDetailsDao weatherDetailsDao;
    private NavController navController;
    private AppBarConfiguration mAppBarConfiguration;
    private String sel_lang;
    private GooeyMenu mGooeyMenu;
    public MenuItem shareItem;

    //common res
    private ApiManager mApiManager;

    //------------------------
    @Override
    protected void init() {
        layoutId= R.layout.activity_user_fragment;
    }

    @Override
    protected void setUpUi(Bundle savedInstanceState, ViewDataBinding viewDataBinding) {
        super.setUpUi(savedInstanceState, viewDataBinding);
        binding = (ActivityUserFragmentBinding) viewDataBinding;
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        userDao = AppDatabase.getInstance(this).userdao();
        weatherDetailsDao = AppDatabase.getInstance(this).weatherDetailsResponseModel();
        navController = Navigation.findNavController(this, R.id.layout_container);
        setupNetwork();
        binding.navBottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dashboardfragment:
                        navController.navigate(R.id.userDashboardFragment);
                        hideIcon();
                        setTittle("Dashboard");
                        return true;
                    case R.id.profileFragment:
                        return true;
                    case R.id.logout:
                        showDialog(UserFragmentActivity.this,"Do you want to logout",true,true, AppConstants.DIALOG_LOGIN_BACK_ID);
                        return true;
                }
                return false;
            }
        });
        binding.topBar.toggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        binding.fab.setOnMenuListener(this);
        closeFabButton();

    }
    private void logout(){
        mApiManager.sendOrDeleteFbToken(null,userDao.getUserResponse().id,false);
        AppDatabase.destroyInstance();
        userDao.deleteUserModel();
        if(weatherDetailsDao.getWeatherDetailsResponseModel()!=null) {
            weatherDetailsDao.deleteWeatherDetailsResponseModel(weatherDetailsDao.getWeatherDetailsResponseModel());
        }
        userDao.deleteUserModel();
        startActivity(new Intent(this,MainSplashActivity.class));
        finish();
    }

    private void setupNetwork() {
        ApiResponseInterface mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
                Toast.makeText(UserFragmentActivity.this, errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void isSuccess(Object response, int serviceCode) {
                if (serviceCode == AppConstants.LOGIN_REQUEST) {
                    RootOneModel rootModel = (RootOneModel) response;

                }

            }
        };
        mApiManager=new ApiManager(this, mInterFace);
    }

    @Override
    public void okDialogClick(int Id) {
        if (Id==AppConstants.DIALOG_LOGIN_BACK_ID){
            logout();
        }

    }

    @Override
    public void cancelDialogClick(int Id) {

    }

    @Override
    public void menuOpen() {

    }

    @Override
    public void menuClose() {

    }

    @Override
    public void menuItemClicked(int menuNumber) {
        if(menuNumber>5){
            menuNumber=menuNumber%5;
            menuNumber=menuNumber>0?menuNumber:5; //this is bug .so i handling like this
        }

        switch (menuNumber){
            case 1: //sms
                navController.navigate(R.id.createSmsFragment);
                break;
            case 2: //voice
                navController.navigate(R.id.createVoiceFragment);
                break;
            case 3: //video
                navController.navigate(R.id.createVideoFragment);
                break;
            case 4: //doc
                navController.navigate(R.id.createDocumentFragment);
                break;
            case 5: //poster
//                CustomFragmentManager.replaceFragment(getSupportFragmentManager(),new CreatePosterFragment());
                break;
        }
        closeFabButton();
    }
    private void closeFabButton(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewUtils.triggerTouchOnView(binding.fab,binding.fab.mCenterX,binding.fab.mCenterY);
            }
        },100);
    }
    public void setTittle(String tittle){
        binding.topBar.txtTittle.setText(tittle);
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

}
