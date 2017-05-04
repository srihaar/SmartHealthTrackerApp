package com.project.smarthealthtracker;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

public class AddContactActivity extends AppCompatActivity {
    EditText name,phoneNumber,relation;
    Button save,delete;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = (EditText)findViewById(R.id.contactName);
        phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        relation = (EditText) findViewById(R.id.relation);
        save = (Button)findViewById(R.id.saveContact);
        delete = (Button) findViewById(R.id.deleteContact);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        alertDialog = new AlertDialog.Builder(AddContactActivity.this).create();
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(AddContactActivity.this,ContactsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        if(getIntent().getStringExtra("add").toString().equals("true")){
            getSupportActionBar().setTitle("Add New Contact");
            delete.setVisibility(View.GONE);
        }else{
            getSupportActionBar().setTitle("Edit or Delete Contact");
            name.setText(getIntent().getStringExtra("name"));
            phoneNumber.setText(getIntent().getStringExtra("phoneNumber"));
            relation.setText(getIntent().getStringExtra("relation"));
        }

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                progressDialog.setMessage("Saving Contact");
                progressDialog.show();
                if(getIntent().getStringExtra("add").toString().equals("true")){
                    addContact();
                }else {
                    updateContact();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                progressDialog.setMessage("Deleting Contact");
                progressDialog.show();
                deleteContact();
            }
        });

    }

    public void addContact(){
        RequestParams params = new RequestParams();
        params.put("name",name.getText().toString());
        params.put("phoneNumber",phoneNumber.getText().toString());
        params.put("relation",relation.getText().toString());
        NodeRestClient.get("/addContactMobile",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject obj) {
                try{
                    if(obj.getInt("statusCode") == 200 ){
                        progressDialog.dismiss();
                        alertDialog.setMessage("Contact Added Successfully");
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

    public void updateContact(){
        RequestParams params = new RequestParams();
        params.put("name",name.getText().toString());
        params.put("phoneNumber",phoneNumber.getText().toString());
        params.put("relation",relation.getText().toString());
        params.put("emergencyId",getIntent().getStringExtra("emergencyId"));
        NodeRestClient.get("/updateContactMobile",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject obj) {
                try{
                    if(obj.getInt("statusCode") == 200 ){
                        progressDialog.dismiss();
                        alertDialog.setMessage("Contact Updated Successfully");
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

    public void deleteContact(){
        RequestParams params = new RequestParams();
        params.put("emergencyId",getIntent().getStringExtra("emergencyId"));
        NodeRestClient.get("/deleteContactMobile",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject obj) {
                try{
                    if(obj.getInt("statusCode") == 200 ){
                        progressDialog.dismiss();
                        alertDialog.setMessage("Contact Deleted Successfully");
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
