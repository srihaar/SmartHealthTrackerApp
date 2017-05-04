package com.project.smarthealthtracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    TextView register,email,password;
    Button loginAction;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login Here");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");

        register = (TextView) findViewById(R.id.tvRegisterLink);
        loginAction = (Button) findViewById(R.id.bLogin);
        email = (TextView)findViewById(R.id.email);
        password = (TextView) findViewById(R.id.passwordL);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
            }
        });

        loginAction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                RequestParams params = new RequestParams();
//                params.put("email",email.getText().toString());
//                params.put("password",password.getText().toString());
//                progressDialog.show();
//                NodeRestClient.get("/loginMobile",params,new JsonHttpResponseHandler(){
//                    @Override
//                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject obj) {
//                        progressDialog.hide();
//                        try{
//                            if(obj.getInt("statusCode")== 200){
//                                Intent i = new Intent(getApplicationContext(),DashboardActivity.class);
//                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(i);
//                            }else{
//                                Toast.makeText(LoginActivity.this,"Incorrect Credentials",Toast.LENGTH_SHORT).show();
//                            }
//
//                        }catch(Exception e){
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable e , JSONArray a) {
//                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//
//                    }
//                });
                //code for now
                Intent i = new Intent(getApplicationContext(),DailyDataActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });
    }
}
