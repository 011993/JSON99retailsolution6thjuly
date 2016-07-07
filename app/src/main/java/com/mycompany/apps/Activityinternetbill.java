package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Activityinternetbill extends Activity {

    DBhelper helper;
    TextView TicketID;
    TextView SupportTicketStatus;
    TextView SubjectDescription;
    TextView Teammember,Teamproduct;
    ListView listView;
    ActionBar actionBar;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityinternetbill);

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

/*  String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        GDBHelper helper = new GDBHelper(this, dbName);
        SQLiteDatabase db = helper.getReadableDatabase();
        final ArrayList arrayList = helper.getDetailinternet();
            Log.e("%%%%%%%%%%%%%%%%%%%", arrayList.toString());
            listView = (ListView) findViewById(R.id.listView1);
            Log.e("***********Lt1*******", listView.toString());
            BillDetailAdapter billDetailAdapter = new BillDetailAdapter(this, arrayList);
            listView.setAdapter(billDetailAdapter);
            Log.e("***********lt2*******", listView.toString());

    }*/
        String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        helper = new DBhelper(this);

        TicketID = (TextView) findViewById(R.id.Mobileno);
        SupportTicketStatus = (TextView) findViewById(R.id.Productnm);
        SubjectDescription = (TextView) findViewById(R.id.MRP);
        Teammember = (TextView) findViewById(R.id.Status);
       Teamproduct = (TextView) findViewById(R.id.Productname);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String Value = extras.getString("id");
            //String Value = extras.getInt("id", -1);
            //String data=.toString();

            //if (Value != "-1" ) {
            //means this is the view part not the add contact part.
            Cursor rs = helper.getBillReport(Value);
            //  id_To_Update = Value;
            rs.moveToFirst();

            String TicketId = rs.getString(rs.getColumnIndex(DBhelper.COLUMN_MOBILENO));
            TicketID.setText(TicketId);
            String Supportticketstatus = rs.getString(rs.getColumnIndex(DBhelper.COLUMN_ORDERID));
            SupportTicketStatus.setText(Supportticketstatus);
            String Subject_desc = rs.getString(rs.getColumnIndex(DBhelper.COLUMN_MRP));
            SubjectDescription.setText(Subject_desc);
            String teamprod = rs.getString(rs.getColumnIndex(DBhelper.COLUMN_PRODNM));
            Teamproduct.setText(teamprod);
            String teammember = rs.getString(rs.getColumnIndex(DBhelper.COLUMN_STATUS));
            Teammember.setText(teammember);

            //String plac = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));
           /* db.execSQL("update retail_str_bill_details_internet set Status='Deliverd'");*/
            if (!rs.isClosed()) {
                rs.close();
            }

            //}

        }
    }


    public void BackToShowActivity(View view) {
        String values= SupportTicketStatus.getText().toString();
        helper.updateRecord(values);
        //helper.getDetailinternet();
        /*  String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        helper = new GDBHelper(this, dbName);
        SQLiteDatabase db = helper.getWritableDatabase();
        helper = new GDBHelper(this, dbName);
        db.execSQL("update retail_str_bill_details_internet set Status = 'Delivered' where orderid='?'");
        db.close();*/
       // Intent in = new Intent(getApplicationContext(), GMainActivity.class);
        Toast.makeText(getApplicationContext(), "Your order has been Delivered   ", Toast.LENGTH_LONG).show();
      //  startActivity(in);

    }

}
