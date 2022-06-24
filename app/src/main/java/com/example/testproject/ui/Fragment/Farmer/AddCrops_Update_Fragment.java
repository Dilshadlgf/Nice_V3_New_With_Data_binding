package com.example.testproject.ui.Fragment.Farmer;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.databinding.AddcropUpdatefragment1Binding;
import com.example.testproject.databinding.AddcropUpdatefragmentBinding;
import com.example.testproject.model.LivestocksArrayModel;
import com.example.testproject.ui.Activity.FarmerMainActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Monika Sharma on 13-01-2021.
 */
public class AddCrops_Update_Fragment extends BaseFragment implements View.OnClickListener {
//    FarmerDetailsModel farmerDetailsModel;
    private String errorMessage = "";
    private AddcropUpdatefragment1Binding binding;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
//    private LoginDao loginDao;
    SharedPreferences.Editor editor;
//    private VillageDao villageDao;
    String enddate;
    String farmerid;
//    private FarmerListDao farmerDao;
    ArrayAdapter<String> adapterseason;
    ArrayAdapter<String> adaptercrop;
    public Spinner splivestock, spvariety, spstages, spvarietycrop, spseason, spareaunit,
            spirrigation, Seasonspinner, spcrop_new, spcrop;
    EditText Quantity, startdate, area;
    String selectedvarirtytxt, selectedlivestocktxt, cropid;
    private HashMap<String, String> spinnercropMap;
    private HashMap<String, String> spinnerIntercropMap;
    private HashMap<String, String> spinnerseasonMap;
    private HashMap<String, String> spinnervarietyMap;
    private HashMap<String, String> spinnerVillageMap;
    private HashMap<String, String> spinnerGrampanchayatMap;
    private HashMap<String, String> spinnerBlockMap;
    ArrayList<String> staticvarietylist;
    ArrayList<String> staticvarietyidlist;
    LivestocksArrayModel livestockmodel;
    ArrayAdapter<String> seasoncropadapter;
    ArrayAdapter<String> varietypadapter;
    String selectedareaUnit, selectedirrigation, varietyid;
    SharedPreferences pres;
    SharedPreferences.Editor edt;
    private boolean isWIP;
    public static AddCrops_Update_Fragment newInstance(Bundle args) {
        AddCrops_Update_Fragment fragment = new AddCrops_Update_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        layoutId = R.layout.addcrop_updatefragment1;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (AddcropUpdatefragment1Binding) viewDataBinding;
//        setupNetwork();
        pres = getActivity().getSharedPreferences("loginpref", MODE_PRIVATE);
        edt= pres.edit();
//        ((FragmentActivity) getActivity()).enableNavigationViews(false);
//        ((FragmentActivity) getActivity()).mBack.setVisibility(View.VISIBLE);
        if (getActivity() != null) {
            ((FarmerMainActivity) getActivity()).getToolIcon1().setVisibility(View.GONE);
        }
//        // ((FragmentActivity) getActivity()).setScreenTitle("Add Crops");
//        ((FragmentActivity)getActivity()).setScreenTitle(getString(R.string.addcrops));
////        addNotification();
//        loginDao = AppDatabase.getInstance(getContext()).loginDetails();
        spinnerVillageMap = new HashMap<>();
        spinnercropMap = new HashMap<>();
        spinnerIntercropMap=new HashMap<>();
        spinnerseasonMap = new HashMap<>();
        spinnervarietyMap=new HashMap<>();
        spinnerGrampanchayatMap = new HashMap<>();
        spinnerBlockMap = new HashMap<>();

        if(getArguments()!=null){
           isWIP= getArguments().getBoolean("isWIP",false);
        }

//        ((FragmentActivity) getActivity()).mBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getFragmentManager().popBackStack();
//                ((FragmentActivity) getActivity()).mBack.setVisibility(View.GONE);
//            }
//        });
        final Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog StartTime = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String myFormat="dd-MM-yyyy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                binding.startdate.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        binding.startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTime.show();
            }
        });
        binding.spinercrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    JsonObject jsonVarienty = new JsonObject();
                    JsonArray jsonArraystatus = new JsonArray();
                    jsonArraystatus.add("Active");
                    JsonArray jsonArraycropid = new JsonArray();
                    String cntCropId=spinnercropMap.get(binding.spinercrop.getSelectedItem().toString());
                    jsonArraycropid.add(cntCropId);
                    jsonVarienty.add("status", jsonArraystatus);
                    jsonVarienty.add("commodity", jsonArraycropid);

