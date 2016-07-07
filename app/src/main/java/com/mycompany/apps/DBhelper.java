package com.mycompany.apps;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import Pojo.AdTickerReportModel;
import Pojo.BillLevelModel;
import Pojo.BlinkingLogoReportModel;
import Pojo.BlinkingModel;
import Pojo.Companylistmodel;
import Pojo.CreditNote;
import Pojo.Customer;
import Pojo.CustomerRejectModel;
import Pojo.DirectVendorPaymentModel;
import Pojo.DoctorPojo;
import Pojo.IndirectVendorPaymentModel;
import Pojo.InventoryReportModel;
import Pojo.Inventorygrnmodel;
import Pojo.Inventorymodel;
import Pojo.Inventoryproductmodel;
import Pojo.ListModel;
import Pojo.LocalProduct;
import Pojo.Loginpo;
import Pojo.LoyalityModel;
import Pojo.Mfglistmodel;
import Pojo.Product;
import Pojo.ProductAuto;
import Pojo.PurchaseInvoiceDropDownModel;
import Pojo.PurchaseProductModelwithpo;
import Pojo.PurchaseProductReportModel;
import Pojo.Registeremployeesmodel;
import Pojo.ReportDistributorModel;
import Pojo.ReportLocalProductCpgModel;
import Pojo.ReportLocalProductPharmaModel;
import Pojo.ReportProductCpgModel;
import Pojo.ReportProductPharmaModel;
import Pojo.ReportVendorModel;
import Pojo.ReportVendorReturnModel;
import Pojo.SaleReportModel;
import Pojo.SalesReturnReportModel;
import Pojo.Salesreturndetail;
import Pojo.SalesreturndetailWithoutPo;
import Pojo.ShowModel;
import Pojo.StoreMainModel;
import Pojo.Topfullproductmodel;
import Pojo.VendorModel;
import Pojo.PurchaseProductModel;
import Pojo.RetailcardDefineModel;
import Pojo.RuleDefinationModel;
import Pojo.Sales;
import Pojo.Store;
import Pojo.Tax;
import Pojo.Vendor;
import Pojo.VendorRejectModel;
import Pojo.VendorReportModel;
import Pojo.VendorReturnModel;
import Pojo.line_item_Product_Model;
import Pojo.localvendor;

