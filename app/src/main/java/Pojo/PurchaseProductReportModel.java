package Pojo;

import android.util.Log;

/**
 * Created by rspl-rahul on 10/3/16.
 */
public class PurchaseProductReportModel {
    String ProductName;
    String ProductId;
    float ProductPrice;
    String Uom;
    String VendorName;
    float productQuantity;
    float Total;
    float GrandTotal;

    public String getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        LastUpdate = lastUpdate;
    }

    String LastUpdate;

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
        Total = productQuantity*ProductPrice;

    }
    public float getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(float productQuantity) {
        this.productQuantity = productQuantity;
        try {
            Total = productQuantity * ProductPrice;


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
            Total = productQuantity *productPrice;
            //Total = productPrice*productQuantity;
        } catch (Exception e) {
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

    public PurchaseProductReportModel() {
        Log.i("&&&&&&&&", "Constructor of PurchaseProductReportModel called ");
        this.productQuantity = 1.0F;
        this.ProductPrice=0.0F;
    }

    @Override
    public String toString() {
        //return "Name:" + ProductName + " , Quantity:" + productQuantity   ;
        return ProductId +"     "+ProductName + ", " + productQuantity;
    }
}
/*
*
*    String ProductName;
    String ProductId;
    String ProductPrice;
    String Uom;
    float productQuantity;
    float Total;
    float GrandTotal;

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
        Total = productQuantity*Float.parseFloat(ProductPrice);
    }
    public float getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(float productQuantity) {
        this.productQuantity = productQuantity;
        try {
            Total = productQuantity * Float.parseFloat(ProductPrice);
        } catch( Exception e) {

        }
    }
    //  Log.i("&&&&&&&&&", "set productQuantity to " + productQuantity + " for "  + ProductName );

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice=productPrice;
        try {
            Total = productQuantity * Float.parseFloat(ProductPrice);
        } catch (Exception e) {
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

    public PurchaseProductReportModel() {
        Log.i("&&&&&&&&", "Constructor of PurchaseProductReportModel called " );
        this.productQuantity = 1.0F;
        this.ProductPrice="0";
    }

    @Override
    public String toString() {
        //return "Name:" + ProductName + " , Quantity:" + productQuantity   ;
        return ProductId +"     "+ProductName;
    }*/