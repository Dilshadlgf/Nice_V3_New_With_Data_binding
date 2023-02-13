package com.example.testproject.Adapter.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.Network.ApiClient;
import com.example.testproject.Network.ApiManager;
import com.example.testproject.Network.ApiResponseInterface;
import com.example.testproject.R;
import com.example.testproject.Util.AppConstants;
import com.example.testproject.Util.CommonUtils;
import com.example.testproject.Util.ImageUtil;
import com.example.testproject.databinding.UserContentCardItemBinding;
import com.example.testproject.interfaces.CustomAlertListener;
import com.example.testproject.interfaces.ListItemClickListener;
import com.example.testproject.model.ContentModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.ui.Fragment.User.UserContentTabFragment;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.List;
import com.example.testproject.BuildConfig;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class UserContentAdaptor extends RecyclerView.Adapter<UserContentAdaptor.MViewholder> implements CustomAlertListener {

    ListItemClickListener itemClickListener;
    private List<ContentModel> modelList;
    private String YoutubeContent="U";
    private String DocContent="D";
    private String PosterContent="P";
    private String VoiceContent="V";
    private String TextContent="S";
    private Context mContext;

    private SimpleExoPlayer absPlayerInternal;
    private int screenType=1;
    private ActionHandler actionHandler;
    private String crntId="";

    private ApiManager mApiManager;
    private ApiResponseInterface mInterFace;
    private String userId;
    public UserContentAdaptor(Context context, String userId, int screenType, List<ContentModel> modelList, ListItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
        this.modelList=modelList;
        this.mContext=context;
        this.screenType=screenType;
        actionHandler= new ActionHandler();
        setupNetwork();
        this.userId=userId;
    }
    public void addItemInList(List<ContentModel> mlist){
        modelList.addAll(mlist);
        notifyDataSetChanged();
    }
    public void doClearList(){
        modelList.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserContentCardItemBinding binding=UserContentCardItemBinding.inflate(LayoutInflater.from(parent.getContext()));

        binding.txtResolvedBy.setVisibility(View.VISIBLE);
        binding.txtReviewBy.setVisibility(View.VISIBLE);
        binding.txtReviewDate.setVisibility(View.VISIBLE);
        if(screenType== UserContentTabFragment.approvedScreen || screenType== UserContentTabFragment.translatedScreen ||screenType== UserContentTabFragment.publishedScreen){
            if(screenType== UserContentTabFragment.approvedScreen ) {
//                binding.btnApprove.setVisibility(View.VISIBLE);
//                binding.btnApprove.setImageResource(R.drawable.ic_baseline_sync_alt_24);
            }else {
                binding.btnApprove.setVisibility(View.INVISIBLE);
            }
            binding.btnEdit.setVisibility(View.INVISIBLE);

            binding.txtCreatedby.setText(mContext.getString(R.string.assignedby));
        }else  if(screenType== UserContentTabFragment.rejectedScreen){
            binding.btnApprove.setVisibility(View.INVISIBLE);
            binding.btnEdit.setVisibility(View.INVISIBLE);
            binding.btnReject.setVisibility(View.INVISIBLE);

            binding.textCreatedby.setText(mContext.getString(R.string.assignedby));
        }else {
            binding.btnApprove.setVisibility(View.VISIBLE);
            binding.btnApprove.setImageResource(R.drawable.tickiv);
            binding.btnEdit.setVisibility(View.VISIBLE);
            binding.btnReject.setVisibility(View.VISIBLE);

            binding.txtResolvedBy.setVisibility(View.INVISIBLE);
            binding.txtReviewBy.setVisibility(View.INVISIBLE);
            binding.txtReviewDate.setVisibility(View.INVISIBLE);
            binding.textCreatedby.setText(mContext.getString(R.string.createdBy));
        }
        binding.btnApprove.setOnClickListener(actionHandler);
        binding.btnEdit.setOnClickListener(actionHandler);
        binding.btnReject.setOnClickListener(actionHandler);
        binding.btnDelete.setOnClickListener(actionHandler);
        return new MViewholder(binding);
    }

    @Override
    public void OnDialogOKClick(int id) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("id",crntId);
        switch (id){

            case 1:

//                mApiManager.contentApproveOrDelete("approved",crntId,AppConstants.APPROVED);
                mApiManager.commonApiWithThreePath("content","status","approved",null,hashMap, AppConstants.APPROVED,AppConstants.METHOD_PUT);
                break;
            case 2:
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("comment","");
                jsonObject.addProperty("id",crntId);
                jsonObject.addProperty("reviewedBy",userId);
                mApiManager.commonApiWithTwoPathPut("content","editrejected",jsonObject,"no",AppConstants.REJECTED);
                break;
            case 3:
                mApiManager.commonApiWithThreePath("content","status","delete",null,hashMap,AppConstants.DELETE,AppConstants.METHOD_DELETE);
                break;
        }
    }

    @Override
    public void OnDialogCancel(int id) {

    }

    class ActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String tag=v.getTag().toString();

            crntId=v.getTag(R.string.alert).toString();

            switch (tag){
                case "approve":
                    int isDissemination= (int) v.getTag(R.string.dissemination);
                    ContentModel model= (ContentModel) v.getTag(R.string.content);
                    String modelStr=CommonUtils.pojoToJson(model);
                    if(isDissemination==0) {
                        CommonUtils.openCustomDialog(v.getContext(), UserContentAdaptor.this,mContext.getString(R.string.doyouwanttoapprovethiscontent), 1);
                    }else {
                        Bundle bundle=new Bundle();
                        bundle.putString("content", modelStr);
                        bundle.putString("contentId", crntId);
                        FragmentManager manager = ((AppCompatActivity) mContext).getSupportFragmentManager();
//                        Navigation.findNavController(v).navigate(R.id.action_queryTabFragment_to_queryDetailPrintFragment,bundle);

//                        CustomFragmentManager.replaceFragment(manager, ContentDisseminationFragment.newInstance(bundle), true);
                    }
                    break;
                case "view":
//                    CommonUtils.makeToast(mContext,"Coming Soon");

                    break;
                case "reject":
                    CommonUtils.openCustomDialog(v.getContext(),UserContentAdaptor.this,mContext.getString(R.string.doyouwanttorejectthiscontent),2);
                    break;
                case "delete":
                    CommonUtils.openCustomDialog(v.getContext(),UserContentAdaptor.this,mContext.getString(R.string.doyouwanttodeletethiscontent),3);
                    break;
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onBindViewHolder(@NonNull MViewholder holder, int position) {
        ContentModel responseDataModel=modelList.get(position);

        holder.binding.btnApprove.setTag(R.string.alert,responseDataModel.id);
        if(screenType== UserContentTabFragment.approvedScreen) {
            holder.binding.btnApprove.setTag(R.string.dissemination, 1);
        }else {
            holder.binding.btnApprove.setTag(R.string.dissemination, 0);
        }
        holder.binding.btnApprove.setTag(R.string.content,responseDataModel);

        holder.binding.btnEdit.setTag(R.string.alert,responseDataModel.id);
        holder.binding.btnReject.setTag(R.string.alert,responseDataModel.id);
        holder.binding.btnDelete.setTag(R.string.alert,responseDataModel.id);


        holder.binding.youtubePlayerView.setVisibility(View.GONE);
        holder.binding.pvMainAudio.setVisibility(View.GONE);
        holder.binding.pvMain.setVisibility(View.GONE);
        holder.binding.imageView.setVisibility(View.GONE);

        holder.binding.txtContentTitle.setText(responseDataModel.contentTitle);

        holder.binding.txtContentId.setText(responseDataModel.recordId);

        holder.binding.txtContent.setVisibility(View.VISIBLE);
        holder.binding.txtContent.setText(responseDataModel.content);

        holder.binding.txtCreatedby.setText(responseDataModel.ref.author.firstName);
        holder.binding.txtAssignDate.setText(CommonUtils.getOnlyDateFormat(responseDataModel.ref.author.createdDate));

        holder.binding.txtReviewBy.setText(responseDataModel.ref.reviewedBy.firstName);
        holder.binding.txtReviewDate.setText(CommonUtils.getOnlyDateFormat(responseDataModel.ref.reviewedBy.createdDate));

        if(responseDataModel.type.equals("U")){
            holder.binding.txtContent.setVisibility(View.GONE);
            holder.binding.imageView.setVisibility(View.VISIBLE);
            holder.binding.imageView.setImageResource(R.drawable.video_iv);
            holder.binding.imageView.setBackgroundTintList( ColorStateList.valueOf(Color.GREEN));
            if(responseDataModel.linkType.equals("Utube")){
            }else if(responseDataModel.linkType.equals("Internal")){
                String urls= BuildConfig.BASE_URL+responseDataModel.content;
                ImageUtil.addThumbFromUrl(mContext,holder.binding.imageView,urls,holder);

            }else {
                try {
                    if(responseDataModel.content!=null && !responseDataModel.content.isEmpty())
                    ImageUtil.addThumbFromUrl(mContext,holder.binding.imageView,responseDataModel.content,holder);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }


        }else if(responseDataModel.type.equals("D")){
            String url="";
            if(responseDataModel.document==null && responseDataModel.content==null){
                return;
            }
            url=responseDataModel.document==null?responseDataModel.content:responseDataModel.document;
             url= ApiClient.BASE_URL+url.replaceFirst("/","");
            remotePDFViewPager =
                    new RemotePDFViewPager(mContext, url, new DownloadFile.Listener() {
                        @Override
                        public void onSuccess(String url, String destinationPath) {
                            if(url==null){
                                return;
                            }
                            PDFPagerAdapter adapter = new PDFPagerAdapter(mContext, FileUtil.extractFileNameFromURL(url));
                            remotePDFViewPager.setAdapter(adapter);

                            holder.binding.pdfViewPager2.setVisibility(View.VISIBLE);
                            holder.binding.pdfViewPager2.removeAllViewsInLayout();
                            holder.binding.pdfViewPager2.removeAllViews();
                            holder.binding.pdfViewPager2.addView(remotePDFViewPager,
                                    ConstraintLayout.LayoutParams.MATCH_PARENT, (int) mContext.getResources().getDimension(com.intuit.sdp.R.dimen._300sdp));
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.d("","");
                        }

                        @Override
                        public void onProgressUpdate(int progress, int total) {

                        }
                    });
        }else if(responseDataModel.type.equals("P")){
            holder.binding.txtContent.setVisibility(View.GONE);
            WebSettings ws = holder.binding.webView1.getSettings();
            ws.setJavaScriptEnabled(true);
            ws.setBuiltInZoomControls(true);
            ws.setDisplayZoomControls(false);
            holder.binding.progressBar.setVisibility(View.VISIBLE);
            holder.binding.webView1.setVisibility(View.GONE);
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                WebView.enableSlowWholeDocumentDraw();
//            }

            holder.binding.webView1.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    if (holder.binding.progressBar.getVisibility()!=View.GONE) {
                        holder.binding.progressBar.setVisibility(View.GONE);
                    }
                    holder.binding.webView1.setVisibility(View.VISIBLE);
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(mContext, "Error:" + description, Toast.LENGTH_SHORT).show();

                }

            });
            holder.binding.webView1.loadDataWithBaseURL("",responseDataModel.content, "text/html", "utf-8","");
        }else if(responseDataModel.type.equals("V")){
            if(responseDataModel.linkType.equals("Text")){

            }else {
                holder.binding.txtContent.setVisibility(View.GONE);
                holder.binding.imageView.setVisibility(View.VISIBLE);
                holder.binding.imageView.setImageResource(R.drawable.audio_wave);
                holder.binding.imageView.setBackgroundTintList( ColorStateList.valueOf(Color.GREEN));
//                if (responseDataModel.getLinkType().equals("Internal")) {
//                    String urls = BuildConfig.BASE_URL + responseDataModel.getContent();
//                    holder.binding.pvMainAudio.setVisibility(View.VISIBLE);
//                    exoVideoPlayer(holder.binding.pvMain.getContext(), holder.binding.pvMain, holder.binding.pvMainAudio, urls, false);
//                } else {
//                    holder.binding.pvMainAudio.setVisibility(View.VISIBLE);
//                    exoVideoPlayer(holder.binding.pvMain.getContext(), holder.binding.pvMain, holder.binding.pvMainAudio, responseDataModel.getContent(), false);
//                }
            }
        }else {

        }
        holder.itemView.setTag(position);
        holder.itemView.setTag(R.string.details,responseDataModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick((Integer) v.getTag(),"");
                int p= (int) v.getTag();
                Bundle bundle=new Bundle();
                bundle.putString("contentId",modelList.get(p).id);
                ContentModel model= (ContentModel) v.getTag(R.string.details);
                String res=CommonUtils.pojoToJson(model);
                bundle.putString("model", res);
                bundle.putBoolean("fromView", true);
                FragmentManager manager = ((AppCompatActivity) mContext).getSupportFragmentManager();
//                Navigation.findNavController(v).navigate(R.id.action_queryTabFragment_to_queryDetailPrintFragment,bundle);

//                CustomFragmentManager.replaceFragment(manager, ViewSingleContentFragment.newInstance(bundle), true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    static class MViewholder extends RecyclerView.ViewHolder {
        UserContentCardItemBinding binding;
        public MViewholder(@NonNull UserContentCardItemBinding itemView) {
            super(itemView.getRoot());
            this.binding=itemView;
        }
    }
    private RemotePDFViewPager remotePDFViewPager;
