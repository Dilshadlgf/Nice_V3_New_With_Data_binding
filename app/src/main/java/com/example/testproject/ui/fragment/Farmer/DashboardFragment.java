package com.example.testproject.ui.fragment.Farmer;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.util.AppConstants;
import com.example.testproject.util.CommonUtils;
import com.example.testproject.util.JsonMyUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.database.Dao.WeatherDetailsDao;
import com.example.testproject.databinding.ActivityDsaboardBinding;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.model.TemperatureModel;
import com.example.testproject.model.WeatherModel;
import com.example.testproject.ui.base.BaseFragment;

public class DashboardFragment extends BaseFragment {
   private ActivityDsaboardBinding binding;
    NavController navController ;
    private static boolean hitFirstWeatherApi;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private UserDao userDao;
    private WeatherDetailsDao weatherDetailsDao;
    protected void init() {
        layoutId=R.layout.activity_dsaboard;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

        binding= (ActivityDsaboardBinding) viewDataBinding;
        navController= NavHostFragment.findNavController(this);
        setupNetwork();
        userDao= AppDatabase.getInstance(getContext()).userdao();
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
            mApiManager.getweatherStateData(userDao.getUserResponse().state);
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
                    RootOneModel rootOneModel= (RootOneModel) response;
                    if (rootOneModel.getResponse().getData().getStateWeatherModels()!=null){
                        WeatherModel weatherModel= (WeatherModel) JsonMyUtils.getPojoFromJsonObj(WeatherModel.class,rootOneModel.getResponse().getData().getStateWeatherModels().getAsJsonObject());
                    setWeatherData(weatherModel);
                    if(weatherModel!=null) {
                        WeatherModel old = weatherDetailsDao.getWeatherDetailsResponseModel();
                        if (old != null) {
                            weatherDetailsDao.updateWeatherDetailsResponseModel(old);
                        } else {
                            weatherDetailsDao.insertWeatherDetailsResponseModel(weatherModel);
                        }
                    }
                    }

                }
            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }
    private void setWeatherData(WeatherModel stateModel){

        if(stateModel!=null) {
            TemperatureModel temp = stateModel.weatherData.temp;
            binding.maxtempTxt.setText(temp.day.intValue()+"°C" );
            binding.mintempTxt.setText("Max: " + temp.max.intValue()+"°C");
            binding.maxxtempTxt.setText("Min: " + temp.min.intValue()+"°C");
            TemperatureModel weatherModel=stateModel.weatherData.weather.get(0);
            if(weatherModel.id.intValue()==800){
                //  binding.imgTxt.setImageResource(R.drawable.ic_baseline_wb_sunny_24);
            }else if(weatherModel.id.intValue()==802||weatherModel.id.intValue()==803|| weatherModel.id.intValue()==804){
                //  binding.imgTxt.setImageResource(R.drawable.cloud_img);
            }else if(weatherModel.id.intValue()==500){
                //  binding.imgTxt.setImageResource(R.drawable.rain_fall);
            }
            // binding.imgTxt.setVisibility(View.VISIBLE);
            binding.placetxttem.setText(stateModel.name);
            binding.tvdatetem.setText(CommonUtils.getOnlyDateFormat(stateModel.date));
        }
    }

    @Override
    public void onBackCustom() {
        showDialog(getActivity(),"Do you want to exit?",true,true,AppConstants.DIALOG_LOGIN_BACK_ID);
    }

    @Override
    public void okDialogClick(int id) {
        if (id ==AppConstants.DIALOG_LOGIN_BACK_ID){
            getActivity().finish();
        }
    }
}