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

import Adapter.ReportLocalProductCpgListAdapter;
import Adapter.ReportLocalProductCpgSearchAdapter;
import Pojo.ReportLocalProductCpgModel;
import Pojo.ReportProductCpgModel;


public class FragmentLocalProductCPGReport extends android.support.v4.app.Fragment{

    ListView listview;
    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    FragmentListLocalProductCPGAdapter localproductListAdapter;;
    ArrayList<ReportLocalProductCpgModel> searchlocalProductList;
    ArrayList<ReportLocalProductCpgModel> arraylocalProductList;
    FragmentSearchLocalProductCPGAdapter searchlocalProductAdapter;
    ActionBar actionBar;
    ArrayList<ReportLocalProductCpgModel>GetAllCpgLocalProduct;
    String ActiveType[];
    Spinner Active;
    String SpinValue;
    ArrayAdapter<String> adapterActiveType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_localprod_cpg_report, container, false);

        final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        final DBhelper helper = new DBhelper(getContext());
        helper.getReadableDatabase();

        listview = (ListView)view.findViewById(R.id.lv_LocalProdCpgReport);
        Log.e("***********Lt1*******", listview.toString());
        autoCompleteTextView = (CustomAuto)view.findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(1);
        Active=(Spinner)view.findViewById(R.id.activeLocalProdCpg);
        GetAllCpgLocalProduct=helper.getLocalProductCpgForReport();
        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
        localproductListAdapter = new FragmentListLocalProductCPGAdapter( GetAllCpgLocalProduct ,getContext());
        listview.setAdapter(localproductListAdapter);

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
                    GetAllCpgLocalProduct=new ArrayList<ReportLocalProductCpgModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    localproductListAdapter = new FragmentListLocalProductCPGAdapter( GetAllCpgLocalProduct ,getContext());
                    listview.setAdapter(localproductListAdapter);


                    arraylocalProductList = helper.getLocalCpgReportforActive(SpinValue);
                    if (localproductListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        localproductListAdapter = new FragmentListLocalProductCPGAdapter( arraylocalProductList ,getContext());
                        listview.setAdapter(localproductListAdapter);
                    }
                    for(ReportLocalProductCpgModel product:arraylocalProductList)
                    {
                        localproductListAdapter.addProductToList(product);}
                    localproductListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }else if(SpinValue.equals("N"))
                {
                    GetAllCpgLocalProduct=new ArrayList<ReportLocalProductCpgModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    localproductListAdapter = new FragmentListLocalProductCPGAdapter( GetAllCpgLocalProduct ,getContext());
                    listview.setAdapter(localproductListAdapter);


                    arraylocalProductList = helper.getLocalCpgReportforActive(SpinValue);
                    if (localproductListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        localproductListAdapter = new FragmentListLocalProductCPGAdapter( arraylocalProductList ,getContext());
                        listview.setAdapter(localproductListAdapter);
                    }
                    for (ReportLocalProductCpgModel prod:arraylocalProductList){
                        localproductListAdapter.addProductToList(prod);
                    }
                    localproductListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }
                else if(SpinValue.equals("All"))
                {
                    GetAllCpgLocalProduct=new ArrayList<ReportLocalProductCpgModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    localproductListAdapter = new FragmentListLocalProductCPGAdapter( GetAllCpgLocalProduct ,getContext());
                    listview.setAdapter(localproductListAdapter);


                    arraylocalProductList = helper.getLocalCpgReportforAllActive(SpinValue);
                    if (localproductListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        localproductListAdapter = new FragmentListLocalProductCPGAdapter( arraylocalProductList ,getContext());
                        listview.setAdapter(localproductListAdapter);
                    }
                    for (ReportLocalProductCpgModel prod:arraylocalProductList){
                        localproductListAdapter.addProductToList(prod);
                    }
                    localproductListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }
                else
                {   arraylocalProductList = new ArrayList<ReportLocalProductCpgModel>();
                    if (localproductListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        localproductListAdapter = new FragmentListLocalProductCPGAdapter( arraylocalProductList ,getContext());
                        listview.setAdapter(localproductListAdapter);
                    }
                    localproductListAdapter.notifyDataSetChanged();
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
                searchlocalProductList = helper.getlocalProductCpgName(userTypedString);
                if (searchlocalProductList != null) {
                    if (searchlocalProductAdapter == null) {
                        searchlocalProductAdapter = new FragmentSearchLocalProductCPGAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchlocalProductList);
                        searchlocalProductAdapter.setList(searchlocalProductList);

                        autoCompleteTextView.setAdapter(searchlocalProductAdapter);
                        autoCompleteTextView.setThreshold(1);
                    } else {
                        searchlocalProductAdapter.setList(searchlocalProductList);
                        searchlocalProductAdapter.notifyDataSetChanged();
                    }

                }
            }
        };

        autoCompleteTextView.addTextChangedListener(mTextWatcher);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Value = parent.getItemAtPosition(position).toString();
                GetAllCpgLocalProduct=new ArrayList<ReportLocalProductCpgModel>();
                Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                localproductListAdapter = new FragmentListLocalProductCPGAdapter( GetAllCpgLocalProduct ,getContext());
                listview.setAdapter(localproductListAdapter);
                arraylocalProductList = helper.getLocalProdCpgReport(Value);
                if (localproductListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    localproductListAdapter = new FragmentListLocalProductCPGAdapter(new ArrayList<ReportLocalProductCpgModel>(),getContext());
                    localproductListAdapter.setList(arraylocalProductList);
                    listview.setAdapter(localproductListAdapter);
                }
                for(ReportLocalProductCpgModel product:arraylocalProductList)
                {
                    localproductListAdapter.addProductToList(product);}
                //Log.i("&&&&&&&&", "Adding " + arrayProductList.get(0) + " to Product List..");
                localproductListAdapter.setList(arraylocalProductList);
                localproductListAdapter.notifyDataSetChanged();
                autoCompleteTextView.setText("");

            }
        });

        return  view;

    }

}
