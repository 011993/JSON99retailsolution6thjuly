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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

import Adapter.ProductAdapter;
import Adapter.VendorAdapter;
import Pojo.Product;
import Pojo.Vendor;


public class ActivityVendor extends Activity {
    TextView ColumnVendorsearch;
    TextView ColumnVendorname;
    TextView ColumnVendorcontactname;
    TextView ColumnVendoraddress;
    TextView ColumnVendorcity;
    TextView ColumnVendorcountry;
    TextView ColumnVendorzip;
    TextView ColumnVendortelephone;
    TextView ColumnVendormobile;
    TextView ColumnVendorinventory, ColumnVendorId;
    String Vend_Id_To_Update;
    Button vendoraddbtn;
    ListView searchResultList;
    Spinner spInventoryType;
    ActionBar actionBar;
    DBhelper mydb;
    TextWatcher mTextWatcher;
    Spinner ColumnVendoractive;
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<Vendor> vendorlist;
    VendorAdapter adapter;
    String ActiveType[];
    ArrayAdapter<String> adapterActiveType;
    String SpinValue;
    TextView Email;
    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_vendor);

        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);


        ColumnVendorname = (TextView) findViewById(R.id.Vendor_Name);
        ColumnVendorcontactname = (TextView) findViewById(R.id.Vendor_Contact_Name);
        ColumnVendoraddress = (TextView) findViewById(R.id.Vendor_Address);
        ColumnVendorcity = (TextView) findViewById(R.id.Vendor_City);
        ColumnVendorcountry = (TextView) findViewById(R.id.Vendor_Country);
        ColumnVendorzip = (TextView) findViewById(R.id.Vendor_Zip);
        ColumnVendortelephone = (TextView) findViewById(R.id.Vendor_Landline);
        ColumnVendormobile = (TextView) findViewById(R.id.Vendor_Mobile);
        ColumnVendorinventory = (TextView) findViewById(R.id.Vendor_Inventory);
        ColumnVendoractive = (Spinner) findViewById(R.id.Vendor_Active);
        ColumnVendorId = (TextView) findViewById(R.id.Vendor_Id);
        Email=(TextView)findViewById(R.id.EmailProd);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);

        // spInventoryType = (Spinner) findViewById(R.id.spinner1);

        // vendoraddbtn = (Button) findViewById(R.id.vendor_create_button);
        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapterActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ColumnVendoractive.setAdapter(adapterActiveType);
        ColumnVendoractive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = ColumnVendoractive.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        try {
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
                    vendorlist = mydb.getAllVendor(userTypedString);

                    if (vendorlist != null) {
                        if (adapter == null) {
                            adapter = new VendorAdapter(ActivityVendor.this, android.R.layout.simple_dropdown_item_1line, vendorlist);
                            adapter.setVendorList(vendorlist);
                            autoCompleteTextView.setAdapter(adapter);
                            autoCompleteTextView.setThreshold(3);
                        } else {

                            adapter.setVendorList(vendorlist);
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

                    final DBhelper mydb = new DBhelper(ActivityVendor.this);

                    Vendor value = (Vendor) parent.getItemAtPosition(pos);
                    ColumnVendorId.setText(value.getVendorId());
                    ColumnVendorname.setText(value.getVendorname());
                    ColumnVendoraddress.setText(value.getAddress());
                    ColumnVendorcountry.setText(value.getCountry());
                    ColumnVendorcontactname.setText(value.getVendorContact());
                    ColumnVendorcity.setText(value.getCity());
                    ColumnVendorzip.setText(value.getZip());
                    ColumnVendormobile.setText(value.getMobile());
                    ColumnVendortelephone.setText(value.getTelephone());
                    ColumnVendorinventory.setText(value.getVendorInventory());
                    Email.setText(value.getEmail());

                    SpinValue=value.getActive();
                    if (SpinValue.equals("Y"))
                    {
                        ColumnVendoractive.setSelection(0);
                    }
                    if (SpinValue.equals("N"))
                    {
                        ColumnVendoractive.setSelection(1);
                    }
                    ColumnVendoractive.requestFocus();
                    autoCompleteTextView.setText("");


                }
            });

            Button Clear = (Button) findViewById(R.id.vendor_clear_button);
            Clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(Buttonok);
                    ColumnVendorinventory.setText("");
                    ColumnVendorname.setText("");
                    ColumnVendoraddress.setText("");
                    ColumnVendorcity.setText("");
                    ColumnVendortelephone.setText("");
                    ColumnVendorzip.setText("");
                    ColumnVendorcontactname.setText("");
                    ColumnVendorcountry.setText("");
                    ColumnVendormobile.setText("");
                    ColumnVendoractive.setAdapter(null);
                    ActiveType = getResources().getStringArray(R.array.active_Status);
                    adapterActiveType = new ArrayAdapter<String>(ActivityVendor.this,android.R.layout.simple_spinner_item,ActiveType);
                    adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    ColumnVendoractive.setAdapter(adapterActiveType);
                    ColumnVendorId.setText("");
                    autoCompleteTextView.setText("");
                }
            });

            Button Exit = (Button) findViewById(R.id.vendor_exit_button);
            Exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(Buttonok);
                    Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                    startActivity(intent);
                }
            });

            Button Update = (Button) findViewById(R.id.vendor_update_button);
            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(Buttonok);
                    /*ColumnVendorId = (TextView) findViewById(R.id.Vendor_Id);
                    ColumnVendoractive = (EditText) findViewById(R.id.Vendor_Active);*/
                    String value = ColumnVendorId.getText().toString();
                    Vend_Id_To_Update = value;
                    if (ColumnVendorname.getText().toString().matches("") || ColumnVendorId.getText().toString().matches(""))
                    {
                        Toast.makeText(getApplicationContext(), "Please select Distributor Name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                  else if(mydb.updateVendor(Vend_Id_To_Update,SpinValue)&&
                    mydb.updateVendorinpurchase(Vend_Id_To_Update,SpinValue));
                    {

                        Toast.makeText(getApplicationContext(), "Distributor Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                        startActivity(intent);

                    }
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            Intent i=new Intent(ActivityVendor.this,Activity_masterScreen2.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
