package com.example.dhp.codefundo;

/**
 * Created by Suyash on 19-03-2018.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * API Contract for the Pets app.
 */
public final class BatchEntry {

    public final static String rollNumber = "rollNumber";
    public final static String studentName = "studentName";
    public final static String markedAttendence = "markedAttendence";
    public final static String totalAttendence = "totalAttendence";
    public final static String emailAddress = "emailAddress";
    public static String TABLE_NAME;


    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public BatchEntry(String tableName) {
        this.TABLE_NAME = tableName;
    }


    public static class WelcomeActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_welcome);
        }
    }
}

