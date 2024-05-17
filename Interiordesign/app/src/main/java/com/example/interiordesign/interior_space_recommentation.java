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
import android.widget.ListView;
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

public class interior_space_recommentation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText e1,e2;
    Spinner s1;
    Button b1;
    SharedPreferences sh;
    String shape,height,width,url1;
    String array[]={"rectancle","oval","round","square","triangle"};
    ArrayList<String>interior,design,photo,details,date;
    ListView l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interior_space_recommentation);
        e1=findViewById(R.id.editTextTextPersonName19);
        e2=findViewById(R.id.editTextTextPersonName18);
        s1=findViewById(R.id.spinner2);
        b1=findViewById(R.id.button23);
        l1=findViewById(R.id.lv1);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ArrayAdapter<String> ad=new ArrayAdapter<>(interior_space_recommentation.this, android.R.layout.simple_list_item_1,array);
        s1.setAdapter(ad);
        s1.setOnItemSelectedListener(interior_space_recommentation.this);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                height=e1.getText().toString();
                width=e2.getText().toString();
                shape=s1.getSelectedItem().toString();

                url1 ="http://"+sh.getString("ip", "") + ":5000/space_designs";
                RequestQueue queue = Volley.newRequestQueue(interior_space_recommentation.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url1 ,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {

                            JSONArray ar=new JSONArray(response);
                            interior= new ArrayList<>();
                            design= new ArrayList<>();
                            details= new ArrayList<>();
                            date=new ArrayList<>();
                            photo=new ArrayList<>();

                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject jo=ar.getJSONObject(i);
                                interior.add(jo.getString("designer_name"));
                                design.add(jo.getString("design"));
                                details.add(jo.getString("details"));
                                date.add(jo.getString("Date"));
                                photo.add(jo.getString("photo"));
                            }
                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);
//                            Toast.makeText(interior_space_recommentation.this, "dataaaaa"+response, Toast.LENGTH_SHORT).show();
                            l1.setAdapter(new customviewdesign(interior_space_recommentation.this,interior,design,details,date,photo));
//                    l1.setOnItemClickListener(viewuser.this);

                        } catch (Exception e) {
                            Log.d("=========", e.toString());
                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(interior_space_recommentation.this, "err"+error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();

                            params.put("shape", shape);
                            params.put("length", height);
                            params.put("width", width);
                        return params;
                    }
                };
                queue.add(stringRequest);

//                    RequestQueue queue = Volley.newRequestQueue(interior_space_recommentation.this);
//                    String url = "http://" + sh.getString("ip", "") + ":5000/space_recom";
//
//                    // Request a string response from the provided URL.
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            // Display the response string.
//                            Log.d("+++++++++++++++++", response);
//                            try {
//                                JSONObject json = new JSONObject(response);
//                                String res = json.getString("task");
//
//                                if (res.equalsIgnoreCase("valid")) {
//                                    space();
//
//                                    Intent ik = new Intent(getApplicationContext(), interior_space_recommentation.class);
//                                    startActivity(ik);
//
//                                } else {
//
//                                    Toast.makeText(interior_space_recommentation.this, "Invalid", Toast.LENGTH_SHORT).show();
//
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//
//                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
//                        }
//                    }) {
//                        @Override
//                        protected Map<String, String> getParams() {
//                            Map<String, String> params = new HashMap<String, String>();
//                            params.put("shape", shape);
//                            params.put("height", height);
//                            params.put("width", width);
////                            params.put("lid", sh.getString("lid", ""));
//
//                            return params;
//                        }
//                    };
//                    queue.add(stringRequest);


                }

        });



    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        shape=array[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}