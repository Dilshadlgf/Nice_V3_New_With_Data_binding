package com.example.testproject.ui.Activity;

import android.os.Bundle;
import android.util.Log;

import com.example.testproject.R;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.database.Dao.RoleDao;
import com.example.testproject.databinding.ActivityFarmerMainBinding;
import com.example.testproject.model.FarmerDataModel;
import com.example.testproject.model.RoleModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;



public class FarmerMainActivity extends AppCompatActivity {

    private ActivityFarmerMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFarmerMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//         Passing each menu ID as a set of Ids because each
//         menu should be considered as top level destinations.

        navController = Navigation.findNavController(this, R.id.fragmentContainerView3);
//        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bottom);
        setSupportActionBar(binding.toolbar);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.navBottom, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout);

        NavigationUI.setupWithNavController(
                binding.toolbar, navController, appBarConfiguration);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

//

        RoleDao roleDao= AppDatabase.getInstance(this).roleDao();
        FarmerDao farmerDao= AppDatabase.getInstance(this).farmerDao();
        if(roleDao.getRole()==null){
            RoleModel roleModel=new RoleModel();
            roleModel.setFarmer(true);
            roleDao.insertRoleResponse(roleModel);
        }
        RoleModel m=roleDao.getRole();
        if(farmerDao.getFarmer()==null){
            FarmerDataModel dataModel=new FarmerDataModel();
            dataModel.setId("621758b11daffc762c720138");
            farmerDao.insertFarmerResponse(dataModel);
        }
        Log.i("","");

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



}