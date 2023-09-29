package com.example.testproject.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.database.AppDatabase;
import com.example.testproject.databinding.CustmAddFeedbackforQueryBinding;
import com.example.testproject.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class FeedbackListAdapter extends RecyclerView.Adapter<FeedbackListAdapter.myviewholder> {
    private CustmAddFeedbackforQueryBinding binding;
    private List<UserModel> data;
    private Context context;
    private String farmerName="";


    public FeedbackListAdapter(List<UserModel> data, Context context) {
        if (this.data == null) {
            this.data = new ArrayList<>();
            farmerName= AppDatabase.getInstance(context).userdao().getUserResponse().name;
        }
        this.data.addAll(data);
        this.context = context;
    }

    public void addNewList(List<UserModel> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }


    public void clearMyList(){
        data.clear();
    }

    @NonNull
    @Override
    public FeedbackListAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustmAddFeedbackforQueryBinding binding = CustmAddFeedbackforQueryBinding.inflate(inflater, parent, false);
        return new myviewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackListAdapter.myviewholder holder, int position) {
//        DataModelTwo queryResponseDataNumModel = data.get(position);
        UserModel dataModelTwo=data.get(position);
        holder.binding.setMydata(dataModelTwo);
        holder.binding.txtName.setText(farmerName);
        holder.binding.txtRating.setRating(Float.parseFloat(dataModelTwo.rating));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class myviewholder extends RecyclerView.ViewHolder {

        CustmAddFeedbackforQueryBinding binding;

        public myviewholder(@NonNull CustmAddFeedbackforQueryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}