package com.example.testproject.ui.fragment.user;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;
import androidx.print.PrintHelper;

import com.example.testproject.Network.ApiClient;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.database.Dao.UserDao;
import com.example.testproject.model.RootModel;
import com.example.testproject.util.JsonMyUtils;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.example.testproject.BuildConfig;
import com.example.testproject.R;
import com.example.testproject.database.AppDatabase;
import com.example.testproject.databinding.CustomDialogWithEditSpinnerTextBinding;
import com.example.testproject.databinding.FragmentSearchContentDetailssBinding;
import com.example.testproject.model.SearchContentResponseDataModel;
import com.example.testproject.ui.base.BaseFragment;
import com.example.testproject.util.AppConstants;
import com.example.testproject.util.CommonUtils;
import com.example.testproject.util.ImageUtil;
import com.example.testproject.util.SharedPreferenceHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;


public class ViewSingleContentFragment extends BaseFragment implements View.OnClickListener, Html.ImageGetter {
    byte FONT_TYPE;
    Bitmap bitmap;
    private static BluetoothSocket btsocket;
    private static OutputStream btoutputstream;
    byte[] image;

    private static final String TAG = "SearchContentDetailsFragment";
    private UserDao userDao;
    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;

    private FragmentSearchContentDetailssBinding binding;
    String content;
    public String data;
    String user_role;
    private String mediaUrl="";
    private MediaController mediaControls;
    private ExoPlayer absPlayerInternal;


    private SearchContentResponseDataModel responseDataModel;

    public static ViewSingleContentFragment newInstance(Bundle args) {
        ViewSingleContentFragment fragment = new ViewSingleContentFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw();
        }
        layoutId = R.layout.fragment_search_content_detailss;
    }


    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding = (FragmentSearchContentDetailssBinding) viewDataBinding;
        user_role = SharedPreferenceHelper.getSharedPreferenceString(getContext(), "user_role", "");
        setupNetwork();

//        ((FragmentActivity) getActivity()).setmBack("Back");
        userDao = AppDatabase.getInstance(getContext()).userdao();
