package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

import com.godbtech.sync.GBrowserNotifyMessageEvt;
import com.godbtech.sync.GNativeSync;
import com.godbtech.sync.GSyncServerConfig;
import com.godbtech.sync.GSyncStatusEvt;
import com.godbtech.sync.GSyncable;
import com.godbtech.sync.GUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapter.InventoryProductDropdownAdapter;
import Adapter.Inventorygrnadapter;
import Adapter.Inventorywithpoadapter;
import Adapter.PurchaseproductlistwithpoAdapter;
import Pojo.Inventorygrnmodel;
import Pojo.PurchaseProductModelwithpo;


public class activity_inventorywithpo extends Activity{
    public static final int MIN_LENGTH_OF_BARCODE_STRING = 13;
    public static final String BARCODE_STRING_PREFIX = "@";
  //Autocompletetext views
    AutoCompleteTextView InvProductidorName;
    AutoCompleteTextView autoCompleteTextView;
  //Adapter class
    PurchaseproductlistwithpoAdapter productListAdapter;
    Inventorygrnadapter grnnumberlistadapter;
    InventoryProductDropdownAdapter inventoryProductDropdownAdapter;
 //pojo class
    ArrayList<PurchaseProductModelwithpo> dropdownProductArrayList;
    ArrayList<PurchaseProductModelwithpo>  HoldInventoryList;
 //textwatcher
    private TextWatcher mTextWatcher;
    private TextWatcher ListTextWatcher;

    String x_imei,x1;
    String Store_Id;
    TextView vendorname;
    TextView Ponumbers;
    String userTypedInvoiceno;
    String Spinnervalue,HoldInventoryorderNo;
    EditText quantity;
    private TextView GrandTotal,Billno;
    private TextView Totalitems;
    ActionBar actionBar;
    Button update ,Holdinventorybutton;
    DBhelper helper;
    ArrayList<PurchaseProductModelwithpo> arrayList;
    Inventorywithpoadapter adapter;
    ListView listView;
    TelephonyManager tel;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));

