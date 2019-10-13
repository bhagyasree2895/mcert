package com.example.mcert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TabsActivity extends AppCompatActivity {
    public static final int GOOD_REQ=1;
    public static final int GOOD_RES=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
    }
    public void incident(View v){
        Intent incident_intent = new Intent(this, IncidentReportActivity.class);
        startActivity(incident_intent);
    }
    public void general(View v){
        Intent general_intent = new Intent(this, GeneralReport.class);
        startActivity(general_intent);
    }
    public void past(View v){
        Intent past_intent = new Intent(this, PastActivity.class);
        startActivity(past_intent);
    }
    public void profile(View v){
        Intent profile_intent = new Intent(this, ProfileActivity.class);
        startActivity(profile_intent);
    }
    public void onActivityResult(int requestCode,int resultCode,Intent tipInt) {
        if (requestCode == GOOD_REQ) {
            if (resultCode == GOOD_RES) {


            }
        }
    }


}
