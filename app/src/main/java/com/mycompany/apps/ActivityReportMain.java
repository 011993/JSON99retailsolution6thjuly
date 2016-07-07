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
import android.widget.ImageButton;


public class ActivityReportMain extends Activity implements View.OnClickListener{
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_report);
        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        ImageButton imageButton=(ImageButton)findViewById(R.id.advertisingimage);
        ImageButton imageButton2=(ImageButton)findViewById(R.id.customerimage);
        ImageButton imageButton3=(ImageButton)findViewById(R.id.newsimage);
        ImageButton imageButton4=(ImageButton)findViewById(R.id.Report_dist);
        ImageButton imageButton5=(ImageButton)findViewById(R.id.Report_localproduct);
        ImageButton imageButton6=(ImageButton)findViewById(R.id.Report_localvendor);
        ImageButton imageButton7=(ImageButton)findViewById(R.id.Report_product);

        ImageButton imageButton8=(ImageButton)findViewById(R.id.salesreportimage);
        ImageButton imageButton9=(ImageButton)findViewById(R.id.purchasereportimage);

        imageButton.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        imageButton4.setOnClickListener(this);
        imageButton5.setOnClickListener(this);
        imageButton6.setOnClickListener(this);
        imageButton7.setOnClickListener(this);
        imageButton8.setOnClickListener(this);
        imageButton9.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.advertisingimage:
                Intent intent = new Intent(this, AdTickerReportActivity.class);
                startActivity(intent);
                break;
            case R.id.customerimage:
                Intent intent1 = new Intent(this, BlinkingLogoReportActivity.class);
                startActivity(intent1);
                break;
            case R.id.newsimage:
                Intent intent2 = new Intent(this, MainAdvertisementReportActivity.class);
                startActivity(intent2);
                break;
            case R.id.Report_localproduct:
                Intent intent3 = new Intent(this, LocalProductPharmaReportActivity.class);
                startActivity(intent3);
                break;
            case R.id.Report_localvendor:
                Intent intent4 = new Intent(this, VendorReportActivity.class);
                startActivity(intent4);
                break;
            case R.id.Report_product:
                Intent intent5 = new Intent(this, ProductPharmaReportActivity.class);
                startActivity(intent5);
                break;
            case R.id.Report_dist:
                Intent intent6 = new Intent(this, DistributorReportActivity.class);
                startActivity(intent6);
                break;
            case R.id.salesreportimage:
                Intent intent7 = new Intent(this, ReportSaleActivity.class);
                startActivity(intent7);
                break;
            case R.id.purchasereportimage:
                Intent intent8 = new Intent(this,ReportTabActivityPurchasing.class);
                startActivity(intent8);
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
        if (id == R.id.action_settings)
        {
            Intent i=new Intent(ActivityReportMain.this,Activity_masterScreen1.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}