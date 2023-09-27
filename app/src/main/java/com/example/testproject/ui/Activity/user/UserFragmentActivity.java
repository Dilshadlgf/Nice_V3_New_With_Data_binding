package com.example.testproject.ui.Activity.user;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.testproject.BuildConfig;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.database.Dao.UserACLDao;
import com.example.testproject.databinding.LanguageLayoutBinding;
import com.example.testproject.model.Useracl;
import com.example.testproject.ui.fragment.user.UserDashboardFragment;
import com.example.testproject.util.AppConstants;
import com.example.testproject.util.JsonMyUtils;
import com.example.testproject.util.LocaleHelper;
import com.example.testproject.util.ViewUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.database.Dao.WeatherDetailsDao;
import com.example.testproject.databinding.ActivityUserFragmentBinding;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.ui.Activity.MainSplashActivity;
import com.example.testproject.ui.views.GooeyMenu;
import com.example.testproject.ui.base.BaseActivity;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.JsonObject;

public class UserFragmentActivity extends BaseActivity implements GooeyMenu.GooeyMenuInterface {

    ActivityUserFragmentBinding binding;
    private UserDao  userDao;
    WeatherDetailsDao weatherDetailsDao;
    private NavController navController;
    private AppBarConfiguration mAppBarConfiguration;
    private String sel_lang;
    private GooeyMenu mGooeyMenu;
    public MenuItem shareItem;
    private  UserACLDao userACLDao;

    //common res
    private ApiManager mApiManager;

    //------------------------
    @Override
    protected void init() {
        layoutId= R.layout.activity_user_fragment;
//        if (BuildConfig.FLAVOR.equals("Feeds")) {

//        }else {
//            LocaleHelper.setLocale(getBaseContext(), LocaleHelper.getLanguage(getBaseContext()));
//        }
        sel_lang= LocaleHelper.getLanguage(getBaseContext());
        if(sel_lang==null){
            sel_lang=LocaleHelper.ENGLISH_LANGUAGE;
            LocaleHelper.setLocale(getBaseContext(), LocaleHelper.ENGLISH_LANGUAGE);
        }
    }

    @Override
    protected void setUpUi(Bundle savedInstanceState, ViewDataBinding viewDataBinding) {
        super.setUpUi(savedInstanceState, viewDataBinding);
        userACLDao=AppDatabase.getInstance(UserFragmentActivity.this).getUseraclDao();
        binding = (ActivityUserFragmentBinding) viewDataBinding;
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        userDao = AppDatabase.getInstance(this).userdao();
        weatherDetailsDao = AppDatabase.getInstance(this).weatherDetailsResponseModel();
        navController = Navigation.findNavController(this, R.id.layout_container);
        setupNetwork();
//       setStatusBarGradiant(this);
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
        if(userACLDao.getALLUseracl().size()==0){
            mApiManager.commonApiWithTwoPathGet("useracl","username","id",userDao.getUserResponse().id,AppConstants.ACL);
        }

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
    class LangClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            LocaleHelper.setLocale(getBaseContext(),v.getTag().toString());
//            CommonUtils.setLocalLanguage(getBaseContext(),v.getTag().toString());
            recreate();
        }
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
                if(serviceCode == AppConstants.ACL){

                        JsonObject jObj= (JsonObject) response;
                        JsonObject dataObj=JsonMyUtils.getDataFromJsonObject(jObj);
                        Useracl useracl = (Useracl) JsonMyUtils.getPojoFromJsonObj(Useracl.class,dataObj.getAsJsonObject("useracl"));
                        assert useracl != null;

                        if(userACLDao.getUseracl()!=null || userACLDao.getALLUseracl().size()>0) {
                            userACLDao.deleteALl();
                            userACLDao.insert(useracl);
                        }else {
                            userACLDao.insert(useracl);
                        }

                }

            }
        };
        mApiManager=new ApiManager(this, mInterFace);
    }

    @Override
    public void okDialogClick(int Id) {
        if (Id==AppConstants.DIALOG_LOGIN_BACK_ID){
            logout();
        }else if(Id==2){
            finish();
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
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getCurrentVisibleFragment();
        if (currentFragment != null) {
            if (currentFragment instanceof UserDashboardFragment) {

                showDialog(UserFragmentActivity.this,"Are you sure you want to exit?",true,true, 2);
            } else {
                NavController navController = NavHostFragment.findNavController(currentFragment);
                navController.popBackStack();
            }
        }
    }

    private Fragment getCurrentVisibleFragment() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.layout_container);
        FragmentManager fragmentManager = navHostFragment.getChildFragmentManager();
        Fragment fragment = fragmentManager.getPrimaryNavigationFragment();
        return fragment instanceof Fragment ? (Fragment) fragment : null;
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
    public static void setStatusBarGradiant(Activity activity) {
        Window window = activity.getWindow();
        Drawable background = activity.getResources().getDrawable(R.drawable.custom_top_bar);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(background);
    }


}
