package com.bjain.pegasus.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bjain.pegasus.R;
import com.bjain.pegasus.database.DatabaseHelper;
import com.bjain.pegasus.pojo.address.AddressDataPOJO;
import com.bjain.pegasus.pojo.address.AddressPOJO;
import com.bjain.pegasus.pojo.address.AddressResultPOJO;
import com.bjain.pegasus.pojo.cart.CartDataPOJO;
import com.bjain.pegasus.pojo.cart.MainCartPOJO;
import com.bjain.pegasus.utils.Pref;
import com.bjain.pegasus.utils.StringUtils;
import com.bjain.pegasus.utils.TagUtils;
import com.bjain.pegasus.utils.ToastClass;
import com.bjain.pegasus.webservice.WebServiceBase;
import com.bjain.pegasus.webservice.WebServicesCallBack;
import com.bjain.pegasus.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartCheckOutActivity extends AppCompatActivity implements WebServicesCallBack {

    private static final String CASH_ON_DELIVERY_API = "cash_on_delivery_api";
    private final String TAG = getClass().getSimpleName();


    private static final String GET_ADDRESS_API = "get_address_api";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_scroll_orders)
    LinearLayout ll_scroll_orders;

    @BindView(R.id.spinner_address)
    Spinner spinner_address;

    @BindView(R.id.rg_shipping_method)
    RadioGroup rg_shipping_method;
    @BindView(R.id.rb_standard_carrier)
    RadioButton rb_standard_carrier;
    @BindView(R.id.rb_express_carrier)
    RadioButton rb_express_carrier;
    @BindView(R.id.rg_payment_method)
    RadioGroup rg_payment_method;
    @BindView(R.id.rb_online_payment)
    RadioButton rb_online_payment;
    @BindView(R.id.rb_cash_on_delivery)
    RadioButton rb_cash_on_delivery;
    @BindView(R.id.tv_subtotal)
    TextView tv_subtotal;
    @BindView(R.id.tv_shipping_charges)
    TextView tv_shipping_charges;
    @BindView(R.id.tv_grand_total)
    TextView tv_grand_total;
    @BindView(R.id.btn_place_order)
    Button btn_place_order;
    @BindView(R.id.ll_billing_addr)
    LinearLayout ll_billing_addr;
    @BindView(R.id.spinner_billing_address)
    Spinner spinner_billing_address;
    @BindView(R.id.check_shipping)
    CheckBox check_shipping;
    @BindView(R.id.btn_add_new)
    Button btn_add_new;

    List<AddressDataPOJO> addressDataPOJOList = new ArrayList<>();

    double grand_amount = 0.00;
    double standard_carrier_charges = 0.0;
    double express_carrier_charges = 0.0;
    double extra_amount = 0.00;
    MainCartPOJO cartPOJO;
    String string_grand_total = "";
    boolean is_online_payment = true;
    boolean is_standart_delivery = true;
    String price_currency = "INR";
    double extra_charges=0.00;

    private static final int ADDRESS_INTENT = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_check_out);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cart Checkout");

        callGetAddressAPI();
        price_currency=Pref.GetCurrency(getApplicationContext());
        cartPOJO = (MainCartPOJO) getIntent().getSerializableExtra("cartpojo");
        if (cartPOJO != null && cartPOJO.getCartDataPOJOList().size() > 0) {
            inflateCartItems(cartPOJO.getCartDataPOJOList());

            tv_grand_total.setText("GRAND TOTAL     " + price_currency + " " + cartPOJO.getGrand_total());
            tv_subtotal.setText("Subtotal     " + price_currency + " " + cartPOJO.getGrand_total());
            tv_shipping_charges.setText("Shipping & Handling (Express Carrier - (Free delivery in 3-5 Working Days)) " + price_currency + " 0.00");

            try {
                Log.d(TAG, "grand_amount:-" + cartPOJO.getGrand_total());
                grand_amount = Double.parseDouble(cartPOJO.getGrand_total());

                if (grand_amount <= 300) {
                    standard_carrier_charges = 50;
//                    grand_amount = Double.parseDouble(cartPOJO.getGrand_total()) + 50;
                    rb_standard_carrier.setText("Standard Carrier (Free delivery in 12-15 Working Days) " + price_currency + " 50.00");
                    tv_shipping_charges.setText("Shipping & Handling (Standard Carrier (Free delivery in 12-15 Working Days)) " + price_currency + " 50.00");
                } else {
//                    grand_amount = Double.parseDouble(cartPOJO.getGrand_total());
                    standard_carrier_charges = 0;
                    rb_standard_carrier.setText("Standard Carrier (Free delivery in 12-15 Working Days) " + price_currency + " 00.00");
                    tv_shipping_charges.setText("Shipping & Handling (Standard Carrier (Free delivery in 12-15 Working Days)) " + price_currency + " 0.00");
                }


                if (grand_amount <= 1000) {
                    express_carrier_charges = 100;
//                    grand_amount = Double.parseDouble(cartPOJO.getGrand_total()) + 100;
                    rb_express_carrier.setText("Express Carrier(Free Delivery in 3-5 Working Days) " + price_currency + " 100.00");
                } else {
//                    if (grand_amount > 100 && grand_amount < 1000) {
//                        express_carrier_charges = 10;
//                        grand_amount = Double.parseDouble(cartPOJO.getGrand_total()) + 10;
//                        rb_express_carrier.setText("Express Carrier(Free Delivery in 3-5 Working Days) " + price_currency + " 10.00");
//                    } else {
                    express_carrier_charges = 0;
//                    grand_amount = Double.parseDouble(cartPOJO.getGrand_total());
                    rb_express_carrier.setText("Express Carrier(Free Delivery in 3-5 Working Days) " + price_currency + " 00.00");
//                    }
                }


                is_standart_delivery = true;

                if (grand_amount <= 300) {
                    standard_carrier_charges = 50;
                    extra_amount = 50;
                    grand_amount = Double.parseDouble(cartPOJO.getGrand_total()) + 50;
                    rb_standard_carrier.setText("Standard Carrier (Free delivery in 12-15 Working Days) " + price_currency + " 50.00");
                    tv_shipping_charges.setText("Shipping & Handling (Standard Carrier (Free delivery in 12-15 Working Days)) " + price_currency + " 50.00");
                } else {
                    extra_amount = 0;
                    standard_carrier_charges = 0;
                    grand_amount = Double.parseDouble(cartPOJO.getGrand_total());
                    rb_standard_carrier.setText("Standard Carrier (Free delivery in 12-15 Working Days) " + price_currency + " 00.00");
                    tv_shipping_charges.setText("Shipping & Handling (Standard Carrier (Free delivery in 12-15 Working Days)) " + price_currency + ".00");
                }

                tv_grand_total.setText("GRAND TOTAL     " + price_currency + " " + grand_amount);
                rg_shipping_method.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        try {
                            if (rg_shipping_method.getCheckedRadioButtonId() == R.id.rb_standard_carrier) {
                                // Do something with the button
                                is_standart_delivery = true;

                                if (grand_amount <= 300) {
                                    standard_carrier_charges = 50;
                                    extra_amount = 50;
                                    grand_amount = Double.parseDouble(cartPOJO.getGrand_total()) + 50;
                                    rb_standard_carrier.setText("Standard Carrier (Free delivery in 12-15 Working Days) " + price_currency + " 50.00");
                                    tv_shipping_charges.setText("Shipping & Handling (Standard Carrier (Free delivery in 12-15 Working Days)) " + price_currency + " 50.00");
                                } else {
                                    extra_amount = 0;
                                    standard_carrier_charges = 0;
                                    grand_amount = Double.parseDouble(cartPOJO.getGrand_total());
                                    rb_standard_carrier.setText("Standard Carrier (Free delivery in 12-15 Working Days) " + price_currency + " 00.00");
                                    tv_shipping_charges.setText("Shipping & Handling (Standard Carrier (Free delivery in 12-15 Working Days)) " + price_currency + ".00");
                                }
                            } else {
                                is_standart_delivery = false;
                                if (grand_amount <= 1000) {
                                    extra_amount = 100;
                                    express_carrier_charges = 100;
                                    grand_amount = Double.parseDouble(cartPOJO.getGrand_total()) + 100;
                                    rb_express_carrier.setText("Express Carrier(Free Delivery in 3-5 Working Days) " + price_currency + " 100.00");
                                    tv_shipping_charges.setText("Shipping & Handling (Express Carrier(Free Delivery in 3-5 Working Days)) " + price_currency + " 100.00");
                                } else {
//                                    if (grand_amount > 100 && grand_amount < 1000) {
//                                        extra_amount = 10;
//                                        express_carrier_charges = 10;
//                                        grand_amount = Double.parseDouble(cartPOJO.getGrand_total()) + 10;
//                                        rb_express_carrier.setText("Express Carrier(Free Delivery in 3-5 Working Days) " + price_currency + " 10.00");
//                                        tv_shipping_charges.setText("Shipping & Handling (Express Carrier(Free Delivery in 3-5 Working Days)) " + price_currency + " 10.00");
//                                    } else {
                                    extra_amount = 0;
                                    express_carrier_charges = 0;
                                    grand_amount = Double.parseDouble(cartPOJO.getGrand_total());
                                    rb_express_carrier.setText("Express Carrier(Free Delivery in 3-5 Working Days) " + price_currency + " 00.00");
                                    tv_shipping_charges.setText("Shipping & Handling (Express Carrier(Free Delivery in 3-5 Working Days)) " + price_currency + " 00.00");
//                                    }
                                }
                            }

                            tv_grand_total.setText("GRAND TOTAL     " + price_currency + " " + grand_amount);
                        } catch (Exception e) {
                            e.printStackTrace();
                            tv_grand_total.setText("GRAND TOTAL     " + price_currency + " " + cartPOJO.getGrand_total());
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                tv_grand_total.setText("GRAND TOTAL     " + price_currency + " " + cartPOJO.getGrand_total());
            }
            string_grand_total = cartPOJO.getGrand_total();


        } else {
            finish();
        }
        Log.d(TAG, "grand total amount:-" + string_grand_total);
        rb_standard_carrier.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rb_cash_on_delivery.setVisibility(View.VISIBLE);
                } else {
                    rb_cash_on_delivery.setVisibility(View.GONE);
                    rb_online_payment.setChecked(true);
                }
            }
        });

        check_shipping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_billing_addr.setVisibility(View.GONE);
                } else {
                    ll_billing_addr.setVisibility(View.VISIBLE);
                }
            }
        });


        btn_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartCheckOutActivity.this, AddNewAddressActivity.class);
                startActivityForResult(intent, ADDRESS_INTENT);
            }
        });
        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                callOrderNoAPI();

                if (spinner_address.getSelectedItem().toString().equals("Add New Address")) {
                    Intent intent = new Intent(CartCheckOutActivity.this, AddNewAddressActivity.class);
                    startActivityForResult(intent, ADDRESS_INTENT);
                } else {
                    switch (rg_payment_method.getCheckedRadioButtonId()) {
                        case R.id.rb_online_payment:
                            Log.d(TAG, "online payment");
                            String url = "http://www.bjain.com/Paytmgetway/index.php?CUST_ID=" + System.currentTimeMillis() + "&TXN_AMOUNT=" + grand_amount + "&EMAIL=" + Pref.GetStringPref(getApplicationContext(), StringUtils.EMAIL_ID, "");
                            Intent intent = new Intent(CartCheckOutActivity.this, OnlinePaymentActivity.class);
                            intent.putExtra("url", url);
                            startActivity(intent);
                            break;
                        case R.id.rb_cash_on_delivery:
                            Log.d(TAG, "cash on delivery");
                            callCashOnDeliveryAPI();
                            break;
                    }
                }

            }
        });
    }

    String base_order_no = "";

    public void callCashOnDeliveryAPI() {
        String shipping_description = "";
        switch (rg_shipping_method.getCheckedRadioButtonId()) {
            case R.id.rb_standard_carrier:
                shipping_description = "Standard Carrier - (Delivered in 12-15 Working Days)";
                break;
            case R.id.rb_express_carrier:
                shipping_description = "Express Carrier - (Delivered in 3-5 Working Days)";
                break;
        }
        Log.d(TAG, shipping_description);
        String is_virtual = "0";
        String customer_id = Pref.GetStringPref(getApplicationContext(), StringUtils.ENTITY_ID, "");
        String base_grand_total = cartPOJO.getGrand_total();
        String base_shippind_invoice = String.valueOf(extra_amount);
        String base_subtotal = cartPOJO.getGrand_total();
        String grand_total = string_grand_total;
        String shipping_amount = String.valueOf(extra_amount);
        String subtotal = cartPOJO.getGrand_total();
        String quoto_id = Pref.GetStringPref(getApplicationContext(), StringUtils.QUOTOID, "");
        String order_no = String.valueOf(System.currentTimeMillis());
        String currency = Pref.GetCurrency(getApplicationContext());
        int index = spinner_address.getSelectedItemPosition();

        AddressDataPOJO addressDataPOJO = null;
        if (addressDataPOJOList.size() > 0) {
            addressDataPOJO = addressDataPOJOList.get(index);
        }
        if (addressDataPOJO != null) {
            String region = addressDataPOJO.getState();
            String postcode = addressDataPOJO.getPost_code();
            String last_name = Pref.GetStringPref(getApplicationContext(), StringUtils.LAST_NAME, "");
            String street = addressDataPOJO.getCity();
            String city = addressDataPOJO.getCity();
            String email = Pref.GetStringPref(getApplicationContext(), StringUtils.EMAIL_ID, "");
            String telephone = addressDataPOJO.getPhone_no();
            String country_id = addressDataPOJO.getCountry_code();
            String first_name = Pref.GetStringPref(getApplicationContext(), StringUtils.FIRST_NAME, "");
            String middle_name = Pref.GetStringPref(getApplicationContext(), StringUtils.MIDDLE_NAME, "");
            String product_id = "";
            String sku = "";
            String name = "";
            String quantity = "";
            String price = "";
            String row_total = "";

            List<CartDataPOJO> cartDataPOJOList = cartPOJO.getCartDataPOJOList();
            for (int i = 0; i < cartDataPOJOList.size(); i++) {
                if ((i + 1) == cartDataPOJOList.size()) {
                    product_id = product_id + cartDataPOJOList.get(i).getProduct_id();
                    sku = sku + cartDataPOJOList.get(i).getProduct_sku();
                    name = name + cartDataPOJOList.get(i).getProduct_name();
                    quantity = quantity + cartDataPOJOList.get(i).getQuantity();
                    price = price + cartDataPOJOList.get(i).getRow_price();
                    row_total = row_total + (getDoubleValue(cartDataPOJOList.get(i).getRow_price())*
                                            getDoubleValue(cartDataPOJOList.get(i).getQuantity()));
                } else {
                    product_id = product_id + cartDataPOJOList.get(i).getProduct_id() + ",";
                    sku = sku + cartDataPOJOList.get(i).getProduct_sku() + ",";
                    name = name + cartDataPOJOList.get(i).getProduct_name() + ",";
                    quantity = quantity + cartDataPOJOList.get(i).getQuantity() + ",";
                    price = price + cartDataPOJOList.get(i).getRow_price() + ",";
                    row_total = row_total + (getDoubleValue(cartDataPOJOList.get(i).getRow_price())*
                            getDoubleValue(cartDataPOJOList.get(i).getQuantity())) + ",";
                }
            }
            Log.d("TAG", name);
            Log.d("TAG", product_id);
            Log.d("TAG", sku);
            Log.d("TAG", quantity);
            Log.d("TAG", price);
            Log.d("TAG", row_total);


            base_order_no = order_no;
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("shipping_description", shipping_description));
            nameValuePairs.add(new BasicNameValuePair("is_virtual", is_virtual));
            nameValuePairs.add(new BasicNameValuePair("customer_id", customer_id));
            nameValuePairs.add(new BasicNameValuePair("base_grand_total", base_grand_total));
            nameValuePairs.add(new BasicNameValuePair("base_shipping_invoiced", base_shippind_invoice));
            nameValuePairs.add(new BasicNameValuePair("base_subtotal", base_subtotal));
            nameValuePairs.add(new BasicNameValuePair("grand_total", grand_total));
            nameValuePairs.add(new BasicNameValuePair("shipping_amount", shipping_amount));
            nameValuePairs.add(new BasicNameValuePair("subtotal", subtotal));
            nameValuePairs.add(new BasicNameValuePair("quote_id", quoto_id));
            nameValuePairs.add(new BasicNameValuePair("order_no", order_no));
            nameValuePairs.add(new BasicNameValuePair("currency_code", Pref.GetCurrencyINR(getApplicationContext())));
            nameValuePairs.add(new BasicNameValuePair("region", region));
            nameValuePairs.add(new BasicNameValuePair("postcode", postcode));
            nameValuePairs.add(new BasicNameValuePair("lastname", last_name));
            nameValuePairs.add(new BasicNameValuePair("street", street));
            nameValuePairs.add(new BasicNameValuePair("city", city));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("telephone", telephone));
            nameValuePairs.add(new BasicNameValuePair("country_id", country_id));
            nameValuePairs.add(new BasicNameValuePair("firstname", first_name));
