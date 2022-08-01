package com.example.testproject.ui.Fragment.Farmer;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.view.animation.TranslateAnimation;

//import com.example.testproject.databinding.ActivityDsaboardBinding;
//import com.example.testproject.databinding.ActivityLoginBinding;
import com.example.testproject.R;
import com.example.testproject.databinding.ActivityDsaboardBinding;
import com.example.testproject.ui.Activity.FarmerMainActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.Set;

import pl.droidsonroids.gif.GifDrawable;

public class DashboardFragment extends BaseFragment {
   private ActivityDsaboardBinding binding;
    NavController navController ;

    protected void init() {
        layoutId=R.layout.activity_dsaboard;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

        binding= (ActivityDsaboardBinding) viewDataBinding;
      ((FarmerMainActivity) getActivity()).getToolIcon1().setVisibility(View.GONE);

        navController= NavHostFragment.findNavController(this);
//        ((GifDrawable) binding.gif.getDrawable()).start();

        binding.contentcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             navController.navigate(R.id.action_dashboard_to_contentFragment);
            }
        });
        binding.profilecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_dashboard_to_profileFragment);

            }
        });
        binding.layQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("queryModule","common");
                navController.navigate(R.id.action_dashboard_to_queryTabFragment,bundle);
            }
        });
        binding.notificationcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_dashboard_to_notificationListFragment);
            }
        });


    }
}