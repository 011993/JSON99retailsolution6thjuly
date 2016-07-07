


        package Adapter;

        import android.app.DatePickerDialog;
        import android.content.Context;
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

        import com.mycompany.apps.DecimalDigitsInputFilter;
        import com.mycompany.apps.R;
        import com.mycompany.apps.activity_inventory;

        import java.text.DecimalFormat;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;

        import Pojo.Inventoryproductmodel;
        import Pojo.PurchaseProductModelwithpo;

public class fullproductadapter  extends BaseAdapter {
    activity_inventory activity;

    ArrayList<Inventoryproductmodel> list;
    private final int layoutResourceId;
    LayoutInflater layoutInflater;
    String item;
    Inventoryproductmodel purchaseProductModel;
    static final int DATE_DIALOG_ID = 0;

    Calendar myCalendar = Calendar.getInstance();
    private int mTempPositionBeforeCalenderDialog = -1;



    public fullproductadapter(activity_inventory activity, ArrayList<Inventoryproductmodel> list, int layoutResourceId,  Inventoryproductmodel purchaseProductModel) {
        this.activity = activity;
        this.list = list;
        this.layoutResourceId = layoutResourceId;
        this.purchaseProductModel = purchaseProductModel;
    }

    public long getItemId(int position)
    {

        Log.e("**********", "" + position);
        return position;
    }

    public int getCount() {
        if (list.size()<0)
            return 1;
        Log.e("**get Count***", list.toString());
        return list.size();

    }
    public Inventoryproductmodel getItem(int position) {
        return list.get(position);
    }

    public static class ViewHolder
    {
        //TextView PurchaseId;
        public TextView PurchaseName;
        public EditText Purchasemrp;
        public EditText Sellingprice;
        public EditText purchasingprice;
        public TextView measure;
        public TextView Total;
        public EditText  EtQty;
        public   EditText expdate;
        public  EditText freequantity;
        public EditText batchno;
        public ImageButton DeleteButton;
        public TextView pricemargin;
        public  TextView industry;
        public  TextView totalqty;
        public  TextView conversion;



    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final         ViewHolder holder;
        DecimalFormat f=new DecimalFormat("##.00");

