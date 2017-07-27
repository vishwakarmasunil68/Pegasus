package com.bjain.pegasus.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bjain.pegasus.R;
import com.bjain.pegasus.pojo.address.AddressDataPOJO;
import com.bjain.pegasus.pojo.address.AddressPOJO;
import com.bjain.pegasus.pojo.address.AddressResultPOJO;
import com.bjain.pegasus.utils.Pref;
import com.bjain.pegasus.utils.StringUtils;
import com.bjain.pegasus.utils.ToastClass;
import com.bjain.pegasus.webservice.WebServiceBase;
import com.bjain.pegasus.webservice.WebServicesCallBack;
import com.bjain.pegasus.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeAddAdressActivity extends AppCompatActivity implements WebServicesCallBack{


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cv_add_address)
    CardView cv_add_address;
    @BindView(R.id.ll_addr_scroll)
    LinearLayout ll_addr_scroll;
    @BindView(R.id.btn_deliver)
    Button btn_deliver;

    private static final String GET_ADDRESS_API = "get_address_api";
    private final String TAG=getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_add_adress);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cart Checkout");

        cv_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChangeAddAdressActivity.this,AddNewAddressActivity.class);
                startActivityForResult(intent,1);
            }
        });

        btn_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addressDataPOJOList.size()>0){
                    if(addrIndex!=-1){
                        AddressDataPOJO addressDataPOJO;
                        try{
                            addressDataPOJO=addressDataPOJOList.get(addrIndex);
                        }catch (Exception e){
                            addressDataPOJO=addressDataPOJOList.get(0);
                            e.printStackTrace();
                        }

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("address",addressDataPOJO);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }else{
                        ToastClass.showShortToast(getApplicationContext(),"Please Select Address");
                    }
                }else{
                    ToastClass.showShortToast(getApplicationContext(),"Please Add Address");
                }
            }
        });
        callGetAddressAPI();
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
    protected void onResume() {
        super.onResume();

    }

    public void callGetAddressAPI() {
        addrIndex=-1;
        Log.d(TAG, "calling get address api");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Pref.GetStringPref(getApplicationContext(), StringUtils.ENTITY_ID, "")));
        new WebServiceBase(nameValuePairs, this, GET_ADDRESS_API).execute(WebServicesUrls.GET_ADDRESS_URL);
    }


    List<AddressDataPOJO> addressDataPOJOList=new ArrayList<>();
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
                inflateAddress(addressDataPOJOList);

            } else {
                ToastClass.showShortToast(getApplicationContext(), "No adress Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    List<RadioButton> radioButtonList=new ArrayList<>();
    int addrIndex=-1;
    public void inflateAddress(List<AddressDataPOJO> addressDataPOJOList){
        ll_addr_scroll.removeAllViews();
        radioButtonList.clear();
        for(int i=0;i<addressDataPOJOList.size();i++){
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_addresses, null);

            LinearLayout ll_address= (LinearLayout) view.findViewById(R.id.ll_address);
            final RadioButton radio_addr= (RadioButton) view.findViewById(R.id.radio_addr);
            TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
            TextView tv_address= (TextView) view.findViewById(R.id.tv_address);
            TextView tv_phone= (TextView) view.findViewById(R.id.tv_phone);

            AddressDataPOJO addressDataPOJO=addressDataPOJOList.get(i);

            tv_name.setText(addressDataPOJO.getFirst_name()+" "+addressDataPOJO.getLast_name());
            tv_address.setText(addressDataPOJO.getCity()+", "+addressDataPOJO.getState()+", "+
            addressDataPOJO.getCountry_code()+" - "+addressDataPOJO.getPost_code());
            tv_phone.setText(addressDataPOJO.getPhone_no());

            radioButtonList.add(radio_addr);
            final int finalI = i;
            ll_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addrIndex= finalI;
                    for(RadioButton radioButton:radioButtonList){
                        radioButton.setChecked(false);
                    }
                    radio_addr.setChecked(true);
                }
            });
            radio_addr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addrIndex= finalI;
                    for(RadioButton radioButton:radioButtonList){
                        radioButton.setChecked(false);
                    }
                    radio_addr.setChecked(true);
                }
            });

            ll_addr_scroll.addView(view);
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                callGetAddressAPI();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_ADDRESS_API:
                parseGetAddressAPI(response);
                break;
        }
    }
}
