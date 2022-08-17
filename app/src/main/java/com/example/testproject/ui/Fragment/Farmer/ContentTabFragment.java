package com.example.testproject.ui.Fragment.Farmer;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testproject.Adapter.VpAdapter;
import com.example.testproject.R;
import com.example.testproject.databinding.FragmentQueryBinding;
import com.example.testproject.databinding.FragmentQueryTabsBinding;
import com.example.testproject.ui.Activity.FarmerMainActivity;
import com.google.android.material.tabs.TabLayout;
public class ContentTabFragment extends BaseFragment {
    private FragmentQueryTabsBinding binding;
    private NavController navController ;

    public static ContentTabFragment newInstance(Bundle bundle) {
        ContentTabFragment fragment = new ContentTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void init() {
        layoutId = R.layout.fragment_query_tabs;
    }
    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (FragmentQueryTabsBinding) viewDataBinding;
        binding.tab.setupWithViewPager(binding.viewpager);
        ((FarmerMainActivity) getActivity()).setTittle("Content");
        binding.tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        VpAdapter vpAdapter = new VpAdapter(getChildFragmentManager());
        vpAdapter.addFragment(new SearchContentDetailsFragment(),getResources().getString(R.string.Content));
        vpAdapter.addFragment(new ContentInfoFragment(),getResources().getString(R.string.Info));
        vpAdapter.addFragment(new QueryFragment(),getResources().getString(R.string.Query));
        vpAdapter.addFragment(new FeedbackList_Fragment(),getResources().getString(R.string.Feedback));
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
        for (int i = 0; i <binding.tab.getTabCount() ; i++) {
            View headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.custom_tab_content, null, false);
            LinearLayout linearLayoutOne = (LinearLayout) headerView.findViewById(R.id.ll3);
            switch (i){
                case 0:
                    ((TextView)linearLayoutOne.findViewById(R.id.tv_tittle)).setText("Content");
                    break;
                case 1:
                    ((TextView)linearLayoutOne.findViewById(R.id.tv_tittle)).setText("Info");
                    break;
                case 2:
                    ((TextView)linearLayoutOne.findViewById(R.id.tv_tittle)).setText("Query");
                    break;
                case 3:
                    ((TextView)linearLayoutOne.findViewById(R.id.tv_tittle)).setText("Feedback");
                    break;
            }
            binding.tab.getTabAt(i).setCustomView(linearLayoutOne);
        }
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

