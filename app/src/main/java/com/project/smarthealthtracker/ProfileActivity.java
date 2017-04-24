package com.project.smarthealthtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        save = (Button)findViewById(R.id.save);
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
