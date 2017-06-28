package com.bjain.pegasus.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjain.pegasus.R;
import com.bjain.pegasus.activity.CartCheckOutActivity;
import com.bjain.pegasus.activity.HomeActivity;
import com.bjain.pegasus.database.DatabaseHelper;
import com.bjain.pegasus.pojo.cart.CartAttrPOJO;
import com.bjain.pegasus.pojo.cart.CartDataPOJO;
import com.bjain.pegasus.pojo.cart.CartPOJO;
import com.bjain.pegasus.pojo.cart.MainCartPOJO;
import com.bjain.pegasus.utils.Pref;
import com.bjain.pegasus.utils.StringUtils;
import com.bjain.pegasus.utils.ToastClass;
import com.bjain.pegasus.webservice.WebServiceBase;
import com.bjain.pegasus.webservice.WebServicesCallBack;
import com.bjain.pegasus.webservice.WebServicesUrls;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bjain.pegasus.webservice.WebServicesUrls.DELETE_FROM_CART;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by sunil on 05-06-2017.
 */

public class CartFragment extends Fragment implements WebServicesCallBack {

    private static final String CART_ITEM_API = "cart_item_api";
    private static final String DELETE_FROM_CART_API = "delete_from_cart_api";
    private static final String UPDATE_CART_ITEM_API = "update_cart_item_api";
    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_cart_sub_total)
    TextView tv_cart_sub_total;
    @BindView(R.id.btn_checkout)
    Button btn_checkout;
    MainCartPOJO mainCartPOJO = new MainCartPOJO();

    DatabaseHelper databaseHelper;
    List<CartDataPOJO> cartDataPOJOList = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Typeface tf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cart, container, false);
        ButterKnife.bind(this, view);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Medium.ttf");
        databaseHelper = new DatabaseHelper(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callCartAPI();

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mainCartPOJO != null && mainCartPOJO.getCartDataPOJOList().size() > 0) {
                    Intent intent = new Intent(getActivity(), CartCheckOutActivity.class);
                    intent.putExtra("cartpojo", mainCartPOJO);
                    startActivity(intent);
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "Please Add Items to cart First");
                }
            }
        });
    }


    public void callCartAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("quote_id", Pref.GetStringPref(getApplicationContext(), StringUtils.QUOTOID, "")));
        new WebServiceBase(nameValuePairs, getActivity(), this, CART_ITEM_API).execute(WebServicesUrls.GET_ALL_CART_DATA_URL);
    }


    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case CART_ITEM_API:
                parseCartItemsResponse(response);
                break;
            case DELETE_FROM_CART_API:
                parsedeleteFromCartResponse(response);
                break;
            case UPDATE_CART_ITEM_API:
                parseUpdateCartItemAPI(response);
                break;
        }
    }

    public void parseUpdateCartItemAPI(String response) {
        Log.d(TAG, "update response:-" + response);
        ToastClass.showShortToast(getActivity().getApplicationContext(), "Item Updated");
        callCartAPI();
    }

    public void parsedeleteFromCartResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                databaseHelper.deleteCartData(jsonObject.optString("cart_id"));
                callCartAPI();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseCartItemsResponse(String response) {
        Log.d(TAG, "cart response:-" + response);
        try {
            Gson gson = new Gson();
            CartPOJO cartPOJO = gson.fromJson(response, CartPOJO.class);
            if (cartPOJO.getSuccess().equals("true")) {
                String grand_total = cartPOJO.getGrand_total();
                mainCartPOJO.setGrand_total(grand_total);
                tv_cart_sub_total.setText("GRAND TOTAL :- "+Pref.GetCurrency(getApplicationContext())+" "+ grand_total);
                List<CartAttrPOJO> cartAttrPOJOList = cartPOJO.getCartAttrPOJOList();
                Set<String> stringSet = new HashSet<>();

                for (CartAttrPOJO cartAttrPOJO : cartAttrPOJOList) {
                    stringSet.add(cartAttrPOJO.getProductId());
                }
                cartDataPOJOList.clear();
                for (String product_ids : stringSet) {
                    CartDataPOJO cartDataPOJO = new CartDataPOJO();
                    for (CartAttrPOJO cartAttrPOJO : cartAttrPOJOList) {
                        if (cartAttrPOJO.getProductId().equals(product_ids)) {
                            switch (cartAttrPOJO.getAttributeId()) {
                                case "71":
                                    cartDataPOJO.setCart_id(cartAttrPOJO.getItemId());
                                    cartDataPOJO.setProduct_id(cartAttrPOJO.getProductId());
                                    cartDataPOJO.setProduct_name(cartAttrPOJO.getValue());
                                    cartDataPOJO.setQuantity(cartAttrPOJO.getQty());
                                    cartDataPOJO.setProduct_sku(cartAttrPOJO.getSku());
                                    cartDataPOJO.setProduct_price(cartAttrPOJO.getPrice());
                                    cartDataPOJO.setRow_price(cartAttrPOJO.getBasePrice());
                                    break;
                                case "85":
                                    cartDataPOJO.setProduct_image(cartAttrPOJO.getValue());
                                    break;
                            }
                        }
                    }
                    cartDataPOJOList.add(cartDataPOJO);
                }
                mainCartPOJO.setCartDataPOJOList(cartDataPOJOList);
                inflateCartItems(cartDataPOJOList);


            } else {
                ToastClass.showShortToast(getApplicationContext(), "Cart is Empty");
                ll_scroll.removeAllViews();
                tv_cart_sub_total.setText("");
                databaseHelper.deleteAllCartItems();
            }


        } catch (Exception e) {
            e.printStackTrace();
            ll_scroll.removeAllViews();
            tv_cart_sub_total.setText("");
            databaseHelper.deleteAllCartItems();
            ToastClass.showShortToast(getApplicationContext(), "Something went wrong");
        }
    }

    public void callRemoveFromCartAPI(String cart_id, String product_id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("cart_id", cart_id));
        nameValuePairs.add(new BasicNameValuePair("product_id", product_id));
        new WebServiceBase(nameValuePairs, getActivity(), this, DELETE_FROM_CART_API).execute(DELETE_FROM_CART);
    }

    public void inflateCartItems(final List<CartDataPOJO> cartDataPOJOList) {
        ll_scroll.removeAllViews();
        databaseHelper.deleteAllCartItems();
        for (int i = 0; i < cartDataPOJOList.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_cart_list, null);

            databaseHelper.insertCartData(cartDataPOJOList.get(i));
            ImageView img_book = (ImageView) view.findViewById(R.id.img_book);
            TextView tv_book_name = (TextView) view.findViewById(R.id.tv_book_name);
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
            TextView tv_sku = (TextView) view.findViewById(R.id.tv_sku);
            ImageView iv_decrease_product = (ImageView) view.findViewById(R.id.iv_decrease_product);
            ImageView iv_delete_item = (ImageView) view.findViewById(R.id.iv_delete_item);
            final TextView tv_quant = (TextView) view.findViewById(R.id.tv_quant);
            ImageView iv_increase_product = (ImageView) view.findViewById(R.id.iv_increase_product);
            Button btn_update = (Button) view.findViewById(R.id.btn_update);


            final LinearLayout ll_cart_item = (LinearLayout) view.findViewById(R.id.ll_cart_item);

            Log.d(TAG, "image url:-" + WebServicesUrls.IMAGE_BASE_URL + cartDataPOJOList.get(i).getProduct_image());
            Glide.with(getApplicationContext())
                    .load(WebServicesUrls.IMAGE_BASE_URL + cartDataPOJOList.get(i).getProduct_image())
                    .into(img_book);
            tv_price.setText(Pref.GetCurrency(getApplicationContext()) + " " + getConvertedPrice(cartDataPOJOList.get(i).getRow_price()));
            tv_book_name.setText(cartDataPOJOList.get(i).getProduct_name());
            tv_sku.setText("ISBN : "+cartDataPOJOList.get(i).getProduct_sku());
            tv_quant.setText(String.valueOf(converttoInt(cartDataPOJOList.get(i).getQuantity())));
            iv_decrease_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        int val = converttoInt(tv_quant.getText().toString());
                        Log.d(TAG, "increase value:-" + val);
                        if (val == 0) {

                        } else {
                            val = val - 1;
                            tv_quant.setText(String.valueOf(val));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            tv_book_name.setTypeface(tf);

            final int finalI = i;
            iv_delete_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callRemoveFromCartAPI(cartDataPOJOList.get(finalI).getCart_id(), cartDataPOJOList.get(finalI).getProduct_id());
                }
            });

            iv_increase_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        int val = converttoInt(tv_quant.getText().toString());
                        val = val + 1;
                        Log.d(TAG, "increase value:-" + val);
                        tv_quant.setText(String.valueOf(val));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            ll_cart_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    homeActivity.showProductViewFragment(cartDataPOJOList.get(finalI).getProduct_id());
                }
            });

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateCartItem(cartDataPOJOList.get(finalI).getCart_id(),
                            Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.QUOTOID, ""),
                            tv_quant.getText().toString());
                }
            });


            ll_scroll.addView(view);
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

    public void updateCartItem(String cart_id, String quote_id, String product_qty) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("cart_id", cart_id));
        nameValuePairs.add(new BasicNameValuePair("quote_id", quote_id));
        nameValuePairs.add(new BasicNameValuePair("product_qty", product_qty));
        new WebServiceBase(nameValuePairs, getActivity(), this, UPDATE_CART_ITEM_API).execute(WebServicesUrls.UPDATE_CART_ITEM);
    }

    public int converttoInt(String str) {
        try {
            double num = Double.parseDouble(str);
            return (int) num;
        } catch (Exception e) {
            return 0;
        }
    }


}
