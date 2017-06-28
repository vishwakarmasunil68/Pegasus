package com.bjain.pegasus.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.bjain.pegasus.R;
import com.bjain.pegasus.activity.HomeActivity;
import com.bjain.pegasus.adapter.CategoryProductAdapter;
import com.bjain.pegasus.pojo.categoryproduct.CategoryProductPOJO;
import com.bjain.pegasus.pojo.categoryproduct.CategoryProductResultPOJO;
import com.bjain.pegasus.utils.TagUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 30-05-2017.
 */

public class CategoryProductFragment extends Fragment{

    @BindView(R.id.rv_category)
    RecyclerView rv_category;
    @BindView(R.id.spinner_sort)
    Spinner spinner_sort;
    @BindView(R.id.spinner_filter)
    Spinner spinner_filter;
    @BindView(R.id.ll_nav_cat)
    LinearLayout ll_nav_cat;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.ll_barcode_scan)
    LinearLayout ll_barcode_scan;

    CategoryProductPOJO categoryProductPOJO;
    public CategoryProductFragment(){

    }
    public CategoryProductFragment(CategoryProductPOJO categoryProductPOJO){
        this.categoryProductPOJO=categoryProductPOJO;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_category,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ll_nav_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) getActivity();
                homeActivity.openCatNavDrawer();
            }
        });
        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) getActivity();
                homeActivity.showSearchFragment();
            }
        });
        ll_barcode_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) getActivity();
                homeActivity.showBarcodeFragment();
            }
        });
        if(categoryProductPOJO!=null&&categoryProductPOJO.getCategoryProductResultPOJOs().size()>0){
            final List<CategoryProductResultPOJO> categoryProductResultPOJOList=categoryProductPOJO.getCategoryProductResultPOJOs();
            final List<CategoryProductResultPOJO> newArrivalDataPOJOList1 = new ArrayList<CategoryProductResultPOJO>(categoryProductResultPOJOList);
            sortByPosition(categoryProductResultPOJOList);
            spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (categoryProductResultPOJOList.size() > 0) {
                        switch (position) {
                            case 0:
//                                sortByPosition(new ArrayList<CategoryProductResultPOJO>(newArrivalDataPOJOList1));
                                break;
                            case 1:
                                sortByName(categoryProductResultPOJOList);
                                break;
                            case 2:
                                sortByPrice(categoryProductResultPOJOList);
                                break;
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
    public void sortByPosition(List<CategoryProductResultPOJO> newArrivalDataPOJOList) {
        setFilterNullListener();
        setSpinner_filterListener(newArrivalDataPOJOList);
        inflateList(newArrivalDataPOJOList);
    }

    public void sortByName(List<CategoryProductResultPOJO> newArrivalDataPOJOList) {
        Collections.sort(newArrivalDataPOJOList, new Comparator<CategoryProductResultPOJO>() {
            @Override
            public int compare(CategoryProductResultPOJO o1, CategoryProductResultPOJO o2) {
                try {
                    return o1.getName().compareTo(o2.getName());
                }
                catch (Exception e){
                    return 0;
                }
            }
        });
        inflateList(newArrivalDataPOJOList);
        setFilterNullListener();
        setSpinner_filterListener(newArrivalDataPOJOList);
    }

    public void setSpinner_filterListener(final List<CategoryProductResultPOJO> newArrivalDataPOJOList) {
        final List<CategoryProductResultPOJO> newArrivalDataPOJOs = new ArrayList<CategoryProductResultPOJO>(newArrivalDataPOJOList);
        spinner_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    Log.d(TagUtils.getTag(), "on filter item selected");
                    Collections.reverse(newArrivalDataPOJOs);
                    inflateList(newArrivalDataPOJOs);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void sortByPrice(List<CategoryProductResultPOJO> newArrivalDataPOJOList) {
        Collections.sort(newArrivalDataPOJOList, new Comparator<CategoryProductResultPOJO>() {
            @Override
            public int compare(CategoryProductResultPOJO o1, CategoryProductResultPOJO o2) {
                return (int) ((Double.parseDouble(o1.getDiscount_price()) - Double.parseDouble(o2.getDiscount_price())));
            }
        });
        inflateList(newArrivalDataPOJOList);
        setFilterNullListener();
        setSpinner_filterListener(newArrivalDataPOJOList);
    }

    public void setFilterNullListener() {
        spinner_filter.setOnItemSelectedListener(null);
        spinner_filter.setSelection(0);
    }
    public void inflateList(List<CategoryProductResultPOJO> categoryProductResultPOJOList){
        CategoryProductAdapter adapter = new CategoryProductAdapter(getActivity(), categoryProductResultPOJOList);
        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(getActivity(), 3);
        rv_category.setLayoutManager(gridLayoutManager);
        rv_category.setAdapter(adapter);
    }

}
