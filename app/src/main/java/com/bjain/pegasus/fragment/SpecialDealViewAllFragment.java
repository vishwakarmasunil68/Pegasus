package com.bjain.pegasus.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.bjain.pegasus.adapter.SpecialListAdapter;
import com.bjain.pegasus.pojo.specialdeal.SpecialDealDataPOJO;
import com.bjain.pegasus.utils.TagUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 20-06-2017.
 */

public class SpecialDealViewAllFragment extends Fragment{
    @BindView(R.id.rv_bookito)
    RecyclerView rv_bookito;
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

    List<SpecialDealDataPOJO> specialDealDataPOJOList;

    public SpecialDealViewAllFragment(List<SpecialDealDataPOJO> specialDealDataPOJOList){
        this.specialDealDataPOJOList=specialDealDataPOJOList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_bookit,container,false);
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

        if(specialDealDataPOJOList!=null&&specialDealDataPOJOList.size()>0) {

            final List<SpecialDealDataPOJO> newArrivalDataPOJOList1 = new ArrayList<SpecialDealDataPOJO>(specialDealDataPOJOList);
            sortByPosition(specialDealDataPOJOList);
            spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (specialDealDataPOJOList.size() > 0) {
                        switch (position) {
                            case 0:
//                                sortByPosition(new ArrayList<SpecialDealDataPOJO>(newArrivalDataPOJOList1));
                                break;
                            case 1:
                                sortByName(specialDealDataPOJOList);
                                break;
                            case 2:
                                sortByPrice(specialDealDataPOJOList);
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
    public void sortByPosition(List<SpecialDealDataPOJO> newArrivalDataPOJOList) {
        setFilterNullListener();
        setSpinner_filterListener(newArrivalDataPOJOList);
        inflateList(newArrivalDataPOJOList);
    }

    public void sortByName(List<SpecialDealDataPOJO> newArrivalDataPOJOList) {
        Collections.sort(newArrivalDataPOJOList, new Comparator<SpecialDealDataPOJO>() {
            @Override
            public int compare(SpecialDealDataPOJO o1, SpecialDealDataPOJO o2) {
                return o1.getProduct_name().compareTo(o2.getProduct_name());
            }
        });
        inflateList(newArrivalDataPOJOList);
        setFilterNullListener();
        setSpinner_filterListener(newArrivalDataPOJOList);
    }

    public void setSpinner_filterListener(final List<SpecialDealDataPOJO> newArrivalDataPOJOList) {
        final List<SpecialDealDataPOJO> newArrivalDataPOJOs = new ArrayList<SpecialDealDataPOJO>(newArrivalDataPOJOList);
        spinner_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TagUtils.getTag(), "on filter item selected");
                if(position!=0) {
                    Collections.reverse(newArrivalDataPOJOs);
                    inflateList(newArrivalDataPOJOs);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void sortByPrice(List<SpecialDealDataPOJO> newArrivalDataPOJOList) {
        Collections.sort(newArrivalDataPOJOList, new Comparator<SpecialDealDataPOJO>() {
            @Override
            public int compare(SpecialDealDataPOJO o1, SpecialDealDataPOJO o2) {
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

    public void inflateList(List<SpecialDealDataPOJO> newArrivalDataPOJOList) {
        SpecialListAdapter adapter = new SpecialListAdapter(getActivity(), newArrivalDataPOJOList);
        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(getActivity(), 3);
        rv_bookito.setLayoutManager(gridLayoutManager);
        rv_bookito.setHasFixedSize(true);
        rv_bookito.setItemAnimator(new DefaultItemAnimator());
        rv_bookito.setAdapter(adapter);
    }

}
