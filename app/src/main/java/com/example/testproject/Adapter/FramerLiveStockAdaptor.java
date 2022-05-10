package com.example.testproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.databinding.FarmerquerylistBinding;
import com.example.testproject.databinding.LivestockItemBinding;
import com.example.testproject.model.livestock.LiveStockDataModel;
import com.example.testproject.model.query.QueryResponseDataNumModel;

import java.util.ArrayList;
import java.util.List;

public class FramerLiveStockAdaptor extends RecyclerView.Adapter<FramerLiveStockAdaptor.MyVHolder> {
    private FarmerquerylistBinding binding;
    private List<LiveStockDataModel> data;
    private Context context;


    public FramerLiveStockAdaptor(List<LiveStockDataModel> data, Context context) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.addAll(data);
        this.context = context;
    }

    public void addNewList(List<LiveStockDataModel> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }
    public void clearMyList(){
        data.clear();
    }

    @NonNull
    @Override
    public FramerLiveStockAdaptor.MyVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LivestockItemBinding binding = LivestockItemBinding.inflate(inflater, parent, false);
        return new MyVHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull FramerLiveStockAdaptor.MyVHolder holder, int position) {
        LiveStockDataModel queryResponseDataNumModel = data.get(position);
        //  holder.binding.setMydata(queryResponseDataNumModel);
        holder.binding.setMydata(queryResponseDataNumModel);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyVHolder extends RecyclerView.ViewHolder {

        LivestockItemBinding binding;

        public MyVHolder(@NonNull LivestockItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}
