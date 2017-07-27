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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.bjain.pegasus.R;
import com.bjain.pegasus.activity.HomeActivity;
import com.bjain.pegasus.adapter.NewArrivalsAdapter;
import com.bjain.pegasus.pojo.newarrival.NewArrivalDataPOJO;
import com.bjain.pegasus.pojo.newbookitopojo.NewBookitoPOJO;
import com.bjain.pegasus.pojo.newbookitopojo.NewBookitoResultPOJO;
import com.bjain.pegasus.utils.TagUtils;
import com.bjain.pegasus.utils.ToastClass;
import com.bjain.pegasus.webservice.WebServiceBase;
import com.bjain.pegasus.webservice.WebServicesCallBack;
import com.bjain.pegasus.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 20-06-2017.
 */

public class BookitoFragment extends Fragment implements WebServicesCallBack {
    private static final String BOOKITO_API_CALL = "bookito_api_call";
    private static final String BOOKITO_API_CALL_AGE = "bookito_api_call_age";
    @BindView(R.id.rv_bookito)
    RecyclerView rv_bookito;
    @BindView(R.id.spinner_sort)
    Spinner spinner_sort;
    @BindView(R.id.spinner_filter)
    Spinner spinner_filter;
    @BindView(R.id.ll_barcode_scan)
    LinearLayout ll_barcode_scan;

    @BindView(R.id.ll_nav_cat)
    LinearLayout ll_nav_cat;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_bookit, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        new GetWebServices(getActivity(), this, BOOKITO_API_CALL).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, WebServicesUrls.BOOKITO_URL);

        ll_nav_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity = (HomeActivity) getActivity();
                homeActivity.openCatNavDrawer();
            }
        });
        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity = (HomeActivity) getActivity();
                homeActivity.showSearchFragment();
            }
        });
        ll_barcode_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity = (HomeActivity) getActivity();
                homeActivity.showBarcodeFragment();
            }
        });

        List<String> sortby = new ArrayList<>();
        sortby.add("Age Group");
        sortby.add("Price");
        sortby.add("Gender");
        ArrayAdapter<String> sortadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sortby);
        sortadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sort.setAdapter(sortadapter);
        setFilterSpinner(0);
        spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        setFilterSpinner(0);
                        break;
                    case 1:
                        setFilterSpinner(1);
                        break;
                    case 2:
                        setFilterSpinner(2);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setFilterSpinner(int position) {
        spinner_filter.setOnItemSelectedListener(null);
        spinner_filter.setSelection(0);
        final List<String> filterlist = new ArrayList<>();
        String url = "";
        switch (position) {
            case 0:
                url = WebServicesUrls.BOOKITO_AGE_URL;
                filterlist.add("ALL");
                filterlist.add("1 Years");
                filterlist.add("2 Years");
                filterlist.add("3 Years");
                filterlist.add("4 Years");
                filterlist.add("5 Years");
                filterlist.add("6 Years");
                filterlist.add("7 Years");
                filterlist.add("8 Years");
                filterlist.add("9 Years");
                filterlist.add("10 Years");
                filterlist.add("11 Years");
                filterlist.add("12 Years");
                break;
            case 1:
                url = WebServicesUrls.BOOKITO_PRICE_URL;
                filterlist.add("ALL");
                filterlist.add("PRICE UNDER INR100");
                filterlist.add("PRICE UNDER INR250");
                filterlist.add("PRICE UNDER INR500");
                filterlist.add("PRICE UNDER INR1000");
                break;
            case 2:
                url = WebServicesUrls.BOOKITO_GENDER_URL;
                filterlist.add("ALL");
                filterlist.add("BOYS");
                filterlist.add("GIRLS");
                break;
        }


        ArrayAdapter<String> filter_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, filterlist);
        filter_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_filter.setAdapter(filter_adapter);

        final String finalUrl = url;
        spinner_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                callBooKTypeAPI(finalUrl, filterlist.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void callBooKTypeAPI(String url, String param) {
        if (url.equals(WebServicesUrls.BOOKITO_AGE_URL)) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("type", param));
            new WebServiceBase(nameValuePairs, getActivity(), this, BOOKITO_API_CALL_AGE).execute(url);
        } else {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("type", param));
            new WebServiceBase(nameValuePairs, getActivity(), this, BOOKITO_API_CALL).execute(url);
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case BOOKITO_API_CALL:
                parseBookitoApiResponse(response);
                break;
            case BOOKITO_API_CALL_AGE:
                parseBookitoAgeResponse(response);
