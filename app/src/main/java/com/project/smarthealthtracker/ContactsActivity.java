package com.project.smarthealthtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ArrayList<JSONObject> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        getSupportActionBar().setTitle("Emergency Contacts");
        ListView contactsView = (ListView)findViewById(R.id.contactsListView);
        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        JSONObject obj1 = new JSONObject();
        list = new ArrayList<JSONObject>();
        try{
            obj.put("name","sri");
            obj.put("number","6695");
            obj1.put("name","ad");
            obj1.put("number","45");
            array.put(obj);
            array.put(obj1);
            list.add(obj);
            list.add(obj1);
        }catch(Exception e){

        }
        ContactsAdapter adapter = new ContactsAdapter(ContactsActivity.this,list);
        contactsView.setAdapter(adapter);
        contactsView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contactsmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.dashboard_page:
                Intent dashboard = new Intent(getApplicationContext(),DashboardActivity.class);
                dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dashboard);
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

            case R.id.addContactButton:
                Intent addActivity = new Intent(getApplicationContext(),AddContactActivity.class);
                addActivity.putExtra("add","true");
                startActivity(addActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        JSONObject obj = list.get(i);
        Intent intent =new Intent(getApplicationContext(),AddContactActivity.class);
        try{
            intent.putExtra("name",obj.getString("name"));
            intent.putExtra("number",obj.getString("number"));
            intent.putExtra("add","false");
        }catch (Exception e){

        }
        startActivity(intent);
    }
}
