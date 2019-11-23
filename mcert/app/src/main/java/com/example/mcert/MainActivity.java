package com.example.mcert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.security.NetworkSecurityPolicy;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText pwd;
    RequestQueue requestQueue;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //NetworkSecurityPolicy.isCleartextTrafficPermitted()
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.UserName);
        pwd = findViewById(R.id.password);

        requestQueue = Volley.newRequestQueue(this);
    }
    public void tip(View v){
        Intent tip_intent = new Intent(this, TabsActivity.class);
        startActivity(tip_intent);
    }
    public void signUpAction(View v){
        Intent signUp_intent = new Intent(this, SignUpActivity.class);
        startActivity(signUp_intent);
    }


    private void getRepoList(String username, String pwd){
        this.url = "http://eoc-dm.herokuapp.com/api/incident/getIncidents?status=open";
       // this.url = " http://www.appdomain.com/users";
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Check the length of our response (to see if the user has any repos)
//                        if (response.length() > 0) {
//                            // The user does have repos, so let's loop through them all.
//                            for (int i = 0; i < response.length(); i++) {
//                                try {
//                                    // For each repo, add a new line to our repo list.
//                                    JSONObject jsonObj = response.getJSONObject(i);
//                                    String repoName = jsonObj.get("name").toString();
//                                    String lastUpdated = jsonObj.get("updated_at").toString();
//                                    addToRepoList(repoName, lastUpdated);
//                                } catch (JSONException e) {
//                                    // If there is an error then output this to the logs.
//                                    Log.e("Volley", "Invalid JSON Object.");
//                                }
//
//                            }
//                        } else {
//                            // The user didn't have any repos.
//                            setRepoListText("No repos found.");
//                        }

                        Log.d("response",response.toString())  ;

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



    public void getReposClicked(View v) {
        // Clear the repo list (so we have a fresh screen to add to)

        // Call our getRepoList() function that is defined above and pass in the
        // text which has been entered into the etGitHubUser text input field.
        getRepoList(username.getText().toString(),pwd.getText().toString());
    }

}