//*********************************filter***************************************************************
    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
    private boolean    syncInProgress = false;
    private boolean    didSyncSucceed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_inventorywithpo);

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf


        update = (Button) findViewById(R.id.addtolist_button);
        Button clrbtn = (Button) findViewById(R.id.clear);
        vendorname=(TextView)findViewById(R.id.productvendor);
        Ponumbers=(TextView)findViewById(R.id.Po_numbers);
        tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Billno = (TextView) findViewById(R.id.sales_billno);
        Billno.setText(tel.getDeviceId().toString());
        Holdinventorybutton=(Button)findViewById(R.id.HoldInventorybill);
        GrandTotal = (TextView) findViewById(R.id.discount_textt10);
        Totalitems = (TextView) findViewById(R.id.discount_textt);
        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        helper = new DBhelper(this);
        autoCompleteTextView = (CustomAuto) findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(3);
        InvProductidorName = (CustomAuto) findViewById(R.id.InventoryautoProductIdandName);
        InvProductidorName.setThreshold(3);

        listView = (ListView) findViewById(R.id.listView);
        SelectPurchaseAlertDialog();
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
                arrayList = helper.getvendorsearch();
                // ArrayList<String>vendornames=helper.getvendorsearchforSpinner();
                if (arrayList != null) {
                    if (adapter == null) {
                        adapter = new Inventorywithpoadapter(activity_inventorywithpo.this, android.R.layout.simple_dropdown_item_1line, arrayList);
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
                String Value = parent.getItemAtPosition(position).toString();
            }
        });


        helper = new DBhelper(this);
        ListTextWatcher = new TextWatcher() {
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
                if (InvProductidorName.isPerformingCompletion()) {
                    Log.i("&&&&&&&&", "Performing completion and hence drop down will not be shown ");
                    return;
                }
                String userTypedString = InvProductidorName.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                if (userTypedString.length()<3) {
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
                    dropdownProductArrayList = helper.getProductdataforInventory(userTypedString.substring(1));
                    //dropdownProductArrayList = helper.getProductdata(userTypedString);
                    if(dropdownProductArrayList.size() == 1) {
                        //we have found the product
                        addProductToPurchaseList(dropdownProductArrayList.get(0));
                        return;
                    } else if( dropdownProductArrayList.size() < 1) {
                        //no product found
                      //  Toast.makeText(activity_inventorywithpo.this, "No Product found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    dropdownProductArrayList = helper.getProductdataforInventory(userTypedString);
                    if( dropdownProductArrayList.size() < 1) {
                        //no product found
                        dropdownProductArrayList.clear();
                     //   Toast.makeText(activity_inventorywithpo.this, "No Product found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (inventoryProductDropdownAdapter == null) {
                    inventoryProductDropdownAdapter = new InventoryProductDropdownAdapter(activity_inventorywithpo.this, android.R.layout.simple_dropdown_item_1line, dropdownProductArrayList);
                    inventoryProductDropdownAdapter.setList(dropdownProductArrayList);
                    InvProductidorName.setAdapter(inventoryProductDropdownAdapter);
                    InvProductidorName.setThreshold(3);
                }
                else {
                    inventoryProductDropdownAdapter.setList(dropdownProductArrayList);
                    inventoryProductDropdownAdapter.notifyDataSetChanged();
                }
            }

        };
        InvProductidorName.addTextChangedListener(ListTextWatcher);
        InvProductidorName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PurchaseProductModelwithpo resval1 = (PurchaseProductModelwithpo) parent.getItemAtPosition(position);
                addProductToPurchaseList(resval1);
                productListAdapter.setBatchdata(resval1);
            }

        });




        //********************************************submit button***************************************************8
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);
//
//                try {
                    Long Value = System.currentTimeMillis();
                    final String result = Long.toString(Value);
                    String invoicevalue = Billno.getText().toString();
                    ArrayList<String> billno = helper.getimeino();
                    for (String str : billno) {
                        if (str.equals(invoicevalue)) {
                            ArrayList<String> imei = helper.getprefix(str);
                            Log.e("%%%%%%%%%%%%%", imei.toString());
                            x_imei = imei.toString();
                            x1 = x_imei.replace("[", "").replace("]", "").concat(result);
                            Log.e("X1_imei is :", x1);
                        } else {
                            continue;
                        }
                    }
                    helper.saveInventorywithpo(productListAdapter.getList(), autoCompleteTextView.getText().toString(), Ponumbers.getText().toString(), x1);
                    helper.saveGranddataintoGrnMaster(x1, Ponumbers.getText().toString(), autoCompleteTextView.getText().toString(), GrandTotal.getText().toString());
                    helper.SavePdfDetailForInventorywithpo(x1, autoCompleteTextView.getText().toString());
                    //  helper.updatebatchnowithpo(productListAdapter.getList());
                    Toast.makeText(getApplicationContext(), x1.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                    startActivity(intent);
//
//                } catch (NullPointerException ex) {
//                    ex.printStackTrace();
//                } catch (IndexOutOfBoundsException ex) {
//                    ex.printStackTrace();
//                }
            }
        });
