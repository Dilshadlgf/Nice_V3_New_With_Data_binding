package com.example.testproject.Util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.testproject.databinding.FarmerCroplistFragmentsBinding;

public class CustomDialog {
    CustomDialog(Context context,int layoutId,int dialogType) {
        Dialog dialog = new Dialog(context);
        ViewDataBinding viewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, null, false);
        dialog.setContentView(viewBinding.getRoot());

        if (dialogType == 1) {
            FarmerCroplistFragmentsBinding cropBinding = (FarmerCroplistFragmentsBinding) viewBinding;

        }
    }
}
