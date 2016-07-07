package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
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
import java.security.MessageDigest;
import java.util.ArrayList;

import Pojo.Registeremployeesmodel;


public class UserManagementActivity extends Activity {

    TextView storeid;
    Spinner username;
    EditText firstname;

    EditText lastname;
    EditText password;
    EditText confirmpassword;
    Spinner active;
    Button cancel;
    Button update;
    DBhelper mydb;
    String Update;
    String Update2;
    static TextView res;
    String positions;

    ActionBar actionBar;

    ArrayList<Registeremployeesmodel> arrayList;

    // Data Source

    String ActiveType[];

    // Adapter
    ArrayAdapter<String> adapteractiveType;
    String SpinValue;
    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        mydb = new DBhelper(this);

        ArrayList<String> updatelist3 = mydb.getdataemp();
        String Store_Id_Search = updatelist3.get(0);
        final Bundle databundle = new Bundle();
        databundle.putString("Store_Id", Store_Id_Search);
        Intent i = getIntent();
        i.putExtras(databundle);

        storeid=(TextView)findViewById(R.id.editstoreid);
        username=(Spinner)findViewById(R.id.username);
        firstname=(EditText)findViewById(R.id.editfirstname);
        lastname=(EditText)findViewById(R.id.editlastname);
        password=(EditText)findViewById(R.id.editpassword);
        confirmpassword=(EditText)findViewById(R.id.editconfrmpassword);
        active=(Spinner)findViewById(R.id.editactive);
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        cancel=(Button)findViewById(R.id.buttoncancl);
        update=(Button)findViewById(R.id.buttonupdate);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);



        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapteractiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        adapteractiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        active.setAdapter(adapteractiveType);
        active.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = active.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String Value = extras.getString("Store_Id");
            Cursor rs = mydb.getdatastore(Value);

            rs.moveToFirst();

            String editstoreid = rs.getString(rs.getColumnIndex(DBhelper.STORE_ID_STORE));
            storeid.setText(editstoreid);

        }

         mydb=new DBhelper(UserManagementActivity.this);
        ArrayList<Registeremployeesmodel> reasonReturn= mydb.getusername();
        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(UserManagementActivity.this, android.R.layout.simple_spinner_dropdown_item,reasonReturn);
        username.setAdapter(stringArrayAdapter);

        username.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Registeremployeesmodel value = (Registeremployeesmodel) parent.getItemAtPosition(position);
                String Value = parent.getSelectedItem().toString();
                ArrayList<Registeremployeesmodel> alldata = mydb.getdatafoeregister(Value);
                firstname.setText(alldata.get(0).getFirstname());
                lastname.setText(alldata.get(0).getLastname());
                password.setText(alldata.get(0).getPassword());
                confirmpassword.setText(alldata.get(0).getConfirmpassword());
               // active.setText(alldata.get(0).getActive());
                SpinValue=alldata.get(0).getActive();
                if (SpinValue.equals("Y"))
                {
                    active.setSelection(0);
                }
                if (SpinValue.equals("N"))
                {
                    active.setSelection(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

       cancel.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);



                Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                startActivity(intent);
            }
       });





        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);



                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    username=(Spinner)findViewById(R.id.username);

                    String Value = extras.getString("Store_Id");
                    String Value2=username.getSelectedItem().toString();
                    Update = Value;
                    Update2 = Value2;


                    mydb.updateemployees(Update,Update2, firstname.getText().toString(), lastname.getText().toString(), password.getText().toString(), confirmpassword.getText().toString(), SpinValue);;
                    Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "updated", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    public static String sha256(String base) {
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            res.setText("SHA-256 hash generated is: "+hexString.toString().toUpperCase());
            Log.d("hhh", hexString.toString());
            return hexString.toString();



        } catch(Exception ex){
            throw new RuntimeException(ex);
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

            Intent i=new Intent(UserManagementActivity.this,ActivityLoyality.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






}
