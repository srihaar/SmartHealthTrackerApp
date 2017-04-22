package com.project.smarthealthtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        name = (EditText)findViewById(R.id.contactName);
        if(getIntent().getStringExtra("add").toString().equals("true")){
            getSupportActionBar().setTitle("Add New Contact");
        }else{
            getSupportActionBar().setTitle("View Contact");
        }
        name.setText(getIntent().getStringExtra("name"));

    }
}