//            nameValuePairs.add(new BasicNameValuePair("middlename", middle_name));
            nameValuePairs.add(new BasicNameValuePair("product_id", price));
            nameValuePairs.add(new BasicNameValuePair("sku", sku));
            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("qty_ordered", quantity));
            nameValuePairs.add(new BasicNameValuePair("price", price));
            nameValuePairs.add(new BasicNameValuePair("row_total", row_total));
            nameValuePairs.add(new BasicNameValuePair("base_grand_total", String.valueOf(grand_amount)));
            nameValuePairs.add(new BasicNameValuePair("base_total_paid", String.valueOf(grand_amount)));
            nameValuePairs.add(new BasicNameValuePair("grand_total", String.valueOf(grand_amount)));
            nameValuePairs.add(new BasicNameValuePair("total_paid", String.valueOf(grand_amount)));
            nameValuePairs.add(new BasicNameValuePair("base_currency_code", Pref.GetCurrencyINR(getApplicationContext())));
            nameValuePairs.add(new BasicNameValuePair("order_currency_code", Pref.GetCurrencyINR(getApplicationContext())));
            nameValuePairs.add(new BasicNameValuePair("shipping_name", Pref.GetStringPref(getApplicationContext(),StringUtils.FIRST_NAME,"")+" "+Pref.GetStringPref(getApplicationContext(),StringUtils.LAST_NAME,"")));
            nameValuePairs.add(new BasicNameValuePair("billing_name", Pref.GetStringPref(getApplicationContext(),StringUtils.FIRST_NAME,"")+" "+Pref.GetStringPref(getApplicationContext(),StringUtils.LAST_NAME,"")));
            nameValuePairs.add(new BasicNameValuePair("status", "processing"));
            nameValuePairs.add(new BasicNameValuePair("base_price", price));
            nameValuePairs.add(new BasicNameValuePair("original_price", price));
            nameValuePairs.add(new BasicNameValuePair("base_original_price", price));
            nameValuePairs.add(new BasicNameValuePair("base_row_tota", price));
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=new Date();
            nameValuePairs.add(new BasicNameValuePair("dates", sdf.format(date)));
            nameValuePairs.add(new BasicNameValuePair("customer_firstname", Pref.GetStringPref(getApplicationContext(),StringUtils.FIRST_NAME,"")));
            nameValuePairs.add(new BasicNameValuePair("customer_lastname", Pref.GetStringPref(getApplicationContext(),StringUtils.LAST_NAME,"")));
