package ReportTabFragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;

import com.mycompany.apps.CustomAuto;
import com.mycompany.apps.DBhelper;
import com.mycompany.apps.R;

import java.util.ArrayList;

import Adapter.VendorListAdapter;
import Adapter.VendorSearchAdapter;
import Pojo.ReportDistributorModel;
import Pojo.ReportVendorModel;
import Pojo.VendorModel;


public class FragmentVendorReport extends android.support.v4.app.Fragment{

    ListView listview;
    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    FragmentListVendorAdapter vendorListAdapter;
    ArrayList<ReportVendorModel> searchVendorList;
    ArrayList<ReportVendorModel> arrayVendorList;
    FragmentSearchVendorAdapter searchVendorAdapter;
    ArrayList<ReportVendorModel>GetAllVendor;
    String ActiveType[];
    Spinner Active;
    String SpinValue;
    ArrayAdapter<String> adapterActiveType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_vendor_report, container, false);

        final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        final DBhelper helper = new DBhelper(getContext());
        helper.getReadableDatabase();

        listview = (ListView)view.findViewById(R.id.lv_VendorReport);
        Log.e("***********Lt1*******", listview.toString());
        autoCompleteTextView = (CustomAuto)view.findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(1);
        Active = (Spinner)view.findViewById(R.id.activeVendor);

        GetAllVendor=helper.getVendorForReport();
        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
        vendorListAdapter = new FragmentListVendorAdapter( GetAllVendor ,getContext());
        listview.setAdapter(vendorListAdapter);

        ActiveType = getResources().getStringArray(R.array.active_Status_for_Report);
        adapterActiveType = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,ActiveType);
        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Active.setAdapter(adapterActiveType);
        Active.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = Active.getSelectedItem().toString();
                if (SpinValue.equals("Y"))
                {
                    GetAllVendor=new ArrayList<ReportVendorModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    vendorListAdapter = new FragmentListVendorAdapter( GetAllVendor ,getContext());
                    listview.setAdapter(vendorListAdapter);


                    arrayVendorList = helper.getVendorReportforActive(SpinValue);
                    if (vendorListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        vendorListAdapter = new FragmentListVendorAdapter( arrayVendorList ,getContext());
                        listview.setAdapter(vendorListAdapter);
                    }
                    for(ReportVendorModel vendor:arrayVendorList)
                    {
                        vendorListAdapter.addVendorToList(vendor);}
                    vendorListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }else if(SpinValue.equals("N"))
                {
                    GetAllVendor=new ArrayList<ReportVendorModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    vendorListAdapter = new FragmentListVendorAdapter( GetAllVendor ,getContext());
                    listview.setAdapter(vendorListAdapter);


                    arrayVendorList = helper.getVendorReportforActive(SpinValue);
                    if (vendorListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        vendorListAdapter = new FragmentListVendorAdapter( arrayVendorList ,getContext());
                        listview.setAdapter(vendorListAdapter);
                    }
                    for (ReportVendorModel prod:arrayVendorList){
                        vendorListAdapter.addVendorToList(prod);
                    }
                    vendorListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }
                else if (SpinValue.equals("All")) {
                    GetAllVendor = new ArrayList<ReportVendorModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    vendorListAdapter = new FragmentListVendorAdapter(GetAllVendor, getContext());
                    listview.setAdapter(vendorListAdapter);
                    arrayVendorList = helper.getVendorReportforAllActive(SpinValue);
                    if (vendorListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        vendorListAdapter = new FragmentListVendorAdapter(arrayVendorList,getContext());
                        listview.setAdapter(vendorListAdapter);
                    }
                    for (ReportVendorModel prod : arrayVendorList) {
                        vendorListAdapter.addVendorToList(prod);
                    }
                    vendorListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }
                else
                {   arrayVendorList = new ArrayList<ReportVendorModel>();
                    if (vendorListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        vendorListAdapter = new FragmentListVendorAdapter( arrayVendorList ,getContext());
                        listview.setAdapter(vendorListAdapter);
                    }
                    vendorListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /******************************** distributor name selected from here********************************************************************************************/
        autoCompleteTextView.setThreshold(1);
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("Debuging", "After text changed called ");
                if (autoCompleteTextView.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = autoCompleteTextView.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                searchVendorList = helper.getVendorReportName(userTypedString);
                if (searchVendorList != null) {
                    if (searchVendorAdapter == null) {
                        searchVendorAdapter = new FragmentSearchVendorAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchVendorList);
                        searchVendorAdapter.setList(searchVendorList);

                        autoCompleteTextView.setAdapter(searchVendorAdapter);
                        autoCompleteTextView.setThreshold(1);
                    } else {
                        searchVendorAdapter.setList(searchVendorList);
                        searchVendorAdapter.notifyDataSetChanged();
                    }

                }
            }
        };

        autoCompleteTextView.addTextChangedListener(mTextWatcher);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Value = parent.getItemAtPosition(position).toString();
                GetAllVendor = new ArrayList<ReportVendorModel>();
                Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                vendorListAdapter = new FragmentListVendorAdapter(GetAllVendor, getContext());
                listview.setAdapter(vendorListAdapter);

                arrayVendorList = helper.getVendorReport(Value);
                if (vendorListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    vendorListAdapter = new FragmentListVendorAdapter(new ArrayList<ReportVendorModel>(),getContext());
                    listview.setAdapter(vendorListAdapter);
                }
                for(ReportVendorModel vendor:arrayVendorList)
                {
                    vendorListAdapter.addVendorToList(vendor);}
                //Log.i("&&&&&&&&", "Adding " + arrayVendorList.get(0) + " to Product List..");
                vendorListAdapter.notifyDataSetChanged();
                autoCompleteTextView.setText("");
            }
        });

        return  view;

    }

}
