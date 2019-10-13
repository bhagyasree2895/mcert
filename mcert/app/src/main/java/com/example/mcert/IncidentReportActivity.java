package com.example.mcert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IncidentReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_report);
    }
    public void onSubmit(View v){
        Intent ini = new Intent();
        setResult(TabsActivity.GOOD_RES,ini);
        finish();
    }
}

