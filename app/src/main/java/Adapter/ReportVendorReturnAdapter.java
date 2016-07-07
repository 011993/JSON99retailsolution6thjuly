package Adapter;

import android.content.Context;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mycompany.apps.DecimalDigitsInputFilter;
import com.mycompany.apps.R;
import com.mycompany.apps.ReportVendorReturnActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import Pojo.ReportVendorReturnModel;

public class ReportVendorReturnAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<ReportVendorReturnModel> list=new ArrayList<ReportVendorReturnModel>();
    ReportVendorReturnActivity activity;

    public ReportVendorReturnAdapter(ArrayList<ReportVendorReturnModel> list, ReportVendorReturnActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    public long getItemId(int position)
    {
        Log.e("**********", "" + position);
        return position;
    }

    public int getCount() {
        if (list.size()<0)
            return 1;
        Log.e("**get Count***",list.toString());
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    public void addIdToList( ReportVendorReturnModel id ) {
        Log.e("&&&&&&&&", "Adding Id " + id.toString() + " to list");
        list.add(id);
    }

    public void setList(ArrayList<ReportVendorReturnModel> list) {
        this.list = list;
    }

    public static class ViewHolder{

        TextView TVVEND_NM;
        TextView TVREASON_RETRN;
        TextView TVPROD_NM;
        TextView TVBATCH_NO;
        TextView TVEXP_DATE;
        TextView TVP_PRICE;
        TextView TVQTY;
        TextView TVUNIT;
        TextView TVTOTAL;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView==null)
        {
            layoutInflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            /***** Inflate tabitem.xml file for each row ( Defined below ) *******/
            convertView =layoutInflater.inflate(R.layout.display_report_vendor_return,null);
            holder = new ViewHolder();
            holder.TVVEND_NM=(TextView)convertView.findViewById(R.id.reportvend_name);
            holder.TVREASON_RETRN=(TextView)convertView.findViewById(R.id.report_reason);
            holder.TVPROD_NM=(TextView)convertView.findViewById(R.id.reportprod_name);
            holder.TVBATCH_NO=(TextView)convertView.findViewById(R.id.report_No);
            holder.TVEXP_DATE=(TextView)convertView.findViewById(R.id.report_date);
            holder.TVP_PRICE=(TextView)convertView.findViewById(R.id.report_price);
            holder.TVQTY=(TextView)convertView.findViewById(R.id.report_qty);
            holder.TVUNIT=(TextView)convertView.findViewById(R.id.report_unit);
            holder.TVTOTAL=(TextView)convertView.findViewById(R.id.report_total);
          //  holder.TVTOTAL.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.TVVEND_NM.setText(list.get(position).getVendrNm());
        holder.TVREASON_RETRN.setText(list.get(position).getReason());
        holder.TVPROD_NM.setText(list.get(position).getProdctNm());
        holder.TVBATCH_NO.setText(list.get(position).getBatchNo());
        holder.TVEXP_DATE.setText(list.get(position).getExpDate());
        holder.TVP_PRICE.setText(list.get(position).getPPrice());
        holder.TVQTY.setText(list.get(position).getQty());
        holder.TVUNIT.setText(list.get(position).getUom());
        holder.TVTOTAL.setText(list.get(position).getTotal());
        return convertView;
    }

    public ArrayList<ReportVendorReturnModel> getList() {
        return list;
    }
}
