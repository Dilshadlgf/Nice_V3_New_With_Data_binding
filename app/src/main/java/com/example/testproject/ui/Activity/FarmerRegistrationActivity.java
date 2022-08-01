package com.example.testproject.ui.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.databinding.ActivityRegistrationBinding;
import com.example.testproject.model.DataModelTwo;
import com.example.testproject.model.FarmerDataModel;
import com.example.testproject.model.GeneralResponseModel;
import com.example.testproject.model.RootOneResModel;
import com.example.testproject.model.SingleObjectModel.SingleObjRootOneResModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FarmerRegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    private String errorMessage = "";
    TextView back, sp_organization;
    AlertDialog alertDialog;
    CheckBox checkBox,checkBox_wOtp;
    ImageView imgdrpdwngram, imgaddgram, imgdropdwnvill, imgaddvill, imgdropdwnblok, imgaddblok;
    EditText et_full_name, et_father_name, et_mobile_number, et_otp_pswd, et_email, edtblock, edtvill;
    private HashMap<String, String> spinnerStateMap;
    private HashMap<String, String> spinnerDistrictMap;
    private HashMap<String, String> spinnerBlockMap;
    private HashMap<String, String> spinnerGrampanchayatMap;
    private HashMap<String, String> spinnerVillageMap;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    GeneralResponseModel otpres;
    Spinner spstate, spDistrict, spBlock, spGrampanchayat, spVillage, sp_gender;
    Button btn_save, btnotp, nextbtn,otpnextbtn,ed_dob,submitOtpBtn;
    LinearLayout mainlyout, mobile_lyout;
    String MobilePattern = "[0-9]{10}";
    String selectedItemText;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final int REQ_CODE_SPEECH_INPUT_fathername = 101;
    private final int REQ_CODE_SPEECH_INPUT_mob = 102;
    ImageView voicetotxt, voicetotxtt;
    RelativeLayout otp_lyout;
    String speechtxt = "Welcome to  Registration";
    //    String speechtxt = "Welcome to Nicessm Registration, Please Click Mike for Voice based Registration";
    String enternametxt = "Please Enter Your Full Name";
    TextToSpeech texttospeech;
    public ListView mList;
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
     private TextView exits_mob_text;
    private long nextSpinnerId;
    private boolean isMobileNoExits,checkMobApiHit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setupNetwork();
        speechtxt="Welcome to "+getString(R.string.app_name)+" Registration";
        back = (TextView) findViewById(R.id.back);
        checkBox=(CheckBox)findViewById(R.id.checkBox);
        checkBox_wOtp=(CheckBox)findViewById(R.id.checkBox_W_OTP);
        mainlyout = (LinearLayout) findViewById(R.id.mainlyout);
        mobile_lyout = (LinearLayout) findViewById(R.id.mobile_lyout);
        mainlyout = (LinearLayout) findViewById(R.id.mainlyout);
        imgdrpdwngram = (ImageView) findViewById(R.id.imgdrpdwngram);
        imgaddblok = (ImageView) findViewById(R.id.imgaddblok);
        imgaddgram = (ImageView) findViewById(R.id.imgaddgram);
        imgdropdwnblok = (ImageView) findViewById(R.id.imgdropdwnblok);
        imgdropdwnvill = (ImageView) findViewById(R.id.imgdropdwnvill);
        imgaddvill = (ImageView) findViewById(R.id.imgaddvill);
        exits_mob_text = (TextView) findViewById(R.id.mob_exits_text);
        et_otp_pswd = (EditText) findViewById(R.id.et_otp_pswd);
        et_full_name = (EditText) findViewById(R.id.et_full_name);
        et_father_name = (EditText) findViewById(R.id.et_father_name);
        et_mobile_number = (EditText) findViewById(R.id.et_mobile_number);
        et_email = (EditText) findViewById(R.id.et_email);
        ed_dob = (Button) findViewById(R.id.et_dob_);
        submitOtpBtn = (Button) findViewById(R.id.submitOtpBtn);
        sp_organization = (TextView) findViewById(R.id.sp_organization);
        sp_gender = (Spinner) findViewById(R.id.sp_gender);
        spstate = (Spinner) findViewById(R.id.sp_state);
        spDistrict = (Spinner) findViewById(R.id.sp_district);
        spBlock = (Spinner) findViewById(R.id.sp_block);
        spGrampanchayat = (Spinner) findViewById(R.id.sp_grampanchayat);
        spstate = (Spinner) findViewById(R.id.sp_state);
        spVillage = (Spinner) findViewById(R.id.sp_village);
        btn_save = (Button) findViewById(R.id.btnsave);
        nextbtn = (Button) findViewById(R.id.nextbtn);
        btnotp = (Button) findViewById(R.id.btnotp);
        otp_lyout = (RelativeLayout) findViewById(R.id.otp_lyout);
        voicetotxt = (ImageView) findViewById(R.id.voice_to_txt);
        voicetotxtt = (ImageView) findViewById(R.id.voice_to_txtt);
        otpnextbtn=(Button) findViewById(R.id.otpnextbtn);