//    private void exoVideoPlayer(Context context, PlayerView playerView, PlayerControlView playerControlView, String CONTENT_URL, boolean isVideo){
//        stopPlayer();
//        TrackSelector trackSelectorDef = new DefaultTrackSelector();
//        absPlayerInternal = ExoPlayerFactory.newSimpleInstance(context, trackSelectorDef); //creating a player instance
//
//        String userAgent = Util.getUserAgent(context, context.getString(R.string.app_name));
//        DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(context,userAgent);
//        Uri uriOfContentUrl = Uri.parse(CONTENT_URL);
//        MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);  // creating a media source
//
//        absPlayerInternal.prepare(mediaSource);
//        absPlayerInternal.addListener(new Player.EventListener() {
//            @Override
//            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                Player.EventListener.super.onPlayerStateChanged(playWhenReady, playbackState);
//                switch(playbackState) {
//                    case ExoPlayer.STATE_BUFFERING:
//                        break;
//                    case ExoPlayer.STATE_ENDED:
//                        //do what you want
//                        break;
//                    case ExoPlayer.STATE_IDLE:
//                        break;
//                    case ExoPlayer.STATE_READY:
////                        binding.progressBar.setVisibility(View.GONE);
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//
//        });
//        absPlayerInternal.setPlayWhenReady(false); // start loading video and play it at the moment a chunk of it is available offline
//        if(isVideo) {
//            playerView.setPlayer(absPlayerInternal);
//        }else {
//            playerControlView.setPlayer(absPlayerInternal);
//        }
//    }
//    private void stopPlayer(){
//        if(absPlayerInternal!=null) {
////            binding.pvMain.setPlayer(null);
//            absPlayerInternal.release();
//            absPlayerInternal = null;
//        }
//    }
    private void setupNetwork() {
        mInterFace = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode) {
//                showDialog(getActivity(), errorCode, false, true, 0);
                Toast.makeText(mContext,errorCode,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void isSuccess(Object response, int ServiceCode) {
                if(ServiceCode== AppConstants.APPROVED || ServiceCode== AppConstants.REJECTED || ServiceCode== AppConstants.DELETE) {
                    JsonObject jsonObject= (JsonObject) response;
                    RootOneModel rootOneModel= (RootOneModel) CommonUtils.getPojoFromStr(RootOneModel.class,jsonObject.toString());
                    if(rootOneModel.getResponse().getStatusCode()==200){
                        CommonUtils.makeToast(mContext,"Operation Successful");
                        itemClickListener.onItemClick(-1,"1");
                    }
                }
            }
        };
        mApiManager = new ApiManager(mContext, mInterFace);
    }
}
