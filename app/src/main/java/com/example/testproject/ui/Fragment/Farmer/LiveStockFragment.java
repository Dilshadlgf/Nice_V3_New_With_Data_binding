package com.example.testproject.ui.Fragment.Farmer;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.databinding.FragmentFarmerLivestockBinding;
import com.example.testproject.model.RootOneResModel;
import com.example.testproject.model.livestock.LiveStockDataModel;

import java.util.List;

public class LiveStockFragment extends BaseFragment{

    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private FragmentFarmerLivestockBinding binding;

    public static LiveStockFragment newInstance(Bundle args) {
        LiveStockFragment fragment = new LiveStockFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        layoutId = R.layout.fragment_farmer_livestock;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
            binding= (FragmentFarmerLivestockBinding) viewDataBinding;
    }

    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
                showDialog(getActivity(), errorCode, false, true, 0);

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if (ServiceCode == AppConstants.CommodityFilterReq) {
                    RootOneResModel rootOneResModel= (RootOneResModel) response;
                    modelListLiveStockName=rootOneResModel.getResponse().getData().getLiveStockCategory();
                    if(modelListLiveStockName!=null && modelListLiveStockName.size()>0){
                        makeTopLiveStockCard();
                        callLiveStockApi(modelListLiveStockName.get(0).getId());
                    }else {
                        binding.farmerRecycler.setVisibility(View.GONE);
                        binding.txtEmpty.setVisibility(View.VISIBLE);
                    }
                }else  if(ServiceCode== AppConstants.LiveSTOCKREQUEST){
                    RootOneResModel rootOneResModel= (RootOneResModel) response;
                    List<LiveStockDataModel> mList=rootOneResModel.getResponse().getData().getFarmerLiveStock();
                    if(mList!=null && mList.size()>0) {
                        FarmerLivastockAdapter adapter = new FarmerLivastockAdapter(getActivity(), mList, FarmerLiveStock_Fragment.this);
                        binding.farmerRecycler.setHasFixedSize(true);
                        binding.farmerRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                        binding.farmerRecycler.setAdapter(adapter);
                        binding.farmerRecycler.setVisibility(View.VISIBLE);
                        binding.txtEmpty.setVisibility(View.GONE);
                    }else {
                        binding.farmerRecycler.setVisibility(View.GONE);
                        binding.txtEmpty.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
        mApiManager = new ApiManager(getActivity(), mInterFace);
    }
}
