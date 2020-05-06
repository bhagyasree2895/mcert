package com.example.mcert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    static String token;
    String firstname;
    String lastname;
    String ContactNo;
    String Medical;
    String enroll;

    EditText fname;
    EditText lname;
    EditText contact;
    EditText med;
    EditText enrollment;

    JSONObject jsonBody;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://eoc-dm.herokuapp.com/api/incident/userdata";
        jsonBody = new JSONObject();

        fname = findViewById(R.id.editText1);
        lname = findViewById(R.id.editText2);
        contact =findViewById(R.id.editText3);
        med = findViewById(R.id.editText4);
        enrollment = findViewById(R.id.editText10);



        final String requestBody = jsonBody.toString();
       // final Intent tip_intent = new Intent(this, IncidentListResponse.class);
        //should be removed after fixing login issue
        //     startActivity(tip_intent);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL, jsonBody, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                Log.i("VOLLEY", response.toString());

                try {
                    firstname = response.getString("firstName");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                fname.setText(firstname);
                try {
                    lastname = response.getString("lastName");
                    ContactNo = response.getString("contactNo");
                    Medical = response.getString("medicalCertification");
                    enroll = response.getString("enforcementOfficer");
                    Log.i("Med", Medical);
                    if(Medical.equals("")){
                        med.setText("No medical certifications");
                    }
                    else{
                        med.setText(Medical);
                    }

                    if(enroll.equals("false")){
                        enrollment.setText("Not an enrollment Officer");
                    }
                    else{
                        enrollment.setText("Yes");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lname.setText(lastname);
                contact.setText(ContactNo);



                // json = new JSONObject(jsonResult);

                ;
                //  Intent ini = new Intent(this,TabsActivity.class)
                //startActivity(tip_intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());

                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";

            }

            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", MainActivity.token);
                Log.e("token", MainActivity.token);

                return params;
            }

        };

        requestQueue.add(stringRequest);

    }

    public void onProfile(View v){
        Intent ini = new Intent(this, TabsActivity.class);
        startActivity(ini);

    }


}
