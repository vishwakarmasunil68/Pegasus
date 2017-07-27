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
import android.widget.Spinner;

import com.bjain.pegasus.R;
import com.bjain.pegasus.activity.HomeActivity;
import com.bjain.pegasus.adapter.NewArrivalsAdapter;
import com.bjain.pegasus.pojo.gradeapi.GradePOJO;
import com.bjain.pegasus.pojo.gradeapi.GradeResultPOJO;
import com.bjain.pegasus.pojo.newarrival.NewArrivalDataPOJO;
import com.bjain.pegasus.pojo.newarrival.NewArrivalPOJO;
import com.bjain.pegasus.pojo.newarrival.NewArrivalResultPOJO;
import com.bjain.pegasus.utils.TagUtils;
import com.bjain.pegasus.utils.ToastClass;
import com.bjain.pegasus.webservice.WebServiceBase;
import com.bjain.pegasus.webservice.WebServicesCallBack;
import com.bjain.pegasus.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 12-06-2017.
 */

public class GradeFragment extends Fragment implements WebServicesCallBack {

    private static final String GRADE_API_CALL = "grade_api_call";
    private static final String GRADE_ACTIVITY_API_CALL= "grade_activity_api_call";

    @BindView(R.id.rv_books)
    RecyclerView rv_books;
    @BindView(R.id.spinner_grade)
    Spinner spinner_grade;
    @BindView(R.id.spinner_category)
    Spinner spinner_category;

    List<String> passgradeString;
    List<String> passcategoryString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_grade, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        passgradeString = new ArrayList<>();
        passgradeString.add("GREAD-K");
        passgradeString.add("GREAD-1");
        passgradeString.add("GREAD-2");
        passgradeString.add("GREAD-3");
        passgradeString.add("GREAD-4");
        passgradeString.add("GREAD-5");
        passgradeString.add("GREAD-6");
        passgradeString.add("GREAD-7");
        passgradeString.add("GREAD-8");

        passcategoryString = new ArrayList<>();
        passcategoryString.add("All");
        passcategoryString.add("299");
        passcategoryString.add("300");
        passcategoryString.add("301");
        passcategoryString.add("302");
        passcategoryString.add("303");
        passcategoryString.add("304");


        List<String> gradeStrings = new ArrayList<>();
        gradeStrings.add("Grade K");
        gradeStrings.add("Grade 1");
        gradeStrings.add("Grade 2");
        gradeStrings.add("Grade 3");
        gradeStrings.add("Grade 4");
        gradeStrings.add("Grade 5");
        gradeStrings.add("Grade 6");
        gradeStrings.add("Grade 7");
        gradeStrings.add("Grade 8");

//        inflateLinearLayouts(gradeStrings);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, gradeStrings);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_grade.setAdapter(arrayAdapter);


        List<String> stringList=new ArrayList<>();
        stringList.add("ALL");
        stringList.add("ACTIVITY BOOKS");
        stringList.add("CHARTS");
        stringList.add("LIBRARY BOOKS");
        stringList.add("READERS BOOKS");
        stringList.add("TEXTBOOK");
        stringList.add("WORKBOOK");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stringList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_category.setAdapter(categoryAdapter);

