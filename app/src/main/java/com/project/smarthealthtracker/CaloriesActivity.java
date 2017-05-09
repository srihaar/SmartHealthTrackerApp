package com.project.smarthealthtracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.project.smarthealthtracker.LoginActivity.MY_PREFS_NAME;

public class CaloriesActivity extends AppCompatActivity {
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);
        getSupportActionBar().setTitle("Calories Burnt");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Fetching Weekly Steps");
        progressDialog.show();
        progressDialog.setCancelable(false);

        RequestParams params = new RequestParams();

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        params.put("accessToken",prefs.getString("accessToken", null));


        NodeRestClient.get("/getCaloriesByWeekMobile",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject obj) {
                try{
                    JSONArray array = obj.getJSONArray("activities-calories");
                    loadChart(array);
                }catch(Exception e){

                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable e , JSONArray a) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

            }
        });



    }

    public void loadChart(JSONArray array)throws JSONException{
        progressDialog.hide();
        progressDialog.dismiss();
        BarChart barChart = (BarChart) findViewById(R.id.chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0;i<array.length();i++){
            entries.add(new BarEntry(array.getJSONObject(i).getInt("value"),i));
        }


        BarDataSet dataset = new BarDataSet(entries, "Calories");

        ArrayList<String> labels = new ArrayList<String>();
        for(int i=0;i<array.length();i++){
            labels.add(array.getJSONObject(i).getString("dateTime"));
        }



        barChart.setDescription("Calories Burnt for the week");
        BarData data = new BarData(labels, dataset);
        barChart.setData(data);
        barChart.animateX(2000);
        barChart.animateY(2000);
        dataset.setColors(ColorTemplate.LIBERTY_COLORS);
        //barChart.getXAxis().setLabelsToSkip(0);

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
                Intent i = new Intent(getApplicationContext(),DailyDataActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.calories_page:
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
