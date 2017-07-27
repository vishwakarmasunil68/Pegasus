package com.bjain.pegasus.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjain.pegasus.R;
import com.bjain.pegasus.activity.HomeActivity;
import com.bjain.pegasus.adapter.ProductImageSwipeAdapter;
import com.bjain.pegasus.adapter.RelatedProductAdapter;
import com.bjain.pegasus.database.DatabaseHelper;
import com.bjain.pegasus.pojo.newproductpojo.NewProductPOJO;
import com.bjain.pegasus.pojo.newproductpojo.NewProductResultPOJO;
import com.bjain.pegasus.pojo.productview.SingleProductPOJO;
import com.bjain.pegasus.pojo.relatedproduct.RelatedPOJO;
import com.bjain.pegasus.pojo.review.UserReviewPOJO;
import com.bjain.pegasus.pojo.review.UserReviewResultPOJO;
import com.bjain.pegasus.utils.Pref;
import com.bjain.pegasus.utils.StringUtils;
import com.bjain.pegasus.utils.TagUtils;
import com.bjain.pegasus.utils.ToastClass;
import com.bjain.pegasus.webservice.WebServiceBase;
import com.bjain.pegasus.webservice.WebServicesCallBack;
import com.bjain.pegasus.webservice.WebServicesUrls;
import com.bjain.pegasus.widgets.ExpandableTextView;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 30-05-2017.
 */

