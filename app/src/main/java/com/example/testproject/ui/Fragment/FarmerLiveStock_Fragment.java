package com.example.testproject.ui.Fragment;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testproject.Adapter.FarmerLivastockAdapter;
import com.example.testproject.BuildConfig;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.database.Dao.RoleDao;
import com.example.testproject.databinding.CrpLivestocklistFragmentBinding;
import com.example.testproject.interfaces.ListItemClickListener;
import com.example.testproject.model.LivestocksArrayModel;
import com.example.testproject.model.RootOneResModel;
import com.example.testproject.ui.Fragment.Farmer.BaseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
    ArrayList<String> staticvarietylist;
    ArrayList<String> staticvarietyidlist;
    LivestocksArrayModel livestockmodel;
    String selectedareaUnit, selectedirrigation, varietyid;
    SharedPreferences pref;
    BottomNavigationView btmview;
    SharedPreferences pres;
    List<LivestocksArrayModel> modelListCatVise=new ArrayList<>();
    List<LivestocksArrayModel> modelListLiveStockName=new ArrayList<>();
    private int selectedIndex;
    private FarmerDao farmerdao;
    private RoleDao roleDao;




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
        setupNetwork();
        farmerdao = AppDatabase.getInstance(getContext()).farmerDao();
//        loginDao = AppDatabase.getInstance(getContext()).loginDetails();

        pres = getActivity().getSharedPreferences("mhh", MODE_PRIVATE);
//        ((FragmentActivity) getActivity()).setScreenTitle("Livestocks Detail List");
//        ((FragmentActivity) getActivity()).mBack.setVisibility(View.VISIBLE);
      //  btmview = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation);
        binding.edtLivestocklinearLyout.setOnClickListener(this);
       // ((FragmentActivity) getActivity()).btnPrint.setVisibility(View.INVISIBLE);
//        btmview.setVisibility(View.GONE);

//        ((FragmentActivity) getActivity()).mBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (getActivity() != null) {
//                    getFragmentManager().popBackStack();
//                    ((FragmentActivity) getActivity()).mBack.setVisibility(View.GONE);
//                }
//            }
//        });
        {
            JsonObject mainObj=new JsonObject();
            JsonArray statusArr=new JsonArray();
            statusArr.add("Active");
            JsonArray fid=new JsonArray();
            fid.add(farmerdao.getFarmer().getId());
            JsonArray lstock=new JsonArray();
            lstock.add("Livestock");
            mainObj.add("status",statusArr);
            mainObj.add("classification",lstock);
//            mainObj.add("farmer",fid);
            mApiManager.commodityCategoryFilter(mainObj);

        }

    }

//    @Override
//    public void onBackCustom() {
//        ((FragmentActivity) getActivity()).btnPrint.setVisibility(View.INVISIBLE);
//        ((FragmentActivity) getActivity()).mBack.setVisibility(View.INVISIBLE);
//        getFragmentManager().popBackStack();
//    }


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
                        callLiveStockApi(modelListLiveStockName.get(0).getId());
                    }else {
                        binding.farmerRecycler.setVisibility(View.GONE);
                        binding.txtEmpty.setVisibility(View.VISIBLE);
                    }
                }else  if(ServiceCode==AppConstants.LiveSTOCKREQUEST){
                    RootOneResModel rootOneResModel= (RootOneResModel) response;
                    List<LivestocksArrayModel> mList=rootOneResModel.getResponse().getDataModel2().getFarmerLiveStock();
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
    private void callLiveStockApi(String catId){
        JsonObject mainObj=new JsonObject();
        JsonArray statusArr=new JsonArray();
        statusArr.add("Active");
        JsonArray fid=new JsonArray();
        fid.add(farmerdao.getFarmer().getId());
        JsonArray lstock=new JsonArray();
        lstock.add(catId);
        mainObj.add("status", statusArr);
        mainObj.add("farmer", fid);
        mainObj.add("category", lstock);
        mApiManager.LiveStockRequest(mainObj);
    }
    private void makeNewRecyclerAdaptor(String catid){
//        modelListCatVise.clear();
//
//        for (int i = 0; i <modelList.size() ; i++) {
//            if(modelList.get(i).getRef().getLiveStock().getCategory().equals(catid)){
//                modelListCatVise.add(modelList.get(i));
//            }
//        }
//        if(modelListCatVise.size()==0){
//            binding.farmerRecycler.setVisibility(View.GONE);
//            binding.txtEmpty.setVisibility(View.VISIBLE);
//        }else {
//            adapter.setnewlist(modelListCatVise);
//        }
    }
    private void makeTopLiveStockCard(){
        binding.bottomlayout.removeAllViews();
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
                    selectedIndex=index;
                    btnHandler(index);
                }
            });
            binding.bottomlayout.addView(v);
        }
    }
    private void btnHandler(int index){
        String c=modelListLiveStockName.get(index).getId();
        callLiveStockApi(c);
        for (int i = 0; i < binding.bottomlayout.getChildCount(); i++) {
            if(i==index){
                ((ViewGroup)binding.bottomlayout.getChildAt(i)).setBackgroundResource(R.drawable.activecom);
            }else {
                ((ViewGroup)binding.bottomlayout.getChildAt(i)).setBackgroundResource(R.drawable.deactivecom);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edt_livestocklinear_lyout:
                Bundle bundlecrop = new Bundle();
                bundlecrop.putString("farmerid", pres.getString("id", ""));
              //  CustomFragmentManager.replaceFragment(getFragmentManager(), AddLiveStock_UpdateFragment.newInstance(bundlecrop), true);
                break;
        }
    }

    @Override
    public void onItemClick(int position, String id) {
        if(position<modelListLiveStockName.size()) {
            btnHandler(position);
        }else{
            btnHandler(selectedIndex);
        }

    }
}