//                    mApiManager.getVarietyList(jsonVarienty);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] areaUnit=new String[]{"Acre","Hectare","Sq.Meter","Dismil"};
        setDataOnSpinner(binding.spareaunit,areaUnit);
        String[] irrigationtypes=new String[]{"Irrigated","Rainfed"};
        setDataOnSpinner(binding.spirrigation,irrigationtypes);



//        ((FragmentActivity) getActivity()).enableBottomNavigation(true);
//        ((FragmentActivity) getActivity()).btnPrint.setVisibility(View.GONE);

        try {
            // payload of crop
            JsonObject mainOb=new JsonObject();
            JsonArray statusArr=new JsonArray();
            statusArr.add("Active");
            JsonArray fs=new JsonArray();
//            fs.add(loginDao.getLoginResponse().getState().getId());
            JsonArray clasification=new JsonArray();
            clasification.add("Crop");
            mainOb.add("status",statusArr);
//            mainOb.add("state",fs);
            mainOb.add("classification",clasification);

//            mApiManager.getFarmerCropList(mainOb);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
//
            //paylod of intercrop
            JsonObject mainOb1=new JsonObject();
            JsonArray statusArra1=new JsonArray();

            statusArra1.add("WIP");
//            JsonArray fs1=new JsonArray();
//            fs1.add(loginDao.getLoginResponse().getId());

            mainOb1.add("status",statusArra1);
//            mainOb1.addProperty("farmer",loginDao.getLoginResponse().getId());
//
//            mApiManager.getFarmerInterCropList(mainOb1);
        } catch (Exception e) {
            e.printStackTrace();
        }



        try {
//            mApiManager.GetSeasonsRequest(loginDao.getLoginResponse().getToken());

            //paylod of season
            JsonObject jseason=new JsonObject();
            JsonArray jaseasonsatatus=new JsonArray();
            jaseasonsatatus.add("Active");
            JsonArray jaseasonstate=new JsonArray();
//            loginDao = AppDatabase.getInstance(context.getApplicationContext()).loginDetails();
//            jaseasonstate.add(loginDao.getLoginResponse().getState());
//            jaseasonstate.add("61c2c696923107fec7af80de");
            jaseasonstate.add("61c2c696923107fec7af80de");
//                            jaseasonstate.add(loginDao.getLoginResponse().getState());

            jseason.add("status",jaseasonsatatus);
            jseason.add("state",jaseasonstate);

//            mApiManager.getFamerSeasonList(jseason);

        } catch (Exception e) {
            e.printStackTrace();
        }







        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isvalid_AddCrops()) try {
                    {
                        edt.putString("startdate",binding.startdate.getText().toString());
                        edt.apply();
                        JsonObject cropobje = new JsonObject();

                        cropobje.addProperty("area",binding.area.getText().toString());
//                        cropobje.addProperty("farmer",loginDao.getLoginResponse().getId());
                        cropobje.addProperty("crop", spinnercropMap.get(binding.spinercrop.getSelectedItem().toString()));
                        cropobje.addProperty("interCrop",spinnerIntercropMap.get(binding.spintercrop.getSelectedItem().toString()));
//                        cropobje.addProperty("interCrop","61d523b1795d0b058a869a34");
//                        cropobje.addProperty("farmerId", farmerid);
                        cropobje.addProperty("irrigation", binding.spirrigation.getSelectedItem().toString());
                        //                   cropobje.addProperty("season","5bbee8a84f0cd4102d186c08");
                        cropobje.addProperty("season", spinnerseasonMap.get(binding.spinrseason.getSelectedItem().toString()));

                        String date11= CommonUtils.getServerFormatDate(binding.startdate.getText().toString(),"dd-MM-yyyy");

                        cropobje.addProperty("startDate", date11);
                        cropobje.addProperty("status", "WIP");

//                        if (isWIP) {
//                            cropobje.addProperty("status", "WIP");
//                        }else {
////                            cropobje.addProperty("status", "DONE");
//                        }

                        cropobje.addProperty("unit", binding.spareaunit.getSelectedItem().toString());
                        cropobje.addProperty("variety", spinnervarietyMap.get(binding.spvariey.getSelectedItem().toString()));
//                        mApiManager.addFarmerCrop(cropobje);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                else {
                    showDialog(getActivity(), errorMessage, false, true, 0);

                }
            }
        });

    }

    @Override
    public void onClick(View v) {

    }


