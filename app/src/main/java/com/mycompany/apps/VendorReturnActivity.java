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
import android.view.Gravity;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import Adapter.VendorNameForVendorReturnAdapter;
import Adapter.VendorReturnProductListAdapter;
import Adapter.VendorReturnSelectProductToreturnAdapter;
import Pojo.VendorModel;
import Pojo.VendorReturnModel;

public class VendorReturnActivity extends Activity {
    ActionBar actionBar;
    Spinner selectReasonsforVendorreturn;
    TextWatcher SelectProducttoreturn;
    TextWatcher selectVendorordistriName;
    ArrayList<String> selectthereason;
    ArrayList<VendorModel> getAllVenderToselect;
    ArrayList<VendorReturnModel>getAllProductstoreturn;
    ArrayList<VendorReturnModel>GetthedatausingPoNumber;
    AutoCompleteTextView VendororDistributorNameAutoComplete;
    AutoCompleteTextView ProductToreturnAutocomplete;
    VendorNameForVendorReturnAdapter adapter;
    VendorReturnSelectProductToreturnAdapter dropDownProductNameAdpater;
    VendorReturnProductListAdapter ProductListAdapter;
    ListView lv_ProductVendorreturn;
    TextView GrandTotal,Billno;
    DBhelper helper;
    Button clrbtn;
    Button Submit;
    String x_imei,x1;
    String ReasonSelected;
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
        setContentView(R.layout.activity_vendor_return);
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        Billno = (TextView) findViewById(R.id.sales_billno);
        TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = tel.getDeviceId();
        Log.e("imei is :", device_id);

        Billno.setText(device_id);

        final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        helper = new DBhelper(this);
        returnpopupdialog();

        lv_ProductVendorreturn=(ListView)findViewById(R.id.lv_ProductVendorreturn);
        GrandTotal=(TextView)findViewById(R.id.GrandTotalVendorReturn);
        clrbtn = (Button) findViewById(R.id.clearvendorReturn);
        Submit=(Button)findViewById(R.id.SubmitVendorReturn);


        VendororDistributorNameAutoComplete = (CustomAuto) findViewById(R.id.VendorNameordistributorNames);
        VendororDistributorNameAutoComplete.setThreshold(1);
        ProductToreturnAutocomplete=(CustomAuto)findViewById(R.id.SelectProducttoReturn);
        ProductToreturnAutocomplete.setThreshold(1);

