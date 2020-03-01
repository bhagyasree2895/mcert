package com.example.mcert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class TabsActivity extends AppCompatActivity {
    public static final int GOOD_REQ=1;
    public static final int GOOD_RES=0;
    public static final int REQ=10;
    public static final int RES=20;
    RequestQueue requestQueue;

    static String response_incident_api ;

    String url = "https://eoc-dm.herokuapp.com/api/incident/getIncidents?status=open";
//    public static final int REQ_TOG=10;
//    public static final int RES_TOG=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        requestQueue = Volley.newRequestQueue(this);

        Button incident_list = findViewById(R.id.incident_list);
        final Intent list = new Intent(this, IncidentListResponse.class);

        incident_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                url = "https://eoc-dm.herokuapp.com/api/incident/getIncidents?status=open";

                JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                Log.d("volley",response.toString()) ;
                                response_incident_api = response.toString();


                                startActivity(list);




                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // If there a HTTP error then add a note to our repo list.
//                        setRepoListText("Error while calling REST API");
                                Log.d("volley", error.toString());
                                // Log.e(tag:"Error",error)
                            }
                        }
                );
                // Add the request we just defined to our request queue.
                // The request queue will automatically handle the request as soon as it can.



                requestQueue.add(arrReq);


            }
        });




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

    private void getIncidentsList() {
        this.url = "https://eoc-dm.herokuapp.com/api/incident/getIncidents?status=open";
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response",response.toString()) ;
                        //LoginRequest.add(response);

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // If there a HTTP error then add a note to our repo list.
//                        setRepoListText("Error while calling REST API");
                        Log.e("Temp", error.toString());
                        // Log.e(tag:"Error",error)
                    }
                }
        );
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(arrReq);
    }



    public void onClick(View v) {
        // Clear the repo list (so we have a fresh screen to add to)

        // Call our getRepoList() function that is defined above and pass in the
        // text which has been entered into the etGitHubUser text input field.
        getIncidentsList();
    }
    public void onActivityResult(int requestCode,int resultCode,Intent tipInt) {
        if (requestCode == GOOD_REQ) {
            if (resultCode == GOOD_RES) {


            }
        }
        else if(requestCode == REQ) {
                if (resultCode == RES) {
                }
            }

    }


}
