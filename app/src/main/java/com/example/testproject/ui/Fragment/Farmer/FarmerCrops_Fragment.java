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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.databinding.CrpcroplistFragmentBinding;
import com.example.testproject.databinding.FragmentQueryTabsBinding;
import com.example.testproject.model.LivestocksArrayModel;
import com.example.testproject.ui.Views.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created Suraj on 28-06-2022.
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
    private int p;
    ArrayList<String> staticvarietylist;
    ArrayList<String>staticvarietyidlist;
    LivestocksArrayModel livestockmodel;
    String selectedareaUnit,selectedirrigation,varietyid;
   // private FarmerListDao farmerDao;
    private EditText completeddate,et_yield,et_input_value,et_input_cost,etyieldvalue,et_remarks;
  //  FarmerLoginResponseDataModel farmerdetailmdl;
    private FragmentQueryTabsBinding binding;
    private NavController  navController;
//    private CrpcroplistFragmentBinding binding;

    public static FarmerCrops_Fragment newInstance(Bundle args) {
        FarmerCrops_Fragment fragment = new FarmerCrops_Fragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void init() {
        layoutId = R.layout.fragment_query_tabs;
//        layoutId =  R.layout.crpcroplist_fragment;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
//        binding = (CrpcroplistFragmentBinding) viewDataBinding;
        binding = (FragmentQueryTabsBinding) viewDataBinding;
        navController= NavHostFragment.findNavController(this);
        farmerdao = AppDatabase.getInstance(getContext()).farmerDao();
        binding.btnAddQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                if (p==0) {
                    bundle.putBoolean("isWIP", true);
                }else
                {
                    bundle.putBoolean("isWIP", false);
                }
                navController.navigate(R.id.action_farmerCrops_Fragment_to_addCrops_Update_Fragment,bundle);
            }
        });
        setupViewPager(binding.viewpager);
        binding.tab.setupWithViewPager(binding.viewpager);
        binding.tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();
                p=position;

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        Bundle bundleResolved = new Bundle();
        viewPagerAdapter.addFragment(CropWIPFragment.newInstance(bundleResolved), "WIP");

        viewPagerAdapter.addFragment(CropDONEFragment.newInstance(bundleResolved), "DONE");


        viewPager.setAdapter(viewPagerAdapter);
    }

}




