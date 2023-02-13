package com.example.testproject.ui.Fragment.User;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.Adapter.user.UserContentAdaptor;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.Util.JsonMyUtils;
import com.example.testproject.Util.ViewUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.CustomDialogWithEditSpinnerTextBinding;
import com.example.testproject.databinding.UserQueryFragmentBinding;
import com.example.testproject.interfaces.ListItemClickListener;
import com.example.testproject.model.AddressModel;
import com.example.testproject.model.ContentModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.ui.Activity.user.UserFragmentActivity;
import com.example.testproject.ui.base.BaseFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DL on 10-09-2022.
 */
public class UserContentFragment extends BaseFragment {
    private static final String TAG = "Dashboard";
    private NavController navController;
    private UserQueryFragmentBinding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private UserDao userDao;
    private int screenType=1;
    private int pageNo=1,maxPage=1;
    private boolean loadmore;

    private List<String> khowadgeListId =new ArrayList<>();
    private List<String> distListId=new ArrayList<>();
    private Spinner kSpinner,dSpinner;
    private JsonObject filterJsonObject;

    public static UserContentFragment newInstance(Bundle args) {
        UserContentFragment fragment = new UserContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        layoutId = R.layout.user_query_fragment;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (UserQueryFragmentBinding) viewDataBinding;
        navController= NavHostFragment.findNavController(this);
        pageNo=1;
        setupNetwork();
        userDao = AppDatabase.getInstance(getContext()).userdao();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        showLoadingPage();
        if(getArguments()!=null){
            screenType=getArguments().getInt("screenType",screenType);
        }
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (! recyclerView.canScrollVertically(1)){ //1 for down
                    if(loadmore) {
                        loadmore=false;
                        if(pageNo<maxPage) {
                            binding.progressBar.setVisibility(View.VISIBLE);
                            pageNo++;
                            contentApiCall(filterJsonObject);
                        }
                    }
                }
            }
        });
//        fillData();
        contentApiCall(null);
//        filterKdomainApi();
//        filterDistrictApi();
        binding.fab.setVisibility(View.VISIBLE);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });
        khowadgeListId.add("");
        distListId.add("");
    }
    private void showFilterDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext(),R.style.MyDialogTheme);
        CustomDialogWithEditSpinnerTextBinding dialogbinding=CustomDialogWithEditSpinnerTextBinding.inflate(LayoutInflater.from(getContext()));
        builder.setView(dialogbinding.getRoot());

        dialogbinding.etInputB1.setVisibility(View.GONE);
        dialogbinding.spinnerB1.setVisibility(View.VISIBLE);
        dialogbinding.etInput.setHint(getString(R.string.search));
        dialogbinding.txtTittle.setText(getString(R.string.search));
        dialogbinding.txtTittleB1.setText(getString(R.string.knowlegdomain));
        dialogbinding.txtTittleC1.setText(getString(R.string.district));

        dialogbinding.etInputC1.setVisibility(View.GONE);
        dialogbinding.spinnerC1.setVisibility(View.VISIBLE);

        kSpinner=dialogbinding.spinnerB1;
        dSpinner=dialogbinding.spinnerC1;

        builder.setPositiveButton(getString(R.string.search), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                JsonObject jsonObject=new JsonObject();
                if(!dialogbinding.etInput.getText().toString().isEmpty()){
                    JsonObject searchObj=new JsonObject();
                    searchObj.addProperty("content",dialogbinding.etInput.getText().toString());
                    jsonObject.add("searchBox",searchObj);
                    jsonObject.add("omitStatus", JsonMyUtils.getOnePremInJsonArr("Deleted"));
                }
                jsonObject.add("knowledgeDomain",JsonMyUtils.getOnePremInJsonArr(khowadgeListId.get(kSpinner.getSelectedItemPosition())));
                jsonObject.add("district",JsonMyUtils.getOnePremInJsonArr(distListId.get(dSpinner.getSelectedItemPosition())));
                filterJsonObject=jsonObject;
                if(userContentAdaptor!=null){
                    userContentAdaptor.doClearList();
                }
                showLoadingPage();
                if(kSpinner.getSelectedItemPosition()==0 && dSpinner.getSelectedItemPosition()==0){
                    jsonObject=null;
                }
                contentApiCall(jsonObject);
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog=builder.create();
        dialog.show();
        filterKdomainApi();
        filterDistrictApi();

    }
    private void showLoadingPage(){
        binding.txtEmpty.setText(getString(R.string.loading));
        binding.txtEmpty.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.flash_leave_now));
        binding.txtEmpty.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.GONE);
    }
    private void filterKdomainApi(){
        JsonObject jsonObject=new JsonObject();
        JsonArray jsonArray=new JsonArray();
        jsonArray.add(true);
        jsonObject.add("activestatus", jsonArray);
        mApiManager.commonApiWithTwoPathPost("knowledgedomain","filter",jsonObject,"no",AppConstants.GeoFilter);
    }
    private void filterDistrictApi(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.add("status", JsonMyUtils.getOnePremInJsonArr("Active"));
        jsonObject.add("state", JsonMyUtils.getOnePremInJsonArr(userDao.getUserResponse().stateId));
        mApiManager.commonApiWithTwoPathPost("district","filter",jsonObject,"no",AppConstants.FILTER);
    }

    private UserContentAdaptor userContentAdaptor;
    private void fillData(List<ContentModel> modelList){
        if(binding.recyclerView.getAdapter()!=null){
            userContentAdaptor.addItemInList(modelList);
            return;
        }
        userContentAdaptor=  new UserContentAdaptor(getContext(),userDao.getUserResponse().id,screenType,modelList,new ListItemClickListener() {
            @Override
            public void onItemClick(int position, String id) {
                if(position==-1){
                    userContentAdaptor.doClearList();
                    binding.progressBar.setVisibility(View.VISIBLE);
                    showLoadingPage();
                    contentApiCall(null);
                }
            }
        });
        binding.recyclerView.setAdapter(userContentAdaptor);
    }
    private void contentApiCall(JsonObject mainObj){

        if(mainObj==null) {
             mainObj = new JsonObject();
        }
        JsonObject accessObj = new JsonObject();
        accessObj.addProperty("isAccess", true);
        accessObj.addProperty("userName", userDao.getUserResponse().userName);
        JsonArray contentType = new JsonArray();
        contentType.add(getArguments().getString("type","S"));
        JsonArray statusType = new JsonArray();
        String sts=getArguments().getString("status","");
        if(sts.length()>1){
            String[] strings=sts.split(",");
            for (String string : strings) {
                statusType.add(string);
            }
        }else {
            statusType.add(sts);
        }

        boolean noTrans=getArguments().getString("translationStatus","").isEmpty();
        if(!noTrans){
            JsonArray transArr=new JsonArray();
            sts=getArguments().getString("translationStatus");
            if(sts.length()>1){
                String[] strings=sts.split(",");
                for (String string : strings) {
                    transArr.add(string);
                }
            }else {
                transArr.add(sts);
            }
            mainObj.add("translationStatus", transArr);//both status and translationStatus hv same payload
        }
        JsonObject dataAccessObj=new JsonObject();
        dataAccessObj.addProperty("isAccess",true);
        dataAccessObj.addProperty("userName",userDao.getUserResponse().userName);
        mainObj.add("dataAccess",dataAccessObj);
        mainObj.add("type", contentType);
        mainObj.add("status", statusType);
        mApiManager.searchContentsposterListRequestofline("" + pageNo, mainObj);
    }



    private void stopTextAnimation(){
        binding.txtEmpty.setAnimation(null);
    }


    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