//            nameValuePairs.add(new BasicNameValuePair("customer_middlename", Pref.GetStringPref(getApplicationContext(),StringUtils.MIDDLE_NAME,"")));
            new WebServiceBase(nameValuePairs, this, CASH_ON_DELIVERY_API).execute(WebServicesUrls.CASH_ON_DELIVERY_API);
        } else {
            ToastClass.showShortToast(getApplicationContext(), "Please Select Address First");
        }
    }

    private double getDoubleValue(String value){
        try{
            return Double.parseDouble(value);
        }catch (Exception e){
            e.printStackTrace();
            return 0.00;
        }
    }

    public void callOrderNoAPI() {
//        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        new WebServiceBase(nameValuePairs, this, CALL_GATEWAY_API).execute(WebServicesUrls.ORDER_NO_URL);
    }

    public void inflateCartItems(List<CartDataPOJO> cartPOJOs) {
        ll_scroll_orders.removeAllViews();
        for (int i = 0; i < cartPOJOs.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_cart_items, null);

            Log.d(TagUtils.getTag(), "cartdata pojo:-" + cartPOJOs.get(i));

            TextView tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            TextView tv_qty = (TextView) view.findViewById(R.id.tv_qty);
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);

            tv_product_name.setText(cartPOJOs.get(i).getProduct_name());
            tv_qty.setText(getConvertedPrice(cartPOJOs.get(i).getQuantity()));
            tv_price.setText(price_currency + " " + getConvertedPrice(cartPOJOs.get(i).getRow_price()));


            ll_scroll_orders.addView(view);
        }
    }

    public String getConvertedPrice(String price) {
        try {
            double val = Double.parseDouble(price);
            DecimalFormat f = new DecimalFormat("##.00");
            return String.valueOf(f.format(val));
        } catch (Exception e) {
            e.printStackTrace();
            return price;
        }
    }

    public void callGetAddressAPI() {
        Log.d(TAG, "calling get address api");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Pref.GetStringPref(getApplicationContext(), StringUtils.ENTITY_ID, "")));
        new WebServiceBase(nameValuePairs, this, GET_ADDRESS_API).execute(WebServicesUrls.GET_ADDRESS_URL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_ADDRESS_API:
                parseGetAddressAPI(response);
                break;
            case CASH_ON_DELIVERY_API:
                parseCashOnDeliveryResponse(response);
                break;
        }
    }


    public void parseCashOnDeliveryResponse(String response) {
        Log.d(TAG, "cash on delivery response:-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                databaseHelper.deleteAllCartItems();
//                ToastClass.showShortToast(getApplicationContext(),"Order Placed Successfully");
                showOrderDialog(base_order_no);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(), "Something went wrong");
        }
    }

    public void showOrderDialog(String order_no) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                CartCheckOutActivity.this);
        alertDialogBuilder.setTitle("Order Placed");
        alertDialogBuilder
                .setMessage("Your Order has been Placed Successfully and your order id is " + order_no)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(CartCheckOutActivity.this, HomeActivity.class));
                        finishAffinity();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    public void parseGetAddressAPI(String response) {
        Log.d(TAG, "get address response:-" + response);

        addressDataPOJOList.clear();
        try {
            Gson gson = new Gson();
            AddressPOJO addressPOJO = gson.fromJson(response, AddressPOJO.class);
            if (addressPOJO.getSuccess().equals("true")) {
                List<AddressResultPOJO> addressResultPOJOList = addressPOJO.getAddressResultPOJOList();
                Set<String> entity_ids = new HashSet<>();
                for (AddressResultPOJO addressResultPOJO : addressResultPOJOList) {
                    entity_ids.add(addressResultPOJO.getEntity_id());
                }

                for (String s : entity_ids) {
                    AddressDataPOJO addressDataPOJO = new AddressDataPOJO();
                    for (AddressResultPOJO addressResultPOJO : addressResultPOJOList) {
                        if (addressResultPOJO.getEntity_id().equals(s)) {
                            switch (addressResultPOJO.getAttribute_id()) {
                                case "20":
                                    addressDataPOJO.setFirst_name(addressResultPOJO.getValue());
                                    break;
                                case "22":
                                    addressDataPOJO.setLast_name(addressResultPOJO.getValue());
                                    break;
                                case "26":
                                    addressDataPOJO.setCity(addressResultPOJO.getValue());
                                    break;
                                case "27":
                                    addressDataPOJO.setCountry_code(addressResultPOJO.getValue());
                                    break;
                                case "28":
                                    addressDataPOJO.setState(addressResultPOJO.getValue());
                                    break;
                                case "30":
                                    addressDataPOJO.setPost_code(addressResultPOJO.getValue());
                                    break;
                                case "31":
                                    addressDataPOJO.setPhone_no(addressResultPOJO.getValue());
                                    break;
                            }
                        }
                    }
                    addressDataPOJOList.add(addressDataPOJO);
                }
                List<String> list = new ArrayList<>();
                for (AddressDataPOJO addressResultPOJO : addressDataPOJOList) {
                    list.add(addressResultPOJO.getFirst_name() + " "
                            + addressResultPOJO.getLast_name() + ", "
                            + addressResultPOJO.getCity() + ", "
                            + addressResultPOJO.getCountry_code() + ", "
                            + addressResultPOJO.getState() + ", "
                            + addressResultPOJO.getPost_code() + ", "
                            + addressResultPOJO.getPhone_no()
                    );
                }


                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_billing_address.setAdapter(spinnerArrayAdapter);

                ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list); //selected item will look like a spinner set from XML
                spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_address.setAdapter(spinnerArrayAdapter2);

            } else {
                ToastClass.showShortToast(getApplicationContext(), "No adress Found");
                emptyspinner();
            }
        } catch (Exception e) {
            e.printStackTrace();
            emptyspinner();
        }
    }

    public void emptyspinner() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Add New Address");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_billing_address.setAdapter(spinnerArrayAdapter);

        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_address.setAdapter(spinnerArrayAdapter2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADDRESS_INTENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                callGetAddressAPI();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