public class ProductFragment extends Fragment implements WebServicesCallBack {
    private static final String PRODUCT_VIEW_API = "product_view_api";
    private static final String RELATED_PRODUCT_API = "related_product_api";
    private static final String USER_REVIEW_API = "user_review_api";
    private static final String ADD_USER_REVIEW = "add_user_review";
    String product_id;
    DatabaseHelper databaseHelper;
    //    @BindView(R.id.iv_image_pic)
//    ImageView iv_image_pic;
    @BindView(R.id.tv_book_name)
    TextView tv_book_name;
    @BindView(R.id.tv_author)
    TextView tv_author;
    @BindView(R.id.tv_sku)
    TextView tv_sku;
    @BindView(R.id.tv_availability)
    TextView tv_availability;
    @BindView(R.id.tv_book_quantity)
    TextView tv_book_quantity;
    @BindView(R.id.iv_increase_product)
    ImageView iv_increase_product;
    @BindView(R.id.iv_decrease_product)
    ImageView iv_decrease_product;
    @BindView(R.id.tv_discount_price)
    TextView tv_discount_price;
    @BindView(R.id.tv_price)
    HtmlTextView tv_price;
    @BindView(R.id.btn_buy_now)
    Button btn_buy_now;
    @BindView(R.id.btn_add_to_wishlist)
    Button btn_add_to_wishlist;
    @BindView(R.id.btn_add_to_cart)
    Button btn_add_to_cart;
    @BindView(R.id.tv_quick_overview)
    ExpandableTextView tv_quick_overview;
    @BindView(R.id.ll_write_review)
    LinearLayout ll_write_review;
    @BindView(R.id.ll_review_scroll)
    LinearLayout ll_review_scroll;
    @BindView(R.id.rv_related_products)
    RecyclerView rv_related_products;
    @BindView(R.id.tv_no_reviews)
    TextView tv_no_reviews;
    @BindView(R.id.image_pager)
    ViewPager image_pager;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.ll_ford)
    LinearLayout ll_ford;
    @BindView(R.id.iv_share)
    ImageView iv_share;


    public ProductFragment(String product_id) {
        this.product_id = product_id;
    }

    public ProductFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_product_view, container, false);
        ButterKnife.bind(this, view);
        databaseHelper = new DatabaseHelper(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        callProductViewAPI();
    }


    public void callProductViewAPI() {
        Log.d(TAG, "product_id:-" + product_id);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("product_id", product_id));
        new WebServiceBase(nameValuePairs, getActivity(), this, PRODUCT_VIEW_API).execute(WebServicesUrls.NEW_SINGLE_PRODUCT_VIEW_URL);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case PRODUCT_VIEW_API:
                parseProductViewResponse(response);
                break;
            case RELATED_PRODUCT_API:
                parseRelatedProductResponse(response);
                break;
            case ADD_USER_REVIEW:
                parseReviews(response);
                break;
            case USER_REVIEW_API:
                parseReviewResponse(response);
                break;
        }
    }

    public void parseReviews(String response) {
        //Log.d(TAG,"Review response:-"+response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("Success").equals("true")) {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Review Added");
                callReviewAPI(product_id);
            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
        }
    }

    public void parseReviewResponse(String response) {
        Log.d(TAG, "review response:-" + response);
        try {
            Gson gson = new Gson();
            UserReviewPOJO userReviewPOJO = gson.fromJson(response, UserReviewPOJO.class);
            if (userReviewPOJO.getSuccess().equals("true")) {
                tv_no_reviews.setVisibility(View.GONE);
                inflateUserReviewResponse(userReviewPOJO.getUserReviewResultPOJOList());
            } else {
                tv_no_reviews.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            tv_no_reviews.setVisibility(View.VISIBLE);
        }
    }

    public void inflateUserReviewResponse(List<UserReviewResultPOJO> userReviewResultPOJOList) {
        ll_review_scroll.removeAllViews();
        for (int i = 0; i < userReviewResultPOJOList.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_user_reviews, null);
            TextView tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
            TextView tv_date = (TextView) view.findViewById(R.id.tv_date);
            TextView tv_review = (TextView) view.findViewById(R.id.tv_review);


            tv_user_name.setText(userReviewResultPOJOList.get(i).getNickname());
            tv_date.setText(userReviewResultPOJOList.get(i).getCreated_at());
            tv_review.setText(userReviewResultPOJOList.get(i).getDetail());

            ll_review_scroll.addView(view);
        }
    }


    private final String TAG = getClass().getSimpleName();

    public void parseProductViewResponse(String response) {
        Log.d(TAG, "product view:-" + response);
        try {
            Gson gson = new Gson();
            NewProductPOJO newProductPOJO = gson.fromJson(response, NewProductPOJO.class);
            if (newProductPOJO.getSuccess().equals("true")) {
                SingleProductPOJO singleProductPOJO = new SingleProductPOJO();
                for (NewProductResultPOJO newProductResultPOJO : newProductPOJO.getNewProductResultPOJOList()) {
                    switch (newProductResultPOJO.getAttributeId()) {
                        case "71":
                            singleProductPOJO.setProduct_id(newProductResultPOJO.getEntityId());
                            singleProductPOJO.setName(newProductResultPOJO.getValue());
                            singleProductPOJO.setDescription(newProductResultPOJO.getDescription());
                            singleProductPOJO.setBase_price(newProductPOJO.getPricePOJO().getMain_price());
                            singleProductPOJO.setDiscount_price(newProductPOJO.getPricePOJO().getDiscount_price());
                            singleProductPOJO.setAuthor(newProductResultPOJO.getAuthorName());
                            break;
                        case "85":
                            singleProductPOJO.setImage(newProductResultPOJO.getValue());
                            break;
                        case "222":
                            singleProductPOJO.setSku(newProductResultPOJO.getSku());
                            break;
                    }
                }
                showProduct(singleProductPOJO);

                List<String> list_images = newProductPOJO.getGalaryPOJOList();
                if(list_images!=null&&list_images.size()>0) {
                    Log.d(TagUtils.getTag(), "images:-" + list_images.toString());
                    setUpViewPager(list_images);
                }
            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "something went wrong");
            }
            iv_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareApp();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shareApp() {
        int applicationNameId = getActivity().getApplicationInfo().labelRes;
        final String appPackageName = getActivity().getPackageName();
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, getActivity().getString(applicationNameId));
        String text = "Install this cool application: ";
        String link = "https://play.google.com/store/apps/details?id=" + appPackageName;
        i.putExtra(Intent.EXTRA_TEXT, text + " " + link);
        startActivity(Intent.createChooser(i, "Share link:"));
    }

    public void setUpViewPager(final List<String> imageIdList) {
        image_pager.setAdapter(new ProductImageSwipeAdapter(getActivity(), imageIdList));
        image_pager.setOffscreenPageLimit(imageIdList.size());
//        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

//        viewPager.setInterval(2000);
//        viewPager.startAutoScroll();
//        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % imageIdList.size());
        checkSwiping(imageIdList.size());
        ll_ford.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((image_pager.getCurrentItem() + 1) == imageIdList.size()) {
                    image_pager.setCurrentItem(0);
                } else {
                    image_pager.setCurrentItem(image_pager.getCurrentItem() + 1);
                }
                checkSwiping(imageIdList.size());
            }
        });
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image_pager.getCurrentItem() != 0) {
                    image_pager.setCurrentItem(image_pager.getCurrentItem() - 1);
                }
                checkSwiping(imageIdList.size());
            }
        });
    }

    public void checkSwiping(int size) {
        if ((image_pager.getCurrentItem() + 1) == size) {
            ll_ford.setVisibility(View.INVISIBLE);
        } else {
            ll_ford.setVisibility(View.VISIBLE);
        }

        if (image_pager.getCurrentItem() == 0) {
            ll_back.setVisibility(View.INVISIBLE);
        } else {
            ll_back.setVisibility(View.VISIBLE);
        }
    }


    public String convertPrice(String price) {
        try {
            double val = Double.parseDouble(price);
            DecimalFormat f = new DecimalFormat("##.00");
            return String.valueOf(f.format(val));
        } catch (Exception e) {
            e.printStackTrace();
            return price;
        }
    }

    public void showProduct(final SingleProductPOJO singleProductPOJO) {
        tv_book_name.setText(singleProductPOJO.getName());
        tv_quick_overview.setText(Html.fromHtml(singleProductPOJO.getDescription()));

        String base_price_text = "<strike>" + Pref.GetCurrency(getActivity().getApplicationContext()) + " " + convertPrice(singleProductPOJO.getBase_price()) + "</strike>";
        tv_price.setText(Html.fromHtml(base_price_text));
        tv_price.setHtml(base_price_text, new HtmlResImageGetter(tv_price));

        tv_price.setVisibility(View.VISIBLE);
        tv_discount_price.setVisibility(View.VISIBLE);
        tv_discount_price.setText(Pref.GetCurrency(getActivity().getApplicationContext()) + " " + convertPrice(singleProductPOJO.getDiscount_price()));
        tv_discount_price.setVisibility(View.VISIBLE);
//        Glide.with(getActivity().getApplicationContext())
//                .load(WebServicesUrls.IMAGE_BASE_URL + singleProductPOJO.getImage())
//                .dontAnimate()
//                .into(iv_image_pic);
        if (singleProductPOJO.getAuthor() != null && !singleProductPOJO.getAuthor().equals("null")) {
            tv_author.setText(singleProductPOJO.getAuthor());
        } else {
            tv_author.setText("Pegasus");
        }
        tv_sku.setText(singleProductPOJO.getSku());

        iv_decrease_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int val = Integer.parseInt(tv_book_quantity.getText().toString());
                    if (val != 0) {
                        val = val - 1;
                        tv_book_quantity.setText(String.valueOf(val));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        iv_increase_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int val = Integer.parseInt(tv_book_quantity.getText().toString());
                    val = val + 1;
                    tv_book_quantity.setText(String.valueOf(val));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        if (databaseHelper.getCartData(product_id) != null) {
            Log.d(TAG, "cartdata:-" + databaseHelper.getCartData(product_id).toString());
            btn_add_to_cart.setText("Remove From Cart");
        } else {
            Log.d(TAG, "cartdata:-empty");
            btn_add_to_cart.setText("Add To Cart");
        }
        if (databaseHelper.getWishListData(singleProductPOJO.getProduct_id()) != null) {
            btn_add_to_wishlist.setText("Remove From WishList");
        } else {
            btn_add_to_wishlist.setText("Add To WishList");
        }

        btn_add_to_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (btn_add_to_wishlist.getText().toString().equalsIgnoreCase("Remove From WishList")) {
                        ((HomeActivity) getActivity()).RemoveFromWishList(databaseHelper.getWishListData(singleProductPOJO.getProduct_id()).getWishlist_item_id(), btn_add_to_wishlist);
                    } else {
                        ((HomeActivity) getActivity()).AddToWishListAPI(singleProductPOJO.getProduct_id(), singleProductPOJO.getDescription(), tv_book_quantity.getText().toString(), btn_add_to_wishlist);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "something went wrong");
                }
            }
        });
        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (btn_add_to_cart.getText().toString().equalsIgnoreCase("Remove From Cart")) {
                        ((HomeActivity) getActivity()).callRemoveFromCartAPI(databaseHelper.getCartData(singleProductPOJO.getProduct_id()).getCart_id(),
                                singleProductPOJO.getProduct_id(), btn_add_to_cart);
                    } else {
                        int qty = Integer.parseInt(tv_book_quantity.getText().toString());
                        if (qty > 0) {
                            ((HomeActivity) getActivity()).AddtoCartAPI(singleProductPOJO.getProduct_id(),
                                    singleProductPOJO.getSku(), singleProductPOJO.getName(),
                                    "", tv_book_quantity.getText().toString(), singleProductPOJO.getDiscount_price(),
                                    convertPrice(singleProductPOJO.getDiscount_price()), btn_add_to_cart, false);
                        } else {
                            ToastClass.showShortToast(getActivity().getApplicationContext(), "Quantity should be greater than 0");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    if (btn_add_to_cart.getText().toString().equalsIgnoreCase("Remove From Cart")) {
//                        ((HomeActivity) getActivity()).callRemoveFromCartAPI(databaseHelper.getCartData(singleProductPOJO.getProduct_id()).getCart_id(),
//                                singleProductPOJO.getProduct_id(), btn_add_to_cart);
//                    } else {
                    int qty = Integer.parseInt(tv_book_quantity.getText().toString());
                    if (qty > 0) {
                        ((HomeActivity) getActivity()).AddtoCartAPI(singleProductPOJO.getProduct_id(),
                                singleProductPOJO.getSku(), singleProductPOJO.getName(),
                                "", tv_book_quantity.getText().toString(), singleProductPOJO.getDiscount_price(),
                                convertPrice(singleProductPOJO.getDiscount_price()), btn_add_to_cart, true);
//                        } else {
//                            ToastClass.showShortToast(getActivity().getApplicationContext(), "Quantity should be greater than 0");
//                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ll_write_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Pref.GetBooleanPref(getActivity().getApplicationContext(),StringUtils.IS_LOGIN,false)) {
                    showReviewDialog(singleProductPOJO.getProduct_id());
                }else{
                    ToastClass.showShortToast(getActivity().getApplicationContext(),"Please Login to write review.");
                }
            }
        });

        callReviewAPI(singleProductPOJO.getProduct_id());

