package com.example.mcert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GeneralReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_report);
    }
    public void onSubmit1(View v){
        Intent gen_ini = new Intent();
        setResult(TabsActivity.RES,gen_ini);
        finish();
    }
}
