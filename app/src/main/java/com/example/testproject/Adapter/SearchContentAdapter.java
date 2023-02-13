package com.example.testproject.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.Util.CommonUtils;
import com.example.testproject.databinding.SearchContentItemListRowBinding;
import com.example.testproject.interfaces.QueryListClickListner;
import com.example.testproject.model.ContentModel;
import com.example.testproject.R;

import java.util.ArrayList;
import java.util.List;

public class SearchContentAdapter extends RecyclerView.Adapter<SearchContentAdapter.myviewholder> {

    private List<ContentModel> data;
    private Context context;
    NavController navController ;
    private QueryListClickListner queryListClickListner;
    private List<ContentModel> mFilteredList;
    private List<ContentModel> mArrayList;

    private SearchContentItemListRowBinding binding;


    public SearchContentAdapter(List<ContentModel> data, Context context,QueryListClickListner queryListClickListner) {
        if(this.data==null){
            this.data=new ArrayList<>();
        }
        this.queryListClickListner=queryListClickListner;
        this.data.addAll(data);
        this.context = context;
        mFilteredList = data;
        mArrayList=data;

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
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    List<ContentModel> filteredList = new ArrayList<>();

                    for (ContentModel saleListULBDocDataModel : mArrayList) {

                        if (saleListULBDocDataModel.content.toLowerCase().contains(charString)) {

                            filteredList.add(saleListULBDocDataModel);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (List<ContentModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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