package com.example.testproject.ui.Fragment.Farmer;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import com.example.testproject.R;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.databinding.FragmentQueryTabsBinding;
import com.example.testproject.ui.Activity.FarmerMainActivity;
import com.example.testproject.ui.Views.ViewPagerAdapter;
import com.example.testproject.ui.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;

/**
 * Created Suraj on 28-06-2022.
 */
public class FarmerCrops_Fragment extends BaseFragment {


    private FarmerDao farmerDao;
    private int p;
    private FragmentQueryTabsBinding binding;
    private NavController  navController;
    public static FarmerCrops_Fragment newInstance(Bundle args) {
        FarmerCrops_Fragment fragment = new FarmerCrops_Fragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void init() {
        layoutId = R.layout.fragment_query_tabs;
//        layoutId =  R.layout.crpcroplist_fragment;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
         binding = (FragmentQueryTabsBinding) viewDataBinding;
        farmerDao= AppDatabase.getInstance((getContext())).farmerDao();
        ((FarmerMainActivity) getActivity()).setTittle(farmerDao.getFarmer().getName()+" Crops");
        ((FarmerMainActivity) getActivity()).showHideEditIcon(false);
        navController= NavHostFragment.findNavController(this);
         binding.btnAddQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                if (p==0) {
                    bundle.putBoolean("isWIP", true);
                }else
                {
                    bundle.putBoolean("isWIP", false);
                }
                navController.navigate(R.id.action_farmerCrops_Fragment_to_addCrops_Update_Fragment,bundle);
            }
        });
        setupViewPager(binding.viewpager);
        binding.tab.setupWithViewPager(binding.viewpager);
        binding.tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();
                p=position;

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        Bundle bundleResolved = new Bundle();
        viewPagerAdapter.addFragment(CropWIPFragment.newInstance(bundleResolved), "WIP");

        viewPagerAdapter.addFragment(CropDONEFragment.newInstance(bundleResolved), "DONE");


        viewPager.setAdapter(viewPagerAdapter);
    }

}




