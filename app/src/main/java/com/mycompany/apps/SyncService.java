/*
package com.mycompany.apps;



import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.godbtech.sync.GBrowserNotifyMessageEvt;
import com.godbtech.sync.GNativeSync;
import com.godbtech.sync.GSyncServerConfig;
import com.godbtech.sync.GSyncStatusEvt;
import com.godbtech.sync.GSyncable;
import com.godbtech.sync.GUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SyncService extends Service  {

    private static final String TAG = SyncService.class.getSimpleName();
    Intent syncIntent;


    GoDBSyncAlarmManager goDBSyncAlarmManager = new GoDBSyncAlarmManager();

    EditText statusEditText = null;
    private boolean    syncInProgress = false;
    private boolean    didSyncSucceed = false;


    //private GSyncStatusHandler gSyncStatusHandler = new GSyncStatusHandler();
    //private GBrowserNotifyHandler gBrowserNotifyHandler = new GBrowserNotifyHandler();
    // private GSyncResultHandler gSyncResultNotifyHandler = new GSyncResultHandler();
    // private GSyncItemStatusHandler gSyncItemStatusHandler = new GSyncItemStatusHandler();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if( null == intent )
        {
            Log.d( "GoDBSyncService", "This is a System Restart");
            */
/* restart the service with startService().
             * This will ensure that the delay between system force-stop and restart
             * is always 5 seconds
             *//*

            startService(new Intent(getBaseContext(), SyncService.class));

        }

        */
/*Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();*//*

        loadSyncLibrary();
        syncIntent=new Intent();
       // syncIntent.putExtras(getSyncBudle());
        syncInProgress = true;
        didSyncSucceed = false;
        doSync(syncIntent);
        goDBSyncAlarmManager.setAlarm(this);
        Log.e("^^^^^^^1^^^^^^", goDBSyncAlarmManager.toString());
        return START_STICKY;
    }




    @Override
    public void onDestroy() {
        Log.d("GoDBSyncService", "In onDestroy");
        goDBSyncAlarmManager.cancelAlarm(this);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    */
/**
     * Load sync library.
     *//*

    private void loadSyncLibrary()
    {
        try
        {
            String trgLib = "gSyncDLL";
            System.loadLibrary(trgLib);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.e("GSync", e.getMessage());
        }
    }


//    public class GoDBSyncAlarmManager extends BroadcastReceiver
//
//    {
//
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Log.d( TAG, "ALarm fired" );
//            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//            PowerManager.WakeLock wirellock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
//            wirellock.acquire();
//            doSync(syncIntent);
//            wirellock.release();
//
//        }
//        public void setAlarm(Context context)
//        {
//            Log.d(TAG, "Setting Alarm" );
//            AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//            Intent intent = new Intent(context, GoDBSyncAlarmManager.class);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//            doSync(syncIntent);
//            am.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), 10000, pendingIntent); // Millisec * Second * Minute
//
//        }
//
//        public void cancelAlarm(Context context)
//        {
//            Intent intent = new Intent(context, GoDBSyncAlarmManager.class);
//            PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
//            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            alarmManager.cancel(sender);
//        }
//
//
//    }
//





    protected GSyncServerConfig getSyncConfigFromSyncIntent(Intent syncIntent)
    {
        GSyncServerConfig gCfg = new GSyncServerConfig();
//        doSync(syncIntent);
        gCfg.setSyncServerAddress(syncIntent.getStringExtra("syncServerIP"));// set SyncServer Address, can be IP
        gCfg.setSyncServerPort(syncIntent.getIntExtra("syncServerPort", 80));// set SyncServer Port
        gCfg.setSyncServerBasePath(syncIntent.getStringExtra("syncServerBasePath"));// set Base path
        gCfg.setSyncServerUsername(syncIntent.getStringExtra("syncServerUsername"));
        gCfg.setSyncServerPassword(syncIntent.getStringExtra("syncServerPassword"));
        gCfg.setSyncServerUserID(syncIntent.getStringExtra("syncServerUserID"));
        gCfg.setLogEnabled(true);//enable logging
        gCfg.setDBName(syncIntent.getStringExtra("dbName"));//db name
        gCfg.setSyncMode(syncIntent.getIntExtra("syncMode", 1));// set Synchronization to full sync if missing
        gCfg.setUseProxy(syncIntent.getBooleanExtra("proxyEnabled", false));
        gCfg.setHttpProxy(syncIntent.getStringExtra("httpProxy"));
        gCfg.setHttpProxyPort(syncIntent.getIntExtra("httpProxyPort", 8080));
        gCfg.setSockConnectTimeoutMillis(syncIntent.getIntExtra("sockConnectTimeoutMillis", 0));
        gCfg.setSockSendTimeoutMillis(syncIntent.getIntExtra("sockSendTimeoutMillis", 0));
        gCfg.setSockRecvTimeoutMillis(syncIntent.getIntExtra("sockRecvTimeoutMillis", 0));
        gCfg.setD4S(syncIntent.getStringExtra("d4s"));
        gCfg.setChunkedTableList(syncIntent.getStringExtra("chunkedTableList"));
        gCfg.setMaxRecChunkSize(syncIntent.getIntExtra("maxRecChunkSize", 0));
        //Log.d("GSync", "Syncing with " + gCfg.toString());
        return gCfg;
    }

    private GSyncable mSyncableListener = new GSyncable() {
        @Override
        public void syncStatusEvent(GSyncStatusEvt statEvt) {
            String msg = statEvt.getMsg();
            if (msg == null)
                msg = "";
            Bundle b = new Bundle();
            b.putString("syncstatusmsg", msg);
            b.putInt("status1",statEvt.getTStatus1());
            b.putInt("status2max", statEvt.getStatus2Max());
            b.putInt("status2",statEvt.getStatus2());
            b.putInt("status3max",statEvt.getStatus3Max());
            b.putInt("status3",statEvt.getStatus3());
            Message m = Message.obtain();
            m.setData(b);
            Log.d(TAG, "Sync returned...with status " + statEvt.getTStatus1());
            Log.d( TAG, "Sync returned...with status " + b );

            if(statEvt.getTStatus1() == 100) {

                syncInProgress=false;
                didSyncSucceed=false;
                //m.setTarget(gSyncResultNoti);

            } else if(statEvt.getTStatus1() == 200) {
                didSyncSucceed=true;
                syncInProgress=false;
            }
          */
/*  else
                m.sendToTarget();*//*

        }





        @Override
        public void browserNotifyEvent(GBrowserNotifyMessageEvt gBrowserNotifyMessageEvt) {

        }

        @Override
        public void itemStatusEvent(String s, String s1) {

        }
    };

    public void doSync(Intent syncIntent)
    {
        Log.d( TAG, "Started do Sync");
        GNativeSync gNSync = GNativeSync.getNativeSyncSingleton();
        //doSync(syncIntent);
        gNSync.addSyncListener( mSyncableListener );// important register sync listener to recieve notifications
        GSyncServerConfig gCfg = getSyncConfigFromSyncIntent(syncIntent);
        String rootPath = syncIntent.getStringExtra("dbName");
        if(rootPath==null || rootPath.lastIndexOf("/")<=0) return;
        rootPath = rootPath.substring(0, rootPath.lastIndexOf("/"));
       // extractPEMFile(getAssets(), rootPath);//extracts on every launch, you can change it to extract only if file isnt present.
        clearViews();
        gNSync.startSync(gCfg);
        Log.d(TAG, "Sync Completed");
    }

    private void clearViews()
    {
        // statusEditText.setText("");
    }


    }*/
