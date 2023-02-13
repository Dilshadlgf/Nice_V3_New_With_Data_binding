package com.example.testproject.ui.Fragment.Farmer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testproject.Adapter.WeatherAdapter;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.Util.JsonMyUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.FragmentWeatherBinding;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.model.UserModel;
import com.example.testproject.model.WeatherModel;
import com.example.testproject.ui.Activity.farmer.FarmerMainActivity;
import com.example.testproject.ui.base.BaseFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
     Created By Suraj Kumar Singh On 28-07-2022
 */
public class WeatherFragment extends BaseFragment {
   private FragmentWeatherBinding binding;
   private UserDao userDao;
   private ApiResponseInterface mInterFace;
   private ApiManager mApiManager;
   private int pageNo = 1, maxPage = 1;
   private boolean loadmore;
   private WeatherAdapter adaptor;
   private HashMap<String, String> stateHash = new HashMap<>();
   private String fDate, tDate, stateId, dialogtdate, dialogfdate;
   private List<WeatherModel>list;
   private NavController navController;
   @Override
   protected void init() {
      layoutId = R.layout.fragment_weather;
   }

   @RequiresApi(api = Build.VERSION_CODES.M)
   @Override
   protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
      binding = (FragmentWeatherBinding) viewDataBinding;
      ((FarmerMainActivity) getActivity()).setTittle("Weather Data");
      ((FarmerMainActivity) getActivity()).showHideEditIcon(false);
      userDao = AppDatabase.getInstance(getContext()).userdao();
      navController= NavHostFragment.findNavController(this);
      setUpNetWork();
      fDate = CommonUtils.getCurrentDateForServer();
      tDate = CommonUtils.getFutuerAndBackDates(7, false, "yyyy-MM-dd'T'HH:mm:ss'Z'");
      stateId = userDao.getUserResponse().state;

      binding.weatherRcv.setLayoutManager(new LinearLayoutManager(getActivity()));
      binding.weatherRcv.setItemAnimator(new DefaultItemAnimator());