//    @Override
//    public void onBackCustom() {
//        ((FragmentActivity) getActivity()).mBack.setVisibility(View.GONE);
//        getFragmentManager().popBackStack();
//    }

//    @Override
//    public void okDialogClick(int Id) {
//        if(Id==10)
//        onBackCustom();
//
//    }

    private void setDataOnSpinner(Spinner spinner,String[] sarr){
        ArrayAdapter aa1= new ArrayAdapter(getActivity(),R.layout.spinner_item,sarr);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa1);
    }

//    private void setupNetwork() {
//        mInterFace = new ApiResponseInterface() {
//            @Override
//            public void isError(String errorCode) {
//                if (errorCode.equalsIgnoreCase("Successfully Updated")) {
//                    CustomFragmentManager.replaceFragment(getFragmentManager(), new DashboardFragment(), true);
//                } else {
//                    showDialog(getActivity(), errorCode, false, true, 0);
//                }
//            }
//
//            @Override
//            public void isSuccess(Object response, int ServiceCode) {
//                if (ServiceCode == AppConstants.farmerCropList) try {
//                    {
//                        RootOneResModel rootOneResModel= (RootOneResModel) response;
//
//                        List<LivestocksArrayModel> croplist=rootOneResModel.getResponse().getData().getCommodity();
//                        String[] spinnerDistrictArray = new String[croplist.size() + 1];
//                        spinnerDistrictArray[0] = "---Select Crop---";
//                        spinnercropMap.put("0", "0");
//                        for (int i = 1; i <= croplist.size(); i++) {
//
//                            spinnerDistrictArray[i] = croplist.get(i-1).getCommonName();
//                            spinnercropMap.put(spinnerDistrictArray[i] , croplist.get(i - 1).getId());
//                        }
//                        String[] varietyArr=new String[]{"---Select Variety---"};
//                        setDataOnSpinner(binding.spinercrop,spinnerDistrictArray);
//                        setDataOnSpinner(binding.spvariey,varietyArr);
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }else if (ServiceCode == AppConstants.farmerInterCropList) try {
//                    {
//                        RootOneResModel rootOneResModel= (RootOneResModel) response;
//
//                        List<CropDataModel> croplist1=rootOneResModel.getResponse().getData().getFarmerCrop();
//                        String[] spinnerDistrictArray1 = new String[croplist1.size() + 1];
//                        spinnerDistrictArray1[0] = "---Select Inter Crop---";
//                        spinnerIntercropMap.put("0", "0");
//
//                        for (int i = 1; i <= croplist1.size(); i++) {
//
//                            if(croplist1.get(i-1).getRef().getCrop().getCommonName()==null)
//                            {
////                                binding.spintercrop.setTag(i,"Empty");
//
//                                spinnerDistrictArray1[i] = "Empty"+i;
//                                spinnerIntercropMap.put( "Empty"+i, croplist1.get(i - 1).getRef().getCrop().getId() );
//
//                            }
//                            else
//                            {
//
//                                spinnerDistrictArray1[i] = croplist1.get(i - 1).getRef().getCrop().getCommonName();
//                                spinnerIntercropMap.put( spinnerDistrictArray1[i] , croplist1.get(i - 1).getRef().getCrop().getId());
//                            }
//
//                        }
//
//                        setDataOnSpinner(binding.spintercrop,spinnerDistrictArray1);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//
//                } else  if (ServiceCode == AppConstants.FarmerSeasonList) try {
//                    {
//                        RootOneResModel seasonlist = (RootOneResModel) response;
//                        List<CropSeasonDataModel> mlist= seasonlist.getResponse().getData().getCropseason();
//                        String[] spinnerBlockArray = new String[mlist.size() + 1];
//                        spinnerBlockArray[0] = "---Select Season---";
//                        spinnerseasonMap.put("0", "0");
//
//                        if (0 < mlist.size()) {
//                            for (int i = 1; i <= mlist.size(); i++) {
//
//                                if (mlist.get(i-1).getName() != null) {
//
//                                    spinnerBlockArray[i] = mlist.get(i - 1).getName();
//                                    spinnerseasonMap.put( spinnerBlockArray[i], mlist.get(i - 1).getId());
//                                }
//
//                            }
//                        }
//
//                        setDataOnSpinner(binding.spinrseason,spinnerBlockArray);
//
////
//
//
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }else if(ServiceCode==AppConstants.FarmerVarietyList) {
//                    try {
//                        RootOneResModel rootOneResModel = (RootOneResModel) response;
//                        List<varietymodel> mlist = rootOneResModel.getResponse().getData().getVariety();
//
//                        String[] spinnerBlockArray2 = new String[mlist.size() + 1];
//                        spinnerBlockArray2[0] = "---Select Variety---";
//                        spinnervarietyMap.put("0","0");
//
//                        if (mlist.size()>0) {
//
//                            for (int i = 1; i <= mlist.size(); i++) {
//
//                                if (mlist.get(i-1).getName() != null) {
//
//                                    spinnerBlockArray2[i] = mlist.get(i - 1).getName();
//                                    spinnervarietyMap.put(spinnerBlockArray2[i], mlist.get(i - 1).getId());
//                                }
//                            }
//                        }
//
//
//                        setDataOnSpinner(binding.spvariey,spinnerBlockArray2);
//
////
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                } else if (ServiceCode == AppConstants.UpdateCrop) try {
//                    {
//                        SingleObjRootOneResModel livestockupdateresponse = (SingleObjRootOneResModel) response;
//
//                        showDialog(getActivity(),getString(R.string.crop_added_success),true,true,10);
//
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
////
//            }
//        };
//        mApiManager = new ApiManager(getContext(), mInterFace);
//    }

    private boolean isvalid_AddCrops() {
        if (binding.spinercrop.getSelectedItemPosition() == 0) {
            errorMessage = "Please Select Crop";
            return false;
        } else if (TextUtils.isEmpty(binding.startdate.getText().toString().trim())) {
            errorMessage = ("Please Select Date");
            return false;
        } else if (TextUtils.isEmpty(binding.area.getText().toString().trim())) {
            errorMessage = ("Please Enter Area");
            return false;
        }  else if (binding.spinercrop.getSelectedItemPosition() == 0) {
            errorMessage = ("Please Select InterCrop");
            return false;
        } else if (binding.spinrseason.getSelectedItemPosition() == 0) {
            errorMessage = ("Please Select Season");
            return false;
        } else if (binding.spvariey.getSelectedItemPosition() == 0) {
            errorMessage = ("Please Select Variety");
            return false;
        } else {
            return true;
        }
    }
}



