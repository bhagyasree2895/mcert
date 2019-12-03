package com.example.mcert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class IncidentReportActivity extends AppCompatActivity {
    public static final int TASK_REQ = 1;
    public static final int TASK_RES = 1;

    public static final int GOOD_RES = 2;

    public static final int TOG_RES = 2;

    public static final int FLO_RES = 2;

    String[] ImpactLevel={"Low","Medium","High"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_report);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,ImpactLevel);
        MaterialBetterSpinner betterSpinner = findViewById(R.id.spinner1);
        betterSpinner.setAdapter(arrayAdapter);
    }

    public void onSubmit(View v) {
        Intent ini = new Intent();
        setResult(TabsActivity.GOOD_RES, ini);
        finish();
    }



    public void getLocationAction(View v) {
        Intent ini = new Intent(this, MapsActivity.class);
        startActivityForResult(ini,11);
    }

    public void getDisasterType(View v) {
        Intent disaster_ini = new Intent(this, ImagesActivity.class);
        startActivityForResult(disaster_ini, TASK_REQ);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent disasterInt) {
        if (requestCode == TASK_REQ) {
            if (resultCode == TASK_RES) {
                String str = disasterInt.getStringExtra("name");
                EditText edt = findViewById(R.id.Type2);
                edt.setText(str);
            }
            else if (resultCode == GOOD_RES) {
                String str = disasterInt.getStringExtra("disaster");
                EditText edt = findViewById(R.id.Type2);
                edt.setText(str);
            }
            else if(resultCode == TOG_RES){
                String str = disasterInt.getStringExtra("disaster");
                EditText edt = findViewById(R.id.Type2);
                edt.setText(str);
            }
            else if(resultCode == FLO_RES){
                String str = disasterInt.getStringExtra("disaster");
                EditText edt = findViewById(R.id.Type2);
                edt.setText(str);
            }
        }
        if(requestCode == 11) {
            if (resultCode == 11) {
                String str = disasterInt.getStringExtra("LocationName");
                TextView incidentLocTV=findViewById(R.id.location);
                incidentLocTV.setText(str);
            }
        }
    }
}

