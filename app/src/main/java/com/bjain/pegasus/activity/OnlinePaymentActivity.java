package com.bjain.pegasus.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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

public class OnlinePaymentActivity extends AppCompatActivity implements WebServicesCallBack{
    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.webview)
    WebView webView;
    String carrier_type="",extra_amount="",grand_amount="",string_grand_total="";
    MainCartPOJO cartPOJO;
    AddressDataPOJO addressDataPOJO;
    private final String CASH_ON_DELIVERY_API="cash_on_delivery_api";
    private static final String MAIL_API = "mail_api";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        cartPOJO= (MainCartPOJO) getIntent().getSerializableExtra("cartPOJO");
        addressDataPOJO= (AddressDataPOJO) getIntent().getSerializableExtra("addressDataPOJO");
        if (bundle != null) {

            String url = bundle.getString("url");
            carrier_type=bundle.getString("carrier_type");
            extra_amount=bundle.getString("extra_amount");
            grand_amount=bundle.getString("grand_amount");
            string_grand_total=bundle.getString("string_grand_total");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
//            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webView.getSettings().setSupportMultipleWindows(true);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webView.setWebViewClient(new AppWebViewClients(this));
            Log.d(TagUtils.getTag(),"url:-"+url);
            webView.loadUrl(url);

//            webView.loadUrl(url);
//            webView.loadData(webdata, "text/html", "UTF-8");
//            callCashOnDeliveryAPI(addressDataPOJO);
        } else {
            finish();
        }

//        webView.setWebViewClient(new AxppWebViewClients(this));

    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
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
        startActivity(new Intent(OnlinePaymentActivity.this, HomeActivity.class));
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
                OnlinePaymentActivity.this);
        alertDialogBuilder.setTitle("Order Placed");
        alertDialogBuilder
                .setMessage("Your Order has been Placed Successfully and your order id is " + order_no)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
//                        startActivity(new Intent(OnlinePaymentActivity.this,HomeActivity.class));
//                        finishAffinity();
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
        nameValuePairs.add(new BasicNameValuePair("payment_method", "secureebs_standard"));

        new WebServiceBase(nameValuePairs, this, MAIL_API).execute(WebServicesUrls.MAIL_API);
    }

    public class AppWebViewClients extends WebViewClient {
        private ProgressDialog progressDialog;
        Activity activity;

        public AppWebViewClients(Activity activity) {
            this.activity = activity;
//            progressDialog = new ProgressDialog(activity);
//            progressDialog.setMessage("Please Wait...");
//            progressDialog.setCancelable(true);
//            progressDialog.show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            Log.d(TAG, "url:-" + url);
            view.loadUrl(url);
            if(url.contains("Paytmgetway/success.php")){
                Log.d(TagUtils.getTag(),"getting pgs response");
                callCashOnDeliveryAPI(addressDataPOJO);
            }else{
                if(url.contains("Paytmgetway/fail.php")){
                    ToastClass.showLongToast(getApplicationContext(),"Payment Failed");
                    startActivity(new Intent(OnlinePaymentActivity.this, HomeActivity.class));
                    finishAffinity();
                }
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
//                view.loadUrl(url);

            Log.d(TAG, "done loading");

        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                super.onBackPressed();
            }
        }
    }
    String mail_currency_code="",mail_shipping_name="",mail_street="",mail_region="",mail_postcode="",
            mail_telephone="",mail_email="",mail_name="",mail_qty_ordered="",mail_sku="",mail_row_total="",
            mail_subtotal="",mail_shipping_handling="",mail_dates="",base_order_no="";

    public void callCashOnDeliveryAPI(AddressDataPOJO addressDataPOJO) {
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

            mail_shipping_name=addressDataPOJO.getFirst_name()+" "+addressDataPOJO.getLast_name();
            mail_street=addressDataPOJO.getCity();
            mail_region=addressDataPOJO.getState();
            mail_postcode=addressDataPOJO.getPost_code();
            mail_telephone=addressDataPOJO.getPhone_no();

            String region = addressDataPOJO.getState();
            String postcode = addressDataPOJO.getPost_code();
            String last_name = Pref.GetStringPref(getApplicationContext(), StringUtils.LAST_NAME, "");
            String street = addressDataPOJO.getCity();
            String city = addressDataPOJO.getCity();
            String email = Pref.GetStringPref(getApplicationContext(), StringUtils.EMAIL_ID, "");
            mail_email=email;
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
            mail_name=name;
            mail_sku=sku;
            mail_qty_ordered=quantity;
            mail_row_total=row_total;

            base_order_no = order_no;
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("payment_method", "paytm_cc"));
            nameValuePairs.add(new BasicNameValuePair("shipping_description", carrier_type));
            nameValuePairs.add(new BasicNameValuePair("is_virtual", is_virtual));
            nameValuePairs.add(new BasicNameValuePair("customer_id", customer_id));
            nameValuePairs.add(new BasicNameValuePair("base_grand_total", base_grand_total));
            nameValuePairs.add(new BasicNameValuePair("base_shipping_invoiced", base_shippind_invoice));
            nameValuePairs.add(new BasicNameValuePair("base_subtotal", base_subtotal));
            nameValuePairs.add(new BasicNameValuePair("grand_total", grand_total));
            nameValuePairs.add(new BasicNameValuePair("shipping_amount", shipping_amount));
            nameValuePairs.add(new BasicNameValuePair("subtotal", subtotal));
            mail_subtotal=subtotal;
            mail_shipping_handling=String.valueOf(extra_amount);
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
            nameValuePairs.add(new BasicNameValuePair("shipping_name", Pref.GetStringPref(getApplicationContext(), StringUtils.FIRST_NAME,"")+" "+Pref.GetStringPref(getApplicationContext(),StringUtils.LAST_NAME,"")));
            nameValuePairs.add(new BasicNameValuePair("billing_name", Pref.GetStringPref(getApplicationContext(),StringUtils.FIRST_NAME,"")+" "+Pref.GetStringPref(getApplicationContext(),StringUtils.LAST_NAME,"")));
            nameValuePairs.add(new BasicNameValuePair("status", "processing"));
            nameValuePairs.add(new BasicNameValuePair("base_price", price));
            nameValuePairs.add(new BasicNameValuePair("original_price", price));
            nameValuePairs.add(new BasicNameValuePair("base_original_price", price));
            nameValuePairs.add(new BasicNameValuePair("base_row_tota", price));
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=new Date();
            mail_currency_code=Pref.GetCurrencyINR(getApplicationContext());
            mail_dates=sdf.format(date);
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


}
