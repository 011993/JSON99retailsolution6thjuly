package Pojo;

/**
 * Created by rspl-rahul on 29/4/16.
 */
public class VendorReturnModel {
    String grnID;
    String ProductName;
    String ProductId;
    String mrp;
    float Sprice;
    float pprice;
    String measure;
    String expdate;
    String batchno;
    String UOM;
    float Stock;
    public float productQuantity;
    float Total;
    String Barcode;
    String FreeQty;

    public String getFreeQty() {
        return FreeQty;
    }

    public void setFreeQty(String freeQty) {
        FreeQty = freeQty;
    }

    public VendorReturnModel() {
        this.productQuantity=1.0f;
    }

    @Override
    public String toString() {
        return ProductName ;
    }

    public String getGrnID() {
        return grnID;
    }

    public void setGrnID(String grnID) {
        this.grnID = grnID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        this.ProductName = productName;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public float getSprice() {
        return Sprice;
    }

    public void setSprice(float sprice) {
        try {
            Total = productQuantity *Sprice;
        } catch( Exception e) {

        }
    }

    public float getPprice() {
        return pprice;
    }

    public void setPprice(float pprice) {
        this.pprice = pprice;
        Total=productQuantity*pprice;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public float getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(float productQuantity) {
        this.productQuantity = productQuantity;
        try {
            Total=productQuantity*pprice;
        } catch( Exception e) {

        }
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float total) {
        Total = total;
        Total=productQuantity*pprice;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public float getStock() {
        return Stock;
    }

    public void setStock(float stock) {
        Stock = stock;
    }
}