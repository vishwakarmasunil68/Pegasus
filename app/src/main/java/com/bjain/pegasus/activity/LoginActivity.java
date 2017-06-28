package com.bjain.pegasus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjain.pegasus.R;
import com.bjain.pegasus.utils.FileUtils;
import com.bjain.pegasus.utils.Pref;
import com.bjain.pegasus.utils.StringUtils;
import com.bjain.pegasus.webservice.WebServiceBase;
import com.bjain.pegasus.webservice.WebServicesCallBack;
import com.bjain.pegasus.webservice.WebServicesUrls;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements WebServicesCallBack {

    private final String TAG = getClass().getSimpleName();
    private final String LOGIN_API_CALL = "login_api_call";
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_fb_login)
    ImageView iv_fb_login;
    @BindView(R.id.tv_dont_have_account)
    TextView tv_dont_have_account;

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        RequestData();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Login");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckValidation(et_email, et_password)) {
                    callLoginAPI(et_email.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Mandatory Data", Toast.LENGTH_LONG).show();
                }
            }
        });
        iv_fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FbIntegration();
            }
        });

        tv_dont_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                finish();
            }
        });
    }

    public void FbIntegration() {
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
    }

    public void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("sunil", response.toString());
                JSONObject json = response.getJSONObject();
                try {
                    if (json != null) {
//                        String text = "<b>Name :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link");
//                        Log.d("sunil",text);
                        Log.d("sunil", json.getString("name"));
                        Log.d("sunil", json.getString("link"));
                        Log.d("sunil", json.getString("id"));
                        Log.d("sunil", json.getString("email"));
                        String path = FileUtils.BASE_FILE_PATH;
                        File file = new File(path + File.separator + "fb_profile.png");
                        String profile_url = "https://graph.facebook.com/" + json.getString("id") + "/picture?type=large";

//                        getBitmapCallApi(profile_url,json.getString("name"),
//                                json.getString("email"),"");
                        String request_data = json.getString("name") + "," + "" + "," +
                                "" + "," + "1234";

                        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                        callLoginAPI(json.getString("email"));
                    }

                } catch (JSONException e) {
                    Log.d("profile", e.toString());
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }


    public boolean CheckValidation(EditText... ets) {
        boolean con = true;
        for (EditText editText : ets) {
            if (editText.getText().toString().length() == 0) {
                con = false;
            }
        }
        return con;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void callLoginAPI(String email) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("reg_email", email));
        new WebServiceBase(nameValuePairs, this, LOGIN_API_CALL).execute(WebServicesUrls.LOGIN_API_URL);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case LOGIN_API_CALL:
                parseLoginData(response);
                break;
        }
    }

    public void parseLoginData(String response) {
        Log.d(TAG, "response:-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("Success").equals("true")) {
                Pref.SetStringPref(getApplicationContext(), StringUtils.QUOTOID,jsonObject.optString("quoto_id"));
                JSONArray jsonArray = jsonObject.optJSONArray("Result");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject innerObject = jsonArray.optJSONObject(i);
                    try {
                        String attribute_id = innerObject.optString("attribute_id");
                        switch (attribute_id) {
                            case "5":
                                Pref.SetStringPref(getApplicationContext(), StringUtils.FIRST_NAME, innerObject.optString("value"));
                                break;
                            case "6":
                                Pref.SetStringPref(getApplicationContext(), StringUtils.MIDDLE_NAME, innerObject.optString("value"));
                                break;
                            case "7":
                                Pref.SetStringPref(getApplicationContext(), StringUtils.LAST_NAME, innerObject.optString("value"));
                                break;
                            case "12":
                                Pref.SetStringPref(getApplicationContext(), StringUtils.PASSWORD, innerObject.optString("value"));
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if ((i + 1) == (jsonArray.length()-1)) {
                        Pref.SetStringPref(getApplicationContext(), StringUtils.ENTITY_ID, innerObject.optString("value"));
                    }
                    if ((i + 1) == jsonArray.length()) {
                        Pref.SetStringPref(getApplicationContext(), StringUtils.EMAIL_ID, innerObject.optString("value"));
                    }
                }
//                JSONObject firstJsonObject=jsonArray.optJSONObject(0);
//                JSONObject middlejsonJsonObject=jsonArray.optJSONObject(1);
//                JSONObject lastjsoJsonObject=jsonArray.optJSONObject(2);
//                JSONObject emailJsonObject=jsonArray.optJSONObject(5);
//                JSONObject entiyjsJsonObject=jsonArray.optJSONObject(4);
//
//
//                String first_name=firstJsonObject.optString("value");
//                String last_name=lastjsoJsonObject.optString("value");
//                String middle_name=middlejsonJsonObject.optString("value");
//                String email=emailJsonObject.optString("value");
//                String entity=entiyjsJsonObject.optString("value");
//
//                Log.d(TAG,"first_name:-"+first_name);
//                Log.d(TAG,"last_name:-"+last_name);
//                Log.d(TAG,"middle_name:-"+middle_name);
//                Log.d(TAG,"email:-"+email);
//                Log.d(TAG,"entity:-"+entity);
//
//                Pref.SetStringPref(getApplicationContext(),StringUtils.FIRST_NAME,first_name);
//                Pref.SetStringPref(getApplicationContext(),StringUtils.MIDDLE_NAME,middle_name);
//                Pref.SetStringPref(getApplicationContext(),StringUtils.LAST_NAME,last_name);
//                Pref.SetStringPref(getApplicationContext(),StringUtils.EMAIL_ID,email);
//                Pref.SetStringPref(getApplicationContext(),StringUtils.ENTITY_ID,entity);

                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, true);

                Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.d(TAG,e.toString());
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
