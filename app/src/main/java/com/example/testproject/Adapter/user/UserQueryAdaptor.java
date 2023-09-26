package com.example.testproject.Adapter.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.model.UserModel;
import com.example.testproject.model.Useracl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.example.testproject.BuildConfig;
import com.example.testproject.R;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.databinding.UserQueryCardItemBinding;
import com.example.testproject.interfaces.CustomAlertListener;
import com.example.testproject.interfaces.ListItemClickListener;
import com.example.testproject.model.QueriesResponseDataNumModel;
import com.example.testproject.util.AppConstants;
import com.example.testproject.util.CommonUtils;
import com.example.testproject.util.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UserQueryAdaptor extends RecyclerView.Adapter<UserQueryAdaptor.MViewholder> implements CustomAlertListener {

    ListItemClickListener itemClickListener;
    private List<QueriesResponseDataNumModel> modelList;
    private ButtonHandler buttonHandler;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private Context mContext;
    private String userId;
    private String currentQId;
    private String queryType;
    private Useracl useracl;

    public UserQueryAdaptor(Context context, List<QueriesResponseDataNumModel> modelList, ListItemClickListener itemClickListener, String userId, String queryType){
        this.itemClickListener=itemClickListener;
        this.modelList=modelList;
        this.userId = userId;
        this.queryType = queryType;
        this.buttonHandler=new ButtonHandler();
        mContext=context;
        setupNetwork();
        useracl= AppDatabase.getInstance(mContext).getUseraclDao().getUseracl();
    }
    public void doClearList(){
        modelList.clear();
        notifyDataSetChanged();
    }
    public void addItemInList(List<QueriesResponseDataNumModel> mlist){
        modelList.addAll(mlist);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserQueryCardItemBinding binding=UserQueryCardItemBinding.inflate(LayoutInflater.from(parent.getContext()));


        binding.btnApprove.setOnClickListener(buttonHandler);
        binding.btnProfile.setOnClickListener(buttonHandler);
        binding.btnEdit.setOnClickListener(buttonHandler);
        binding.btnView.setOnClickListener(buttonHandler);
        binding.btnDelete.setOnClickListener(buttonHandler);

        binding.btnProfile.setVisibility(View.VISIBLE);
        binding.btnDelete.setVisibility(View.VISIBLE);
        binding.btnEdit.setVisibility(View.VISIBLE);
        binding.btnApprove.setVisibility(View.VISIBLE);

        switch (queryType){
            case "assignedToMe":
                binding.btnDelete.setVisibility(View.GONE);
                binding.btnEdit.setVisibility(View.GONE);
                break;
            case "assigned":
                binding.btnProfile.setVisibility(View.GONE);
                binding.btnDelete.setVisibility(View.GONE);
                binding.btnEdit.setVisibility(View.GONE);
                break;
            case "resolved":
                binding.btnProfile.setVisibility(View.INVISIBLE);
                binding.btnApprove.setVisibility(View.GONE);
                binding.btnDelete.setVisibility(View.VISIBLE);
                binding.btnEdit.setVisibility(View.GONE);
                break;
        }
        handleBtnVisibiltyAcordingToRole(binding);

        return new MViewholder(binding);
    }

    @Override
    public void OnDialogOKClick(int id) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("id",currentQId);
        switch (id){
           case 3:
                mApiManager.commonApiWithThreePath("query","status","delete",null,hashMap,AppConstants.DELETE,AppConstants.METHOD_DELETE);
                break;
        }
    }

    @Override
    public void OnDialogCancel(int id) {

    }

    class ButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String aType= (String) v.getTag(R.string.alert);
            String id= (String) v.getTag();
            currentQId=id;
            switch (aType){
                case "assign":
                    JsonObject jsonObject=new JsonObject();
                    JsonArray statusArr=new JsonArray();
                    statusArr.add("Active");
                    JsonObject dataArr=new JsonObject();
                    dataArr.addProperty("isAccess",true);
                    dataArr.addProperty("userName",userId);

                    jsonObject.add("dataAccess",dataArr);
                    jsonObject.add("status",statusArr);
                    jsonObject.addProperty("sortBy","name");
                    jsonObject.addProperty("sortOrder",-1);
                    mApiManager.commonApiWithTwoPathPost("user","filter",jsonObject,"no",AppConstants.GeoFilter);
                    break;
                case "view":
                    Bundle bundleQ=new Bundle();
                    QueriesResponseDataNumModel model= (QueriesResponseDataNumModel) v.getTag(R.string.details);
                    String res=CommonUtils.pojoToJson(model);
                    bundleQ.putString("model", res);
                    bundleQ.putString("QueryUId", model.getUniqueId());
                    bundleQ.putString("id", currentQId);
                    bundleQ.putBoolean("callFromOut", false);
                    bundleQ.putBoolean("fromView", true);
                    Navigation.findNavController(v).navigate(R.id.viewSingleQueryFragment,bundleQ);
//                    FragmentManager manager = ((AppCompatActivity) mContext).getSupportFragmentManager();
//                    CustomFragmentManager.replaceFragment(manager, ViewSingleQueryFragment.newInstance(bundleQ), true);
                    break;
                case "edit":
                    Bundle bundleR=new Bundle();
                    QueriesResponseDataNumModel model2= (QueriesResponseDataNumModel) v.getTag(R.string.details);
                    String res2=CommonUtils.pojoToJson(model2);
                    bundleR.putString("model", res2);
                    bundleR.putString("uId",model2.getUniqueId());

                    Navigation.findNavController(v).navigate(R.id.userQueryResolveFragment,bundleR);
//                    CustomFragmentManager.replaceFragment(((AppCompatActivity)v.getContext()).getSupportFragmentManager(), UserQueryResolveFragment.newInstance(bundleR),true);
                    break;
                case "resolve":
                    Bundle bundle=new Bundle();
                    bundle.putString("queryId",id);
                    bundle.putString("uId",v.getTag(R.string.details).toString());
                    Navigation.findNavController(v).navigate(R.id.userQueryResolveFragment,bundle);
//                    CustomFragmentManager.replaceFragment(((AppCompatActivity)v.getContext()).getSupportFragmentManager(), UserQueryResolveFragment.newInstance(bundle),true);
                    break;
                case "delete":
                    CommonUtils.openCustomDialog(v.getContext(),UserQueryAdaptor.this,v.getContext().getString(R.string.do_you_want_to_delete_this_query),3);
                    break;
            }
        }
    }
    private void handleBtnVisibiltyAcordingToRole(UserQueryCardItemBinding itemBinding){
        handleButton(useracl.contentAndQueryAccess.hasQueryEdit(),itemBinding.btnApprove);
        handleButton(useracl.contentAndQueryAccess.hasQueryEdit(),itemBinding.btnEdit);
        handleButton(useracl.contentAndQueryAccess.hasQueryEdit(),itemBinding.btnProfile);
        handleButton(useracl.contentAndQueryAccess.hasDelete(),itemBinding.btnDelete);
    }
    private void handleButton(boolean value,View view){
        if(value){
//            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MViewholder holder, int position) {
        QueriesResponseDataNumModel model=modelList.get(position);
        if(model.getCreatedType().equals("Farmer")){
            holder.binding.txtName.setText(model.getRef().createdByFarmer.name);
        }else {
            holder.binding.txtName.setText(model.getRef().createdByUser.name);
        }

        holder.binding.txtDate.setText(CommonUtils.getOnlyDateFormat(model.getDate()));
        holder.binding.txtTime.setText(CommonUtils.getOnlyTimeFormat(model.getDate()));
        holder.binding.txtQuery.setText(model.getQuery());
        if(model.getImages()!=null && model.getImages().size()>0){
            Picasso.get().load(BuildConfig.BASE_URL+model.getImages().get(0)).into(holder.binding.ivQueryPic);
        }
        holder.binding.btnApprove.setTag(model.getId());
        holder.binding.btnApprove.setTag(R.string.details,model.getUniqueId());
        holder.binding.btnView.setTag(model.getId());
        holder.binding.btnView.setTag(R.string.details,model);
        holder.binding.btnDelete.setTag(model.getId());
        holder.binding.btnEdit.setTag(R.string.details,model);
        holder.binding.btnEdit.setTag(model.getId());
        holder.binding.btnProfile.setTag(model.getId());


        holder.binding.btnApprove.setTag(R.string.alert,"resolve");
        holder.binding.btnView.setTag(R.string.alert,"view");
        holder.binding.btnDelete.setTag(R.string.alert,"delete");
        holder.binding.btnEdit.setTag(R.string.alert,"edit");
        holder.binding.btnProfile.setTag(R.string.alert,"assign");

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick((Integer) v.getTag(),"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    static class MViewholder extends RecyclerView.ViewHolder {
        UserQueryCardItemBinding binding;
        public MViewholder(@NonNull UserQueryCardItemBinding itemView) {
            super(itemView.getRoot());
            this.binding=itemView;
        }
    }
    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
//                showDialog(getActivity(), errorCode, false, true, 0);
                Toast.makeText(mContext,errorCode,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if(ServiceCode== AppConstants.GeoFilter) {
                    JsonObject jsonObject = (JsonObject) response;
                    if(jsonObject.has("response")&& jsonObject.getAsJsonObject("response").has("data")){
                        JsonObject jsonObject1=jsonObject.getAsJsonObject("response").getAsJsonObject("data");
                        if(jsonObject1.has("user")){
                            List<UserModel> model = (List<UserModel>) CommonUtils.getPojoFromStr(new TypeToken<List<UserModel>>() {
                            }.getType(), jsonObject1.getAsJsonArray("user").toString());

                            assignDialog(model);
                        }
                    }

                }else if(ServiceCode== AppConstants.ASSIGN) {
                    JsonObject jsonObject= (JsonObject) response;
                    RootOneModel rootModel= (RootOneModel) CommonUtils.getPojoFromStr(RootOneModel.class,jsonObject.toString());
                    if(rootModel.getResponse().getStatusCode()==200){
                        CommonUtils.makeToast(mContext,mContext.getString(R.string.operation_successful));
                        itemClickListener.onItemClick(-1,"1");
                    }
                }else if(ServiceCode== AppConstants.DELETE) {
                    JsonObject jsonObject= (JsonObject) response;
                    RootOneModel rootModel= (RootOneModel) CommonUtils.getPojoFromStr(RootOneModel.class,jsonObject.toString());
                    if(rootModel.getResponse().getStatusCode()==200){
                        CommonUtils.makeToast(mContext,mContext.getString(R.string.operation_successful));
                        itemClickListener.onItemClick(-1,"1");
                    }
                }
            }
        };
        mApiManager = new ApiManager(mContext, mInterFace);
    }
    private void assignDialog(List<UserModel> model){
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext,R.style.MyDialogTheme);
        builder.setTitle(mContext.getString(R.string.assign_to_user));
        Spinner spinner=new Spinner(mContext);
        String[] names=new String[model.size()];
        for (int i = 0; i < model.size(); i++) {
            names[i]=model.get(i).firstName;
        }
        RelativeLayout viewGroup =new RelativeLayout(mContext);
        viewGroup.setPadding(40,0,10,0);
        ViewUtils.fillDataInSpinner(mContext,spinner, Arrays.asList(names));
        spinner.setBackgroundResource(R.drawable.ract_green_user);
        spinner.setGravity(RelativeLayout.CENTER_HORIZONTAL);
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        spinner.setLayoutParams(layoutParams);
        spinner.setPadding(40,0,10,0);
//        viewGroup.addView(spinner);
        builder.setView(spinner);

        builder.setPositiveButton("Add User", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("queryId",currentQId);
                jsonObject.addProperty("userId",model.get(spinner.getSelectedItemPosition()).id);
                mApiManager.commonApiWithTwoPathPut("query","assinguser",jsonObject,"no",AppConstants.ASSIGN);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog=builder.create();

        dialog.show();
    }
}
