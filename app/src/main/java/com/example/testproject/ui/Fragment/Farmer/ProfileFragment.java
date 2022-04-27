package com.example.testproject.ui.Fragment.Farmer;

import android.os.Bundle;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testproject.Adapter.SearchContentAdapter;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.databinding.FragmentProfileBinding;
import com.example.testproject.model.Data;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.ui.Fragment.Farmer.BaseFragment;

import retrofit2.Call;

public class ProfileFragment extends BaseFragment {


    private RootOneModel rootOneModel;
    private SearchContentAdapter adapter;
    private ApiResponseInterface mInterFace;
    private Call<RootOneModel> call;
    private ApiManager mApiManager;


    private FragmentProfileBinding binding;

    @Override

    protected void init() {

        layoutId= R.layout.fragment_profile;

    }


    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

        binding = (FragmentProfileBinding) viewDataBinding;
        setUpNetWork();

        mApiManager.getProfile("621758b11daffc762c720138");

    }

    private void setUpNetWork() {

        mInterFace = new ApiResponseInterface() {



            @Override
            public void isError(String errorCode) {

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {

                RootOneModel rootOneModel=(RootOneModel)response;
                Data d2 = rootOneModel.getResponse().getData().getData();

                binding.setMydata(d2);


            }
        };
        mApiManager = new ApiManager(getActivity(), mInterFace);


    }

    }