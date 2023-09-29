package com.example.testproject.ui.views;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.testproject.R;
import com.github.irshulx.Editor;
import com.github.irshulx.EditorListener;
import com.github.irshulx.models.EditorTextStyle;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SmartEditView extends LinearLayout {

    public SmartEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setUpEditor() {
        Editor editor=findViewById(R.id.ed_content);
        findViewById(com.github.irshulx.R.id.action_h1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.H1);
            }
        });

        findViewById(com.github.irshulx.R.id.action_h2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.H2);
            }
        });

        findViewById(com.github.irshulx.R.id.action_h3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.H3);
            }
        });

        findViewById(com.github.irshulx.R.id.action_bold).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.BOLD);
            }
        });

        findViewById(com.github.irshulx.R.id.action_Italic).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.ITALIC);
            }
        });

        findViewById(com.github.irshulx.R.id.action_indent).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.INDENT);
            }
        });

        findViewById(com.github.irshulx.R.id.action_blockquote).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.BLOCKQUOTE);
            }
        });

        findViewById(com.github.irshulx.R.id.action_outdent).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.OUTDENT);
            }
        });

        findViewById(com.github.irshulx.R.id.action_bulleted).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.insertList(false);
            }
        });

        findViewById(com.github.irshulx.R.id.action_unordered_numbered).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.insertList(true);
            }
        });

        findViewById(com.github.irshulx.R.id.action_hr).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.insertDivider();
            }
        });


        findViewById(com.github.irshulx.R.id.action_color).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                new ColorPickerPopup.Builder(EditorTestActivity.this)
//                        .initialColor(Color.RED) // Set initial color
//                        .enableAlpha(true) // Enable alpha slider or not
//                        .okTitle("Choose")
//                        .cancelTitle("Cancel")
//                        .showIndicator(true)
//                        .showValue(true)
//                        .build()
//                        .show(findViewById(android.com.github.irshulx.R.id.content), new ColorPickerPopup.ColorPickerObserver() {
//                            @Override
//                            public void onColorPicked(int color) {
//                                Toast.makeText(EditorTestActivity.this, "picked" + colorHex(color), Toast.LENGTH_LONG).show();
//                                editor.updateTextColor(colorHex(color));
//                            }
//
//                            @Override
//                            public void onColor(int color, boolean fromUser) {
//
//                            }
//                        });


            }
        });

