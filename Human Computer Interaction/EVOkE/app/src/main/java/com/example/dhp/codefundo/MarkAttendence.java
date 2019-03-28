package com.example.dhp.codefundo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class MarkAttendence extends AppCompatActivity {
    ListView simpleList;
    UUID[] b;
    String groupid;
    String date;
    int[] mAttendance, tAttendance;
    String[] tStudentRoll;
    private String[] personnames, personrolls;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), PersonGroup.class);
        i.putExtra("groupId", groupid);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendence);

        personnames = getIntent().getStringArrayExtra("personsnames");
        personrolls = getIntent().getStringArrayExtra("personrolls");
        groupid = getIntent().getStringExtra("groupId");
        simpleList = (ListView) findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listview, R.id.textView, personnames);
        simpleList.setAdapter(arrayAdapter);

        mAttendance = new int[personrolls.length];

        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        ReturnColumnName col = new ReturnColumnName(date);
        date = col.returncolumn();
        Log.v("Date is: ", date);
        Thread th = new Thread() {
            @Override
            public void run() {
                Log.v("Thread start", "Start?");
                int counttotal = 0;
                AttendanceDbHelper attendanceDbHelper = new AttendanceDbHelper(getApplicationContext());
                SQLiteDatabase db2 = attendanceDbHelper.getWritableDatabase();
                Cursor c = db2.rawQuery("select * from " + groupid + " limit 0", null);
                Log.v("Thread start", "Start?");
                int checking = 0;
                if (c.getColumnIndex(date) == -1) {
                    String query1 = "alter table " + groupid + " add column \"" + date + "\" INTEGER";
                    db2.execSQL(query1);
                    Log.v("database check", "column created");
                    checking = 1;
                }
                db2.close();

                SQLiteDatabase dbx = attendanceDbHelper.getReadableDatabase();
                if (checking == 1) {
                    String query2 = "select count(*) from " + groupid;
                    Cursor c2 = dbx.rawQuery(query2, null);
                    c2.moveToFirst();
                    while (!c2.isAfterLast()) {
                        counttotal = c2.getInt(0);
                        c2.moveToNext();
                    }
                    Log.v("database check", counttotal + "");
                    tAttendance = new int[counttotal];
                    tStudentRoll = new String[counttotal];
                    String query3 = "select rollNumber,totalAttendence from " + groupid;
                    Cursor c3 = dbx.rawQuery(query3, null);
                    c3.moveToFirst();
                    int i = 0;
                    while (!c3.isAfterLast()) {
                        tStudentRoll[i] = c3.getString(0);
                        tAttendance[i] = c3.getInt(1);
                        i++;
                        c3.moveToNext();
                        Log.v("database check", "total roll and attendence find");
                    }
                }
                Log.v("Thread start", "Start?");

                for (int i = 0; i < personrolls.length; i++) {
                    String query4 = "select markedAttendence from " + groupid + " where rollNumber = \"" + personrolls[i] + "\"";
                    Cursor c4 = dbx.rawQuery(query4, null);
                    c4.moveToFirst();
                    while (!c4.isAfterLast()) {
                        mAttendance[i] = c4.getInt(0);
                        c4.moveToNext();
                    }
                }
                Log.v("database check", "getting marked attendence");
                dbx.close();

                SQLiteDatabase db3 = attendanceDbHelper.getWritableDatabase();
                if (counttotal != 0) {
                    for (int i = 0; i < tAttendance.length; i++) {
                        ContentValues values = new ContentValues();
                        values.put(BatchEntry.totalAttendence, tAttendance[i] + 1);
                        values.put(date, 0);
                        db3.update(groupid, values, "rollNumber = \"" + tStudentRoll[i] + "\"", null);
                        Log.v("database check", "total attendence updated");
                    }
                }

                for (int i = 0; i < personrolls.length; i++) {
                    ContentValues values1 = new ContentValues();
                    String query5 = "select " + date + " from " + groupid + " where rollNumber = \"" + personrolls[i] + "\"";
                    Cursor c5 = db3.rawQuery(query5, null);
                    c5.moveToFirst();
                    int flag = c5.getInt(0);
                    if (flag != 1) {
                        values1.put(date, 1);
                        values1.put(BatchEntry.markedAttendence, mAttendance[i] + 1);
                        db3.update(groupid, values1, "rollNumber = \"" + personrolls[i] + "\"", null);
                    }
                }
                Log.v("database check", "marked attendence updated");
                db3.close();

            }
        };
        th.start();

    }
}

