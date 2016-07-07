package Pojo;



public class VendorReportModel
{
    String Po_No;
    String Vendor_Nm;
    String Total;

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getVendor_Nm() {
        return Vendor_Nm;
    }

    public void setVendor_Nm(String vendor_Nm) {
        this.Vendor_Nm = vendor_Nm;
    }

    public String getPo_No() {
        return Po_No;
    }

    public void setPo_No(String po_No) {
        this.Po_No = po_No;
    }

    public VendorReportModel() {

    }

    @Override
    public String toString() {
        return  Vendor_Nm;
    }



}

