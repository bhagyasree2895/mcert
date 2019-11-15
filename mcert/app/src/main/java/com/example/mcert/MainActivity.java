package com.example.mcert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;

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
5
    EditText username = findViewById(R.id.UserName);
    EditText pwd = findViewById(R.id.password);
    String url;
    private void getRepoList(String username, String pwd){
        this.url = "https://eoc-dm.herokuapp.com/api/incident/editIncident";
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.POST, url,
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
                        setRepoListText("Error while calling REST API");
                        Log.e("Volley", error.toString());
                        // Log.e(tag:"Error",error)
                    }
                }
        );
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(arrReq);
    }
    }

    public void getReposClicked(View v) {
        // Clear the repo list (so we have a fresh screen to add to)

        // Call our getRepoList() function that is defined above and pass in the
        // text which has been entered into the etGitHubUser text input field.
        getRepoList(username.getText().toString(),pwd.getText().toString());
    }

}
