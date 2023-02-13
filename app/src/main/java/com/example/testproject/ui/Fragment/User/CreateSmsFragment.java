package com.example.testproject.ui.Fragment.User;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.Util.JsonMyUtils;
import com.example.testproject.Util.SharedPreferenceHelper;
import com.example.testproject.Util.ViewUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.DefailtConfigDao;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.FragmentCreateSmsBinding;
import com.example.testproject.model.AddressModel;
import com.example.testproject.model.DataModel;
import com.example.testproject.model.ProductconfigModel;
import com.example.testproject.model.RefModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.model.UserModel;
import com.example.testproject.ui.Activity.user.UserFragmentActivity;
import com.example.testproject.ui.base.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
      Created by suraj singh rajput on 15-11-2022
 */
public class CreateSmsFragment extends BaseFragment implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private FragmentCreateSmsBinding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private UserDao userDao;
    private ProductconfigModel productconfigModel;
    private String kDomainId,stateId,orgId,projectId;
    private NavController navController;
    public static CreateSmsFragment newInstance(Bundle args) {
        CreateSmsFragment fragment = new CreateSmsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        layoutId = R.layout.fragment_create_sms;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (FragmentCreateSmsBinding) viewDataBinding;
        navController= NavHostFragment.findNavController(this);
        ((UserFragmentActivity) getActivity()).setTittle(getString(R.string.create_sms));
        ((UserFragmentActivity) getActivity()).showHideEditIcon(false);
        setupNetwork();
        userDao = AppDatabase.getInstance(getContext()).userdao();
        productconfigModel = AppDatabase.getInstance(getContext()).defailtConfigDao().getproductconfig();
        initSavedData();
        filterApiDomainAndTopics("subdomain","knowledgeDomain",kDomainId, AppConstants.SUBDOMAIN);
        filterApi("district","state",stateId,AppConstants.DISTREQ);

        binding.boxSubdomian.spinner.setOnItemSelectedListener(this);
        binding.boxSubdomian.spinner.setTag("boxSubdomian");

        binding.boxTopic.spinner.setOnItemSelectedListener(this);
        binding.boxTopic.spinner.setTag("boxTopic");

        binding.boxSubtopic.spinner.setOnItemSelectedListener(this);
        binding.boxSubtopic.spinner.setTag("boxSubtopic");

        binding.boxDistrict.spinner.setOnItemSelectedListener(this);
        binding.boxDistrict.spinner.setTag("boxDistrict");
        binding.boxDistrict.checkboxGroup.setVisibility(View.VISIBLE);
        binding.boxDistrict.txtCheckBox.setText(getString(R.string.ignore_district));

        binding.boxBlock.spinner.setOnItemSelectedListener(this);
        binding.boxBlock.spinner.setTag("boxBlock");
        binding.boxBlock.checkboxGroup.setVisibility(View.VISIBLE);
        binding.boxBlock.txtCheckBox.setText(getString(R.string.ignore_block));

        binding.boxGramPanchayat.spinner.setOnItemSelectedListener(this);
        binding.boxGramPanchayat.spinner.setTag("boxGramPanchayat");
        binding.boxGramPanchayat.checkboxGroup.setVisibility(View.VISIBLE);
        binding.boxGramPanchayat.txtCheckBox.setText(getString(R.string.ignore_gramPanchayat));

        binding.boxVilage.spinner.setOnItemSelectedListener(this);
        binding.boxVilage.spinner.setTag("boxVilage");
        binding.boxVilage.checkboxGroup.setVisibility(View.VISIBLE);
        binding.boxVilage.txtCheckBox.setText(getString(R.string.ignore_village));

        binding.boxDistrict.checkBox.setOnCheckedChangeListener(this);
        binding.boxBlock.checkBox.setOnCheckedChangeListener(this);
        binding.boxGramPanchayat.checkBox.setOnCheckedChangeListener(this);
        binding.boxVilage.checkBox.setOnCheckedChangeListener(this);

        binding.btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitCreateApi();
            }
        });
        binding.txtUserCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFarmerUserCount();
            }
        });
        binding.txtFramerCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFarmerUserCount();
            }
        });
    }
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getParent()==binding.boxDistrict.getRoot()){
            handleCheckBoxButton(4,isChecked);
        }else if(buttonView.getParent()==binding.boxBlock.getRoot()){
            handleCheckBoxButton(3,isChecked);
        }else if(buttonView.getParent()==binding.boxGramPanchayat.getRoot()){
            handleCheckBoxButton(2,isChecked);
        }else if(buttonView.getParent()==binding.boxVilage.getRoot()){
            handleCheckBoxButton(1,isChecked);
        }
    }
    private void getFarmerUserCount(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.add("organisation", JsonMyUtils.getOnePremInJsonArr(orgId));
        jsonObject.add("project", JsonMyUtils.getOnePremInJsonArr(projectId));
        jsonObject.add("state", JsonMyUtils.getOnePremInJsonArr(stateId));
        if(isSpinnerEnabled(binding.boxDistrict.spinner)&&binding.boxDistrict.spinner.getSelectedItemPosition()>0)
            jsonObject.add("district", JsonMyUtils.getOnePremInJsonArr(getItemIdFromSpinner(binding.boxDistrict.spinner)));
        if(isSpinnerEnabled(binding.boxBlock.spinner)&&binding.boxBlock.spinner.getSelectedItemPosition()>0)
            jsonObject.add("block", JsonMyUtils.getOnePremInJsonArr(getItemIdFromSpinner(binding.boxBlock.spinner)));
        if(isSpinnerEnabled(binding.boxGramPanchayat.spinner)&&binding.boxGramPanchayat.spinner.getSelectedItemPosition()>0)
            jsonObject.add("gramPanchayat", JsonMyUtils.getOnePremInJsonArr(getItemIdFromSpinner(binding.boxGramPanchayat.spinner)));
        if(isSpinnerEnabled(binding.boxVilage.spinner)&&binding.boxVilage.spinner.getSelectedItemPosition()>0)
            jsonObject.add("village", JsonMyUtils.getOnePremInJsonArr(getItemIdFromSpinner(binding.boxVilage.spinner)));


        mApiManager.commonApiWithThreePath("contentdissemination","farmeruser","count",jsonObject,null,AppConstants.DASHBOADCOUNT,AppConstants.METHOD_POST);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String itemId = ((String[]) parent.getSelectedItem())[1];

        if (parent.getTag().equals("boxSubdomian") && binding.boxSubdomian.spinner.getSelectedItemPosition() > 0) {
            filterApiDomainAndTopics("topic", "subDomain", itemId, AppConstants.TOPICREQ);

            //make dependent spinner selection 0
            if(binding.boxSubtopic.spinner.getSelectedItemPosition()!=0){
                binding.boxSubtopic.spinner.setSelection(0);
            }
        }
        else if (parent.getTag().equals("boxTopic")  && binding.boxTopic.spinner.getSelectedItemPosition() > 0) {
            filterApiDomainAndTopics("subtopic", "topic", itemId, AppConstants.SUBTOPICREQ);
        }
        else if (parent.getTag().equals("boxDistrict")  && binding.boxDistrict.spinner.getSelectedItemPosition() > 0) {
            filterApi("block", "district", itemId, AppConstants.BLOCKREQ);
            removeCountData();
            //make dependent spinner selection 0
            if(binding.boxGramPanchayat.spinner.getSelectedItemPosition()!=0){
                binding.boxGramPanchayat.spinner.setSelection(0);
            }
            if(binding.boxVilage.spinner.getSelectedItemPosition()!=0){
                binding.boxVilage.spinner.setSelection(0);
            }
        }
        else if (parent.getTag().equals("boxBlock") && binding.boxBlock.spinner.getSelectedItemPosition() > 0) {
            filterApi("grampanchayat", "block", itemId, AppConstants.GRAMREQ);
            removeCountData();
        }
        else if (parent.getTag().equals("boxGramPanchayat") && binding.boxGramPanchayat.spinner.getSelectedItemPosition() > 0) {
            filterApi("village", "gramPanchayat", itemId, AppConstants.VILLAGEREQ);
            removeCountData();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void initSavedData(){

        stateId=productconfigModel.state.stateId;
        orgId=productconfigModel.orgnisation.orgnisationId;
        kDomainId=productconfigModel.knowledgedomain.knowledgedomainId;
        projectId=productconfigModel.project.projectId;
    }
    private void setCountData(String fcount,String ucount){
        binding.txtFramerCount.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        binding.txtUserCount.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        binding.txtFramerCount.setText(fcount);
        binding.txtUserCount.setText(ucount);
    }
    private void removeCountData(){
        binding.txtFramerCount.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_refresh_24,0);
        binding.txtUserCount.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_refresh_24,0);
        binding.txtFramerCount.setText("");
        binding.txtUserCount.setText("");
    }

    private void filterApiDomainAndTopics(String path,String prem1,String prem2,int reqCode){
        JsonObject jsonObject=new JsonObject();
        JsonArray jsonArray=new JsonArray();
        jsonArray.add(true);
        jsonObject.add("activestatus", jsonArray);
        jsonObject.add(prem1, JsonMyUtils.getOnePremInJsonArr(prem2));
        mApiManager.commonApiWithTwoPathPost(path,"filter",jsonObject,"no",reqCode);
    }

    private void filterApi(String path,String pram1,String pram1Value,int resCode){
        JsonObject jsonObject=new JsonObject();
        jsonObject.add("status", JsonMyUtils.getOnePremInJsonArr("Active"));
        jsonObject.add(pram1, JsonMyUtils.getOnePremInJsonArr(pram1Value));
        mApiManager.commonApiWithTwoPathPost(path,"filter",jsonObject,"no",resCode);
    }
    private void hitCreateApi(){
        String str=validation();
        if(!str.isEmpty()){
            CommonUtils.makeToast(getContext(),str);
            return;
        }
        JsonObject mainObj=new JsonObject();
        mainObj.addProperty("subTopic",getItemIdFromSpinner(binding.boxSubtopic.spinner));
        mainObj.addProperty("byPass",false);
        mainObj.addProperty("subDomain",getItemIdFromSpinner(binding.boxSubdomian.spinner));
        mainObj.addProperty("organisation",orgId);
        mainObj.addProperty("project",projectId);
        mainObj.addProperty("description","");
        mainObj.addProperty("contentTitle",binding.edContentTittle.getText().toString());
        mainObj.addProperty("type","S");
        mainObj.addProperty("content",binding.edContent.getText().toString());

        JsonObject indexingDataObj=new JsonObject();

        indexingDataObj.addProperty("STATE",stateId);
        if(!binding.boxDistrict.checkBox.isChecked() && isSpinnerEnabled(binding.boxDistrict.spinner))
            indexingDataObj.addProperty("DISTRICT",getItemIdFromSpinner(binding.boxDistrict.spinner));
        if(!binding.boxBlock.checkBox.isChecked()  && isSpinnerEnabled(binding.boxBlock.spinner))
            indexingDataObj.addProperty("BLOCK",getItemIdFromSpinner(binding.boxBlock.spinner));
        if(!binding.boxVilage.checkBox.isChecked() &&isSpinnerEnabled(binding.boxVilage.spinner))
            indexingDataObj.addProperty("VILLAGE",getItemIdFromSpinner(binding.boxVilage.spinner));
        if(!binding.boxGramPanchayat.checkBox.isChecked() &&isSpinnerEnabled(binding.boxGramPanchayat.spinner))
            indexingDataObj.addProperty("GRAM_PANCHAYAT",getItemIdFromSpinner(binding.boxGramPanchayat.spinner));



        mainObj.add("indexingData",indexingDataObj);
        mainObj.addProperty("topic",getItemIdFromSpinner(binding.boxTopic.spinner));
        mainObj.addProperty("author",userDao.getUserResponse().id);
        mainObj.addProperty("knowledgeDomain",kDomainId);


        mApiManager.commonOnePathApi("content",mainObj,null,AppConstants.SUBMITREQ,AppConstants.METHOD_POST);
    }

    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
