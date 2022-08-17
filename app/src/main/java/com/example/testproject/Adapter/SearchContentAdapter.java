package com.example.testproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.Util.CommonUtils;
import com.example.testproject.databinding.SearchContentItemListRowBinding;
import com.example.testproject.interfaces.QueryListClickListner;
import com.example.testproject.model.ContentModel;
import com.example.testproject.R;
import com.example.testproject.ui.Activity.FarmerLoginActivity;
import com.example.testproject.ui.Activity.FarmerMainActivity;
import com.example.testproject.ui.Activity.MainSplashActivity;
import com.example.testproject.ui.Fragment.Farmer.ContentInfoFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchContentAdapter extends RecyclerView.Adapter<SearchContentAdapter.myviewholder> {

    private List<ContentModel> data;
    private Context context;
    NavController navController ;
    private QueryListClickListner queryListClickListner;

    private SearchContentItemListRowBinding binding;


    public SearchContentAdapter(List<ContentModel> data, Context context,QueryListClickListner queryListClickListner) {
        if(this.data==null){
            this.data=new ArrayList<>();
        }
        this.queryListClickListner=queryListClickListner;
        this.data.addAll(data);
        this.context = context;
    }
    public void addNewList(List<ContentModel> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }
    public void clearMyList(){
        data.clear();
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SearchContentItemListRowBinding binding = SearchContentItemListRowBinding.inflate(inflater,parent,false);
        return new myviewholder(binding);

    }


    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        ContentModel contentModel = data.get(position);
        holder.binding.setMydata(contentModel);
         holder.binding.card.setTag(position);
//        navController= NavHostFragment.findNavController();


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {

        SearchContentItemListRowBinding binding;

        public myviewholder(@NonNull SearchContentItemListRowBinding binding  ) {
            super(binding.getRoot());
            this.binding=binding;
            binding.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    Bundle bundle = new Bundle();
                    bundle.putString("model", CommonUtils.pojoToJson(data.get(position)));
                    queryListClickListner.onRowClick(position);
                    Navigation.findNavController(view).navigate(R.id.action_contentFragment_to_queryFragment,bundle);

                }
            });


        }

    }

}