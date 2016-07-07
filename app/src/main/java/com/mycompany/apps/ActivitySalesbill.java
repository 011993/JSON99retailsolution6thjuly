package com.mycompany.apps;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
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
import java.util.List;
import Adapter.CreditNoteAdapter;
import Adapter.CustomerSalesAdapter;
//import Adapter.HoldBillAdapter;
import Adapter.SalesAdapter;
import Adapter.SalesProductNmAdaptor;
import Adapter.TabsPagerAdapter;
import Fragments.FastMovingFragment;
import Pojo.CreditNote;
import Pojo.Customer;
import Pojo.DoctorPojo;
import Pojo.Sales;
import Pojo.Salesreturndetail;


public class ActivitySalesbill extends FragmentActivity implements View.OnClickListener,
        ActionBar.TabListener {
    EditText BarcodeScan;
    DBhelper db;
    LinearLayout container;
    ArrayList<Sales> salearrayList;
    ArrayList<Sales>GetAllhold;
    SalesAdapter adapter;
    ListView listView;
    TextView enteramt;
    Spinner doctor;
    String item;
    ScrollView scrollView1;
    ArrayList<Sales> holdinvoicelist;
     ArrayList<String>doctorname;

    TextView Grandtotldialog;



    public static final int MIN_LENGTH_OF_BARCODE_STRING = 13;
    public static final String BARCODE_STRING_PREFIX = "@";


    private TextWatcher salesTextWatcher;
    private TextWatcher barcodeTextWatcher;
    private TextWatcher  ProductNameTextWatcher,MrpTextwatcher,SpriceTextwatcher;
    AutoCompleteTextView ProductName;
    SalesProductNmAdaptor productNmAdaptor;
    ArrayList<Sales>ProductNameArrayList;
    ArrayList<Sales>SalesBarcodearraylist;
    ArrayList<Sales>holdarraylist;
    CustomerSalesAdapter customerSalesAdapter;
    ArrayList<Customer>customerlist;
    EditText reciveamt;
    //HoldBillAdapter holdBillAdapter;
    TextView expectedchang,creditnoteretrieve;
    CreditNoteAdapter creditNoteAdapter;
    private TextWatcher cTextWatcher;
    ArrayList<CreditNote>CreditNotelist;
    String Store_Id;
    TextView fragmarquetv;
    String x_imei;
    TextView Totalsavings;


    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;

    Button search,clear,hold;


    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    Button cashbt, chequebt, cardbt, creditnote;

    private TextView GrandTotal,Transid,Billno,PosCust;
    private  TextView Totalitems;
    private  Button paybycash;
    TelephonyManager tel;
    TextView Sellinprice;
    Spinner holdspinner;
    TextView Mrp;
    EditText Quantity;
    // Tab titles
    private String[] tabs = {"Fast Moving"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_salesbill);
        try {
      /*  fragmarquetv=(TextView)this.findViewById(R.id.comingsoon);
        fragmarquetv.setEllipsize(TextUtils.TruncateAt.START);

        fragmarquetv.setEllipsize(TextUtils.TruncateAt.MARQUEE);

        fragmarquetv.setMarqueeRepeatLimit(1000000);
        fragmarquetv.setSelected(true);
        fragmarquetv.setSingleLine(true);

//*/

            TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf


            Billno = (TextView) findViewById(R.id.sales_billno);
            TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = tel.getDeviceId();
            Log.e("imei is :", device_id);

            Billno.setText(device_id);
            db = new DBhelper(this);
            listView = (ListView) findViewById(R.id.listView);

            Totalitems = (TextView) findViewById(R.id.totalitem_textt);
            GrandTotal = (TextView) findViewById(R.id.grandtotal_textt);
            PosCust = (TextView) findViewById(R.id.sale_customer);
            paybycash = (Button) findViewById(R.id.paycash);
            //reciveamt=(EditText)findViewById(R.id.reciveamount);
            expectedchang = (TextView) findViewById(R.id.expectedchange);
            Transid = (TextView) findViewById(R.id.sales_invoiceno);
            ProductName = (CustomAuto) findViewById(R.id.autoProductName);
            creditnoteretrieve = (TextView) findViewById(R.id.creditnote);
            Sellinprice = (TextView) findViewById(R.id.sprice);
            Mrp = (TextView) findViewById(R.id.mrp);
            Quantity = (EditText) findViewById(R.id.quantity);
            hold = (Button) findViewById(R.id.hold);
            Totalsavings = (TextView) findViewById(R.id.totalsavings_textt);
            container = (LinearLayout) findViewById(R.id.container);
            creditnote = (Button) findViewById(R.id.creditnotesbutton);
            doctor = (Spinner) findViewById(R.id.doctorname);
            holdspinner = (Spinner) findViewById(R.id.holdsalebill);
            clear = (Button) findViewById(R.id.clear);
            scrollView1 = (ScrollView) findViewById(R.id.scrollView);

            //  Typeface roboto = Typeface.createFromAsset(getApplicationContext().getAssets(),"font/Roboto-Regular.ttf"); //use this.getAssets if you are calling from an Activity
            //  PosCust.setTypeface(roboto);


            actionBar = getActionBar();
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setIcon(R.drawable.w);
            invoiceno();
            final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
            db = new DBhelper(this);
            clear.setOnClickListener(
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            v.startAnimation(Buttonok);

                            try{
                                if (adapter.getList().isEmpty())
                                {
                                    return;
                                }

                            adapter.clearAllRows();
                            invoiceno();
                            PosCust.setText("");

                            GrandTotal.setText(".0");
                            Totalitems.setText("0");
                                Totalsavings.setText(".0");
                            reciveamt.setText(".0");
                            setSummaryRow();
                            }catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });

      /*  ProductName.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    setFocusOnBarcodeView();
                    ProductName.setText("");
                    return true;
                }
                return false;
            }
        });*/


            listView.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    return (event.getAction() == MotionEvent.ACTION_MOVE);
                }
            });

            Log.v("&&&&&&&&&&", "AutoCompletetextview for Productname initilised");
            ProductName.setThreshold(2);
