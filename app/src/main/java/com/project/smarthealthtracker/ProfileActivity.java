package com.project.smarthealthtracker;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    Button save;
    EditText firstName,lastName,password,phoneNumber,height,gender;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("View Or Edit Profile");
        save = (Button)findViewById(R.id.save);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        password = (EditText) findViewById(R.id.password);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        height = (EditText) findViewById(R.id.height);
        gender = (EditText) findViewById(R.id.gender);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        alertDialog = new AlertDialog.Builder(ProfileActivity.this).create();
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getProfile();
                        dialog.dismiss();
                    }
                });
        alertDialog.setMessage("Updated Profile Successfully");
        getProfile();

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateProfile();
            }
        });
    }

    public void getProfile(){
        progressDialog.setMessage("Getting Profile");
        progressDialog.show();

        NodeRestClient.get("/getProfileMobile",null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject obj) {
                try{
                    firstName.setText(obj.getString("firstname"));
                    lastName.setText(obj.getString("lastname"));
                    password.setText(obj.getString("password"));
                    phoneNumber.setText(obj.getString("phonenumber"));
                    height.setText(obj.getString("height"));
                    gender.setText(obj.getString("gender"));
                    progressDialog.dismiss();
                }catch(Exception e){

                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable e , JSONArray a) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

            }
        });
    }

    public void updateProfile(){
        progressDialog.setMessage("Updating Profile");
        progressDialog.show();
        RequestParams params = new RequestParams();
        params.put("firstName",firstName.getText().toString());
        params.put("lastName",lastName.getText().toString());
        params.put("password",password.getText().toString());
        params.put("phoneNumber",phoneNumber.getText().toString());
        params.put("height",height.getText().toString());
        params.put("gender",gender.getText().toString());

        NodeRestClient.get("/updateProfileMobile",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject obj) {
                try{
                    if (obj.getInt("statusCode")== 200){
                        progressDialog.dismiss();
                        alertDialog.show();
                    }
                }catch(Exception e){

                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable e , JSONArray a) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.dashboard_page:
                Intent profileActivity = new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(profileActivity);
                finish();
                return true;
            case R.id.calories_page:
                Intent i = new Intent(getApplicationContext(),CaloriesActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.steps_page:
                Intent stepsActivity = new Intent(getApplicationContext(),StepsActivity.class);
                startActivity(stepsActivity);
                finish();
                return true;
            case R.id.log_page:
                Intent logActivity = new Intent(getApplicationContext(),LogActivity.class);
                startActivity(logActivity);
                finish();
                return true;
            case R.id.contacts_page:
                Intent contactsActivity = new Intent(getApplicationContext(),ContactsActivity.class);
                startActivity(contactsActivity);
                finish();
                return true;
            case R.id.profile_page:
                return true;
            case R.id.goals_page:
                Intent goalsActivity = new Intent(getApplicationContext(),GoalsActivity.class);
                startActivity(goalsActivity);
                finish();
                return true;
            case R.id.weight_page:
                Intent weightActivity = new Intent(getApplicationContext(),WeightActivity.class);
                startActivity(weightActivity);
                finish();
                return true;
            case R.id.logoutMenu:
                Intent loginActivity = new Intent(getApplicationContext(),MainActivity.class);
                loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loginActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
