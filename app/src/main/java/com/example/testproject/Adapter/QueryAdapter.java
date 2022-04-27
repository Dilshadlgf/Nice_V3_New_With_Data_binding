package com.example.testproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.databinding.FarmerquerylistBinding;
import com.example.testproject.databinding.SearchContentItemListRowBinding;
import com.example.testproject.model.ContentModel;
import com.example.testproject.model.query.QueryResponseDataNumModel;

import java.util.ArrayList;
import java.util.List;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.myviewholder> {
    private FarmerquerylistBinding binding;
    private List<QueryResponseDataNumModel> data;
    private Context context;


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
    public QueryAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FarmerquerylistBinding binding = FarmerquerylistBinding.inflate(inflater, parent, false);
        return new myviewholder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull QueryAdapter.myviewholder holder, int position) {
        QueryResponseDataNumModel queryResponseDataNumModel = data.get(position);
        //  holder.binding.setMydata(queryResponseDataNumModel);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class myviewholder extends RecyclerView.ViewHolder {

        FarmerquerylistBinding binding;

        public myviewholder(@NonNull FarmerquerylistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}
