package com.example.testproject.util;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.example.testproject.R;
import com.example.testproject.databinding.AlertDialogBinding;
import com.example.testproject.interfaces.CustomAlertListener;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Monika on 06/02/19.
 */
public class CommonUtils {
    public static String addNAifValueEmptyORNull(String str){
        if(str==null || str.isEmpty()){
            return "N/A";
        }
        return str;
    }
    public  static boolean isEditTextEmpty(EditText editText) {
        if(editText.getText().toString().isEmpty()){
            Toast.makeText(editText.getContext(),"No a valid input",Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
    public static void makeToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    private static ProgressDialog progressDialog;

    public static void downloadFile(Context context, boolean isText, Bitmap bitmap, String url, String tittle, String msg) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    File outputFile=null;
                    String fileext="";
                    String mimeType="";
                    String path="";
                    fileext = ImageUtil.getFileExt(url);
                    if(!isText && !fileext.isEmpty()) {

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog=new ProgressDialog(context);
                                progressDialog.show();
                            }
                        });
                        outputFile = ImageUtil.getTempFile(context, ImageUtil.getFileName(url)+"." + fileext);
                        URL u = new URL(url);
                        URLConnection conn = u.openConnection();
                        int contentLength = conn.getContentLength();
                        if(contentLength>10) {
                            mimeType = conn.getContentType();
                            if (mimeType != null) {
                                String[] strings = mimeType.split("/");
                                if (strings.length > 0) {
                                    mimeType = strings[0];
                                }
                            }

                            DataInputStream stream = new DataInputStream(u.openStream());

                            byte[] buffer = new byte[contentLength];
                            stream.readFully(buffer);
                            stream.close();

                            DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
                            fos.write(buffer);
                            fos.flush();
                            fos.close();
                            path = outputFile.getAbsolutePath();
                        }else{
                            mimeType="";
                            fileext="";
                        }
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if(progressDialog!=null && progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                    progressDialog=null;
                                }
                            }
                        });
//                        mimeType=getMimeType(context,Uri.parse(path));
                    }

                    shareMediaWithText(context,isText,bitmap,path,mimeType,fileext,tittle,msg);
                } catch (FileNotFoundException e) {
                    return; // swallow a 404
                } catch (IOException e) {
                    return; // swallow a 404
                }
            }
        }).start();
    }
    public static void openCustomDialog(Context context, CustomAlertListener customAlertListener, String msg, int id){
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(context, R.style.MyDialogTheme);
        AlertDialogBinding binding=AlertDialogBinding.inflate(LayoutInflater.from(context));
        alertDialog.setView(binding.getRoot());
        binding.textDialog.setText(msg);
        Dialog dialog=alertDialog.create();
        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                customAlertListener.OnDialogOKClick(id);
            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                customAlertListener.OnDialogCancel(id);
            }
        });
        dialog.show();
    }

    public static void shareMediaWithText(Context context,boolean isText,Bitmap bitmap,String path,String fileType,String fileExt,String tittle,String msg){
//        String path = ""; //should be local path of downloaded video
        Uri bitmapUri=null;
        boolean hasMedia=false;

        if (!isText &&fileExt != null && !fileExt.isEmpty()) {
            hasMedia=true;
//            ContentValues content = new ContentValues(4);
//            content.put(MediaStore.Video.VideoColumns.DATE_ADDED,
//                    System.currentTimeMillis() / 1000);
//            content.put(MediaStore.Video.Media.MIME_TYPE, fileType);
//            content.put(MediaStore.Video.Media.DATA, path);
//
//            ContentResolver resolver = context.getContentResolver();
//             uri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, content);
        }else {
            if(bitmap!=null){
                bitmapUri=ImageUtil.saveImage(context,bitmap);
                fileType = "Image";
            }else {
                fileType = "text";
            }
        }
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType(fileType+"/*");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, tittle);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, msg);
        if(hasMedia ) {
            Uri muri=null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                muri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", new File(path));
            } else {
                muri = Uri.fromFile(new File(path));
            }
//                sharingIntent.setDataAndType(muri, context.getContentResolver().getType(muri));
            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, muri);
