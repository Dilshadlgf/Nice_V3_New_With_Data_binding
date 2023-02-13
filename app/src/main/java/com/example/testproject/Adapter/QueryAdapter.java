package com.example.testproject.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.R;
import com.example.testproject.util.CommonUtils;
import com.example.testproject.databinding.FarmerquerylistBinding;
import com.example.testproject.model.QueryModel;

import java.util.ArrayList;
import java.util.List;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.MyVHolder> {
    private FarmerquerylistBinding binding;
    private List<QueryModel> data;
    private Context context;
    private String queryType;

    NavController navController ;

    public QueryAdapter(List<QueryModel> data, Context context, String queryType) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.addAll(data);
        this.context = context;
        switch (queryType){
            case "R":
                this.queryType = "resolved";
                break;
            case "C":
                this.queryType="unresolved";
                break;
            case "M":
                this.queryType ="unresolved";
            case "O":
                this.queryType="assigned";
                break;
        }
    }
    private String getQueryStatus(String s){
        switch (s) {
            case "R":
                return  "resolved";
            case "C":
            case "M":
                return "unresolved";
            case "O":
                return "assigned";

        }
        return "unresolved";
    }

    public void addNewList(List<QueryModel> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }
    public void clearMyList(){
        data.clear();
    }

    @NonNull
    @Override
    public QueryAdapter.MyVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FarmerquerylistBinding binding = FarmerquerylistBinding.inflate(inflater, parent, false);
        return new MyVHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QueryAdapter.MyVHolder holder, int position) {
        QueryModel queryResponseDataNumModel = data.get(position);
        //  holder.binding.setMydata(queryResponseDataNumModel);
        queryType=getQueryStatus(queryResponseDataNumModel.status);
        if (queryType.equals("assigned")){
            holder.binding.txtAssignTo.setText(context.getString(R.string.assignedby));
            holder.binding.txtResolvedBy.setText(CommonUtils.addNAifValueEmptyORNull(queryResponseDataNumModel.ref.assignedTo.firstName));
        }else if (queryType.equals("resolved")){
//            holder.binding.txtResolvedBy.setText(CommonUtils.addNAifValueEmptyORNull(queryResponseDataNumModel.getRef().getReviewedBy().getName()));

        }else if (queryType.equals("unresolved")){
            holder.binding.txtAssignTo.setText(context.getString(R.string.assignedby));
            holder.binding.txtResolvedBy.setText(CommonUtils.addNAifValueEmptyORNull(queryResponseDataNumModel.ref.assignedTo.firstName));
        }
        holder.binding.setMydata(queryResponseDataNumModel);
        holder.binding.card.setTag(position);
        holder.binding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (queryResponseDataNumModel.query!=null){
                    try {
                        Bundle bundle= new Bundle();
                        bundle.putString("id", queryResponseDataNumModel.id);
                        bundle.putString("status", queryResponseDataNumModel.status);
                        bundle.putString("Query", queryResponseDataNumModel.query);
                        bundle.putString("QueryDate", CommonUtils.getOnlyDateFormat(queryResponseDataNumModel.date));
                        bundle.putString("solution", queryResponseDataNumModel.solution);
                        bundle.putString("query_type", queryResponseDataNumModel.queryType);
                        bundle.putString("village",queryResponseDataNumModel.ref.state.name+">"+queryResponseDataNumModel.ref.village.name);
                        bundle.putString("Resolution", queryResponseDataNumModel.ref.assignedTo.name);
                        bundle.putString("AssignDate", CommonUtils.getOnlyDateFormat((String) queryResponseDataNumModel.assignedDate));
                        bundle.putString("AssignTO", queryResponseDataNumModel.ref.assignedTo.firstName);
                        bundle.putString("ResolvedDate",CommonUtils.getOnlyDateFormat(queryResponseDataNumModel.resolvedDate));
                        bundle.putString("ResolvedBy",CommonUtils.addNAifValueEmptyORNull(queryResponseDataNumModel.ref.resolvedBy.name));
                        if(queryResponseDataNumModel.createdType.equals("Farmer")) {
                            bundle.putString("CreatedBy",CommonUtils.addNAifValueEmptyORNull(queryResponseDataNumModel.ref.createdByFarmer.userName));
                        }else {
                            bundle.putString("CreatedBy",CommonUtils.addNAifValueEmptyORNull(queryResponseDataNumModel.ref.createdByFarmer.userName));
                        }

                        bundle.putString("QueryUId",queryResponseDataNumModel.uniqueId);
                        if(queryResponseDataNumModel.images!=null) {
                            bundle.putStringArrayList("images", new ArrayList<String>(queryResponseDataNumModel.images));
                        }else {
                            bundle.putStringArrayList("images", null);
                        }
                        bundle.putString("queryCat",queryType);
                        bundle.putInt("fragmentId",2);
                        Navigation.findNavController(view).navigate(R.id.action_queryTabFragment_to_queryDetailPrintFragment,bundle);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyVHolder extends RecyclerView.ViewHolder {

        FarmerquerylistBinding binding;

        public MyVHolder(@NonNull FarmerquerylistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