public class DBhelper  extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/" + Activity_Installation.class.getPackage().getName() + "/databases/";
	private static final String DATABASE_NAME = "Db";
	private SQLiteDatabase myDataBase;
	private final Context mycontext;


	public static final String TABLE_NAME = "retail_cust";//customer table name
	public static final String TABLE_NAME1 = "retail_vendor";//vendor table name
	public static final String TABLE_NAME2 = "retail_employees";//employee table name
	public static final String TABLE_NAME3 = "retail_store_prod_local";//local product table name
	public static final String TABLE_NAME4 = "retail_store_prod";//local product table name
	public static final String TABLE_NAME5 = "retail_store";//store table name
	public static final String TABLE_NAME6 = "retail_store_maint";//maintainence table name
	public static final String TABLE_NAME7 = "retail_ad_ticker";//tickek table name
	public static final String TABLE_NAME8 = "retail_tax";//tax table name

	private static final String TABLE_NAME10 = "retail_str_dstr";

	private static final String TABLE_NAME11 = "retail_str_po_detail";

	private static final String TABLE_NAME13 = "retail_store_prod_com";

	public static final String STORE_ID_INVENTORY = "Store_Id";//Store table name


	public static final String COLUMN_TICKET_ID = "Ticket_Id";//Column of retail_store_maint
	public static final String COLUMN_SUPPORT_TICKET_STATUS = "Support_Ticket_Status";//Column of retail_store_maint
	public static final String COLUMN_SUBJECT_DESC = "Subject_Desc";//Column of retail_store_maint
	public static final String COLUMN_TEAM_MEMBER = "Team_Member";//Column of retail_store_maint
	public static final String COLUMN_DATE = "Date";//Column of retail_store_maint
	public static final String COLUMN_LastUpdate = "LastUpdate";//Column of retail_store_maint
	//public static final String COLUMN_AD_MAIN_ID="Ad_Main_Id";
	public static final String STORE_ID = "Store_Id";//Store table name
	public static final String STORE_NAME = "Str_Nm";//Store table name
	public static final String STORE_EMAIL = "E_Mail";//Store table name
	public static final String STORE_MOBILE = "Tele";//Store table name
	public static final String STORE_ZIP = "Zip";//Store table name
	public static final String STORE_CONTACTNAME = "Str_Cntct_Nm";//Store table name
	public static final String STORE_CITY = "Cty";//Store table name
	public static final String STORE_COUNTRY = "Str_Ctr";//Store table name
	public static final String STORE_ADDRESS1 = "Add_1";//Store table name
	public static final String PRODUCTNAME = "Prod_Nm";//Column of product
	public static final String PRODUCTSTOREID = "Store_Id";//Column of product
	public static final String PRODUCTBARCODE = "BarCode";//Column of product
	public static final String PRODUCTMRP = "MRP";//Column of product
	public static final String PRODUCTSELLING = "S_Price";//Column of product
	public static final String PRODUCTPURCHASE = "P_Price";//Column of product
	public static final String PRODUCTACTIVE = "Active";//Column of product
	public static final String PRODUCTTAXDESC = "Tax_Desc";//Column of product
	public static final String PRODUCTMANUF = "Mfg";//Column of product
	public static final String PRODUCTINTERNET = "Internet_Price";//Column of product
	public static final String PRODUCTRELEVANT = "Is_Prod_Rel_Int";//Column of product
	public static final String PRODUCTMARGIN = "Profit_Margin";//Column of product
	//public static final String PRODUCTMEASUREUNIT = "Unit_Of_Measure";//Column of product
	//********************************************************************************8
	public static final String PRODUCTMEASUREUNITINV = "Selling_Order_Unit";//Column of product
	public static final String PRODUCTBATCHINV = "Batch_No";//Column of product
	public static final String PRODUCTEXPDATEINV = "Exp_Date";//Column of product
	public static final String PRODUCTSTOCK = "Qty";//Column of product
	public static final String PRODUCTCONVERSION = "Conv_Fact";//Column of product
	public static final String PRODUCTINDUSTERY = "Ind_Nm";//Column of product
	public static final String PRODUCTFREEGOODS = "Free_Goods";//Column of product

	public static final String PRODUCTINVENTORYUOM = "Uom";//Column of product
	public static final String PRODUCTSTRENGTH = "Strength";//Column of product
	public static final String PRODUCTPRODUCTID = "Prod_Id";//Column of product
	public static final String PRODUCTPACKINGUNIT = "Purch_Order_Unit";//Column of product
	public static final String CUSTOMERNAME = "name";// Columnn of customers
	public static final String CUSTOMERMOBILENO = "Mobile_No";//Columnn of customers
	public static final String CUSTOMEREMAIL = "e_mail";//Columnn of customers
	public static final String CUSTOMERCREDIT = "Credit_Cust";
	//public static final String VENDORSTOREID = "Dstr_Id";//Columnn of customers
	public static final String VENDORID = "Dstr_Id";//Columnn of vendors
	public static final String VENDORNAME = "Dstr_Nm";//Columnn of vendors
	public static final String VENDORCONTACTNAME = "Dstr_Cntct_Nm";//Columnn of vendors
	public static final String VENDORADDRESS = "Add_1";//Columnn of vendors
	public static final String VENDORCITY = "City";//Columnn of vendors
	public static final String VENDORCOUNTRY = "Ctr_Nm";//Columnn of vendors
	public static final String VENDORZIP = "Zip";//Columnn of vendors
	public static final String VENDORTTELEPHONE = "Tele";//Columnn of vendors
	public static final String VENDORMOBILE = "Mobile";//Columnn of vendors
	public static final String VENDORINVENTORY = "Dstr_Inv";//Columnn of vendors
	public static final String VENDORACTIVE = "Active";//Columnn of vendors
	public static final String VENDOREmail = "Email";//Columnn of vendors
	public static final String PRODUCTLOCALSTOREID = "Store_Id";//Columnn of localproduct
	public static final String PRODUCTLOCALPRODUCTID = "Prod_Id";//Columnn of localproduct
	public static final String PRODUCTLOCALPRODUCTNAME = "Prod_Nm";//Columnn of localproduct
	public static final String PRODUCTLOCALPRODUCTACTIVE = "Active";//Columnn of localproduct
	public static final String PRODUCTLOCALBARCODE = "BarCode";//Columnn of localproduct
	public static final String PRODUCTLOCALMRP = "MRP";//Columnn of localproduct
	public static final String PRODUCTLOCALSELLING = "S_Price";//Columnn of localproduct
	public static final String PRODUCTLOCALPURCHASE = "P_Price";//Columnn of localproduct
	public static final String PRODUCTLOCALTAXID = "Tax_Id";//Columnn of localproduct
	public static final String PRODUCTLOCALACTIVE = "Active";//Columnn of localproduct
	public static final String PRODUCTLOCALMARGIN = "Profit_Margin";//Columnn of localproduct
	public static final String TAX_DESCRIPTION = "tax_desc";// Columnn of Tax
	public static final String TAX_RATE = "tax_rate";// Columnn of Tax
	public static final String TAX_ID = "tax_id";// Columnn of Tax
	public static final String LOCALVENDORSTOREID = "Store_Id";// Columnn of localvendor
	public static final String LOCALVENDORID = "Vend_Id";// Columnn of localvendor
	public static final String LOCALVENDORNAME = "Vend_Nm";// Columnn of localvendor
	public static final String LOCALVENDORCONTACT = "Vend_cntct_Nm";// Columnn of localvendor
	public static final String LOCALVENDORADDRESS = "Add_1";// Columnn of localvendor
	public static final String LOCALVENDORCITY = "City";// Columnn of localvendor
	public static final String LOCALVENDORCOUNTRY = "Ctr_Desc";// Columnn of localvendor
	public static final String LOCALVENDORZIP = "Zip";// Columnn of localvendor
	public static final String LOCALVENDORTELE = "Tele";// Columnn of localvendor
	public static final String LOCALVENDORMOBILE = "Mobile";// Columnn of localvendor
	public static final String LOCALVENDORACTIVE = "Active";// Columnn of localvendor
	public static final String LOCALVENDORINVENTORY = "Vend_Inv";// Columnn of localvendor
	public static final String ADMAINID = "Ad_Main_Id";
	public static final String STOREID = "Store_Id";
	public static final String ADDESC = "Ad_Desc";
	public static final String ADTEXT = "Ad_Text";
	public static final String ADSTARTDATE = "Ad_Strt_Dt";
	public static final String ADENDDATE = "Ad_End_Dt";
	public static final String STATUS = "Status";
	public static final String COLUMN_UOM = "Uom";
	public static final String COLUMN_LAST = "LastUpdate";

	/**************************************************
	 * retail_str_stock_master
	 **************************************/
	public static final String COLUMN_PROD_ID = "Prod_Id";
	public static final String COLUMN_PROD_NAME = "Prod_Nm";
	public static final String COLUMN_BATCH_NO = "Batch_No";
	public static final String COLUMN_EXP_DATE = "Exp_Date";
	public static final String COLUMN_QUANTITY = "Qty";

	/****************************
	 * Get the values from retail_ad_ticker
	 *****************************************/
	public static final String COLUMN_AD_MAIN_ID = "Ad_Main_Id";
	public static final String COLUMN_AD_TEXT = "Ad_Text";
	public static final String COLUMN_AD_START_DATE = "Ad_Strt_Dt";
	public static final String COLUMN_AD_END_DATE = "Ad_End_Dt";
	public static final String COLUMN_AD_CST_SLB1 = "Ad_Cst_Slb1";
	public static final String COLUMN_AD_CST_SLB2 = "Ad_Cst_Slb2";
	public static final String COLUMN_AD_CST_SLB3 = "Ad_Cst_Slb3";
	public static final String COLUMN_AD_STATUS = "Status";
	public static final String COLUMN_AD_ACTIVE = "Active";
	/****************************
	 * Get the values from retail_ad_blinkinglogo
	 *****************************************/
	public static final String COLUMN_Blinkinglogo_AD_MAIN_ID = "Ad_Main_Id";
	public static final String COLUMN_Blinkinglogo_AD_DESC = "Ad_Desc";
	public static final String COLUMN_Blinkinglogo_AD_START_DATE = "Ad_Strt_Dt";
	public static final String COLUMN_Blinkinglogo_AD_END_DATE = "Ad_End_Dt";
	public static final String COLUMN_Blinkinglogo_AD_CST_SLB1 = "Ad_Cst_Slb1";
	public static final String COLUMN_Blinkinglogo_AD_CST_SLB2 = "Ad_Cst_Slb2";
	public static final String COLUMN_Blinkinglogo_AD_CST_SLB3 = "Ad_Cst_Slb3";
	public static final String COLUMN_Blinkinglogo_AD_STATUS = "Status";
	public static final String COLUMN_Blinkinglogo_AD_ACTIVE = "Active";
	/*******************************
	 * Get the Values from retail_ad_Store_Main
	 **************************************************/
	public static final String COLUMN_StoreMain_AD_MAIN_ID = "Ad_Main_Id";
	public static final String COLUMN_StoreMain_AD_DESC = "Ad_Desc";
	public static final String COLUMN_StoreMain_AD_START_DATE = "Ad_Strt_Dt";
	public static final String COLUMN_StoreMain_AD_END_DATE = "Ad_End_Dt";
	public static final String COLUMN_StoreMain_AD_CST_SLB1 = "Ad_Cst_Slb1";
	public static final String COLUMN_StoreMain_AD_CST_SLB2 = "Ad_Cst_Slb2";
	public static final String COLUMN_StoreMain_AD_CST_SLB3 = "Ad_Cst_Slb3";
	public static final String COLUMN_StoreMain_AD_STATUS = "Status";
	public static final String COLUMN_StoreMain_AD_ACTIVE = "Active";

	/***************************************************
	 * retail_str_vendor_detail_return
	 ******************************************/

	public static final String COLUMN_REPRTVENDR_NM = "Vendor_Nm";
	public static final String COLUMN_REPRTPROD_NM = "Prod_Nm";
	public static final String COLUMN_REPRTP_Price = "P_Price";
	public static final String COLUMN_REPRT_QTY = "Qty";
	public static final String COLUMN_REPRTVENDR_ID = "Vendor_Return_Id";
	public static final String COLUMN_REPRT_UOM = "Uom";
	public static final String COLUMN_REPRTBATCH_NO = "Batch_No";
	public static final String COLUMN_REPRTEXP_DATE = "Exp_Date";
	public static final String COLUMN_REPRT_TOTAL = "Total";
	public static final String COLUMN_REPRTREASON_RETRN = "Reason_Of_Return";

	/*******************************
	 * Get the Values from retail_str_bill_details_internet
	 **************************************************/

	public static final String COLUMN_ORDERID = "orderid";
	public static final String COLUMN_STORE_ID = "Store_Id";
	public static final String COLUMN_MOBILENO = "Mobile_No";
	public static final String COLUMN_PRODUCTID = "Product_id";
	public static final String COLUMN_PRODNM = "Prod_Nm";
	public static final String COLUMN_MRP = "MRP";
	public static final String COLUMN_STATUS = "Status";
	/****************************
	 * Get the values from retail_str_day_open_close
	 *****************************************/
	public static final String POS_DATE = "Business_Date";
	public static final String START_DATE = "Start_Date";
	public static final String START_CASH = "Start_Cash";
	public static final String CBCASH_ONHAND = "CBcash_Onhand";
	public static final String TRANSACTIONID = "Tri_Id";
	public static final String FLAG = "Flag";


	public static final String Userid = "Usr_Nm";//Columnn of Login Activity
	public static final String Pass = "Password";//Columnn of Login Activity

	//***************************************retail_Card_define************************************************
	private static final String TABLE_NAME_CARD = "retail_Card_define";
	public static final String STORE_ID_CARD = "Store_Id";
	public static final String POINTS_RANGE = "Points_Range";
	public static final String ID = "Id";
	public static final String CARD_TYPE = "Card_Type";

	//**************************************rule_defination***********************************************************
	private static final String TABLE_NAME_RULE = "rule_defination";
	public static final String STORE_ID_RULE = "Store_Id";
	public static final String BASE_VALUE = "Base_Value";
	public static final String SNO = "SNo";
	public static final String CARD_TYPE_RULE = "Card_Type";
	public static final String PER_TON_POINTS = "Per_ton_Points";
	//**************************************retail_store_reports***********************************************************
	private static final String TABLE_NAME_REPORT = "retail_store_reports";
	public static final String STORE_ID_REPORT = "Store_Id";
	public static final String REPORT_NAME = "Report_Name";
	public static final String SNO_REPORT = "SNo";
	public static final String REPORT_CRITERIA = "Report_Criteria";
	public static final String COMMENTS = "Comments";
	/******************************************
	 * Get the values from retail_cust_loyality
	 *************************************************************************************/
	public static final String COLUMN_CUSTID = "Cust_id";
	public static final String COLUMN_MOBILENO1 = "Mobile_No";
	public static final String COLUMN_NAME = "Name";
	public static final String COLUMN_POINTSEARNED = "Points_Earned";
	public static final String COLUMN_POINTSREDEEMED = "Points_Redeemed";
	public static final String COLUMN_POINTSAVALILABLE = "Points_Available";

	/**********************************************
	 * Get the values from retail_card_define
	 **********************************************************************************/
	public static final String COLUMN_STOREID_RETAIL_CARD_DEFINE = "Store_Id";
	public static final String COLUMN_ID = "Id";
	public static final String COLUMN_CARD_TYPE = "Card_Type";
	public static final String COLUMN_POINTS_RANGE = "Points_Range";
	/****************************************************
	 * Get the values from rule defination
	 ***************************************************************************************/
	public static final String COLUMN_Store_ID_RULE_DEFINAATION = "Store_Id";
	public static final String COLUMN_SNO = "SNo";
	public static final String COLUMN_CARDTYPE = "Card_Type";
	public static final String COLUMN_BASE_VALUE = "Base_Value";
	public static final String COLUMN_PER_TON_POINTS = "Per_ton_Points";


	/*******************************************************
	 * retail_str_dstr
	 ******************************************************************************/
	public static final String COLUMN_DSTR_NAME = "Dstr_Nm";
	public static final String COLUMN_ACTIVE = "Active";
	public static final String COLUMN_DSTR_INV = "Dstr_Inv";
	/****************************************
	 * get data and insert into retail-str_po_detail
	 ********************************************************************************************/
	public static final String COLUMN_PO_NO = "Po_No";
	public static final String COLUMN_PRODUCT_ID = "Prod_Id";
	public static final String COLUMN_PRODUCT_NAME = "Prod_Nm";
	public static final String COLUMN_P_PRICE = "P_Price";
	public static final String COLUMN_QTY = "Qty";
	public static final String COLUMN_TOTAL = "Total";
	public static final String COLUMN_STOREID = "Store_Id";
	public static final String COLUMN_VEND_DSTR_ID = "Vend_Dstr_Id";
	public static final String COLUMN_STORE_ID_DSTR = "Store_Id";
	public static final String COLUMN_VEND_DSTR_NAME = "Vend_Dstr_Nm";
	public static final String COLUMN_VEND_DSTR_INV = "Vend_Dstr_Inv";
	//***********************************Sales bill************************************
	public static final String PRODUCT_NAME = "Prod_Nm";
	public static final String BATCH_NO = "Batch_No";
	public static final String EXPIRY = "Exp_Date";
	public static final String P_PRICE = "P_Price";
	public static final String S_PRICE = "S_Price";
	public static final String QUANTITY = "Qty";
	public static final String TOTALQTY = "Con_Mul_Qty";
	public static final String MRP = "MRP";
	public static final String CONVMRP = "Conv_MRP";
	public static final String CONVSPRICE = "Conv_SPrice";






	public static final String AMOUNT = "Amount";
	public static final String BARCODE = "BarCode";
	public static final String UOM = "Uom";
	public static final String TOTAL = "total";
	//*****************************Sales Screen Co************************************
	public static final String TRANS_ID = "Tri_Id";
	public static final String STORESALES_ID = "Store_Id";
	public static final String GRAND_TOTAL = "Grand_Total";
	public static final String BILLNO = "Invoice_No";
	//**************************************Sales Return Column****************************
	public static final String RETURNTRANSIDs = "Tri_Id";
	public static final String RETURNTORESALES_IDs = "Store_Id";
	public static final String RETURNEXPIRY = "Exp_Date";
	public static final String RETURNPRODUCTNAME = "Prod_Nm";
	public static final String RETURNSELLINGPRICE = "S_Price";
	public static final String RETURNQUANTITY = "Qty";
	public static final String RETURNUNITOFMEASURE = "Uom";
	public static final String RETURNGRANDTOTAL = "Total";
	public static final String RETURNBATCHNO = "Batch_No";
	public static final String RETURNMRP = "MRP";
	public static final String RETURNREASON = "Reason_Return";
	public static final String RETURSALEDATE = "Sale_Date";
	public static final String RETURNBUSINESS = "Business_Date";
	public static final String RETURNSALETIME = "Sale_Time";
	/**********************************************************************************************/
	public static final String COLUMN_FOR_VEND_RETURN_REJECTION = "Reason_for_Rejection";
	/****************************************************
	 * Store data into Retail_str_po_detail
	 ********************************************************************************************/
	public static final String Purchase_COLUMN_PRODUCT_ID = "Prod_Id";
	public static final String Purchase_COLUMN_PRODUCT_NAME = "Prod_Nm";
	public static final String Purchase_COLUMN_P_PRICE = "P_Price";
	public static final String Purchase_COLUMN_VENDOR_DISTRIBUTOR_NAME = "Vendor_Nm";
	public static final String Purchase_COLUMN_UOM = "Uom";
	public static final String Purchase_COLUMN_QTY = "Qty";
	public static final String Purchase_COLUMN_TOTAL = "Total";
	public static final String Purchase_COLUMN_MRP = "MRP";
	public static final String Purchase_COLUMN_Conv_Fact = "Conv_Fact";
	public static final String Purchase_COLUMN_Flag = "Flag";
	/****************************************************
	 * Store data into Retail_str_po_master
	 ********************************************************************************************/
	public static final String COLUMN_MASTER_PO_NO = "Po_No";
	public static final String COLUMN_MASTER_STORE_ID = "Store_Id";
	public static final String COLUMN_MASTER_GRAND_TOTAL = "Grand_Total";
	public static final String COLUMN_MASTER_VENDOR_NAME = "Vendor_Nm";
	public static final String COLUMN_MASTER_LASTUPDATE = "Lastupdate";
	/****************************************************
	 * tmp_vend_pay_desc
	 ********************************************************************************************/
	public static final String COLUMN_VENDOR_REASONS_DESCRIPTION = "Description";
	/****************************************************
	 * tmp_vend_dstr_payment
	 ********************************************************************************************/
	public static final String COLUMN_PAYMENTID = "Pay_Id";
	public static final String COLUMN_PAYMENT_VENDORNAME = "Vend_Nm";
	public static final String COLUMN_PAYMENT_REASONS_OF_PAY = "Reason_Of_Pay";
	public static final String COLUMN_PAYMENT_AMOUNT = "Amount";
	public static final String COLUMN_PAYMENT_BANKNAME = "Bank_Name";
	public static final String COLUMN_PAYMENT_CHEQUENUMBER = "Cheque_No";
	/****************************************************
	 * bankdetails
	 ********************************************************************************************/
	public static final String COLUMN_BANKNAME = "Bank_Name";

	/****************************************************
	 * Store data into Retail_str_po_detail
	 ********************************************************************************************/
	public static final String COLUMN_DETAIL_PO_NO = "Po_No";
	public static final String COLUMN_DETAIL_PROD_ID = "Prod_Id";
	public static final String COLUMN_DETAIL_PROD_NAME = "Prod_Nm";
	public static final String COLUMN_DETAIL_PPRICE = "P_Price";
	public static final String COLUMN_DETAIL_QTY = "Qty";
	public static final String COLUMN_DETAIL_TOTAL = "Total";
	public static final String COLUMN_DETAIL_AMOUNT = "Amount";


	public static final String COLUMN_DETAIL_UOM = "Uom";
	public static final String COLUMN_DETAIL_MRP = "MRP";
	public static final String COLUMN_DETAIL_SPRICE = "S_Price";

	//***********************************Employees Table****************************************************************
	private static final String TABLE_NAME_EMPLOYEES = "retail_employees";
	public static final String STORE_ID_EMP = "Store_Id";
	public static final String USER_NAME = "Usr_Nm";
	public static final String FIRST_NAME = "First_Name";
	public static final String LAST_NAME = "Last_Name";
	public static final String PASSWORD = "Password";
	public static final String CONFIRMPASSWORD = "Confirm_Password";
	public static final String ACTIVE = "Active";
	//****************************retail_store**************************************
	private static final String TABLE_NAME_STORE = "retail_store";
	public static final String STORE_ID_STORE = "Store_Id";
	//public static final String COLUMN_MASTER_GRNID ="Grn_Id";
	//**************************************retail_comp_btl***********************************************************
	private static final String COLOUM_STORE_ID_COMP = "Store_Id";
	private static final String TABLE_NAME_COMP = "retail_comp_btl";
	public static final String COLOUM_COMP_NM = "Comp_Nm";
	public static final String COLOUM_COMP_TARGET_AMOUNT = "Target_Amount";
	public static final String COLOUM_COMP_BTL_DESC = "Btl_Desc";
	public static final String COLOUM_COMP_TARGET_VALUE = "Target_Value";
	public static final String COLOUM_COMP_START_DATE = "Start_date";
	public static final String COLOUM_COMP_END_DATE = "End_Date";
	//**************************************retail_mfg_btl***********************************************************
	private static final String TABLE_NAME_MFG = "retail_mfg_btl";
	public static final String COLOUM_MFG_NM = "Mfg_Nm";
	public static final String COLOUM_MFG_TARGET_AMOUNT = "Target_Amount";
	public static final String COLOUM_MFG_BTL_DESC = "Btl_Desc";
	public static final String COLOUM_MFG_TARGET_VALUE = "Target_Value";
	public static final String COLOUM_MFG_START_DATE = "Start_date";
	public static final String COLOUM_MFG_END_DATE = "End_Date";
	/*********************************************************
	 * Retail_str_grn_master
	 ********************************************************************************/
	public static final String COLUMN_MASTER_GRNID = "Grn_Id";
	public static final String COLUMN_MASTER_GRN_VENDORNAME = "Vend_Nm";
	public static final String COLUMN_MASTER_GRN_GRAND_AMOUNT = "Grand_Amount";

	/**************************
	 * code from rahul for stock maaster only***********************Get the data from retail_str_stock_master
	 ***********************************************************************************************/
	//public static final String COLUMN_STOCK_GRNID="Grn_Id";
	public static final String COLUMN_STOCK_PROD_ID = "Prod_Id";
	public static final String COLUMN_STOCK_PROD_NAME = "Prod_Nm";
	public static final String COLUMN_STOCK_BATCHNO = "Batch_No";
	public static final String COLUMN_STOCK_EXP_DATE = "Exp_Date";
	public static final String COLUMN_STOCK_QTY = "Qty";
	public static final String COLUMN_STOCK_MRP = "MRP";
	public static final String COLUMN_STOCK_AMOUNT = "Amount";
	public static final String COLUMN_STOCK_UOM = "Uom";
	public static final String COLUMN_STOCK_PPRICE = "P_Price";
	public static final String COLUMN_STOCK_SPRICE = "S_Price";
	public static final String IMEI = "IMEI_Number";
	public static final String STOCK = "Stock";
	public static final String COLUMN_STOCK_BARCODE = "Barcode";
	public static final String PREFIX = "Prefix";
	public static final String COLUMN_TOP_PRODUCT_NAME = "Prod_Nm";
	public static final String COLUMN_TOP_PRODUCT_ID = "Prod_Id";
	public static final String COLUMN_FREE_GOODS = "Free_Goods";
	//	********************************************************************************/
	public static final String DOCTORNAME = "dr_name";
	public static final String DOCTORSPECIALITY = "Speciality";
	public static final String DOCTORQUAL = "dr_qualification";
	/***************************************************************
	 * retail_top_product
	 **************************************************************/
	public static final String TOP_PRODUCT_NAME = "Prod_Nm";
	public static final String TOP_PRODUCT_SORT_NAME = "Prod_Short_Nm";
	///////////////////////////////////////retail_store_prod_cpg///////////////////////////////////////////////////
	public static final String COLUMN_REPRT_CPG_ID = "Prod_Id";
	public static final String COLUMN_REPRT_CPG_NM = "Prod_Nm";
	public static final String COLUMN_REPRT_CPG_CODE = "BarCode";
	public static final String COLUMN_REPRT_CPG_MRP = "MRP";
	public static final String COLUMN_REPRT_CPG_S_PRICE = "S_Price";
	public static final String COLUMN_REPRT_CPG_P_PRICE = "P_Price";
	public static final String COLUMN_REPRT_CPG_ACTIVE = "Active";
	public static final String COLUMN_REPRT_CPG_MARGIN = "Profit_Margin";

	////////////////////////////////////////retail_store_prod_local_cpg///////////////////////////////////////////////////

	public static final String COLUMN_REPRT_LOCAL_CPG_ID = "Prod_Id";
	public static final String COLUMN_REPRT_LOCAL_CPG_NM = "Prod_Nm";
	public static final String COLUMN_REPRT_LOCAL_CPG_CODE = "BarCode";
	public static final String COLUMN_REPRT_LOCAL_CPG_MRP = "MRP";
	public static final String COLUMN_REPRT_LOCAL_CPG_S_PRICE = "S_Price";
	public static final String COLUMN_REPRT_LOCAL_CPG_P_PRICE = "P_Price";
	public static final String COLUMN_REPRT_LOCAL_CPG_ACTIVE = "Active";
	public static final String COLUMN_REPRT_LOCAL_CPG_MARGIN = "Profit_Margin";


	////////////////////////////////////////retail_str_sales_detail///////////////////////////////////////////////////

	public static final String COLUMN_REPRT_SALE_ID = "Tri_Id";
	public static final String COLUMN_REPRT_SALE_NO = "Batch_No";
	public static final String COLUMN_REPRT_SALE_NM = "Prod_Nm";
	public static final String COLUMN_REPRT_SALE_DATE = "Exp_Date";
	public static final String COLUMN_REPRT_SALE_PRICE = "S_Price";
	public static final String COLUMN_REPRT_SALE_QTY = "Qty";
	public static final String COLUMN_REPRT_SALE_MRP = "MRP";
	public static final String COLUMN_REPRT_SALE_TOTAL = "Total";
	public static final String COLUMN_REPRT_SALE_UOM = "Uom";

	////////////////////////////////////////retail_str_sales_detail/////////////////////////////////////////////

	public static final String COLUMN_REPRT_SALE_SDATE = "Sale_Date";

	////////////////////////////////////////retail_str_sales_details_return///////////////////////////////////////////////////

	public static final String COLUMN_REPRT_SALE_RETURN_ID = "Tri_Id";
	public static final String COLUMN_REPRT_SALE_RETURN_NO = "Batch_No";
	public static final String COLUMN_REPRT_SALE_RETURN_NM = "Prod_Nm";
	public static final String COLUMN_REPRT_SALE_RETURN_DATE = "Exp_Date";
	public static final String COLUMN_REPRT_SALE_RETURN_PRICE = "S_Price";
	public static final String COLUMN_REPRT_SALE_RETURN_QTY = "Qty";
	public static final String COLUMN_REPRT_SALE_RETURN_MRP = "MRP";
	public static final String COLUMN_REPRT_SALE_RETURN_GRANDTOTAL = "Grand_Total";
	public static final String COLUMN_REPRT_SALE_RETURN_UOM = "Uom";
	public static final String COLUMN_REPRT_SALE_RETURN_REASON = "Reason_Of_Return";


	/**************************************************
	 * tmp_vend_str_stock_master_hold
	 **************************************/


	public static final String COLUMN_DETAIL_BATCHNO = "Batch_No";


	public static final String COLUMN_DETAIL_EXPDATE = "Exp_Date";


	public static final String COLUMN_MASTER_FLAG = "Flag";

	public static final String COLUMN_MASTER_GRN_NO = "Grn_Id";

	public static final String COLUMN_VENDOR_RETURNID = "Vendor_Return_Id";


	public static final String COLUMN_REPRT_VENDDSTR_NM = "Vend_Dstr_Nm";
	public static final String COLUMN_REPRT_PAY_ID = "Pay_Id";
	public static final String COLUMN_REPRT_RCVD_AMNT = "Received_Amount";
	public static final String COLUMN_REPRT_DUE_AMNT = "Due_Amount";
	public static final String COLUMN_REPRT_PAID_AMNT = "Amount";
	public static final String COLUMN_REPRT_BANK_NM = "Bank_Name";
	public static final String COLUMN_REPRT_CHEQUE_NO = "Cheque_No";
	public static final String COLUMN_REPRT_REASN_OF_PAY = "Reason_Of_Pay";
	public static final String COLUMN_REPRT_LAST_UPDTE = "LastUpdate";//
	public static final String COLUMN_DOCTOR_SPECIAL = "Speciality";


	/******************************** ******************************************************************/
	/********************************
	 * Customer Rejection
	 ************************************************/
	private static final String TABLE_NAME_CUSTOMER_REJECTION = "retail_store_sales_desc";
	public static final String REJECTIONREASON = "Reason_Return";
	public static final String REJECTIONID = "Id";
	public static final String REJECTIONLASTUP = "LastUpdate";



	/********************************Vendor Rejection************************************************/

	private static final String TABLE_NAME_VENDOR_REJECTION = "retail_store_vend_reject";
	public static final String REJECTREASON = "Reason_for_Rejection";
	public static final String REJECTID ="Id";


	/*************************************retail_str_bill_lvl_disc*************************************************/
	public static final String BILLLEVELNAME = "bill_lvl_name";




	public static final String TABLE_NAME_TOP_PRODUCT ="retail_top_product";
	public static final String TOP_PRODUCT_NAME_TOP = "Prod_Nm";



	public static final String LINEITEMNAME = "Line_Item_Nm";

	public static final String LINEITEMTABLENAME ="retail_str_lineitem_disc";



	//*****************************************************************************************************************


	//*****************************************************************************************************************


	private static final String TAG = "MyActivity";

	@SuppressLint("NewApi")
	public DBhelper(Context context) {

		super(context, DB_PATH + DATABASE_NAME, null, 1);
		this.mycontext = context;
	}

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			//do nothing - database already exist
		} else {

			//By calling this method and empty database will be created into the default system path
			//of your application so we are gonna be able to overwrite that database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each time you open the application.
	 *
	 * @return true if it exists, false if it doesn't
	 */
	public boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			//database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created empty database in the
	 * system folder, from where it can be accessed and handled.
	 * This is done by transfering bytestream.
	 */
	private void copyDataBase() throws IOException {

		//Open your local db as the input stream
		InputStream myInput = mycontext.getAssets().open(DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DATABASE_NAME;

		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		//Open the database
		String myPath = DB_PATH + DATABASE_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
		db.execSQL("DROP TABLE IF EXISTS");
		onCreate(db);
	}

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!********** CODE TO FETCH ALL DATA *************!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

// !!!!!!!!*********Code to get all Customers*******!!!!!!!!!!

	public ArrayList<Customer> getAllCustomers(String nameOrPhone) {
		ArrayList<Customer> customerlist = new ArrayList<Customer>();
		SQLiteDatabase db = this.getReadableDatabase();

		try {
			String[] params = new String[2];
			params[0] = nameOrPhone + "%";
			params[1] = nameOrPhone + "%";
			Cursor res = db.rawQuery("select Mobile_No,name,e_mail,Credit_Cust from retail_cust where"
							+ " Mobile_No  like ? or  name like ? "
					, params);
			if (null != res && res.moveToFirst() && res.getCount() > 0) {
				do {
					Customer customer = new Customer();
					customer.setCustomermobileno(res.getString(res.getColumnIndex(CUSTOMERMOBILENO)));
					customer.setCustomername(res.getString(res.getColumnIndex(CUSTOMERNAME)));
					customer.setCustomeremail(res.getString(res.getColumnIndex(CUSTOMEREMAIL)));
					//customer.setCreditcust(res.getString(res.getColumnIndex(CUSTOMERCREDIT)));
					customerlist.add(customer);
					//customerlist.add(res.getString(res.getColumnIndex(CUSTOMERMOBILENO)));
				} while (res.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
		}

		db.close();
		return customerlist;
	}


	// !!!!!!!!!**********Code to get all Products******!!!!!!!!
	public ArrayList<Product> getAllProducts(String nameOrPhoneOrNo) {
		ArrayList<Product> Productlist = new ArrayList<Product>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[3];
			params[0] = nameOrPhoneOrNo + "%";
			params[1] = nameOrPhoneOrNo + "%";
			params[2] = nameOrPhoneOrNo + "%";
			Cursor res = db.rawQuery("select distinct Prod_Nm,Store_Id,Prod_Id,BarCode,MRP,S_Price,P_Price,TAX_Desc,Purch_Order_Unit,Mfg,Selling_order_Unit,Internet_Price,Is_Prod_Rel_Int,Active ,Profit_Margin from retail_store_prod where"
					+ " Prod_Nm  like ? or Prod_Id like ? or BarCode like ?", params);

			if (res.moveToFirst())
				Log.e(TAG, "getTableAsString called");
			{
				do {
					Product product = new Product();
					product.setStoreId(res.getString(res.getColumnIndex(PRODUCTSTOREID)));
					product.setProductId(res.getString(res.getColumnIndex(PRODUCTPRODUCTID)));
					product.setProductName(res.getString(res.getColumnIndex(PRODUCTNAME)));
					product.setMRP(res.getString(res.getColumnIndex(PRODUCTMRP)));
					//	product.setMeasure(res.getString(res.getColumnIndex(PRODUCTMEASURE)));
					product.setInternet(res.getString(res.getColumnIndex(PRODUCTINTERNET)));
					//	product.setMeasureUnit(res.getString(res.getColumnIndex(PRODUCTMEASURE)));
					product.setMeasureUnit(res.getString(res.getColumnIndex(PRODUCTMEASUREUNITINV)));
					product.setPackingUnit1(res.getString(res.getColumnIndex(PRODUCTPACKINGUNIT)));
					product.setPurchaseprice(res.getString(res.getColumnIndex(PRODUCTPURCHASE)));
					product.setSellingprice(res.getString(res.getColumnIndex(PRODUCTSELLING)));
					product.setManuf(res.getString(res.getColumnIndex(PRODUCTMANUF)));
					product.setProductBarcode(res.getString(res.getColumnIndex(PRODUCTBARCODE)));
					//	product.setStrength(res.getString(res.getColumnIndex(PRODUCTSTRENGTH)));
					product.setTaxid(res.getString(res.getColumnIndex(PRODUCTTAXDESC)));
					product.setInternetrelevant(res.getString(res.getColumnIndex(PRODUCTRELEVANT)));
					product.setActive(res.getString(res.getColumnIndex(PRODUCTACTIVE)));
					product.setMargin(res.getString(res.getColumnIndex(PRODUCTMARGIN)));
					Productlist.add(product);


				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return Productlist;
	}

	// !!!!!!!!!**********Code to get all Products******!!!!!!!!
	public ArrayList<Product> getAllProductsCPG(String nameOrPhoneOrNo) {
		ArrayList<Product> Productlist = new ArrayList<Product>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[3];
			params[0] = nameOrPhoneOrNo + "%";
			params[1] = nameOrPhoneOrNo + "%";
			params[2] = nameOrPhoneOrNo + "%";
			Cursor res = db.rawQuery("select  distinct Store_Id,Prod_Id,Prod_Nm,BarCode,MRP,S_Price,P_Price,Selling_order_Unit,Internet_Price,Is_Prod_Rel_Int,Active,Profit_Margin  from retail_store_prod_cpg where"
					+ " Prod_Nm  like ? or Prod_Id like ? or BarCode like ?", params);

			if (res.moveToFirst())
				Log.e(TAG, "getTableAsString called");
			{
				do {
					Product product = new Product();
					product.setStoreId(res.getString(res.getColumnIndex(PRODUCTSTOREID)));
					product.setProductId(res.getString(res.getColumnIndex(PRODUCTPRODUCTID)));
					product.setProductName(res.getString(res.getColumnIndex(PRODUCTNAME)));
					product.setMRP(res.getString(res.getColumnIndex(PRODUCTMRP)));
					//	product.setMeasure(res.getString(res.getColumnIndex(PRODUCTMEASURE)));
					product.setInternet(res.getString(res.getColumnIndex(PRODUCTINTERNET)));
					//	product.setMeasureUnit(res.getString(res.getColumnIndex(PRODUCTMEASURE)));
					product.setMeasureUnit(res.getString(res.getColumnIndex(PRODUCTMEASUREUNITINV)));
					//product.setPackingUnit1(res.getString(res.getColumnIndex(PRODUCTPACKINGUNIT)));
					product.setPurchaseprice(res.getString(res.getColumnIndex(PRODUCTPURCHASE)));
					product.setSellingprice(res.getString(res.getColumnIndex(PRODUCTSELLING)));
					//product.setManuf(res.getString(res.getColumnIndex(PRODUCTMANUF)));
					product.setProductBarcode(res.getString(res.getColumnIndex(PRODUCTBARCODE)));
					//	product.setStrength(res.getString(res.getColumnIndex(PRODUCTSTRENGTH)));
					//product.setTaxid(res.getString(res.getColumnIndex(PRODUCTTAXDESC)));
					product.setInternetrelevant(res.getString(res.getColumnIndex(PRODUCTRELEVANT)));
					product.setActive(res.getString(res.getColumnIndex(PRODUCTACTIVE)));
					Productlist.add(product);


				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return Productlist;
	}

	//!!!!!!!!!!**********Code to get all Stores********!!!!!!!!!!!!!!
	public ArrayList<String> getAllStores() {
		ArrayList<String> storelist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_store ", null);
		if (res.moveToFirst())
			Log.e(TAG, "getTableAsString called");
		{
			do {
				storelist.add(res.getString(res.getColumnIndex(STORE_ID)));
			} while (res.moveToNext());
		}

		return storelist;
	}

	//!!!!!!!!!!!!!!!!!!!!************Code to get maintainence********!!!!!!!!!!!!!!!!
	public ArrayList<ShowModel> getAlldata() {
		ArrayList<ShowModel> array_list = new ArrayList<ShowModel>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cur = db.rawQuery("select * from retail_store_maint", null);
		if (cur.moveToFirst()) {
			do {
				ShowModel sm = new ShowModel();
				sm.setTICKET_ID(cur.getString(cur.getColumnIndex(COLUMN_TICKET_ID)));
				sm.setSUBJECT_DESC(cur.getString(cur.getColumnIndex(COLUMN_SUBJECT_DESC)));
				sm.setSUPPORT_TICKET_STATUS(cur.getString(cur.getColumnIndex(COLUMN_SUPPORT_TICKET_STATUS)));
				sm.setTEAM_MEMBER(cur.getString(cur.getColumnIndex(COLUMN_TEAM_MEMBER)));
				sm.setTimeStamp(cur.getString(cur.getColumnIndex(COLUMN_DATE)));
				array_list.add(sm);
			} while (cur.moveToNext());
		}
		return array_list;
	}

	//!!!!!!!!!!!!!!!!!!!!************Code to get Vendor********!!!!!!!!!!!!!!!!
	public ArrayList<Vendor> getAllVendor(String nameOrPhone) {

		ArrayList<Vendor> vendorlist = new ArrayList<Vendor>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = nameOrPhone + "%";
			params[1] = nameOrPhone + "%";
			Cursor res = db.rawQuery("select Dstr_Id,Dstr_Nm,Dstr_Cntct_Nm,Add_1,City,Ctr_Nm,Zip,Tele,Mobile ,Dstr_Inv, Active,Email from retail_str_dstr where "
					+ " Dstr_Nm like ? or Dstr_Id like ?", params);
			if (res.moveToFirst()) {
				do {
					Vendor vendor = new Vendor();
					vendor.setVendorId(res.getString(res.getColumnIndex(VENDORID)));
					vendor.setVendorname(res.getString(res.getColumnIndex(VENDORNAME)));
					vendor.setVendorContact(res.getString(res.getColumnIndex(VENDORCONTACTNAME)));
					vendor.setAddress(res.getString(res.getColumnIndex(VENDORADDRESS)));
					vendor.setCity(res.getString(res.getColumnIndex(VENDORCITY)));
					vendor.setCountry(res.getString(res.getColumnIndex(VENDORCOUNTRY)));
					vendor.setMobile(res.getString(res.getColumnIndex(VENDORMOBILE)));
					vendor.setTelephone(res.getString(res.getColumnIndex(VENDORTTELEPHONE)));
					vendor.setZip(res.getString(res.getColumnIndex(VENDORZIP)));
					vendor.setVendorInventory(res.getString(res.getColumnIndex(VENDORINVENTORY)));
					vendor.setActive(res.getString(res.getColumnIndex(VENDORACTIVE)));
					vendor.setEmail(res.getString(res.getColumnIndex(VENDOREmail)));
					vendorlist.add(vendor);
				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return vendorlist;
	}

	//!!!!!!!!!!!!!!!!!!!!************Code to get localVendor********!!!!!!!!!!!!!!!!
	public ArrayList<localvendor> getAlllocalVendor(String nameOrPhone) {
		ArrayList<localvendor> localvendorlist = new ArrayList<localvendor>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = nameOrPhone + "%";
			params[1] = nameOrPhone + "%";
			Cursor res = db.rawQuery("select  DISTINCT Store_Id, Vend_Id, Vend_Nm,Vend_cntct_Nm,Add_1,City,Ctr_Desc,Zip,Tele,Mobile,Active,Vend_Inv from retail_store_vendor where "
					+ " Vend_Nm like ? or  Vend_Id like ?", params);
			if (res.moveToFirst()) {
				do {
					localvendor localvendor = new localvendor();

					localvendor.setLocalvendorid(res.getString(res.getColumnIndex(LOCALVENDORID)));
					localvendor.setLocalvendorname(res.getString(res.getColumnIndex(LOCALVENDORNAME)));
					localvendor.setLocalvendoraddress(res.getString(res.getColumnIndex(LOCALVENDORADDRESS)));
					localvendor.setLocalvendorcity(res.getString(res.getColumnIndex(LOCALVENDORCITY)));
					localvendor.setLocalvendorcontactname(res.getString(res.getColumnIndex(LOCALVENDORCONTACT)));
					localvendor.setLocalvendormobile(res.getString(res.getColumnIndex(LOCALVENDORMOBILE)));
					localvendor.setLocalvendortele(res.getString(res.getColumnIndex(LOCALVENDORTELE)));
					localvendor.setLocalvendorcountry(res.getString(res.getColumnIndex(LOCALVENDORCOUNTRY)));
					localvendor.setLocalvendorzip(res.getString(res.getColumnIndex(LOCALVENDORZIP)));
					localvendor.setLocalactive(res.getString(res.getColumnIndex(LOCALVENDORACTIVE)));
					localvendor.setLocalvendorinventory(res.getString(res.getColumnIndex(LOCALVENDORINVENTORY)));
					localvendorlist.add(localvendor);
				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return localvendorlist;
	}

	//!!!!!!!!!!!!!!!!!!!!************Code to get Local product********!!!!!!!!!!!!!!!!
	public ArrayList<LocalProduct> getAllLocalProduct(String nameOrPhone) {

		ArrayList<LocalProduct> localproductlist = new ArrayList<LocalProduct>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = nameOrPhone + "%";
			//params[1] = nameOrPhone + "%";
			Cursor res = db.rawQuery("select  Store_Id,Prod_Id,Prod_Nm,BarCode,MRP,S_Price,P_Price,Tax_Id,Active,Profit_Margin from retail_store_prod_local where "
					+ " Prod_Nm  like ? or Prod_Id like ?", params);
			if (res.moveToFirst()) {
				do {
					LocalProduct localProduct = new LocalProduct();
					localProduct.setStoreid(res.getString(res.getColumnIndex(PRODUCTLOCALSTOREID)));
					localProduct.setProductid(res.getString(res.getColumnIndex(PRODUCTLOCALPRODUCTID)));
					localProduct.setProductname(res.getString(res.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					localProduct.setBarcode(res.getString(res.getColumnIndex(PRODUCTLOCALBARCODE)));
					localProduct.setMRP(res.getString(res.getColumnIndex(PRODUCTLOCALMRP)));
					localProduct.setPurchasePrice(res.getString(res.getColumnIndex(PRODUCTLOCALPURCHASE)));
					localProduct.setSellingPrice(res.getString(res.getColumnIndex(PRODUCTLOCALSELLING)));
					localProduct.setBarcode(res.getString(res.getColumnIndex(PRODUCTLOCALBARCODE)));
					localProduct.setTaxid(res.getString(res.getColumnIndex(PRODUCTLOCALTAXID)));
					localProduct.setActive(res.getString(res.getColumnIndex(PRODUCTLOCALACTIVE)));
					localProduct.setMargin(res.getString(res.getColumnIndex(PRODUCTLOCALMARGIN)));

					localproductlist.add(localProduct);
				} while (res.moveToNext());

			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return localproductlist;
	}

	///##########################################local product cpg#################################
	public ArrayList<LocalProduct> getAllLocalProductCpg(String nameOrPhone) {

		ArrayList<LocalProduct> localproductlist = new ArrayList<LocalProduct>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = nameOrPhone + "%";
			//params[1] = nameOrPhone + "%";
			Cursor res = db.rawQuery("select  Store_Id,Prod_Id,Prod_Nm,BarCode,MRP,S_Price,P_Price,Tax_Id,Active from retail_store_prod_local_cpg where "
					+ " Prod_Nm  like ? or Prod_Id like ?", params);
			if (res.moveToFirst()) {
				do {
					LocalProduct localProduct = new LocalProduct();
					localProduct.setStoreid(res.getString(res.getColumnIndex(PRODUCTLOCALSTOREID)));
					localProduct.setProductid(res.getString(res.getColumnIndex(PRODUCTLOCALPRODUCTID)));
					localProduct.setProductname(res.getString(res.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					localProduct.setBarcode(res.getString(res.getColumnIndex(PRODUCTLOCALBARCODE)));
					localProduct.setMRP(res.getString(res.getColumnIndex(PRODUCTLOCALMRP)));
					localProduct.setPurchasePrice(res.getString(res.getColumnIndex(PRODUCTLOCALPURCHASE)));
					localProduct.setSellingPrice(res.getString(res.getColumnIndex(PRODUCTLOCALSELLING)));
					localProduct.setBarcode(res.getString(res.getColumnIndex(PRODUCTLOCALBARCODE)));
					localProduct.setTaxid(res.getString(res.getColumnIndex(PRODUCTLOCALTAXID)));
					localProduct.setActive(res.getString(res.getColumnIndex(PRODUCTLOCALACTIVE)));

					localproductlist.add(localProduct);
				} while (res.moveToNext());

			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return localproductlist;
	}


	//!!!!!!!!!!!!!!!!!!!!************Code to get Tax********!!!!!!!!!!!!!!!!
	public ArrayList<Tax> getAllTax() {
		ArrayList<Tax> taxlist = new ArrayList<Tax>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_tax", null);
		if (res.moveToFirst())
			Log.e(TAG, "getTableAsString called");
		{
			do {
				//namelist.add(res.getString(res.getColumnIndex(CUSTOMER_NAME)));
				Tax tax = new Tax();
				tax.setTAX_DESCRIPTION(res.getString(res.getColumnIndex(TAX_DESCRIPTION)));
				tax.setTAX_RATE(res.getString(res.getColumnIndex(TAX_RATE)));
				taxlist.add(tax);

			} while (res.moveToNext());
		}

		return taxlist;
	}

	/************
	 * get data from mobile internet
	 ***********/
	public ArrayList<String> getMobileinternet() {

		ArrayList<String> array_list = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from retail_str_bill_details_internet", null);

		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {

			array_list.add(cursor.getString(cursor.getColumnIndex(COLUMN_MOBILENO)));
			cursor.moveToNext();
		}
		return array_list;
	}

	/************
	 * get data from retail_ad_Ticker
	 ***********/
	public ArrayList<ListModel> getReportData() {
		ArrayList<ListModel> arrayList = new ArrayList<ListModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select Ad_Main_Id,Ad_Text,Ad_Cst_Slb1,Ad_Cst_Slb2,Ad_Cst_Slb3 ,Status,Active from retail_ad_ticker where Status Like 'A%'and Active Like 'Y%' ", null);
		if (cur.moveToFirst()) {
			do {
				Log.e("***Db*arrayList*** ", arrayList.toString());
				ListModel lm = new ListModel();
				lm.setAD_MAIN_ID(cur.getString(cur.getColumnIndex(COLUMN_AD_MAIN_ID)));
				lm.setAD_TEXT(cur.getString(cur.getColumnIndex(COLUMN_AD_TEXT)));
			/*lm.setSTR_ID(cur.getString(cur.getColumnIndex(COLUMN_STR_ID)));*/
			/*lm.setAD_START_DATE(cur.getString(cur.getColumnIndex(COLUMN_AD_START_DATE)));
			lm.setAD_END_DATE(cur.getString(cur.getColumnIndex(COLUMN_AD_END_DATE)));*/
				lm.setAD_CST_SLB1(cur.getString(cur.getColumnIndex(COLUMN_AD_CST_SLB1)));
				lm.setAD_CST_SLB2(cur.getString(cur.getColumnIndex(COLUMN_AD_CST_SLB2)));
				lm.setAD_CST_SLB3(cur.getString(cur.getColumnIndex(COLUMN_AD_CST_SLB3)));

				arrayList.add(lm);
			}
			while (cur.moveToNext());
		}
		return arrayList;
	}

	/************
	 * Get data from BLINkING
	 ***********/
	public ArrayList<BlinkingModel> getBlinkinglogoData() {
		ArrayList<BlinkingModel> arraylist = new ArrayList<BlinkingModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select Ad_Main_Id,Ad_Desc,Ad_Cst_Slb1,Ad_Cst_Slb2,Ad_Cst_Slb3 ,Status,Active from retail_ad_blinkinglogo where Status Like 'A%'and Active Like 'Y%' ", null);
		if (cur.moveToFirst()) {
			do {
				Log.e("***Db*arrayList*** ", arraylist.toString());
				BlinkingModel bm = new BlinkingModel();
				bm.setAD_MAIN_ID(cur.getString(cur.getColumnIndex(COLUMN_StoreMain_AD_MAIN_ID)));
				bm.setAD_DESC(cur.getString(cur.getColumnIndex(COLUMN_StoreMain_AD_DESC)));
				bm.setAD_CST_SLB1(cur.getString(cur.getColumnIndex(COLUMN_Blinkinglogo_AD_CST_SLB1)));
				bm.setAD_CST_SLB2(cur.getString(cur.getColumnIndex(COLUMN_Blinkinglogo_AD_CST_SLB2)));
				bm.setAD_CST_SLB3(cur.getString(cur.getColumnIndex(COLUMN_Blinkinglogo_AD_CST_SLB3)));
				arraylist.add(bm);
			}
			while (cur.moveToNext());
		}
		return arraylist;

	}

	/************
	 * Get data from STORE NEWS
	 ***********/
	public ArrayList<StoreMainModel> getStoreMainData() {
		ArrayList<StoreMainModel> arraylist = new ArrayList<StoreMainModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select Ad_Main_Id,Ad_Desc,Ad_Cst_Slb1,Ad_Cst_Slb2,Ad_Cst_Slb3 ,Status,Active from retail_ad_Store_Main where Status Like 'A%'and Active Like 'Y%' ", null);

		if (cur.moveToFirst()) {
			do {
				Log.e("***Db*arrayList*** ", arraylist.toString());
				StoreMainModel sm = new StoreMainModel();
				sm.setAD_MAIN_ID(cur.getString(cur.getColumnIndex(COLUMN_Blinkinglogo_AD_MAIN_ID)));
				sm.setAD_DESC(cur.getString(cur.getColumnIndex(COLUMN_Blinkinglogo_AD_DESC)));
				sm.setAD_CST_SLB1(cur.getString(cur.getColumnIndex(COLUMN_StoreMain_AD_CST_SLB1)));
				sm.setAD_CST_SLB2(cur.getString(cur.getColumnIndex(COLUMN_StoreMain_AD_CST_SLB2)));
				sm.setAD_CST_SLB3(cur.getString(cur.getColumnIndex(COLUMN_StoreMain_AD_CST_SLB3)));
				arraylist.add(sm);
			}
			while (cur.moveToNext());
		}
		return arraylist;
	}

	//!!!!!!!!!!!!!!!!!!!!************Code to get Local product from Barcode********!!!!!!!!!!!!!!!!
	public ArrayList<LocalProduct> getLocalProductfromBarcode(String BarcodefromScanner) {

		ArrayList<LocalProduct> localproductlist = new ArrayList<LocalProduct>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = BarcodefromScanner + "%";
			Cursor res = db.rawQuery("select  Store_Id,Prod_Id,Prod_Nm,BarCode,MRP,S_Price,P_Price,Tax_Id,Active,Profit_Margin from retail_store_prod_local where "
					+ " BarCode  like ? ", params);
			if (res.moveToFirst()) {
				do {
					LocalProduct localProduct = new LocalProduct();
					localProduct.setStoreid(res.getString(res.getColumnIndex(PRODUCTLOCALSTOREID)));
					localProduct.setProductid(res.getString(res.getColumnIndex(PRODUCTLOCALPRODUCTID)));
					localProduct.setProductname(res.getString(res.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					localProduct.setBarcode(res.getString(res.getColumnIndex(PRODUCTLOCALBARCODE)));
					localProduct.setMRP(res.getString(res.getColumnIndex(PRODUCTLOCALMRP)));
					localProduct.setPurchasePrice(res.getString(res.getColumnIndex(PRODUCTLOCALPURCHASE)));
					localProduct.setSellingPrice(res.getString(res.getColumnIndex(PRODUCTLOCALSELLING)));
					localProduct.setBarcode(res.getString(res.getColumnIndex(PRODUCTLOCALBARCODE)));
					localProduct.setTaxid(res.getString(res.getColumnIndex(PRODUCTLOCALTAXID)));
					localProduct.setActive(res.getString(res.getColumnIndex(PRODUCTLOCALACTIVE)));
					localProduct.setMargin(res.getString(res.getColumnIndex(PRODUCTLOCALMARGIN)));
					localproductlist.add(localProduct);
				} while (res.moveToNext());

			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return localproductlist;
	}


	//!!!!!!!!!!!!!!!!!!!!************Code to get Single Tax********!!!!!!!!!!!!!!!!

	public Cursor getTax(String tax_id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_tax where tax_id=" + tax_id + "", null);
		return res;
	}
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!************* CODE TO FETCH SINGLE DATA ****************!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


	// !!!!!!!!!!**********Code to single Store******!!!!!!!!!!!!!!!
	public Store getStoreDetails() {

		Store store = null;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_store", null);
		if (res.moveToFirst()) {
			//do {
			store = new Store();
			store.setStoreId(res.getString(res.getColumnIndex(STORE_ID)));
			store.setStoreName(res.getString(res.getColumnIndex(STORE_NAME)));
			store.setStoreemail(res.getString(res.getColumnIndex(STORE_EMAIL)));
			store.setStoreTele(res.getString(res.getColumnIndex(STORE_MOBILE)));
			store.setStorezip(res.getString(res.getColumnIndex(STORE_ZIP)));
			store.setStorecity(res.getString(res.getColumnIndex(STORE_CITY)));
			store.setStorecontactname(res.getString(res.getColumnIndex(STORE_CONTACTNAME)));
			store.setStorecountry(res.getString(res.getColumnIndex(STORE_COUNTRY)));

			//} while (res.moveToNext());


		}
		return store;
	}
//	public  Cursor getStore(String Storeid){
//		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor res =  db.rawQuery( "select Store_Id from retail_store where Store_Id=" + Storeid + "",null) ;
//		return res;
//	}


	// !!!!!!!!!!**********Code to Store for Day open******!!!!!!!!!!!!!!!
	public Cursor getStoreDay() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select Store_Id from retail_store where Store_Id = Store_Id", null);
		return res;
	}
	//!!!!!!!!!!!**********Code to get single maintainence******!!!!!!!

	public Cursor getData(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_store_maint where Ticket_id=" + id + "", null);
		return res;
	}

	//!!!!!!!!!!!**********Code to get single internet detail******!!!!!!!
	public Cursor getBillReport(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor ress = db.rawQuery("select * from retail_str_bill_details_internet where Mobile_No=" + id + "", null);
		return ress;
	}

	public void updateRecord(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("update retail_str_bill_details_internet set Status = 'Delivered' where orderid=" + id + "");
		db.close();
	}
	//!!!!!!!!!!!**********Code to get single localproductlist******!!!!!!!

	public Cursor getlocalproduct(String nameOrPhone) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = nameOrPhone + "%";
		Cursor res = db.rawQuery("select  Store_Id,Prod_Id,Prod_Nm,BarCode,MRP,S_Price,P_Price,TAX_Id from retail_store_prod_local where "
				+ " Prod_Nm  like ?", params);
		return res;
	}

	//.......!!!!!! LOGIN GET DATA METHOD TO VALIDATE !!!!!!.........//

	public ArrayList<String> getData() {
		ArrayList<String> namelist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			Cursor res = db.rawQuery("select * from retail_employees", null);
			if (res.moveToFirst())
				Log.e(TAG, "getTableAsString called");
			{
				do {
					namelist.add(res.getString(res.getColumnIndex(Userid)));
				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return namelist;
	}

	// !!!!!!!!!!!!!*********VALIDATE*****!!!!!!!!!!!!!!!
	public boolean validateUser(String username, String password) {
		Cursor c = getReadableDatabase().rawQuery(
				"SELECT * FROM " + TABLE_NAME2 + " WHERE "
						+ Loginpo.User.USER_NAME + "='" + username + "'AND " + Loginpo.User.PASS + "='" + password + "'", null);
		if (c.getCount() > 0)
			return true;
		return false;
	}

	// !!!!!!!!!!!!!*********INSERT CUSTOMER*****!!!!!!!!!!!!!!!
	public boolean insertCustomer(Customer customer) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Cust_Id", getSystemCurrentTime());
		contentValues.put("mobile_no", customer.getCustomermobileno());
		contentValues.put("name", customer.getCustomername());
		contentValues.put("e_mail", customer.getCustomeremail());
		contentValues.put("password", customer.getCustomermobileno());
		db.insert("retail_cust", null, contentValues);
		return true;
	}

	// !!!!!!!!!!!!!*********INSERT DOCTOR****!!!!!!!!!!!!!!!
	public boolean insertDoctor(DoctorPojo doctorPojo) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("dr_id", getSystemCurrentTime());
		contentValues.put("dr_name", doctorPojo.getDoctorName());
		contentValues.put("dr_address", doctorPojo.getDoctorSpeciality());
		contentValues.put("Speciality", doctorPojo.getDoctorQualification());
		//contentValues.put("password", customer.getCustomermobileno());
		db.insert("dr_discription", null, contentValues);
		return true;
	}


//*******************!!!!!! SYSTEM CURRENTTIME IN MILISECOND CODE USED IN TABLES!!!!**************

	String getSystemCurrentTime() {

		Long value = System.currentTimeMillis();
		String result = Long.toString(value);
		return result;
	}

//*******************!!!!!! SYSTEM CURRENTTIME IN MILISECOND CODE USED IN SALESRE!!!!**************

	String getSystemCurrentTimeinsalesreturnwithin() {

		Long value = System.currentTimeMillis() + 1000;
		String result = Long.toString(value);
		return result;
	}

	//!!!!!!!!!!!************UPDATE STORE************!!!!!!!!!!!!!!!!!!!!!!!!!
	public boolean updateStore(String STORE_ID, String MOBILE_NO) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Tele", MOBILE_NO);
		db.update("retail_store", contentValues, "Store_Id = ? ", new String[]{String.valueOf(STORE_ID)});
		return true;
	}

	//!!!!!!!!!!!************UPDATE PRODUCT************!!!!!!!!!!!!!!!!!!!!!!!!!
	public boolean updateProduct(String PRODUCTPRODUCTID, String PRODUCTSELLING, String PRODUCTPURCHASE, String PRODUCTINTERNET, String PRODUCTRELEVANT, String ACTIVE, String MARGIN) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("S_Price", PRODUCTSELLING);
		contentValues.put("P_Price", PRODUCTPURCHASE);
		contentValues.put("Internet_Price", PRODUCTINTERNET);
		contentValues.put("Is_Prod_Rel_Int", PRODUCTRELEVANT);
		contentValues.put("Active", ACTIVE);
		contentValues.put("Profit_Margin", MARGIN);
		db.update("retail_store_prod", contentValues, "Prod_Id = ? ", new String[]{String.valueOf(PRODUCTPRODUCTID)});
		return true;
	}

	//!!!!!!!!!!!************UPDATE LOCALPRODUCT************!!!!!!!!!!!!!!!!!!!!!!!!!
	public boolean updatelocalProduct(String PRODUCTLOCALPRODUCTID, String PRODUCTLOCALPRODUCTNAME, String PRODUCTLOCALBARCODE, String PRODUCTLOCALMRP, String PRODUCTLOCALSELLING, String PRODUCTLOCALPURCHASE, String ACTIVE, String MARGIN) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Prod_Id", PRODUCTLOCALPRODUCTID);
		contentValues.put("Prod_Nm", PRODUCTLOCALPRODUCTNAME);
		contentValues.put("BarCode", PRODUCTLOCALBARCODE);
		contentValues.put("MRP", PRODUCTLOCALMRP);
		contentValues.put("S_Price", PRODUCTLOCALSELLING);
		contentValues.put("P_Price", PRODUCTLOCALPURCHASE);
		contentValues.put("Active", ACTIVE);
		contentValues.put("Profit_Margin", MARGIN);
		db.update("retail_store_prod_local", contentValues, "Prod_Id = ? ", new String[]{String.valueOf(PRODUCTLOCALPRODUCTID)});
		return true;


	}

	public boolean updateVendor(String VENDORID, String VENDORACTIVE) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("Active", VENDORACTIVE);

		db.update("retail_str_dstr", contentValues, "Dstr_Id = ? ", new String[]{String.valueOf(VENDORID)});
		return true;
	}

	//************************!!!!!UPDATE LOCAL VENDOR!!!!!!!*****************************************
 public boolean updatelocalVendor(String LOCALVENDORID, String LOCALVENDORNAME, String LOCALVENDORCONTACT, String LOCALVENDORADDRESS,String LOCALVENDORCOUNTRY,String LOCALVENDORCITY, String LOCALVENDORMOBILE,String Spinvalue, String invspinValue, String LOCALVENDORZIP, String LOCALVENDORTELE) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Vend_Id", LOCALVENDORID);
		contentValues.put("Vend_Nm", LOCALVENDORNAME);
		contentValues.put("Vend_cntct_Nm", LOCALVENDORCONTACT);
		contentValues.put("Add_1", LOCALVENDORADDRESS);
		contentValues.put("City", LOCALVENDORCITY);
		contentValues.put("Ctr_Desc", LOCALVENDORCOUNTRY);
		contentValues.put("Mobile", LOCALVENDORMOBILE);
		contentValues.put("Vend_Inv",invspinValue );
		contentValues.put("Zip", LOCALVENDORZIP);
		contentValues.put("Active", Spinvalue);
		contentValues.put("Tele", LOCALVENDORTELE);

		int SqlValue = db.update("retail_store_vendor", contentValues, "Vend_Id = ? ", new String[]{String.valueOf(LOCALVENDORID)});
		if (SqlValue < 1) {
			Log.e("UpdateFailLocalVendor:", String.valueOf(SqlValue));
			return false;
		}
		return true;
	}

	// !!!!!!!!!!!!!*********INSERT DAYOPEN*****!!!!!!!!!!!!!!!
	public boolean insertDayopen(String STOREID, String STARTCASH) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Tri_Id", getSystemCurrentTime());
		contentValues.put("Store_Id", STOREID);
		contentValues.put("Business_Date", getDate());
		contentValues.put("Start_Date", getDate());
		contentValues.put("Start_Cash", STARTCASH);
		contentValues.put("Flag", "1");
		db.insert("retail_str_day_open_close", null, contentValues);
		return true;
	}


	// *************************!!!!!!!! CURRENT DATE  !!!!!!!!!!!!!!********************************
	public String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

	// *************************!!!!!!!! CURRENT TIME !!!!!!!!!!!!!!********************************
	public String getTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

	// ***********************!!!!!!VALIDATION TO CHECK EXIST RECORD!!!!!!!!*****************************
	public boolean CheckIsDataAlreadyInDBorNot(String Phone) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = Phone + "%";
		String Query = ("select Mobile_No from retail_cust where " + " Mobile_No like ?");
		Cursor cursor = db.rawQuery(Query, params);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;


	}

	// ***********************!!!!!!VALIDATION TO CHECK EXIST RECORD!!!!!!!!*****************************
	public boolean CheckBatchAlreadyInInventory(String BATCH_NO) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = BATCH_NO + "%";
		String Query = ("select Batch_No from retail_str_stock_master where " + " Batch_No like ?");
		Cursor cursor = db.rawQuery(Query, params);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;


	}

	// ***********************!!!!!!VALIDATION TO CHECK EXIST Date!!!!!!!!*****************************
	public boolean CheckDateAlreadyInDBorNot(String s) {
		SQLiteDatabase db = this.getReadableDatabase();
//		String[] params = new String[1];
//		params[0] = Phone + "%";
		//String Query =("select Start_Date from retail_str_day_open_close where Start_Date = date('now')",null);
		Cursor cursor = db.rawQuery("SELECT Start_Cash from retail_str_day_open_close where Flag  like  '1%' AND Start_Date = date('now')", null);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;


	}

	public boolean CheckTenderDateAlreadyInDBorNot(String s) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT Start_Cash from retail_str_day_open_close where Flag  like  '0%' AND Start_Date = date('now')", null);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;
	}

	// ******************************CODE TO GET TENDER****************************
	public Cursor gettender() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select Start_Cash ,Tri_Id  from retail_str_day_open_close where Start_Date= date('now')", null);
		return cursor;
	}

	//******************************CODE TO UPDATE TENDER******************************
	public boolean updatedayclose(String TRANSACTIONID, String CBCASH_ONHAND) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("CBcash_Onhand", CBCASH_ONHAND);
		contentValues.put("Flag", "0");
		db.update("retail_str_day_open_close", contentValues, "Tri_Id = ? ", new String[]{String.valueOf(TRANSACTIONID)});
		return true;


	}

	//***************************for retail care define*********************************
	public Cursor getdata(String Store_Id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_Card_define where Store_Id = " + Store_Id + "", null);
		return res;
	}

	//******************************for rule defination*******************************************
	public Cursor getdatarule(String Store_Id_rule) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from rule_defination where Store_Id = " + Store_Id_rule + "", null);
		return res;
	}

	//*************************************************************************************************************************
//****************************retrive data for retail_card_define**********************************************
	public ArrayList<String> getdata1() {
		ArrayList<String> updatelist1 = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select * from retail_card_define ", null);
			if (res.moveToFirst())
				Log.e(TAG, "getTableAsString called");
			{
				do {
					updatelist1.add(res.getString(res.getColumnIndex(STORE_ID_CARD)));

				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return updatelist1;
	}

	//****************************************retrive data for rule_defination************************
	public ArrayList<String> getdata2() {
		ArrayList<String> updatelist2 = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select * from rule_defination ", null);
			if (res.moveToFirst())
				Log.e(TAG, "getTableAsString called");
			{
				do {
					updatelist2.add(res.getString(res.getColumnIndex(STORE_ID_RULE)));

				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return updatelist2;
	}

	//*************************update fr retail card define****************************************
	public boolean updateContact(String STORE_ID, String POINTS_RANGE) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Points_Range", POINTS_RANGE);
		db.update("retail_Card_define", contentValues, "Store_Id = ? ", new String[]{String.valueOf(STORE_ID)});
		return true;
	}

	//**********************************update for rule defination*****************************************************
	public boolean updateContact2(String STORE_ID, String BASE_VALUE) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Base_Value", BASE_VALUE);
		db.update("rule_defination", contentValues, "Store_Id = ? ", new String[]{String.valueOf(STORE_ID)});
		return true;
	}

//*******************************************insert data for reports****************************************

	public boolean insertdata3(String STORE_ID, String SNO, String REPORT_NAME, String REPORT_CRITERIA, String COMMENTS) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Store_Id", STORE_ID);
		contentValues.put("SNo", SNO);
		contentValues.put("Report_Name", REPORT_NAME);
		contentValues.put("Report_Criteria", REPORT_CRITERIA);
		contentValues.put("Comments", COMMENTS);
		db.insert("retail_store_reports", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return true;
	}

//******************************retrive data for reports*********************************************************

	public ArrayList<String> getdatareports() {
		ArrayList<String> updatelist3 = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_store ", null);
		if (res.moveToFirst())
			Log.e(TAG, "getTableAsString called");
		{
			do {
				updatelist3.add(res.getString(res.getColumnIndex(STORE_ID_REPORT)));
				Log.e(TAG, "inside do function");
			} while (res.moveToNext());
		}

		return updatelist3;
	}

	public ArrayList<LoyalityModel> getLoyalityReport() {
		ArrayList<LoyalityModel> arrayList1 = new ArrayList();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_cust_loyalty", null);

		if (cur.moveToFirst()) {
			do {
				LoyalityModel lm = new LoyalityModel();
				lm.setCust_id(cur.getString(cur.getColumnIndex(COLUMN_CUSTID)));
				lm.setMobile_No(cur.getString(cur.getColumnIndex(COLUMN_MOBILENO1)));
				lm.setName(cur.getString(cur.getColumnIndex(COLUMN_NAME)));
				lm.setPoints_Earned(cur.getFloat(cur.getColumnIndex(COLUMN_POINTSEARNED)));
				lm.setPoints_Redeemed(cur.getFloat(cur.getColumnIndex(COLUMN_POINTSREDEEMED)));
				lm.setPoints_Available(cur.getFloat(cur.getColumnIndex(COLUMN_POINTSAVALILABLE)));
				arrayList1.add(lm);

			} while (cur.moveToNext());

		}
		return arrayList1;
	}

	public ArrayList<RetailcardDefineModel> getRetail_Card_Define() {
		ArrayList<RetailcardDefineModel> arrayList = new ArrayList<RetailcardDefineModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select Store_Id,Id,Card_type,Points_Range from retail_card_define", null);

		if (cursor.moveToFirst()) {
			do {
				RetailcardDefineModel rdm = new RetailcardDefineModel();
				rdm.setSTORID(cursor.getString(cursor.getColumnIndex(COLUMN_STOREID_RETAIL_CARD_DEFINE)));
				rdm.setCARDTYPE(cursor.getString(cursor.getColumnIndex(COLUMN_CARD_TYPE)));
				rdm.setID(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
				rdm.setPOINTSRANGE(cursor.getString(cursor.getColumnIndex(COLUMN_POINTS_RANGE)));
				arrayList.add(rdm);
			} while (cursor.moveToNext());
		}
		return arrayList;
	}

	public ArrayList<RuleDefinationModel> getRuleDefination() {
		ArrayList<RuleDefinationModel> arraylist = new ArrayList<RuleDefinationModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from rule_defination", null);
		if (cur.moveToFirst()) {
			do {
				RuleDefinationModel rm = new RuleDefinationModel();
				rm.setStoreID(cur.getString(cur.getColumnIndex(COLUMN_Store_ID_RULE_DEFINAATION)));
				rm.setSno(cur.getString(cur.getColumnIndex(COLUMN_SNO)));
				rm.setCARD_TYPE(cur.getString(cur.getColumnIndex(COLUMN_CARDTYPE)));
				rm.setBASEVALUE(cur.getFloat(cur.getColumnIndex(COLUMN_BASE_VALUE)));
				rm.setPerTONPOINTS(cur.getFloat(cur.getColumnIndex(COLUMN_PER_TON_POINTS)));
				arraylist.add(rm);
			} while (cur.moveToNext());
		}

		return arraylist;
	}

	public ArrayList<ProductAuto> getProductDetailsFromBarcode(String barcodeString) {

		ArrayList<ProductAuto> productAutoArrayList = new ArrayList<ProductAuto>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = barcodeString + "%";
			Cursor res = db.rawQuery("select distinct Store_Id,Prod_Id,Prod_Nm,BarCode,MRP,S_Price,P_Price,TAX_Desc,Mfg,Purch_Order_Unit,Selling_Order_Unit,Internet_Price,Is_Prod_Rel_Int,Active ,Profit_Margin from retail_store_prod where"
					+ " BarCode like ? ", params);

			if (res.moveToFirst())
				Log.e(TAG, "getTableAsString called");
			{
				do {
					ProductAuto product = new ProductAuto();
					product.setAutoStoreId(res.getString(res.getColumnIndex(PRODUCTSTOREID)));
					product.setAutoProductId(res.getString(res.getColumnIndex(PRODUCTPRODUCTID)));
					product.setAutoProductname(res.getString(res.getColumnIndex(PRODUCTNAME)));
					product.setAutoMrp(res.getString(res.getColumnIndex(PRODUCTMRP)));
					//product.setAutoMeasure(res.getString(res.getColumnIndex(PRODUCTMEASURE)));
					product.setAutoInternetPrice(res.getString(res.getColumnIndex(PRODUCTINTERNET)));
					//product.setAutoMeasure(res.getString(res.getColumnIndex(PRODUCTMEASURE)));
					product.setAutoMeasureunit(res.getString(res.getColumnIndex(PRODUCTMEASUREUNITINV)));
					product.setAutoPackingUnit(res.getString(res.getColumnIndex(PRODUCTPACKINGUNIT)));
					product.setAutoPurchaseprice(res.getString(res.getColumnIndex(PRODUCTPURCHASE)));
					product.setAutoSellingprice(res.getString(res.getColumnIndex(PRODUCTSELLING)));
					product.setAutoManuf(res.getString(res.getColumnIndex(PRODUCTMANUF)));
					product.setAutoBarcode(res.getString(res.getColumnIndex(PRODUCTBARCODE)));
					//	product.setAutoStrength(res.getString(res.getColumnIndex(PRODUCTSTRENGTH)));
					product.setAutoTax(res.getString(res.getColumnIndex(PRODUCTTAXDESC)));
					product.setAutoInternetrel(res.getString(res.getColumnIndex(PRODUCTRELEVANT)));
					product.setAutoActive(res.getString(res.getColumnIndex(PRODUCTACTIVE)));
					product.setMargin(res.getString(res.getColumnIndex(PRODUCTMARGIN)));
					productAutoArrayList.add(product);


				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return productAutoArrayList;
	}

	// ***********************!!!!!!VALIDATION TO CHECK EXIST Cash!!!!!!!!*****************************
	public boolean CheckCashInHandAlreadyInDBorNot(String s) {
		SQLiteDatabase db = this.getReadableDatabase();
//		String[] params = new String[1];
//		params[0] = Phone + "%";
		//String Query =("select Start_Date from retail_str_day_open_close where Start_Date = date('now')",null);
		Cursor cursor = db.rawQuery("SELECT Flag from retail_str_day_open_close where Flag  like '0%'", null);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;
	}

	public boolean insertdetailofpurchase(String Po_No, String Prod_Id, String Prod_Nm, String ProductPrice, String Qty, String Total) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Po_No", Po_No);
		contentValues.put("Prod_Nm", Prod_Nm);
		contentValues.put("Prod_Id", Prod_Id);
		contentValues.put("P_Price", ProductPrice);
		contentValues.put("Qty", Qty);
		contentValues.put("Total", Total);


		db.insert("retail_str_po_detail", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return true;
	}

	public ArrayList<PurchaseProductModel> getProductdata(String idorName) {
		ArrayList<PurchaseProductModel> productlist = new ArrayList<PurchaseProductModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = idorName + "%";
			params[1] = idorName + "%";
			/*params[2] = idorName + "%";*/
			//	params[3] = idorName + "%";
			/*Cursor productcursor = db.rawQuery("select distinct Prod_Id,Prod_Nm,P_Price,Selling_Order_Unit,MRP,Profit_Margin,S_Price,BarCode,Active,Conv_Fact,Ind_Nm from retail_store_prod_com where "
					+ "  Prod_Id  like ? or  Prod_Nm  like ? or BarCode like ? and Active = 'Y%'  "
					, params);*/
			Cursor productcursor = db.rawQuery("select distinct Prod_Id,Prod_Nm,P_Price,Selling_Order_Unit,MRP,Profit_Margin,S_Price,BarCode,Active,Conv_Fact,Ind_Nm from retail_store_prod_com where "
							+ "  Prod_Nm  like ? and Active like'Y%' or BarCode like ?  "
					, params);

			if (productcursor.moveToFirst()) {
				do {
					PurchaseProductModel pm = new PurchaseProductModel();
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCTNAME)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTPURCHASE)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(PRODUCTMEASUREUNITINV)));
					pm.setSellingPrice(productcursor.getString(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setMRP(productcursor.getString(productcursor.getColumnIndex(PRODUCTMRP)));
					pm.setBarcode(productcursor.getString(productcursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setProfit_Margin(productcursor.getString(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setConversionfactor(productcursor.getString(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndusteryname(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));

					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}

	ArrayList<VendorModel> getVendorNameforPurchase(String name) {
		ArrayList<VendorModel> vendorlist = new ArrayList<VendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select Vend_Dstr_Nm from retail_vend_dstr  where"
							+ " Vend_Dstr_Nm like ?  And Active like 'Y%' "
					, params);
			if (cursor.moveToFirst()) {
				do {
					VendorModel pm = new VendorModel(name);
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_VEND_DSTR_NAME)));
					vendorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;
	}

	ArrayList<VendorModel> getVendorName(String name) {
		ArrayList<VendorModel> vendorlist = new ArrayList<VendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct Vend_Nm from retail_str_grn_master  where"
							+ " Vend_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					VendorModel pm = new VendorModel(name);
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRN_VENDORNAME)));
					vendorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;
	}

	public ArrayList<String> getStoreid() {
		ArrayList<String> getstoreid = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select * from retail_store ", null);

			Log.e("Message", "############## data fetched  and result is " + res);
			if (res.moveToFirst())
				Log.e(TAG, "Get Store_id from retail_store table ");
			{
				do {
					getstoreid.add(res.getString(res.getColumnIndex(STOREID)));

				} while (res.moveToNext());
			}
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return getstoreid;
	}

	public void InsertAdTicker(String AdMainId, String AdDesc, String AdText, String AdStartDate, String AdEndDate, String Status) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			cv.put("Store_Id", PersistenceManager.getStoreId(mycontext));
			Log.e("########", "Ad_Main_Id in database:" + AdMainId);
			Log.e("########", "Ad_Desc:" + AdDesc);
			Log.e("#######", "Ad_Text in database:" + AdText);
			Log.e("#######", "Ad_Strt_Dt in database:" + AdStartDate);
			Log.e("#######", "Ad_End_Dt in database:" + AdEndDate);
			Log.e("#######", "Status in database:" + Status);
			cv.put("Ad_Main_Id", AdMainId);

			cv.put("Ad_Desc", AdDesc);
			cv.put("Ad_Text", AdText);
			cv.put("Ad_Strt_Dt", AdStartDate);
			cv.put("Ad_End_Dt", AdEndDate);
			cv.put("Status", Status);


			long result = db.insert("ad_ticker_main", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
			//db.close();
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
		//db.close();
	}


	ArrayList<Inventorymodel> getInventoryName(String name) {
		ArrayList<Inventorymodel> vendorlist = new ArrayList<Inventorymodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select Vend_Dstr_Nm from retail_vend_dstr  where"
							+ " Vend_Dstr_Nm like ?  AND Active like 'Y%'"
					, params);

//			Cursor cursor = db.rawQuery("select Vend from retail_store_prod_com  where"
//					+ " Store_Id like ? "
//					, params);

			if (cursor.moveToFirst()) {
				do {
					Inventorymodel pm = new Inventorymodel(name);
					//pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_STOREID)));

					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_VEND_DSTR_NAME)));
					vendorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;
	}

/*
	public ArrayList<Inventoryproductmodel> getInventoryProductdata(String idorName) {
		ArrayList<Inventoryproductmodel> productlist = new ArrayList<Inventoryproductmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = idorName + "%";
			params[1] = idorName + "%";


			Cursor productcursor = db.rawQuery("select Prod_Id, Prod_Nm ,Batch_No,Exp_Date, MRP, S_Price, P_Price, Uom, Qty , Conv_Fact from retail_str_stock_master where"
					+ " Prod_Id  like ? or  Prod_Nm  like ? "
					, params);
			if (productcursor.moveToFirst()) {
				do {
					Inventoryproductmodel pm = new Inventoryproductmodel();
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCTNAME)));
					pm.setMrp(productcursor.getString(productcursor.getColumnIndex(PRODUCTMRP)));
					pm.setSprice(productcursor.getString(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setPprice(productcursor.getString(productcursor.getColumnIndex(PRODUCTPURCHASE)));
					pm.setTax(productcursor.getString(productcursor.getColumnIndex(PRODUCTINVENTORYUOM)));
					pm.setBatchno(productcursor.getString(productcursor.getColumnIndex(PRODUCTBATCHINV)));
					pm.setExpdate(productcursor.getString(productcursor.getColumnIndex(PRODUCTEXPDATEINV)));
					pm.setStock(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTSTOCK)));
					pm.setConvfact(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));


					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}

		return productlist;
	}
*/

	// Adding new records to customer table
	public void insertinventory(String STORE_ID_MASTER, String PRODUCT_ID, String BATCH_NO, String EXP_DATE, String PPRICE, String S_PRICE, String QUANTITY, String MRP, String AMOUNT, String UOM) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("Store_Id", STORE_ID_MASTER);
		values.put("Prod_Id", PRODUCT_ID);
		values.put("Batch_No", BATCH_NO);
		values.put("Exp_Date", EXP_DATE);
		values.put("P_Price", PPRICE);
		values.put("S_Price", S_PRICE);
		values.put("Qty", QUANTITY);
		values.put("MRP", MRP);

		values.put("Amount", AMOUNT);
		values.put("Uom", UOM);
		// Inserting Row
		db.insert("retail_str_stock_master", null, values);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
	}

	public void savePurchaseList(ArrayList<PurchaseProductModel> list, String invoiceNumber, String VendorName) {
		SQLiteDatabase db = this.getWritableDatabase();

		if (list == null || invoiceNumber == null) {
			return;
		}

		for (PurchaseProductModel prod : list) {
			ContentValues contentValues = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			contentValues.put("Store_Id", PersistenceManager.getStoreId(mycontext));
			contentValues.put("Po_No", invoiceNumber);
			contentValues.put("Vendor_Nm", VendorName);
			contentValues.put("Prod_Nm", prod.getProductName());
			contentValues.put("Prod_Id", prod.getProductId());
			contentValues.put("MRP", prod.getMRP());
			contentValues.put("Uom", prod.getUom());
			contentValues.put("S_Price", prod.getSellingPrice());
			contentValues.put("P_Price", prod.getProductPrice());
			contentValues.put("BarCode", prod.getBarcode());
			contentValues.put("Profit_Margin", prod.getProfit_Margin());
			contentValues.put("Conv_Fact", prod.getConversionfactor());
			contentValues.put("Ind_Nm", prod.getIndusteryname());


			if (prod.getProductQuantity() != 0) {
				contentValues.put("Qty", prod.getProductQuantity());
			} else {
				Log.e("Database Operation", "row  not inserted...");
				continue;
			}
			contentValues.put("Total", prod.getTotal());


			db.insert("retail_str_po_detail", null, contentValues);
		}

		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}

	public void saveHoldPurchaseList(ArrayList<PurchaseProductModel> list, String invoiceNumber, String VendorName) {
		SQLiteDatabase db = this.getWritableDatabase();

		if (list == null || invoiceNumber == null) {
			return;
		}

		for (PurchaseProductModel prod : list) {
			ContentValues contentValues = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			contentValues.put("Store_Id", PersistenceManager.getStoreId(mycontext));
			contentValues.put("Po_No", invoiceNumber);
			contentValues.put("Vendor_Nm", VendorName);
			contentValues.put("Prod_Nm", prod.getProductName());
			contentValues.put("Prod_Id", prod.getProductId());
			contentValues.put("MRP", prod.getMRP());
			contentValues.put("Uom", prod.getUom());
			contentValues.put("S_Price", prod.getSellingPrice());
			contentValues.put("P_Price", prod.getProductPrice());
			contentValues.put("BarCode", prod.getBarcode());
			contentValues.put("Profit_Margin", prod.getProfit_Margin());
			contentValues.put("Conv_Fact", prod.getConversionfactor());
			contentValues.put("Ind_Nm", prod.getIndusteryname());
			if (prod.getProductQuantity() != 0) {
				contentValues.put("Qty", prod.getProductQuantity());
			} else {
				Log.e("Database Operation", "row  not inserted...");
				continue;
			}
			contentValues.put("Total", prod.getTotal());
			contentValues.put("Flag", "H");

			db.insert("retail_str_po_detail_hold", null, contentValues);
		}

		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}

	public ArrayList<String> getinventorystore() {
		ArrayList<String> storelist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_store_prod_com ", null);
		if (res.moveToFirst())
			Log.e(TAG, "getTableAsString called");
		{
			do {
				storelist.add(res.getString(res.getColumnIndex(STORE_ID_INVENTORY)));
			} while (res.moveToNext());
		}

		return storelist;
	}

	// ***********************!!!!!!VALIDATION TO CHECK EXIST Cash!!!!!!!!*****************************
	public boolean CheckDayOpenforPurchase(String s) {
		SQLiteDatabase db = this.getReadableDatabase();
//		String[] params = new String[1];
//		params[0] = Phone + "%";
		//String Query =("select Start_Date from retail_str_day_open_close where Start_Date = date('now')",null);
		Cursor cursor = db.rawQuery("SELECT Start_Date from retail_str_day_open_close  where Start_Date = ' ' ", null);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;
	}

	public boolean CheckValidationForPurchase(String s) {
		SQLiteDatabase db = this.getReadableDatabase();
//		String[] params = new String[1];
//		params[0] = Phone + "%";
		//String Query =("select Start_Date from retail_str_day_open_close where Start_Date = date('now')",null);
		Cursor cursor = db.rawQuery("SELECT Flag from retail_str_day_open_close where Start_Date = date('now')", null);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;


	}

	public void saveInventoryList(String STORE_ID_MASTER, ArrayList<Inventoryproductmodel> list, String BATCH_NO, String EXP_DATE) {
		SQLiteDatabase db = this.getWritableDatabase();

		if (list == null) {
			return;
		}

		for (Inventoryproductmodel prod : list) {
			ContentValues values = new ContentValues();
			values.put("Store_Id", STORE_ID_MASTER);
			values.put("Prod_Id", prod.getProductId());
			values.put("Prod_Nm", prod.getProductName());
			values.put("Batch_No", BATCH_NO);
			values.put("Exp_Date", EXP_DATE);
			values.put("P_Price", prod.getPprice());
			values.put("S_Price", prod.getSprice());
			values.put("Qty", prod.getProductQuantity());
			values.put("MRP", prod.getMrp());
			values.put("Amount", prod.getTotal());
			values.put("Uom", prod.getTax());
			// Inserting Row
			db.insert("retail_str_stock_master", null, values);
		}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;

	}

	public void savesalesListdetail(String TRANS_ID, ArrayList<Sales> list) {
		SQLiteDatabase db = this.getWritableDatabase();

		if (list == null) {
			return;
		}

		for (Sales sales : list) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("Tri_Id", TRANS_ID);
			//	contentValues.put("Invoice_No", INVOICE);
			contentValues.put("Batch_No", sales.getBatchNo());
			contentValues.put("Prod_Nm", sales.getProductName());
			contentValues.put("Exp_Date", sales.getExpiry());
			contentValues.put("S_Price", sales.getSPrice());

			/*if (sales.getQuantity()<=sales.getStockquant() || sales.getQuantity()!=0) {

			}else{
				continue;

			}*/
			contentValues.put("Qty", sales.getQuantity());

			contentValues.put("MRP", sales.getMrp());
			contentValues.put("Uom", sales.getUom());
			contentValues.put("Total", sales.getTotal());
			db.insert("retail_str_sales_detail", null, contentValues);
		}

		db.close(); // Closing database connection
		Log.e("Database Operation for", "row inserted...");
		return;
	}

	public boolean insertdetailsifpaybaycash(String TRANS_ID, String GrandTotal) {

		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("Tri_Id", TRANS_ID);
		contentValues.put("Store_Id", PersistenceManager.getStoreId(mycontext));
		//contentValues.put("Invoice_No", INVOICENO);
		contentValues.put("Grand_Total", GrandTotal);
		contentValues.put("Business_Date", getDate());
		contentValues.put("Sale_Date", getDate());
		contentValues.put("Sale_Time", getTime());


		db.insert("retail_str_sales_master", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return true;
	}


	public ArrayList<Sales> getSalesDetailsFromBarcode(String nameOrPhone) {

		ArrayList<Sales> salesproductlist = new ArrayList<Sales>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = nameOrPhone + "%";
			//params[1] = nameOrPhone + "%";
			Cursor res = db.rawQuery("select Prod_Nm,Batch_No,Exp_Date,Qty,MRP,Amount,uom,S_Price from retail_str_stock_master where "
					+ " BarCode like ? ", params);
			if (res.moveToFirst()) {
				do {
					Sales saleslist = new Sales();
					saleslist.setProductName(res.getString(res.getColumnIndex(PRODUCT_NAME)));
					saleslist.setBatchNo(res.getString(res.getColumnIndex(BATCH_NO)));
					saleslist.setExpiry(res.getString(res.getColumnIndex(EXPIRY)));
					saleslist.setQuantity(res.getInt(res.getColumnIndex(QUANTITY)));
					saleslist.setMrp(res.getFloat(res.getColumnIndex(MRP)));
					// saleslist.setAmount(res.getString(res.getColumnIndex(AMOUNT)));
					saleslist.setUom(res.getString(res.getColumnIndex(UOM)));
					// saleslist.setPPrice(res.getString(res.getColumnIndex(P_PRICE)));
					saleslist.setSPrice(res.getFloat(res.getColumnIndex(S_PRICE)));
					salesproductlist.add(saleslist);
				} while (res.moveToNext());

			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return salesproductlist;
	}

	public ArrayList<Salesreturndetail> getSalesReturn(String invoiceno) {

		ArrayList<Salesreturndetail> salesreturnlist = new ArrayList<Salesreturndetail>();
		try {

			String[] params = new String[1];
			params[0] = invoiceno + "%";
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("Select Prod_Nm,Batch_No,Exp_Date,S_Price,Qty,MRP,Uom,Total from retail_str_sales_detail where"
					+ " Tri_Id like ? ", params);
			if (res.moveToFirst()) {
				do {
					Salesreturndetail salesreturndetail = new Salesreturndetail();
//					salesreturndetail.setSaleTransid(res.getString(res.getColumnIndex(RETURNTRANSIDs)));
					//salesreturndetail.setSaleBillno(res.getString(res.getColumnIndex(BILLNO)));
					salesreturndetail.setSaleproductname(res.getString(res.getColumnIndex(RETURNPRODUCTNAME)));
					salesreturndetail.setSaleexpiry(res.getString(res.getColumnIndex(RETURNEXPIRY)));
					salesreturndetail.setSalebatchno(res.getString(res.getColumnIndex(RETURNBATCHNO)));
					salesreturndetail.setSalesellingprice(res.getFloat(res.getColumnIndex(RETURNSELLINGPRICE)));
					salesreturndetail.setSaleqty(res.getFloat(res.getColumnIndex(RETURNQUANTITY)));
					salesreturndetail.setSalemrp(res.getString(res.getColumnIndex(RETURNMRP)));
					salesreturndetail.setSaleuom(res.getString(res.getColumnIndex(RETURNUNITOFMEASURE)));
					salesreturndetail.setSaletotal(res.getFloat(res.getColumnIndex(RETURNGRANDTOTAL)));
					salesreturnlist.add(salesreturndetail);

				}
				while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return salesreturnlist;
	}

	public ArrayList<PurchaseProductModel> getAllOLDPurchaseList(String OldInvoiceno) {
		ArrayList<PurchaseProductModel> OldInvoiceData = new ArrayList<PurchaseProductModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = OldInvoiceno + "%";

			Cursor productcursor = db.rawQuery("select Po_No,Prod_Id,Prod_Nm,P_Price,MRP,Uom,Qty,Total,Vendor_Nm,Conv_Fact,Flag,S_Price,Conv_Fact,Ind_Nm ,Profit_Margin from retail_str_po_detail where "
					+ " Po_No  like ?  "
					, params);
			if (OldInvoiceno == null) {
				return null;
			}
			if (productcursor.moveToFirst()) {
				do {
					PurchaseProductModel pm = new PurchaseProductModel();
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_PRODUCT_ID)));

					////find from here i want to do wwork from here
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_PRODUCT_ID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_PRODUCT_NAME)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(Purchase_COLUMN_P_PRICE)));
					pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_VENDOR_DISTRIBUTOR_NAME)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_UOM)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(Purchase_COLUMN_QTY)));
					pm.setTotal(productcursor.getFloat(productcursor.getColumnIndex(Purchase_COLUMN_TOTAL)));
					pm.setMRP(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_MRP)));
					pm.setConversionfactor(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_Conv_Fact)));
					pm.setIndusteryname(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setSellingPrice(productcursor.getString(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setConversionfactor(productcursor.getString(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setProfit_Margin(productcursor.getString(productcursor.getColumnIndex(PRODUCTMARGIN )));
					OldInvoiceData.add(pm);
				} while (productcursor.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return OldInvoiceData;
	}

	public ArrayList<VendorModel> getVendors() {

		ArrayList<VendorModel> vendorNamelist = new ArrayList<VendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
         /*String []params=new String[1];
         params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct Vendor_Nm from retail_str_po_master", null);
			if (cursor.moveToFirst()) {
				do {
					VendorModel pm = new VendorModel();
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vendorNamelist.add(pm);

				} while (cursor.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return vendorNamelist;
	}

	public ArrayList<PurchaseInvoiceDropDownModel> getLastInvoices(String VendorName) {
		ArrayList<PurchaseInvoiceDropDownModel> LastInvoicelist = new ArrayList<PurchaseInvoiceDropDownModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = VendorName + "%";
			Cursor cursor = db.rawQuery("select distinct Po_No,Vendor_Nm,Lastupdate from retail_str_po_master where "
							+ "Vendor_Nm  like ? ORDER BY Po_No DESC limit 3 "
					, params);

			if (cursor.moveToFirst()) {
				do {
					PurchaseInvoiceDropDownModel purchaseInvoiceDropDownModel = new PurchaseInvoiceDropDownModel();
					purchaseInvoiceDropDownModel.setPurchaseOrderNo(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_PO_NO)));
					purchaseInvoiceDropDownModel.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_LASTUPDATE)));
					LastInvoicelist.add(purchaseInvoiceDropDownModel);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return LastInvoicelist;
	}


	public void SaveGrandDataForPurchase(String InvoiceNo, String VendorName, String GrandTotal) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		contentValues.put("Store_Id", PersistenceManager.getStoreId(mycontext));
		contentValues.put("Vendor_Nm", VendorName);
		contentValues.put("Po_No", InvoiceNo);
		contentValues.put("Grand_Total", GrandTotal);
		contentValues.put("Po_Date", getDate());
		db.insert("retail_str_po_master", null, contentValues);

		//}
		db.close(); // Closing database connection
		Log.e("Database Operation", "Data into MAster inserted...");
		return;
	}


	public ArrayList<ReportDistributorModel> getDistributorReport(String DistributorName) {

		ArrayList<ReportDistributorModel> distributorlist = new ArrayList<ReportDistributorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = DistributorName + "%";
			Cursor cursor = db.rawQuery("select Dstr_Nm, Active from retail_str_dstr  where"
							+ " Dstr_Nm like ? "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportDistributorModel dm = new ReportDistributorModel();
					dm.setDstr_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_DSTR_NAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportDistributorModel> getDistributorName(String name) {
		ArrayList<ReportDistributorModel> distributornamelist = new ArrayList<ReportDistributorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select Dstr_Nm from retail_str_dstr  where"
							+ " Dstr_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportDistributorModel dm = new ReportDistributorModel();
					dm.setDstr_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_DSTR_NAME)));
					distributornamelist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return distributornamelist;
	}

	public ArrayList<Salesreturndetail> getReasonReturn() {

		ArrayList<Salesreturndetail> returnlist = new ArrayList<Salesreturndetail>();
		try {

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select Reason_Return from retail_store_sales_desc", null);
			if (res.moveToFirst()) {
				do {
					Salesreturndetail salesreturndetail = new Salesreturndetail();
					salesreturndetail.setReasons(res.getString(res.getColumnIndex(RETURNREASON)));
				/*returnlist.add(res.getString(res.getColumnIndex(RETURNREASON)));*/
					returnlist.add(salesreturndetail);

				}
				while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return returnlist;
	}


	public ArrayList<VendorModel> getvendorsforIndirectPayment() {
		ArrayList<VendorModel> vendorNamelist = new ArrayList<VendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			/*String []params=new String[1];
			params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct Vend_Nm from retail_store_vendor where "
							+ "Active like 'Y%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					VendorModel pm = new VendorModel();
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_VENDORNAME)));
					vendorNamelist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorNamelist;
	}

	public ArrayList<String> selecttheReason() {
		ArrayList<String> Paymentreasons = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			/*String []params=new String[1];
			params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select  Description from tmp_retail_pay_desc", null);
			if (cursor.moveToFirst()) {
				do {
					Paymentreasons.add(cursor.getString(cursor.getColumnIndex(COLUMN_VENDOR_REASONS_DESCRIPTION)));

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return Paymentreasons;
	}

	public void saveIndirectPayment(String PurchaseOrderNo, String vendorselected, String selectedReasons, String amount, String Resval) {
		SQLiteDatabase db = this.getWritableDatabase();

	/*	if(GrandTotal == null || InvoiceNo == null || VendorName == null) {
			return;
		}
*/
				/*if(VendorModel prod: InvoiceNo){*/
		if (amount == null) {
			return;
		}
		ContentValues contentValues = new ContentValues();
		contentValues.put("Pay_Id", PurchaseOrderNo);
		contentValues.put("vend_Dstr_Nm", vendorselected);
		contentValues.put("Reason_Of_Pay", selectedReasons);
		contentValues.put("Amount", amount);
		contentValues.put("Payment_Id", Resval);
		db.insert("tmp_vend_dstr_payment", null, contentValues);
		//}
		db.close(); // Closing database connection
		Log.e("Database Operation", "Data  Payment table inserted...");
		return;
	}

	public ArrayList<String> getBankName() {
		ArrayList<String> BankName = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			/*String []params=new String[1];
			params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct Bank_Name from bank_details", null);
			if (cursor.moveToFirst()) {
				do {
					BankName.add(cursor.getString(cursor.getColumnIndex(COLUMN_BANKNAME)));

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return BankName;
	}

	public void saveIndirectPaymentByCheque(String PurchaseOrderNo, String vendorselected, String selectedReasons, String amount, String bankNameSelected, String ChequeNo) {
		SQLiteDatabase db = this.getWritableDatabase();
		if (amount == null) {
			return;
		}
		ContentValues contentValues = new ContentValues();
		contentValues.put("Pay_Id", PurchaseOrderNo);
		contentValues.put("vend_Dstr_Nm", vendorselected);
		contentValues.put("Reason_Of_Pay", selectedReasons);
		contentValues.put("Amount", amount);
		contentValues.put("Bank_Name", bankNameSelected);
		contentValues.put("Cheque_No", ChequeNo);

		db.insert("tmp_vend_dstr_payment", null, contentValues);

		//}
		db.close(); // Closing database connection
		Log.e("Database Operation", "Data  Payment byCheque table inserted...");
		return;
	}

	public ArrayList<ReportLocalProductPharmaModel> getLocalProdPharmaReport(String localProductName) {

		ArrayList<ReportLocalProductPharmaModel> localproductreportlist = new ArrayList<ReportLocalProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
      /*String[] params = new String[1];
      params[0] = localProductName + "%";*/
			Cursor cursor = db.rawQuery("select distinct Prod_Nm,BarCode,MRP,S_Price,P_Price,Active,Profit_Margin from retail_store_prod_local  where"
							+ " Prod_Nm = '" + localProductName + "'  "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductPharmaModel lm = new ReportLocalProductPharmaModel();
					lm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					lm.setBarCode(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALBARCODE)));
					lm.setMRP(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALMRP)));
					lm.setS_Price(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALSELLING)));
					lm.setPPrice(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPURCHASE)));
					lm.setActive(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALACTIVE)));
					lm.setMargin(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALMARGIN)));
					localproductreportlist.add(lm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return localproductreportlist;
	}


	public ArrayList<ReportLocalProductCpgModel> getlocalProductCpgName(String name) {
		ArrayList<ReportLocalProductCpgModel> localproductnamelist = new ArrayList<ReportLocalProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct Prod_Nm from retail_store_prod_local_cpg  where"
							+ " Prod_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductCpgModel lm = new ReportLocalProductCpgModel();
					lm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_NM)));
					localproductnamelist.add(lm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return localproductnamelist;

	}

	public ArrayList<ReportLocalProductPharmaModel> getlocalProductPharmaName(String name) {
		ArrayList<ReportLocalProductPharmaModel> localproductnamelist = new ArrayList<ReportLocalProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct Prod_Nm from retail_store_prod_local  where"
							+ " Prod_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductPharmaModel lm = new ReportLocalProductPharmaModel();
					lm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					localproductnamelist.add(lm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return localproductnamelist;

	}


	public ArrayList<ReportProductPharmaModel> getProductPharmaReport(String ProductName) {

		ArrayList<ReportProductPharmaModel> productlist = new ArrayList<ReportProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = ProductName + "%";
			Cursor cursor = db.rawQuery("select distinct Prod_Nm,BarCode,MRP,S_Price,P_Price,Active,Profit_Margin from retail_store_prod  where"
							+ " Prod_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportProductPharmaModel pm = new ReportProductPharmaModel();
					pm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTNAME)));
					pm.setBarCode(cursor.getString(cursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setMRP(cursor.getString(cursor.getColumnIndex(PRODUCTMRP)));
					pm.setS_Price(cursor.getString(cursor.getColumnIndex(PRODUCTSELLING)));
					pm.setP_Price(cursor.getString(cursor.getColumnIndex(PRODUCTPURCHASE)));
					pm.setActive(cursor.getString(cursor.getColumnIndex(PRODUCTACTIVE)));
					pm.setMargin(cursor.getString(cursor.getColumnIndex(PRODUCTMARGIN)));
					productlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return productlist;
	}


	public ArrayList<ReportProductCpgModel> getProductCpgReport(String ProductName) {

		ArrayList<ReportProductCpgModel> productlist = new ArrayList<ReportProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = ProductName + "%";
			Cursor cursor = db.rawQuery("select distinct Prod_Nm,BarCode,MRP,S_Price,P_Price,Active,Profit_Margin from retail_store_prod_cpg  where"
							+ " Prod_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportProductCpgModel pm = new ReportProductCpgModel();
					pm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_NM)));
					pm.setBarCode(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_CODE)));
					pm.setMRP(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_MRP)));
					pm.setS_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_S_PRICE)));
					pm.setP_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_P_PRICE)));
					pm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_ACTIVE)));
					pm.setProfitMargin(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_MARGIN)));
					productlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return productlist;
	}


	public ArrayList<ReportLocalProductCpgModel> getLocalProdCpgReport(String localProductName) {

		ArrayList<ReportLocalProductCpgModel> localproductreportlist = new ArrayList<ReportLocalProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = localProductName + "%";
			Cursor cursor = db.rawQuery("select distinct Prod_Nm,BarCode,MRP,S_Price,P_Price,Active,Profit_Margin from retail_store_prod_local_cpg  where"
							+ " Prod_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductCpgModel lm = new ReportLocalProductCpgModel();
					lm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_NM)));
					lm.setBarCode(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_CODE)));
					lm.setMRP(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_MRP)));
					lm.setS_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_S_PRICE)));
					lm.setP_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_P_PRICE)));
					lm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_ACTIVE)));
					lm.setProfitMargin(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_MARGIN)));
					localproductreportlist.add(lm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return localproductreportlist;
	}

	/////////////////////////////////////*************/////////////////////////////////////////////////////////////////

	public ArrayList<ReportProductCpgModel> getProductCpgName(String name) {
		ArrayList<ReportProductCpgModel> productnamelist = new ArrayList<ReportProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct Prod_Nm from retail_store_prod_cpg  where"
							+ " Prod_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportProductCpgModel pm = new ReportProductCpgModel();
					pm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_NM)));
					productnamelist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return productnamelist;
	}


	/////////////////////////////////////*************/////////////////////////////////////////////////////////////////
	public ArrayList<ReportProductPharmaModel> getProductPharmaName(String name) {
		ArrayList<ReportProductPharmaModel> productnamelist = new ArrayList<ReportProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct Prod_Nm from retail_store_prod  where"
							+ " Prod_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportProductPharmaModel pm = new ReportProductPharmaModel();
					pm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTNAME)));
					productnamelist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return productnamelist;
	}

	public ArrayList<ReportVendorModel> getVendorReport(String VendorName) {

		ArrayList<ReportVendorModel> vendorlist = new ArrayList<ReportVendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = VendorName + "%";
			Cursor cursor = db.rawQuery("select distinct Vend_Nm, Active from retail_store_vendor  where"
							+ " Vend_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorModel vm = new ReportVendorModel();
					vm.setVend_Nm(cursor.getString(cursor.getColumnIndex(LOCALVENDORNAME)));
					vm.setActive(cursor.getString(cursor.getColumnIndex(LOCALVENDORACTIVE)));
					vendorlist.add(vm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;
	}

	/////////////////////////////////////*************/////////////////////////////////////////////////////////////////
	public ArrayList<ReportVendorModel> getVendorReportName(String name) {
		ArrayList<ReportVendorModel> vendornamelist = new ArrayList<ReportVendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct Vend_Nm from retail_store_vendor  where"
							+ " Vend_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorModel vm = new ReportVendorModel();
					vm.setVend_Nm(cursor.getString(cursor.getColumnIndex(LOCALVENDORNAME)));
					vendornamelist.add(vm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendornamelist;
	}


	public ArrayList<Sales> getProductNamedata(String idorName) {
		ArrayList<Sales> productNamelist = new ArrayList<Sales>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = idorName + "%";
			params[1] = idorName + "%";
			Cursor productcursor = db.rawQuery("select distinct Prod_Nm,Prod_Id,Batch_No,Exp_Date,MRP,Amount,uom,S_Price,Qty,BarCode,Conv_Fact,Con_Mul_Qty, Conv_MRP,Conv_SPrice from retail_str_stock_master where "
							+ "  Prod_Nm LIKE ? or BarCode like ? "
					, params);

			if (productcursor.moveToFirst()) {
				do {
					Sales saleslist = new Sales();
					saleslist.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCT_NAME)));
					saleslist.setBatchNo(productcursor.getString(productcursor.getColumnIndex(BATCH_NO)));
					saleslist.setExpiry(productcursor.getString(productcursor.getColumnIndex(EXPIRY)));
					saleslist.setStockquant(productcursor.getFloat(productcursor.getColumnIndex(TOTALQTY)));
					saleslist.setMrp(productcursor.getFloat(productcursor.getColumnIndex(CONVMRP)));
					saleslist.setProdid(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					// saleslist.setAmount(res.getString(res.getColumnIndex(AMOUNT)));
					saleslist.setUom(productcursor.getString(productcursor.getColumnIndex(UOM)));
					// saleslist.setPPrice(res.getString(res.getColumnIndex(P_PRICE)));
					saleslist.setSPrice(productcursor.getFloat(productcursor.getColumnIndex(CONVSPRICE)));
					saleslist.setConversionfacter(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));


					productNamelist.add(saleslist);
				} while (productcursor.moveToNext());

			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return productNamelist;
	}

	public ArrayList<PurchaseProductModelwithpo> getvendorsearch() {

		ArrayList<PurchaseProductModelwithpo> vendorNamelist = new ArrayList<PurchaseProductModelwithpo>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
         /*String []params=new String[1];
         params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct Vendor_Nm from retail_str_po_detail", null);
			if (cursor.moveToFirst()) {
				do {
					PurchaseProductModelwithpo pm = new PurchaseProductModelwithpo();
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vendorNamelist.add(pm);

				} while (cursor.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return vendorNamelist;
	}

	public ArrayList<String> getPo_numbers(String name) {

		ArrayList<String> vendorNamelist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct Po_No from retail_str_po_detail where "
							+ " Vendor_Nm like ? "
					, params);
			if (cursor.moveToFirst()) {
				do {
					vendorNamelist.add(cursor.getString(cursor.getColumnIndex(COLUMN_DETAIL_PO_NO)));


				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorNamelist;
	}


	public ArrayList<PurchaseProductModelwithpo> getPurchaseProductdata(String PO) {
		ArrayList<PurchaseProductModelwithpo> productlist = new ArrayList<PurchaseProductModelwithpo>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = PO + "%";

			Cursor productcursor = db.rawQuery(" select Po_No, Prod_Id, MRP,S_Price ,Prod_Nm ,P_Price, Qty, Total,Profit_Margin, Uom,Conv_Fact,Ind_Nm from retail_str_po_detail where "
							+ " Po_No  like ? "
					, params);

			if (productcursor.moveToFirst()) {
				do {
					PurchaseProductModelwithpo pm = new PurchaseProductModelwithpo();
					//  pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_VENDOR)));

					pm.setPurchaseno(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PO_NO)));
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_ID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_NAME)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_PPRICE)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(COLUMN_DETAIL_QTY)));
					pm.setTotal(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_TOTAL)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_UOM)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_MRP)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_SPRICE)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setConversion(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustery(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));


					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}

	public void saveInventorywithpo(ArrayList<PurchaseProductModelwithpo> list, String VendorName, String Ponumbers, String grnId) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			boolean returnval = true;
			boolean fortrue = false;
			if (list == null) {
				return;
			}
			for (PurchaseProductModelwithpo prod : list) {
				PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
				PersistenceManager.getStoreId(mycontext);

				ContentValues values = new ContentValues();
				Log.e("Prodlength", String.valueOf(list.size()));

				// for(int Batch=0;Batch<list.size();Batch++) {
				fortrue = CheckbatchnoAlreadyInDBorNot(prod.getBatch_No());

				float totalinventoryqty=prod.productQuantity+prod.getDiscountitems();
				float mrp = prod.getMrp();
				float sprice=prod.getSprice();
				float conversin=prod.getConversion();
				float newmrp = mrp / conversin;
				if (newmrp < 0) {
					newmrp = 0;
				}
				float newsprice = sprice / conversin;
				if (newsprice < 0) {
					newsprice = 0;
				}
				if (fortrue == false) {
					values.put("Grn_Id", grnId);
					values.put("Vend_Nm", VendorName);
					values.put("Po_No", Ponumbers);
					values.put("Batch_No", prod.getBatch_No());
					values.put("Exp_Date", prod.getExp_Date());
					values.put("Store_Id", PersistenceManager.getStoreId(mycontext));
					values.put("Prod_Id", prod.getProductId());
					values.put("Prod_Nm", prod.getProductName());
					values.put("P_Price", prod.getProductPrice());
					values.put("Profit_margin", prod.getProductmargin());
					values.put("Qty", prod.getProductQuantity());
					values.put("MRP", prod.getMrp());
					values.put("S_Price", prod.getSprice());
					values.put("Amount", prod.getTotal());
					values.put("Uom", prod.getUom());
					values.put("BarCode", "NA");
					values.put("Conv_Fact", prod.getConversion());
					values.put("Free_Goods", prod.getDiscountitems());
					values.put("Con_Mul_Qty",prod.getTotalqty());
					values.put("Conv_MRP",newmrp);
					values.put("Conv_SPrice",newsprice);
					values.put("Prev_Qty",totalinventoryqty);

					// Inserting Row
					db.insert("retail_str_stock_master", null, values);
				} else {
					String batchqty = getparticularbatchqty(prod.getBatch_No(), prod.getProductId());
					values.put("Grn_Id", grnId);
					float prodQuantity = prod.getTotalqty();
					float newStockQuantity = Float.parseFloat(batchqty) + prodQuantity;
					if (newStockQuantity < 0) {
						newStockQuantity = 0;
					}
					values.put("Con_Mul_Qty", Double.toString(newStockQuantity));
					int sqlUpdateRetval = db.update("retail_str_stock_master", values, "Batch_No = ?  and " +
							"Prod_Id " +
							" = ? ", new String[]{prod.getBatch_No(), prod.getProductId()});

					Log.d("Sudhee", "Update done for batch:" + prod.getBatch_No() + ", prodid:" + prod.getProductId());

					if (sqlUpdateRetval < 1) {
						Log.e("Update fail", "returned: " + sqlUpdateRetval);
						returnval = false;
					}
					//return;
				}
				//}
			}
			db.close(); // Closing database connection
			Log.e("Database Operation", "row inserted...");
			return;

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

	public boolean CheckbatchnoAlreadyInDBorNot(String batchno) {
		SQLiteDatabase db = this.getReadableDatabase();
	/*	String[] params = new String[1];
		params[0] = batchno + "%";*/
		//  params[1] =productid + "%";

		String Query = ("select Batch_No,Prod_Id,Con_Mul_Qty from retail_str_stock_master where "
				+ " Batch_No ='"+batchno+" '");
		Cursor cursor = db.rawQuery(Query, null);

		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		return true;
	}
	public String getparticularbatchqty(String batchno, String Prod_Id) {
		String getQty = null;
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("select Con_Mul_Qty from retail_str_stock_master where " + " Batch_No =  '" + batchno + "' and Prod_Id = '" + Prod_Id + "'");
		Log.e("Query::", "select Con_Mul_Qty from retail_str_stock_master where " + " Batch_No =  '" + batchno + "' and Prod_Id = '" + Prod_Id + "'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			getQty = cursor.getString(cursor.getColumnIndex(TOTALQTY));
		}
		return getQty;
	}

	public String getparticularbatchqtyforsalesreturn(String batchno, String Prod_Nm) {
		String getQty = null;
		SQLiteDatabase db = this.getReadableDatabase();
   /* String[] params = new String[1];
    params[0] = batchno + "%";
    params[1] = Prod_Id + "%";*/
		String Query = ("select Qty from retail_str_stock_master where " + " Batch_No =  '" + batchno + "' and Prod_Nm = '" + Prod_Nm + "'");
		Log.e("Query::", "select Qty from retail_str_stock_master where " + " Batch_No =  '" + batchno + "' and Prod_Nm = '" + Prod_Nm + "'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			getQty = cursor.getString(cursor.getColumnIndex(QUANTITY));
		}
		return getQty;

	}

	public ArrayList<Salesreturndetail> getSaleReturndetail() {
		ArrayList<Salesreturndetail> salereturntimedatelist = new ArrayList<Salesreturndetail>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor Returncursor = db.rawQuery("select Business_Date,Sale_Date,Sale_Time from retail_str_sales_master", null);

			if (Returncursor.moveToFirst()) {
				do {
					Salesreturndetail sr = new Salesreturndetail();

//
					sr.setSaleDate(Returncursor.getString(Returncursor.getColumnIndex(RETURSALEDATE)));

					salereturntimedatelist.add(sr);
				} while (Returncursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return salereturntimedatelist;
	}

	public void insertsalesreturn(String INVOICE, ArrayList<Salesreturndetail> list, String Item) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		if (list == null) {
			return;
		}

		for (Salesreturndetail salesreturndetail : list) {
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));

			ContentValues contentValues = new ContentValues();
			contentValues.put("Id", getSystemCurrentTimeinsalesreturnwithin());
			contentValues.put("Tri_Id", INVOICE);
			//	contentValues.put("Invoice_No", INVOICE);
			contentValues.put("Store_Id", PersistenceManager.getStoreId(mycontext));
			////contentValues.put("Prod_Nm", salesreturndetail.getSaleproductname());
			//contentValues.put("Batch_No", salesreturndetail.getSalebatchno());
			//	contentValues.put("Exp_Date", salesreturndetail.getSaleexpiry());
			//contentValues.put("S_Price", salesreturndetail.getSalesellingprice());
			//	contentValues.put("Qty", salesreturndetail.getSaleqty());
			//contentValues.put("MRP", salesreturndetail.getSalemrp());
			//	contentValues.put("UOM", salesreturndetail.getSaleuom());
			contentValues.put("Reason_Of_Return", Item);
			contentValues.put("Grand_Total", salesreturndetail.getSaletotal());
			// Inserting Row
			db.insert("retail_str_sales_master_return", null, contentValues);
		}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;


	}

	public void insertsalesreturnwithoutinvoice(String INVOICE, ArrayList<SalesreturndetailWithoutPo> list, String Item) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		if (list == null) {
			return;
		}

		for (SalesreturndetailWithoutPo salesreturndetail : list) {
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));

			ContentValues contentValues = new ContentValues();
			contentValues.put("Tri_Id", INVOICE);
			contentValues.put("Id", getSystemCurrentTimeinsalesreturnwithin());
			//	contentValues.put("Invoice_No", INVOICE);
			contentValues.put("Store_Id", PersistenceManager.getStoreId(mycontext));
			contentValues.put("Reason_Of_Return", Item);
			contentValues.put("Grand_Total", salesreturndetail.getSaletotal());
			// Inserting Row
			db.insert("retail_str_sales_master_return", null, contentValues);
		}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;


	}


	public ArrayList<String> getBillno() {
		ArrayList<String> productlist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();


			Cursor productcursor = db.rawQuery("select (ret_ticket_install_register.Prefix||retail_str_sales_master.Tri_Id) AS fullBillNo from ret_ticket_install_register,retail_str_sales_master", null);
			Log.d("Sudhee", "Retrieved " + productcursor.getCount() + " Rows");
			if (productcursor.moveToFirst()) {
				do {

					productlist.add(productcursor.getString(productcursor.getColumnIndex("fullBillNo")));
					//productlist.add(productcursor.getString(productcursor.getColumnIndex(PREFIX)));

					;
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}
	// we are calling this method in our activity
	// and setting the data in particular textbox

	public boolean updateProductCom(String PRODUCTPRODUCTID, String PRODUCTSELLING, String PRODUCTPURCHASE, String PRODUCTINTERNET, String PRODUCTRELEVANT, String ACTIVE) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("S_Price", PRODUCTSELLING);
		contentValues.put("P_Price", PRODUCTPURCHASE);
		contentValues.put("Internet_Price", PRODUCTINTERNET);
		contentValues.put("Is_Prod_Rel_Int", PRODUCTRELEVANT);
		contentValues.put("Active", ACTIVE);
		db.update("retail_store_prod_com", contentValues, "Prod_Id = ? ", new String[]{String.valueOf(PRODUCTPRODUCTID)});
		return true;
	}

	public boolean updatelocalProductCpg(String PRODUCTLOCALPRODUCTID, String PRODUCTLOCALPRODUCTNAME, String PRODUCTLOCALBARCODE, String PRODUCTLOCALMRP, String PRODUCTLOCALSELLING, String PRODUCTLOCALPURCHASE, String Active) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Prod_Id", PRODUCTLOCALPRODUCTID);
		contentValues.put("Prod_Nm", PRODUCTLOCALPRODUCTNAME);
		contentValues.put("BarCode", PRODUCTLOCALBARCODE);
		contentValues.put("MRP", PRODUCTLOCALMRP);
		contentValues.put("S_Price", PRODUCTLOCALSELLING);
		contentValues.put("P_Price", PRODUCTLOCALPURCHASE);
		contentValues.put("Active", Active);
		//	contentValues.put("Active", ACTIVE);
		db.update("retail_store_prod_com", contentValues, "Prod_Id = ? ", new String[]{String.valueOf(PRODUCTLOCALPRODUCTID)});
		return true;
	}

	public boolean updatelocalProductCom(String PRODUCTLOCALPRODUCTID, String PRODUCTLOCALPRODUCTNAME, String PRODUCTLOCALBARCODE, String PRODUCTLOCALMRP, String PRODUCTLOCALSELLING, String PRODUCTLOCALPURCHASE, String Active) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Prod_Id", PRODUCTLOCALPRODUCTID);
		contentValues.put("Prod_Nm", PRODUCTLOCALPRODUCTNAME);
		contentValues.put("BarCode", PRODUCTLOCALBARCODE);
		contentValues.put("MRP", PRODUCTLOCALMRP);
		contentValues.put("S_Price", PRODUCTLOCALSELLING);
		contentValues.put("P_Price", PRODUCTLOCALPURCHASE);
		contentValues.put("Active", Active);
		//	contentValues.put("Active", ACTIVE);
		db.update("retail_store_prod_com", contentValues, "Prod_Id = ? and Prod_Nm", new String[]{String.valueOf(PRODUCTLOCALPRODUCTID)});
		return true;
	}
	//!!!!!!!!!!!************UPDATE sales Quantity************!!!!!!!!!!!!!!!!!!!!!!!!!

	/**
	 * Update the Stock Quantity based on purchased items according to the salesBillItems
	 *
	 * @param salesBillItems Arraylist of Sales Items in current sale
	 * @return True if all updates successful, else False
	 */
	public boolean updateStockQty(ArrayList<Sales> salesBillItems) {

		boolean returnval = true;


		if (salesBillItems == null) {
			return true;
		}

		SQLiteDatabase db = this.getWritableDatabase();

		//For all items in sales bill, update the stock quantity
		for (Sales sales : salesBillItems) {
			ContentValues contentValues = new ContentValues();

			float stockQuantity = sales.getStockquant();
			int saleQuantity = sales.getQuantity();

			if (saleQuantity <= stockQuantity) {

				float newStockQuantity = stockQuantity - saleQuantity;

				contentValues.put("Con_Mul_Qty", Double.toString(newStockQuantity));


			} else if (saleQuantity > stockQuantity) {
				float newStockQuantity = stockQuantity - saleQuantity;
				float newstockQuant = stockQuantity - newStockQuantity;
				float newstock = saleQuantity - newstockQuant;

				contentValues.put("Con_Mul_Qty", Double.toString(newstock));


			}


			int sqlUpdateRetval = db.update("retail_str_stock_master", contentValues, "Batch_No = ?  and " +
					"Prod_Id " +
					" = ? ", new String[]{sales.getBatchNo(), sales.getProdid()});
			if (sqlUpdateRetval < 1) {
				returnval = false;
			}
		}

		db.close();
		return returnval;
	}


	public ArrayList<SalesreturndetailWithoutPo> getProductReturnData(String idorName) {
		ArrayList<SalesreturndetailWithoutPo> productNamelist = new ArrayList<SalesreturndetailWithoutPo>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = idorName + "%";
			Cursor res = db.rawQuery("select distinct Prod_Id, Prod_Nm, MRP ,Selling_Order_Unit ,S_Price,Conv_Fact from retail_store_prod_com where "
					+ "  Prod_Nm LIKE ? AND Active ='Y' ", params);

			if (res.moveToFirst()) {
				do {
					SalesreturndetailWithoutPo salesreturndetail = new SalesreturndetailWithoutPo();
					//salesreturndetail.setSaleTransid(res.getString(res.getColumnIndex(RETURNTRANSIDs)));
					//salesreturndetail.setSaleBillno(res.getString(res.getColumnIndex(BILLNO)));
					salesreturndetail.setSaleProdid(res.getString(res.getColumnIndex(PRODUCTPRODUCTID)));
					salesreturndetail.setSaleproductname(res.getString(res.getColumnIndex(RETURNPRODUCTNAME)));
					salesreturndetail.setSalesellingprice(res.getFloat(res.getColumnIndex(RETURNSELLINGPRICE)));
					//salesreturndetail.setSaleqty(res.getFloat(res.getColumnIndex(RETURNQUANTITY)));
					salesreturndetail.setSalemrp(res.getString(res.getColumnIndex(RETURNMRP)));
					salesreturndetail.setSaleuom(res.getString(res.getColumnIndex(PRODUCTMEASUREUNITINV)));
					//salesreturndetail.setSaletotal(res.getFloat(res.getColumnIndex(RETURNGRANDTOTAL)));
					salesreturndetail.setConversionfactorreturn(res.getString(res.getColumnIndex(PRODUCTCONVERSION)));
					productNamelist.add(salesreturndetail);
				} while (res.moveToNext());

			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return productNamelist;
	}


	/*public boolean updatequantity(ArrayList<Inventoryproductmodel> salesBillItems) {

		boolean returnval = true;


		if (salesBillItems == null) {
			return true;
		}

		SQLiteDatabase db = this.getWritableDatabase();

		//For all items in sales bill, update the stock quantity
		for (Inventoryproductmodel sales : salesBillItems) {
			ContentValues contentValues = new ContentValues();
			try {
				float total = Float.parseFloat(sales.getMrp());
				Float conversionfactor = sales.getConvfact();
				Float stockQuantity = sales.getStock();
				int prodQuantity = sales.productQuantity;

				float newStockQuantity = stockQuantity + prodQuantity;
				float multilplefactor = conversionfactor * prodQuantity;
				float totalamount = total * prodQuantity;
				if (newStockQuantity < 0) {
					newStockQuantity = 0;
				}

				if (multilplefactor < 0) {
					multilplefactor = 0;
				}

				if (totalamount < 0) {
					totalamount = 0;
				}

				contentValues.put("Qty", Double.toString(newStockQuantity));
				contentValues.put("Con_Mul_Qty", Double.toString(multilplefactor));


				int sqlUpdateRetval = db.update("retail_str_stock_master", contentValues, "Batch_No = ?  and " +
						"Prod_Id " +
						" = ? ", new String[]{sales.getBatchno(), sales.getProductId()});
				if (sqlUpdateRetval < 1) {
					returnval = false;
				}

			} catch (NumberFormatException ex) {
				ex.printStackTrace();
			}
		}

		db.close();
		return returnval;
	}

*/
	public boolean updatebatchnowithpo(ArrayList<PurchaseProductModelwithpo> salesBillItems) {

		boolean returnval = true;


		if (salesBillItems == null) {
			return true;
		}

		SQLiteDatabase db = this.getWritableDatabase();

		//For all items in sales bill, update the stock quantity
		for (PurchaseProductModelwithpo invbatchno : salesBillItems) {
			ContentValues contentValues = new ContentValues();

			/*float stockQuantity = sales.getStockquant();
			int saleQuantity = sales.getQuantity();

			float newStockQuantity = stockQuantity - saleQuantity;
			if (newStockQuantity < 0) {
				newStockQuantity = 0;
			}*/

			String batchno = invbatchno.getExp_Date();
			contentValues.put("Exp_Date", batchno);

			int sqlUpdateRetval = db.update("retail_str_stock_master", contentValues, "Batch_No = ?  and " +
							"Prod_Id " +
							"= ? ",
					new String[]{invbatchno.getBatch_No(), invbatchno.getProductId()});
			if (sqlUpdateRetval < 1) {
				returnval = false;
			}
		}

		db.close();
		return returnval;
	}


	//*******************************retrive data for retail_store*********************************************

	public ArrayList<String> getdataemp() {
		ArrayList<String> updatelist3 = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_store ", null);
		if (res.moveToFirst())
			Log.e(TAG, "getTableAsString called");
		{
			do {
				updatelist3.add(res.getString(res.getColumnIndex(STORE_ID_STORE)));
				Log.e(TAG, "inside do function");
			} while (res.moveToNext());
		}

		return updatelist3;
	}


	//**************************retail_store***********************************************************
	public Cursor getdatastore(String Store_Id_store) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select Store_Id from retail_store where Store_Id = " + Store_Id_store + "", null);
		return res;
	}

	;;


	public ArrayList<Registeremployeesmodel> getusername() {

		ArrayList<Registeremployeesmodel> ponamelist = new ArrayList<Registeremployeesmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
           /* String []params=new String[1];
            params[0]=name+"%";
          */
			Cursor cursor = db.rawQuery("select distinct Usr_Nm  from retail_employees "
					, null);
			if (cursor.moveToFirst()) {
				do {
					Registeremployeesmodel pm = new Registeremployeesmodel();
					pm.setUsername(cursor.getString(cursor.getColumnIndex(USER_NAME)));
					ponamelist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return ponamelist;
	}


//***********************************update for employees table**********************************************

	public boolean updateemployees(String STORE_ID, String USER_NAME, String FIRSTNAME, String LASTNAME, String PASSWORD, String CONFIRMPASSWORD, String ACTIVE) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("First_Name", FIRSTNAME);
		contentValues.put("Last_Name", LASTNAME);
		contentValues.put("Password", PASSWORD);
		contentValues.put("Confirm_Password", CONFIRMPASSWORD);
		contentValues.put("Active", ACTIVE);
		db.update("retail_employees", contentValues, "Store_Id = ? and " + "Usr_Nm  " + "= ?", new String[]{String.valueOf(STORE_ID), String.valueOf(USER_NAME)});
		return true;
	}


	public ArrayList<Registeremployeesmodel> getdatafoeregister(String name) {
		ArrayList<Registeremployeesmodel> updatelist3 = new ArrayList<Registeremployeesmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor res = db.rawQuery("select Usr_Nm, First_Name, Last_Name, Password, Confirm_Password, Active  from retail_employees where "
							+ " Usr_Nm like ? "
					, params);
			if (res.moveToFirst())

				Log.e(TAG, "getTableAsString called");
			{
				do {
					Registeremployeesmodel rm = new Registeremployeesmodel();
					rm.setFirstname(res.getString(res.getColumnIndex(FIRST_NAME)));
					rm.setLastname(res.getString(res.getColumnIndex(LAST_NAME)));
					rm.setPassword(res.getString(res.getColumnIndex(PASSWORD)));
					rm.setConfirmpassword(res.getString(res.getColumnIndex(CONFIRMPASSWORD)));
					rm.setActive(res.getString(res.getColumnIndex(ACTIVE)));
					updatelist3.add(rm);
					Log.e(TAG, "inside do function");
				} while (res.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}

		return updatelist3;
	}

	public void saveGranddataintoGrnMaster(String result, String ponumberstoselectProducts, String VendorName, String GrandTotal) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		ContentValues values = new ContentValues();
		values.put("Store_Id", PersistenceManager.getStoreId(mycontext));
		values.put("Grn_Id", result);
		values.put("Vend_Nm", VendorName);
		values.put("Po_No", ponumberstoselectProducts);
		values.put("Grand_Amount", GrandTotal);
		values.put("Flag", "0");
		db.insert("retail_str_grn_master", null, values);

		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted.into retail_str_grn_master..");


		return;
	}

	public ArrayList<VendorModel> getVendorsForVendorPayment() {

		ArrayList<VendorModel> vendorNamelist = new ArrayList<VendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
      /*String []params=new String[1];
      params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct Vend_Nm from retail_str_grn_master where Flag like '0%' ", null);
			if (cursor.moveToFirst()) {
				do {
					VendorModel pm = new VendorModel();
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRN_VENDORNAME)));
					vendorNamelist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorNamelist;
	}

	public ArrayList<String> getGrnNumber(String VendorName) {
		ArrayList<String> LastInvoicelist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
   /* String []params=new String[2];
      params[0]=VendorName+"%";
      params[1]="0";*/
			Cursor cursor = db.rawQuery("select distinct Grn_Id,Vend_Nm,Flag from retail_str_grn_master where "
							+ "  Flag like '0%' and Vend_Nm  like '" + VendorName + "' ORDER BY Po_No DESC limit 3"
					, null);

			if (cursor.moveToFirst()) {
				do {
					LastInvoicelist.add(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRNID)));
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return LastInvoicelist;
	}


	public void savedirectPayment(String vendorselected, String po_numberselected, String Amount, String Payment_ID, String Userpayamount, String DueAmount) {
		SQLiteDatabase db = this.getReadableDatabase();

		if (Amount == null) {
			return;
		}
		ContentValues contentValues = new ContentValues();
		contentValues.put("vend_Dstr_Nm", vendorselected);
		contentValues.put("Pay_Id", po_numberselected);
		contentValues.put("Amount", Amount);
		contentValues.put("Payment_Id", Payment_ID);
		contentValues.put("Received_Amount", Userpayamount);
		contentValues.put("Due_Amount", DueAmount);
		db.insert("tmp_vend_dstr_payment", null, contentValues);


		db.close(); // Closing database connection
		Log.e("Database Operation", "Data  Payment bycash table inserted...");
		return;
	}


	public void savedirectPaymentwithpobycheque(String PurchaseOrderNo, String vendorselected, String amount, String bankNameSelected, String ChequeNo, String Payment_Id, String UserPayment, String AmountDue) {
		SQLiteDatabase db = this.getWritableDatabase();
		if (amount == null) {
			return;
		}
		ContentValues contentValues = new ContentValues();
		contentValues.put("Pay_Id", PurchaseOrderNo);
		contentValues.put("vend_Dstr_Nm", vendorselected);
		contentValues.put("Payment_Id", Payment_Id);
		contentValues.put("Amount", amount);
		contentValues.put("Bank_Name", bankNameSelected);
		contentValues.put("Cheque_No", ChequeNo);
		contentValues.put("Received_Amount", UserPayment);
		contentValues.put("Due_Amount", AmountDue);
		db.insert("tmp_vend_dstr_payment", null, contentValues);

		//}
		db.close(); // Closing database connection
		Log.e("Database Operation", "Data  Payment byCheque table inserted...");
		return;
	}


	public ArrayList<Companylistmodel> getcompanylist() {
		ArrayList<Companylistmodel> productlist = new ArrayList<Companylistmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();


          /* Cursor productcursor = db.rawQuery("select Prod_Id,Prod_Nm , MRP, S_Price, P_Price, Unit_Of_Measure,Pack_Unit_1 from retail_store_prod where"
                    + " Prod_Id  like ? or  Prod_Nm  like ? "
                    , params);
*/
			Cursor productcursor = db.rawQuery("select * from retail_comp_btl "
					, null);


			if (productcursor.moveToFirst()) {
				do {
					Companylistmodel pm = new Companylistmodel();
					pm.setCompname(productcursor.getString(productcursor.getColumnIndex(COLOUM_COMP_NM)));
					pm.setTargetamount(productcursor.getString(productcursor.getColumnIndex(COLOUM_COMP_TARGET_AMOUNT)));
					pm.setBTLdesc(productcursor.getString(productcursor.getColumnIndex(COLOUM_COMP_BTL_DESC)));
					pm.setTargetvalue(productcursor.getString(productcursor.getColumnIndex(COLOUM_COMP_TARGET_VALUE)));
					pm.setStartdate(productcursor.getString(productcursor.getColumnIndex(COLOUM_COMP_START_DATE)));
					pm.setEnddate(productcursor.getString(productcursor.getColumnIndex(COLOUM_COMP_END_DATE)));


					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}


	public ArrayList<Mfglistmodel> getmfglist() {
		ArrayList<Mfglistmodel> productlist = new ArrayList<Mfglistmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();


          /* Cursor productcursor = db.rawQuery("select Prod_Id,Prod_Nm , MRP, S_Price, P_Price, Unit_Of_Measure,Pack_Unit_1 from retail_store_prod where"
                    + " Prod_Id  like ? or  Prod_Nm  like ? "
                    , params);
*/
			Cursor productcursor = db.rawQuery("select * from retail_mfg_btl "
					, null);


			if (productcursor.moveToFirst()) {
				do {
					Mfglistmodel pm = new Mfglistmodel();
					pm.setMfgname(productcursor.getString(productcursor.getColumnIndex(COLOUM_MFG_NM)));
					pm.setTargetamount(productcursor.getString(productcursor.getColumnIndex(COLOUM_MFG_TARGET_AMOUNT)));
					pm.setBTLdesc(productcursor.getString(productcursor.getColumnIndex(COLOUM_MFG_BTL_DESC)));
					pm.setTargetvalue(productcursor.getString(productcursor.getColumnIndex(COLOUM_MFG_TARGET_VALUE)));
					pm.setStartdate(productcursor.getString(productcursor.getColumnIndex(COLOUM_MFG_START_DATE)));
					pm.setEnddate(productcursor.getString(productcursor.getColumnIndex(COLOUM_MFG_END_DATE)));


					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}

	public ArrayList<String> getthereasonsfromVendorReturn() {

		ArrayList<String> reasonslist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			/*String []params=new String[1];
			params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct Reason_for_Rejection from retail_store_vend_reject ORDER BY Reason_for_Rejection DESC limit 3 ", null);
			if (cursor.moveToFirst()) {
				do {
					reasonslist.add(cursor.getString(cursor.getColumnIndex(COLUMN_FOR_VEND_RETURN_REJECTION)));

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return reasonslist;
	}


	public ArrayList<VendorReturnModel> GetDataToreturn(String userEnteredNumber) {
		ArrayList<VendorReturnModel> GetDataUsingGrnID = new ArrayList<VendorReturnModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = userEnteredNumber + "%";
			Cursor NumberCursor = db.rawQuery(" select Prod_Id,Prod_Nm,Exp_Date,Batch_No,Mrp,Qty,P_Price,S_Price,Amount,Uom,Grn_Id from retail_str_stock_master where "
							+ " Prod_Nm  like ?  "
					, params);
			if (NumberCursor.moveToFirst()) {
				do {
					VendorReturnModel vendorReturnModel = new VendorReturnModel();
					vendorReturnModel.setProductId(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_PROD_ID)));
					vendorReturnModel.setProductName(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_PROD_NAME)));
					vendorReturnModel.setBatchno(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_BATCHNO)));
					vendorReturnModel.setExpdate(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_EXP_DATE)));
					vendorReturnModel.setMrp(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_MRP)));
					vendorReturnModel.setPprice(NumberCursor.getFloat(NumberCursor.getColumnIndex(COLUMN_STOCK_PPRICE)));
					vendorReturnModel.setSprice(NumberCursor.getFloat(NumberCursor.getColumnIndex(COLUMN_STOCK_SPRICE)));
					vendorReturnModel.setUOM(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_UOM)));
					vendorReturnModel.setStock(NumberCursor.getFloat(NumberCursor.getColumnIndex(COLUMN_STOCK_QTY)));
					vendorReturnModel.setTotal(NumberCursor.getFloat(NumberCursor.getColumnIndex(COLUMN_STOCK_AMOUNT)));
					GetDataUsingGrnID.add(vendorReturnModel);

				} while (NumberCursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return GetDataUsingGrnID;
	}

	public ArrayList<VendorReturnModel> getAllVendorreturndata(String userTypedproduct) {
		ArrayList<VendorReturnModel> GetDataUsingGrnID = new ArrayList<VendorReturnModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = userTypedproduct + "%";
			Cursor NumberCursor = db.rawQuery(" select Prod_Id,Prod_Nm,Exp_Date,Batch_No,Mrp,Qty,P_Price,S_Price,Amount,Uom,Grn_Id,Free_Goods from retail_str_stock_master where "
							+ " Grn_Id  like ?  "
					, params);
			if (NumberCursor.moveToFirst()) {
				do {
					VendorReturnModel vendorReturnModel = new VendorReturnModel();
					vendorReturnModel.setProductId(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_PROD_ID)));
					vendorReturnModel.setProductName(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_PROD_NAME)));
					vendorReturnModel.setBatchno(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_BATCHNO)));
					vendorReturnModel.setExpdate(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_EXP_DATE)));
					vendorReturnModel.setMrp(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_MRP)));
					vendorReturnModel.setPprice(NumberCursor.getFloat(NumberCursor.getColumnIndex(COLUMN_STOCK_PPRICE)));
					vendorReturnModel.setSprice(NumberCursor.getFloat(NumberCursor.getColumnIndex(COLUMN_STOCK_SPRICE)));
					vendorReturnModel.setUOM(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_UOM)));
					vendorReturnModel.setStock(NumberCursor.getFloat(NumberCursor.getColumnIndex(COLUMN_STOCK_QTY)));
					vendorReturnModel.setProductQuantity(NumberCursor.getFloat(NumberCursor.getColumnIndex(COLUMN_STOCK_QTY)));
					vendorReturnModel.setTotal(NumberCursor.getFloat(NumberCursor.getColumnIndex(COLUMN_STOCK_AMOUNT)));
					vendorReturnModel.setFreeQty(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_FREE_GOODS)));
					GetDataUsingGrnID.add(vendorReturnModel);

				} while (NumberCursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return GetDataUsingGrnID;

	}

	public ArrayList<String> getVendorNameForAuto(String userEnteredNumber) {
		ArrayList<String> GetVendorNameForAutocom = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = userEnteredNumber + "%";
			Cursor NumberCursor = db.rawQuery(" select Vend_Nm from retail_str_grn_master where "
							+ " Grn_Id  like ?  "
					, params);
			if (NumberCursor.moveToFirst()) {
				do {
					GetVendorNameForAutocom.add(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_MASTER_GRN_VENDORNAME)));

				} while (NumberCursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return GetVendorNameForAutocom;
	}

	public void InsertDataforVendorReturn(String vendor_return_id, ArrayList<VendorReturnModel> list, String vendororDistributorNameAutoComplete, String reasonSelected) {
		SQLiteDatabase db = this.getWritableDatabase();

		if (list == null || vendor_return_id == null || vendororDistributorNameAutoComplete == null || reasonSelected == null) {
			return;
		}

		for (VendorReturnModel prod : list) {

			ContentValues contentValues = new ContentValues();
			contentValues.put("vendor_Return_Id", vendor_return_id);
			contentValues.put("Vendor_Nm", vendororDistributorNameAutoComplete);
			contentValues.put("Reason_Of_Return", reasonSelected);
			contentValues.put("Prod_Nm", prod.getProductName());
			contentValues.put("Exp_Date", prod.getExpdate());
			contentValues.put("P_Price", prod.getPprice());
			contentValues.put("Batch_No", prod.getBatchno());
			contentValues.put("UOM", prod.getUOM());
			if (prod.getProductQuantity() != 0.0f) {
				contentValues.put("Qty", prod.getProductQuantity());
			} else {
				Log.e("Database Operation", "row Not  inserted...");
				continue;
			}
			contentValues.put("Total", prod.getTotal());
			db.insert("retail_str_vendor_detail_return", null, contentValues);


		}

		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;

	}

	public void InsertMasterDataForVendorReturn(String vendor_return_id, String VendorName, String reasonSelected, String grandAmount) {
		SQLiteDatabase db = this.getReadableDatabase();

		if (vendor_return_id == null || reasonSelected == null || grandAmount == null) {
			return;
		}
		ContentValues contentValues = new ContentValues();
		contentValues.put("vendor_Return_Id", vendor_return_id);
		contentValues.put("Vendor_Nm", VendorName);
		contentValues.put("Reason_Of_Return", reasonSelected);
		contentValues.put("Return_Amount", grandAmount);

		db.insert("retail_str_vendor_Master_return", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row  in Master Table inserted...");
		return;

	}


	public ArrayList<String> getGrandTotalforVendorPayment(String po_numberselected) {
		ArrayList<String> GetGrandTotal = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = po_numberselected + "%";
			Cursor NumberCursor = db.rawQuery(" select Grand_Amount from retail_str_grn_master where "
							+ " Grn_Id  like ?  "
					, params);

			if (NumberCursor.moveToFirst()) {
				do {
					GetGrandTotal.add(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_MASTER_GRN_GRAND_AMOUNT)));
				} while (NumberCursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return GetGrandTotal;

	}


	public boolean UpdateStockQtyForVendorReturn(ArrayList<VendorReturnModel> salesBillItems) {

		boolean returnval = true;


		if (salesBillItems == null) {
			return true;
		}

		SQLiteDatabase db = this.getWritableDatabase();

		//For all items in sales bill, update the stock quantity
		for (VendorReturnModel sales : salesBillItems) {
			ContentValues contentValues = new ContentValues();

			Float stockQuantity = sales.getStock();
			Float prodQuantity = sales.productQuantity;

			float newStockQuantity = stockQuantity - prodQuantity;
			if (newStockQuantity < 0) {
				newStockQuantity = 0;
			}
			contentValues.put("Qty", Double.toString(newStockQuantity));
			int sqlUpdateRetval = db.update("retail_str_stock_master", contentValues, "Batch_No = ?  and " +
					"Prod_Id " +
					" = ? ", new String[]{sales.getBatchno(), sales.getProductId()});
			if (sqlUpdateRetval < 1) {
				returnval = false;
			}
		}

		db.close();
		return returnval;
	}


	public ArrayList<String> getvendorsearchforSpinner() {

		ArrayList<String> vendorNamelist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
         /*
         String []params=new String[1];
         params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct Vendor_Nm from retail_str_po_detail", null);
			if (cursor.moveToFirst()) {
				do {
					vendorNamelist.add(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));

				} while (cursor.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return vendorNamelist;


	}

	public ArrayList<String> getimeino() {
		ArrayList<String> imeilist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
         /*
         String []params=new String[1];
         params[0]=name+"%";*/
		Cursor cursor = db.rawQuery("select IMEI_Number from ret_ticket_install_register", null);
		if (cursor.moveToFirst()) {
			do {
				imeilist.add(cursor.getString(cursor.getColumnIndex(IMEI)));

			} while (cursor.moveToNext());
		}
		return imeilist;

	}


	public ArrayList<String> getprefix(String str) {
		ArrayList<String> prefixlist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();

		String[] params = new String[1];
		params[0] = str + "%";
		Cursor cursor = db.rawQuery("select Prefix from ret_ticket_install_register where "
				+ " IMEI_Number LIKE ?", params);
		if (cursor.moveToFirst()) {
			do {
				///	prefixlist.add(cursor.getString(cursor.getColumnIndex(IMEI)));
				prefixlist.add(cursor.getString(cursor.getColumnIndex(PREFIX)));


			} while (cursor.moveToNext());
		}
		return prefixlist;

	}

	public boolean updateVendorinpurchase(String VENDORID, String VENDORACTIVE) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Active", VENDORACTIVE);
		db.update("retail_vend_dstr", contentValues, "Vend_Dstr_Id = ? ", new String[]{String.valueOf(VENDORID)});
		return true;
	}


	public boolean updatelocalVendorinpurchase(String LOCALVENDORID,String LOCALVENDORNAME,String LOCALVENDORADDRESS,String LOCALVENDORCITY,String LOCALVENDORCOUNTRY,String LOCALVENDORZIP,String Invspinvalue, String LOCALVENDORACTIVE) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();


		contentValues.put("Vend_Dstr_Nm", LOCALVENDORNAME);
		contentValues.put("Vend_Dstr_Add",LOCALVENDORADDRESS);
		contentValues.put("Vend_Dstr_City",LOCALVENDORCITY);
		contentValues.put("Vend_Dstr_Ctr",LOCALVENDORCOUNTRY);
		contentValues.put("Vend_Dstr_Zip",LOCALVENDORZIP);
		contentValues.put("Vend_Dstr_Inv",Invspinvalue);
		contentValues.put("Active", LOCALVENDORACTIVE);
		int SqlValue = db.update("retail_vend_dstr", contentValues, "Vend_Dstr_Id = ? ", new String[]{String.valueOf(LOCALVENDORID)});
		Log.e("Update for LocalVendor", LOCALVENDORACTIVE);
		if (SqlValue < 1) {
			Log.e("UpdateFailLocalVendor:", String.valueOf(SqlValue));
			return false;
		}
		return true;

	}

	public ArrayList<CreditNote> getcreditno(String userTypedInvoiceno) {
		ArrayList<CreditNote> creditNoteslist = new ArrayList<CreditNote>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = userTypedInvoiceno + "%";
			Cursor creditnotecursor = db.rawQuery("select Tri_Id,Grand_Total from retail_str_sales_master_return where "
							+ " Tri_Id  like ? "
					, params);
			if (null != creditnotecursor && creditnotecursor.moveToFirst() && creditnotecursor.getCount() > 0) {
				do {
					CreditNote cn = new CreditNote();
					cn.setReturnInvoiceno(creditnotecursor.getString(creditnotecursor.getColumnIndex(TRANS_ID)));
					cn.setCreditnotevalue(creditnotecursor.getString(creditnotecursor.getColumnIndex(GRAND_TOTAL)));
					creditNoteslist.add(cn);

					//cn.getReturnCreatedon(creditnotecursor.getString(creditnotecursor.getColumnIndex(BILLNO)));
				} while (creditnotecursor.moveToNext());

			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return creditNoteslist;
	}

	public Boolean updateintoGrnMasterTableForVendorPayment(String vendorselected, String po_numberselected, String Amount) {
		boolean returnval = true;
		SQLiteDatabase db = this.getReadableDatabase();
		ContentValues value = new ContentValues();
		value.put("Flag", "1");
		int sqlUpdateRetval = db.update("retail_str_grn_master", value,
				" vend_Nm = ? and  " + "Grn_Id " + " = ? "
				, new String[]{String.format(vendorselected), String.format(po_numberselected)});

		if (sqlUpdateRetval < 1) {
			returnval = false;
		}
		db.close();
		return returnval;
	}

	public Boolean updateintoGrnMasterForDueAmount(String vendorselected, String po_numberselected, String Amount) {
		boolean returnval = true;
		SQLiteDatabase db = this.getReadableDatabase();
		ContentValues value = new ContentValues();
		value.put("Grand_Amount", Amount);
		int sqlUpdateRetval = db.update("retail_str_grn_master", value,
				" vend_Nm = ? and  " + "Grn_Id " + " = ? "
				, new String[]{String.format(vendorselected), String.format(po_numberselected)});

		if (sqlUpdateRetval < 1) {
			returnval = false;
		}
		db.close();
		return returnval;
	}


	public ArrayList<VendorModel> getVendorsforVendorReturn() {

		ArrayList<VendorModel> vendorNamelist = new ArrayList<VendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
      /*String []params=new String[1];
      params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct Vend_Nm from retail_str_grn_master  where  "
					+ " Flag like '1'", null);
			if (cursor.moveToFirst()) {
				do {
					VendorModel pm = new VendorModel();
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRN_VENDORNAME)));
					vendorNamelist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorNamelist;
	}

	public ArrayList<String> getLastInvoicesForVendorReturn(String VendorName) {
		ArrayList<String> LastInvoicelist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
   /* String []params=new String[2];
      params[0]=VendorName+"%";
      params[1]="0";*/
			Cursor cursor = db.rawQuery("select distinct Grn_Id,Vend_Nm,Flag from retail_str_grn_master where "
							+ "Flag like '1' and Vend_Nm  = '" + VendorName + "' ORDER BY Po_No DESC limit 20"
					, null);

			if (cursor.moveToFirst()) {
				do {
					LastInvoicelist.add(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRNID)));
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return LastInvoicelist;
	}


	public void linediscountitem(ArrayList<line_item_Product_Model> list) {
		SQLiteDatabase db = this.getWritableDatabase();
		if (list == null) {
			return;
		}
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		for (line_item_Product_Model prod : list) {
			ContentValues values = new ContentValues();
			//values.put("Store_Id", PersistenceManager.getStoreId(mycontext));
			values.put("Line_Item_Nm", prod.getProductname());
			values.put("Line_Item_Disc", prod.getDiscountitem());
			values.put("lineitem_Id", getSystemCurrentTime());
			//   values.put(";Prod_Id",prod.getProductId());
			// Inserting Row
			db.insert("retail_str_lineitem_disc", null, values);
		}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}


	public ArrayList<line_item_Product_Model> getLineItem() {

		ArrayList<line_item_Product_Model> returnlist = new ArrayList<line_item_Product_Model>();
		try {

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select distinct Line_Item_Nm from retail_str_lineitem_disc", null);
			if (res.moveToFirst()) {
				do {


					line_item_Product_Model mline_item_product_model=new line_item_Product_Model();
					mline_item_product_model.setProductname(res.getString(res.getColumnIndex(LINEITEMNAME)));
					returnlist.add(mline_item_product_model);


				}
				while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return returnlist;
	}



	public void saveTop15Product(ArrayList<Topfullproductmodel> list) {
		SQLiteDatabase db = this.getWritableDatabase();

		if (list.size() <= 15 || list.size() <= 0) {
			for (Topfullproductmodel prod : list) {

				PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
				PersistenceManager.getStoreId(mycontext);
				ContentValues values = new ContentValues();
				values.put("Store_Id", PersistenceManager.getStoreId(mycontext));
				values.put("Prod_Nm", prod.getProductname());
				values.put("Prod_Short_Nm", prod.getShortname());
				values.put("Prod_Id", prod.getProductId());
				// Inserting Row
				db.insert("retail_top_product", null, values);

			}

		} else if (list.size() == 15)

		{


			for (Topfullproductmodel prod : list) {
				PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
				PersistenceManager.getStoreId(mycontext);
				ContentValues values = new ContentValues();
				values.put("Store_Id", PersistenceManager.getStoreId(mycontext));
				values.put("Prod_Nm", prod.getProductname());
				values.put("Prod_Short_Nm", prod.getShortname());
				values.put("Prod_Id", prod.getProductId());
				db.update("retail_top_product", values, "Prod_Id = ? ", new String[]{prod.getProductId()});


			}

		}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;

	}


	public ArrayList<Topfullproductmodel> gettopProductSpinner() {

		ArrayList<Topfullproductmodel> returnlist = new ArrayList<Topfullproductmodel>();
		try {

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select distinct Prod_Nm from retail_top_product", null);
			if (res.moveToFirst()) {
				do {
					Topfullproductmodel topfullproductmodel=new Topfullproductmodel();

					topfullproductmodel.setProductname(res.getString(res.getColumnIndex(COLUMN_TOP_PRODUCT_NAME)));
					returnlist.add(topfullproductmodel);
				}
				while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return returnlist;
	}


	public void DeleteRecord(String remove) {
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("delete from " + TABLE_NAME_TOP_PRODUCT + " where " + TOP_PRODUCT_NAME_TOP + " = '" + remove+ "'");
		db.close();

	}


	public void DeleteRecordLineItem(String contact) {
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("delete from " + LINEITEMTABLENAME + " where " + LINEITEMNAME + " = '" + contact+ "'");
		db.close();
	}





	ArrayList<Topfullproductmodel> topproductnameorid(String NameorId) {
		ArrayList<Topfullproductmodel> vendorlist = new ArrayList<Topfullproductmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = NameorId + "%";
			params[1] = NameorId + "%";

			Cursor cursor = db.rawQuery("select Prod_Nm, Prod_Id from retail_store_prod_com where"
							+ " Prod_Nm like ?  or  Prod_Id  like ? "
					, params);

           /* Cursor cursor = db.rawQuery("select Store_Id from retail_store_prod_com  where"
                    + " Store_Id like ? "
                    , params);
*/
			if (cursor.moveToFirst()) {
				do {
					Topfullproductmodel pm = new Topfullproductmodel();

					pm.setProductId(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_ID)));
					pm.setProductname(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_NAME)));
                   /* pm.setMrp(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_MRP)));
                    pm.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_PPRICE)));
                    pm.setSprice(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_SPRICE)));
                    pm.setSou(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_SOU)));
*/


					vendorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;
	}


	ArrayList<line_item_Product_Model> lineproductnameorid(String NameorId) {
		ArrayList<line_item_Product_Model> vendorlist = new ArrayList<line_item_Product_Model>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = NameorId + "%";
			params[1] = NameorId + "%";

			Cursor cursor = db.rawQuery("select Prod_Nm, Prod_Id from retail_store_prod_com where"
							+ " Prod_Nm like ?  or  Prod_Id  like ? "
					, params);

           /* Cursor cursor = db.rawQuery("select Store_Id from retail_store_prod_com  where"
                    + " Store_Id like ? "
                    , params);
*/
			if (cursor.moveToFirst()) {
				do {
					line_item_Product_Model pm = new line_item_Product_Model();

					pm.setProductId(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_ID)));
					pm.setProductname(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_NAME)));
                   /* pm.setMrp(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_MRP)));
                    pm.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_PPRICE)));
                    pm.setSprice(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_SPRICE)));
                    pm.setSou(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_SOU)));
*/


					vendorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;
	}

	public ArrayList<VendorReportModel> getVendorNameprocurement(String name) {
		ArrayList<VendorReportModel> vendornamelist = new ArrayList<VendorReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct Vendor_Nm from retail_str_po_detail  where"
					+ " Vendor_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					VendorReportModel vm = new VendorReportModel();
					vm.setVendor_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vendornamelist.add(vm);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendornamelist;
	}

	//////////////////////////////////////////////get DATA FROM  retail_str_po_detail TABLE//////////////////////////////////////////////////

	public ArrayList<VendorReportModel> getVendorReportprocurement(String PoNo) {

		ArrayList<VendorReportModel> vendorlist = new ArrayList<VendorReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = PoNo + "%";
			Cursor cursor = db.rawQuery("select distinct Po_No,Vendor_Nm,Total from retail_str_po_detail  where"
							+ " Vendor_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					VendorReportModel vm = new VendorReportModel();
					vm.setPo_No(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_PO_NO)));
					vm.setVendor_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_TOTAL)));
					vendorlist.add(vm);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;
	}
