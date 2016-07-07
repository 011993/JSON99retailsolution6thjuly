package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Pojo.DayOpenClose;


public class ActivityDayOpen extends Activity  {
    ActionBar actionBar;
    TextView BusinessDate;
   TextView Startdate;
    EditText StartCash,Store;
    Button btn;
    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;

    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_day_open);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        BusinessDate=(TextView)findViewById(R.id.posdate);
        Startdate=(TextView)findViewById(R.id.startdate);
        StartCash=(EditText)findViewById(R.id.startcash);
        Store = (EditText)findViewById(R.id.storeid);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        final Date date = new Date();
        final SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd ", Locale.getDefault());
        BusinessDate.setText(dateFormat.format(date));
        Startdate.setText(dateFormat.format(date));

        btn=(Button)findViewById(R.id.opencash_button);
        final DBhelper mydb = new DBhelper(this);
        Cursor res = mydb.getStoreDay();
        if(res.moveToFirst())
        {
            do{
                String store = res.getString(res.getColumnIndex(DBhelper.STORE_ID));
                Store.setText(store);

            }while (res.moveToNext());
        }


            btn.setOnClickListener( new View.OnClickListener(){
                @Override
            public void onClick(View v) {
                BusinessDate=(TextView)findViewById(R.id.posdate);
                Startdate=(TextView)findViewById(R.id.startdate);
                StartCash=(EditText)findViewById(R.id.startcash);
                    Store = (EditText)findViewById(R.id.storeid);
                DBhelper db = new DBhelper(ActivityDayOpen.this);
                    if(StartCash.getText().toString().matches(""))
                    {
                        StartCash.setError("Please enter the cash");
                        return;

                    }

                    if(db. CheckDateAlreadyInDBorNot(StartCash.getText().toString()))


                    {
                        Toast toast1 = Toast.makeText(ActivityDayOpen.this, "PLEASE CLOSE THE DAY FIRST",Toast.LENGTH_SHORT);
                        toast1.show();
                        return;
                    }
                    if(db.CheckCashInHandAlreadyInDBorNot(StartCash.getText().toString()))
                    {

                    }

                        if  (db.insertDayopen(Store.getText().toString(),StartCash.getText().toString()));
                Toast toast1 = Toast.makeText(ActivityDayOpen.this, "DAY IS OPENED", Toast.LENGTH_SHORT);
                toast1.show();
                Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                startActivity(intent);

            }
        });


        Button Exit = (Button)findViewById(R.id.day_close_button);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                startActivity(intent);
            }
        });




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

            Intent i=new Intent(ActivityDayOpen.this,ActivitySales.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    }