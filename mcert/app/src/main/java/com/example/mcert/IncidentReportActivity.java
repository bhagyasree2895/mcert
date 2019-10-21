package com.example.mcert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class IncidentReportActivity extends AppCompatActivity {
    public static final int TASK_REQ=1;
    public static final int TASK_RES=1;

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
    public void getLocationAction(View v){
        Intent ini = new Intent(this,MapsActivity.class);
        startActivity(ini);
    }
    public void getDisasterType(View v){
        Intent disaster_ini = new Intent(this,ImagesActivity.class);
        startActivityForResult(disaster_ini,TASK_REQ);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent disasterInt){
        if(requestCode==TASK_REQ&&resultCode==TASK_RES){
            String str = disasterInt.getStringExtra("name");
            EditText edt= findViewById(R.id.editText5);
            edt.setText(str);
        }
    }
}

