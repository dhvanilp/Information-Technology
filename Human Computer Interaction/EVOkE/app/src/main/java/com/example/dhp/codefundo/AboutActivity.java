package com.example.dhp.codefundo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    TextView about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        about=findViewById(R.id.about);
        about.setText("");
        about.append("Steps to be followed:-\n");
        about.append("1) First create the group in the homescreen and the id should be unique\n");
        about.append("2) Secondly add the data of the person like image roll number in the add person activity\n");
        about.append("3) We will train the our api according to the data given by you \n");
        about.append("4) Click on the imageview which is their and take the image we will match the person accordingly\n");
        about.append("5) Click on the markattendence option in order to mark the attendence of the detected person\n");
        about.append("6) In order to check what is the attendence marked just go to the homescreen and check the details in our account option\n");
        about.append("7) Remember the attendence is marked for the whole day so one day will carry one attendence\n\n\n");
        about.append(" This project can be further extended to far over the boundaries in order to make the india digitalised and not only in classrooms but also in offices and in other departments\n");
        about.setEnabled(false);

    }
}
