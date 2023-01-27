package com.example.testproject.ui.Fragment.Farmer;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
;
import com.example.testproject.Adapter.QueryAdapter;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;

import com.example.testproject.Util.AppConstants;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.database.Dao.RoleDao;
import com.example.testproject.databinding.FragmentQuery2Binding;
import com.example.testproject.model.query.QueryResponseDataNumModel;
import com.example.testproject.model.query.RootQueryModel;
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
    private RootQueryModel rootQueryModel;
    private  QueryResponseDataNumModel queryResponseDataNumModel;
    private boolean loadmore;
    private int pageNo=1,maxPage=1;
    private RoleDao roleDao;
    private FarmerDao farmerDao;
    private  boolean isFarmerLogin;
    private List<QueryResponseDataNumModel> list;

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
        roleDao= AppDatabase.getInstance(getContext()).roleDao();
        farmerDao= AppDatabase.getInstance(getContext()).farmerDao();

        setUpNetWork();
        if(roleDao!=null && roleDao.getRole()!=null){
            isFarmerLogin=roleDao.getRole().isFarmer();
        }
        if(!isFarmerLogin){
            getActivity().getActionBar().setTitle("Query");
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
        if(roleDao.getRole().isFarmer()){

            JsonObject mainObj=new JsonObject();
            JsonArray fid=new JsonArray();
            fid.add(farmerDao.getFarmer().getId());
            if (queryModule!=null) {
                if (queryModule.equals("farmer")) {
                    mainObj.add("farmer", fid);
                }
            }else {
                Toast toast = Toast.makeText(getContext(), "Farmer Type Not Available", Toast.LENGTH_LONG);
                toast.show();              }
            if(queryType.equalsIgnoreCase("unresolved")){
                JsonArray qarray=new JsonArray();
                qarray.add("C");
                qarray.add("M");
                mainObj.add("status",qarray);

                mApiManager.queriesListRequest(mainObj,""+pageNo);
            }else if(queryType.equalsIgnoreCase("assigned")){
                JsonArray qarray=new JsonArray();
                qarray.add("O");
                mainObj.add("status",qarray);
                mApiManager.queriesListRequest(mainObj,""+pageNo);
            }else if(queryType.equalsIgnoreCase("resolved")){
                JsonArray qarray=new JsonArray();
                qarray.add("R");
                mainObj.add("status",qarray);
                mApiManager.queriesListRequest(mainObj,""+pageNo);
            }else if(queryType.equalsIgnoreCase("contentQuery")){
                JsonObject cobject=new JsonObject();
                JsonArray qarray=new JsonArray();
                qarray.add(getArguments().getString("contentId"));
                JsonArray statusArr=new JsonArray();
                statusArr.add("Active");
                cobject.add("contentId",qarray);
//                cobject.add("status",statusArr);
                mApiManager.queriesListRequest(cobject,""+pageNo);
            }else if(queryType.equalsIgnoreCase("feedbackQuery")){
                JsonArray qarray=new JsonArray();
                qarray.add("R");
                mainObj.add("status",qarray);
                mApiManager.queriesListRequest(mainObj,""+pageNo);
            }
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
                rootQueryModel=(RootQueryModel) response;

                 list=rootQueryModel.getResponse().getDataQueryModel().getData();
                maxLimit=rootQueryModel.getResponse().getDataQueryModel().getPaginationModel().getTotalPage();
                if(adapter==null) {
                    adapter = new QueryAdapter(list, getActivity(),queryType);
                    binding.queryRecycler.setAdapter(adapter);
                }else{
                    adapter.addNewList(list);
                }

            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter=null;
        assert list!=null;
        list.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}




