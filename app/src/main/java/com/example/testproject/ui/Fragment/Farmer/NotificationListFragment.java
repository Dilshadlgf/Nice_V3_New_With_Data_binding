package com.example.testproject.ui.Fragment.Farmer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.Adapter.NotificationListAdaptor;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.databinding.NotificationListFragmentBinding;
import com.example.testproject.model.NotificationDataModel;
import com.example.testproject.model.RootOneResModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationListFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "EventDetailsFragment";


    private NotificationListFragmentBinding binding;


    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private boolean isFarmerLogin;
    private boolean loadmore;
    private int pageNo=1,maxPage=1;
    private NotificationListAdaptor adaptor;
    private List<NotificationDataModel> mainList;
    private NavController navController;

    public static NotificationListFragment newInstance(Bundle args) {


        NotificationListFragment fragment = new NotificationListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        layoutId = R.layout.notification_list_fragment;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (NotificationListFragmentBinding) viewDataBinding;
        setupNetwork();
        navController= NavHostFragment.findNavController(this);
       // ((FragmentActivity) getActivity()).setScreenTitle("");
     //   loginDao = AppDatabase.getInstance(getContext()).loginDetails();
//        if(loginDao!=null && loginDao.getLoginResponse()!=null){
//            isFarmerLogin=loginDao.getLoginResponse().getRole().equalsIgnoreCase("farmer");
////        }
//        ((FragmentActivity) getActivity()).btnPrint.setVisibility(View.GONE);
//        ((FragmentActivity) getActivity()).img_help.setVisibility(View.GONE);
//        ((FragmentActivity) getActivity()).shareimg.setVisibility(View.GONE);
//        ((FragmentActivity) getActivity()).img_help.setVisibility(View.GONE);
//        ((FragmentActivity) getActivity()).imgfeedback.setVisibility(View.GONE);
//        ((FragmentActivity) getActivity()).enableNavigationViews(false);
//        ((FragmentActivity) getActivity()).setScreenTitle("Notification List");
//
//        ((FragmentActivity) getActivity()).mBack.setVisibility(View.VISIBLE);
//
//        ((FragmentActivity) getActivity()).mBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackCustom();
//            }
//        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());


        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (! recyclerView.canScrollVertically(1)){ //1 for down
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

//        loadData(1);

    }
    private void loadData(int pageNo){
       // if(loginDao.getLoginResponse().getRole().equalsIgnoreCase("farmer")){

            JsonObject main=new JsonObject();
            JsonArray statsArr=new JsonArray();
            statsArr.add("Active");
            JsonArray idArr=new JsonArray();
            idArr.add("623adc6b4d13f2bb63f346b1");
//          idArr.add(loginDao.getLoginResponse().getId());
            main.add("status",statsArr);
            main.add("userName",idArr);
            mApiManager.getNotificationList(main,""+pageNo);
        }
   // }
    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                    if(ServiceCode== AppConstants.NotificationListReq){
                        loadmore=true;
                        if(mainList==null){
                            mainList=new ArrayList<>();
                        }
                        RootOneResModel rootOneResModel=(RootOneResModel) response;
                       maxPage=rootOneResModel.getResponse().getDataModel2().getPagination1().getTotalPage();
                        List<NotificationDataModel> list=rootOneResModel.getResponse().getDataModel2().getNotificationlog();
                        mainList.addAll(list);
                        if(adaptor==null) {
                            adaptor = new NotificationListAdaptor(getActivity(), mainList,navController);
                            binding.recyclerView.setAdapter(adaptor);
                        }else {
                            adaptor.addToList(mainList);
                        }
                    }

            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }

    @Override
    public void onPause() {
        super.onPause();
        adaptor=null;
        assert mainList!=null;
        mainList.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adaptor==null){
            loadData(1);
        }
    }

    @Override
    public void onClick(View view) {

    }
}


