package com.example.dhp.codefundo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SpecificDayGroupAttendence extends AppCompatActivity {


    EditText groupid;
    String rollnumber[];
    String ma[];
    String ta[];
    EditText datetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_day_group_attendence);

        groupid =findViewById(R.id.groupid);
        datetext=findViewById(R.id.date);
        Button submit = findViewById(R.id.submitbutton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = groupid.getText().toString().trim();
                String findday = datetext.getText().toString().trim();
                if (findday.length() != 0) {
                    ReturnColumnName rcn = new ReturnColumnName(findday);
                    findday = rcn.returncolumn();

                    AttendanceDbHelper dbHelper = new AttendanceDbHelper(getApplicationContext());
                    SQLiteDatabase db = dbHelper.getReadableDatabase();

                    Cursor cx = db.rawQuery("SELECT count(name) FROM sqlite_master where type = 'table'", null);
                    cx.moveToFirst();
                    int counted = cx.getInt(0);
                    Log.v("Counted", counted + "");
                    String[] presentgroups = new String[counted - 1];

                    Cursor cy = db.rawQuery("SELECT name FROM sqlite_master where type = 'table'", null);
                    cy.moveToFirst();
                    int i = 0;
                    while (!cy.isAfterLast()) {
                        if (!cy.getString(0).equals("android_metadata")) {
                            presentgroups[i] = cy.getString(0).trim();
                            Log.v("name", cy.getString(0));
                            i++;
                        }
                        cy.moveToNext();
                    }
                    int flag = 0;
                    for (int u = 0; u < presentgroups.length; u++) {
                        if (name.equals(presentgroups[u])) {
                            flag = 1;
                        }
                    }

                    if (flag == 0) {
                        groupid.setError("No such group record present");
                        groupid.requestFocus();
                        return;
                    }


                    Cursor cyz = db.rawQuery("select * from " + name + " limit 0", null);
                    Log.v("Thread start", "Start?");
                    int checking = 0;
                    if (cyz.getColumnIndex(findday) != -1) {


                        String query = "select rollNumber," + findday + " from " + name;

                        String query2 = "select count(*) from " + name;
                        Cursor c1 = db.rawQuery(query2, null);
                        c1.moveToFirst();
                        int count = c1.getInt(0);
                        Log.v("countoftotalstudents", count + "");
                        rollnumber = new String[count];
                        ma = new String[count];
                        ta = new String[count];
                        Cursor c = db.rawQuery(query, null);
                        c.moveToFirst();
                        int w = 0;
                        while (!c.isAfterLast()) {
                            rollnumber[w] = c.getString(0);
                            ma[w] = c.getInt(1) + "";
                            w++;
                            Log.v("Rollnumber", c.getString(0));
                            c.moveToNext();
                        }

                        ListView rollnumber2 = findViewById(R.id.roll_listview);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.total_attendence_textview, R.id.typetext, rollnumber);
                        rollnumber2.setAdapter(arrayAdapter);


                        ListView markedattendence = findViewById(R.id.ma_listview);
                        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.total_attendence_textview, R.id.typetext, ma);
                        markedattendence.setAdapter(arrayAdapter2);
//
//                ListView totalattendence = findViewById(R.id.ta_listview);
//                ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.total_attendence_textview, R.id.typetext, ta);
//                totalattendence.setAdapter(arrayAdapter3);
                    } else {
                        Toast.makeText(getApplicationContext(), "NO records on such date is present", Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                }else{
                    datetext.setError("This cannot be null");
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(),UserAccount.class);
        startActivity(i);
    }
}




