package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.sql.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

import Adapter.PurchaseproductlistAdapter;
import Pojo.PurchaseProductModel;
import Pojo.VendorModel;

public class VendorPaymentActivity extends Activity  {
    PurchaseproductlistAdapter productlistAdapter;
    TextView VendorPayGrandtotal;
    Spinner VendorSpinner;
    Spinner PO_NoSpinner;
    Spinner VendorReturnID;
    DBhelper helper;
    Button PaybyCash;
    Button PaybyCheque;
    String PO_Numberselected;
    String Vendorselected;
    ArrayAdapter<VendorModel> VendorName;
    ArrayList<String> Ponumbers;
    ArrayList<String>VendoReturnIDArrayList;
    EditText UserPayAmount;
    TextView VendorDuePayAmount,Billno;
    TextWatcher dueAMountTextWatcher;
    String x_imei;
    ActionBar actionBar;
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
        setContentView(R.layout.activity_vendor_payment);

        final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        helper = new DBhelper(this);

        VendorPayGrandtotal = (TextView) findViewById(R.id.VendorPayGrandtotal);
        UserPayAmount= (EditText) findViewById(R.id.VendorPayAmount);
        UserPayAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});


        VendorDuePayAmount= (TextView) findViewById(R.id.VendorDuePayAmount);
        VendorDuePayAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});


        VendorSpinner = (Spinner) findViewById(R.id.VendororDistributorName);
        PO_NoSpinner = (Spinner) findViewById(R.id.Lastinvoiceno);