//        findViewById(com.github.irshulx.R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                editor.openImagePicker();
//            }
//        });

        findViewById(com.github.irshulx.R.id.action_insert_link).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.insertLink();
            }
        });


        findViewById(com.github.irshulx.R.id.action_erase).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clearAllContents();
            }
        });
        //editor.dividerBackground=R.drawable.divider_background_dark;
        //editor.setFontFace(R.string.fontFamily__serif);
        Map<Integer, String> headingTypeface = getHeadingTypeface();
        Map<Integer, String> contentTypeface = getContentface();
        editor.setHeadingTypeface(headingTypeface);
        editor.setContentTypeface(contentTypeface);
        editor.setDividerLayout(com.github.irshulx.R.layout.tmpl_divider_layout);
        editor.setEditorImageLayout(com.github.irshulx.R.layout.tmpl_image_view);
        editor.setListItemLayout(com.github.irshulx.R.layout.tmpl_list_item);
        //editor.setNormalTextSize(10);
        // editor.setEditorTextColor("#FF3333");
        //editor.StartEditor();
        editor.setEditorListener(new EditorListener() {
            @Override
            public void onTextChanged(EditText editText, Editable text) {
                // Toast.makeText(EditorTestActivity.this, text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpload(Bitmap image, String uuid) {
                Toast.makeText(getContext(), uuid, Toast.LENGTH_LONG).show();
                /**
                 * TODO do your upload here from the bitmap received and all onImageUploadComplete(String url); to insert the result url to
                 * let the editor know the upload has completed
                 */
                if(image==null){
                    return;
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 70, baos); // bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encodedImage = "data:image/png;base64,"+Base64.encodeToString(b, Base64.DEFAULT);
                editor.onImageUploadComplete(encodedImage, uuid);
                // editor.onImageUploadFailed(uuid);
            }

            @Override
            public View onRenderMacro(String name, Map<String, Object> props, int index) {
//                View view = getLayoutInflater().inflate(R.layout.layout_authored_by, null);
                return null;
            }

        });


        /**
         * rendering serialized content
         // */
        //  String serialized = "{\"nodes\":[{\"content\":[\"\\u003cp dir\\u003d\\\"ltr\\\"\\u003etextline 1 a great time and I will branch office is closed on Sundays\\u003c/p\\u003e\\n\"],\"contentStyles\":[\"H1\"],\"textSettings\":{\"textColor\":\"#c00000\"},\"type\":\"INPUT\"},{\"content\":[],\"type\":\"hr\"},{\"content\":[\"\\u003cp dir\\u003d\\\"ltr\\\"\\u003ethe only one that you have received the stream free and open minded person to discuss a business opportunity to discuss my background.\\u003c/p\\u003e\\n\"],\"contentStyles\":[],\"textSettings\":{\"textColor\":\"#000000\"},\"type\":\"INPUT\"},{\"childs\":[{\"content\":[\"it is a great weekend and we will have the same to me that the same a great time\"],\"contentStyles\":[\"BOLD\"],\"textSettings\":{\"textColor\":\"#FF0000\"},\"type\":\"IMG_SUB\"}],\"content\":[\"http://www.videogamesblogger.com/wp-content/uploads/2015/08/metal-gear-solid-5-the-phantom-pain-cheats-640x325.jpg\"],\"type\":\"img\"},{\"content\":[\"\\u003cp dir\\u003d\\\"ltr\\\"\\u003eI have a place where I have a great time and I will branch manager state to boast a new job in a few weeks and we can host or domain to get to know.\\u003c/p\\u003e\\n\"],\"contentStyles\":[],\"textSettings\":{\"textColor\":\"#000000\"},\"type\":\"INPUT\"},{\"childs\":[{\"content\":[\"the stream of water in a few weeks and we can host in the stream free and no ippo\"],\"contentStyles\":[],\"textSettings\":{\"textColor\":\"#5E5E5E\"},\"type\":\"IMG_SUB\"}],\"content\":[\"http://www.videogamesblogger.com/wp-content/uploads/2015/08/metal-gear-solid-5-the-phantom-pain-cheats-640x325.jpg\"],\"type\":\"img\"},{\"content\":[\"\\u003cp dir\\u003d\\\"ltr\\\"\\u003eit is that I can get it done today will online at location and I am not a big difference to me so that we are headed \\u003ca href\\u003d\\\"www.google.com\\\"\\u003ewww.google.com\\u003c/a\\u003e it was the only way I.\\u003c/p\\u003e\\n\"],\"contentStyles\":[],\"textSettings\":{\"textColor\":\"#000000\"},\"type\":\"INPUT\"},{\"content\":[\"\\u003cp dir\\u003d\\\"ltr\\\"\\u003eit is not a good day to get the latest version to blame it to the product the.\\u003c/p\\u003e\\n\"],\"contentStyles\":[\"BOLDITALIC\"],\"textSettings\":{\"textColor\":\"#000000\"},\"type\":\"INPUT\"},{\"content\":[\"\\u003cp dir\\u003d\\\"ltr\\\"\\u003eit is that I can send me your email to you and I am not able a great time and consideration I have to do the needful.\\u003c/p\\u003e\\n\"],\"contentStyles\":[\"INDENT\"],\"textSettings\":{\"textColor\":\"#000000\"},\"type\":\"INPUT\"},{\"content\":[\"\\u003cp dir\\u003d\\\"ltr\\\"\\u003eI will be a while ago to a great weekend a great time with the same.\\u003c/p\\u003e\\n\"],\"contentStyles\":[],\"textSettings\":{\"textColor\":\"#000000\"},\"type\":\"INPUT\"}]}";
