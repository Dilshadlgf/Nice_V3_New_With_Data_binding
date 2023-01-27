package com.example.testproject.ui.Fragment.Farmer;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

//import com.example.testproject.databinding.ActivityDsaboardBinding;
//import com.example.testproject.databinding.ActivityLoginBinding;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.FarmerDao;
import com.example.testproject.database.Dao.WeatherDetailsDao;
import com.example.testproject.databinding.ActivityDsaboardBinding;
import com.example.testproject.model.SingleObjectModel.SingleObjRootOneResModel;
import com.example.testproject.model.WeatherModel;
import com.example.testproject.model.WeatherStateModel;
import com.example.testproject.model.WeatherTemperatureModel;
import com.example.testproject.ui.Activity.FarmerMainActivity;
import com.example.testproject.ui.base.BaseFragment;

public class DashboardFragment extends BaseFragment {
   private ActivityDsaboardBinding binding;
    NavController navController ;
    private static boolean hitFirstWeatherApi;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    WeatherDetailsDao weatherDetailsDao;
    private FarmerDao farmerDao;
    protected void init() {
        layoutId=R.layout.activity_dsaboard;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

        binding= (ActivityDsaboardBinding) viewDataBinding;
        navController= NavHostFragment.findNavController(this);
        setupNetwork();
        farmerDao= AppDatabase.getInstance(getContext()).farmerDao();
        weatherDetailsDao = AppDatabase.getInstance(getContext()).weatherDetailsResponseModel();
        setWeatherData(weatherDetailsDao.getWeatherDetailsResponseModel());

        binding.contentcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             navController.navigate(R.id.action_dashboard_to_contentFragment);
            }
        });
        binding.profilecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_dashboard_to_profileFragment);

            }
        });
        binding.layQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("queryModule","common");
                navController.navigate(R.id.action_dashboard_to_queryTabFragment,bundle);
            }
        });
        binding.notificationcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_dashboard_to_notificationListFragment);
            }
        });

        if(!hitFirstWeatherApi){
            hitFirstWeatherApi=true;
            mApiManager.getweatherStateData(farmerDao.getFarmer().getStateid());
        }
    }
    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void isError(String errorCode) {
//                showDialog(getActivity(), errorCode, false, true, 0);
                if(getActivity()!=null)
                    Toast.makeText(getActivity(),errorCode,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if(ServiceCode==AppConstants.WeatherAlert_LIST_REQUEST){
                    SingleObjRootOneResModel rootOneResModel= (SingleObjRootOneResModel) response;
                    WeatherStateModel stateModel=rootOneResModel.getResponse().getData().getStateweatherdata();
                    setWeatherData(stateModel);
                    if(stateModel!=null){
                        WeatherStateModel old= weatherDetailsDao.getWeatherDetailsResponseModel();
                        if(old!=null){
                            stateModel.setDbid(old.getDbid());
                            weatherDetailsDao.updateWeatherDetailsResponseModel(stateModel);
                        }else{
                            weatherDetailsDao.insertWeatherDetailsResponseModel(stateModel);
                        }
                    }

                }
            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }
    private void setWeatherData(WeatherStateModel stateModel){

        if(stateModel!=null) {
            WeatherTemperatureModel temp = stateModel.getWeatherData().getTemp();
            binding.maxtempTxt.setText(temp.getDay().intValue()+"°C" );
            binding.mintempTxt.setText("Max: " + temp.getMax().intValue()+"°C");
            binding.maxxtempTxt.setText("Min: " + temp.getMin().intValue()+"°C");
            WeatherModel weatherModel=stateModel.getWeatherData().getWeather().get(0);
            if(weatherModel.getId().intValue()==800){
                //  binding.imgTxt.setImageResource(R.drawable.ic_baseline_wb_sunny_24);
            }else if(weatherModel.getId().intValue()==802||weatherModel.getId().intValue()==803|| weatherModel.getId().intValue()==804){
                //  binding.imgTxt.setImageResource(R.drawable.cloud_img);
            }else if(weatherModel.getId().intValue()==500){
                //  binding.imgTxt.setImageResource(R.drawable.rain_fall);
            }
            // binding.imgTxt.setVisibility(View.VISIBLE);
            binding.placetxttem.setText(stateModel.getName());
            binding.tvdatetem.setText(CommonUtils.getOnlyDateFormat(stateModel.getDate()));
        }
    }

}