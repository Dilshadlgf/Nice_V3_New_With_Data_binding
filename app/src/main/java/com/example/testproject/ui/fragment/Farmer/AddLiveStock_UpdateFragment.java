package com.example.testproject.ui.fragment.Farmer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.util.AppConstants;
import com.example.testproject.util.JsonMyUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.AddlivestocUpdatefragmentBinding;
import com.example.testproject.model.CommodityModel;
import com.example.testproject.model.LivestockModel;
import com.example.testproject.model.RootOneModel;
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
    private UserDao userDao;
    String farmerid;
    public Spinner splivestock,spvariety,spstages,spvarietycrop,spseason,spareaunit,
            spirrigation,spcrop;
    private NavController navController;
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
        userDao = AppDatabase.getInstance(getContext()).userdao();
        navController=NavHostFragment.findNavController(this);
        farmerid=userDao.getUserResponse().id;
        binding.quantity.setText(String.valueOf(userDao.getUserResponse().liveStockCount));
        setupNetwork();
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

            List<LivestockModel> modelList= (List<LivestockModel>) obj;
            comodityIDList.add("");//for 0 position
            if(modelList!=null && modelList.size()>0) {
                list.add("---Select LiveStock---");
                for (int i = 0; i < modelList.size(); i++) {
                    if (modelList.get(i).commonName != null) {
                        list.add(modelList.get(i).commonName);
                        comodityIDList.add(modelList.get(i).id);
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

            List<CommodityModel> modelList= (List<CommodityModel>) obj;
            varietiresIdList.add("");//for 0 position
            if(modelList!=null && modelList.size()>0) {
                list2.add("---Select Varieties---");
                for (int i = 0; i < modelList.size(); i++) {
                    list2.add(modelList.get(i).name);
                    varietiresIdList.add(modelList.get(i).id);
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

            List<CommodityModel> modelList= (List<CommodityModel>) obj;
            stageIdList.add("");//for 0 position
            if(modelList!=null && modelList.size()>0) {
                list2.add("---Select Stage---");
                for (int i = 0; i < modelList.size(); i++) {
                    if (modelList.get(i).name != null) {
                        list2.add(modelList.get(i).name);
                        stageIdList.add(modelList.get(i).id);
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
                RootOneModel rootOneModel= (RootOneModel) response;

                 if (ServiceCode == AppConstants.LiveSTOCKREQUEST) {
                     if (rootOneModel.getResponse().getData().commodity!=null){
                         List<LivestockModel> livestockModel=JsonMyUtils.getPojoFromJsonArr(rootOneModel.getResponse().getData().commodity.getAsJsonArray(),LivestockModel.class);
                         fillData(livestockModel,1);
                         cntComodityId=livestockModel.get(0).id;
                         callVarietyApi();
                         callStageApi();
                     }

                 }else if (ServiceCode == AppConstants.VarietyListReq) {
                     if (rootOneModel.getResponse().getData().variety!=null){
                         List<CommodityModel> commodityModels=JsonMyUtils.getPojoFromJsonArr(rootOneModel.getResponse().getData().variety.getAsJsonArray(),CommodityModel.class);
                         fillData(commodityModels,2);

                     }
                 }else if (ServiceCode == AppConstants.StageListReq) {
                     if (rootOneModel.getResponse().getData().stage!=null){
                         List<CommodityModel> commodityModels=JsonMyUtils.getPojoFromJsonArr(rootOneModel.getResponse().getData().variety.getAsJsonArray(),CommodityModel.class);
                         fillData(commodityModels,3);

                     }
                 }else if (ServiceCode == AppConstants.UpdatreLivesstock) {
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

    @Override
    public void onBackCustom() {
        navController.navigate(R.id.farmerLiveStock_Fragment);
    }
}