      binding.weatherRcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
         @Override
         public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (!recyclerView.canScrollVertically(1)) { //1 for down
               if (loadmore) {
                  loadmore = false;
                  if (pageNo < maxPage) {
                     pageNo++;
                     loadData(pageNo);
                  }
               }
            }
         }
      });
      loadData(1);
      binding.weatherfilter.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            JsonObject mainObj = new JsonObject();
            JsonArray statusArray = new JsonArray();
            statusArray.add("Active");
            mainObj.add("status", statusArray);
            mApiManager.stateWeather(mainObj);

         }
      });
   }

   private void showCustomDialog(List<String> stringList) {
      final Dialog dialog = new Dialog(getContext());
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      dialog.setCancelable(false);
      dialog.setContentView(R.layout.custom_dilouge);
      TextView textView = dialog.findViewById(R.id.tvstart);
      TextView textView1 = dialog.findViewById(R.id.tvend);
      textView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            final Calendar newCalendar = Calendar.getInstance();
            final DatePickerDialog StartTime = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
               public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                  Calendar newDate = Calendar.getInstance();
                  newDate.set(year, monthOfYear, dayOfMonth);
                  String myFormat = "dd-MM-yyyy";
                  SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                  textView.setText(dateFormat.format(newDate.getTime()));
                  dialogfdate = dateFormat.format(newDate.getTime());
                  dialogfdate = CommonUtils.getServerFormatDate(dialogfdate, "dd-MM-yyyy");
               }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            StartTime.show();
         }
      });
      textView1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            final Calendar newCalendar = Calendar.getInstance();
            final DatePickerDialog StartTime = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
               public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                  Calendar newDate = Calendar.getInstance();
                  newDate.set(year, monthOfYear, dayOfMonth);
                  String myFormat = "dd-MM-yyyy";
                  SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                  textView1.setText(dateFormat.format(newDate.getTime()));
                  dialogtdate = dateFormat.format(newDate.getTime());
                  dialogtdate = CommonUtils.getServerFormatDate(dialogtdate, "dd-MM-yyyy");

               }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            StartTime.show();
         }
      });
      Spinner spinner = dialog.findViewById(R.id.sp_spinner);
      ArrayAdapter ad = new ArrayAdapter(getContext(), R.layout.spinner_textview, stringList);
      ad.setDropDownViewResource(R.layout
              .textview);
      spinner.setAdapter(ad);

      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String mid = stateHash.get(spinner.getSelectedItem().toString());
            stateId = mid;
            Log.d("", "mid" + mid);
         }
         @Override
         public void onNothingSelected(AdapterView<?> parent) {
         }
      });
      Button button = dialog.findViewById(R.id.btnsearch);
      Button button1 = dialog.findViewById(R.id.btnreset);
      button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            dialog.dismiss();
            if (dialogtdate != null && !dialogtdate.isEmpty() && dialogfdate != null && !dialogfdate.isEmpty()) {
               fDate = dialogfdate;
               tDate = dialogtdate;
               adaptor=null;
               pageNo = 1;
               loadData(pageNo);
            } else {
               Toast.makeText(getContext(), "Plaease Enter Data", Toast.LENGTH_SHORT).show();
            }

         }
      });
      button1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            dialog.dismiss();
         }
      });
      dialog.show();
   }

   private void loadData(int pageNo) {
      JsonObject mainObj = new JsonObject();
      JsonArray stateObject = new JsonArray();
      stateObject.add(stateId);
      mainObj.add("state", stateObject);
      JsonArray statusArray = new JsonArray();
      statusArray.add("Active");
      mainObj.add("status", statusArray);
      JsonObject dateObject = new JsonObject();
      dateObject.addProperty("from", fDate);
      dateObject.addProperty("to", tDate);
      mainObj.add("dateRange", dateObject);
      mApiManager.getWeatherData(mainObj, "" + pageNo);
   }

   private void setUpNetWork() {
      mInterFace = new ApiResponseInterface() {

         @Override
         public void isError(String errorCode) {

         }

         @Override
         public void isSuccess(Object response, int ServiceCode) {
            if (ServiceCode == AppConstants.WeatherData) {
               loadmore = true;

               RootOneModel rootOneModel = (RootOneModel) response;
               if (rootOneModel.getResponse().getData().getStateWeatherModels()!=null){
                  list= JsonMyUtils.getPojoFromJsonArr(rootOneModel.getResponse().getData().getStateWeatherModels().getAsJsonArray(),WeatherModel.class);
                  maxPage = rootOneModel.getResponse().getData().getPagination().getTotalPage();
                  if (list != null && list.size() > 0) {
                     if (adaptor == null) {
                        adaptor = new WeatherAdapter(getActivity(), list);
                        binding.weatherRcv.setAdapter(adaptor);
                     } else {
                        adaptor.addToList(list);
                     }
               }

               }

            } else if (ServiceCode == AppConstants.StateWeather) {
               RootOneModel rootOneModel = (RootOneModel) response;
               if (rootOneModel.getResponse().getData().data!=null){
                  List<UserModel>userModel= JsonMyUtils.getPojoFromJsonArr(rootOneModel.getResponse().getData().data.getAsJsonArray(),UserModel.class);
                  List<String> nameList = new ArrayList<>();
                  nameList.add("---Select State---");
                  for (int i = 0; i < userModel.size(); i++) {
                     stateHash.put(userModel.get(i).name, list.get(i).id);
                     nameList.add(userModel.get(i).name);
                  }
                  showCustomDialog(nameList);

               }

            }
         }
      };
      mApiManager = new ApiManager(getActivity(), mInterFace);

   }

   @Override
   public void onBackCustom() {
      ((FarmerMainActivity) getActivity()).setTittle(getString(R.string.dashboard));
      ((FarmerMainActivity) getActivity()).hideIcon();
      navController.navigate(R.id.dashboardfragment);
   }
}