////////////////////***************Search Through ProductName********************///////////////

            db = new DBhelper(this);
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
                    if (userTypedString.length() < 3) {
                        return;
                    }


                    if (userTypedString.startsWith(BARCODE_STRING_PREFIX)) {
                        if (ProductNameArrayList != null) {
                            ProductNameArrayList.clear();
                        }

                        //this is a barcode generated string
                        if (userTypedString.length() <= MIN_LENGTH_OF_BARCODE_STRING) {
                            //ignore all strings of length < 13
                            return;
                        }
                        ProductNameArrayList = db.getProductNamedata(userTypedString.substring(1));
                        //dropdownProductArrayList = helper.getProductdata(userTypedString);
                        if (ProductNameArrayList.size() == 1) {
                            //we have found the product
                            AddSalesProducttoList(ProductNameArrayList.get(0));
                            return;
                        } else if (ProductNameArrayList.size() < 1) {
                            //no product found
                            //   Toast.makeText(ActivitySalesbill.this, "No Product found", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        ProductNameArrayList = db.getProductNamedata(userTypedString);
                        if (ProductNameArrayList.size() < 1) {
                            ProductNameArrayList.clear();
                            //no product found
                            // Toast.makeText(ActivitySalesbill.this, "No Product found", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (ProductNameArrayList != null) {
                        if (productNmAdaptor == null) {
                            productNmAdaptor = new SalesProductNmAdaptor(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, ProductNameArrayList);
                            productNmAdaptor.setList(ProductNameArrayList);
                            ProductName.setAdapter(productNmAdaptor);
                            ProductName.setThreshold(2);
                        } else {
                            productNmAdaptor.setList(ProductNameArrayList);
                            productNmAdaptor.notifyDataSetChanged();
                        }
                    }

                }
            };

            ProductName.addTextChangedListener(ProductNameTextWatcher);
            ProductName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //  Cursor curral=(Cursor)parent.getItemAtPosition(position);
                    // PurchaseProductModel resval1=(PurchaseProductModel) parent.getItemAtPosition(position);
                    Sales result = (Sales) parent.getItemAtPosition(position);
                    AddSalesProducttoList(result);
                }
            });
            creditnote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreditnotesAlertDialog();

                }
            });
            final DBhelper mydb = new DBhelper(ActivitySalesbill.this);
            doctorname = mydb.getAllDoctorsforsale();
            final ArrayAdapter<String> stringArrayAdapter =
                    new ArrayAdapter<String>(ActivitySalesbill.this, android.R.layout.simple_spinner_dropdown_item, doctorname);
            doctor.setAdapter(stringArrayAdapter);
            doctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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

            //  final DBhelper =new DBhelper(ActivitySalesbill.this);


      /*  barcodeTextWatcher = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("Sudhee", "on text changed called" + s);
            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.i("&&&&&&&&", "After text changed called and text value is " + s.toString());
                String barcodeFromScanner = BarcodeScan.getText().toString().trim();
                if (barcodeFromScanner.equals("")) {
                    return;
                }
                SalesBarcodearraylist= db.getSalesDetailsFromBarcode(barcodeFromScanner);
                Log.d("Sudhee", "Product arraylist size is " + SalesBarcodearraylist.size());
                if (SalesBarcodearraylist != null && SalesBarcodearraylist.size() == 1) {
                    if (adapter== null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        adapter = new SalesAdapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<Sales>());
                        listView.setAdapter(adapter);
                    }
                    adapter.addProductToList(SalesBarcodearraylist.get(0));
                    Log.i("&&&&&&&&!", "Adding " + SalesBarcodearraylist.get(0) + " to Product List..");
                    adapter.notifyDataSetChanged();
                    setSummaryRow();
                    BarcodeScan.setText("");
                    BarcodeScan.setVisibility(View.VISIBLE);
                    setFocusOnBarcodeView();

                }
            }
        };
        BarcodeScan.addTextChangedListener(barcodeTextWatcher);
*/

       /* salesTextWatcher=new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        reciveamt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    double recieveamount = Double.parseDouble(reciveamt.getText().toString());
                    if (recieveamount == 0.0f) {
                        expectedchang.setText("");
                        return;
                    }

                    Editable editableText1 = reciveamt.getText();
                    double value1 = 0, result, total;


                    if (editableText1 != null && editableText1.length() >= 1)
                        value1 = Double.parseDouble(editableText1.toString());
                    float Getval = adapter.getGrandTotal();
                    Log.d("&&&&&&&&", "" + Getval);

                    DecimalFormat f=new DecimalFormat("##.0");
                    String GrandVal = f.format(Getval);

                    GrandTotal.setText(GrandVal);

                    Log.d("**!!!!!!!*****", "" + GrandVal);
                    Log.d("***!!!!!!****", "" + value1);
                    result = value1 - Getval;
                    String totalres= f.format(result);
                    Log.d("*******", "" + result);
                    expectedchang.setText((totalres));
                    Log.d("$$$$$$$$$$$", "" + expectedchang);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

*/
            paybycash.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if (adapter.getList().isEmpty()) {
                                    return;
                                }

                                HotkeyAlertDialog();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });


            final DBhelper db = new DBhelper(ActivitySalesbill.this);
            final ArrayList<String> holdbilltransaction = mydb.getTransidofholdbill();
            final ArrayAdapter<String> holdbilladapter =
                    new ArrayAdapter<String>(ActivitySalesbill.this, android.R.layout.simple_spinner_dropdown_item, holdbilltransaction);
            holdspinner.setAdapter(holdbilladapter);

            holdspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String holdspinneritem = holdspinner.getSelectedItem().toString();
                    final ArrayList<String> holdbilltransaction = mydb.getTransidofholdbill();
                    for (String str : holdbilltransaction) {

                        if (str.equals(holdspinneritem)) {

                            holdinvoicelist = mydb.getallholdinvoicedata(holdspinneritem);

                            if (holdinvoicelist != null && holdinvoicelist.size() > 0) {
                                if (adapter == null) {
                                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                                    adapter = new SalesAdapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, holdinvoicelist);
                                    listView.setAdapter(adapter);
                                }
//                           else {
                                adapter.setsalearrayList(holdinvoicelist);
                                adapter.notifyDataSetChanged();
                            }
                            setSummaryRow();
                        }
                    }
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });
            hold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        final String Valuetrans = Transid.getText().toString();

