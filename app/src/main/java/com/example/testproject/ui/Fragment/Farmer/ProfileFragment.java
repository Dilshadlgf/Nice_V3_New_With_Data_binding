package com.example.testproject.ui.Fragment.Farmer;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.testproject.Adapter.SearchContentAdapter;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.databinding.FragmentProfileBinding;
import com.example.testproject.model.FarmerDataModel;
import com.example.testproject.model.Root.RootModelOne;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.ui.Activity.FarmerMainActivity;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {


    private RootOneModel rootOneModel;
    private SearchContentAdapter adapter;
    private ApiResponseInterface mInterFace;
    private Call<RootOneModel> call;
    private ApiManager mApiManager;
    NavController navController ;


    private FragmentProfileBinding binding;

    @Override

    protected void init() {

        layoutId= R.layout.fragment_profile;

    }


    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

        binding = (FragmentProfileBinding) viewDataBinding;
        navController= NavHostFragment.findNavController(this);
        makeEditBox(false);
        setUpNetWork();

        List<String> genderlist = new ArrayList<String>();
        genderlist.add("---Select Gender---");
        genderlist.add("Male");
        genderlist.add("Female");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter0 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, genderlist);
        // Drop down layout style - list view with radio button
        dataAdapter0.setDropDownViewResource(R.layout.spinner_item);
        // attaching data adapter to spinner
        binding.tvGender.setAdapter(dataAdapter0);
        if (getActivity() != null) {
            ((FarmerMainActivity) getActivity()).getToolIcon1().setVisibility(View.VISIBLE);
        }
        mApiManager.getProfile("621758b11daffc762c720138");
    }
    private void makeEditBox(boolean isEnable) {

        if (isEnable)
        {
            binding.tvFirstName.setEnabled(true);
            binding.tvFirstName.setBackgroundResource(android.R.color.white);
            binding.layoutRow1.setVisibility(View.GONE);
            binding.layoutRow2.setVisibility(View.GONE);
            binding.tvFatherHusbandName.setEnabled(true);
            binding.tvFatherHusbandName.setBackgroundResource(android.R.color.white);
            binding.tvDateOfBirth.setEnabled(true);
            binding.edgender.setVisibility(View.GONE);
            binding.tvDateOfBirth.setBackgroundResource(android.R.color.white);
            binding.tvMobileNumber.setEnabled(true);
            binding.tvMobileNumber.setBackgroundResource(android.R.color.white);
            binding.tvEmail.setEnabled(true);
            binding.tvEmail.setBackgroundResource(android.R.color.white);
            binding.tvAddress.setEnabled(true);
            binding.tvAddress.setBackgroundResource(android.R.color.white);
            binding.ivSpinner.setVisibility(View.VISIBLE);
            binding.tvGender.setVisibility(View.VISIBLE);
            binding.btnSave.setVisibility(View.VISIBLE);
            binding.bottomlayout.setVisibility(View.GONE);
        }else
        {
            binding.tvFirstName.setEnabled(false);
            binding.tvFirstName.setBackgroundResource(android.R.color.transparent);
            binding.tvFatherHusbandName.setEnabled(false);
            binding.tvFatherHusbandName.setBackgroundResource(android.R.color.transparent);
            binding.tvGender.setVisibility(View.GONE);
            binding.edgender.setEnabled(false);
            binding.edgender.setBackgroundResource(android.R.color.transparent);
            binding.tvGender.setBackgroundResource(android.R.color.transparent);
            binding.ivSpinner.setVisibility(View.GONE);
            binding.tvDateOfBirth.setEnabled(false);
            binding.tvDateOfBirth.setBackgroundResource(android.R.color.transparent);
            binding.tvMobileNumber.setEnabled(false);
            binding.tvMobileNumber.setBackgroundResource(android.R.color.transparent);
            binding.tvEmail.setEnabled(false);
            binding.tvEmail.setBackgroundResource(android.R.color.transparent);
            binding.tvAddress.setEnabled(false);
            binding.tvAddress.setBackgroundResource(android.R.color.transparent);
            binding.layoutRow1.setVisibility(View.VISIBLE);
            binding.layoutRow2.setVisibility(View.VISIBLE);
            binding.btnSave.setVisibility(View.GONE);
            binding.bottomlayout.setVisibility(View.VISIBLE);
        }
    }

    private void setUpNetWork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if (ServiceCode== AppConstants.PROFILE_REQUEST) {
                    RootOneModel rootOneModel = (RootOneModel) response;
                    FarmerDataModel d2 = rootOneModel.getResponse().getData().getData();
                    binding.setMydata(d2);
//                    if (d2.getGender().equalsIgnoreCase("male"))
//                    {
//                        binding.tvGender.setSelection(1);
//                    }else if(d2.getGender().equalsIgnoreCase("female")){
//                        binding.tvGender.setSelection(2);
//                    }
                    if (getActivity()!=null){
                        ((FarmerMainActivity)getActivity()).getToolIcon1().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                makeEditBox(true);
                                binding.btnSave.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        savedata(view);
                                    }

                                    private void savedata(View view) {

                                        try {
                                            JsonObject profileobject = new JsonObject();
                                            profileobject.addProperty("id","621758b11daffc762c720138");

                                            if (TextUtils.isEmpty(binding.tvprofilename.getText().toString().trim())) {
                                                profileobject.addProperty("name", d2.getName());
                                            } else {
                                                profileobject.addProperty("name", binding.tvprofilename.getText().toString());
                                            }
                                            if (TextUtils.isEmpty(binding.tvFatherHusbandName.getText().toString().trim())) {
                                                profileobject.addProperty("fatherName", d2.getFatherName().toString());
                                            } else {
                                                profileobject.addProperty("fatherName", binding.tvFatherHusbandName.getText().toString());
                                            }
                                            if (binding.tvGender.getSelectedItemPosition() == 0) {
                                                profileobject.addProperty("gender", d2.getGender().toString());
                                            } else {
                                                profileobject.addProperty("gender", binding.tvGender.getSelectedItem().toString());
                                            }
                                            if (TextUtils.isEmpty(binding.tvMobileNumber.getText().toString().trim())) {
                                                profileobject.addProperty("mobileNumber", d2.getMobileNumber().toString());
                                            } else {
                                                profileobject.addProperty("mobileNumber", binding.tvMobileNumber.getText().toString());
                                            }
                                            if (TextUtils.isEmpty(binding.tvEmail.getText().toString().trim())) {
                                                profileobject.addProperty("email", d2.getEmail().toString());
                                            } else {
                                                profileobject.addProperty("email", binding.tvEmail.getText().toString());
                                            }
                                            if (TextUtils.isEmpty(binding.tvDateOfBirth.getText().toString().trim())) {
                                                profileobject.addProperty("dateOfBirth", d2.getDateOfBirth().toString());
                                            } else {
                                                String d = CommonUtils.getServerFormatDate(binding.tvDateOfBirth.getText().toString(), "dd-MM-yyyy");
                                                profileobject.addProperty("dateOfBirth", d);
                                            }

                                            mApiManager.editprofileUser(profileobject);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }else if (ServiceCode==AppConstants.EDIT_PROFILE_USER)
                {
                    RootModelOne rootOneModel=(RootModelOne)response;
                    navController.navigate(R.id.action_profileFragment_self);
//                    makeEditBox(false);
                }
            }
        };
        mApiManager = new ApiManager(getActivity(), mInterFace);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardcrop:
                try {
                    Bundle bundle =new Bundle();
                    navController.navigate(R.id.action_profileFragment_to_farmerCrops_Fragment,bundle);

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

        }

    }
}