package com.example.testproject.ui.Activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.LocaleHelper;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.database.Dao.RoleDao;

import com.example.testproject.databinding.ActivityFarmerMainBinding;
import com.example.testproject.model.FarmerDataModel;
import com.example.testproject.model.RoleModel;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Locale;

public class FarmerMainActivity extends AppCompatActivity {

    private ActivityFarmerMainBinding binding;
    private NavController navController;
    private AppBarConfiguration mAppBarConfiguration;
    private ApiManager mApiManager;
   // private LoginDao loginDao;
    private FarmerDao farmerDao;
    private RoleDao roleDao;
    private ApiResponseInterface mInterFace;
    boolean click = true;
    private String languageCode = "";
    private ImageView toolbarEditIcon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFarmerMainBinding.inflate(getLayoutInflater());
        setUpNetWork();
        setContentView(binding.getRoot());
        FarmerDao  farmerDao = AppDatabase.getInstance(this).farmerDao();
        RoleDao roleDao=AppDatabase.getInstance(this).roleDao();
        toolbarEditIcon=binding.toolbar.findViewById(R.id.iconedit);
//         Passing each menu ID as a set of Ids because each
//         menu should be considered as top level destinations.

//        binding.toolbar.inflateMenu(R.menu.icon_menu);
//        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Log.i("","menu");
//                return false;
//            }
//        });

        navController = Navigation.findNavController(this, R.id.fragmentContainerView3);
 //        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bottom);
        setSupportActionBar(binding.toolbar);
         mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.dashboardfragment, R.id.profileFragment)
                .setOpenableLayout(binding.drawerLayout)
                .build();
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.navBottom, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout);
//
//        NavigationUI.setupWithNavController(
//                binding.toolbar, navController, mAppBarConfiguration);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


//        getSupportActionBar().setIcon(R.drawable.ic_baseline_edit_24);
//
//        RoleDao roleDao= AppDatabase.getInstance(this).roleDao();
//        FarmerDao farmerDao= AppDatabase.getInstance(this).farmerDao();
//        if(roleDao.getRole()==null){
//            RoleModel roleModel=new RoleModel();
//            roleModel.setFarmer(true);
//            roleDao.insertRoleResponse(roleModel);
//        }
//        RoleModel m=roleDao.getRole();
//        if(farmerDao.getFarmer()==null){
//            FarmerDataModel farmerDataModel=new FarmerDataModel();
//            farmerDataModel.setId("621758b11daffc762c720138");
//            farmerDao.insertFarmerResponse(farmerDataModel);
//        }
        Log.i("","");



               navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                   @Override
                   public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                       getSupportActionBar().setDisplayShowHomeEnabled(true);
                       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(FarmerMainActivity.this,binding.drawerLayout,binding.toolbar,R.string.nav_open,R.string.nav_close);
                       binding.drawerLayout.addDrawerListener(toggle);
                       toggle.syncState();
                   }
               });

        binding.navBottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboardfragment:
                         navController.navigate(R.id.dashboardfragment);
                        break;

                    case R.id.profileFragment:
                        navController.navigate(R.id.profileFragment);

                        break;

                    case R.id.logout:

                        new AlertDialog.Builder(FarmerMainActivity.this,R.style.MyDialogTheme)
                                .setTitle("Logout")
                                .setMessage("Do You Want to logout?")

                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // logout
                                        mApiManager.sendOrDeleteFbToken(null,farmerDao.getFarmer().getId(),false);
                                        AppDatabase.destroyInstance();
                                        roleDao.deleteRoleModel();
                                        farmerDao.deleteFarmer();
                                        startActivity(new Intent(FarmerMainActivity.this, FarmerLoginActivity.class));

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // user doesn't want to logout
                                    }
                                })
                                .show();
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
                        new AlertDialog.Builder(FarmerMainActivity.this,R.style.MyDialogTheme)
                                .setTitle("Logout")
                                .setMessage("Would you like to logout?")

                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // logout
                                        mApiManager.sendOrDeleteFbToken(null,farmerDao.getFarmer().getId(),false);
                                        AppDatabase.destroyInstance();
                                        roleDao.deleteRoleModel();
                                        farmerDao.deleteFarmer();
                                        startActivity(new Intent(FarmerMainActivity.this, FarmerLoginActivity.class));

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // user doesn't want to logout
                                    }
                                })
                                .show();
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

    public ImageView getToolIcon1(){
        return toolbarEditIcon;
    }
    public void setTittle(String tittle){
        binding.toolbar.setTitle(tittle);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, binding.drawerLayout);
    }
    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
//
//     View hView = binding.navView.getHeaderView(0);
//    ImageView english = hView.findViewById(R.id.ivenglish);
//    ImageView hindi = hView.findViewById(R.id.ivhindi);
//    ImageView marathi = hView.findViewById(R.id.ivmarathi);
//    ImageView tamil = hView.findViewById(R.id.ivtamil);

//    @SuppressLint("ResourceType")
//    NavigationView navigationView1 = (NavigationView) findViewById(R.layout.header_layout);
//    View hView1 = navigationView1.getHeaderView(0);
//        ImageView english = hView1.findViewById(R.id.ivenglish);

}
