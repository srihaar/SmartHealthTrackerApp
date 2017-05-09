package com.project.smarthealthtracker;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.project.smarthealthtracker.LoginActivity.MY_PREFS_NAME;

public class GoalsActivity extends AppCompatActivity {
    EditText calories,steps,distance,floors;
    ProgressDialog progressDialog;
    Button saveBtn;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);
        calories = (EditText) findViewById(R.id.calories);
        steps = (EditText) findViewById(R.id.steps);
        distance = (EditText) findViewById(R.id.distance);
        floors = (EditText) findViewById(R.id.floors);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        progressDialog = new ProgressDialog(this);
        getGoals();

        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateGoals();
            }
        });
         alertDialog = new AlertDialog.Builder(GoalsActivity.this).create();
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getGoals();
                        dialog.dismiss();
                    }
                });
        alertDialog.setMessage("Updated Goals Successfully");

    }

    public void getGoals(){
        progressDialog.setMessage("Getting Goals");
        progressDialog.show();
        RequestParams params = new RequestParams();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        params.put("accessToken",prefs.getString("accessToken", null));
        NodeRestClient.get("/getDailyActivityMobile",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject obj) {
                try{
                        steps.setText(Integer.toString(obj.getJSONObject("activities").getJSONObject("goals").getInt("steps")));
                        calories.setText(Integer.toString(obj.getJSONObject("activities").getJSONObject("goals").getInt("caloriesOut")));
                        distance.setText(Double.toString(obj.getJSONObject("activities").getJSONObject("goals").getDouble("distance")));
                        floors.setText(Integer.toString(obj.getJSONObject("activities").getJSONObject("goals").getInt("floors")));
                        progressDialog.hide();
                        progressDialog.dismiss();
                }catch(Exception e){
                    System.out.println(e);
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable e , JSONArray a) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

            }
        });
    }

    public void updateGoals(){
        progressDialog.setMessage("Updating Goals");
        progressDialog.show();
        RequestParams params = new RequestParams();
        params.put("distance",distance.getText().toString());
        params.put("steps",steps.getText().toString());
        params.put("caloriesOut",calories.getText().toString());
        params.put("floors",floors.getText().toString());
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        params.put("accessToken",prefs.getString("accessToken", null));

        NodeRestClient.get("/updateGoalsMobile",params,new JsonHttpResponseHandler(){
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
                Intent goalsActivity = new Intent(getApplicationContext(),DailyDataActivity.class);
                startActivity(goalsActivity);
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
                Intent profileActivity = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(profileActivity);
                finish();
                return true;
//            case R.id.goals_page:
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
