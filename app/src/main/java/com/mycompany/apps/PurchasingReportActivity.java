package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class PurchasingReportActivity extends Activity implements View.OnClickListener{
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasing_report);
        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        Button PurchaseBtn=(Button)findViewById(R.id.PurchaseReportPurchase);
        ImageButton InventoryBtn=(ImageButton)findViewById(R.id.PurchaseReportInvntry);
        ImageButton VendorPaymntBtn=(ImageButton)findViewById(R.id.PurchaseReportVendPaymnt);
        ImageButton VendorReturnBtn=(ImageButton)findViewById(R.id.PurchaseReportVendRetrn);

        PurchaseBtn.setOnClickListener(this);
        InventoryBtn.setOnClickListener(this);
        VendorPaymntBtn.setOnClickListener(this);
        VendorReturnBtn.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.PurchaseReportPurchase:
                Intent intent = new Intent(this, ReportTabActivityPurchasing.class);
                startActivity(intent);
                break;
            case R.id.PurchaseReportInvntry:
                Intent in = new Intent(this, ReportTabActivityInventory.class);
                startActivity(in);
                break;
            case R.id.PurchaseReportVendPaymnt:
                Intent intent3 = new Intent(this, VendorPaymentReportActivity.class);
                startActivity(intent3);
                break;
            case R.id.PurchaseReportVendRetrn:
                Intent in4 = new Intent(this, ReportTabActivityVendorReturn.class);
                startActivity(in4);
                break;
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

            Intent i=new Intent(PurchasingReportActivity.this,ActivityLoyalityCust.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

