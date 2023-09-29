package com.example.testproject.ui.fragment.Farmer;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.Adapter.NotificationListAdaptor;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.util.AppConstants;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.NotificationListFragmentBinding;
import com.example.testproject.model.NotificationDataModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.ui.Activity.farmer.FarmerMainActivity;
import com.example.testproject.ui.base.BaseFragment;
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
    private UserDao userDao;

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
        userDao= AppDatabase.getInstance((getContext())).userdao();
        ((FarmerMainActivity) getActivity()).showHideEditIcon(false);
        ((FarmerMainActivity) getActivity()).setTittle("Notification");
        loadData(pageNo);

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

            JsonObject main=new JsonObject();
            JsonArray statsArr=new JsonArray();
            statsArr.add("Active");
            JsonArray idArr=new JsonArray();
            idArr.add(userDao.getUserResponse().id);

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
                        RootOneModel rootOneModel=(RootOneModel) response;
                       maxPage=rootOneModel.getResponse().getData().getPagination().getTotalPage();
                        List<NotificationDataModel> list=rootOneModel.getResponse().getData().getNotificationlog();
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

    @Override
    public void onBackCustom() {
        ((FarmerMainActivity) getActivity()).setTittle(getString(R.string.dashboard));
        ((FarmerMainActivity) getActivity()).hideIcon();
        navController.navigate(R.id.dashboardfragment);
    }
}


