package com.bjain.pegasus.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjain.pegasus.R;
import com.bjain.pegasus.activity.HomeActivity;
import com.bjain.pegasus.pojo.category.CategoryPOJO;
import com.bjain.pegasus.pojo.category.CategoryResultPOJO;
import com.bjain.pegasus.utils.Pref;
import com.bjain.pegasus.utils.StringUtils;
import com.bjain.pegasus.utils.TagUtils;
import com.bjain.pegasus.webservice.GetWebServices;
import com.bjain.pegasus.webservice.WebServicesUrls;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by sunil on 05-06-2017.
 */

public class CategoryViewFragment extends Fragment {

    @BindView(R.id.ll_nav_cat_level0)
    LinearLayout ll_nav_cat_level0;
    private static final String GET_CATEGORY_DATA = "get_category_data";
    public CategoryViewFragment() {

    }

//    List<CategoryResultPOJO> categoryResultPOJOList;

//    public CategoryViewFragment(List<CategoryResultPOJO> categoryResultPOJOList) {
//        this.categoryResultPOJOList = categoryResultPOJOList;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_category_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if (categoryResultPOJOList != null && categoryResultPOJOList.size() > 0) {
//            inflateCategory(categoryResultPOJOList);
//        }
        try{
            CategoryPOJO categoryPOJO=(CategoryPOJO) Pref.GetPOJO(getActivity().getApplicationContext(),StringUtils.CATEGORY_TYPE,StringUtils.CATEGORY_TYPE);
            if(categoryPOJO!=null) {
                loadCategory(categoryPOJO);
            }else{
                new GetWebServices(getActivity(),this, GET_CATEGORY_DATA).execute(WebServicesUrls.CATEGORY_DATA_URL);
            }
        }catch (Exception e){
            e.printStackTrace();
            new GetWebServices(getActivity(),this, GET_CATEGORY_DATA).execute(WebServicesUrls.CATEGORY_DATA_URL);
        }
    }
    List<CategoryResultPOJO> categoryResultPOJOList;
    public void loadCategory(CategoryPOJO categoryPOJO){
        if (categoryPOJO.getSuccess().equals("true")) {
            CategoryResultPOJO categoryResultPOJO = categoryPOJO.getCategoryResultPOJO().
                    getCategoryResultPOJOList().get(0).getCategoryResultPOJOList().get(0);
            Log.d(TagUtils.getTag(), "category result:-" + categoryResultPOJO.toString());
            categoryResultPOJOList = categoryResultPOJO.getCategoryResultPOJOList();
            Pref.SavePOJO(getApplicationContext(),StringUtils.CATEGORY_TYPE,categoryPOJO);
//                Pref.SavePOJO(getApplicationContext(),StringUtils.CATEGORY_TYPE,categoryPOJO);
            inflateCategory(categoryResultPOJO.getCategoryResultPOJOList());
        } else {

        }
    }

    public void inflateCategory(List<CategoryResultPOJO> categoryResultPOJOList) {
        if (categoryResultPOJOList.size() > 0) {
            ll_nav_cat_level0.removeAllViews();
            for (int i = 0; i < categoryResultPOJOList.size(); i++) {
                final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.inflate_category, null);

                TextView tv_category_name = (TextView) view.findViewById(R.id.tv_category_name);
                final ImageView iv_catvisible = (ImageView) view.findViewById(R.id.iv_catvisible);
                final LinearLayout ll_category_data = (LinearLayout) view.findViewById(R.id.ll_category_data);


                tv_category_name.setText(categoryResultPOJOList.get(i).getName());

                iv_catvisible.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ll_category_data.getVisibility() == View.VISIBLE) {
                            ll_category_data.setVisibility(View.GONE);
                            iv_catvisible.setImageResource(R.drawable.ic_cat_max);
                        } else {
                            ll_category_data.setVisibility(View.VISIBLE);
                            iv_catvisible.setImageResource(R.drawable.ic_cat_min);
                        }
                    }
                });
                final LinearLayout ll_category = (LinearLayout) view.findViewById(R.id.ll_category);

                ll_category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ll_category_data.getVisibility() == View.VISIBLE) {
                            ll_category_data.setVisibility(View.GONE);
                            iv_catvisible.setImageResource(R.drawable.ic_cat_max);
                        } else {
                            ll_category_data.setVisibility(View.VISIBLE);
                            iv_catvisible.setImageResource(R.drawable.ic_cat_min);
                        }
                    }
                });

                inflateCategoryData(ll_category_data, categoryResultPOJOList.get(i).getCategoryResultPOJOList());

                ll_nav_cat_level0.addView(view);
            }
        }
    }

    public void inflateCategoryData(LinearLayout ll_category_data, final List<CategoryResultPOJO> categoryResultPOJOList) {
        if (categoryResultPOJOList.size() > 0) {
            ll_category_data.removeAllViews();
            for (int i = 0; i < categoryResultPOJOList.size(); i++) {
                final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.inflate_category_data, null);
                LinearLayout ll_sub_category = (LinearLayout) view.findViewById(R.id.ll_sub_category);
                TextView tv_category_name = (TextView) view.findViewById(R.id.tv_category_name);

                tv_category_name.setText(categoryResultPOJOList.get(i).getName());
                final int finalI = i;
                ll_sub_category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HomeActivity homeActivity = (HomeActivity) getActivity();
                        homeActivity.callCategoryProductAPI(categoryResultPOJOList.get(finalI).getCategory_id());
                    }
                });

                ll_category_data.addView(view);
            }
        }
    }
}
