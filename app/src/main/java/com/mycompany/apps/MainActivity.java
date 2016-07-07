package com.mycompany.apps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.godbtech.sync.GBrowserNotifyMessageEvt;
import com.godbtech.sync.GNativeSync;
import com.godbtech.sync.GSyncServerConfig;
import com.godbtech.sync.GSyncStatusEvt;
import com.godbtech.sync.GSyncable;
import com.godbtech.sync.GUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/*
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
*/


public class MainActivity extends Activity{


    //the following are application only fot secure SQLITE GoDB Sync
    public static final String DB_ENC = "rc4";//should not be made available in source code
    public static final String DB_KEY = "mykey";//should not be made available in source code
    //RC4,AES128-OFB,AES258-OFB are currently Supported algorithms

    Bundle syncDataBundle = null;
    Button syncbutton1;

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications


    VideoView videoView;
    TextView textView_scrolling;
    ImageView imageView;
    String Ad_Play, Store_id, Store_Media_Id, startdate, enddate, starttime, endtime, Str_store_id, Str_store_media_id;
    int videoIncrement, imageIncrement = 0;
    int i = 0;
    int j = 0;
    ArrayList<Uri> main_ad_video = new ArrayList<Uri>();
    ArrayList<Uri> cont_sen_ad_video_1 = new ArrayList<Uri>();
    ArrayList<Uri> cont_sen_ad_video_2 = new ArrayList<Uri>();
    SimpleDateFormat timeFormat;
    SimpleDateFormat Addatetime;
    private LinearLayout horizontalOuterLayout;
    private HorizontalScrollView horizontalScrollview;
    private int scrollMax;
    private int scrollPos = 0;
    private TimerTask clickSchedule;
    private TimerTask scrollerSchedule;
    private TimerTask faceAnimationSchedule;
    private Timer scrollTimer = null;
    private Timer clickTimer = null;
    private Timer faceTimer = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
   // private GoogleApiClient client;
ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(new yourAdapter(this, new String[]{"data1",
                "data2"}));

        //call funtion, create video and image folder in external storage of POS device
        creatFoldervideo1();
        creatFoldervideo2();
        creatFoldervideo3();
        creatFolderimage();



