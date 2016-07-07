package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapter.SalesReturnAdapter;
import Adapter.SalesReturnProductAdapter;
import Adapter.SalesReturnwithoutinvoiceno;
import Pojo.Salesreturndetail;
import Pojo.SalesreturndetailWithoutPo;

public class Activity_Salesreturn_withoutinvoiceno extends Activity {



    private TextWatcher  ProductNameTextWatcher;
    AutoCompleteTextView ProductName;
    ArrayList<SalesreturndetailWithoutPo> ProductNamelist;
    SalesReturnProductAdapter productNmAdaptor ;
    SalesReturnwithoutinvoiceno ReturnListAdapter;
    ArrayList<SalesreturndetailWithoutPo>ProductNameArrayList;
    DBhelper mydb;
    Spinner reason;
    ListView listView;
    String item;
    Button submit,clear;
    TextView Grandtotal,invoice;
    TextView Totalitems,Billno;
    ActionBar actionBar;
    TelephonyManager tel;
    String x_imei;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
    Bundle syncDataBundle = null;
    private boolean syncInProgress = false;
    private boolean didSyncSucceed = false;

    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__salesreturn_withoutinvoiceno);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        listView = (ListView)findViewById(R.id.lv_PurchaseProduct);
        submit = (Button)findViewById(R.id.salesreturn_submit);
        clear=(Button)findViewById(R.id.clear);
        Grandtotal=(TextView)findViewById(R.id.grandtotal_textt);
        reason = (Spinner)findViewById(R.id.reasonreturn);
        invoice = (TextView)findViewById(R.id.invoiceno);
        Totalitems=(TextView)findViewById(R.id.totalitem_textt);
        ProductName = (AutoCompleteTextView) findViewById(R.id.autoProductName);
        ProductName.setThreshold(1);
        actionBar=getActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Billno = (TextView)findViewById(R.id.sales_billno);
        Billno.setText(tel.getDeviceId().toString());
        invoiceno();


        final DBhelper db = new DBhelper(this);
        ProductNameTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.i("&&&&&&&&", "After text changed called and text value is" + s.toString());
                if (ProductName.isPerformingCompletion()) {
                    Log.i("&&&&&&&&", "Performing completion and hence drop down will not be shown ");
                    return;
                }
                String userTypedString = ProductName.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }

                ProductNameArrayList = db.getProductReturnData(userTypedString);
                if (ProductNameArrayList != null) {
                    if (productNmAdaptor == null) {
                        productNmAdaptor = new SalesReturnProductAdapter(Activity_Salesreturn_withoutinvoiceno.this, android.R.layout.simple_dropdown_item_1line, ProductNameArrayList);
                        productNmAdaptor.setList(ProductNameArrayList);
                        ProductName.setAdapter(productNmAdaptor);
                        ProductName.setThreshold(1);
                    } else {
                        productNmAdaptor.setList(ProductNameArrayList);
                        productNmAdaptor.notifyDataSetChanged();
                    }
                }

            }
        };

        ProductName.addTextChangedListener(ProductNameTextWatcher);
        ProductName.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //  Cursor curral=(Cursor)parent.getItemAtPosition(position);
                        // PurchaseProductModel resval1=(PurchaseProductModel) parent.getItemAtPosition(position);
                        SalesreturndetailWithoutPo result = (SalesreturndetailWithoutPo) parent.getItemAtPosition(position);
                        if (ReturnListAdapter == null) {
                            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                            ReturnListAdapter = new SalesReturnwithoutinvoiceno(Activity_Salesreturn_withoutinvoiceno.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<SalesreturndetailWithoutPo>());
                            listView.setAdapter(ReturnListAdapter);
                        }

                        ReturnListAdapter.addProductToList(result);

                        Log.i("&&&&&&&&", "Adding " + result + " to Product List..");
                        ReturnListAdapter.notifyDataSetChanged();
                        ProductName.setText("");
                        setSummaryRow();

                    }


                });

        final DBhelper mydb=new DBhelper(Activity_Salesreturn_withoutinvoiceno.this);
        ArrayList<Salesreturndetail> reasonReturn= mydb.getReasonReturn();
        ArrayAdapter<Salesreturndetail > stringArrayAdapter =
                new ArrayAdapter<Salesreturndetail>(Activity_Salesreturn_withoutinvoiceno.this, android.R.layout.simple_spinner_dropdown_item,reasonReturn);
        reason.setAdapter(stringArrayAdapter);
        reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                item = adapterView.getItemAtPosition(position).toString();

                // Toast.makeText(getApplicationContext(),
                //    "report : " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }

        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  try {
                    mydb.insertsalesreturnwithoutinvoice(invoice.getText().toString(),ReturnListAdapter.getList(), item.toString());
                    mydb.insertsalereturndataforproductdetailwithoutinvoiceno(invoice.getText().toString(), ReturnListAdapter.getList());
                    mydb.updateqtyforinvoiceforsalesreturnwithoutinvoiceno(ReturnListAdapter.getList());
                    Toast.makeText(getApplicationContext(), invoice.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                    startActivity(intent);



//                } catch (NullPointerException ex) {
//                    ex.printStackTrace();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(null);
                invoiceno();
                Totalitems.setText("");
                Grandtotal.setText("");


            }
        });
    }


    public  void invoiceno() {
        Long Value = System.currentTimeMillis();
        final String result = Long.toString(Value);
        String invoicevalue = Billno.getText().toString();
        mydb = new DBhelper(this);

        ArrayList<String> billno = mydb.getimeino();
        for (String str : billno) {
            if (str.equals(invoicevalue))
            {
                ArrayList<String> imei = mydb.getprefix(str);
                Log.e("%%%%%%%%%%%%%", imei.toString());
                x_imei=imei.toString();
                String x1=  x_imei.replace("[", "").replace("]","").concat(result);
                Log.e("X1_imei is :",x1);
                invoice.setText(x1);
            } else {
                continue;
            }
        }
    }
    /*    public void grandtotal()
    {
        float Getval = ReturnListAdapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = Float.toString(Getval);
        Grandtotal.setText(GrandVal);


    }*/
    public void setSummaryRow() {
        float Getval = ReturnListAdapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = Float.toString(Getval);
        Grandtotal.setText(GrandVal);
        Log.d("@@@@@@@@@@", "" + Grandtotal);


        int Getitem=ReturnListAdapter.getCount();
        int Allitem=Getitem;
        String GETITEM=Integer.toString(Allitem);
        Totalitems.setText(GETITEM);
    }
    @Override
    public String toString() {
        return item;
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


            Intent i=new Intent(Activity_Salesreturn_withoutinvoiceno.this,ActivitySales.class);
            startActivity(i);
            return true;


        }

        return super.onOptionsItemSelected(item);
    }









}

