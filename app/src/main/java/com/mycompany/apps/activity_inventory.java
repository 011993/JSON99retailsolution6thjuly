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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import Adapter.InventoryProductDropdownAdapter;
import Adapter.Inventoryadapter;
import Adapter.Inventoryproductadapter;
import Adapter.PurchaseproductlistwithpoAdapter;
import Adapter.fullproductadapter;
import Pojo.Inventorymodel;
import Pojo.Inventoryproductmodel;
import Pojo.PurchaseProductModelwithpo;


public class activity_inventory extends Activity  {

    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteTextView ProductidorName;


    private TextWatcher mTextWatcher;
    private TextWatcher pTextWatcher;

    public static final int MIN_LENGTH_OF_BARCODE_STRING = 13;
    public static final String BARCODE_STRING_PREFIX = "@";

    EditText quantity;
    private TextView GrandTotal, Billno;
    private TextView Totalitems;

    String Store_Id;
    String x_imei, x1;

    Button update, Holdinventorybutton;
    DBhelper helper;

    ArrayList<Inventorymodel> arrayList;
    ArrayList<Inventoryproductmodel> arrayList1;

    Inventoryproductadapter productAdapter;
    Inventoryadapter adapter;
    fullproductadapter Fullproductadapter;
    ListView listView;
    ActionBar actionBar;
    TelephonyManager tel;


    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean syncInProgress = false;
    private boolean didSyncSucceed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_inventory);

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf


        update = (Button) findViewById(R.id.addtolist_button);
        GrandTotal = (TextView) findViewById(R.id.discount_textt10);
        Totalitems = (TextView) findViewById(R.id.discount_textt);
        Button clrbtn = (Button) findViewById(R.id.clearinventory);
        Holdinventorybutton = (Button) findViewById(R.id.HoldInventorybillwithoutpo);
        tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Billno = (TextView) findViewById(R.id.sales_billno);
        Billno.setText(tel.getDeviceId().toString());

        //   SelectPurchaseAlertDialog();
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);

        helper = new DBhelper(this);
        autoCompleteTextView = (CustomAuto) findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(1);
        ProductidorName = (CustomAuto) findViewById(R.id.autoProductIdandNameforWithout);

        listView = (ListView) findViewById(R.id.listView);
        ProductidorName.setThreshold(1);

//
//        final List<String> storeidlist= helper.getinventorystore();
//        Log.e("###############", storeidlist.toString().replace("[", "").replace("]", ""));
//        Store_Id= storeidlist.get(0).toString().replace("[", "").replace("]", "");

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
                arrayList = helper.getInventoryName(userTypedString);
                if (arrayList != null) {
                    if (adapter == null) {
                        adapter = new Inventoryadapter(activity_inventory.this, android.R.layout.simple_dropdown_item_1line, arrayList);
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
                //  Toast.makeText(getApplicationContext(), "selected store " + Value, Toast.LENGTH_SHORT).show();
            }
        });


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
                if (userTypedString.length() < 3) {
                    return;
                }
                if (userTypedString.startsWith(BARCODE_STRING_PREFIX)) {
                    if (arrayList1 != null) {
                        arrayList1.clear();
                    }
                    //this is a barcode generated string
                    if (userTypedString.length() <= MIN_LENGTH_OF_BARCODE_STRING) {
                        //ignore all strings of length < 13
                        return;
                    }
                    arrayList1 = helper.getProductdataforwithoutInventory(userTypedString.substring(1));
                    //dropdownProductArrayList = helper.getProductdata(userTypedString);
                    if (arrayList1.size() == 1) {
                        //we have found the product
                        addProductToPurchaseList(arrayList1.get(0));
                        return;
                    } else if (arrayList1.size() < 1) {
                        //no product found
                        // Toast.makeText(activity_inventory.this, "No Product found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    arrayList1 = helper.getProductdataforwithoutInventory(userTypedString);
                    if (arrayList1.size() < 1) {
                        //no product found
                        arrayList1.clear();
                        //Toast.makeText(activity_inventory.this, "No Product found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (productAdapter == null) {
                    productAdapter = new Inventoryproductadapter(activity_inventory.this, android.R.layout.simple_dropdown_item_1line, arrayList1);
                    productAdapter.setList(arrayList1);
                    ProductidorName.setAdapter(productAdapter);
                    ProductidorName.setThreshold(3);
                } else {
                    productAdapter.setList(arrayList1);
                    productAdapter.notifyDataSetChanged();
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

                Inventoryproductmodel resval = (Inventoryproductmodel) parent.getItemAtPosition(position);

                addProductToPurchaseList(resval);
                Fullproductadapter.setBatchdata(resval);

            }


        });


