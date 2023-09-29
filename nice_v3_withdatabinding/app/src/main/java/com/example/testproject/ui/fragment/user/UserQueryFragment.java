package com.example.testproject.ui.fragment.user;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.Adapter.user.UserQueryAdaptor;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.model.QueriesResponseDataNumModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.util.CommonUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.example.testproject.R;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.databinding.UserQueryFragmentBinding;
import com.example.testproject.interfaces.ListItemClickListener;
import com.example.testproject.ui.base.BaseFragment;
import com.example.testproject.util.AppConstants;
import com.example.testproject.util.JsonMyUtils;
import com.example.testproject.util.LocaleHelper;

import java.util.List;

/**
 * Created by DL on 10-09-2022.
 */
public class UserQueryFragment extends BaseFragment {
    private static final String TAG = "Dashboard";



    private UserQueryFragmentBinding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private UserDao userDao;

    private int pageNo=1,maxPage=1;
    private boolean loadmore;
    private String queryTabType="";
    public static UserQueryFragment newInstance(Bundle args) {
        UserQueryFragment fragment = new UserQueryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        layoutId = R.layout.user_query_fragment;
        LocaleHelper.setLocale(getContext(),LocaleHelper.getLanguage(getContext()));
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (UserQueryFragmentBinding) viewDataBinding;
        pageNo=1;
        setupNetwork();
        userDao = AppDatabase.getInstance(getContext()).userdao();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        fillData();

        showLoadingPage();
        if(getArguments()!=null){
            queryTabType=getArguments().getString("query","");
        }
        apiQueryCountCall();

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (! recyclerView.canScrollVertically(1)){ //1 for down
                    if(loadmore) {
                        loadmore=false;
                        if(pageNo<maxPage) {
                            pageNo++;
                            binding.progressBar.setVisibility(View.VISIBLE);
                            apiQueryCountCall();
                        }
                    }
                }
            }
        });
        binding.fab.setVisibility(View.GONE);
    }
    private void showLoadingPage(){
        binding.txtEmpty.setText(getString(R.string.loading));
        binding.txtEmpty.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.flash_leave_now));
        binding.txtEmpty.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.GONE);
    }
    private void stopTextAnimation(){
        binding.txtEmpty.setAnimation(null);
    }

    private UserQueryAdaptor userQueryAdaptor;
    private void fillData(List<QueriesResponseDataNumModel> modelList){
        if(binding.recyclerView.getAdapter()!=null){
            userQueryAdaptor.addItemInList(modelList);
            return;
        }
        userQueryAdaptor=new UserQueryAdaptor(getContext(),modelList,new ListItemClickListener() {
            @Override
            public void onItemClick(int position, String id) {
                    if(position==-1) {
                        showLoadingPage();
                        userQueryAdaptor.doClearList();
                        apiQueryCountCall();
                    }
            }
        },userDao.getUserResponse().userName,queryTabType);
        binding.recyclerView.setAdapter(userQueryAdaptor);
    }
    private void apiQueryCountCall(){

        JsonObject mainObj = new JsonObject();
        JsonArray fid = new JsonArray();
        fid.add(userDao.getUserResponse().id);
//        mainObj.add("farmer", fid);
        JsonArray qarray = new JsonArray();
        boolean hasQtoMe=false;
        if(getArguments()!=null) {
            String[] strings=getArguments().getString("queryType").split(",");
            for(String s:strings){
                qarray.add(s);
            }
            mainObj.add("status", qarray);
            if(getArguments().containsKey("assignedTo")){
                mainObj.add("assignedTo", JsonMyUtils.getOnePremInJsonArr(userDao.getUserResponse().id));
            }
            if(getArguments().getString("query","").equals("assignedToMe")){
                hasQtoMe=true;
            }
        }
        JsonObject accessObj = new JsonObject();
        accessObj.addProperty("isAccess", true);
        accessObj.addProperty("userName", userDao.getUserResponse().userName);

        if(!hasQtoMe) {//gokul told to add this 03-04-2023
            mainObj.add("dataAccess", accessObj);
        }

        //gokul told to remove 17-11-2022
//        mainObj.addProperty("sortBy", "_id");
//        mainObj.addProperty("sortOrder", 1);

        mApiManager.queriesListRequest(mainObj,""+pageNo);
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
                if(ServiceCode==AppConstants.QUERIES_LIST_REQUEST_FEEDBACK) {
                    RootOneModel rootModel = (RootOneModel) response;
//                    JsonObject jsonObject=rootModel.getResponse().getData().getData();
                    loadmore=true;
                    binding.progressBar.setVisibility(View.GONE);
                    if(rootModel.getResponse().getData()!=null ) {
//                        String sm = jsonObject.getAsJsonArray("data").toString();
                        List<QueriesResponseDataNumModel> list = (List<QueriesResponseDataNumModel>) JsonMyUtils.getPojoFromJsonArr( rootModel.getResponse().getData().data.getAsJsonArray(),QueriesResponseDataNumModel.class);


//                        List<QueriesResponseDataNumModel> list = rootModel.getResponse().getData().getData();
                        maxPage=rootModel.getResponse().getData().getPagination().getTotalPage();
                        fillData(list);
                        binding.txtEmpty.setVisibility(View.GONE);
                        binding.recyclerView.setVisibility(View.VISIBLE);
                        stopTextAnimation();
                    }else {
                        binding.txtEmpty.setText(getString(R.string.no_data_found));
                        binding.txtEmpty.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);
                        stopTextAnimation();
                    }
                }
            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }

    @Override
    public void onBackCustom() {
        getFragmentManager().popBackStack();
    }
}