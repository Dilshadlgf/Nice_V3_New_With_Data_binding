package com.example.testproject.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.databinding.WeatherLayoutBinding;
import com.example.testproject.model.WeatherStateModel;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.myviewholder> {
    private List<WeatherStateModel> listdata;
    private Context context;
    public WeatherAdapter(Context content, List<WeatherStateModel> listdata) {
        this.context =content;
        if(this.listdata==null){
            this.listdata=new ArrayList<>();
        }
//        this.listdata=listdata;
        this.listdata.addAll(listdata);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void addToList(List<WeatherStateModel> modelList) {
//        listdata.clear();
        listdata.addAll(modelList);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        WeatherLayoutBinding binding = WeatherLayoutBinding.inflate(inflater,parent,false);
        return new myviewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        WeatherStateModel weatherStateModel = listdata.get(position);
        holder.binding.setMydata(weatherStateModel);
        holder.binding.tvhumidity.setText(""+listdata.get(position).getWeatherData().getHumidity()+"%");
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }



    class myviewholder extends RecyclerView.ViewHolder {

        WeatherLayoutBinding binding;

        public myviewholder(@NonNull WeatherLayoutBinding binding  ) {
            super(binding.getRoot());
            this.binding=binding;


        }


    }

}

