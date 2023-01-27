package com.example.testproject.ui.Fragment.Farmer;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RatingBar;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.databinding.FragmentAddFeedbackBinding;
import com.example.testproject.model.ContentModel;
import com.example.testproject.model.GeneralResponseModel;
import com.example.testproject.model.SingleObjectModel.SingleObjRootOneResModel;
import com.example.testproject.ui.Activity.FarmerMainActivity;
import com.example.testproject.ui.base.BaseFragment;
import com.google.gson.JsonObject;

import java.util.List;

/**
     Created by suraj singh rajput on 25-01-2023
 */
public class AddFeedbackFragment extends BaseFragment {
    private FragmentAddFeedbackBinding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private FarmerDao farmerDao;
    private int rateTimeliness=0;
    private int rateCompleteness=0;
    private int rateRelevance=0;
    private int rateUnderstandable=0;
    private String errorMessage="";
    private GeneralResponseModel generalResponseModel;
    NavController navController ;

    public static AddFeedbackFragment newInstance(Bundle args) {
        AddFeedbackFragment fragment = new AddFeedbackFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void init() {
        layoutId=R.layout.fragment_add_feedback;
    }
    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding=(FragmentAddFeedbackBinding)viewDataBinding;
        setupNetwork();
        ((FarmerMainActivity) getActivity()).setTittle(getString(R.string.newfeedback));
        ((FarmerMainActivity) getActivity()).showHideEditIcon(false);
        navController= NavHostFragment.findNavController(this);
        farmerDao= AppDatabase.getInstance(getContext()).farmerDao();
        addListenerOnRatingBar();
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callFeedbackApi();
            }
        });

    }

    private void callFeedbackApi() {
        if(isValidRequest()) {
                String modelJson = getArguments().getString("model","");
                ContentModel contentmodel = (ContentModel) CommonUtils.jsonToPojo(modelJson,ContentModel.class);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("content", contentmodel.getId());
                jsonObject.addProperty("type", "Quantitative");
                jsonObject.addProperty("village", farmerDao.getFarmer().getVillage());
                jsonObject.addProperty("farmerId", farmerDao.getFarmer().getId());
                jsonObject.addProperty("createdBy", farmerDao.getFarmer().getId());

                jsonObject.addProperty("feedbackType", "Farmer");
                jsonObject.addProperty("feedback", binding.etFeedback.getText().toString());
                jsonObject.addProperty("timeliness", rateTimeliness);
                jsonObject.addProperty("completeness", rateCompleteness);
                jsonObject.addProperty("relevance", rateRelevance);
                jsonObject.addProperty("understandable", rateUnderstandable);
                mApiManager.addFeedbackRequest(jsonObject);
        } else
        {
            showDialog(getActivity(),errorMessage,false,false,0);
        }

    }

    public void addListenerOnRatingBar() {


        binding.ratingTimeliness.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                rateTimeliness=(int)rating;
                //txtRatingValue.setText(String.valueOf(rating));

            }
        });
        binding.ratingCompleteness.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                rateCompleteness=(int)rating;
                //txtRatingValue.setText(String.valueOf(rating));

            }
        });

        binding.ratingRelevance.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                rateRelevance=(int)rating;
                //txtRatingValue.setText(String.valueOf(rating));

            }
        });

        binding.ratingUnderstandable.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                rateUnderstandable=(int)rating;
                //txtRatingValue.setText(String.valueOf(rating));

            }
        });
    }
    private void resetViews(){
        binding.etFeedback.setText("");
        binding.ratingCompleteness.setNumStars(0);
        binding.ratingTimeliness.setNumStars(0);
        binding.ratingRelevance.setNumStars(0);
        binding.ratingUnderstandable.setNumStars(0);
    }
    private boolean isValidRequest()
    {
        if(TextUtils.isEmpty(binding.etFeedback.getText().toString()))
        {
            errorMessage="Enter Feedback";
            return false;
        }
        else
        {
            return true;
        }
    }
    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
                showDialog(getActivity(), errorCode, false, true, 0);

            }
            @Override
            public void isSuccess(Object response, int ServiceCode) {
//
                if(ServiceCode == AppConstants.ADD_QUERY_REQUEST)
                    try {
                        {

                            generalResponseModel = (GeneralResponseModel) response;
                            showDialog(getActivity(), getString(R.string.feedbacksaved), false, true, 0);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                else if(ServiceCode == AppConstants.ADD_FEEDBACK_REQUEST)
                    try {
                        {

                            SingleObjRootOneResModel rootOneResModel = (SingleObjRootOneResModel) response;
                            showDialog(getActivity(), getString(R.string.feedbacksaved), false, true, 0);
                            resetViews();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }

    @Override
    public void okDialogClick(int id) {

//        navController.navigate(R.id.action_contentFragment_to_queryFragment);
    }
}