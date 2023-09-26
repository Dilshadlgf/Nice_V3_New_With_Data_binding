package com.example.testproject.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUtil {

    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );


        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
    public static String getFileExt(String uri){
        if(uri.contains(".")) {
            return uri.substring(uri.lastIndexOf(".")+1);
        }
        return "";
    }
    public static File getTempFile(Context context, String fileName){
        File file= new File(context.getCacheDir() + "/" + fileName);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    public static String getFileName(String uri){
        if(uri.contains("/")) {
            uri= uri.substring(uri.lastIndexOf("/")+1);
            if (uri.indexOf(".") > 0)
                uri = uri.substring(0, uri.lastIndexOf("."));
            return uri;
        }
        return "";
    }
    public static Uri saveImage(Context context, Bitmap image) {
        //TODO - Should be processed in another thread
        File imagesFolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        try {
            if(!imagesFolder.exists()) {
                imagesFolder.mkdir();
            }
            File file = new File(imagesFolder, "shared_image.png");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".provider", file);

        } catch (IOException e) {
            Log.d("TAG", "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }
    public static void addThumbFromUrl(Context context, ImageView view, String url , RecyclerView.ViewHolder viewHolder){
        long thumb = viewHolder.getLayoutPosition()*1000;
        RequestOptions options = new RequestOptions().frame(thumb);
        Glide.with(context).load(url).apply(options).into(view);
    }
    public static Bitmap screenshot2(WebView webView) {
        webView.measure(View.MeasureSpec.makeMeasureSpec(
                        View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        webView.layout(0, 0, webView.getMeasuredWidth(), webView.getMeasuredHeight());
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(webView.getMeasuredWidth(),
                webView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        int iHeight = bitmap.getHeight();
        canvas.drawBitmap(bitmap, 0, iHeight, paint);
        webView.draw(canvas);
        return bitmap;
    }


}