//        searchContentDetailDao = AppDatabase.getInstance(getContext()).searchContentDetailResponse();
//        if(getActivity() instanceof FragmentActivity) {
//            ((FragmentActivity) getActivity()).enableNavigationViews(false);
//            ((FragmentActivity) getActivity()).btnPrint.setBackgroundResource(R.drawable.printer);
//            ((FragmentActivity) getActivity()).btnPrint.setVisibility(View.VISIBLE);
//            ((FragmentActivity) getActivity()).img_help.setVisibility(View.GONE);
//            ((FragmentActivity) getActivity()).shareimg.setVisibility(View.VISIBLE);
//            ((FragmentActivity) getActivity()).mBack.setVisibility(View.VISIBLE);
//            ((FragmentActivity) getActivity()).shareimg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    shareOnClick();
//                }
//            });
//        }



        binding.btnViewImg.setVisibility(View.GONE);
        if (getArguments() != null) {
            content = getArguments().getString("contentId");
            String d=getArguments().getString("model");
            if(d!=null)
            responseDataModel= (SearchContentResponseDataModel)CommonUtils.jsonToPojo(getArguments().getString("model"), SearchContentResponseDataModel.class);


//            ((FragmentActivity)getActivity()).setScreenTitle(getString(R.string.content));

            if (responseDataModel == null) {
                mApiManager.searchContentsListDetailRequest(content);
            } else {
                fillPosterData();
            }
        }
        binding.tvContent.setTextColor(Color.BLACK);



    }
    private void shareOnClick(){
        if(responseDataModel!=null) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
//                    String shareBody = "Knowledge domain : " + responseDataModel.getRef().getKnowledgeDomain().getName() + "\n" + "Content : " + responseDataModel.getContent();
            String shareBody =  "Content : " + responseDataModel.getContent();
            String cType=responseDataModel.getType();
            String lType=responseDataModel.getLinkType();

            if(cType.equals("V") && lType.equals("Internal")|| cType.equals("U") && lType.equals("Internal")){
                CommonUtils.downloadFile(getContext(),false,null,BuildConfig.BASE_URL+responseDataModel.getContent(),"Content:-",responseDataModel.getContent());
            }else if(cType.equals("V") && lType.equals("External")|| cType.equals("U") && lType.equals("External")){
                CommonUtils.downloadFile(getContext(),false,null,responseDataModel.getContent(),"Content:-",responseDataModel.getContent());
            }else if(cType.equals("D") && lType.equals("External")){
                CommonUtils.downloadFile(getContext(),false,null,responseDataModel.getDocument(),"Content:-",responseDataModel.getContent());
            }else if(cType.equals("D") ){
                CommonUtils.downloadFile(getContext(),false,null,ApiClient.BASE_URL+responseDataModel.getDocument(),"Content:-",responseDataModel.getContent());
            }else if(cType.equals("P") ){

                CommonUtils.downloadFile(getContext(),false,ImageUtil.screenshot2(binding.webView1),"","*Poster Content*","");

            }else {
                CommonUtils.downloadFile(getContext(),true,null,responseDataModel.getContent(),"Content:-",responseDataModel.getContent());
            }




//                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "content");
//                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
//                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
    }
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item=menu.findItem(R.id.mIShare);
        if(item!=null)
            item.setVisible(true);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mIShare: {
                shareOnClick();
                return false;
            }
            case R.id.miInfo: {
                showInfoDialog();
                return false;
            }
        }
        return false;
    }
    private void showInfoDialog(){

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext(),R.style.MyDialogTheme);
        builder.setTitle(getString(R.string.contentInfo));
        LinearLayout linearLayout=new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
            CustomDialogWithEditSpinnerTextBinding dialogbinding = CustomDialogWithEditSpinnerTextBinding.inflate(LayoutInflater.from(getContext()));
            dialogbinding.spinnerB1.setVisibility(View.GONE);
            dialogbinding.spinnerC1.setVisibility(View.GONE);
            dialogbinding.etInput.setEnabled(false);
            dialogbinding.etInputB1.setEnabled(false);
            dialogbinding.etInputC1.setEnabled(false);

            dialogbinding.txtTittle.setText(getString(R.string.organisation));
            dialogbinding.etInput.setText(CommonUtils.addNAifValueEmptyORNull(responseDataModel.getRef().organisation.name));

            dialogbinding.txtTittleB1.setText(getString(R.string.project));
            dialogbinding.etInputB1.setText(CommonUtils.addNAifValueEmptyORNull(responseDataModel.getRef().project.name));

            dialogbinding.txtTittleC1.setText(getString(R.string.subdomain));
            dialogbinding.etInputC1.setText(CommonUtils.addNAifValueEmptyORNull(responseDataModel.getRef().subDomain.name));


            linearLayout.addView(dialogbinding.getRoot());

        builder.setView(linearLayout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void addQuery(){
        Bundle bundle=new Bundle();
        bundle.putString("","");
//        CustomFragmentManager.replaceFragment(getFragmentManager(), UploadQueryImageFragment.newInstance(bundle), true);
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.btn_finish) {
//            if(getActivity() instanceof FragmentActivity) {
//                ((FragmentActivity) getActivity()).imgfeedback.setVisibility(View.GONE);
//                ((FragmentActivity) getActivity()).btnPrint.setVisibility(View.GONE);
//            }
            getFragmentManager().popBackStack();
//        }
    }

    @Override
    public void onBackCustom() {
        FragmentManager manager=getFragmentManager();
//        if (getActivity() instanceof FragmentActivity) {
//            if (manager != null) {
//                    manager.popBackStack();
//            }
//        }else {
//            if(manager!=null && loginDao.getLoginResponse()!=null && !loginDao.getLoginResponse().getRole().isEmpty()){
//                if(getArguments() != null && getArguments().containsKey("fromView")){
//                    manager.popBackStack();
//                }else {
//                    CustomFragmentManager.replaceFragment(manager, new UserDashboardFragment());
//                }
//            }else {
//                getActivity().startActivity(new Intent(getActivity(), UserLoginActivity.class));
//                getActivity().finish();
//            }
//        }
    }


    private void fillPosterData(){


        if(responseDataModel.getType().equals("U")){
            binding.btnViewImg.setText(getString(R.string.viewInYoutube));
            binding.btnViewImg.setVisibility(View.VISIBLE);
            binding.lyoutContent.setVisibility(View.VISIBLE);
            binding.tvContent.setText(responseDataModel.getContentTitle());
        }else if(responseDataModel.getType().equals("D")){
            binding.btnViewImg.setText("View Doc");
            binding.btnViewImg.setVisibility(View.VISIBLE);
            binding.lyoutContent.setVisibility(View.VISIBLE);
            binding.tvContent.setVisibility(View.VISIBLE);
            binding.tvContent.setText(responseDataModel.getContentTitle());
        }else if(responseDataModel.getType().equals("P")){
            binding.btnViewImg.setText("View Poster");
            binding.btnViewImg.setVisibility(View.VISIBLE);
            binding.lyoutContent.setVisibility(View.VISIBLE);
            binding.tvContent.setVisibility(View.VISIBLE);
            binding.tvContent.setText(responseDataModel.getContentTitle());
        }else if(responseDataModel.getType().equals("V")){
            binding.btnViewImg.setVisibility(View.VISIBLE);
            binding.lyoutContent.setVisibility(View.VISIBLE);
            binding.tvContent.setVisibility(View.VISIBLE);

        }else {
            binding.tvContent.setVisibility(View.VISIBLE);
            binding.btnViewImg.setVisibility(View.GONE);
            binding.tvContent.setText(responseDataModel.getContent());
        }
        binding.btnViewImg.setVisibility(View.GONE);




        showAttachments();
    }
    RemotePDFViewPager remotePDFViewPager;

    private void showAttachments(){



//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


//        binding.webView1.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//            }
//        });

        if (responseDataModel.getType().equals("U")) {
//            binding.progressBar.setVisibility(View.VISIBLE);
            String urls = responseDataModel.getContent();
            binding.btnViewImg.setVisibility(View.GONE);
            binding.tvContent.setVisibility(View.VISIBLE);
            binding.tvContent.setText(responseDataModel.getContentTitle());
            binding.webView1.setVisibility(View.GONE);
            if(urls==null ||urls.isEmpty()){
                Toast.makeText(getActivity(),"no media link",Toast.LENGTH_SHORT).show();
                return;
            }
//             urls = "https://download.samplelib.com/mp4/sample-5s.mp4";
            if (responseDataModel.getLinkType().equals("Utube")) {
                binding.btnViewImg.setVisibility(View.VISIBLE);
                final String vidUrl=urls;
                binding.btnViewImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(vidUrl)));
                    }
                });


