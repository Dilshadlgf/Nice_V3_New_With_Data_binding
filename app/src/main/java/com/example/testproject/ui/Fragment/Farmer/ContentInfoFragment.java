package com.example.testproject.ui.Fragment.Farmer;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.databinding.FragmentContentInfoBinding;
import com.example.testproject.model.QueryRef;
import com.example.testproject.model.RootOneResModel;
import com.example.testproject.model.SearchContentResponseDataModel;    


/**
 * Created by Suraj  on 07-07-2022.
 */

public class ContentInfoFragment extends BaseFragment {
    private FragmentContentInfoBinding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private SearchContentResponseDataModel responseDataModel;
    private FarmerDao farmerDao;

    public static Fragment newInstance(Bundle infoBundle) {
        return null;
    }


    @Override
    protected void init() {

        layoutId = R.layout.fragment_content_info;

    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

        binding = (FragmentContentInfoBinding) viewDataBinding;
        setUpNetWork();
        farmerDao= AppDatabase.getInstance((getContext())).farmerDao();

        mApiManager.searchContentsListDetailRequest(farmerDao.getFarmer().getId());
    }

    private void setUpNetWork() {

        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
                showDialog(getActivity(), errorCode, false, true, 0);
            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if (ServiceCode == AppConstants.SEARCH_CONTENT_LIST_Detail_REQUEST) {
                    RootOneResModel rootOneResModel = (RootOneResModel) response;
                    QueryRef Q2 = rootOneResModel.getResponse().getDataModel2().getContent().getQueryRef();

                    binding.setMydata(Q2);
                }
            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }
}