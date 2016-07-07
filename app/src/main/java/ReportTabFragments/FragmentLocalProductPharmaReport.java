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

import Adapter.ReportLocalProductPharmaListAdapter;
import Adapter.ReportLocalProductPharmaSearchAdapter;
import Pojo.ReportLocalProductPharmaModel;
import Pojo.ReportProductPharmaModel;


public class FragmentLocalProductPharmaReport extends android.support.v4.app.Fragment{

    ListView listview;
    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    FragmentListLocalProductPharmaAdapter localproductListAdapter;
    ArrayList<ReportLocalProductPharmaModel> searchlocalProductList;
    ArrayList<ReportLocalProductPharmaModel> arraylocalProductList;
    FragmentSearchLocalProductPharmaAdapter searchlocalProductAdapter;
    ArrayList<ReportLocalProductPharmaModel>GetAllPharmaLocalProduct;
    ActionBar actionBar;
    String ActiveType[];
    Spinner Active;
    String SpinValue;
    ArrayAdapter<String> adapterActiveType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_localprod_pharma_report, container, false);

        final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        final DBhelper helper = new DBhelper(getContext());
        helper.getReadableDatabase();

        listview = (ListView)view.findViewById(R.id.lv_LocalProdReport);
        Log.e("***********Lt1*******", listview.toString());
        autoCompleteTextView = (CustomAuto)view.findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(1);
        Active=(Spinner)view.findViewById(R.id.activeLocalProdPharma);
        GetAllPharmaLocalProduct=helper.getLocalProductPharmaForReport();
        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
        localproductListAdapter = new FragmentListLocalProductPharmaAdapter( GetAllPharmaLocalProduct ,getContext());
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
                    GetAllPharmaLocalProduct=new ArrayList<ReportLocalProductPharmaModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    localproductListAdapter = new FragmentListLocalProductPharmaAdapter( GetAllPharmaLocalProduct ,getContext());
                    listview.setAdapter(localproductListAdapter);


                    arraylocalProductList = helper.getLocalProductPharmaReportforActive(SpinValue);
                    if (localproductListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        localproductListAdapter = new FragmentListLocalProductPharmaAdapter( arraylocalProductList ,getContext());
                        listview.setAdapter(localproductListAdapter);
                    }
                    for(ReportLocalProductPharmaModel product:arraylocalProductList)
                    {
                        localproductListAdapter.addProductToList(product);}
                    localproductListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }else if(SpinValue.equals("N"))
                {
                    GetAllPharmaLocalProduct=new ArrayList<ReportLocalProductPharmaModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    localproductListAdapter = new FragmentListLocalProductPharmaAdapter( GetAllPharmaLocalProduct ,getContext());
                    listview.setAdapter(localproductListAdapter);


                    arraylocalProductList = helper.getLocalProductPharmaReportforActive(SpinValue);
                    if (localproductListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        localproductListAdapter = new FragmentListLocalProductPharmaAdapter( arraylocalProductList ,getContext());
                        listview.setAdapter(localproductListAdapter);
                    }
                    for (ReportLocalProductPharmaModel prod:arraylocalProductList){
                        localproductListAdapter.addProductToList(prod);
                    }
                    localproductListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }
                else if(SpinValue.equals("All"))
                {
                    GetAllPharmaLocalProduct=new ArrayList<ReportLocalProductPharmaModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    localproductListAdapter = new FragmentListLocalProductPharmaAdapter( GetAllPharmaLocalProduct ,getContext());
                    listview.setAdapter(localproductListAdapter);

                    arraylocalProductList = helper.getLocalProductPharmaReportforAllActive(SpinValue);
                    if (localproductListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        localproductListAdapter = new FragmentListLocalProductPharmaAdapter( arraylocalProductList ,getContext());
                        listview.setAdapter(localproductListAdapter);
                    }
                    for (ReportLocalProductPharmaModel prod:arraylocalProductList){
                        localproductListAdapter.addProductToList(prod);
                    }
                    localproductListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }
                else
                {   arraylocalProductList = new ArrayList<ReportLocalProductPharmaModel>();
                    if (localproductListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        localproductListAdapter = new FragmentListLocalProductPharmaAdapter( arraylocalProductList ,getContext());
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
                searchlocalProductList = helper.getlocalProductPharmaName(userTypedString);
                if (searchlocalProductList != null) {
                    if (searchlocalProductAdapter == null) {
                        searchlocalProductAdapter = new FragmentSearchLocalProductPharmaAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchlocalProductList);
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
                GetAllPharmaLocalProduct=new ArrayList<ReportLocalProductPharmaModel>();
                Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                localproductListAdapter = new FragmentListLocalProductPharmaAdapter( GetAllPharmaLocalProduct ,getContext());
                listview.setAdapter(localproductListAdapter);
                arraylocalProductList = helper.getLocalProdPharmaReport(Value);
                if (localproductListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    localproductListAdapter = new FragmentListLocalProductPharmaAdapter(new ArrayList<ReportLocalProductPharmaModel>(),getContext());
                    localproductListAdapter.setList(arraylocalProductList);
                    listview.setAdapter(localproductListAdapter);
                }
                for(ReportLocalProductPharmaModel product:arraylocalProductList)
                {
                    localproductListAdapter.addProductToList(product);
                }
                //Log.i("&&&&&&&&", "Adding " + arrayProductList.get(0) + " to Product List..");
                localproductListAdapter.setList(arraylocalProductList);
                localproductListAdapter.notifyDataSetChanged();
                autoCompleteTextView.setText("");

            }
        });

        return  view;

    }

}
