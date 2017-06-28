package com.bjain.pegasus.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjain.pegasus.R;
import com.bjain.pegasus.activity.HomeActivity;
import com.bjain.pegasus.pojo.newsearch.NewSearchResultPOJO;
import com.bjain.pegasus.utils.Pref;
import com.bumptech.glide.Glide;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.MyViewHolder> {
    private List<NewSearchResultPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_product;
        public TextView tv_name;
        public HtmlTextView tv_price;
        public TextView tv_discount_price;
        public ImageView iv_add_cart;
//        public ImageView iv_favorite;
        public CardView cv_product;
        public LinearLayout ll_product;

        public MyViewHolder(View view) {
            super(view);
            iv_product = (ImageView) view.findViewById(R.id.iv_product);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_price = (HtmlTextView) view.findViewById(R.id.tv_price);
            tv_discount_price = (TextView) view.findViewById(R.id.tv_discount_price);
            iv_add_cart = (ImageView) view.findViewById(R.id.iv_add_cart);
//            iv_favorite = (ImageView) view.findViewById(R.id.iv_favorite);
            cv_product = (CardView) view.findViewById(R.id.cv_product);
            ll_product = (LinearLayout) view.findViewById(R.id.ll_product);
        }
    }


    public SearchProductAdapter(Activity activity, List<NewSearchResultPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_category_products, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Glide.with(activity).load("http://www.pegasusforkids.com/media/catalog/product" + horizontalList.get(position).getProduct_image()).into(holder.iv_product);
        holder.tv_name.setText(horizontalList.get(position).getProduct_name());

        String main_price="<s>"+Pref.GetCurrency(activity.getApplicationContext())+" " + horizontalList.get(position).getMain_price()+"</s>";
        holder.tv_price.setHtml(main_price, new HtmlResImageGetter(holder.tv_price));
        holder.tv_discount_price.setText(Pref.GetCurrency(activity.getApplicationContext())+" " + horizontalList.get(position).getDiscount_price());

        holder.ll_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) activity;
                homeActivity.showProductViewFragment(horizontalList.get(position).getProduct_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}
