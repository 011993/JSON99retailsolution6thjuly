package com.mycompany.apps;



import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Activity_Media extends FragmentActivity implements View.OnClickListener{


    Button  Sbtbtn, Canclbtn;
    EditText EditStart, EditEnd, AdText;
    String TickerDescAD;
    String Store_Id, AdMainId, Status;
    SimpleDateFormat admainid;

    ActionBar actionBar;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));


    final Calendar c = Calendar.getInstance();
    int Year = c.get(Calendar.YEAR);
    int Month = c.get(Calendar.MONTH)+1;
    int Day = c.get(Calendar.DAY_OF_MONTH);

    public static int startDay,startMonth,startYear;
    StartDateDialogFragment startDatepicker;
    EndDateDialogFragment endDatepicker;

    boolean startDatePicked=false;
    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__media);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        EditStart = (EditText) findViewById(R.id.txtStartDate);
        EditStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                startDatepicker = new StartDateDialogFragment();

                startDatepicker.show(getSupportFragmentManager(), "showDate");
            }
        });


        EditEnd = (EditText) findViewById(R.id.txtEndDate);
        EditEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (startDatePicked) {
                    endDatepicker = new EndDateDialogFragment();
                    endDatepicker.show(getSupportFragmentManager(), "showDate");
                } else {
                    Toast.makeText(getApplicationContext(), "Enter Start Date First !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AdText = (EditText) findViewById(R.id.adedit);
        AdText.requestFocus();
        //AdText.requestFocus();



        Sbtbtn = (Button) findViewById(R.id.sbtbutton);
        Canclbtn = (Button) findViewById(R.id.canclbutton);


        Sbtbtn.setOnClickListener(this);
        Canclbtn.setOnClickListener(this);


        Status = "Created";
        final Calendar calendar = Calendar.getInstance();
        admainid = new SimpleDateFormat("yyyyMMDDHHmmss");
        AdMainId = admainid.format(calendar.getTime());
        Log.e("############", AdMainId);


        TickerDescAD = "Ad-" + AdMainId;
        Log.e("###########", TickerDescAD);

    }


    public class StartDateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal=Calendar.getInstance();
            int year=cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            validateStartDate(year, monthOfYear + 1, dayOfMonth);

        }

    }



    public class EndDateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal=Calendar.getInstance();
            int year=cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            validateEndDate(year, monthOfYear + 1, dayOfMonth);

        }

    }



    public void validateStartDate(int year,int month,int day) {
        Date Tdate=null,edate=null;

        String enddate=year+"-"+month+"-"+day;
        String todaysdate=Year+"-"+Month+"-"+Day;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            edate = sdf.parse(enddate);
            Tdate = sdf.parse(todaysdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(Tdate);
        cal2.setTime(edate);
        if(cal2.before(cal1)){
            startDatepicker.dismiss();
            Toast.makeText(getApplicationContext(),"Invalid date !!",Toast.LENGTH_SHORT).show();
            startDatepicker = new StartDateDialogFragment();
            startDatepicker.show(getSupportFragmentManager(), "showDate");

        }
        else{
            startDatePicked=true;
            EditStart.setText(day + "/" + month + "/" + year);
            startDay=day;
            startMonth=month;
            startYear=year;
        }

        Log.e("########", "----------->" + Tdate);
        Log.e("########", "----------->" + edate);
    }


    public void validateEndDate(int year,int month,int day){
        Date sdate=null,edate=null;

        String enddate=year+"-"+month+"-"+day;
        String startdate=startYear+"-"+startMonth+"-"+startDay;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            edate = sdf.parse(enddate);
            sdate = sdf.parse(startdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(sdate);
        cal2.setTime(edate);
        if(cal2.before(cal1)){
            endDatepicker.dismiss();
            Toast.makeText(getApplicationContext(),"Invalid date !!",Toast.LENGTH_SHORT).show();
            endDatepicker = new EndDateDialogFragment();

            endDatepicker.show(getSupportFragmentManager(), "showDate");
        }
        else{
            EditEnd.setText(day + "/" + month + "/" + year);
        }
    }



    @Override
    public void onClick(View v) {
        AdText = (EditText) findViewById(R.id.adedit);

        AdText.setKeyListener(DigitsKeyListener.getInstance("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz  "));


        if (v == Sbtbtn) {


            if ( ( AdText.getText().toString().equals("")) || EditEnd.getText().toString().equals("") || EditStart.getText().toString().equals("") )
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else {
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();

                helper.InsertAdTicker(AdMainId.toString(), TickerDescAD.toString(), AdText.getText().toString(), EditStart.getText().toString(), EditEnd.getText().toString(), Status.toString());
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();

                AdText.setText("");
                EditStart.setText("");
                EditEnd.setText("");


                final Calendar calendar = Calendar.getInstance();
                admainid = new SimpleDateFormat("yyyyMMDDHHmmss");
                AdMainId = admainid.format(calendar.getTime());
                Log.e("############", AdMainId);
                TickerDescAD = "Ad-" + AdMainId;
                Log.e("###########", TickerDescAD);

            }

        }
        if (v ==Canclbtn ) {

            finish();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen2, menu);


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


            Intent i = new Intent(Activity_Media.this, ActivityLoyality.class);
            startActivity(i);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }



}