package com.example.dhp.codefundo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SpecificPersonTA extends AppCompatActivity {


    EditText groupid;
    String rollnumbers[];
    String ma[];
    String ta[];
    EditText personrollno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_person_t);
        groupid =findViewById(R.id.groupid);
        personrollno=findViewById(R.id.rollnumber);
        Button submit = findViewById(R.id.submitbutton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = groupid.getText().toString().trim();
                String findrollno = personrollno.getText().toString().trim();

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


                String query = "select rollNumber from " + name;

                String query2 = "select count(*) from " + name;
                Cursor c1 = db.rawQuery(query2, null);
                c1.moveToFirst();
                int count = c1.getInt(0);
                Log.v("countoftotalstudents", count + "");
                rollnumbers = new String[count];
                Cursor c = db.rawQuery(query, null);
                c.moveToFirst();
                int w = 0;
                int flage=0;
                while (!c.isAfterLast()) {
                    rollnumbers[w] = c.getString(0);
                    c.moveToNext();
                    if(rollnumbers[w].equals(findrollno)){
                        flage=1;
                        break;
                    }
                    w++;
                }

                if( flage!=0){

                    ma = new String[1];
                    ta = new String[1];
                    String[] finalpersonroll = new String[1];
                    finalpersonroll[0]=findrollno;

                    String querydetails = "select markedAttendence,totalAttendence from " + name + " where rollNumber = \"" + findrollno+"\"";

                    Cursor cursordetails = db.rawQuery(querydetails,null);
                    cursordetails.moveToFirst();
                    ma[0]=cursordetails.getInt(0)+"";
                    ta[0]=cursordetails.getInt(1)+"";

                    ListView rollnumber2 = findViewById(R.id.roll_listview);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.total_attendence_textview, R.id.typetext, finalpersonroll);
                    rollnumber2.setAdapter(arrayAdapter);


                    ListView markedattendence = findViewById(R.id.ma_listview);
                    ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.total_attendence_textview, R.id.typetext, ma);
                    markedattendence.setAdapter(arrayAdapter2);

                    ListView totalattendence = findViewById(R.id.ta_listview);
                    ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.total_attendence_textview, R.id.typetext, ta);
                    totalattendence.setAdapter(arrayAdapter3);



                }
                else{
                    personrollno.setError("No such roll number present");
                    personrollno.requestFocus();
                    return;
                }
                db.close();
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
