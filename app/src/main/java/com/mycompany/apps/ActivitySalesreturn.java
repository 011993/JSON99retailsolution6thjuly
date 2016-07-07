package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import Adapter.ProductAutoAdapter;
import Adapter.SalesAdapter;
import Adapter.SalesProductNmAdaptor;
import Adapter.SalesReturnAdapter;
import Adapter.SalesReturnProductAdapter;
import Pojo.PurchaseProductModel;
import Pojo.Sales;
import Pojo.SalesReturn;
import Pojo.Salesreturndetail;
import Pojo.VendorModel;


public class ActivitySalesreturn extends Activity
{
    ArrayList<Salesreturndetail> Returnlist;
    SalesReturnAdapter ReturnListAdapter;
    DBhelper mydb;
    ListView listView;
    TextView invoice ;
    Button clear,submit;
    Spinner reason;
    private TextView Grandtotal;
    String item;
    ActionBar actionBar;
    private TextWatcher  ProductNameTextWatcher;
    AutoCompleteTextView ProductName;
    SalesReturnProductAdapter productNmAdaptor;
    ArrayList<Salesreturndetail> ProductNameArrayList;
    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_salesreturn);
        listView = (ListView)findViewById(R.id.lv_PurchaseProduct);
        invoice = (TextView)findViewById(R.id.invoiceno);
        Grandtotal=(TextView)findViewById(R.id.GrandTotal);
        reason = (Spinner)findViewById(R.id.reasonreturn);
        clear = (Button)findViewById(R.id.clear);
        submit = (Button)findViewById(R.id.salesreturn_submit);
        actionBar=getActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        ProductName = (AutoCompleteTextView) findViewById(R.id.autoProductName);
      //  ProductName.setThreshold(3);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        SelectPurchaseAlertDialog();
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ReturnListAdapter.clearAllRows();
                    invoice.setText("");
                    Grandtotal.setText("");
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                // setSummaryRow();
            }
        });

        final DBhelper mydb=new DBhelper(ActivitySalesreturn.this);
        ArrayList<Salesreturndetail> reasonReturn= mydb.getReasonReturn();
        ArrayAdapter<Salesreturndetail > stringArrayAdapter =
                new ArrayAdapter<Salesreturndetail>(ActivitySalesreturn.this, android.R.layout.simple_spinner_dropdown_item,reasonReturn);
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
                try {

                    mydb.insertsalesreturn(invoice.getText().toString(), ReturnListAdapter.getList(), item.toString());
                    mydb.insertdataIntosendMailforSalesReturn(invoice.getText().toString());
                    mydb.insertsalereturndataforproductdetail(invoice.getText().toString(),ReturnListAdapter.getList());
                    mydb.updateqtyforinvoice(ReturnListAdapter.getList());
                    Toast.makeText(getApplicationContext(),invoice.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                    startActivity(intent);

                } catch (NullPointerException ex){
                ex.printStackTrace();
            }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });
    }


    public void SelectPurchaseAlertDialog() {

        LayoutInflater inflater = getLayoutInflater();

        final View alertLayout = inflater.inflate(R.layout.alert_dialog_salesreturn, null);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Button submit = (Button) alertLayout.findViewById(R.id.invoice_submit_button);
        final Button NewPurchase = (Button) alertLayout.findViewById(R.id.Noinvoice);
        final Button OldPurchase = (Button) alertLayout.findViewById(R.id.Invoiceno);
        final EditText LastInvoiceno = (EditText) alertLayout.findViewById(R.id.EnterInvoiceno);
        final Button cancel=(Button)alertLayout.findViewById(R.id.Cancel);
        LastInvoiceno.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.VISIBLE);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        alert.setTitle("");
        final AlertDialog dialog = alert.create();
        OldPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LastInvoiceno.setVisibility(View.VISIBLE);
                NewPurchase.setVisibility(View.INVISIBLE);
                submit.setVisibility(View.VISIBLE);
            }
        });

        NewPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_Salesreturn_withoutinvoiceno.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String userTypedInvoiceno = LastInvoiceno.getText().toString().trim();
                    Log.e("!!!!!!!!!!!", "" + LastInvoiceno.toString());
                    if (userTypedInvoiceno.equals("")) {
                        return;
                    }
                    final DBhelper mydb = new DBhelper(ActivitySalesreturn.this);
//                    if (mydb.CheckIsInvoiceReturn(invoice.getText().toString()))
//                    {
//                        Toast.makeText(getApplicationContext(),"Invoice Alredy return", Toast.LENGTH_SHORT).show();
//                        return;
//                    }


                    Returnlist = mydb.getSalesReturn(userTypedInvoiceno);
                    Log.d("!@#!@#!@#!@#", "Product arraylist size is " + Returnlist.size());

                    if (Returnlist != null && Returnlist.size() > 0) {
                        if (ReturnListAdapter == null) {
                            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                            ReturnListAdapter = new SalesReturnAdapter(ActivitySalesreturn.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<Salesreturndetail>());
                            listView.setAdapter(ReturnListAdapter);
                            String res = LastInvoiceno.getText().toString();
                            invoice.setText(res);
                        }
                        for (Salesreturndetail prod : Returnlist) {
                            ReturnListAdapter.addProductToList(prod);
                        }
                        //  ReturnListAdapter.addProductToList(Returnlist.get(0));
                        // Log.i("&&&&&&&&!", "Adding " + Returnlist.get(0) + " to Product List..");
                        ReturnListAdapter.notifyDataSetChanged();
                        grandtotal();
                        dialog.dismiss();
                    }

                }catch (NullPointerException ex) {
                    ex.printStackTrace();

                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OldPurchase.setVisibility(View.VISIBLE);
                NewPurchase.setVisibility(View.VISIBLE);
                submit.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                startActivity(intent);

            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);


    }

    public void grandtotal()
    {
        DecimalFormat f=new DecimalFormat("##.00");
        float Getval = ReturnListAdapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        Grandtotal.setText(GrandVal);


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


            Intent i=new Intent(ActivitySalesreturn.this,Activity_masterScreen1.class);
            startActivity(i);
            return true;


        }

        return super.onOptionsItemSelected(item);
    }




}