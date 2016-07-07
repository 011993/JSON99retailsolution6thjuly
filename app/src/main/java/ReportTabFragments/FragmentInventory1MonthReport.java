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
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.mycompany.apps.CustomAuto;
import com.mycompany.apps.DBhelper;
import com.mycompany.apps.R;

import java.util.ArrayList;

import Pojo.InventoryReportModel;
import Pojo.ReportDistributorModel;


public class FragmentInventory1MonthReport extends android.support.v4.app.Fragment {

    ListView listview;
   /* AutoCompleteTextView idTextView;
    private TextWatcher idTextWatcher;*/
    FragmentList1MonthInventoryAdapter inventoryListAdapter;
    ArrayList<InventoryReportModel> searchIdList;
    ArrayList<InventoryReportModel> Get1MonthData;
    //ArrayList<InventoryReportModel> arrayInventoryList;
   // FragmentSearch1MonthInventoryAdapter searchIdAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_activity_inventory_month1_data, container, false);

      final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
       final DBhelper helper = new DBhelper(getContext());
        helper.getReadableDatabase();

        listview = (ListView)view. findViewById(R.id.lv_InventoryReport);
        Log.e("***********Lt1*******", listview.toString());
       /* idTextView = (CustomAuto)view. findViewById(R.id.InventryReportTextView);
        idTextView.setThreshold(1);*/

        Get1MonthData = helper.getInventory1MonthDataForReport();
        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
        inventoryListAdapter = new FragmentList1MonthInventoryAdapter(Get1MonthData, getContext());
        listview.setAdapter(inventoryListAdapter);

        //******************************** vendor name selected from here********************************************************************************************/
       /* idTextView.setThreshold(1);
        idTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (idTextView.getText().toString().matches("")) {
                    Toast.makeText(getContext(), "Please select the Product Id", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("Debuging", "After text changed called ");
                if (idTextView.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = idTextView.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                searchIdList = helper.getProductId(userTypedString);
                if (searchIdList != null) {
                    if (searchIdAdapter == null) {
                        searchIdAdapter = new FragmentSearch1MonthInventoryAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchIdList);
                        searchIdAdapter.setList(searchIdList);
                        idTextView.setAdapter(searchIdAdapter);
                        idTextView.setThreshold(1);
                    } else {
                        searchIdAdapter.setList(searchIdList);
                        searchIdAdapter.notifyDataSetChanged();
                    }
                }
            }
        };
        idTextView.addTextChangedListener(idTextWatcher);
        idTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Value = parent.getItemAtPosition(position).toString();
                Get1MonthData = new ArrayList<InventoryReportModel>();
                Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                inventoryListAdapter = new FragmentList1MonthInventoryAdapter(Get1MonthData, getContext());
                listview.setAdapter(inventoryListAdapter);

                arrayInventoryList = helper.getInventoryReport(Value);
                if (inventoryListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    inventoryListAdapter = new FragmentList1MonthInventoryAdapter(new ArrayList<InventoryReportModel>(), getContext());
                    inventoryListAdapter.setList(arrayInventoryList);
                    listview.setAdapter(inventoryListAdapter);
                    idTextView.setText("");
                } else {
                    inventoryListAdapter.setList(arrayInventoryList);
                    inventoryListAdapter.notifyDataSetChanged();
                    idTextView.setText("");
                }
            }
        });
*/        return view;
    }


}
