package com.example.testproject.ui.Fragment.Farmer;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.databinding.ViewDataBinding;

import android.text.Html;
import android.view.View;

import com.example.testproject.R;
import com.example.testproject.databinding.SearchContentDetailFragmentBinding;

public class SearchContentDetailsFragment extends BaseFragment implements View.OnClickListener, Html.ImageGetter {
    private SearchContentDetailFragmentBinding binding;

    public static QueryFragment newInstance(Bundle args) {
        QueryFragment fragment = new QueryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    protected void init() {
        layoutId = R.layout.search_content_detail_fragment;
    }
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (SearchContentDetailFragmentBinding) viewDataBinding;
    }

    @Override
    public Drawable getDrawable(String s) {
        return null;
    }

    @Override
    public void onClick(View view) {

    }
}