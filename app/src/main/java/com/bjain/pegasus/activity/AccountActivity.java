package com.bjain.pegasus.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.bjain.pegasus.R;
import com.bjain.pegasus.utils.Pref;
import com.bjain.pegasus.utils.StringUtils;
import com.bjain.pegasus.webservice.WebServicesCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountActivity extends AppCompatActivity implements WebServicesCallBack{
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

    }
}
