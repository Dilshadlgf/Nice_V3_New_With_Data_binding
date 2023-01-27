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
import com.example.testproject.databinding.FragmentQueryTabsBinding;
import com.example.testproject.model.ContentModel;
import com.example.testproject.ui.Activity.FarmerMainActivity;
import com.example.testproject.ui.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class ContentTabFragment extends BaseFragment {
    private FragmentQueryTabsBinding binding;
    private NavController navController ;
    private List<ContentModel>data;
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
        ((FarmerMainActivity) getActivity()).setTittle(getString(R.string.content));
        ((FarmerMainActivity) getActivity()).showHideEditIcon(false);
        binding.tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        VpAdapter vpAdapter = new VpAdapter(getChildFragmentManager());
        Bundle bundle =new Bundle();
        String model = getArguments().getString("model","");
        bundle.putString("model", model);
        vpAdapter.addFragment(new SearchContentDetailsFragment(),getResources().getString(R.string.content));
        vpAdapter.addFragment( ContentInfoFragment.newInstance(bundle),getResources().getString(R.string.contentInfo));
        vpAdapter.addFragment(new QueryFragment(),getResources().getString(R.string.query));
        vpAdapter.addFragment( FeedbackList_Fragment.newInstance(bundle),getResources().getString(R.string.feedback));
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
                if (binding.tab.getSelectedTabPosition() == 2) {
                    navController.navigate(R.id.action_queryFragment_to_addFarmerqurie_Fragment);
                }else if (binding.tab.getSelectedTabPosition()==3){
                    Bundle feedbckbundle = new Bundle();
                    String model = getArguments().getString("model","");
                    feedbckbundle.putString("model", model);
                    navController.navigate(R.id.action_queryFragment_to_addFeedbackFragment,feedbckbundle);
                }
            }
        });
    }
}

