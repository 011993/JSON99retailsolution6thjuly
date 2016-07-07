package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mycompany.apps.AdTickerReportActivity;
import com.mycompany.apps.R;

import java.util.ArrayList;

import Pojo.AdTickerReportModel;


public class AdTickerReportAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<AdTickerReportModel> list = new ArrayList<AdTickerReportModel>();
    AdTickerReportActivity activity;

    public AdTickerReportAdapter(ArrayList<AdTickerReportModel> list, AdTickerReportActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    public long getItemId(int position) {
        Log.e("**********", "" + position);
        return position;
    }

    public int getCount() {
        if (list.size() < 0)
            return 1;
        Log.e("**get Count***", list.toString());
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    public void addVendorToList(AdTickerReportModel vendor) {
        Log.e("&&&&&&&&", "Adding product " + vendor.toString() + " to product list");
        list.add(vendor);
    }

    public void setList(ArrayList<AdTickerReportModel> list) {
        this.list = list;
    }

    public static class ViewHolder {
        /*2
        layout created display _report _row which text view are used
        */
        TextView TVAD_ID;
        TextView TVAD_TEXT;
        TextView TVAD_COST;

    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //**** Inflate tabitem.xml file for each row ( Defined below ) ******
            convertView = layoutInflater.inflate(R.layout.display_adticker_report_row, null);
            holder = new ViewHolder();
            holder.TVAD_ID = (TextView) convertView.findViewById(R.id.rowtvAd_Id);
            holder.TVAD_TEXT = (TextView) convertView.findViewById(R.id.rowtvAd_Text);
            holder.TVAD_COST = (TextView) convertView.findViewById(R.id.rowtv_AdCost);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.TVAD_ID.setText(list.get(position).getAD_MAIN_ID());
        holder.TVAD_TEXT.setText(list.get(position).getAD_TEXT());
        holder.TVAD_COST.setText(list.get(position).getAD_CST_SLB1());
        return convertView;
    }

    public ArrayList<AdTickerReportModel> getList() {
        return list;
    }
}
