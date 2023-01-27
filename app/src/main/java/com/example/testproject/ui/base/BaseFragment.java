package com.example.testproject.ui.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.testproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseFragment extends Fragment implements BaseFragmentInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final int REQUEST_LOCATION = 101;

    protected int layoutId = -1;
    private boolean gpsAlertEnable=false;

    public BaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BaseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseFragment newInstance(String param1, String param2) {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        init();
        ViewDataBinding viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false);
        if (viewBinding != null) {
            view = viewBinding.getRoot();


            setUpUi(view, viewBinding);
        }

//        if (!isGooglePlayServicesAvailable()) {
//
//            showDialog(getActivity(), getString(R.string.google_play_services),false,false,0);
//
//
//        }
//        else if(isGPSEnabled()==false){
////            alertMessageNoGps();
//        }


        return view;
    }
    protected void init() {


    }

    protected void setUpUi(Bundle savedInstanceState) {

    }

    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {

    }
    private void EnableGPSIfPossible() {
        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Your GPS seems to be disabled,Please enable it?")
                .setCancelable(false)
                .setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        AlertDialog d=builder.create();
        d.show();
    }

    public void showDialog(Activity activity, String msg, boolean isCancelBtnVisible, final boolean isClickable, final int Id){
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            TextView text = dialog.findViewById(R.id.text_dialog);
            text.setText(msg);

            Button dialogOKButton = dialog.findViewById(R.id.btn_ok);

            dialogOKButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (isClickable)
                        BaseFragment.this.okDialogClick(Id);
                }
            });

            Button dialogCancelButton = dialog.findViewById(R.id.btn_cancel);
            if (isCancelBtnVisible) {
                dialogCancelButton.setVisibility(View.VISIBLE);
            } else {
                dialogCancelButton.setVisibility(View.GONE);

            }
            dialogCancelButton.setOnClickListener(v -> {
                dialog.dismiss();
                if (isClickable)
                    BaseFragment.this.cancelDialogClick(Id);
            });


            dialog.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void cancelDialogClick(int id) {

    }

    public void okDialogClick(int id) {


    }
    OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
        @Override
        public void handleOnBackPressed() {
            // Handle the back button event
            onBackCustom();
        }
    };


    public void onBackCustom() {

    }

}