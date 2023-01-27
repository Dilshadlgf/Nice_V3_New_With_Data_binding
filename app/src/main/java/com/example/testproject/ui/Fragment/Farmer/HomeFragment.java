package com.example.testproject.ui.Fragment.Farmer;

import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;

import com.example.testproject.R;
import com.example.testproject.databinding.ActivityDsaboardBinding;
import com.example.testproject.ui.base.BaseFragment;
//import com.example.testproject.ui.Activity.databinding.FragmentDashboardBinding;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    ActivityDsaboardBinding binding;


     NavController navController ;






    @Override
    protected void init() {

        layoutId=R.layout.fragment_home;

    }//navController= Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_farmer_main);


    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

        binding= (ActivityDsaboardBinding) viewDataBinding;




    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onClick(View view) {

        if(binding.contentcard==view)
        {
//            navController.navigate(R.id.action_navigation_home_to_contentFragment);
        }




    }
}