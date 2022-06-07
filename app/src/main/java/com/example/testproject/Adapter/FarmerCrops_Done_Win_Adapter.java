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
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.model.RootOneResModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Monika Sharma on 29-10-2020.
 */
public class FarmerCrops_Done_Win_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>   {

    private static final int ITEM = 0;
   // private LoginDao loginDao;

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

    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
//                showDialog(getActivity(), errorCode, false, true, 0);
            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if(ServiceCode== AppConstants.farmerCropList){
                    RootOneResModel rootOneResModel= (RootOneResModel) response;
                    Object mlist=rootOneResModel.getResponse().getData().getCommodity();
                    editDialog(mlist,AppConstants.farmerCropList);
                }else

                if(ServiceCode==AppConstants.farmerInterCropList)
                {
                    RootOneResModel rootOneResModel= (RootOneResModel) response;
                    Object mlist=rootOneResModel.getResponse().getData().getFarmerCrop();
                    editDialog(mlist,AppConstants.farmerInterCropList);
                }else

                if(ServiceCode==AppConstants.FarmerVarietyList)
                {
                    RootOneResModel rootOneResModel= (RootOneResModel) response;
                    Object mlist=rootOneResModel.getResponse().getData().getVariety();
                    editDialog(mlist,AppConstants.FarmerVarietyList);

                }else

                if(ServiceCode==AppConstants.FarmerSeasonList)
                {
                    RootOneResModel rootOneResModel= (RootOneResModel) response;
                    Object mlist=rootOneResModel.getResponse().getData().getCropseason();
                    editDialog(mlist,AppConstants.FarmerSeasonList);

                }else