//        final Calendar newCalendar = Calendar.getInstance();
//        final DatePickerDialog  StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
//                String myFormat="dd-MM-yyyy";
//                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
//                ed_dob.setText(dateFormat.format(newDate.getTime()));
//
//
//            }
//
//        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//        StartTime.getDatePicker().setMaxDate(new Date().getTime());
//        ed_dob.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                StartTime.show();
//            }
//        });

        voicetotxtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormSpeech();

            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_mobile_number.getText().toString().trim())) {
//                if(TextUtils.isEmpty(et_mobile_number.getText().toString().trim())||et_mobile_number.getText().toString().length()<10){
                    AlertDialog.Builder builder = new AlertDialog.Builder(FarmerRegistrationActivity.this);
                    builder.setMessage("Please Enter Mobile Number");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();

                } else if (et_mobile_number.getText().toString().length() < 10) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FarmerRegistrationActivity.this);
                    builder.setMessage("Please Enter Valid Mobile Number");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();

                } else {
                    mApiManager.MobnovalidRequest(et_mobile_number.getText().toString());


                }
            }
        });


        texttospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int lang = texttospeech.setLanguage(Locale.ENGLISH);
                    int spechtext = texttospeech.speak(speechtxt, TextToSpeech.QUEUE_FLUSH, null);
//                   FormSpeech();

                }
            }
        });

        spstate.setOnItemSelectedListener(this);
        spDistrict.setOnItemSelectedListener(this);
        spBlock.setOnItemSelectedListener(this);
        spGrampanchayat.setOnItemSelectedListener(this);
        spVillage.setOnItemSelectedListener(this);



        List<String> genderlist = new ArrayList<String>();
        genderlist.add("--Select Gender--");
        genderlist.add("Male");
        genderlist.add("Female");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_textview, genderlist);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.textview);
        // attaching data adapter to spinner
        sp_gender.setAdapter(dataAdapter);

        sp_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemText = (String) parent.getItemAtPosition(position);
                if(position==0)
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.graycolor));
                // Notify the selected item text
//        Toast.makeText
//                (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
//                .show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerStateMap = new HashMap<>();
        spinnerDistrictMap = new HashMap<>();
        spinnerBlockMap = new HashMap<>();
        spinnerGrampanchayatMap = new HashMap<>();
        spinnerVillageMap = new HashMap<>();


//        mApiManager.activeStatesListRequest();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrationIntent = new Intent(FarmerRegistrationActivity.this, FarmerLoginActivity.class);
                startActivity(registrationIntent);
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    Locale.setDefault(Locale.ENGLISH);

                    JsonObject regreq = new JsonObject();
                    regreq.addProperty("name", et_full_name.getText().toString());
                    regreq.addProperty("fatherName", et_father_name.getText().toString());
                    regreq.addProperty("gender", selectedItemText);
                    regreq.addProperty("mobileNumber", et_mobile_number.getText().toString());
//                    regreq.addProperty("state", spinnerStateMap.get(spstate.getSelectedItem().toString()));

