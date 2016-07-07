package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ActivityMaintain extends Activity {
ActionBar actionBar;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
    Bundle syncDataBundle = null;
    private boolean syncInProgress = false;
    private boolean didSyncSucceed = false;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_maintain);
        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void SubBack(View view)
    {
        Intent intent = new Intent(this,Activity_masterScreen1.class);
        startActivity(intent);
    }

/*
    public void SubInsertData(View view)
    {
        String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
*/
/*
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        Date date = new Date();
     *//*

        String result=Long.toString(value);

        Toast.makeText(getApplicationContext(), "" + value, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),"before",Toast.LENGTH_LONG).show();
        db.execSQL("INSERT INTO retail_maintenance(Ticket_id,Support_Ticket_Status,Str_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment)VALUES('" + result + "','open','47','','NetworkingIssues','veryhigh','posgroup','99RS','NOCOMMENT')");
        Toast.makeText(getApplicationContext(),"After",Toast.LENGTH_LONG).show();
        db.close();
    }

*/

    public void NetworkingIssues(View view){
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);

        DBhelper helper = new DBhelper(this);
        Button network = (Button)findViewById(R.id.Networking_Issues);

        SQLiteDatabase db = helper.getWritableDatabase();
        network.setFocusable(true);
        long value= System.currentTimeMillis();
        String result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date)VALUES('"+result+"','Open','133','','Networking Issues','Very high','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"')");
        // db.execSQL("INSERT INTO retail_maintenance(Ticket_id,Support_Ticket_Status,Str_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment)VALUES('"+result+"','open','47','','NetworkingIssues','veryhigh','posgroup','OURTEAM','NOCOMMENT')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"NETWORK ISSUE RAISED",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);
        db.close();

    }

    public void Masterdatasupport(View view){


        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        DBhelper helper = new DBhelper(this);
        view.startAnimation(Buttonok);
        SQLiteDatabase db = helper.getWritableDatabase();
        long value= System.currentTimeMillis();
        String result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date)VALUES('" + result + "','Open','133','','Master Data Support','Very high','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"')");
        // db.execSQL("INSERT INTO retail_maintenance(Ticket_id,Support_Ticket_Status,Str_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment)VALUES('"+result+"','open','47','','NetworkingIssues','veryhigh','posgroup','OURTEAM','NOCOMMENT')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"MASTER DATA SUPPORT ISSUE RAISED",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();
    }

    public void Printernotworking(View view){

        DBhelper helper = new DBhelper(this);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        SQLiteDatabase db = helper.getWritableDatabase();
        long value= System.currentTimeMillis();
        String result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date)VALUES('" + result + "','Open','133','','Printer Not Working','High','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"PRINTER ISSUE RAISED",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);


    }

    public void Scannernotworking(View view){

        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        long value= System.currentTimeMillis();
        String result=Long.toString(value);
       Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date)VALUES('" + result + "','Open','133','','Scanner Not Working','High','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"SCANNER ISSUE RAISED",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();

    }

    public void Simcardnotworking(View view){

        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        long value= System.currentTimeMillis();
        String result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date)VALUES('" + result + "','Open','133','','Simcard Not Working','Veryhigh','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"SIM CARD ISSUE RAISED",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();

    }


    public void Walletnotworking(View view){

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        long value= System.currentTimeMillis();
        String result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date)VALUES('" + result + "','Open','133','','Wallet Not Working','Medium','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"WALLET ISSUE RAISED",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();

    }

    public void Purchaseprocess(View view){

        DBhelper helper = new DBhelper(this);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        String result=Long.toString(value);
       Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date)VALUES('"+result+"','Open','133','','Purchase Process','High','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
       Toast.makeText(getApplicationContext(),"PURCHASE PROCESS ISSUE RAISED",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();

    }

    public void Goodsreceipt(View view)
    {

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        String result=Long.toString(value);
       Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date)VALUES('" + result + "','Open','133','','Goods Receipt','High','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"GOODS RECEIPT ISSUE RAISED",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();
    }


    public void Invoiceprocess(View view)
    {

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        String result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date)VALUES('" + result + "','Open','133','','Invoice Process','High','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"INVOICE PROCESS ISSUE RAISED",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();


    }

    public void Salesprocess(View view)
    {

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        String result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date)VALUES('" + result + "','Open','133','','Sales Process','Veryhigh','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"SALES PROCESS ISSUE RAISED",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();


    }

    public void Salesreturn(View view)
    {

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        long value= System.currentTimeMillis();
        String result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date)VALUES('" + result + "','Open','133','','Sales Return','High','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"SALES RETURN ISSUE RAISED",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();


    }

    public void Reportingerror(View view)
    {
      final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);

        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        String result=Long.toString(value);
       Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date)VALUES('" + result + "','Open','133','','Reporting Error','Medium','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
       Toast.makeText(getApplicationContext(),"REPORTING ERROR ISSUE RAISED",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();


    }


    public void Paymentrelated(View view)
    {

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        String result=Long.toString(value);
       Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date)VALUES('"+result+"','Open','133','','Payment Related','High','POSGROUP','99RS','NOCOMMENT')");
        // db.execSQL("INSERT INTO retail_maintenance(Ticket_id,Support_Ticket_Status,Str_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment)VALUES('"+result+"','open','47','','NetworkingIssues','veryhigh','posgroup','OURTEAM','NOCOMMENT')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"PAYMENT RELATED ISSUE RAISED",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();


    }

    public void Manufacturingpayment(View view)
    {

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        String result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date)VALUES('"+result+"','Open','133','','Manufacturing Payment','High','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"')");
        // db.execSQL("INSERT INTO retail_maintenance(Ticket_id,Support_Ticket_Status,Str_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment)VALUES('"+result+"','open','47','','NetworkingIssues','veryhigh','posgroup','OURTEAM','NOCOMMENT')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
       Toast.makeText(getApplicationContext(),"MANUFACTURING PAYMENT ISSUE RAISED",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();


    }

    public void Anyothers(View view)
    {

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        String result=Long.toString(value);
       Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();
        db.execSQL("insert into retail_Store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date)VALUES('"+result+"','Open','133','','Any Others','Medium','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"')");
        // db.execSQL("INSERT INTO retail_maintenance(Ticket_id,Support_Ticket_Status,Str_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment)VALUES('"+result+"','open','47','','NetworkingIssues','veryhigh','posgroup','OURTEAM','NOCOMMENT')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"ANY OTHERS ISSUE RAISED",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();


    }


    public void Hardwarecrash(View view)
    {

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        String result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date)VALUES('"+result+"','Open','133','','Hardware Crash','veryhigh','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"')");
        // db.execSQL("INSERT INTO retail_maintenance(Ticket_id,Support_Ticket_Status,Str_Id,LastUpdate,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment)VALUES('"+result+"','open','47','','NetworkingIssues','veryhigh','posgroup','OURTEAM','NOCOMMENT')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"HARDWARE CRASH ISSUE RAISED",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main_maintainence, menu);
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
            Intent i=new Intent(ActivityMaintain.this,Activity_masterScreen1.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
