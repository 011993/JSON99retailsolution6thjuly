package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.renderscript.Sampler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import java.util.Date;
import java.util.Locale;

import Pojo.Store;


public class ActivityStore extends Activity  {
    ActionBar actionBar;
    TextView Storeid;
    TextView Storename;
    TextView Email;
    EditText Mobile;
    TextView Zip;
    TextView StoreContactName;
    TextView City;
    TextView Address1;
    TextView Country;
    DBhelper mydb;
    Store store;
    TextWatcher mobilep;
    String Store_Id_To_Update;
    private PersistenceManager persistancemanager;
    Activity context = this;
    private String storeId;
    private static String mUserName=null;



    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_store);
        Storeid = (TextView) findViewById(R.id.Store_Storeid);
        Storename = (TextView) findViewById(R.id.Store_Name);
        Email = (TextView) findViewById(R.id.Store_Email);
        Mobile = (EditText) findViewById(R.id.Store_Mobile);
        Zip = (TextView) findViewById(R.id.Store_Zip);
        StoreContactName = (TextView) findViewById(R.id.Store_Contact);
        City = (TextView) findViewById(R.id.Store_city);
        Address1 = (TextView) findViewById(R.id.Store_Address1);
        Country = (TextView) findViewById(R.id.Store_Country);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        persistancemanager = new PersistenceManager();
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);



//        final DBhelper  mydb=new DBhelper(this);
//            ArrayList<String> storelist = mydb.getAllStores();
//            String Store_Id_Search = storelist.get(0);
//            Bundle databundle = new Bundle();
//            databundle.putString("Store_Id", Store_Id_Search);
//            Intent i = getIntent();
//            i.putExtras(databundle);
//            Bundle extras = getIntent().getExtras();
//            if (extras != null) {
//                String Value = extras.getString("Store_Id");
//        Cursor res = mydb.getStore(Value);
//                if(res.moveToFirst())
//                {
//                    do {
//                        String localprodstore = res.getString(res.getColumnIndex(DBhelper.STORE_ID));
//                        Storeid.setText(localprodstore);
//                    }while (res.moveToNext());
//                    }
//                }

        final DBhelper mydb = new DBhelper(ActivityStore.this);
        Store value = mydb.getStoreDetails();
        Storeid.setText(value.getStoreId());
        Storename.setText(value.getStoreName());
        Email.setText(value.getStoreemail());
        Mobile.setText(value.getStoreTele());
        Zip.setText(value.getStorezip());
        City.setText(value.getStorecity());
        Country.setText(value.getStorecountry());
        Address1.setText(value.getStoreAddress());
        StoreContactName.setText(value.getStorecontactname());


        PersistenceManager.saveStoreId(this, value.getStoreId());


        Button Exit = (Button) findViewById(R.id.Store_exit_button);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);
                Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                startActivity(intent);
            }
        });
        mobilep = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String monbile = Mobile.getText().toString();

            }
        };

        Button Update = (Button) findViewById(R.id.Store_update_button);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);
                Mobile = (EditText) findViewById(R.id.Store_Mobile);
                Storeid = (TextView) findViewById(R.id.Store_Storeid);
                String Value = Storeid.getText().toString();
                    Store_Id_To_Update = Value;

                if (Mobile.getText().toString().length() >10||Mobile.getText().toString().length()!=10)
                {
                   // Toast.makeText(getApplicationContext(), "INVALID NUMBER", Toast.LENGTH_SHORT).show();
                    Mobile.setError("Please enter 10 digit mobile number");
                    return;
                }
                mydb.updateStore(Store_Id_To_Update, Mobile.getText().toString());

                    Toast.makeText(getApplicationContext(), "Store Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
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

            Intent i=new Intent(ActivityStore.this,Activity_masterScreen2.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


   }