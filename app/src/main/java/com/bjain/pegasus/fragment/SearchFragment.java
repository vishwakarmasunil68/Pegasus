package com.bjain.pegasus.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.bjain.pegasus.R;
import com.bjain.pegasus.activity.HomeActivity;
import com.bjain.pegasus.adapter.SearchProductAdapter;
import com.bjain.pegasus.pojo.newsearch.NewSearchPOJO;
import com.bjain.pegasus.pojo.newsearch.NewSearchResultPOJO;
import com.bjain.pegasus.utils.ToastClass;
import com.bjain.pegasus.webservice.WebServiceBase;
import com.bjain.pegasus.webservice.WebServicesCallBack;
import com.bjain.pegasus.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 26-05-2017.
 */

public class SearchFragment extends Fragment implements WebServicesCallBack {

    private final String TAG = getClass().getSimpleName();
    private final String SEARCH_PRODUCT_API = "search_product_api";


    @BindView(R.id.rv_search)
    RecyclerView rv_search;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.ll_nav_cat)
    LinearLayout ll_nav_cat;
    @BindView(R.id.spinner_sort)
    Spinner spinner_sort;
    @BindView(R.id.spinner_filter)
    Spinner spinner_filter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_search.post(new Runnable() {
            @Override
            public void run() {
                et_search.requestFocus();
                InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imgr.showSoftInput(et_search, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_search.getText().toString().length() > 0) {
                    callSearchAPI(et_search.getText().toString());
                    rv_search.setVisibility(View.VISIBLE);
                } else {
                    List<NewSearchResultPOJO> searchResultPOJOList = new ArrayList<>();
                    inflateAdapter(searchResultPOJOList);
                    rv_search.setVisibility(View.GONE);
                }
            }
        });
        et_search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
//                    Toast.makeText(getActivity().getApplicationContext(), et_search.getText(), Toast.LENGTH_SHORT).show();
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });
        ll_nav_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity = (HomeActivity) getActivity();
                homeActivity.openCatNavDrawer();
            }
        });
    }

    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void callSearchAPI(String product_name) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("product_name", product_name));
        new WebServiceBase(nameValuePairs, getActivity(), this, SEARCH_PRODUCT_API, false).execute(WebServicesUrls.SEARCH_PRODUCT_URL);
    }

    public void parseSearchResponse(String response) {
        Log.d(TAG, "search response:-" + response);
        try {
            Gson gson = new Gson();
            NewSearchPOJO searchPOJO = gson.fromJson(response, NewSearchPOJO.class);
//            List<String> stringList=new ArrayList<>();

            if(searchPOJO.getSuccess().equals("true")){
//                Set<NewSearchResultPOJO> newSearchResultPOJOs=new HashSet<>();
//                for(String s:searchPOJO.getStringList()){
//                    if(!s.equals("false")){
//                        Gson gson1=new Gson();
//                        NewSearchResultPOJO newSearchResultPOJO=gson1.fromJson(s,NewSearchResultPOJO.class);
//                        newSearchResultPOJOs.add(newSearchResultPOJO);
//                    }
//                }
//                List<NewSearchResultPOJO> newSearchResultPOJOList=new ArrayList<>(newSearchResultPOJOs);
                List<NewSearchResultPOJO> newSearchResultPOJOList=new ArrayList<>();
                for(String s:searchPOJO.getStringList()){
                    if(!s.equals("false")) {
                        Gson gson1 = new Gson();
                        NewSearchResultPOJO newSearchResultPOJO=gson1.fromJson(s,NewSearchResultPOJO.class);
                        if(!newSearchResultPOJOList.contains(newSearchResultPOJO)){
                            newSearchResultPOJOList.add(newSearchResultPOJO);
                        }
                    }
                }
                inflateAdapter(newSearchResultPOJOList);
            }else{
                ToastClass.showShortToast(getActivity().getApplicationContext(), "No Product Found");
            }




//            Set<String> newSearchResultPOJOSet = new HashSet<>();
//            List<NewSearchResultPOJO> searchResultPOJOList = new ArrayList<>();
//            if (searchPOJO.getSuccess().equals("true")) {
//                for (String s : searchPOJO.getStringList()) {
//                    if(!s.equals("false")) {
//                        Gson gson1 = new Gson();
//                        NewSearchResultPOJO searchResultPOJO = gson1.fromJson(s, NewSearchResultPOJO.class);
//                        newSearchResultPOJOSet.add(searchResultPOJO.getProduct_id());
//                        searchResultPOJOList.add(searchResultPOJO);
//                    }
//                }
//
//
//                if(newSearchResultPOJOSet.size()>0){
//                    List<NewSearchResultPOJO> newSearchResultPOJOList=new ArrayList<>();
//                    for(String s:newSearchResultPOJOSet){
//                        boolean val=false;
//                        for(NewSearchResultPOJO newSearchResultPOJO:searchResultPOJOList){
//                            if(s.equals(newSearchResultPOJO.getProduct_id())){
//                                val=true;
//                            }
//                        }
//                        if(val){
//                            newSearchResultPOJOList.add(searchResultPOJOList.get(0));
//                        }
//                    }
//
//                    inflateAdapter(newSearchResultPOJOList);
//                }
//            } else {
//                ToastClass.showShortToast(getActivity().getApplicationContext(), "No Product Found");
//            }




//            inflateAdapter(searchResultPOJOList);

//            final List<SearchResultPOJO> newArrivalDataPOJOList1 = new ArrayList<SearchResultPOJO>(searchResultPOJOList);
//            switch (spinner_sort.getSelectedItemPosition()) {
//                case 0:
//                    sortByPosition(searchResultPOJOList);
//                    break;
//                case 1:
//                    sortByName(searchResultPOJOList);
//                    break;
//                case 2:
//                    sortByPrice(searchResultPOJOList);
//                    break;
//            }
//
//            spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    if (searchResultPOJOList.size() > 0) {
//                        switch (position) {
//                            case 0:
//                                sortByPosition(new ArrayList<SearchResultPOJO>(newArrivalDataPOJOList1));
//                                break;
//                            case 1:
//                                sortByName(searchResultPOJOList);
//                                break;
//                            case 2:
//                                sortByPrice(searchResultPOJOList);
//                                break;
//                        }
//                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });

        } catch (Exception e) {
            e.printStackTrace();
            List<NewSearchResultPOJO> searchResultPOJOList = new ArrayList<>();
            inflateAdapter(searchResultPOJOList);
        }
    }