//********************************************hiddon button***************************************************8

        Holdinventorybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                helper = new DBhelper(activity_inventorywithpo.this);

                if (autoCompleteTextView.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Select Vendor Or Distributor Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productListAdapter.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Product Selected", Toast.LENGTH_SHORT).show();

                    return;
                }
                try {
                    Long Value = System.currentTimeMillis();
                    final String result = Long.toString(Value);
                    String invoicevalue = Billno.getText().toString();
                    ArrayList<String> billno = helper.getimeino();
                    for (String str : billno) {
                        if (str.equals(invoicevalue)) {
                            ArrayList<String> imei = helper.getprefix(str);
                            Log.e("%%%%%%%%%%%%%", imei.toString());
                            x_imei = imei.toString();
                            x1 = x_imei.replace("[", "").replace("]", "").concat(result);
                            Log.e("X1_imei is :", x1);
                        } else {
                            continue;
                        }
                    }

                    helper.saveInventoryholdbillwithpo(productListAdapter.getList(), autoCompleteTextView.getText().toString(), Ponumbers.getText().toString(), x1);
                    Toast.makeText(getApplicationContext(), x1.toString(), Toast.LENGTH_SHORT).show();

                    Intent syncIntent = new Intent(getApplicationContext(), com.mycompany.apps.Activitypurchase.class);
                    syncIntent.putExtras(syncDataBundle);


                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //********************************************cencel button***************************************************8
        clrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);

                try {

                    productListAdapter.clearAllRows();
                    autoCompleteTextView.setText("");
                    Ponumbers.setText("");
                    // Ponumbers.setAdapter(null);
                    setSummaryRow();
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


    private void addProductToPurchaseList(PurchaseProductModelwithpo resval1) {
        if (productListAdapter == null) {
            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
            productListAdapter = new PurchaseproductlistwithpoAdapter(activity_inventorywithpo.this, new ArrayList<PurchaseProductModelwithpo>(), android.R.layout.simple_dropdown_item_1line, resval1);
            listView.setAdapter(productListAdapter);
        }
      int pos=  productListAdapter.addProductToList(resval1);
        Log.i("&&&&&&&&", "Adding " + resval1 + " to Product List..");
        productListAdapter.notifyDataSetChanged();
        InvProductidorName.setText("");
        setSummaryRow();
        inventoryProductDropdownAdapter.setList(dropdownProductArrayList);
        dropdownProductArrayList.clear();
        listView.smoothScrollToPosition(pos);
    }

    //**************************************8alertbox***********************************************************
    public void SelectPurchaseAlertDialog() {

        LayoutInflater inflater = getLayoutInflater();

        final View alertLayout = inflater.inflate(R.layout.inventory_alert_dialog, null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final Button po = (Button) alertLayout.findViewById(R.id.po);
        final Button withoutpo = (Button) alertLayout.findViewById(R.id.withpo);
        final Button cancel = (Button) alertLayout.findViewById(R.id.cancelinventoryalert);
        final Button submit = (Button) alertLayout.findViewById(R.id.submitbtn);
        final Spinner spinner = (Spinner) alertLayout.findViewById(R.id.VendororDistributorName);

        final Button Hold=(Button)alertLayout.findViewById(R.id.HoldButton);
        final Button SubmitForHold=(Button)alertLayout.findViewById(R.id.submitbtnForHold);
        final Spinner Holddata=(Spinner)alertLayout.findViewById(R.id.HoldPurchaseNoforinventory);

        final Spinner Last3InvoiceNo = (Spinner) alertLayout.findViewById(R.id.Last3InvoiceNo);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);


        Last3InvoiceNo.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.VISIBLE);
        Hold.setVisibility(View.VISIBLE);
        Holddata.setVisibility(View.GONE);
        SubmitForHold.setVisibility(View.GONE);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        final AlertDialog dialog = alert.create();


        po.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                withoutpo.setVisibility(View.INVISIBLE);
                Last3InvoiceNo.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                SubmitForHold.setVisibility(View.GONE);
                Holddata.setVisibility(View.GONE);
                Hold.setVisibility(View.GONE);
            }
        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);

                Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                startActivity(intent);
            }
        });
        withoutpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);


                Intent intent = new Intent(getApplicationContext(), activity_inventory.class);
                startActivity(intent);

            }
        });


        Hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Holddata.setVisibility(View.VISIBLE);
                Last3InvoiceNo.setVisibility(View.GONE);
                po.setVisibility(View.GONE);
                withoutpo.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);
                SubmitForHold.setVisibility(View.VISIBLE);
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);

                Spinnervalue = spinner.getSelectedItem().toString();
                userTypedInvoiceno = Last3InvoiceNo.getSelectedItem().toString().trim();
                Log.e("!!!!!!!!!!!", "" + Last3InvoiceNo);
                if (userTypedInvoiceno.equals("")) {
                    return;
                }

                ArrayList<PurchaseProductModelwithpo> alldata = helper.getPurchaseProductdata(userTypedInvoiceno);
                //   PurchaseProductModelwithpo resval1 = (PurchaseProductModelwithpo) parent.getItemAtPosition(position);
                Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                productListAdapter = new PurchaseproductlistwithpoAdapter(activity_inventorywithpo.this, new ArrayList<PurchaseProductModelwithpo>(), android.R.layout.simple_dropdown_item_1line, null);
                listView.setAdapter(productListAdapter);

                for (PurchaseProductModelwithpo prod : alldata) {
                    productListAdapter.addProductToList(prod);
                    productListAdapter.setBatchdata(prod);
                }
                productListAdapter.notifyDataSetChanged();
                //autoCompleteTextView.setText("");
                setSummaryRow();
                autoCompleteTextView.setText("" + spinner.getSelectedItem().toString() + "");
                autoCompleteTextView.removeTextChangedListener(mTextWatcher);

                Ponumbers.setText(userTypedInvoiceno);
                //  Log.i("&&&&&&&&!", "Adding " + OldPurchaseArraylist.get(0) + " to Product List..");
                productListAdapter.notifyDataSetChanged();
                setSummaryRow();
                autoCompleteTextView.setEnabled(false);
                dialog.dismiss();


            }
        });
        arrayList = helper.getvendorsearch();
        ArrayAdapter<PurchaseProductModelwithpo> stringArrayAdapter =
                new ArrayAdapter<PurchaseProductModelwithpo>(activity_inventorywithpo.this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        spinner.setAdapter(stringArrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(), "OnItemSelected  :" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                String Spinnervalue = spinner.getSelectedItem().toString();
                Log.e("*************", Spinnervalue);
                ArrayList<String> LastInvoices = helper.getPo_numbers(Spinnervalue);
                ArrayAdapter<String> InvoiceNoAdapter =
                        new ArrayAdapter<String>(activity_inventorywithpo.this, android.R.layout.simple_spinner_dropdown_item, LastInvoices);
                Last3InvoiceNo.setAdapter(InvoiceNoAdapter);
                Last3InvoiceNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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




        SubmitForHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (HoldInventoryorderNo == null)
                    {
                        Toast.makeText(getApplicationContext(), "No Hold Item Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    HoldInventoryList = helper.getholddataforinventory(HoldInventoryorderNo);
                    if (HoldInventoryList != null && HoldInventoryList.size() > 0) {
                        if (productListAdapter == null) {
                            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                            productListAdapter = new PurchaseproductlistwithpoAdapter(activity_inventorywithpo.this, new ArrayList<PurchaseProductModelwithpo>(), android.R.layout.simple_dropdown_item_1line, null);
                            listView.setAdapter(productListAdapter);
                        }
                        for (PurchaseProductModelwithpo prod : HoldInventoryList) {
                            productListAdapter.addProductToList(prod);
                            // Ponumbers.findViewById(R.id.Po_numbers);
                        }

                        autoCompleteTextView.setText(HoldInventoryList.get(0).getVendorName());
                        // Ponumbers.setText(HoldInventoryList.get(0).getPurchaseno());
                        //   autoCompleteTextView.setText("" + Holddata.getSelectedItem().toString() + "");
                        helper.Updateflag(HoldInventoryorderNo);
                        autoCompleteTextView.removeTextChangedListener(mTextWatcher);
                        autoCompleteTextView.setEnabled(false);
                        Log.i("&&&&&&&&!", "Adding " + HoldInventoryList.get(0) + " to Product List..");
                        productListAdapter.notifyDataSetChanged();
                        setSummaryRow();
                        dialog.dismiss();


                    }

            }
        });


        ArrayList<Inventorygrnmodel> LastInvoices = helper.getgrnNumberForinventory();
        grnnumberlistadapter = new Inventorygrnadapter(activity_inventorywithpo.this, android.R.layout.simple_dropdown_item_1line, LastInvoices);
        Holddata.setAdapter(grnnumberlistadapter);
        Holddata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Inventorygrnmodel resval1 = (Inventorygrnmodel) parent.getItemAtPosition(position);
                HoldInventoryorderNo = resval1.getInventoryOrderNo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

    }


    public void setSummaryRow() {
        DecimalFormat f=new DecimalFormat("##.00");
        float Getval = productListAdapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        GrandTotal.setText(GrandVal);

        int Getitem = productListAdapter.getCount();
        int Allitem = Getitem;
        String GETITEM = Integer.toString(Allitem);
        Totalitems.setText(GETITEM);
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


            Intent i = new Intent(activity_inventorywithpo.this, Activitypurchase.class);
            startActivity(i);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }



}
