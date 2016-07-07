package com.mycompany.apps;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import JSON.JSONParser;
import JSON.JSONParserSync;

public class Activity_Installation extends Activity implements OnClickListener {
    public EditText user, pass;
    private Button bLogin;
    // Progress Dialog
    private ProgressDialog pDialog;
    //rahul created this file
    // JSON parser class



    //rajeev diwan
    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = "http://52.76.28.14/Android/arun.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    DBhelper db;
    ActionBar actionBar;
//    ProgressDialog progressBar;
//    private int progressBarStatus = 0;
//    private Handler progressBarHandler = new Handler();
//    private long fileSize = 0;

    String STORE_ID;
    public String JSON_STRING,JSON_STRING1,table_data;
    private ArrayList<String>data1;
    private ArrayList<String>data2;
    private ArrayList<String>data3;
    private ArrayList<String>data4;
    private ArrayList<String>data5;
    private ArrayList<String>data6;
    private ArrayList<String>data7;
    private ArrayList<String>data8;
    private ArrayList<String>data9;
    private ArrayList<String>data10;
    private ArrayList<String>data11;
    private ArrayList<String>data12;
    private  ArrayList<String>data13;
    private ArrayList<String>data14;
    private ArrayList<String>data15;
    private ArrayList<String>data16;
    private ArrayList<String>data17;
    private ArrayList<String>data18;
    private ArrayList<String>data19;
    private ArrayList<String>data20;
    private ArrayList<String>data21;
    private ArrayList<String>data22;
    private ArrayList<String>data23;
    private ArrayList<String>data24;
    private ArrayList<String>data25;
    private ArrayList<String>data26;
    private ArrayList<String>data27;
    private ArrayList<String>data28;
    private ArrayList<String>data29;
    private ArrayList<String>data30;
    private ArrayList<String>data31;
    private ArrayList<String>data32;
    private ArrayList<String>data33;
    private ArrayList<String>data34;
    private ArrayList<String>data35;
    private ArrayList<String>data36;
    private ArrayList<String>data37;
    private ArrayList<String>data38;
    private ArrayList<String>data39;
    private ArrayList<String>data40;
    private ArrayList<String>data41;
    private ArrayList<String>data42;
    private ArrayList<String>data43;
    private ArrayList<String>data44;
    private ArrayList<String>data45;
    private ArrayList<String>data46;
    private ArrayList<String>data47;
    private ArrayList<String>data48;
    private ArrayList<String>data49;
    private ArrayList<String>data50;
    private ArrayList<String>data51;
    private ArrayList<String>data52;
    private ArrayList<String>data53;
    private ArrayList<String>data54;
    private ArrayList<String>data55;
    private ArrayList<String>data56;
    private ArrayList<String>data57;
    private ArrayList<String>data58;
    private ArrayList<String>data59;
    private ArrayList<String>data60;
    private ArrayList<String>data61;
    private ArrayList<String>data62;
    private ArrayList<String> field1 ;
    private ArrayList<String> field2 ;
    private ArrayList<String> field3 ;
    private ArrayList<String> field4 ;
    private ArrayList<String> field5;
    private ArrayList<String> field6 ;
    private ArrayList<String> field7 ;
    private ArrayList<String> field8 ;
    private ArrayList<String> field9 ;
    private ArrayList<String> field10 ;
    private ArrayList<String> field11 ;
    private ArrayList<String> field12 ;
    private ArrayList<String> field13 ;
    private ArrayList<String> field14 ;
    private ArrayList<String> field15;
    private ArrayList<String> field16 ;
    private ArrayList<String> field17 ;
    private ArrayList<String> field18 ;
    private ArrayList<String> field19 ;
    private ArrayList<String> field20 ;
    private ArrayList<String> field21 ;
    private ArrayList<String> field22 ;
    private ArrayList<String> field23 ;
    private ArrayList<String> field24 ;
    private ArrayList<String> field25;
    private ArrayList<String> field26 ;
    private ArrayList<String> field27 ;
    private ArrayList<String> field28 ;
    private ArrayList<String> field29 ;
    private ArrayList<String> field30 ;
    private ArrayList<String> field31 ;
    private ArrayList<String> field32 ;
    private ArrayList<String> field33 ;
    private ArrayList<String> field34 ;
    private ArrayList<String> field35;
    private ArrayList<String> field36 ;
    private ArrayList<String> field37 ;
    private ArrayList<String> field38 ;
    private ArrayList<String> field39 ;
    private ArrayList<String> field40 ;
    private ArrayList<String> field41 ;
    private ArrayList<String> field42;
    private ArrayList<String> field43 ;
    private ArrayList<String> field44;
    private ArrayList<String> field45;
    private ArrayList<String> field46;
    private ArrayList<String> field47;
    private ArrayList<String> field48;
    private ArrayList<String> field49;
    private ArrayList<String> field50;
    private ArrayList<String> field51;
    private ArrayList<String> field52;
    private ArrayList<String> field53;
    private ArrayList<String> field54;
    private ArrayList<String> field55;
    private ArrayList<String> field56;
    private ArrayList<String> field57;
    private ArrayList<String> field58;
    private ArrayList<String> field59;
    private ArrayList<String> field60;
    private ArrayList<String> field61;
    private ArrayList<String> field62;




    private ArrayList<String>values=new ArrayList<>();


    String tablename_1="ret_ticket_install_register";
    String tablename_2="retail_ad_blinkinglogo";
    String tablename_3="retail_ad_blinkinglogo_cont";
    String tablename_4="retail_ad_store_main";
    String tablename_5="retail_ad_store_main_cont";
    String tablename_6="retail_ad_ticker ";
    String tablename_7="retail_ad_ticker_cont";
    String tablename_8="retail_card_define";
    String tablename_9="retail_card_define_mfg";
    String tablename_10="retail_comp_btl";
    String tablename_11="retail_cust";
    String tablename_12="retail_cust_loyalty";
    String tablename_13="retail_inventory";
    String tablename_14="retail_media";
    String tablename_15="retail_mfg_btl";
    String tablename_16="retail_store";
    String tablename_17="retail_store_maint";
    String tablename_18="retail_store_prod";
    String tablename_19="retail_store_prod_local_cpg";
    String tablename_20="retail_store_reports";
    String tablename_21="retail_store_vend_reject";
    String tablename_22="retail_store_vendor";
    String tablename_23="retail_str_dstr";
    String tablename_24="retail_str_grn_detail";
    String tablename_25="retail_str_grn_master";
    String tablename_26="retail_str_po_detail";
    String tablename_27="retail_str_po_master";
    String tablename_28="retail_str_sales_detail";
    String tablename_29="retail_str_sales_details_return";
    String tablename_30="retail_str_sales_master_return";
    String tablename_31="retail_str_sales_master";
    String tablename_32="retail_str_vendor_detail_return";
    String tablename_33="retail_str_vendor_master_return";
    String tablename_34="retail_tax";
    String tablename_35="retail_top_product";
    String tablename_36="retail_vend_dstr";
    String tablename_37="retail_videodata";
    String tablename_38="retail_videodata_cont";
    String tablename_39="retail_videodata_cont1";
    String tablename_40="rule_defination";
    String tablename_41="tmp_retail_pay_desc";
    String tablename_42="ad_ticker_main";
    String tablename_43="bank_details";
    String tablename_44="retail_store_prod_com";
    String tablename_45= "retail_store_prod_cpg";
    String tablename_46="retail_store_prod_local";
    String tablename_47= "retail_str_po_detail_hold";
    String tablename_48="retail_str_stock_master";
    String tablename_49="retail_str_stock_master_hold";
    String tablename_50="retail_str_day_open_close";
    String tablename_51="retail_store_sales_desc";
    String tablename_52="retail_employees";
    String tablename_53="tmp_retail_str_sales_detail";
    String tablename_54="tmp_retail_str_sales_master";
    String tablename_55 = "dr_speciality";
    String tablename_56="dr_discription";
    String tablename_57="retail_send_mail_pdf";
    String tablename_58="retail_str_lineitem_disc";
    String tablename_59="ad_main";
    String tablename_60="retail_media_click";
    String tablename_61="tmp_vend_dstr_payment";
    String tablename_62="retail_str_bill_lvl_disc";

    String id,INSERT;



