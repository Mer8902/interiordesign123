package com.example.interiordesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class send_feedback extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner s1;
    EditText e1;
    Button b1;
    String feedback,design,url;
    SharedPreferences sh;
    ArrayList<String> designs,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);
        s1=findViewById(R.id.spinner);
        e1=findViewById(R.id.editTextTextPersonName15);
        b1=findViewById(R.id.button19);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sh.getString("ip", "") + ":5000/view_designfeed";
        RequestQueue queue = Volley.newRequestQueue(send_feedback.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    designs= new ArrayList<>();
                    id= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        designs.add(jo.getString("design"));
                        id.add(jo.getString("id"));



                    }

                     ArrayAdapter<String> ad=new ArrayAdapter<>(send_feedback.this,android.R.layout.simple_list_item_1,designs);
                    s1.setAdapter(ad);
                    s1.setOnItemSelectedListener(send_feedback.this);

//                    l1.setAdapter(new Custom(viewuser.this,name,place));
//                    l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(send_feedback.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        queue.add(stringRequest);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedback=e1.getText().toString();

                if (feedback.equalsIgnoreCase("")) {
                    e1.setError("Enter the feedback");
                } else {


                    RequestQueue queue = Volley.newRequestQueue(send_feedback.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/feedback_app";

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
                                    Toast.makeText(send_feedback.this, "send", Toast.LENGTH_SHORT).show();

                                    Intent ik = new Intent(getApplicationContext(), Home_page.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(send_feedback.this, "Invalid", Toast.LENGTH_SHORT).show();

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
                            params.put("Feedback", feedback);
                            params.put("did", design);
                            params.put("lid", sh.getString("lid", ""));


                            return params;
                        }
                    };
                    queue.add(stringRequest);


                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        design=id.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}