package com.example.testproject.ui.Fragment.Farmer;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;

import com.example.testproject.Adapter.FeedbackListAdapter;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.Util.JsonMyUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.FragmentFeedbackListBinding;
import com.example.testproject.model.ContentModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.model.UserModel;
import com.example.testproject.ui.base.BaseFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
/**
 * Created by Suraj on 07-07-2022.
 */
public class FeedbackList_Fragment extends BaseFragment {

  private FragmentFeedbackListBinding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private String queryType = "",queryModule="";
    private FeedbackListAdapter adapter;
    private boolean positionChanged;
    int mPosition;
    int maxLimit=1;
    private int pageNo=1,maxPage=1;
    private UserDao userDao;
    private RootOneModel rootOneModel;
    private NavController navController;
    public static FeedbackList_Fragment newInstance(Bundle bundle) {
        FeedbackList_Fragment fragment = new FeedbackList_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void init() {

        layoutId = R.layout.fragment_feedback_list_;

    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

        binding = (FragmentFeedbackListBinding) viewDataBinding;
        userDao= AppDatabase.getInstance((getContext())).userdao();
        navController= NavHostFragment.findNavController(this);
        setUpNetWork();

        binding.farmerRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.idNestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {

                    if(pageNo<maxPage) {
                        pageNo++;
                        loadmore(pageNo);
                    }
                }
            }
        });
        loadmore(1);

    }

    private void loadmore(int pageNo){
        JsonObject object=new JsonObject();
        JsonArray jsonArray=new JsonArray();
        String modelJson = getArguments().getString("model","");
        ContentModel contentmodel = (ContentModel) CommonUtils.jsonToPojo(modelJson,ContentModel.class);
        jsonArray.add(contentmodel.id);
        object.add("content",jsonArray);
        JsonArray  jsonArray1=new JsonArray();
        jsonArray1.add("Active");
        object.add("status",jsonArray1);
        mApiManager.feedbacklistRequest(object,""+pageNo);
    }

    private void setUpNetWork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if(ServiceCode== AppConstants.FeedbacklistRequest)
                    if(positionChanged){
                        positionChanged=false;
                        if(adapter!=null)
                            adapter.clearMyList();
                    }
                rootOneModel=(RootOneModel) response;
                if (rootOneModel.getResponse().getData().data!=null){
                    List<UserModel> list= JsonMyUtils.getPojoFromJsonArr(rootOneModel.getResponse().getData().data.getAsJsonArray(),UserModel.class);
                    maxLimit=rootOneModel.getResponse().getData().getPagination().getTotalPage();
                    if(adapter==null) {
                        adapter = new FeedbackListAdapter(list, getActivity());
                        binding.farmerRecycler.setAdapter(adapter);
                    }else{
//                    adapter = new FeedbackListAdapter(list, getActivity());
//                    binding.farmerRecycler.setAdapter(adapter);
                        adapter.addNewList(list);
                    }
                }


            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }

    @Override
    public void onBackCustom() {
     navController.navigate(R.id.contentFragment);
    }
}