package com.example.testproject.ui.Fragment.Farmer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.databinding.ViewDataBinding;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.database.Dao.RoleDao;
import com.example.testproject.databinding.AddlivestocUpdatefragmentBinding;
import com.example.testproject.model.LivestocksArrayModel;
import com.example.testproject.model.RootOneResModel;
import com.example.testproject.model.SingleObjectModel.SingleObjRootOneResModel;
import com.example.testproject.model.stagemodel;
import com.example.testproject.model.varietymodel;
import com.example.testproject.ui.base.BaseFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suraj on 28-06-2022.
 */
public class AddLiveStock_UpdateFragment extends BaseFragment implements View.OnClickListener {
  //  FarmerDetailsModel farmerDetailsModel;
    private String errorMessage = "";
    private AddlivestocUpdatefragmentBinding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private FarmerDao farmerdao;
    private RoleDao roleDao;

    //    private LoginDao loginDao;
//    private FarmerListDao farmerDao;
    String farmerid;
    public Spinner splivestock,spvariety,spstages,spvarietycrop,spseason,spareaunit,
            spirrigation,spcrop;


    private String cntComodityId="",cntLiveStockName,cntState;
    private boolean isFirstSpSet;
    private List<String> comodityIDList=new ArrayList<>();
    private List<String> varietiresIdList=new ArrayList<>();
    private List<String> stageIdList=new ArrayList<>();

