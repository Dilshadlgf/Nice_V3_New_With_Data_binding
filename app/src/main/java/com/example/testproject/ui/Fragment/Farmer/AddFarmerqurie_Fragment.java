package com.example.testproject.ui.Fragment.Farmer;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.ViewDataBinding;

import com.example.testproject.Network.ApiClient;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.ImageUtil;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.databinding.FragmentAddQueryBinding;
import com.example.testproject.model.ActiveKnowledgeDomainResponseDataModel;
import com.example.testproject.model.Farmerlistnewdatamodel;
import com.example.testproject.model.GeneralResponseModel;
import com.example.testproject.model.SubDomainDataModel;
import com.example.testproject.model.query.ChatbotqueryModel;
import com.example.testproject.model.query.QueryResponseDataNumModel;
import com.example.testproject.model.query.RootQueryModel;
import com.example.testproject.ui.base.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;

public class AddFarmerqurie_Fragment extends BaseFragment implements View.OnClickListener {
    private Farmerlistnewdatamodel farmerListModel;
    MultipartBody.Part attachProof;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private FragmentAddQueryBinding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private FarmerDao loginDao;
    private ProgressDialog dialog;
    private FarmerDao villageDao;
    ChatbotqueryModel res;
//    FarmerListClickListner farmerListClickListner;
    private HashMap<Integer, String> spinnerKnowledgeDomainMapp;
    private HashMap<Integer, String> spinnerSubDomainMap;
    private List<ActiveKnowledgeDomainResponseDataModel> activeKnowledgeDomainResponseDataModels;
    private List<SubDomainDataModel> subDomainDataModelList;
    private String errorMessage = "";
    SharedPreferences pref;
    private FarmerDao farmerdao;
    GeneralResponseModel generalResponseModel;
    private static int uploadImage2 = 2;
    private static int uploadImage3 = 3;
    private static int uploadImage4 = 4;
    private static int uploadImage5 = 5;
    private RequestBody userAvtar;
    private String pictureImagePath = "";
    private static int uploadImage1 = 1;
    private static final int REQUEST_CAMERA = 101, SELECT_FILE = 1;
    int requestCode;
    Intent datagallry;
    private boolean loadAutoSuggest;
    private ArrayList<Uri> imageUriList;
    private String  mCurrentPhotoPath;
    private boolean isEdit;
    private String edit_QueryId;
    private String edit_QueryStatus;

