package ReportTabFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mycompany.apps.DBhelper;
import com.mycompany.apps.R;
import com.mycompany.apps.ShowDailySaleListActivity;

import java.util.ArrayList;

import Pojo.SaleReportModel;


public class FragmentDailySalesReport extends android.support.v4.app.Fragment{


    // add comment only....
    ListView listview;
   /* AutoCompleteTextView idTextView;
    private TextWatcher idTextWatcher;*/
    FragmentListDailySalesAdapter totalListAdapter;
    ArrayList<SaleReportModel>GetDailySales;
   /* ArrayList<SaleReportModel> searchIdList;
    ArrayList<SaleReportModel> arrayTotalList;




    uuuSBD<Jcbagblcabjvdcvja,hv,cja,bv,hjsvbmhzh,vcb,z,v,hzh

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
    FragmentSearchDailySalesAdapter searchIdAdapter;*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_sale_report, container, false);

        final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        final DBhelper helper = new DBhelper(getContext());
        helper.getReadableDatabase();

        listview = (ListView)view.findViewById(R.id.lv_SaleReport);
        Log.e("***********Lt1*******", listview.toString());

        GetDailySales=helper.getDailySalesReport();
        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
        totalListAdapter = new FragmentListDailySalesAdapter( GetDailySales ,getContext());
        listview.setAdapter(totalListAdapter);

        /*Submit = (Button)view.findViewById(R.id.Submit);

        FromMonth = (Spinner)view.findViewById(R.id.TotalFromMonth);
        ToMonth = (Spinner)view.findViewById(R.id.TotalToMonth);
        FromYear = (Spinner)view.findViewById(R.id.TotalFromYear);
        ToYear = (Spinner)view.findViewById(R.id.TotalToYear);
        FromDate = (Spinner)view.findViewById(R.id.TotalFromDate);
        ToDate = (Spinner)view.findViewById(R.id.TotalToDate);


        idTextView = (CustomAuto)view.findViewById(R.id.idTextView);
        idTextView.setThreshold(1);

        monthList = (ListView)view.findViewById(R.id.lv_SaleReport);
        Log.e("***********Lt1*******", monthList.toString());

        final String[] month = {"Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, month);


        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2016; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        ArrayList<String> dates = new ArrayList<String>();
        int Date = Calendar.getInstance().get(Calendar.DATE);
        for (int i = 1; i <= 31; i++) {
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

                String month_to = ToMonthValue;
                String year_to = ToYearValue;
                String date_to = ToDateValue;


                fromString = String.format("%s-%02d-%s",year_from ,getIntFromMonthName(month_from), date_from);
                Log.e("Value from date ",fromString);
                toString = String.format("%s-%02d-%s", year_to,getIntFromMonthName(month_to),date_to );
                Log.e("Value To date ",toString);
                arrayTotalList = helper.SaleData(fromString, toString);
                totalListAdapter = new FragmentListDailySalesAdapter(arrayTotalList,getContext());
                monthList.setAdapter(totalListAdapter);
                totalListAdapter.notifyDataSetChanged();

            }
        });*/
        /******************************** distributor name selected from here********************************************************************************************/
       /* idTextView.setThreshold(1);
        idTextWatcher = new

                TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (idTextView.getText().toString().matches("")) {
                            Toast.makeText(getContext(), "Please select the Transaction Id", Toast.LENGTH_SHORT).show();
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
                        searchIdList = helper.getTransId(userTypedString);
                        if (searchIdList != null) {
                            if (searchIdAdapter == null) {
                                searchIdAdapter = new FragmentSearchDailySalesAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchIdList);
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
        idTextView.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Value = parent.getItemAtPosition(position).toString();

                arrayTotalList = helper.getSaleReport(Value);
                if (totalListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    totalListAdapter = new FragmentListDailySalesAdapter(new ArrayList<SaleReportModel>(),getContext());
                    totalListAdapter.setList(arrayTotalList);
                    listview.setAdapter(totalListAdapter);
                } else {
                    totalListAdapter.setList(arrayTotalList);
                    totalListAdapter.notifyDataSetChanged();
                }

            }
        });*/

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                String ty =null;
                for (SaleReportModel prod:GetDailySales)
                {
                    ty =prod.getTransId().toString();
                    Log.e("Madhavi", "" + prod.getGrnTotl().toString());
                }


                Log.e("Test", ty);
                Bundle dataBundle = new Bundle();
                dataBundle.putString("id", ty);

                Intent intent = new Intent(getContext(), ShowDailySaleListActivity.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
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

  /*  private int getIntFromMonthName( String monthName ) {
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
    }*/

}