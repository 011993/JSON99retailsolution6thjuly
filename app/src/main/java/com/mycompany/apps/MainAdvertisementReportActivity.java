package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import Adapter.StoreMainAdapter;
import Pojo.StoreMainModel;


public class MainAdvertisementReportActivity extends Activity {
    public Spinner spinner;
    private ListView listView;
    ArrayList<StoreMainModel> arraylist;
   StoreMainModel storeMainModel;
    DBhelper helper;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_main);
        String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        DBhelper helper=new DBhelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        final ArrayList arrayList = helper.getStoreMainData();
        Log.e("%%%%%%%%%%%%%%%%%%%", arrayList.toString());
        listView = (ListView) findViewById(R.id.lv_storemain);
        Log.e("***********Lt1*******", listView.toString());
        StoreMainAdapter storeMainAdapter = new StoreMainAdapter(this, arrayList);
        listView.setAdapter(storeMainAdapter);
        Log.e("***********lt2*******", listView.toString());
        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
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
        if (id == R.id.action_settings) {

            Intent i=new Intent(MainAdvertisementReportActivity.this,MediaReportActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
