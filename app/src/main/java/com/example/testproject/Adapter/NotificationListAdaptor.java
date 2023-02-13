package com.example.testproject.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.util.CommonUtils;
import com.example.testproject.databinding.NotiListItemBinding;
import com.example.testproject.model.NotificationDataModel;

import java.util.List;

public class NotificationListAdaptor extends RecyclerView.Adapter<NotificationListAdaptor.ViewHolder> {

    private List<NotificationDataModel> listdata;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private Context content;
    private NavController navController;
    public NotificationListAdaptor(Context content, List<NotificationDataModel> listdata, NavController navController){
        this.content=content;
        this.navController=navController;
//        if(this.listdata==null){
//            this.listdata=new ArrayList<>();
//        }
        this.listdata=listdata;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void addToList(List<NotificationDataModel> modelList){
//        listdata.clear();
        listdata.addAll(modelList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        NotiListItemBinding binding= NotiListItemBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            NotificationDataModel model= listdata.get(position);
            holder.binding.setMydata(model);
            holder.binding.txtDate.setText(CommonUtils.getOnlyDateFormat(model.getSentDate()));
            if(model.getData().getQuery_Type().equals("ViewSingleQuery")){
                holder.binding.tvCreatedBy.setText(content.getString(R.string.query));
            }else if (model.getData().getQuery_Type().equals("ViewSingleContent")){
                holder.binding.tvCreatedBy.setText(content.getString(R.string.content));
            }else{
                holder.binding.tvCreatedBy.setText(content.getString(R.string.weather));

            }

            holder.binding.textquery.setVisibility(View.INVISIBLE);
            holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle mbundle=new Bundle();
                    FragmentManager manager = ((AppCompatActivity )content).getSupportFragmentManager();

                        if(model.getData().getQuery_Type().equals("ViewSingleQuery")){
                            mbundle.putString("id",model.getData().getId());
                            mbundle.putString("query","contentQuery");
                            mbundle.putString("queryModule","farmer");
                            mbundle.putBoolean("callFromOut",true);
                            mbundle.putInt("fragmentId",1);
//                             CustomFragmentManager.replaceFragment(manager, QueryDetailPrintFragment.newInstance(mbundle), true);
                            navController.navigate(R.id.action_notificationListFragment_to_queryDetailPrintFragment,mbundle);
                        }else if (model.getData().getQuery_Type().equals("ViewSingleContent")){
                            mbundle.putString("contentId", model.getData().getId());
                            navController.navigate(R.id.action_notificationListFragment_to_contentDetailFragment,mbundle);

                            // CustomFragmentManager.replaceFragment(manager, ContentDetailFragment.newInstance(mbundle), true);
                        }
                    }
            });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final NotiListItemBinding binding;
        public ViewHolder(NotiListItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

}