//    public void sortByPosition(List<SearchResultPOJO> newArrivalDataPOJOList) {
//        setFilterNullListener();
//        setSpinner_filterListener(newArrivalDataPOJOList);
//        inflateAdapter(newArrivalDataPOJOList);
//    }
//
//    public void sortByName(List<SearchResultPOJO> newArrivalDataPOJOList) {
//        Collections.sort(newArrivalDataPOJOList, new Comparator<SearchResultPOJO>() {
//            @Override
//            public int compare(SearchResultPOJO o1, SearchResultPOJO o2) {
//                return o1.getName().compareTo(o2.getName());
//            }
//        });
//        inflateAdapter(newArrivalDataPOJOList);
//        setFilterNullListener();
//        setSpinner_filterListener(newArrivalDataPOJOList);
//    }
//
//    public void setSpinner_filterListener(final List<SearchResultPOJO> newArrivalDataPOJOList) {
//        final List<SearchResultPOJO> newArrivalDataPOJOs = new ArrayList<SearchResultPOJO>(newArrivalDataPOJOList);
//        spinner_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TagUtils.getTag(), "on filter item selected");
//                Collections.reverse(newArrivalDataPOJOs);
//                inflateAdapter(newArrivalDataPOJOs);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//
//    public void sortByPrice(List<SearchResultPOJO> newArrivalDataPOJOList) {
//        Collections.sort(newArrivalDataPOJOList, new Comparator<SearchResultPOJO>() {
//            @Override
//            public int compare(SearchResultPOJO o1, SearchResultPOJO o2) {
//                return (int) ((Double.parseDouble(o1.getPrice()) - Double.parseDouble(o2.getPrice())));
//            }
//        });
//        inflateAdapter(newArrivalDataPOJOList);
//        setFilterNullListener();
//        setSpinner_filterListener(newArrivalDataPOJOList);
//    }

    public void setFilterNullListener() {
        spinner_filter.setOnItemSelectedListener(null);
        spinner_filter.setSelection(0);
    }


    public void inflateAdapter(List<NewSearchResultPOJO> searchResultPOJOList) {
        SearchProductAdapter adapter = new SearchProductAdapter(getActivity(), searchResultPOJOList);
        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(getActivity(), 3);
        rv_search.setLayoutManager(gridLayoutManager);
        rv_search.setHasFixedSize(true);
        rv_search.setItemAnimator(new DefaultItemAnimator());
        rv_search.setAdapter(adapter);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case SEARCH_PRODUCT_API:
                parseSearchResponse(response);
                break;
        }
    }
}