//////////////////////////////////////////////// get DATA From year and Month For Purchase REPORT//////////////////////////////////////////////////////////


	public ArrayList<VendorReportModel> PurchaseDataForMonth(String Value1, String Value2) {
		ArrayList<VendorReportModel> OldInvoiceData = new ArrayList<VendorReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor productcursor = db.rawQuery("select distinct Po_No,Vendor_Nm,Total from retail_str_po_detail where "
					+ " LastUpdate between '" + Value1 + "' AND '" + Value2 + "'", null);
			if (productcursor.moveToFirst()) {
				do {
					VendorReportModel vm = new VendorReportModel();
					vm.setPo_No(productcursor.getString(productcursor.getColumnIndex(COLUMN_MASTER_PO_NO)));
					vm.setVendor_Nm(productcursor.getString(productcursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vm.setTotal(productcursor.getString(productcursor.getColumnIndex(COLUMN_TOTAL)));
					OldInvoiceData.add(vm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return OldInvoiceData;
	}


/////////////////////////////////////////////////////get all data from retail_str_po_detail ////////////////////////////////////////////////////////////////////////////

	public ArrayList<PurchaseProductReportModel> getallPurchasedata(String Value) {
		ArrayList<PurchaseProductReportModel> getpurchaselist = new ArrayList<PurchaseProductReportModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = Value + "%";
		Cursor cursor = db.rawQuery("select Prod_Nm,P_Price,Qty,Total,Uom,LastUpdate from retail_str_po_detail where "
						+ "  Po_No like ? "
				, params);
		if (cursor.moveToFirst()) {
			do {
				PurchaseProductReportModel pm = new PurchaseProductReportModel();
				pm.setProductName(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
				pm.setProductPrice(cursor.getFloat(cursor.getColumnIndex(P_PRICE)));
				pm.setTotal(cursor.getFloat(cursor.getColumnIndex(COLUMN_TOTAL)));
				pm.setUom(cursor.getString(cursor.getColumnIndex(COLUMN_UOM)));
				pm.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_LAST)));
				pm.setProductQuantity(cursor.getFloat(cursor.getColumnIndex(COLUMN_QTY)));
				getpurchaselist.add(pm);

			} while (cursor.moveToNext());
		}
		return getpurchaselist;
	}

	public void tempsavesalesListdetail(String TRANS_ID, ArrayList<Sales> list) {
		SQLiteDatabase db = this.getWritableDatabase();

		if (list == null) {
			return;
		}

		for (Sales sales : list) {
			ContentValues contentValues = new ContentValues();
			int Quants = sales.getQuantity();
			Float stockquant = sales.getStockquant();


			contentValues.put("Tri_Id", TRANS_ID);
			contentValues.put("Prod_Nm", sales.getProductName());
			contentValues.put("Batch_No", sales.getBatchNo());
			contentValues.put("Exp_Date", sales.getExpiry());
			contentValues.put("S_Price", sales.getSPrice());
			contentValues.put("Qty", sales.getQuantity());
			contentValues.put("Res_Stock", sales.getQuantity());
			contentValues.put("MRP", sales.getMrp());
			contentValues.put("UOM", sales.getUom());
			contentValues.put("Stock", sales.getStockquant());
			contentValues.put("Total", sales.getTotal());
			db.insert("tmp_retail_str_sales_detail", null, contentValues);
		}

		db.close(); // Closing database connection
		Log.e("Database Operation for", "row inserted...");
		return;
	}


	public ArrayList<Sales> getallholdinvoicedata(String data) {
		ArrayList<Sales> holdbilllist = new ArrayList<Sales>();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = data + "%";

		//Cursor productcursor = db.rawQuery("select a.Tri_Id,a.Prod_Nm,a.Batch_No,a.Exp_Date,a.S_Price,a.Qty,a.MRP,a.UOM,a.Stock,a.Total from tmp_retail_str_sales_detail a,tmp_retail_str_sales_master b where a.Tri_Id = b.Tri_Id and a.Tri_Id Like ? ",params);		if (productcursor.moveToFirst()) {
		Cursor productcursor = db.rawQuery("select Tri_Id,Prod_Nm,Batch_No,Exp_Date,S_Price,Qty,MRP,UOM,Stock,Total from tmp_retail_str_sales_detail where"
				+ " Tri_Id Like ? ",params);
		if (productcursor.moveToFirst()) {
			do {
				Sales saleslist = new Sales();
				saleslist.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCT_NAME)));
				saleslist.setBatchNo(productcursor.getString(productcursor.getColumnIndex(BATCH_NO)));
				saleslist.setExpiry(productcursor.getString(productcursor.getColumnIndex(EXPIRY)));
				saleslist.setQuantity(productcursor.getInt(productcursor.getColumnIndex(QUANTITY)));
				saleslist.setMrp(productcursor.getFloat(productcursor.getColumnIndex(MRP)));
				saleslist.setStockquant(productcursor.getFloat(productcursor.getColumnIndex(STOCK)));
				saleslist.setTrans_id(productcursor.getString(productcursor.getColumnIndex(TRANS_ID)));
				saleslist.setTotal(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_TOTAL)));
				saleslist.setUom(productcursor.getString(productcursor.getColumnIndex(UOM)));
				saleslist.setSPrice(productcursor.getFloat(productcursor.getColumnIndex(S_PRICE)));


				holdbilllist.add(saleslist);

			} while (productcursor.moveToNext());
		}
		return holdbilllist;
	}


	public ArrayList<String> getTransidofholdbill() {
		ArrayList<String> imeilist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select distinct Tri_Id from tmp_retail_str_sales_detail", null);
		if (cursor.moveToFirst()) {
			do {
				imeilist.add(cursor.getString(cursor.getColumnIndex(TRANS_ID)));

			} while (cursor.moveToNext());
		}
		return imeilist;

	}

	public ArrayList<PurchaseProductModelwithpo> getProductdataforInventory(String idorName) {
		ArrayList<PurchaseProductModelwithpo> productlist = new ArrayList<PurchaseProductModelwithpo>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[3];
			params[0] = idorName + "%";
			params[1] = idorName + "%";
			params[2] = idorName + "%";
			//params[3] = idorName + "%";
			Cursor productcursor = db.rawQuery("select distinct Prod_Id,Prod_Nm,P_Price,S_Price,Selling_Order_Unit,Profit_Margin,MRP,S_Price,BarCode,Conv_Fact,Ind_Nm from retail_store_prod_com where "
							+ " Prod_Id  like ? or  Prod_Nm  like ? And Active like 'Y%'  or BarCode like ? "
					, params);

			if (productcursor.moveToFirst()) {
				do {
					PurchaseProductModelwithpo pm = new PurchaseProductModelwithpo();
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCTNAME)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTPURCHASE)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(PRODUCTMEASUREUNITINV)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMRP)));
					pm.setBarcode(productcursor.getString(productcursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setConversion(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustery(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));

					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}

	public boolean activestore() {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		ContentValues contentValues = new ContentValues();

		contentValues.put("Flag", "Y");

		db.update("retail_store", contentValues, "Store_Id = ? ", new String[]{String.valueOf(PersistenceManager.getStoreId(mycontext))});
		Log.e("Store Active ####", "row inserted...");
		return true;
	}

	public void SavePdfDetailForPurchase(String InvoiceNO, String VendorName) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("Txn_Id", "");
		contentValues.put("Prefix_Nm", "PO-");
		contentValues.put("Vendor_Name", VendorName);
		contentValues.put("Po_No", InvoiceNO);
		contentValues.put("Universal_Id", PersistenceManager.getStoreId(mycontext));
		db.insert("retail_send_mail_pdf", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}

	public void SavePdfDetailForInventorywithpo(String InvoiceNO, String VendorName) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("Txn_Id", "");
		contentValues.put("Prefix_Nm", "INV_PO-");
		contentValues.put("Vendor_Name", VendorName);
		contentValues.put("Po_No", InvoiceNO);
		contentValues.put("Universal_Id", PersistenceManager.getStoreId(mycontext));
		db.insert("retail_send_mail_pdf", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}


	public void SavePDfDetailForInventoryWithoutPo(String InvoiceNO, String VendorName) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("Txn_Id", "");
		contentValues.put("Prefix_Nm", "INV_PO-");
		contentValues.put("Vendor_Name", VendorName);
		contentValues.put("Po_No", InvoiceNO);
		contentValues.put("Universal_Id", PersistenceManager.getStoreId(mycontext));
		db.insert("retail_send_mail_pdf", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}


	public void SavePdfDetailForInstallation(String InvoiceNO) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("Txn_Id", "");
		contentValues.put("Prefix_Nm", "INS-");
		contentValues.put("Vendor_Name", "");
		contentValues.put("Po_No", InvoiceNO);
		contentValues.put("Universal_Id", PersistenceManager.getStoreId(mycontext));
		db.insert("retail_send_mail_pdf", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}

/*	public ArrayList<DoctorPojo> getAllDoctors(String nameOrPhone) {
		ArrayList<DoctorPojo> doctorlist = new ArrayList<DoctorPojo>();
		SQLiteDatabase db = this.getReadableDatabase();

		try {
			String[] params = new String[1];
			params[0] = nameOrPhone + "%";

			Cursor res = db.rawQuery("select dr_name,Speciality from dr_discription where"
					+ " dr_name  like ? "
					, params);
			//null != res && res.moveToFirst() && res.getCount() > 0
			if (res.moveToFirst()) {
				do {
					DoctorPojo doctor = new DoctorPojo();
					doctor.setDoctorName(res.getString(res.getColumnIndex(DOCTORNAME)));
					doctor.setDoctorSpeciality(res.getString(res.getColumnIndex(DOCTORSPECIALITY)));
					doctorlist.add(doctor);
					//customerlist.add(res.getString(res.getColumnIndex(CUSTOMERMOBILENO)));
				} while (res.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} *//*catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
		}*//*
		return doctorlist;
	}*/

	public ArrayList<DoctorPojo> getAllDoctors(String idorName) {
		ArrayList<DoctorPojo> DoctorList = new ArrayList<DoctorPojo>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = idorName + "%";
			Cursor productcursor = db.rawQuery("select dr_name,Speciality from dr_discription where "
							+ " dr_name like ?  "
					, params);
			if (productcursor.moveToFirst()) {
				do {
					DoctorPojo pm = new DoctorPojo();
					pm.setDoctorName(productcursor.getString(productcursor.getColumnIndex(DOCTORNAME)));
					pm.setDoctorSpeciality(productcursor.getString(productcursor.getColumnIndex(DOCTORSPECIALITY)));

					DoctorList.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return DoctorList;
	}

	public void saveDataforPdfVendorPayment(String InvoiceNO, String VendorName) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("Txn_Id", "");
		contentValues.put("Prefix_Nm", "VEND_PAY-");
		contentValues.put("Vendor_Name", VendorName);
		contentValues.put("Po_No", InvoiceNO);
		contentValues.put("Universal_Id", PersistenceManager.getStoreId(mycontext));
		db.insert("retail_send_mail_pdf", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}

	public void saveDataforPdfVendorIndirectpayment(String InvoiceNO, String VendorName) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("Txn_Id", "");
		contentValues.put("Prefix_Nm", "VEND_INDIRECT-");
		contentValues.put("Vendor_Name", VendorName);
		contentValues.put("Po_No", InvoiceNO);
		contentValues.put("Universal_Id", PersistenceManager.getStoreId(mycontext));
		db.insert("retail_send_mail_pdf", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}


	public void saveDataforPdfVendorReturn(String InvoiceNO, String VendorName) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("Txn_Id", "");
		contentValues.put("Prefix_Nm", "VEND_Return-");
		contentValues.put("Vendor_Name", VendorName);
		contentValues.put("Po_No", InvoiceNO);
		contentValues.put("Universal_Id", PersistenceManager.getStoreId(mycontext));
		db.insert("retail_send_mail_pdf", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}
//************************ TOP 15 PRODUCT DEtAILS***************************//

	public ArrayList<Sales> gettopproducts() {

		ArrayList<Sales> topiemlist = new ArrayList<Sales>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select Prod_Short_Nm,Prod_Id  from retail_top_product ", null);
			if (res.moveToFirst())
				Log.e(TAG, "getTableAsString called");
			{
				do {

					Sales topitemmodel = new Sales();
					topitemmodel.setProductshortName(res.getString(res.getColumnIndex(TOP_PRODUCT_SORT_NAME)));
					topiemlist.add(topitemmodel);
				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();


		}
		return topiemlist;

	}

	///********************TOP 15 PRODUCT RECIEVED  DATA DETAILS *********************///
	public ArrayList<Sales> getAllTopProductDetails(String idorName) {
		ArrayList<Sales> productNamelist = new ArrayList<Sales>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = idorName + "%";

			Cursor productcursor = db.rawQuery("select a.Prod_Id,a.Prod_Nm,a.Batch_No,a.Exp_Date,a.Qty,a.MRP,a.S_Price,a.Uom,a.Conv_Fact from retail_str_stock_master a,retail_top_product b where a.Prod_Id = b.Prod_Id and b.Prod_Short_Nm like ? ORDER BY Batch_No DESC LIMIT 1"
					, params);
			//Cursor productcursor= db.rawQuery(MY_QUERY, new String[]{String.valueOf(STORE_ID)});

			if (null != productcursor && productcursor.moveToFirst() && productcursor.getCount() > 0) {
				do {
					Sales saleslist = new Sales();
					saleslist.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCT_NAME)));
					saleslist.setBatchNo(productcursor.getString(productcursor.getColumnIndex(BATCH_NO)));
					saleslist.setExpiry(productcursor.getString(productcursor.getColumnIndex(EXPIRY)));
					saleslist.setStockquant(productcursor.getFloat(productcursor.getColumnIndex(QUANTITY)));
					saleslist.setMrp(productcursor.getFloat(productcursor.getColumnIndex(MRP)));
					saleslist.setProdid(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					// saleslist.setAmount(res.getString(res.getColumnIndex(AMOUNT)));
					saleslist.setUom(productcursor.getString(productcursor.getColumnIndex(UOM)));
					// saleslist.setPPrice(res.getString(res.getColumnIndex(P_PRICE)));
					saleslist.setSPrice(productcursor.getFloat(productcursor.getColumnIndex(S_PRICE)));
					saleslist.setConversionfacter(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));


					productNamelist.add(saleslist);
				} while (productcursor.moveToNext());

			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return productNamelist;
	}

	public ArrayList<String> getAllDoctorsforsale() {
		ArrayList<String> doctorlist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();

		try {


			Cursor res = db.rawQuery("select dr_name from dr_discription where dr_id = dr_id"
					, null);
			if (res.moveToFirst()) {
				do {
					doctorlist.add(res.getString(res.getColumnIndex(DOCTORNAME)));
					//customerlist.add(res.getString(res.getColumnIndex(CUSTOMERMOBILENO)));
				} while (res.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
		}

		db.close();
		return doctorlist;
	}

	public void insertdataIntosendMailforSales(String InvoiceNo, String Name) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("Txn_Id", "");
		contentValues.put("Prefix_Nm", "Sales-");
		contentValues.put("Vendor_Name", Name);
		contentValues.put("Po_No", InvoiceNo);
		contentValues.put("Universal_Id", PersistenceManager.getStoreId(mycontext));
		db.insert("retail_send_mail_pdf", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}

	public void insertdataIntosendMailforSalesReturn(String InvoiceNo) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("Txn_Id", "");
		contentValues.put("Prefix_Nm", "SalesReturn-");
		//contentValues.put("Vendor_Name",Name);
		contentValues.put("Po_No", InvoiceNo);
		contentValues.put("Universal_Id", PersistenceManager.getStoreId(mycontext));
		db.insert("retail_send_mail_pdf", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}


	public void insertsalereturndataforproductdetail(String INVOICE, ArrayList<Salesreturndetail> list) {

		SQLiteDatabase db = this.getWritableDatabase();

		if (list == null) {
			return;
		}
		for (Salesreturndetail salesreturndetail : list) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("Tri_Id", INVOICE);
			contentValues.put("Id", getSystemCurrentTimeinsalesreturnwithin());
			contentValues.put("Prod_Nm", salesreturndetail.getSaleproductname());
			contentValues.put("Batch_No", salesreturndetail.getSalebatchno());
			contentValues.put("Exp_Date", salesreturndetail.getSaleexpiry());
			contentValues.put("S_Price", salesreturndetail.getSalesellingprice());
			contentValues.put("Qty", salesreturndetail.getSaleqty());
			contentValues.put("MRP", salesreturndetail.getSalemrp());
			contentValues.put("Uom", salesreturndetail.getSaleuom());
			// Inserting Row
			db.insert("retail_str_sales_details_return", null, contentValues);
		}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}


	public void insertsalereturndataforproductdetailwithoutinvoiceno(String INVOICE, ArrayList<SalesreturndetailWithoutPo> list) {

		SQLiteDatabase db = this.getWritableDatabase();

		if (list == null) {
			return;
		}
		for (SalesreturndetailWithoutPo salesreturndetail : list) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("Tri_Id", INVOICE);
			contentValues.put("Id", getSystemCurrentTimeinsalesreturnwithin());
			contentValues.put("Prod_Nm", salesreturndetail.getSaleproductname());
			contentValues.put("Batch_No", salesreturndetail.getSalebatchno());
			contentValues.put("Exp_Date", salesreturndetail.getSaleexpiry());
			contentValues.put("S_Price", salesreturndetail.getSalesellingprice());
			contentValues.put("Qty", salesreturndetail.getSaleqty());
			contentValues.put("MRP", salesreturndetail.getSalemrp());
			contentValues.put("Uom", salesreturndetail.getSaleuom());
			// Inserting Row
			db.insert("retail_str_sales_details_return", null, contentValues);
		}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}

	public boolean updateStockQtyforsalereturn(ArrayList<Salesreturndetail> list) {

		boolean returnval = true;


		if (list == null) {
			return true;
		}

		SQLiteDatabase db = this.getWritableDatabase();

		//For all items in sales bill, update the stock quantity
		for (Salesreturndetail salesreturn : list) {
			ContentValues contentValues = new ContentValues();


			float stockQuantity = salesreturn.getSalestockqty();

			float saleQuantity = salesreturn.getSaleqty();


			float newStockQuantity = stockQuantity + saleQuantity;
			if (newStockQuantity < 0) {
				newStockQuantity = 0;
			}
			contentValues.put("Qty", Double.toString(newStockQuantity));


			int sqlUpdateRetval = db.update("retail_str_stock_master", contentValues, "Batch_No = ?  and " +
					"Prod_Id " +
					" = ? ", new String[]{salesreturn.getSalebatchno(), salesreturn.getSaleProdid()});
			if (sqlUpdateRetval < 1) {
				returnval = false;
			}
		}

		db.close();
		return returnval;
	}


	// ***********************!!!!!!VALIDATION TO CHECK EXIST Date!!!!!!!!*****************************
	public boolean CheckInstalltionkit(String s) {
		SQLiteDatabase db = this.getReadableDatabase();
//		String[] params = new String[1];
//		params[0] = Phone + "%";
		//String Query =("select Start_Date from retail_str_day_open_close where Start_Date = date('now')",null);
		Cursor cursor = db.rawQuery("SELECT Flag from retail_store where Flag  like  'N%' ", null);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;

	}

	public boolean CheckbatchnoAlreadyInDBorNotforinvoice(String batchno) {
		SQLiteDatabase db = this.getReadableDatabase();
	/*	String[] params = new String[1];
		params[0] = batchno + "%";*/
		//  params[1] =productid + "%";

		String Query = ("select Batch_No,Con_Mul_Qty from retail_str_stock_master where "
				+ " Batch_No ='" + batchno + "'");
		Cursor cursor = db.rawQuery(Query, null);

		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		return true;
	}


	public String getparticularbatchqtyforinvoice(String batchno, String Prod_Nm) {
		String getQty = null;
		SQLiteDatabase db = this.getReadableDatabase();
//    String[] params = new String[2];
//    params[0] = batchno + "%";
//    params[1] = Prod_Id + "%";
		String Query = ("select Con_Mul_Qty from retail_str_stock_master where " + " Batch_No =  '" + batchno + "' and Prod_Nm = '" + Prod_Nm + "'");
		//Log.e("Query::", "select Qty from retail_str_stock_master where " + " Batch_No =  '" + batchno + "' and Prod_Nm = '" + Prod_Nm + "'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			getQty=cursor.getString(cursor.getColumnIndex(TOTALQTY));
		}
		return  getQty;

	}

	/////////////////////////////////////get DATA From year and Month For Inventory REPORT///////////////////////////////

	public ArrayList<InventoryReportModel> InventoryDataForMonth(String Value1, String Value2) {
		ArrayList<InventoryReportModel> purchaseData = new ArrayList<InventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor productcursor = db.rawQuery("select distinct Prod_Nm,Batch_No,Exp_Date,Qty from retail_str_stock_master where "
					+ " LastUpdate between '" + Value1 + "' AND '" + Value2 + "'", null);
			if (productcursor.moveToFirst()) {
				do {
					InventoryReportModel vm = new InventoryReportModel();
					vm.setProd_Nm(productcursor.getString(productcursor.getColumnIndex(COLUMN_PROD_NAME)));
					vm.setBatch(productcursor.getString(productcursor.getColumnIndex(COLUMN_BATCH_NO)));
					vm.setExpiry(productcursor.getString(productcursor.getColumnIndex(COLUMN_EXP_DATE)));
					vm.setQuantity(productcursor.getString(productcursor.getColumnIndex(COLUMN_QUANTITY)));
					purchaseData.add(vm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return purchaseData;
	}

	///////////////////////////////////////////////get product id from retail_str_stock_master/////////////////////////////////////////////////////////////////

	public ArrayList<InventoryReportModel> getProductId(String id) {
		ArrayList<InventoryReportModel> productIdlist = new ArrayList<InventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = id + "%";
			params[1] = id + "%";
			Cursor cursor = db.rawQuery("select distinct Prod_Id,Prod_Nm from retail_str_stock_master  where"
							+ " Prod_Id like ? or Prod_Nm like ?"
					, params);
			if (cursor.moveToFirst()) {
				do {
					InventoryReportModel im = new InventoryReportModel();
					im.setProd_Id(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_ID)));
					im.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_NAME)));
					productIdlist.add(im);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return productIdlist;
	}
	//////////////////////////////////////////////get DATA FROM  retail_str_stock_master TABLE//////////////////////////////////////////////////

	public ArrayList<InventoryReportModel> getInventoryReport(String ProdId) {
		ArrayList<InventoryReportModel> Idlist = new ArrayList<InventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = ProdId + "%";
			params[1] = ProdId + "%";
			Cursor cursor = db.rawQuery("select distinct Prod_Nm,Batch_No,Exp_Date,Qty from retail_str_stock_master  where"
							+ " Prod_Id like ? or Prod_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					InventoryReportModel im = new InventoryReportModel();
					im.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_NAME)));
					im.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NO)));
					im.setExpiry(cursor.getString(cursor.getColumnIndex(COLUMN_EXP_DATE)));
					im.setQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY)));
					Idlist.add(im);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return Idlist;
	}


	////////////////////////////////////////////get grnId from tmp_vend_dstr_payment//////////////////////////////

	public ArrayList<DirectVendorPaymentModel> getGrnDirectId(String id) {
		ArrayList<DirectVendorPaymentModel> grnIdlist = new ArrayList<DirectVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = id + "%";
			params[1] = id + "%";
			Cursor cursor = db.rawQuery("select distinct Pay_Id,Vend_Dstr_Nm from tmp_vend_dstr_payment  where"
							+ " Pay_Id like ? or Vend_Dstr_Nm like ? "
					, params);
			if (cursor.moveToFirst()) {
				do {
					DirectVendorPaymentModel im = new DirectVendorPaymentModel();
					im.setGrnId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAY_ID)));
					im.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					grnIdlist.add(im);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return grnIdlist;
	}

	//////////////////////////////////////////////get directpayByCash data from tmp_vend_dstr_payment///////////////////////////

	public ArrayList<DirectVendorPaymentModel> getDirectPayByCashReport(String GrnId) {
		ArrayList<DirectVendorPaymentModel> paybyCashlist = new ArrayList<DirectVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = GrnId + "%";
			params[1] = GrnId + "%";
			Cursor cursor = db.rawQuery("select distinct Vend_Dstr_Nm,Amount,Received_Amount,Due_Amount,LastUpdate from tmp_vend_dstr_payment where"
							+ " Pay_Id like ? or Vend_Dstr_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					DirectVendorPaymentModel pm = new DirectVendorPaymentModel();
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_UPDTE)));
					paybyCashlist.add(pm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return paybyCashlist;
	}

