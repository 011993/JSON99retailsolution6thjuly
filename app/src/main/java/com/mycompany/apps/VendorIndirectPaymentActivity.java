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
import android.text.InputFilter;
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
import java.util.ArrayList;

import Pojo.VendorModel;

public class VendorIndirectPaymentActivity extends Activity  {
        TextView Vendorpaymentno,Billno;
         Spinner selectVendorName;
         Spinner selecttheReason;
        ArrayList<VendorModel>vendors;
        ArrayList<String>SelectReasons;
        EditText PaymentAmount;
        Button Payycash;
        Button paybycheque;
    String Vendorselected;
    String selectedReasons;
        DBhelper helper;
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
        setContentView(R.layout.activity_vendor_indirect_payment);
        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        Billno = (TextView)findViewById(R.id.sales_billno);
        Vendorpaymentno=(TextView)findViewById(R.id.VendorPay);
        TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = tel.getDeviceId();
        Log.e("imei is :", device_id);
        Billno.setText(device_id);

        helper = new DBhelper(this);

        selectVendorName=(Spinner)findViewById(R.id.Vendornameforindirectpayment);
        selecttheReason=(Spinner)findViewById(R.id.selectthereason);
        PaymentAmount=(EditText)findViewById(R.id.VendorPaytotal);
        PaymentAmount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});

        Payycash=(Button)findViewById(R.id.indirectpaybycash);
        paybycheque=(Button)findViewById(R.id.indirectpaybycheque);

//        final Long Value = System.currentTimeMillis();
//        final String resval = Long.toString(Value);
//       // Vendorpaymentno.setText(resval);
        invoiceno() ;
        vendors=helper.getvendorsforIndirectPayment();
        ArrayAdapter<VendorModel>VendorName=new ArrayAdapter<VendorModel>(this,android.R.layout.simple_spinner_item,vendors);
        selectVendorName.setAdapter(VendorName);
        selectVendorName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Vendorselected = parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SelectReasons =helper.selecttheReason();
        ArrayAdapter<String>selectPaymentReasons=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,SelectReasons);
        selecttheReason.setAdapter(selectPaymentReasons);
        selecttheReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedReasons = parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Payycash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Vendorselected.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Please Select VendorName or Select Distributor Name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (selectedReasons.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Please select Reason of Payment", Toast.LENGTH_SHORT).show();
                    return;
                } else if (PaymentAmount.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Please Enter Amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                final Long Value = System.currentTimeMillis();
                final String resval = Long.toString(Value);
                helper.saveIndirectPayment(Vendorpaymentno.getText().toString(), Vendorselected, selectedReasons, PaymentAmount.getText().toString(),resval);
                helper.saveDataforPdfVendorIndirectpayment(Vendorpaymentno.getText().toString(), Vendorselected);

                Toast.makeText(getApplicationContext(),Vendorpaymentno.getText().toString() , Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getApplicationContext(), Activitypurchase.class);
                startActivity(in);
            }
        });
        paybycheque.setOnClickListener(new View.OnClickListener() {
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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String BankNameSelected = BankName.getSelectedItem().toString();
                //EnterChequeno.getText().toString();

                if (Vendorselected.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Please Select VendorName or Select Distributor Name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (selectedReasons.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Please select Reason of Payment", Toast.LENGTH_SHORT).show();
                    return;
                } else if (PaymentAmount.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Please Enter Amount", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                } else if (BankNameSelected.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Please select Bank name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (EnterChequeno.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Please Enter Cheque Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                helper.saveIndirectPaymentByCheque(Vendorpaymentno.getText().toString(), Vendorselected, selectedReasons, PaymentAmount.getText().toString(), BankNameSelected, EnterChequeno.getText().toString());
                helper.saveDataforPdfVendorIndirectpayment(Vendorpaymentno.getText().toString(), Vendorselected);
                dialog.dismiss();

                Intent in = new Intent(getApplicationContext(), Activitypurchase.class);
                startActivity(in);
                Toast.makeText(getApplicationContext(), Vendorpaymentno.getText().toString(), Toast.LENGTH_SHORT).show();

            }

        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    dialog.show();
        dialog.setCanceledOnTouchOutside(false);
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
                Vendorpaymentno.setText(x1);
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

            Intent i=new Intent(VendorIndirectPaymentActivity.this,Activitypurchase.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
