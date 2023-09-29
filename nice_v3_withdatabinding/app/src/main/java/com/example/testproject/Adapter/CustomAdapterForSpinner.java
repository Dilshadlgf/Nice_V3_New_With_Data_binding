package com.example.testproject.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.testproject.R;

import java.util.List;

public class CustomAdapterForSpinner extends ArrayAdapter<String[]> {

    LayoutInflater flater;


    public CustomAdapterForSpinner(@NonNull Context context, int resource) {
        super(context, resource);
    }
    public CustomAdapterForSpinner(@NonNull Context context, int resource, List<String[]> strings) {
        super(context, resource,strings);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView,position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowviewDrop(convertView,position);
    }

    private View rowview(View convertView , int position){

        String[] rowItem = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.spinner_textview, null, false);
            ((TextView)rowview).setText(rowItem[0]);
            holder.txtTitle =((TextView)rowview);
            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }
//        holder.txtTitle.setBackgroundColor(Color.WHITE);
        holder.txtTitle.setTextColor(Color.DKGRAY);
        holder.txtTitle.setGravity(Gravity.CENTER);
//        holder.imageView.setImageResource(rowItem.getImageId());
        holder.txtTitle.setText(rowItem[0]);

        return rowview;
    }
    private View rowviewDrop(View convertView , int position){

        String[] rowItem = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.spinner_textview, null, false);
            ((TextView)rowview).setText(rowItem[0]);
            holder.txtTitle =((TextView)rowview);
            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }
        holder.txtTitle.setBackgroundColor(Color.WHITE);
        holder.txtTitle.setTextColor(Color.BLACK);
//        holder.imageView.setImageResource(rowItem.getImageId());
        holder.txtTitle.setText(rowItem[0]);

        return rowview;
    }

    private class viewHolder{
        TextView txtTitle;
        ImageView imageView;
    }
}