////////////////////////////////////////////////get directpayByCheque data from tmp_vend_dstr_payment //////////////////////////

	public ArrayList<DirectVendorPaymentModel> getDirectPayByChequeReport(String GrnId) {
		ArrayList<DirectVendorPaymentModel> paybyChequelist = new ArrayList<DirectVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = GrnId + "%";
			params[1] = GrnId + "%";
			Cursor cursor = db.rawQuery("select distinct Vend_Dstr_Nm,Amount,Received_Amount,Due_Amount,Bank_Name,Cheque_No,LastUpdate from tmp_vend_dstr_payment where"
							+ " Pay_Id like ? or Vend_Dstr_Nm like ? "
					, params);
			if (cursor.moveToFirst()) {
				do {
					DirectVendorPaymentModel pm = new DirectVendorPaymentModel();
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setBankName(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_BANK_NM)));
					pm.setChequeNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CHEQUE_NO)));
					pm.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_UPDTE)));
					paybyChequelist.add(pm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return paybyChequelist;
	}


////////////////////////////////////////////get grnId from tmp_vend_dstr_payment//////////////////////////////

	public ArrayList<IndirectVendorPaymentModel> getGrnIndirectId(String id) {
		ArrayList<IndirectVendorPaymentModel> grnIdlist = new ArrayList<IndirectVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = id + "%";
			params[1] = id + "%";
			Cursor cursor = db.rawQuery("select distinct Pay_Id,Vend_Dstr_Nm from tmp_vend_dstr_payment  where"
							+ " Pay_Id like ? or Vend_Dstr_Nm like ? "
					, params);
			if (cursor.moveToFirst()) {
				do {
					IndirectVendorPaymentModel im = new IndirectVendorPaymentModel();
					im.setGrnId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAY_ID)));
					im.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					grnIdlist.add(im);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return grnIdlist;
	}

