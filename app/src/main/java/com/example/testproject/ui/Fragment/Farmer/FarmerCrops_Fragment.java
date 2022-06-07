package com.example.testproject.ui.Fragment.Farmer;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.databinding.CrpcroplistFragmentBinding;
import com.example.testproject.model.LivestocksArrayModel;
import com.example.testproject.ui.Views.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Monika Sharma on 30-10-2020.
 */
public class FarmerCrops_Fragment extends BaseFragment {

    BottomNavigationView btmview;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
   // private LoginDao loginDao;
    SharedPreferences pres;

    Spinner sp_yilduniyt,et_Consumption;
    String errorMessage="";
   // ListItemClickListener farmerLivestockClickListner;
    ArrayAdapter<String> adaptercrop;
    Spinner splivestock,spvariety,spstages,spcropp,spvarietycrop,spseason,spareaunit,spirrigation;
    EditText Quantity,startdate,area;
    String selectedvarirtytxt,selectedlivestocktxt,cropid;
    private HashMap<Integer,String> spinnerlivestockMap;
    private HashMap<Integer,String> spinnervarietytMap;
    private HashMap<Integer,String> spinnerstagesMap;
    private HashMap<Integer,String> spinnercropMap;
    private FarmerDao farmerdao;
    ArrayList<String> staticvarietylist;
    ArrayList<String>staticvarietyidlist;
    LivestocksArrayModel livestockmodel;
    String selectedareaUnit,selectedirrigation,varietyid;
   // private FarmerListDao farmerDao;
    private EditText completeddate,et_yield,et_input_value,et_input_cost,etyieldvalue,et_remarks;
  //  FarmerLoginResponseDataModel farmerdetailmdl;
    private CrpcroplistFragmentBinding binding;

    public static FarmerCrops_Fragment newInstance(Bundle args) {
        FarmerCrops_Fragment fragment = new FarmerCrops_Fragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void init() {
        layoutId = R.layout.fragment_query_tabs;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (CrpcroplistFragmentBinding) viewDataBinding;
      farmerdao = AppDatabase.getInstance(getContext()).farmerDao();
        setupNetwork();

       // loginDao = AppDatabase.getInstance(getContext()).loginDetails();
////        LoginModel m=loginDao.getLoginResponse();
//        ((FragmentActivity) getActivity()).setScreenTitle(farmerDao.getFarmerListResponse().getName()+" Crops");
//        ((FragmentActivity) getActivity()).mBack.setVisibility(View.VISIBLE);
//        ((FragmentActivity) getActivity()).btnPrint.setVisibility(View.GONE);

        spinnervarietytMap=new HashMap<>();
        spinnercropMap= new HashMap<>();

        binding.edtCropslinearLyout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Bundle bundlecrop = new Bundle();
               //     bundlecrop.putString("farmerid", loginDao.getLoginResponse().getId());
                //    CustomFragmentManager.replaceFragment(getFragmentManager(), AddCrops_Update_Fragment.newInstance(bundlecrop), true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        setupViewPager(binding.viewpager);
        binding.tab.setupWithViewPager(binding.viewpager);
        binding.tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


//        try {
//            mApiManager.GetCroprequest(loginDao.getLoginResponse().getToken());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

     //   farmerLivestockClickListner= (position,id) ->
        {
            try {
                {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);

//                    dialog.setContentView(R.layout.complete_crop_fragment);
//                    sp_yilduniyt= dialog.findViewById(R.id.sp_yilduniyt);
//                    et_yield= dialog.findViewById(R.id.et_yield);
//                    completeddate= dialog.findViewById(R.id.completeddate);
//                    etyieldvalue= dialog.findViewById(R.id.etyieldvalue);
//                    et_input_cost=dialog.findViewById(R.id.et_input_cost);
//                    et_remarks= dialog.findViewById(R.id.et_remarks);
//                    et_Consumption=dialog.findViewById(R.id.et_Consumption);

                    List<String> irrigationlist = new ArrayList<String>();
                    irrigationlist.add("--Select yield--");
                    irrigationlist.add("quintal");
                    irrigationlist.add("ton");
                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, irrigationlist);
                    // Drop down layout style - list view with radio button
                    dataAdapter4.setDropDownViewResource(R.layout.spinner_item);
                    // attaching data adapter to spinner
                    sp_yilduniyt.setAdapter(dataAdapter4);

                    List<String> consumptionlist = new ArrayList<String>();
                    consumptionlist.add("--Select Consuption--");
                    consumptionlist.add("Market");
                    consumptionlist.add("Self Use");
                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, consumptionlist);
                    // Drop down layout style - list view with radio button
                    dataAdapter4.setDropDownViewResource(R.layout.spinner_item);
                    // attaching data adapter to spinner
                    et_Consumption.setAdapter(dataAdapter4);


                    Button dialogOKButton =dialog.findViewById(R.id.btn_ok);
                    completeddate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CommonUtils.openCalender(getActivity(),completeddate,false);


                        }
                    });

                    dialogOKButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(isValid()){
                                JsonObject cropendobj= new JsonObject();

                                cropendobj.addProperty("completedDate",completeddate.getText().toString());
                                cropendobj.addProperty("consumption",et_Consumption.getSelectedItem().toString());
                                cropendobj.addProperty("inputCost",Integer.parseInt(et_input_cost.getText().toString()));
                                cropendobj.addProperty("remarks",et_remarks.getText().toString());
                                cropendobj.addProperty("startDate","04-Nov-2020");
                                cropendobj.addProperty("status","DONE");
                                cropendobj.addProperty("yield",Integer.parseInt(et_yield.getText().toString()));
                                cropendobj.addProperty("yieldUnit",sp_yilduniyt.getSelectedItem().toString());
                                cropendobj.addProperty("yieldValue",Integer.parseInt(etyieldvalue.getText().toString()));
                                cropendobj.addProperty("version",0);
                               // mApiManager.farmercropRequest(farmerdetailmdl.getFarmer().getFarmerCrops().get(position).getId() ,loginDao.getLoginResponse().getToken(),cropendobj);
                                dialog.dismiss();}
                            else{
                                showDialog(getActivity(), errorMessage, false, true, 0);

                            }
                        }
                    });



                    dialog.show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

      //  ((FragmentActivity) getActivity()).mBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(getActivity()!=null) {
