package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
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

import Adapter.ProductAdapter;
import Adapter.ProductAutoAdapter;
import Pojo.Product;
import Pojo.ProductAuto;


public class ActivityProduct extends Activity  {
    // ActionBar actionBar;
    TextView ProductBarcode;
    TextView ProductName;
    TextView MRP;
    EditText Sellingprice;
    Spinner Active;
    EditText Purchaseprice;
    TextView Taxid,Margin;
    TextView Manuf;
    TextView PackingUnit1;
    TextView MeasureUnit;
    TextView Measure;
    TextView Strength;
    TextView ProductId;
    TextView Internet;
    EditText auto;
    String Prod_Id_To_Update;
   TextView Internetrelevant;
    ActionBar actionBar;
    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    private TextWatcher nTextWatcher;

    ArrayList<Product> Productlist;
    ArrayList<ProductAuto>ProductAutoArraylist;
    ProductAutoAdapter autoAdapter;
    ProductAdapter adapter;
    String item ;
    String item2;
    String SpinValue;

    // Data Source
    String InventoryType[] = {"Y", "N"};
    String ActiveType[];

    // Adapter
    ArrayAdapter<String> adapterInventoryType;
    ArrayAdapter<String> adapterActiveType;


    DBhelper mydb;
    String SpinInvVal;

    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_product);
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        ProductBarcode = (TextView) findViewById(R.id.product_Barcode);
        ProductName = (TextView) findViewById(R.id.product_Desc);
        MRP = (TextView) findViewById(R.id.product_MRP);
        Sellingprice = (EditText) findViewById(R.id.product_Selling);
        Sellingprice.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});

        Purchaseprice = (EditText) findViewById(R.id.product_Purchase);
        Purchaseprice.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});


        Taxid = (TextView) findViewById(R.id.product_Tax);
        Manuf = (TextView) findViewById(R.id.product_manuf);
        Margin =(TextView)findViewById(R.id.product_margin);
        Internetrelevant= (TextView) findViewById(R.id.product_internetrelevant);
        // PackingUnit1=(TextView)findViewById(R.id.product_PackingUnit);
        MeasureUnit = (TextView) findViewById(R.id.product_MeasureUnit);
      //  Measure = (TextView) findViewById(R.id.product_Measure);
      //  Strength = (TextView) findViewById(R.id.product_Strength);
        ProductId = (TextView) findViewById(R.id.product_Prodid);
        Internet = (TextView) findViewById(R.id.product_InternetPrice);
       Active = (Spinner) findViewById(R.id.product_active);
      //  auto = (AutoCompleteTextView)findViewById(R.id.product_auto_complete);
        auto = (EditText)findViewById(R.id.product_complete);



        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapterActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Active.setAdapter(adapterActiveType);
        Active.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = Active.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*ActiveType = getResources().getStringArray(R.array.active_Status);
        adapterInventoryType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        adapterInventoryType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Internetrelevant.setAdapter(adapterInventoryType);
        Internetrelevant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinInvVal = Internetrelevant.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        mydb = new DBhelper(this);
            autoCompleteTextView = (CustomAuto) findViewById(R.id.myautocomplete);
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
                    Log.d("NishantSingh", "After text changed called ");
                    if (autoCompleteTextView.isPerformingCompletion()) {
                        Log.d("Nishu", "Performing completion ");
                        return;
                    }
                    String userTypedString = autoCompleteTextView.getText().toString().trim();
                    if (userTypedString.equals("")) {
                        return;
                    }
                    Productlist = mydb.getAllProducts(userTypedString);

                    if (Productlist != null) {
                        if (adapter == null) {
                            adapter = new ProductAdapter(ActivityProduct.this, android.R.layout.simple_dropdown_item_1line, Productlist);
                            adapter.setProductList(Productlist);
                            autoCompleteTextView.setAdapter(adapter);
                            autoCompleteTextView.setThreshold(3);

                        } else {

                            adapter.setProductList(Productlist);
                            adapter.notifyDataSetChanged();

                        }
                    }
                }
            };

            autoCompleteTextView.addTextChangedListener(mTextWatcher);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                    Log.d("Nishu99", "On click called ");

                    final DBhelper mydb = new DBhelper(ActivityProduct.this);

                    Product value = (Product) parent.getItemAtPosition(pos);
                    ProductName.setText(value.getProductName());
                    ProductBarcode.setText(value.getProductBarcode());
                    ProductId.setText(value.getProductId());


                    Internet.setText(value.getInternet());
