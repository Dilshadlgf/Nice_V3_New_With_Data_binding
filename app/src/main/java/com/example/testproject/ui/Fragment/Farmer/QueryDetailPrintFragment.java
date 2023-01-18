package com.example.testproject.ui.Fragment.Farmer;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.testproject.Network.ApiClient;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.database.Dao.RoleDao;
import com.example.testproject.databinding.QuerydetailprintFragmentBinding;
import com.example.testproject.model.DataModel;
import com.example.testproject.model.FarmerDataModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.model.query.QueryResponseDataNumModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;


import java.util.List;


public class QueryDetailPrintFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "QueryDetail";
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private QuerydetailprintFragmentBinding binding;
    private boolean isFarmerLogin;
    private FarmerDao farmerDao;
    private RoleDao roleDao;
    private QueryResponseDataNumModel responseDataModel;

    public static QueryDetailPrintFragment newInstance(Bundle args) {
        QueryDetailPrintFragment fragment = new QueryDetailPrintFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        layoutId = R.layout.querydetailprint_fragment;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        setupNetwork();
        binding = (QuerydetailprintFragmentBinding) viewDataBinding;
        farmerDao = AppDatabase.getInstance(getContext()).farmerDao();
        roleDao=AppDatabase.getInstance(getContext()).roleDao();
        if(farmerDao!=null && farmerDao.getFarmer()!=null){
            isFarmerLogin=roleDao.getRole().isFarmer();
        }
        if(!isFarmerLogin){

        }
        if (getArguments() != null) {

            boolean hitApi= getArguments().getBoolean("callFromOut",false);
            String queryUId=getArguments().getString("QueryUId");
            String id=getArguments().getString("id");
            String title="Q Id:"+queryUId;
            String d=getArguments().getString("model");
            if(d!=null)
                 responseDataModel= (QueryResponseDataNumModel) CommonUtils.jsonToPojo(getArguments().getString("model"), QueryResponseDataNumModel.class);
                binding.setMydata(responseDataModel);



        }
    }

    private void createAndShowImages(List<String> list){
        if(list!=null && list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                ImageView imageView=new ImageView(getActivity());
                Picasso.get().load(ApiClient.BASE_URL+list.get(i))
                        .into(imageView);
                assert binding.imageContainer != null;
                binding.imageContainer.addView(imageView);
            }

        }
    }
    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
                showDialog(getActivity(), errorCode, false, true, 0);

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if(ServiceCode== AppConstants.QUERIES_LIST_REQUEST){
                    RootOneModel rootOneModel= (RootOneModel) response;
                    DataModel model=rootOneModel.getResponse().getData();
//                    binding.setMydata(model);
                    if(model!=null) {
                      //  fillData(true, model);
                    }else {
                        Toast.makeText(getActivity(),"error found: try later",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }
    @Override
    public void onClick(View v) {
    }

}