//                    regreq.addProperty("block", spinnerBlockMap.get(spBlock.getSelectedItem().toString()));
                    regreq.addProperty("registrationType", "android");
                    regreq.addProperty("state", spinnerStateMap.get(spVillage.getSelectedItemPosition()+""));
                    regreq.addProperty("district", spinnerDistrictMap.get(spVillage.getSelectedItemPosition()+""));
                    regreq.addProperty("block", spinnerBlockMap.get(spVillage.getSelectedItemPosition()+""));
                    regreq.addProperty("gramPanchayat", spinnerGrampanchayatMap.get(spVillage.getSelectedItemPosition()+""));
                    regreq.addProperty("village", spinnerVillageMap.get(spVillage.getSelectedItemPosition()+""));
//                       regreq.addProperty("isOnline", true);
//                       mApiManager.RegistrationRequest(regreq);
                    if(checkBox_wOtp.isChecked()) {
                        mApiManager.registrationWithOtp(regreq);
                        checkBox_wOtp.setEnabled(false);
                    }else {
                        mApiManager.RegistrationRequest(regreq);
                    }

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(FarmerRegistrationActivity.this);
                    builder.setMessage(errorMessage);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();

                }
            }
        });
        checkBox_wOtp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    btn_save.setText("Send OTP");
                }else {
                    btn_save.setText("Register");
                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {


                    if (isValid()) {
                        btn_save.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    btn_save.setVisibility(View.VISIBLE);
                }
            }

        });
        nextSpinnerId = spVillage.getId();
        callFilterApi("village", "", "");

        et_mobile_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==10 && !checkMobApiHit){
                    checkMobApiHit=true;
                    mApiManager.checkMobileNumberUniqueness("farmer", "mobileNumber", et_mobile_number.getText().toString());
                }
            }
        });


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            if (parent.getId() == spstate.getId()) {

                nextSpinnerId = spDistrict.getId();
                callFilterApi("district", "state", spinnerStateMap.get(spstate.getSelectedItem().toString()));

            } else if (parent.getId() == spDistrict.getId()) {
                nextSpinnerId = spBlock.getId();
                callFilterApi("block", "district", spinnerDistrictMap.get(spDistrict.getSelectedItem().toString()));
            } else if (parent.getId() == spBlock.getId()) {
                nextSpinnerId = spGrampanchayat.getId();
                callFilterApi("grampanchayat", "block", spinnerBlockMap.get(spBlock.getSelectedItem().toString()));
            } else if (parent.getId() == spGrampanchayat.getId()) {
                nextSpinnerId = spVillage.getId();
                callFilterApi("village", "grampanchayat", spinnerGrampanchayatMap.get(spGrampanchayat.getSelectedItem().toString()));
            } else if (parent.getId() == spVillage.getId()) {
//                nextSpinnerId = spVillage.getId();
//                callFilterApi("district", "state", spinnerVillageMap.get(spVillage.getSelectedItem().toString()));
            }
        }else{
            if (parent.getId() == spstate.getId()) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.graycolor));
            }
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void callFilterApi(String path,String key,String value){
        JsonObject mainObj=new JsonObject();
        JsonArray statusArr=new JsonArray();
        statusArr.add("Active");
//        JsonArray array=new JsonArray();
//        array.add(value);
        mainObj.add("status",statusArr);
//        mainObj.add(key,array);
        mApiManager.geoFilter(path,mainObj);
    }

    private void makeSpinner(long spId, List<DataModelTwo> dataModelTwos){
        List<String> districlist = new ArrayList<String>();

        districlist.add("---Select Village---");
        for (int i = 0; i < dataModelTwos.size(); i++) {
            DataModelTwo modelTwo=dataModelTwos.get(i);
            districlist.add(modelTwo.getName()+" [ "+modelTwo.getRef().getDistrict().getName()+" ]");
            if(spId==spstate.getId()){
                spinnerStateMap.put(modelTwo.getName(),modelTwo.getId());
            }else if(spId==spDistrict.getId()){
                spinnerDistrictMap.put(modelTwo.getName(),modelTwo.getId());
            }else if(spId==spBlock.getId()){
                spinnerBlockMap.put(modelTwo.getName(),modelTwo.getId());
            }else if(spId==spGrampanchayat.getId()){
                spinnerGrampanchayatMap.put(modelTwo.getName(),modelTwo.getId());
            }else if(spId==spVillage.getId()){
                spinnerVillageMap.put(""+(i+1),modelTwo.getId());
                spinnerStateMap.put(""+(i+1),modelTwo.getRef().getState().getId());
                spinnerDistrictMap.put(""+(i+1),modelTwo.getRef().getDistrict().getId());
                spinnerBlockMap.put(""+(i+1),modelTwo.getRef().getBlock().getId());
                spinnerGrampanchayatMap.put(""+(i+1),modelTwo.getRef().getGramPanchayat().getId());
            }
        }
//        districlist.add("---Select District---");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, R.layout.spinner_textview, districlist);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(R.layout.textview);
        // attaching data adapter to spinner
        if(spId==spstate.getId()){
            spstate.setAdapter(dataAdapter2);
        }else if(spId==spDistrict.getId()){
            spDistrict.setAdapter(dataAdapter2);
        }else if(spId==spBlock.getId()){
            spBlock.setAdapter(dataAdapter2);
        }else if(spId==spGrampanchayat.getId()){
            spGrampanchayat.setAdapter(dataAdapter2);
        }else if(spId==spVillage.getId()){
            spVillage.setAdapter(dataAdapter2);
        }


    }

    private void FormSpeech() {
        if (TextUtils.isEmpty(et_full_name.getText().toString().trim())) {
            texttospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if (i == TextToSpeech.SUCCESS) {
/*                        et_father_name.setFocusable(false);
                        et_father_name.setFocusableInTouchMode(false);*/
                        int lang = texttospeech.setLanguage(Locale.ENGLISH);
                        int spechtext = texttospeech.speak(enternametxt, TextToSpeech.QUEUE_FLUSH, null);
                        new Handler().postDelayed(() ->
                                {
                                    promptSpeechInput();
                                },
                                3000); //time in ms
                    }
                }
            });
        } else {
            et_father_name.setFocusable(true);
            et_father_name.setFocusableInTouchMode(true);

            FormSpeechfathername();
        }
    }

    private void FormSpeechfathername() {
        if (TextUtils.isEmpty(et_father_name.getText().toString().trim())) {
            texttospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if (i == TextToSpeech.SUCCESS) {
//                        et_father_name.setFocusable(false);
//                        et_father_name.setFocusableInTouchMode(false);
                        int lang = texttospeech.setLanguage(Locale.ENGLISH);
                        int spechtext = texttospeech.speak("Enter Your Father Name", TextToSpeech.QUEUE_FLUSH, null);
                        new Handler().postDelayed(() ->
                                {
                                    promptSpeechInputfather();
                                },
                                2000); //time in ms


                    }
                }
            });
        } else {
            FormSpeechMobileno();
        }
    }

    private void FormSpeechMobileno() {
        if (TextUtils.isEmpty(et_mobile_number.getText().toString().trim())) {
            texttospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if (i == TextToSpeech.SUCCESS) {
//                        et_father_name.setFocusable(false);
//                        et_father_name.setFocusableInTouchMode(false);
                        int lang = texttospeech.setLanguage(Locale.ENGLISH);
                        int spechtext = texttospeech.speak("Enter Your Mobile Number", TextToSpeech.QUEUE_FLUSH, null);
                        new Handler().postDelayed(() ->
                                {
                                    promptSpeechInputmobileno();
                                },
                                2000); //time in ms

                    }
                }
            });
        }
    }

    private void FormSpeechselectgender() {
//        if (TextUtils.isEmpty(et_mobile_number.getText().toString().trim())) {
        texttospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
//                        et_father_name.setFocusable(false);
//                        et_father_name.setFocusableInTouchMode(false);
                    int lang = texttospeech.setLanguage(Locale.ENGLISH);
                    int spechtext = texttospeech.speak("Select Gender", TextToSpeech.QUEUE_FLUSH, null);
                    new Handler().postDelayed(() ->
                            {

                                if (spstate.getSelectedItemPosition() == 0 && sp_gender.getSelectedItemPosition() != 0) {

                                    FormSpeechselectstate();
                                }
                            },
                            4000); //time in ms


                }


            }
        });
