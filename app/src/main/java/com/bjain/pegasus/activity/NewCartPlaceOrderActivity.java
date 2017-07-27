package com.bjain.pegasus.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bjain.pegasus.R;
import com.bjain.pegasus.database.DatabaseHelper;
import com.bjain.pegasus.pojo.address.AddressDataPOJO;
import com.bjain.pegasus.pojo.cart.CartDataPOJO;
import com.bjain.pegasus.pojo.cart.MainCartPOJO;
import com.bjain.pegasus.utils.Pref;
import com.bjain.pegasus.utils.StringUtils;
import com.bjain.pegasus.utils.TagUtils;
import com.bjain.pegasus.utils.ToastClass;
import com.bjain.pegasus.webservice.WebServiceBase;
import com.bjain.pegasus.webservice.WebServicesCallBack;
import com.bjain.pegasus.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewCartPlaceOrderActivity extends AppCompatActivity implements WebServicesCallBack {

    private static final String MAIL_API = "mail_api";
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.tv_grand_total)
    TextView tv_grand_total;

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
    @BindView(R.id.btn_place_order)
    Button btn_place_order;


    AddressDataPOJO addressDataPOJO;


    double grand_amount = 0.00;
    double standard_carrier_charges = 0.0;
    double express_carrier_charges = 0.0;
    double extra_amount = 0.00;
    MainCartPOJO cartPOJO;
    String string_grand_total = "";
    boolean is_online_payment = true;
    boolean is_standart_delivery = true;
    String price_currency = "INR";
    double extra_charges = 0.00;

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cart_place_order_activityy);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Payment");

        price_currency = Pref.GetCurrency(getApplicationContext());

        cartPOJO = (MainCartPOJO) getIntent().getSerializableExtra("cartpojo");
        addressDataPOJO = (AddressDataPOJO) getIntent().getSerializableExtra("addresspojo");

        if (cartPOJO != null && cartPOJO.getCartDataPOJOList().size() > 0) {

            tv_grand_total.setText("GRAND TOTAL     " + price_currency + " " + cartPOJO.getGrand_total());

            try {
                Log.d(TAG, "grand_amount:-" + cartPOJO.getGrand_total());
                grand_amount = Double.parseDouble(cartPOJO.getGrand_total());

                if (grand_amount <= 300) {
                    standard_carrier_charges = 50;
//                    grand_amount = Double.parseDouble(cartPOJO.getGrand_total()) + 50;
                    rb_standard_carrier.setText("Standard Carrier (Free delivery in 12-15 Working Days) " + price_currency + " 50.00");
//                    tv_shipping_charges.setText("Shipping & Handling (Standard Carrier (Free delivery in 12-15 Working Days)) " + price_currency + " 50.00");
                } else {
//                    grand_amount = Double.parseDouble(cartPOJO.getGrand_total());
                    standard_carrier_charges = 0;
                    rb_standard_carrier.setText("Standard Carrier (Free delivery in 12-15 Working Days) " + price_currency + " 00.00");
//                    tv_shipping_charges.setText("Shipping & Handling (Standard Carrier (Free delivery in 12-15 Working Days)) " + price_currency + " 0.00");
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
//                    tv_shipping_charges.setText("Shipping & Handling (Standard Carrier (Free delivery in 12-15 Working Days)) " + price_currency + " 50.00");
                } else {
                    extra_amount = 0;
                    standard_carrier_charges = 0;
                    grand_amount = Double.parseDouble(cartPOJO.getGrand_total());
                    rb_standard_carrier.setText("Standard Carrier (Free delivery in 12-15 Working Days) " + price_currency + " 00.00");
//                    tv_shipping_charges.setText("Shipping & Handling (Standard Carrier (Free delivery in 12-15 Working Days)) " + price_currency + ".00");
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
//                                    tv_shipping_charges.setText("Shipping & Handling (Standard Carrier (Free delivery in 12-15 Working Days)) " + price_currency + " 50.00");
                                } else {
                                    extra_amount = 0;
                                    standard_carrier_charges = 0;
                                    grand_amount = Double.parseDouble(cartPOJO.getGrand_total());
                                    rb_standard_carrier.setText("Standard Carrier (Free delivery in 12-15 Working Days) " + price_currency + " 00.00");
//                                    tv_shipping_charges.setText("Shipping & Handling (Standard Carrier (Free delivery in 12-15 Working Days)) " + price_currency + ".00");
                                }
                            } else {
                                is_standart_delivery = false;
                                if (grand_amount <= 1000) {
                                    extra_amount = 100;
                                    express_carrier_charges = 100;
                                    grand_amount = Double.parseDouble(cartPOJO.getGrand_total()) + 100;
                                    rb_express_carrier.setText("Express Carrier(Free Delivery in 3-5 Working Days) " + price_currency + " 100.00");
//                                    tv_shipping_charges.setText("Shipping & Handling (Express Carrier(Free Delivery in 3-5 Working Days)) " + price_currency + " 100.00");
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
//                                    tv_shipping_charges.setText("Shipping & Handling (Express Carrier(Free Delivery in 3-5 Working Days)) " + price_currency + " 00.00");
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
            rg_payment_method.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    if (rg_payment_method.getCheckedRadioButtonId() == R.id.rb_online_payment) {
                        btn_place_order.setText("CONTINUE");
                    } else {
                        btn_place_order.setText("PLACE ORDER");
                    }
                }
            });
            btn_place_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (rg_payment_method.getCheckedRadioButtonId()) {
                        case R.id.rb_online_payment:
                            String carrier_type = "";
                            switch (rg_shipping_method.getCheckedRadioButtonId()) {
                                case R.id.rb_standard_carrier:
                                    carrier_type = "Standard Carrier - (Delivered in 12-15 Working Days)";
                                    break;
                                case R.id.rb_express_carrier:
                                    carrier_type = "Express Carrier - (Delivered in 3-5 Working Days)";
                                    break;
                            }

                            Log.d(TAG, "online payment");
                            String url = "https://www.pegasusforkids.com/Paytmgetway/index.php?CUST_ID=" + Pref.GetStringPref(getApplicationContext(), StringUtils.ENTITY_ID, "") + "&TXN_AMOUNT=" + grand_amount + "&EMAIL=" + Pref.GetStringPref(getApplicationContext(), StringUtils.EMAIL_ID, "");
//                            String url = "https://www.pegasusforkids.com/Paytmgetway/index.php?CUST_ID=" + Pref.GetStringPref(getApplicationContext(), StringUtils.ENTITY_ID, "") + "&TXN_AMOUNT=" + cartPOJO.getGrand_total()+ "&EMAIL=" + Pref.GetStringPref(getApplicationContext(), StringUtils.EMAIL_ID, "");
                            Intent intent = new Intent(NewCartPlaceOrderActivity.this, OnlinePaymentActivity.class);
                            intent.putExtra("url", url);
                            intent.putExtra("extra_amount", String.valueOf(extra_amount));
                            intent.putExtra("carrier_type", carrier_type);
                            intent.putExtra("grand_amount", String.valueOf(grand_amount));
                            intent.putExtra("string_grand_total", string_grand_total);
                            intent.putExtra("cartPOJO", cartPOJO);
                            intent.putExtra("addressDataPOJO", addressDataPOJO);
                            startActivity(intent);
                            break;
                        case R.id.rb_cash_on_delivery:
                            Log.d(TAG, "cash on delivery");
                            callCashOnDeliveryAPI(addressDataPOJO);
                            break;
                    }
                }
            });
        } else {
            finish();
        }
    }

    String base_order_no = "";


    String mail_shipping_name = "";
    String mail_street = "";
    String mail_region = "";
    String mail_postcode = "";
    String mail_telephone = "";
    String mail_dates = "";
    String mail_subtotal = "";
    String mail_currency_code = "";
    String mail_name = "";
    String mail_sku = "";
    String mail_qty_ordered = "";
    String mail_row_total = "";
    String mail_email = "";
    String mail_shipping_handling = "";


    public void callCashOnDeliveryAPI(AddressDataPOJO addressDataPOJO) {
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

        if (addressDataPOJO != null) {

            mail_shipping_name = addressDataPOJO.getFirst_name() + " " + addressDataPOJO.getLast_name();
            mail_street = addressDataPOJO.getCity();
            mail_region = addressDataPOJO.getState();
            mail_postcode = addressDataPOJO.getPost_code();
            mail_telephone = addressDataPOJO.getPhone_no();

            String region = addressDataPOJO.getState();
            String postcode = addressDataPOJO.getPost_code();
            String last_name = Pref.GetStringPref(getApplicationContext(), StringUtils.LAST_NAME, "");
            String street = addressDataPOJO.getCity();
            String city = addressDataPOJO.getCity();
            String email = Pref.GetStringPref(getApplicationContext(), StringUtils.EMAIL_ID, "");
            mail_email = email;
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
                    row_total = row_total + (getDoubleValue(cartDataPOJOList.get(i).getRow_price()) *
                            getDoubleValue(cartDataPOJOList.get(i).getQuantity()));
                } else {
                    product_id = product_id + cartDataPOJOList.get(i).getProduct_id() + ",";
                    sku = sku + cartDataPOJOList.get(i).getProduct_sku() + ",";
                    name = name + cartDataPOJOList.get(i).getProduct_name() + ",";
                    quantity = quantity + cartDataPOJOList.get(i).getQuantity() + ",";
                    price = price + cartDataPOJOList.get(i).getRow_price() + ",";
                    row_total = row_total + (getDoubleValue(cartDataPOJOList.get(i).getRow_price()) *
                            getDoubleValue(cartDataPOJOList.get(i).getQuantity())) + ",";
                }
            }
            Log.d("TAG", name);
            Log.d("TAG", product_id);
            Log.d("TAG", sku);
            Log.d("TAG", quantity);
            Log.d("TAG", price);
            Log.d("TAG", row_total);
            mail_name = name;
            mail_sku = sku;
            mail_qty_ordered = quantity;
            mail_row_total = row_total;

            base_order_no = order_no;
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("payment_method", "cashondelivery"));
            nameValuePairs.add(new BasicNameValuePair("shipping_description", shipping_description));
            nameValuePairs.add(new BasicNameValuePair("is_virtual", is_virtual));
            nameValuePairs.add(new BasicNameValuePair("customer_id", customer_id));
            nameValuePairs.add(new BasicNameValuePair("base_grand_total", base_grand_total));
            nameValuePairs.add(new BasicNameValuePair("base_shipping_invoiced", base_shippind_invoice));
            nameValuePairs.add(new BasicNameValuePair("base_subtotal", base_subtotal));
            nameValuePairs.add(new BasicNameValuePair("grand_total", grand_total));
            nameValuePairs.add(new BasicNameValuePair("shipping_amount", shipping_amount));
            nameValuePairs.add(new BasicNameValuePair("subtotal", subtotal));
            mail_subtotal = subtotal;
            mail_shipping_handling = String.valueOf(extra_amount);
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
            nameValuePairs.add(new BasicNameValuePair("shipping_name", Pref.GetStringPref(getApplicationContext(), StringUtils.FIRST_NAME, "") + " " + Pref.GetStringPref(getApplicationContext(), StringUtils.LAST_NAME, "")));
            nameValuePairs.add(new BasicNameValuePair("billing_name", Pref.GetStringPref(getApplicationContext(), StringUtils.FIRST_NAME, "") + " " + Pref.GetStringPref(getApplicationContext(), StringUtils.LAST_NAME, "")));
            nameValuePairs.add(new BasicNameValuePair("status", "processing"));
            nameValuePairs.add(new BasicNameValuePair("base_price", price));
            nameValuePairs.add(new BasicNameValuePair("original_price", price));
            nameValuePairs.add(new BasicNameValuePair("base_original_price", price));
            nameValuePairs.add(new BasicNameValuePair("base_row_tota", price));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            mail_currency_code = Pref.GetCurrencyINR(getApplicationContext());
            mail_dates = sdf.format(date);
            nameValuePairs.add(new BasicNameValuePair("dates", sdf.format(date)));
            nameValuePairs.add(new BasicNameValuePair("customer_firstname", Pref.GetStringPref(getApplicationContext(), StringUtils.FIRST_NAME, "")));
            nameValuePairs.add(new BasicNameValuePair("customer_lastname", Pref.GetStringPref(getApplicationContext(), StringUtils.LAST_NAME, "")));