//        String serialized = "{\"nodes\":[{\"content\":[\"\\u003cp dir\\u003d\\\"ltr\\\"\\u003e\\u003cspan style\\u003d\\\"color:#000000;\\\"\\u003e\\u003cspan style\\u003d\\\"color:#000000;\\\"\\u003eit is not available beyond that statue in a few days and then we could\\u003c/span\\u003e\\u003c/span\\u003e\\u003c/p\\u003e\\n\"],\"contentStyles\":[\"H1\"],\"textSettings\":{\"textColor\":\"#000000\"},\"type\":\"INPUT\"},{\"content\":[],\"type\":\"hr\"},{\"content\":[\"author-tag\"],\"macroSettings\":{\"data-author-name\":\"Alex Wong\",\"data-tag\":\"macro\",\"data-date\":\"12 July 2018\"},\"type\":\"macro\"},{\"content\":[\"\\u003cp dir\\u003d\\\"ltr\\\"\\u003eit is a free trial to get a great weekend a good day to you u can do that for.\\u003c/p\\u003e\\n\"],\"contentStyles\":[],\"textSettings\":{\"textColor\":\"#000000\"},\"type\":\"INPUT\"},{\"content\":[\"\\u003cp dir\\u003d\\\"ltr\\\"\\u003eit is that I have to do the needful as early in life is not available beyond my imagination to be a good.\\u003c/p\\u003e\\n\"],\"contentStyles\":[],\"textSettings\":{\"textColor\":\"#000000\"},\"type\":\"INPUT\"},{\"childs\":[{\"content\":[\"\\u003cp dir\\u003d\\\"ltr\\\"\\u003e\\u003cb\\u003eit is not available in the next week or two and I have a place where I\\u003c/b\\u003e\\u003c/p\\u003e\\n\"],\"contentStyles\":[],\"textSettings\":{\"textColor\":\"#006AFF\"},\"type\":\"IMG_SUB\"}],\"content\":[\"http://www.videogamesblogger.com/wp-content/uploads/2015/08/metal-gear-solid-5-the-phantom-pain-cheats-640x325.jpg\"],\"type\":\"img\"},{\"content\":[\"\\u003cp dir\\u003d\\\"ltr\\\"\\u003eit is not available in the next week to see you tomorrow morning to see you then.\\u003c/p\\u003e\\n\"],\"contentStyles\":[],\"textSettings\":{\"textColor\":\"#000000\"},\"type\":\"INPUT\"},{\"content\":[],\"type\":\"hr\"},{\"content\":[\"\\u003cp dir\\u003d\\\"ltr\\\"\\u003eit is not available in the next day delivery to you soon with it and.\\u003c/p\\u003e\\n\"],\"contentStyles\":[],\"textSettings\":{\"textColor\":\"#000000\"},\"type\":\"INPUT\"}]}";
        // EditorContent des = editor.getContentDeserialized(serialized);
        // editor.render(des);

