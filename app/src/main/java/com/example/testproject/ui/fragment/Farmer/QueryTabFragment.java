package com.example.testproject.ui.fragment.Farmer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import com.example.testproject.R;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.FragmentQueryTabsBinding;
import com.example.testproject.ui.Activity.farmer.FarmerMainActivity;
import com.example.testproject.ui.views.ViewPagerAdapter;
import com.example.testproject.ui.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;

public class QueryTabFragment extends BaseFragment {

    FragmentQueryTabsBinding binding;
    String queryModule;
    private UserDao userDao;
  private NavController navController ;

    public static QueryTabFragment newInstance(Bundle args) {
        QueryTabFragment fragment = new QueryTabFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void init() {
        layoutId = R.layout.fragment_query_tabs;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        super.setUpUi(view, viewDataBinding);
        binding= (FragmentQueryTabsBinding) viewDataBinding;
        binding.btnAddQuery.setVisibility(View.VISIBLE);
        navController= NavHostFragment.findNavController(this);
        userDao= AppDatabase.getInstance(getContext()).userdao();
        ((FarmerMainActivity) getActivity()).setTittle(getString(R.string.common_content));
        ((FarmerMainActivity) getActivity()).showHideEditIcon(false);
        if(getArguments()!=null) {
            queryModule = getArguments().getString("queryModule", "");
            if(queryModule.equals("common")) {
//                ((FragmentActivity) getActivity()).setScreenTitle(getString(R.string.commonquery));
            }else {
//                ((FragmentActivity) getActivity()).setScreenTitle(loginDao.getLoginResponse().getName()+" Query's");
            }
        }
        binding.tab.setSelectedTabIndicator(null);
        setupViewPager(binding.viewpager);
        binding.tab.setupWithViewPager(binding.viewpager);
        binding.tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        settabIcon();
        binding.tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    private void settabIcon(){

        for (int i = 0; i <binding.tab.getTabCount() ; i++) {
            View headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.custom_tab_content, null, false);
            LinearLayout linearLayoutOne = (LinearLayout) headerView.findViewById(R.id.ll3);
            switch (i){
                case 0:
                    ((TextView)linearLayoutOne.findViewById(R.id.tv_tittle)).setText(getString(R.string.unresolved));
                    break;
                case 1:
                    ((TextView)linearLayoutOne.findViewById(R.id.tv_tittle)).setText(getString(R.string.assigned));
                    break;
                case 2:
                    ((TextView)linearLayoutOne.findViewById(R.id.tv_tittle)).setText(getString(R.string.resolved));
                    break;

            }
            binding.tab.getTabAt(i).setCustomView(linearLayoutOne);
        }
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());


        if (userDao.getUserResponse().isFarmer) {
            Bundle bundleResolved = new Bundle();
            bundleResolved.putString("query", "unresolved");
            bundleResolved.putString("queryModule",queryModule);
            bundleResolved.putInt("framentId",2);
            viewPagerAdapter.addFragment(QueryFragment.newInstance(bundleResolved), getResources().getString(R.string.unresolvequery));

            Bundle assignBundle = new Bundle();
            assignBundle.putString("query", "assigned");
            assignBundle.putString("queryModule",queryModule);
            assignBundle.putInt("framentId",2);
            viewPagerAdapter.addFragment(QueryFragment.newInstance(assignBundle), getResources().getString(R.string.assignedquery));

            Bundle bundleUnResolved = new Bundle();
            bundleUnResolved.putString("query", "resolved");
            bundleUnResolved.putString("queryModule",queryModule);
            bundleUnResolved.putInt("fragmentId",2);
            viewPagerAdapter.addFragment(QueryFragment.newInstance(bundleUnResolved), getResources().getString(R.string.resolvequery));
            viewPager.setOffscreenPageLimit(3);

        } else {
            viewPager.setOffscreenPageLimit(1);
        }


        viewPager.setAdapter(viewPagerAdapter);

        binding.btnAddQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("fragmentId",2);
                navController.navigate(R.id.action_queryTabFragment_to_addFarmerqurie_Fragment,bundle);

            }
        });

    }

    @Override
    public void onBackCustom() {
        ((FarmerMainActivity) getActivity()).setTittle(getString(R.string.dashboard));
        ((FarmerMainActivity) getActivity()).hideIcon();
        navController.navigate(R.id.dashboardfragment);
    }
}
