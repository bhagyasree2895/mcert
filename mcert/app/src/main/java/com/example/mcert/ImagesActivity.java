package com.example.mcert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ImagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
    }

    public void onTornado(View v){
        Intent in = new Intent();
        in.putExtra("name","Tornado");
        setResult(IncidentReportActivity.TASK_RES,in);

        finish();
    }
}