//
        }else if(bitmapUri!=null){
            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        }
        context.startActivity(Intent.createChooser(sharingIntent, "Share Video"));
    }

    public static String extractYTId(String ytUrl) {

        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(ytUrl);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "error";
        }
    }
    public static String pojoToJson(Object o){
        Gson gson=new Gson();
        return gson.toJson(o);
    }
    public static Object jsonToPojo(String s, Class Ty){
        Gson gson=new Gson();
        return gson.fromJson(s, Ty);
    }
    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public static boolean isValidPhoneNumber(CharSequence target) {
        if (target.length()!=10) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    public static String getFutuerAndBackDates(int day,boolean isBackDate,String format){
//        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.ss'Z'");
        org.joda.time.LocalDateTime dtf=null;
        if(isBackDate){
            dtf = org.joda.time.LocalDateTime.now().minusDays(day);
        }else{
            dtf = LocalDateTime.now().plusDays(day);
        }

// pass your DOB String
// Format for output
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern(format);
// Print the date
        System.out.println(dtfOut.print(dtf));
        return dtfOut.print(dtf);
    }

    public static String getMonthFormate(String date){
//        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.ss'Z'");
        DateTime dtf = ISODateTimeFormat.dateTimeParser().parseDateTime(date);
// pass your DOB String
// Format for output
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("EE");
// Print the date
        System.out.println(dtfOut.print(dtf));
        return dtfOut.print(dtf);
    }


    public static void setLocalLanguage(Context context,String lang){
        Locale locale = new Locale(lang);
        locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = context.getSharedPreferences("settings", MODE_PRIVATE).edit();
        editor.putString("mylang", lang);
        editor.apply();
    }
    public static String loadLocalLanguage(Context context){
        SharedPreferences sp= context.getSharedPreferences("settings", MODE_PRIVATE);
        String lang=sp.getString("mylang","en");
        if(lang.isEmpty()){
            lang="en";
        }
        Locale locale = new Locale(lang);
        locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        return lang;
    }

    public static String validMobile(String mobile) {

        String errormsg = "";
        String FIRST_DIGIT_EXAMPLE = "^[6-9][0-9]{9}$";
        String MOBILE_REGEX_SAME_EXAMPLE = "^(?=\\d{10}$)" +
                "(?:(.)\\1*|0?1?2?3?4?5?6?7?8?9?|9?8?7?6?5?4?3?2?1?0?)$";
        //first digit Indian digit

        if (!mobile.matches(FIRST_DIGIT_EXAMPLE)) {

            errormsg = "Mobile Number first digit should be 6,7,8,9";

            return errormsg;
        } else if (mobile.matches(MOBILE_REGEX_SAME_EXAMPLE)) {

            errormsg = "Mobile Number all digits should not be same";
            return errormsg;
        } else {

            return errormsg;

        }
    }
    public static String[] printDifference(String startDates, String endDates) {
        //format dd/M/yyyy hh:mm:ss
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
        Date endDate=null;
        Date startDate=null;
        try {
             endDate = format.parse(endDates);
             startDate = format.parse(startDates);
        }catch (ParseException  e){
            e.printStackTrace();
        }
            //milliseconds
        long different = endDate.getTime() - startDate.getTime();

//        System.out.println("startDate : " + startDate);
//        System.out.println("endDate : "+ endDate);
//        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        return new String[]{String.valueOf(elapsedDays), String.valueOf(elapsedHours),String.valueOf(elapsedMinutes)};
    }
    public static String convertDateFormat(String myDate,String from,String to) {
        String inputPattern = from;
        String outputPattern = to;
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(myDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getServerFormatDate(String datetime,String fromFormat) {
        if(datetime==null || datetime.isEmpty()){
            return "";
        }
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        SimpleDateFormat sdf = new SimpleDateFormat(fromFormat);//
        String formattedTime =datetime;
        Date d = null;
        try {
            d = sdf.parse(datetime);
            formattedTime= output.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }


        return formattedTime;
    }
    public static String getLastBitFromUrl(final String url){
        // return url.replaceFirst("[^?]*/(.*?)(?:\\?.*)","$1);" <-- incorrect
        return url.replaceFirst(".*/([^#/?]+).*", "$1");
    }
    public static String getDateFormat(String time) {
        if(time==null || time.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
        String formattedTime =time;
        Date d = null;
        try {
            d = sdf.parse(time);
            formattedTime= output.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }


        return formattedTime;
    }
    public static String getOnlyTimeFormat(String time) {
        // Parse the input date string
        DateTimeFormatter parser = ISODateTimeFormat.dateTime();
        DateTime dateTime = parser.parseDateTime(time);

        // Format the DateTime object to HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm:ss");
        return formatter.print(dateTime);
    }
    public static String getOnlyDateFormat(String time) {
        if(time==null || time.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
        String formattedTime =time;
        Date d = null;
        try {
            d = sdf.parse(time);
            formattedTime= output.format(d);
        } catch (ParseException e) {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try {
                d = sdf2.parse(time);
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            formattedTime= output.format(d);
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }


        return formattedTime;
    }
    public static String getOnlyDayFromDate(String time) {
        if(time==null || time.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        SimpleDateFormat output = new SimpleDateFormat("dd");
        String formattedTime =time;
        Date d = null;
        try {
            d = sdf.parse(time);
            formattedTime= output.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }


        return formattedTime;
    }
    public static String getOnlyMonthFromDate(String time) {
        if(time==null || time.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        SimpleDateFormat output = new SimpleDateFormat("MMM");
        String formattedTime =time;
        Date d = null;
        try {
            d = sdf.parse(time);
            formattedTime= output.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }


        return formattedTime;
    }

    public static String getCurrentDateForServer(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.getDefault());
        return sdf.format(new Date());
    }
    public static void openCalender(Activity activity, final EditText etDateToSet, boolean isOTB) {

        Context context = activity;
        if (isBrokenSamsungDevice()) {
            context = new ContextThemeWrapper(activity, android.R.style.Theme_Holo_Light_Dialog);
        }
        Locale.setDefault(Locale.ENGLISH);

        final Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.YEAR,-18); //If age limit is 18 year
        final DatePickerDialog datePickerDialog = new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT,
                (view, year, monthOfYear, dayOfMonth) -> {



                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (dialog, which) -> {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                DatePicker datePicker = datePickerDialog.getDatePicker();
                calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());

                int year = calendar.get(Calendar.YEAR);
                SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                String month = month_date.format(calendar.getTime());
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                etDateToSet.setText(makeDateDoubleFigure(day) + "-" + month + "-" + year);
            }
        });
        /*datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        if (isOTB) {
            try {
                Date today = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                c.add(Calendar.MONTH, -1);// Subtract 1 months
                long minDate = c.getTime().getTime(); // Twice!
                datePickerDialog.getDatePicker().setMinDate(minDate);

            } catch (Exception ex) {

            }
        }*/

        datePickerDialog.show();

    }

    public static void openWaetherCalender(Activity activity, final EditText etDateToSet, boolean isOTB) {

        Context context = activity;
        if (isBrokenSamsungDevice()) {
            context = new ContextThemeWrapper(activity, android.R.style.Theme_Holo_Light_Dialog);
        }

        final Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.YEAR,-18); //If age limit is 18 year
        final DatePickerDialog datePickerDialog = new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT,
                (view, year, monthOfYear, dayOfMonth) -> {



                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (dialog, which) -> {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                DatePicker datePicker = datePickerDialog.getDatePicker();
                int dayOfMonth = datePicker.getDayOfMonth(), year = datePicker.getYear(), monthOfYear = datePicker.getMonth() + 1;
                etDateToSet.setText(makeDateDoubleFigure(dayOfMonth) + "-" + makeDateDoubleFigure(monthOfYear) + "-" + year);            }
        });
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        if (isOTB) {
            try {
                Date today = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                c.add(Calendar.MONTH, -1);// Subtract 1 months
                long minDate = c.getTime().getTime(); // Twice!
                datePickerDialog.getDatePicker().setMinDate(minDate);

            } catch (Exception ex) {

                ex.getMessage();
            }
        }

        datePickerDialog.show();

    }
    private static boolean isBrokenSamsungDevice() {
        return (Build.MANUFACTURER.equalsIgnoreCase("samsung")
                && isBetweenAndroidVersions(
                Build.VERSION_CODES.LOLLIPOP,
                Build.VERSION_CODES.LOLLIPOP_MR1));
    }

    private static boolean isBetweenAndroidVersions(int min, int max) {
        return Build.VERSION.SDK_INT >= min && Build.VERSION.SDK_INT <= max;
    }

    private static String makeDateDoubleFigure(int data) {
        String temp = "";
        if (data < 10) {
            temp = "0" + data;
        } else {
            temp = "" + data;
        }
        return temp;
    }


    public static String getCurrentDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return formatter.format(date);
    }
    public static String getCurrentDateAndTime()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
        Date date = new Date();
        return formatter.format(date);
    }

    public static String getCurrentResolutionDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");
        Date date = new Date();
        return formatter.format(date);
    }

    public static boolean between(String weatherdate, String fromdate, String todate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

            Date date = sdf1.parse(weatherdate);
            Date dateStart=sdf.parse(fromdate);
            Date dateEnd=sdf.parse(todate);
            if (date != null && dateStart != null && dateEnd != null) {
                if ((date.after(dateStart) && date.before(dateEnd)) || date.equals(dateStart) || date.equals(todate)) {
                    return true;
                }
                else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return false;
    }

    public static <T> Object getPojoFromStr (T className,String str){
        return new Gson().fromJson(str, (Type) className);
    }
    public static <T> String getStrFromPojo (T className){
        return new Gson().toJson(className);
    }





}
