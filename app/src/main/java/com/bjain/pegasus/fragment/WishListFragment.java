package com.bjain.pegasus.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjain.pegasus.R;
import com.bjain.pegasus.activity.HomeActivity;
import com.bjain.pegasus.database.DatabaseHelper;
import com.bjain.pegasus.pojo.wishlist.WishListDataPOJO;
import com.bjain.pegasus.pojo.wishlist.WishListResultPOJO;
import com.bjain.pegasus.pojo.wishlist.WishlistPOJO;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by sunil on 05-06-2017.
 */

public class WishListFragment extends Fragment implements WebServicesCallBack {

    private static final String WISH_LIST_API_CALL = "wishlist_api_call";
    private static final String DELETE_FROM_WISHLIST_API = "delete_from_wishlist_api";
    private static final String UPDATE_WISLIST_DATA = "update_wishlist_data";
    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;

    DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_wish_list, container, false);
        ButterKnife.bind(this, view);
        databaseHelper = new DatabaseHelper(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callWishListAPI();
    }

    public void callWishListAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Pref.GetStringPref(getApplicationContext(), StringUtils.ENTITY_ID, "")));
        new WebServiceBase(nameValuePairs, getActivity(), this, WISH_LIST_API_CALL).execute(WebServicesUrls.WISH_LIST_URL);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case WISH_LIST_API_CALL:
                parseWishListData(response);
                break;
            case DELETE_FROM_WISHLIST_API:
                parseWishListDeleteResponse(response);
                break;
            case UPDATE_WISLIST_DATA:
                parseUpdateResponse(response);
                break;
        }
    }


    public void parseUpdateResponse(String response){
        Log.d(TAG,"update response:-"+response);
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("Success").equals("true")){
                ToastClass.showShortToast(getActivity().getApplicationContext(),"Item Updated");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void parseWishListDeleteResponse(String response) {
        Log.d(TAG, "delete wishlist response:-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.optString("success");
            if (success.equals("true")) {
                databaseHelper.deleteWishListItemByItemId(jsonObject.optString("wishlist_id"));
                callWishListAPI();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void parseWishListData(String response) {
        Log.d(TAG, "wishlist data:-" + response);
        try {
            Gson gson = new Gson();
            WishlistPOJO wishlistPOJO = gson.fromJson(response, WishlistPOJO.class);
            if (wishlistPOJO.getSuccess().equals("true")) {
//                inflateWishListData(wishlistPOJO.getWishListDataPOJOs());
                List<WishListDataPOJO> wishListDataPOJOs = wishlistPOJO.getWishListDataPOJOs();
                Set<String> wishlist_ids = new HashSet<>();
                List<WishListResultPOJO> wishListResultPOJOs = new ArrayList<>();
                for (WishListDataPOJO wishListDataPOJO : wishListDataPOJOs) {
                    wishlist_ids.add(wishListDataPOJO.getProductId());
                }


                for (String s : wishlist_ids) {

                    WishListResultPOJO wishListResultPOJO = new WishListResultPOJO();
                    wishListResultPOJO.setProduct_id(s);
                    for (WishListDataPOJO wishListDataPOJO : wishListDataPOJOs) {
                        if (s.equals(wishListDataPOJO.getProductId())) {
                            switch (wishListDataPOJO.getAttributeId()) {
                                case "71":
                                    wishListResultPOJO.setProduct_id(wishListDataPOJO.getEntityId());
                                    wishListResultPOJO.setWishlist_item_id(wishListDataPOJO.getWishlistItemId());
                                    wishListResultPOJO.setCustomer_id(wishListDataPOJO.getCustomerId());
                                    wishListResultPOJO.setDescription(wishListDataPOJO.getDescription());
                                    wishListResultPOJO.setProduct_name(wishListDataPOJO.getValue());
                                    wishListResultPOJO.setPrice(wishListDataPOJO.getMain_price());
                                    wishListResultPOJO.setDiscount_price(wishListDataPOJO.getDiscount_price());
                                    wishListResultPOJO.setQuantity(wishListDataPOJO.getQty());
                                    break;
                                case "85":
                                    wishListResultPOJO.setImage(wishListDataPOJO.getValue());
                                    break;
                                case "222":
                                    wishListResultPOJO.setProduct_sku(wishListDataPOJO.getValue());
                                    break;
                            }
                        }
                    }
                    wishListResultPOJOs.add(wishListResultPOJO);
                }


                inflateWishListData(wishListResultPOJOs);

            } else {
                databaseHelper.deleteALLWishListData();
                ll_scroll.removeAllViews();
            }
        } catch (Exception e) {
            e.printStackTrace();
            databaseHelper.deleteALLWishListData();
            ll_scroll.removeAllViews();
        }
    }


    public void inflateWishListData(final List<WishListResultPOJO> wishListResultPOJOList) {
        ll_scroll.removeAllViews();
        databaseHelper.deleteALLWishListData();
        for (int i = 0; i < wishListResultPOJOList.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_wish_list, null);
            databaseHelper.insertWishListData(wishListResultPOJOList.get(i));
            ImageView img_book = (ImageView) view.findViewById(R.id.img_book);
            TextView tv_book_name = (TextView) view.findViewById(R.id.tv_book_name);
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
            TextView tv_sku = (TextView) view.findViewById(R.id.tv_sku);
            ImageView iv_decrease_product = (ImageView) view.findViewById(R.id.iv_decrease_product);
            final TextView tv_quant = (TextView) view.findViewById(R.id.tv_quant);
            ImageView iv_increase_product = (ImageView) view.findViewById(R.id.iv_increase_product);
            final ImageView iv_add_cart = (ImageView) view.findViewById(R.id.iv_add_cart);
            ImageView iv_delete_item = (ImageView) view.findViewById(R.id.iv_delete_item);
            LinearLayout ll_wish_item = (LinearLayout) view.findViewById(R.id.ll_wish_item);
            final EditText et_review = (EditText) view.findViewById(R.id.et_review);
            Button btn_update = (Button) view.findViewById(R.id.btn_update);


            Glide.with(getApplicationContext())
                    .load(WebServicesUrls.IMAGE_BASE_URL + wishListResultPOJOList.get(i).getImage())
                    .into(img_book);

            tv_price.setText(Pref.GetCurrency(getApplicationContext())+" " + wishListResultPOJOList.get(i).getPrice());
            tv_book_name.setText(wishListResultPOJOList.get(i).getProduct_name());
            tv_sku.setText(wishListResultPOJOList.get(i).getProduct_sku());
            tv_quant.setText(String.valueOf(converttoInt(wishListResultPOJOList.get(i).getQuantity())));
            et_review.setText(Html.fromHtml(wishListResultPOJOList.get(i).getDescription()));
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
            final int finalI = i;
            ll_wish_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    homeActivity.showProductViewFragment(wishListResultPOJOList.get(finalI).getProduct_id());
                }
            });
//            final int finalI1 = i;
            iv_delete_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RemoveFromWishList(wishListResultPOJOList.get(finalI).getWishlist_item_id());
                }
            });
            if(databaseHelper.getCartData(wishListResultPOJOList.get(i).getProduct_id())!=null){
                Log.d(TAG,"cart item:-"+databaseHelper.getCartData(wishListResultPOJOList.get(i).getProduct_id()));
                iv_add_cart.setVisibility(View.GONE);
            }else{
                iv_add_cart.setVisibility(View.VISIBLE);
            }
            iv_add_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WishListResultPOJO wishListResultPOJO=wishListResultPOJOList.get(finalI);
                    HomeActivity homeActivity= (HomeActivity) getActivity();
                    homeActivity.AddtoCartAPI(wishListResultPOJO.getProduct_id(),
                            wishListResultPOJO.getProduct_sku(),
                            wishListResultPOJO.getProduct_name(),
                            "",
                            tv_quant.getText().toString(),
                            wishListResultPOJO.getDiscount_price(),
                            wishListResultPOJO.getDiscount_price(),
                            iv_add_cart,false);

                }
            });
            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateWishlist(et_review.getText().toString(),
                            tv_quant.getText().toString(),
                            wishListResultPOJOList.get(finalI).getWishlist_item_id()
                    );
                }
            });

            ll_scroll.addView(view);
        }
    }


    public void updateWishlist(String description,String qty,String wishlist_item_id){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("description", description));
        nameValuePairs.add(new BasicNameValuePair("qty", qty));
        nameValuePairs.add(new BasicNameValuePair("wishlist_item_id", wishlist_item_id));
        nameValuePairs.add(new BasicNameValuePair("user_id", Pref.GetStringPref(getActivity().getApplicationContext(),StringUtils.ENTITY_ID,"")));
        new WebServiceBase(nameValuePairs, getActivity(), this, UPDATE_WISLIST_DATA).execute(WebServicesUrls.UPDATE_WISHLIST_ITEM);
    }


    public int converttoInt(String str) {
        try {
            double num = Double.parseDouble(str);
            return (int) num;
        } catch (Exception e) {
            return 0;
        }
    }

    public void RemoveFromWishList(String wishlist_item_id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("wishlist_item_id", wishlist_item_id));
        new WebServiceBase(nameValuePairs, getActivity(), this, DELETE_FROM_WISHLIST_API).execute(WebServicesUrls.DELETE_FROM_WISHLIST);
    }
}
