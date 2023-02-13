package com.example.testproject.Util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.testproject.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UploadPicVidDocFiles {
    public static int CAMERA_REQUEST=111;
    public static int GALLERY_REQUEST=112;
    public static int DOC_REQUEST=113;
    public static int AUDIO_REQUEST=114;
    public static int VIDEO_REQUEST=115;
    public static String FilePath="";
    public List<Uri> fileUriList ;
    public List<String> serverUrlList ;

    public static String AUDIO_TYPE="Upload Audio";
    public static String DOC_TYPE="Upload Doc";
    public static String VIDEO_TYPE="Upload Video";
    public static String CHOOSE_GALLERY_TYPE="Choose from Gallery";
    private static UploadPicVidDocFiles uploadPicVidDocFiles;

    public static UploadPicVidDocFiles getInstance(){
        if(uploadPicVidDocFiles==null){
            uploadPicVidDocFiles=new UploadPicVidDocFiles();
        }
        return uploadPicVidDocFiles;
    }

    public void selectImage(Activity activity, Fragment fragment) {
        if(checkAndRequestPermissions(activity)){
            return;
        }
        try {
            final CharSequence[] options = {"Take Photo", "Choose from Gallery","Upload Doc", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Add Photo!");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
                            // Create the File where the photo should go
                            File photoFile = null;
                            try {
                                photoFile = createImageFile(".jpg");

                            } catch (IOException ex) {
                                // Error occurred while creating the File
//                                Log.i(TAG, "IOException");
                            }
                            // Continue only if the File was successfully created
                            File finalPhotoFile = photoFile;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (finalPhotoFile != null) {
                                        FilePath= finalPhotoFile.getAbsolutePath();
                                        Uri outputFileUri = FileProvider.getUriForFile( Objects.requireNonNull(activity),
                                                BuildConfig.APPLICATION_ID+ ".provider", finalPhotoFile);
                                        String[] mimeTypes = {"image/jpeg", "image/png"};
                                        cameraIntent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                                        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        if(fragment!=null) {
                                            fragment.startActivityForResult(cameraIntent, CAMERA_REQUEST);
                                        }else {
                                            activity.startActivityForResult(cameraIntent, CAMERA_REQUEST);
                                        }
                                    }
                                }
                            },400);



                        }

                    } else if (options[item].equals("Choose from Gallery")) {
                        //                    galleryIntent();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                String[] mimeTypes = {"image/jpeg", "image/png"};
                                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                if(fragment!=null) {
                                    fragment.startActivityForResult(intent, GALLERY_REQUEST);
                                }else {
                                    activity.startActivityForResult(intent, GALLERY_REQUEST);
                                }
                            }
                        },400);
                    } else if (options[item].equals("Upload Doc")) {
                        //                    galleryIntent();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                String[] mimeTypes = {"application/pdf", "application/msword"};
                                intent.setType("*/*");
                                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                if(fragment!=null) {
                                    fragment.startActivityForResult(intent, DOC_REQUEST);
                                }else {
                                    activity.startActivityForResult(intent, DOC_REQUEST);
                                }
                            }
                        },400);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void chooseFile(Activity activity,Fragment fragment,String fileType){
        if(checkAndRequestPermissions(activity) || fileType==null || fileType.isEmpty()){
            return;
        }
        try {
            final CharSequence[] options = {fileType,"Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Choose File");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Choose from Gallery")) {
                        //                    galleryIntent();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                String[] mimeTypes = {"image/jpeg", "image/png"};
                                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                if(fragment!=null) {
                                    fragment.startActivityForResult(intent, GALLERY_REQUEST);
                                }else {
                                    activity.startActivityForResult(intent, GALLERY_REQUEST);
                                }
                            }
                        },400);
                    } else if (options[item].equals("Upload Doc")) {
                        //                    galleryIntent();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                String[] mimeTypes = {"application/pdf", "application/msword"};
                                intent.setType("*/*");
                                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                if(fragment!=null) {
                                    fragment.startActivityForResult(intent, DOC_REQUEST);
                                }else {
                                    activity.startActivityForResult(intent, DOC_REQUEST);
                                }
                            }
                        },400);
                    }else if (options[item].equals("Upload Audio")) {
                        //                    galleryIntent();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                String[] mimeTypes = {"audio/wav", "audio/mp3"};
                                intent.setType("audio/*");
                                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                if(fragment!=null) {
                                    fragment.startActivityForResult(intent, AUDIO_REQUEST);
                                }else {
                                    activity.startActivityForResult(intent, AUDIO_REQUEST);
                                }
                            }
                        },400);
                    }else if (options[item].equals("Upload Video")) {
                        //                    galleryIntent();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                String[] mimeTypes = {"video/mp4", "video/3gp"};
                                intent.setType("*/*");
                                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                if(fragment!=null) {
                                    fragment.startActivityForResult(intent, VIDEO_REQUEST);
                                }else {
                                    activity.startActivityForResult(intent, VIDEO_REQUEST);
                                }
                            }
                        },400);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static File createImageFile(String extention) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                extention,         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private boolean checkAndRequestPermissions(Activity activity) {
        int camera = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int read = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int write = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (write != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty())
        {
            int REQUEST_CAMERA = 105;
            ActivityCompat.requestPermissions(activity,listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_CAMERA);
        }else {
            return false;
        }
        return true;
    }
    public void setImage(Context context,ImageView imageView,Uri uri){

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
//                setPreviewImage(bitmap, path);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
