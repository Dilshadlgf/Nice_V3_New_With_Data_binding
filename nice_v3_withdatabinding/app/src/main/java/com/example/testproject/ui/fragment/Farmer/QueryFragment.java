package com.example.testproject.ui.fragment.Farmer;

import android.os.Bundle;
import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
;
import com.example.testproject.Adapter.QueryAdapter;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;

import com.example.testproject.util.AppConstants;
import com.example.testproject.util.CommonUtils;
import com.example.testproject.util.JsonMyUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.FragmentQuery2Binding;
import com.example.testproject.model.ContentModel;
import com.example.testproject.model.QueryModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.ui.Activity.farmer.FarmerMainActivity;
import com.example.testproject.ui.base.BaseFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;


public class QueryFragment extends BaseFragment {

    private FragmentQuery2Binding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private String queryType = "",queryModule="";
    private QueryAdapter adapter;
    private boolean positionChanged;
    int mPosition;
    int maxLimit=1;
    private RootOneModel rootOneModel;
    private boolean loadmore;
    private int pageNo=1,maxPage=1;
    private UserDao userDao;
    private  boolean isFarmerLogin;
    private NavController navController;
    public static QueryFragment newInstance(Bundle args) {
        QueryFragment fragment = new QueryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void init() {

        layoutId = R.layout.fragment_query2;

    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

        binding = (FragmentQuery2Binding) viewDataBinding;
        userDao= AppDatabase.getInstance(getContext()).userdao();
        navController= NavHostFragment.findNavController(this);
        setUpNetWork();
        if(userDao!=null && userDao.getUserResponse().role!=null){
            isFarmerLogin=userDao.getUserResponse().isFarmer;
        }
        if(!isFarmerLogin){
            ((FarmerMainActivity) getActivity()).setTittle(getString(R.string.query));

        }
        if (getArguments() != null) {
            queryType = getArguments().getString("query");
            queryModule = getArguments().getString("queryModule");
        }

        binding.queryRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.idNestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    if(loadmore) {
                        loadmore=false;
                        if(pageNo<maxPage) {
                            pageNo++;
                            loadData(pageNo);
                        }
                    }
                }
            }
        });
        loadData(1);

    }
    private void loadData(int pageNo){
            JsonObject mainObj=new JsonObject();

            JsonArray fid=new JsonArray();
            fid.add(userDao.getUserResponse().id);

//        if(queryModule.equals("farmer")) {
//            mainObj.add("farmer", fid);
//        }
            if(queryType.equalsIgnoreCase("unresolved")){
                JsonArray qarray=new JsonArray();
                qarray.add("C");
                qarray.add("M");
                mainObj.add("status",qarray);
                mainObj.addProperty("sortBy","_id");
                mainObj.addProperty("sortOrder",1);

                mApiManager.queriesListRequest(mainObj,""+pageNo);
            }else if(queryType.equalsIgnoreCase("assigned")){
                JsonArray qarray=new JsonArray();
                qarray.add("O");
                mainObj.add("status",qarray);
                mainObj.addProperty("sortBy","_id");
                mainObj.addProperty("sortOrder",1);
                mApiManager.queriesListRequest(mainObj,""+pageNo);
            }else if(queryType.equalsIgnoreCase("resolved")){
                JsonArray qarray=new JsonArray();
                qarray.add("R");
                mainObj.add("status",qarray);
                mainObj.addProperty("sortBy","_id");
                mainObj.addProperty("sortOrder",1);
                mApiManager.queriesListRequest(mainObj,""+pageNo);
            }else if(queryType.equalsIgnoreCase("common")){
                JsonObject cobject=new JsonObject();
                JsonArray qarray=new JsonArray();
                String modelJson = getArguments().getString("model","");
                ContentModel contentmodel = (ContentModel) CommonUtils.jsonToPojo(modelJson,ContentModel.class);
                qarray.add(contentmodel.id);
                JsonArray statusArr=new JsonArray();
                statusArr.add("Active");
                cobject.add("contentId",qarray);
                cobject.addProperty("sortBy","_id");
                cobject.addProperty("sortOrder",1);
//                cobject.add("status",statusArr);
                mApiManager.queriesListRequest(cobject,""+pageNo);
            }else if(queryType.equalsIgnoreCase("feedbackQuery")){
                JsonArray qarray=new JsonArray();
                qarray.add("R");
                mainObj.add("status",qarray);
                mainObj.addProperty("sortBy","_id");
                mainObj.addProperty("sortOrder",1);
                mApiManager.queriesListRequest(mainObj,""+pageNo);
            }

    }

    private void setUpNetWork() {
        mInterFace = new ApiResponseInterface() {


            @Override
            public void isError(String errorCode) {
//                showDialog(getActivity(), errorCode, false, true, 0);
                binding.txtEmpty.setVisibility(View.VISIBLE);
                binding.txtEmpty.setText(errorCode);
                binding.queryRecycler.setVisibility(View.GONE);

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if(ServiceCode== AppConstants.QUERIES_LIST_REQUEST_FEEDBACK)
                    if(positionChanged){
                        positionChanged=false;
                        if(adapter!=null)
                            adapter.clearMyList();
                    }
                rootOneModel=(RootOneModel) response;
                if (rootOneModel.getResponse().getData().data!=null){
                    List<QueryModel> queryModel = JsonMyUtils.getPojoFromJsonArr(rootOneModel.getResponse().getData().data.getAsJsonArray(),QueryModel.class);
                    maxLimit=rootOneModel.getResponse().getData().getPagination().getTotalPage();
                    if(adapter==null) {
                        adapter = new QueryAdapter(queryModel, getActivity(),queryType);
                        binding.queryRecycler.setAdapter(adapter);
                    }else{
                        adapter.addNewList(queryModel);
                    }

                }

            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }

    @Override
    public void onBackCustom() {
        Bundle bundle=new Bundle();
        int srcfragmentId = -1;
        if (getArguments()!=null){
            srcfragmentId=getArguments().getInt("fragmentId",-1);
        }
        if (srcfragmentId==1){
            navController.navigate(R.id.contentFragment);

        }else if (srcfragmentId==2){
            ((FarmerMainActivity) getActivity()).setTittle(getString(R.string.dashboard));
            ((FarmerMainActivity) getActivity()).hideIcon();
            navController.navigate(R.id.dashboardfragment);
        }else {
            navController.navigate(R.id.profileFragment);

        }

    }
}