//        Intent intent = new Intent(getApplicationContext(), RenderTestActivity.class);
//        intent.putExtra("content", serialized);
//        startActivity(intent);


        /**
         * Rendering html
         */
        //render();
        //editor.render();  // this method must be called to start the editor
        String text = "<h1 data-tag=\"input\" style=\"color:#c00000;\"><span style=\"color:#C00000;\">textline 1 a great time and I will branch office is closed on Sundays</span></h1><hr data-tag=\"hr\"/><p data-tag=\"input\" style=\"color:#000000;\">the only one that you have received the stream free and open minded person to discuss a business opportunity to discuss my background.</p><div data-tag=\"img\"><img src=\"http://www.videogamesblogger.com/wp-content/uploads/2015/08/metal-gear-solid-5-the-phantom-pain-cheats-640x325.jpg\" /><p data-tag=\"img-sub\" style=\"color:#FF0000;\" class=\"editor-image-subtitle\"><b>it is a great weekend and we will have the same to me that the same a great time</b></p></div><p data-tag=\"input\" style=\"color:#000000;\">I have a place where I have a great time and I will branch manager state to boast a new job in a few weeks and we can host or domain to get to know.</p><div data-tag=\"img\"><img src=\"http://www.videogamesblogger.com/wp-content/uploads/2015/08/metal-gear-solid-5-the-phantom-pain-cheats-640x325.jpg\" /><p data-tag=\"img-sub\" style=\"color:#5E5E5E;\" class=\"editor-image-subtitle\">the stream of water in a few weeks and we can host in the stream free and no ippo</p></div><p data-tag=\"input\" style=\"color:#000000;\">it is that I can get it done today will online at location and I am not a big difference to me so that we are headed <a href=\"www.google.com\">www.google.com</a> it was the only way I.</p><blockquote data-tag=\"input\" style=\"color:#000000;\">I have to do the negotiation and a half years old story and I am looking forward in a few days.</blockquote><p data-tag=\"input\" style=\"color:#000000;\">it is not a good day to get the latest version to blame it to the product the.</p><ol data-tag=\"ol\"><li data-tag=\"list-item-ol\"><span style=\"color:#000000;\">it is that I can send me your email to you and I am not able a great time and consideration I have to do the needful.</span></li><li data-tag=\"list-item-ol\"><span style=\"color:#000000;\">I have to do the needful and send to me and</span></li><li data-tag=\"list-item-ol\"><span style=\"color:#000000;\">I will be a while ago to a great weekend a great time with the same.</span></li></ol><p data-tag=\"input\" style=\"color:#000000;\">it was u can do to make an offer for a good day I u u have been working with a new job to the stream free and no.</p><p data-tag=\"input\" style=\"color:#000000;\">it was u disgraced our new home in time to get the chance I could not find a good idea for you have a great.</p><p data-tag=\"input\" style=\"color:#000000;\">I have to do a lot to do the same a great time and I have a great.</p><p data-tag=\"input\" style=\"color:#000000;\"></p>";
        //editor.render("<p>Hello man, whats up!</p>");
        //String text = "<p data-tag=\"input\" style=\"color:#000000;\">I have to do the needful and send to me and my husband is in a Apple has to offer a variety is not a.</p><p data-tag=\"input\" style=\"color:#000000;\">I have to go with you will be highly grateful if we can get the latest</p><blockquote data-tag=\"input\" style=\"color:#000000;\">I have to do the negotiation and a half years old story and I am looking forward in a few days.</blockquote><p data-tag=\"input\" style=\"color:#000000;\">I have to do the needful at your to the product and the other to a new job is going well and that the same old stuff and a half day city is the stream and a good idea to get onboard the stream.</p>";
        editor.render(text);
//        findViewById(com.github.irshulx.R.id.btnRender).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /*
//                Retrieve the content as serialized, you could also say getContentAsHTML();
//                */
//                String text = editor.getContentAsSerialized();
//                editor.getContentAsHTML();
////                Intent intent = new Intent(getApplicationContext(), RenderTestActivity.class);
////                intent.putExtra("content", text);
////                startActivity(intent);
//            }
//        });


        /**
         * Since the endusers are typing the content, it's always considered good idea to backup the content every specific interval
         * to be safe.
         *
         private final long backupInterval = 10 * 1000;
         Timer timer = new Timer();
         timer.scheduleAtFixedRate(new TimerTask() {
        @Override public void run() {
        String text = editor.getContentAsSerialized();
        SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        preferences.putString(String.format("backup-{0}",  new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date())), text);
        preferences.apply();
        }
        }, 0, backupInterval);
         */
    }

    public Map<Integer, String> getHeadingTypeface() {
        Map<Integer, String> typefaceMap = new HashMap<>();
        typefaceMap.put(Typeface.NORMAL, "fonts/GreycliffCF-Bold.ttf");
        typefaceMap.put(Typeface.BOLD, "fonts/GreycliffCF-Heavy.ttf");
        typefaceMap.put(Typeface.ITALIC, "fonts/GreycliffCF-Heavy.ttf");
        typefaceMap.put(Typeface.BOLD_ITALIC, "fonts/GreycliffCF-Bold.ttf");
        return typefaceMap;
    }

    public Map<Integer, String> getContentface() {
        Map<Integer, String> typefaceMap = new HashMap<>();
        typefaceMap.put(Typeface.NORMAL, "fonts/Lato-Medium.ttf");
        typefaceMap.put(Typeface.BOLD, "fonts/Lato-Bold.ttf");
        typefaceMap.put(Typeface.ITALIC, "fonts/Lato-MediumItalic.ttf");
        typefaceMap.put(Typeface.BOLD_ITALIC, "fonts/Lato-BoldItalic.ttf");
        return typefaceMap;
    }
}
