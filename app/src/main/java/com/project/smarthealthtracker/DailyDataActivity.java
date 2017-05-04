package com.project.smarthealthtracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

public class DailyDataActivity extends AppCompatActivity {
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_data);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data");
        progressDialog.show();



        NodeRestClient.get("/getDailyActivityMobile",null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject obj) {
                try{
                    Intent dashboard = new Intent(DailyDataActivity.this,DashboardActivity.class);
                    //steps goal and value
                    dashboard.putExtra("steps",obj.getJSONObject("activities").getJSONObject("summary").getInt("steps"));
                    dashboard.putExtra("stepsGoal",obj.getJSONObject("activities").getJSONObject("goals").getInt("steps"));
                    //calories goal and value
                    dashboard.putExtra("calories",obj.getJSONObject("activities").getJSONObject("summary").getInt("caloriesOut"));
                    dashboard.putExtra("caloriesGoal",obj.getJSONObject("activities").getJSONObject("goals").getInt("caloriesOut"));
                    //distance goal and value
                    JSONArray distances = obj.getJSONObject("activities").getJSONObject("summary").getJSONArray("distances");
                    double totalDistance = 0;
                    for(int i=0;i<distances.length(); i++){
                        JSONObject distance = distances.getJSONObject(i);
                        totalDistance = totalDistance + distance.getDouble("distance");
                    }
                    dashboard.putExtra("distance",totalDistance);
                    dashboard.putExtra("distanceGoal",obj.getJSONObject("activities").getJSONObject("goals").getDouble("distance"));
                    //active Minutes goal and value
                    int activeMinutes = obj.getJSONObject("activities").getJSONObject("summary").getInt("veryActiveMinutes") +
                            obj.getJSONObject("activities").getJSONObject("summary").getInt("fairlyActiveMinutes") +
                            obj.getJSONObject("activities").getJSONObject("summary").getInt("lightlyActiveMinutes");
                    dashboard.putExtra("activeMinutes",activeMinutes);
                    dashboard.putExtra("activeMinutesGoal",obj.getJSONObject("activities").getJSONObject("goals").getInt("activeMinutes"));
                    progressDialog.hide();
                    startActivity(dashboard);
                }catch(Exception e){

                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable e , JSONArray a) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

            }
        });
    }
}
