package com.example.mcert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    EditText firstname;
    EditText lastname;
    EditText Email;
    EditText Contact;
    EditText Password;
    EditText Confirm;

    RequestQueue requestQueue;
    String url;
    ArrayList<JSONArray> LoginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstname = findViewById(R.id.firstName);
        lastname = findViewById(R.id.lastName);
        Email = findViewById(R.id.editText7);
        Contact = findViewById(R.id.number);
        Password = findViewById(R.id.editText13);
        Confirm = findViewById(R.id.editText14);


        requestQueue = Volley.newRequestQueue(this);
    }

//    public void registerAction(View v) {
//        Intent in = new Intent(this, ThankYouActivity.class);
//        startActivity(in);
//
//    }

    public void Register(View v) {
        final String fName = firstname.getText().toString();
        final String lName = lastname.getText().toString();
        final String mobNum = Contact.getText().toString();
        final String user = Email.getText().toString();
        final String p = Password.getText().toString();
        final String Cpass = Confirm.getText().toString();
        if (fName.length() == 0) {
            firstname.requestFocus();
            firstname.setError("Name field cannot be empty!!");
        } else if (!fName.matches("[a-zA-Z ]+")) {
            firstname.requestFocus();
            firstname.setError("ENTER ONLY ALPHABETICAL CHARACTER");
        }
        if (lName.length() == 0) {
            lastname.requestFocus();
            lastname.setError("Name field cannot be empty!!");
        } else if (!lName.matches("[a-zA-Z ]+")) {
            lastname.requestFocus();
            lastname.setError("ENTER ONLY ALPHABETICAL CHARACTER");
        }
        //EmailId validation

        else if (user.length() == 0) {
            Email.requestFocus();
            Email.setError("Email field cannot be empty!!");
        }
        else if (!isValidEmail(user)) {

            Email.requestFocus();
            Email.setError("Email must include @somedomain.com");
        }

//isValidEmail
        //Mobile Number validation

        else if (mobNum.length() < 10||mobNum.length() >10) {
            Contact.requestFocus();
            Contact.setError("Mobile Field is Empty/ too short");
        } else if (!Pattern.matches("[0-9]+", mobNum)) {
            Contact.setError("Mobile Field should contain only numerical values");
        }

        //Password validation
        else if (p.length() < 8 && !isValidPassword(p)) {
            Password.requestFocus();
            Password.setError("Enter Valid Password with atleast 1 capital letter, 1 small letter, 1 number and a symbol");
        }

        //Confirm Password Validation
        else if (!(Cpass.equals(p))) {
            Confirm.requestFocus();
            Confirm.setError("Password and Confirm Password does not match");
        }
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "https://eoc-dm.herokuapp.com/api/auth/register";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("contactNo",this.Contact.getText().toString());
            jsonBody.put("email",this.Email.getText().toString());
            jsonBody.put("enforcementOfficer","false");
            jsonBody.put("firstName",this.firstname.getText().toString());
            jsonBody.put("lastName",this.lastname.getText().toString());
            jsonBody.put("medicalCertification","");
            jsonBody.put("password",this.Password.getText().toString());
            jsonBody.put("password2",this.Confirm.getText().toString());



            // Toast.makeText(getApplicationContext(),this.pwd.getText().toString()+"",Toast.LENGTH_SHORT).show();

            final String requestBody = jsonBody.toString();
            final Intent login_intent = new Intent(this, MainActivity.class);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override

                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    //  Intent ini = new Intent(this,TabsActivity.class)
                    startActivity(login_intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    Log.e("VOLLEY", error.getMessage());
//
//                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static boolean isValidPassword(final String password){
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN="^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern=Pattern.compile(PASSWORD_PATTERN);
        matcher=pattern.matcher(password);
        return matcher.matches();
    }

    // ^[a-z0-9](\.?[a-z0-9]){5,}@g(oogle)?mail\.com$
    public static boolean isValidEmail(final String Email){
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN="^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$";
        pattern=Pattern.compile(EMAIL_PATTERN);
        matcher=pattern.matcher(Email);
        return matcher.matches();
    }

//    private void getRepoList(String username, String pwd) {
//        this.url = "http://eoc-dm.herokuapp.com/api/incident/getIncidents?status=open";
//        // this.url = " http://www.appdomain.com/users";
//        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        // Check the length of our response (to see if the user has any repos)
////                        if (response.length() > 0) {
////                            // The user does have repos, so let's loop through them all.
////                            for (int i = 0; i < response.length(); i++) {
////                                try {
////                                    // For each repo, add a new line to our repo list.
////                                    JSONObject jsonObj = response.getJSONObject(i);
////                                    String repoName = jsonObj.get("name").toString();
////                                    String lastUpdated = jsonObj.get("updated_at").toString();
////                                    addToRepoList(repoName, lastUpdated);
////                                } catch (JSONException e) {
////                                    // If there is an error then output this to the logs.
////                                    Log.e("Volley", "Invalid JSON Object.");
////                                }
////
////                            }
////                        } else {
////                            // The user didn't have any repos.
////                            setRepoListText("No repos found.");
////                        }
//
//                        Log.d("response", response.toString());
//                        LoginRequest.add(response);
//
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // If there a HTTP error then add a note to our repo list.
////                        setRepoListText("Error while calling REST API");
//                        Log.e("Temp", error.toString());
//                        // Log.e(tag:"Error",error)
//                    }
//                }
//        );
//        // Add the request we just defined to our request queue.
//        // The request queue will automatically handle the request as soon as it can.
//        requestQueue.add(arrReq);
//    }
//
//
//    public void getReposClicked(View v) {
//        // Clear the repo list (so we have a fresh screen to add to)
//
//        // Call our getRepoList() function that is defined above and pass in the
//        // text which has been entered into the etGitHubUser text input field.
//        getRepoList(firstname.getText().toString(), lastname.getText().toString());
//    }


}