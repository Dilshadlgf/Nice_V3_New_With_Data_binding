package com.example.testproject.ui.fragment.user;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.ui.views.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.example.testproject.R;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.databinding.UserFragmentTabLayoutBinding;

import com.example.testproject.ui.base.BaseFragment;
import com.example.testproject.util.AppConstants;
import com.example.testproject.util.CommonUtils;

/**
 * Created by DL on 10-09-2022.
 */
public class UserQueryTabFragment extends BaseFragment {
    private static final String TAG = "Dashboard";



    private UserFragmentTabLayoutBinding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private UserDao userDao;

    public static int currentTab;

    public static UserQueryTabFragment newInstance(Bundle args) {
        UserQueryTabFragment fragment = new UserQueryTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    protected void init() {
        layoutId = R.layout.user_fragment_tab_layout;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (UserFragmentTabLayoutBinding) viewDataBinding;
        getActivity().setTitle(getString(R.string.queries));
        setupNetwork();
        userDao = AppDatabase.getInstance(getContext()).userdao();
        setupViewPager(binding.viewpager);
        binding.tab.setupWithViewPager(binding.viewpager);
        binding.tab.setTabMode(TabLayout.MODE_SCROLLABLE);

        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        binding.getRoot().setLayoutParams(layoutParams);


        binding.btnAddQuery.setVisibility(View.GONE);

        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentTab=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.viewpager.setCurrentItem(currentTab);
//        settabIcon();
    }
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item=menu.findItem(R.id.mIShare);
        if(item!=null)
            item.setVisible(false);

    }
    private void apiQueryCountCall(){
        JsonObject jsonObject=new JsonObject();
        JsonObject accessObj=new JsonObject();
        accessObj.addProperty("isAccess",true);
        accessObj.addProperty("userName","admin");
        jsonObject.add("dataAccess",accessObj);
        mApiManager.queryCount(jsonObject,AppConstants.QUERIES_LIST_REQUEST);
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
                if(ServiceCode==AppConstants.WeatherAlert_LIST_REQUEST) {
                    RootOneModel rootModel = (RootOneModel) response;
                    JsonObject jsonObject=rootModel.getResponse().getData().user.getAsJsonObject();
                    if(jsonObject!=null && jsonObject.has("user")) {
//                        String sm = jsonObject.getAsJsonObject("user").toString();
//                        User model = (User) CommonUtils.getPojoFromStr(User.class, sm);
                    }
                }
            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        Bundle bundleResolved = new Bundle();
        bundleResolved.putString("query", "unresolved");
        bundleResolved.putString("queryType", "C,M");
        bundleResolved.putBoolean("hideDialog", true);
        viewPagerAdapter.addFragment(UserQueryFragment.newInstance(bundleResolved), getString(R.string.unresolved));

        Bundle infoBundle = new Bundle();
        infoBundle.putString("query", "assigned");
        infoBundle.putString("queryType", "O");
        infoBundle.putBoolean("hideDialog", true);
        viewPagerAdapter.addFragment(UserQueryFragment.newInstance(infoBundle), getString(R.string.assigned));

        Bundle assignBundle = new Bundle();
        assignBundle.putString("query", "resolved");
        assignBundle.putString("queryType", "R");
        assignBundle.putBoolean("hideDialog", true);
        viewPagerAdapter.addFragment(UserQueryFragment.newInstance(assignBundle),getString(R.string.resolved));

        Bundle assignMeBundle = new Bundle();
        assignMeBundle.putString("query", "assignedToMe");
        assignMeBundle.putString("queryType", "O");
        assignMeBundle.putString("assignedTo", "");
        assignMeBundle.putBoolean("hideDialog", true);
        viewPagerAdapter.addFragment(UserQueryFragment.newInstance(assignMeBundle), getString(R.string.assigntome));

        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onBackCustom() {
        getFragmentManager().popBackStack();
    }

}