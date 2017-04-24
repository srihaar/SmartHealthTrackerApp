package com.project.smarthealthtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle("Dashboard Daily Activity");


        PieChart pieChart1 = (PieChart) findViewById(R.id.chart1);
        PieChart pieChart2 = (PieChart) findViewById(R.id.chart2);
        PieChart pieChart3 = (PieChart) findViewById(R.id.chart3);
        PieChart pieChart4 = (PieChart) findViewById(R.id.chart4);
        PieChart pieChart5 = (PieChart) findViewById(R.id.chart5);

        ArrayList<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(2450f, 0));
        entries1.add(new Entry(4550f, 1));

        ArrayList<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(1.6f, 0));
        entries2.add(new Entry(2.4f, 1));

        ArrayList<Entry> entries3 = new ArrayList<>();
        entries3.add(new Entry(490f, 0));
        entries3.add(new Entry(610f, 1));

        ArrayList<Entry> entries4 = new ArrayList<>();
        entries4.add(new Entry(5f, 0));
        entries4.add(new Entry(10f, 1));

        ArrayList<Entry> entries5 = new ArrayList<>();
        entries5.add(new Entry(1500f, 0));
        entries5.add(new Entry(50f, 1));

        PieDataSet dataset1 = new PieDataSet(entries1, "Steps");
        PieDataSet dataset2 = new PieDataSet(entries2, "Distance");
        PieDataSet dataset3 = new PieDataSet(entries3, "Calories Burnt");
        PieDataSet dataset4 = new PieDataSet(entries4, "Active Minutes");
        PieDataSet dataset5 = new PieDataSet(entries5, "Calories Consumed");

        ArrayList<String> labels1 = new ArrayList<String>();
        labels1.add("Steps Completed");
        labels1.add("Steps Remaining");

        ArrayList<String> labels2 = new ArrayList<String>();
        labels2.add("Distance Completed");
        labels2.add("Distance Remaining");

        ArrayList<String> labels3 = new ArrayList<String>();
        labels3.add("Calories Burnt");
        labels3.add("Burn Calories Remaining Target");


        ArrayList<String> labels4 = new ArrayList<String>();
        labels4.add("Active Minutes Completed");
        labels4.add("Active Minutes Remaining");

        ArrayList<String> labels5 = new ArrayList<String>();
        labels5.add("Calories Consumed");
        labels5.add("Consume Calories Remaining Target");

        PieData data1 = new PieData(labels1, dataset1);
        data1.setValueTextSize(8f);
        dataset1.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data2 = new PieData(labels2, dataset2);
        data2.setValueTextSize(8f);
        dataset2.setColors(ColorTemplate.LIBERTY_COLORS);

        PieData data3 = new PieData(labels3, dataset3);
        data3.setValueTextSize(8f);
        dataset3.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData data4 = new PieData(labels4, dataset4);
        data4.setValueTextSize(8f);
        dataset4.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data5 = new PieData(labels5, dataset5);
        data5.setValueTextSize(8f);
        dataset5.setColors(ColorTemplate.JOYFUL_COLORS);

        pieChart1.setDescription("Steps Today as per your goal");
        pieChart2.setDescription("Distance Today in miles as per your goal");
        pieChart3.setDescription("Calories Burnt Today as per your goal");
        pieChart4.setDescription("Active Minutes Today as per your goal");
        pieChart5.setDescription("Calories Consumed Today as per your goal");

        pieChart1.setData(data1);
        pieChart2.setData(data2);
        pieChart3.setData(data3);
        pieChart4.setData(data4);
        pieChart5.setData(data5);
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
                return true;
            case R.id.calories_page:
                Intent i = new Intent(getApplicationContext(),CaloriesActivity.class);
                startActivity(i);
                return true;
            case R.id.steps_page:
                Intent stepsActivity = new Intent(getApplicationContext(),StepsActivity.class);
                startActivity(stepsActivity);
                return true;
            case R.id.log_page:
                Intent logActivity = new Intent(getApplicationContext(),LogActivity.class);
                startActivity(logActivity);
                return true;
            case R.id.contacts_page:
                Intent contactsActivity = new Intent(getApplicationContext(),ContactsActivity.class);
                startActivity(contactsActivity);
                return true;
            case R.id.profile_page:
                Intent profileActivity = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(profileActivity);
                return true;
            case R.id.goals_page:
                Intent goalsActivity = new Intent(getApplicationContext(),GoalsActivity.class);
                startActivity(goalsActivity);
                return true;
            case R.id.weight_page:
                Intent weightActivity = new Intent(getApplicationContext(),WeightActivity.class);
                startActivity(weightActivity);
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