        horizontalScrollview = (HorizontalScrollView) findViewById(R.id.horiztonal_scrollview_id);
        horizontalOuterLayout = (LinearLayout) findViewById(R.id.horiztonal_outer_layout_id);
        horizontalScrollview.setHorizontalScrollBarEnabled(false);
        ViewTreeObserver viewTreeObserver = horizontalOuterLayout.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                horizontalOuterLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                getScrollMaxAmount();
                startAutoScrolling();
                LockScroll();

            }
        });


        Video_DataBase_Helper databasehandler = new Video_DataBase_Helper(getApplicationContext());

       Admainvideo();

       // Adcontsenvideo1();

       // Adcontsenvideo2();





        //Blinking Image code Here.....................................
        imageView = (ImageView) findViewById(R.id.blinking_image_id);
        File imagedir = new File(Environment.getExternalStorageDirectory() + "/Retail_Street_Image");
        Log.e("####******########", imagedir.toString());
        imagedir.mkdir();
        File imageList[] = imagedir.listFiles();
        Bitmap b = BitmapFactory.decodeFile(imageList[imageIncrement].getAbsolutePath());
        imageView.setImageBitmap(b);
        imageView = (ImageView) findViewById(R.id.blinking_image_id);


        //all retail_ad_ticker call here and set here......................
        final ArrayList array_list = databasehandler.getAllAdTicker();
        Log.e("###############", array_list.toString().replace("[", "").replace("]", "").replace(",", "----"));
        textView_scrolling = (TextView) findViewById(R.id.scrolling_text_id);
        textView_scrolling.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView_scrolling.setSelected(true);
        textView_scrolling.setSingleLine(true);
        textView_scrolling.setText(array_list.toString().replace("[", "").replace("]", "").replace(",", "."));

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();



    }//onCreate end here.......................

    // uploading function define here.........and call after every video insert
    public void loaddatamainactivity(){

        syncbutton1 = (Button) findViewById(R.id.MainActivitySync);
        syncbutton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });


        Toast.makeText(this, " uploading....", Toast.LENGTH_LONG).show();
        syncbutton1.performClick();
        Log.e("EEEEEEE", syncbutton1.toString());


    }

    //create video and image folder in external storage of POS device(Main ad video)
    public void creatFoldervideo1(){

        //  main_ad_video folder Name Specify Here
        File videodir = new File(Environment.getExternalStorageDirectory() + "/Retail_Street_Video");
        Log.e("####******########", videodir.toString());
        final File[] filest = videodir.listFiles();
        if (videodir.exists() && videodir.isDirectory()) {
            final File[] files = videodir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file != null) {
                        if (file.isDirectory()) {  // it is a folder...
                        } else {  // it is a file...
                            main_ad_video.add(Uri.fromFile(file));
                        }
                    }
                }
            }

        } else {
            videodir.mkdir();
        }


    }

    //create video  folder in external storage of POS device(contact sensitive ad video1)
    public void creatFoldervideo2(){

        File videodir2 = new File(Environment.getExternalStorageDirectory() + "/Retail_Street_Video2");
        Log.e("####******########", videodir2.toString());
        final File[] filest2 = videodir2.listFiles();
        if (videodir2.exists() && videodir2.isDirectory()) {
            final File[] files = videodir2.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file != null) {
                        if (file.isDirectory()) {  // it is a folder...
                        } else {  // it is a file...
                            cont_sen_ad_video_1.add(Uri.fromFile(file));
                        }
                    }
                }
            }

        } else {
            videodir2.mkdir();
        }



    }

    //create video folder in external storage of POS device(contact sensitive ad video2)
    public void creatFoldervideo3(){
        File videodir3 = new File(Environment.getExternalStorageDirectory() + "/Retail_Street_Video3");
        Log.e("####******########", videodir3.toString());
        final File[] filest3 = videodir3.listFiles();
        if (videodir3.exists() && videodir3.isDirectory()) {
            final File[] files = videodir3.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file != null) {
                        if (file.isDirectory()) {  // it is a folder...
                        } else {  // it is a file...
                            cont_sen_ad_video_2.add(Uri.fromFile(file));
                        }
                    }
                }
            }

        } else {
            videodir3.mkdir();
        }


    }


    //create image folder in external storage of POS device(company logo)
    public void creatFolderimage(){

        File imagedir = new File(Environment.getExternalStorageDirectory() + "/Retail_Street_Image");
        Log.e("####******########", imagedir.toString());
        imagedir.mkdir();
    }



    //inseet main ad video details into data base here................
    public void Admainvideo(){
        Video_DataBase_Helper databasehandler = new Video_DataBase_Helper(getApplicationContext());

        ArrayList<Video_Data> videodata = new ArrayList<Video_Data>();
        videodata = databasehandler.getVideosDetails();
        Log.e("####", "#####size:" + videodata.size());

        Date date = new Date();
        final CharSequence s = DateFormat.format("yyyy-MM-dd ", date.getTime());
        final Calendar c1 = Calendar.getInstance();
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        Addatetime = new SimpleDateFormat("yyyyMMDDHHmmss");

        final List<String> storeidlist = databasehandler.getStoreid();
        Log.e("###############", storeidlist.toString().replace("[", "").replace("]", ""));
        Str_store_id = storeidlist.toString().replace("[", "").replace("]", "");


        final List<String> storemediaid = databasehandler.getMediaid();
        Log.e("###############", storemediaid.toString().replace("[", "").replace("]", ""));
        Str_store_media_id = storemediaid.toString().replace("[", "").replace("]", "");


        // attach videos to videoview xml and video1.............
        videoView = (VideoView) findViewById(R.id.videoView1);
        videoView.setVideoURI(main_ad_video.get(videoIncrement));
        videoView.start();
        startdate = s.toString();
        starttime = timeFormat.format(c1.getTime());
        Ad_Play = Addatetime.format(c1.getTime());
        Store_id = Str_store_id;
        Store_Media_Id = Str_store_media_id;

        // call setOnPreparedListener for set default mute option........
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.setVolume(0, 0);
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Calendar c2 = Calendar.getInstance();
                enddate = s.toString();
                Ad_Play = Addatetime.format(c2.getTime());
                endtime = timeFormat.format(c2.getTime());
                Video_DataBase_Helper databasehandler = new Video_DataBase_Helper(getApplicationContext());
                databasehandler.getWritableDatabase();

                //video details insert from here...
                databasehandler.insertVideoData(Ad_Play, Store_id, Store_Media_Id, main_ad_video.get(videoIncrement).toString(), startdate, enddate, starttime, endtime);

                // uploading function call here
                loaddatamainactivity();


                videoIncrement++;
                if (videoIncrement < main_ad_video.size()) {
                    videoView = (VideoView) findViewById(R.id.videoView1);
                    videoView.setVideoURI(main_ad_video.get(videoIncrement));
                    videoView.start();
                    Calendar c3 = Calendar.getInstance();
                    startdate = s.toString();
                    Ad_Play = Addatetime.format(c3.getTime());
                    starttime = timeFormat.format(c3.getTime());
                    enddate = s.toString();
                    endtime = timeFormat.format(c3.getTime());


                } else {
                    videoIncrement = 0;
                    videoView = (VideoView) findViewById(R.id.videoView1);
                    videoView.setVideoURI(main_ad_video.get(videoIncrement));
                    videoView.start();
                    Calendar c4 = Calendar.getInstance();
                    startdate = s.toString();
                    Ad_Play = Addatetime.format(c4.getTime());
                    starttime = timeFormat.format(c4.getTime());
                    enddate = s.toString();
                    endtime = timeFormat.format(c4.getTime());
                }
            }
        });




    }




   /* public void getScrollMaxAmount() {
        int actualWidth = (horizontalOuterLayout.getMeasuredWidth() - 800);
        scrollMax = actualWidth;
    }*/


    public void getScrollMaxAmount() {
        int actualWidth = (horizontalOuterLayout.getMeasuredWidth() - 1024);
        scrollMax = actualWidth;
    }

    /*for Locking Scroll upper side company images*/
    public void LockScroll() {
        ((LockableScrollView) findViewById(R.id.horiztonal_scrollview_id)).setScrollingEnabled(false);
    }


    public void startAutoScrolling() {
        if (scrollTimer == null) {
            scrollTimer = new Timer();
            final Runnable Timer_Tick = new Runnable() {
                public void run() {
                    moveScrollView();
                }
            };

            if (scrollerSchedule != null) {
                scrollerSchedule.cancel();
                scrollerSchedule = null;
            }
            scrollerSchedule = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(Timer_Tick);
                }
            };

            scrollTimer.schedule(scrollerSchedule, 10, 10);
        }
    }

    public void moveScrollView() {
        scrollPos = (int) (horizontalScrollview.getScrollX() + 1.0);
        if (scrollPos >= scrollMax) {
            scrollPos = 0;
        }
        horizontalScrollview.scrollTo(scrollPos, 0);

    }


    public void stopAutoScrolling() {
        if (scrollTimer != null) {
            scrollTimer.cancel();
            scrollTimer = null;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onPause() {
        super.onPause();
        finish();
    }

    public void onDestroy() {
        clearTimerTaks(clickSchedule);
        clearTimerTaks(scrollerSchedule);
        clearTimerTaks(faceAnimationSchedule);
        clearTimers(scrollTimer);
        clearTimers(clickTimer);
        clearTimers(faceTimer);

        clickSchedule = null;
        scrollerSchedule = null;
        faceAnimationSchedule = null;
        scrollTimer = null;
        clickTimer = null;
        faceTimer = null;
        super.onDestroy();
    }

    private void clearTimers(Timer timer) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void clearTimerTaks(TimerTask timerTask) {
        if (timerTask != null) {
            timerTask.cancel();

        }
    }




    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            // Close every kind of system dialog
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


  /*  @Override
    protected void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.retail.solutions.pvt.Ltd/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }
*/

    }