package com.bjain.pegasus.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjain.pegasus.R;
import com.bjain.pegasus.pojo.orderlist.OrderListPOJO;
import com.bjain.pegasus.pojo.orderlist.OrderListResultPOJO;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListActivity extends AppCompatActivity implements WebServicesCallBack {

    private final String TAG = getClass().getSimpleName();
    private final String ORDER_LIST_API = "order_list_api";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Orders");
    }

    @Override
    protected void onResume() {
        super.onResume();
        callOrderListAPI();
    }

    public void callOrderListAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Pref.GetStringPref(getApplicationContext(), StringUtils.ENTITY_ID, "")));
        new WebServiceBase(nameValuePairs, this, ORDER_LIST_API).execute(WebServicesUrls.ORDER_LIST_URL);
//            parseOrderListResponse(Pref.orders);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case ORDER_LIST_API:
                parseOrderListResponse(response);
                break;
        }
    }

    public void parseOrderListResponse(String response) {
        Log.d(TAG,"response:-"+response);
//        try {
//            JSONArray jsonArray = new JSONArray(response);
//            if (jsonArray.length() > 0) {
//                Toast.makeText(getApplicationContext(), "Total Orders:-" + jsonArray.length(), Toast.LENGTH_LONG).show();
////                Gson gson = new Gson();
//                Type listType = new TypeToken<List<OrderPOJOResult>>() {
//                }.getType();
//                List<OrderPOJOResult> orderPOJOResultList = new Gson().fromJson(response, listType);
//                Log.d(TAG, "orderPOJOResultList:-" + orderPOJOResultList.toString());
//                inflateOrders(orderPOJOResultList);
//            } else {
//                Toast.makeText(getApplicationContext(), "No Orders Found", Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
//        }
        try{
            Gson gson=new Gson();
            OrderListPOJO orderListPOJO=gson.fromJson(response,OrderListPOJO.class);
            List<OrderListResultPOJO> orderListResultPOJOList=orderListPOJO.getOrderListResultPOJOList();
            inflateOrders(orderListResultPOJOList);
        }catch (Exception e){
            ToastClass.showShortToast(getApplicationContext(),"No Orders Found");
            e.printStackTrace();
        }
    }

    public void inflateOrders(List<OrderListResultPOJO> orderListResultPOJOList) {
        ll_scroll.removeAllViews();
        for (int i = 0; i < orderListResultPOJOList.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_orders_list, null);
            TextView tv_order_no = (TextView) view.findViewById(R.id.tv_order_no);
            TextView tv_date = (TextView) view.findViewById(R.id.tv_date);
            TextView tv_ship_to = (TextView) view.findViewById(R.id.tv_ship_to);
            TextView tv_view_order = (TextView) view.findViewById(R.id.tv_view_order);
            TextView tv_reorder = (TextView) view.findViewById(R.id.tv_reorder);
            TextView tv_order_total = (TextView) view.findViewById(R.id.tv_order_total);
            TextView tv_status = (TextView) view.findViewById(R.id.tv_status);


            final OrderListResultPOJO orderPOJOResult = orderListResultPOJOList.get(i);
            tv_order_no.setText(orderPOJOResult.getIncrement_id());
            tv_date.setText(orderPOJOResult.getCreated_at());
            tv_ship_to.setText(orderPOJOResult.getFirstname()+" "+orderPOJOResult.getLastname());
            tv_order_total.setText(orderPOJOResult.getGrand_total() + " " + orderPOJOResult.getOrder_currency_code());
            tv_status.setText(orderPOJOResult.getStatus());


            tv_view_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(OrderListActivity.this, OrderDetailActivity.class);
                    intent.putExtra("order_id", orderPOJOResult.getIncrement_id());
                    startActivity(intent);
                }
            });


            ll_scroll.addView(view);
        }
    }
}