    SQLiteDatabase myDataBase;
    SQLiteStatement insertStmt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        try {


            DBhelper db = new DBhelper(Activity_Installation.this);


            Boolean fortrue = db.checkDataBase();
            if (fortrue == true) {
                Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
                startActivity(intent);
                return;
            } else {
                setContentView(R.layout.activity_activity__installation);
                actionBar = getActionBar();
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(true);
                actionBar.setIcon(R.drawable.w);
                user = (EditText) findViewById(R.id.username);
                pass = (EditText) findViewById(R.id.password);
                bLogin = (Button) findViewById(R.id.login);
                bLogin.setOnClickListener(this);
                getJSON();
                //   addListenerOnButtonClick();
//        SharedPreferences pref = getSharedPreferences("Activity_Installation", Context.MODE_PRIVATE);
//        if(pref.getBoolean("activity_executed", false)){
//            Intent intent = new Intent(this, GMainActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            SharedPreferences.Editor ed = pref.edit();
//            ed.putBoolean("activity_executed", true);
//            ed.commit();
//        }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //    public void addListenerOnButtonClick() {
//        btnStartProgress = (Button) findViewById(R.id.button1);
//        btnStartProgress.setOnClickListener(new OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                // creating progress bar dialog
//                progressBar = new ProgressDialog(v.getContext());
//                progressBar.setCancelable(true);
//                progressBar.setMessage("File downloading ...");
//                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                progressBar.setProgress(0);
//                progressBar.setMax(100);
//                progressBar.show();
//                //reset progress bar and filesize status
//                progressBarStatus = 0;
//                fileSize = 0;
//
//                new Thread(new Runnable() {
//                    public void run() {
//                        while (progressBarStatus < 100) {
//                            // performing operation
//                            progressBarStatus = doOperation();
//                            try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
//                            // Updating the progress bar
//                            progressBarHandler.post(new Runnable() {
//                                public void run() {
//                                    progressBar.setProgress(progressBarStatus);
//                                }
//                            });
//                        }
//                        // performing operation if file is downloaded,
//                        if (progressBarStatus >= 100) {
//                            // sleeping for 1 second after operation completed
//                            try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
//                            // close the progress bar dialog
//                            progressBar.dismiss();
//                        }
//                    }
//                }).start();
//            }//end of onClick method
//        });
//    }
    // checking how much file is downloaded and updating the filesize
//    public int doOperation() {
//        //The range of ProgressDialog starts from 0 to 10000
//        while (fileSize <= 10000) {
//            fileSize++;
//            if (fileSize == 1000) {
//                return 10;
//            } else if (fileSize == 2000) {
//                return 20;
//            } else if (fileSize == 3000) {
//                return 30;
//            } else if (fileSize == 4000) {
//                return 40;//you can add more else if
//            } else{
//                return 100;
//            }
//        }//end of while
//        return 100;
//    }//end of doOperation
    void jsonparsed(String input) {

        field1 = new ArrayList<>();
        field2 = new ArrayList<>();
        field3 = new ArrayList<>();
        field4 = new ArrayList<>();
        field5 = new ArrayList<>();
        field6 = new ArrayList<>();
        field7 = new ArrayList<>();
        field8 = new ArrayList<>();
        field9 = new ArrayList<>();
        field10 = new ArrayList<>();
        field11 = new ArrayList<>();
        field12 = new ArrayList<>();
        field13 = new ArrayList<>();
        field14 = new ArrayList<>();
        field15 = new ArrayList<>();
        field16 = new ArrayList<>();
        field17 = new ArrayList<>();
        field18 = new ArrayList<>();
        field19 = new ArrayList<>();
        field20 = new ArrayList<>();
        field21 = new ArrayList<>();
        field22 = new ArrayList<>();
        field23 = new ArrayList<>();
        field24 = new ArrayList<>();
        field25 = new ArrayList<>();
        field26 = new ArrayList<>();
        field27 = new ArrayList<>();
        field28 = new ArrayList<>();
        field29 = new ArrayList<>();
        field30 = new ArrayList<>();
        field31 = new ArrayList<>();
        field32 = new ArrayList<>();
        field33 = new ArrayList<>();
        field34 = new ArrayList<>();
        field35 = new ArrayList<>();
        field36 = new ArrayList<>();
        field37 = new ArrayList<>();
        field38 = new ArrayList<>();
        field39 = new ArrayList<>();
        field40 = new ArrayList<>();
        field41 = new ArrayList<>();
        field42 = new ArrayList<>();
        field43 = new ArrayList<>();
        field44 = new ArrayList<>();
        field45 = new ArrayList<>();
        field46 = new ArrayList<>();
        field47 = new ArrayList<>();
        field48 = new ArrayList<>();
        field49 = new ArrayList<>();
        field50 = new ArrayList<>();
        field51 = new ArrayList<>();
        field52 = new ArrayList<>();
        field53 = new ArrayList<>();
        field54 = new ArrayList<>();
        field55 = new ArrayList<>();
        field56 = new ArrayList<>();
        field57 = new ArrayList<>();
        field58 = new ArrayList<>();
        field59 = new ArrayList<>();
        field60 = new ArrayList<>();
        field61 = new ArrayList<>();
        field62 = new ArrayList<>();


        data1 = new ArrayList<>();
        data2 = new ArrayList<>();
        data3 = new ArrayList<>();
        data4 = new ArrayList<>();
        data5 = new ArrayList<>();
        data6 = new ArrayList<>();
        data7 = new ArrayList<>();
        data8 = new ArrayList<>();
        data9 = new ArrayList<>();
        data10 = new ArrayList<>();
        data11 = new ArrayList<>();
        data12 = new ArrayList<>();
        data13 = new ArrayList<>();
        data14 = new ArrayList<>();
        data15 = new ArrayList<>();
        data16 = new ArrayList<>();
        data17 = new ArrayList<>();
        data18 = new ArrayList<>();
        data19 = new ArrayList<>();
        data20 = new ArrayList<>();
        data21 = new ArrayList<>();
        data22 = new ArrayList<>();
        data23 = new ArrayList<>();
        data24 = new ArrayList<>();
        data25 = new ArrayList<>();
        data26 = new ArrayList<>();
        data27 = new ArrayList<>();
        data28 = new ArrayList<>();
        data29 = new ArrayList<>();
        data30 = new ArrayList<>();
        data31 = new ArrayList<>();
        data32 = new ArrayList<>();
        data33 = new ArrayList<>();
        data34 = new ArrayList<>();
        data35 = new ArrayList<>();
        data36 = new ArrayList<>();
        data37 = new ArrayList<>();
        data38 = new ArrayList<>();
        data39 = new ArrayList<>();
        data40 = new ArrayList<>();
        data41 = new ArrayList<>();
        data42 = new ArrayList<>();
        data43 = new ArrayList<>();
        data44 = new ArrayList<>();
        data45 = new ArrayList<>();
        data46 = new ArrayList<>();
        data47 = new ArrayList<>();
        data48 = new ArrayList<>();
        data49 = new ArrayList<>();
        data50 = new ArrayList<>();
        data51 = new ArrayList<>();
        data52 = new ArrayList<>();
        data53 = new ArrayList<>();
        data54 = new ArrayList<>();
        data55 = new ArrayList<>();
        data56 = new ArrayList<>();
        data57 = new ArrayList<>();
        data58 = new ArrayList<>();
        data59 = new ArrayList<>();
        data60 = new ArrayList<>();
        data61 = new ArrayList<>();
        data62 = new ArrayList<>();


        try {
            //storeId = storeID.getText().toString();

            JSONObject jsonaman = new JSONObject(input);
            JSONArray firsttitlearray = jsonaman.getJSONArray(Config.TAG_ARRAY_TABLE_ONE);
            for (int i = 0; i < firsttitlearray.length(); i++) {
                JSONObject obj1 = (JSONObject) firsttitlearray.get(i);
                id = obj1.getString(Config.TAG_FIELD);
                field1.add(id);
                System.gc();

            }
            createDynamicDatabase(Activity_Installation.this, tablename_1, field1);
            System.gc();
        } catch (JSONException e) {
            Log.e("exception", e.toString());
        }

        try {

            JSONObject jsonObject2 = new JSONObject(input);
            JSONArray firsttitlearray2 = jsonObject2.getJSONArray(Config.TAG_ARRAY_TABLE_TWO);
            for (int i = 0; i < firsttitlearray2.length(); i++) {
                JSONObject obj2 = (JSONObject) firsttitlearray2.get(i);
                id = obj2.getString(Config.TAG_FIELD);
                field2.add(id);
                System.gc();

            }
            createDynamicDatabase(Activity_Installation.this, tablename_2, field2);
            System.gc();
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject3 = new JSONObject(input);
            JSONArray firsttitlearray3 = jsonObject3.getJSONArray(Config.TAG_ARRAY_TABLE_THREE);
            for (int i = 0; i < firsttitlearray3.length(); i++) {
                JSONObject obj3 = (JSONObject) firsttitlearray3.get(i);
                id = obj3.getString(Config.TAG_FIELD);
                field3.add(id);
                System.gc();
            }
            createDynamicDatabase(Activity_Installation.this, tablename_3, field3);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject4 = new JSONObject(input);
            JSONArray firsttitlearray4 = jsonObject4.getJSONArray(Config.TAG_ARRAY_TABLE_FOUR);
            for (int i = 0; i < firsttitlearray4.length(); i++) {
                JSONObject obj4 = (JSONObject) firsttitlearray4.get(i);
                id = obj4.getString(Config.TAG_FIELD);
                field4.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_4, field4);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject5 = new JSONObject(input);
            JSONArray firsttitlearray5 = jsonObject5.getJSONArray(Config.TAG_ARRAY_TABLE_FIVE);
            for (int i = 0; i < firsttitlearray5.length(); i++) {
                JSONObject obj5 = (JSONObject) firsttitlearray5.get(i);
                id = obj5.getString(Config.TAG_FIELD);
                field5.add(id);
            }

            createDynamicDatabase(Activity_Installation.this, tablename_5, field5);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject6 = new JSONObject(input);
            JSONArray firsttitlearray6 = jsonObject6.getJSONArray(Config.TAG_ARRAY_TABLE_SIX);
            for (int i = 0; i < firsttitlearray6.length(); i++) {
                JSONObject obj6 = (JSONObject) firsttitlearray6.get(i);
                id = obj6.getString(Config.TAG_FIELD);
                field6.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_6, field6);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject7 = new JSONObject(input);
            JSONArray firsttitlearray7 = jsonObject7.getJSONArray(Config.TAG_ARRAY_TABLE_SEVEN);
            for (int i = 0; i < firsttitlearray7.length(); i++) {

                JSONObject obj7 = (JSONObject) firsttitlearray7.get(i);
                id = obj7.getString(Config.TAG_FIELD);
                field7.add(id);

            }
            createDynamicDatabase(Activity_Installation.this, tablename_7, field7);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject8 = new JSONObject(input);
            JSONArray firsttitlearray8 = jsonObject8.getJSONArray(Config.TAG_ARRAY_TABLE_EIGHT);
            for (int i = 0; i < firsttitlearray8.length(); i++) {
                JSONObject obj8 = (JSONObject) firsttitlearray8.get(i);
                id = obj8.getString(Config.TAG_FIELD);
                field8.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_8, field8);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject9 = new JSONObject(input);
            JSONArray firsttitlearray9 = jsonObject9.getJSONArray(Config.TAG_ARRAY_TABLE_NINE);
            for (int i = 0; i < firsttitlearray9.length(); i++) {

                JSONObject obj9 = (JSONObject) firsttitlearray9.get(i);
                id = obj9.getString(Config.TAG_FIELD);
                field9.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_9, field9);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject10 = new JSONObject(input);
            JSONArray firsttitlearray10 = jsonObject10.getJSONArray(Config.TAG_ARRAY_TABLE_TEN);
            for (int i = 0; i < firsttitlearray10.length(); i++) {
                JSONObject obj10 = (JSONObject) firsttitlearray10.get(i);
                id = obj10.getString(Config.TAG_FIELD);
                field10.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_10, field10);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject11 = new JSONObject(input);
            JSONArray firsttitlearray11 = jsonObject11.getJSONArray(Config.TAG_ARRAY_TABLE_ELEVEN);
            for (int i = 0; i < firsttitlearray11.length(); i++) {
                JSONObject obj11 = (JSONObject) firsttitlearray11.get(i);
                id = obj11.getString(Config.TAG_FIELD);
                field11.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_11, field11);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject12 = new JSONObject(input);
            JSONArray firsttitlearray12 = jsonObject12.getJSONArray(Config.TAG_ARRAY_TABLE_TWELVE);
            for (int i = 0; i < firsttitlearray12.length(); i++) {
                JSONObject obj12 = (JSONObject) firsttitlearray12.get(i);
                id = obj12.getString(Config.TAG_FIELD);
                field12.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_12, field12);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject13 = new JSONObject(input);
            JSONArray firsttitlearray13 = jsonObject13.getJSONArray(Config.TAG_ARRAY_TABLE_THIRTEEN);
            for (int i = 0; i < firsttitlearray13.length(); i++) {
                JSONObject obj13 = (JSONObject) firsttitlearray13.get(i);
                id = obj13.getString(Config.TAG_FIELD);
                field13.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_13, field13);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject14 = new JSONObject(input);
            JSONArray firsttitlearray14 = jsonObject14.getJSONArray(Config.TAG_ARRAY_TABLE_FOURTEEN);
            for (int i = 0; i < firsttitlearray14.length(); i++) {
                JSONObject obj14 = (JSONObject) firsttitlearray14.get(i);
                id = obj14.getString(Config.TAG_FIELD);
                field14.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_14, field14);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject15 = new JSONObject(input);
            JSONArray firsttitlearray15 = jsonObject15.getJSONArray(Config.TAG_ARRAY_TABLE_FIFTEEN);
            for (int i = 0; i < firsttitlearray15.length(); i++) {
                JSONObject obj15 = (JSONObject) firsttitlearray15.get(i);
                id = obj15.getString(Config.TAG_FIELD);
                field15.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_15, field15);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject16 = new JSONObject(input);
            JSONArray firsttitlearray16 = jsonObject16.getJSONArray(Config.TAG_ARRAY_TABLE_SIXTEEN);
            for (int i = 0; i < firsttitlearray16.length(); i++) {
                JSONObject obj16 = (JSONObject) firsttitlearray16.get(i);
                id = obj16.getString(Config.TAG_FIELD);
                field16.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_16, field16);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject17 = new JSONObject(input);
            JSONArray firsttitlearray17 = jsonObject17.getJSONArray(Config.TAG_ARRAY_TABLE_SEVENTEEN);
            for (int i = 0; i < firsttitlearray17.length(); i++) {
                JSONObject obj17 = (JSONObject) firsttitlearray17.get(i);
                id = obj17.getString(Config.TAG_FIELD);
                field17.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_17, field17);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject18 = new JSONObject(input);
            JSONArray firsttitlearray18 = jsonObject18.getJSONArray(Config.TAG_ARRAY_TABLE_EIGHTEEN);
            for (int i = 0; i < firsttitlearray18.length(); i++) {
                JSONObject obj18 = (JSONObject) firsttitlearray18.get(i);
                id = obj18.getString(Config.TAG_FIELD);
                field18.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_18, field18);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject19 = new JSONObject(input);
            JSONArray firsttitlearray19 = jsonObject19.getJSONArray(Config.TAG_ARRAY_TABLE_NINETEEN);
            for (int i = 0; i < firsttitlearray19.length(); i++) {
                JSONObject obj19 = (JSONObject) firsttitlearray19.get(i);
                id = obj19.getString(Config.TAG_FIELD);
                field19.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_19, field19);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject20 = new JSONObject(input);
            JSONArray firsttitlearray20 = jsonObject20.getJSONArray(Config.TAG_ARRAY_TABLE_TWENTY);
            for (int i = 0; i < firsttitlearray20.length(); i++) {
                JSONObject obj20 = (JSONObject) firsttitlearray20.get(i);
                id = obj20.getString(Config.TAG_FIELD);
                field20.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_20, field20);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject21 = new JSONObject(input);
            JSONArray firsttitlearray21 = jsonObject21.getJSONArray(Config.TAG_ARRAY_TABLE_21);
            for (int i = 0; i < firsttitlearray21.length(); i++) {
                JSONObject obj21 = (JSONObject) firsttitlearray21.get(i);
                id = obj21.getString(Config.TAG_FIELD);
                field21.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_21, field21);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject22 = new JSONObject(input);
            JSONArray firsttitlearray22 = jsonObject22.getJSONArray(Config.TAG_ARRAY_TABLE_22);
            for (int i = 0; i < firsttitlearray22.length(); i++) {
                JSONObject obj22 = (JSONObject) firsttitlearray22.get(i);
                id = obj22.getString(Config.TAG_FIELD);
                field22.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_22, field22);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject23 = new JSONObject(input);
            JSONArray firsttitlearray23 = jsonObject23.getJSONArray(Config.TAG_ARRAY_TABLE_23);
            for (int i = 0; i < firsttitlearray23.length(); i++) {
                JSONObject obj23 = (JSONObject) firsttitlearray23.get(i);
                id = obj23.getString(Config.TAG_FIELD);
                field23.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_23, field23);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject24 = new JSONObject(input);
            JSONArray firsttitlearray24 = jsonObject24.getJSONArray(Config.TAG_ARRAY_TABLE_24);
            for (int i = 0; i < firsttitlearray24.length(); i++) {
                JSONObject obj24 = (JSONObject) firsttitlearray24.get(i);
                id = obj24.getString(Config.TAG_FIELD);
                field24.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_24, field24);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject25 = new JSONObject(input);
            JSONArray firsttitlearray25 = jsonObject25.getJSONArray(Config.TAG_ARRAY_TABLE_25);
            for (int i = 0; i < firsttitlearray25.length(); i++) {
                JSONObject obj25 = (JSONObject) firsttitlearray25.get(i);
                id = obj25.getString(Config.TAG_FIELD);
                field25.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_25, field25);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject26 = new JSONObject(input);
            JSONArray firsttitlearray26 = jsonObject26.getJSONArray(Config.TAG_ARRAY_TABLE_26);
            for (int i = 0; i < firsttitlearray26.length(); i++) {
                JSONObject obj26 = (JSONObject) firsttitlearray26.get(i);
                id = obj26.getString(Config.TAG_FIELD);
                field26.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_26, field26);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject27 = new JSONObject(input);
            JSONArray firsttitlearray27 = jsonObject27.getJSONArray(Config.TAG_ARRAY_TABLE_27);
            for (int i = 0; i < firsttitlearray27.length(); i++) {
                JSONObject obj27 = (JSONObject) firsttitlearray27.get(i);
                id = obj27.getString(Config.TAG_FIELD);
                field27.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_27, field27);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject28 = new JSONObject(input);
            JSONArray firsttitlearray28 = jsonObject28.getJSONArray(Config.TAG_ARRAY_TABLE_28);
            for (int i = 0; i < firsttitlearray28.length(); i++) {
                JSONObject obj28 = (JSONObject) firsttitlearray28.get(i);
                id = obj28.getString(Config.TAG_FIELD);
                field28.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_28, field28);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject29 = new JSONObject(input);
            JSONArray firsttitlearray29 = jsonObject29.getJSONArray(Config.TAG_ARRAY_TABLE_29);
            for (int i = 0; i < firsttitlearray29.length(); i++) {
                JSONObject obj29 = (JSONObject) firsttitlearray29.get(i);
                id = obj29.getString(Config.TAG_FIELD);
                field29.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_29, field29);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject30 = new JSONObject(input);
            JSONArray firsttitlearray30 = jsonObject30.getJSONArray(Config.TAG_ARRAY_TABLE_30);
            for (int i = 0; i < firsttitlearray30.length(); i++) {
                JSONObject obj30 = (JSONObject) firsttitlearray30.get(i);
                id = obj30.getString(Config.TAG_FIELD);
                field30.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_30, field30);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject31 = new JSONObject(input);
            JSONArray firsttitlearray31 = jsonObject31.getJSONArray(Config.TAG_ARRAY_TABLE_31);
            for (int i = 0; i < firsttitlearray31.length(); i++) {
                JSONObject obj31 = (JSONObject) firsttitlearray31.get(i);
                id = obj31.getString(Config.TAG_FIELD);
                field31.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_31, field31);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject32 = new JSONObject(input);
            JSONArray firsttitlearray32 = jsonObject32.getJSONArray(Config.TAG_ARRAY_TABLE_32);
            for (int i = 0; i < firsttitlearray32.length(); i++) {
                JSONObject obj32 = (JSONObject) firsttitlearray32.get(i);
                id = obj32.getString(Config.TAG_FIELD);
                field32.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_32, field32);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject33 = new JSONObject(input);
            JSONArray firsttitlearray33 = jsonObject33.getJSONArray(Config.TAG_ARRAY_TABLE_33);
            for (int i = 0; i < firsttitlearray33.length(); i++) {
                JSONObject obj33 = (JSONObject) firsttitlearray33.get(i);
                id = obj33.getString(Config.TAG_FIELD);
                field33.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_33, field33);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject34 = new JSONObject(input);
            JSONArray firsttitlearray34 = jsonObject34.getJSONArray(Config.TAG_ARRAY_TABLE_34);
            for (int i = 0; i < firsttitlearray34.length(); i++) {
                JSONObject obj34 = (JSONObject) firsttitlearray34.get(i);
                id = obj34.getString(Config.TAG_FIELD);
                field34.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_34, field34);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject35 = new JSONObject(input);
            JSONArray firsttitlearray35 = jsonObject35.getJSONArray(Config.TAG_ARRAY_TABLE_35);
            for (int i = 0; i < firsttitlearray35.length(); i++) {
                JSONObject obj35 = (JSONObject) firsttitlearray35.get(i);
                id = obj35.getString(Config.TAG_FIELD);
                field35.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_35, field35);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject36 = new JSONObject(input);
            JSONArray firsttitlearray36 = jsonObject36.getJSONArray(Config.TAG_ARRAY_TABLE_36);
            for (int i = 0; i < firsttitlearray36.length(); i++) {
                JSONObject obj36 = (JSONObject) firsttitlearray36.get(i);
                id = obj36.getString(Config.TAG_FIELD);
                field36.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_36, field36);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject37 = new JSONObject(input);
            JSONArray firsttitlearray37 = jsonObject37.getJSONArray(Config.TAG_ARRAY_TABLE_37);
            for (int i = 0; i < firsttitlearray37.length(); i++) {
                JSONObject obj37 = (JSONObject) firsttitlearray37.get(i);
                id = obj37.getString(Config.TAG_FIELD);
                field37.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_37, field37);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject38 = new JSONObject(input);
            JSONArray firsttitlearray38 = jsonObject38.getJSONArray(Config.TAG_ARRAY_TABLE_38);
            for (int i = 0; i < firsttitlearray38.length(); i++) {
                JSONObject obj38 = (JSONObject) firsttitlearray38.get(i);
                id = obj38.getString(Config.TAG_FIELD);
                field38.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_38, field38);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject39 = new JSONObject(input);
            JSONArray firsttitlearray39 = jsonObject39.getJSONArray(Config.TAG_ARRAY_TABLE_39);
            for (int i = 0; i < firsttitlearray39.length(); i++) {
                JSONObject obj39 = (JSONObject) firsttitlearray39.get(i);
                id = obj39.getString(Config.TAG_FIELD);
                field39.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_39, field39);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject40 = new JSONObject(input);
            JSONArray firsttitlearray40 = jsonObject40.getJSONArray(Config.TAG_ARRAY_TABLE_40);
            for (int i = 0; i < firsttitlearray40.length(); i++) {
                JSONObject obj40 = (JSONObject) firsttitlearray40.get(i);
                id = obj40.getString(Config.TAG_FIELD);
                field40.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_40, field40);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject41 = new JSONObject(input);
            JSONArray firsttitlearray41 = jsonObject41.getJSONArray(Config.TAG_ARRAY_TABLE_41);
            for (int i = 0; i < firsttitlearray41.length(); i++) {
                JSONObject obj41 = (JSONObject) firsttitlearray41.get(i);
                id = obj41.getString(Config.TAG_FIELD);
                field41.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_41, field41);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject42 = new JSONObject(input);
            JSONArray firsttitlearray42 = jsonObject42.getJSONArray(Config.TAG_ARRAY_TABLE_42);
            for (int i = 0; i < firsttitlearray42.length(); i++) {
                JSONObject obj42 = (JSONObject) firsttitlearray42.get(i);
                id = obj42.getString(Config.TAG_FIELD);
                field42.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_42, field42);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject43 = new JSONObject(input);
            JSONArray firsttitlearray43 = jsonObject43.getJSONArray(Config.TAG_ARRAY_TABLE_43);
            for (int i = 0; i < firsttitlearray43.length(); i++) {
                JSONObject obj43 = (JSONObject) firsttitlearray43.get(i);
                id = obj43.getString(Config.TAG_FIELD);
                field43.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_43, field43);
            Toast.makeText(getApplicationContext(), "Tables Created", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject44 = new JSONObject(input);
            JSONArray firsttitlearray44 = jsonObject44.getJSONArray(Config.TAG_ARRAY_TABLE_44);
            for (int i = 0; i < firsttitlearray44.length(); i++) {
                JSONObject obj44 = (JSONObject) firsttitlearray44.get(i);
                id = obj44.getString(Config.TAG_FIELD);
                field44.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_44, field44);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject45 = new JSONObject(input);
            JSONArray firsttitlearray45 = jsonObject45.getJSONArray(Config.TAG_ARRAY_TABLE_45);
            for (int i = 0; i < firsttitlearray45.length(); i++) {
                JSONObject obj45 = (JSONObject) firsttitlearray45.get(i);
                id = obj45.getString(Config.TAG_FIELD);
                field45.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_45, field45);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject46 = new JSONObject(input);
            JSONArray firsttitlearray46 = jsonObject46.getJSONArray(Config.TAG_ARRAY_TABLE_46);
            for (int i = 0; i < firsttitlearray46.length(); i++) {
                JSONObject obj46 = (JSONObject) firsttitlearray46.get(i);
                id = obj46.getString(Config.TAG_FIELD);
                field46.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_46, field46);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        try {
            JSONObject jsonObject47 = new JSONObject(input);
            JSONArray firsttitlearray47 = jsonObject47.getJSONArray(Config.TAG_ARRAY_TABLE_47);
            for (int i = 0; i < firsttitlearray47.length(); i++) {
                JSONObject obj47 = (JSONObject) firsttitlearray47.get(i);
                id = obj47.getString(Config.TAG_FIELD);
                field47.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_47, field47);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        try {
            JSONObject jsonObject48 = new JSONObject(input);
            JSONArray firsttitlearray48 = jsonObject48.getJSONArray(Config.TAG_ARRAY_TABLE_48);
            for (int i = 0; i < firsttitlearray48.length(); i++) {
                JSONObject obj48 = (JSONObject) firsttitlearray48.get(i);
                id = obj48.getString(Config.TAG_FIELD);
                field48.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_48, field48);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        try {
            JSONObject jsonObject49 = new JSONObject(input);
            JSONArray firsttitlearray49 = jsonObject49.getJSONArray(Config.TAG_ARRAY_TABLE_49);
            for (int i = 0; i < firsttitlearray49.length(); i++) {
                JSONObject obj49 = (JSONObject) firsttitlearray49.get(i);
                id = obj49.getString(Config.TAG_FIELD);
                field49.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_49, field49);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject50 = new JSONObject(input);
            JSONArray firsttitlearray50 = jsonObject50.getJSONArray(Config.TAG_ARRAY_TABLE_50);
            for (int i = 0; i < firsttitlearray50.length(); i++) {
                JSONObject obj50 = (JSONObject) firsttitlearray50.get(i);
                id = obj50.getString(Config.TAG_FIELD);
                field50.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_50, field50);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject51 = new JSONObject(input);
            JSONArray firsttitlearray51 = jsonObject51.getJSONArray(Config.TAG_ARRAY_TABLE_51);
            for (int i = 0; i < firsttitlearray51.length(); i++) {
                JSONObject obj51 = (JSONObject) firsttitlearray51.get(i);
                id = obj51.getString(Config.TAG_FIELD);
                field51.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_51, field51);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject52 = new JSONObject(input);
            JSONArray firsttitlearray52 = jsonObject52.getJSONArray(Config.TAG_ARRAY_TABLE_52);
            for (int i = 0; i < firsttitlearray52.length(); i++) {
                JSONObject obj52 = (JSONObject) firsttitlearray52.get(i);
                id = obj52.getString(Config.TAG_FIELD);
                field52.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_52, field52);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject53 = new JSONObject(input);
            JSONArray firsttitlearray53 = jsonObject53.getJSONArray(Config.TAG_ARRAY_TABLE_53);
            for (int i = 0; i < firsttitlearray53.length(); i++) {
                JSONObject obj53 = (JSONObject) firsttitlearray53.get(i);
                id = obj53.getString(Config.TAG_FIELD);
                field53.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_53, field53);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject54 = new JSONObject(input);
            JSONArray firsttitlearray54 = jsonObject54.getJSONArray(Config.TAG_ARRAY_TABLE_54);
            for (int i = 0; i < firsttitlearray54.length(); i++) {
                JSONObject obj54 = (JSONObject) firsttitlearray54.get(i);
                id = obj54.getString(Config.TAG_FIELD);
                field54.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_54, field54);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject55 = new JSONObject(input);
            JSONArray firsttitlearray55 = jsonObject55.getJSONArray(Config.TAG_ARRAY_TABLE_55);
            for (int i = 0; i < firsttitlearray55.length(); i++) {
                JSONObject obj55 = (JSONObject) firsttitlearray55.get(i);
                id = obj55.getString(Config.TAG_FIELD);
                field55.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_55, field55);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject56 = new JSONObject(input);
            JSONArray firsttitlearray56 = jsonObject56.getJSONArray(Config.TAG_ARRAY_TABLE_56);
            for (int i = 0; i < firsttitlearray56.length(); i++) {
                JSONObject obj56 = (JSONObject) firsttitlearray56.get(i);
                id = obj56.getString(Config.TAG_FIELD);
                field56.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_56, field56);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject57 = new JSONObject(input);
            JSONArray firsttitlearray57 = jsonObject57.getJSONArray(Config.TAG_ARRAY_TABLE_57);
            for (int i = 0; i < firsttitlearray57.length(); i++) {
                JSONObject obj57 = (JSONObject) firsttitlearray57.get(i);
                id = obj57.getString(Config.TAG_FIELD);
                field57.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_57, field57);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject58 = new JSONObject(input);
            JSONArray firsttitlearray58 = jsonObject58.getJSONArray(Config.TAG_ARRAY_TABLE_58);
            for (int i = 0; i < firsttitlearray58.length(); i++) {
                JSONObject obj58 = (JSONObject) firsttitlearray58.get(i);
                id = obj58.getString(Config.TAG_FIELD);
                field58.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_58, field58);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject59 = new JSONObject(input);
            JSONArray firsttitlearray59 = jsonObject59.getJSONArray(Config.TAG_ARRAY_TABLE_59);
            for (int i = 0; i < firsttitlearray59.length(); i++) {
                JSONObject obj59 = (JSONObject) firsttitlearray59.get(i);
                id = obj59.getString(Config.TAG_FIELD);
                field59.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_59, field59);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject60 = new JSONObject(input);
            JSONArray firsttitlearray60 = jsonObject60.getJSONArray(Config.TAG_ARRAY_TABLE_60);
            for (int i = 0; i < firsttitlearray60.length(); i++) {
                JSONObject obj60 = (JSONObject) firsttitlearray60.get(i);
                id = obj60.getString(Config.TAG_FIELD);
                field60.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_60, field60);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        try {
            JSONObject jsonObject61 = new JSONObject(input);
            JSONArray firsttitlearray61 = jsonObject61.getJSONArray(Config.TAG_ARRAY_TABLE_61);
            for (int i = 0; i < firsttitlearray61.length(); i++) {
                JSONObject obj61 = (JSONObject) firsttitlearray61.get(i);
                id = obj61.getString(Config.TAG_FIELD);
                field61.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_61, field61);

        } catch (Exception e) {
            Log.e("exception", e.toString());

        }
        try {
            JSONObject jsonObject62 = new JSONObject(input);
            JSONArray firsttitlearray62 = jsonObject62.getJSONArray(Config.TAG_ARRAY_TABLE_62);
            for (int i = 0; i < firsttitlearray62.length(); i++) {
                JSONObject obj62 = (JSONObject) firsttitlearray62.get(i);
                id = obj62.getString(Config.TAG_FIELD);
                field62.add(id);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_62, field62);
        } catch (Exception e) {
            Log.e("exception 62", e.toString());
        }

        Toast.makeText(getApplicationContext(), "Tables Created", Toast.LENGTH_LONG).show();


    }

    void jsonInsert(String input_data) {

        try{

            JSONObject jsonObject_insert_1 = new JSONObject(input_data);
            JSONArray jsonArray1 = jsonObject_insert_1.getJSONArray(Config.TAG_ARRAY_1);
            for (int c = 0; c < jsonArray1.length(); c++) {
                JSONObject jsonObject_getting_1 = (JSONObject) jsonArray1.get(c);

                table_data=jsonObject_getting_1.getString(Config.TAG_DATA);

                data1.add(table_data);

            }
            int rows_number = field1.size();
            int row_index = 0;
            for (int i = 0; i <data1.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data1, field1,tablename_1, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 1",e.toString());}

        try{

            JSONObject jsonObject_insert_2 = new JSONObject(input_data);
            JSONArray jsonArray2 = jsonObject_insert_2.getJSONArray(Config.TAG_ARRAY_2);
            for (int c = 0; c < jsonArray2.length(); c++) {
                JSONObject jsonObject_getting_2 = (JSONObject) jsonArray2.get(c);

                table_data=jsonObject_getting_2.getString(Config.TAG_DATA);

                data2.add(table_data);

            }
            int rows_number = field2.size();
            int row_index = 0;
            for (int i = 0; i <data2.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data2, field2, tablename_2, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 2",e.toString());}

        try{

            JSONObject jsonObject_insert_3 = new JSONObject(input_data);
            JSONArray jsonArray3 = jsonObject_insert_3.getJSONArray(Config.TAG_ARRAY_3);
            for (int c = 0; c < jsonArray3.length(); c++) {
                JSONObject jsonObject_getting_3 = (JSONObject) jsonArray3.get(c);

                table_data=jsonObject_getting_3.getString(Config.TAG_DATA);

                data3.add(table_data);

            }
            int rows_number = field3.size();
            int row_index = 0;
            for (int i = 0; i <data3.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data3, field3, tablename_3, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 3",e.toString());}


        try{

            JSONObject jsonObject_insert_4 = new JSONObject(input_data);
            JSONArray jsonArray4 = jsonObject_insert_4.getJSONArray(Config.TAG_ARRAY_4);
            for (int c = 0; c < jsonArray4.length(); c++) {
                JSONObject jsonObject_getting_4 = (JSONObject) jsonArray4.get(c);

                table_data=jsonObject_getting_4.getString(Config.TAG_DATA);

                data4.add(table_data);

            }
            int rows_number = field4.size();
            int row_index = 0;
            for (int i = 0; i <data4.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data4, field4, tablename_4, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 4",e.toString());}

        try{

            JSONObject jsonObject_insert_5 = new JSONObject(input_data);
            JSONArray jsonArray5 = jsonObject_insert_5.getJSONArray(Config.TAG_ARRAY_5);
            for (int c = 0; c < jsonArray5.length(); c++) {
                JSONObject jsonObject_getting_5 = (JSONObject) jsonArray5.get(c);

                table_data=jsonObject_getting_5.getString(Config.TAG_DATA);

                data5.add(table_data);

            }
            int rows_number = field5.size();
            int row_index = 0;
            for (int i = 0; i <data5.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data5, field5, tablename_5, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 5",e.toString());}

        try{

            JSONObject jsonObject_insert_6 = new JSONObject(input_data);
            JSONArray jsonArray6 = jsonObject_insert_6.getJSONArray(Config.TAG_ARRAY_6);
            for (int c = 0; c < jsonArray6.length(); c++) {
                JSONObject jsonObject_getting_6 = (JSONObject) jsonArray6.get(c);

                table_data=jsonObject_getting_6.getString(Config.TAG_DATA);

                data6.add(table_data);

            }
            int rows_number = field6.size();
            int row_index = 0;
            for (int i = 0; i <data6.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data6, field6, tablename_6, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 6",e.toString());}

        try{

            JSONObject jsonObject_insert_7 = new JSONObject(input_data);
            JSONArray jsonArray7 = jsonObject_insert_7.getJSONArray(Config.TAG_ARRAY_7);
            for (int c = 0; c < jsonArray7.length(); c++) {
                JSONObject jsonObject_getting_7 = (JSONObject) jsonArray7.get(c);

                table_data=jsonObject_getting_7.getString(Config.TAG_DATA);

                data7.add(table_data);

            }
            int rows_number = field7.size();
            int row_index = 0;
            for (int i = 0; i <data7.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data7, field7, tablename_7, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 7",e.toString());}

        try{

            JSONObject jsonObject_insert_8 = new JSONObject(input_data);
            JSONArray jsonArray8 = jsonObject_insert_8.getJSONArray(Config.TAG_ARRAY_8);
            for (int c = 0; c < jsonArray8.length(); c++) {
                JSONObject jsonObject_getting_8 = (JSONObject) jsonArray8.get(c);

                table_data=jsonObject_getting_8.getString(Config.TAG_DATA);

                data8.add(table_data);

            }
            int rows_number = field8.size();
            int row_index = 0;
            for (int i = 0; i <data8.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data8, field8,tablename_8, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 8",e.toString());}

        try{

            JSONObject jsonObject_insert_9 = new JSONObject(input_data);
            JSONArray jsonArray9 = jsonObject_insert_9.getJSONArray(Config.TAG_ARRAY_9);
            for (int c = 0; c < jsonArray9.length(); c++) {
                JSONObject jsonObject_getting_9 = (JSONObject) jsonArray9.get(c);

                table_data=jsonObject_getting_9.getString(Config.TAG_DATA);

                data9.add(table_data);

            }
            int rows_number = field9.size();
            int row_index = 0;
            for (int i = 0; i <data9.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data9, field9, tablename_9, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 9",e.toString());}

        try{

            JSONObject jsonObject_insert_10 = new JSONObject(input_data);
            JSONArray jsonArray10 = jsonObject_insert_10.getJSONArray(Config.TAG_ARRAY_10);
            for (int c = 0; c < jsonArray10.length(); c++) {
                JSONObject jsonObject_getting_10 = (JSONObject) jsonArray10.get(c);

                table_data=jsonObject_getting_10.getString(Config.TAG_DATA);

                data1.add(table_data);

            }
            int rows_number = field10.size();
            int row_index = 0;
            for (int i = 0; i <data10.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data10, field10, tablename_10, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 10",e.toString());}



        try{

            JSONObject jsonObject_insert_11 = new JSONObject(input_data);
            JSONArray jsonArray11 = jsonObject_insert_11.getJSONArray(Config.TAG_ARRAY_11);
            for (int c = 0; c < jsonArray11.length(); c++) {
                JSONObject jsonObject_getting_11 = (JSONObject) jsonArray11.get(c);

                table_data=jsonObject_getting_11.getString(Config.TAG_DATA);

                data1.add(table_data);

            }
            int rows_number = field11.size();
            int row_index = 0;
            for (int i = 0; i <data11.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data11, field11, tablename_11, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 11",e.toString());}

        try{

            JSONObject jsonObject_insert_12 = new JSONObject(input_data);
            JSONArray jsonArray12 = jsonObject_insert_12.getJSONArray(Config.TAG_ARRAY_12);
            for (int c = 0; c < jsonArray12.length(); c++) {
                JSONObject jsonObject_getting_12 = (JSONObject) jsonArray12.get(c);

                table_data=jsonObject_getting_12.getString(Config.TAG_DATA);

                data12.add(table_data);

            }
            int rows_number = field12.size();
            int row_index = 0;
            for (int i = 0; i <data12.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data12, field12, tablename_12, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 12",e.toString());}

        try{

            JSONObject jsonObject_insert_13 = new JSONObject(input_data);
            JSONArray jsonArray13 = jsonObject_insert_13.getJSONArray(Config.TAG_ARRAY_13);
            for (int c = 0; c < jsonArray13.length(); c++) {
                JSONObject jsonObject_getting_13 = (JSONObject) jsonArray13.get(c);

                table_data=jsonObject_getting_13.getString(Config.TAG_DATA);

                data13.add(table_data);

            }
            int rows_number = field13.size();
            int row_index = 0;
            for (int i = 0; i <data13.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data13, field13, tablename_13, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 13",e.toString());}
/*
        try{

            JSONObject jsonObject_insert_14 = new JSONObject(input_data);
            JSONArray jsonArray14 = jsonObject_insert_14.getJSONArray(Config.TAG_ARRAY_14);
            for (int c = 0; c < jsonArray14.length(); c++) {
                JSONObject jsonObject_getting_14 = (JSONObject) jsonArray14.get(c);

                table_data=jsonObject_getting_14.getString(Config.TAG_DATA);

                data14.add(table_data);

            }
            int rows_number = field14.size();
            int row_index = 0;
            for (int i = 0; i <data14.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data14, field14, tablename_14, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 14",e.toString());}*/


        try{

            JSONObject jsonObject_insert_15 = new JSONObject(input_data);
            JSONArray jsonArray15 = jsonObject_insert_15.getJSONArray(Config.TAG_ARRAY_15);
            for (int c = 0; c < jsonArray15.length(); c++) {
                JSONObject jsonObject_getting_15 = (JSONObject) jsonArray15.get(c);

                table_data=jsonObject_getting_15.getString(Config.TAG_DATA);

                data15.add(table_data);

            }
            int rows_number = field15.size();
            int row_index = 0;
            for (int i = 0; i <data15.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data15, field15, tablename_15, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 15",e.toString());}

        try{

            JSONObject jsonObject_insert_16 = new JSONObject(input_data);
            JSONArray jsonArray16 = jsonObject_insert_16.getJSONArray(Config.TAG_ARRAY_16);
            for (int c = 0; c < jsonArray16.length(); c++) {
                JSONObject jsonObject_getting_16 = (JSONObject) jsonArray16.get(c);

                table_data=jsonObject_getting_16.getString(Config.TAG_DATA);

                data16.add(table_data);

            }
            int rows_number = field16.size();
            int row_index = 0;
            for (int i = 0; i <data16.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data16, field16, tablename_16, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 16",e.toString());}

        try{

            JSONObject jsonObject_insert_17 = new JSONObject(input_data);
            JSONArray jsonArray17 = jsonObject_insert_17.getJSONArray(Config.TAG_ARRAY_17);
            for (int c = 0; c < jsonArray17.length(); c++) {
                JSONObject jsonObject_getting_17 = (JSONObject) jsonArray17.get(c);

                table_data=jsonObject_getting_17.getString(Config.TAG_DATA);

                data17.add(table_data);

            }
            int rows_number = field17.size();
            int row_index = 0;
            for (int i = 0; i <data17.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data17, field17, tablename_17, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 17",e.toString());}

        try{

            JSONObject jsonObject_insert_18 = new JSONObject(input_data);
            JSONArray jsonArray18 = jsonObject_insert_18.getJSONArray(Config.TAG_ARRAY_18);
            for (int c = 0; c < jsonArray18.length(); c++) {
                JSONObject jsonObject_getting_18 = (JSONObject) jsonArray18.get(c);

                table_data=jsonObject_getting_18.getString(Config.TAG_DATA);

                data18.add(table_data);

            }
            int rows_number = field18.size();
            int row_index = 0;
            for (int i = 0; i <data18.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data18, field18, tablename_18, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 18",e.toString());}

        try{

            JSONObject jsonObject_insert_19 = new JSONObject(input_data);
            JSONArray jsonArray19 = jsonObject_insert_19.getJSONArray(Config.TAG_ARRAY_19);
            for (int c = 0; c < jsonArray19.length(); c++) {
                JSONObject jsonObject_getting_19 = (JSONObject) jsonArray19.get(c);

                table_data=jsonObject_getting_19.getString(Config.TAG_DATA);

                data19.add(table_data);

            }
            int rows_number = field19.size();
            int row_index = 0;
            for (int i = 0; i <data19.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data19, field19, tablename_19, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 19",e.toString());}


        try{

            JSONObject jsonObject_insert_20 = new JSONObject(input_data);
            JSONArray jsonArray20 = jsonObject_insert_20.getJSONArray(Config.TAG_ARRAY_20);
            for (int c = 0; c < jsonArray20.length(); c++) {
                JSONObject jsonObject_getting_20 = (JSONObject) jsonArray20.get(c);

                table_data=jsonObject_getting_20.getString(Config.TAG_DATA);

                data1.add(table_data);

            }
            int rows_number = field20.size();
            int row_index = 0;
            for (int i = 0; i <data20.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data20, field20, tablename_20, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 20",e.toString());}

        try{

            JSONObject jsonObject_insert_21 = new JSONObject(input_data);
            JSONArray jsonArray21 = jsonObject_insert_21.getJSONArray(Config.TAG_ARRAY_21);
            for (int c = 0; c < jsonArray21.length(); c++) {
                JSONObject jsonObject_getting_21 = (JSONObject) jsonArray21.get(c);

                table_data=jsonObject_getting_21.getString(Config.TAG_DATA);

                data21.add(table_data);

            }
            int rows_number = field21.size();
            int row_index = 0;
            for (int i = 0; i <data21.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data21, field21, tablename_21, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 21",e.toString());}

        try{

            JSONObject jsonObject_insert_22 = new JSONObject(input_data);
            JSONArray jsonArray22 = jsonObject_insert_22.getJSONArray(Config.TAG_ARRAY_22);
            for (int c = 0; c < jsonArray22.length(); c++) {
                JSONObject jsonObject_getting_22 = (JSONObject) jsonArray22.get(c);

                table_data=jsonObject_getting_22.getString(Config.TAG_DATA);

                data22.add(table_data);

            }
            int rows_number = field22.size();
            int row_index = 0;
            for (int i = 0; i <data22.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data22, field22, tablename_22, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 22",e.toString());}

        try{

            JSONObject jsonObject_insert_23 = new JSONObject(input_data);
            JSONArray jsonArray23 = jsonObject_insert_23.getJSONArray(Config.TAG_ARRAY_23);
            for (int c = 0; c < jsonArray23.length(); c++) {
                JSONObject jsonObject_getting_23 = (JSONObject) jsonArray23.get(c);

                table_data=jsonObject_getting_23.getString(Config.TAG_DATA);

                data23.add(table_data);

            }
            int rows_number = field23.size();
            int row_index = 0;
            for (int i = 0; i <data23.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data23, field23, tablename_23, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 23",e.toString());}


        try{

            JSONObject jsonObject_insert_24 = new JSONObject(input_data);
            JSONArray jsonArray24 = jsonObject_insert_24.getJSONArray(Config.TAG_ARRAY_24);
            for (int c = 0; c < jsonArray24.length(); c++) {
                JSONObject jsonObject_getting_24 = (JSONObject) jsonArray24.get(c);

                table_data=jsonObject_getting_24.getString(Config.TAG_DATA);

                data24.add(table_data);

            }
            int rows_number = field24.size();
            int row_index = 0;
            for (int i = 0; i <data24.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data24, field24, tablename_24, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 24",e.toString());}

        try{

            JSONObject jsonObject_insert_25 = new JSONObject(input_data);
            JSONArray jsonArray25 = jsonObject_insert_25.getJSONArray(Config.TAG_ARRAY_25);
            for (int c = 0; c < jsonArray25.length(); c++) {
                JSONObject jsonObject_getting_25 = (JSONObject) jsonArray25.get(c);

                table_data=jsonObject_getting_25.getString(Config.TAG_DATA);

                data25.add(table_data);

            }
            int rows_number = field25.size();
            int row_index = 0;
            for (int i = 0; i <data25.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data25, field25, tablename_25, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 25",e.toString());}

        try{

            JSONObject jsonObject_insert_26 = new JSONObject(input_data);
            JSONArray jsonArray26 = jsonObject_insert_26.getJSONArray(Config.TAG_ARRAY_26);
            for (int c = 0; c < jsonArray26.length(); c++) {
                JSONObject jsonObject_getting_26 = (JSONObject) jsonArray26.get(c);

                table_data=jsonObject_getting_26.getString(Config.TAG_DATA);

                data26.add(table_data);

            }
            int rows_number = field26.size();
            int row_index = 0;
            for (int i = 0; i <data26.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data26, field26, tablename_26, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 26",e.toString());}

        try{

            JSONObject jsonObject_insert_27 = new JSONObject(input_data);
            JSONArray jsonArray27 = jsonObject_insert_27.getJSONArray(Config.TAG_ARRAY_27);
            for (int c = 0; c < jsonArray27.length(); c++) {
                JSONObject jsonObject_getting_27 = (JSONObject) jsonArray27.get(c);

                table_data=jsonObject_getting_27.getString(Config.TAG_DATA);

                data27.add(table_data);

            }
            int rows_number = field27.size();
            int row_index = 0;
            for (int i = 0; i <data27.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data27, field27, tablename_27, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 27",e.toString());}

        try{

            JSONObject jsonObject_insert_28 = new JSONObject(input_data);
            JSONArray jsonArray28 = jsonObject_insert_28.getJSONArray(Config.TAG_ARRAY_28);
            for (int c = 0; c < jsonArray28.length(); c++) {
                JSONObject jsonObject_getting_28 = (JSONObject) jsonArray28.get(c);

                table_data=jsonObject_getting_28.getString(Config.TAG_DATA);

                data28.add(table_data);

            }
            int rows_number = field28.size();
            int row_index = 0;
            for (int i = 0; i <data28.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data28, field28, tablename_28, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 28",e.toString());}

        try{

            JSONObject jsonObject_insert_29 = new JSONObject(input_data);
            JSONArray jsonArray29 = jsonObject_insert_29.getJSONArray(Config.TAG_ARRAY_29);
            for (int c = 0; c < jsonArray29.length(); c++) {
                JSONObject jsonObject_getting_29 = (JSONObject) jsonArray29.get(c);

                table_data=jsonObject_getting_29.getString(Config.TAG_DATA);

                data29.add(table_data);

            }
            int rows_number = field29.size();
            int row_index = 0;
            for (int i = 0; i <data29.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data29, field29, tablename_29, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 29",e.toString());}

        try{

            JSONObject jsonObject_insert_30 = new JSONObject(input_data);
            JSONArray jsonArray30 = jsonObject_insert_30.getJSONArray(Config.TAG_ARRAY_30);
            for (int c = 0; c < jsonArray30.length(); c++) {
                JSONObject jsonObject_getting_30 = (JSONObject) jsonArray30.get(c);

                table_data=jsonObject_getting_30.getString(Config.TAG_DATA);

                data1.add(table_data);

            }
            int rows_number = field30.size();
            int row_index = 0;
            for (int i = 0; i <data30.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data30, field30, tablename_30, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 30",e.toString());}

        try{

            JSONObject jsonObject_insert_31 = new JSONObject(input_data);
            JSONArray jsonArray31 = jsonObject_insert_31.getJSONArray(Config.TAG_ARRAY_31);
            for (int c = 0; c < jsonArray31.length(); c++) {
                JSONObject jsonObject_getting_31 = (JSONObject) jsonArray31.get(c);

                table_data=jsonObject_getting_31.getString(Config.TAG_DATA);

                data31.add(table_data);

            }
            int rows_number = field31.size();
            int row_index = 0;
            for (int i = 0; i <data31.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data31, field31, tablename_31, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 31",e.toString());}

        try{

            JSONObject jsonObject_insert_32 = new JSONObject(input_data);
            JSONArray jsonArray32 = jsonObject_insert_32.getJSONArray(Config.TAG_ARRAY_32);
            for (int c = 0; c < jsonArray32.length(); c++) {
                JSONObject jsonObject_getting_32 = (JSONObject) jsonArray32.get(c);

                table_data=jsonObject_getting_32.getString(Config.TAG_DATA);

                data32.add(table_data);

            }
            int rows_number = field32.size();
            int row_index = 0;
            for (int i = 0; i <data32.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data32, field32, tablename_32, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 32",e.toString());}

        try{

            JSONObject jsonObject_insert_33 = new JSONObject(input_data);
            JSONArray jsonArray33 = jsonObject_insert_33.getJSONArray(Config.TAG_ARRAY_33);
            for (int c = 0; c < jsonArray33.length(); c++) {
                JSONObject jsonObject_getting_33 = (JSONObject) jsonArray33.get(c);

                table_data=jsonObject_getting_33.getString(Config.TAG_DATA);

                data33.add(table_data);

            }
            int rows_number = field33.size();
            int row_index = 0;
            for (int i = 0; i <data33.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data33, field33, tablename_33, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 33",e.toString());}


        try{

            JSONObject jsonObject_insert_34 = new JSONObject(input_data);
            JSONArray jsonArray34 = jsonObject_insert_34.getJSONArray(Config.TAG_ARRAY_34);
            for (int c = 0; c < jsonArray34.length(); c++) {
                JSONObject jsonObject_getting_34 = (JSONObject) jsonArray34.get(c);

                table_data=jsonObject_getting_34.getString(Config.TAG_DATA);

                data34.add(table_data);

            }
            int rows_number = field34.size();
            int row_index = 0;
            for (int i = 0; i <data34.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data34, field34, tablename_34, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 34",e.toString());}

        try{

            JSONObject jsonObject_insert_35 = new JSONObject(input_data);
            JSONArray jsonArray35 = jsonObject_insert_35.getJSONArray(Config.TAG_ARRAY_35);
            for (int c = 0; c < jsonArray35.length(); c++) {
                JSONObject jsonObject_getting_35 = (JSONObject) jsonArray35.get(c);

                table_data=jsonObject_getting_35.getString(Config.TAG_DATA);

                data35.add(table_data);

            }
            int rows_number = field35.size();
            int row_index = 0;
            for (int i = 0; i <data35.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data35, field35, tablename_35, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 35",e.toString());}

        try{

            JSONObject jsonObject_insert_36 = new JSONObject(input_data);
            JSONArray jsonArray36 = jsonObject_insert_36.getJSONArray(Config.TAG_ARRAY_36);
            for (int c = 0; c < jsonArray36.length(); c++) {
                JSONObject jsonObject_getting_36 = (JSONObject) jsonArray36.get(c);

                table_data=jsonObject_getting_36.getString(Config.TAG_DATA);

                data36.add(table_data);

            }
            int rows_number = field36.size();
            int row_index = 0;
            for (int i = 0; i <data36.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data36, field36, tablename_36, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 36",e.toString());}

  /*      try{

            JSONObject jsonObject_insert_37 = new JSONObject(input_data);
            JSONArray jsonArray37 = jsonObject_insert_37.getJSONArray(Config.TAG_ARRAY_37);
            for (int c = 0; c < jsonArray37.length(); c++) {
                JSONObject jsonObject_getting_37 = (JSONObject) jsonArray37.get(c);

                table_data=jsonObject_getting_37.getString(Config.TAG_DATA);

                data37.add(table_data);

            }
            int rows_number = field37.size();
            int row_index = 0;
            for (int i = 0; i <data37.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data37, field37, tablename_37, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 37",e.toString());}

        try{

            JSONObject jsonObject_insert_38 = new JSONObject(input_data);
            JSONArray jsonArray38 = jsonObject_insert_38.getJSONArray(Config.TAG_ARRAY_38);
            for (int c = 0; c < jsonArray38.length(); c++) {
                JSONObject jsonObject_getting_38 = (JSONObject) jsonArray38.get(c);

                table_data=jsonObject_getting_38.getString(Config.TAG_DATA);

                data38.add(table_data);

            }
            int rows_number = field38.size();
            int row_index = 0;
            for (int i = 0; i <data38.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data38, field38, tablename_38, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 38",e.toString());}

        try{

            JSONObject jsonObject_insert_39 = new JSONObject(input_data);
            JSONArray jsonArray39 = jsonObject_insert_39.getJSONArray(Config.TAG_ARRAY_39);
            for (int c = 0; c < jsonArray39.length(); c++) {
                JSONObject jsonObject_getting_39 = (JSONObject) jsonArray39.get(c);

                table_data=jsonObject_getting_39.getString(Config.TAG_DATA);

                data39.add(table_data);

            }
            int rows_number = field39.size();
            int row_index = 0;
            for (int i = 0; i <data39.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data39, field39, tablename_39, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 39",e.toString());}*/

        try{

            JSONObject jsonObject_insert_40 = new JSONObject(input_data);
            JSONArray jsonArray40 = jsonObject_insert_40.getJSONArray(Config.TAG_ARRAY_40);
            for (int c = 0; c < jsonArray40.length(); c++) {
                JSONObject jsonObject_getting_40 = (JSONObject) jsonArray40.get(c);

                table_data=jsonObject_getting_40.getString(Config.TAG_DATA);

                data1.add(table_data);

            }
            int rows_number = field40.size();
            int row_index = 0;
            for (int i = 0; i <data40.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data40, field40, tablename_40, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 40",e.toString());}

        try{

            JSONObject jsonObject_insert_41 = new JSONObject(input_data);
            JSONArray jsonArray41 = jsonObject_insert_41.getJSONArray(Config.TAG_ARRAY_41);
            for (int c = 0; c < jsonArray41.length(); c++) {
                JSONObject jsonObject_getting_41 = (JSONObject) jsonArray41.get(c);

                table_data=jsonObject_getting_41.getString(Config.TAG_DATA);

                data41.add(table_data);

            }
            int rows_number = field41.size();
            int row_index = 0;
            for (int i = 0; i <data41.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data41, field41, tablename_41, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 41",e.toString());}

        try{

            JSONObject jsonObject_insert_42 = new JSONObject(input_data);
            JSONArray jsonArray42 = jsonObject_insert_42.getJSONArray(Config.TAG_ARRAY_42);
            for (int c = 0; c < jsonArray42.length(); c++) {
                JSONObject jsonObject_getting_42 = (JSONObject) jsonArray42.get(c);

                table_data=jsonObject_getting_42.getString(Config.TAG_DATA);

                data42.add(table_data);

            }
            int rows_number = field42.size();
            int row_index = 0;
            for (int i = 0; i <data42.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data42, field42, tablename_42, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 42",e.toString());}

        try{

            JSONObject jsonObject_insert_43 = new JSONObject(input_data);
            JSONArray jsonArray43 = jsonObject_insert_43.getJSONArray(Config.TAG_ARRAY_43);
            for (int c = 0; c < jsonArray43.length(); c++) {
                JSONObject jsonObject_getting_43 = (JSONObject) jsonArray43.get(c);

                table_data=jsonObject_getting_43.getString(Config.TAG_DATA);

                data43.add(table_data);

            }
            int rows_number = field43.size();
            int row_index = 0;
            for (int i = 0; i <data43.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data43, field43, tablename_43, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 43",e.toString());}


        try{

            JSONObject jsonObject_insert_44 = new JSONObject(input_data);
            JSONArray jsonArray44 = jsonObject_insert_44.getJSONArray(Config.TAG_ARRAY_44);
            for (int c = 0; c < jsonArray44.length(); c++) {
                JSONObject jsonObject_getting_44 = (JSONObject) jsonArray44.get(c);

                table_data=jsonObject_getting_44.getString(Config.TAG_DATA);

                data44.add(table_data);

            }
            int rows_number = field44.size();
            int row_index = 0;
            for (int i = 0; i <data44.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data44, field44, tablename_44, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 44",e.toString());}

        try{

            JSONObject jsonObject_insert_45 = new JSONObject(input_data);
            JSONArray jsonArray45 = jsonObject_insert_45.getJSONArray(Config.TAG_ARRAY_45);
            for (int c = 0; c < jsonArray45.length(); c++) {
                JSONObject jsonObject_getting_45 = (JSONObject) jsonArray45.get(c);

                table_data=jsonObject_getting_45.getString(Config.TAG_DATA);

                data45.add(table_data);

            }
            int rows_number = field45.size();
            int row_index = 0;
            for (int i = 0; i <data45.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data45, field45, tablename_45, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 45",e.toString());}

        try{

            JSONObject jsonObject_insert_46 = new JSONObject(input_data);
            JSONArray jsonArray46 = jsonObject_insert_46.getJSONArray(Config.TAG_ARRAY_46);
            for (int c = 0; c < jsonArray46.length(); c++) {
                JSONObject jsonObject_getting_46 = (JSONObject) jsonArray46.get(c);

                table_data=jsonObject_getting_46.getString(Config.TAG_DATA);

                data46.add(table_data);

            }
            int rows_number = field46.size();
            int row_index = 0;
            for (int i = 0; i <data46.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data46, field46, tablename_46, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 46",e.toString());}

        try{

            JSONObject jsonObject_insert_47 = new JSONObject(input_data);
            JSONArray jsonArray47 = jsonObject_insert_47.getJSONArray(Config.TAG_ARRAY_47);
            for (int c = 0; c < jsonArray47.length(); c++) {
                JSONObject jsonObject_getting_47 = (JSONObject) jsonArray47.get(c);

                table_data=jsonObject_getting_47.getString(Config.TAG_DATA);

                data47.add(table_data);

            }
            int rows_number = field47.size();
            int row_index = 0;
            for (int i = 0; i <data47.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data47, field47, tablename_47, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 47",e.toString());}

        try{

            JSONObject jsonObject_insert_48 = new JSONObject(input_data);
            JSONArray jsonArray48 = jsonObject_insert_48.getJSONArray(Config.TAG_ARRAY_48);
            for (int c = 0; c < jsonArray48.length(); c++) {
                JSONObject jsonObject_getting_48 = (JSONObject) jsonArray48.get(c);

                table_data=jsonObject_getting_48.getString(Config.TAG_DATA);

                data48.add(table_data);

            }
            int rows_number = field48.size();
            int row_index = 0;
            for (int i = 0; i <data48.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data48, field48, tablename_48, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 48",e.toString());}

        try{

            JSONObject jsonObject_insert_49 = new JSONObject(input_data);
            JSONArray jsonArray49 = jsonObject_insert_49.getJSONArray(Config.TAG_ARRAY_49);
            for (int c = 0; c < jsonArray49.length(); c++) {
                JSONObject jsonObject_getting_49 = (JSONObject) jsonArray49.get(c);

                table_data=jsonObject_getting_49.getString(Config.TAG_DATA);

                data49.add(table_data);

            }
            int rows_number = field49.size();
            int row_index = 0;
            for (int i = 0; i <data49.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data49, field49, tablename_49, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 49",e.toString());}
        try{

            JSONObject jsonObject_insert_50 = new JSONObject(input_data);
            JSONArray jsonArray50 = jsonObject_insert_50.getJSONArray(Config.TAG_ARRAY_50);
            for (int c = 0; c < jsonArray50.length(); c++) {
                JSONObject jsonObject_getting_50 = (JSONObject) jsonArray50.get(c);

                table_data=jsonObject_getting_50.getString(Config.TAG_DATA);

                data1.add(table_data);

            }
            int rows_number = field50.size();
            int row_index = 0;
            for (int i = 0; i <data50.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data50, field50, tablename_50, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 50",e.toString());}

        try{

            JSONObject jsonObject_insert_51 = new JSONObject(input_data);
            JSONArray jsonArray51 = jsonObject_insert_51.getJSONArray(Config.TAG_ARRAY_51);
            for (int c = 0; c < jsonArray51.length(); c++) {
                JSONObject jsonObject_getting_51 = (JSONObject) jsonArray51.get(c);

                table_data=jsonObject_getting_51.getString(Config.TAG_DATA);

                data51.add(table_data);

            }
            int rows_number = field51.size();
            int row_index = 0;
            for (int i = 0; i <data51.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data51, field51, tablename_51, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 51",e.toString());}

        try{

            JSONObject jsonObject_insert_52 = new JSONObject(input_data);
            JSONArray jsonArray52 = jsonObject_insert_52.getJSONArray(Config.TAG_ARRAY_52);
            for (int c = 0; c < jsonArray52.length(); c++) {
                JSONObject jsonObject_getting_52 = (JSONObject) jsonArray52.get(c);

                table_data=jsonObject_getting_52.getString(Config.TAG_DATA);

                data52.add(table_data);

            }
            int rows_number = field52.size();
            int row_index = 0;
            for (int i = 0; i <data52.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data52, field52, tablename_52, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 52",e.toString());}

        try{

            JSONObject jsonObject_insert_53 = new JSONObject(input_data);
            JSONArray jsonArray53 = jsonObject_insert_53.getJSONArray(Config.TAG_ARRAY_53);
            for (int c = 0; c < jsonArray53.length(); c++) {
                JSONObject jsonObject_getting_53 = (JSONObject) jsonArray53.get(c);

                table_data=jsonObject_getting_53.getString(Config.TAG_DATA);

                data53.add(table_data);

            }
            int rows_number = field53.size();
            int row_index = 0;
            for (int i = 0; i <data53.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data53, field53, tablename_53, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 53",e.toString());}


        try{

            JSONObject jsonObject_insert_54 = new JSONObject(input_data);
            JSONArray jsonArray54 = jsonObject_insert_54.getJSONArray(Config.TAG_ARRAY_54);
            for (int c = 0; c < jsonArray54.length(); c++) {
                JSONObject jsonObject_getting_54 = (JSONObject) jsonArray54.get(c);

                table_data=jsonObject_getting_54.getString(Config.TAG_DATA);

                data54.add(table_data);

            }
            int rows_number = field54.size();
            int row_index = 0;
            for (int i = 0; i <data54.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data54, field54, tablename_54, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 54",e.toString());}

        try{

            JSONObject jsonObject_insert_55 = new JSONObject(input_data);
            JSONArray jsonArray55 = jsonObject_insert_55.getJSONArray(Config.TAG_ARRAY_55);
            for (int c = 0; c < jsonArray55.length(); c++) {
                JSONObject jsonObject_getting_55 = (JSONObject) jsonArray55.get(c);

                table_data=jsonObject_getting_55.getString(Config.TAG_DATA);

                data55.add(table_data);

            }
            int rows_number = field55.size();
            int row_index = 0;
            for (int i = 0; i <data55.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data55, field55, tablename_55, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 55",e.toString());}

        try{

            JSONObject jsonObject_insert_56 = new JSONObject(input_data);
            JSONArray jsonArray56 = jsonObject_insert_56.getJSONArray(Config.TAG_ARRAY_56);
            for (int c = 0; c < jsonArray56.length(); c++) {
                JSONObject jsonObject_getting_56 = (JSONObject) jsonArray56.get(c);

                table_data=jsonObject_getting_56.getString(Config.TAG_DATA);

                data56.add(table_data);

            }
            int rows_number = field56.size();
            int row_index = 0;
            for (int i = 0; i <data56.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data56, field56, tablename_56, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 56",e.toString());}

        try{

            JSONObject jsonObject_insert_57 = new JSONObject(input_data);
            JSONArray jsonArray57 = jsonObject_insert_57.getJSONArray(Config.TAG_ARRAY_57);
            for (int c = 0; c < jsonArray57.length(); c++) {
                JSONObject jsonObject_getting_57 = (JSONObject) jsonArray57.get(c);

                table_data=jsonObject_getting_57.getString(Config.TAG_DATA);

                data57.add(table_data);

            }
            int rows_number = field57.size();
            int row_index = 0;
            for (int i = 0; i <data57.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data57, field57, tablename_57, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 57",e.toString());}

        try{

            JSONObject jsonObject_insert_58 = new JSONObject(input_data);
            JSONArray jsonArray58 = jsonObject_insert_58.getJSONArray(Config.TAG_ARRAY_58);
            for (int c = 0; c < jsonArray58.length(); c++) {
                JSONObject jsonObject_getting_58 = (JSONObject) jsonArray58.get(c);

                table_data=jsonObject_getting_58.getString(Config.TAG_DATA);

                data58.add(table_data);

            }
            int rows_number = field58.size();
            int row_index = 0;
            for (int i = 0; i <data58.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data58, field58, tablename_58, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 58",e.toString());}

        try{

            JSONObject jsonObject_insert_59 = new JSONObject(input_data);
            JSONArray jsonArray59 = jsonObject_insert_59.getJSONArray(Config.TAG_ARRAY_59);
            for (int c = 0; c < jsonArray59.length(); c++) {
                JSONObject jsonObject_getting_59 = (JSONObject) jsonArray59.get(c);

                table_data=jsonObject_getting_59.getString(Config.TAG_DATA);

                data59.add(table_data);

            }
            int rows_number = field59.size();
            int row_index = 0;
            for (int i = 0; i <data59.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data59, field59, tablename_59, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 59",e.toString());}
        try{

            JSONObject jsonObject_insert_60 = new JSONObject(input_data);
            JSONArray jsonArray60 = jsonObject_insert_60.getJSONArray(Config.TAG_ARRAY_60);
            for (int c = 0; c < jsonArray60.length(); c++) {
                JSONObject jsonObject_getting_60 = (JSONObject) jsonArray60.get(c);

                table_data=jsonObject_getting_60.getString(Config.TAG_DATA);

                data1.add(table_data);

            }
            int rows_number = field60.size();
            int row_index = 0;
            for (int i = 0; i <data60.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data60, field60, tablename_60, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 60",e.toString());}

        try{

            JSONObject jsonObject_insert_61 = new JSONObject(input_data);
            JSONArray jsonArray61 = jsonObject_insert_61.getJSONArray(Config.TAG_ARRAY_61);
            for (int c = 0; c < jsonArray61.length(); c++) {
                JSONObject jsonObject_getting_61 = (JSONObject) jsonArray61.get(c);

                table_data=jsonObject_getting_61.getString(Config.TAG_DATA);

                data1.add(table_data);

            }
            int rows_number = field61.size();
            int row_index = 0;
            for (int i = 0; i <data61.size() - 1; i = i + rows_number) {

                insert(Activity_Installation.this, data61, field61, tablename_61, row_index);
                row_index += rows_number;

            }

        }catch(Exception e){Log.e("Exception in table 61",e.toString());}
        try{
            JSONObject jsonObject_insert_62 = new JSONObject(input_data);
            JSONArray jsonArray62 = jsonObject_insert_62.getJSONArray(Config.TAG_ARRAY_62);
            for (int c = 0; c < jsonArray62.length(); c++) {
                JSONObject jsonObject_getting_62 = (JSONObject) jsonArray62.get(c);
                table_data=jsonObject_getting_62.getString(Config.TAG_DATA);
                data62.add(table_data);
            }
            int rows_number = field62.size();
            int row_index = 0;
            for (int i = 0; i <data62.size() - 1; i = i + rows_number) {
                insert(Activity_Installation.this, data62, field62, tablename_62, row_index);
                row_index += rows_number;
            }
        }catch(Exception e){Log.e("Exception in table 62",e.toString());}


        Toast.makeText(getApplicationContext(), "Download Completed", Toast.LENGTH_LONG).show();
    }


    public void createDynamicDatabase(Context context, String tableName, ArrayList<String> title) {
        Log.i("INSIDE LoginDatabase", "****creatLoginDatabase*****");
        try {
            int i;
            String querryString;
            myDataBase = context.openOrCreateDatabase("Db", Context.MODE_WORLD_WRITEABLE, null);         //Opens database in writable mode.

            querryString = title.get(0) + " NVARCHAR(30),";
            Log.d("**createDynamicDatabase", "in oncreate");
            for (i = 1; i < title.size() - 1; i++) {
                querryString += title.get(i);
                querryString += " NVARCHAR(30) ";
                querryString += ",";
            }
            querryString += title.get(i) + " NVARCHAR(30) ";
            querryString = "CREATE TABLE IF NOT EXISTS " + tableName + "(" + querryString + ");";
            System.out.println("Create Table Stmt : " + querryString);

            myDataBase.execSQL(querryString);
            //   Toast.makeText(this, "Table "+ tablename_first +" created.", Toas
            // t.LENGTH_SHORT).show();
            myDataBase.close();

        } catch (android.database.SQLException ex) {
            Log.i("CreateDB Exception ", ex.getMessage());
        }
    }

    int insertion_index = 0;

    public void insert(Context context, ArrayList<String> array_vals, ArrayList<String> title, String TABLE_NAME, int row_index) {
        Log.d("Inside Insert", "Insertion starts for table name: " + TABLE_NAME);
        myDataBase = context.openOrCreateDatabase("Db", Context.MODE_WORLD_WRITEABLE, null);         //Opens database in writable mode.

        String titleString = null;
        String markString = null;
        int i = 0, c = 0;
        titleString = title.get(0) + ",";
        markString = "'" + array_vals.get(row_index) + "','";


        for (i = 1; i < title.size() - 1; i++) {
            titleString += title.get(i);
            titleString += ",";
        }


        for (c = row_index + 1; c < row_index + (title.size() - 1); c++) {
            Log.d("**Inserting Column", "in On create");
            Log.d("Column index", String.valueOf(c));
            markString += array_vals.get(c);
            markString += "','";
        }

        Log.d("**createDynamicDatabase", "in oncreate");

        titleString += title.get(i);
        markString += array_vals.get(c) + "'";

        INSERT = "insert into " + TABLE_NAME + "(" + titleString + ")" + "values" + "(" + markString + ")";
        insertion_index++;

        Log.d("Insert called", String.valueOf(insertion_index));

        System.out.println("Insert statement: " + INSERT);
        int s = 0;

        //  while (s < array_vals.size()) {

        //System.out.println("Size of array1" + array_vals.size());
        int j = 1;
        this.insertStmt = this.myDataBase.compileStatement(INSERT);
         /*   for (int k = 0; k < title.size(); k++) {

                System.out.println("BindString: insertStmt.bindString(" + j + "," + array_vals.get(k + s) + ")");
              //  insertStmt.bindString(j, array_vals.get(k + s));
                j++;
            }*/
        //  s += title.size();
        // }
        insertStmt.executeInsert();
        myDataBase.close();
        int flagger = 1;

    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Activity_Installation.this, "Fetching Data", "Wait...", false, false);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                jsonparsed(s);


            }

            @Override
            protected String doInBackground(Void... params) {
                JSONParserSync rh = new JSONParserSync();
                String s = rh.sendGetRequest(Config.Link_Download);
                return s;

            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void getJSON_TABLE_DATA() {
        class GetJSON2 extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Activity_Installation.this, "Fetching Data", "Wait...", false, false);
//                loading.setIndeterminate(false);
//                loading.setCancelable(true);
//                loading.show();

            }

            @Override
            protected void onPostExecute(String s1) {
                super.onPostExecute(s1);
                loading.dismiss();
                JSON_STRING1 = s1;
                jsonInsert(s1);


            }

            @Override
            protected String doInBackground(Void... params) {
//                loading = ProgressDialog.show(MainActivity.this, "Loading Data", "Wait...", false, false);

                JSONParserSync rh = new JSONParserSync();
                String s1 = rh.sendGetRequestParam(Config.LINK_JSON, STORE_ID);
                loading.getProgress();
                Intent ii = new Intent(Activity_Installation.this, Activity_masterScreen1.class);

                startActivity(ii);
                return s1;


            }


        }
        GetJSON2 gj1 = new GetJSON2();
        gj1.execute();
    }


    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login:
          //     new AttemptLogin().execute();
            //    new GetJSON2().execute();
               STORE_ID = user.getText().toString().trim();
//
             getJSON_TABLE_DATA();
                // here we have used, switch case, because on login activity you may //also want to show registration button, so if the user is new ! we can go the //registration activity , other than this we could also do this without switch //case.
            default:
                break;
        }
    }

    class AttemptLogin extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_Installation.this);
            pDialog.setMessage("Attempting for login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected  String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // here Check for success tag
            int success;
            String username =user.getText().toString();


            String password = pass.getText().toString();


//            Bundle b = new Bundle();
//            b.putString("Store_Id", username);
//            Intent i = new Intent(Activity_Installation.this,GMainActivity.class);
//            i.putExtras(b);
//            startActivity(i);

            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("Store_Id", username));
                params.add(new BasicNameValuePair("OTP", password));

                Log.d("request!", "starting");
                JSONParser jsonParser = new JSONParser();
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);


                // checking  log for json response
                Log.d("Login attempt", json.toString());

                // success tag for json
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Successfully Login!", json.toString());

                    Intent ii = new Intent(Activity_Installation.this,login.class);
                    ii.putExtra("Store_Id", username );
                    startActivity(ii);
                    finish();
                    // this finish() method is used to tell android os that we are done with current //activity now! Moving to other activity

                    return json.getString(TAG_MESSAGE);
                }else{

                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        /**
         * Once the background process is done we need to  Dismiss the progress dialog asap
         * **/
        protected void onPostExecute(String message) {

            pDialog.dismiss();
            if (message != null){
                Toast.makeText(Activity_Installation.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}