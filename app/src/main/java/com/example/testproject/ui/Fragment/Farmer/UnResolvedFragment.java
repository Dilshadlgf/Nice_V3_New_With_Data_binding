package com.example.testproject.ui.Fragment.Farmer;

import androidx.core.widget.NestedScrollView;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;

import com.example.testproject.Adapter.QueryAdapter;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.databinding.FragmentUnResolvedBinding;
import com.example.testproject.model.query.QueryResponseDataNumModel;
import com.example.testproject.model.query.RootQueryModel;
import com.example.testproject.ui.base.BaseFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UnResolvedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UnResolvedFragment extends BaseFragment {

    private FragmentUnResolvedBinding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private String queryType = "",queryModule="";
    private QueryAdapter adapter;
    private boolean positionChanged;
    int mPosition;
    int maxLimit=1;
    private int pageNo=1,maxPage=1;

    private RootQueryModel rootQueryModel;
    private QueryResponseDataNumModel queryResponseDataNumModel;



    @Override
    protected void init() {

        layoutId = R.layout.fragment_un_resolved;

    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

        binding = (FragmentUnResolvedBinding) viewDataBinding;

        setUpNetWork();

        binding.queryRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.idNestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
//                    page++;
                    //   binding.lodingpb.setVisibility(View.VISIBLE);
                    if(pageNo<maxPage) {
                        pageNo++;
                        loadmore(pageNo);
                    }                }
            }
        });
        loadmore(1);

    }
    private void loadmore(int pageNo){

        JsonObject mainObj=new JsonObject();
        JsonArray qarray=new JsonArray();
        qarray.add("C");
        qarray.add("M");
        mainObj.add("status",qarray);
        mApiManager.queriesListRequest(mainObj,""+pageNo);
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

                List<QueryResponseDataNumModel> queryResponseDataNumModel=rootQueryModel.getResponse().getDataQueryModel().getData();
                maxLimit=rootQueryModel.getResponse().getDataQueryModel().getPaginationModel().getTotalPage();
                if(adapter==null) {
                    adapter = new QueryAdapter(queryResponseDataNumModel, getActivity(), queryType);
                    binding.queryRecycler.setAdapter(adapter);
                }else{
                    adapter.addNewList(queryResponseDataNumModel);
                }

            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }
}