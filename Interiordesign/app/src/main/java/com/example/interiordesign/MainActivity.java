package com.example.interiordesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sh;
    String IP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=findViewById(R.id.editTextTextPersonName2);
        b1=findViewById(R.id.button);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IP=e1.getText().toString();

                if (IP.equalsIgnoreCase("")) {
                    e1.setError("Enter the valid ip");
                } else {

                    SharedPreferences.Editor edp = sh.edit();
                    edp.putString("ip", IP);
                    edp.commit();
                    Intent ik = new Intent(getApplicationContext(), Login.class);
                    startActivity(ik);

                }
            }
        });

    }
}