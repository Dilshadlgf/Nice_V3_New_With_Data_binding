package com.example.testproject.ui.Fragment.Farmer;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testproject.Adapter.QueryAdapter;
import com.example.testproject.Adapter.VpAdapter;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.databinding.FragmentAllQueryFragmnetBinding;
import com.example.testproject.databinding.FragmentQuery2Binding;
import com.example.testproject.model.query.QueryResponseDataNumModel;
import com.example.testproject.model.query.RootQueryModel;
import com.example.testproject.ui.Activity.FarmerMainActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;


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