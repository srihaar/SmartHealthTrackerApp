package com.project.smarthealthtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SrIHaaR on 4/22/2017.
 */

public class ContactsAdapter extends ArrayAdapter {

    ArrayList<JSONObject> results;
    Context context;

    public ContactsAdapter(Context context,  ArrayList<JSONObject> results) {
        super(context, R.layout.single_contact_row, results);
        this.results=results;
        this.context=context;
    }
    class ResultsViewHolder{
        TextView myTitle;
        TextView myPhone;

        ResultsViewHolder(View v){
            myTitle = (TextView) v.findViewById(R.id.nameField);
            myPhone  = (TextView) v.findViewById(R.id.phoneField);
        }
    }
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        ResultsViewHolder holder = null;
        if(row==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_contact_row, parent, false);
            holder = new ResultsViewHolder(row);
            row.setTag(holder);
        }else{
            holder = (ResultsViewHolder) row.getTag();
        }
        JSONObject result = results.get(position);
        try{
            holder.myTitle.setText(result.getString("name"));
            holder.myPhone.setText(result.getString("phone"));
        }catch (Exception e){

        }

        return row;
    }

}