//////////////////////////////////////////////get indirectpayByCash data from tmp_vend_dstr_payment///////////////////////////

	public ArrayList<IndirectVendorPaymentModel> getIndirectPayByCashReport(String GrnId) {
		ArrayList<IndirectVendorPaymentModel> paybyCashlist = new ArrayList<IndirectVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = GrnId + "%";
			params[1] = GrnId + "%";
			Cursor cursor = db.rawQuery("select distinct Vend_Dstr_Nm,Amount,Received_Amount,Due_Amount,Reason_Of_Pay,LastUpdate from tmp_vend_dstr_payment where"
							+ " Pay_Id like ? or Vend_Dstr_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					IndirectVendorPaymentModel pm = new IndirectVendorPaymentModel();
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setReasonOfPay(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_REASN_OF_PAY)));
					pm.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_UPDTE)));
					paybyCashlist.add(pm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return paybyCashlist;
	}

////////////////////////////////////////////////get indirectpayByCheque data from tmp_vend_dstr_payment //////////////////////////

	public ArrayList<IndirectVendorPaymentModel> getIndirectPayByChequeReport(String GrnId) {
		ArrayList<IndirectVendorPaymentModel> paybyChequelist = new ArrayList<IndirectVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = GrnId + "%";
			params[1] = GrnId + "%";
			Cursor cursor = db.rawQuery("select distinct Vend_Dstr_Nm,Amount,Received_Amount,Due_Amount,Bank_Name,Cheque_No,Reason_Of_Pay,LastUpdate from tmp_vend_dstr_payment where"
							+ " Pay_Id like ? or Vend_Dstr_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					IndirectVendorPaymentModel pm = new IndirectVendorPaymentModel();
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setBankName(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_BANK_NM)));
					pm.setChequeNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CHEQUE_NO)));
					pm.setReasonOfPay(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_REASN_OF_PAY)));
					pm.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_UPDTE)));
					paybyChequelist.add(pm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return paybyChequelist;
	}







	////////////////////////////////////////////get vendorReturnId from retail_str_vendor_detail_return//////////////////////////////

	public ArrayList<ReportVendorReturnModel> getVendorReturnId(String id) {
		ArrayList<ReportVendorReturnModel> ReturnId = new ArrayList<ReportVendorReturnModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = id + "%";
			params[1] = id + "%";
			Cursor cursor = db.rawQuery("select distinct Vendor_Return_Id,Vendor_Nm from retail_str_vendor_detail_return  where"
							+ " Vendor_Return_Id like ? or Vendor_Nm like ? "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorReturnModel vm = new ReportVendorReturnModel();
					vm.setVendrId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTVENDR_ID)));
					vm.setVendrNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTVENDR_NM)));
					ReturnId.add(vm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return ReturnId;
	}
	//////////////////////////////////////////////get VendorReturn data from retail_str_vendor_detail_return///////////////////////////

	public ArrayList<ReportVendorReturnModel> getVendorReturnReport(String GrnId) {
		ArrayList<ReportVendorReturnModel> vendorReturnList = new ArrayList<ReportVendorReturnModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = GrnId + "%";
			params[1] = GrnId + "%";
			Cursor cursor = db.rawQuery("select distinct Vendor_Nm,Prod_Nm,Batch_No,Exp_Date,Reason_Of_Return,Qty,P_Price,Total,Uom from retail_str_vendor_detail_return where"
							+ " Vendor_Return_Id like ? or Vendor_Nm like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorReturnModel pm = new ReportVendorReturnModel();
					pm.setVendrNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTVENDR_NM)));
					pm.setProdctNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTPROD_NM)));
					pm.setBatchNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTBATCH_NO)));
					pm.setExpDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTEXP_DATE)));
					pm.setReason(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTREASON_RETRN)));
					pm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_QTY)));
					pm.setPPrice(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTP_Price)));
					pm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_TOTAL)));
					pm.setUom(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_UOM)));
					vendorReturnList.add(pm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return vendorReturnList;
	}





	public void updateqtyforinvoice(ArrayList<Salesreturndetail> list) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			boolean returnval = true;
			boolean fortrue = false;
			if (list == null) {
				return;
			}
			for (Salesreturndetail prod : list) {

				ContentValues values = new ContentValues();
				Log.e("Prodlength", String.valueOf(list.size()));
				fortrue = CheckbatchnoAlreadyInDBorNotforinvoice(prod.getSalebatchno());

				// for(int Batch=0;Batch<list.size();Batch++) {
				if (fortrue == true) {

            /*values.put("Batch_No", prod.getSalebatchno());
            //values.put("Exp_Date", prod.getExp_Date().concat("/01"));
            //values.put("Store_Id", STORE_ID_MASTER);
            values.put("Prod_Id", prod.getSaleProdid());
            values.put("Prod_Nm", prod.getSaleproductname());
            //values.put("P_Price", prod.getsa);
            // values.put("Profit_margin", prod.get);
            values.put("Qty", prod.getSaleqty());
            values.put("MRP", prod.getSalemrp());
            values.put("S_Price", prod.getSalesellingprice());
            values.put("Amount", prod.getSaletotal());
            values.put("Uom", prod.getSaleuom());
            //values.put("BarCode", "NA");

            // Inserting Row
            db.insert("retail_str_stock_master", null, values);*/


					String batchqty = getparticularbatchqtyforinvoice(prod.getSalebatchno(), prod.getSaleproductname());
					float prodQuantity = prod.Saleqty;


					float newStockQuantity = Float.parseFloat(batchqty) + prodQuantity;
					if (newStockQuantity < 0) {
						newStockQuantity = 0;
					}
					values.put("Con_Mul_Qty", Double.toString(newStockQuantity));
					int sqlUpdateRetval = db.update("retail_str_stock_master", values, "Batch_No = ?  and " +
							"Prod_Nm " +
							" = ? ", new String[]{prod.getSalebatchno(), prod.getSaleproductname()});

					Log.d("Sudhee", "Update done for batch:" + prod.getSalebatchno() + ", prodid:" + prod.getSaleproductname());

					if (sqlUpdateRetval < 1) {
						Log.e("Update fail", "returned: " + sqlUpdateRetval);
						returnval = false;
					}
					//return;
				}
				//}
			}
			db.close(); // Closing database connection
			Log.e("Database Operation", "row inserted...");
			return;

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

	public boolean CheckIsInvoiceReturn(String Phone) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = Phone + "%";
		String Query = ("select Tri_Id from retail_str_sales_master_return where " + " Tri_Id like ?");
		Cursor cursor = db.rawQuery(Query, params);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;


	}

	public ArrayList<Inventoryproductmodel> getProductdataforwithoutInventory(String idorName) {
		ArrayList<Inventoryproductmodel> productlist = new ArrayList<Inventoryproductmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[3];
			params[0] = idorName + "%";
			params[1] = idorName + "%";
			params[2] = idorName + "%";
			Cursor productcursor = db.rawQuery("select distinct Prod_Id,Prod_Nm,P_Price,S_Price,Selling_Order_Unit,Profit_Margin, MRP ,S_Price, BarCode,Conv_Fact,Ind_Nm from retail_store_prod_com where "
							+ " Prod_Id  like ? or  Prod_Nm  like ?  AND Active like 'Y%' or BarCode like  ?  "
					, params);

			if (productcursor.moveToFirst()) {
				do {
					Inventoryproductmodel pm = new Inventoryproductmodel();
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCTNAME)));
					pm.setPprice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTPURCHASE)));
					pm.setTax(productcursor.getString(productcursor.getColumnIndex(PRODUCTMEASUREUNITINV)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMRP)));
					pm.setBarcode(productcursor.getString(productcursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setConvfact(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustry(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));

					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}


	public void saveInventorywithoutpo(ArrayList<Inventoryproductmodel> list, String VendorName, String grnId) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			boolean returnval = true;
			boolean fortrue = false;
			if (list == null) {
				return;
			}
			for (Inventoryproductmodel prod : list) {


				ContentValues values = new ContentValues();

				PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
				PersistenceManager.getStoreId(mycontext);

				Log.e("Prodlength", String.valueOf(list.size()));
				float totalinventoryqty=prod.productQuantity+prod.getFreequantity();

				float mrp = prod.getMrp();
				float sprice=prod.getSprice();
				float conversin=prod.getConvfact();
				float newmrp = mrp / conversin;
				if (newmrp < 0) {
					newmrp = 0;
				}
				float newsprice = sprice / conversin;
				if (newsprice < 0) {
					newsprice = 0;
				}

				// for(int Batch=0;Batch<list.size();Batch++) {
				fortrue = CheckbatchnoAlreadyInDBorNotwithoutpo(prod.getBatchno());
				if (fortrue == false) {
					values.put("Grn_Id", grnId);
					values.put("Vend_Nm", VendorName);
					//values.put("Po_No", Ponumbers);
					//String demo = prod.getIndustry().toString();
					values.put("Batch_No", prod.getBatchno());
					values.put("Exp_Date", prod.getExpdate());
					values.put("Store_Id", PersistenceManager.getStoreId(mycontext));
					values.put("Prod_Id", prod.getProductId());
					values.put("Prod_Nm", prod.getProductName());
					values.put("P_Price", prod.getPprice());
					values.put("Profit_margin", prod.getProductmargin());
					values.put("Qty", prod.getProductQuantity());
					values.put("MRP", prod.getMrp());
					values.put("S_Price", prod.getSprice());
					values.put("Amount", prod.getTotal());
					values.put("Uom", prod.getTax());
					values.put("BarCode", "NA");
					values.put("Conv_Fact", prod.getConvfact());
					values.put("Free_Goods", prod.getFreequantity());
					values.put("Con_Mul_Qty",prod.getTotalqty());
					values.put("Prev_Qty",totalinventoryqty);
					values.put("Conv_MRP",newmrp);
					values.put("Conv_SPrice",newsprice);
					// Inserting Row
					db.insert("retail_str_stock_master", null, values);
				} else {

					String batchqty = getparticularbatchqtywithoutpo(prod.getBatchno(), prod.getProductId());
					values.put("Grn_Id", grnId);
					float prodQuantity = prod.getTotalqty();
					float newStockQuantity = Float.parseFloat(batchqty) + prodQuantity;
					if (newStockQuantity < 0) {
						newStockQuantity = 0;
					}
					values.put("Con_Mul_Qty", Double.toString(newStockQuantity));
					int sqlUpdateRetval = db.update("retail_str_stock_master", values, "Batch_No = ?  and " + "Prod_Id " +
							" = ? ", new String[]{prod.getBatchno(), prod.getProductId()});

					Log.d("Sudhee", "Update done for batch:" + prod.getBatchno() + ", prodid:" + prod.getProductId());

					if (sqlUpdateRetval < 1) {
						Log.e("Update fail", "returned: " + sqlUpdateRetval);
						returnval = false;
					}
					//return;
				}
				//}
			}
			db.close(); // Closing database connection
			Log.e("Database Operation", "row inserted...");
			return;

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}


	public boolean CheckbatchnoAlreadyInDBorNotwithoutpo(String batchno) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = batchno + "%";
		//  params[1] =productid + "%";

		String Query = ("select Batch_No,Prod_Id,Con_Mul_Qty from retail_str_stock_master where "
				+ " Batch_No like ?");
		Cursor cursor = db.rawQuery(Query, params);

		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		return true;
	}


	public String getparticularbatchqtywithoutpo(String batchno, String Prod_Id) {
		String getQty = null;
		SQLiteDatabase db = this.getReadableDatabase();
  /* String[] params = new String[1];
   params[0] = batchno + "%";
   params[1] = Prod_Id + "%";*/
		String Query = ("select Con_Mul_Qty from retail_str_stock_master where " + " Batch_No =  '" + batchno + "' and Prod_Id = '" + Prod_Id + "'");
		Log.e("Query::", "select Con_Mul_Qty from retail_str_stock_master where " + " Batch_No =  '" + batchno + "' and Prod_Id = '" + Prod_Id + "'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			getQty = cursor.getString(cursor.getColumnIndex(TOTALQTY));
		}
		return getQty;

	}

	public void saveGranddataintoGrnMasterwithoutpo(String result, String VendorName, String GrandTotal) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		ContentValues values = new ContentValues();
		values.put("Store_Id", PersistenceManager.getStoreId(mycontext));
		values.put("Grn_Id", result);
		values.put("Vend_Nm", VendorName);
		values.put("Grand_Amount", GrandTotal);
		values.put("Flag", "0");
		db.insert("retail_str_grn_master", null, values);

		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted.into retail_str_grn_master..");


		return;
	}

	public ArrayList<String> getdoctorspecialication() {

		ArrayList<String> doctspeclist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
          /* String []params=new String[1];
           params[0]=name+"%";
         */
			Cursor cursor = db.rawQuery("select distinct Speciality  from dr_speciality "
					, null);
			if (cursor.moveToFirst()) {
				do {

					doctspeclist.add(cursor.getString(cursor.getColumnIndex(COLUMN_DOCTOR_SPECIAL)));
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return doctspeclist;
	}


	public ArrayList<ReportDistributorModel> getDistributorForReport() {
		ArrayList<ReportDistributorModel> distributorlist = new ArrayList<ReportDistributorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select Dstr_Nm, Active from retail_str_dstr  where"
							+ " Active like 'Y%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportDistributorModel dm = new ReportDistributorModel();
					dm.setDstr_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_DSTR_NAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportVendorModel> getVendorForReport() {
		ArrayList<ReportVendorModel> distributorlist = new ArrayList<ReportVendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select Vend_Nm, Active from retail_store_vendor  where"
							+ " Active like 'Y%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorModel dm = new ReportVendorModel();
					dm.setVend_Nm(cursor.getString(cursor.getColumnIndex(LOCALVENDORNAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(LOCALVENDORACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}


	public ArrayList<ReportProductCpgModel> getProductCpgForReport() {
		ArrayList<ReportProductCpgModel> distributorlist = new ArrayList<ReportProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select Prod_Nm, Active from retail_store_prod_cpg  where"
							+ " Active like 'Y%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportProductCpgModel dm = new ReportProductCpgModel();
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_NM)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_ACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportProductPharmaModel> getProductPharmaForReport() {
		ArrayList<ReportProductPharmaModel> distributorlist = new ArrayList<ReportProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select Prod_Nm, Active from retail_store_prod  where"
							+ " Active like 'Y%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportProductPharmaModel dm = new ReportProductPharmaModel();
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTNAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(PRODUCTACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}


	//////////////////////////////////////////////// get DATA From year and Month For MediaAdTicker REPORT//////////////////////////////////////////////////////////


	public ArrayList<AdTickerReportModel> MediaAdTicker(String Value1, String Value2) {
		ArrayList<AdTickerReportModel> OldInvoiceData = new ArrayList<AdTickerReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor productcursor = db.rawQuery("select distinct Ad_Main_Id,Ad_Text,Ad_Cst_Slb1 from retail_ad_ticker where "
					+ " LastUpdate between '" + Value1 + "' AND '" + Value2 + "'", null);
			if (productcursor.moveToFirst()) {
				do {
					AdTickerReportModel vm = new AdTickerReportModel();
					vm.setAD_MAIN_ID(productcursor.getString(productcursor.getColumnIndex(COLUMN_AD_MAIN_ID)));
					vm.setAD_TEXT(productcursor.getString(productcursor.getColumnIndex(COLUMN_AD_TEXT)));
					vm.setAD_CST_SLB1(productcursor.getString(productcursor.getColumnIndex(COLUMN_AD_CST_SLB1)));
					OldInvoiceData.add(vm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return OldInvoiceData;
	}

	public ArrayList<BlinkingLogoReportModel> BlinkingLogoReport(String Value1, String Value2) {
		ArrayList<BlinkingLogoReportModel> OldInvoiceData = new ArrayList<BlinkingLogoReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor productcursor = db.rawQuery("select distinct Ad_Main_Id,Ad_Desc,Ad_Strt_Dt,Ad_End_Dt,Ad_Cst_Slb1,Ad_Cst_Slb2,Ad_Cst_Slb3 from retail_ad_blinkinglogo where "
					+ " LastUpdate between '" + Value1 + "' AND '" + Value2 + "'", null);
			if (productcursor.moveToFirst()) {
				do {
					BlinkingLogoReportModel vm = new BlinkingLogoReportModel();
					vm.setAD_MAIN_ID(productcursor.getString(productcursor.getColumnIndex(COLUMN_Blinkinglogo_AD_MAIN_ID)));
					vm.setAD_DESC(productcursor.getString(productcursor.getColumnIndex(COLUMN_Blinkinglogo_AD_DESC)));
					vm.setAD_START_DATE(productcursor.getString(productcursor.getColumnIndex(COLUMN_Blinkinglogo_AD_START_DATE)));
					vm.setAD_END_DATE(productcursor.getString(productcursor.getColumnIndex(COLUMN_Blinkinglogo_AD_END_DATE)));
					vm.setAD_CST_SLB1(productcursor.getString(productcursor.getColumnIndex(COLUMN_Blinkinglogo_AD_CST_SLB1)));
					vm.setAD_CST_SLB2(productcursor.getString(productcursor.getColumnIndex(COLUMN_Blinkinglogo_AD_CST_SLB2)));
					vm.setAD_CST_SLB3(productcursor.getString(productcursor.getColumnIndex(COLUMN_Blinkinglogo_AD_CST_SLB3)));
					OldInvoiceData.add(vm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return OldInvoiceData;
	}


	public ArrayList<ReportLocalProductPharmaModel> getLocalProductPharmaForReport() {
		ArrayList<ReportLocalProductPharmaModel> distributorlist = new ArrayList<ReportLocalProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select Prod_Nm, Active from retail_store_prod_local  where"
							+ " Active like 'Y%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductPharmaModel dm = new ReportLocalProductPharmaModel();
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPRODUCTACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportLocalProductCpgModel> getLocalProductCpgForReport() {
		ArrayList<ReportLocalProductCpgModel> distributorlist = new ArrayList<ReportLocalProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select Prod_Nm, Active from retail_store_prod_local_cpg  where"
							+ " Active like 'Y%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductCpgModel dm = new ReportLocalProductCpgModel();
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_NM)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_ACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportDistributorModel> getDistributorReportforActive(String Name) {

		ArrayList<ReportDistributorModel> distributorlist = new ArrayList<ReportDistributorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
      /*String[] params = new String[1];
      params[0] = Name + "%";*/
			Cursor cursor = db.rawQuery("select Dstr_Nm, Active from retail_str_dstr  where"
							+ " Active like '" + Name + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportDistributorModel dm = new ReportDistributorModel();
					dm.setDstr_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_DSTR_NAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportLocalProductPharmaModel> getLocalProductPharmaReportforActive(String Name) {

		ArrayList<ReportLocalProductPharmaModel> distributorlist = new ArrayList<ReportLocalProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select Prod_Nm, Active from retail_store_prod_local  where"
							+ " Active like '" + Name + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductPharmaModel dm = new ReportLocalProductPharmaModel();
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPRODUCTACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportProductPharmaModel> getProductPharmaReportforActive(String Name) {

		ArrayList<ReportProductPharmaModel> distributorlist = new ArrayList<ReportProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
      /*String[] params = new String[1];
      params[0] = Name + "%";*/
			Cursor cursor = db.rawQuery("select Prod_Nm, Active from retail_store_prod  where"
							+ " Active like '" + Name + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportProductPharmaModel dm = new ReportProductPharmaModel();
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTNAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(PRODUCTACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportVendorModel> getVendorReportforActive(String Name) {

		ArrayList<ReportVendorModel> distributorlist = new ArrayList<ReportVendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select Vend_Nm, Active from retail_store_vendor  where"
							+ " Active like '" + Name + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorModel dm = new ReportVendorModel();
					dm.setVend_Nm(cursor.getString(cursor.getColumnIndex(LOCALVENDORNAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(LOCALVENDORACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportLocalProductCpgModel> getLocalCpgReportforActive(String Name) {

		ArrayList<ReportLocalProductCpgModel> distributorlist = new ArrayList<ReportLocalProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select Prod_Nm, Active from retail_store_prod_local_cpg  where"
							+ " Active like '" + Name + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductCpgModel dm = new ReportLocalProductCpgModel();
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_NM)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_ACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportProductCpgModel> getCpgReportforActive(String Name) {

		ArrayList<ReportProductCpgModel> distributorlist = new ArrayList<ReportProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select Prod_Nm, Active from retail_store_prod_cpg  where"
							+ " Active like '" + Name + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportProductCpgModel dm = new ReportProductCpgModel();
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_NM)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_ACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}


	////////////////////////////////////////////////DATA FROM  retail_str_bill_master TABLE//////////////////////////////////////

	public ArrayList<SaleReportModel> getSaleReport(String TransId) {

		ArrayList<SaleReportModel> idlist = new ArrayList<SaleReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = TransId + "%";
			//params[1] = TransId + "%";
			Cursor cursor = db.rawQuery("select distinct Total,Uom from retail_str_sales_detail  where"
					+ " Tri_Id like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					SaleReportModel sm = new SaleReportModel();
					sm.setGrnTotl(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_TOTAL)));
					sm.setUom(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_UOM)));
					idlist.add(sm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return idlist;
	}

	////////////////////////////////////////////////////*************/////////////////////////////////////////////////////////////////

	ArrayList<String> getTransId(String id) {
		ArrayList<String> transidlist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = id + "%";
			//params[1] = id + "%";
			Cursor cursor = db.rawQuery("select distinct Tri_Id from retail_str_sales_detail  where"
					+ " Tri_Id like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					//SaleReportModel sm = new SaleReportModel();
					transidlist.add(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_ID)));
					//transidlist.add(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NM)));
					//transidlist.add(transidlist);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return transidlist;
	}

public ArrayList<SaleReportModel> SaleData(String Value1, String Value2) {
		ArrayList<SaleReportModel> purchaseData = new ArrayList<SaleReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor productcursor = db.rawQuery("select distinct Tri_Id,Total,Uom,Prod_Nm,Batch_No from retail_str_sales_detail where "
					+ " LastUpdate between '" + Value1 + "' AND '" + Value2 + "'", null);
			if (productcursor.moveToFirst()) {
				do {
					SaleReportModel vm = new SaleReportModel();
					vm.setTransId(productcursor.getString(productcursor.getColumnIndex(COLUMN_REPRT_SALE_ID)));
					vm.setGrnTotl(productcursor.getString(productcursor.getColumnIndex(COLUMN_REPRT_SALE_TOTAL)));
					vm.setUom(productcursor.getString(productcursor.getColumnIndex(COLUMN_REPRT_SALE_UOM)));
					vm.setProdNm(productcursor.getString(productcursor.getColumnIndex(COLUMN_REPRT_SALE_NM)));
					vm.setBatch(productcursor.getString(productcursor.getColumnIndex(COLUMN_REPRT_SALE_NO)));
					purchaseData.add(vm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return purchaseData;
	}


	////////////////////////////////////////////////////get all data from retail_str_sales_detail ////////////////////////////////////////////////////////////////////////////

	public ArrayList<SaleReportModel> getallSaledata(String Value) {
		ArrayList<SaleReportModel> getpurchaselist = new ArrayList<SaleReportModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = Value + "%";
		Cursor cursor = db.rawQuery("select Exp_Date,S_Price,Qty,MRP from retail_str_sales_detail where "
						+ "  Tri_Id like ? "
				, params);

		if (cursor.moveToFirst()) {
			do {
				SaleReportModel pm = new SaleReportModel();
				pm.setExp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_DATE)));
				pm.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_PRICE)));
				//pm.setSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_SDATE)));
				pm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_QTY)));
				pm.setMrp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_MRP)));
				getpurchaselist.add(pm);

			} while (cursor.moveToNext());
		}
		return getpurchaselist;
	}


	////////////////////////////////////////////////////////get all data from retail str sales master//////////////

	public ArrayList<SaleReportModel> getSalesDate(String salesDate) {
		ArrayList<SaleReportModel> getpurchaselist1 = new ArrayList<SaleReportModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = salesDate + "%";
		Cursor cursor = db.rawQuery("select Sale_Date from retail_str_sales_master where "
						+ "  Tri_Id like ? "
				, params);

		if (cursor.moveToFirst()) {
			do {
				SaleReportModel pm = new SaleReportModel();
				pm.setSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_SDATE)));
				getpurchaselist1.add(pm);

			} while (cursor.moveToNext());
		}
		return getpurchaselist1;
	}




	////////////////////////////////////////////////DATA FROM  retail_str_sales_detail TABLE//////////////////////////////////////

	public ArrayList<SalesReturnReportModel> getSalesReturnReport(String TransId) {

		ArrayList<SalesReturnReportModel> idlist = new ArrayList<SalesReturnReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = TransId + "%";
			Cursor cursor = db.rawQuery("select distinct Grand_Total,Reason_Of_Return,Tri_Id from retail_str_sales_master_return  where"
							+ " Tri_Id like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					SalesReturnReportModel sm = new SalesReturnReportModel();
					sm.setGrnTotl(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_GRANDTOTAL)));
					sm.setReason(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_REASON)));
					sm.setReason(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
					idlist.add(sm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return idlist;
	}

///////////////////////////////////////////////////////////////

	ArrayList<String> getInvoiceTransId(String id) {
		ArrayList<String> transidlist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = id + "%";
			Cursor cursor = db.rawQuery("select distinct Tri_Id from retail_str_sales_details_return  where"
							+ " Tri_Id like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					transidlist.add(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return transidlist;
	}

//////////////////////////////////////////////// get DATA From year and Month For Sale REPORT//////////////////////////////////////////////////////////

	public ArrayList<SalesReturnReportModel> SalesReturnData(String Value3, String Value4) {
		ArrayList<SalesReturnReportModel> OldInvoiceData = new ArrayList<SalesReturnReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor productcursor = db.rawQuery("select distinct Grand_Total,Reason_Of_Return,Tri_Id from retail_str_sales_master_return where "
					+ " LastUpdate between '" + Value3 + "' AND '" + Value4 + "'", null);
			if (productcursor.moveToFirst()) {
				do {
					SalesReturnReportModel vm = new SalesReturnReportModel();
					vm.setGrnTotl(productcursor.getString(productcursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_GRANDTOTAL)));
					vm.setReason(productcursor.getString(productcursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_REASON)));
					vm.setTransId(productcursor.getString(productcursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
					OldInvoiceData.add(vm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return OldInvoiceData;
	}


	/////////////////////////////////////////////////////get all data from retail_str_sales_details_return ////////////////////////////////////////////////////////////////////////////

	public ArrayList<SalesReturnReportModel> getallSalesReturndata(String Value) {
		ArrayList<SalesReturnReportModel> getpurchaselist = new ArrayList<SalesReturnReportModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = Value + "%";
		Cursor cursor = db.rawQuery("select Prod_Nm,Batch_No,Exp_Date,S_Price,Qty,MRP from retail_str_sales_details_return where "
						+ "  Tri_Id like ? "
				, params);
		if (cursor.moveToFirst()) {
			do {
				SalesReturnReportModel pm = new SalesReturnReportModel();
				pm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_NM)));
				pm.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_NO)));
				pm.setExp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_DATE)));
				pm.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_PRICE)));
				pm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_QTY)));
				pm.setMrp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_MRP)));
				getpurchaselist.add(pm);

			} while (cursor.moveToNext());
		}
		return getpurchaselist;
	}

	////////////////////////////////////////////////DATA FROM  retail_str_sales_master_return TABLE//////////////////////////////////////

	public ArrayList<SalesReturnReportModel> getSalesReturn2Report(String TransId) {

		ArrayList<SalesReturnReportModel> idlist = new ArrayList<SalesReturnReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = TransId + "%";
			Cursor cursor = db.rawQuery("select distinct Grand_Total,Reason_Of_Return,Tri_Id from retail_str_sales_master_return  where"
							+ " Tri_Id like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					SalesReturnReportModel sm = new SalesReturnReportModel();
					sm.setGrnTotl(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_GRANDTOTAL)));
					sm.setReason(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_REASON)));
					sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
					idlist.add(sm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return idlist;
	}

	/////////////////////////////////////////////////
