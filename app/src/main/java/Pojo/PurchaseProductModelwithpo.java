package Pojo;

import android.util.Log;

/**
 * Created by Rahul on 4/7/2016.
 */
public class PurchaseProductModelwithpo {



    String Purchaseno;
    String ProductName;
    String ProductId;
    float ProductPrice;
    String Uom;
    String VendorName;
    String Exp_Date;
    String Batch_No;
    String Barcode;
    float totalqty;
    float productmargin;
    float conversion;
    String Industery;
    float Sprice;
    float Mrp;
    public int productQuantity;
    public   int discountitems;
    float Total;
    float GrandTotal;


    public float getTotalqty() {
        return totalqty;
    }

    public void setTotalqty(float totalqty) {
        this.totalqty = totalqty;
        try {
            totalqty = ((productQuantity+discountitems)* conversion);
        } catch( Exception e) {

        }
    }

    public float getConversion() {
        return conversion;
    }

    public void setConversion(float conversion) {

        this.conversion = conversion;
        try {
            totalqty = ((productQuantity+discountitems)* conversion);
        } catch( Exception e) {

        }
    }


    public String getIndustery() {
        return Industery;
    }

    public void setIndustery(String industery) {
        Industery = industery;
    }


    public float getProductmargin() {
        return productmargin;
    }

    public void setProductmargin(float productmargin) {

        this.productmargin = productmargin;



        try {
            productmargin  =((Mrp-ProductPrice)/Mrp)*100;
        } catch( Exception e) {

        }

    }



    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }


    public float getSprice() {
        return Sprice;
    }

    public void setSprice(float sprice)
    {

        Sprice = sprice;


        try {
            productmargin  =((Mrp-ProductPrice)/Mrp)*100;
        } catch( Exception e) {

        }
    }

    public float getMrp() {
        return Mrp;
    }

    public void setMrp(float mrp) {
        Mrp = mrp;
    }




    public float getDiscountitems() {
        return discountitems;
    }

    public void setDiscountitems(int discountitems) {
        this.discountitems = discountitems;

        try {
            Total = productQuantity * ProductPrice;
          //  productQuantity=productQuantity+discountitems;
            totalqty = ((productQuantity+discountitems)* conversion);
        } catch( Exception e) {

        }
    }


    public String getExp_Date() {
        return Exp_Date;
    }

    public void setExp_Date(String exp_Date) {
        Exp_Date = exp_Date;
    }


    public String getBatch_No() {
        return Batch_No;
    }

    public void setBatch_No(String batch_No) {
        Batch_No = batch_No;
    }

    public String getPurchaseno() {
        return Purchaseno;
    }

    public void setPurchaseno(String purchaseno) {
        Purchaseno = purchaseno;
    }


    public String getVendorName() {
        return VendorName;
    }
    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }

    public String getUom() {
        return Uom;
    }
    public void setUom(String uom) {
        Uom = uom;
    }

    public float getGrandTotal() {
        return GrandTotal;
    }
    public void setGrandTotal(float grandTotal) {
        GrandTotal = grandTotal;
    }


    public float getTotal() {
        return Total;
    }

    public void setTotal(float total) {
        Total=total;
        try {
            Total = productQuantity * ProductPrice;
        } catch( Exception e) {

        }
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
        try {
            Total = productQuantity * ProductPrice;
            totalqty = ((productQuantity+discountitems)* conversion);
         //   productQuantity=productQuantity+discountitems;

        } catch( Exception e) {

        }
    }
    //  Log.i("&&&&&&&&&", "set productQuantity to " + productQuantity + " for "  + ProductName );

    public float getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(float productPrice) {
        ProductPrice=productPrice;
        try {
            Total = productQuantity * ProductPrice;
            productmargin  =((Mrp-ProductPrice)/Mrp)*100;


        } catch( Exception e) {

        }
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public PurchaseProductModelwithpo() {
        Log.i("&&&&&&&&", "Constructor of PurchaseProductModelwithpo called ");
        this.productQuantity = 1;
        this.ProductPrice=0.0F;
        this.discountitems=0;
        this.Batch_No="";
        this.Exp_Date="2019/12/31";
    }

    @Override
    public String toString() {
        //return "Name:" + ProductName + " , Quantity:" + productQuantity   ;
        return VendorName;
    }



}