//                   // LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                   // final View addView = layoutInflater.inflate(R.layout.button_sales, null);
////                    final String receivedata = Transid.getText().toString();
////                    String output = receivedata.substring(13,16);
                        db.tempsavesalesListdetail(Valuetrans, adapter.getList());
                        db.updateStockQty(adapter.getList());
                        invoiceno();
                        GrandTotal.setText("");
                        Totalitems.setText("");
                        adapter.clearAllRows();
                        Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                        startActivity(intent);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                }
            });
//
////                    Button buttonRemove = (Button) addView.findViewById(R.id.remove);

////                    GetAllhold = new ArrayList<Sales>();
////                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
////                    adapter = new SalesAdapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, GetAllhold);
////                    listView.setAdapter(adapter);


//
////                    buttonRemove.setOnClickListener(new View.OnClickListener() {
////
////                        @Override
////
////                        public void onClick(View v) {
////                            ((LinearLayout) addView.getParent()).removeView(addView);
////
////                            ArrayList<String> transid = db.getTransidofholdbill();
////                            for (String str : transid) {
////                                if (str.equals(Valuetrans)) {
////                                    holdarraylist = db.getallholdinvoicedata(Valuetrans);
////                                    Log.d("nishantsinghhold", "Product arraylist size is " + holdarraylist.size());
////                                    adapter = new SalesAdapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, holdarraylist);
////                                    listView.setAdapter(adapter);
////
////                                } else  {
////
////                                    break;
////                                }
////
////                            }   adapter.notifyDataSetChanged();
////
////                        }
////                    });
////                    container.addView(addView);
////                    db = new DBhelper(ActivitySalesbill.this);
//                    //  db.tempsavesalesListdetail(Valuetrans, adapter.getList());