        selectReasonsforVendorreturn = (Spinner) findViewById(R.id.Vendorreturn);
        selectthereason = helper.getthereasonsfromVendorReturn();
        final ArrayAdapter<String> selecttheadapterforreturn = new ArrayAdapter<String>(VendorReturnActivity.this, android.R.layout.simple_spinner_item, selectthereason);
        selectReasonsforVendorreturn.setAdapter(selecttheadapterforreturn);
        selectReasonsforVendorreturn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ReasonSelected = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        selectVendorordistriName = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("Debuging", "After text changed called ");
                if (VendororDistributorNameAutoComplete.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String UserTypedValue = VendororDistributorNameAutoComplete.getText().toString().trim();
                if (UserTypedValue.equals("")) {
                    return;
                }
                getAllVenderToselect = helper.getVendorName(UserTypedValue);
                if (getAllVenderToselect != null) {
                    if (adapter == null) {
                        adapter = new VendorNameForVendorReturnAdapter(VendorReturnActivity.this, android.R.layout.simple_spinner_item, getAllVenderToselect);
                        adapter.setList(getAllVenderToselect);
                        VendororDistributorNameAutoComplete.setAdapter(adapter);
                    } else {
                        adapter.setList(getAllVenderToselect);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

        };
        VendororDistributorNameAutoComplete.addTextChangedListener(selectVendorordistriName);

        SelectProducttoreturn =new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("Debuging", "After text changed called ");
                if (VendororDistributorNameAutoComplete.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Select Vendor Or Distributor Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ReasonSelected.equals(""))
                {
                    //Toast.makeText(getApplicationContext(), "No Reason Selected", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ProductToreturnAutocomplete.isPerformingCompletion())
                {
                    return;
                }
                String UserTypedproduct=ProductToreturnAutocomplete.getText().toString().trim();
                if (UserTypedproduct.equals(""))
                {
                    return;
                }
                if(UserTypedproduct.length()<3)
                {
                    return;
                }
                getAllProductstoreturn=helper.GetDataToreturn(UserTypedproduct);
                if (getAllProductstoreturn != null) {
                    if (dropDownProductNameAdpater== null)
                    {
                        dropDownProductNameAdpater= new VendorReturnSelectProductToreturnAdapter(VendorReturnActivity.this,android.R.layout.simple_list_item_1,getAllProductstoreturn);
                        dropDownProductNameAdpater.setList(getAllProductstoreturn);
                        ProductToreturnAutocomplete.setAdapter(dropDownProductNameAdpater);
                    }else
                    {
                        dropDownProductNameAdpater.setList(getAllProductstoreturn);
                        dropDownProductNameAdpater.notifyDataSetChanged();
                    }

                }
            }
        };

        ProductToreturnAutocomplete.addTextChangedListener(SelectProducttoreturn);
        ProductToreturnAutocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VendorReturnModel resval = (VendorReturnModel) parent.getItemAtPosition(position);
                if (ProductListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    ProductListAdapter = new VendorReturnProductListAdapter(VendorReturnActivity.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<VendorReturnModel>(), resval);
                    lv_ProductVendorreturn.setAdapter(ProductListAdapter);
                }
                ProductListAdapter.addProductToList(resval);
                Log.i("&&&&&&&&", "Adding " + resval + " to Product List..");
                ProductListAdapter.notifyDataSetChanged();
                ProductToreturnAutocomplete.setText("");
                setSummaryRow();
            }
        });

        clrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ProductListAdapter.clearAllRows();
                    setSummaryRow();
                    VendororDistributorNameAutoComplete.setText("");
                    VendororDistributorNameAutoComplete.addTextChangedListener(selectVendorordistriName);
                    VendororDistributorNameAutoComplete.setEnabled(true);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }

        });


       Submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

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


               if (VendororDistributorNameAutoComplete.getText().toString().trim().isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Please Select Vendor Or Distributor Name", Toast.LENGTH_SHORT).show();
                   return;
               }
               try {
                   if (ProductListAdapter.isEmpty()) {
                       Toast.makeText(getApplicationContext(), "No Product Selected", Toast.LENGTH_SHORT).show();
                       return;
                   }

                   if (ReasonSelected.equals("")) {
                       Toast.makeText(getApplicationContext(), "No Reason Selected", Toast.LENGTH_SHORT).show();
                       return;
                   }

                   helper.InsertDataforVendorReturn(x1, ProductListAdapter.getList(), VendororDistributorNameAutoComplete.getText().toString(), ReasonSelected);
                   helper.InsertMasterDataForVendorReturn(x1, VendororDistributorNameAutoComplete.getText().toString(), ReasonSelected, GrandTotal.getText().toString());
                   helper.UpdateStockQtyForVendorReturn(ProductListAdapter.getList());

                   helper.saveDataforPdfVendorReturn(x1, VendororDistributorNameAutoComplete.getText().toString());
                   Toast.makeText(getApplicationContext(), x1.toString(), Toast.LENGTH_SHORT).show();


                   Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                   startActivity(intent);

               }catch (NullPointerException ex)
               {
                   ex.printStackTrace();
               }
           }
       });
    }

    public void setSummaryRow() {
        DecimalFormat f = new DecimalFormat("##.0");

        float Getval = ProductListAdapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        GrandTotal.setText(GrandVal);
    }

    public void returnpopupdialog() {

        final LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.alertdailog_vendorreturn, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final Button WithPo = (Button) alertLayout.findViewById(R.id.WithPoButton);
        final Button WithoutPo = (Button) alertLayout.findViewById(R.id.WithoutPObutton);
        final Button submit = (Button) alertLayout.findViewById(R.id.submitbtn);
        final Button Cancel = (Button) alertLayout.findViewById(R.id.Cancelbtn);
         final Spinner VendornameForReturnSpinner=(Spinner)alertLayout.findViewById(R.id.VendororDistributorNameforVendorReturn);
        final Spinner GrnIdForVendorreturnSpinner=(Spinner)alertLayout.findViewById(R.id.Last3InvoiceNoVendorReturn);

        //final EditText LastInvoiceno = (EditText) alertLayout.findViewById(R.id.EnterInvoicenoforVendorReturn);
        alert.setCancelable(false);
        //LastInvoiceno.setVisibility(View.INVISIBLE);
        WithPo.setVisibility(View.VISIBLE);
        WithoutPo.setVisibility(View.VISIBLE);
        VendornameForReturnSpinner.setVisibility(View.INVISIBLE);
        GrnIdForVendorreturnSpinner.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.INVISIBLE);
        Cancel.setVisibility(View.VISIBLE);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        TextView title = new TextView(this);