    public static AddLiveStock_UpdateFragment newInstance(Bundle args) {
        AddLiveStock_UpdateFragment fragment = new AddLiveStock_UpdateFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void init() {
        layoutId = R.layout.addlivestoc_updatefragment;
    }
    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (AddlivestocUpdatefragmentBinding) viewDataBinding;
//        ((FragmentActivity) getActivity()).enableNavigationViews(false);
//        ((FragmentActivity) getActivity()).mBack.setVisibility(View.VISIBLE);
        //((FragmentActivity) getActivity()).setScreenTitle("Add LiveStocks");
     //   ((FragmentActivity)getActivity()).setScreenTitle(getString(R.string.addlivestock));
        roleDao = AppDatabase.getInstance(getContext()).roleDao();
        farmerdao = AppDatabase.getInstance(getContext()).farmerDao();

        farmerid=farmerdao.getFarmer().getId();
        binding.quantity.setText(String.valueOf(farmerdao.getFarmer().getLiveStockCount()));


        setupNetwork();

//
//        ((FragmentActivity) getActivity()).mBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackCustom();
//            }
//        });
//        ((FragmentActivity) getActivity()).enableBottomNavigation(true);





        JsonObject mainObj=new JsonObject();
        JsonArray statusArr=new JsonArray();
        statusArr.add("Active");
        mainObj.add("status",statusArr);
        JsonArray lives=new JsonArray();
        lives.add("Livestock");
        mainObj.add("classification",lives);


        mApiManager.getLiveStockList(mainObj);

        binding.splivestocks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position>0){
                        cntComodityId=comodityIDList.get(position);
//                        cntComodityId="6219bc1fb1417e7c507e2c7c"; testing
                        callVarietyApi();
                        callStageApi();
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isvalid_AddLivestocks()){
                    try {
                        JsonObject livestockobj= new JsonObject();
                        livestockobj.addProperty("LiveStock",comodityIDList.get(binding.splivestocks.getSelectedItemPosition()));
                        livestockobj.addProperty("stage",stageIdList.get(binding.spvarieties.getSelectedItemPosition()));
                        livestockobj.addProperty("veriety",varietiresIdList.get(binding.spstages.getSelectedItemPosition()));
                        livestockobj.addProperty("quantity",Integer.parseInt(binding.quantity.getText().toString()));
                        livestockobj.addProperty("farmer", farmerid);
                        livestockobj.addProperty("activeStatus", true);
                        mApiManager.addLivestock(livestockobj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    showDialog(getActivity(), errorMessage, false, true, 0);

                }

            }
        });
       }
    private void fillData(Object obj,int apiType){

        if(apiType==1){
            List<String> list = new ArrayList<String>();

            List<LivestocksArrayModel> modelList= (List<LivestocksArrayModel>) obj;
            comodityIDList.add("");//for 0 position
            if(modelList!=null && modelList.size()>0) {
                list.add("---Select LiveStock---");
                for (int i = 0; i < modelList.size(); i++) {
                    if (modelList.get(i).getCommonName() != null) {
                        list.add(modelList.get(i).getCommonName());
                        comodityIDList.add(modelList.get(i).getId());
                    }
                }
            }else{
                list.add("---Empty---");
            }
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, list);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(R.layout.spinner_item);
            // attaching data adapter to spinner
            binding.splivestocks.setAdapter(dataAdapter);
        }else if(apiType==2){
            List<String> list2 = new ArrayList<String>();

            List<varietymodel> modelList= (List<varietymodel>) obj;
            varietiresIdList.add("");//for 0 position
            if(modelList!=null && modelList.size()>0) {
                list2.add("---Select Varieties---");
                for (int i = 0; i < modelList.size(); i++) {
                    list2.add(modelList.get(i).getName());
                    varietiresIdList.add(modelList.get(i).getId());
                }
            }else{
                list2.add("---Empty---");
            }
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, list2);
            // Drop down layout style - list view with radio button
            dataAdapter1.setDropDownViewResource(R.layout.spinner_item);
            // attaching data adapter to spinner
            binding.spvarieties.setAdapter(dataAdapter1);

        }else if(apiType==3){
            List<String> list2 = new ArrayList<String>();

            List<stagemodel> modelList= (List<stagemodel>) obj;
            stageIdList.add("");//for 0 position
            if(modelList!=null && modelList.size()>0) {
                list2.add("---Select Stage---");
                for (int i = 0; i < modelList.size(); i++) {
                    if (modelList.get(i).getName() != null) {
                        list2.add(modelList.get(i).getName());
                        stageIdList.add(modelList.get(i).getId());
                    }
                }
            }else {
                list2.add("---Empty---");
            }
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, list2);
            // Drop down layout style - list view with radio button
            dataAdapter1.setDropDownViewResource(R.layout.spinner_item);
            // attaching data adapter to spinner
            binding.spstages.setAdapter(dataAdapter1);
        }

    }
    @Override
    public void onClick(View v) {

    }

    private void callVarietyApi(){
        JsonObject mainObj=new JsonObject();
        JsonArray statusArr=new JsonArray();
        statusArr.add("Active");
        mainObj.add("status",statusArr);
        JsonArray comodityId=new JsonArray();
        comodityId.add(cntComodityId);
        mainObj.add("commodity",comodityId);
        mApiManager.varietyList(mainObj);
    }
    private void callStageApi(){
        JsonObject mainObj=new JsonObject();
        JsonArray statusArr=new JsonArray();
        statusArr.add("Active");
        mainObj.add("status",statusArr);
        JsonArray comodityId=new JsonArray();
        comodityId.add(cntComodityId);
        mainObj.add("commodity",comodityId);
        mApiManager.stageList(mainObj);
    }

    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
                showDialog(getActivity(), errorCode, false, true, 0);

            }
            @Override
            public void isSuccess(Object response, int ServiceCode) {

                 if (ServiceCode == AppConstants.LiveSTOCKREQUEST) {
                     RootOneResModel rootOneResModel= (RootOneResModel) response;
                    fillData(rootOneResModel.getResponse().getDataModel2().getCommodity(),1);
                     cntComodityId=rootOneResModel.getResponse().getDataModel2().getCommodity().get(0).getId();
                     callVarietyApi();
                     callStageApi();
                 }else if (ServiceCode == AppConstants.VarietyListReq) {
                     RootOneResModel rootOneResModel= (RootOneResModel) response;
                     fillData(rootOneResModel.getResponse().getDataModel2().getVariety(),2);
                 }else if (ServiceCode == AppConstants.StageListReq) {
                     RootOneResModel rootOneResModel= (RootOneResModel) response;
                     fillData(rootOneResModel.getResponse().getDataModel2().getStage(),3);
                 }else if (ServiceCode == AppConstants.UpdatreLivesstock) {
                     SingleObjRootOneResModel oneResModel= (SingleObjRootOneResModel) response;
                     showDialog(getActivity(), binding.splivestocks.getSelectedItem().toString()+getString(R.string.livestock_added_success), false, true, 0);
                 }

            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }
    private boolean isvalid_AddLivestocks() {
        if (binding.splivestocks.getSelectedItemPosition()==0) {
            errorMessage = "Please Select Livestocks";
            return false;
        }
        else if (binding.spvarieties.getSelectedItemPosition()==0) {
            errorMessage = ("Please Select Varieties");
            return false;
        }   else if (binding.spstages.getSelectedItemPosition()==0) {
            errorMessage = ("Please Select Stage");
            return false;
        }
        else if (TextUtils.isEmpty(binding.quantity.getText().toString().trim())) {
            errorMessage = ("Please Enter Quantity");
            return false;
        }
        else {
            return true;
        }
    }



}




