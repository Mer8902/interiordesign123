package com.example.interiordesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registration extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
    Button b1;
    String fname,lname,place,post,pin,phone,email,uname,pwd,url;
    SharedPreferences sh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        e1=findViewById(R.id.editTextTextPersonName5);
        e2=findViewById(R.id.editTextTextPersonName6);
        e3=findViewById(R.id.editTextTextPersonName7);
        e4=findViewById(R.id.editTextTextPersonName8);
        e5=findViewById(R.id.editTextTextPersonName9);
        e6=findViewById(R.id.editTextTextPersonName10);
        e7=findViewById(R.id.editTextTextPersonName11);
        e8=findViewById(R.id.editTextTextPersonName12);
        e9=findViewById(R.id.editTextTextPassword);
        b1=findViewById(R.id.button5);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = e1.getText().toString();
                lname = e2.getText().toString();
                place = e3.getText().toString();
                post = e4.getText().toString();
                pin = e5.getText().toString();
                phone = e6.getText().toString();
                email = e7.getText().toString();
                uname = e8.getText().toString();
                pwd = e9.getText().toString();
                if (fname.equalsIgnoreCase("")) {
                    e1.setError("Enter first name");
                } else if (lname.equalsIgnoreCase("")) {
                    e2.setError("Enter last name");
                }
                else if (place.equalsIgnoreCase("")) {
                    e3.setError("Enter Your Place");
                } else if (post.equalsIgnoreCase("")) {
                    e4.setError("Enter Your post");
                } else if (pin.equalsIgnoreCase("")) {
                    e5.setError("Enter Your Pin");
                } else if (pin.length() != 6) {
                    e5.setError("invalid pin");
                    e5.requestFocus();
                }
                else if (phone.equalsIgnoreCase("")) {
                    e6.setError("Enter Your Phone No");
                } else if (phone.length() < 10) {
                    e6.setError("Minimum 10 nos required");
                    e6.requestFocus();
                } else if (email.equalsIgnoreCase("")) {
                    e7.setError("Enter Your Email");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    e7.setError("Enter Valid Email");
                    e7.requestFocus();
                }  else if (uname.equalsIgnoreCase("")) {
                    e8.setError("Enter Your username");
                } else if (pwd.equalsIgnoreCase("")) {
                    e9.setError("Enter Your password");
                } else {


                    RequestQueue queue = Volley.newRequestQueue(registration.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/registration";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("valid")) {
                                    Toast.makeText(registration.this, "successful", Toast.LENGTH_SHORT).show();

                                    Intent ik = new Intent(getApplicationContext(), Login.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(registration.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Firstname", fname);
                            params.put("Lastname", lname);
                            params.put("Place", place);
                            params.put("Post", post);
                            params.put("Pin", pin);
                            params.put("Phone", phone);
                            params.put("Email", email);
                            params.put("Username", uname);
                            params.put("Password", pwd);

                            return params;
                        }
                    };
                    queue.add(stringRequest);


                }
            }
        });
    }
}