    public static AddFarmerqurie_Fragment newInstance(Bundle args) {
        AddFarmerqurie_Fragment fragment = new AddFarmerqurie_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        layoutId = R.layout.fragment_add_query;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (FragmentAddQueryBinding) viewDataBinding;
        pref = getActivity().getSharedPreferences("mhh", MODE_PRIVATE);
        loginDao = AppDatabase.getInstance(getContext()).farmerDao();
        farmerdao = AppDatabase.getInstance(getContext()).farmerDao();
        villageDao = AppDatabase.getInstance(getContext()).farmerDao();
        setupNetwork();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    REQUEST_CAMERA);

        }
        binding.btnUploadImage1.setOnClickListener(this);
        binding.previewivcancel1.setOnClickListener(this);
        binding.previewivcancel2.setOnClickListener(this);
        binding.previewivcancel3.setOnClickListener(this);

        binding.btnSubmit.setOnClickListener(this);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(true);

        binding.voiceToTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        if(getArguments()!=null)
        isEdit=getArguments().getBoolean("isEdit",false);

         if(isEdit){
             Gson gson=new Gson();
             QueryResponseDataNumModel model=gson.fromJson(getArguments().getString("res"), QueryResponseDataNumModel.class);
             binding.etQuery.setText(model.getQuery());
             edit_QueryId=model.getId();
             edit_QueryStatus=model.getStatus();

             if(imageUriList==null){


                 imageUriList= new ArrayList<>( Arrays.asList(Uri.parse(""),Uri.parse(""),Uri.parse("")));
             }
             if(model.getImages()!=null) {
                 for (int i = 0; i < model.getImages().size(); i++) {
                     imageUriList.set(i,Uri.parse(model.getImages().get(i)));
                        switch (i){
                            case 0:
                                Picasso.get().load(ApiClient.BASE_URL+model.getImages().get(i)).into(binding.previewiv1);
                                break;
                            case 1:
                                Picasso.get().load(ApiClient.BASE_URL+model.getImages().get(i)).into(binding.previewiv2);
                                break;
                            case 2:
                                Picasso.get().load(ApiClient.BASE_URL+model.getImages().get(i)).into(binding.previewiv3);
                                break;
                        }
                 }
                 setUploadFileTextCount(model.getImages().size());
             }
         }


        spinnerKnowledgeDomainMapp = new HashMap<>();
        spinnerSubDomainMap = new HashMap<>();

            loadAutoSuggest=false;
        }
    private void setUploadFileTextCount(int count){
        binding.txtUploadFiletext.setText("Upload Files "+count+"/3");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_upload_image1) {
            selectImage(0);
        }else
        if (v.getId() == R.id.previewivcancel1) {
            handleCancelBtn(1);
        }else
        if (v.getId() == R.id.previewivcancel2) {
            handleCancelBtn(2);
        }else
        if (v.getId() == R.id.previewivcancel3) {
            handleCancelBtn(3);
        }else
        if (v.getId() == R.id.btn_submit) {
            uploadImages();
        }

    }
    private void handleCancelBtn(int i){
        switch (i){
            case 1:
                if(imageUriList!=null&& imageUriList.size()>=1) {
                    imageUriList.set(0,Uri.parse(""));
                }
                binding.previewiv1.setImageResource(R.drawable.imgmediagrey);
                break;
            case 2:
                if(imageUriList!=null&& imageUriList.size()>=2) {
                    imageUriList.set(1,Uri.parse(""));
                }
                binding.previewiv2.setImageResource(R.drawable.imgmediagrey);
                break;
            case 3:
                if(imageUriList!=null&& imageUriList.size()>=3) {
                    imageUriList.set(2,Uri.parse(""));;
                }
                binding.previewiv3.setImageResource(R.drawable.imgmediagrey);
                break;
        }
    }
    private void selectImage( int uploadImage) {
        try {
            final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Add Photo!");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            // Create the File where the photo should go
                            File photoFile = null;
                            try {

                                photoFile = ImageUtil.createImageFile();

                            } catch (IOException ex) {
                                // Error occurred while creating the File
//                                Log.i(TAG, "IOException");
                            }
                            // Continue only if the File was successfully created
                            File finalPhotoFile = photoFile;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (finalPhotoFile != null) {
                                        mCurrentPhotoPath= finalPhotoFile.getAbsolutePath();
                                        Uri outputFileUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".com.nice.provider", finalPhotoFile);
                                        String[] mimeTypes = {"image/jpeg", "image/png"};
                                        cameraIntent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                                        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        startActivityForResult(cameraIntent, uploadImage);
                                    }
                                }
                            },400);



                        }

                    } else if (options[item].equals("Choose from Gallery")) {
                        //                    galleryIntent();

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, uploadImage);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQ_CODE_SPEECH_INPUT && null != data) {
            ArrayList<String> result = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            binding.etQuery.setText(result.get(0));

        }else if (resultCode == getActivity().RESULT_OK ) {

           //initialize blank list
            if(imageUriList==null){
                imageUriList= new ArrayList<>( Arrays.asList(Uri.parse(""),Uri.parse(""),Uri.parse("")));
            }
            Uri path ;
            if(data==null || data.getData()==null){
                path= Uri.fromFile(new File(mCurrentPhotoPath));
            }else {
                 path = data.getData();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path);
                if(requestCode==0){
//                    binding.btnUploadImage1.setImageBitmap(bitmap);

                    setPreviewImage(bitmap,path);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void setPreviewImage(Bitmap bitmap,Uri uri){
        int size=0;
        for (int i = 0; i <imageUriList.size() ; i++) {
            if(!imageUriList.get(i).getPath().equals("")){
                size++;
            }
        }
            if(size<3){
                setUploadFileTextCount(size+1);
                if(imageUriList.get(0).getPath().equals("")){
                    imageUriList.set(0,uri);
                    binding.previewiv1.setImageBitmap(bitmap);
                }else if(imageUriList.get(1).getPath().equals("")){
                    binding.previewiv2.setImageBitmap(bitmap);
                    imageUriList.set(1,uri);
                }else if(imageUriList.get(2).getPath().equals("")){
                    imageUriList.set(2,uri);
                    binding.previewiv3.setImageBitmap(bitmap);
                }
            }else {
                Toast.makeText(getActivity(),"You can only select 3 images",Toast.LENGTH_SHORT).show();
            }

    }
    private void uploadImages(){
            if(imageUriList!=null) {
                mApiManager.uploadQueryImageRequest(getActivity(), imageUriList, AppConstants.ADD_UPLOAD_QUERY_IMAGE);
            }else{
                submitQuery(null);
            }
    }
    private void resetViews(){
        binding.etQuery.setText("");
//        binding.etTitle.setText("");
        binding.previewiv1.setImageResource(R.drawable.camera);
        binding.previewiv2.setImageResource(R.drawable.camera);
        binding.previewiv3.setImageResource(R.drawable.camera);
    }


    private boolean isValidRequest() {

            if (TextUtils.isEmpty(binding.etQuery.getText().toString())) {
            errorMessage = "Enter Query";
            showDialog(getActivity(),getString(R.string.enter_query),true,true,1);
            return false;
        } else {
            return true;
        }
    }


    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
                showDialog(getActivity(), errorCode, false, true, 0);
                loadAutoSuggest=false;
            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if (ServiceCode == AppConstants.sessionrequest) try {
                    {

                       // mApiManager.farmerListRequest(farmerdao.getFarmer().getId(), farmerdao.getFarmer().getToken());


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                else if (ServiceCode == AppConstants.addqueryautosuggRequest) {
                    res = (ChatbotqueryModel) response;

                }

                else if (ServiceCode == AppConstants.ACTIVE_KNOWLEDGE_REQUEST) try {
                    {
                        activeKnowledgeDomainResponseDataModels = (List<ActiveKnowledgeDomainResponseDataModel>) response;
                        String[] spinnerKnowledgeDomainArray = new String[activeKnowledgeDomainResponseDataModels.size() + 1];
                        spinnerKnowledgeDomainArray[0] = "--Select Knowledge--";
                        spinnerKnowledgeDomainMapp.put(0, "0");

                        for (int i = 1; i <= activeKnowledgeDomainResponseDataModels.size(); i++) {
                            spinnerKnowledgeDomainMapp.put(i, activeKnowledgeDomainResponseDataModels.get(i - 1).getId());
                            spinnerKnowledgeDomainArray[i] = activeKnowledgeDomainResponseDataModels.get(i - 1).getName();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_textview, spinnerKnowledgeDomainArray);
                        adapter.setDropDownViewResource(R.layout.spinner_item);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                else if (ServiceCode == AppConstants.FARMER_LIST_REQUEST) try {
                    {
                        farmerListModel = (Farmerlistnewdatamodel) response;
                        String[] spinnerKnowledgeDomainArray = new String[farmerListModel.getFarmers().size() + 1];
                        spinnerKnowledgeDomainArray[0] = "--Select Farmer--";
                        spinnerKnowledgeDomainMapp.put(0, "0");

                        for (int i = 1; i <= farmerListModel.getFarmers().size(); i++) {
                            spinnerKnowledgeDomainMapp.put(i, farmerListModel.getFarmers().get(i - 1).getId());
                            spinnerKnowledgeDomainArray[i] = farmerListModel.getFarmers().get(i - 1).getName();
                        }
                        if (getActivity() != null) {
                            ArrayAdapter<String> adapterr = new ArrayAdapter<>(getActivity(), R.layout.spinner_textview, spinnerKnowledgeDomainArray);
                            adapterr.setDropDownViewResource(R.layout.spinner_item);
//                            binding.spKnowledgeDomain.setAdapter(adapterr);

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                else if (ServiceCode == AppConstants.ADD_UPLOAD_QUERY_IMAGE) {
                    if(response==null){
                        submitQuery(null);
                    }else {
                        RootQueryModel loginModel= (RootQueryModel) response;
                    }

                }else if (ServiceCode == AppConstants.ADD_QUERY_REQUEST) {
                    RootQueryModel loginModel= (RootQueryModel) response;
                    if(isEdit){
                        showDialog(getActivity(), getString(R.string.query_update_success), false, true, 10);

                    }else {
                        showDialog(getActivity(), getString(R.string.query_added_success), false, false, 0);
                    }
                    resetViews();
                }else if (ServiceCode == AppConstants.UpdateQuery) {

                    if(isEdit){
                        showDialog(getActivity(), getString(R.string.query_update_success), false, true, 10);

                    }else {
                        showDialog(getActivity(), getString(R.string.query_added_success), false, false, 0);
                    }
                    resetViews();
                }

            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }
    private void submitQuery(List<String> arrayList){
        if(isValidRequest()) {
            JsonObject mainObj = new JsonObject();
            mainObj.addProperty("query", binding.etQuery.getText().toString());
//            mainObj.addProperty("title", binding.etTitle.getText().toString());
            mainObj.addProperty("queryType", "farmer");
            mainObj.addProperty("createdBy", farmerdao.getFarmer().getId());
            mainObj.addProperty("createdType", "Farmer");
//            mainObj.addProperty("village", villageDao.getVillageDBModelResponse().getVillageId());
//            mainObj.addProperty("district", villageDao.getVillageDBModelResponse().getDistrictID());
//            mainObj.addProperty("gramPanchayat", villageDao.getVillageDBModelResponse().getGramPanchayatId());
//            mainObj.addProperty("block", villageDao.getVillageDBModelResponse().getBlockId());
//            mainObj.addProperty("state", villageDao.getVillageDBModelResponse().getStateId());
//            mainObj.addProperty("farmer", loginDao.getLoginResponse().getId());
            mainObj.addProperty("type", "Farmer");
            JsonArray images = new JsonArray();
            if (arrayList != null) {

                for (int i = 0; i < arrayList.size(); i++) {
                    images.add(arrayList.get(i));
                }
                if (images.size() > 0) {
                    mainObj.add("images", images);
                }
            }
            if(isEdit){
                for (int i = 0; i <imageUriList.size() ; i++) {
                    if(imageUriList.get(i).toString().contains("/documents")){
                        images.add(imageUriList.get(i).toString());
                    }
                }
                if (images.size() > 0) {
                    mainObj.add("images", images);
                }
            }
            if(isEdit){
                mainObj.addProperty("id", edit_QueryId);
                mainObj.addProperty("status", edit_QueryStatus);
                mApiManager.updateQuery("update", mainObj);
            }else {
                mApiManager.addQueryRequest("", mainObj);
            }
        }
    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                ("Say something"));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity(),
                    ("Sorry! Your device doesn\\'t support speech input"),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void okDialogClick(int id) {
        if (id==1){
            dialog.dismiss();
        }
    }
}




