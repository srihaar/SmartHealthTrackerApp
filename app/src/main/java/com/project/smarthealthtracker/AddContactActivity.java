package com.project.smarthealthtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = (EditText)findViewById(R.id.contactName);
        if(getIntent().getStringExtra("add").toString().equals("true")){
            getSupportActionBar().setTitle("Add New Contact");
        }else{
            getSupportActionBar().setTitle("Edit or Delete Contact");
        }
        name.setText(getIntent().getStringExtra("name"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
