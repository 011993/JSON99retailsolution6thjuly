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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ActivityTender extends Activity implements TextWatcher {

    ActionBar actionBar;
    TextView Posdate;
    TextView Systemdate;
    Button btn;
    EditText Text1,Text2,Text3,Text4,Text5,Text6,Text7,Text8;
   TextView ResultField1000,ResultField500,ResultField100,ResultField50,ResultField10,ResultField5,ResultField2,ResultField1;
    TextView TotalResult;
    EditText posdate;

    String Trans_Id_Update;
    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_tender);
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        ResultField1000 = (TextView)findViewById(R.id.editTextres1000);
        ResultField500=(TextView)findViewById(R.id.editTextres500);
        ResultField100=(TextView)findViewById(R.id.editTextres100);
        ResultField50=(TextView)findViewById(R.id.editTextres50);
        ResultField10=(TextView)findViewById(R.id.editTextres10);
        ResultField5=(TextView)findViewById(R.id.editTextres5);
        ResultField2=(TextView)findViewById(R.id.editTextres2);
        ResultField1=(TextView)findViewById(R.id.editTextres1);
         posdate = (EditText)findViewById(R.id.posdate);
         Systemdate = (TextView)findViewById(R.id.systemdate);
        Posdate = (TextView)findViewById(R.id.posdateno);
        TotalResult=(TextView)findViewById(R.id.Totalresult);
        final Date date = new Date();
        final SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd ", Locale.getDefault());
       Systemdate.setText(dateFormat.format(date));
       Posdate.setText(dateFormat.format(date));



        // editTextres1 = (EditText) findViewById(R.id.editText1000);
        Text1 = (EditText)findViewById(R.id.editText1000);
        Text1.addTextChangedListener(this);

        Text2=(EditText)findViewById(R.id.editText500);
        Text2.addTextChangedListener(this);

        Text3=(EditText)findViewById(R.id.editText100);
        Text3.addTextChangedListener(this);

        Text4=(EditText)findViewById(R.id.editText50);
        Text4.addTextChangedListener(this);

        Text5=(EditText)findViewById(R.id.editText10);
        Text5.addTextChangedListener(this);

        Text6=(EditText)findViewById(R.id.editText5);
        Text6.addTextChangedListener(this);

        Text7=(EditText)findViewById(R.id.editText2);
        Text7.addTextChangedListener(this);

        Text8=(EditText)findViewById(R.id.editText1);
        Text8.addTextChangedListener(this);



        final DBhelper  mydb=new DBhelper(this);
        Cursor cs = mydb.gettender();
        if(cs.moveToFirst())
        {
            do {
                String localprodstore = cs.getString(cs.getColumnIndex(DBhelper.TRANSACTIONID));
                posdate.setText(localprodstore);
            }while (cs.moveToNext());
        }

        Button closeday = (Button)findViewById(R.id.tender_closeday_button);
        closeday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                posdate = (EditText) findViewById(R.id.posdate);
                TotalResult = (TextView) findViewById(R.id.Totalresult);
                if (TotalResult.getText().toString().matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Please fill the Field", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mydb.CheckTenderDateAlreadyInDBorNot(TotalResult.getText().toString()))

                {
                    Toast.makeText(getApplicationContext(), "DAY IS ALREADY CLOSED", Toast.LENGTH_SHORT).show();
                    return;
                }
                String value = posdate.getText().toString();
                Trans_Id_Update = value;
                mydb.updatedayclose(Trans_Id_Update, TotalResult.getText().toString());
                Toast.makeText(getApplicationContext(), "DAY CLOSE", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                startActivity(intent);


            }
        });



        Button Exit = (Button) findViewById(R.id.tender_exit_button);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);
                Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                startActivity(intent);
            }
        });

    }






    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        calcResult();

    }


    private void calcResult() throws NumberFormatException {
        Editable editableText1 = Text1.getText();
        Editable editableText2=Text2.getText();
        Editable editableText3=Text3.getText();
        Editable editableText4=Text4.getText();
        Editable editableText5=Text5.getText();
        Editable editableText6=Text6.getText();
        Editable editableText7=Text7.getText();
        Editable editableText8=Text8.getText();

        // editableText2 = Text2.getText();

        double  value1000 = 1000.0,value500=500.0,value100=100.0,value50=50.0,value10=10.0,value05=5.0,value02=2.0,
                value01=1.0,
                value1 = 0.0,value2=0.0,value3=0.0,value4=0.0,value5=0.0,value6=0.0,value7=0.0,value8=0.0,
                result1,result2,result3,result4,result5,result6,result7,result8;

        double total;

        if (editableText1 != null && editableText1.length() >= 1)
            value1 = Double.parseDouble(editableText1.toString());

        if (editableText2 != null && editableText2.length() >= 1)
            value2 = Double.parseDouble(editableText2.toString());

        if (editableText3 != null && editableText3.length() >= 1)
            value3 = Double.parseDouble(editableText3.toString());

        if (editableText4!= null && editableText4.length() >= 1)
            value4 = Double.parseDouble(editableText4.toString());

        if (editableText5 != null && editableText5.length() >= 1)
            value5 = Double.parseDouble(editableText5.toString());

        if (editableText6!= null && editableText6.length() >= 1)
            value6= Double.parseDouble(editableText6.toString());

        if (editableText7!= null && editableText7.length() >= 1)
            value7 = Double.parseDouble(editableText7.toString());

        if (editableText8!= null && editableText8.length() >= 1)
            value8= Double.parseDouble(editableText8.toString());


DecimalFormat f= new DecimalFormat("##.0");

        // Whatever your magic formula is
        result1 = value1000 * value1;
        ResultField1000.setText((Double.toString(result1)));

        result2=value500*value2;
        ResultField500.setText((Double.toString(result2)));

        result3=value100*value3;
        ResultField100.setText((Double.toString(result3)));

        result4=value50*value4;
        ResultField50.setText((Double.toString(result4)));

        result5=value10*value5;
        ResultField10.setText((Double.toString(result5)));

        result6=value05*value6;
        ResultField5.setText((Double.toString(result6)));

        result7=value02*value7;
        ResultField2.setText((Double.toString(result7)));

        result8=value01*value8;
        ResultField1.setText((Double.toString(result8)));



        total=result1+result2+result3+result4+result5+result6+result7+result8;
        TotalResult.setText((Double.toString(total)));


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

            Intent i=new Intent(ActivityTender.this,ActivitySales.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
