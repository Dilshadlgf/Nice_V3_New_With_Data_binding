package com.example.testproject.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.testproject.R;
import com.example.testproject.databinding.ActivityFarmerMainBinding;
import com.example.testproject.ui.Fragment.Farmer.Dashboard;
import com.example.testproject.ui.Fragment.Farmer.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.example.testproject.ui.Activity.databinding.ActivityFarmerMainBinding;

public class FarmerMainActivity extends AppCompatActivity {

    private ActivityFarmerMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_farmer_main);

//       NavController navController=Navigation.findNavController(this,R.id.activity_main_bottom_navigation_view);





        binding = ActivityFarmerMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
////

//         Passing each menu ID as a set of Ids because each
//         menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView3);
//        BottomNavigationView bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation_view);
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.navBottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case   R.id.navigation_home:

                        navController.navigate(R.id.dashboard);

                         break;
                    case R.id.navigation_dashboard:
                        navController.navigate(R.id.profileFragment);

                        break;
                    case R.id.navigation_notifications:

                        break;

                }


                return false;
            }
        });



    }

}