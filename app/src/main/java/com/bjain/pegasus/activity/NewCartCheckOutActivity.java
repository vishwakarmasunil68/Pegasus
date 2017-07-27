package com.bjain.pegasus.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjain.pegasus.R;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewCartCheckOutActivity extends AppCompatActivity implements WebServicesCallBack{

    private static final String GET_ADDRESS_API = "get_address_api";
    private final String TAG=getClass().getSimpleName();

    MainCartPOJO mainCartPOJO;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_change_add_addr)
    Button btn_change_add_addr;
    @BindView(R.id.ll_name_addr)
    LinearLayout ll_name_addr;
    @BindView(R.id.tv_no_addr_found)
    TextView tv_no_addr_found;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.ll_scroll_orders)
    LinearLayout ll_scroll_orders;
    @BindView(R.id.btn_continue)
    Button btn_continue;
    @BindView(R.id.tv_total)
    TextView tv_total;

    String price_currency = "INR";
    AddressDataPOJO addressDataPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cart_check_out);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cart Checkout");

        price_currency=Pref.GetCurrency(getApplicationContext());
        mainCartPOJO= (MainCartPOJO) getIntent().getSerializableExtra("cartpojo");

        if(mainCartPOJO!=null){
            inflateCartItems(mainCartPOJO.getCartDataPOJOList());
            tv_total.setText("Total : "+Pref.GetCurrency(getApplicationContext())+" "+mainCartPOJO.getGrand_total());
            btn_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(addressDataPOJO!=null){
                        Intent intent=new Intent(NewCartCheckOutActivity.this,NewCartPlaceOrderActivity.class);
                        intent.putExtra("cartpojo",mainCartPOJO);
                        intent.putExtra("addresspojo",addressDataPOJO);
                        startActivity(intent);
                    }else{
                        ToastClass.showShortToast(getApplicationContext(),"Please Add Adress.");
                    }
                }
            });
        }else{
            finish();
        }

        btn_change_add_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewCartCheckOutActivity.this, ChangeAddAdressActivity.class);
                startActivityForResult(i, 1);
            }
        });
        callGetAddressAPI();
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
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case GET_ADDRESS_API:
                parseGetAddressAPI(response);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                AddressDataPOJO address_result= (AddressDataPOJO) data.getSerializableExtra("address");
                setSelectedAddress(address_result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    List<AddressDataPOJO> addressDataPOJOList = new ArrayList<>();
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
                ll_name_addr.setVisibility(View.VISIBLE);
                tv_no_addr_found.setVisibility(View.GONE);
                setSelectedAddress(addressDataPOJOList.get(0));

            } else {
                ll_name_addr.setVisibility(View.GONE);
                tv_no_addr_found.setVisibility(View.VISIBLE);
                addressDataPOJO=null;
//                ToastClass.showShortToast(getApplicationContext(), "No adress Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addressDataPOJO=null;
            ll_name_addr.setVisibility(View.GONE);
            tv_no_addr_found.setVisibility(View.VISIBLE);
        }
    }


    public void setSelectedAddress(AddressDataPOJO addressDataPOJO){
        this.addressDataPOJO=addressDataPOJO;
        tv_name.setText(addressDataPOJO.getFirst_name()+" "+addressDataPOJO.getLast_name());
        String addr=addressDataPOJO.getCity() + ", "
                + addressDataPOJO.getState() + ", "
                + addressDataPOJO.getCountry_code() + " - "
                + addressDataPOJO.getPost_code() + ", ";
        tv_address.setText(addr);
        tv_phone.setText(String.valueOf(addressDataPOJO.getPhone_no()));
    }
}
