package com.project.smarthealthtracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.project.smarthealthtracker.LoginActivity.MY_PREFS_NAME;

public class DailyDataActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    Intent dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_data);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        RequestParams params = new RequestParams();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        params.put("accessToken",prefs.getString("accessToken", null));



        NodeRestClient.get("/getDailyActivityMobile",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject obj) {
                try{
                    dashboard = new Intent(DailyDataActivity.this,DashboardActivity.class);
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
                    //floors goal and value
                    dashboard.putExtra("floors",obj.getJSONObject("activities").getJSONObject("summary").getInt("floors"));
                    dashboard.putExtra("floorsGoal",obj.getJSONObject("activities").getJSONObject("goals").getInt("floors"));
                    getCaloriesConsumed();
                    //progressDialog.hide();
                    //startActivity(dashboard);
                }catch(Exception e){

                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable e , JSONArray a) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

            }
        });
    }

    public void getCaloriesConsumed(){
        RequestParams params = new RequestParams();

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        System.out.println("user id is"+prefs.getInt("userID", 0));
        params.put("userID",prefs.getInt("userID", 0));
        NodeRestClient.get("/getCaloriesConsumedMobile",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject obj) {
                try{
                    dashboard.putExtra("caloriesIn",obj.getInt("totalCaloriesConsumed"));
                        progressDialog.dismiss();
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
