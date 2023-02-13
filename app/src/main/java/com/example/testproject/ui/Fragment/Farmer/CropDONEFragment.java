package com.example.testproject.ui.Fragment.Farmer;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testproject.Adapter.FarmerCrops_Done_Win_Adapter;
import com.example.testproject.Network.ApiClient;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.JsonMyUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.FragmentCropDone1Binding;
import com.example.testproject.interfaces.ListItemClickListener;
import com.example.testproject.interfaces.QueryListClickListner;
import com.example.testproject.interfaces.ResolutionClickListener;

import com.example.testproject.model.CropModel;
import com.example.testproject.model.LivestockModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.ui.base.BaseFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
     Created by suraj singh rajput on 30-01-2023
 */
public class CropDONEFragment extends BaseFragment implements View.OnClickListener, ListItemClickListener {
    private static final String TAG = "DoneFragment";
    //    private QueryAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    public FragmentCropDone1Binding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;

    private QueryListClickListner queryListClickListner;
    private ResolutionClickListener resolutionClickListener;
//    private QueriesResponseDataModel queriesResponseDataModel;
    private String queryType = "";
    String subdomain, kndonain;
    SharedPreferences userrolesh;
    SharedPreferences pref;
    String farmerrole,user_role;
    private List<CropModel> cropModels;
    List<LivestockModel> modelListLiveStockName;
    private int selectedCatindex;
    private UserDao userDao;
    private NavController navController;
    public static CropDONEFragment newInstance(Bundle args) {
        CropDONEFragment fragment = new CropDONEFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        layoutId = R.layout.fragment_crop_done1;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (FragmentCropDone1Binding) viewDataBinding;
        userDao= AppDatabase.getInstance((getContext())).userdao();
        navController= NavHostFragment.findNavController(this);
        userrolesh = getActivity().getSharedPreferences("userrole", 0);
        pref = getActivity().getSharedPreferences("mhh", MODE_PRIVATE);
        if (pref != null) {
            farmerrole = pref.getString("role", "");
        }



        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.doneRecycler.setLayoutManager(linearLayoutManager);
        setupNetwork();

        JsonObject mainObj=new JsonObject();
        JsonArray statusArr=new JsonArray();
        statusArr.add("Active");
        JsonArray lstock=new JsonArray();
        lstock.add("Crop");
        mainObj.add("status",statusArr);
        mainObj.add("classification",lstock);
        mApiManager.commodityCategoryFilter(mainObj);

    }
    @Override
    public void onClick(View v) {
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
                    RootOneModel rootOneModel= (RootOneModel) response;
                    if (rootOneModel.getResponse().getData().data!=null){
                        modelListLiveStockName= JsonMyUtils.getPojoFromJsonArr(rootOneModel.getResponse().getData().data.getAsJsonArray(),LivestockModel.class);
                        if(modelListLiveStockName!=null && modelListLiveStockName.size()>0){
                            makeTopLiveStockCard();
                            callDONEApi(modelListLiveStockName.get(0).id);
                        }else {
                            binding.doneRecycler.setVisibility(View.GONE);
                            binding.txtEmpty.setVisibility(View.VISIBLE);
                        }
                    }

                }else  if(ServiceCode==AppConstants.FARMER_DETAILS_REQUEST) {
                    RootOneModel rootOneModel = (RootOneModel) response;
                    if (rootOneModel.getResponse().getData().data!=null){
                        cropModels= JsonMyUtils.getPojoFromJsonArr(rootOneModel.getResponse().getData().data.getAsJsonArray(),CropModel.class);

                    }
                    if(cropModels!=null && cropModels.size()>0) {
                        binding.doneRecycler.setAdapter(new FarmerCrops_Done_Win_Adapter(cropModels, CropDONEFragment.this, getContext(), false));
                        binding.doneRecycler.setVisibility(View.VISIBLE);
                        binding.txtEmpty.setVisibility(View.GONE);
                    }else {
                        binding.doneRecycler.setVisibility(View.GONE);
                        binding.txtEmpty.setVisibility(View.VISIBLE);
                    }
                }


            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }

    private void callDONEApi(String catId){
        JsonObject object=new JsonObject();
        JsonArray array=new JsonArray();
        array.add("DONE");
        JsonArray cat=new JsonArray();
        cat.add(catId);

        object.add("status", array);
//        object.add("category", cat);
//        object.addProperty("farmer",loginDao.getLoginResponse().getId());
        object.addProperty("farmer",userDao.getUserResponse().id);
        mApiManager.farmerCropDetaile(object);
    }

    private void makeTopLiveStockCard(){
        binding.catLayout.removeAllViews();
        for (int i = 0; i < modelListLiveStockName.size(); i++) {
            View v=getActivity().getLayoutInflater().inflate(R.layout.live_stock_card,null);
            ((TextView)v.findViewById(R.id.tv_name)).setText(modelListLiveStockName.get(i).name);
            Picasso.get().load(ApiClient.BASE_URL+modelListLiveStockName.get(i).icon).placeholder(R.drawable.vegetablecrop).into(((ImageView)v.findViewById(R.id.iv_icon)));
            v.setTag(i);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams((int)getResources().getDimension(com.intuit.sdp.R.dimen._120sdp), (int)getResources().getDimension(com.intuit.sdp.R.dimen._50sdp));
            layoutParams.leftMargin=3;
            layoutParams.rightMargin=3;
            if(i==0){
                ((ViewGroup)v).setBackgroundResource(R.drawable.custom_card_selcted);
            }else {
                ((ViewGroup)v).setBackgroundResource(R.drawable.custom_card);
            }

            v.setLayoutParams(layoutParams);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index= (int) v.getTag();
                    selectedCatindex=index;
                    catBtnClick(index);
                }
            });
            binding.catLayout.addView(v);
        }
    }
    private void catBtnClick(int index){
        String c=modelListLiveStockName.get(index).id;
        callDONEApi(c);
        for (int i = 0; i < binding.catLayout.getChildCount(); i++) {
            if(i==index){
                ((ViewGroup)binding.catLayout.getChildAt(i)).setBackgroundResource(R.drawable.custom_card_selcted);
            }else {
                ((ViewGroup)binding.catLayout.getChildAt(i)).setBackgroundResource(R.drawable.custom_card);
            }
        }
    }

    @Override
    public void onItemClick(int position, String id) {
        if(position<modelListLiveStockName.size()) {
            catBtnClick(position);
        }else{
            catBtnClick(selectedCatindex);
        }
    }

    @Override
    public void onBackCustom() {
        navController.navigate(R.id.profileFragment);
    }
}