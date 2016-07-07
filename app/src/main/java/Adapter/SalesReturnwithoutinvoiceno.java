package Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.mycompany.apps.ActivitySalesreturn;
import com.mycompany.apps.Activity_Salesreturn_withoutinvoiceno;
import com.mycompany.apps.DBhelper;
import com.mycompany.apps.R;
//import com.mycompany.apps.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Pojo.PurchaseProductModel;
import Pojo.SalesReturn;
import Pojo.Salesreturndetail;
import Pojo.SalesreturndetailWithoutPo;

/**
 * Created by rspl-nishant on 1/4/16.
 */
public class SalesReturnwithoutinvoiceno extends ArrayAdapter<SalesreturndetailWithoutPo> {
    private final int layoutResourceId;
    private Context mcontext;
    Activity_Salesreturn_withoutinvoiceno activity;
    private LayoutInflater mInflater;
    TextView tv;
    TextView v;
    DBhelper mydb;

    public SalesReturnwithoutinvoiceno (Activity_Salesreturn_withoutinvoiceno activity, int layoutResourceId, ArrayList<SalesreturndetailWithoutPo> msalesreturnlist) {
        super(activity, layoutResourceId,  msalesreturnlist);
        this.activity = activity;
        this.salesreturnlist=  msalesreturnlist;
        this.layoutResourceId = layoutResourceId;
//        mInflater = (LayoutInflater) activity
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    public void setSalesReturnList(ArrayList<SalesreturndetailWithoutPo> salesreturnlist) {
        this.salesreturnlist = salesreturnlist;
    }

    private ArrayList<Pojo.SalesreturndetailWithoutPo> salesreturnlist;



    public int getCount() {
        if (salesreturnlist.size() < 0)
            return 1;
        return salesreturnlist.size();
    }


    public SalesreturndetailWithoutPo getItem(int position) {
        return salesreturnlist.get(position);
    }


    public long getItemId(int position) {

        //.getCustomermobileno();
        return position;
    }

    public void clearAllRows() {
        salesreturnlist.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder {

        public TextView Transid;
        // public TextView Billno;
        public EditText batchno;
        // public TextView BatchNo;
        // public TextView mrp;
        public EditText quantity;
        public TextView uom;
        public TextView productname;
        public TextView sellingprice;
        public TextView total;
         public TextView convertionfactorreturn;
        // public Spinner  reason;
        public ImageButton Delete;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();


            LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.displaysalewithoutinvoiceno, parent, false);
            // holder.Transid = (TextView) convertView.findViewById(R.id.transid);
            // holder.Billno = (TextView) convertView.findViewById(R.id.billno);
            holder.batchno = (EditText) convertView.findViewById(R.id.batchno);
            // holder.BatchNo = (TextView) convertView.findViewById(R.id.batchno);
            // holder.reason = (EditText) convertView.findViewById(R.id.reason);
            holder.quantity= (EditText) convertView.findViewById(R.id.quty);
            // holder. reason = (Spinner) convertView.findViewById(R.id.reasonreturn);
            holder.uom = (TextView) convertView.findViewById(R.id.uom);
            //   holder.saledate = (TextView)convertView.findViewById(R.id.saledate);
            holder.productname = (TextView) convertView.findViewById(R.id.prodname);
            holder.sellingprice = (TextView) convertView.findViewById(R.id.selling);
            holder.total= (TextView) convertView.findViewById(R.id.totalpt);
            holder.Delete = (ImageButton) convertView .findViewById(R.id.deleteButton);
            holder.convertionfactorreturn=(TextView)convertView.findViewById(R.id.convefactreturn);

            //  holder.mrp = (TextView) convertView.findViewById(R.id.mrp);







            convertView.setTag(holder);
        }
        //holder.name.setText(mcustomerlist.get(position).getCustomername());
        else {
            holder = (ViewHolder) convertView.getTag();

        }
        DecimalFormat f= new DecimalFormat("##.00");
//            holder.Transid.setText(salesreturnlist.get(position).getSaleTransid());
        // holder.Billno.setText(salesreturnlist.get(position).getSaleBillno());
        holder.productname.setText(salesreturnlist.get(position).getSaleproductname());
        // holder.saledate.setText(salesreturnlist.get(position).getSaleDate());
        // holder.BatchNo.setText(salesreturnlist.get(position).getSalebatchno());

        // holder.mrp.setText(salesreturnlist.get(position).getSalemrp());
        // holder.reason.setText(salesreturnlist.get(position).);
        if ((holder.batchno.getTag() != null) && (holder.batchno.getTag() instanceof TextWatcher)) {
            holder.batchno.removeTextChangedListener((TextWatcher) holder.batchno.getTag());
        }
        holder.batchno.setText(salesreturnlist.get(position).getSalebatchno());


        if ((holder.quantity.getTag() != null) && (holder.quantity.getTag() instanceof TextWatcher)) {
            holder.quantity.removeTextChangedListener((TextWatcher) holder.quantity.getTag());
        }
        holder.quantity.setText(String.format("%.2f", salesreturnlist.get(position).getSaleqty()));

        holder.uom.setText(salesreturnlist.get(position).getSaleuom());

        if ((holder.sellingprice.getTag() != null) && (holder.sellingprice.getTag() instanceof TextWatcher)) {
            holder.sellingprice.removeTextChangedListener((TextWatcher) holder.sellingprice.getTag());
        }
        holder.sellingprice.setText(String.format("%.2f", salesreturnlist.get(position).getSalesellingprice()));
        if ((holder.convertionfactorreturn.getTag() != null) && (holder.convertionfactorreturn.getTag() instanceof TextWatcher)) {
            holder.convertionfactorreturn.removeTextChangedListener((TextWatcher) holder.convertionfactorreturn.getTag());
        }
        holder.convertionfactorreturn.setText(salesreturnlist.get(position).getConversionfactorreturn());

        holder.total.setText(String.format("%.2f", salesreturnlist.get(position).getSaletotal()));

        holder.total.setText(String.valueOf(f.format(Double.parseDouble(holder.sellingprice.getText().toString()) / (Double.parseDouble(holder.convertionfactorreturn.getText().toString())) * Double.parseDouble(holder.quantity.getText().toString()))));
        TextWatcher quantityTextChangeWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {

DecimalFormat f= new DecimalFormat("##.00");

                    holder.total.setText(String.valueOf(f.format(Double.parseDouble(holder.sellingprice.getText().toString()) / (Double.parseDouble(holder.convertionfactorreturn.getText().toString())) * Double.parseDouble(holder.quantity.getText().toString()))));

                    salesreturnlist.get(position).setSaleqty(Float.parseFloat(holder.quantity.getText().toString()));
                    salesreturnlist.get(position).setSalesellingprice(Float.parseFloat(holder.sellingprice.getText().toString()));
                    salesreturnlist.get(position).setSalebatchno(holder.batchno.getText().toString());



                    Log.e("&&&total&&&&", "$$$$$" + holder.total.getText().toString());
                    activity.setSummaryRow();

                } catch (Exception e) {
                    Log.e("&&&&&RAhulTest&&&", "Exception " + e + " while parsing double");
                    e.printStackTrace();
                }
            }
        };
        holder.quantity.addTextChangedListener(quantityTextChangeWatcher);
        holder.quantity.setTag(quantityTextChangeWatcher);

        holder.sellingprice.addTextChangedListener(quantityTextChangeWatcher);
        holder.sellingprice.setTag(quantityTextChangeWatcher);

        holder.batchno.addTextChangedListener(quantityTextChangeWatcher);
        holder.batchno.setTag(quantityTextChangeWatcher);


        holder.convertionfactorreturn.addTextChangedListener(quantityTextChangeWatcher);
        holder.convertionfactorreturn.setTag(quantityTextChangeWatcher);


        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salesreturnlist.remove(position);
                activity.setSummaryRow();
                notifyDataSetChanged();
            }
        });

