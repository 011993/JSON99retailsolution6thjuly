package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HandshakeCompletedListener;

import Adapter.PurchaseAdapter;
import Adapter.PurchaseInvoiceNoListAdapter;
import Adapter.PurchaseProductAdapter;
import Adapter.PurchaseproductlistAdapter;
import Pojo.PurchaseInvoiceDropDownModel;
import Pojo.VendorModel;
import Pojo.PurchaseProductModel;

public class PurchaseActivity extends Activity  {
    public static final int MIN_LENGTH_OF_BARCODE_STRING = 13;
    public static final String BARCODE_STRING_PREFIX = "@";
    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    private TextWatcher pTextWatcher;
    EditText statusEditText = null;
    ActionBar actionBar;
    private TextWatcher barcodeTextWatcher;

    AutoCompleteTextView ProductidorName;
    EditText BarcodeScan;
    TextView invoiceNo;
    DBhelper helper;
    ArrayList<VendorModel> arrayList;
    ArrayList<PurchaseProductModel> dropdownProductArrayList;

    ArrayList<PurchaseProductModel> purchaseBarcodearraylist;
    ArrayList<PurchaseProductModel> OldPurchaseArraylist;
    ArrayList<PurchaseProductModel>HoldPurchaseList;
    PurchaseAdapter adapter;
    PurchaseProductAdapter productDropdownAdapter;
    PurchaseInvoiceNoListAdapter invoiceNOListAdapter;
    PurchaseInvoiceDropDownModel purchasemodel;
    TelephonyManager tel;

    String PurchaseOrderNUmber;
    String HoldPurchaseorderNo;
    String x_imei;
    //purchaseBarcodeAdapter barcodeAdapter;
    PurchaseproductlistAdapter productListAdapter = null;
    ListView listView;
    private TextView GrandTotal;
    private TextView Saving;
    private TextView Totalitems,Billno;


    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
    Bundle syncDataBundle = null;
    private boolean syncInProgress = false;
    private boolean didSyncSucceed = false;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        Log.v("&&&&&&&&&&", "on create");


        // getBundle.putString("d4s", "Synctype=RS");

        final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        helper = new DBhelper(this);
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        Button sbtbtn = (Button) findViewById(R.id.submit);
        Button clrbtn = (Button) findViewById(R.id.clear);
        Button HoldBtn=(Button)findViewById(R.id.HoldPurchase);
        Billno = (TextView)findViewById(R.id.sales_billno);
        TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = tel.getDeviceId();
        Log.e("imei is :", device_id);

        Billno.setText(device_id);

        Log.v("&&&&&&&&&&", "Listview initilised");
        listView = (ListView) findViewById(R.id.lv_PurchaseProduct);
        GrandTotal = (TextView) findViewById(R.id.GrandTotal);
        Totalitems = (TextView) findViewById(R.id.Totalitem);
        // Saving=(TextView)findViewById(R.id.PurchaseSaving);

        Log.v("&&&&&&&&&&", "AutoCompletetextview for vendorname initilised");
        autoCompleteTextView = (CustomAuto) findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(3);

        Log.v("&&&&&&&&&&", "AutoCompletetextview for Productname initilised");
        ProductidorName = (CustomAuto) findViewById(R.id.autoProductIdandName);
        ProductidorName.setThreshold(3);

      /*  BarcodeScan = (EditText) findViewById(R.id.Barcodescan);*/

        invoiceNo = (TextView) findViewById(R.id.invoiceno);
        Billno =  (TextView) findViewById(R.id.sales_billno);

//        final Long Value = System.currentTimeMillis();
//        final String resval = Long.toString(Value);
//        invoiceNo.setText(resval);

