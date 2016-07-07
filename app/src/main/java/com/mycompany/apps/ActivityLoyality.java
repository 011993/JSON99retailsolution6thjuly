package com.mycompany.apps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityLoyality extends Activity implements View.OnClickListener {
    ActionBar actionBar;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_loyality);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        ImageButton imageButton = (ImageButton) findViewById(R.id.loyal1);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.loyal2);
       // ImageButton imageButton3 = (ImageButton) findViewById(R.id.loyal3);
        ImageButton imageButton4 = (ImageButton) findViewById(R.id.loyal4);
        ImageButton imageButton5 = (ImageButton) findViewById(R.id.loyal5);
        ImageButton imageButton6 = (ImageButton) findViewById(R.id.loyal6);
        Button Button7 = ( Button) findViewById(R.id.loyal7);
        Button imageButton8 = ( Button) findViewById(R.id.loyal8);
        ImageButton imageButton9 = ( ImageButton) findViewById(R.id.loyal9);
        ImageButton imageButton10 = (ImageButton) findViewById(R.id.loyal10);
        imageButton.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
       // imageButton3.setOnClickListener(this);
        imageButton4.setOnClickListener(this);
        imageButton5.setOnClickListener(this);
        imageButton6.setOnClickListener(this);
        Button7.setOnClickListener(this);
        imageButton8.setOnClickListener(this);
        imageButton9.setOnClickListener(this);
        imageButton10.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loyal1:

                Intent intent = new Intent(this, Activityloyalitydefine.class);
                startActivity(intent);
                break;
            case R.id.loyal2:
                Intent intent1 = new Intent(this, Activityloyalityaccural.class);
                startActivity(intent1);
                break;
         /*   case R.id.loyal3:
                Intent intent2 = new Intent(this, Activityreport.class);
                startActivity(intent2);
                break;*/
            case R.id.loyal4:
                Intent intent3 = new Intent(this, Activity_Media.class);
                startActivity(intent3);
                break;
            case R.id.loyal5:
                Intent intent4 = new Intent(this,UserManagementActivity.class);
                startActivity(intent4);
                break;
            case R.id.loyal6:
                Intent intent5 = new Intent(this,Activity_Top_Product.class);
                startActivity(intent5);
                break;
            case R.id.loyal7:
                Intent intent6 = new Intent(this, Activity_Bill_Level.class);
                startActivity(intent6);
                break;
            case R.id.loyal8:
                Intent intent7 = new Intent(this, Activity_lineitem_discount.class);
                startActivity(intent7);
                break;
            case R.id.loyal9:
                Intent intent8 = new Intent(this,CustomerRejection.class);
                startActivity(intent8);
                break;
            case R.id.loyal10:
                Intent intent9 = new Intent(this,VendorRejection.class);
                startActivity(intent9);
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

            Intent i=new Intent(ActivityLoyality.this,Activity_masterScreen1.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}