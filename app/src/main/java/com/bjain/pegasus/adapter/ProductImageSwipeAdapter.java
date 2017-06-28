package com.bjain.pegasus.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bjain.pegasus.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class ProductImageSwipeAdapter extends PagerAdapter {

    private Activity activity;
    private List<String> imagesArray;

    public ProductImageSwipeAdapter(Activity activity, List<String> imagesArray) {

        this.activity = activity;
        this.imagesArray = imagesArray;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = ((Activity) activity).getLayoutInflater();

        View viewItem = inflater.inflate(R.layout.inflate_product_slider, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.iv_images);
        try {
            Glide.with(activity).load(imagesArray.get(position)).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((ViewPager) container).addView(viewItem);

        return viewItem;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imagesArray.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
        return view == ((View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }
}
