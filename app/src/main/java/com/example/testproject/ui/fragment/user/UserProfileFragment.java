package com.example.testproject.ui.fragment.user;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.ViewDataBinding;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.model.UserModel;
import com.google.gson.JsonObject;
import com.example.testproject.R;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.databinding.UserProfileFragmentBinding;
import com.example.testproject.ui.base.BaseFragment;
import com.example.testproject.util.AppConstants;
import com.example.testproject.util.CommonUtils;
import com.example.testproject.util.JsonMyUtils;
import com.example.testproject.util.ViewUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by DL on 10-09-2022.
 */
public class UserProfileFragment extends BaseFragment {
    private static final String TAG = "Profile";



    private UserProfileFragmentBinding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private UserDao userDao;
    private JsonObject upJson;


    public static UserProfileFragment newInstance(Bundle args) {
        UserProfileFragment fragment = new UserProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        layoutId = R.layout.user_profile_fragment;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (UserProfileFragmentBinding) viewDataBinding;
        getActivity().setTitle(getString(R.string.profile));
        setupNetwork();
        userDao = AppDatabase.getInstance(getContext()).userdao();



        final Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog StartTime = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String myFormat="dd-MM-yyyy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                binding.boxDob.etInput.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        StartTime.getDatePicker().setMaxDate(new Date().getTime());
          binding.boxDob.etInput.setEnabled(false);
        binding.boxDob.layInputBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime.show();
            }
        });

        ViewUtils.addAstrickLastOfText(binding.boxFirstName.txtTittle,getString(R.string.first_name));
        ViewUtils.addAstrickLastOfText(binding.boxLastName.txtTittle,getString(R.string.last_name));
        ViewUtils.addAstrickLastOfText(binding.boxFatherName.txtTittle,getString(R.string.father_husband_name));
        ViewUtils.addAstrickLastOfText(binding.boxDob.txtTittle,getString(R.string.date_of_birth));



        ViewUtils.addAstrickLastOfText(binding.boxGender.txtTittle,getString(R.string.gender));
        binding.boxGender.etInput.setVisibility(View.GONE);
        binding.boxGender.spinner.setVisibility(View.VISIBLE);
        ViewUtils.fillDataInSpinner(getContext(),binding.boxGender.spinner, Arrays.asList(getResources().getStringArray(R.array.gender)));


        ViewUtils.addAstrickLastOfText(binding.boxAddress.txtTittle,getString(R.string.address));


//        ViewUtils.addAstrickLastOfText(binding.boxUserName.txtTittle,getString(R.string.user_name_required));
//        binding.boxUserName.etInput.setFocusable(true);

        ViewUtils.addAstrickLastOfText(binding.boxEmail.txtTittle,getString(R.string.email));

        ViewUtils.addAstrickLastOfText(binding.boxMobile.txtTittle,getString(R.string.mobileno));



        binding.boxAltnumber.txtTittle.setText(getString(R.string.alt_number));

