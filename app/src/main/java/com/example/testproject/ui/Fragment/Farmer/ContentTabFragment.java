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
import com.example.testproject.ui.Activity.farmer.FarmerMainActivity;
import com.example.testproject.ui.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;

public class ContentTabFragment extends BaseFragment {
    private FragmentQueryTabsBinding binding;
    private NavController navController ;
    String modeldata;
    String type="0";
    VpAdapter vpAdapter;
    Bundle bundle;
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
        binding.tab.setSelectedTabIndicator(null);
        navController= NavHostFragment.findNavController(this);
        ((FarmerMainActivity) getActivity()).setTittle(getString(R.string.content));
        ((FarmerMainActivity) getActivity()).showHideEditIcon(false);
        binding.tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.viewpager.setOffscreenPageLimit(1);
        vpAdapter = new VpAdapter(getChildFragmentManager());
        bundle =new Bundle();
        modeldata = getArguments().getString("model","");
        type = getArguments().getString("type","0");
        bundle.putString("model", modeldata);
        if (type.equals("0")){
            select(0);
            binding.btnAddQuery.setVisibility(View.GONE);
        }else if (type.equals("3")){
            select(3);
            binding.btnAddQuery.setVisibility(View.VISIBLE);
        }else if (type.equals("2")){
            select(2);
            binding.btnAddQuery.setVisibility(View.VISIBLE);
        }
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
    @Override
    public void onBackCustom() {
        navController.navigate(R.id.contentFragment);
    }
    private void settabIcon() {
        for (int i = 0; i <binding.tab.getTabCount() ; i++) {
            View headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.custom_tab_content, null, false);
            LinearLayout linearLayoutOne = (LinearLayout) headerView.findViewById(R.id.ll3);
            switch (i){
                case 0:
                    ((TextView)linearLayoutOne.findViewById(R.id.tv_tittle)).setText(getString(R.string.content));
                    break;
                case 1:
                    ((TextView)linearLayoutOne.findViewById(R.id.tv_tittle)).setText(getString(R.string.contentInfo));
                    break;
                case 2:
                    ((TextView)linearLayoutOne.findViewById(R.id.tv_tittle)).setText(getString(R.string.query));
                    break;
                case 3:
                    ((TextView)linearLayoutOne.findViewById(R.id.tv_tittle)).setText(getString(R.string.feedback));
                    break;
            }
            binding.tab.getTabAt(i).setCustomView(linearLayoutOne);
        }
    }
    private void setupViewPager(ViewPager viewpager) {
        binding.btnAddQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.tab.getSelectedTabPosition() == 2) {
                    Bundle feedbckbundle = new Bundle();
                    String model = getArguments().getString("model","");
                    feedbckbundle.putString("model", model);
                    feedbckbundle.putInt("fragmentId",1);
                    navController.navigate(R.id.action_queryFragment_to_addFarmerqurie_Fragment,feedbckbundle);
                }else if (binding.tab.getSelectedTabPosition()==3){
                    Bundle feedbckbundle = new Bundle();
                    String model = getArguments().getString("model","");
                    feedbckbundle.putString("model", model);
                    navController.navigate(R.id.action_queryFragment_to_addFeedbackFragment,feedbckbundle);
                }
            }
        });
    }

    public void select(int pageindex){
        vpAdapter.addFragment( SearchContentDetailsFragment.newInstance(bundle),getResources().getString(R.string.content));
        vpAdapter.addFragment( ContentInfoFragment.newInstance(bundle),getResources().getString(R.string.contentInfo));
        Bundle bundle1= new Bundle();
        bundle1.putInt("fragmentId",1);
        bundle1.putString("query","contentQuery");
        bundle1.putString("queryModule","common");
        vpAdapter.addFragment( QueryFragment.newInstance(bundle1),getResources().getString(R.string.query));
        vpAdapter.addFragment( FeedbackList_Fragment.newInstance(bundle),getResources().getString(R.string.feedback));
        binding.viewpager.setAdapter(vpAdapter);
        setupViewPager(binding.viewpager);
        binding.viewpager.setCurrentItem(pageindex);

    }

}

