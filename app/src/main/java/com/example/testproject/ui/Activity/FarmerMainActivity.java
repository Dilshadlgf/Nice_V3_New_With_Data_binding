package com.example.testproject.ui.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.Util.LocaleHelper;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.database.Dao.RoleDao;

import com.example.testproject.databinding.ActivityFarmerMainBinding;
import com.example.testproject.databinding.HeaderLayoutBinding;
import com.example.testproject.model.FarmerDataModel;
import com.example.testproject.model.RoleModel;
import com.example.testproject.ui.base.BaseActivity;
import com.example.testproject.ui.base.BaseFragment;
import com.example.testproject.ui.base.BaseFragmentInterface;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.view.GravityCompat;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;
import java.util.Locale;

public class FarmerMainActivity extends BaseActivity {

    private ActivityFarmerMainBinding binding;
    private NavController navController;
    private AppBarConfiguration mAppBarConfiguration;
    private ApiManager mApiManager;
    private FarmerDao farmerDao;
    private RoleDao roleDao;
    private ApiResponseInterface mInterFace;
    private HeaderLayoutBinding headerLayoutBinding;
    @Override
    protected void init() {
        layoutId = R.layout.activity_farmer_main;

    }

    @Override
    protected void setUpUi(Bundle savedInstanceState, ViewDataBinding viewDataBinding) {
        binding = (ActivityFarmerMainBinding) viewDataBinding;
        setUpNetWork();
        setContentView(binding.getRoot());
        farmerDao = AppDatabase.getInstance(this).farmerDao();
        roleDao=AppDatabase.getInstance(this).roleDao();
        navController = Navigation.findNavController(this, R.id.fragmentContainerView3);
         mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.dashboardfragment, R.id.profileFragment)
                .setOpenableLayout(binding.drawerLayout)
                .build();
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.navBottom, navController);
        headerLayoutBinding=HeaderLayoutBinding.inflate(LayoutInflater.from(this));
        binding.navView.addHeaderView(headerLayoutBinding.getRoot());

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
@Override
public void onBackPressed() {
    NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView3);
    int backStackEntryCount = navHostFragment.getChildFragmentManager().getBackStackEntryCount();
    if(backStackEntryCount>0 ){
        String st= (String) navController.getCurrentDestination().getLabel();
        if(st.equals("Dashboard")){
            finish();
            return;
        }else {
            hideIcon();
        }
        navController.popBackStack();
        setTittle("Dashboard");

    }else {
        showDialog(FarmerMainActivity.this,getString(R.string.doyouwanttoexit),true,true,1);
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
    public void onBack(View v){
        navController.navigateUp();
        hideIcon();
        setTittle("Dashboard");
    }

    @Override
    public void okDialogClick(int Id) {
        if (Id == AppConstants.DIALOG_LOGIN_BACK_ID) {
            mApiManager.sendOrDeleteFbToken(null,farmerDao.getFarmer().getId(),false);
            AppDatabase.destroyInstance();
            roleDao.deleteRoleModel();
            farmerDao.deleteFarmer();
            startActivity(new Intent(FarmerMainActivity.this, FarmerLoginActivity.class));
            finish();
        }else if (Id==1){
            finish();

        }
    }

    @Override
    public void cancelDialogClick(int Id) {

    }
}