        spinner_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                HomeActivity homeActivity = (HomeActivity) getActivity();
//                homeActivity.setGradeCategorySelection(0, false);
                callGradeBooksFragment(0,position);
                spinner_category.setOnItemSelectedListener(null);
                spinner_category.setSelection(0);
                categoryspinnerselection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void categoryspinnerselection(){
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetSelectedCategory(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void GetSelectedCategory(int position) {
        callGradeBooksFragment(position,spinner_grade.getSelectedItemPosition());
    }


    @Override
    public void onResume() {
        super.onResume();
//        HomeActivity homeActivity = (HomeActivity) getActivity();
//        homeActivity.showOpportunityNav();
    }

    @Override
    public void onPause() {
        super.onPause();
//        HomeActivity homeActivity = (HomeActivity) getActivity();
//        homeActivity.hideOpptunityNav();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        HomeActivity homeActivity = (HomeActivity) getActivity();
        homeActivity.hideOpptunityNav();
    }

    public void gradeRVClickedPosition(int position) {
//        ToastClass.showShortToast(getActivity().getApplicationContext(), "position clicked:-" + position);
//        callGradeBooksFragment(position);
    }

    public void callGradeBooksFragment(int cat_pos, int grade_pos) {
        String grade = passgradeString.get(grade_pos);
        String category = passcategoryString.get(cat_pos);

        if(cat_pos==0) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("gread", grade));
            new WebServiceBase(nameValuePairs, getActivity(), this, GRADE_API_CALL).execute(WebServicesUrls.GRADE_URL);
        }else{
            List<String> attrs=new ArrayList<>();
            attrs.add("241");
            attrs.add("242");
            attrs.add("243");
            attrs.add("244");
            attrs.add("245");
            attrs.add("246");
            attrs.add("247");
            attrs.add("248");
            attrs.add("249");
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("act_id", category));
            nameValuePairs.add(new BasicNameValuePair("att_id", attrs.get(grade_pos)));
            new WebServiceBase(nameValuePairs, getActivity(), this, GRADE_ACTIVITY_API_CALL).execute(WebServicesUrls.NEW_GRADE_URL);
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GRADE_API_CALL:
                parseCallBrowseResponse(response);
                break;
            case GRADE_ACTIVITY_API_CALL:
                parseGradeApiActivityResponse(response);
                break;
        }
    }

    public void parseGradeApiActivityResponse(String response){
        Log.d(TagUtils.getTag(),"grade activity api call:-"+response);
        try{
            Gson gson=new Gson();
            GradePOJO gradePOJO=gson.fromJson(response,GradePOJO.class);
            if(gradePOJO.getSuccess().equals("true")){
                List<GradeResultPOJO> gradeResultPOJOList=new ArrayList<>();
                Set<String> stringSet=new HashSet<>();
                for(String s:gradePOJO.getStringList()){
                    if(!s.equals("false")){
                        Gson gson1=new Gson();
                        GradeResultPOJO gradeResultPOJO=gson1.fromJson(s,GradeResultPOJO.class);
                        gradeResultPOJOList.add(gradeResultPOJO);
                        stringSet.add(gradeResultPOJO.getEntityId());
                    }
                }
                List<NewArrivalDataPOJO> newArrivalDataPOJOList = new ArrayList<>();
                for(String entityid:stringSet){
                    NewArrivalDataPOJO newArrivalDataPOJO = new NewArrivalDataPOJO();
                    for (GradeResultPOJO gradeResultPOJO: gradeResultPOJOList) {
                        if (entityid.equals(gradeResultPOJO.getEntityId())) {
                            switch (gradeResultPOJO.getAttributeId()) {
                                case "71":
                                    newArrivalDataPOJO.setProduct_id(gradeResultPOJO.getProductId());
                                    newArrivalDataPOJO.setProduct_name(gradeResultPOJO.getValue());
                                    newArrivalDataPOJO.setDiscount_price(gradeResultPOJO.getDiscountPrice());
                                    newArrivalDataPOJO.setMain_price(gradeResultPOJO.getMainPrice());
                                    newArrivalDataPOJO.setProduct_image(gradeResultPOJO.getImage_url());
                                    break;
//                                case "85":
//                                    newArrivalDataPOJO.setProduct_image(gradeResultPOJO.getValue());
//                                    break;
                                case "222":
                                    newArrivalDataPOJO.setSku(gradeResultPOJO.getValue());
                                    break;
                            }
                        }
                    }
                    newArrivalDataPOJOList.add(newArrivalDataPOJO);
                }

                NewArrivalsAdapter adapter = new NewArrivalsAdapter(getActivity(), newArrivalDataPOJOList);
                GridLayoutManager gridLayoutManager
                        = new GridLayoutManager(getActivity(), 3);
                rv_books.setLayoutManager(gridLayoutManager);
                rv_books.setHasFixedSize(true);
                rv_books.setItemAnimator(new DefaultItemAnimator());
                rv_books.setAdapter(adapter);
            }else{
                setDefaultRecyclerview();
                ToastClass.showShortToast(getActivity().getApplicationContext(),"No Products Found.");
            }
        }catch (Exception e){
            e.printStackTrace();
            setDefaultRecyclerview();
            ToastClass.showShortToast(getActivity().getApplicationContext(),"No Products Found.");
        }
    }

    public void setDefaultRecyclerview(){
        List<NewArrivalDataPOJO> newArrivalDataPOJOList = new ArrayList<>();
        NewArrivalsAdapter adapter = new NewArrivalsAdapter(getActivity(), newArrivalDataPOJOList);
        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(getActivity(), 3);
        rv_books.setLayoutManager(gridLayoutManager);
        rv_books.setHasFixedSize(true);
        rv_books.setItemAnimator(new DefaultItemAnimator());
        rv_books.setAdapter(adapter);
    }

    public void parseCallBrowseResponse(String response) {
        Log.d(TagUtils.getTag(), "browse response:-" + response);
        try {
            Gson gson = new Gson();
            NewArrivalPOJO newArrivalPOJO = gson.fromJson(response, NewArrivalPOJO.class);
            if (newArrivalPOJO.getSuccess().equals("true")) {
                Set<String> product_ids = new HashSet<>();
                for (NewArrivalResultPOJO newArrivalResultPOJO : newArrivalPOJO.getNewArrivalResultPOJOList()) {
                    product_ids.add(newArrivalResultPOJO.getProductId());
                }

                List<NewArrivalDataPOJO> newArrivalDataPOJOList = new ArrayList<>();
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


                NewArrivalsAdapter adapter = new NewArrivalsAdapter(getActivity(), newArrivalDataPOJOList);
                GridLayoutManager gridLayoutManager
                        = new GridLayoutManager(getActivity(), 3);
                rv_books.setLayoutManager(gridLayoutManager);
                rv_books.setHasFixedSize(true);
                rv_books.setItemAnimator(new DefaultItemAnimator());
                rv_books.setAdapter(adapter);

            } else {

                loadEmptyList();
                ToastClass.showShortToast(getActivity().getApplicationContext(), "No Products Found.");
            }

        } catch (Exception e) {
            loadEmptyList();
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
            e.printStackTrace();
        }
    }

    public void loadEmptyList() {
        List<NewArrivalDataPOJO> newArrivalDataPOJOs = new ArrayList<>();
        NewArrivalsAdapter adapter = new NewArrivalsAdapter(getActivity(), newArrivalDataPOJOs);
        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(getActivity(), 3);
        rv_books.setLayoutManager(gridLayoutManager);
        rv_books.setHasFixedSize(true);
        rv_books.setItemAnimator(new DefaultItemAnimator());
        rv_books.setAdapter(adapter);
    }
}