///////////////////////////////////////////////////////////////

	ArrayList<String> getWithoutInvoiceTransId(String id) {
		ArrayList<String> transidlist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = id + "%";
			Cursor cursor = db.rawQuery("select distinct Tri_Id from retail_str_sales_details_return  where"
							+ " Tri_Id like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					transidlist.add(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return transidlist;
	}

//////////////////////////////////////////////// get DATA From year and Month For Sale REPORT//////////////////////////////////////////////////////////

	public ArrayList<SalesReturnReportModel> SalesReturn2Data(String Value3, String Value4) {
		ArrayList<SalesReturnReportModel> OldInvoiceData = new ArrayList<SalesReturnReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor productcursor = db.rawQuery("select distinct Grand_Total,Reason_Of_Return,Tri_Id from retail_str_sales_master_return where "
					+ " LastUpdate between '" + Value3 + "' AND '" + Value4 + "'", null);
			if (productcursor.moveToFirst()) {
				do {
					SalesReturnReportModel vm = new SalesReturnReportModel();
					vm.setGrnTotl(productcursor.getString(productcursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_GRANDTOTAL)));
					vm.setReason(productcursor.getString(productcursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_REASON)));
					vm.setTransId(productcursor.getString(productcursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
					OldInvoiceData.add(vm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return OldInvoiceData;
	}


	/////////////////////////////////////////////////////get all data from retail_str_sales_details_return ////////////////////////////////////////////////////////////////////////////

	public ArrayList<SalesReturnReportModel> getallSalesReturn2data(String Value) {
		ArrayList<SalesReturnReportModel> getpurchaselist = new ArrayList<SalesReturnReportModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = Value + "%";
		Cursor cursor = db.rawQuery("select Prod_Nm,Batch_No,Exp_Date,S_Price,Qty,MRP from retail_str_sales_details_return where "
						+ " Tri_Id like ? "
				, params);
		if (cursor.moveToFirst()) {
			do {
				SalesReturnReportModel pm = new SalesReturnReportModel();
				pm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_NM)));
				pm.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_NO)));
				pm.setExp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_DATE)));
				pm.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_PRICE)));
				pm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_QTY)));
				pm.setMrp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_MRP)));
				getpurchaselist.add(pm);

			} while (cursor.moveToNext());
		}
		return getpurchaselist;
	}

	public void updateqtyforinvoiceforsalesreturnwithoutinvoiceno(ArrayList<SalesreturndetailWithoutPo> list) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			boolean returnval = true;
			boolean fortrue = false;
			if (list == null) {
				return;
			}
			for (SalesreturndetailWithoutPo salesreturndetail : list) {

				ContentValues contentValues = new ContentValues();
				Log.e("Prodlength", String.valueOf(list.size()));
				fortrue = CheckbatchnoAlreadyInDBorNotforinvoice(salesreturndetail.getSalebatchno());

				// for(int Batch=0;Batch<list.size();Batch++) {
				if (fortrue == false) {
					PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
					PersistenceManager.getStoreId(mycontext);
					contentValues.put("Store_Id", PersistenceManager.getStoreId(mycontext));
					contentValues.put("Prod_Nm", salesreturndetail.getSaleproductname());
					contentValues.put("Batch_No", salesreturndetail.getSalebatchno());
					contentValues.put("Exp_Date", salesreturndetail.getSaleexpiry());
					contentValues.put("S_Price", salesreturndetail.getSalesellingprice());
					contentValues.put("Con_Mul_Qty", salesreturndetail.getSaleqty());
					contentValues.put("MRP", salesreturndetail.getSalemrp());
					contentValues.put("Uom", salesreturndetail.getSaleuom());
					contentValues.put("Prod_Id", salesreturndetail.getSaleProdid());

					// Inserting Row
					db.insert("retail_str_stock_master", null, contentValues);
				} else {
					String batchqty = getparticularbatchqtyforinvoice(salesreturndetail.getSalebatchno(), salesreturndetail.getSaleProdid());
					float prodQuantity = salesreturndetail.Saleqty;


					float newStockQuantity = Float.parseFloat(batchqty) + prodQuantity;
					if (newStockQuantity < 0) {
						newStockQuantity = 0;
					}
					contentValues.put("Con_Mul_Qty", Double.toString(newStockQuantity));
					int sqlUpdateRetval = db.update("retail_str_stock_master", contentValues, "Batch_No = ?  and " +
							"Prod_Id " +
							" = ? ", new String[]{salesreturndetail.getSalebatchno(), salesreturndetail.getSaleProdid()});

					Log.d("Sudhee", "Update done for batch:" + salesreturndetail.getSalebatchno() + ", prodid:" + salesreturndetail.getSaleProdid());

					if (sqlUpdateRetval < 1) {
						Log.e("Update fail", "returned: " + sqlUpdateRetval);
						returnval = false;
					}
					//return;
				}
				//}
			}
			db.close(); // Closing database connection
			Log.e("Database Operation", "row inserted...");
			return;

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

	public ArrayList<PurchaseProductModel> getAllHoldPurchaseData(String spinnervalue) {
		ArrayList<PurchaseProductModel> OldInvoiceData = new ArrayList<PurchaseProductModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = spinnervalue + "%";
		//distinct Prod_Id,Prod_Nm,P_Price,Selling_Order_Unit,MRP,Profit_Margin,S_Price,BarCode,Active,Conv_Fact,Ind_Nm
			Cursor productcursor = db.rawQuery("select Po_No,Prod_Id,Prod_Nm,P_Price,MRP,Uom,Qty,Total,Vendor_Nm,Conv_Fact,Flag,S_Price,Conv_Fact,Ind_Nm from retail_str_po_detail_hold where "
					+ " Po_No  like ?  "
					, params);
			if (spinnervalue == null) {
				return null;
			}
			if (productcursor.moveToFirst()) {
				do {
					PurchaseProductModel pm = new PurchaseProductModel();
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_PRODUCT_ID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_PRODUCT_NAME)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(Purchase_COLUMN_P_PRICE)));
					pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_VENDOR_DISTRIBUTOR_NAME)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_UOM)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(Purchase_COLUMN_QTY)));
					pm.setTotal(productcursor.getFloat(productcursor.getColumnIndex(Purchase_COLUMN_TOTAL)));
					pm.setMRP(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_MRP)));
					pm.setConversionfactor(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_Conv_Fact)));
					pm.setIndusteryname(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setSellingPrice(productcursor.getString(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setConversionfactor(productcursor.getString(productcursor.getColumnIndex(PRODUCTCONVERSION)));

					//pm.setProductId(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					/*pm.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCTNAME)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTPURCHASE)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(PRODUCTMEASUREUNITINV)));
					pm.setSellingPrice(productcursor.getString(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setMRP(productcursor.getString(productcursor.getColumnIndex(PRODUCTMRP)));
					pm.setBarcode(productcursor.getString(productcursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setProfit_Margin(productcursor.getString(productcursor.getColumnIndex(PRODUCTMARGIN)));

				pm.setIndusteryname(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));*/

					OldInvoiceData.add(pm);
				} while (productcursor.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return OldInvoiceData;
	}

	public ArrayList<PurchaseInvoiceDropDownModel> getInvoiceNumberForPurchase() {
		ArrayList<PurchaseInvoiceDropDownModel> LastInvoicelist = new ArrayList<PurchaseInvoiceDropDownModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct Po_No,Flag,Lastupdate from retail_str_po_detail_hold where Flag like 'H%'", null);

			if (cursor.moveToFirst()) {
				do {
					PurchaseInvoiceDropDownModel purchaseInvoiceDropDownModel = new PurchaseInvoiceDropDownModel();
					purchaseInvoiceDropDownModel.setPurchaseOrderNo(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_PO_NO)));
					purchaseInvoiceDropDownModel.setFlag(cursor.getString(cursor.getColumnIndex(Purchase_COLUMN_Flag)));
					//purchaseInvoiceDropDownModel.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_LASTUPDATE)));
					LastInvoicelist.add(purchaseInvoiceDropDownModel);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return LastInvoicelist;
	}

	public void replaceflag(String prod) {
		SQLiteDatabase db = this.getWritableDatabase();
		boolean returnval = true;
		ContentValues values = new ContentValues();
		values.put("Flag", "N");

		int sqlUpdateRetval = db.update("retail_str_po_detail_hold", values, "Po_No " + " = ? ",
				new String[]{prod.toString()});

		//	Log.d("Sudhee", "Update done for batch:" + salesreturndetail.getSalebatchno() + ", prodid:" + salesreturndetail.getSaleProdid());

		if (sqlUpdateRetval < 1) {
			Log.e("Update fail", "returned: " + sqlUpdateRetval);
			returnval = false;
		}
	}


	/*public void saveInventoryholdbillwithoutpo(ArrayList<Inventoryproductmodel> list, String VendorName, String grnId) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {

			if (list == null) {
				return;
			}
			for (Inventoryproductmodel prod : list) {
				PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
				PersistenceManager.getStoreId(mycontext);

				ContentValues values = new ContentValues();
				Log.e("Prodlength", String.valueOf(list.size()));
				values.put("Grn_Id", grnId);
				values.put("Vend_Nm", VendorName);
				values.put("Batch_No", prod.getBatchno());
				values.put("Exp_Date", prod.getExpdate());
				values.put("Store_Id", PersistenceManager.getStoreId(mycontext));
				values.put("Prod_Id", prod.getProductId());
				values.put("Prod_Nm", prod.getProductName());
				values.put("P_Price", prod.getPprice());
				values.put("Profit_margin", prod.getProductmargin());
				values.put("Qty", prod.getProductQuantity());
				values.put("MRP", prod.getMrp());
				values.put("S_Price", prod.getSprice());
				values.put("Amount", prod.getTotal());
				values.put("Uom", prod.getTax());
				values.put("BarCode", "NA");
				values.put("Conv_Fact",prod.getConvfact());
				values.put("Flag","H");
				values.put("Ind_Nm",prod.getIndustry());

				// Inserting Row
				db.insert("retail_str_stock_master_hold", null, values);
			}

			db.close(); // Closing database connection
			Log.e("Database Operation", "row inserted...");
			return;

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

*/


	public ArrayList<PurchaseProductModelwithpo> getholddataforinventory(String PO) {
		ArrayList<PurchaseProductModelwithpo> productlist = new ArrayList<PurchaseProductModelwithpo>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = PO + "%";

			Cursor productcursor = db.rawQuery(" select Po_No,Grn_Id,Vend_Nm, Prod_Id,Batch_No,Exp_Date, MRP,S_Price ,Prod_Nm ,P_Price, Qty, Amount,Profit_Margin, Uom,Conv_Fact,Ind_Nm,Free_Goods from retail_str_stock_master_hold where "
							+ " Grn_Id  like ? "
					, params);



			if (productcursor.moveToFirst()) {
				do {
					PurchaseProductModelwithpo pm = new PurchaseProductModelwithpo();
					//  pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_VENDOR)));

					pm.setPurchaseno(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PO_NO)));
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_ID)));
					pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(LOCALVENDORNAME)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_NAME)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_PPRICE)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(COLUMN_DETAIL_QTY)));
					pm.setBatch_No(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_BATCHNO)));
					pm.setExp_Date(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_EXPDATE)));
					pm.setTotal(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_AMOUNT)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_UOM)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_MRP)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_SPRICE)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setConversion(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustery(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setDiscountitems(productcursor.getInt(productcursor.getColumnIndex(PRODUCTFREEGOODS)));



					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}

	public ArrayList<Inventorygrnmodel> getgrnNumberForinventory() {
		ArrayList<Inventorygrnmodel> LastInvoicelist = new ArrayList<Inventorygrnmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct Grn_Id, Flag,Lastupdate from retail_str_stock_master_hold where Flag like 'H%' ", null);

			if (cursor.moveToFirst()) {
				do {
					Inventorygrnmodel purchaseInvoiceDropDownModel = new Inventorygrnmodel();
					purchaseInvoiceDropDownModel.setInventoryOrderNo(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRN_NO)));
					purchaseInvoiceDropDownModel.setFlag(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_FLAG)));
					LastInvoicelist.add(purchaseInvoiceDropDownModel);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return LastInvoicelist;
	}


	public void Updateflag(String prod) {
		SQLiteDatabase db = this.getWritableDatabase();
		boolean returnval = true;
		ContentValues values = new ContentValues();
		values.put("Flag", "N");

		int sqlUpdateRetval = db.update("retail_str_stock_master_hold", values, "Grn_Id " + " = ? ",
				new String[]{prod.toString()});

		//	Log.d("Sudhee", "Update done for batch:" + salesreturndetail.getSalebatchno() + ", prodid:" + salesreturndetail.getSaleProdid());

		if (sqlUpdateRetval < 1) {
			Log.e("Update fail", "returned: " + sqlUpdateRetval);
			returnval = false;
		}
	}


	/*

        public void saveInventoryholdbillwithpo(ArrayList<PurchaseProductModelwithpo> list, String VendorName, String Ponumbers, String grnId) {
            SQLiteDatabase db = this.getWritableDatabase();
            try {

                if (list == null) {
                    return;
                }
                for (PurchaseProductModelwithpo prod : list) {
                    PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
                    PersistenceManager.getStoreId(mycontext);

                    ContentValues values = new ContentValues();
                    Log.e("Prodlength", String.valueOf(list.size()));
                    values.put("Grn_Id", grnId);
                    values.put("Vend_Nm", VendorName);
                    values.put("Po_No", Ponumbers);
                    values.put("Batch_No", prod.getBatch_No());
                    values.put("Exp_Date", prod.getExp_Date());
                    values.put("Store_Id", PersistenceManager.getStoreId(mycontext));
                    values.put("Prod_Id", prod.getProductId());
                    values.put("Prod_Nm", prod.getProductName());
                    values.put("P_Price", prod.getProductPrice());
                    values.put("Profit_margin", prod.getProductmargin());
                    values.put("Qty", prod.getProductQuantity());
                    values.put("MRP", prod.getMrp());
                    values.put("S_Price", prod.getSprice());
                    values.put("Amount", prod.getTotal());
                    values.put("Uom", prod.getUom());
                    values.put("BarCode", "NA");
                    values.put("Conv_Fact", prod.getConversion());
                    values.put("Flag", "H");
                    values.put("Ind_Nm", prod.getIndustery());

                    // Inserting Row
                    db.insert("retail_str_stock_master_hold", null, values);
                }

                db.close(); // Closing database connection
                Log.e("Database Operation", "row inserted...");
                return;

            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

        }
        */
	public void saveInventoryholdbillwithpo(ArrayList<PurchaseProductModelwithpo> list, String VendorName, String Ponumbers, String grnId) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			boolean returnval = true;
			boolean fortrue = false;
			if (list == null) {
				return;
			}
			for (PurchaseProductModelwithpo prod : list) {
				PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
				PersistenceManager.getStoreId(mycontext);

				ContentValues values = new ContentValues();
				Log.e("Prodlength", String.valueOf(list.size()));
				//float holdqty=prod.getProductQuantity()+prod.getDiscountitems();

				// for(int Batch=0;Batch<list.size();Batch++) {
				fortrue = CheckbatchnoAlreadyInDBorNotForHoldwithpo(prod.getBatch_No());
				if (fortrue == false) {
					values.put("Grn_Id", grnId);
					values.put("Vend_Nm", VendorName);
					values.put("Po_No", Ponumbers);
					values.put("Batch_No", prod.getBatch_No());
					values.put("Exp_Date", prod.getExp_Date());
					values.put("Store_Id", PersistenceManager.getStoreId(mycontext));
					values.put("Prod_Id", prod.getProductId());
					values.put("Prod_Nm", prod.getProductName());
					values.put("P_Price", prod.getProductPrice());
					values.put("Profit_margin", prod.getProductmargin());
					values.put("Qty", prod.getProductQuantity());
					values.put("MRP", prod.getMrp());
					values.put("S_Price", prod.getSprice());
					values.put("Amount", prod.getTotal());
					values.put("Uom", prod.getUom());
					values.put("BarCode", "NA");
					values.put("Conv_Fact", prod.getConversion());
					values.put("Free_Goods", prod.getDiscountitems());
					values.put("Flag", "H");
					values.put("Ind_Nm", prod.getIndustery());
					values.put("Con_Mul_Qty",prod.getTotalqty());


					// Inserting Row
					db.insert("retail_str_stock_master_hold", null, values);
				} else {
					String batchqty = getparticularbatchqtyForHoldwithpo(prod.getBatch_No(), prod.getProductId());
					values.put("Grn_Id", grnId);
					int prodQuantity = prod.productQuantity;
					float newStockQuantity = Float.parseFloat(batchqty) + prodQuantity;
					if (newStockQuantity < 0) {
						newStockQuantity = 0;
					}
					values.put("Flag", "H");
					values.put("Qty", prod.productQuantity);
					int sqlUpdateRetval = db.update("retail_str_stock_master_hold", values, "Batch_No = ?  and " +
							"Prod_Id " +
							" = ? ", new String[]{prod.getBatch_No(), prod.getProductId()});

					Log.d("Sudhee", "Update done for batch:" + prod.getBatch_No() + ", prodid:" + prod.getProductId());

					if (sqlUpdateRetval < 1) {
						Log.e("Update fail", "returned: " + sqlUpdateRetval);
						returnval = false;
					}
					//return;
				}
				//}
			}
			db.close(); // Closing database connection
			Log.e("Database Operation", "row inserted...");
			return;

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

	public boolean CheckbatchnoAlreadyInDBorNotForHoldwithpo(String batchno) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = batchno + "%";
		//  params[1] =productid + "%";

		String Query = ("select Batch_No,Prod_Id,Qty from retail_str_stock_master_hold where "
				+ " Batch_No like ?");
		Cursor cursor = db.rawQuery(Query, params);

		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		return true;
	}


	public String getparticularbatchqtyForHoldwithpo(String batchno, String Prod_Id) {
		String getQty = null;
		SQLiteDatabase db = this.getReadableDatabase();
   /* String[] params = new String[1];
    params[0] = batchno + "%";
    params[1] = Prod_Id + "%";*/
		String Query = ("select Qty from retail_str_stock_master_hold where " + " Batch_No =  '" + batchno + "' and Prod_Id = '" + Prod_Id + "'");
		Log.e("Query::", "select Qty from retail_str_stock_master_hold where " + " Batch_No =  '" + batchno + "' and Prod_Id = '" + Prod_Id + "'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			getQty = cursor.getString(cursor.getColumnIndex(QUANTITY));
		}
		return getQty;

	}


	public void saveInventoryholdbillwithoutpo(ArrayList<Inventoryproductmodel> list, String VendorName, String grnId) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			boolean returnval = true;
			boolean fortrue = false;
			if (list == null) {
				return;
			}
			for (Inventoryproductmodel prod : list) {


				ContentValues values = new ContentValues();

				PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
				PersistenceManager.getStoreId(mycontext);

				Log.e("Prodlength", String.valueOf(list.size()));
			//	float holdqty=prod.getProductQuantity()-prod.getFreequantity();
				// for(int Batch=0;Batch<list.size();Batch++) {
				fortrue = CheckholdbatchnoAlreadyInDBorNotwithoutpo(prod.getBatchno());
				if (fortrue == false) {
					values.put("Grn_Id", grnId);
					values.put("Vend_Nm", VendorName);
					//values.put("Po_No", Ponumbers);
					//String demo = prod.getIndustry().toString();
					values.put("Batch_No", prod.getBatchno());
					values.put("Exp_Date", prod.getExpdate());
					values.put("Store_Id", PersistenceManager.getStoreId(mycontext));
					values.put("Prod_Id", prod.getProductId());
					values.put("Prod_Nm", prod.getProductName());
					values.put("P_Price", prod.getPprice());
					values.put("Profit_margin", prod.getProductmargin());
					values.put("Qty", prod.getProductQuantity());
					values.put("MRP", prod.getMrp());
					values.put("S_Price", prod.getSprice());
					values.put("Amount", prod.getTotal());
					values.put("Uom", prod.getTax());
					values.put("BarCode", "NA");
					values.put("Flag", "H");
					values.put("Con_Mul_Qty",prod.getTotalqty());
					values.put("Conv_Fact", prod.getConvfact());
					values.put("Free_Goods", prod.getFreequantity());

					// Inserting Row
					db.insert("retail_str_stock_master_hold", null, values);
				} else {

					String batchqty = getparticularholdbatchqtywithoutpo(prod.getBatchno(), prod.getProductId());
					values.put("Grn_Id", grnId);
					int prodQuantity = prod.productQuantity;
					float newStockQuantity = Float.parseFloat(batchqty) + prodQuantity;
					if (newStockQuantity < 0) {
						newStockQuantity = 0;
					}
					values.put("Flag", "H");
					values.put("Qty", prod.productQuantity);
					int sqlUpdateRetval = db.update("retail_str_stock_master_hold", values, "Batch_No = ?  and " + "Prod_Id " +
							" = ? ", new String[]{prod.getBatchno(), prod.getProductId()});

					Log.d("Sudhee", "Update done for batch:" + prod.getBatchno() + ", prodid:" + prod.getProductId());

					if (sqlUpdateRetval < 1) {
						Log.e("Update fail", "returned: " + sqlUpdateRetval);
						returnval = false;
					}
					//return;
				}
				//}
			}
			db.close(); // Closing database connection
			Log.e("Database Operation", "row inserted...");
			return;

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}


	public boolean CheckholdbatchnoAlreadyInDBorNotwithoutpo(String batchno) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = batchno + "%";
		//  params[1] =productid + "%";

		String Query = ("select Batch_No,Prod_Id,Qty from retail_str_stock_master_hold where "
				+ " Batch_No like ?");
		Cursor cursor = db.rawQuery(Query, params);

		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		return true;
	}


	public String getparticularholdbatchqtywithoutpo(String batchno, String Prod_Id) {
		String getQty = null;
		SQLiteDatabase db = this.getReadableDatabase();
  /* String[] params = new String[1];
   params[0] = batchno + "%";
   params[1] = Prod_Id + "%";*/
		String Query = ("select Qty from retail_str_stock_master_hold where " + " Batch_No =  '" + batchno + "' and Prod_Id = '" + Prod_Id + "'");
		Log.e("Query::", "select Qty from retail_str_stock_master_hold where " + " Batch_No =  '" + batchno + "' and Prod_Id = '" + Prod_Id + "'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			getQty = cursor.getString(cursor.getColumnIndex(QUANTITY));
		}
		return getQty;

	}


	/*public ArrayList<String> getVendorReturndataForVendorPayment() {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<String> LastInvoicelist = new ArrayList<String>();
		Cursor cursor = db.rawQuery("select vendor_Return_Id from retail_str_vendor_master_return ", null);
		if (cursor.moveToFirst()) {
				do {
					LastInvoicelist.add(cursor.getString(cursor.getColumnIndex(COLUMN_VENDOR_RETURNID)));
				} while (cursor.moveToNext());
			}
		return LastInvoicelist;

	}*/
	public ArrayList<String> getVendorReturndataForVendorPayment() {
		ArrayList<String> LastInvoicelist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
   /* String []params=new String[2];
      params[0]=VendorName+"%";
      params[1]="0";*/
			Cursor cursor = db.rawQuery("select distinct Vendor_Return_Id from retail_str_vendor_master_return "
					, null);

			if (cursor.moveToFirst()) {
				do {
					LastInvoicelist.add(cursor.getString(cursor.getColumnIndex(COLUMN_VENDOR_RETURNID)));
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return LastInvoicelist;
	}


/*************************************************************************************************************************/
	/***************************************
	 * CUSTOMER REJECTION
	 *******************************************************************/

	public ArrayList<String> getReasonRejection() {

		ArrayList<String> returnlist = new ArrayList<String>();
		try {

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select distinct Reason_Return from retail_store_sales_desc", null);
			if (res.moveToFirst()) {
				do {
					//returnlist.add(res.getString(res.getColumnIndex(REJECTID)));
					returnlist.add(res.getString(res.getColumnIndex(REJECTIONREASON)));
         /*returnlist.add(res.getString(res.getColumnIndex(RETURNREASON)));*/

				}
				while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return returnlist;
	}


	public ArrayList<CustomerRejectModel> getCustomerRejection(String invoiceno) {

		ArrayList<CustomerRejectModel> salesreturnlist = new ArrayList<CustomerRejectModel>();
		try {

			String[] params = new String[1];
			params[0] = invoiceno + "%";
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("Select Id,Reason_Return,LastUpdate from retail_store_sales_desc where"
					+ " Reason_Return like ? ", params);
			if (res.moveToFirst()) {
				do {
					CustomerRejectModel customerrejectiondetail = new CustomerRejectModel();

					customerrejectiondetail.setmVId(res.getString(res.getColumnIndex(REJECTIONID)));
					customerrejectiondetail.setmVReason(res.getString(res.getColumnIndex(REJECTIONREASON)));
					customerrejectiondetail.setLastupdate(res.getString(res.getColumnIndex(REJECTIONLASTUP)));
					salesreturnlist.add(customerrejectiondetail);

				}
				while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return salesreturnlist;
	}


	public void updateCReason(String STORE_ID, String VREASON) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		// contentValues.put("Store_Id", PersistenceManager.getStoreId(mycontext));
		contentValues.put("Id", getSystemCurrentTime());
		contentValues.put("Reason_Return", VREASON);

		db.insert("retail_store_sales_desc", null, contentValues);

		return;
	}

//***********************************************************************************************************************/
	//**************************************Vendor Rejection *****************************************************//

	public ArrayList<String> getReasonofReject() {

		ArrayList<String> returnlist = new ArrayList<String>();
		try {

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select distinct Reason_for_Rejection from retail_store_vend_reject", null);
			if (res.moveToFirst()) {
				do {
					///returnlist.add(res.getString(res.getColumnIndex(REJECTID)));
					returnlist.add(res.getString(res.getColumnIndex(REJECTREASON)));
         /*returnlist.add(res.getString(res.getColumnIndex(RETURNREASON)));*/

				}
				while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return returnlist;
	}


	public ArrayList<VendorRejectModel> getVendorRejection(String invoiceno) {

		ArrayList<VendorRejectModel> salesreturnlist = new ArrayList<VendorRejectModel>();
		try {

			String[] params = new String[1];
			params[0] = invoiceno + "%";
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("Select distinct Store_Id,Id,Reason_for_Rejection from retail_store_vend_reject where"
					+ " Reason_for_Rejection like ? ", params);
			if (res.moveToFirst()) {
				do {
					VendorRejectModel vendorrejectiondetail = new VendorRejectModel();
//
					vendorrejectiondetail.setmVId(res.getString(res.getColumnIndex(REJECTID)));
					vendorrejectiondetail.setmVReason(res.getString(res.getColumnIndex(REJECTREASON)));

					salesreturnlist.add(vendorrejectiondetail);

				}
				while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return salesreturnlist;
	}


	public void updateReason(String STORE_ID, String VREASON) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		contentValues.put("Store_Id", PersistenceManager.getStoreId(mycontext));
		contentValues.put("Id", getSystemCurrentTime());
		contentValues.put("Reason_for_Rejection", VREASON);

		db.insert("retail_store_vend_reject", null, contentValues);

		return;
	}
/******************************************************Activity Bill Level********************************************/


public ArrayList<String> getBillLevel() {
	ArrayList<String> billlevellist = new ArrayList<String>();
	try {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("Select distinct bill_lvl_name from retail_str_bill_lvl_disc",null);
		if (res.moveToFirst()) {
			do {
				billlevellist.add(res.getString(res.getColumnIndex(BILLLEVELNAME)));
				//billlevellist.add(billlevelmodel);
			}
			while (res.moveToNext());
		}
	} catch (CursorIndexOutOfBoundsException e) {
		e.printStackTrace();
	} catch (IndexOutOfBoundsException e) {
		e.printStackTrace();
	} catch (NullPointerException e) {
		e.printStackTrace();
	}
	return billlevellist;
}




	public void updateBillLevel(String STORE_ID, String BILLLEVELNAME, String BILLLEVELTYPE) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		// contentValues.put("Store_Id", PersistenceManager.getStoreId(mycontext));
		contentValues.put("id", getSystemCurrentTime());
		contentValues.put("bill_lvl_name", BILLLEVELNAME);
		contentValues.put("disc_type", BILLLEVELTYPE);

		db.insert("retail_str_bill_lvl_disc", null, contentValues);

		return;
	}


	public ArrayList<InventoryReportModel> getInventory1MonthDataForReport() {
		ArrayList<InventoryReportModel> distributorlist = new ArrayList<InventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct Prod_Nm,Batch_No,Exp_Date,Qty from retail_str_stock_master "
					, null);
			if (cursor.moveToFirst()) {
				do {
					InventoryReportModel im = new InventoryReportModel();
					im.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_NAME)));
					im.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NO)));
					im.setExpiry(cursor.getString(cursor.getColumnIndex(COLUMN_EXP_DATE)));
					im.setQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY)));
					distributorlist.add(im);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<InventoryReportModel> getInventory3MonthDataForReport() {
		ArrayList<InventoryReportModel> distributorlist = new ArrayList<InventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct Prod_Nm,Batch_No,Exp_Date,Qty from retail_str_stock_master "
					, null);
			if (cursor.moveToFirst()) {
				do {
					InventoryReportModel im = new InventoryReportModel();
					im.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_NAME)));
					im.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NO)));
					im.setExpiry(cursor.getString(cursor.getColumnIndex(COLUMN_EXP_DATE)));
					im.setQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY)));
					distributorlist.add(im);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<InventoryReportModel> getInventory6MonthDataForReport() {
		ArrayList<InventoryReportModel> distributorlist = new ArrayList<InventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct Prod_Nm,Batch_No,Exp_Date,Qty from retail_str_stock_master "
					, null);
			if (cursor.moveToFirst()) {
				do {
					InventoryReportModel im = new InventoryReportModel();
					im.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_NAME)));
					im.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NO)));
					im.setExpiry(cursor.getString(cursor.getColumnIndex(COLUMN_EXP_DATE)));
					im.setQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY)));
					distributorlist.add(im);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportDistributorModel> getDistributorReportforAllActive(String Name) {

		ArrayList<ReportDistributorModel> distributorlist = new ArrayList<ReportDistributorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select Dstr_Nm, Active from retail_str_dstr  where"
					+ " Active like 'Y%' or Active like 'N%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportDistributorModel dm = new ReportDistributorModel();
					dm.setDstr_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_DSTR_NAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}


	public ArrayList<ReportVendorModel> getVendorReportforAllActive(String Name) {
		ArrayList<ReportVendorModel> distributorlist = new ArrayList<ReportVendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select Vend_Nm, Active from retail_store_vendor  where"
					+ " Active like 'Y%' or Active like 'N%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorModel dm = new ReportVendorModel();
					dm.setVend_Nm(cursor.getString(cursor.getColumnIndex(LOCALVENDORNAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(LOCALVENDORACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportProductPharmaModel> getProductPharmaReportforAllActive(String name) {
		ArrayList<ReportProductPharmaModel> distributorlist = new ArrayList<ReportProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select Prod_Nm, Active from retail_store_prod  where"
					+ " Active like 'Y%' or Active like 'N%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportProductPharmaModel dm = new ReportProductPharmaModel();
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTNAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(PRODUCTACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportProductCpgModel> getCpgReportforAllActive(String name) {
		ArrayList<ReportProductCpgModel> distributorlist = new ArrayList<ReportProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select Prod_Nm, Active from retail_store_prod_cpg  where"
					+ " Active like 'Y%' or Active like 'N%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportProductCpgModel dm = new ReportProductCpgModel();
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_NM)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_ACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportLocalProductCpgModel> getLocalCpgReportforAllActive(String name) {
		ArrayList<ReportLocalProductCpgModel> distributorlist = new ArrayList<ReportLocalProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select Prod_Nm, Active from retail_store_prod_local_cpg  where"
					+ " Active like 'Y%' or Active like 'N%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductCpgModel dm = new ReportLocalProductCpgModel();
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_NM)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_ACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportLocalProductPharmaModel> getLocalProductPharmaReportforAllActive(String name) {
		ArrayList<ReportLocalProductPharmaModel> distributorlist = new ArrayList<ReportLocalProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select Prod_Nm, Active from retail_store_prod_local  where"
					+ " Active like 'Y%' or Active like 'N%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductPharmaModel dm = new ReportLocalProductPharmaModel();
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPRODUCTACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<VendorReportModel> getPurchasing1MonthDataForReport() {

		ArrayList<VendorReportModel> vendorlist = new ArrayList<VendorReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
      /*String[] params = new String[1];
      params[0] = PoNo + "%";*/
			Cursor cursor = db.rawQuery("select distinct Po_No,Vendor_Nm,Total from retail_str_po_detail  "
					, null);
			if (cursor.moveToFirst()) {
				do {
					VendorReportModel vm = new VendorReportModel();
					vm.setPo_No(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_PO_NO)));
					vm.setVendor_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_TOTAL)));
					vendorlist.add(vm);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;
	}



	public ArrayList<VendorReportModel> getPurchasing3MonthDataForReport() {

		ArrayList<VendorReportModel> vendorlist = new ArrayList<VendorReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
      /*String[] params = new String[1];
      params[0] = PoNo + "%";*/
			Cursor cursor = db.rawQuery("select distinct Po_No,Vendor_Nm,Total from retail_str_po_detail "
					, null);
			if (cursor.moveToFirst()) {
				do {
					VendorReportModel vm = new VendorReportModel();
					vm.setPo_No(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_PO_NO)));
					vm.setVendor_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_TOTAL)));
					vendorlist.add(vm);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;
	}

/////////////////////////////////////get DATA From year and Month For VendorReturnReport REPORT///////////////////////////////

	public ArrayList<ReportVendorReturnModel> VendorReturnReportDataForMonth(String Value1, String Value2) {
		ArrayList<ReportVendorReturnModel> purchaseData = new ArrayList<ReportVendorReturnModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select distinct Vendor_Nm,Prod_Nm,Batch_No,Exp_Date,Reason_Of_Return,Qty,P_Price,Total,Uom from retail_str_vendor_detail_return where "
					+ " LastUpdate between '" + Value1 + "' AND '" + Value2 + "'", null);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorReturnModel pm = new ReportVendorReturnModel();
					pm.setVendrNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTVENDR_NM)));
					pm.setProdctNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTPROD_NM)));
					pm.setBatchNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTBATCH_NO)));
					pm.setExpDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTEXP_DATE)));
					pm.setReason(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTREASON_RETRN)));
					pm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_QTY)));
					pm.setPPrice(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTP_Price)));
					pm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_TOTAL)));
					pm.setUom(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_UOM)));
					purchaseData.add(pm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return purchaseData;
	}

	public ArrayList<ReportVendorReturnModel> getVendorReturn1MonthDataForReport() {
		ArrayList<ReportVendorReturnModel> distributorlist = new ArrayList<ReportVendorReturnModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct Vendor_Nm,Prod_Nm,Batch_No,Exp_Date,Reason_Of_Return,Qty,P_Price,Total,Uom from retail_str_vendor_detail_return "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorReturnModel pm = new ReportVendorReturnModel();
					pm.setVendrNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTVENDR_NM)));
					pm.setProdctNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTPROD_NM)));
					pm.setBatchNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTBATCH_NO)));
					pm.setExpDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTEXP_DATE)));
					pm.setReason(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTREASON_RETRN)));
					pm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_QTY)));
					pm.setPPrice(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTP_Price)));
					pm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_TOTAL)));
					pm.setUom(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_UOM)));
					distributorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportVendorReturnModel> getVendorReturn3MonthDataForReport() {
		ArrayList<ReportVendorReturnModel> distributorlist = new ArrayList<ReportVendorReturnModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct Vendor_Nm,Prod_Nm,Batch_No,Exp_Date,Reason_Of_Return,Qty,P_Price,Total,Uom from retail_str_vendor_detail_return "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorReturnModel pm = new ReportVendorReturnModel();
					pm.setVendrNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTVENDR_NM)));
					pm.setProdctNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTPROD_NM)));
					pm.setBatchNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTBATCH_NO)));
					pm.setExpDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTEXP_DATE)));
					pm.setReason(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTREASON_RETRN)));
					pm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_QTY)));
					pm.setPPrice(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTP_Price)));
					pm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_TOTAL)));
					pm.setUom(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_UOM)));
					distributorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportVendorReturnModel> getVendorReturn6MonthDataForReport() {
		ArrayList<ReportVendorReturnModel> distributorlist = new ArrayList<ReportVendorReturnModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct Vendor_Nm,Prod_Nm,Batch_No,Exp_Date,Reason_Of_Return,Qty,P_Price,Total,Uom from retail_str_vendor_detail_return "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorReturnModel pm = new ReportVendorReturnModel();
					pm.setVendrNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTVENDR_NM)));
					pm.setProdctNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTPROD_NM)));
					pm.setBatchNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTBATCH_NO)));
					pm.setExpDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTEXP_DATE)));
					pm.setReason(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTREASON_RETRN)));
					pm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_QTY)));
					pm.setPPrice(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTP_Price)));
					pm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_TOTAL)));
					pm.setUom(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_UOM)));
					distributorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}


	public ArrayList<SaleReportModel> getDailySalesReport() {
		ArrayList<SaleReportModel> idlist = new ArrayList<SaleReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct Tri_Id,Total,Uom,Prod_Nm,Batch_No from retail_str_sales_detail "
					, null);
			if (cursor.moveToFirst()) {
				do {
					SaleReportModel sm = new SaleReportModel();
					sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_ID)));
					sm.setGrnTotl(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_TOTAL)));
					sm.setUom(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_UOM)));
					sm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NM)));
					sm.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NO)));
					idlist.add(sm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return idlist;
	}


	public ArrayList<IndirectVendorPaymentModel> getIndirectCash1MonthDataForReport() {
		ArrayList<IndirectVendorPaymentModel> distributorlist = new ArrayList<IndirectVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct Vend_Dstr_Nm,Amount,Received_Amount,Due_Amount,Reason_Of_Pay,LastUpdate from tmp_vend_dstr_payment "
					, null);
			if (cursor.moveToFirst()) {
				do {
					IndirectVendorPaymentModel pm = new IndirectVendorPaymentModel();
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setReasonOfPay(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_REASN_OF_PAY)));
					pm.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_UPDTE)));
					distributorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}


	public ArrayList<IndirectVendorPaymentModel> getIndirectCash3MonthDataForReport() {
		ArrayList<IndirectVendorPaymentModel> distributorlist = new ArrayList<IndirectVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct Vend_Dstr_Nm,Amount,Received_Amount,Due_Amount,Reason_Of_Pay,LastUpdate from tmp_vend_dstr_payment "
					, null);
			if (cursor.moveToFirst()) {
				do {
					IndirectVendorPaymentModel pm = new IndirectVendorPaymentModel();
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setReasonOfPay(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_REASN_OF_PAY)));
					pm.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_UPDTE)));
					distributorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<IndirectVendorPaymentModel> getIndirectCash6MonthDataForReport() {
		ArrayList<IndirectVendorPaymentModel> distributorlist = new ArrayList<IndirectVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct Vend_Dstr_Nm,Amount,Received_Amount,Due_Amount,Reason_Of_Pay,LastUpdate from tmp_vend_dstr_payment "
					, null);
			if (cursor.moveToFirst()) {
				do {
					IndirectVendorPaymentModel pm = new IndirectVendorPaymentModel();
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setReasonOfPay(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_REASN_OF_PAY)));
					pm.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_UPDTE)));
					distributorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}


	public ArrayList<DirectVendorPaymentModel> getDirectCash1MonthDataForReport() {
		ArrayList<DirectVendorPaymentModel> distributorlist = new ArrayList<DirectVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct Vend_Dstr_Nm,Amount,Received_Amount,Due_Amount,LastUpdate from tmp_vend_dstr_payment "
					, null);
			if (cursor.moveToFirst()) {
				do {
					DirectVendorPaymentModel pm = new DirectVendorPaymentModel();
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_UPDTE)));
					distributorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}


	public ArrayList<DirectVendorPaymentModel> getDirectCash3MonthDataForReport() {
		ArrayList<DirectVendorPaymentModel> distributorlist = new ArrayList<DirectVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct Vend_Dstr_Nm,Amount,Received_Amount,Due_Amount,LastUpdate from tmp_vend_dstr_payment "
					, null);
			if (cursor.moveToFirst()) {
				do {
					DirectVendorPaymentModel pm = new DirectVendorPaymentModel();
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_UPDTE)));
					distributorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}


	public ArrayList<DirectVendorPaymentModel> getDirectCash6MonthDataForReport() {
		ArrayList<DirectVendorPaymentModel> distributorlist = new ArrayList<DirectVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct Vend_Dstr_Nm,Amount,Received_Amount,Due_Amount,LastUpdate from tmp_vend_dstr_payment "
					, null);
			if (cursor.moveToFirst()) {
				do {
					DirectVendorPaymentModel pm = new DirectVendorPaymentModel();
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_UPDTE)));
					distributorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}


	/////////////////////////////////////get DATA From year and Month For IndirectPayByChequeReport REPORT///////////////////////////////

	public ArrayList<IndirectVendorPaymentModel> IndirectPayByChequeReportDataForMonth(String Value1, String Value2) {
		ArrayList<IndirectVendorPaymentModel> purchaseData = new ArrayList<IndirectVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select distinct Vend_Dstr_Nm,Amount,Received_Amount,Due_Amount,Bank_Name,Cheque_No,Reason_Of_Pay,LastUpdate from tmp_vend_dstr_payment where "
					+ " LastUpdate between '" + Value1 + "' AND '" + Value2 + "'", null);
			if (cursor.moveToFirst()) {
				do {
					IndirectVendorPaymentModel pm = new IndirectVendorPaymentModel();
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setBankName(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_BANK_NM)));
					pm.setChequeNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CHEQUE_NO)));
					pm.setReasonOfPay(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_REASN_OF_PAY)));
					pm.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_UPDTE)));
					purchaseData.add(pm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return purchaseData;
	}


	/////////////////////////////////////get DATA From year and Month For DirectPayByCashReport REPORT///////////////////////////////

	public ArrayList<DirectVendorPaymentModel> DirectPayByCashReportDataForMonth(String Value1, String Value2) {
		ArrayList<DirectVendorPaymentModel> purchaseData = new ArrayList<DirectVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select distinct Vend_Dstr_Nm,Amount,Received_Amount,Due_Amount,LastUpdate from tmp_vend_dstr_payment where "
					+ " LastUpdate between '" + Value1 + "' AND '" + Value2 + "'", null);
			if (cursor.moveToFirst()) {
				do {
					DirectVendorPaymentModel pm = new DirectVendorPaymentModel();
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_UPDTE)));
					purchaseData.add(pm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return purchaseData;
	}


	/////////////////////////////////////get DATA From year and Month For DirectPayByChequeReport REPORT///////////////////////////////

	public ArrayList<DirectVendorPaymentModel> DirectPayByChequeReportDataForMonth(String Value1, String Value2) {
		ArrayList<DirectVendorPaymentModel> purchaseData = new ArrayList<DirectVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select distinct Vend_Dstr_Nm,Amount,Received_Amount,Due_Amount,Bank_Name,Cheque_No,LastUpdate from tmp_vend_dstr_payment where "
					+ " LastUpdate between '" + Value1 + "' AND '" + Value2 + "'", null);
			if (cursor.moveToFirst()) {
				do {
					DirectVendorPaymentModel pm = new DirectVendorPaymentModel();
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setBankName(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_BANK_NM)));
					pm.setChequeNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CHEQUE_NO)));
					pm.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_UPDTE)));
					purchaseData.add(pm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return purchaseData;
	}


}