//                    // Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_SHORT).show();
//
//                    loadSyncLibrary();
            //   doSync(syncDataBundle);


            search = (Button) findViewById(R.id.search_button);
        /*add.setOnClickListener(this);*/
        /*chequebt = (Button) findViewById(R.id.chequebutton);
        chequebt.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                ChequeAlertDialog();

            }


        });
*/
        /*cashbt = (Button) findViewById(R.id.cashbutton);
        cashbt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CashAlertDialog();

                    }
                });*/


       /* cardbt = (Button) findViewById(R.id.cardbutton);
        cardbt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CardAlertDialog();

                    }
                });*/

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(Buttonok);
                    SearchCustomerDialog();


                }
            });
            // Initilization
            viewPager = (ViewPager) findViewById(R.id.pager);
            actionBar = getActionBar();
            mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

            viewPager.setAdapter(mAdapter);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            // Adding Tabs
            for (String tab_name : tabs) {
                actionBar.addTab(actionBar.newTab().setText(tab_name)
                        .setTabListener(this));
            }

            /**
             * on swiping the viewpager make respective tab selected
             * */
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    // on changing the page
                    // make respected tab selected
                    actionBar.setSelectedNavigationItem(position);
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                }
            });
        } catch (Exception e)
        {e.printStackTrace();}
    }
    private void AddSalesProducttoList(Sales result) {
        if (adapter == null) {
            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
            adapter = new SalesAdapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line,new ArrayList<Sales>());
        listView.setAdapter(adapter);
            ScrollviewSales.getListViewSize(listView);
        }
       int pos= adapter.addProductToList(result);
        Log.i("&&&&&&&&", "Adding " + result + " to Product List..");
        adapter.notifyDataSetChanged();
        ProductName.setText("");
        setSummaryRow();
        ProductNameArrayList.clear();
        listView.smoothScrollToPosition(pos);
    }


    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            case R.id.addtolist_button:
