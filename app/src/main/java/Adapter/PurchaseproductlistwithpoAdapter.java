package Adapter;

import android.app.DatePickerDialog;
import android.content.Context;

import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.mycompany.apps.R;
import com.mycompany.apps.DecimalDigitsInputFilter;
import com.mycompany.apps.R;
import com.mycompany.apps.activity_inventorywithpo;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Pojo.PurchaseProductModelwithpo;


/**
 * Created by rspl-rahul on 11/3/16.
 */
public class PurchaseproductlistwithpoAdapter extends BaseAdapter {

    activity_inventorywithpo activity;
    ArrayList<PurchaseProductModelwithpo> list;
    SimpleDateFormat sdf;
    private final int layoutResourceId;
    LayoutInflater layoutInflater;
    String item;
    PurchaseProductModelwithpo purchaseProductModelwithpo;

    boolean isUserEditingQuantityTextView = true;
    static final int DATE_DIALOG_ID = 0;

    Calendar myCalendar = Calendar.getInstance();
    private int mTempPositionBeforeCalenderDialog = -1;

    public PurchaseproductlistwithpoAdapter(activity_inventorywithpo activity, ArrayList<PurchaseProductModelwithpo> list, int layoutResourceId, PurchaseProductModelwithpo purchaseProductModelwithpo) {
        Log.e("AAAAAAAA&&&&&&&&&", "in fullproductAdapterclass inside constructor");
        this.activity = activity;
        this.list = list;
        this.layoutResourceId = layoutResourceId;
        this.purchaseProductModelwithpo = purchaseProductModelwithpo;

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

    public PurchaseProductModelwithpo getItem(int position) {

        return list.get(position);
    }

    public static class ViewHolder {

        public TextView PurchaseName;
        public EditText mrp;
        public EditText sprice;
        public EditText Purchaseprice;
        public EditText EtQty;
        public TextView Total;
        public TextView UOM;
        public EditText BatchNumber;
        public EditText ExpDate;
        public TextView ProductId;
        public TextView PurchaseNumber;
        public ImageButton DeleteButton;
        public  EditText discounts;
        public  TextView margin;
        public  TextView industry;
        public  TextView totalqty;
        public  TextView conversion;

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        DecimalFormat f=new DecimalFormat("##.00");
        final ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.display_purchase_productwithpo_row, parent, false);
            holder.mrp = (EditText) convertView.findViewById(R.id.mrp);
            holder.mrp.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7,2)});

            holder.sprice = (EditText) convertView.findViewById(R.id.sprice);
            holder.sprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7,2)});

            holder.Purchaseprice = (EditText) convertView.findViewById(R.id.pprice);
            holder.Purchaseprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7,2)});

            holder.PurchaseName = (TextView) convertView.findViewById(R.id.productname);
            holder.EtQty = (EditText) convertView.findViewById(R.id.qty);
            holder.Total = (TextView) convertView.findViewById(R.id.totalval);
            holder.Total.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});

            holder.UOM = (TextView) convertView.findViewById(R.id.uom);
            holder.industry = (TextView) convertView.findViewById(R.id.inventoryindustery);
            holder.ExpDate = (EditText) convertView.findViewById(R.id.Exp_date);
            holder.BatchNumber = (EditText) convertView.findViewById(R.id.BatchNumber);
            holder.discounts =(EditText)convertView.findViewById(R.id.disc);

            holder.margin =(TextView)convertView.findViewById(R.id.inventorymargin);
            holder.margin.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});

            holder.totalqty = (TextView) convertView.findViewById(R.id.totalqty);
            holder.conversion = (TextView) convertView.findViewById(R.id.inventoryconversion);
            // holder.PurchaseNumber=(TextView)convertView.findViewById(R.id.Purchaseno);
            // holder.ProductId=(TextView)convertView.findViewById(R.id.productid);
            holder.DeleteButton = (ImageButton) convertView.findViewById(R.id.deleteButton);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
      /*  String ActiveType[] = {"2016/06","2016/07","2016/08","2016/09","2016/10","2016/11","2016/12","2017/01","2017/02","2017/03","2017/04","2017/05","2017/06",
        "2017/07","2017/08","2017/09","2017/10","2017/11","2017/12","2018/01","2018/02","2018/03","2018/04","2018/05","2018/06","2018/07","2018/08","2018/09","2018/10","2018/11","2018/12"
                ,"2019/16","2019/02","2019/03","2019/04","2019/05","2019/06","2019/07","2019/08","2019/09","2019/10","2019/11","2019/12"};
        ArrayAdapter<String> adapteractiveType =new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_dropdown_item,ActiveType);
        //adapteractiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        holder.ExpDate.setAdapter(adapteractiveType);
        holder.ExpDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                item = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
*/
        holder.PurchaseName.setText(list.get(position).getProductName());
        holder.industry.setText(list.get(position).getIndustery());

        if ((holder.mrp.getTag() != null) && (holder.mrp.getTag() instanceof TextWatcher)) {
            holder.mrp.removeTextChangedListener((TextWatcher) holder.mrp.getTag());
        }
        holder.mrp.setText(String.format("%.2f", list.get(position).getMrp()));

        if ((holder.Purchaseprice.getTag() != null) && (holder.Purchaseprice.getTag() instanceof TextWatcher)) {
            holder.Purchaseprice.removeTextChangedListener((TextWatcher) holder.Purchaseprice.getTag());
        }
        holder.Purchaseprice.setText(String.format("%.2f", list.get(position).getProductPrice()));

        if ((holder.EtQty.getTag() != null) && (holder.EtQty.getTag() instanceof TextWatcher)) {
            holder.EtQty.removeTextChangedListener((TextWatcher) holder.EtQty.getTag());
        }
        holder.EtQty.setText(String.format("%d", list.get(position).getProductQuantity()));

        if( (holder.discounts.getTag() != null)  && (holder.discounts.getTag() instanceof TextWatcher) ) {
            holder.discounts.removeTextChangedListener((TextWatcher) holder.discounts.getTag());
        }
        holder.discounts.setText(String.format("%d", list.get(position).discountitems));

        if ((holder.sprice.getTag() != null) && (holder.sprice.getTag() instanceof TextWatcher)) {
            holder.sprice.removeTextChangedListener((TextWatcher) holder.sprice.getTag());
        }
        holder.sprice.setText(String.format("%.2f", list.get(position).getSprice()));

        if ((holder.BatchNumber.getTag() != null) && (holder.BatchNumber.getTag() instanceof TextWatcher)) {
            holder.BatchNumber.removeTextChangedListener((TextWatcher) holder.BatchNumber.getTag());
        }
        holder.BatchNumber.setText(list.get(position).getBatch_No());

      //  holder.ProductId.setText(list.get(position).getProductId());
     //   holder.PurchaseNumber.setText(list.get(position).getPurchaseno());
        holder.UOM.setText(list.get(position).getUom());
        holder.totalqty.setText(String.format("%.2f", list.get(position).getTotalqty()));
        holder.conversion.setText(String.format("%.2f", list.get(position).getConversion()));
        holder.margin.setText(String.format("%.2f", list.get(position).getProductmargin()));
        holder.Total.setText(String.format("%.2f", list.get(position).getTotal()));


       Double RetVal=((Double.parseDouble(holder.mrp.getText().toString()) - (Double.parseDouble(holder.Purchaseprice.getText().toString()))) * 100) / Double.parseDouble(holder.mrp.getText().toString());
        holder.margin.setText(String.valueOf(f.format(RetVal)));

        holder.totalqty.setText(String.valueOf(((Double.parseDouble(holder.EtQty.getText().toString()) + (Double.parseDouble(holder.discounts.getText().toString()))) * Double.parseDouble(holder.conversion.getText().toString()))));
        holder.Total.setText(String.valueOf(f.format(Double.parseDouble(holder.Purchaseprice.getText().toString()) * (Double.parseDouble(holder.EtQty.getText().toString())))));
   //     holder.EtQty.setText(String.valueOf(Double.parseDouble(holder.EtQty.getText().toString()) + (Double.parseDouble(holder.discounts.getText().toString()) )));
       holder.ExpDate.setText(list.get(position).getExp_Date());
        TextWatcher quantityTextChangeWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                DecimalFormat f=new DecimalFormat("##.00");

                View adapterView;
                try {


                    if (holder.EtQty.getText().toString().equals("")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        holder.EtQty.setError("not empty");
                        return;


                    }
                  else  if (holder.discounts.getText().toString().equals("")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        holder.discounts.setError("not empty");
                        return;


                    }



                  /*  if (holder.mrp.getText().toString().trim().equals("") || holder.mrp.getText().toString().trim().equals("0.00")
                            ||Double.parseDouble(holder.sprice
                            .getText().toString().trim()) > Double.parseDouble(holder.mrp.getText().toString())) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        holder.sprice.setError("not more then mrp");
                        holder.sprice.setText("");
                    return;
                    }*/
                     if (holder.mrp.getText().toString().trim().equals("")
                            ||Double.parseDouble(holder.sprice
                            .getText().toString().trim()) > Double.parseDouble(holder.mrp.getText().toString())) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        holder.sprice.setText("");

                        return;
                    }
                    Double RetVal=((Double.parseDouble(holder.mrp.getText().toString()) - (Double.parseDouble(holder.Purchaseprice.getText().toString()))) * 100) / Double.parseDouble(holder.mrp.getText().toString());
                    holder.margin.setText(String.valueOf(f.format(RetVal)));

                    holder.totalqty.setText(String.valueOf(((Double.parseDouble(holder.EtQty.getText().toString()) + (Double.parseDouble(holder.discounts.getText().toString()))) * Double.parseDouble(holder.conversion.getText().toString()))));
                   holder.Total.setText(String.valueOf(f.format(Double.parseDouble(holder.Purchaseprice.getText().toString()) * (Double.parseDouble(holder.EtQty.getText().toString())))));
            //        holder.EtQty.setText(String.valueOf(Double.parseDouble(holder.EtQty.getText().toString()) + (Double.parseDouble(holder.discounts.getText().toString()) )));
                    //  list.get(position).setTotal((Float.parseFloat(holder.Purchaseprice.getText().toString()))*Float.parseFloat(holder.EtQty.getText().toString()));
                    list.get(position).setProductQuantity(Integer.parseInt(holder.EtQty.getText().toString()));
                    list.get(position).setMrp(Float.parseFloat(holder.mrp.getText().toString()));
                    list.get(position).setSprice(Float.parseFloat(holder.sprice.getText().toString()));
                    list.get(position).setProductPrice(Float.parseFloat(holder.Purchaseprice.getText().toString()));
                    list.get(position).setDiscountitems(Integer.parseInt(holder.discounts.getText().toString()));
                    list.get(position).setProductmargin(Float.parseFloat((holder.margin.getText().toString())));
                    list.get(position).setBatch_No(holder.BatchNumber.getText().toString());
                    Log.e("&&&total&&&&", "$$$$$" + holder.Total.getText().toString());
                    activity.setSummaryRow();
                } catch (Exception e) {
                    Log.e("&&&&&&&&", "Exception " + e + " while parsing double");
                    e.printStackTrace();
                }
            }
        };

        holder.EtQty.addTextChangedListener(quantityTextChangeWatcher);
        holder.EtQty.setTag(quantityTextChangeWatcher);

        holder.mrp.addTextChangedListener(quantityTextChangeWatcher);
        holder.mrp.setTag(quantityTextChangeWatcher);

        holder.sprice.addTextChangedListener(quantityTextChangeWatcher);
        holder.sprice.setTag(quantityTextChangeWatcher);

        holder.Purchaseprice.addTextChangedListener(quantityTextChangeWatcher);
        holder.Purchaseprice.setTag(quantityTextChangeWatcher);

        holder.discounts.addTextChangedListener(quantityTextChangeWatcher);
        holder.discounts.setTag(quantityTextChangeWatcher);

        holder.BatchNumber.addTextChangedListener(quantityTextChangeWatcher);
        holder.BatchNumber.setTag(quantityTextChangeWatcher);

        holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                activity.setSummaryRow();
                notifyDataSetChanged();
            }
        });



        holder.ExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTempPositionBeforeCalenderDialog = position;
                new DatePickerDialog(activity, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        return convertView;

    }


    private DatePickerDialog.OnDateSetListener date = new
            DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month,
                                      int day) {
                    // TODO Auto-generated method stub

                    validateDate(year, month + 1, day);
                  /* if(mTempPositionBeforeCalenderDialog != -1 && mTempPositionBeforeCalenderDialog < list.size()) {
                        int Mymonth=1+monthOfYear;
                        list.get(mTempPositionBeforeCalenderDialog).setExp_Date(year+"/"+Mymonth +"/"+dayOfMonth);
                        notifyDataSetChanged();
                        mTempPositionBeforeCalenderDialog = -1;
                    }*/
                    //  demo =myCalendar.get(Calendar.YEAR)+"/"+ myCalendar.get(Calendar.MONTH)+"/"+myCalendar.get(Calendar.DAY_OF_MONTH);
                }
            };


    public void validateDate(int year,int month,int day) {
        ViewHolder holder=new ViewHolder();
        Date Todaydate=null,edate=null;

        String enddate=year+"/"+month+"/"+day;
        Log.e("########", "----------->" + enddate);


        String   demo =myCalendar.get(Calendar.YEAR)+"/"+ myCalendar.get(Calendar.MONTH)+"/"+myCalendar.get(Calendar.DAY_OF_MONTH);
        Log.e("########", "----------->" + demo);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            edate = sdf.parse(enddate);
            Todaydate = sdf.parse(demo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(edate.before(Todaydate)){
            //   startDatepicker.dismiss();
            Toast.makeText(activity,"Invalid date !!", Toast.LENGTH_SHORT).show();
            //  list.get(mTempPositionBeforeCalenderDialog).setExp_Date(year+"/"+month +"/"+day);
            //  startDatepicker.show(getSupportFragmentManager(), "showDate");
            //return;
            return ;


        }
        else{
            Log.e("########", "----------->" + demo);
            Log.e("########", "----------->" + edate);

            //   startDatePicked=true;
            if(mTempPositionBeforeCalenderDialog != -1 && mTempPositionBeforeCalenderDialog < list.size()) {
                int Mymonth=month;
                list.get(mTempPositionBeforeCalenderDialog).setExp_Date(year+"/"+Mymonth +"/"+day);
                notifyDataSetChanged();
                mTempPositionBeforeCalenderDialog = -1;
                // holder.ExpDate.setText(day + "/" + month + "/" + year);

            }
        }
    }






    public int  addProductToList(PurchaseProductModelwithpo product) {
        Log.e("&&&&&&&&", "Adding product " + product.toString() + " to product list");

        PurchaseProductModelwithpo productAlreadyInList = findProductInList(product);
        if (productAlreadyInList == null) {
            list.add(0,product);
            return 0;
        } else {
            productAlreadyInList.setProductQuantity((int) (productAlreadyInList.getProductQuantity() + product.getProductQuantity()));
            return list.indexOf(productAlreadyInList);
        }

    }
    public void setBatchdata(PurchaseProductModelwithpo product){
        PurchaseProductModelwithpo productAlreadyInList = findProductInList(product);
        if (productAlreadyInList.getIndustery().toString().equals("CPG")||productAlreadyInList.getIndustery().toString().equals("CPG local"))
        {
            productAlreadyInList.setBatch_No(product.getProductName());
        }
        else
        {
            productAlreadyInList.setBatch_No(product.getBatch_No());
        }
    }
    public ArrayList<PurchaseProductModelwithpo> getList()
    {
        return list;
    }

    private PurchaseProductModelwithpo findProductInList(PurchaseProductModelwithpo product) {
        PurchaseProductModelwithpo returnProductVal = null;

        for (PurchaseProductModelwithpo productInList : list) {
            if (productInList.getProductId().trim().equals(product.getProductId().trim())) {
                returnProductVal = productInList;
            }
        }
        return returnProductVal;
    }

    public void clearAllRows() {
        list.clear();
        notifyDataSetChanged();
    }

    public float getGrandTotal() {
        ViewHolder holder = new ViewHolder();
        float finalamount = 0.0f;
        for (int listIndex = 0; listIndex < list.size(); listIndex++) {
            try {
                finalamount += (list.get(listIndex).getTotal());
            } catch (Exception e) {
                //ignore exeption for parse
            }
            Log.e("&&&&&^^^^^^^^", "$$$$$$$$" + finalamount);

        }
        Log.e("&&&&55555555&&&", "Total Price is:" + finalamount);
        // notifyDataSetChanged();
        return finalamount;
    }
}