//                showDialog(getActivity(), errorCode, false, true, 0);
                if(getActivity()!=null) {
                    Toast.makeText(getActivity(), errorCode, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if(getContext()==null){
                    return;
                }
                if(ServiceCode== AppConstants.SEARCHCONTENTPOSTERCONTENT) {
                    RootOneModel rootOneModel = (RootOneModel) response;
                    if (rootOneModel.getResponse().getData().content!=null){
                        loadmore=true;
                        binding.progressBar.setVisibility(View.GONE);
                        if(rootOneModel.getResponse().getData()!=null && rootOneModel.getResponse().getData().getPagination().getTotalPage()>0) {
                            maxPage=rootOneModel.getResponse().getData().getPagination().getTotalPage();
                            List<ContentModel> list = JsonMyUtils.getPojoFromJsonArr(rootOneModel.getResponse().getData().content.getAsJsonArray(),ContentModel.class);
                            fillData(list);
                            binding.txtEmpty.setVisibility(View.GONE);
                            binding.recyclerView.setVisibility(View.VISIBLE);
                            stopTextAnimation();
                    }

                    }else {
                        binding.txtEmpty.setText(getString(R.string.no_data_found));
                        binding.txtEmpty.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);
                        stopTextAnimation();
                    }
                }else if(ServiceCode==AppConstants.GeoFilter) {
                    JsonObject jsonObject= (JsonObject) response;
                    JsonObject jsonObject1=JsonMyUtils.getDataFromJsonObject(jsonObject);
                    List<AddressModel> modelList= (List<AddressModel>) CommonUtils.getPojoFromStr(new TypeToken<List<AddressModel>>() {
                    }.getType(),jsonObject1.getAsJsonArray("data").toString());
                    List<String> mlist=new ArrayList<>();
                    mlist.add(getString(R.string.select_knowleadge));
                    for (int i = 0; i < modelList.size(); i++) {
                        mlist.add(modelList.get(i).name);
                        khowadgeListId.add(modelList.get(i).id);
                    }

                    ViewUtils.fillDataInSpinner(getContext(),kSpinner,mlist);
                }else if(ServiceCode==AppConstants.FILTER) {
                    JsonObject jsonObject= (JsonObject) response;
                    JsonObject jsonObject1=JsonMyUtils.getDataFromJsonObject(jsonObject);
                    List<AddressModel> modelList= (List<AddressModel>) CommonUtils.getPojoFromStr(new TypeToken<List<AddressModel>>() {
                    }.getType(),jsonObject1.getAsJsonArray("data").toString());
                    List<String> mlist=new ArrayList<>();
                    mlist.add(getString(R.string.select_district));
                    for (int i = 0; i < modelList.size(); i++) {
                        mlist.add(modelList.get(i).name);
                        distListId.add(modelList.get(i).id);
                    }
                    ViewUtils.fillDataInSpinner(getContext(),dSpinner,mlist);
                }
            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }

    @Override
    public void onBackCustom() {
        ((UserFragmentActivity) getActivity()).setTittle(getString(R.string.dashboard));
        ((UserFragmentActivity) getActivity()).hideIcon();
        navController.navigate(R.id.userDashboardFragment);
    }
}