package com.bjain.pegasus.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjain.pegasus.R;
import com.bjain.pegasus.activity.HomeActivity;
import com.bjain.pegasus.pojo.relatedproduct.RelatedProductPOJO;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class RelatedProductAdapter extends RecyclerView.Adapter<RelatedProductAdapter.MyViewHolder> {

private List<RelatedProductPOJO> horizontalList;
private Activity activity;
private final String TAG=getClass().getSimpleName();

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_name;
    public TextView tv_price;
    public ImageView iv_product;
    public LinearLayout ll_product;


    public MyViewHolder(View view) {
        super(view);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        iv_product = (ImageView) view.findViewById(R.id.iv_product);
        ll_product = (LinearLayout) view.findViewById(R.id.ll_product);

    }
}


    public RelatedProductAdapter(Activity activity, List<RelatedProductPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity=activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_special_deals, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_name.setText(horizontalList.get(position).getName());
        holder.tv_price.setText(horizontalList.get(position).getPrice());
//        Glide.with(activity.getApplicationContext())
//                .load(WebServicesUrls.IMAGE_BASE_URL+horizontalList.get(position).getProduct_image())
//                .into(holder.iv_product);

        holder.ll_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) activity;
                homeActivity.showProductViewFragment(horizontalList.get(position).getProductId());
            }
        });
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
        if(horizontalList!=null){
            return horizontalList.size();
        }else{
            return 0;
        }
    }

}
