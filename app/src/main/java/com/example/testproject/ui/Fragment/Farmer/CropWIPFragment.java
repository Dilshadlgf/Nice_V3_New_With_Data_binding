package com.example.testproject.ui.Fragment.Farmer;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testproject.R;
import com.example.testproject.databinding.FragmentCropWipBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nice.BuildConfig;
import com.nice.R;
import com.nice.adapter.FarmerCrops_Done_Win_Adapter;
import com.nice.database.AppDatabase;
import com.nice.database.dao.LoginDao;
import com.nice.database.dao.QueriesDao;
import com.nice.database.dao.VillageDao;
import com.nice.databinding.FragmentCropWipBinding;
import com.nice.interfaces.ListItemClickListener;
import com.nice.interfaces.QueryListClickListner;
import com.nice.interfaces.ResolutionClickListener;
import com.nice.model.CropDataModel;
import com.nice.model.LivestocksArrayModel;
import com.nice.model.QueriesResponseDataModel;
import com.nice.model.QueriesResponseModel;
import com.nice.model.rootresponsemodel.RootOneResModel;
import com.nice.network.ApiManager;
import com.nice.network.ApiResponseInterface;
import com.nice.ui.activity.FragmentActivity;
import com.nice.util.AppConstants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CropWIPFragment extends BaseFragment implements View.OnClickListener, ListItemClickListener {
    private static final String TAG = "WIPFragment";
    //    private QueryAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private FragmentCropWipBinding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private LoginDao loginDao;
    private VillageDao villageDao;
    private QueriesDao queriesDao;
    private QueriesResponseModel queryofflinrmodel;
    private QueryListClickListner queryListClickListner;
    private ResolutionClickListener resolutionClickListener;
    private QueriesResponseDataModel queriesResponseDataModel;
    private String queryType = "";
    String subdomain, kndonain;

    String farmerrole,user_role;
    private List<RootOneResModel> croplist;

    private String crop;
    private String inter_Crop;
    private String variety;
    private String seasion;
    private String area;
    private String irrigation;
    private String startdate;
    private String completeddate;
    private String yield;
    List<LivestocksArrayModel> modelListLiveStockName;
    private int selectedCatindex;


    public static CropWIPFragment newInstance(Bundle args) {
        CropWIPFragment fragment = new CropWIPFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        layoutId = R.layout.fragment_crop_wip;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (FragmentCropWipBinding) viewDataBinding;

//        setupNetwork();




        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);


        binding.wipRecycler.setLayoutManager(linearLayoutManager);
//

        loginDao = AppDatabase.getInstance(getContext()).loginDetails();

        JsonObject mainObj=new JsonObject();
        JsonArray statusArr=new JsonArray();
        statusArr.add("Active");
        JsonArray lstock=new JsonArray();
        lstock.add("Crop");
        mainObj.add("status",statusArr);
        mainObj.add("classification",lstock);
        mApiManager.commodityCategoryFilter(mainObj);


    }
    private void callWIPApi(String catId){
        JsonObject object=new JsonObject();
        JsonArray array=new JsonArray();
        array.add("WIP");
        JsonArray cat=new JsonArray();
        cat.add(catId);

        object.add("status", array);
        object.add("category", cat);
        object.addProperty("farmer",loginDao.getLoginResponse().getId());

        mApiManager.farmerCropDetaile(object);
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
                    RootOneResModel rootOneResModel= (RootOneResModel) response;
                    modelListLiveStockName=rootOneResModel.getResponse().getData().getLiveStockCategory();
                    if(modelListLiveStockName!=null && modelListLiveStockName.size()>0){
                        makeTopLiveStockCard();
                        callWIPApi(modelListLiveStockName.get(0).getId());
                    }else {
                        binding.wipRecycler.setVisibility(View.GONE);
                        binding.txtEmpty.setVisibility(View.VISIBLE);
                    }
                }else  if(ServiceCode==AppConstants.FARMER_DETAILS_REQUEST) {
                    RootOneResModel rootOneResModel = (RootOneResModel) response;

                    List<CropDataModel> cropDataModels = rootOneResModel.getResponse().getData().getFarmerCrop();
                    if(cropDataModels!=null && cropDataModels.size()>0) {

                        binding.wipRecycler.setAdapter(new FarmerCrops_Done_Win_Adapter(cropDataModels, CropWIPFragment.this, getContext(), true));
                        binding.wipRecycler.setVisibility(View.VISIBLE);
                        binding.txtEmpty.setVisibility(View.GONE);
                    }else {
                        binding.wipRecycler.setVisibility(View.GONE);
                        binding.txtEmpty.setVisibility(View.VISIBLE);
                    }
                }

            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }
    private void makeTopLiveStockCard(){
        binding.catLayout.removeAllViews();
        for (int i = 0; i < modelListLiveStockName.size(); i++) {
            View v=getActivity().getLayoutInflater().inflate(R.layout.live_stock_card,null);
            ((TextView)v.findViewById(R.id.tv_name)).setText(modelListLiveStockName.get(i).getName());
            Picasso.with(getContext()).load(BuildConfig.BASE_URL+modelListLiveStockName.get(i).getIcon()).into(((ImageView)v.findViewById(R.id.iv_icon)));
            v.setTag(i);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams((int)getResources().getDimension(R.dimen._70sdp), (int)getResources().getDimension(R.dimen._70sdp));
            layoutParams.leftMargin=3;
            layoutParams.rightMargin=3;
            if(i==0){
                ((ViewGroup)v).setBackgroundResource(R.drawable.activecom);
            }else {
                ((ViewGroup)v).setBackgroundResource(R.drawable.deactivecom);
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
        String c=modelListLiveStockName.get(index).getId();
        callWIPApi(c);
        for (int i = 0; i < binding.catLayout.getChildCount(); i++) {
            if(i==index){
                ((ViewGroup)binding.catLayout.getChildAt(i)).setBackgroundResource(R.drawable.activecom);
            }else {
                ((ViewGroup)binding.catLayout.getChildAt(i)).setBackgroundResource(R.drawable.deactivecom);
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
}