//                showDialog(getActivity(), errorCode, false, true, 0);
                Toast.makeText(getActivity(),errorCode,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if(ServiceCode== AppConstants.WeatherAlert_LIST_REQUEST) {
                    RootOneModel rootOneModel = (RootOneModel) response;
                    JsonObject jsonObject=rootOneModel.getResponse().getData().data.getAsJsonObject();
                    if(jsonObject!=null && jsonObject.has("user")) {
                        String sm = jsonObject.getAsJsonObject("user").toString();
                        UserModel model = (UserModel) CommonUtils.getPojoFromStr(UserModel.class, sm);
                    }
                }else if(ServiceCode==AppConstants.SUBDOMAIN) {
                    JsonObject jsonObject= (JsonObject) response;
                    parseJson(jsonObject,getString(R.string.select_sub_domain),binding.boxSubdomian.spinner);
                }else if(ServiceCode==AppConstants.TOPICREQ) {
                    JsonObject jsonObject= (JsonObject) response;
                    parseJson(jsonObject,getString(R.string.select_topic),binding.boxTopic.spinner);
                }else if(ServiceCode==AppConstants.SUBTOPICREQ) {
                    JsonObject jsonObject= (JsonObject) response;
                    parseJson(jsonObject,getString(R.string.select_subtopic),binding.boxSubtopic.spinner);
                }else if(ServiceCode==AppConstants.DISTREQ) {
                    JsonObject jsonObject= (JsonObject) response;
                    parseJson(jsonObject,getString(R.string.select_district),binding.boxDistrict.spinner);
                }else if(ServiceCode==AppConstants.BLOCKREQ) {
                    JsonObject jsonObject= (JsonObject) response;
                    parseJson(jsonObject,getString(R.string.select_block),binding.boxBlock.spinner);
                }else if(ServiceCode==AppConstants.GRAMREQ) {
                    JsonObject jsonObject= (JsonObject) response;
                    parseJson(jsonObject,getString(R.string.select_grampanchyat),binding.boxGramPanchayat.spinner);
                }else if(ServiceCode==AppConstants.VILLAGEREQ) {
                    JsonObject jsonObject= (JsonObject) response;
                    parseJson(jsonObject,getString(R.string.select_village),binding.boxVilage.spinner);
                }else if(ServiceCode==AppConstants.SUBMITREQ) {
                    JsonObject jsonObject= (JsonObject) response;
                    RootOneModel model = (RootOneModel) CommonUtils.getPojoFromStr(RootOneModel.class, jsonObject.toString());
                    if(model.getResponse().getStatusCode()==200){
                        CommonUtils.makeToast(getContext(),getString(R.string.success));
                        showDialog(getActivity(),getString(R.string.content)+" "+getString(R.string.created),false,true,110);
                        resetViews();
                    }

                }else if(ServiceCode==AppConstants.DASHBOADCOUNT) {
                    RootOneModel model = new Gson().fromJson(((JsonObject) response).toString(), RootOneModel.class);
                    JsonObject jsonObject = (JsonObject) model.getResponse().getData().contentDissiminateUserAndFarmer.getAsJsonObject();
                    if(jsonObject!=null){
                        setCountData(jsonObject.get("farmersCount").getAsString(),jsonObject.get("usersCount").getAsString());
                    }
                }
            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }
    private String validation(){
        if(binding.boxSubdomian.spinner.getSelectedItemPosition()==0 ){
            return "Please select a subdomain";
        }else if(binding.boxTopic.spinner.getSelectedItemPosition()==0 ){
            return "Please select a topic";
        }else if(binding.boxSubtopic.spinner.getSelectedItemPosition()==0 ){
            return "Please select a subtopic";
        }else if(!binding.boxDistrict.checkBox.isChecked() && binding.boxDistrict.spinner.getSelectedItemPosition()==0 ){
            return "Please select a district";
        }else if(!binding.boxBlock.checkBox.isChecked() && binding.boxBlock.spinner.getSelectedItemPosition()==0 ){
            return "Please select a block";
        }else if(!binding.boxGramPanchayat.checkBox.isChecked() && binding.boxGramPanchayat.spinner.getSelectedItemPosition()==0 ){
            return "Please select a grampanchayat";
        }else if(!binding.boxVilage.checkBox.isChecked() && binding.boxVilage.spinner.getSelectedItemPosition()==0 ){
            return "Please select a village";
        }else if(binding.edContentTittle.getText().toString().isEmpty()){
            return "Content tittle should not be empty";
        }else if(binding.edContent.getText().toString().isEmpty() ){
            return "Content should not be empty";
        }else if(binding.edContentTittle.getText().toString().isEmpty() ){
            return "Content tittle should not be empty";
        }else {
            return "";
        }
    }
    private void parseJson(JsonObject jsonObject, String firstRowText, Spinner spinner){
        JsonObject jsonObject1=JsonMyUtils.getDataFromJsonObject(jsonObject);
        List<AddressModel> modelList= (List<AddressModel>) CommonUtils.getPojoFromStr(new TypeToken<List<AddressModel>>() {
        }.getType(),jsonObject1.getAsJsonArray("data").toString());
        List<String[]> mlist=new ArrayList<>();
        mlist.add(new String[]{firstRowText,"00"});
        for (int i = 0; i < modelList.size(); i++) {
            String [] ns=new String[2];
            ns[0]=modelList.get(i).name;
            ns[1]=modelList.get(i).id;
            mlist.add(ns);
        }
        ViewUtils.fillDataInSpinnerWithCustomAdaptor(getContext(),spinner,mlist);
//        String[]strings= (String[]) spinner.getSelectedItem();
//        CommonUtils.makeToast(getContext(),""+strings[0]+strings[1]);
    }
    private String getItemIdFromSpinner(Spinner spinner){
        return ((String[])spinner.getSelectedItem())[1];
    }
    private boolean isSpinnerEnabled(Spinner spinner){
        return spinner.isEnabled()&& ((ViewGroup)spinner.getParent().getParent()).getVisibility()==View.VISIBLE;
    }
    private void resetViews(){
        binding.boxSubdomian.spinner.setSelection(0);
        binding.boxTopic.spinner.setSelection(0);
        binding.boxSubtopic.spinner.setSelection(0);
        binding.boxDistrict.spinner.setSelection(0);
        binding.edContentTittle.setText("");
        binding.edContent.setText("");
    }

    private void handleCheckBoxButton(int noOfSecToHide,boolean isChecked){

        int visibility=View.VISIBLE;
        if(isChecked){
            visibility=View.GONE;
        }
        switch (noOfSecToHide){
            case 4:
//                binding.boxDistrict.spinner.setEnabled(isChecked ?false:true);
                binding.boxDistrict.mainLayout.setVisibility(visibility);
                binding.boxBlock.getRoot().setVisibility(visibility);
                binding.boxGramPanchayat.getRoot().setVisibility(visibility);
                binding.boxVilage.getRoot().setVisibility(visibility);
                break;
            case 3:

//                binding.boxBlock.spinner.setEnabled(isChecked ?false:true);
                binding.boxBlock.mainLayout.setVisibility(visibility);
                binding.boxGramPanchayat.getRoot().setVisibility(visibility);
                binding.boxVilage.getRoot().setVisibility(visibility);
                break;
            case 2:
//                binding.boxGramPanchayat.spinner.setEnabled(isChecked ?false:true);
                binding.boxGramPanchayat.mainLayout.setVisibility(visibility);
                binding.boxVilage.getRoot().setVisibility(visibility);
                break;
            case 1:
//                binding.boxVilage.spinner.setEnabled(isChecked ?false:true);
                binding.boxVilage.mainLayout.setVisibility(visibility);
        }
    }
    @Override
    public void onBackCustom() {
        ((UserFragmentActivity) getActivity()).setTittle(getString(R.string.dashboard));
        ((UserFragmentActivity) getActivity()).hideIcon();
        navController.navigate(R.id.userDashboardFragment);
    }

}