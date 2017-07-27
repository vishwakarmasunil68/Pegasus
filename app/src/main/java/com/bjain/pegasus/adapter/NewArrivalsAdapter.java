package com.bjain.pegasus.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjain.pegasus.R;
import com.bjain.pegasus.activity.HomeActivity;
import com.bjain.pegasus.pojo.newarrival.NewArrivalDataPOJO;
import com.bjain.pegasus.utils.Pref;
import com.bjain.pegasus.utils.TagUtils;
import com.bumptech.glide.Glide;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class NewArrivalsAdapter extends RecyclerView.Adapter<NewArrivalsAdapter.MyViewHolder> {
    private List<NewArrivalDataPOJO> horizontalList;
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


    public NewArrivalsAdapter(Activity activity, List<NewArrivalDataPOJO> horizontalList) {
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
//        String image_url="http://www.pegasusforkids.com/media/catalog/product" + horizontalList.get(position).getProduct_image();
//        String image_url= WebServicesUrls.GetImageUrl(horizontalList.get(position).getSku());
        Log.d(TagUtils.getTag(),"image url:-"+horizontalList.get(position).getProduct_image());
        Glide.with(activity).load(horizontalList.get(position).getProduct_image()).into(holder.iv_product);
        holder.tv_name.setText(horizontalList.get(position).getProduct_name());

        try {
            String main_price="<strike>"+ Pref.GetCurrency(activity.getApplicationContext())+" "+
                    horizontalList.get(position).getMain_price()+"</strike>";
//            holder.tv_price.setText(Html.fromHtml(main_price));
            holder.tv_price.setHtml(main_price, new HtmlResImageGetter(holder.tv_price));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        holder.ll_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) activity;
                homeActivity.showProductViewFragment(horizontalList.get(position).getProduct_id());
            }
        });

        try{

            holder.tv_discount_price.setText(Pref.GetCurrency(activity.getApplicationContext())+" "+
                    horizontalList.get(position).getDiscount_price());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String getConvertedPrice(String price){
        try{
            double val=Double.parseDouble(price);
            DecimalFormat f = new DecimalFormat("##.00");
            return String.valueOf(f.format(val));
        }catch (Exception e){
            e.printStackTrace();
            return price;
        }
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}
