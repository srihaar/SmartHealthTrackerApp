package com.project.smarthealthtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        getSupportActionBar().setTitle("Steps");

        LineChart lineChart = (LineChart) findViewById(R.id.chart1);


        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4000f, 0));
        entries.add(new Entry(8000f, 1));
        entries.add(new Entry(6000f, 2));
        entries.add(new Entry(9000f, 3));
        entries.add(new Entry(3000f, 4));
        entries.add(new Entry(10000f, 5));
        entries.add(new Entry(6000f, 6));

        LineDataSet dataset = new LineDataSet(entries, "Weekly Step Count");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Mon");
        labels.add("Tue");
        labels.add("Wed");
        labels.add("Thu");
        labels.add("Fri");
        labels.add("Sat");
        labels.add("Sun");

        LineData data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        lineChart.setDescription("Steps for the week");

        lineChart.setData(data);
        lineChart.animateX(3000);
        lineChart.animateY(3000);
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
                Intent stepsActivity = new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(stepsActivity);
                finish();
                return true;
            case R.id.calories_page:
                Intent i = new Intent(getApplicationContext(),CaloriesActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.steps_page:
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
