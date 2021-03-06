package com.example.testproject.ui.Fragment.Farmer;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.testproject.Adapter.SearchContentAdapter;
import com.example.testproject.model.ContentModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.databinding.ActivityContentBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;

public class ContentListFragment extends BaseFragment {

    private ActivityContentBinding binding;

   private String[] contenttype;
   private Spinner  spinner;
   private RecyclerView rcv;
   private RootOneModel rootOneModel;
    private SearchContentAdapter adapter;
    private ApiResponseInterface mInterFace;
    private Call<RootOneModel> call;
    private ApiManager mApiManager;

    int num = 10, count, total = 1, totalV = 1, totalU = 1, totalD = 1, totalP = 1, totalsuper = 1;
    int mPosition,maxLimit=1;
    private int lastindex;
    private boolean positionChanged;
    @Override

    protected void init() {

        layoutId=R.layout.activity_content;

    }


    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

        binding = (ActivityContentBinding) viewDataBinding;



        setUpNetWork();

        binding.searchContentRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.idNestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
//                    page++;
                 //   binding.lodingpb.setVisibility(View.VISIBLE);
                    loadmore();
                }
            }
        });

        contenttype=new String[]{"SMS Content","Voice Sms Content" ,"Video Url Content", "Documents Content","Poster Content"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spiner_text_view, contenttype);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spSearchContent.setAdapter(spinnerArrayAdapter);

        binding.spSearchContent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position!=mPosition){
                    positionChanged=true;
                }
                mPosition=position;
                loadmore();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

    }
    private void loadmore(){

    if(mPosition==0){
        if(total<=maxLimit){
            JsonObject mainObj = new JsonObject();
            JsonArray contentType = new JsonArray();
            contentType.add("S");
            JsonArray statusType = new JsonArray();
            statusType.add("A");
            statusType.add("E");
            mainObj.add("type", contentType);
            mainObj.add("status", statusType);
            mApiManager.searchContentList("" + total, mainObj,AppConstants.CONTENT_SMS_REQUEST);
            total++;
        }
    }else if(mPosition==1){
        if (totalV<=maxLimit) {

            JsonObject mainObj = new JsonObject();
            JsonArray contentType = new JsonArray();
            contentType.add("V");
            JsonArray statusType = new JsonArray();
            statusType.add("A");
            statusType.add("E");
            mainObj.add("type", contentType);
            mainObj.add("status", statusType);

            mApiManager.searchContentList("" + totalV, mainObj, AppConstants.CONTENT_SMS_REQUEST);
            totalV++;
        }

    }else if (mPosition==2){
        if (totalU<=maxLimit) {
            JsonObject mainObj = new JsonObject();

            JsonArray contentType = new JsonArray();
            contentType.add("U");
            JsonArray statusType = new JsonArray();
            statusType.add("A");
            statusType.add("E");
            mainObj.add("type", contentType);
            mainObj.add("status", statusType);

            mApiManager.searchContentList("" + totalU, mainObj, AppConstants.CONTENT_SMS_REQUEST);
            totalU++;
        }
    }else if (mPosition==3){
        if (totalD<=maxLimit) {
            JsonObject mainObj = new JsonObject();
            JsonArray contentType = new JsonArray();
            contentType.add("D");
            JsonArray statusType = new JsonArray();
            statusType.add("A");
            statusType.add("E");
            mainObj.add("type", contentType);
            mainObj.add("status", statusType);

            mApiManager.searchContentList("" + totalD, mainObj, AppConstants.CONTENT_SMS_REQUEST);
            totalD++;
        }
    }else if (mPosition==4){
        if (totalP<=maxLimit) {
            JsonObject mainObj = new JsonObject();
            JsonArray contentType = new JsonArray();
            contentType.add("P");
            JsonArray statusType = new JsonArray();
            statusType.add("A");
            statusType.add("E");
            mainObj.add("type", contentType);
            mainObj.add("status", statusType);

            mApiManager.searchContentList("" + totalP, mainObj, AppConstants.CONTENT_SMS_REQUEST);
           totalP++;
        }
    }
    }

        private void setUpNetWork() {
        mInterFace = new ApiResponseInterface() {


            @Override
            public void isError(String errorCode) {


            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {

                if(ServiceCode== AppConstants.CONTENT_SMS_REQUEST)
                    if(positionChanged){
                        positionChanged=false;
                        if(adapter!=null)
                        adapter.clearMyList();
                    }
                    rootOneModel=(RootOneModel) response;

                List<ContentModel> contentModels=rootOneModel.getResponse().getData().getContent();
                maxLimit=rootOneModel.getResponse().getData().getPagination().getTotalPage();
                if(adapter==null) {
                    adapter = new SearchContentAdapter(contentModels, getActivity());
                    binding.searchContentRecycler.setAdapter(adapter);
                }else{
                    adapter.addNewList(contentModels);
                }

            }
        };
        mApiManager = new ApiManager(getActivity(), mInterFace);


    }

}