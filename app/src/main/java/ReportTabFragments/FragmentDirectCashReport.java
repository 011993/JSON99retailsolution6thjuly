package ReportTabFragments;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mycompany.apps.CustomAuto;
import com.mycompany.apps.DBhelper;
import com.mycompany.apps.R;

import java.util.ArrayList;
import java.util.Calendar;

import Pojo.DirectVendorPaymentModel;


public class FragmentDirectCashReport extends android.support.v4.app.Fragment{

    ListView listview;
    AutoCompleteTextView idTextView;
    private TextWatcher idTextWatcher;
    FragmentDirectCashListAdapter paycashListAdapter;
    ArrayList<DirectVendorPaymentModel> searchIdList;
    ArrayList<DirectVendorPaymentModel> arrayPayCashList;
    FragmentDirectCashSearchAdapter searchIdAdapter;
    public Spinner FromMonth;
    public Spinner ToMonth;
    public Spinner FromYear;
    public Spinner ToYear;
    public Spinner FromDate;
    public Spinner ToDate;
    String FromYearValue;
    String ToYearValue;
    String FromMonthValue;
    String ToMonthValue;
    String FromDateValue;
    String ToDateValue;
    String fromString;
    String toString ;
    Button Submit;
    private ListView monthList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_report_direct_pay_by_cash, container, false);



        final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        final DBhelper helper = new DBhelper(getContext());
        helper.getReadableDatabase();

        listview = (ListView) view.findViewById(R.id.lv_directCashReport);
        Log.e("***********Lt1*******", listview.toString());
        idTextView = (CustomAuto) view.findViewById(R.id.DirectCashIdTextView);
        idTextView.setThreshold(1);

        Submit=(Button)view.findViewById(R.id.Submit);
        FromMonth = (Spinner)view. findViewById(R.id.DaysFromMonth);
        ToMonth=(Spinner)view.findViewById(R.id.DaysToMonth);
        FromYear=(Spinner)view.findViewById(R.id.DaysFromYear);
        ToYear=(Spinner)view.findViewById(R.id.DaysToYear);
        FromDate=(Spinner)view.findViewById(R.id.DaysFromDate);
        ToDate=(Spinner)view.findViewById(R.id.DaysToDate);

        monthList = (ListView)view. findViewById(R.id.lv_directCashReport);
        Log.e("***********Lt1*******", monthList.toString());

        final String[] month = {"Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, month);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2016; i <= 2019; i++) {
            years.add(Integer.toString(i));
        }

        ArrayList<String> dates = new ArrayList<String>();
        int Date = Calendar.getInstance().get(Calendar.DATE);
        for (int i = 1;i<=31;i++){
            dates.add(Integer.toString(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years);
        FromMonth.setAdapter(arrayAdapter);
        ToMonth.setAdapter(arrayAdapter);
        FromYear.setAdapter(adapter);
        ToYear.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dates);
        FromDate.setAdapter(adapter1);
        ToDate.setAdapter(adapter1);

        FromMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FromMonthValue = FromMonth.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ToMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToMonthValue = ToMonth.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        FromYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FromYearValue = FromYear.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ToYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToYearValue = ToYear.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        FromDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FromDateValue = FromDate.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ToDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToDateValue = ToDate.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String month_from = FromMonthValue;
                String year_from = FromYearValue;
                String date_from = FromDateValue;
                String  month_to = ToMonthValue;
                String  year_to = ToYearValue;
                String  date_to = ToDateValue;

                fromString = String.format("%s-%02d-%s",year_from ,getIntFromMonthName(month_from), date_from);
                Log.e("Value from date ",fromString);
                toString = String.format("%s-%02d-%s", year_to,getIntFromMonthName(month_to),date_to );
                Log.e("Value To date ",toString);
                arrayPayCashList = helper.DirectPayByCashReportDataForMonth(fromString, toString);
                paycashListAdapter = new FragmentDirectCashListAdapter(arrayPayCashList,getContext());
                monthList.setAdapter(paycashListAdapter);
                paycashListAdapter.notifyDataSetChanged();
            }
        });

        //******************************** grn id selected from here********************************************************************************************/
        idTextView.setThreshold(1);
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
                    Toast.makeText(getContext(), "Please select the Grn Id", Toast.LENGTH_SHORT).show();
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
                searchIdList = helper.getGrnDirectId(userTypedString);
                if (searchIdList != null) {
                    if (searchIdAdapter == null) {
                        searchIdAdapter = new FragmentDirectCashSearchAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchIdList);
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
                arrayPayCashList = helper.getDirectPayByCashReport(Value);
                if (paycashListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    paycashListAdapter = new FragmentDirectCashListAdapter(new ArrayList<DirectVendorPaymentModel>(), getContext());
                    paycashListAdapter.setList(arrayPayCashList);
                    listview.setAdapter(paycashListAdapter);
                    idTextView.setText("");
                } else {
                    paycashListAdapter.setList(arrayPayCashList);
                    paycashListAdapter.notifyDataSetChanged();
                    idTextView.setText("");
                }
            }
        });


        return view;
    }

    /**
     *
     * @param monthName Month name either in full ("January") or short "Jan"
     *                  case insensitive
     * @return Returns integer corresponding to month name (1 for January...)
     *          Returns -1 if month name not recognized
     */

    private int getIntFromMonthName( String monthName ) {
        String[] monthNames = new String[] { "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December" };
        int returnVal = -1;
        for( int i=0; i < monthNames.length; i++ ) {
            if( monthNames[i].toLowerCase().contains(monthName.toLowerCase())) {
                returnVal = i+1;
                break;
            }
        }
        return returnVal;
    }

}
