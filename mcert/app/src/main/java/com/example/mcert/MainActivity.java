package com.example.mcert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void tip(View v){
        Intent tip_intent = new Intent(this, TabsActivity.class);
        startActivity(tip_intent);
    }
    public void signUpAction(View v){
        Intent signUp_intent = new Intent(this, SignUpActivity.class);
        startActivity(signUp_intent);
    }

}
