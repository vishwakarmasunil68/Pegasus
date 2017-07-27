package com.bjain.pegasus.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bjain.pegasus.R;
import com.bjain.pegasus.utils.Pref;
import com.bjain.pegasus.utils.StringUtils;
import com.bjain.pegasus.utils.TagUtils;
import com.bjain.pegasus.utils.ToastClass;
import com.bjain.pegasus.webservice.WebServiceBase;
import com.bjain.pegasus.webservice.WebServicesCallBack;
import com.bjain.pegasus.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountActivity extends AppCompatActivity implements WebServicesCallBack{
    private static final String UPDATE_PROFILE_API = "update_profile_api";
    private final String TAG=getClass().getSimpleName();
    private final static String REGISTER_API_CALL="register_api_call";


    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_middle_name)
    EditText et_middle_name;
    @BindView(R.id.et_last_name)
    EditText et_last_name;
    @BindView(R.id.et_email)
    EditText et_email;

    @BindView(R.id.btn_update)
    Button btn_update;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("My Account");


        et_name.setText(Pref.GetStringPref(getApplicationContext(), StringUtils.FIRST_NAME,""));
        et_middle_name.setText(Pref.GetStringPref(getApplicationContext(), StringUtils.MIDDLE_NAME,""));
        et_last_name.setText(Pref.GetStringPref(getApplicationContext(), StringUtils.LAST_NAME,""));
        et_email.setText(Pref.GetStringPref(getApplicationContext(), StringUtils.EMAIL_ID,""));

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    public void updateProfile(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("first_name", et_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("last_name", et_last_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("email", et_email.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("user_id", Pref.GetStringPref(getApplicationContext(),StringUtils.ENTITY_ID,"")));
        new WebServiceBase(nameValuePairs, this, UPDATE_PROFILE_API).execute(WebServicesUrls.UPDATE_PROFILE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case UPDATE_PROFILE_API:
                    updateProfileResponse(response);
                break;
        }
    }
    public void updateProfileResponse(String response){
        Log.d(TagUtils.getTag(),"update response:-"+response);
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("Success").equals("true")){
                ToastClass.showShortToast(getApplicationContext(),"Profile Updated Successfully");
                Pref.SetStringPref(getApplicationContext(),StringUtils.FIRST_NAME,et_name.getText().toString());
                Pref.SetStringPref(getApplicationContext(),StringUtils.MIDDLE_NAME,et_middle_name.getText().toString());
                Pref.SetStringPref(getApplicationContext(),StringUtils.LAST_NAME,et_last_name.getText().toString());
                Pref.SetStringPref(getApplicationContext(),StringUtils.EMAIL_ID,et_email.getText().toString());
            }else{
                ToastClass.showShortToast(getApplicationContext(),"Profile Not Updated");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Profile Not Updated");
        }
    }
}