//      }
    }

    private void FormSpeechselectstate() {
//        if (TextUtils.isEmpty(et_mobile_number.getText().toString().trim())) {
        texttospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
//                        et_father_name.setFocusable(false);
//                        et_father_name.setFocusableInTouchMode(false);
                    int lang = texttospeech.setLanguage(Locale.ENGLISH);
                    int spechtext = texttospeech.speak("Select State", TextToSpeech.QUEUE_FLUSH, null);
                      /*  new Handler().postDelayed(() ->
                                {

                                    if(spDistrict.getSelectedItemPosition() == 0 ||spstate.getSelectedItemPosition() != 0){
//                        FormSpeechselectDistrict();
                                    }
                                },
                                4000); //time in ms
*/
                }
            }
        });
//      }
    }

    private void FormSpeechselectDistrict() {
//        if (TextUtils.isEmpty(et_mobile_number.getText().toString().trim())) {
        texttospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
//                        et_father_name.setFocusable(false);
//                        et_father_name.setFocusableInTouchMode(false);
                    int lang = texttospeech.setLanguage(Locale.ENGLISH);
                    int spechtext = texttospeech.speak("Select District", TextToSpeech.QUEUE_FLUSH, null);
                     /*   new Handler().postDelayed(() ->
                                {

                                    if(spBlock.getSelectedItemPosition() == 0 && spDistrict.getSelectedItemPosition() != 0 ){
                        FormSpeechselectblock();
                                    }
                                },
                                4000); //time in ms
*/
                }
            }
        });