/*
        VendorReturnID=(Spinner)findViewById(R.id.VendorReturnID);
*/

        PaybyCash = (Button) findViewById(R.id.Paybycashbtn);
        PaybyCheque = (Button) findViewById(R.id.paybychequebtn);
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        final ArrayList<VendorModel> vendors = helper.getVendorsForVendorPayment();
        VendorName= new ArrayAdapter<VendorModel>(VendorPaymentActivity.this, android.R.layout.simple_list_item_1, vendors);

        VendorSpinner.setAdapter(VendorName);
        VendorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Vendorselected = parent.getSelectedItem().toString();
                Ponumbers = helper.getGrnNumber(Vendorselected);
                ArrayAdapter<String> Purchasrordernumber = new ArrayAdapter<String>(VendorPaymentActivity.this, android.R.layout.simple_list_item_1, Ponumbers);
                PO_NoSpinner.setAdapter(Purchasrordernumber);
                PO_NoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        PO_Numberselected = parent.getSelectedItem().toString();
                        ArrayList<String> grandtotal = helper.getGrandTotalforVendorPayment(PO_Numberselected);
                        VendorPayGrandtotal.setText(grandtotal.get(0));
                        UserPayAmount.setText(VendorPayGrandtotal.getText().toString());
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
        // AmountDuePayment();

        if( (UserPayAmount.getTag() != null)  && (UserPayAmount.getTag() instanceof TextWatcher) ) {
            UserPayAmount.removeTextChangedListener((TextWatcher) UserPayAmount.getTag() );
        }
        dueAMountTextWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                DecimalFormat f=new DecimalFormat("##.00");

                try {
                    if (UserPayAmount.getText().toString().equals("")) {
                        VendorDuePayAmount.setText("");
                        return;
                    }

                    VendorDuePayAmount.setText(String.valueOf(f.format(Double.parseDouble(VendorPayGrandtotal.getText().toString()) - Double.parseDouble(UserPayAmount.getText().toString()))));
                } catch (Exception ex) {
                        ex .printStackTrace();
                }

            }

        };
        UserPayAmount.addTextChangedListener(dueAMountTextWatcher);
        UserPayAmount.setTag(dueAMountTextWatcher);

        VendoReturnIDArrayList=helper.getVendorReturndataForVendorPayment();
        if (VendoReturnIDArrayList==null){
            return;
        }
      /*  ArrayAdapter<String>VendorReturnIDAdpater=new ArrayAdapter<String>(VendorPaymentActivity.this, android.R.layout.simple_list_item_1, VendoReturnIDArrayList);
        VendorReturnID.setAdapter(VendorReturnIDAdpater);*/

        PaybyCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VendorName.isEmpty()) {
                    return;
                }
              final Long Value = System.currentTimeMillis();
                final String resval = Long.toString(Value);

                if(VendorPayGrandtotal.getText().toString().equals(UserPayAmount.getText().toString())|| VendorDuePayAmount.getText().toString().equals("0.0")||Double.parseDouble(UserPayAmount.getText().toString())>Double.parseDouble(VendorPayGrandtotal.getText().toString())) {
                    helper.savedirectPayment(Vendorselected, PO_Numberselected, VendorPayGrandtotal.getText().toString(),resval,UserPayAmount.getText().toString(),VendorDuePayAmount.getText().toString());
                    helper.updateintoGrnMasterTableForVendorPayment(Vendorselected, PO_Numberselected, VendorPayGrandtotal.getText().toString());
                    helper.saveDataforPdfVendorPayment(PO_Numberselected, Vendorselected);

                }
                else{
                    helper.savedirectPayment(Vendorselected, PO_Numberselected, UserPayAmount.getText().toString(),resval,UserPayAmount.getText().toString(),VendorDuePayAmount.getText().toString());
                    helper.updateintoGrnMasterForDueAmount(Vendorselected, PO_Numberselected, VendorDuePayAmount.getText().toString());
                    helper.saveDataforPdfVendorPayment(PO_Numberselected, Vendorselected);

                }

                Toast.makeText(getApplicationContext(),PO_Numberselected, Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getApplicationContext(), Activityvendorpayment.class);
                startActivity(in);
            }

        });

        PaybyCheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertForChequePayment();
            }
        });
    }
    private void AlertForChequePayment() {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.alertdialog_forvendorindirectpayment, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final  Button submit=(Button)alertLayout.findViewById(R.id.submitpaymentbtn);
        final EditText EnterChequeno=(EditText)alertLayout.findViewById(R.id.EnterCheque1);
        final Spinner  BankName = (Spinner)alertLayout.findViewById(R.id.selectBank);
        final Button Cancel=(Button)alertLayout.findViewById(R.id.cancel);

        alert.setView(alertLayout);
        alert.setCancelable(true);

        alert.setTitle("                    Enter Bank Details ");
        final AlertDialog dialog =alert.create();

        ArrayList<String>BankNameArraylist=helper.getBankName();
        ArrayAdapter<String>BankNameAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,BankNameArraylist);
        BankName.setAdapter(BankNameAdapter);


        if (VendorName.isEmpty())
        {
            return;
        }else {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String BankNameSelected = BankName.getSelectedItem().toString();
                    //EnterChequeno.getText().toString();
                    final Long Value = System.currentTimeMillis();
                    final String resval = Long.toString(Value);

                    if (BankNameSelected.trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), " Please select Bank name", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (EnterChequeno.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), " Please Enter Cheque Number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                   /* if ( Double.parseDouble(UserPayAmount.getText().toString())>Double.parseDouble(VendorPayGrandtotal.getText().toString()))
                    {

                        Toast.makeText(getApplicationContext(),"Amount Is Greater than Payment" ,Toast.LENGTH_SHORT).show();
                        return;

                    }*/

                    if(VendorPayGrandtotal.getText().toString().equals(UserPayAmount.getText().toString())|| VendorDuePayAmount.getText().toString().equals("0.0")) {
                        helper.savedirectPaymentwithpobycheque( PO_Numberselected,Vendorselected, VendorPayGrandtotal.getText().toString(), BankNameSelected, EnterChequeno.getText().toString(),resval,UserPayAmount.getText().toString(),VendorDuePayAmount.getText().toString());
                        helper.updateintoGrnMasterTableForVendorPayment(Vendorselected, PO_Numberselected, VendorPayGrandtotal.getText().toString());
                        helper.saveDataforPdfVendorPayment(PO_Numberselected, Vendorselected);

                    }
                    else {
                        helper.savedirectPaymentwithpobycheque( PO_Numberselected,Vendorselected, UserPayAmount.getText().toString(),BankNameSelected,EnterChequeno.getText().toString(),resval,UserPayAmount.getText().toString(),VendorDuePayAmount.getText().toString());
                        helper.updateintoGrnMasterForDueAmount(Vendorselected, PO_Numberselected, VendorDuePayAmount.getText().toString());
                        helper.saveDataforPdfVendorPayment(PO_Numberselected,Vendorselected);

                    }
                    Intent in = new Intent(getApplicationContext(), Activityvendorpayment.class);
                    startActivity(in);
                    Toast.makeText(getApplicationContext(),PO_Numberselected, Toast.LENGTH_SHORT).show();

                }

            });

            Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   dialog.dismiss();
                }
            });
        }

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

    }


    @Override
    protected void onStop() {
        super.onStop();
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

            Intent i=new Intent(VendorPaymentActivity.this,Activitypurchase.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