//                    Strength.setText(value.getStrength());
                    Manuf.setText(value.getManuf());
                    MeasureUnit.setText(value.getMeasureUnit());
                    //    Measure.setText(value.getMeasure());
                    Taxid.setText(value.getTaxid());
                    Sellingprice.setText(value.getSellingprice());
                    Sellingprice.requestFocus();
                    Purchaseprice.setText(value.getPurchaseprice());
                    MRP.setText(value.getMRP());
                    autoCompleteTextView.setText("");
                    Internetrelevant.setText(value.getInternetrelevant());
                    Margin.setText(value.getMargin());

                    SpinValue=value.getActive();
                    if (SpinValue.equals("Y"))
                    {
                        Active.setSelection(0);
                    }
                    if (SpinValue.equals("N"))
                    {
                        Active.setSelection(1);
                    }

                }
            });
            mydb = new DBhelper(this);
            auto = (EditText) findViewById(R.id.product_complete);
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
                    ProductAutoArraylist = mydb.getProductDetailsFromBarcode(barcodeFromScanner);
                    if (ProductAutoArraylist != null && ProductAutoArraylist.size() > 0) {
                        ProductAuto value = (ProductAuto) ProductAutoArraylist.get(0);

                        ProductName.setText(value.getAutoProductname());
                        ProductBarcode.setText(value.getAutoBarcode());
                        ProductId.setText(value.getAutoProductId());
                        Internetrelevant.setText(value.getAutoInternetrel());


                        SpinValue=value.getAutoActive();
                        if (SpinValue.equals("Y"))
                        {
                            Active.setSelection(0);
                        }
                        if (SpinValue.equals("N"))
                        {
                            Active.setSelection(1);
                        }
                        Internet.setText(value.getAutoInternetPrice());
//                        Strength.setText(value.getAutoStrength());
                        Manuf.setText(value.getAutoManuf());
                        MeasureUnit.setText(value.getAutoMeasureunit());
                        //                       Measure.setText(value.getAutoMeasure());
                        Taxid.setText(value.getAutoTax());
                        Sellingprice.setText(value.getAutoSellingprice());
                        Purchaseprice.setText(value.getAutoPurchaseprice());
                        MRP.setText(value.getAutoMrp());
                        Margin.setText(value.getMargin());

                    }
                }
            });
        Button Exit = (Button) findViewById(R.id.product_exit_button);
            Exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    view.startAnimation(Buttonok);
                    Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                    startActivity(intent);
                }
            });

            Button Update = (Button) findViewById(R.id.product_update_button);
            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  try {
                        view.startAnimation(Buttonok);

                        String value = ProductId.getText().toString();
                        Prod_Id_To_Update = value;
                    if(Sellingprice.getText().toString().matches(""))
                    {
                        Sellingprice.setError("Please enter the Selling Price");
                        return;
                    }
                    if (Purchaseprice.getText().toString().matches(""))
                    {
                        Purchaseprice.setError("Please enter the Purchase Price");
                        return;
                    }
                  /*  if (Internet.getText().toString().matches(""))
                    {
                        Internet.setError("Please enter the Internet Price");
                        return;
                    }
*/
                    try {
                        float selling = Float.parseFloat(Sellingprice.getText().toString());
                        float purchase = Float.parseFloat(Purchaseprice.getText().toString());
                        float mrp = Float.parseFloat(MRP.getText().toString());
                     //   float internet = Float.parseFloat(Internet.getText().toString());
                        if (selling > mrp) {
                            Sellingprice.setError("Invalid Selling Price");
                            return;

                        }
                        if (purchase > selling) {
                            Sellingprice.setError("Invalid Selling Price");
                            return;
                        }
                        if (purchase > mrp)
                        {
                            Purchaseprice.setError("Invalid purchase Price");
                            return;
                        }

                        mydb.updateProduct(Prod_Id_To_Update, Sellingprice.getText().toString(), Purchaseprice.getText().toString(), Internet.getText().toString(),Internetrelevant.getText().toString(), SpinValue,Margin.getText().toString()) ;
                        mydb.updateProductCom(Prod_Id_To_Update, Sellingprice.getText().toString(), Purchaseprice.getText().toString(), Internet.getText().toString(),Internetrelevant.getText().toString(), SpinValue);
                        {

                            Toast.makeText(getApplicationContext(), "Product Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                            startActivity(intent);
                           //// loadSyncLibrary();
                           // doSync(syncDataBundle);
                        }
                    } catch (Exception ex) {

                    }


                }
            });

            Button Clear = (Button) findViewById(R.id.product_clear_button);
            Clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(Buttonok);
                    auto.requestFocus();
                    ProductId.setText("");
                    ProductBarcode.setText("");
                    Purchaseprice.setText("");
                    Sellingprice.setText("");
                    MRP.setText("");
                    Internetrelevant.setText("");
//                    ActiveType = getResources().getStringArray(R.array.active_Status);
//                    adapterInventoryType = new ArrayAdapter<String>(ActivityProduct.this,android.R.layout.simple_spinner_item,ActiveType);
//                    adapterInventoryType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    Internetrelevant.setAdapter(adapterInventoryType);
                    Internet.setText("");
//                Strength.setText("");
                    Taxid.setText("");
                    //        Measure.setText("");
                    MeasureUnit.setText("");
                    ProductName.setText("");
                    Manuf.setText("");
                    Active.setAdapter(null);
                    ActiveType = getResources().getStringArray(R.array.active_Status);
                    adapterActiveType = new ArrayAdapter<String>(ActivityProduct.this,android.R.layout.simple_spinner_item,ActiveType);
                    adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    Active.setAdapter(adapterActiveType);
                    autoCompleteTextView.setText("");
                    auto.setText("");


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

            Intent i=new Intent(ActivityProduct.this,Activity_masterScreen2.class);
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
