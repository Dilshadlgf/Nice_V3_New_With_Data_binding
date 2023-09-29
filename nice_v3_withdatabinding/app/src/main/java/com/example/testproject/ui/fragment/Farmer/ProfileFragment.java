package com.example.testproject.ui.fragment.Farmer;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.util.AppConstants;
import com.example.testproject.util.CommonUtils;
import com.example.testproject.util.JsonMyUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.FragmentProfileBinding;
import com.example.testproject.model.UserModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.ui.Activity.farmer.FarmerMainActivity;
import com.example.testproject.ui.base.BaseFragment;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private ApiResponseInterface mInterFace;
    private ApiManager mApiManager;
    NavController navController ;
    private FragmentProfileBinding binding;
    private UserDao userDao;
    private ImageView editIcon;
    @Override
    protected void init() {
        layoutId= R.layout.fragment_profile;
    }
    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

        binding = (FragmentProfileBinding) viewDataBinding;
        userDao= AppDatabase.getInstance((getContext())).userdao();
        navController= NavHostFragment.findNavController(this);
        makeEditBox(false);
        setUpNetWork();
        ((FarmerMainActivity) getActivity()).setTittle("My Profile");
        ((FarmerMainActivity) getActivity()).showHideEditIcon(true);
        ConstraintLayout constraintLayout=(ConstraintLayout)((FarmerMainActivity)getActivity()).findViewById(R.id.top_bar);
        editIcon = (ImageView) constraintLayout.findViewById(R.id.edit);
        binding.cardcrop.setOnClickListener(this);
        binding.livestoklyout.setOnClickListener(this);
        binding.querieslayout.setOnClickListener(this);

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
        mApiManager.getProfile(userDao.getUserResponse().id);
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
                    if (rootOneModel.getResponse().getData().data!=null) {
                        UserModel userModel = (UserModel) JsonMyUtils.getPojoFromJsonObj(UserModel.class, rootOneModel.getResponse().getData().data.getAsJsonObject());
                        binding.setMydata(userModel);
                        binding.tvCrop.setText(String.valueOf(userModel.cropCount));
                        binding.tvTotalLand.setText(String.valueOf(userModel.totalLand));
                        binding.tvLivestock.setText(String.valueOf(userModel.liveStockCount));
                        binding.tvCultivatedArea.setText(String.valueOf(userModel.cultivatedArea));
                        binding.tvVacantArea.setText(String.valueOf(userModel.vacantArea));

                        if (getActivity() != null) {
                            editIcon.setOnClickListener(new View.OnClickListener() {
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
                                                profileobject.addProperty("id", userDao.getUserResponse().id);

                                                if (TextUtils.isEmpty(binding.tvprofilename.getText().toString().trim())) {
                                                    profileobject.addProperty("name", userModel.name);
                                                } else {
                                                    profileobject.addProperty("name", binding.tvprofilename.getText().toString());
                                                }
                                                if (TextUtils.isEmpty(binding.tvFatherHusbandName.getText().toString().trim())) {
                                                    profileobject.addProperty("fatherName", userModel.fatherName.toString());
                                                } else {
                                                    profileobject.addProperty("fatherName", binding.tvFatherHusbandName.getText().toString());
                                                }
                                                if (binding.tvGender.getSelectedItemPosition() == 0) {
                                                    profileobject.addProperty("gender", userModel.gender.toString());
                                                } else {
                                                    profileobject.addProperty("gender", binding.tvGender.getSelectedItem().toString());
                                                }
                                                if (TextUtils.isEmpty(binding.tvMobileNumber.getText().toString().trim())) {
                                                    profileobject.addProperty("mobileNumber", userModel.mobileNumber.toString());
                                                } else {
                                                    profileobject.addProperty("mobileNumber", binding.tvMobileNumber.getText().toString());
                                                }
                                                if (TextUtils.isEmpty(binding.tvEmail.getText().toString().trim())) {
                                                    profileobject.addProperty("email", userModel.email.toString());
                                                } else {
                                                    profileobject.addProperty("email", binding.tvEmail.getText().toString());
                                                }
                                                if (TextUtils.isEmpty(binding.tvDateOfBirth.getText().toString().trim())) {
                                                    profileobject.addProperty("dateOfBirth", userModel.dateOfBirth.toString());
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
                    }
                }else if (ServiceCode==AppConstants.EDIT_PROFILE_USER)
                {
                    RootOneModel rootOneModel=(RootOneModel)response;
                    navController.navigate(R.id.action_profileFragment_self);
//                    makeEditBox(false);
                }
            }
        };
        mApiManager = new ApiManager(getActivity(), mInterFace);
    }

    @Override
    public void onClick(View view) {
        if (getActivity() != null) {
            ((FarmerMainActivity) getActivity()).showHideEditIcon(false);
        }
        switch (view.getId()){
            case R.id.cardcrop:
                try {
                    Bundle bundle =new Bundle();
                    navController.navigate(R.id.action_profileFragment_to_farmerCrops_Fragment,bundle);

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.livestoklyout:
                try {
                    Bundle bundle =new Bundle();

                    navController.navigate(R.id.action_profileFragment_to_farmerLiveStock_Fragment,bundle);

                }catch (Exception e){
                    e.printStackTrace();
                }
                 break;

            case R.id.querieslayout:
                try {
                    Bundle bundle=new Bundle();
                    bundle.putString("queryModule","farmer");
                    navController.navigate(R.id.action_profileFragment_to_queryTabFragment,bundle);
                }catch (Exception e){
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void onBackCustom() {
        ((FarmerMainActivity) getActivity()).setTittle(getString(R.string.dashboard));
        ((FarmerMainActivity) getActivity()).hideIcon();
        navController.navigate(R.id.dashboardfragment);
    }
}