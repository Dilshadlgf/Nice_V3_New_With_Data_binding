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
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.databinding.FarmerquerylistBinding;
import com.example.testproject.databinding.SearchContentItemListRowBinding;
import com.example.testproject.model.ContentModel;
import com.example.testproject.model.query.QueryResponseDataNumModel;

import java.util.ArrayList;
import java.util.List;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.MyVHolder> {
    private FarmerquerylistBinding binding;
    private List<QueryResponseDataNumModel> data;
    private Context context;

    NavController navController ;

    public QueryAdapter(List<QueryResponseDataNumModel> data, Context context) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.addAll(data);
        this.context = context;
    }

    public void addNewList(List<QueryResponseDataNumModel> data) {
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
        QueryResponseDataNumModel queryResponseDataNumModel = data.get(position);
        //  holder.binding.setMydata(queryResponseDataNumModel);
        holder.binding.setMydata(queryResponseDataNumModel);
        holder.binding.getRoot().setTag(position);

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
             binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();

                    Bundle bundle=new Bundle();
                    bundle.putString("model", CommonUtils.pojoToJson(data.get(position)));
                    Navigation.findNavController(view).navigate(R.id.action_queryTabFragment_to_queryDetailPrintFragment);


                }
            });


        }
    }
}
