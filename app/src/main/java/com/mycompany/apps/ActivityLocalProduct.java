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
import android.text.Editable;
import android.text.InputFilter;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapter.CustomerAdapter;
import Adapter.localproductadapter;
import Pojo.Customer;
import Pojo.LocalProduct;


public class      ActivityLocalProduct extends Activity  {
    ActionBar actionBar;
    EditText localproductname,localbarcode,localmrp,localsellingprice,localpurchaseprice,localtaxid;
     String localProd_Id_To_Update;
    TextView localproductid,localmargin;
     DBhelper mydb;
    TextWatcher mTextWatcher;
    localproductadapter adapter;
    AutoCompleteTextView autoCompleteTextView;
   EditText auto;
    ArrayList<LocalProduct>localproductlist;
    String ActiveType[] ;
    String item;
    String SpinValue;
    Spinner localactive;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));

    // Adapter
    ArrayAdapter<String> adapterActiveType;



    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_local_product);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        localproductid = (TextView)findViewById(R.id.localprod_prodid);
        localproductname= (EditText)findViewById(R.id.localprod_prodname);
       localbarcode =(EditText)findViewById(R.id.localprod_barcode);
        localmrp = (EditText)findViewById(R.id.localprod_mrp);
        localmrp.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7,2)});

        localsellingprice =(EditText)findViewById(R.id.localprod_sellingprice);
        localsellingprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7,2)});

        localpurchaseprice =(EditText)findViewById(R.id.localprod_purchaseprice);
        localpurchaseprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7,2)});

        localactive = (Spinner)findViewById(R.id.localprod_active);
        localmargin = (TextView)findViewById(R.id.localprod_margin);
       // localtaxid =(EditText)findViewById(R.id.localprod_Taxid);

        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapterActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        localactive.setAdapter(adapterActiveType);
        localactive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = localactive.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
