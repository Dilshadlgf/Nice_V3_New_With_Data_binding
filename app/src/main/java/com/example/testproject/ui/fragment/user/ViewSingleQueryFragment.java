package com.example.testproject.ui.fragment.user;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.testproject.BuildConfig;
import com.example.testproject.Network.ApiClient;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.QuerydetailprintFragmentBinding;
import com.example.testproject.model.DataModelTwo;
import com.example.testproject.model.RootModel;
import com.example.testproject.ui.Activity.user.UserFragmentActivity;
import com.example.testproject.ui.base.BaseFragment;
import com.example.testproject.util.AppConstants;
import com.example.testproject.util.CommonUtils;
import com.example.testproject.util.JsonMyUtils;
import com.example.testproject.util.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ViewSingleQueryFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "QueryDetail";
    private UserDao userDao;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private QuerydetailprintFragmentBinding binding;
    String content, QueryDate,assignTOName, solution, query_type, village, Resolution,AssignDate, ResolvedDate, ResolvedBy,CreatedBy;

    private String queryCat;
    private boolean isFarmerLogin;
    private String queryStatus;
    private DataModelTwo dataModelTwo;

    public static ViewSingleQueryFragment newInstance(Bundle args) {
        ViewSingleQueryFragment fragment = new ViewSingleQueryFragment();
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
        layoutId = R.layout.querydetailprint_fragment;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (QuerydetailprintFragmentBinding) viewDataBinding;

//        ((FragmentActivity) getActivity()).setmBack("Back");
        userDao = AppDatabase.getInstance(getContext()).userdao();
        if(userDao!=null && userDao.getUserResponse()!=null){
            isFarmerLogin=userDao.getUserResponse().role.equalsIgnoreCase("farmer");
        }
//        if(getActivity() instanceof FragmentActivity) {
//            ((FragmentActivity) getActivity()).enableNavigationViews(false);
//            ((FragmentActivity) getActivity()).btnPrint.setBackgroundResource(R.drawable.printer);
//            ((FragmentActivity) getActivity()).btnPrint.setVisibility(View.VISIBLE);
//            ((FragmentActivity) getActivity()).img_help.setVisibility(View.GONE);
//            ((FragmentActivity) getActivity()).shareimg.setVisibility(View.VISIBLE);
//            ((FragmentActivity) getActivity()).mBack.setVisibility(View.VISIBLE);
//            /*  ((FragmentActivity) getActivity()).setmBack("Back");*/
//            ((FragmentActivity) getActivity()).mBack.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onBackCustom();
//                }
//            });
//
//            if(!isFarmerLogin){
//                ((FragmentActivity) getActivity()).lay_bottom_navigation.setVisibility(View.GONE);
//            }
//            ((FragmentActivity) getActivity()).shareimg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    shareQuery();
//                }
//            });
//            ((FragmentActivity) getActivity()).btnPrint.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    printQuery();
//                }
//            });
//        }

        if (getArguments() != null) {

            boolean hitApi= getArguments().getBoolean("callFromOut",false);
            String queryUId=getArguments().getString("QueryUId");
            String id=getArguments().getString("id");
            String title="Q Id:"+CommonUtils.addNAifValueEmptyORNull(queryUId);

            //((FragmentActivity) getActivity()).setScreenTitle("Anonymous Query");

            if (!hitApi) {
                if(getArguments().getString("model")!=null){
                    dataModelTwo= (DataModelTwo) CommonUtils.getPojoFromStr(DataModelTwo.class,getArguments().getString("model"));
                    fillData(true,dataModelTwo);
                }else {
                    fillData(false,null);
                }

            }else {
                setupNetwork();
                mApiManager.getSingleQuery(id);

            }
            if(getActivity() instanceof FragmentActivity){
//                if(!isHidden()){
//                    ((FragmentActivity)getActivity()).setScreenTitle(title);
//                }else {
//                    ((FragmentActivity)getActivity()).setScreenTitle("Q Id:");
//                }
            }else if(getActivity() instanceof UserFragmentActivity){
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT
//                );
//                params.setMargins((int)getResources().getDimension(R.dimen._10sdp), 0, (int)getResources().getDimension(R.dimen._10sdp), 0);
//                binding.bottomlayout.setLayoutParams(params);
                ViewUtils.setMargins(binding.bottomlayout,(int)getResources().getDimension(R.dimen._10sdp), 0, (int)getResources().getDimension(R.dimen._10sdp), 0);
            }
        }

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item=menu.findItem(R.id.mIShare);
        if(item!=null)
            item.setVisible(true);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mIShare: {
                shareQuery();
                return false;
            }
        }
        return false;
    }

    private void shareQuery(){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        String shareBody =
                "Query : " + content + "\n" + "QueryDate : " + QueryDate + "\n" + "solution : " + solution + "\n"
                        + "query_type : " + query_type + "\n" + "village : " + village + "\n" + "Assigned To : "
                        + Resolution + "\n" + "Assign Date : "
                        + AssignDate + "\n" + "ResolvedDate : " + ResolvedDate + "\n" + "ResolvedBy : "
                        + ResolvedBy;
        StringBuilder sb=new StringBuilder("Query : ");
        sb.append(content).append("\n");
        makeShareText(sb,"QueryDate",QueryDate);
        makeShareText(sb,"Solution",solution);
        makeShareText(sb,"QueryType",query_type);
        makeShareText(sb,"village",village);
        makeShareText(sb,"ResolvedDate",ResolvedDate);
        makeShareText(sb,"ResolvedBy",ResolvedBy);
        shareBody=sb.toString();
        if(getArguments()!=null){
            List images=getArguments().getStringArrayList("images");
            if(images!=null && images.size()>0){
                CommonUtils.downloadFile(getContext(),false,null, BuildConfig.BASE_URL+images.get(0).toString(),"Query",shareBody);
                return;
            }else if(dataModelTwo!=null && dataModelTwo.getImages()!=null && dataModelTwo.getImages().size()>0){
                CommonUtils.downloadFile(getContext(),false,null, BuildConfig.BASE_URL+dataModelTwo.getImages().get(0).toString(),"Query",shareBody);
                return;
            }
        }

        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Query");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
    private void printQuery(){
        String BILL = "";
        BILL = "         Anonymous Query    \n";
        BILL = BILL + "\n";

        BILL = BILL + " QueryDate" + "      :" + "    "+ QueryDate + "\n";
        BILL = BILL + "\n";

        BILL = BILL + " Village" + "        :" + "    "+ village + "\n";
        BILL = BILL + "\n";

        BILL = BILL + " Query" + "          :" + "    "+ content + "\n";
        BILL = BILL + "\n";

        BILL = BILL + " Query Type" + "     :" + "    "+ query_type + "\n";
        BILL = BILL + "\n";

        BILL = BILL + " Resolution" + "     :" + "    "+ Resolution + "\n";
        BILL = BILL + "\n";

        BILL = BILL + " Resolved Date" + "  :" + "    "+ResolvedDate + "\n";
        BILL = BILL + "\n";

        BILL = BILL + " Resolved By" + "    :" + "    "+ ResolvedBy + "\n";
        BILL = BILL + "\n";

        BILL = BILL + " Solution" + "       :" + "    "+ solution + "\n";
        BILL = BILL + "\n";
        if(getActivity() instanceof FragmentActivity) {
//            ((FragmentActivity) getActivity()).connect(BILL);
        }
    }
    private void fillData(boolean dataFromApi,DataModelTwo model){
        if(!dataFromApi) {
            queryStatus=getArguments().getString("status");
            content = getArguments().getString("Query");
            QueryDate = getArguments().getString("QueryDate");
            solution = getArguments().getString("solution");
            query_type = getArguments().getString("query_type");
            village = getArguments().getString("village");
            Resolution = getArguments().getString("Resolution");
            AssignDate = getArguments().getString("AssignDate");
            assignTOName=getArguments().getString("AssignTO");
            ResolvedDate = getArguments().getString("ResolvedDate");
            ResolvedBy = CommonUtils.addNAifValueEmptyORNull(getArguments().getString("ResolvedBy"));
            queryCat = getArguments().getString("queryCat");
            CreatedBy = getArguments().getString("CreatedBy");
            List images = getArguments().getStringArrayList("images");
            createAndShowImages(images);
        }else {

            queryStatus= model.getStatus();
            content = model.getQuery();
            QueryDate = CommonUtils.getOnlyDateFormat(model.getCreatedOn().getOn());
            solution = model.getSolution();
            query_type = model.getQueryType();
            village = model.getRef().state.name+">"+model.getRef().district.name+">"+model.getRef().village.name;
            if(model.getCreatedType().equals("Farmer")) {
                CreatedBy = CommonUtils.addNAifValueEmptyORNull(model.getRef().createdByFarmer.name);
            }else {
                CreatedBy = CommonUtils.addNAifValueEmptyORNull(model.getRef().createdByUser.name);
            }
            Resolution = "";
            assignTOName=model.getRef().assignedTo.firstName;
            AssignDate = CommonUtils.getOnlyDateFormat(model.getAssignedDate());
            ResolvedDate = CommonUtils.getOnlyDateFormat(model.getResolvedDate());
            ResolvedBy = CommonUtils.addNAifValueEmptyORNull(model.getRef().resolvedBy.name);
            queryCat = "";
            createAndShowImages(model.getImages());
        }

        binding.txtQuery.setText(content);
        binding.txtDate.setText(QueryDate);
        binding.txtSolution.setText(solution);
        binding.txtCreatedBy.setText(CreatedBy);
        binding.txtAddress.setText(village);
//        binding.txtResolution.setText(Resolution);
        binding.txtResolvedDate.setText(ResolvedDate);
        binding.txtResolvedBy.setText(ResolvedBy);

        switch (queryStatus){
            case "C"://created/unresolved
                binding.laySolution.setVisibility(View.GONE);
                binding.layResolvedBy.setVisibility(View.GONE);
                break;
            case "M":
                binding.laySolution.setVisibility(View.GONE);
                binding.layResolvedBy.setVisibility(View.GONE);
                break;
            case "O"://assigned
                ResolvedDate = AssignDate;
                ResolvedBy = assignTOName;
                binding.txtResolvedDate.setText(ResolvedDate);
                binding.txtResolvedBy.setText(ResolvedBy);
                binding.laySolution.setVisibility(View.GONE);
                binding.layResolvedBy.setVisibility(View.VISIBLE);
                binding.txtAssignTo.setText("Assign To");

                break;
            case "R"://resolved
                binding.laySolution.setVisibility(View.VISIBLE);
                binding.laySolution.setVisibility(View.VISIBLE);
                break;
        }


    }
    private void createAndShowImages(List<String> list){
        if(list!=null && list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                ImageView imageView=new ImageView(getActivity());
                imageView.setAdjustViewBounds(true);
//                imageView.setMaxHeight((int) getResources().getDimension(R.dimen._150sdp));
//                imageView.setMaxWidth((int) getResources().getDimension(R.dimen._150sdp));
                Picasso.get().load(ApiClient.BASE_URL+list.get(i))
                        .into(imageView);
//                imageView.setBackgroundResource(R.drawable.splash);
                assert binding.imageContainer != null;
                binding.imageContainer.addView(imageView);
            }

        }
    }
    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
                showDialog(getActivity(), errorCode, false, true, 0);

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if(ServiceCode==AppConstants.QUERIES_LIST_REQUEST){
                    RootModel rootOneResModel= (RootModel) response;
                    DataModelTwo model= (DataModelTwo) JsonMyUtils.getPojoFromJsonObj(DataModelTwo.class,rootOneResModel.getResponse().getData().getAsJsonObject());
                    if(model!=null) {
                        fillData(true, model);
                    }else {
                        Toast.makeText(getActivity(),"error found: try later",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }
    @Override
    public void onClick(View v) {
    }
//    @Override
//    public void onBackCustom() {
//        FragmentManager manager=getFragmentManager();
//        if (getActivity() instanceof FragmentActivity) {
//            if (manager != null) {
//                if (manager.getBackStackEntryCount() == 0) {
//                    FragmentTransaction trans = manager.beginTransaction();
//                    trans.remove(this);
//                    trans.commit();
//
//                    if (isFarmerLogin) {
//                        CustomFragmentManager.addFragment(manager, new DashboardFragment());
//                    } else {
//                        getActivity().startActivity(new Intent(getActivity(), FarmerLogin.class));
//                        getActivity().finish();
//                    }
//                } else {
//                    manager.popBackStack();
//                }
//            }
//        }else {
//            if(manager!=null && loginDao.getLoginResponse()!=null && !loginDao.getLoginResponse().getRole().isEmpty()){
//                if(getArguments() != null && getArguments().containsKey("fromView")){
//                    manager.popBackStack();
//                }else {
//                    CustomFragmentManager.replaceFragment(manager, new UserDashboardFragment());
//                }
//            }else {
//                getActivity().startActivity(new Intent(getActivity(), UserLoginActivity.class));
//                getActivity().finish();
//            }
//        }
//    }
    private void makeShareText(StringBuilder sb,String key,String value){
        if(value!=null && !value.isEmpty()){
            sb.append(key).append(":-");
            sb.append(value).append("\n");
        }
    }
}


