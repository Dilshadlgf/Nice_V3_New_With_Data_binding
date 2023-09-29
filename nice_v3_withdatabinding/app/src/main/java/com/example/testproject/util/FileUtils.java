package com.example.testproject.util;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtils {


    public static File createTempFile(Context context,String extention) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "file_" + timeStamp + "_";
        File outputDir = context.getCacheDir(); // context being the Activity pointer

        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return File.createTempFile(imageFileName, extention, outputDir);
    }
    public static File createTempFileOverWrite(Context context,String extention) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "file_" + "overwrite" + "_";
        File outputDir = context.getCacheDir(); // context being the Activity pointer

        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return File.createTempFile(imageFileName, extention, outputDir);
    }
    public static void copyFileToSDFromAsset(Context context,String filename) {
        AssetManager assetManager = context.getAssets();
        if(checkAndRequestPermissions((Activity) context)){
            return;
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            // Create new file to copy into.
            File file = null; //or any other folder for specific purpose
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                File mfile = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)));
                if(!mfile.exists()){
                    mfile.mkdir();
                }
                file= new File(mfile.getAbsolutePath()+"/"+filename);
            }else {
                file = new File(Environment.getExternalStorageDirectory() + File.separator + filename);
            }

            file.createNewFile();
//            String newFileName = "/data/data/" + context.getPackageName() + "/" + filename;
            out = new FileOutputStream(file.getAbsolutePath());

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            Toast.makeText(context, "File Successfully downloaded on your device :  "+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
            Toast.makeText(context, "ERROR:  "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public static boolean checkAndRequestPermissions(Context activity) {
        int read1 = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int write = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (read1 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (write != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty())
        {
            int REQUEST_CAMERA = 105;
            ActivityCompat.requestPermissions((Activity) activity,listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_CAMERA);
        }else {
            return false;
        }
        return true;
    }

    public static boolean checkAndRequestPermissionsAny(Context activity,String[] permissionList) {
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (int i = 0; i <permissionList.length ; i++) {
            if(ContextCompat.checkSelfPermission(activity,permissionList[i])!=PackageManager.PERMISSION_GRANTED){
                listPermissionsNeeded.add(permissionList[i]);
            }
        }
        if (!listPermissionsNeeded.isEmpty())
        {
            int REQUEST_CAMERA = 105;
            ActivityCompat.requestPermissions((Activity) activity,listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_CAMERA);
        }else {
            return false;
        }
        return true;
    }

    public static String downloadFileInCacheDir(String url,Context context,String extension) {
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();
            String contentLengthStr=conn.getHeaderField("content-length");
            contentLength=Integer.parseInt(contentLengthStr);
            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();
            File file=createTempFile(context,extension);
            DataOutputStream fos = new DataOutputStream(new FileOutputStream(file));
            fos.write(buffer);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch(FileNotFoundException e) {
            return ""; // swallow a 404
        } catch (IOException e) {
            return ""; // swallow a 404
        }
    }
    public static String downloadTask(Context context,String url , String name) throws Exception {
        File result =null;
        try {
//            File file = new File(Environment.getExternalStorageDirectory(), "Download");
//            if (!file.exists()) {
//                //noinspection ResultOfMethodCallIgnored
//                file.mkdirs();
//            }
//            File result = new File(file.getAbsolutePath() + File.separator + name);
             result = createTempFile(context,name);;
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
//            request.setDestinationUri(Uri.fromFile(result));
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS,"JNKV.tmp");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setAllowedOverRoaming(false).setTitle(name);//for mp3 title
            request.setDescription("Something useful. No, really.");
            if (downloadManager != null) {
                downloadManager.enqueue(request);
            }
            //mToast(mContext, "Starting download...");
            MediaScannerConnection.scanFile(context, new String[]{result.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("tag >>>>", "on Downlaod check it");


                        }
                    });
        } catch (Exception e) {
            Log.i("tag >>>> ", e.toString());
            return result!=null?result.getAbsolutePath():null;
        }
        return result!=null?result.getAbsolutePath():null;
    }
}
