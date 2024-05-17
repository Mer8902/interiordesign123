package com.example.interiordesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewdesign extends AppCompatActivity {
    ListView l1;
    SharedPreferences sh;
    ArrayList<String>interior,design,photo,details,date;
    String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdesign);
        l1=findViewById(R.id.viewdesign);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        url ="http://"+sh.getString("ip", "") + ":5000/view_design1";
        RequestQueue queue = Volley.newRequestQueue(viewdesign.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
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
//                    Toast.makeText(viewdesign.this, "dataaaaa"+response, Toast.LENGTH_SHORT).show();
                    l1.setAdapter(new customviewdesign(viewdesign.this,interior,design,details,date,photo));
//                    l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewdesign.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        queue.add(stringRequest);
    }
}