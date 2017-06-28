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
import android.widget.Button;

import com.appyvet.rangebar.RangeBar;
import com.bjain.pegasus.R;
import com.bjain.pegasus.adapter.NewArrivalsAdapter;
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

public class BrowseByAgeFragment extends Fragment implements WebServicesCallBack{
    String age;
    @BindView(R.id.range_bar)
    RangeBar range_bar;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.rv_browse_by_age)
    RecyclerView rv_browse_by_age;
    String initialValue="";String finalValue="";

    private final String CALL_BROWSE_BY_API_API="call_browse_by_age_api";

    public BrowseByAgeFragment(String age,String initialValue,String finalValue){
        this.age=age;
        this.initialValue=initialValue;
        this.finalValue=finalValue;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_browse_by_age,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callAgeAPI(age);
        range_bar.setLeft(Integer.parseInt(initialValue));
        range_bar.setRight(Integer.parseInt(finalValue));
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAgeAPI(range_bar.getLeftPinValue());
            }
        });
    }

    public void callAgeAPI(String age){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("age", age));
        new WebServiceBase(nameValuePairs, getActivity(),this, CALL_BROWSE_BY_API_API).execute(WebServicesUrls.BROWSE_BY_AGE_URL);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case CALL_BROWSE_BY_API_API:
                parseCallBrowseResponse(response);
                break;
        }
    }

    public void parseCallBrowseResponse(String response){
        Log.d(TagUtils.getTag(),"browse response:-"+response);
        try{
            Gson gson=new Gson();
            NewArrivalPOJO newArrivalPOJO=gson.fromJson(response,NewArrivalPOJO.class);
            if(newArrivalPOJO.getSuccess().equals("true")){
                Set<String> product_ids=new HashSet<>();
                for(NewArrivalResultPOJO newArrivalResultPOJO:newArrivalPOJO.getNewArrivalResultPOJOList()){
                    product_ids.add(newArrivalResultPOJO.getProductId());
                }

                List<NewArrivalDataPOJO> newArrivalDataPOJOList=new ArrayList<>();
                for(String product_id:product_ids){
                    NewArrivalDataPOJO newArrivalDataPOJO=new NewArrivalDataPOJO();
                    for(NewArrivalResultPOJO newArrivalResultPOJO:newArrivalPOJO.getNewArrivalResultPOJOList()){
                        if(product_id.equals(newArrivalResultPOJO.getProductId())){
                            switch (newArrivalResultPOJO.getAttributeId()){
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


                NewArrivalsAdapter adapter = new NewArrivalsAdapter(getActivity(), newArrivalDataPOJOList);
                GridLayoutManager gridLayoutManager
                        = new GridLayoutManager(getActivity(), 3);
                rv_browse_by_age.setLayoutManager(gridLayoutManager);
                rv_browse_by_age.setHasFixedSize(true);
                rv_browse_by_age.setItemAnimator(new DefaultItemAnimator());
                rv_browse_by_age.setAdapter(adapter);

            }else{
                ToastClass.showShortToast(getActivity().getApplicationContext(),"No Products Found.");
            }

        }
        catch (Exception e){
            ToastClass.showShortToast(getActivity().getApplicationContext(),"Something went wrong");
            e.printStackTrace();
        }
    }
}
