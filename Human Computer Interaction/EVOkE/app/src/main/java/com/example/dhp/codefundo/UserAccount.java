package com.example.dhp.codefundo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class UserAccount extends AppCompatActivity {

    Button totalGroupAttendence;
    Button attendenceonParticularDay;
    Button specificPersonTotalattendence;
    Button personSpecificDayAttendence;
    String ans = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        totalGroupAttendence = findViewById(R.id.getTotalstats);
        attendenceonParticularDay = findViewById(R.id.getspecificday);
        specificPersonTotalattendence = findViewById(R.id.getspecificPersonTotal);
        personSpecificDayAttendence = findViewById(R.id.getspecificPerson);

        totalGroupAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),TotalGroupAttendence.class);
                startActivity(i);
            }
        });

        attendenceonParticularDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SpecificDayGroupAttendence.class);
                startActivity(i);
            }
        });

        specificPersonTotalattendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SpecificPersonTA.class);
                startActivity(i);
            }
        });

        personSpecificDayAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),PersonSpecificDayAttendence.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}
