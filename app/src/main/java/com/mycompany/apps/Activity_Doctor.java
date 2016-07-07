package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapter.DoctorAdapter;
import Pojo.DoctorPojo;

public class Activity_Doctor extends Activity  {


    EditText CUSTOMERNAME;
    EditText CUSTOMERMOBILE;
    LinearLayout hidden;
    Spinner CUSTOMEREMAIL;
    LinearLayout hidden2;
    Button addbtn;
    ActionBar actionBar;
    DBhelper mydb;
    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    ArrayList<DoctorPojo> doctorlist;
    TextView Customername, Customermobile;
    Spinner DoctorSpecialtion;
    TextView DoctorSpecialition;
    DoctorAdapter adapter;
    Bundle syncDataBundle = null;
    String item;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean syncInProgress = false;
    private boolean didSyncSucceed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__doctor);
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        CUSTOMERNAME = (EditText) findViewById(R.id.Cust_name);
        CUSTOMERMOBILE = (EditText) findViewById(R.id.Cust_mobileno);
        CUSTOMEREMAIL = (Spinner) findViewById(R.id.doctor_special);
        Customername = (TextView) findViewById(R.id.Cust_name2);
        Customermobile = (TextView) findViewById(R.id.Cust_mobileno1);
        //DoctorSpecialtion = (Spinner)findViewById(R.id.Cust_email2);
        final EditText Custhidd = (EditText) findViewById(R.id.Cust_mobilenohidd);
        LinearLayout show = (LinearLayout) findViewById(R.id.Hiddenlayout1);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        // final AutoCompleteTextView autoCompleteTextView;
        mydb = new DBhelper(this);
        autoCompleteTextView = (CustomAuto) findViewById(R.id.myautocomplete);
        autoCompleteTextView.setThreshold(3);
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("Debuging", "After text changed called ");
                if (autoCompleteTextView.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = autoCompleteTextView.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                doctorlist = mydb.getAllDoctors(userTypedString);

                if (doctorlist != null) {
                    if (adapter == null) {
                        adapter = new DoctorAdapter(Activity_Doctor.this, android.R.layout.simple_dropdown_item_1line, doctorlist);
                        adapter.setDoctorList(doctorlist);
                        autoCompleteTextView.setAdapter(adapter);
                        autoCompleteTextView.setThreshold(3);
                    } else {
                        adapter.setDoctorList(doctorlist);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        };
        autoCompleteTextView.addTextChangedListener(mTextWatcher);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                Log.d("Debuging", "On click called ");

                final DBhelper mydb = new DBhelper(Activity_Doctor.this);

                DoctorPojo value = (DoctorPojo) parent.getItemAtPosition(pos);
                Customername.setText(value.getDoctorSpeciality());
                Customermobile.setText(value.getDoctorName());

                Custhidd.requestFocus();
                autoCompleteTextView.setText("");


            }
        });
        final DBhelper mydb = new DBhelper(Activity_Doctor.this);
        ArrayList<String> doctorspecial = mydb.getdoctorspecialication();
        ArrayAdapter<String> stringArrayAdapter =
                new ArrayAdapter<String>(Activity_Doctor.this, android.R.layout.simple_spinner_dropdown_item, doctorspecial);
        CUSTOMEREMAIL.setAdapter(stringArrayAdapter);
        CUSTOMEREMAIL.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                item = adapterView.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }

        });
        addbtn = (Button) findViewById(R.id.Cust_ok_button);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                hidden2 = (LinearLayout) findViewById(R.id.Hiddenlayout1);
                hidden2.setVisibility(View.INVISIBLE);
                hidden = (LinearLayout) findViewById(R.id.Hiddenlayout);
                hidden.setVisibility(View.VISIBLE);
                LinearLayout hidden3 = (LinearLayout) findViewById(R.id.autolayout);
                hidden3.setVisibility(View.INVISIBLE);
                CUSTOMERNAME = (EditText) findViewById(R.id.Cust_name);
                CUSTOMERMOBILE = (EditText) findViewById(R.id.Cust_mobileno);
                CUSTOMEREMAIL = (Spinner) findViewById(R.id.doctor_special);
                CUSTOMERNAME.setFocusableInTouchMode(true);
                CUSTOMERMOBILE.setFocusableInTouchMode(true);
                CUSTOMEREMAIL.setFocusableInTouchMode(true);


                DBhelper db = new DBhelper(Activity_Doctor.this);

                if (CUSTOMERMOBILE.getText().toString().matches("") || CUSTOMERNAME.getText().toString().matches("") || item.toString().matches("")) {
                    Toast toast = Toast.makeText(Activity_Doctor.this, "PLEASE FILL THE FIELD", Toast.LENGTH_SHORT);
                    toast.show();
                    return;

                } else {
                    db.insertDoctor(new DoctorPojo(CUSTOMERMOBILE.getText().toString(),
                            CUSTOMERNAME.getText().toString(), item.toString()));
                    Toast toast = Toast.makeText(Activity_Doctor.this, "DOCTOR ADDED", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                    startActivity(intent);

                }

            }
        });

        Button Exit = (Button) findViewById(R.id.Cust_Exit_button);

        Exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);
                Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                startActivity(intent);
            }
        });
        Button Clear = (Button) findViewById(R.id.Cust_clear_button);
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                CUSTOMEREMAIL.setAdapter(null);
                final DBhelper mydb = new DBhelper(Activity_Doctor.this);
                ArrayList<String> doctorspecial = mydb.getdoctorspecialication();
                ArrayAdapter<String> stringArrayAdapter =
                        new ArrayAdapter<String>(Activity_Doctor.this, android.R.layout.simple_spinner_dropdown_item, doctorspecial);
                CUSTOMEREMAIL.setAdapter(stringArrayAdapter);
                CUSTOMERNAME.setText("");
                CUSTOMERMOBILE.setText("");
                Customermobile.setText("");
                Customername.setText("");
                autoCompleteTextView.setText("");

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

            Intent i = new Intent(Activity_Doctor.this, Activity_masterScreen2.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

//        private boolean isValidEmail(String email) {
//            String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//
//            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
//            Matcher matcher = pattern.matcher(email);
//            return matcher.matches();
//        }