//        callRelatedProductAPI(singleProductPOJO.getProduct_id());
    }

    public void callReviewAPI(String product_id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("product_id", product_id));
        new WebServiceBase(nameValuePairs, getActivity(), this, USER_REVIEW_API, false).execute(WebServicesUrls.USER_REVIEW_URL);
    }

    public void showReviewDialog(final String product_id) {
        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
//        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog1.setContentView(R.layout.dialog_review);
        dialog1.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog1.setTitle("Write Review");
        dialog1.setCancelable(true);
        dialog1.show();
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn_add = (Button) dialog1.findViewById(R.id.btn_add);
        final EditText et_review = (EditText) dialog1.findViewById(R.id.et_review);
        final EditText et_summary = (EditText) dialog1.findViewById(R.id.et_summary);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_review.getText().toString().length() > 0 && et_summary.getText().toString().length() > 0) {
                    callAddUserReview(product_id, et_summary.getText().toString(), et_review.getText().toString());
                    dialog1.dismiss();
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Write Something");
                }
            }
        });
    }

    public void callAddUserReview(String product_id, String title, String detail) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("product_id", product_id));
        nameValuePairs.add(new BasicNameValuePair("title", title));
        nameValuePairs.add(new BasicNameValuePair("detail", detail));
        nameValuePairs.add(new BasicNameValuePair("nickname", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.FIRST_NAME, "")));
        nameValuePairs.add(new BasicNameValuePair("customer_id", Pref.GetStringPref(getActivity().getApplicationContext(),
                StringUtils.ENTITY_ID, "")));
        new WebServiceBase(nameValuePairs, getActivity(), this, ADD_USER_REVIEW).execute(WebServicesUrls.ADD_USER_REVIEW_URL);
    }

    public void callRelatedProductAPI(String product_id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("product_id", product_id));
        new WebServiceBase(nameValuePairs, getActivity(), this, RELATED_PRODUCT_API).execute(WebServicesUrls.RELATED_PRODUCT_URL);
    }


    public void parseRelatedProductResponse(String response) {
        Log.d(TAG, "related response:-" + response);
        try {
            Gson gson = new Gson();
            RelatedPOJO relatedPOJO = gson.fromJson(response, RelatedPOJO.class);
            if (relatedPOJO != null && relatedPOJO.getRelatedProductPOJOList().size() > 0) {
                RelatedProductAdapter relatedProductAdapter = new RelatedProductAdapter(getActivity(), relatedPOJO.getRelatedProductPOJOList());
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                rv_related_products.setLayoutManager(horizontalLayoutManagaer);
                rv_related_products.setAdapter(relatedProductAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
