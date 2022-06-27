package com.example.testproject.ui.Fragment.Farmer;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
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
import com.example.testproject.databinding.FragmentCropDone1Binding;
import com.example.testproject.interfaces.ListItemClickListener;
import com.example.testproject.interfaces.QueryListClickListner;
import com.example.testproject.interfaces.ResolutionClickListener;
import com.example.testproject.model.CropDataModel;
import com.example.testproject.model.LivestocksArrayModel;
import com.example.testproject.model.RootOneResModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CropDONEFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CropDONEFragment extends BaseFragment implements View.OnClickListener, ListItemClickListener {
    private static final String TAG = "DoneFragment";
    //    private QueryAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    public FragmentCropDone1Binding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
//    private LoginDao loginDao;
//    private VillageDao villageDao;
//    private QueriesDao queriesDao;
//    private QueriesResponseModel queryofflinrmodel;
    private QueryListClickListner queryListClickListner;
    private ResolutionClickListener resolutionClickListener;
//    private QueriesResponseDataModel queriesResponseDataModel;
    private String queryType = "";
    String subdomain, kndonain;
    SharedPreferences userrolesh;
    SharedPreferences pref;
    String farmerrole,user_role;
    private List<CropDataModel> croplist;
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
//        ((FragmentActivity) getActivity()).mBack.setVisibility(View.VISIBLE);
//        user_role = SharedPreferenceHelper.getSharedPreferenceString(getContext(), "user_role", "");

        /*  ((FragmentActivity) getActivity()).setmBack("Back");*/
        userrolesh = getActivity().getSharedPreferences("userrole", 0);
        pref = getActivity().getSharedPreferences("mhh", MODE_PRIVATE);
        if (pref != null) {
            farmerrole = pref.getString("role", "");
        }



        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.doneRecycler.setLayoutManager(linearLayoutManager);
//        FarmerCrops_Done_Win_Adapter adapter=new FarmerCrops_Done_Win_Adapter(croplist,getContext());
//        binding.doneRecycler.setAdapter(adapter);
//        binding.queryRecycler.setLayoutManager(linearLayoutManager);
//        binding.queryRecycler.setItemAnimator(new DefaultItemAnimator());

//        if (getArguments() != null) {
//            queryType = getArguments().getString("query");
//            if(queryType.equalsIgnoreCase("unpicked")){
//
//            }
//        }
//
//        loginDao = AppDatabase.getInstance(getContext()).loginDetails();
//        villageDao = AppDatabase.getInstance(getContext()).getVillageDBModelResponse();
//        queriesDao = AppDatabase.getInstance(getContext()).queriesResponseModel();
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
                    RootOneResModel rootOneResModel= (RootOneResModel) response;
                    modelListLiveStockName=rootOneResModel.getResponse().getDataModel2().getLiveStockCategory();
                    if(modelListLiveStockName!=null && modelListLiveStockName.size()>0){
                        makeTopLiveStockCard();
                        callDONEApi(modelListLiveStockName.get(0).getId());
                    }else {
                        binding.doneRecycler.setVisibility(View.GONE);
                        binding.txtEmpty.setVisibility(View.VISIBLE);
                    }
                }else  if(ServiceCode==AppConstants.FARMER_DETAILS_REQUEST) {
                    RootOneResModel rootOneResModel = (RootOneResModel) response;

                    List<CropDataModel> cropDataModels = rootOneResModel.getResponse().getDataModel2().getFarmerCrop();
                    if(cropDataModels!=null && cropDataModels.size()>0) {
                        binding.doneRecycler.setAdapter(new FarmerCrops_Done_Win_Adapter(cropDataModels, CropDONEFragment.this, getContext(), false));
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
        object.addProperty("farmer","628cc9e2a1e0bfbb4b7e3e8b");
        mApiManager.farmerCropDetaile(object);
    }

    private void makeTopLiveStockCard(){
        binding.catLayout.removeAllViews();
        for (int i = 0; i < modelListLiveStockName.size(); i++) {
            View v=getActivity().getLayoutInflater().inflate(R.layout.live_stock_card,null);
            ((TextView)v.findViewById(R.id.tv_name)).setText(modelListLiveStockName.get(i).getName());
            Picasso.with(getContext()).load(ApiClient.BASE_URL+modelListLiveStockName.get(i).getIcon()).into(((ImageView)v.findViewById(R.id.iv_icon)));
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
        callDONEApi(c);
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