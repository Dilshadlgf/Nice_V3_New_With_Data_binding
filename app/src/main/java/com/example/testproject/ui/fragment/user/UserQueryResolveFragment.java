package com.example.testproject.ui.fragment.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.model.DataModelTwo;
import com.example.testproject.model.RootOneModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.example.testproject.BuildConfig;
import com.example.testproject.R;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.databinding.ImageviewWithTopCloseBtnBinding;
import com.example.testproject.databinding.UserQueryResolveFragmentBinding;
import com.example.testproject.ui.base.BaseFragment;
import com.example.testproject.util.AppConstants;
import com.example.testproject.util.CommonUtils;
import com.example.testproject.util.JsonMyUtils;
import com.example.testproject.util.UploadPicVidDocFiles;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DL on 10-09-2022.
 */
public class UserQueryResolveFragment extends BaseFragment {
    private static final String TAG = "Dashboard";



    private UserQueryResolveFragmentBinding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private UserDao userDao;
    private UploadPicVidDocFiles uploadPicVidDocFiles;

    private boolean hasImageUpdate,inUpdateScreen;
//    private DataModelTwo dataModelTwo;

    private String queryId;
    NavController navController;

    public static UserQueryResolveFragment newInstance(Bundle args) {
        UserQueryResolveFragment fragment = new UserQueryResolveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        layoutId = R.layout.user_query_resolve_fragment;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (UserQueryResolveFragmentBinding) viewDataBinding;
//         navController = Navigation.findNavController(binding.getRoot());
        navController = NavHostFragment.findNavController(this);
        setupNetwork();
        userDao = AppDatabase.getInstance(getContext()).userdao();

        if(getArguments()!=null){
           queryId= getArguments().getString("queryId");
            getActivity().setTitle(getString(R.string.query)+" "+getArguments().getString("uId",""));
        }


        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadPicVidDocFiles==null){
                    uploadPicVidDocFiles=new UploadPicVidDocFiles();
                }
                uploadPicVidDocFiles.selectImage(getActivity(),UserQueryResolveFragment.this);
            }
        });
        binding.btnResolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inUpdateScreen){
                    uploadFiles((DataModelTwo) v.getTag(R.string.details));
                }else {
                    uploadFiles(null);
                }
            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CustomFragmentManager.replaceFragment(getFragmentManager(),new UserQueryTabFragment());
                Navigation.findNavController(v).navigate(R.id.userQueryTabFragment);
            }
        });
        if(getArguments()!=null&& getArguments().containsKey("model")){
            binding.btnResolve.setText(getString(R.string.update));
            inUpdateScreen=true;
            DataModelTwo modelTwo= (DataModelTwo) CommonUtils.getPojoFromStr(DataModelTwo.class,getArguments().getString("model"));
            fillData(modelTwo);
            binding.btnResolve.setTag(R.string.details,modelTwo);
        }
    }
    private void fillData(DataModelTwo model){
        binding.edSolutions.setText(model.getQuery());
        if(model.getImages()!=null && model.getImages().size()>0){
            binding.layImageHolder.removeAllViews();
            for (int i = 0; i < model.getImages().size(); i++) {
                ImageviewWithTopCloseBtnBinding binding1=ImageviewWithTopCloseBtnBinding.inflate(LayoutInflater.from(getContext()));
                binding1.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hasImageUpdate=true;
                        removeViewFromParent((View) v.getParent());
                    }
                });
                binding1.imageView.setTag(R.string.viewimage,model.getImages().get(i));
                Picasso.get().load(BuildConfig.BASE_URL+model.getImages().get(i)).into(binding1.imageView);
                binding.layImageHolder.addView(binding1.getRoot());
            }
        }
    }
    private void updateApi(DataModelTwo model){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("id",model.getId());
        jsonObject.addProperty("query",binding.edSolutions.getText().toString());
        jsonObject.addProperty("queryType",model.getQueryType());

        if(hasImageUpdate){
            JsonArray jsonArray=new JsonArray();
            for (int i = 0; i < binding.layImageHolder.getChildCount(); i++) {
                View view=binding.layImageHolder.getChildAt(i);
                if(view.getTag(R.string.viewimage)!=null) {
                    jsonArray.add(String.valueOf(view.getTag(R.string.viewimage)));
                }
            }
            if(uploadPicVidDocFiles!=null && uploadPicVidDocFiles.serverUrlList!=null) {
                for (int i = 0; i < uploadPicVidDocFiles.serverUrlList.size(); i++) {
                    jsonArray.add(uploadPicVidDocFiles.serverUrlList.get(i).toString());
                }
            }
            jsonObject.add("images",jsonArray);
        }
        mApiManager.commonOnePathApi("query",jsonObject,null,AppConstants.UpdateCrop,AppConstants.METHOD_PUT);
    }
    private void setImage(Uri path){
        hasImageUpdate=true;
        for (int i = 0; i < binding.layImageHolder.getChildCount(); i++) {
            View view=binding.layImageHolder.getChildAt(i);
            if(view.getTag()!=null&& view.getTag().toString().equals("holder")){
                binding.layImageHolder.removeView(view);
            }
        }
        ImageviewWithTopCloseBtnBinding binding1=ImageviewWithTopCloseBtnBinding.inflate(LayoutInflater.from(getContext()));
        binding1.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeViewFromParent((View) v.getParent());
            }
        });
        //        ImageView imageView=new ImageView(getContext());