        if (convertView==null)
        {
            holder= new ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.display_inventory_product_row,parent,false);
            //  holder.PurchaseId=(TextView)convertView.findViewById(R.id.PurchaseproductId);
            holder.PurchaseName=(TextView)convertView.findViewById(R.id.PurchaseproductName);
            holder.Purchasemrp=(EditText)convertView.findViewById(R.id.PurchaseproductMrp);
            holder.Purchasemrp.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});

            holder.Sellingprice=(EditText)convertView.findViewById(R.id.purchaseselling);
            holder.Sellingprice.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});

            holder.industry = (TextView) convertView.findViewById(R.id.inventoryindustery);
            holder.purchasingprice=(EditText)convertView.findViewById(R.id.purchaseprice);
            holder.purchasingprice.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});

            holder.measure=(TextView)convertView.findViewById(R.id.purchasemeasure);
            holder.EtQty =(EditText) convertView.findViewById(R.id.purchasequantity);
            holder.expdate =(EditText) convertView.findViewById(R.id.Exp_date);
            holder.batchno =(EditText)convertView.findViewById(R.id.purchasebatchno);
            //  holder.stock =(TextView)convertView.findViewById(R.id.stockinv);
            holder.freequantity =(EditText) convertView.findViewById(R.id.discountwithoutpo);
            holder.DeleteButton=(ImageButton)convertView.findViewById(R.id.deleteButton);
            holder.Total=(TextView)convertView.findViewById(R.id.we);
            holder.Total.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});

            holder.pricemargin=(TextView)convertView.findViewById(R.id.inventorymargin);
            holder.pricemargin.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});

            holder.totalqty = (TextView) convertView.findViewById(R.id.totalqty);
            holder.conversion = (TextView) convertView.findViewById(R.id.inventoryconversion);


            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }

      /*  String ActiveType[] = {"2016/06","2016/07","2016/08","2016/09","2016/10","2016/11","2016/12","2017/01","2017/02","2017/03","2017/04","2017/05","2017/06",
                "2017/07","2017/08","2017/09","2017/10","2017/11","2017/12","2018/01","2018/02","2018/03","2018/04","2018/05","2018/06","2018/07","2018/08","2018/09","2018/10","2018/11","2018/12"
                ,"2019/16","2019/02","2019/03","2019/04","2019/05","2019/06","2019/07","2019/08","2019/09","2019/10","2019/11","2019/12"};
        ArrayAdapter<String> adapteractiveType =new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_dropdown_item,ActiveType);
        //adapteractiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        holder.expdate.setAdapter(adapteractiveType);
        holder.expdate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                item = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

*/

        //     holder.PurchaseId.setText(list.get(position).getProductId());
        //   holder.industry.setText(list.get(position).getIndustry());


        if ((holder.Purchasemrp.getTag() != null) && (holder.Purchasemrp.getTag() instanceof TextWatcher)) {
            holder.Purchasemrp.removeTextChangedListener((TextWatcher) holder.Purchasemrp.getTag());
        }
        holder.Purchasemrp.setText(String.format("%.2f", list.get(position).getMrp()));

        if ((holder.purchasingprice.getTag() != null) && (holder.purchasingprice.getTag() instanceof TextWatcher)) {
            holder.purchasingprice.removeTextChangedListener((TextWatcher) holder.purchasingprice.getTag());
        }
        holder.purchasingprice.setText(String.format("%.2f", list.get(position).getPprice()));

        if ((holder.EtQty.getTag() != null) && (holder.EtQty.getTag() instanceof TextWatcher)) {
            holder.EtQty.removeTextChangedListener((TextWatcher) holder.EtQty.getTag());
        }
        holder.EtQty.setText(String.format("%d", list.get(position).getProductQuantity()));

        if( (holder.freequantity.getTag() != null)  && (holder.freequantity.getTag() instanceof TextWatcher) ) {
            holder.freequantity.removeTextChangedListener((TextWatcher) holder.freequantity.getTag());
        }
        holder.freequantity.setText(String.format("%d", list.get(position).getFreequantity()));

        if ((holder.Sellingprice.getTag() != null) && (holder.Sellingprice.getTag() instanceof TextWatcher)) {
            holder.Sellingprice.removeTextChangedListener((TextWatcher) holder.Sellingprice.getTag());
        }
        holder.Sellingprice.setText(String.format("%.2f", list.get(position).getSprice()));
        if ((holder.batchno.getTag() != null) && (holder.batchno.getTag() instanceof TextWatcher)) {
            holder.batchno.removeTextChangedListener((TextWatcher) holder.batchno.getTag());
        }

        holder.batchno.setText(list.get(position). getBatchno());
        holder.totalqty.setText(String.format("%.2f", list.get(position).getTotalqty()));
        holder.conversion.setText(String.format("%.2f", list.get(position).getConvfact()));
        holder.pricemargin.setText(String.format("%.2f", list.get(position).getProductmargin()));
        holder.PurchaseName.setText(list.get(position).getProductName());
        holder.measure.setText(list.get(position).getTax());

        holder.Total.setText(String.format("%.2f", list.get(position).getTotal()));
        Double RetVal=((Double.parseDouble(holder.Purchasemrp.getText().toString()) - (Double.parseDouble(holder.purchasingprice.getText().toString()))) * 100) / Double.parseDouble(holder.Purchasemrp.getText().toString());
        holder.pricemargin.setText(String.valueOf(f.format(RetVal)));


        holder.totalqty.setText(String.valueOf(((Double.parseDouble(holder.EtQty.getText().toString()) + (Double.parseDouble(holder.freequantity.getText().toString()))) * Double.parseDouble(holder.conversion.getText().toString()))));
      //  holder.pricemargin.setText(String.valueOf(((Double.parseDouble(holder.Purchasemrp.getText().toString()) - (Double.parseDouble(holder.purchasingprice.getText().toString()))) * 100) / Double.parseDouble(holder.Purchasemrp.getText().toString())));
        holder.Total.setText((String.valueOf(f.format(Double.parseDouble(holder.purchasingprice.getText().toString()) * (Double.parseDouble(holder.EtQty.getText().toString()))))));
     //   holder.EtQty.setText(String.valueOf(Double.parseDouble(holder.EtQty.getText().toString()) * (Double.parseDouble(holder.freequantity.getText().toString()) )));
        holder.expdate.setText(list.get(position).getExpdate());

        //TextWatcher quantityTextChangeWatcher = new TextWatcher() {

   /* catch (NumberFormatException ex){
    ex.printStackTrace();*/


        //  holder.EtQty.addTextChangedListener(new Customtextwatcher(holder.EtQty));

        // holder.EtQty.getText().toString();
       /* if (holder.batchno.getText().toString().length()==0) {
            Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
            holder.batchno.setError("not empty");

        }*/

        TextWatcher quantityTextChangeWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (holder.batchno.getText().toString().length()==0) {
                    Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                    holder.batchno.setError("not empty");
                    return;


                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {

                    if (holder.EtQty.getText().toString().equals("")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                       // holder.EtQty.setError("not empty");
                        return;


                    }
                    if (holder.freequantity.getText().toString().equals("")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        holder.freequantity.setError("not empty");
                        return;


                    }
                    if (holder.batchno.getText().toString().length()==0) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                       // holder.batchno.setError("not empty");
                        return;


                    }

                    if (holder.Purchasemrp.getText().toString().trim().equals("") || holder.Purchasemrp.getText().toString().trim().equals("0.00")
                            ||Double.parseDouble(holder.Sellingprice
                            .getText().toString().trim()) > Double.parseDouble(holder.Purchasemrp.getText().toString())) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        holder.Sellingprice.setError("not more then mrp");
                        // holder.sprice.setText("");
                        return;
                    }
                    // holder.EtQty.setText(list.get(position).getProductQuantity());


                  //  holder.totalqty.setText(String.valueOf(Double.parseDouble(holder.EtQty.getText().toString())  * Double.parseDouble(holder.conversion.getText().toString())));
                    holder.totalqty.setText(String.valueOf(((Double.parseDouble(holder.EtQty.getText().toString()) + (Double.parseDouble(holder.freequantity.getText().toString()))) * Double.parseDouble(holder.conversion.getText().toString()))));        holder.pricemargin.setText(String.valueOf(((Double.parseDouble(holder.Purchasemrp.getText().toString()) - (Double.parseDouble(holder.purchasingprice.getText().toString()))) * 100) / Double.parseDouble(holder.Purchasemrp.getText().toString())));
                    DecimalFormat f=new DecimalFormat("##.00");
                    Double RetVal=((Double.parseDouble(holder.Purchasemrp.getText().toString()) - (Double.parseDouble(holder.purchasingprice.getText().toString()))) * 100) / Double.parseDouble(holder.Purchasemrp.getText().toString());
                    holder.pricemargin.setText(String.valueOf(f.format(RetVal)));

                    //    holder.pricemargin.setText(String.valueOf(((Double.parseDouble(holder.Purchasemrp.getText().toString()) - (Double.parseDouble(holder.purchasingprice.getText().toString()))) * 100) / Double.parseDouble(holder.Purchasemrp.getText().toString())));
                    holder.Total.setText((String.valueOf(f.format(Double.parseDouble(holder.purchasingprice.getText().toString()) * (Double.parseDouble(holder.EtQty.getText().toString()))))));
                    list.get(position).setProductQuantity(Integer.parseInt(holder.EtQty.getText().toString()));
                      /*  PurchaseProductModel purchaseProductModel= list.get(position);
                    holder.EtQty.setText((int) purchaseProductModel.getProductQuantity());*/
                    list.get(position).setProductQuantity(Integer.parseInt(holder.EtQty.getText().toString()));
                    list.get(position).setMrp(Float.parseFloat(holder.Purchasemrp.getText().toString()));
                    list.get(position).setSprice(Float.parseFloat(holder.Sellingprice.getText().toString()));
                    list.get(position).setPprice(Float.parseFloat(holder.purchasingprice.getText().toString()));
                    list.get(position).setFreequantity(Integer.parseInt(holder.freequantity.getText().toString()));
                    list.get(position).setProductmargin(Float.parseFloat(holder.pricemargin.getText().toString()));
                    list.get(position).setTotalqty(Float.parseFloat(holder.totalqty.getText().toString()));

                    // list.get(position).setExpdate(item.toString());
                    list.get(position).setBatchno(holder.batchno.getText().toString());


                    activity.setSummaryRow();

                } catch (Exception e) {
                    Log.e("&&&&&&&&", "Exception " + e + " while parsing double");
                    e.printStackTrace();
                }
            }
        };

        holder.EtQty.addTextChangedListener(quantityTextChangeWatcher);
        holder.EtQty.setTag(quantityTextChangeWatcher);

        holder.Purchasemrp.addTextChangedListener(quantityTextChangeWatcher);
        holder.Purchasemrp.setTag(quantityTextChangeWatcher);

        holder.Sellingprice.addTextChangedListener(quantityTextChangeWatcher);
        holder.Sellingprice.setTag(quantityTextChangeWatcher);

        holder.purchasingprice.addTextChangedListener(quantityTextChangeWatcher);
        holder.purchasingprice.setTag(quantityTextChangeWatcher);

        holder.freequantity.addTextChangedListener(quantityTextChangeWatcher);
        holder.freequantity.setTag(quantityTextChangeWatcher);

        holder.batchno.addTextChangedListener(quantityTextChangeWatcher);
        holder.batchno.setTag(quantityTextChangeWatcher);


        holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                activity.setSummaryRow();
                notifyDataSetChanged();
            }
        });



        holder.expdate.setOnClickListener(new View.OnClickListener() {
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
                        list.get(mTempPositionBeforeCalenderDialog).setExpdate(year+"/"+Mymonth +"/"+dayOfMonth);
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
                list.get(mTempPositionBeforeCalenderDialog).setExpdate(year+"/"+Mymonth +"/"+day);
                notifyDataSetChanged();
                mTempPositionBeforeCalenderDialog = -1;
                // holder.ExpDate.setText(day + "/" + month + "/" + year);

            }
        }
    }





    public  ArrayList<Inventoryproductmodel>getlist(){

        return list;
    }


    public int addProductToList( Inventoryproductmodel product ) {
        Log.e("&&&&&&&&", "Adding product " + product.toString() + " to product list");

        Inventoryproductmodel productAlreadyInList = findProductInList(product);
        if(productAlreadyInList == null) {
            list.add(0,product);
            return 0;
        } else {
            productAlreadyInList.setProductQuantity((int) (productAlreadyInList.getProductQuantity() + product.getProductQuantity()));
                return list.indexOf(productAlreadyInList);
        }

    }

    private Inventoryproductmodel findProductInList(Inventoryproductmodel product) {
        Inventoryproductmodel returnProductVal = null;

        for( Inventoryproductmodel productInList : list) {
            if( productInList.getProductName().trim().equals(product.getProductName().trim()) ) {
                returnProductVal = productInList;
            }
        }


        return returnProductVal;
    }

    public void clearAllRows()
    {
        list.clear();
        notifyDataSetChanged();

    }
    public void setBatchdata(Inventoryproductmodel product){
        Inventoryproductmodel productAlreadyInList = findProductInList(product);
        if (productAlreadyInList.getIndustry().toString().equals("CPG")||productAlreadyInList.getIndustry().toString().equals("CPG local"))
        {
            productAlreadyInList.setBatchno(product.getProductName());
        }
        else
        {
            productAlreadyInList.setBatchno(product.getBatchno());
        }
    }


    public float getGrandTotal()
    {
        // ViewHolder holder=new ViewHolder();
        float finalamount=0.0f;
        for (int listIndex = 0; listIndex < list.size(); listIndex++) {
            try {
                finalamount += (list.get(listIndex).getTotal());
            } catch (Exception e) {
                //ignore exeption for parse
            }
            Log.e("&&&&&^^^^^^^^", "$$$$$$$$" + finalamount);

        }
        Log.e("&&&&55555555&&&", "Total Price is:" + finalamount);
        return finalamount;
    }
}

