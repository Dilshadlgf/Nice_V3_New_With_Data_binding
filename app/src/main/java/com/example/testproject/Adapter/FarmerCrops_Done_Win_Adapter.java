package com.example.testproject.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.interfaces.ListItemClickListener;
import com.example.testproject.model.CropDataModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FarmerCrops_Done_Win_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>   {

    private static final int ITEM = 0;
//    private LoginDao loginDao;
    private String loginDao="628cc9e2a1e0bfbb4b7e3e8b";

    private List<CropDataModel> croplist;
    private Context context;
    private Boolean isWIP;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private HashMap<String ,String> cropHashmap=new HashMap<String ,String>();
    private HashMap<String ,String> interCropHashmap=new HashMap<String ,String>();
    private HashMap<String ,String> varietyHashmap=new HashMap<String ,String>();
    private HashMap<String ,String> seasonHashmap=new HashMap<String ,String>();
    private String cntCropId;
    private int currentCard;
    Spinner spCrop = null,spInterCrop= null,spVariety= null,spSeason= null,spIrrigation,spAreaUnit;
    private int editdialogapiHitCount;
    private EditText ed_crop_date;
    private Button edit_submit_btn;
    private TextView txArea;

    private ListItemClickListener listItemClickListener;
    public FarmerCrops_Done_Win_Adapter(List<CropDataModel> croplist, ListItemClickListener listItemClickListener, Context context, Boolean isWIP) {
        this.croplist = croplist;
        this.context = context;
        this.isWIP=isWIP;
//        setupNetwork();
        this.listItemClickListener=listItemClickListener;
//        loginDao = AppDatabase.getInstance(context.getApplicationContext()).loginDetails();
    }

    public FarmerCrops_Done_Win_Adapter(List<CropDataModel> croplist, Context context) {
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;

        }
        return viewHolder;
    }
    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.farmer_croplist_fragments, parent, false);
        viewHolder = new DashboardVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        switch (getItemViewType(position)) {
            case ITEM:
                DashboardVH dashboardVH = (DashboardVH) holder;
                try {


                    CropDataModel  model=croplist.get(position);
//                     CropDataModel model=croplist.get(position).getResponse().getData().getFarmerCrop().get(position);
//                    bindData(dashboardVH.crop,model.getRef().getCrop().getId());
                    
                    dashboardVH.crop.setText(model.getRef().getCrop().getId());
                    dashboardVH.scientificName.setText(model.getRef().getCrop().getScientificName());
                    dashboardVH.interCrop.setText(model.getRef().getInterCrop().getCommonName());

                    if(model.getRef().getVariety().getName()==null || model.getRef().getVariety().getName().isEmpty()) {
                        dashboardVH.variety.setText("NA");
                    }else {
                        dashboardVH.variety.setText(model.getRef().getVariety().getName());
                    }
                    if (model.getRef().getSeason().getName()==null || model.getRef().getSeason().getName().isEmpty())
                    {
                        dashboardVH.Seasion.setText("NA");
                    }else {
                        dashboardVH.Seasion.setText(model.getRef().getSeason().getName());
                    }
                    dashboardVH.Area_acre.setText(model.getArea());
                    dashboardVH.Irrigation.setText(model.getIrrigation());
                    dashboardVH.edit.setTag(model);
//                    dashboardVH.Yield_Q.setText("hjvsdahudfshadwf");

                    if(model.getYieldValue()==null)
                    {
                        dashboardVH.Yield_Q.setText("");
                    }
                    else {

                        Integer n=model.getYieldValue();
                        dashboardVH.Yield_Q.setText(""+model.getYieldValue());
                    }

                    dashboardVH.delete.setTag(position);
                    dashboardVH.done.setTag(position);
                    dashboardVH.calander.setTag(position);
                    dashboardVH.calander.setVisibility(View.GONE);
                    dashboardVH.edit.setTag(position);
                    if(isWIP) {
                        dashboardVH.layout_Yield_Q.setVisibility(View.GONE);
                        dashboardVH.border.setVisibility(View.GONE);
                        dashboardVH.layout_CompletedDate.setVisibility(View.GONE);
                    }else
                    {
                        dashboardVH.edit.setVisibility(View.GONE);
                        dashboardVH.done.setVisibility(View.GONE);
                    }
                    dashboardVH.done.setTag(R.string.crops,dashboardVH.itemView);
                    dashboardVH.done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            currentCard= (int) view.getTag();
                            View view1= (View) view.getTag(R.string.crops);
                            showD(view1);
//                            cropDoneDialog(null,currentCard);

                        }
                    });
                } catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }
    private void bindData(View view,String text){
        if(text==null || text.isEmpty()){
            text="N/A";
        }
        if(view instanceof TextView) {
            ((TextView) view).setText(text);
        }else if(view instanceof EditText){
            ((EditText) view).setText(text);
        }
    }

    private void showD(View view){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
    }
    AlertDialog editDialog,doneDialog,calenderDialog;

    public void showDialog(Activity activity, String msg, boolean isCancelBtnVisible, final boolean isClickable, final int Id){
        try {
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
//                        mApiManager.getDeleteCrop(croplist.get(currentCard).getId());
                        dialog.dismiss();
                    }else {
                        dialog.dismiss();
                    }
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



    private void setDataOnSpinner(Spinner spinner,String[] sarr){
        ArrayAdapter aa1= new ArrayAdapter(context,R.layout.spinner_item,sarr);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa1);
    }


    @Override
    public int getItemCount() {
        return  croplist.size();
    }
    @Override
    public int getItemViewType(int position) {

        return 0;
    }




    protected class DashboardVH extends RecyclerView.ViewHolder {

        private TextView crop,interCrop,variety,Seasion,Area_acre,Irrigation,StartDate,Completed_Date,Yield_Q,scientificName;
        private ViewGroup calander,edit,delete,done,layout_Yield_Q,layout_CompletedDate;
        private  View border;
        int position;
        private Button cancel;
        private EditText et;

        public DashboardVH(View itemView) {
            super(itemView);
            crop=itemView.findViewById(R.id.tv_crop);
            scientificName=itemView.findViewById(R.id.tv_scientificName);
            interCrop=itemView.findViewById(R.id.tv_interCrop);
            variety=itemView.findViewById(R.id.tv_variety1);
            Seasion=itemView.findViewById(R.id.tv_Seasion);
            Area_acre=itemView.findViewById(R.id.tv_Area_acre);
            Irrigation=itemView.findViewById(R.id.tv_Irrigation);
            StartDate=itemView.findViewById(R.id.tv_StartDate);
            Completed_Date=itemView.findViewById(R.id.tv_Completed_Date);
            Yield_Q=itemView.findViewById(R.id.tv_Yield_Q);
            calander=itemView.findViewById(R.id.calender);
            edit=itemView.findViewById(R.id.edit);
            delete=itemView.findViewById(R.id.landlyout_delete);
            done=itemView.findViewById(R.id.done);
            border=itemView.findViewById(R.id.border);
            layout_Yield_Q = itemView.findViewById(R.id.layout_Yield_Q);
            layout_CompletedDate = itemView.findViewById(R.id.layout_CompletedDate);

        }
    }

    private void removeItem(int position) {
        croplist.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, croplist.size());
    }
}