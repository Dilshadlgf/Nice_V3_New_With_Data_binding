package com.example.testproject.ui.fragment.Farmer;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.util.AppConstants;
import com.example.testproject.util.CommonUtils;
import com.example.testproject.util.JsonMyUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.FragmentContentInfoBinding;
import com.example.testproject.model.ContentModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.ui.base.BaseFragment;


/**
 * Created by Suraj  on 07-07-2022.
 */

public class ContentInfoFragment extends BaseFragment {
    private FragmentContentInfoBinding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private UserDao userDao;
    private String modelJson;

    private NavController navController;
    public static ContentInfoFragment newInstance(Bundle bundle) {
        ContentInfoFragment fragment = new ContentInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void init() {

        layoutId = R.layout.fragment_content_info;

    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

        binding = (FragmentContentInfoBinding) viewDataBinding;
        setUpNetWork();
        navController= NavHostFragment.findNavController(this);
        userDao= AppDatabase.getInstance((getContext())).userdao();
        modelJson = getArguments().getString("model","");
        ContentModel contentmodel = (ContentModel) CommonUtils.jsonToPojo(modelJson,ContentModel.class);
        mApiManager.searchContentsListDetailRequest(contentmodel.id);
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
                    RootOneModel rootOneModel = (RootOneModel) response;
                    if (rootOneModel.getResponse().getData().content!=null){
                        ContentModel contentModel = (ContentModel) JsonMyUtils.getPojoFromJsonObj(ContentModel.class,rootOneModel.getResponse().getData().content.getAsJsonObject());
                        binding.setMydata(contentModel.ref);

                    }

                }
            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }

    @Override
    public void onBackCustom() {
        navController.navigate(R.id.contentFragment);
    }
}