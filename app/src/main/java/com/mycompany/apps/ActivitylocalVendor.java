package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
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
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.godbtech.sync.GBrowserNotifyMessageEvt;
import com.godbtech.sync.GNativeSync;
import com.godbtech.sync.GSyncServerConfig;
import com.godbtech.sync.GSyncStatusEvt;
import com.godbtech.sync.GSyncable;
import com.godbtech.sync.GUtils;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapter.CustomerAdapter;
import Adapter.localvendoradapter;
import Pojo.Customer;
import Pojo.localvendor;


public class ActivitylocalVendor extends Activity {

    EditText localvendorname,localvendorcontactname,localvendoraddress,
            localvendorcity, localvendorzip,localvendortele,localvendormobile;
    TextView localvendorcountry, localvendorid;
    ActionBar actionBar;
   String localVendor_Id_To_Update;
    TextWatcher mTextWatcher;
    AutoCompleteTextView autoCompleteTextView;
  Spinner localvendoractive, localvendorinventory;

    ArrayList<localvendor>localvendorlist;
    DBhelper mydb;
    localvendoradapter adapter;
    String ActiveType[],SpinType[];
    String item;
    String SpinValue,InvSpinValue;

    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
    // Adapter
    ArrayAdapter<String> adapterActiveType;
    ArrayAdapter<String>InventoyActiveType;
    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitylocal_vendor);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        localvendorid = (TextView)findViewById(R.id.localvend_vendorid);
        localvendorname= (EditText)findViewById(R.id.localvend_name);
        localvendorcontactname =(EditText)findViewById(R.id.localvend_contactname);
        localvendoraddress = (EditText)findViewById(R.id.localvend_address1);
        localvendorcity =(EditText)findViewById(R.id.localvend_city);
        localvendorcountry =(TextView)findViewById(R.id.localvend_countrydesc);
        localvendorzip =(EditText)findViewById(R.id.localvend_zip);
        localvendortele =(EditText)findViewById(R.id.localvend_telephone);
        localvendormobile =(EditText)findViewById(R.id.localvend_mobilenumber);
        localvendorinventory =(Spinner)findViewById(R.id.localvend_inventory);
        localvendoractive = (Spinner)findViewById(R.id.localvend_active);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
       // final AutoCompleteTextView autoCompleteTextView;
        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        SpinType = getResources().getStringArray(R.array.active_Statusinventory);
        InventoyActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,SpinType);
        InventoyActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        localvendorinventory.setAdapter(InventoyActiveType);
        localvendorinventory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InvSpinValue = localvendorinventory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapterActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        localvendoractive.setAdapter(adapterActiveType);
        localvendoractive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = localvendoractive.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        try
        {
            mydb = new DBhelper(this);
            autoCompleteTextView = (CustomAuto)findViewById(R.id.myautocomplete);
            autoCompleteTextView.setThreshold(1);
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
                    if( autoCompleteTextView.isPerformingCompletion()) {
                        Log.d( "Debuging", "Performing completion " );
                        return;
                    }
                    String userTypedString = autoCompleteTextView.getText().toString().trim();
                    if (userTypedString.equals("")) {
                        return;
                    }
                    localvendorlist = mydb.getAlllocalVendor(userTypedString);

                    if (localvendorlist != null) {
                        if (adapter == null) {
                            adapter = new localvendoradapter(ActivitylocalVendor.this, android.R.layout.simple_dropdown_item_1line, localvendorlist);
                            adapter.setLocalVendorList(localvendorlist);
                            autoCompleteTextView.setAdapter(adapter);
                            autoCompleteTextView.setThreshold(3);
                        } else {

                            adapter.setLocalVendorList(localvendorlist);
                            adapter.notifyDataSetChanged();

                        }
                    }
                }
            };
            autoCompleteTextView.addTextChangedListener(mTextWatcher);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                    Log.d( "Debuging", "On click called " );

                    final DBhelper  mydb=new DBhelper(ActivitylocalVendor.this);

                    localvendor value = (localvendor) parent.getItemAtPosition(pos);
                    localvendorid.setText(value.getLocalvendorid());
                    localvendorname.setText(value.getLocalvendorname());
                    localvendorname.requestFocus();
                    localvendoraddress.setText(value.getLocalvendoraddress());
                    localvendortele.setText(value.getLocalvendortele());
                    localvendorzip.setText(value.getLocalvendorzip());
                    localvendorcontactname.setText(value.getLocalvendorcontactname());
                    localvendormobile.setText(value.getLocalvendormobile());
                    localvendorcity.setText(value.getLocalvendorcity());
                    localvendorcountry.setText(value.getLocalvendorcountry());
                    //localvendorinventory.setText();
                    InvSpinValue=value.getLocalvendorinventory();
                    if (  InvSpinValue.equals("N"))
                    {
                        localvendorinventory.setSelection(0);
                    }
                    if (  InvSpinValue.equals("Y"))
                    {
                        localvendorinventory.setSelection(1);
                    }
                    SpinValue=value.getLocalactive();
                    if (SpinValue.equals("Y"))
                    {
                        localvendoractive.setSelection(0);
                    }
                    if (SpinValue.equals("N"))
                    {
                        localvendoractive.setSelection(1);
                    }
                    autoCompleteTextView.setText("");

                }
            });
            Button Update = (Button)findViewById(R.id.localvendor_edit_button);
            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(Buttonok);

                    String value = localvendorid.getText().toString();
                    localVendor_Id_To_Update = value;
                    if (localvendorname.getText().toString().matches(""))
                    {
                        localvendorname.setError("Please select Vendor Name");
                        return;
                    }
                    if (localvendorcontactname.getText().toString().matches(""))
                    {
                        localvendorcontactname.setError("Please select Vendor Contact Name");
                        return;
                    }

                    mydb.updatelocalVendor(localVendor_Id_To_Update, localvendorname.getText().toString(), localvendorcontactname.getText().toString(),
                            localvendoraddress.getText().toString(), localvendorcountry.getText().toString(), localvendorcity.getText().toString(), localvendormobile.getText().toString(), SpinValue, InvSpinValue, localvendorzip.getText().toString(), localvendortele.getText().toString()
                    );
                    mydb.updatelocalVendorinpurchase(localVendor_Id_To_Update,localvendorname.getText().toString(),localvendoraddress.getText().toString(),localvendorcountry.getText().toString(), localvendorcity.getText().toString(),localvendorzip.getText().toString(),InvSpinValue,SpinValue);

                        Toast.makeText(getApplicationContext(), "Local Vendor Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                        startActivity(intent);



                }
            });


        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button Clear = (Button) findViewById(R.id.localvendor_clear_button);
        Clear .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                localvendoraddress.setText("");
                localvendorid.setText("");
                localvendorcity.setText("");
                localvendorname.setText("");
                localvendorcontactname.setText("");
                localvendormobile.setText("");
                localvendorcountry.setText("");
                localvendortele.setText("");
                localvendorzip.setText("");
                localvendorinventory.setAdapter(null);
                autoCompleteTextView.setText("");
                localvendoractive.setAdapter(null);
                ActiveType = getResources().getStringArray(R.array.active_Status);
                adapterActiveType = new ArrayAdapter<String>(ActivitylocalVendor.this,android.R.layout.simple_spinner_item,ActiveType);
                adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                InventoyActiveType = new ArrayAdapter<String>(ActivitylocalVendor.this,android.R.layout.simple_spinner_item,ActiveType);
                InventoyActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                localvendoractive.setAdapter(adapterActiveType);
                localvendorinventory.setAdapter(InventoyActiveType);
            }
        });

        Button Exit = (Button)findViewById(R.id.localvendor_exit_button);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);
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

            Intent i=new Intent(ActivitylocalVendor.this,Activity_masterScreen2.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public String toString() {
        return item;
    }


    }
