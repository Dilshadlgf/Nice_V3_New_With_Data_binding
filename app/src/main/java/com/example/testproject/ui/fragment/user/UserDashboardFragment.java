package com.example.testproject.ui.fragment.user;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.Adapter.user.UserDashboardAdaptor;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.util.AppConstants;
import com.example.testproject.util.CommonUtils;
import com.example.testproject.util.JsonMyUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.database.Dao.WeatherDetailsDao;
import com.example.testproject.databinding.UserDashboardFragmentBinding;
import com.example.testproject.interfaces.ListItemClickListener;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.model.TemperatureModel;
import com.example.testproject.model.WeatherModel;
import com.example.testproject.ui.base.BaseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager;

/**
 * Created by suraj  on 08-02-2023.
 */
public class UserDashboardFragment extends BaseFragment {
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;

    private static final String TAG = "Dashboard";

    private UserDashboardFragmentBinding binding;

    BottomNavigationView btmview;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private UserDao userDao;
    WeatherDetailsDao weatherDetailsDao;
    private static boolean hitFirstWeatherApi;
    private static int[] holdData=new int[8];
    private NavController navController;
    private boolean isRVScrolling;
    UserDashboardAdaptor userDashboardAdaptor;

    public static UserDashboardFragment newInstance(Bundle args) {
        UserDashboardFragment fragment = new UserDashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void init() {
        layoutId = R.layout.user_dashboard_fragment;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (UserDashboardFragmentBinding) viewDataBinding;
//        ((UserFragmentActivity) getActivity()).setTittle(getString(R.string.dashboard));
//        ((UserFragmentActivity) getActivity()).showHideEditIcon(false);
        setupNetwork();

        navController= NavHostFragment.findNavController(this);
        userDao = AppDatabase.getInstance(getContext()).userdao();
        weatherDetailsDao = AppDatabase.getInstance(getContext()).weatherDetailsResponseModel();
        setWeatherData(weatherDetailsDao.getWeatherDetailsResponseModel());
        fillProfileData();

        userDashboardAdaptor= new UserDashboardAdaptor(getContext(),new ListItemClickListener(){

            @Override
            public void onItemClick(int position, String id) {
                if(position==0){
                    navController.navigate(R.id.userContentTabFragment);
                }else  if(position==1){
                    navController.navigate(R.id.userQueryTabFragment);
//                    CustomFragmentManager.replaceFragment(getFragmentManager(),new UserQueryTabFragment(),true);
                }else  if(position==2){
                    navController.navigate(R.id.userProfileFragment);
//                    CustomFragmentManager.replaceFragment(getFragmentManager(),new UserProfileFragment(),true);
                }
            }
        });

        binding.carouselRecyclerview.setAdapter(userDashboardAdaptor);
        binding.carouselRecyclerview.set3DItem(true);
        binding.carouselRecyclerview.setItemSelectListener(new CarouselLayoutManager.OnSelected() {
            @Override
            public void onItemSelected(int i) {
                if(!isRVScrolling && !binding.carouselRecyclerview.isComputingLayout())
                    binding.carouselRecyclerview.post(new Runnable() {
                        @Override
                        public void run() {
                            userDashboardAdaptor.setCustomItem(i);
                        }
                    });

            }
        });
        binding.carouselRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==RecyclerView.SCROLL_STATE_IDLE){
                    isRVScrolling=false;
                }else {
                    isRVScrolling=true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        if(!hitFirstWeatherApi){
            hitFirstWeatherApi=true;
            mApiManager.getweatherStateData(userDao.getUserResponse().stateId);
            apiCall("S","sms",AppConstants.SEARCHCONTENtMixContent);
            apiCall("V","voice",AppConstants.SEARCHCONTENTVOICECONTENT);
            apiCall("U","video",AppConstants.VIDEO_CONTENT);
            apiCall("P","poster",AppConstants.SEARCHCONTENTPOSTERCONTENT);
            apiCall("D","docment",AppConstants.SEARCHCONTENTDOCUMENTCONTENT);
            apiQueryCountCall();
        }else {
            setSavedData();
        }
    }

    private void apiCall(String contentType,String path,int reqCode){
        JsonObject jsonObject=new JsonObject();
        JsonArray typeArr=new JsonArray();
        typeArr.add(contentType);
        jsonObject.add("type",typeArr);
        JsonObject accessObj=new JsonObject();
        accessObj.addProperty("isAccess",true);
        accessObj.addProperty("userName","admin");

        jsonObject.add("dataAccess",accessObj);
        mApiManager.contentCount(path,jsonObject,reqCode);
    }
    private void apiQueryCountCall(){
        JsonObject jsonObject=new JsonObject();
        JsonObject accessObj=new JsonObject();
        accessObj.addProperty("isAccess",true);
        accessObj.addProperty("userName","admin");
        jsonObject.add("dataAccess",accessObj);
        mApiManager.queryCount(jsonObject,AppConstants.QUERIES_LIST_REQUEST);
    }
    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
//                showDialog(getActivity(), errorCode, false, true, 0);
                Toast.makeText(getActivity(),errorCode,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if(ServiceCode==AppConstants.WeatherAlert_LIST_REQUEST){
                    RootOneModel rootOneModel= (RootOneModel) response;
                    if (rootOneModel.getResponse().getData().getStateWeatherModels()!=null){
                        WeatherModel weatherModel = (WeatherModel) JsonMyUtils.getPojoFromJsonObj(WeatherModel.class,rootOneModel.getResponse().getData().getStateWeatherModels().getAsJsonObject());
                        setWeatherData(weatherModel);
                        if(weatherModel!=null){
                            WeatherModel old= weatherDetailsDao.getWeatherDetailsResponseModel();
                            if(old!=null){
                                weatherModel.dbid=old.dbid;
                                weatherDetailsDao.updateWeatherDetailsResponseModel(weatherModel);
                            }else{
                                weatherDetailsDao.insertWeatherDetailsResponseModel(weatherModel);
                            }
                        }
                    }


                }else if(ServiceCode==AppConstants.SEARCHCONTENtMixContent){
                    JsonObject jsonObject= (JsonObject) response;
                    binding.txtIcon1V.setText(parseJSON(jsonObject));
                    holdData[0]=Integer.parseInt(parseJSON(jsonObject));
                }else if(ServiceCode==AppConstants.SEARCHCONTENTVOICECONTENT){
                    JsonObject jsonObject= (JsonObject) response;
                    binding.txtIcon2V.setText(parseJSON(jsonObject));
                    holdData[1]=Integer.parseInt(parseJSON(jsonObject));
                }else if(ServiceCode==AppConstants.VIDEO_CONTENT){
                    JsonObject jsonObject= (JsonObject) response;
                    binding.txtIcon3V.setText(parseJSON(jsonObject));
                    holdData[2]=Integer.parseInt(parseJSON(jsonObject));
                }else if(ServiceCode==AppConstants.SEARCHCONTENTPOSTERCONTENT){
                    JsonObject jsonObject= (JsonObject) response;
                    binding.txtIcon4V.setText(parseJSON(jsonObject));
                    holdData[3]=Integer.parseInt(parseJSON(jsonObject));
                }else if(ServiceCode==AppConstants.SEARCHCONTENTDOCUMENTCONTENT){
                    JsonObject jsonObject= (JsonObject) response;
                    binding.txtIcon5V.setText(parseJSON(jsonObject));
                    holdData[4]=Integer.parseInt(parseJSON(jsonObject));
                }else if(ServiceCode==AppConstants.QUERIES_LIST_REQUEST){
                    JsonObject jsonObject= (JsonObject) response;
                    parseQuery(jsonObject);
                }
            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }

    private void setSavedData(){
        binding.txtIcon1V.setText(String.valueOf(holdData[0]));
        binding.txtIcon2V.setText(String.valueOf(holdData[1]));
        binding.txtIcon3V.setText(String.valueOf(holdData[2]));
        binding.txtIcon4V.setText(String.valueOf(holdData[3]));
        binding.txtIcon5V.setText(String.valueOf(holdData[4]));

        binding.txtUnresolvedQValue.setText(String.valueOf(holdData[5]));
        binding.txtAssignedQV.setText(String.valueOf(holdData[6]));
        binding.txtResolvedQV.setText(String.valueOf(holdData[7]));
    }

    private void fillProfileData(){
        binding.txtName.setText(userDao.getUserResponse().name);
        binding.txtName.setText(userDao.getUserResponse().name);
    }
    private String parseJSON(JsonObject jsonObject){

        if(jsonObject!=null){
            try {
                jsonObject=jsonObject.getAsJsonObject("response").getAsJsonObject("data");
                if(jsonObject.has("content")){
                    JsonArray jsonArray=jsonObject.getAsJsonArray("content");
                    if(jsonArray!=null && jsonArray.size()>0){
                        int unreview=  jsonArray.get(0).getAsJsonObject().get("unReviewed").getAsInt();
                        int review=  jsonArray.get(0).getAsJsonObject().get("reviewed").getAsInt();
                        int rejected=  jsonArray.get(0).getAsJsonObject().get("rejected").getAsInt();
                        return ""+(unreview+rejected+review);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "0";
    }
    private String parseQuery(JsonObject jsonObject){

        if(jsonObject!=null){
            try {
                jsonObject=jsonObject.getAsJsonObject("response").getAsJsonObject("data");
                if(jsonObject.has("query")){
                    JsonArray jsonArray=jsonObject.getAsJsonArray("query");
                    if(jsonArray!=null && jsonArray.size()>0){
                        int unreview=  jsonArray.get(0).getAsJsonObject().get("unresolvedQueries").getAsInt();
                        int review=  jsonArray.get(0).getAsJsonObject().get("assinged").getAsInt();
                        int rejected=  jsonArray.get(0).getAsJsonObject().get("resolvedQueries").getAsInt();
                        binding.txtUnresolvedQValue.setText(""+unreview);
                        binding.txtAssignedQV.setText(""+review);
                        binding.txtResolvedQV.setText(""+rejected);
                        holdData[5]=unreview;
                        holdData[6]=review;
                        holdData[7]=rejected;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "0";
    }
    private void setWeatherData(WeatherModel stateModel){

        if(stateModel!=null) {
            TemperatureModel temp = stateModel.weatherData.temp;
            binding.txtDegreeValue.setText(temp.day.intValue()+"°C" );

            TemperatureModel weatherModel=stateModel.weatherData.weather.get(0);
            if(weatherModel.id ==800){
                //  binding.imgTxt.setImageResource(R.drawable.ic_baseline_wb_sunny_24);
            }else if(weatherModel.id ==802|| weatherModel.id ==803|| weatherModel.id ==804){
                //  binding.imgTxt.setImageResource(R.drawable.cloud_img);
            }else if(weatherModel.id ==500){
                //  binding.imgTxt.setImageResource(R.drawable.rain_fall);
            }
            // binding.imgTxt.setVisibility(View.VISIBLE);
            binding.txtCity.setText(stateModel.name);
            binding.txtDate.setText(CommonUtils.getOnlyDateFormat(stateModel.date));
        }
    }

}