//
//                binding.videoView.setVisibility(View.GONE);
//                binding.webView1.setVisibility(View.GONE);
            }else if(responseDataModel.getLinkType().equals("Internal")){
                urls= BuildConfig.BASE_URL+responseDataModel.getContent();

                binding.textVideoNotOpen.setVisibility(View.VISIBLE);
                binding.videNotOpenClick.setVisibility(View.VISIBLE);
                final String mUrl=urls;
                binding.videNotOpenClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(mUrl), "video/*");
                        startActivity(intent);
                    }
                });
                binding.pvMain.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.VISIBLE);


                exoVideoPlayer(urls,true);
            }else if(!responseDataModel.getLinkType().isEmpty()){
                binding.textVideoNotOpen.setVisibility(View.VISIBLE);
                binding.videNotOpenClick.setVisibility(View.VISIBLE);
                final String mUrl=urls;
                binding.videNotOpenClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(mUrl), "video/mp4");
                        startActivity(intent);
                    }
                });
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.pvMain.setVisibility(View.VISIBLE);

                exoVideoPlayer(urls,true);
            }
        }else if(responseDataModel.getType().equals("D") || responseDataModel.getType().equals("P")){
            WebSettings ws = binding.webView1.getSettings();
            ws.setJavaScriptEnabled(true);
            ws.setBuiltInZoomControls(true);
            ws.setDisplayZoomControls(false);
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.webView1.setVisibility(View.VISIBLE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                WebView.enableSlowWholeDocumentDraw();
            }

            binding.webView1.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    if (binding.progressBar.getVisibility()!=View.GONE) {
                        binding.progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(getActivity(), "Error:" + description, Toast.LENGTH_SHORT).show();

                }

            });
            String url="";
            if(responseDataModel.getType().equals("D")){

                binding.textVideoNotOpen.setText(getString(R.string.doc_not_open));
                if(responseDataModel.getContent()==null|| responseDataModel.getContent().isEmpty()){
                    Toast.makeText(getActivity(), "Error:Doc missing", Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.GONE);
                    binding.tvContent.setVisibility(View.VISIBLE);
                    binding.tvContent.setText(responseDataModel.getContentTitle());
                    return;
                }

                url= ApiClient.BASE_URL+responseDataModel.getDocument().replaceFirst("/","");
//                url="http://nicedemo.logikoof.org/api/documents/kmdocument/6EE743D9CDNCF3%20%281%29.pdf";

                if(url.contains(".pdf")){
//                    url="https://drive.google.com/viewerng/viewer?embedded=true&url="+url;
                }
                final String loadUrl=url;
//                url= "<html><body><br><iframe width=\"100%\" height="+getResources().getDimension(R.dimen._510sdp)+" src="+url+" frameborder=\"0\" allowfullscreen></iframe></body></html>";

                 remotePDFViewPager =
                        new RemotePDFViewPager(getActivity(), url, new DownloadFile.Listener() {
                            @Override
                            public void onSuccess(String url, String destinationPath) {
                                if(url==null){
                                    return;
                                }
                                PDFPagerAdapter    adapter = new PDFPagerAdapter(getActivity(), FileUtil.extractFileNameFromURL(url));
                                remotePDFViewPager.setAdapter(adapter);

                                binding.pdfViewPager2.setVisibility(View.VISIBLE);
                                binding.pdfViewPager2.removeAllViewsInLayout();
                                binding.pdfViewPager2.addView(remotePDFViewPager,
                                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                binding.webView1.setVisibility(View.GONE);
                                binding.progressBar.setVisibility(View.GONE);
                                binding.textVideoNotOpen.setVisibility(View.VISIBLE);
                                binding.videNotOpenClick.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Log.d("","");
                            }

                            @Override
                            public void onProgressUpdate(int progress, int total) {

                            }
                        });
//                remotePDFViewPager.setId(R.id.pdfViewPager);

//                binding.webView1.loadData(url, "text/html", "utf-8");
                binding.videNotOpenClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(loadUrl));
                        startActivity(browserIntent);
                    }
                });


            }else {
                url=responseDataModel.getContent();
                if(url==null|| url.isEmpty()){
                    Toast.makeText(getActivity(), "no poster", Toast.LENGTH_SHORT).show();
                    binding.tvContent.setVisibility(View.VISIBLE);
                    binding.tvContent.setText(responseDataModel.getContentTitle());
                    binding.progressBar.setVisibility(View.GONE);
                    return;
                }
                if(url.contains(".pdf")){
                    url="https://drive.google.com/viewerng/viewer?embedded=true&url="+url;
                }
                final String mUrl=url;
                 binding.webView1.loadDataWithBaseURL("",mUrl, "text/html", "utf-8","");

            }

