package Adapter;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mycompany.apps.DecimalDigitsInputFilter;
import com.mycompany.apps.R;
import com.mycompany.apps.VendorReturnActivity;


import java.text.DecimalFormat;
import java.util.ArrayList;
import Pojo.VendorReturnModel;

/**
 * Created by rspl-rahul on 3/5/16.
 */
public class VendorReturnProductListAdapter extends BaseAdapter {
    VendorReturnActivity activity;
    private final int layoutResourceId;
    ArrayList<VendorReturnModel> list;
    LayoutInflater layoutInflater;
    VendorReturnModel vendorReturnModel;


    public VendorReturnProductListAdapter(VendorReturnActivity activity, int textViewResourceId, ArrayList<VendorReturnModel> objects,VendorReturnModel vendorReturnModel) {
        //super(activity, textViewResourceId, objects);
        this.activity=activity;
        this.layoutResourceId=textViewResourceId;
        this.list=objects;
        this.vendorReturnModel=vendorReturnModel;
    }



    public void setList(ArrayList<VendorReturnModel> list) {
        this.list = list;
    }

    public int getCount() {
        if(list.size()<0)

            return 1;
        Log.e("**get Count***", list.toString());
        return list.size();
    }
    public VendorReturnModel getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        Log.e("**********", "" + position);
        return position;
    }

    public static class ViewHolder
    {
        TextView vendorName;
        TextView ProductName;
        TextView ExpDate;
        EditText productQuantity;
        TextView MRP;
        TextView Stock;
        TextView PurchasePrice;
        TextView BatchNumber;
        TextView Uom;
        TextView Total;
        TextView FreeQty;
        public ImageButton DeleteButton;

    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        DecimalFormat f = new DecimalFormat("##.0");

        if(convertView==null){
            holder= new ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.display_vendor_return,parent,false);
            holder.ProductName=(TextView)convertView.findViewById(R.id.prodnameVendorreturn);
            holder.ExpDate=(TextView)convertView.findViewById(R.id.expdateVendorReturn);
            holder.productQuantity=(EditText)convertView.findViewById(R.id.qtyVendorReturn);
            holder.BatchNumber=(TextView)convertView.findViewById(R.id.BatchNumberVendorReturn);
            holder.PurchasePrice=(TextView)convertView.findViewById(R.id.PPriceVendorReturn);
            holder.Stock=(TextView)convertView.findViewById(R.id.StockVendorReturn);
            holder.Uom=(TextView)convertView.findViewById(R.id.uomVendorReturn);
            holder.Total=(TextView)convertView.findViewById(R.id.totalVendorReturn);
            holder.Total.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});


            holder.DeleteButton=(ImageButton)convertView.findViewById(R.id.deleteButtonVendorReturn);
           // holder.FreeQty=(TextView)convertView.findViewById(R.id.FreeVendorReturn);
            convertView.setTag(holder);


        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        //   holder.vendorName.setText(list.get(position).getVendorName());
        holder.ProductName.setText(list.get(position).getProductName());
        holder.ExpDate.setText(list.get(position).getExpdate());
        holder.BatchNumber.setText(list.get(position).getBatchno());
      //  holder.FreeQty.setText(list.get(position).getFreeQty());
        // holder.SellingPrice.setText(String.format("%.2f",list.get(position).getSprice()));
        if( (holder.PurchasePrice.getTag() != null)  && (holder.PurchasePrice.getTag() instanceof TextWatcher) ) {
            holder.PurchasePrice.removeTextChangedListener((TextWatcher) holder.PurchasePrice.getTag() );
        }

        holder.PurchasePrice.setText(String.format("%.2f",list.get(position).getPprice()));
        holder.Uom.setText(list.get(position).getUOM());
        holder.Stock.setText(String.format("%.2f",list.get(position).getStock()));


        if( (holder.productQuantity.getTag() != null)  && (holder.productQuantity.getTag() instanceof TextWatcher) ) {
            holder.productQuantity.removeTextChangedListener((TextWatcher) holder.productQuantity.getTag() );
        }

        holder.productQuantity.setText(String.format("%.2f", list.get(position).getProductQuantity()));
        holder.Total.setText(String.valueOf(Double.parseDouble(holder.PurchasePrice.getText().toString()) * Double.parseDouble(holder.productQuantity.getText().toString())));
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

                    if (holder.productQuantity.getText().toString().trim().equals("") || holder.productQuantity.getText().toString().trim().equals("0.00")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                       return;
                    }else if (Double.parseDouble(holder.productQuantity.getText().toString().trim()) > Double.parseDouble(holder.Stock.getText().toString()))
                    {
                        holder.productQuantity.setText("");
                        return;
                    }
                    holder.Total.setText(String.valueOf(Double.parseDouble(holder.PurchasePrice.getText().toString()) * Double.parseDouble(holder.productQuantity.getText().toString())));
                    list.get(position).setProductQuantity(Float.parseFloat(holder.productQuantity.getText().toString()));
                    list.get(position).setPprice(Float.parseFloat(holder.PurchasePrice.getText().toString()));

                    Log.e("&&&total&&&&", "$$$$$" + holder.Total.getText().toString());
                    activity.setSummaryRow();
                } catch (Exception e) {
                    Log.e("&&&&&&&&","Exception " + e + " while parsing double" );
                    e.printStackTrace();
                }
            }
        };

        holder.productQuantity.addTextChangedListener(quantityTextChangeWatcher);
        holder.productQuantity.setTag(quantityTextChangeWatcher);

        holder.PurchasePrice.addTextChangedListener(quantityTextChangeWatcher);
        holder.PurchasePrice.setTag(quantityTextChangeWatcher);

        holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                activity.setSummaryRow();
                notifyDataSetChanged();
            }
        });

        return convertView;
    }



    public void addProductToList( VendorReturnModel product ) {
        Log.e("&&&&&&&&", "Adding product " + product.toString() + " to product list");

        VendorReturnModel productAlreadyInList = findProductInList(product);
        if(productAlreadyInList == null) {
            list.add(product);
        } else {
            productAlreadyInList.setProductQuantity( productAlreadyInList.getProductQuantity() + product.getProductQuantity());
        }

    }

    public ArrayList<VendorReturnModel> getList() {
        return list;
    }

    private VendorReturnModel findProductInList(VendorReturnModel product) {
        VendorReturnModel returnProductVal = null;

        for( VendorReturnModel productInList : list) {
            if( productInList.getProductId().trim().equals(product.getProductId().trim()) && productInList.getBatchno().trim().equals(product.getBatchno().trim())) {
                returnProductVal = productInList;
            }
        }
        return returnProductVal;
    }

    public void clearAllRows() {
        list.clear();
        notifyDataSetChanged();
    }

    public float getGrandTotal()
    {
        //  ViewHolder holder=new ViewHolder();
        float finalamount=0.0f;
        for (int listIndex = 0; listIndex < list.size(); listIndex++) {
            try {
                finalamount += (list.get(listIndex).getTotal());
            } catch (Exception e) {
                //ignore exeption for parse
            }
            Log.e("&&&&&^^^^^^^^", "$$$$$$$$" + finalamount);

        }
        Log.e("&&&&55555555&&&", "Total Price is:" + finalamount);
        return finalamount;
    }
}
