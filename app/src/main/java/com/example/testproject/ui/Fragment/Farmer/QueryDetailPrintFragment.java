package com.example.testproject.ui.Fragment.Farmer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.testproject.Network.ApiClient;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.Util.JsonMyUtils;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.databinding.QuerydetailprintFragmentBinding;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.model.UserModel;
import com.example.testproject.ui.Activity.farmer.FarmerMainActivity;
import com.example.testproject.ui.base.BaseFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QueryDetailPrintFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "QueryDetail";
    private UserDao userDao;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private QuerydetailprintFragmentBinding binding;
    String content, QueryDate,assignTOName, solution, query_type, village, Resolution,AssignDate, ResolvedDate, ResolvedBy,CreatedBy;

    private String queryCat;
    private boolean isFarmerLogin;
    private String queryStatus;
    private NavController navController;
    public static QueryDetailPrintFragment newInstance(Bundle args) {
        QueryDetailPrintFragment fragment = new QueryDetailPrintFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        layoutId = R.layout.querydetailprint_fragment;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (QuerydetailprintFragmentBinding) viewDataBinding;

        if (getArguments() != null) {

            boolean hitApi= getArguments().getBoolean("callFromOut",false);
            String queryUId=getArguments().getString("QueryUId");
            String id=getArguments().getString("id");
            String title="Q Id:"+ CommonUtils.addNAifValueEmptyORNull(queryUId);
            navController= NavHostFragment.findNavController(this);

            if (!hitApi) {
                fillData(false,null);
            }else {
                setupNetwork();
                mApiManager.getSingleQuery(id);

            }
            if(getActivity() instanceof FarmerMainActivity){
                if(!isHidden()){
                    ((FarmerMainActivity)getActivity()).setTittle(title);
                }else {
                    ((FarmerMainActivity)getActivity()).setTittle("Q Id:");
                }
            }
        }

    }
    private void shareQuery(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
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
//                CommonUtils.downloadFile(getContext(),false,null, BuildConfig.BASE_URL+images.get(0).toString(),"Query",shareBody);
                return;
            }
        }

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Query");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
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
        if(getActivity() instanceof FarmerMainActivity) {
            ((FarmerMainActivity) getActivity()).connect(BILL);
        }
    }
    private void fillData(boolean dataFromApi, UserModel model){
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
            if(getActivity() instanceof FarmerMainActivity) {
                ((FarmerMainActivity) getActivity()).setTittle("Q Id:" + model.uniqueId);
            }
            queryStatus= model.status;
            content = model.queryType;
            QueryDate = CommonUtils.getOnlyDateFormat(model.createdOn.on);
            solution = model.solution;
            query_type = model.queryType;
            village = model.ref.state.name+">"+model.ref.district.name+">"+model.ref.village.name;
            if(model.createdType.equals("Farmer")) {
                CreatedBy = CommonUtils.addNAifValueEmptyORNull(model.ref.createdByFarmer.userName);
            }else {
//                CreatedBy = CommonUtils.addNAifValueEmptyORNull(model.getRef().getCreatedByUser().getUserName());
            }
            Resolution = "";
            assignTOName=model.ref.assignedTo.firstName;;
            AssignDate = CommonUtils.getOnlyDateFormat(model.assignedDate);
            ResolvedDate = CommonUtils.getOnlyDateFormat(model.resolvedDate);
            ResolvedBy = CommonUtils.addNAifValueEmptyORNull(model.ref.resolvedBy.name);
            queryCat = "";
            createAndShowImages(model.images);
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
                binding.txtAssignTo.setText(getString(R.string.assignedto));

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

                Picasso.get().load(ApiClient.BASE_URL+list.get(i))
                        .into(imageView);
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
                if(ServiceCode== AppConstants.QUERIES_LIST_REQUEST){
                    RootOneModel rootOneModel= (RootOneModel) response;
                    if (rootOneModel.getResponse().getData().data!=null){
                        UserModel userModel = (UserModel) JsonMyUtils.getPojoFromJsonObj(UserModel.class,rootOneModel.getResponse().getData().data.getAsJsonObject());
                        if(userModel!=null) {
                            fillData(true, userModel);
                        }else {
                            Toast.makeText(getActivity(),"error found: try later",Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }
    @Override
    public void onClick(View v) {
    }
    @Override
    public void onBackCustom() {
        int srcfragmentId = 0;
        if (getArguments()!=null){
            srcfragmentId=getArguments().getInt("fragmentId",0);
        }
        if (srcfragmentId==1){
            navController.navigate(R.id.notificationListFragment);

        }else if (srcfragmentId==2){
            navController.navigate(R.id.queryTabFragment);
        }

    }
    private void makeShareText(StringBuilder sb,String key,String value){
        if(value!=null && !value.isEmpty()){
            sb.append(key).append(":-");
            sb.append(value).append("\n");
        }
    }
}


