package com.example.testproject.ui.Activity.user;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.ViewDataBinding;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.Util.JsonMyUtils;
import com.example.testproject.Util.ViewUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.UserRegistationBinding;
import com.example.testproject.model.AddressModel;
import com.example.testproject.model.RefModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.model.UserModel;
import com.example.testproject.ui.base.BaseActivity;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserRegistrationActivity extends BaseActivity {

    private UserRegistationBinding binding;

    //common res
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private UserDao userDao;
    //------------------------

    private String stateId,orgId,distId;
    private boolean waitForNewReq,userNameOk,firstUnameCheck;
    private List<String> distIdlist=new ArrayList<>();

    @Override
    protected void init() {
        layoutId= R.layout.user_registation;
    }

    @Override
    protected void setUpUi(Bundle savedInstanceState, ViewDataBinding viewDataBinding) {
        super.setUpUi(savedInstanceState, viewDataBinding);
        binding= (UserRegistationBinding) viewDataBinding;
        setupNetwork();
        userDao= AppDatabase.getInstance(this).userdao();
        userDao.deleteUserModel();


        ViewUtils.addAstrickLastOfText(binding.boxOrganization.txtTittle,getString(R.string.organisation));
        binding.boxOrganization.etInput.setEnabled(false);
        ViewUtils.addAstrickLastOfText(binding.boxNickname.txtTittle,getString(R.string.name));

        ViewUtils.addAstrickLastOfText(binding.boxDob.txtTittle,getString(R.string.date_of_birth));
        final Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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

        ViewUtils.addAstrickLastOfText(binding.boxFirstName.txtTittle,getString(R.string.fullname));
        ViewUtils.addAstrickLastOfText(binding.boxFatherName.txtTittle,getString(R.string.father_husband_name));


        ViewUtils.addAstrickLastOfText(binding.boxGender.txtTittle,getString(R.string.gender));
        binding.boxGender.etInput.setVisibility(View.GONE);
        binding.boxGender.spinner.setVisibility(View.VISIBLE);
        ViewUtils.fillDataInSpinner(this,binding.boxGender.spinner, Arrays.asList(getResources().getStringArray(R.array.gender)));


        ViewUtils.addAstrickLastOfText(binding.boxAddress.txtTittle,getString(R.string.address));

        ViewUtils.addAstrickLastOfText(binding.boxState.txtTittle,getString(R.string.state));
        binding.boxState.etInput.setEnabled(false);

        ViewUtils.addAstrickLastOfText(binding.boxCity.txtTittle,getString(R.string.city));

        ViewUtils.addAstrickLastOfText(binding.boxDistrict.txtTittle,getString(R.string.district));
        binding.boxDistrict.etInput.setVisibility(View.GONE);
        binding.boxDistrict.spinner.setVisibility(View.VISIBLE);

        ViewUtils.addAstrickLastOfText(binding.boxPinCode.txtTittle,getString(R.string.pincode));
        binding.boxPinCode.etInput.setInputType(EditorInfo.TYPE_CLASS_NUMBER);

        ViewUtils.addAstrickLastOfText(binding.boxUserName.txtTittle,getString(R.string.user_name_required));
        binding.boxUserName.etInput.setFocusable(true);

        ViewUtils.addAstrickLastOfText(binding.boxEmail.txtTittle,getString(R.string.email));

        ViewUtils.addAstrickLastOfText(binding.boxMobile.txtTittle,getString(R.string.mobileno));
        binding.boxPinCode.etInput.setInputType(EditorInfo.TYPE_CLASS_PHONE);


        binding.boxAltnumber.txtTittle.setText(getString(R.string.alt_number));

        ViewUtils.addAstrickLastOfText(binding.boxRole.txtTittle,getString(R.string.role));
        binding.boxRole.etInput.setVisibility(View.GONE);
        binding.boxRole.spinner.setVisibility(View.VISIBLE);
        ViewUtils.fillDataInSpinner(this,binding.boxRole.spinner, Arrays.asList(getResources().getStringArray(R.array.user_role)));

        callApis();
        addTextWatcher(binding.boxEmail.etInput,1);
        addTextWatcher(binding.boxMobile.etInput,2);
        addTextWatcher(binding.boxUserName.etInput,3);

        ViewUtils.addAstrickLastOfText(binding.boxOtp.txtTittle,getString(R.string.enterotp));
        binding.boxOtp.getRoot().setVisibility(View.GONE);
        binding.btnsave.setText(getString(R.string.getOtp));

        binding.btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.btnsave.getText().toString().contains("OTP")) {
                    callGenerateOrValidateOtpApi(true);
                }else {
                    callGenerateOrValidateOtpApi(false);
                }
            }
        });


    }
    private void addTextWatcher(TextView textView,int type){
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(type==1) {
                    if (!waitForNewReq && !userNameOk) {
                        waitForNewReq = true;
                        firstUnameCheck=true;
                        checkUserNameUniqueness(binding.boxUserName.etInput.getText().toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(type==3) {
                    if(firstUnameCheck && s.toString().length()>3) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!waitForNewReq ) {
                                    waitForNewReq = true;
                                    checkUserNameUniqueness(binding.boxUserName.etInput.getText().toString());
                                }
                            }
                        }, 1500);
                    }
                }else {
                    if (s.toString().length() == 10 && !waitForNewReq) {
                        waitForNewReq = true;
                        checkMobileUniqueness(s.toString());
                    }
                }
            }
        });
    }

    private void callApis(){
        mApiManager.commonApiWithTwoPathGet("productconfig","getdefault","","", AppConstants.StageListReq);
    }
    private void callDistApi(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.add("status", JsonMyUtils.getOnePremInJsonArr("Active"));
        jsonObject.add("state", JsonMyUtils.getOnePremInJsonArr(stateId));
        mApiManager.commonApiWithTwoPathPost("district","filter",jsonObject,"no",AppConstants.GeoFilter);
    }
    private void checkUserNameUniqueness(String s){
        if(s.isEmpty()){
            waitForNewReq=false;
            return;
        }
        mApiManager.checkUserNameUniqueness(s);
    }
    private void checkMobileUniqueness(String s){
        mApiManager.checkMobileNumberUniqueness("user","mobileNumber",s);
    }
    private void fillApiData1(RefModel model){
        binding.boxOrganization.etInput.setText(model.orgnisation.orgnisationName);
        binding.boxState.etInput.setText(model.state.name);
        orgId=model.orgnisation.orgnisationId;
        stateId=model.state.stateId;
        callDistApi();
    }
    private void fillDistData(List<String> list){
        list.set(0,"Select-District");
        ViewUtils.fillDataInSpinner(this,binding.boxDistrict.spinner, list);
    }
    private void fillUserNameUniqueness(String s){
        binding.boxUserName.txtValidation.setVisibility(View.VISIBLE);
        if(s.contains("not")){
            binding.boxUserName.txtValidation.setText(getString(R.string.user_name_available));
            binding.boxUserName.txtValidation.setTextColor(Color.parseColor("#BFBC01"));
            userNameOk=true;
        }else{
            userNameOk=false;
            binding.boxUserName.etInput.requestFocus();
            binding.boxUserName.txtValidation.setText(getString(R.string.user_name_exits));
            binding.boxUserName.txtValidation.setTextColor(Color.RED);
        }
    }
    private void fillMobileUniqueness(String s){
        binding.boxMobile.txtValidation.setVisibility(View.VISIBLE);
        if(!s.contains("failed")){
            binding.boxMobile.txtValidation.setText(getString(R.string.ok));
            binding.boxMobile.txtValidation.setTextColor(Color.parseColor("#BFBC01"));
        }else{
            binding.boxMobile.txtValidation.setText(getString(R.string.mobileno_exits));
            binding.boxMobile.txtValidation.setTextColor(Color.RED);
        }
    }
    private void showOtpUi(){
        binding.boxOtp.getRoot().setVisibility(View.VISIBLE);
    }
    private void callGenerateOrValidateOtpApi(boolean isGenerate){
        String s=validation();
        if(!s.isEmpty()){
            CommonUtils.makeToast(this,s);
        }else  {
            JsonObject mainObj = new JsonObject();
            JsonObject accessPrivilege = new JsonObject();
            accessPrivilege.addProperty("accessLevel", "");
            accessPrivilege.add("states", JsonMyUtils.getOnePremInJsonArr(""));
            accessPrivilege.add("districts", JsonMyUtils.getOnePremInJsonArr(""));
            mainObj.add("accessPrivilege", accessPrivilege);

            mainObj.addProperty("userOrg", orgId);
            mainObj.addProperty("firstName", getTxt(binding.boxFirstName.etInput));
            mainObj.addProperty("lastName", getTxt(binding.boxNickname.etInput));
            mainObj.addProperty("dateOfBirth", CommonUtils.getServerFormatDate(getTxt(binding.boxDob.etInput),"dd-MM-yyyy"));
            mainObj.addProperty("father_husbandName", getTxt(binding.boxFatherName.etInput));
            mainObj.addProperty("gender", binding.boxGender.spinner.getSelectedItem().toString());
            mainObj.addProperty("address", getTxt(binding.boxAddress.etInput));
            mainObj.addProperty("city", getTxt(binding.boxCity.etInput));
            mainObj.addProperty("district", getTxt(binding.boxDistrict.etInput));
            mainObj.addProperty("districtCode", distIdlist.get(binding.boxDistrict.spinner.getSelectedItemPosition()));
            mainObj.addProperty("state", getTxt(binding.boxState.etInput));
            mainObj.addProperty("stateCode", stateId);
            mainObj.addProperty("pinCode", Integer.parseInt(getTxt(binding.boxPinCode.etInput)));
            mainObj.addProperty("userName", getTxt(binding.boxUserName.etInput));
            mainObj.addProperty("email", getTxt(binding.boxEmail.etInput));
            mainObj.addProperty("mobileNumber", getTxt(binding.boxMobile.etInput));
            mainObj.addProperty("alternateNumber", getTxt(binding.boxAltnumber.etInput));
            mainObj.addProperty("role", binding.boxRole.spinner.getSelectedItem().toString());
            mainObj.addProperty("type", binding.boxRole.spinner.getSelectedItem().toString());
            mainObj.addProperty("isSelfRegistration", true);
            if(!isGenerate){
                mainObj.addProperty("otp", binding.boxOtp.etInput.getText().toString());
                mApiManager.registrationValidateOtp("user",mainObj);
            }else {
                mApiManager.registrationWithOtp("user",mainObj);
            }

        }
    }



    private void setupNetwork() {
        mInterFace=new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
                Toast.makeText(UserRegistrationActivity.this,errorCode,Toast.LENGTH_SHORT).show();
                waitForNewReq=false;
            }

            @Override
            public void isSuccess(Object response, int serviceCode) {
                if(serviceCode== AppConstants.StageListReq){
                    JsonObject jsonObject= (JsonObject) response;
                    JsonObject jsonObject1=JsonMyUtils.getDataFromJsonObject(jsonObject);
                    if(jsonObject1!=null){
                        RefModel model= (RefModel) CommonUtils.getPojoFromStr(RefModel.class,jsonObject1.getAsJsonObject("productconfig").toString());
                        fillApiData1(model);
                    }
                }else  if(serviceCode== AppConstants.GeoFilter){
                    JsonObject jsonObject= (JsonObject) response;
                    JsonObject jsonObject1=JsonMyUtils.getDataFromJsonObject(jsonObject);
                    if(jsonObject1!=null && jsonObject1.getAsJsonArray("data")!=null){
                        List<AddressModel> modelList= (List<AddressModel>) CommonUtils.getPojoFromStr(new TypeToken<List<AddressModel>>() {
                        }.getType(),jsonObject1.getAsJsonArray("data").toString());
                       List<String> mlist=new ArrayList<>();
                        distIdlist.add("");
                        for (int i = 0; i < modelList.size(); i++) {
                            mlist.add(modelList.get(i).name);
                            distIdlist.add(modelList.get(i).id);
                        }
                        fillDistData(mlist);
                    }
                }else if(serviceCode== AppConstants.UNIQUENESS){
                    JsonObject jsonObject= (JsonObject) response;
                    waitForNewReq=false;
                    if(jsonObject.getAsJsonObject("response")!=null){
                        String s=jsonObject.getAsJsonObject("response").get("message").toString();
                        fillUserNameUniqueness(s);
                    }
                }else if(serviceCode== AppConstants.UniqueNumber){
                    RootOneModel rootOneModel= (RootOneModel) response;
                    waitForNewReq=false;
                    if(rootOneModel.getResponse()!=null){
                        String s=CommonUtils.pojoToJson(rootOneModel.getResponse().getData());
                        if(s!=null) {
                            fillMobileUniqueness(s);
                        }
                    }
                }else if(serviceCode== AppConstants.SEND_OTP_REQUEST){
                    RootOneModel model= (RootOneModel) response;
                    if(model.getResponse().getStatusCode()==200){
                        showOtpUi();
                        CommonUtils.makeToast(UserRegistrationActivity.this,"Otp Successfully sent");
                        binding.btnsave.setText(getString(R.string.registeratn));
                    }
                }else if(serviceCode== AppConstants.REGISTRATION_REQUEST){
                    RootOneModel model= (RootOneModel) response;
                    if(model.getResponse().getStatusCode()==200){
                     showDialog(UserRegistrationActivity.this,"Registration Successful , Go to login page",false,true,11);
                    }
                }

            }
        };
        mApiManager=new ApiManager(this,mInterFace);
    }

    private String validation(){
        if(isTextEmpty(binding.boxOrganization.etInput)){
            return "Organization should not be empty";
        }else if(isTextEmpty(binding.boxFirstName.etInput)){
            return "Name should not be empty";
        }else if(isTextEmpty(binding.boxFatherName.etInput)){
            return "Father name should not be empty";
        }else if(isTextEmpty(binding.boxDob.etInput)){
            return "Date of birth should not be empty";
        }else if(isTextEmpty(binding.boxNickname.etInput)){
            return "NickName should not be empty";
        }else if(isTextEmpty(binding.boxAddress.etInput)){
            return "Address should not be empty";
        }else if(binding.boxState.spinner.getSelectedItem().toString().isEmpty()){
            return "State should not be empty";
        }else if(isTextEmpty(binding.boxCity.etInput)){
            return "City should not be empty";
        }else if(isTextEmpty(binding.boxPinCode.etInput)){
            return "PinCode should not be empty";
        }else if(binding.boxDistrict.spinner.getSelectedItem().toString().isEmpty()){
            return "District should not be empty";
        }else if(isTextEmpty(binding.boxUserName.etInput)){
            return "Username should not be empty";
        }else if(isTextEmpty(binding.boxEmail.etInput) && !CommonUtils.isValidEmail(binding.boxEmail.etInput.getText().toString())){
            return "Email should not be empty And it should be a valid email ";
        }else if(isTextEmpty(binding.boxMobile.etInput ) && !CommonUtils.isValidPhoneNumber(binding.boxMobile.etInput.getText().toString())){
            return "Mobile no should not be empty And it should be a valid number";
        }else if(binding.boxGender.spinner.getSelectedItemPosition()==0){
            return "Please select gender";
        }else if(binding.boxDistrict.spinner.getSelectedItemPosition()==0){
            return "Please select district";
        }else if(binding.boxRole.spinner.getSelectedItemPosition()==0){
            return "Please select role";
        }
        return "";
    }
    private boolean isTextEmpty(Object o){
        if(o instanceof TextView){
          return ((TextView) o).getText().toString().isEmpty() || ((TextView) o).getText().toString().length() < 3;
        }else {
            return ((EditText) o).getText().toString().isEmpty() || ((EditText) o).getText().toString().length() < 3;
        }
    }
    private String getTxt(EditText editText){
        return editText.getText().toString();
    }

    private void goToNewActivity(){
        startActivity(new Intent(this, UserLoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToNewActivity();
        finish();
    }

    @Override
    public void okDialogClick(int Id) {
        if(Id==11){
            startActivity(new Intent(this,UserLoginActivity.class));
            finish();
        }
    }

    @Override
    public void cancelDialogClick(int Id) {

    }

}
