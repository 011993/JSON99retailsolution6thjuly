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

import Adapter.ReportProductPharmaListAdapter;
import Adapter.ReportProductPharmaSearchAdapter;
import Pojo.ReportDistributorModel;
import Pojo.ReportProductPharmaModel;
import Pojo.ReportVendorModel;


public class FragmentProductPharmaReport extends android.support.v4.app.Fragment{

    ListView listview;
    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    FragmentListProductPharmaAdapter productListAdapter;
    ArrayList<ReportProductPharmaModel> searchProductList;
    ArrayList<ReportProductPharmaModel> arrayProductList;
    FragmentSearchProductPharmaAdapter searchProductAdapter;
    ArrayList<ReportProductPharmaModel>GetAllPharmaProduct;
    ActionBar actionBar;
    String ActiveType[];
    Spinner Active;
    String SpinValue;
    ArrayAdapter<String> adapterActiveType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_product_pharma_report, container, false);

        final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        final DBhelper helper = new DBhelper(getContext());
        helper.getReadableDatabase();

        listview = (ListView)view.findViewById(R.id.lv_ProductReport);
        Log.e("***********Lt1*******", listview.toString());
        autoCompleteTextView = (CustomAuto)view.findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(1);
        Active = (Spinner)view.findViewById(R.id.activeProdPharma);

        GetAllPharmaProduct=helper.getProductPharmaForReport();
        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
        productListAdapter = new FragmentListProductPharmaAdapter( GetAllPharmaProduct ,getContext());
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
                {//rahul
                    GetAllPharmaProduct=new ArrayList<ReportProductPharmaModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new FragmentListProductPharmaAdapter( GetAllPharmaProduct ,getContext());
                    listview.setAdapter(productListAdapter);


                    arrayProductList = helper.getProductPharmaReportforActive(SpinValue);
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new FragmentListProductPharmaAdapter( arrayProductList ,getContext());
                        listview.setAdapter(productListAdapter);
                    }
                    for(ReportProductPharmaModel product:arrayProductList)
                    {
                        productListAdapter.addProductToList(product);}
                    productListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }else if(SpinValue.equals("N"))
                {
                    GetAllPharmaProduct=new ArrayList<ReportProductPharmaModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new FragmentListProductPharmaAdapter( GetAllPharmaProduct ,getContext());
                    listview.setAdapter(productListAdapter);


                    arrayProductList = helper.getProductPharmaReportforActive(SpinValue);
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new FragmentListProductPharmaAdapter( arrayProductList ,getContext());
                        listview.setAdapter(productListAdapter);
                    }
                    for (ReportProductPharmaModel prod:arrayProductList){
                        productListAdapter.addProductToList(prod);
                    }
                    productListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }
                else if(SpinValue.equals("All"))
                {
                    GetAllPharmaProduct=new ArrayList<ReportProductPharmaModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new FragmentListProductPharmaAdapter( GetAllPharmaProduct ,getContext());
                    listview.setAdapter(productListAdapter);


                    arrayProductList = helper.getProductPharmaReportforAllActive(SpinValue);
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new FragmentListProductPharmaAdapter( arrayProductList ,getContext());
                        listview.setAdapter(productListAdapter);
                    }
                    for (ReportProductPharmaModel prod:arrayProductList){
                        productListAdapter.addProductToList(prod);
                    }
                    productListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }
                else
                {   arrayProductList = new ArrayList<ReportProductPharmaModel>();
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new FragmentListProductPharmaAdapter( arrayProductList ,getContext());
                        listview.setAdapter(productListAdapter);
                    }

                    // distributorListAdapter.addProductToList(arrayDistributorList.get(0));
                    // Log.i("&&&&&&&&", "Adding " + arrayDistributorList.get(0) + " to Product List..");
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
                searchProductList = helper.getProductPharmaName(userTypedString);
                if (searchProductList != null) {
                    if (searchProductAdapter == null) {
                        searchProductAdapter = new FragmentSearchProductPharmaAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchProductList);
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
                GetAllPharmaProduct=new ArrayList<ReportProductPharmaModel>();
                Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                productListAdapter = new FragmentListProductPharmaAdapter( GetAllPharmaProduct ,getContext());
                listview.setAdapter(productListAdapter);
                arrayProductList = helper.getProductPharmaReport(Value);
                if (productListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new FragmentListProductPharmaAdapter(new ArrayList<ReportProductPharmaModel>(),getContext());
                    productListAdapter.setList(arrayProductList);
                    listview.setAdapter(productListAdapter);
                }
                for(ReportProductPharmaModel product:arrayProductList)
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