//                Intent intent = new Intent(this, Additems.class);
//                startActivity(intent);
//                break;
//            case R.id.search_button:
//                Intent intent1 = new Intent(this,ActivityCustomerSales.class);
//                startActivity(intent1);
//                break;


        }

    }

    public  void invoiceno() {
        Long Value = System.currentTimeMillis();
        final String result = Long.toString(Value);
        String invoicevalue = Billno.getText().toString();
        db = new DBhelper(this);

        ArrayList<String> billno = db.getimeino();
        for (String str : billno) {
            if (str.equals(invoicevalue))
            {
                ArrayList<String> imei = db.getprefix(str);
                Log.e("%%%%%%%%%%%%%", imei.toString());
                x_imei=imei.toString();
                String x1=  x_imei.replace("[", "").replace("]","").concat(result);
                Log.e("X1_imei is :",x1);
                Transid.setText(x1);
            } else {
                continue;
            }
        }
    }

    public void  CustomerAlertDialog()
    {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alertcustomer_sales, null);
    }
    private void CreditnotesAlertDialog() {


        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.creditnote_dialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(ActivitySalesbill.this);
        final Button cleardialog = (Button) alertLayout.findViewById(R.id.clear_dialog_button);
        final Button addtopay = (Button) alertLayout.findViewById(R.id.addtopayment_button);
        final TextView creditvalue = (TextView)alertLayout.findViewById(R.id.creditamount_tv);
        final AutoCompleteTextView billno = (CustomAuto) alertLayout.findViewById(R.id.invoiceno);
        final AlertDialog dialog = alert.create();
        dialog.setView(alertLayout);
        dialog.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
        dialog.show();



        db = new DBhelper(this);
        billno.setThreshold(1);
        cTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//        addtopay.setOnClickListener(new View.OnClickLi
//
// stener() {
//            @Override
//            public void onClick(View v) {
                try {
                    if (billno.isPerformingCompletion()) {
                        Log.d("Debuging", "Performing completion ");
                        return;
                    }
                    String userTypedInvoiceno = billno.getText().toString().trim();
                    Log.e("!!!!!!!!!!!", "" + billno.toString());
                    if (userTypedInvoiceno.equals("")) {
                        return;
                    }
                    CreditNotelist = db.getcreditno(userTypedInvoiceno);
                    Log.d("!@#!@#!@#!@#", "creditnote arraylist size is " + CreditNotelist.size());

                    if (CreditNotelist != null && CreditNotelist.size() > 0) {
                        if (creditNoteAdapter == null) {
                            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                            creditNoteAdapter = new CreditNoteAdapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, CreditNotelist);
                            creditNoteAdapter.setCreditNotelist(CreditNotelist);

                            billno.setAdapter(creditNoteAdapter);
                            billno.setThreshold(1);

                        } else {
                            creditNoteAdapter.setCreditNotelist(CreditNotelist);
                            creditNoteAdapter.notifyDataSetChanged();
                            billno.setAdapter(creditNoteAdapter);
                            billno.setThreshold(1);
                        }
                        }
                    }catch(NullPointerException ex){
                        ex.printStackTrace();
                    }
                }

        };
        billno.removeTextChangedListener(cTextWatcher);
        billno.addTextChangedListener(cTextWatcher);
        billno.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                Log.d("Debuging", "On click called ");

                final DBhelper mydb = new DBhelper(ActivitySalesbill.this);

                CreditNote value = (CreditNote) parent.getItemAtPosition(pos);
                creditvalue.setText(value.getCreditnotevalue());

                billno.setText("");


            }
        });
        addtopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String crt = creditvalue.getText().toString();
                creditnoteretrieve.setText(crt);
                dialog.dismiss();
                calculatecreditnote();
            }
        });
        cleardialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }



    public  void calculatecreditnote(){

        try {
            Float result;
            Float Getval = Float.parseFloat(creditnoteretrieve.getText().toString());
            Float value = Float.parseFloat(GrandTotal.getText().toString());
            result = value - Getval;
            GrandTotal.setText(result.toString());
        }catch (NumberFormatException ex)
        {ex.printStackTrace();
        }catch (NullPointerException ex)
        {ex.printStackTrace();
        }
    }
    private void SearchCustomerDialog() {

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.search_customer_dialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(ActivitySalesbill.this);
        final AutoCompleteTextView searchauto = (CustomAuto) alertLayout.findViewById(R.id.myautocomplete);
        final Button addnewcustomer = (Button) alertLayout.findViewById(R.id.Cust_ok_button);
        final LinearLayout hidden = (LinearLayout) alertLayout.findViewById(R.id.Hiddenlayout);
        final TextView CustomerMobile = (TextView) alertLayout.findViewById(R.id.Cust_mobileno1);
        final Button update = (Button) alertLayout.findViewById(R.id.Cust_update_button);
        final Button clear = (Button) alertLayout.findViewById(R.id.Cust_clear_button);
        final Button exit = (Button) alertLayout.findViewById(R.id.Cust_Exit_button);
        final Button exitcust=(Button)alertLayout.findViewById(R.id.exit);
        final LinearLayout hidden2 = (LinearLayout) alertLayout.findViewById(R.id.Hiddenlayout1);
        final LinearLayout hidden3 = (LinearLayout) alertLayout.findViewById(R.id.autolayout);
        final LinearLayout buttonlayout = (LinearLayout) alertLayout.findViewById(R.id.buttons_layout);


        final TextView CUSTOMERNAME = (TextView) alertLayout.findViewById(R.id.Cust_name);
        final TextView CUSTOMERMOBILE = (TextView) alertLayout.findViewById(R.id.Cust_mobileno);
        final TextView CUSTOMEREMAIL = (TextView) alertLayout.findViewById(R.id.Cust_email);


        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        final AlertDialog dialog = alert.create();
        buttonlayout.setVisibility(View.INVISIBLE);

        dialog.setView(alertLayout);
        dialog.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
        dialog.show();


        db = new DBhelper(this);
        // final AutoCompleteTextView autoCompleteTextView = (CustomAuto) alertLayout.findViewById(R.id.myautocomplete);
        searchauto.setThreshold(1);


        TextWatcher mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("Debuging", "After text changed called ");
                if (searchauto.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = searchauto.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                customerlist = db.getAllCustomers(userTypedString);

                if (customerlist != null) {
                    if (customerSalesAdapter == null) {
                        customerSalesAdapter = new CustomerSalesAdapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, customerlist);
                        customerSalesAdapter.setCustomerList(customerlist);
                        searchauto.setAdapter(customerSalesAdapter);
                        searchauto.setThreshold(1);

                    } else {

                        customerSalesAdapter.setCustomerList(customerlist);
                        customerSalesAdapter.notifyDataSetChanged();
                        searchauto.setAdapter(customerSalesAdapter);
                        searchauto.setThreshold(1);
                    }
                }
            }
        };
        searchauto.removeTextChangedListener(mTextWatcher);
        searchauto.addTextChangedListener(mTextWatcher);
        searchauto.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                Log.d("Debuging", "On click called ");

                final DBhelper mydb = new DBhelper(ActivitySalesbill.this);

                Customer value = (Customer) parent.getItemAtPosition(pos);


               // Customername.setText(value.getCustomername());
                CustomerMobile.setText(value.getCustomername());
                String res = CustomerMobile.getText().toString();
                PosCust.setText(res);
                searchauto.setText("");
                customerlist.remove(pos);
                customerSalesAdapter.notifyDataSetChanged();

                dialog.dismiss();
                // doTheAutoRefresh();

            }
        });