        SelectPurchaseAlertDialog();
        invoiceno();

/******************************** vendor name oor Distributor name selected from here********************************************************************************************/
        autoCompleteTextView.setThreshold(3);
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("Debuging", "After text changed called ");
                if (autoCompleteTextView.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = autoCompleteTextView.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                arrayList = helper.getVendorNameforPurchase(userTypedString);
                if (arrayList != null) {
                    if (adapter == null) {
                        adapter = new PurchaseAdapter(PurchaseActivity.this, android.R.layout.simple_dropdown_item_1line, arrayList);
                        adapter.setList(arrayList);

                        autoCompleteTextView.setAdapter(adapter);
                        autoCompleteTextView.setThreshold(3);
                    } else {
                        adapter.setList(arrayList);
                        adapter.notifyDataSetChanged();
                    }

                }
            }
        };
        autoCompleteTextView.addTextChangedListener(mTextWatcher);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String VendorName = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(), "U select " + VendorName, Toast.LENGTH_SHORT).show();
            }
        });
        /**************************End for select product and display into the listview ***********************************************************************************************************************/



        helper = new DBhelper(this);
        pTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (autoCompleteTextView.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Please select the vendor Or Distributor ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i("&&&&&&&&", "After text changed called and text value is" + s.toString());
                if (ProductidorName.isPerformingCompletion()) {
                    Log.i("&&&&&&&&", "Performing completion and hence drop down will not be shown ");
                    return;
                }
                String userTypedString = ProductidorName.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                if (userTypedString.length()<3)
                {
                    return;
                }

                if(userTypedString.startsWith(BARCODE_STRING_PREFIX) ) {
                    if(dropdownProductArrayList!= null) {
                        dropdownProductArrayList.clear();
                    }
                    //this is a barcode generated string
                    if(userTypedString.length() <= MIN_LENGTH_OF_BARCODE_STRING) {
                        //ignore all strings of length < 13
                        return;
                    }
                    dropdownProductArrayList = helper.getProductdata(userTypedString.substring(1));
                    //dropdownProductArrayList = helper.getProductdata(userTypedString);
                    if(dropdownProductArrayList.size() == 1) {
                        //we have found the product
                        addProductToPurchaseList(dropdownProductArrayList.get(0));
                        return;
                    } else if( dropdownProductArrayList.size() < 1) {
                        //no product found
                       // Toast.makeText(PurchaseActivity.this, "No Product found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    dropdownProductArrayList = helper.getProductdata(userTypedString);
                    if( dropdownProductArrayList.size() < 1) {
                        dropdownProductArrayList.clear();
                        //no product found
                       // Toast.makeText(PurchaseActivity.this, "No Product found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (productDropdownAdapter == null) {
                    productDropdownAdapter = new PurchaseProductAdapter(PurchaseActivity.this, android.R.layout.simple_dropdown_item_1line, dropdownProductArrayList);
                    productDropdownAdapter.setList(dropdownProductArrayList);
                    ProductidorName.setAdapter(productDropdownAdapter);
                    ProductidorName.setThreshold(3);
                } else {
                    productDropdownAdapter.setList(dropdownProductArrayList);
                    productDropdownAdapter.notifyDataSetChanged();
                }
            }
            //}
        };
        ProductidorName.addTextChangedListener(pTextWatcher);
        ProductidorName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  Cursor curral=(Cursor)parent.getItemAtPosition(position);
                // PurchaseProductModel resval1=(PurchaseProductModel) parent.getItemAtPosition(position);

                PurchaseProductModel resval1 = (PurchaseProductModel) parent.getItemAtPosition(position);
                addProductToPurchaseList(resval1);
            }

        });
