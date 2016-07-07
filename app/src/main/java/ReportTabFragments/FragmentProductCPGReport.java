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

import Adapter.ReportProductCpgListAdapter;
import Adapter.ReportProductCpgSearchAdapter;
import Pojo.ReportProductCpgModel;
import Pojo.ReportProductPharmaModel;


public class FragmentProductCPGReport extends android.support.v4.app.Fragment{

    ListView listview;
    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    FragmentListProductCPGAdapter productListAdapter;
    ArrayList<ReportProductCpgModel> searchProductList;
    ArrayList<ReportProductCpgModel> arrayProductList;
    FragmentSearchProductCPGAdapter searchProductAdapter;
    ActionBar actionBar;
    ArrayList<ReportProductCpgModel>GetAllCpgProduct;
    String ActiveType[];
    Spinner Active;
    String SpinValue;
    ArrayAdapter<String> adapterActiveType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_product_cpg_report, container, false);

        final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        final DBhelper helper = new DBhelper(getContext());
        helper.getReadableDatabase();

        listview = (ListView)view.findViewById(R.id.lv_ProductCpgReport);
        Log.e("***********Lt1*******", listview.toString());
        autoCompleteTextView = (CustomAuto)view.findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(1);
        Active = (Spinner)view.findViewById(R.id.activeProdCpg);
        GetAllCpgProduct=helper.getProductCpgForReport();
        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
        productListAdapter = new FragmentListProductCPGAdapter( GetAllCpgProduct ,getContext());
        listview.setAdapter(productListAdapter);

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
                    GetAllCpgProduct=new ArrayList<ReportProductCpgModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new FragmentListProductCPGAdapter( GetAllCpgProduct ,getContext());
                    listview.setAdapter(productListAdapter);


                    arrayProductList = helper.getCpgReportforActive(SpinValue);
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new FragmentListProductCPGAdapter( arrayProductList ,getContext());
                        listview.setAdapter(productListAdapter);
                    }
                    for(ReportProductCpgModel product:arrayProductList)
                    {
                        productListAdapter.addProductToList(product);}
                    productListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }else if(SpinValue.equals("N"))
                {
                    GetAllCpgProduct=new ArrayList<ReportProductCpgModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new FragmentListProductCPGAdapter( GetAllCpgProduct ,getContext());
                    listview.setAdapter(productListAdapter);


                    arrayProductList = helper.getCpgReportforActive(SpinValue);
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new FragmentListProductCPGAdapter( arrayProductList ,getContext());
                        listview.setAdapter(productListAdapter);
                    }
                    for (ReportProductCpgModel prod:arrayProductList){
                        productListAdapter.addProductToList(prod);
                    }
                    productListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }
                else if(SpinValue.equals("All"))
                {
                    GetAllCpgProduct=new ArrayList<ReportProductCpgModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new FragmentListProductCPGAdapter( GetAllCpgProduct ,getContext());
                    listview.setAdapter(productListAdapter);


                    arrayProductList = helper.getCpgReportforAllActive(SpinValue);
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new FragmentListProductCPGAdapter( arrayProductList ,getContext());
                        listview.setAdapter(productListAdapter);
                    }
                    for (ReportProductCpgModel prod:arrayProductList){
                        productListAdapter.addProductToList(prod);
                    }
                    productListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }
                else
                {   arrayProductList = new ArrayList<ReportProductCpgModel>();
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new FragmentListProductCPGAdapter( arrayProductList ,getContext());
                        listview.setAdapter(productListAdapter);
                    }
                    productListAdapter.notifyDataSetChanged();
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
                searchProductList = helper.getProductCpgName(userTypedString);
                if (searchProductList != null) {
                    if (searchProductAdapter == null) {
                        searchProductAdapter = new FragmentSearchProductCPGAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchProductList);
                        searchProductAdapter.setList(searchProductList);

                        autoCompleteTextView.setAdapter(searchProductAdapter);
                        autoCompleteTextView.setThreshold(1);
                    } else {
                        searchProductAdapter.setList(searchProductList);
                        searchProductAdapter.notifyDataSetChanged();
                    }

                }
            }
        };

        autoCompleteTextView.addTextChangedListener(mTextWatcher);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Value = parent.getItemAtPosition(position).toString();
                GetAllCpgProduct=new ArrayList<ReportProductCpgModel>();
                Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                productListAdapter = new FragmentListProductCPGAdapter( GetAllCpgProduct ,getContext());
                listview.setAdapter(productListAdapter);
                arrayProductList = helper.getProductCpgReport(Value);
                if (productListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new FragmentListProductCPGAdapter(new ArrayList<ReportProductCpgModel>(),getContext());
                    productListAdapter.setList(arrayProductList);
                    listview.setAdapter(productListAdapter);
                }
                for(ReportProductCpgModel product:arrayProductList)
                {
                    productListAdapter.addProductToList(product);}
                //Log.i("&&&&&&&&", "Adding " + arrayProductList.get(0) + " to Product List..");
                productListAdapter.setList(arrayProductList);
                productListAdapter.notifyDataSetChanged();
                autoCompleteTextView.setText("");

            }
        });

        return  view;

    }

}
