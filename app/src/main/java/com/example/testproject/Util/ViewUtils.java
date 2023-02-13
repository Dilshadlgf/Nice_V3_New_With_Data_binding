package com.example.testproject.Util;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.testproject.Adapter.user.CustomAdapterForSpinner;
import com.example.testproject.R;

import java.util.List;

public class ViewUtils {

    public static TextView addAstrickLastOfText(TextView textView,String str){
        Spannable wordtoSpan = new SpannableString(str+"*");
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), str.length(), str.length()+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(wordtoSpan);
        return textView;
    }
    public static void fillDataInSpinner(Context context, Spinner spinner, List<String> list){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.text_view_white_color, list);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.textview);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
    public static void fillDataInSpinnerWithCustomAdaptor(Context context, Spinner spinner, List<String[]> list){
        CustomAdapterForSpinner dataAdapter = new CustomAdapterForSpinner(context, R.layout.spinner_textview, list);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.textview);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
    public static void triggerTouchOnView(View view,int x,int y){
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 100;
        int[] location = new int[2];
        int[] location2 = new int[2];
        view.getLocationOnScreen(location);
        float aa=view.getX();
        float aa2=view.getY();
        view.getLocationInWindow(location2);
        float aa4=view.getPivotX();
        float aa5=location2[1];
//        int x = location[0];
//        int y = location[1];
// List of meta states found here:     developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
        int metaState = 0;

        view.dispatchTouchEvent( MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis()+1000, (int)MotionEvent.ACTION_DOWN, x, y, 0));
        view.dispatchTouchEvent( MotionEvent.obtain(SystemClock.uptimeMillis()+1, SystemClock.uptimeMillis()+1001, (int)MotionEvent.ACTION_UP, x, y, 0));
    }
    public static void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
    public static void setMargins (ViewGroup view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}
