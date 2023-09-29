package com.example.testproject.ui.fragment.Farmer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testproject.Adapter.FarmerLivastockAdapter;
import com.example.testproject.BuildConfig;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.util.AppConstants;
import com.example.testproject.util.JsonMyUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.CrpLivestocklistFragmentBinding;
import com.example.testproject.interfaces.ListItemClickListener;
import com.example.testproject.model.LivestockModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.ui.Activity.farmer.FarmerMainActivity;
import com.example.testproject.ui.base.BaseFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Suraj on 27-06-2022.
 */
public class FarmerLiveStock_Fragment extends BaseFragment implements View.OnClickListener, ListItemClickListener {
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
  //  private LoginDao loginDao;
  //  private FarmerListDao farmerDao;
    private CrpLivestocklistFragmentBinding binding;
    String farmerrole;
    Spinner splivestock, spvariety, spstages, spcropp, spvarietycrop, spseason, spareaunit, spirrigation;
    EditText Quantity, startdate, area;
    String selectedvarirtytxt, selectedlivestocktxt, cropid;
    private HashMap<Integer, String> spinnerlivestockMap;
    private HashMap<Integer, String> spinnervarietytMap;
    private HashMap<Integer, String> spinnerstagesMap;
    private HashMap<Integer, String> spinnercropMap;
    SharedPreferences pres;
    private int selectedIndex;
    private UserDao userDao;
    NavController navController ;
    private List<LivestockModel> livestockModels;


    public static FarmerLiveStock_Fragment newInstance(Bundle args) {
        FarmerLiveStock_Fragment fragment = new FarmerLiveStock_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        layoutId = R.layout.crp_livestocklist_fragment;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (CrpLivestocklistFragmentBinding) viewDataBinding;
        navController= NavHostFragment.findNavController(this);
        ((FarmerMainActivity) getActivity()).setTittle("Farmer Live Stock");
        ((FarmerMainActivity) getActivity()).showHideEditIcon(false);
        setupNetwork();
        userDao = AppDatabase.getInstance(getContext()).userdao();

        pres = getActivity().getSharedPreferences("mhh", MODE_PRIVATE);
//
        binding.edtLivestocklinearLyout.setOnClickListener(this);

        {
            JsonObject mainObj=new JsonObject();
            JsonArray statusArr=new JsonArray();
            statusArr.add("Active");
            JsonArray fid=new JsonArray();
            fid.add(userDao.getUserResponse().id);
            JsonArray lstock=new JsonArray();
            lstock.add("Livestock");
            mainObj.add("status",statusArr);
            mainObj.add("classification",lstock);
//            mainObj.add("farmer",fid);
            mApiManager.commodityCategoryFilter(mainObj);

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
                RootOneModel rootOneModel= (RootOneModel) response;
                if (ServiceCode == AppConstants.CommodityFilterReq) {
                    if (rootOneModel.getResponse().getData().liveStockCategory!=null){
                        livestockModels = JsonMyUtils.getPojoFromJsonArr(rootOneModel.getResponse().getData().liveStockCategory.getAsJsonArray(),LivestockModel.class);
                        if(livestockModels!=null && livestockModels.size()>0){
                            makeTopLiveStockCard();
                            callLiveStockApi(livestockModels.get(0).id);
                        }else {
                            binding.farmerRecycler.setVisibility(View.GONE);
                            binding.txtEmpty.setVisibility(View.VISIBLE);
                        }
                    }

                }else  if(ServiceCode==AppConstants.LiveSTOCKREQUEST){
                    if (rootOneModel.getResponse().getData().farmerLiveStock!=null){
                        List<LivestockModel> livestockModels =JsonMyUtils.getPojoFromJsonArr(rootOneModel.getResponse().getData().farmerLiveStock.getAsJsonArray(),LivestockModel.class);
                        if(livestockModels!=null && livestockModels.size()>0) {
                            FarmerLivastockAdapter adapter = new FarmerLivastockAdapter(getActivity(), livestockModels, FarmerLiveStock_Fragment.this);
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
            }
        };
        mApiManager = new ApiManager(getActivity(), mInterFace);
    }
    private void callLiveStockApi(String catId){
        JsonObject mainObj=new JsonObject();
        JsonArray statusArr=new JsonArray();
        statusArr.add("Active");
        JsonArray fid=new JsonArray();
        fid.add(userDao.getUserResponse().id);
        JsonArray lstock=new JsonArray();
        lstock.add(catId);
        mainObj.add("status", statusArr);
        mainObj.add("farmer", fid);
        mainObj.add("category", lstock);
        mApiManager.LiveStockRequest(mainObj);
    }
    private void makeTopLiveStockCard(){
        binding.bottomlayout.removeAllViews();
        for (int i = 0; i < livestockModels.size(); i++) {
            View v=getActivity().getLayoutInflater().inflate(R.layout.live_stock_card,null);
            ((TextView)v.findViewById(R.id.tv_name)).setText(livestockModels.get(i).name);
            Picasso.get().load(BuildConfig.BASE_URL+livestockModels.get(i).icon).placeholder(R.drawable.livestock).into(((ImageView)v.findViewById(R.id.iv_icon)));
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
                    selectedIndex=index;
                    btnHandler(index);
                }
            });
            binding.bottomlayout.addView(v);
        }
    }
    private void btnHandler(int index){
        String c=livestockModels.get(index).id;
        callLiveStockApi(c);
        for (int i = 0; i < binding.bottomlayout.getChildCount(); i++) {
            if(i==index){
                ((ViewGroup)binding.bottomlayout.getChildAt(i)).setBackgroundResource(R.drawable.custom_card_selcted);
            }else {
                ((ViewGroup)binding.bottomlayout.getChildAt(i)).setBackgroundResource(R.drawable.custom_card);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edt_livestocklinear_lyout:
                Bundle bundlecrop = new Bundle();
                bundlecrop.putString("farmerid", pres.getString("id", ""));
                navController.navigate(R.id.action_farmerLiveStock_Fragment_to_addLiveStock_UpdateFragment);
                break;
        }
    }

    @Override
    public void onItemClick(int position, String id) {
        if(position<livestockModels.size()) {
            btnHandler(position);
        }else{
            btnHandler(selectedIndex);
        }

    }

    @Override
    public void onBackCustom() {
        navController.navigate(R.id.profileFragment);
    }
}