//        imageView.setTag("real");
//        imageView.setPadding(10,0,10,0);
//        imageView.setAdjustViewBounds(true);
//        imageView.setLayoutParams(new ViewGroup.LayoutParams((int) getActivity().getResources().getDimension(R.dimen._100sdp), ViewGroup.LayoutParams.WRAP_CONTENT));
        uploadPicVidDocFiles.setImage(getContext(),binding1.imageView,path);

        binding.layImageHolder.addView(binding1.getRoot());

    }
    private void removeViewFromParent(View mView){
        for (int i = 0; i < binding.layImageHolder.getChildCount(); i++) {
            View view=binding.layImageHolder.getChildAt(i);
            if(view.getId()==mView.getId()){
                binding.layImageHolder.removeView(view);
            }
        }
    }
    private void apiQueryCountCall(){
        if(CommonUtils.isEditTextEmpty(binding.edSolutions)){
            return;
        }
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("queryId",getArguments().getString("queryId"));
        jsonObject.addProperty("userId",userDao.getUserResponse().id);
        jsonObject.addProperty("solution",binding.edSolutions.getText().toString());
        JsonArray jsonArray=new JsonArray();
        if(uploadPicVidDocFiles!=null && uploadPicVidDocFiles.serverUrlList!=null) {
            for (int i = 0; i < uploadPicVidDocFiles.serverUrlList.size(); i++) {
                jsonArray.add(uploadPicVidDocFiles.serverUrlList.get(i).toString());
            }
            if(jsonArray.size()>0){
                jsonObject.add("images",jsonArray);
            }
        }
        mApiManager.submitQuerySolution(jsonObject,AppConstants.UpdateQuery);
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
                if(ServiceCode==AppConstants.UpdateQuery) {
                    JsonObject jsonObject = (JsonObject) response;
                    RootOneModel rootModel= (RootOneModel) CommonUtils.getPojoFromStr(RootOneModel.class,jsonObject.toString());
                    if(rootModel.getResponse().getStatusCode()==200) {
                       CommonUtils.makeToast(getContext(),"Solution successfully submitted");
//                        CustomFragmentManager.replaceFragment(getFragmentManager(),new UserQueryTabFragment());
                        navController.navigate(R.id.userQueryTabFragment);
                        if(uploadPicVidDocFiles!=null && uploadPicVidDocFiles.serverUrlList!=null){
                            uploadPicVidDocFiles.serverUrlList.clear();
                        }
                    }
                }else if(ServiceCode==AppConstants.FileUpload) {
                    RootOneModel model = new Gson().fromJson(((JsonObject) response).toString(), RootOneModel.class);
                    JsonArray jsonArray = (JsonArray) model.getResponse().getData().FileURIs.getAsJsonArray();
                    List<String> filesStr = new ArrayList();
                    for (int i = 0; i < jsonArray.size();i++){
                        filesStr.add(jsonArray.get(i).getAsString());
                    }

                    uploadPicVidDocFiles.fileUriList.clear();
                    if (filesStr.size()>0) {
                        uploadPicVidDocFiles.serverUrlList=filesStr;
                    }
                    if(inUpdateScreen) {
                        updateApi((DataModelTwo) binding.btnResolve.getTag(R.string.details));
                    }else {
                        apiQueryCountCall();
                    }
                }else if(ServiceCode==AppConstants.UpdateCrop) {
                    JsonObject jsonObject= (JsonObject) response;
                    if(JsonMyUtils.isResponseSuccess(jsonObject)){
                        binding.edSolutions.setText("");
                        showDialog(getActivity(),getString(R.string.update_successful),false,true,10);
                    }
                }
            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }
    private void uploadFiles(DataModelTwo modelTwo){
        if(uploadPicVidDocFiles!=null && uploadPicVidDocFiles.fileUriList!=null && uploadPicVidDocFiles.fileUriList.size()>0) {
            if(inUpdateScreen){
                mApiManager.uploadFilesRequest(getContext(), uploadPicVidDocFiles.fileUriList, "query", AppConstants.FileUpload);
            }else {
                mApiManager.uploadFilesRequest(getContext(), uploadPicVidDocFiles.fileUriList, "queryresolveimg", AppConstants.FileUpload);
            }

        }else {
            if(inUpdateScreen){
                updateApi(modelTwo);
            }else {
                apiQueryCountCall();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Uri path=null;
        if(requestCode==UploadPicVidDocFiles.GALLERY_REQUEST){
            if(data!=null){
                path = data.getData();
            }
        }else if(requestCode==UploadPicVidDocFiles.CAMERA_REQUEST){

            if(data!=null){
                path = data.getData();
            }else {
                path = Uri.fromFile(new File(UploadPicVidDocFiles.FilePath));
            }

        }else if(requestCode==UploadPicVidDocFiles.DOC_REQUEST){
            if(data!=null){
                path = data.getData();
            }
        }
        if(path!=null) {
            if(uploadPicVidDocFiles.fileUriList==null)
                uploadPicVidDocFiles.fileUriList = new ArrayList<>();
            uploadPicVidDocFiles.fileUriList.add(path);
//            edselectbill.setText(""+new File(path.getPath()).getName());
            setImage(path);
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackCustom() {
        getFragmentManager().popBackStack();
    }

}