                if(ServiceCode==AppConstants.DeletecropList)
                {
                    SingleObjRootOneResModel rootOneResModel= (SingleObjRootOneResModel) response;
                    if(rootOneResModel.getResponse().getStatusCode()==200) {
                        showDialog((Activity) context, "Crop Successfully Deleted",false,true, 0);
                        croplist.remove(currentCard);
                        notifyItemRemoved(currentCard);
                    }else{
                        showDialog((Activity) context, "Try after sometime",false,false, 0);
                    }
//                     deleteFarmerCrop(mlist,AppConstants.DeletecropList);
                }else if(ServiceCode==AppConstants.CropDoneRequest)
                {
                    SingleObjRootOneResModel rootOneResModel= (SingleObjRootOneResModel) response;
                    if(rootOneResModel.getResponse().getStatusCode()==200) {
                        showDialog((Activity) context, "Crop Done Successful",false,true, 0);
                        if(editDialog!=null){
                            editDialog.dismiss();
                        }
                    }else{
                        showDialog((Activity) context, "Try after sometime",false,false, 0);
                    }
//                     deleteFarmerCrop(mlist,AppConstants.DeletecropList);
                }else if(ServiceCode==AppConstants.CropCalenderRequest)
                {
                    SingleObjRootOneResModel rootOneResModel= (SingleObjRootOneResModel) response;
                    if(rootOneResModel.getResponse().getStatusCode()==200) {
                        showDialog((Activity) context, "Calender Successfully Updated",false,false, 0);

                    }else{
                        showDialog((Activity) context, "Try after sometime",false,false, 0);
                    }
//                     deleteFarmerCrop(mlist,AppConstants.DeletecropList);
                }else if(ServiceCode==AppConstants.UpdateCrop)
                {
                    SingleObjRootOneResModel rootOneResModel= (SingleObjRootOneResModel) response;
                    if(rootOneResModel.getResponse().getStatusCode()==200) {

                        showDialog((Activity) context, "Crop Successfully Updated",false,true, 0);
                        if(doneDialog!=null){
                            doneDialog.dismiss();
                        }
                        if(spCrop!=null ){
                            listItemClickListener.onItemClick(spCrop.getSelectedItemPosition()-1,"");
                        }else {
                            listItemClickListener.onItemClick(currentCard,"");
                        }

                    }else{

                        showDialog((Activity) context, "Try after sometime",false,false, 0);
                    }
//                     deleteFarmerCrop(mlist,AppConstants.DeletecropList);
                }


            }
        };
        mApiManager = new ApiManager(context, mInterFace);
    }



    private ListItemClickListener listItemClickListener;
    public FarmerCrops_Done_Win_Adapter(List<CropDataModel> croplist,ListItemClickListener listItemClickListener, Context context,Boolean isWIP) {
        this.croplist = croplist;
        this.context = context;
        this.isWIP=isWIP;
        setupNetwork();
        this.listItemClickListener=listItemClickListener;
        loginDao = AppDatabase.getInstance(context.getApplicationContext()).loginDetails();
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
                    dashboardVH.crop.setText(model.getRef().getCrop().getCommonName());
                    dashboardVH.scientificName.setText(model.getRef().getCrop().getScientificName());
                    dashboardVH.interCrop.setText(model.getRef().getInterCrop().getCommonName());

                    if(model.getRef().getVariety().getName()==null) {
                        dashboardVH.variety.setText(" ");
                    }else {
                        dashboardVH.variety.setText(model.getRef().getVariety().getName());
                    }
                    dashboardVH.Seasion.setText(model.getRef().getSeason().getName());
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

                    if(model.getStartDate()==null)
                    {
//                        dashboardVH.StartDate.setText("DD-MM-YYYY");

                    }else {
                        dashboardVH.StartDate.setText(CommonUtils.getDateFormat(model.getStartDate()));
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
//

                    // when user clicked  the Calender
//                    dashboardVH.calander.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            currentCard= (int) view.getTag();
//                            calenderDialog(null,0);
//
//
//                        }
//                    });

                    // when user clicked  the edit
                    dashboardVH.edit.setOnClickListener(new View.OnClickListener() {


                        @Override
                        public void onClick(View view) {
                            currentCard= (int) view.getTag();
                            // payload of crop
                            JsonObject mainOb=new JsonObject();
                            JsonArray statusArr=new JsonArray();
                            statusArr.add("Active");
                            JsonArray fs=new JsonArray();
                            fs.add("farmer state");
                            JsonArray cl=new JsonArray();
                            cl.add("Crop");
                            mainOb.add("status",statusArr);
                            mainOb.add("classification",cl);
//                            mainOb.add("state",fs);

                            mApiManager.getFarmerCropList(mainOb);



                            //paylod of intercrop
                            JsonObject mainOb1=new JsonObject();
                            JsonArray statusArra1=new JsonArray();

                            statusArra1.add("WIP");
//                            JsonArray famerIdArr=new JsonArray();
//                            famerIdArr.add(loginDao.getLoginResponse().getId());


                            mainOb1.add("status",statusArra1);
                            mainOb1.addProperty("farmer",loginDao.getLoginResponse().getId());

                            mApiManager.getFarmerInterCropList(mainOb1);



                            //paylod of season
                            JsonObject jseason=new JsonObject();
                            JsonArray jaseasonstate=new JsonArray();

                            jaseasonstate.add("61c2c696923107fec7af80de");
                            jseason.add("status",statusArr);
                            jseason.add("state",jaseasonstate);

                            mApiManager.getFamerSeasonList(jseason);

                            //paylod of season
                            JsonObject vObj=new JsonObject();
                            JsonArray cropIdArr=new JsonArray();

                            cropIdArr.add(cntCropId);
                            vObj.add("status",statusArr);
                            vObj.add("commodity",cropIdArr);

                            mApiManager.getVarietyList(vObj);


//


                        }
                    });

                    // when user clicked  the delete
                    dashboardVH.delete.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("WrongViewCast")
                        @Override
                        public void onClick(View view) {
                            currentCard= (int) view.getTag();
                            showDialog((Activity) context, "Do You Want To Delete Crop",true,false, 1);


                        }
                    });

                    // when user clicked  the done
                    dashboardVH.done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            currentCard= (int) view.getTag();
                            cropDoneDialog(null,currentCard);

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
                        mApiManager.getDeleteCrop(croplist.get(currentCard).getId());
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

    private void editDialog(Object obj,int code){

        if(editDialog==null) {
            editdialogapiHitCount=0;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View alert = LayoutInflater.from(context).inflate(R.layout.alertdailog_edit, null);
            spCrop=alert.findViewById(R.id.spinercrop);
            spInterCrop=alert.findViewById(R.id.spinterintercrop);
            spVariety=alert.findViewById(R.id.spAlertvariey);
            spSeason=alert.findViewById(R.id.spinrAlertseason);
            spIrrigation=alert.findViewById(R.id.spareirrigationtype);
            spAreaUnit=alert.findViewById(R.id.sparearea);
            ed_crop_date=alert.findViewById(R.id.editstartdate);
            edit_submit_btn=alert.findViewById(R.id.btn_update);
            alert.findViewById(R.id.btn_Cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editDialog.dismiss();
                }
            });
            txArea=alert.findViewById(R.id.area);
            builder.setView(alert);
            builder.setCancelable(true);
            editDialog= builder.create();
            String[] areaUnit=new String[]{"Acre","Hectare","Sq.Meter","Dismil"};
            setDataOnSpinner(spAreaUnit,areaUnit);
            String[] irrigationtypes=new String[]{"Irrigated","Rainfed"};
            setDataOnSpinner(spIrrigation,irrigationtypes);
            final Calendar newCalendar = Calendar.getInstance();
            final DatePickerDialog  StartTime = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    String myFormat="dd-MM-yyyy";
                    SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                    ed_crop_date.setText(dateFormat.format(newDate.getTime()));
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            ed_crop_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartTime.show();
                }
            });
            edit_submit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JsonObject mainObj=new JsonObject();
                    mainObj.addProperty("crop",cntCropId);
                    mainObj.addProperty("area",txArea.getText().toString());
                    mainObj.addProperty("farmer",loginDao.getLoginResponse().getId());
                    mainObj.addProperty("id",croplist.get(currentCard).getId());
                    mainObj.addProperty("interCrop",interCropHashmap.get(spInterCrop.getSelectedItem().toString()));
                    mainObj.addProperty("irrigation",spIrrigation.getSelectedItem().toString());
                    mainObj.addProperty("season",seasonHashmap.get(spSeason.getSelectedItem().toString()));
                   String date11=CommonUtils.getServerFormatDate(ed_crop_date.getText().toString(),"dd-MM-yyyy");

                    mainObj.addProperty("startDate",date11);
                    if (isWIP) {
                        mainObj.addProperty("status", "WIP");
                    }else {
                        mainObj.addProperty("status", "DONE");
                    }
                    mainObj.addProperty("unit",spAreaUnit.getSelectedItem().toString());
                    mainObj.addProperty("variety",varietyHashmap.get(spVariety.getSelectedItem().toString()));
                    mainObj.addProperty("year",0);

                    mApiManager.editCrop(mainObj);
                    editDialog.dismiss();
                }
            });
        }


        if(code==AppConstants.farmerCropList){
            editdialogapiHitCount++;
            List<LivestocksArrayModel> mlist= (List<LivestocksArrayModel>) obj;
            String[]  sarr=new String[mlist.size()+1];
            sarr[0]="----Select----";
            for (int i = 0; i < mlist.size(); i++) {
                sarr[i+1]=mlist.get(i).getCommonName();
                if(mlist.get(i).getCommonName()==null){
                    sarr[i+1]="no name"+(i+i);
                }
                cropHashmap.put(mlist.get(i).getCommonName(),mlist.get(i).getId());
            }

            Spinner finalSpCrop = spCrop;
            spCrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position>0) {
                                JsonObject jsonVarienty = new JsonObject();
                                JsonArray jsonArraystatus = new JsonArray();
                                jsonArraystatus.add("Active");
                                JsonArray jsonArraycropid = new JsonArray();
                                cntCropId=cropHashmap.get(finalSpCrop.getSelectedItem().toString());
                                jsonArraycropid.add(cntCropId);

                                jsonVarienty.add("status", jsonArraystatus);
                                jsonVarienty.add("commodity", jsonArraycropid);

                                mApiManager.getVarietyList(jsonVarienty);
                            }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            setDataOnSpinner(spCrop,sarr);
        }
        else if(code==AppConstants.farmerInterCropList) {
            editdialogapiHitCount++;
            List<CropDataModel> mlist = (List<CropDataModel>) obj;
            String[] sarr ;
            if (mlist!=null && mlist.size() > 0) {
                sarr= new String[mlist.size()];
                for (int i = 0; i < mlist.size(); i++) {

                    if(mlist.get(i).getRef().getCrop().getCommonName()!=null) {
                        sarr[i] = mlist.get(i).getRef().getCrop().getCommonName();
                    }else{
                        sarr[i]="no name here";
                    }
                    interCropHashmap.put(sarr[i],mlist.get(i).getId());
                }
            }else{
                 sarr = new String[]{"---Empty---"};
            }
            setDataOnSpinner(spInterCrop,sarr);
        }
        else if(code==AppConstants.FarmerSeasonList){
            editdialogapiHitCount++;
            List<CropSeasonDataModel> mlist=(List<CropSeasonDataModel>) obj;

            String[]  sarr;
            if (mlist!=null && mlist.size() > 0) {
                sarr=new String[mlist.size()];
                for (int i = 0; i < mlist.size(); i++) {
                    sarr[i] = mlist.get(i).getName();
                    seasonHashmap.put(sarr[i],mlist.get(i).getId());
                }
            }else {
                sarr = new String[]{"---Empty---"};
            }

            setDataOnSpinner(spSeason,sarr);
        }
         else if(code==AppConstants.FarmerVarietyList){
            editdialogapiHitCount++;
             List<varietymodel> mlist=(List<varietymodel>) obj;

             String[]  sarr;
            if (mlist!=null && mlist.size() > 0) {
                sarr=new String[mlist.size()];
                for (int i = 0; i < mlist.size(); i++) {
                    sarr[i] = mlist.get(i).getName();
                    varietyHashmap.put(sarr[i],mlist.get(i).getId());
                }
            }else {
                sarr = new String[]{"---Empty---"};
            }

            setDataOnSpinner(spVariety,sarr);

         }
        if(editdialogapiHitCount>=4){
            if(!editDialog.isShowing()){
                editDialog.show();
            }
        }
    }

    private void calenderDialog(Object obj,int code){

        if(calenderDialog==null) {
            editdialogapiHitCount=0;

            AlertDialog.Builder builder = new AlertDialog.Builder(context);


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            AlertdailogCalenderBinding binding = AlertdailogCalenderBinding.inflate(inflater);


            builder.setView(binding.getRoot());
            builder.setCancelable(true);
            calenderDialog= builder.create();


            String[] irrigationtypes=new String[]{"Irrigated","Rainfed"};
            setDataOnSpinner(binding.spinnerIrrgation,irrigationtypes);


            final Calendar newCalendar = Calendar.getInstance();
            final DatePickerDialog  startDate = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    String myFormat="dd-MM-yyyy";
                    SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                    binding.tvStartDate.setText(dateFormat.format(newDate.getTime()));
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


            final DatePickerDialog  endDate = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    String myFormat="dd-MM-yyyy";
                    SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                    binding.tvEndDate.setText(dateFormat.format(newDate.getTime()));
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            binding.tvStartDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startDate.show();
                }
            });
            binding.tvEndDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    endDate.show();
                }
            });

            binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JsonObject mainObj=new JsonObject();
                    mainObj.addProperty("","");
                    Toast.makeText(context,"Done",Toast.LENGTH_SHORT).show();
                }
            });
            binding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calenderDialog.dismiss();
                }
            });
        }




            if(!calenderDialog.isShowing()){
                calenderDialog.show();
            }
    }
    private void cropDoneDialog(Object obj,int index){

        if(doneDialog==null) {
            editdialogapiHitCount=0;

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            FarmerCropdoneAlertboxBinding binding = FarmerCropdoneAlertboxBinding.inflate(inflater);


            builder.setView(binding.getRoot());
            builder.setCancelable(true);
            doneDialog= builder.create();
            String[] areaUnit=new String[]{"Quiental","Ton"};
            setDataOnSpinner(binding.sparearea,areaUnit);
            String[] irrigationtypes=new String[]{"Market","SelfUse"};
            setDataOnSpinner(binding.spareirConsumption,irrigationtypes);
            final Calendar newCalendar = Calendar.getInstance();
            final DatePickerDialog  StartTime = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    String myFormat="dd-MM-yyyy";
                    SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                    binding.startdate.setText(dateFormat.format(newDate.getTime()));
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

//            final Calendar newCalendar = Calendar.getInstance();
            final DatePickerDialog  compTime = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    String myFormat="dd-MM-yyyy";
                    SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                    binding.compddate.setText(dateFormat.format(newDate.getTime()));
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            binding.compddate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    compTime.show();
                }
            });

            binding.startdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartTime.show();
                }
            });
            binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int index= (int) v.getTag();
                    JsonObject mainObj=new JsonObject();
                    mainObj.addProperty("area",binding.tvYield.getText().toString());
                    String date12=CommonUtils.getServerFormatDate(binding.compddate.getText().toString(),"dd-MM-yyyy");
                    mainObj.addProperty("completedDate",date12);
                    mainObj.addProperty("consumption",binding.spareirConsumption.getSelectedItem().toString());
                    mainObj.addProperty("crop",croplist.get(currentCard).getId());
                    mainObj.addProperty("farmer",loginDao.getLoginResponse().getId());
                    mainObj.addProperty("id",croplist.get(index).getId());
                    mainObj.addProperty("inputCost",Integer.parseInt(binding.tvInputCost.getText().toString()));
                    mainObj.addProperty("interCrop",croplist.get(currentCard).getRef().getInterCrop().getId());
                    mainObj.addProperty("irrigation",croplist.get(currentCard).getCompletedDate());
                    mainObj.addProperty("remarks",binding.editTextTextMultiLine.getText().toString());
                    mainObj.addProperty("season",croplist.get(currentCard).getRef().getSeason().getId());


                    String date11=CommonUtils.getServerFormatDate(binding.startdate.getText().toString(),"dd-MM-yyyy");
                    mainObj.addProperty("startDate",date11);
                    mainObj.addProperty("status", "DONE");
//                    if (isWIP) {
//                        mainObj.addProperty("status", "WIP");
//                    }else {
//                        mainObj.addProperty("status", "DONE");
//                    }
                    mainObj.addProperty("variety",croplist.get(currentCard).getRef().getVariety().getId());
                    mainObj.addProperty("year",0);
                    mainObj.addProperty("yield",binding.tvYield.getText().toString());
                    if(binding.sparearea.getSelectedItemPosition()==0){
                        mainObj.addProperty("yieldUnit","Q");
                    }else{
                        mainObj.addProperty("yieldUnit","T");
                    }

                    mainObj.addProperty("yieldValue",Integer.parseInt(binding.tvYieldvalue.getText().toString()));

                    mApiManager.updateCrop(mainObj);


                }
            });
            binding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doneDialog.dismiss();
                }
            });

        }



            if(!doneDialog.isShowing()){
                doneDialog.show();
            }
    }




//    private void deleteItem(int positon) {
//
//        croplist.remove(positon);
//        notifyItemRemoved(positon);
//        notifyItemRangeChanged(positon,croplist.size());
//    }

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