//       holder.name.setText(customerlist.get(position).getCustomername());
//        holder.mobile_no.setText(customerlist.get(position).getCustomermobileno());

        return convertView;


    }
    public void addProductToList( SalesreturndetailWithoutPo product ) {
        Log.e("&&&&&&&&", "Adding product " + product.toString() + " to product list");

        SalesreturndetailWithoutPo productAlreadyInList = findProductInList(product);
        if(productAlreadyInList == null) {
            salesreturnlist.add(product);
        } else {
            productAlreadyInList.setSaleqty(productAlreadyInList.getSaleqty() + product.getSaleqty());

        }

    }

    public ArrayList<SalesreturndetailWithoutPo> getList() {
        return salesreturnlist;
    }

    private SalesreturndetailWithoutPo findProductInList(SalesreturndetailWithoutPo product) {
        SalesreturndetailWithoutPo returnProductVal = null;

        for( SalesreturndetailWithoutPo productInList : salesreturnlist) {
            if( productInList.getSaleProdid().trim().equals(product.getSaleProdid().trim()) ) {
                returnProductVal = productInList;
            }
        }
        return returnProductVal;
    }

    public float getGrandTotal() {
        // ViewHolder holder=new ViewHolder();
        float finalamount = 0.0f;
        for (int listIndex = 0; listIndex < salesreturnlist.size(); listIndex++) {
            try {
                finalamount += (salesreturnlist.get(listIndex).getSaletotal());
            } catch (Exception e) {
                //ignore exeption for parse
            }
            Log.e("&&&&&^^^^^^^^", "$$$$$$$$" + finalamount);

        }
        Log.e("&&&&55555555&&&", "Total Price is:" + finalamount);
        return finalamount;
    }
}