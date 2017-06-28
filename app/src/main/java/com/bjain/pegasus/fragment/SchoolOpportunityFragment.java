package com.bjain.pegasus.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjain.pegasus.R;
import com.bjain.pegasus.activity.HomeActivity;
import com.bjain.pegasus.utils.ToastClass;
import com.bjain.pegasus.webservice.WebServiceBase;
import com.bjain.pegasus.webservice.WebServicesCallBack;
import com.bjain.pegasus.webservice.WebServicesUrls;
import com.bumptech.glide.Glide;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 12-06-2017.
 */

public class SchoolOpportunityFragment extends Fragment implements WebServicesCallBack{

    private static final String SCHOOL_CONNECT_API = "school_connect_api";
    @BindView(R.id.iv_school_books)
    ImageView iv_school_books;
    @BindView(R.id.iv_school_schools)
    ImageView iv_school_schools;
    @BindView(R.id.iv_school_ministry)
    ImageView iv_school_ministry;
    @BindView(R.id.iv_school_curriculum)
    ImageView iv_school_curriculum;
    @BindView(R.id.iv_pegasus_banner)
    ImageView iv_pegasus_banner;
    @BindView(R.id.iv_activities)
    ImageView iv_activities;
    @BindView(R.id.iv_curriculum)
    ImageView iv_curriculum;
    @BindView(R.id.iv_books_offering)
    ImageView iv_books_offering;

    @BindView(R.id.tv_book_seller)
    TextView tv_book_seller;
    @BindView(R.id.tv_activity_read_more)
    TextView tv_activity_read_more;
    @BindView(R.id.tv_curriculum)
    TextView tv_curriculum;
    @BindView(R.id.tv_books_offering)
    TextView tv_books_offering;
    @BindView(R.id.btn_query)
    Button btn_query;
    @BindView(R.id.et_school_institute)
    EditText et_school_institute;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_enquiry)
    EditText et_enquiry;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_school_opportunity,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iv_school_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) getActivity();
                homeActivity.showSchoolBooksFragment(0);
            }
        });
        iv_school_schools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) getActivity();
                homeActivity.showSchoolBooksFragment(1);
            }
        });
        iv_school_ministry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) getActivity();
                homeActivity.showSchoolBooksFragment(2);
            }
        });
        iv_school_curriculum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) getActivity();
                homeActivity.showSchoolBooksFragment(3);
            }
        });
        tv_book_seller.setVisibility(View.GONE);
        tv_books_offering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) getActivity();
                homeActivity.showGradeFragment();
            }
        });
        iv_books_offering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) getActivity();
                homeActivity.showGradeFragment();
            }
        });

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEdits(et_school_institute)||checkEdits(et_address)||
                        checkEdits(et_email)||checkEdits(et_phone)||
                checkEdits(et_enquiry)){
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("school", et_school_institute.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("address", et_address.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("email", et_email.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("phone", et_phone.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("enquiry", et_enquiry.getText().toString()));
                    new WebServiceBase(nameValuePairs,getActivity(), SchoolOpportunityFragment.this, SCHOOL_CONNECT_API).execute(WebServicesUrls.CONNECT_WITH_US);
                }
                else{
                    ToastClass.showShortToast(getActivity().getApplicationContext(),"Please Fill Form Properly");
                }

            }
        });

        Glide.with(getActivity().getApplicationContext())
                .load("http://www.pegasusforkids.com/media/wysiwyg/Pegasus-banner.png")
                .error(R.drawable.ic_school_connect)
                .placeholder(R.drawable.ic_school_connect)
                .dontAnimate()
                .into(iv_pegasus_banner);

        Glide.with(getActivity().getApplicationContext())
                .load("http://www.pegasusforkids.com/media/wysiwyg/School_Connect_Program/activities_1.jpg")
                .error(R.drawable.ic_school_connect)
                .placeholder(R.drawable.ic_school_connect)
                .dontAnimate()
                .into(iv_activities);

        Glide.with(getActivity().getApplicationContext())
                .load("http://www.pegasusforkids.com/media/wysiwyg/School_Connect_Program/teachers.jpg")
                .error(R.drawable.ic_school_connect)
                .placeholder(R.drawable.ic_school_connect)
                .dontAnimate()
                .into(iv_curriculum);

        Glide.with(getActivity().getApplicationContext())
                .load("http://www.pegasusforkids.com/media/wysiwyg/School_Connect_Program/books_offering.jpg")
                .error(R.drawable.ic_school_connect)
                .placeholder(R.drawable.ic_school_connect)
                .dontAnimate()
                .into(iv_books_offering);


        iv_activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) getActivity();
                homeActivity.showActivityFragment();
            }
        });
        tv_activity_read_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) getActivity();
                homeActivity.showActivityFragment();
            }
        });
        tv_curriculum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) getActivity();
                homeActivity.showCurriculumFragment();
            }
        });


        iv_curriculum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity= (HomeActivity) getActivity();
                homeActivity.showCurriculumFragment();
            }
        });

    }

    public boolean checkEdits(EditText editText){
        if(editText.getText().toString().length()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case SCHOOL_CONNECT_API:
                parseSchoolApi(response);
                break;
        }
    }

    public void parseSchoolApi(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("success").equals("true")){
                et_address.setText("");
                et_email.setText("");
                et_enquiry.setText("");
                et_phone.setText("");
                et_school_institute.setText("");
                ToastClass.showShortToast(getActivity().getApplicationContext(),"Thanks, your details saved Successfully");
            }else{
//                ToastClass.showShortToast(getActivity().getApplicationContext(),"");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