//            nameValuePairs.add(new BasicNameValuePair("customer_middlename", Pref.GetStringPref(getApplicationContext(),StringUtils.MIDDLE_NAME,"")));
            new WebServiceBase(nameValuePairs, this, CASH_ON_DELIVERY_API).execute(WebServicesUrls.CASH_ON_DELIVERY_API);
        } else {
            ToastClass.showShortToast(getApplicationContext(), "Please Select Address First");
        }
    }

    private double getDoubleValue(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.00;
        }
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

    private static final String CASH_ON_DELIVERY_API = "cash_on_delivery_api";

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {

            case CASH_ON_DELIVERY_API:
                parseCashOnDeliveryResponse(response);
                break;
            case MAIL_API:
                parseMailAPI(response);
                break;
        }
    }


    public void parseMailAPI(String response) {
        Log.d(TagUtils.getTag(), "mail response:-" + response);
        startActivity(new Intent(NewCartPlaceOrderActivity.this, HomeActivity.class));
        finishAffinity();
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

    public void showOrderDialog(final String order_no) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                NewCartPlaceOrderActivity.this);
        alertDialogBuilder.setTitle("Order Placed");
        alertDialogBuilder
                .setMessage("Your Order has been Placed Successfully and your order id is " + order_no)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        callMailAPI(order_no);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void callMailAPI(String order_no) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("shipping_name", mail_shipping_name));
        nameValuePairs.add(new BasicNameValuePair("street", mail_street));
        nameValuePairs.add(new BasicNameValuePair("region", mail_region));
        nameValuePairs.add(new BasicNameValuePair("postcode", mail_postcode));
        nameValuePairs.add(new BasicNameValuePair("telephone", mail_telephone));
        nameValuePairs.add(new BasicNameValuePair("shipping_description", "Standard Carrier - (Delivered in 12-15 Working Days)"));
        nameValuePairs.add(new BasicNameValuePair("order_no", order_no));
        nameValuePairs.add(new BasicNameValuePair("dates", mail_dates));
        nameValuePairs.add(new BasicNameValuePair("subtotal", mail_subtotal));
        nameValuePairs.add(new BasicNameValuePair("currency_code", mail_currency_code));
        nameValuePairs.add(new BasicNameValuePair("name", mail_name));
        nameValuePairs.add(new BasicNameValuePair("sku", mail_sku));
        nameValuePairs.add(new BasicNameValuePair("qty_ordered", mail_qty_ordered));
        nameValuePairs.add(new BasicNameValuePair("row_total", mail_row_total));
        nameValuePairs.add(new BasicNameValuePair("email", mail_email));
        nameValuePairs.add(new BasicNameValuePair("shipping_handling", mail_shipping_handling));
        nameValuePairs.add(new BasicNameValuePair("payment_method", "cashondelivery"));

        new WebServiceBase(nameValuePairs, this, MAIL_API).execute(WebServicesUrls.MAIL_API);
    }

}
