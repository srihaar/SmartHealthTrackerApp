package com.project.smarthealthtracker;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.entity.mime.Header;

import static com.project.smarthealthtracker.LoginActivity.MY_PREFS_NAME;

public class LogActivity extends AppCompatActivity{
    Button checkCalories,logWeightBtn,logFoodBtn;
    AsyncHttpClient client;
    TextView foodName,servings,weight,weightDate;
    ProgressDialog progressDialog,weightProgress;
    AlertDialog alertDialog,weightAlert;
    String value = "";
    String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        getSupportActionBar().setTitle("Log Food and Weight");
        checkCalories = (Button) findViewById(R.id.getCaloriesBtn);
        foodName = (TextView) findViewById(R.id.foodName);
        servings = (TextView) findViewById(R.id.servings);
        weight = (TextView) findViewById(R.id.weight);
        weightDate = (TextView) findViewById(R.id.weightDate);
        logWeightBtn = (Button) findViewById(R.id.logWeightBtn);
        logFoodBtn = (Button) findViewById(R.id.logFoodBtn);
        client = new AsyncHttpClient();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting Calories");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        alertDialog = new AlertDialog.Builder(LogActivity.this).create();
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        checkCalories.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(foodName.getText().length()>0){
                    progressDialog.show();
                    getNdbno();
                }else{
                    Toast.makeText(LogActivity.this,"Enter Food Name to check calories",Toast.LENGTH_SHORT).show();

                }
            }
        });


        weightProgress = new ProgressDialog(this);
        weightProgress.setCanceledOnTouchOutside(false);
        weightProgress.setCancelable(false);

        weightAlert = new AlertDialog.Builder(LogActivity.this).create();
        weightAlert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        weightAlert.dismiss();
                    }
                });

        logWeightBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(weight.getText().length()>0 && weightDate.getText().length() >0){
                    weightProgress.setMessage("Logging Weight");
                    weightProgress.show();
                    logWeight();
                }else{
                    Toast.makeText(LogActivity.this,"Enter both weight and date",Toast.LENGTH_SHORT).show();
                }
            }
        });

        logFoodBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(foodName.getText().length()>0 && servings.getText().length() >0){
                    weightProgress.setMessage("Logging Food");
                    weightProgress.show();
                    status = "log food";
                    getNdbno();
                }else{
                    Toast.makeText(LogActivity.this,"Enter both food name and servings",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void logFood (){
        RequestParams params = new RequestParams();
        params.put("foodName",foodName.getText().toString());
        params.put("serving",servings.getText().toString());
        params.put("calories",value);

            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            params.put("userID",prefs.getInt("userID", 0));
            NodeRestClient.get("/logFoodMobile",params,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject obj) {
                    try{
                        if(obj.getInt("statusCode") == 200){
                            weightProgress.dismiss();
                            foodName.setText("");
                            servings.setText("");
                            weightAlert.setMessage("Logged Food Successfully");
                            weightAlert.show();
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

    public void logWeight(){
        RequestParams params = new RequestParams();
        params.put("weight",weight.getText().toString());
        params.put("date",weightDate.getText().toString());
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        params.put("accessToken",prefs.getString("accessToken", null));
        NodeRestClient.get("/logWeightMobile",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject obj) {
                try{
                    if(obj.getInt("statusCode") == 200){
                        weightProgress.dismiss();
                        weight.setText("");
                        weightDate.setText("");
                        weightAlert.setMessage("Logged Weight Successfully");
                        weightAlert.show();
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

    //Get Ndbno
    public void getNdbno(){
        RequestParams params = new RequestParams();
        params.put("format","json");
        params.put("q",foodName.getText().toString());
        params.put("api_key","GTHGyVUgYnz3NhhfYIRUIf7WRv11Hxbr5MKulieu");
        client.get("https://api.nal.usda.gov/ndb/search/", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject obj) {
                try{
                    if (obj.has("errors")) {
                        progressDialog.hide();
                        weightProgress.hide();
                        alertDialog.setMessage("No Food found");
                        alertDialog.show();
                    }else{
                        getCalories(obj.getJSONObject("list").getJSONArray("item").getJSONObject(0).getString("ndbno"));
                    }

                }catch(Exception e){

                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable e , JSONArray a) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                progressDialog.hide();
                alertDialog.setMessage("Something Went Wrong, Please try again");
                alertDialog.show();
            }
        });
    }


    //Finding calories
    public void getCalories(String ndbno){
        RequestParams params = new RequestParams();
        params.put("format","json");
        params.put("api_key","GTHGyVUgYnz3NhhfYIRUIf7WRv11Hxbr5MKulieu");
        params.put("nutrients","208");
        params.put("ndbno",ndbno);
        client.get("https://api.nal.usda.gov/ndb/nutrients/", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject obj) {
                try{
                    JSONObject foodObj = obj.getJSONObject("report").getJSONArray("foods").getJSONObject(0);
                    JSONObject nutrient = foodObj.getJSONArray("nutrients").getJSONObject(0);
                    value = nutrient.getString("value");
                    progressDialog.hide();
                    if(status.equals("log food")){
                        logFood();
                    }else{
                        alertDialog.setMessage("Calories per serving is " + value);
                        alertDialog.show();
                    }

                }catch(Exception e){
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable e , JSONArray a) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                progressDialog.hide();
                alertDialog.setMessage("Something Went Wrong, Please try again");
                alertDialog.show();
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
                Intent logActivity = new Intent(getApplicationContext(),DailyDataActivity.class);
                startActivity(logActivity);
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
                return true;
            case R.id.contacts_page:
                Intent contactsActivity = new Intent(getApplicationContext(),ContactsActivity.class);
                startActivity(contactsActivity);
                finish();
                return true;
            case R.id.profile_page:
                Intent profileActivity = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(profileActivity);
                finish();
                return true;
//            case R.id.goals_page:
//                Intent goalsActivity = new Intent(getApplicationContext(),GoalsActivity.class);
//                startActivity(goalsActivity);
//                finish();
//                return true;
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
