package com.example.testproject.ui.Fragment.Farmer;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import com.example.testproject.Adapter.VpAdapter;
import com.example.testproject.R;
import com.example.testproject.databinding.FragmentQueryBinding;
import com.example.testproject.databinding.FragmentQueryTabsBinding;
import com.google.android.material.tabs.TabLayout;

public class ContentTabFragment extends BaseFragment {

    private FragmentQueryTabsBinding binding;
    private NavController navController ;


    @Override
    protected void init() {
        layoutId = R.layout.fragment_query_tabs;
    }


    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

        binding = (FragmentQueryTabsBinding) viewDataBinding;


        binding.tab.setupWithViewPager(binding.viewpager);

        VpAdapter vpAdapter = new VpAdapter(getChildFragmentManager());
        vpAdapter.addFragment(new SearchContentDetailsFragment() ,"Content");
        vpAdapter.addFragment(new ContentInfoFragment(),"Info");
        vpAdapter.addFragment(new QueryFragment(),"Query");
        vpAdapter.addFragment(new FeedbackList_Fragment(),"Feedback");
        binding.viewpager.setAdapter(vpAdapter);


        setupViewPager(binding.viewpager);
        binding.tab.setupWithViewPager(binding.viewpager);
        settabIcon();

        binding.tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==2|| tab.getPosition()==3){
//                    show
                    binding.btnAddQuery.setVisibility(View.VISIBLE);
                }else{
                    binding.btnAddQuery.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        }

    private void settabIcon() {
    }

    private void setupViewPager(ViewPager viewpager) {
        navController= NavHostFragment.findNavController(this);

        binding.btnAddQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_queryFragment_to_addFarmerqurie_Fragment);

            }
        });
    }

}

