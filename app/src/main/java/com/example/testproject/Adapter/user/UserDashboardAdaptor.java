package com.example.testproject.Adapter.user;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.R;
import com.example.testproject.databinding.UserDashboardCardItemBinding;
import com.example.testproject.interfaces.ListItemClickListener;

public class UserDashboardAdaptor extends RecyclerView.Adapter<UserDashboardAdaptor.MViewholder> {

    ListItemClickListener itemClickListener;
    private Context context;
    private int currentIndex;
    public UserDashboardAdaptor(Context context, ListItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
        this.context=context;
    }
    @NonNull
    @Override
    public MViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserDashboardCardItemBinding binding=UserDashboardCardItemBinding.inflate(LayoutInflater.from(parent.getContext()));

        return new MViewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MViewholder holder, int position) {
        if(currentIndex==position){
            holder.binding.layParent.setBackgroundResource(R.drawable.custom_card_select);
        }else {
            holder.binding.layParent.setBackgroundResource(R.drawable.ract_green_dark);
        }
            if(position==0){
                holder.binding.text1.setText(context.getString(R.string.all));
                holder.binding.text2.setText(context.getString(R.string.content));
            }else if(position==1){
                holder.binding.text1.setText(context.getString(R.string.all));
                holder.binding.text2.setText(context.getString(R.string.queries));
            }else if(position==2){
                holder.binding.text1.setText(context.getString(R.string.user));
                holder.binding.text2.setText(context.getString(R.string.profile));
            }
        holder.binding.layParent.setTag(position);
        holder.binding.layParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick((int) v.getTag(),"");
            }
        });
    }
    public void setCustomItem(int position){
        currentIndex=position;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    static class MViewholder extends RecyclerView.ViewHolder {
        UserDashboardCardItemBinding binding;
        public MViewholder(@NonNull UserDashboardCardItemBinding itemView) {
            super(itemView.getRoot());
            this.binding=itemView;
        }
    }
}
