package com.bjain.pegasus.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bjain.pegasus.R;
import com.bjain.pegasus.activity.HomeActivity;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class CustomswipeAdapter extends PagerAdapter {

    private Activity activity;
    private List<String> imagesArray;
    boolean is_home=false;
    public CustomswipeAdapter(Activity activity, List<String> imagesArray,boolean is_home) {

        this.activity = activity;
        this.imagesArray = imagesArray;
        this.is_home=is_home;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater inflater = ((Activity) activity).getLayoutInflater();

        View viewItem = inflater.inflate(R.layout.inflate_slider_layout, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.iv_images);
        try {
            Glide.with(activity).load(imagesArray.get(position)).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(is_home) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeActivity homeActivity = (HomeActivity) activity;
                    if (position == 2) {
                        homeActivity.showSchoolOpportunityFragment();
                    } else {
                        if (position == 3) {
                            homeActivity.callCategoryProductAPI("264");
                        } else {
                            if (position == 1) {
                                homeActivity.showBetterTommorowFragment();
                            } else {
                                if (position == 0) {
                                    homeActivity.callCategoryProductAPI("43");
                                }
                            }
                        }
                    }
                }
            });
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
