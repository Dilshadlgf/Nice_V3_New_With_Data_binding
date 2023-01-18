package com.example.testproject.ui.Fragment.Farmer;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.testproject.BuildConfig;
import com.example.testproject.Network.ApiClient;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.databinding.FragmentBlank1Binding;
import com.example.testproject.model.SearchContentResponseDataModel;
import com.example.testproject.model.SingleObjectModel.SingleObjRootOneResModel;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class SearchContentDetailsFragment extends BaseFragment {

    private FragmentBlank1Binding binding;
    private SearchContentResponseDataModel responseDataModel;

    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private String mediaUrl="";
    private MediaController mediaControls;
    private SimpleExoPlayer absPlayerInternal;
//    private RemotePDFViewPager remotePDFViewPager;

    public static QueryFragment newInstance(Bundle args) {
        QueryFragment fragment = new QueryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        layoutId=R.layout.fragment_blank1;
    }

    @Override
    protected void setUpUi(View view, ViewDataBinding viewDataBinding) {
        binding= (FragmentBlank1Binding) viewDataBinding;

        if (getArguments() != null) {
            String contentId = getArguments().getString("contentId");
            String d=getArguments().getString("model");
            if(d!=null)
                responseDataModel= (SearchContentResponseDataModel) CommonUtils.jsonToPojo(getArguments().getString("model"), SearchContentResponseDataModel.class);

            if (responseDataModel == null) {
                mApiManager.searchContentsListDetailRequest(contentId);
            } else {
                fillData();

            }
        }
    }


    private void fillData(){
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
    private void showAttachments(){



        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels/3;
        int width = displayMetrics.widthPixels;

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
            }else {
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

                binding.textVideoNotOpen.setText(getString(R.string.dob_required));
                if(responseDataModel.getDocument()==null|| responseDataModel.getDocument().isEmpty()){
                    Toast.makeText(getActivity(), "Error:Doc missing", Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.GONE);
                    binding.tvContent.setVisibility(View.VISIBLE);
                    binding.tvContent.setText(responseDataModel.getContentTitle());
                    return;
                }

                url= ApiClient.BASE_URL+responseDataModel.getDocument().replaceFirst("/","");
                final String loadUrl=url;
//                url= "<html><body><br><iframe width=\"100%\" height="+getResources().getDimension(R.dimen._510sdp)+" src="+url+" frameborder=\"0\" allowfullscreen></iframe></body></html>";

//                remotePDFViewPager =
//                        new RemotePDFViewPager(getActivity(), url, new DownloadFile.Listener() {
//                            @Override
//                            public void onSuccess(String url, String destinationPath) {
//                                if(url==null){
//                                    return;
//                                }
//                                PDFPagerAdapter    adapter = new PDFPagerAdapter(getActivity(), FileUtil.extractFileNameFromURL(url));
//                                remotePDFViewPager.setAdapter(adapter);
//
//                                binding.pdfViewPager2.setVisibility(View.VISIBLE);
//                                binding.pdfViewPager2.removeAllViewsInLayout();
//                                binding.pdfViewPager2.addView(remotePDFViewPager,
//                                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                                binding.webView1.setVisibility(View.GONE);
//                                binding.progressBar.setVisibility(View.GONE);
//                                binding.textVideoNotOpen.setVisibility(View.VISIBLE);
//                                binding.videNotOpenClick.setVisibility(View.VISIBLE);
//                            }
//
//                            @Override
//                            public void onFailure(Exception e) {
//                                Log.d("","");
//                            }
//
//                            @Override
//                            public void onProgressUpdate(int progress, int total) {
//
//                            }
//                        });
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
                    mediaUrl= BuildConfig.BASE_URL+responseDataModel.getContent();
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
        absPlayerInternal = new SimpleExoPlayer.Builder(getActivity()).build(); //creating a player instance

        String userAgent = Util.getUserAgent(getActivity(), this.getString(R.string.app_name));
        DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(getActivity(),userAgent);
        Uri uriOfContentUrl = Uri.parse(CONTENT_URL);
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(MediaItem.fromUri(uriOfContentUrl));  // creating a media source

        absPlayerInternal.prepare(mediaSource);
        absPlayerInternal.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
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
                Player.Listener.super.onPlaybackStateChanged(playbackState);
            }
        });

        absPlayerInternal.setPlayWhenReady(true); // start loading video and play it at the moment a chunk of it is available offline
        if(isVideo) {
            binding.pvMain.setPlayer(absPlayerInternal);
        }else {
            binding.pvMainAudio.setPlayer(absPlayerInternal);
        }
    }
    private void pausePlayer(SimpleExoPlayer player) {
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
                        SingleObjRootOneResModel rootOneResModel= (SingleObjRootOneResModel) response;
                        responseDataModel =rootOneResModel.getResponse().getData().getContent();
                        fillData();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        mApiManager = new ApiManager(getContext(), mInterFace);
    }
}