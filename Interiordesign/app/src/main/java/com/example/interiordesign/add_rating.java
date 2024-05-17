package com.example.interiordesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

public class add_rating extends AppCompatActivity {
    EditText e1;
    RatingBar r1;
    Button b1;
    String review,rating,url;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rating);
        e1=findViewById(R.id.editTextTextPersonName14);
        r1=findViewById(R.id.ratingBar);
        b1=findViewById(R.id.button18);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                review=e1.getText().toString();
                rating=String.valueOf(r1.getRating());
                if (review.equalsIgnoreCase("")) {
                    e1.setError("Enter the review");
                } else {


                    RequestQueue queue = Volley.newRequestQueue(add_rating.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/add_rating";

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

                                    Intent ik = new Intent(getApplicationContext(), Home_page.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(add_rating.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

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
                            params.put("rating", rating);
                            params.put("review", review);
                            params.put("lid", sh.getString("lid", ""));

                            return params;
                        }
                    };
                    queue.add(stringRequest);


                }
            }
        });

    }
}