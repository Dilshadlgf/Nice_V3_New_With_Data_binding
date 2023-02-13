package com.example.testproject.ui.Fragment.User;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.UserConentTabFragmentBinding;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.model.UserModel;
import com.example.testproject.ui.Activity.user.UserFragmentActivity;
import com.example.testproject.ui.base.BaseFragment;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suraj on 09-02-2023.
 */
public class UserContentTabFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "Dashboard";
    private UserConentTabFragmentBinding binding;
    private NavController navController;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private UserDao userDao;
    public final static int approvedScreen=1,translatedScreen=2,createdScreen=3,publishedScreen=4,rejectedScreen=5;
    private  List<View> viewList =new ArrayList<>();

    private static int currentTab,currentBtn;
    public static UserContentTabFragment newInstance(Bundle args) {
        UserContentTabFragment fragment = new UserContentTabFragment();
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
        layoutId = R.layout.user_conent_tab_fragment;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (UserConentTabFragmentBinding) viewDataBinding;
        navController= NavHostFragment.findNavController(this);
        ((UserFragmentActivity) getActivity()).setTittle(getString(R.string.content));
        ((UserFragmentActivity) getActivity()).showHideEditIcon(false);
        setupNetwork();
        userDao = AppDatabase.getInstance(getContext()).userdao();
        setupViewPager(binding.viewpager,"S"," "+getString(R.string.sms));//type sms
        binding.tab.setupWithViewPager(binding.viewpager);

        binding.btnSms.setOnClickListener(this);
        binding.btnVoice.setOnClickListener(this);
        binding.btnDoc.setOnClickListener(this);
        binding.btnPoster.setOnClickListener(this);
        binding.btnVideo.setOnClickListener(this);

        viewList.add(binding.btnSms);
        viewList.add(binding.btnVoice);
        viewList.add(binding.btnVideo);
        viewList.add(binding.btnPoster);
        viewList.add(binding.btnDoc);

        binding.btnSms.setBackgroundResource(R.drawable.ract_greendark3);

        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentTab=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.viewpager.setCurrentItem(currentTab);
        saveLastScreen();
    }
    private void saveLastScreen(){
        if(currentBtn>0){
            switch (currentBtn){
                case 1:
                    binding.btnVoice.performClick();
                    break;
                case 2:
                    binding.btnVideo.performClick();
                    break;
                case 3:
                    binding.btnPoster.performClick();
                    break;
                case 4:
                    binding.btnDoc.performClick();
                    break;
            }
        }
    }
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item=menu.findItem(R.id.mIShare);
        if(item!=null)
            item.setVisible(false);

    }
    @Override
    public void onClick(View v) {
        String ty= (String) v.getTag();
        setupViewPager(binding.viewpager,ty," "+getNameByTag(ty));
        buttonBgChange(v);
    }
    private void buttonBgChange(View view){
        for (int i = 0; i <viewList.size() ; i++) {
            if(view.getId()==viewList.get(i).getId()){
                currentBtn=i;
                view.setBackgroundResource(R.drawable.ract_greendark3);
            }else {
                viewList.get(i).setBackgroundResource(R.drawable.ract_green_dark);
            }
        }
    }
    private String getNameByTag(String s){
        switch (s){
            case "S":
                return getString(R.string.sms);
            case "U":
                return getString(R.string.video);
            case "V":
                return getString(R.string.voice);
            case "D":
                return getString(R.string.documents);
            case "P":
                return getString(R.string.poster);
        }
        return "";
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
                if(ServiceCode== AppConstants.WeatherAlert_LIST_REQUEST) {
                    RootOneModel rootModel = (RootOneModel) response;
                    JsonObject jsonObject=rootModel.getResponse().getData().data.getAsJsonObject();
                    if(jsonObject!=null && jsonObject.has("user")) {
                        String sm = jsonObject.getAsJsonObject("user").toString();
                        UserModel model = (UserModel) CommonUtils.getPojoFromStr(UserModel.class, sm);
                    }
                }
            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }
    private void setupViewPager(ViewPager viewPager,String type,String name) {
        MyFAdaptor viewPagerAdapter = new MyFAdaptor(getChildFragmentManager());

        Bundle bundleResolved = new Bundle();
        bundleResolved.putString("type", type);
        bundleResolved.putString("status", "U");
        bundleResolved.putInt("screenType", createdScreen);
        viewPagerAdapter.addFragment(UserContentFragment.newInstance(bundleResolved), getString(R.string.created)+name);

        Bundle bundleResolved2 = new Bundle();
        bundleResolved2.putString("type", type);
        bundleResolved2.putString("status", "A,E");
        bundleResolved2.putInt("screenType", approvedScreen);
        viewPagerAdapter.addFragment(UserContentFragment.newInstance(bundleResolved2), getString(R.string.approved)+name);

        if( type.equals("S") || type.equals("V") || type.equals("P")) {
            Bundle bundleResolved3 = new Bundle();
            bundleResolved3.putString("type", type);
            bundleResolved3.putString("status", "A,E");
            bundleResolved3.putString("translationStatus", "U");
            bundleResolved3.putInt("screenType", translatedScreen);
            viewPagerAdapter.addFragment(UserContentFragment.newInstance(bundleResolved3), getString(R.string.transplated) + name);

            Bundle bundleResolved4 = new Bundle();
            bundleResolved4.putString("type", type);
            bundleResolved4.putString("status", "A,E");
            bundleResolved4.putString("translationStatus", "A,E");
            bundleResolved4.putInt("screenType", publishedScreen);
            viewPagerAdapter.addFragment(UserContentFragment.newInstance(bundleResolved4), getString(R.string.published) + name);
        }

        Bundle bundleResolved5 = new Bundle();
        bundleResolved5.putString("type", type);
        bundleResolved5.putString("status", "R");
        bundleResolved5.putInt("screenType", rejectedScreen);
        viewPagerAdapter.addFragment(UserContentFragment.newInstance(bundleResolved5), getString(R.string.rejected)+name);

        viewPager.setAdapter(viewPagerAdapter);
    }

    class MyFAdaptor extends FragmentStatePagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> fragmentTitles = new ArrayList<>();

        public MyFAdaptor(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }

        public void addFragment(Fragment fragment, String name) {
            fragmentList.add(fragment);
            fragmentTitles.add(name);
        }
    }

    @Override
    public void onBackCustom() {
        ((UserFragmentActivity) getActivity()).setTittle(getString(R.string.dashboard));
        ((UserFragmentActivity) getActivity()).hideIcon();
        navController.navigate(R.id.userDashboardFragment);
    }
}