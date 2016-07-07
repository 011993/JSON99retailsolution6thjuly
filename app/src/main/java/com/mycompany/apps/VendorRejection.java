package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.godbtech.sync.GBrowserNotifyMessageEvt;
import com.godbtech.sync.GNativeSync;
import com.godbtech.sync.GSyncServerConfig;
import com.godbtech.sync.GSyncStatusEvt;
import com.godbtech.sync.GSyncable;
import com.godbtech.sync.GUtils;

import Adapter.VendorRjectAdapter;
import Pojo.VendorRejectModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class VendorRejection extends Activity   {


    ActionBar actionBar;
    Spinner mreasone;
    DBhelper mydb;
    String SpinValue;
    EditText mVReasonid,mVReason;
    String Store_Id_To_Update;
    TextView mTextView1;

    Button clrbtn, update;
    ListView listView;
    ArrayList<VendorRejectModel>reasonarraylist;

    ArrayAdapter<String> adapteractiveType;
    VendorRjectAdapter adapter;
    String   item;


    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean    syncInProgress = false;
    private boolean    didSyncSucceed = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_rejection);





        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setIcon(R.drawable.w);

        //TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf


        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);

        mydb = new DBhelper(this);
        //listView=(ListView) findViewById(R.id.VlistView) ;
        mreasone = (Spinner) findViewById(R.id.vendor_reason);
        update = (Button) findViewById(R.id.addtolist_vbutton);
        clrbtn = (Button) findViewById(R.id.clearvendrorejection);
        mVReason = (EditText) findViewById(R.id.mRejectreason);
        mTextView1=(TextView) findViewById(R.id.vendor_reject_text1);


        //
        //  adapteractiveType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);


        final DBhelper mydb = new DBhelper(VendorRejection.this);
        ArrayList<String> reasonReturn = mydb.getReasonofReject();
        ArrayAdapter<String> stringArrayAdapter =
                new ArrayAdapter<String>(VendorRejection.this, android.R.layout.simple_spinner_dropdown_item, reasonReturn);
        mreasone.setAdapter(stringArrayAdapter);
        mreasone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                //SpinValue = mreasone.getSelectedItem().toString();
                String   item = mreasone.getSelectedItem().toString();

                //String item = holdbill.getSelectedItem().toString();
                if (item.matches("")) {
                    return;
                }
                reasonarraylist= mydb.getVendorRejection(item);
                if (  reasonarraylist != null &&   reasonarraylist.size() > 0) {
                    if (adapter == null) {

                        Log.d("vendorrejection", "Product arraylist size is " + reasonarraylist.size());
                        //  adapter = new VendorRjectAdapter(VendorRejection.this, android.R.layout.simple_dropdown_item_1line, reasonarraylist);
                        //listView.setAdapter(adapter);


                    }
                    else if (adapter != null)
                    {
                        //  adapter = new VendorRjectAdapter(VendorRejection.this, android.R.layout.simple_dropdown_item_1line, reasonarraylist);
                        // listView.setAdapter(adapter);
                        //mreasone.setAdapter(null);


                    }


//                    for (VendorRejectionModel prod: reasonarraylist)
//                    {
//                        adapter.addProductToList(prod);
//                    }
//                        listView.setAdapter(adapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }

        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);

                //mVReasonid = (EditText) findViewById(R.id.mRejectid);


                if (mVReason.getText().toString().matches("")) {
                    mVReason.setError("Please Enter Reason");

                    return;
                }

                try {



                    mydb.updateReason(Store_Id_To_Update,mVReason.getText().toString());


                  /*  Long Value = System.currentTimeMillis();
                    String resval = Long.toString(Value);

                   mydb.updateReason(adapter.getList());*/
                    Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                    startActivity(intent);

                    // loadSyncLibrary();
                    //  doSync(syncDataBundle);

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }

        });

/*
        Button Exit = (Button) findViewById(R.id.mVexit_button);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);
                Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                startActivity(intent);
            }
        });*/


        clrbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                try {

                    mVReason.setText("");

                }catch (NullPointerException ex) {
                    ex.printStackTrace();
                }


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_vendor, menu);


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


            Intent i=new Intent(VendorRejection.this,Activity_masterScreen1.class);
            startActivity(i);
            return true;


        }

        return super.onOptionsItemSelected(item);
    }




}
