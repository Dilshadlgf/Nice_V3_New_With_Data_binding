package com.example.testproject.ui.Fragment.Farmer;

import androidx.databinding.ViewDataBinding;

import android.view.View;

import com.example.testproject.Adapter.VpAdapter;
import com.example.testproject.R;
import com.example.testproject.databinding.FragmentQueryBinding;

public class ContentTabFragment extends BaseFragment {

    private FragmentQueryBinding binding;


    @Override
    protected void init() {
        layoutId = R.layout.fragment_query;
    }


    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

        binding = (FragmentQueryBinding) viewDataBinding;


        binding.tab.setupWithViewPager(binding.viewpager);

        VpAdapter vpAdapter = new VpAdapter(getChildFragmentManager());
        vpAdapter.addFragment(new SearchContentDetailsFragment() ,"Content");
        vpAdapter.addFragment(new ContentInfoFragment(),"Info");
        vpAdapter.addFragment(new QueryFragment(),"Query");
        vpAdapter.addFragment(new FeedbackList_Fragment(),"Feedback");
        binding.viewpager.setAdapter(vpAdapter);

    }
}