//      }
    }

    private void FormSpeechselectblock() {
//        if (TextUtils.isEmpty(et_mobile_number.getText().toString().trim())) {
        texttospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
//                        et_father_name.setFocusable(false);
//                        et_father_name.setFocusableInTouchMode(false);
                    int lang = texttospeech.setLanguage(Locale.ENGLISH);
                    int spechtext = texttospeech.speak("Select Block", TextToSpeech.QUEUE_FLUSH, null);
                     /*   new Handler().postDelayed(() ->
                                {

                                    if(spGrampanchayat.getSelectedItemPosition() == 0&&spBlock.getSelectedItemPosition() != 0 ){
                        FormSpeechselectgrampanchyt();
                                    }
                                },
                                4000); //time in ms
*/
                }
            }
        });
//      }
    }

    private void FormSpeechselectgrampanchyt() {
//        if (TextUtils.isEmpty(et_mobile_number.getText().toString().trim())) {
        texttospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
//                        et_father_name.setFocusable(false);
//                        et_father_name.setFocusableInTouchMode(false);
                    int lang = texttospeech.setLanguage(Locale.ENGLISH);
                    int spechtext = texttospeech.speak("Select Grampanchayat", TextToSpeech.QUEUE_FLUSH, null);
                      /*  new Handler().postDelayed(() ->
                                {

                                    if(spVillage.getSelectedItemPosition() == 0 &&spGrampanchayat.getSelectedItemPosition()!=0 ){
                        FormSpeechselectvillage();
                                    }
                                },
                                4000); //time in ms
*/
                }
            }
        });
//      }
    }

    private void FormSpeechselectvillage() {
//        if (TextUtils.isEmpty(et_mobile_number.getText().toString().trim())) {
        texttospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
//                        et_father_name.setFocusable(false);
//                        et_father_name.setFocusableInTouchMode(false);
                    int lang = texttospeech.setLanguage(Locale.ENGLISH);
                    int spechtext = texttospeech.speak("Select Village", TextToSpeech.QUEUE_FLUSH, null);
                    new Handler().postDelayed(() ->
                            {

                                if (spVillage.getSelectedItemPosition() != 0) {
                                    FormSpeechagreefornicetrms();
                                }
                            },
                            4000); //time in ms

                }
            }
        });