//        customerSalesAdapter.notifyDataSetChanged();




        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                CUSTOMEREMAIL.setText("");
                CUSTOMERNAME.setText("");
                CUSTOMERMOBILE.setText("");

            }
        });

        exitcust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBhelper db = new DBhelper(ActivitySalesbill.this);

                String res = CUSTOMERNAME.getText().toString();
                PosCust.setText(res);


                if (CUSTOMERMOBILE.getText().toString().matches("")) {
                    Toast toast = Toast.makeText(ActivitySalesbill.this, "PLEASE FILL THE FIELD", Toast.LENGTH_SHORT);
                    toast.show();
                    return;

                }

                if (db.CheckIsDataAlreadyInDBorNot(CUSTOMERMOBILE.getText().toString()))

                {
                    Toast toast1 = Toast.makeText(ActivitySalesbill.this, "MOBILE NO ALREADY REGISTERED", Toast.LENGTH_SHORT);
                    toast1.show();
                    return;
                } else if (db.insertCustomer(new Customer(CUSTOMERMOBILE.getText().toString(),
                        CUSTOMERNAME.getText().toString(), CUSTOMEREMAIL.getText().toString())))

                {
                    Toast toast = Toast.makeText(ActivitySalesbill.this, "CUSTOMER ADDED", Toast.LENGTH_SHORT);
                    toast.show();
                    dialog.dismiss();



                }

            }


        });


        addnewcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(Buttonok);

                hidden2.setVisibility(View.INVISIBLE);
                hidden.setVisibility(View.VISIBLE);
                hidden3.setVisibility(View.INVISIBLE);
                buttonlayout.setVisibility(View.VISIBLE);
            }

        });


    }
    public void CashAlertDialog() {

        LayoutInflater inflater = getLayoutInflater();
        View alertlayout = inflater.inflate(R.layout.cashpayment_dialog,null);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertlayout);
        final AlertDialog dialog = alert.create();
        dialog.show();
        setcashdetail();

    }

    public void setcashdetail() {


        float Getval = adapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = Float.toString(Getval);
        // TextView textView = (TextView) findViewById(R.id.enteramount);
        //   textView.setText(GrandVal);


    }




    public void CardAlertDialog() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.cardpayment_dialog, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        DatePicker picker = new DatePicker(this);
        picker.setCalendarViewShown(true);

        alert.setView(alertLayout);
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void ChequeAlertDialog() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.checquepayment_dialog, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        DatePicker picker = new DatePicker(this);
        picker.setCalendarViewShown(true);

        alert.setView(alertLayout);
        AlertDialog dialog = alert.create();
        dialog.show();
    }
    public void setSummaryRow() {

        DecimalFormat f=new DecimalFormat("##.00");
        float Getval = adapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        GrandTotal.setText(GrandVal);
        Log.d("@@@@@@@@@@", "" + GrandTotal);



       // reciveamt.setText("");
       // expectedchang.setText("");
        int Getitem=adapter.getCount();
        int Allitem=Getitem;
        String GETITEM=Integer.toString(Allitem);
        Totalitems.setText(GETITEM);

        float Gettotalsavings = adapter.gettotalsavings();
        Log.d("&&&&&&&&", "" + Getval);
        String Grandtotalsaving = f.format(Gettotalsavings);
        Totalsavings.setText(Grandtotalsaving);
        Log.d("@@@@@@@@@@", "" + Totalsavings);


    }





    public void fragmentdata() {
/* String shilpa="Hello";
 textView.setText(shilpa);*/
        String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";

        DBhelper db = new DBhelper(this);

        FastMovingFragment fragobj = new FastMovingFragment();

        String value = (String) fragobj.myBundle.get("id_User");
        try {

            String userTypedCaption = value.toString();
            Log.e("!!!!!!!!!!!", "" + value.toString());
            if (userTypedCaption.equals("")) {
                return;
            }


            ArrayList<Sales> fragmentdatalist = db.getAllTopProductDetails(userTypedCaption);
            if (fragmentdatalist != null) {
                if (adapter == null) {
                    adapter = new SalesAdapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, fragmentdatalist);
                    //adapter.setsalearrayList(fragmentdatalist);
                    listView.setAdapter(adapter);

                }

                adapter.addProductToList(fragmentdatalist.get(0));
                //adapter.setsalearrayList(fragmentdatalist);
                adapter.notifyDataSetChanged();
                setSummaryRow();

            }
        }catch (Exception npe){
            npe.printStackTrace();
        }

    }




    /*final Handler requestFocusHandler = new Handler();
    private void setFocusOnBarcodeView() {
        requestFocusHandler.post( new Runnable() {

            @Override
            public void run() {
                ProductName.setFocusable(true);
                ProductName.setFocusableInTouchMode(true);
                ProductName.requestFocus();
            }
        });

        requestFocusHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                ProductName.setFocusable(true);
                ProductName.setFocusableInTouchMode(true);
                ProductName.requestFocus();
            }
        }, 1000);
    }*/




    public void HotkeyAlertDialog() {

        LayoutInflater inflater = getLayoutInflater();

        final View alertLayout = inflater.inflate(R.layout.keyboard_popup_salesbill, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Button submit = (Button) alertLayout.findViewById(R.id.paycash);
        final TextView grandtotal=(TextView)alertLayout.findViewById(R.id.grandtot_ed);
        final EditText reciveamt=(EditText)alertLayout.findViewById(R.id.reciveamountdialog);
        final TextView expectedchang=(TextView)alertLayout.findViewById(R.id.expectedchange);
        final Button close=(Button)alertLayout.findViewById(R.id.close);
        submit.setVisibility(View.VISIBLE);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        alert.setTitle("");
        final AlertDialog dialog = alert.create();

        String res = GrandTotal.getText().toString();
        grandtotal.setText(res);

        if (adapter.getList().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill field", Toast.LENGTH_SHORT).show();
            return;
        }
        salesTextWatcher=new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        reciveamt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    double recieveamount = Double.parseDouble(reciveamt.getText().toString());
                    if (recieveamount == 0.00) {
                        expectedchang.setText("");
                        return;
                    }

                    Editable editableText1 = reciveamt.getText();
                    double value1 = 0.00, result, total;


                    if (editableText1 != null && editableText1.length() >= 1)
                        value1 = Double.parseDouble(editableText1.toString());
                    float Getval = adapter.getGrandTotal();
                    Log.d("&&&&&&&&", "" + Getval);

                    DecimalFormat f = new DecimalFormat("##.00");
                    String GrandVal = f.format(Getval);

                    // GrandTotal.setText(GrandVal);

                    Log.d("**!!!!!!!*****", "" + GrandVal);
                    Log.d("***!!!!!!****", "" + value1);
                    result = value1 - Getval;
                    String totalres = f.format(result);
                    Log.d("*******", "" + result);
                    expectedchang.setText((totalres));
                    Log.d("$$$$$$$$$$$", "" + expectedchang);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          DBhelper mydb = new DBhelper(ActivitySalesbill.this);
                                          String Valuetrans = Transid.getText().toString();
                                          try {
                                              double selling = Double.parseDouble(reciveamt.getText().toString());
                                              double purchase = Double.parseDouble(GrandTotal.getText().toString());
                                              if (selling < purchase) {
                                                  Toast.makeText(getApplicationContext(), "Please enter Amount Received > = Sales Value", Toast.LENGTH_SHORT).show();
                                                  return;
                                              }
                                              if (adapter.getList().isEmpty()) {
                                                  Toast.makeText(getApplicationContext(), "Please fill field", Toast.LENGTH_SHORT).show();
                                                  return;
                                              }
                                              db.savesalesListdetail(Valuetrans, adapter.getList());
                                              db.insertdetailsifpaybaycash(Valuetrans, GrandTotal.getText().toString());
                                              db.updateStockQty(adapter.getList());
                                              db.insertdataIntosendMailforSales(Transid.getText().toString(), PosCust.getText().toString());
                                              Toast.makeText(getApplicationContext(), Transid.getText().toString(), Toast.LENGTH_SHORT).show();

                                              adapter.clearAllRows();
                                              GrandTotal.setText("");
                                              Totalitems.setText("");
                                              reciveamt.setText("");
                                              Totalsavings.setText("");
                                              adapter.clearAllRows();
                                              PosCust.setText("");
                                              invoiceno();
                                              doctorname.clear();
                                              PosCust.setText("");
                                              doctorname = mydb.getAllDoctorsforsale();
                                              final ArrayAdapter<String> stringArrayAdapter =
                                                      new ArrayAdapter<String>(ActivitySalesbill.this, android.R.layout.simple_spinner_dropdown_item, doctorname);
                                              doctor.setAdapter(stringArrayAdapter);
                                              doctor.setAdapter(stringArrayAdapter);
                                              dialog.dismiss();
                                              setSummaryRow();
                                          } catch (IndexOutOfBoundsException ex) {
                                              ex.printStackTrace();
                                          } catch (NullPointerException ex) {
                                              ex.printStackTrace();
                                          } catch (NumberFormatException ex) {
                                              ex.printStackTrace();
                                          }
                                      }
                                  }

        );
            // GrandTotal.setText("");


            dialog.show();
            dialog.setCanceledOnTouchOutside(false);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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


            Intent i = new Intent(ActivitySalesbill.this, ActivitySales.class);
            startActivity(i);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }




}