// You Can Customise your Title here
        title.setText("Select The Inventory Stock Document Options");
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(25);

        alert.setCustomTitle(title);
        final AlertDialog dialog = alert.create();
        WithPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WithoutPo.setVisibility(View.INVISIBLE);
                VendornameForReturnSpinner.setVisibility(View.VISIBLE);
                GrnIdForVendorreturnSpinner.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                // LastInvoiceno.setVisibility(View.VISIBLE);
            }
        });
        WithoutPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WithPo.setVisibility(View.INVISIBLE);
                //LastInvoiceno.setVisibility(View.INVISIBLE);
                VendornameForReturnSpinner.setVisibility(View.INVISIBLE);
                GrnIdForVendorreturnSpinner.setVisibility(View.INVISIBLE);
                submit.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            }
        });



        ArrayList<VendorModel> vendors = helper.getVendorsforVendorReturn();
        ArrayAdapter<VendorModel > stringArrayAdapter =
                new ArrayAdapter<VendorModel>(VendorReturnActivity.this, android.R.layout.simple_spinner_dropdown_item,vendors);
        VendornameForReturnSpinner.setAdapter(stringArrayAdapter);
        VendornameForReturnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(), "OnItemSelected  :" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                String Spinnervalue = VendornameForReturnSpinner.getSelectedItem().toString();
                Log.e("*************", Spinnervalue);
                ArrayList<String> LastInvoices = helper.getLastInvoicesForVendorReturn(Spinnervalue);
                ArrayAdapter<String> InvoiceNoAdapter =
                        new ArrayAdapter<String>(VendorReturnActivity.this, android.R.layout.simple_spinner_dropdown_item, LastInvoices);
                GrnIdForVendorreturnSpinner.setAdapter(InvoiceNoAdapter);
                GrnIdForVendorreturnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserEnteredNumber=null;
                UserEnteredNumber=GrnIdForVendorreturnSpinner.getSelectedItem().toString().trim();
                GetthedatausingPoNumber = helper.getAllVendorreturndata(UserEnteredNumber);
                ArrayList<String>GetVendorNameforAutocomplete=helper.getVendorNameForAuto(UserEnteredNumber);
                if (GetthedatausingPoNumber != null && GetthedatausingPoNumber.size() > 0) {
                    if (ProductListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        ProductListAdapter = new VendorReturnProductListAdapter(VendorReturnActivity.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<VendorReturnModel>(), null);
                        lv_ProductVendorreturn.setAdapter(ProductListAdapter);
                    }
                    for (VendorReturnModel prod : GetthedatausingPoNumber) {
                        ProductListAdapter.addProductToList(prod);
                    }
                    VendororDistributorNameAutoComplete.setText("" + GetVendorNameforAutocomplete.toString().replace("[", "").replace("]", "" + ""));
                    VendororDistributorNameAutoComplete.removeTextChangedListener(selectVendorordistriName);
                    VendororDistributorNameAutoComplete.setEnabled(false);
                    ProductListAdapter.notifyDataSetChanged();
                    setSummaryRow();
                    ProductToreturnAutocomplete.setEnabled(false);
                    {
                        Toast.makeText(getApplicationContext(), "Ur Using With GrnID ", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();

                }

            }
        });
        Cancel.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick (View v){
                                          WithPo.setVisibility(View.VISIBLE);
                                          WithoutPo.setVisibility(View.VISIBLE);
                                          submit.setVisibility(View.VISIBLE);
                                         // LastInvoiceno.setVisibility(View.INVISIBLE);
                                          Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                                          startActivity(intent);
                                      }
                                  }

        );
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

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

            Intent i=new Intent(VendorReturnActivity.this,Activitypurchase.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
