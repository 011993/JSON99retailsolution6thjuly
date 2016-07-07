package ReportTabFragments;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Toast;

import com.mycompany.apps.CustomAuto;
import com.mycompany.apps.DBhelper;
import com.mycompany.apps.R;

import java.util.ArrayList;

import Adapter.DistributorListAdapter;
import Adapter.DistributorSearchAdapter;
import Pojo.InventoryReportModel;
import Pojo.ReportDistributorModel;
import Pojo.ReportVendorModel;


public class FragmentDistributorReport extends android.support.v4.app.Fragment{

    ListView listview;
    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    FragmentListDistributorAdapter distributorListAdapter;
    ArrayList<ReportDistributorModel> GetAllDistibutor;
    ArrayList<ReportDistributorModel> searchDistributorList;
    ArrayList<ReportDistributorModel> arrayDistributorList;
    FragmentSearchDistributorAdapter searchDistributorAdapter;
    String ActiveType[];
    Spinner Active;
    String SpinValue;
    ArrayAdapter<String> adapterActiveType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_distributor_report, container, false);

        final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        final DBhelper helper = new DBhelper(getContext());
        helper.getReadableDatabase();

        listview = (ListView)view.findViewById(R.id.lv_DistributorReport);
        Log.e("***********Lt1*******", listview.toString());
        autoCompleteTextView = (CustomAuto)view.findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(1);
        Active = (Spinner)view.findViewById(R.id.activeDistributor);

        GetAllDistibutor = helper.getDistributorForReport();
        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
        distributorListAdapter = new FragmentListDistributorAdapter(GetAllDistibutor, getContext());
        listview.setAdapter(distributorListAdapter);

        ActiveType = getResources().getStringArray(R.array.active_Status_for_Report);
        adapterActiveType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,ActiveType);
        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Active.setAdapter(adapterActiveType);
        Active.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = Active.getSelectedItem().toString();
                if (SpinValue.equals("Y")) {//rahul
                    GetAllDistibutor = new ArrayList<ReportDistributorModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    distributorListAdapter = new FragmentListDistributorAdapter(GetAllDistibutor, getContext());
                    listview.setAdapter(distributorListAdapter);
                    arrayDistributorList = helper.getDistributorReportforActive(SpinValue);
                    if (distributorListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        distributorListAdapter = new FragmentListDistributorAdapter(arrayDistributorList, getContext());
                        listview.setAdapter(distributorListAdapter);
                    }
                    for(ReportDistributorModel distributor:arrayDistributorList)
                    {
                        distributorListAdapter.addDistributorList(distributor);}
                    distributorListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                } else if (SpinValue.equals("N")) {
                    GetAllDistibutor = new ArrayList<ReportDistributorModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    distributorListAdapter = new FragmentListDistributorAdapter(GetAllDistibutor, getContext());
                    listview.setAdapter(distributorListAdapter);
                    arrayDistributorList = helper.getDistributorReportforActive(SpinValue);
                    if (distributorListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        distributorListAdapter = new FragmentListDistributorAdapter(arrayDistributorList,getContext());
                        listview.setAdapter(distributorListAdapter);
                    }
                    for (ReportDistributorModel prod : arrayDistributorList) {
                        distributorListAdapter.addDistributorList(prod);
                    }
                    distributorListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }
                else if (SpinValue.equals("All")) {
                    GetAllDistibutor = new ArrayList<ReportDistributorModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    distributorListAdapter = new FragmentListDistributorAdapter(GetAllDistibutor, getContext());
                    listview.setAdapter(distributorListAdapter);
                    arrayDistributorList = helper.getDistributorReportforAllActive(SpinValue);
                    if (distributorListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        distributorListAdapter = new FragmentListDistributorAdapter(arrayDistributorList,getContext());
                        listview.setAdapter(distributorListAdapter);
                    }
                    for (ReportDistributorModel prod : arrayDistributorList) {
                        distributorListAdapter.addDistributorList(prod);
                    }
                    distributorListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }
                else {
                    arrayDistributorList = new ArrayList<ReportDistributorModel>();
                    if (distributorListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        distributorListAdapter = new FragmentListDistributorAdapter(arrayDistributorList,getContext());
                        listview.setAdapter(distributorListAdapter);
                    }
                    distributorListAdapter.notifyDataSetChanged();
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
                searchDistributorList = helper.getDistributorName(userTypedString);
                if (searchDistributorList != null) {
                    if (searchDistributorAdapter == null) {
                        searchDistributorAdapter = new FragmentSearchDistributorAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchDistributorList);
                        searchDistributorAdapter.setList(searchDistributorList);

                        autoCompleteTextView.setAdapter(searchDistributorAdapter);
                        autoCompleteTextView.setThreshold(1);
                    } else {
                        searchDistributorAdapter.setList(searchDistributorList);
                        searchDistributorAdapter.notifyDataSetChanged();
                    }

                }
            }
        };

        autoCompleteTextView.addTextChangedListener(mTextWatcher);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Value = parent.getItemAtPosition(position).toString();
                GetAllDistibutor = new ArrayList<ReportDistributorModel>();
                Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                distributorListAdapter = new FragmentListDistributorAdapter(GetAllDistibutor, getContext());
                listview.setAdapter(distributorListAdapter);

                arrayDistributorList = helper.getDistributorReport(Value);
                if (distributorListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    distributorListAdapter = new FragmentListDistributorAdapter(new ArrayList<ReportDistributorModel>(),getContext());
                    listview.setAdapter(distributorListAdapter);
                }
                for(ReportDistributorModel distributor:arrayDistributorList)
                {
                    distributorListAdapter.addDistributorList(distributor);}
                distributorListAdapter.notifyDataSetChanged();
                autoCompleteTextView.setText("");
            }
        });

        return  view;

    }

}
