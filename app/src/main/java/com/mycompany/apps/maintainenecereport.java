package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import Adapter.MaintenanceAdapter;
import Pojo.ShowModel;

/****************Show the data maintenance *************/
public class maintainenecereport extends Activity {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    DBhelper helper;
    ShowModel showModel;
    ActionBar actionBar;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
    Bundle syncDataBundle = null;
    private boolean syncInProgress = false;
    private boolean didSyncSucceed = false;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        DBhelper helper=new DBhelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        final ArrayList arrayList = helper.getAlldata();
        Collections.reverse(arrayList);
        Log.e("%%%%%%%%%%%%%%%%%%%", arrayList.toString());
        obj = (ListView) findViewById(R.id.listView);
        Log.e("***********Lt1*******", obj.toString());
        MaintenanceAdapter maintenanceAdapter = new MaintenanceAdapter(this, arrayList);
        obj.setAdapter(maintenanceAdapter);
        Log.e("***********lt2*******", obj.toString());

       /* String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        GDBHelper helper = new GDBHelper(this, dbName);
        SQLiteDatabase db = helper.getWritableDatabase();

        helper = new GDBHelper(this,dbName);
        final ArrayList array_list = helper.getAlldata();
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

        Log.e("*****************", array_list.toString());
        obj = (ListView)findViewById(R.id.listView);
        Log.e("*********",obj.toString());
        obj.setAdapter(arrayAdapter);
        Log.e("*********",arrayAdapter.toString());*/

       /* obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                String id_To_Search = array_list.get(arg2);

                //String id_To_Search=
                Bundle dataBundle = new Bundle();
                dataBundle.putString("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(), ShowfulldataActivity.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Intent i=new Intent(maintainenecereport.this,ActivityMainMaintainence.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






}
