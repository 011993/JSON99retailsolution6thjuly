package com.mycompany.apps;

/**
 * Created by Aman on 02-06-2016.
 */public class Config {
    public static final String LINK_JSON = "http://52.76.28.14/Android/download.php?STORE_ID=";
    // public static final String Link_Download = "http://52.76.28.14/Android/json.php";
    public static final String Link_UPLOAD = "http://52.76.28.14/Android/2.php";


    public static final String Link_Update = "http://52.76.28.14/Android/update.php";
    public static final String Link_Download = "http://52.76.28.14/Android/json.php";
    public static final String Link_Upload = "http://52.76.28.14/Android/retail_videodata.php";
    public static final String Link_Upload_Media_Click = "http://52.76.28.14/Android/retail_media_click.php";




    /***************************************RETAIL MEDIA CLICK DATA INSERT DATA INTO DATABASE***********************************************/

    public static final String VIDEODATA_ADPLAY= "ad_play";
    public static final String VIDEODATA_STOREID= "store_id";
    public static final String VIDEODATA_MEDIAID= "store_media_id";
    public static final String VIDEODATA_VIDEONAME= "video_name";
    public static final String VIDEODATA_STARTDATE= "start_date";
    public static final String VIDEODATA_ENDDATE= "end_date";
    public static final String VIDEODATA_STARTTIME= "start_time";
    public static final String VIDEODATA_ENDTIME= "end_time";




    /***************************************RETAIL VIDEO DATA INSERT DATA INTO DATABASE***********************************************/

    public static final String MEDIACLICK_ADPLAYID= "ad_play";
    public static final String MEDIACLICK_STOREMEDIAID= "media_store_id";
    public static final String MEDIACLICK_NUMBEROFCLICK= "number_of_click";
    public static final String MEDIACLICK_MOBILENUMBER= "mobile_number";
//==========================================JSON TAGS FOR TABLE NAME=====================================================================//

    public static final String TAG_ARRAY_TABLE_ONE = "ret_ticket_install_register";
    public static final String TAG_ARRAY_TABLE_TWO = "retail_ad_blinkinglogo ";
    public static final String TAG_ARRAY_TABLE_THREE = "retail_ad_blinkinglogo_cont";
    public static final String TAG_ARRAY_TABLE_FOUR = "retail_ad_store_main";
    public static final String TAG_ARRAY_TABLE_FIVE = "retail_ad_store_main_cont";
    public static final String TAG_ARRAY_TABLE_SIX = "retail_ad_ticker";
    public static final String TAG_ARRAY_TABLE_SEVEN = "retail_ad_ticker_cont";
    public static final String TAG_ARRAY_TABLE_EIGHT = "retail_card_define";
    public static final String TAG_ARRAY_TABLE_NINE = "retail_card_define_mfg";
    public static final String TAG_ARRAY_TABLE_TEN = "retail_comp_btl";
    public static final String TAG_ARRAY_TABLE_ELEVEN = "retail_cust";
    public static final String TAG_ARRAY_TABLE_TWELVE = "retail_cust_loyalty";
    public static final String TAG_ARRAY_TABLE_THIRTEEN = "retail_inventory";
    public static final String TAG_ARRAY_TABLE_FOURTEEN = "retail_media";
    public static final String TAG_ARRAY_TABLE_FIFTEEN = "retail_mfg_btl";
    public static final String TAG_ARRAY_TABLE_SIXTEEN = "retail_store";
    public static final String TAG_ARRAY_TABLE_SEVENTEEN = "retail_store_maint";
    public static final String TAG_ARRAY_TABLE_EIGHTEEN = "retail_store_prod";
    public static final String TAG_ARRAY_TABLE_NINETEEN = "retail_store_prod_local_cpg";
    public static final String TAG_ARRAY_TABLE_TWENTY = "retail_store_reports";
    public static final String TAG_ARRAY_TABLE_21 = "retail_store_vend_reject";
    public static final String TAG_ARRAY_TABLE_22 = "retail_store_vendor";
    public static final String TAG_ARRAY_TABLE_23 = "retail_str_dstr";
    public static final String TAG_ARRAY_TABLE_24 = "retail_str_grn_detail";
    public static final String TAG_ARRAY_TABLE_25 = "retail_str_grn_master";
    public static final String TAG_ARRAY_TABLE_26 = "retail_str_po_detail";
    public static final String TAG_ARRAY_TABLE_27 = "retail_str_po_master";
    public static final String TAG_ARRAY_TABLE_28 = "retail_str_sales_detail";
    public static final String TAG_ARRAY_TABLE_29 = "retail_str_sales_details_return";
    public static final String TAG_ARRAY_TABLE_30 = "retail_str_sales_master_return";
    public static final String TAG_ARRAY_TABLE_31 = "retail_str_sales_master";
    public static final String TAG_ARRAY_TABLE_32 = "retail_str_vendor_detail_return";
    public static final String TAG_ARRAY_TABLE_33 = "retail_str_vendor_master_return";
    public static final String TAG_ARRAY_TABLE_34 = "retail_tax";
    public static final String TAG_ARRAY_TABLE_35 = "retail_top_product";
    public static final String TAG_ARRAY_TABLE_36 = "retail_vend_dstr";
    public static final String TAG_ARRAY_TABLE_37 = "retail_videodata";
    public static final String TAG_ARRAY_TABLE_38 = "retail_videodata_cont";
    public static final String TAG_ARRAY_TABLE_39 = "retail_videodata_cont1";
    public static final String TAG_ARRAY_TABLE_40 = "rule_defination";
    public static final String TAG_ARRAY_TABLE_41 = "tmp_retail_pay_desc";
    public static final String TAG_ARRAY_TABLE_42 = "ad_ticker_main";
    public static final String TAG_ARRAY_TABLE_43 = "bank_details";
    public static final String TAG_ARRAY_TABLE_44 = "retail_store_prod_com";
    public static final String TAG_ARRAY_TABLE_45 = "retail_store_prod_cpg";
    public static final String TAG_ARRAY_TABLE_46 = "retail_store_prod_local";
    public static final String TAG_ARRAY_TABLE_47 = "retail_str_po_detail_hold";
    public static final String TAG_ARRAY_TABLE_48 = "retail_str_stock_master";
    public static final String TAG_ARRAY_TABLE_49 = "retail_str_stock_master_hold";
    public static final String TAG_ARRAY_TABLE_50 = "retail_str_day_open_close";
    public static final String TAG_ARRAY_TABLE_51 = "retail_store_sales_desc";
    public static final String TAG_ARRAY_TABLE_52 = "retail_employees";
    public static final String TAG_ARRAY_TABLE_53 = "tmp_retail_str_sales_detail";
    public static final String TAG_ARRAY_TABLE_54 = "tmp_retail_str_sales_master";
    public static final String TAG_ARRAY_TABLE_55 = "dr_speciality";
    public static final String TAG_ARRAY_TABLE_56 = "dr_discription";
    public static final String TAG_ARRAY_TABLE_57 = "retail_send_mail_pdf";
    public static final String TAG_ARRAY_TABLE_58 = "retail_str_lineitem_disc";
    public static final String TAG_ARRAY_TABLE_59 = "ad_main";
    public static final String TAG_ARRAY_TABLE_60 = "retail_media_click";
    public static final String TAG_ARRAY_TABLE_61= "tmp_vend_dstr_payment";
    public static final String TAG_ARRAY_TABLE_62="retail_str_bill_lvl_disc";


//================================================TAG ARRAY==========================================================================//

    public static final String TAG_ARRAY_1 = "Ret_ticket_install_register";
    public static final String TAG_ARRAY_2 = "Retail_ad_blinkinglogo";
    public static final String TAG_ARRAY_3 ="Retail_ad_blinkinglogo_cont";
    public static final String TAG_ARRAY_4 ="Retail_ad_store_main";
    public static final String TAG_ARRAY_5 ="Retail_ad_store_main_cont";
    public static final String TAG_ARRAY_6 = "Retail_ad_ticker";
    public static final String TAG_ARRAY_7 ="Retail_ad_ticker_cont";
    public static final String TAG_ARRAY_8 = "Retail_card_define";
    public static final String TAG_ARRAY_9 ="Retail_card_define";
    public static final String TAG_ARRAY_10 = "Retail_comp_btl";
    public static final String TAG_ARRAY_11 = "Retail_cust";
    public static final String TAG_ARRAY_12 = "Retail_cust_loyalty";
    public static final String TAG_ARRAY_13 = "Retail_inventory";
    public static final String TAG_ARRAY_14 ="Retail_media";
    public static final String TAG_ARRAY_15 = "Retail_mfg_btl";
    public static final String TAG_ARRAY_16 = "Retail_store";
    public static final String TAG_ARRAY_17 = "Retail_store_maint";
    public static final String TAG_ARRAY_18 = "Retail_store_prod";
    public static final String TAG_ARRAY_19 = "Retail_store_prod_local_cpg";
    public static final String TAG_ARRAY_20 ="retail_store_reports";
    public static final String TAG_ARRAY_21 ="retail_store_vend_reject";
    public static final String TAG_ARRAY_22 = "retail_store_vendor";
    public static final String TAG_ARRAY_23 = "Retail_str_dstr";
    public static final String TAG_ARRAY_24 ="retail_str_grn_detail";
    public static final String TAG_ARRAY_25 ="retail_str_grn_master";
    public static final String TAG_ARRAY_26 ="retail_str_po_detail";
    public static final String TAG_ARRAY_27 ="retail_str_po_master";
    public static final String TAG_ARRAY_28 ="retail_str_sales_detail";
    public static final String TAG_ARRAY_29 ="retail_str_sales_details_return";
    public static final String TAG_ARRAY_30 ="retail_str_sales_master_return";
    public static final String TAG_ARRAY_31 ="retail_str_sales_master";
    public static final String TAG_ARRAY_32 ="retail_str_vendor_detail_return";
    public static final String TAG_ARRAY_33 ="retail_str_vendor_master_return";
    public static final String TAG_ARRAY_34 ="retail_tax";
    public static final String TAG_ARRAY_35 = "Retail_top_product";
    public static final String TAG_ARRAY_36 = "Retail_vend_dstr";
    public static final String TAG_ARRAY_37 ="Retail_videodata";
    public static final String TAG_ARRAY_38 ="Retail_videodata_cont";
    public static final String TAG_ARRAY_39 ="Retail_videodata_cont1";
    public static final String TAG_ARRAY_40 ="Rule_defination";
    public static final String TAG_ARRAY_41 ="Tmp_retail_pay_desc";
    public static final String TAG_ARRAY_42 ="ad_ticker_main";
    public static final String TAG_ARRAY_43 ="bank_details";
    public static final String TAG_ARRAY_44 = "Retail_store_prod_com";
    public static final String TAG_ARRAY_45 = "retail_store_prod_cpg";
    public static final String TAG_ARRAY_46 = "Retail_store_prod_local";
    public static final String TAG_ARRAY_47 = "Retail_str_po_detail_hold";
    public static final String TAG_ARRAY_48 = "retail_str_stock_master";
    public static final String TAG_ARRAY_49 = "Retail_str_stock_master_hold";
    public static final String TAG_ARRAY_50 ="Retail_str_day_open_close";
    public static final String TAG_ARRAY_51 =  "Retail_store_sales_desc";
    public static final String TAG_ARRAY_52 ="Retail_employees";
    public static final String TAG_ARRAY_53 ="Tmp_retail_str_sales_detail";
    public static final String TAG_ARRAY_54 ="Tmp_retail_str_sales_master";

    public static final String TAG_ARRAY_55 =  "dr_speciality";
    public static final String TAG_ARRAY_56 ="Dr_discription";
    public static final String TAG_ARRAY_57 ="Retail_send_mail_pdf";
    public static final String TAG_ARRAY_58 ="Retail_str_lineitem_disc";
    public static final String TAG_ARRAY_59 ="Ad_main";
    public static final String TAG_ARRAY_60 ="Retail_media_click";
    public static final String TAG_ARRAY_61 ="Tmp_vend_dstr_payment";
    public static final String TAG_ARRAY_62="Retail_str_bill_lvl_disc";

    public static final String TAG_FIELD = "Field";

    public static final String TAG_DATA="data";


}