//
//        localactive.setAdapter(adapterActiveType);
//        localactive.setThreshold(1);
//        // Business Type Item Selected Listener
//        localactive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//  adapterActiveType = new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, ActiveType);
//            @Override
//            public void onItemClick(AdapterView<?> adapter, View v,
//                                    int position, long id) {
//                // On selecting a spinner item
//                item = adapter.getItemAtPosition(position).toString();
//
////
//            }
//
//        });

        try
        {
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
                    Log.d("Debuging", "After text changed called ");
                    if( autoCompleteTextView.isPerformingCompletion()) {
                        Log.d( "Debuging", "Performing completion " );
                        return;
                    }
                    String userTypedString = autoCompleteTextView.getText().toString().trim();
                    if (userTypedString.equals("")) {
                        return;
                    }
                    localproductlist = mydb.getAllLocalProduct(userTypedString);

                    if (localproductlist != null) {
                        if (adapter == null) {
                            adapter = new localproductadapter(ActivityLocalProduct.this, android.R.layout.simple_dropdown_item_1line, localproductlist);
                            adapter.setLocalProductList(localproductlist);
                            autoCompleteTextView.setAdapter(adapter);
                            autoCompleteTextView.setThreshold(3);
                        } else {

                            adapter.setLocalProductList(localproductlist);
                            adapter.notifyDataSetChanged();
//
                        }
                    }
                }
            };
            autoCompleteTextView.addTextChangedListener(mTextWatcher);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                    Log.d("Debuging", "On click called ");

                    final DBhelper mydb = new DBhelper(ActivityLocalProduct.this);

                    LocalProduct value = (LocalProduct) parent.getItemAtPosition(pos);
                    localproductid.setText(value.getProductid());
                    localproductname.setText(value.getProductname());
                    localproductname.requestFocus();
                    localsellingprice.setText(value.getSellingPrice());
                    localpurchaseprice.setText(value.getPurchasePrice());
                    localbarcode.setText(value.getBarcode());
                    localmrp.setText(value.getMRP());

                    SpinValue=value.getActive();
                    if (SpinValue.equals("Y"))
                    {
                        localactive.setSelection(0);
                    }
                    if (SpinValue.equals("N"))
                    {
                        localactive.setSelection(1);
                    }
                    localmargin.setText(value.getMargin());

                    autoCompleteTextView.setText("");



                }
            });



            Button Clear = (Button)findViewById(R.id.localprod_clear_button);
                Clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.startAnimation(Buttonok);
                        auto.requestFocus();
                        localproductid.setText("");
                        localbarcode.setText("");
                        localmrp.setText("");
                        localproductname.setText("");
                        localpurchaseprice.setText("");
                        localsellingprice.setText("");
                        autoCompleteTextView.setText("");
                        auto.setText("");
                        localactive.setAdapter(null);

                        ActiveType = getResources().getStringArray(R.array.active_Status);
                        adapterActiveType = new ArrayAdapter<String>(ActivityLocalProduct.this,android.R.layout.simple_spinner_item,ActiveType);
                        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        localactive.setAdapter(adapterActiveType);
                        localmargin.setText("");



                    }
                });
            mydb = new DBhelper(this);
            auto = (EditText)findViewById(R.id.product_auto_complete);
            auto.requestFocus();
            auto.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                String barcodeFromScanner = auto.getText().toString().trim();
                                                if (barcodeFromScanner.equals("")) {
                                                    return;
                                                }
                                                localproductlist = mydb.getLocalProductfromBarcode(barcodeFromScanner);
                                                if (localproductlist != null && localproductlist.size() > 0) {

                                                    LocalProduct value = (LocalProduct) localproductlist.get(0);
                                                    localproductid.setText(value.getProductid());
                                                    localproductname.setText(value.getProductname());
                                                   // localproductname.requestFocus();
                                                    localsellingprice.setText(value.getSellingPrice());
                                                    localpurchaseprice.setText(value.getPurchasePrice());
                                                    localbarcode.setText(value.getBarcode());
                                                    localmrp.setText(value.getMRP());
                                                    SpinValue=value.getActive();
                                                    if (SpinValue.equals("Y"))
                                                    {
                                                        localactive.setSelection(1);
                                                    }
                                                    if (SpinValue.equals("N"))
                                                    {
                                                        localactive.setSelection(2);
                                                    }
                                                    localmargin.setText(value.getMargin());


                                                }
                                            }
                                        });

                        Button Exit = (Button) findViewById(R.id.localprod_exit_button);
                    Exit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            view.startAnimation(Buttonok);
                            Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                            startActivity(intent);
                        }
                    });
                    Button Update = (Button) findViewById(R.id.localprod_edit_button);
                    Update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            view.startAnimation(Buttonok);
                           /* localproductid = (TextView) findViewById(R.id.localprod_prodid);
                            localproductname = (EditText) findViewById(R.id.localprod_prodname);
                            localbarcode = (EditText) findViewById(R.id.localprod_barcode);
                            localmrp = (EditText) findViewById(R.id.localprod_mrp);
                            localsellingprice = (EditText) findViewById(R.id.localprod_sellingprice);
                            localpurchaseprice = (EditText) findViewById(R.id.localprod_purchaseprice);
                            localactive = (EditText) findViewById(R.id.localprod_active);*/
                            String value = localproductid.getText().toString();
                            localProd_Id_To_Update = value;
                            if (localsellingprice.getText().toString().matches(""))
                            {
                                localsellingprice.setError("Please fill Selling Price");
                                return;
                            }
                            if (localpurchaseprice.getText().toString().matches(""))
                            {
                                localpurchaseprice.setError("Please fill Selling Price");
                                return;
                            }
                            if (localproductname.getText().toString().matches(""))
                            {
                                localproductname.setError("Please fill Selling Price");
                                return;
                            }
                            if (localmrp.getText().toString().matches(""))
                            {
                                localmrp.setError("Please fill Selling Price");
                                return;
                            }
                            try {
                               float selling =Float.parseFloat(localsellingprice.getText().toString());
                                float purchase = Float.parseFloat(localpurchaseprice.getText().toString());
                                float mrp = Float.parseFloat(localmrp.getText().toString());
                                if (selling > mrp) {
                                    localsellingprice.setError("Invalid Selling Price");
                                    return;

                                }
                                if (purchase > selling) {
                                    localsellingprice.setError("Invalid Selling Price");
                                    return;
                                }
                                if (purchase > mrp) {
                                    localpurchaseprice.setError("Invalid purchase Price");
                                    return;
                                }

                                mydb.updatelocalProduct(localProd_Id_To_Update, localproductname.getText().toString(), localbarcode.getText().toString(), localmrp.getText().toString(),
                                        localsellingprice.getText().toString(), localpurchaseprice.getText().toString(), SpinValue, localmargin.getText().toString());
                                mydb.updatelocalProductCom(localProd_Id_To_Update, localproductname.getText().toString(), localbarcode.getText().toString(), localmrp.getText().toString(),
                                        localsellingprice.getText().toString(), localpurchaseprice.getText().toString(), SpinValue);

                                Toast.makeText(getApplicationContext(), "Local Product Updated", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                                startActivity(intent);



                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                        }
                    });

                }

                catch(
                NullPointerException e
                )

                {
                    e.printStackTrace();
                }

                catch(
                Exception e
                )

                {
                    e.printStackTrace();
                }


            }

            @Override
    public String toString() {
        return item;
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

            Intent i=new Intent(ActivityLocalProduct.this,Activity_masterScreen2.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}