//                    onBackCustom();
//                }
//            }
//        });




    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        Bundle bundleResolved = new Bundle();
//        viewPagerAdapter.addFragment(CropWIPFragment.newInstance(bundleResolved), "WIP");
//
//        viewPagerAdapter.addFragment(CropDONEFragment.newInstance(bundleResolved), "DONE");


        viewPager.setAdapter(viewPagerAdapter);
    }




    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
                showDialog(getActivity(), errorCode, false, true, 0);

            }
            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if (ServiceCode == AppConstants.CROPListREquest) try {
                    {
                        List<LivestocksArrayModel> croplist = (ArrayList<LivestocksArrayModel>) response;
                        //                    Toast.makeText(getActivity(),livestock.getCommonName(),Toast.LENGTH_LONG).show();
                        String[] spinnerDistrictArray = new String[croplist.size()+1];
                        spinnerDistrictArray[0]="---Select Crop---";
                        spinnercropMap.put(0,"0");
                        for (int i = 1; i < croplist.size(); i++)
                        {
                            spinnercropMap.put(i,croplist.get(i-1).getId());
                            spinnerDistrictArray[i] = croplist.get(i-1).getCommonName();
                        }
                        adaptercrop =new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, spinnerDistrictArray);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                else if(ServiceCode == AppConstants.farmercropRequest) try {
                    {
                     //   EditProfileUserResponseModel editprofileUserResponse=(EditProfileUserResponseModel)response;
                      //  showDialog(getActivity(),editprofileUserResponse.getMessage() , false, true, 0);
                      //  CustomFragmentManager.replaceFragment(getFragmentManager(),new DashboardFragment(),true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                else if (ServiceCode == AppConstants.CROPvarietyRequest) try {
                    {
                        LivestocksArrayModel livestock = (LivestocksArrayModel) response;
                        spinnerstagesMap= new HashMap<>();
                        //                    Toast.makeText(getActivity(),livestock.getCommonName(),Toast.LENGTH_LONG).show();
                      //  String[] spinnerDistrictArray = new String[livestock.getVariety().size()+1];
                      //  spinnerDistrictArray[0]="---Select Varieties---";
                        spinnervarietytMap.put(0,"0");
//                        for (int i = 1; i < livestock.getVariety().size(); i++)
//                        {
////                            spinnervarietytMap.put(i,livestock.getVariety().get(i-1).getId());
////                            spinnerDistrictArray[i] = livestock.getVariety().get(i-1).getName();
//                        }
//                        ArrayAdapter<String> adapter =new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, spinnerDistrictArray);
//                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        spvarietycrop.setAdapter(adapter);



                     //   spvarietycrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                     //       @Override
//                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                                {
//                                    String[] spinnerDistrictArraystage = new String[livestock.getVariety().size() + 1];
//                                    spinnerDistrictArraystage[0] = "---Select Variety---";
//                                    spinnerstagesMap.put(0, "0");
//                                    for (int j = 1; j<=livestock.getVariety().size(); j++) {
//                                        spinnerstagesMap.put(j, livestock.getVariety().get(j - 1).getId());
//                                        spinnerDistrictArraystage[j] = livestock.getVariety().get(j - 1).getName();
//                                    }
//                                    ArrayAdapter<String> adapterstage = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerDistrictArraystage);
//                                    adapterstage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                    spseason.setAdapter(adapterstage);
//                                }

                       //     }
//
//                            @Override
//                            public void onNothingSelected(AdapterView<?> adapterView) {
//
//                            }
//                        });




                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        mApiManager = new ApiManager(getActivity(), mInterFace);

    }
    private boolean isValid() {

        if (TextUtils.isEmpty(et_yield.getText().toString().trim())) {
            errorMessage = "All Fields are Required";
            return false;
        }
        else if (TextUtils.isEmpty(et_input_cost.getText().toString().trim())) {
            errorMessage = "All Fields are Required";
            return false;
        }
        else if (TextUtils.isEmpty(etyieldvalue.getText().toString().trim())) {
            errorMessage = "All Fields are Required";
            return false;
        }
        else if (TextUtils.isEmpty(et_remarks.getText().toString().trim())) {
            errorMessage = "All Fields are Required";
            return false;
        } /*else if (sp_yilduniyt.getSelectedItemPosition()==0) {
            errorMessage = getString(R.string.gender_required);
            return false;
        }*/
        else {
            return true;
        }
    }


}




