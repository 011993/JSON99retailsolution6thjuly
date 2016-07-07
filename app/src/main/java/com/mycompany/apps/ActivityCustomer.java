package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextWatcher;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Adapter.CustomerAdapter;
import Pojo.Customer;

/**
 * Created by nishant on 1/27/2016.
 */
public class ActivityCustomer extends Activity

{
    TextView CUSTOMERNAME;
    TextView CUSTOMERMOBILE;
    TextView CUSTOMEREMAIL;
    LinearLayout hidden;
    LinearLayout hidden2;
    Button addbtn;
    ActionBar actionBar;
    DBhelper mydb;
    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    ArrayList<Customer> customerlist;
    TextView Customername,Customermobile,Customeremail;
    CustomerAdapter adapter;
    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitycustomer);

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        CUSTOMERNAME = (EditText)findViewById(R.id.Cust_name);
        CUSTOMERMOBILE = (EditText)findViewById(R.id.Cust_mobileno);
        CUSTOMEREMAIL = (EditText)findViewById(R.id.Cust_email);

         Customername = (TextView)findViewById(R.id.Cust_name2);
         Customermobile = (TextView)findViewById(R.id.Cust_mobileno1);
       Customeremail = (TextView)findViewById(R.id.Cust_email2);
       final EditText Custhidd = (EditText)findViewById(R.id.Cust_mobilenohidd);
        LinearLayout show = (LinearLayout)findViewById(R.id.Hiddenlayout1);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
       // final AutoCompleteTextView autoCompleteTextView;
        mydb = new DBhelper(this);
        autoCompleteTextView = (CustomAuto)findViewById(R.id.myautocomplete);
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
                    Log.d( "Debuging", "After text changed called " );
                    if( autoCompleteTextView.isPerformingCompletion()) {
                        Log.d( "Debuging", "Performing completion " );
                        return;
                    }
                    String userTypedString = autoCompleteTextView.getText().toString().trim();
                    if (userTypedString.equals("")) {
                        return;
                    }
                    customerlist = mydb.getAllCustomers(userTypedString);

                    if (customerlist != null) {
                        if (adapter == null) {
                            adapter = new CustomerAdapter(ActivityCustomer.this, android.R.layout.simple_dropdown_item_1line, customerlist);
                            adapter.setCustomerList(customerlist);
                            autoCompleteTextView.setAdapter(adapter);
                            autoCompleteTextView.setThreshold(3);
                        } else {

                            adapter.setCustomerList(customerlist);
                            adapter.notifyDataSetChanged();
//
                        }
                    }
                }
         };
        CUSTOMEREMAIL.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (CUSTOMEREMAIL.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && s.length() > 0) {
                  //  CUSTOMEREMAIL .setText("valid email");

                } else {
                    CUSTOMEREMAIL.setError("invalid email");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        autoCompleteTextView.addTextChangedListener(mTextWatcher);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                    Log.d( "Debuging", "On click called " );

                    final DBhelper  mydb=new DBhelper(ActivityCustomer.this);

                    Customer value = (Customer) parent.getItemAtPosition(pos);
                    Customername.setText(value.getCustomername());
                    Customermobile.setText(value.getCustomermobileno());
                    Customeremail.setText(value.getCustomeremail());
                    Custhidd.requestFocus();
                    autoCompleteTextView.setText("");



                }
            });





//        final DBhelper  mydb=new DBhelper(this);
//        final ArrayList<String> customerlist= mydb.getAllCustomers();
//
//        ArrayAdapter adp=new ArrayAdapter(this,
//                android.R.layout.simple_dropdown_item_1line,customerlist);
//
//        autoCompleteTextView.setThreshold(1);
//        autoCompleteTextView.setAdapter(adp);
//        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
//
//                String value = customerlist.get(pos);
//                value=autoCompleteTextView.getText().toString();
//                Cursor cs = mydb.getCustomer(value);
//                    if (cs.moveToFirst())
//
//                    {
//                        do {
//                            String custname = cs.getString(cs.getColumnIndex(DBhelper.CUSTOMERNAME));
//                            Customername.setText(custname);
//                            String custmobile = cs.getString(cs.getColumnIndex(DBhelper.CUSTOMERMOBILENO));
//                            Customermobile.setText(custmobile);
//                            String custemail = cs.getString(cs.getColumnIndex(DBhelper.CUSTOMEREMAIL));
//                            Customeremail.setText(custemail);
//                        } while (cs.moveToNext());
//                   }
//
//            }
//
//        });


        addbtn = (Button) findViewById(R.id.Cust_ok_button);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                hidden2 = (LinearLayout)findViewById(R.id.Hiddenlayout1);
                hidden2.setVisibility(View.INVISIBLE);
                hidden = (LinearLayout)findViewById(R.id.Hiddenlayout);
                hidden.setVisibility(View.VISIBLE);
                LinearLayout hidden3 = (LinearLayout)findViewById(R.id.autolayout);
                hidden3.setVisibility(View.INVISIBLE);
                CUSTOMERNAME = (TextView) findViewById(R.id.Cust_name);
                CUSTOMERMOBILE = (TextView) findViewById(R.id.Cust_mobileno);
                CUSTOMEREMAIL = (TextView) findViewById(R.id.Cust_email);
                CUSTOMERNAME.setFocusableInTouchMode(true);
                CUSTOMERMOBILE.setFocusableInTouchMode(true);
                CUSTOMEREMAIL.setFocusableInTouchMode(true);



                DBhelper db = new DBhelper(ActivityCustomer.this);

                if (CUSTOMERMOBILE.getText().toString().matches(""))
                {
                    Toast toast = Toast.makeText(ActivityCustomer.this, "PLEASE FILL THE FIELD", Toast.LENGTH_SHORT);
                    toast.show();
                    return;


                }

                if (CUSTOMERMOBILE.getText().toString().length() >10||CUSTOMERMOBILE.getText().toString().length()!=10)
                {
                    CUSTOMERMOBILE.setError("Invalid Number");
                    return;
                }



               if(db.CheckIsDataAlreadyInDBorNot(CUSTOMERMOBILE.getText().toString()))


                {
                    Toast toast1 = Toast.makeText(ActivityCustomer.this, "MOBILE NO ALREADY REGISTERED",Toast.LENGTH_SHORT);
                    toast1.show();
                    return;
                }

                else if  (db.insertCustomer(new Customer(CUSTOMERMOBILE.getText().toString(),
                        CUSTOMERNAME.getText().toString(),CUSTOMEREMAIL.getText().toString())))

                {
                    Toast toast = Toast.makeText(ActivityCustomer.this, "CUSTOMER ADDED", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                    startActivity(intent);


                }

            }
        });

        Button Exit = (Button)findViewById(R.id.Cust_Exit_button);

        Exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);
                Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                startActivity(intent);
            }
        });
        Button Clear = (Button)findViewById(R.id.Cust_clear_button);
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                CUSTOMEREMAIL.setText("");
                CUSTOMERNAME.setText("");
                CUSTOMERMOBILE.setText("");
                Customeremail.setText("");
                Customermobile.setText("");
                Customername.setText("");
                autoCompleteTextView.setText("");

            }
        });
    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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

            Intent i=new Intent(ActivityCustomer.this,Activity_masterScreen2.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




  }