//      }
    }

    private void FormSpeechagreefornicetrms() {
//        if (TextUtils.isEmpty(et_mobile_number.getText().toString().trim())) {
        texttospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
//                        et_father_name.setFocusable(false);
//                        et_father_name.setFocusableInTouchMode(false);
                    int lang = texttospeech.setLanguage(Locale.ENGLISH);
                    int spechtext = texttospeech.speak("I Agree to share data with NICE", TextToSpeech.QUEUE_FLUSH, null);
                       /* new Handler().postDelayed(() ->
                                {

//                                    if(spVillage.getSelectedItemPosition() != 0 ){
                        FormSpeechagreefornicetrms();
//                                    }
                                },
                                4000); //time in ms
*/
                }
            }
        });
//      }
    }

    private void FormSpeechotp() {
        if (TextUtils.isEmpty(et_otp_pswd.getText().toString().trim())) {
            texttospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if (i == TextToSpeech.SUCCESS) {
//                        et_father_name.setFocusable(false);
//                        et_father_name.setFocusableInTouchMode(false);
                        int lang = texttospeech.setLanguage(Locale.ENGLISH);
                        int spechtext = texttospeech.speak("Enter OTP", TextToSpeech.QUEUE_FLUSH, null);

                    }
                }
            });
        }
    }


    private boolean isValid() {

        if (TextUtils.isEmpty(et_full_name.getText().toString().trim())) {
            errorMessage = getString(R.string.name_required);
            return false;
        }else if(!et_full_name.getText().toString().matches("[a-zA-Z ]+")){
            errorMessage = getString(R.string.name_error);
            return false;
        }
        else if (TextUtils.isEmpty(et_father_name.getText().toString().trim())) {
            errorMessage = getString(R.string.father_name_required);
            return false;
        }
//        else if (TextUtils.isEmpty(ed_dob.getText().toString().trim())) {
//            errorMessage = getString(R.string.dob_required);
//            return false;
//        }
        else if(!et_father_name.getText().toString().matches("[a-zA-Z ]+")){
            errorMessage = getString(R.string.fathername_error);
            return false;
        }
        else if (sp_gender.getSelectedItemPosition() == 0) {
            errorMessage = getString(R.string.gender_required);
            return false;
        }
        else if (TextUtils.isEmpty(et_mobile_number.getText().toString().trim())) {
            errorMessage = ("mobile number required");
            return false;
        }
        else if (!et_mobile_number.getText().toString().matches(MobilePattern)) {
            errorMessage = ("mobile number is not valid");
            Toast.makeText(getApplicationContext(), "mobile number is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }

//        else if ((spstate.getSelectedItemPosition() == 0)) {
//            FormSpeechselectstate();
//
//            errorMessage = ("State is Required");
//            return false;
//        } else if ((spDistrict.getSelectedItemPosition() == 0)) {
//            FormSpeechselectDistrict();
//
//            errorMessage = ("District is Required");
//            return false;
//        } else if ((spBlock.getSelectedItemPosition() == 0)) {
//            FormSpeechselectblock();
//
//            errorMessage = ("Block is Required");
//            return false;
//        } else if ((spGrampanchayat.getSelectedItemPosition() == 0)) {
//            FormSpeechselectgrampanchyt();
//
//            errorMessage = ("Grampanchayat is Required");
//            return false;
//        }
        else if ((spVillage.getSelectedItemPosition() == 0)) {
            FormSpeechselectvillage();

            errorMessage = ("Village is Required");
            return false;
        } else {
            return true;
        }
    }




    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
                showDialog(FarmerRegistrationActivity.this, errorCode, false, true, 0);

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if(ServiceCode==AppConstants.MobvalidationRequst){
                    SingleObjRootOneResModel resModel= (SingleObjRootOneResModel) response;
                    if(resModel.getResponse().getStatusCode()==200 && resModel.getResponse().getData().getDuplicate().equals("success")){
                        mainlyout.setVisibility(View.VISIBLE);
                        //get state list
                        nextSpinnerId=spstate.getId();
                        callFilterApi("state","","");
                    }else {
                        showDialog(FarmerRegistrationActivity.this,"Mobile number already exists",true,false,0);
                    }

                }else if(ServiceCode==AppConstants.GeoFilter){
                    RootOneResModel rootOneResModel= (RootOneResModel) response;
                    if(rootOneResModel.getResponse().getDataModel2().getData()!=null){
                        makeSpinner(nextSpinnerId,rootOneResModel.getResponse().getDataModel2().getData());
                    }

                }else if(ServiceCode==AppConstants.REGISTRATION_REQUEST) {
                    SingleObjRootOneResModel resModel = (SingleObjRootOneResModel) response;
                    if (resModel.getResponse().getData()== null) {
                        showDialog(FarmerRegistrationActivity.this,"Error Or Invalid Otp",false,true,0);
                    } else{
                        showDialog(FarmerRegistrationActivity.this, getString(R.string.registration_success), false, false, 1);
                    }
                }else if(ServiceCode==AppConstants.UniqueNumber){
                    SingleObjRootOneResModel resModel= (SingleObjRootOneResModel) response;
                    checkMobApiHit=false;
                    if(resModel.getResponse().getData().getDuplicate().equalsIgnoreCase("failed")){//duplicate found
                        back.setVisibility(View.VISIBLE);
                        exits_mob_text.setVisibility(View.VISIBLE);

//                        showDialog(Farmer_Registration_Activity.this,"Mobile number exits",false,true,0);
                    }else {
                        exits_mob_text.setVisibility(View.GONE);
                    }
                }else if(ServiceCode==AppConstants.SEND_OTP_REQUEST){
                    SingleObjRootOneResModel resModel= (SingleObjRootOneResModel) response;
                    if(resModel.getResponse().getData().getOtp()!=null){
                        Toast.makeText(FarmerRegistrationActivity.this,"Enter Otp",Toast.LENGTH_SHORT).show();
                        submitOtpBtn.setVisibility(View.VISIBLE);
                        btn_save.setVisibility(View.GONE);
                        otp_lyout.setVisibility(View.VISIBLE);
                        submitOtpBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                JsonObject regreq = new JsonObject();
                                regreq.addProperty("name", et_full_name.getText().toString());
                                regreq.addProperty("fatherName", et_father_name.getText().toString());
                                regreq.addProperty("gender", selectedItemText);
                                regreq.addProperty("mobileNumber", et_mobile_number.getText().toString());
//                    regreq.addProperty("state", spinnerStateMap.get(spstate.getSelectedItem().toString()));

//                    regreq.addProperty("block", spinnerBlockMap.get(spBlock.getSelectedItem().toString()));
                                regreq.addProperty("registrationType", "android");
                                regreq.addProperty("state", spinnerStateMap.get(spVillage.getSelectedItemPosition()+""));
                                regreq.addProperty("district", spinnerDistrictMap.get(spVillage.getSelectedItemPosition()+""));
                                regreq.addProperty("block", spinnerBlockMap.get(spVillage.getSelectedItemPosition()+""));
                                regreq.addProperty("gramPanchayat", spinnerGrampanchayatMap.get(spVillage.getSelectedItemPosition()+""));
                                regreq.addProperty("village", spinnerVillageMap.get(spVillage.getSelectedItemPosition()+""));
                                regreq.addProperty("otp", et_otp_pswd.getText().toString());
//                       regreq.addProperty("isOnline", true);
//                       mApiManager.RegistrationRequest(regreq);
                                mApiManager.registrationValidateOtp(regreq);
                            }
                        });
                    }else{
                        showDialog(FarmerRegistrationActivity.this,"Mobile number exits",false,true,0);
                    }
                }

            }
        };
        mApiManager = new ApiManager(this, mInterFace);
    }

    public void showDialog(Activity activity, String msg, boolean isCancelBtnVisible,
                           final boolean isClickable, final int Id) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog);

        TextView text = dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogOKButton = dialog.findViewById(R.id.btn_ok);

        dialogOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(Id==1){
                    Intent registrationIntent = new Intent(FarmerRegistrationActivity.this, FarmerLoginActivity.class);
                    startActivity(registrationIntent);
                    dialog.dismiss();
                }else {
                    if (isClickable) dialog.cancel();
                }



            }
        });

        Button dialogCancelButton = dialog.findViewById(R.id.btn_cancel);
        if (isCancelBtnVisible) {
            dialogCancelButton.setVisibility(View.VISIBLE);
        } else {
            dialogCancelButton.setVisibility(View.GONE);

        }
        dialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (isClickable)
                    dialog.cancel();
            }
        });


        dialog.show();

    }



    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
      /* intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                ("Say something"));
       */
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    ("Sorry! Your device doesn\\'t support speech input"),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void promptSpeechInputfather() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                ("Say something"));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT_fathername);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    ("Sorry! Your device doesn\\'t support speech input"),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void promptSpeechInputmobileno() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                ("Say something"));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT_mob);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    ("Sorry! Your device doesn\\'t support speech input"),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT:
                try {
                    {
                        if (resultCode == RESULT_OK && null != data) {

                            ArrayList<String> result = data
                                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                            if (!result.get(0).contains("1") && !result.get(0).contains("2") &&
                                    !result.get(0).contains("3") && !result.get(0).contains("4")
                                    && !result.get(0).contains("5") && !result.get(0).contains("6") && !result.get(0).contains("7")
                                    && !result.get(0).contains("8") && !result.get(0).contains("9") && !result.get(0).contains("0")) {

                                et_full_name.setText(result.get(0));
                            }
                            new Handler().postDelayed(() ->
                                    {
                                        if (!et_full_name.getText().toString().equalsIgnoreCase("")) {
                                            FormSpeechfathername();
                                        }
                                    },
                                    1000); //time in ms


                        }
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case REQ_CODE_SPEECH_INPUT_fathername:
                try {
                    {
                        if (resultCode == RESULT_OK && null != data) {

                            ArrayList<String> result = data
                                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                            if (!result.get(0).contains("1") && !result.get(0).contains("2") &&
                                    !result.get(0).contains("3") && !result.get(0).contains("4")
                                    && !result.get(0).contains("5") && !result.get(0).contains("6") && !result.get(0).contains("7")
                                    && !result.get(0).contains("8") && !result.get(0).contains("9") && !result.get(0).contains("0")) {
                                et_father_name.setText(result.get(0));
                            }

                            new Handler().postDelayed(() ->
                                    {
                                        if (!et_father_name.getText().toString().equalsIgnoreCase("")) {
                                            FormSpeechMobileno();
                                        }
                                    },
                                    1000); //time in ms


                        }
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case REQ_CODE_SPEECH_INPUT_mob:
                try {
                    {
                        if (resultCode == RESULT_OK && null != data) {
                            ArrayList<String> result = data
                                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                            if (result.get(0).contains("1") || result.get(0).contains("2") ||
                                    result.get(0).contains("3") || result.get(0).contains("4")
                                    || result.get(0).contains("5") || result.get(0).contains("6") || result.get(0).contains("7")) {
                                et_mobile_number.setText(result.get(0));
                            }
                            new Handler().postDelayed(() ->
                                    {
                                        if (!et_mobile_number.getText().toString().equalsIgnoreCase("") && sp_gender.getSelectedItemPosition() == 0) {
                                            FormSpeechselectgender();
                                        }
                                    },
                                    1000); //time in ms


                        }
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }

    public void informationMenu() {
        startActivity(new Intent("android.intent.action.INFOSCREEN"));
    }


    public void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    public boolean addgrampanchaytcheck(ArrayList<String>gramlistcheck, String selectgramstring){
        for(int i=0;i<gramlistcheck.size();i++){
            if(gramlistcheck.get(i).toLowerCase().equals(selectgramstring.toLowerCase())){
                return  false;
            }
        }
        return true;
    }
    public boolean addblockcheck(ArrayList<String>blocklist, String seletblockstring){
        for(int i=0;i<blocklist.size();i++){
            if(blocklist.get(i).toLowerCase().equals(seletblockstring.toLowerCase())){
                return  false;
            }
        }
        return true;
    }
    public boolean addvillcheck(ArrayList<String>villlist, String seletvillstring){
        for(int i=0;i<villlist.size();i++){
            if(villlist.get(i).toLowerCase().equals(seletvillstring.toLowerCase())){
                return  false;
            }
        }
        return true;
    }


}
