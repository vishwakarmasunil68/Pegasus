package com.bjain.pegasus.fragment;

import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.bjain.pegasus.R;
import com.bjain.pegasus.activity.HomeActivity;
import com.bjain.pegasus.adapter.NewArrivalsAdapter;
import com.bjain.pegasus.pojo.newarrival.NewArrivalDataPOJO;
import com.bjain.pegasus.pojo.newarrival.NewArrivalPOJO;
import com.bjain.pegasus.pojo.newarrival.NewArrivalResultPOJO;
import com.bjain.pegasus.utils.TagUtils;
import com.bjain.pegasus.utils.ToastClass;
import com.bjain.pegasus.webservice.GetWebServices;
import com.bjain.pegasus.webservice.WebServicesCallBack;
import com.bjain.pegasus.webservice.WebServicesUrls;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 08-06-2017.
 */

public class NewArrivalsFragment extends Fragment implements WebServicesCallBack {

    private final String TAG = getClass().getSimpleName();
    private final String NEW_ARRIVALS_API = "new_arrival_api";
    @BindView(R.id.rv_new_arrivals)
    RecyclerView rv_new_arrivals;
    @BindView(R.id.iv_new_arrivals)
    ImageView iv_new_arrivals;
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

    boolean is_new_arrival = false;

    public NewArrivalsFragment(boolean is_new_arrival) {
        this.is_new_arrival = is_new_arrival;
    }

    public NewArrivalsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_new_arrivals, container, false);
        ButterKnife.bind(this, view);
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
        Log.d(TAG, "is new arrival:-" + is_new_arrival);
        if (is_new_arrival) {
            iv_new_arrivals.setImageResource(R.drawable.ic_new_arrivals);
            new GetWebServices(getActivity(), this, NEW_ARRIVALS_API).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, WebServicesUrls.NEW_ARRIVALS_URL);
        } else {
            iv_new_arrivals.setImageResource(R.drawable.ic_best_seller);
            new GetWebServices(getActivity(), this, NEW_ARRIVALS_API).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, WebServicesUrls.BEST_SELLER_URL);
        }
        ll_barcode_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) getActivity();
                homeActivity.showBarcodeFragment();
            }
        });

    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case NEW_ARRIVALS_API:
                parseNewArrivalResponse(response);
                break;
        }
    }

    public void parseNewArrivalResponse(String response) {
        Log.d(TAG, "new ArrivalReponse:-" + response);
        try {
            Gson gson = new Gson();
            NewArrivalPOJO newArrivalPOJO = gson.fromJson(response, NewArrivalPOJO.class);
            if (newArrivalPOJO.getSuccess().equals("true")) {
                Set<String> product_ids = new HashSet<>();
                for (NewArrivalResultPOJO newArrivalResultPOJO : newArrivalPOJO.getNewArrivalResultPOJOList()) {
                    product_ids.add(newArrivalResultPOJO.getProductId());
                }

                final List<NewArrivalDataPOJO> newArrivalDataPOJOList = new ArrayList<>();
                for (String product_id : product_ids) {
                    NewArrivalDataPOJO newArrivalDataPOJO = new NewArrivalDataPOJO();
                    for (NewArrivalResultPOJO newArrivalResultPOJO : newArrivalPOJO.getNewArrivalResultPOJOList()) {
                        if (product_id.equals(newArrivalResultPOJO.getProductId())) {
                            switch (newArrivalResultPOJO.getAttributeId()) {
                                case "71":
                                    newArrivalDataPOJO.setProduct_id(product_id);
                                    newArrivalDataPOJO.setProduct_name(newArrivalResultPOJO.getValue());
                                    newArrivalDataPOJO.setDiscount_price(newArrivalResultPOJO.getDiscountPrice());
                                    newArrivalDataPOJO.setMain_price(newArrivalResultPOJO.getMainPrice());
                                    break;
                                case "85":
                                    newArrivalDataPOJO.setProduct_image(newArrivalResultPOJO.getValue());
                                    break;
                                case "222":
                                    newArrivalDataPOJO.setSku(newArrivalResultPOJO.getValue());
                                    break;
                            }
                        }
                    }
                    newArrivalDataPOJOList.add(newArrivalDataPOJO);
                }

                final List<NewArrivalDataPOJO> newArrivalDataPOJOList1 = new ArrayList<>(newArrivalDataPOJOList);
                sortByPosition(newArrivalDataPOJOList);
                spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (newArrivalDataPOJOList.size() > 0) {
                            switch (position) {
                                case 0:
//                                    sortByPosition(new ArrayList<NewArrivalDataPOJO>(newArrivalDataPOJOList1));
                                    break;
                                case 1:
                                    sortByName(newArrivalDataPOJOList);
                                    break;
                                case 2:
                                    sortByPrice(newArrivalDataPOJOList);
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "No Products Found.");
            }

        } catch (Exception e) {
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
            e.printStackTrace();
        }
    }

    public void sortByPosition(List<NewArrivalDataPOJO> newArrivalDataPOJOList) {
        setFilterNullListener();
        setSpinner_filterListener(newArrivalDataPOJOList);
        inflateList(newArrivalDataPOJOList);
    }

    public void sortByName(List<NewArrivalDataPOJO> newArrivalDataPOJOList) {
        Collections.sort(newArrivalDataPOJOList, new Comparator<NewArrivalDataPOJO>() {
            @Override
            public int compare(NewArrivalDataPOJO o1, NewArrivalDataPOJO o2) {
                return o1.getProduct_name().compareTo(o2.getProduct_name());
            }
        });
        inflateList(newArrivalDataPOJOList);
        setFilterNullListener();
        setSpinner_filterListener(newArrivalDataPOJOList);
    }

    public void setSpinner_filterListener(final List<NewArrivalDataPOJO> newArrivalDataPOJOList) {
        final List<NewArrivalDataPOJO> newArrivalDataPOJOs = new ArrayList<NewArrivalDataPOJO>(newArrivalDataPOJOList);
        spinner_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TagUtils.getTag(), "on filter item selected");
//                switch (position){
//                    case 0:
//                        Log.d(TagUtils.getTag(),"on filter item selected");
//
//
//                        break;
//                    case 1:
//                        Collections.reverse(new ArrayList<>(newArrivalDataPOJOList));
//                        break;
//                }
//                Log.d(TagUtils.getTag(),"before filter:-"+newArrivalDataPOJOs.toString());
                if(position!=0) {
                    Collections.reverse(newArrivalDataPOJOs);
//                Log.d(TagUtils.getTag(),"after filter:-"+newArrivalDataPOJOs.toString());
                    inflateList(newArrivalDataPOJOs);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void sortByPrice(List<NewArrivalDataPOJO> newArrivalDataPOJOList) {
        Collections.sort(newArrivalDataPOJOList, new Comparator<NewArrivalDataPOJO>() {
            @Override
            public int compare(NewArrivalDataPOJO o1, NewArrivalDataPOJO o2) {
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

    public void inflateList(List<NewArrivalDataPOJO> newArrivalDataPOJOList) {
        NewArrivalsAdapter adapter = new NewArrivalsAdapter(getActivity(), newArrivalDataPOJOList);
        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(getActivity(), 3);
        rv_new_arrivals.setLayoutManager(gridLayoutManager);
        rv_new_arrivals.setHasFixedSize(true);
        rv_new_arrivals.setItemAnimator(new DefaultItemAnimator());
        rv_new_arrivals.setAdapter(adapter);
    }
}
