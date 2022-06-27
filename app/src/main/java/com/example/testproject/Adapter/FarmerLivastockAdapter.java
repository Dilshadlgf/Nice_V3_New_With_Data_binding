package com.example.testproject.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.databinding.AddlivestocUpdatefragmentBinding;
import com.example.testproject.interfaces.ListItemClickListener;
import com.example.testproject.model.LivestocksArrayModel;
import com.example.testproject.model.RootOneResModel;
import com.example.testproject.model.SingleObjectModel.SingleObjRootOneResModel;
import com.example.testproject.model.livestock.RootLiveStockResponse;
import com.example.testproject.model.stagemodel;
import com.example.testproject.model.varietymodel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suraj on 27-06-2022.
 */
public class FarmerLivastockAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    List<LivestocksArrayModel> modelList;
    private static final int ITEM = 0;
    private Context context;
    ListItemClickListener farmerLivestockClickListner;

    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private String errorMessage="";

    String farmerid;
    public Spinner splivestock,spvariety,spstages,spvarietycrop,spseason,spareaunit,
            spirrigation,spcrop;


    private String cntComodityId="",cntVariety,cntState;
    private boolean isFirstSpSet;
    private List<String> comodityIDList=new ArrayList<>();
    private List<String> varietiresIdList=new ArrayList<>();
    private List<String> stageIdList=new ArrayList<>();
    private boolean isFirstLiveSClick,isFirstV,isFirstS;
    private int cntIndex;
    private FarmerDao farmerDao;

    public FarmerLivastockAdapter(Context context, List<LivestocksArrayModel> farmerdetailmodel, ListItemClickListener farmerLivestockClickListner) {
        this.context = context;
//        this.modelList=new ArrayList<>();
//        this.modelList.addAll(farmerdetailmodel);
        this.modelList=farmerdetailmodel;
        farmerDao = AppDatabase.getInstance(context.getApplicationContext()).farmerDao();
        this.farmerLivestockClickListner= farmerLivestockClickListner;
        setupNetwork();

    }
    public void setnewlist(List<LivestocksArrayModel> list){

        modelList.clear();
        modelList.addAll(list);
        notifyDataSetChanged();

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

                viewHolder = getViewHolder(parent, inflater);

        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.activity_crp_livestocks, parent, false);
        viewHolder = new DashboardVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {



                DashboardVH dashboardVH = (DashboardVH) holder;
                LivestocksArrayModel model=modelList.get(position);
                try {

                    dashboardVH.tittle.setText(model.getRef().getLiveStock().getName());
                    dashboardVH.variety.setText(model.getRef().getVeriety().getName());
                    dashboardVH.stage.setText(model.getRef().getStage().getName());
                    dashboardVH.no_of_livestoks.setText(model.getRef().getLiveStock().getCommonName());
                    dashboardVH.quantity.setText(model.getQuantity());
                    dashboardVH.edtlivestock.setTag(position);
                    dashboardVH.edtlivestock.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cntIndex= (int) v.getTag();
                            customDialog(model);
                        }
                    });
                    dashboardVH.deleteStock.setTag(position);
                    dashboardVH.deleteStock.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cntIndex= (int) v.getTag();
                            showDialog((Activity) context, "Do You Want To Delete Crop",true,false, 1);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }



    }
    AddlivestocUpdatefragmentBinding binding;
    private LivestocksArrayModel selectedModel;
    private Dialog dialogitemView;
    private void customDialog(LivestocksArrayModel model){
        isFirstLiveSClick=false;
        isFirstV=false;
        isFirstS=false;
         dialogitemView =new Dialog(context);
        selectedModel=model;


        binding= DataBindingUtil.inflate( LayoutInflater.from(context),R.layout.addlivestoc_updatefragment,null,false );
        dialogitemView.setContentView(binding.getRoot());
        binding.btnOk.setText("Update LiveStock");

        JsonObject mainObj=new JsonObject();
        JsonArray statusArr=new JsonArray();
        statusArr.add("Active");
        mainObj.add("status",statusArr);
        JsonArray lives=new JsonArray();
        lives.add("Livestock");
        mainObj.add("classification",lives);

        binding.quantity.setText(model.getQuantity());
        mApiManager.getLiveStockList(mainObj);
        binding.splivestocks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    cntComodityId=comodityIDList.get(position);
//                        cntComodityId="6219bc1fb1417e7c507e2c7c"; testing
                    callVarietyApi();
                    callStageApi();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isvalid_AddLivestocks()){
                    try {
                        JsonObject livestockobj= new JsonObject();
                        livestockobj.addProperty("LiveStock",comodityIDList.get(binding.splivestocks.getSelectedItemPosition()));
                        livestockobj.addProperty("stage",stageIdList.get(binding.spvarieties.getSelectedItemPosition()));
                        livestockobj.addProperty("veriety",varietiresIdList.get(binding.spstages.getSelectedItemPosition()));
                        livestockobj.addProperty("quantity",Integer.parseInt(binding.quantity.getText().toString()));
                        livestockobj.addProperty("farmer", farmerDao.getFarmer().getId());
                        livestockobj.addProperty("id", model.getId());
                        livestockobj.addProperty("activeStatus", true);
                        mApiManager.UpdateLivestock("",livestockobj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
//                    showDialog(getActivity(), errorMessage, false, true, 0);
                    Toast.makeText(context,"",Toast.LENGTH_SHORT).show();

                }

            }
        });

        if( !dialogitemView.isShowing()){
            dialogitemView.show();
        }
    }
    private void fillData(Object obj,int apiType){

        if(apiType==1){
            List<String> list = new ArrayList<String>();

            List<LivestocksArrayModel> modelList= (List<LivestocksArrayModel>) obj;
            comodityIDList.add("");//for 0 position
            if(modelList!=null && modelList.size()>0) {
                list.add("---Select LiveStock---");
                for (int i = 0; i < modelList.size(); i++) {
                    if (modelList.get(i).getCommonName() != null) {
                        list.add(modelList.get(i).getCommonName());
                        comodityIDList.add(modelList.get(i).getId());
                    }
                }
            }else{
                list.add("---Empty---");
            }
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_textview, list);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(R.layout.spinner_item);
            // attaching data adapter to spinner
            binding.splivestocks.setAdapter(dataAdapter);
            if(!isFirstLiveSClick) {
                for (int i = 0; i < binding.splivestocks.getCount(); i++) {
                    if (selectedModel.getRef().getLiveStock().getCommonName().equals(binding.splivestocks.getItemAtPosition(i))) {
                        binding.splivestocks.setSelection(i);
                    }
                }
                isFirstLiveSClick=true;
            }
        }else if(apiType==2){
            List<String> list2 = new ArrayList<String>();

            List<varietymodel> modelList= (List<varietymodel>) obj;
            varietiresIdList.add("");//for 0 position
            if(modelList!=null && modelList.size()>0) {
                list2.add("---Select Varieties---");
                for (int i = 0; i < modelList.size(); i++) {
                    list2.add(modelList.get(i).getName());
                    varietiresIdList.add(modelList.get(i).getId());
                }
            }else{
                list2.add("---Empty---");
            }
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(context, R.layout.spinner_textview, list2);
            // Drop down layout style - list view with radio button
            dataAdapter1.setDropDownViewResource(R.layout.spinner_item);
            // attaching data adapter to spinner
            binding.spvarieties.setAdapter(dataAdapter1);
            if(!isFirstV) {
                for (int i = 0; i < binding.spvarieties.getCount(); i++) {
                    if (selectedModel.getRef().getVeriety().getName().equals(binding.spvarieties.getItemAtPosition(i))) {
                        binding.spvarieties.setSelection(i);
                    }
                }
                isFirstV=true;
            }

        }else if(apiType==3){
            List<String> list2 = new ArrayList<String>();

            List<stagemodel> modelList= (List<stagemodel>) obj;
            stageIdList.add("");//for 0 position
            if(modelList!=null && modelList.size()>0) {
                list2.add("---Select Stage---");
                for (int i = 0; i < modelList.size(); i++) {
                    if (modelList.get(i).getName() != null) {
                        list2.add(modelList.get(i).getName());
                        stageIdList.add(modelList.get(i).getId());
                    }
                }
            }else {
                list2.add("---Empty---");
            }
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(context, R.layout.spinner_textview, list2);
            // Drop down layout style - list view with radio button
            dataAdapter1.setDropDownViewResource(R.layout.spinner_item);
            // attaching data adapter to spinner
            binding.spstages.setAdapter(dataAdapter1);
            if(!isFirstS) {
                for (int i = 0; i < binding.spstages.getCount(); i++) {

                    if (selectedModel.getRef().getStage().getName()!=null && selectedModel.getRef().getStage().getName().equals(binding.spstages.getItemAtPosition(i))) {
                        binding.spstages.setSelection(i);
                    }
                }
                isFirstS=true;
            }

        }



    }
    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
                Toast.makeText(context,""+errorCode,Toast.LENGTH_SHORT).show();

            }
            @Override
            public void isSuccess(Object response, int ServiceCode) {

                if (ServiceCode == AppConstants.LiveSTOCKREQUEST) {
                    RootOneResModel rootOneResModel= (RootOneResModel) response;
                    fillData(rootOneResModel.getResponse().getDataModel2().getCommodity(),1);
                    cntComodityId=rootOneResModel.getResponse().getDataModel2().getCommodity().get(0).getId();
                    callVarietyApi();
                    callStageApi();
                }else if (ServiceCode == AppConstants.VarietyListReq) {
                    RootOneResModel rootOneResModel= (RootOneResModel) response;
                    fillData(rootOneResModel.getResponse().getDataModel2().getVariety(),2);
                }else if (ServiceCode == AppConstants.StageListReq) {
                    RootOneResModel rootOneResModel= (RootOneResModel) response;
                    fillData(rootOneResModel.getResponse().getDataModel2().getStage(),3);
                }else if (ServiceCode == AppConstants.UpdatreLivesstock) {
                    SingleObjRootOneResModel oneResModel= (SingleObjRootOneResModel) response;
//                    Toast.makeText(context,""+context.getString(R.string.livestock_update_success),Toast.LENGTH_SHORT).show();
                    showDialog((Activity) context, context.getString(R.string.livestock_update_success),false,true, 10);
//                    showDialog(getActivity(), getString(R.string.livestock_added_success), false, true, 0);
                }else if (ServiceCode == AppConstants.DeleteLiveStock) {
                    RootLiveStockResponse rootOneResModel= (RootLiveStockResponse) response;
//                    Toast.makeText(context,""+context.getString(R.string.livestock_delete_success),Toast.LENGTH_SHORT).show();
                    if(rootOneResModel.getResponse().getStatusCode()==200) {
                        showDialog((Activity) context, "Livestock Successfully Deleted",false,true, 0);
                        modelList.remove(cntIndex);
                        notifyItemRemoved(cntIndex);
                    }else{
                        showDialog((Activity) context, "Try after sometime",false,false, 0);
                    }
//                    showDialog(getActivity(), getString(R.string.livestock_added_success), false, true, 0);
                }

            }
        };
        mApiManager = new ApiManager(context, mInterFace);
    }
    private boolean isvalid_AddLivestocks() {
        if (binding.splivestocks.getSelectedItemPosition()==0) {
            errorMessage = "Please Select Livestocks";
            return false;
        }
        else if (binding.spvarieties.getSelectedItemPosition()==0) {
            errorMessage = ("Please Select Varieties");
            return false;
        }   else if (binding.spstages.getSelectedItemPosition()==0) {
            errorMessage = ("Please Select Stage");
            return false;
        }
        else if (TextUtils.isEmpty(binding.quantity.getText().toString().trim())) {
            errorMessage = ("Please Enter Quantity");
            return false;
        }
        else {
            return true;
        }
    }
    private void callVarietyApi(){
        JsonObject mainObj=new JsonObject();
        JsonArray statusArr=new JsonArray();
        statusArr.add("Active");
        mainObj.add("status",statusArr);
        JsonArray comodityId=new JsonArray();
        comodityId.add(cntComodityId);
        mainObj.add("commodity",comodityId);
        mApiManager.varietyList(mainObj);
    }
    private void callStageApi(){
        JsonObject mainObj=new JsonObject();
        JsonArray statusArr=new JsonArray();
        statusArr.add("Active");
        mainObj.add("status",statusArr);
        JsonArray comodityId=new JsonArray();
        comodityId.add(cntComodityId);
        mainObj.add("commodity",comodityId);
        mApiManager.stageList(mainObj);
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }






    protected class DashboardVH extends RecyclerView.ViewHolder {
        private TextView totalland,no_of_livestoks,variety,stage,quantity,tittle;
        private ImageView edtlivestock,deleteStock;
        public DashboardVH(View itemView) {
            super(itemView);
           // totalland=itemView.findViewById(R.id.totalland);
            no_of_livestoks=itemView.findViewById(R.id.no_of_livestoks);
            variety=itemView.findViewById(R.id.variety);
            stage=itemView.findViewById(R.id.stage);
            quantity=itemView.findViewById(R.id.quantity);
            edtlivestock=itemView.findViewById(R.id.iv_edit);
            deleteStock=itemView.findViewById(R.id.iv_delete);
            tittle=itemView.findViewById(R.id.txt_tittle);


        }
    }



    public void showDialog(Activity activity, String msg, boolean isCancelBtnVisible, final boolean isClickable, final int Id){
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog);
//            dialog.setTitle("Update Live Stock");

            TextView text = dialog.findViewById(R.id.text_dialog);
            text.setText(msg);

            Button dialogOKButton = dialog.findViewById(R.id.btn_ok);

            dialogOKButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(Id==1){
                        mApiManager.deleteliveStock(modelList.get(cntIndex).getId().trim());
                    }else if(Id==10){
                        if(splivestock!=null){
                            farmerLivestockClickListner.onItemClick(splivestock.getSelectedItemPosition()-1,"");
                        }else {
                            farmerLivestockClickListner.onItemClick(cntIndex,"");
                        }
                        dialogitemView.dismiss();

                    }
                    dialog.dismiss();
                }
            });

            Button dialogCancelButton = dialog.findViewById(R.id.btn_cancel);
            if (isCancelBtnVisible) {
                dialogCancelButton.setVisibility(View.VISIBLE);
            } else {
                dialogCancelButton.setVisibility(View.GONE);

            }
            dialogCancelButton.setOnClickListener(v -> {
                dialog.dismiss();
            });


            dialog.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}




