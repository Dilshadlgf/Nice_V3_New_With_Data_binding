package com.example.testproject.ui.Fragment.Farmer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.testproject.R;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.database.Dao.RoleDao;
import com.example.testproject.databinding.FragmentQueryTabsBinding;
import com.example.testproject.ui.Views.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;


import static android.content.Context.MODE_PRIVATE;

public class ContentDetailFragment extends BaseFragment implements View.OnClickListener {
    BottomNavigationView btmview;
    SharedPreferences pref;
    private String queryModule="common";
    private boolean isFarmerLogin;
    private FragmentQueryTabsBinding binding;
    private FarmerDao farmerDao;
    private RoleDao roleDao;


    public static ContentDetailFragment newInstance(Bundle args) {
        ContentDetailFragment fragment = new ContentDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void init() {
        layoutId = R.layout.fragment_query_tabs;
    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        super.setUpUi(view, viewDataBinding);
       // btmview = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation);
      //  loginDao = AppDatabase.getInstance(getContext()).loginDetails();
        binding = (FragmentQueryTabsBinding) viewDataBinding;
        pref = getActivity().getSharedPreferences("mhh", MODE_PRIVATE);

        binding.btnAddQuery.setVisibility(View.GONE);

        btmview.setVisibility(View.GONE);


//        btmview=(BottomNavigationView)getActivity().findViewById(R.id.bottom_navigation);
//        ((FragmentActivity) getActivity()).mBack.setVisibility(View.VISIBLE);
//        ((FragmentActivity) getActivity()).btnPrint.setVisibility(View.GONE);
//        ((FragmentActivity) getActivity()).shareimg.setVisibility(View.GONE);
//        /* ((FragmentActivity) getActivity()).setmBack("Back");*/
//        ((FragmentActivity) getActivity()).mBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackCustom();
//            }
//        });
//        btmview.setVisibility(View.GONE);
//        binding.btnAddQuery.setOnClickListener(this);
//       // ((FragmentActivity) getActivity()).imgfeedback.setVisibility(View.VISIBLE);
//        ((FragmentActivity) getActivity()).imgfeedback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bundle feedbckbundle = new Bundle();
//                String contentId = getArguments().getString("contentId","");
//                feedbckbundle.putString("contentId",contentId);
//              //  CustomFragmentManager.replaceFragment(getFragmentManager(), AddFeedbackFragment.newInstance(feedbckbundle), true);
//
//            }
//        });

//
//        if(loginDao!=null && loginDao.getLoginResponse()!=null){
//           isFarmerLogin=loginDao.getLoginResponse().getRole().equalsIgnoreCase("farmer");
//        }
//        if(!isFarmerLogin){
//            ((FragmentActivity) getActivity()).lay_bottom_navigation.setVisibility(View.GONE);
//        }
//
//
//        ((FragmentActivity) getActivity()).setScreenTitle(getString(R.string.content));
        queryModule="farmer";

        setupViewPager(binding.viewpager);
        binding.tab.setupWithViewPager(binding.viewpager);
        settabIcon();

        binding.tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==2|| tab.getPosition()==3){
//                    show
                    binding.btnAddQuery.setVisibility(View.VISIBLE);
                }else{
                    binding.btnAddQuery.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
//        for (int i = 0; i < binding.tab.getTabCount(); i++) {
//            binding.tab.getTabAt(i).setCustomView();
//        }
    }
    private void settabIcon(){


        for (int i = 0; i <binding.tab.getTabCount() ; i++) {
            View headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.custom_tab_content, null, false);
            LinearLayout linearLayoutOne = (LinearLayout) headerView.findViewById(R.id.ll3);
            switch (i){
                case 0:
                    ((TextView)linearLayoutOne.findViewById(R.id.tv_tittle)).setText("unresolved");
                    break;
                case 1:
                    ((TextView)linearLayoutOne.findViewById(R.id.tv_tittle)).setText("assigned");
                    break;
                case 2:
                    ((TextView)linearLayoutOne.findViewById(R.id.tv_tittle)).setText("resolved");
                    break;

            }
            binding.tab.getTabAt(i).setCustomView(linearLayoutOne);
        }
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        String contentId = getArguments().getString("contentId","");
        if (isFarmerLogin) {
            Bundle bundleResolved = new Bundle();
            bundleResolved.putString("query", "unresolved");
            bundleResolved.putString("queryModule",queryModule);
            if(getArguments()!=null){
                bundleResolved.putString("contentId",contentId);
                bundleResolved.putString("model", getArguments().getString("model"));
            }
            viewPagerAdapter.addFragment(SearchContentDetailsFragment.newInstance(bundleResolved), getResources().getString(R.string.content));

            Bundle infoBundle = new Bundle();
//            assignBundle.putString("contentId","61c99938fec187d08f30b6e4");
            infoBundle.putString("contentId",contentId);
            infoBundle.putString("query", "contentQuery");
            infoBundle.putString("queryModule",queryModule);
            viewPagerAdapter.addFragment(ContentInfoFragment.newInstance(infoBundle), getResources().getString(R.string.contentInfo));

            Bundle assignBundle = new Bundle();
//            assignBundle.putString("contentId","61c99938fec187d08f30b6e4");
            assignBundle.putString("contentId",contentId);
            assignBundle.putString("query", "contentQuery");
            assignBundle.putString("queryModule",queryModule);
            viewPagerAdapter.addFragment(QueryFragment.newInstance(assignBundle), getResources().getString(R.string.query));

            Bundle bundleUnResolved = new Bundle();
            bundleUnResolved.putString("query", "feedbackQuery");
            bundleResolved.putString("contentId",contentId);
            bundleUnResolved.putString("queryModule",queryModule);
            viewPagerAdapter.addFragment(FeedbackList_Fragment.newInstance(bundleUnResolved), getResources().getString(R.string.feedback));
            viewPager.setOffscreenPageLimit(4);

        } else {
            Bundle bundleResolved = new Bundle();
            bundleResolved.putString("query", "unresolved");
            bundleResolved.putString("queryModule",queryModule);
            if(getArguments()!=null){


                bundleResolved.putString("contentId",contentId);
                bundleResolved.putString("model", getArguments().getString("model"));
            }
            viewPagerAdapter.addFragment(SearchContentDetailsFragment.newInstance(bundleResolved), getResources().getString(R.string.content));

            binding.appBarLayout.setVisibility(View.GONE);
            viewPager.setOffscreenPageLimit(1);
        }


        viewPager.setAdapter(viewPagerAdapter);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add_query1) {
//            if (loginDao.getLoginResponse().getRole().equalsIgnoreCase("farmer")) {
//
//                try {
//                    String contentId = getArguments().getString("contentId","");
//                    Bundle bundle=new Bundle();
//                    bundle.putString("contentId",contentId);
//
//                    CustomFragmentManager.replaceFragment(getFragmentManager(),AddFarmerqurie_Fragment.newInstance(bundle), true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

            }
        }

   // }
}