//
        }else if(responseDataModel.getType().equals("V")){
            if(responseDataModel.getLinkType().equals("Text") ) {
                binding.tvContent.setVisibility(View.VISIBLE);
                binding.tvContent.setText(responseDataModel.getContent());
            }else if( responseDataModel.getContent()==null || responseDataModel.getContent().isEmpty()){
                binding.tvContent.setVisibility(View.VISIBLE);
                binding.tvContent.setText("content missing");
            }else {
                if (responseDataModel.getLinkType().equals("Internal")){
                    mediaUrl=BuildConfig.BASE_URL+responseDataModel.getContent();
                }else{
                    mediaUrl=responseDataModel.getContent();
                }
                binding.tvContent.setVisibility(View.VISIBLE);
                binding.pvMainAudio.setVisibility(View.VISIBLE);
                exoVideoPlayer(mediaUrl,false);
                binding.tvContent.setText(responseDataModel.getContentTitle());

            }
        }
    }




    private void exoVideoPlayer(String CONTENT_URL,boolean isVideo){
        TrackSelector trackSelectorDef = new DefaultTrackSelector();
        absPlayerInternal = new ExoPlayer.Builder(getActivity()).build(); //creating a player instance

        String userAgent = Util.getUserAgent(getActivity(), this.getString(R.string.app_name));
        DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(getActivity(),userAgent);
        Uri uriOfContentUrl = Uri.parse(CONTENT_URL);
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);  // creating a media source

        absPlayerInternal.prepare(mediaSource);
        absPlayerInternal.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Player.EventListener.super.onPlayerStateChanged(playWhenReady, playbackState);
                switch(playbackState) {
                    case ExoPlayer.STATE_BUFFERING:
                        break;
                    case ExoPlayer.STATE_ENDED:
                        //do what you want
                        break;
                    case ExoPlayer.STATE_IDLE:
                        break;
                    case ExoPlayer.STATE_READY:
                        binding.progressBar.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }


        });
        absPlayerInternal.setPlayWhenReady(true); // start loading video and play it at the moment a chunk of it is available offline
        if(isVideo) {
            binding.pvMain.setPlayer(absPlayerInternal);
        }else {
            binding.pvMainAudio.setPlayer(absPlayerInternal);
        }
    }
    private void pausePlayer(ExoPlayer player) {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }
    private void playPlayer(SimpleExoPlayer player) {
        if (player != null) {
            player.setPlayWhenReady(true);
        }
    }
    private void stopPlayer(){
        if(absPlayerInternal!=null) {
            binding.pvMain.setPlayer(null);
            absPlayerInternal.release();
            absPlayerInternal = null;
        }
    }





    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
                showDialog(getActivity(), errorCode, false, true, 0);
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void isSuccess(Object response, int ServiceCode) {
                try {
                    if (ServiceCode == AppConstants.SEARCH_CONTENT_LIST_Detail_REQUEST) {
                        RootModel rootOneResModel= (RootModel) response;
                        SearchContentResponseDataModel s= (SearchContentResponseDataModel) JsonMyUtils.getPojoFromJsonObj(SearchContentResponseDataModel.class,rootOneResModel.getResponse().getData().getAsJsonObject());
                        responseDataModel =s;
                        if(responseDataModel==null){
                            Toast.makeText(getContext(),"No Data",Toast.LENGTH_SHORT).show();
                        }else {
                            fillPosterData();
                        }

                    }
                    else if (ServiceCode == AppConstants.SEARCH_POSTER_LIST_Detail_REQUEST) {

                        RootModel rootOneResModel= (RootModel) response;
                        SearchContentResponseDataModel s= (SearchContentResponseDataModel) JsonMyUtils.getPojoFromJsonObj(SearchContentResponseDataModel.class,rootOneResModel.getResponse().getData().getAsJsonObject());
                        responseDataModel =s;
                        if(responseDataModel==null){
                            Toast.makeText(getContext(),"No Data",Toast.LENGTH_SHORT).show();
                        }else {
                            fillPosterData();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }

//    protected void connect() {
//        if (btsocket == null) {
//            Intent BTIntent = new Intent(getActivity(), BTDeviceList.class);
//            this.startActivityForResult(BTIntent, BTDeviceList.REQUEST_CONNECT_BT);
//            doPhotoPrint();
//
//        } else {
//
//            OutputStream opstream = null;
//            try {
//                opstream = btsocket.getOutputStream();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            btoutputstream = opstream;
////            print_bt();
//            doPhotoPrint();
//        }
//
//    }

    public void screenShot(View view) {
        bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
//        binding.screenShot.setImageBitmap(bitmap);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        Bitmap bitmapp=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inTargetDensity = 200;
        options.inDensity = 200;

        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        image = stream.toByteArray();
//        connect();
        doPhotoPrint();
//        textView.setText("click");
    }

    private void doPhotoPrint() {
        PrintHelper photoPrinter = new PrintHelper(getActivity());
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//                R.drawable.droids);
        photoPrinter.printBitmap("Nicessm", bitmap);


    }

    private void print_bt() {
        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            btoutputstream = btsocket.getOutputStream();

//            byte[] printformat =   {0x1B, 0x2A, 33, (byte)255, 0};
//            btoutputstream.write(printformat);
//            String msg = data;
            btoutputstream.write(image);
            btoutputstream.flush();

           /* btoutputstream.write(msg.getBytes());
            btoutputstream.write(0x0D);
            btoutputstream.write(0x0D);
            btoutputstream.write(0x0D);
       */
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
//            btsocket = BTDeviceList.getSocket();
//            if (btsocket != null) {
////                print_bt();
//                doPhotoPrint();
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(R.mipmap.ic_launcher);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        new LoadImage().execute(source, d);

        return d;
    }

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private LevelListDrawable mDrawable;

        @SuppressLint("LongLogTag")
        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            Log.d(TAG, "doInBackground " + source);
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d(TAG, "onPostExecute drawable " + mDrawable);
            Log.d(TAG, "onPostExecute bitmap " + bitmap);
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);
                // i don't know yet a better way to refresh TextView
                // mTv.invalidate() doesn't work as expected
//                CharSequence t = binding.tvState.getText();
//                binding.tvState.setText(t);
            }
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible())
        {
            if (!isVisibleToUser)   // If we are becoming invisible, then...
            {

                pausePlayer(absPlayerInternal);
            }

            if (isVisibleToUser) // If we are becoming visible, then...
            {
                //play your video
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.webView1.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onPause() {
        super.onPause();
        pausePlayer(absPlayerInternal);
        binding.webView1.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlayer();

        try {
            if (btsocket != null) {
                btoutputstream.close();
                btsocket.close();
                btsocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


