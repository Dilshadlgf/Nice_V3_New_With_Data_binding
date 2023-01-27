package com.example.testproject.ui.Fragment.Farmer;

import androidx.databinding.ViewDataBinding;

import android.view.View;

import com.example.testproject.Adapter.QueryAdapter;
import com.example.testproject.Adapter.VpAdapter;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.databinding.FragmentAllQueryFragmnetBinding;
import com.example.testproject.model.query.QueryResponseDataNumModel;
import com.example.testproject.model.query.RootQueryModel;
import com.example.testproject.ui.Activity.FarmerMainActivity;
import com.example.testproject.ui.base.BaseFragment;


public class AllQueryFragmnet extends BaseFragment {

    private   FragmentAllQueryFragmnetBinding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private String queryType = "",queryModule="";
    private QueryAdapter adapter;
    private boolean positionChanged;
    int mPosition;
    int maxLimit=1;
    private int pageNo=1,maxPage=1;

    private RootQueryModel rootQueryModel;
    private QueryResponseDataNumModel queryResponseDataNumModel;



    @Override
    protected void init() {

        layoutId = R.layout.fragment_all_query_fragmnet;

    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

        binding = (FragmentAllQueryFragmnetBinding) viewDataBinding;
        binding.tab.setupWithViewPager(binding.viewpager);
        ((FarmerMainActivity) getActivity()).setTittle("Common Query");
        VpAdapter vpAdapter = new VpAdapter(getChildFragmentManager());
        vpAdapter.addFragment(new UnResolvedFragment(),"UnResolved");
        vpAdapter.addFragment(new AssignedFragment(),"Assigned");
        vpAdapter.addFragment(new ResolvedFragment(),"ResolvedFragment");
        binding.viewpager.setAdapter(vpAdapter);


    }
}