//        ViewUtils.addAstrickLastOfText(binding.boxRole.txtTittle,getString(R.string.role));
//        binding.boxRole.etInput.setVisibility(View.GONE);
//        binding.boxRole.spinner.setVisibility(View.VISIBLE);
//        ViewUtils.fillDataInSpinner(this,binding.boxRole.spinner, Arrays.asList(getResources().getStringArray(R.array.user_role)));



        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.boxFirstName.etInput.isEnabled()){
                    apiQueryCountCall();

                }else {
                    fillProfileData(true);
                    binding.btnSave.setText(getString(R.string.update));
                }
            }
        });

        fillProfileData(false);

    }
    private void apiQueryCountCall(){
        String str=validation();
        if(str.isEmpty()){
            CommonUtils.makeToast(getContext(),str);
            return;
        }
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("id",userDao.getUserResponse().id);
        jsonObject.addProperty("firstName",binding.boxFirstName.etInput.getText().toString());
        jsonObject.addProperty("lastName",binding.boxLastName.etInput.getText().toString());
        jsonObject.addProperty("father_husbandName",binding.boxFatherName.etInput.getText().toString());
        jsonObject.addProperty("dateOfBirth",CommonUtils.getServerFormatDate(binding.boxDob.etInput.getText().toString(),"dd-MM-yyyy"));
        jsonObject.addProperty("gender",binding.boxGender.spinner.getSelectedItem().toString());
        jsonObject.addProperty("mobileNumber",binding.boxMobile.etInput.getText().toString());
        jsonObject.addProperty("alternateNumber",binding.boxAltnumber.etInput.getText().toString());
        jsonObject.addProperty("email",binding.boxEmail.etInput.getText().toString());
        upJson=jsonObject;
        mApiManager.commonOnePathApi("user",jsonObject,null,AppConstants.SUBMITREQ,AppConstants.METHOD_PUT);
    }
    private void updateLocaldb(){
        if(upJson==null){
            return;
        }
        UserModel user=userDao.getUserResponse();
        user.firstName=upJson.get("firstName").getAsString();
        user.lastname=upJson.get("lastName").getAsString();
        user.fatherHusbandName=upJson.get("father_husbandName").getAsString();
        user.dateOfBirth=upJson.get("dateOfBirth").getAsString();
        user.gender=upJson.get("gender").getAsString();
        user.mobileNumber=upJson.get("mobileNumber").getAsString();
        user.alternateNumber=upJson.get("alternateNumber").getAsString();
        user.email=upJson.get("email").getAsString();
        userDao.update(user);
        upJson=null;
    }
    @SuppressLint("ResourceAsColor")
    private void fillDataInEd(EditText editText, String txt, boolean enable, ConstraintLayout cl){
        if (txt.isEmpty()){
            editText.setText("N/A");
        }else {
            editText.setText(txt);
        }
        if (enable){
            cl.setBackgroundTintList(ColorStateList.valueOf(R.color.lyt_green));
            binding.boxGender.layInputBox.setBackgroundTintList(ColorStateList.valueOf(R.color.lyt_green));

        }else {
            cl.setBackgroundTintList(ColorStateList.valueOf(R.color.graycolor));
            binding.boxGender.layInputBox.setBackgroundTintList(ColorStateList.valueOf(R.color.graycolor));


        }

        editText.setEnabled(enable);
    }

    private void fillProfileData(boolean isEnable){
        UserModel model=userDao.getUserResponse();

        fillDataInEd(binding.boxFirstName.etInput,model.firstName,isEnable,binding.boxFirstName.layInputBox);
        fillDataInEd(binding.boxLastName.etInput,model.lastname,isEnable,binding.boxLastName.layInputBox);
        fillDataInEd(binding.boxFatherName.etInput,model.fatherHusbandName,isEnable,binding.boxFatherName.layInputBox);
        fillDataInEd(binding.boxEmail.etInput,model.email,isEnable,binding.boxEmail.layInputBox);
        fillDataInEd(binding.boxMobile.etInput,model.mobileNumber,isEnable,binding.boxMobile.layInputBox);
        fillDataInEd(binding.boxAltnumber.etInput,model.alternateNumber,isEnable,binding.boxAltnumber.layInputBox);
        fillDataInEd(binding.boxAddress.etInput,model.address,isEnable,binding.boxAddress.layInputBox);
        fillDataInEd(binding.boxDob.etInput,CommonUtils.getOnlyDateFormat(model.dateOfBirth),isEnable,binding.boxDob.layInputBox);

        if(model.gender!=null && model.gender.equals("Male")){
            binding.boxGender.spinner.setSelection(1);
        }else if(model.gender!=null && model.gender.equals("Female")){
            binding.boxGender.spinner.setSelection(2);
        }else if(model.gender!=null && model.gender.equals("Others")){
            binding.boxGender.spinner.setSelection(3);
        }
        binding.boxGender.spinner.setVisibility(View.VISIBLE);
        binding.boxGender.etInput.setVisibility(View.GONE);
        binding.boxGender.spinner.setEnabled(isEnable);
        if(isEnable){
            binding.boxAddress.getRoot().setVisibility(View.GONE);
        }else {
            binding.boxAddress.getRoot().setVisibility(View.VISIBLE);
        }
    }
    private String validation() {
        if (binding.boxFirstName.etInput.getText().toString().isEmpty()) {
            return " first name should not be empty";
        }else if (binding.boxLastName.etInput.getText().toString().isEmpty()) {
            return " last name should not be empty";
        }else if (binding.boxFatherName.etInput.getText().toString().isEmpty()) {
            return " father/husband name should not be empty";
        }else if (binding.boxMobile.etInput.getText().toString().isEmpty()) {
            return " mobile number should not be empty";
        }else if (binding.boxGender.spinner.getSelectedItemPosition()>0) {
            return " choose gender";
        }else {
            return "";
        }
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
                if(ServiceCode==AppConstants.UserModule) {
                    RootOneModel rootModel = (RootOneModel) response;
                    JsonObject jsonObject=rootModel.getResponse().getData().user.getAsJsonObject();
                    if(jsonObject!=null && jsonObject.has("user")) {
                        String sm = jsonObject.getAsJsonObject("user").toString();
                        UserModel model = (UserModel) CommonUtils.getPojoFromStr(UserModel.class, sm);
                    }
                }else if(ServiceCode==AppConstants.SUBMITREQ) {
                    JsonObject jsonObject= (JsonObject) response;
                    if(JsonMyUtils.isResponseSuccess(jsonObject)){
                        showDialog(getActivity(),getString(R.string.update_successful),false,true,1);
                        binding.btnSave.setText(getString(R.string.edit));
                        updateLocaldb();
                    }
                    fillProfileData(false);
                }
            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }
    @Override
    public void onBackCustom() {
        getFragmentManager().popBackStack();
    }

}