//****************************************************submit Button****************************************************8
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);

                try {
                    GrandTotal.getText().toString();


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
                    //  helper.updatequantity(Fullproductadapter.getlist());
                    helper.saveInventorywithoutpo(Fullproductadapter.getlist(), autoCompleteTextView.getText().toString(), x1);

                    helper.saveGranddataintoGrnMasterwithoutpo(x1, autoCompleteTextView.getText().toString(), GrandTotal.getText().toString());

                    helper.SavePDfDetailForInventoryWithoutPo(x1, autoCompleteTextView.getText().toString());
                    Toast.makeText(getApplicationContext(), x1.toString(), Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                    startActivity(intent);

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }

        });
//******************************************************hold button*************************************************

        Holdinventorybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                helper = new DBhelper(activity_inventory.this);


                if (autoCompleteTextView.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Select Vendor Or Distributor Name", Toast.LENGTH_SHORT).show();
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


                    helper.saveInventoryholdbillwithoutpo(Fullproductadapter.getlist(), autoCompleteTextView.getText().toString(), x1);

                    Toast.makeText(getApplicationContext(), x1, Toast.LENGTH_SHORT).show();

                    Intent syncIntent = new Intent(getApplicationContext(), com.mycompany.apps.Activitypurchase.class);
                    startActivity(syncIntent);


                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //**************************Alll clear screen ***********************************************************************************************************************/

        clrbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);

                try {
                    Fullproductadapter.clearAllRows();
                    setSummaryRow();
                    autoCompleteTextView.setText("");
                    ProductidorName.setText("");

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void addProductToPurchaseList(Inventoryproductmodel resval1) {
        if (Fullproductadapter == null) {
            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
            Fullproductadapter = new fullproductadapter(activity_inventory.this, new ArrayList<Inventoryproductmodel>(), android.R.layout.simple_dropdown_item_1line, resval1);
            listView.setAdapter(Fullproductadapter);
        }
        int pos = Fullproductadapter.addProductToList(resval1);
        Log.i("&&&&&&&&", "Adding " + resval1 + " to Product List..");
        Fullproductadapter.notifyDataSetChanged();
        ProductidorName.setText("");
        setSummaryRow();
        productAdapter.setList(arrayList1);
        arrayList1.clear();
        listView.smoothScrollToPosition(pos);
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


            Intent i = new Intent(activity_inventory.this, Activitypurchase.class);
            startActivity(i);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }


    private void calcResult() throws NumberFormatException {
        //  Editable editableText1 = Mrp.getText();
        Editable editableText2 = quantity.getText();
        double result, value1 = 0.0, Mrp = 0.0;
        if (editableText2 != null && editableText2.length() >= 1)
            value1 = Double.parseDouble(editableText2.toString());
        result = value1 * Mrp;

    }

    public void setSummaryRow() {
        DecimalFormat f = new DecimalFormat("##.00");
        float Getval = Fullproductadapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        GrandTotal.setText(GrandVal);

        int Getitem = Fullproductadapter.getCount();
        int Allitem = Getitem;
        String GETITEM = Integer.toString(Allitem);
        Totalitems.setText(GETITEM);
    }
}