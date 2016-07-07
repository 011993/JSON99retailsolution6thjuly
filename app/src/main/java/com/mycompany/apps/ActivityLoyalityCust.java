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

public class ActivityLoyalityCust extends Activity implements View.OnClickListener{

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_loyality_cust);

        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);


        ImageButton MasterDataBtn= (ImageButton) findViewById(R.id.MasterdataReports);
        ImageButton MediaBtn=(ImageButton)findViewById(R.id.MediaReports);
        ImageButton PurchasingBtn=(ImageButton)findViewById(R.id.PurchasingReports);
        Button SalesBtn=(Button)findViewById(R.id.Sales_Reports);
        //Button FinancialBtn=(Button)findViewById(R.id.Financial_Reports);
        ImageButton OthersBtn=(ImageButton)findViewById(R.id.OthersReports);

        MasterDataBtn.setOnClickListener(this);
        MediaBtn.setOnClickListener(this);
        PurchasingBtn.setOnClickListener(this);
        SalesBtn.setOnClickListener(this);
        //FinancialBtn.setOnClickListener(this);
        OthersBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.MasterdataReports:
                Intent intent = new Intent(this, ReportTabActivityMasterData.class);
                startActivity(intent);
                break;
            case R.id.MediaReports:
                Intent in = new Intent(ActivityLoyalityCust.this, MediaReportActivity.class);
                startActivity(in);
                break;
            case R.id.PurchasingReports:
                Intent intent3 = new Intent(this,PurchasingReportActivity.class);
                startActivity(intent3);
                break;
            case R.id.Sales_Reports:
                Intent in4 = new Intent(this,ReportTabActivitySalesReport.class);
                startActivity(in4);
                break;
            case R.id.OthersReports:
                Intent in6 = new Intent(this, Activity_Loyalty_Report.class);
                startActivity(in6);
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

            Intent i=new Intent(ActivityLoyalityCust.this,Activity_masterScreen1.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