/*************************************************************************************************************************/
/**************************End for Barcode***********************************************************************************************************************/
        sbtbtn.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

              final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
              SQLiteDatabase db = helper.getWritableDatabase();
              String invoicevalue = invoiceNo.getText().toString();

              if (autoCompleteTextView.getText().toString().trim().isEmpty()) {
                  Toast.makeText(getApplicationContext(), "Please Select Vendor Or Distributor Name", Toast.LENGTH_SHORT).show();
                  return;
              }
              if (productListAdapter.isEmpty()) {
                  Toast.makeText(getApplicationContext(), "No Product Selected", Toast.LENGTH_SHORT).show();

                  return;
              }
              helper.savePurchaseList(productListAdapter.getList(), invoiceNo.getText().toString(), autoCompleteTextView.getText().toString());
              helper.SaveGrandDataForPurchase(invoiceNo.getText().toString(), autoCompleteTextView.getText().toString(), GrandTotal.getText().toString());
              helper.SavePdfDetailForPurchase(invoiceNo.getText().toString(), autoCompleteTextView.getText().toString());
              Toast.makeText(getApplicationContext(), invoicevalue, Toast.LENGTH_SHORT).show();

                                          Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                                          startActivity(intent);
          }
      }
        );
        HoldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
                SQLiteDatabase db = helper.getWritableDatabase();
                String invoicevalue = invoiceNo.getText().toString();

                if (autoCompleteTextView.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Select Vendor Or Distributor Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productListAdapter.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Product Selected", Toast.LENGTH_SHORT).show();

                    return;
                }
                helper.saveHoldPurchaseList(productListAdapter.getList(), invoiceNo.getText().toString(), autoCompleteTextView.getText().toString());

                Toast.makeText(getApplicationContext(), invoiceNo.getText().toString(), Toast.LENGTH_SHORT).show();


                Intent syncIntent = new Intent(getApplicationContext(), com.mycompany.apps.Activitypurchase.class);
                startActivity(syncIntent);
            }
        });

        /**************************Alll clear screen ***********************************************************************************************************************/
        clrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    productListAdapter.clearAllRows();
                    setSummaryRow();
                    invoiceNo.setText("");
                    autoCompleteTextView.setText("");
                    invoiceno();
                    autoCompleteTextView.removeTextChangedListener(mTextWatcher);
                    autoCompleteTextView.setEnabled(true);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } finally {
                    autoCompleteTextView.setText("");
                }
            }
        });

    }
    final Handler requestFocusHandler = new Handler();



    private void addProductToPurchaseList(PurchaseProductModel resval1) {
        if (productListAdapter == null) {
            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
            productListAdapter = new PurchaseproductlistAdapter(PurchaseActivity.this, new ArrayList<PurchaseProductModel>(), android.R.layout.simple_dropdown_item_1line, resval1);
            listView.setAdapter(productListAdapter);
        }
       int pos= productListAdapter.addProductToList(resval1);
        Log.i("&&&&&&&&", "Adding " + resval1 + " to Product List..");
        productListAdapter.notifyDataSetChanged();
        ProductidorName.setText("");
        setSummaryRow();
        productDropdownAdapter.setList(dropdownProductArrayList);
        dropdownProductArrayList.clear();
        listView.smoothScrollToPosition(pos);
    }
    public void SelectPurchaseAlertDialog() {

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.alertdailog_purchase, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);


        final Button NewPurchase = (Button) alertLayout.findViewById(R.id.NewPurchase);
        final Button OldPurchase = (Button) alertLayout.findViewById(R.id.Oldpurchasebutton);
        final Button Hold=(Button)alertLayout.findViewById(R.id.HoldButton);
        final Button submit = (Button) alertLayout.findViewById(R.id.submitbtn);
        final Button Cancel = (Button) alertLayout.findViewById(R.id.Cancelbtn);
        final Button SubmitForHold=(Button)alertLayout.findViewById(R.id.submitbtnForHold);
        /*final EditText LastInvoiceno=(EditText)alertLayout.findViewById(R.id.EnterInvoiceno);*/
        final Spinner spinner = (Spinner) alertLayout.findViewById(R.id.VendororDistributorName);
        final Spinner Last3InvoiceNo = (Spinner) alertLayout.findViewById(R.id.Last3InvoiceNo);
        final Spinner Holddata=(Spinner)alertLayout.findViewById(R.id.HoldPurchaseNo);
        // final Spinner spinner;
        Last3InvoiceNo.setVisibility(View.GONE);
        submit.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.GONE);
        Cancel.setVisibility(View.VISIBLE);
        Holddata.setVisibility(View.GONE);
        SubmitForHold.setVisibility(View.GONE);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        TextView title = new TextView(this);
        title.setText("Select Purchase Option");
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(25);
        alert.setCustomTitle(title);
        final AlertDialog dialog = alert.create();

        OldPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(), "Old Purchase", Toast.LENGTH_SHORT).show();
                Last3InvoiceNo.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                NewPurchase.setVisibility(View.GONE);
                Hold.setVisibility(View.GONE);
                SubmitForHold.setVisibility(View.GONE);

              /*  LastInvoiceno.getText().toString();*/

            }
        });

        NewPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "New Purchase", Toast.LENGTH_SHORT).show();
                OldPurchase.setVisibility(View.GONE);
                NewPurchase.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                Hold.setVisibility(View.GONE);
                SubmitForHold.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
        Hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Holddata.setVisibility(View.VISIBLE);
                OldPurchase.setVisibility(View.GONE);
                NewPurchase.setVisibility(View.GONE);
                Last3InvoiceNo.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);
                SubmitForHold.setVisibility(View.VISIBLE);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try{ String Spinnervalue = spinner.getSelectedItem().toString();
                String userTypedInvoiceno = Last3InvoiceNo.getSelectedItem().toString().trim();
                Log.e("!!!!!!!!!!!", "" + Last3InvoiceNo);
                ProductidorName.requestFocus();
                if (userTypedInvoiceno.equals("")) {
                    return;
                }
                OldPurchaseArraylist = helper.getAllOLDPurchaseList(PurchaseOrderNUmber);
                Log.d("!@#!@#!@#!@#", "Product arraylist size is " + OldPurchaseArraylist.size());

                if (OldPurchaseArraylist != null && OldPurchaseArraylist.size() > 0) {
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new PurchaseproductlistAdapter(PurchaseActivity.this, new ArrayList<PurchaseProductModel>(), android.R.layout.simple_dropdown_item_1line, null);
                        listView.setAdapter(productListAdapter);
                    }
                    for (PurchaseProductModel prod : OldPurchaseArraylist) {
                        productListAdapter.addProductToList(prod);
                    }
                    autoCompleteTextView.setText("" + spinner.getSelectedItem().toString() + "");
                    autoCompleteTextView.removeTextChangedListener(mTextWatcher);
                    autoCompleteTextView.setEnabled(false);
                    Log.i("&&&&&&&&!", "Adding " + OldPurchaseArraylist.get(0) + " to Product List..");
                    productListAdapter.notifyDataSetChanged();
                    setSummaryRow();
                    dialog.dismiss();

                }
                }catch (Exception e){
                   e.printStackTrace();
               }
            }
        });
        SubmitForHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    String Spinnervalue = Holddata.getSelectedItem().toString();
                if (HoldPurchaseorderNo == null)
                {
                    Toast.makeText(getApplicationContext(), "No Hold Item Found", Toast.LENGTH_SHORT).show();
                    return;
                }
                HoldPurchaseList = helper.getAllHoldPurchaseData(HoldPurchaseorderNo);
                if (HoldPurchaseList != null && HoldPurchaseList.size() > 0) {
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new PurchaseproductlistAdapter(PurchaseActivity.this, new ArrayList<PurchaseProductModel>(), android.R.layout.simple_dropdown_item_1line, null);
                        listView.setAdapter(productListAdapter);
                    }
                    for (PurchaseProductModel prod : HoldPurchaseList) {
                        productListAdapter.addProductToList(prod);

                    }
                    autoCompleteTextView.setText(HoldPurchaseList.get(0).getVendorName());
                    helper.replaceflag(HoldPurchaseorderNo);
                    autoCompleteTextView.removeTextChangedListener(mTextWatcher);
                    autoCompleteTextView.setEnabled(false);
                    Log.i("&&&&&&&&!", "Adding " + HoldPurchaseList.get(0) + " to Product List..");
                    productListAdapter.notifyDataSetChanged();
                    setSummaryRow();
                    dialog.dismiss();
                }
            }
        });

        ArrayList<VendorModel> vendors = helper.getVendors();
        ArrayAdapter<VendorModel> stringArrayAdapter =
                new ArrayAdapter<VendorModel>(PurchaseActivity.this, android.R.layout.simple_spinner_dropdown_item, vendors);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(getApplicationContext(), "OnItemSelectedListener  :" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                String Spinnervalue = spinner.getSelectedItem().toString();
                Log.e("*************", Spinnervalue);


                ArrayList<PurchaseInvoiceDropDownModel> LastInvoices = helper.getLastInvoices(Spinnervalue);
                invoiceNOListAdapter = new PurchaseInvoiceNoListAdapter(PurchaseActivity.this, android.R.layout.simple_dropdown_item_1line, LastInvoices);
                Last3InvoiceNo.setAdapter(invoiceNOListAdapter);
                Last3InvoiceNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Toast.makeText(getApplicationContext(), "OnItemSelectedListener  :" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                        PurchaseInvoiceDropDownModel resval1 = (PurchaseInvoiceDropDownModel) parent.getItemAtPosition(position);
                        PurchaseOrderNUmber = resval1.getPurchaseOrderNo();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayList<PurchaseInvoiceDropDownModel> LastInvoices = helper.getInvoiceNumberForPurchase();
        invoiceNOListAdapter = new PurchaseInvoiceNoListAdapter(PurchaseActivity.this, android.R.layout.simple_dropdown_item_1line, LastInvoices);
        Holddata.setAdapter(invoiceNOListAdapter);
        Holddata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PurchaseInvoiceDropDownModel resval1 = (PurchaseInvoiceDropDownModel) parent.getItemAtPosition(position);
                HoldPurchaseorderNo = resval1.getPurchaseOrderNo();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OldPurchase.setVisibility(View.VISIBLE);
                NewPurchase.setVisibility(View.VISIBLE);
                submit.setVisibility(View.INVISIBLE);
                spinner.setVisibility(View.INVISIBLE);
                Last3InvoiceNo.setVisibility(View.INVISIBLE);
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                startActivity(intent);

            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public String toString() {
        return super.toString();
    }


    /**************************
     * Alll Calculation done here
     ***********************************************************************************************************************/

    public void setSummaryRow() {
        DecimalFormat f=new DecimalFormat("##.00");
        float Getval = productListAdapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        GrandTotal.setText(GrandVal);
        //  productListAdapter.notifyDataSetChanged();


        int Getitem = productListAdapter.getCount();
        int Allitem = Getitem;
        String GETITEM = Integer.toString(Allitem);
        Totalitems.setText(GETITEM);

       /* float GetValue=productListAdapter.TotalSaving();
        String Getsave=Float.toString(GetValue);
        Saving.setText(Getsave);
*/
    }

    public  void invoiceno() {
        Long Value = System.currentTimeMillis();
        final String result = Long.toString(Value);
        String invoicevalue = Billno.getText().toString();
        helper = new DBhelper(this);

        ArrayList<String> billno = helper.getimeino();
        for (String str : billno) {
            if (str.equals(invoicevalue))
            {
                ArrayList<String> imei = helper.getprefix(str);
                Log.e("%%%%%%%%%%%%%", imei.toString());
                x_imei=imei.toString();
                String x1=  x_imei.replace("[", "").replace("]","").concat(result);
                Log.e("X1_imei is :",x1);
                invoiceNo.setText(x1);
            } else {
                continue;
            }
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

            Intent i=new Intent(PurchaseActivity.this,Activitypurchase.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





   }