//                parseBookitoApiResponse(response);
                break;
        }
    }

    public void parseBookitoAgeResponse(String response) {
        Log.d(TagUtils.getTag(), "response:-" + response);
        try {
            Gson gson = new Gson();
            NewBookitoPOJO newArrivalPOJO = gson.fromJson(response, NewBookitoPOJO.class);
            if (newArrivalPOJO.getSuccess().equals("true")) {
                Set<String> product_ids = new HashSet<>();
                List<NewBookitoResultPOJO> newBookitoResultPOJOList = new ArrayList<>();
                for (String s : newArrivalPOJO.getResult()) {
                    if (!s.equals("false")) {
                        Gson gson1 = new Gson();
                        NewBookitoResultPOJO newBookitoResultPOJO = gson1.fromJson(s, NewBookitoResultPOJO.class);
//                        Log.d(TagUtils.getTag(),"product_ids:-"+newBookitoResultPOJO.getEntity_id());
                        product_ids.add(newBookitoResultPOJO.getEntity_id());
                        newBookitoResultPOJOList.add(newBookitoResultPOJO);
                    }
                }

                List<NewArrivalDataPOJO> newArrivalDataPOJOList = new ArrayList<>();

                for(NewBookitoResultPOJO newBookitoResultPOJO:newBookitoResultPOJOList){
                    NewArrivalDataPOJO newArrivalDataPOJO=new NewArrivalDataPOJO();
                    newArrivalDataPOJO.setProduct_name(newBookitoResultPOJO.getValue());
                    newArrivalDataPOJO.setProduct_id(newBookitoResultPOJO.getEntity_id());
                    newArrivalDataPOJO.setDiscount_price(newBookitoResultPOJO.getDiscount_price());
                    newArrivalDataPOJO.setMain_price(newBookitoResultPOJO.getMain_price());
                    newArrivalDataPOJO.setSku(newBookitoResultPOJO.getSku());
                    newArrivalDataPOJO.setProduct_image(newBookitoResultPOJO.getImage_url());

                    newArrivalDataPOJOList.add(newArrivalDataPOJO);
                }

                inflateList(newArrivalDataPOJOList);
//                Log.d(TagUtils.getTag(), "resultpojo:-" + newBookitoResultPOJOList.toString());
//                final List<NewArrivalDataPOJO> newArrivalDataPOJOList = new ArrayList<>();
//                for (String product_id : product_ids) {
//                    NewArrivalDataPOJO newArrivalDataPOJO = new NewArrivalDataPOJO();
//                    for (NewBookitoResultPOJO newArrivalResultPOJO : newBookitoResultPOJOList) {
//                        if (product_id.equals(newArrivalResultPOJO.getEntity_id())) {
//                            switch (newArrivalResultPOJO.getAttribute_id()) {
//                                case "71":
//                                    newArrivalDataPOJO.setProduct_id(product_id);
//                                    newArrivalDataPOJO.setProduct_name(newArrivalResultPOJO.getValue());
//                                    newArrivalDataPOJO.setDiscount_price(newArrivalResultPOJO.getDiscount_price());
//                                    newArrivalDataPOJO.setMain_price(newArrivalResultPOJO.getMain_price());
//                                    break;
//                                case "85":
//                                    newArrivalDataPOJO.setProduct_image(newArrivalResultPOJO.getValue());
//                                    break;
//                                case "222":
//                                    newArrivalDataPOJO.setSku(newArrivalResultPOJO.getValue());
//                                    break;
//                            }
//                        }
//                    }
//                    newArrivalDataPOJOList.add(newArrivalDataPOJO);
//                }

                inflateList(newArrivalDataPOJOList);
            }else{
                List<NewArrivalDataPOJO> newArrivalDataPOJOList = new ArrayList<>();
                inflateList(newArrivalDataPOJOList);
                ToastClass.showShortToast(getActivity().getApplicationContext(), "No Products Found.");
            }
        } catch (Exception e) {
            List<NewArrivalDataPOJO> newArrivalDataPOJOList = new ArrayList<>();
            inflateList(newArrivalDataPOJOList);
            e.printStackTrace();
        }
    }

    public void parseBookitoApiResponse(String response) {
        Log.d(TagUtils.getTag(), "response:-" + response);
        try {
            Gson gson = new Gson();
            NewBookitoPOJO newArrivalPOJO = gson.fromJson(response, NewBookitoPOJO.class);
            if (newArrivalPOJO.getSuccess().equals("true")) {
                Set<String> product_ids = new HashSet<>();
                List<NewBookitoResultPOJO> newBookitoResultPOJOList = new ArrayList<>();
                for (String s : newArrivalPOJO.getResult()) {
                    if (!s.equals("false")) {
                        Gson gson1 = new Gson();
                        NewBookitoResultPOJO newBookitoResultPOJO = gson1.fromJson(s, NewBookitoResultPOJO.class);
//                        Log.d(TagUtils.getTag(),"product_ids:-"+newBookitoResultPOJO.getEntity_id());
                        product_ids.add(newBookitoResultPOJO.getEntity_id());
                        newBookitoResultPOJOList.add(newBookitoResultPOJO);
                    }
                }
                Log.d(TagUtils.getTag(), "resultpojo:-" + newBookitoResultPOJOList.toString());
                final List<NewArrivalDataPOJO> newArrivalDataPOJOList = new ArrayList<>();
                for (String product_id : product_ids) {
                    NewArrivalDataPOJO newArrivalDataPOJO = new NewArrivalDataPOJO();
                    for (NewBookitoResultPOJO newArrivalResultPOJO : newBookitoResultPOJOList) {
                        if (product_id.equals(newArrivalResultPOJO.getEntity_id())) {
                            switch (newArrivalResultPOJO.getAttribute_id()) {
                                case "71":
                                    newArrivalDataPOJO.setProduct_id(product_id);
                                    newArrivalDataPOJO.setProduct_name(newArrivalResultPOJO.getValue());
                                    newArrivalDataPOJO.setDiscount_price(newArrivalResultPOJO.getDiscount_price());
                                    newArrivalDataPOJO.setMain_price(newArrivalResultPOJO.getMain_price());
                                    newArrivalDataPOJO.setProduct_image(newArrivalResultPOJO.getImage_url());
                                    break;
//                                case "85":
//                                    newArrivalDataPOJO.setProduct_image(newArrivalResultPOJO.getValue());
//                                    break;
                                case "222":
                                    newArrivalDataPOJO.setSku(newArrivalResultPOJO.getValue());
                                    break;
                            }
                        }
                    }
                    newArrivalDataPOJOList.add(newArrivalDataPOJO);
                }

                inflateList(newArrivalDataPOJOList);

//                NewArrivalsAdapter adapter = new NewArrivalsAdapter(getActivity(), newArrivalDataPOJOList);
//                GridLayoutManager gridLayoutManager
//                        = new GridLayoutManager(getActivity(), 3);
//                rv_bookito.setLayoutManager(gridLayoutManager);
//                rv_bookito.setHasFixedSize(true);
//                rv_bookito.setItemAnimator(new DefaultItemAnimator());
//                rv_bookito.setAdapter(adapter);
//                final List<NewArrivalDataPOJO> newArrivalDataPOJOList1 = new ArrayList<>(newArrivalDataPOJOList);
//                sortByPosition(newArrivalDataPOJOList);
//                spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        if (newArrivalDataPOJOList.size() > 0) {
//                            switch (position) {
//                                case 0:
////                                    sortByPosition(new ArrayList<NewArrivalDataPOJO>(newArrivalDataPOJOList1));
//                                    break;
//                                case 1:
//                                    sortByName(newArrivalDataPOJOList);
//                                    break;
//                                case 2:
//                                    sortByPrice(newArrivalDataPOJOList);
//                                    break;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });


            } else {
                List<NewArrivalDataPOJO> newArrivalDataPOJOList = new ArrayList<>();
                inflateList(newArrivalDataPOJOList);
                ToastClass.showShortToast(getActivity().getApplicationContext(), "No Products Found.");
            }

        } catch (Exception e) {
            List<NewArrivalDataPOJO> newArrivalDataPOJOList = new ArrayList<>();
            inflateList(newArrivalDataPOJOList);
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
                if (position != 0) {
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
        rv_bookito.setLayoutManager(gridLayoutManager);
        rv_bookito.setHasFixedSize(true);
        rv_bookito.setItemAnimator(new DefaultItemAnimator());
        rv_bookito.setAdapter(adapter);
    }
}
