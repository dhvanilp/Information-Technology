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
import android.widget.TabHost;
import android.widget.Toast;

public class PersonSpecificDayAttendence extends AppCompatActivity {


    EditText groupid;
    String rollnumbers[];
    String ma[];
    String ta[];
    EditText personrollno;
    EditText datefield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_specific_day_attendence);

        groupid =findViewById(R.id.groupid);
        datefield=findViewById(R.id.date);
        personrollno = findViewById(R.id.personrollnumber);
        Button submit = findViewById(R.id.submitbutton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = groupid.getText().toString().trim();
                String findday = datefield.getText().toString().trim();
                String finalpersonroll = personrollno.getText().toString().trim();
                if(findday.length()!=0){
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
                    int flage = 0;
                    while (!c.isAfterLast()) {
                        rollnumbers[w] = c.getString(0);
                        if (rollnumbers[w].equals(finalpersonroll)) {
                            flage = 1;
                            break;
                        }
                        w++;
                        Log.v("Rollnumber", c.getString(0));
                        c.moveToNext();
                    }

                    if (flage == 1) {

                        String querytstudentdetails = "select "+findday+" from "+name+" where rollNumber = \""+finalpersonroll+"\"";
                        Cursor cursorstudentdetails =db.rawQuery(querytstudentdetails,null);
                        cursorstudentdetails.moveToFirst();
                        ma = new String[1];
                        ma[0]=cursorstudentdetails.getInt(0)+"";
                        String[] rollnumbertodisplay = new String[1];
                        rollnumbertodisplay[0]=finalpersonroll;

                        ListView rollnumber2 = findViewById(R.id.roll_listview);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.total_attendence_textview, R.id.typetext, rollnumbertodisplay);
                        rollnumber2.setAdapter(arrayAdapter);


                        ListView markedattendence = findViewById(R.id.ma_listview);
                        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.total_attendence_textview, R.id.typetext, ma);
                        markedattendence.setAdapter(arrayAdapter2);
//
//                ListView totalattendence = findViewById(R.id.ta_listview);
//                ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.total_attendence_textview, R.id.typetext, ta);
//                totalattendence.setAdapter(arrayAdapter3);

                    } else {
                        Toast.makeText(getApplicationContext(), "No such Roll number exists", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getApplicationContext(),"NO records on such date is present",Toast.LENGTH_SHORT).show();
                }
                db.close();
                }else{
//                    Toast.makeText(getApplicationContext(),"This field can't be null",Toast.LENGTH_SHORT).show();
                    datefield.setError("This field can't be null");
                    datefield.requestFocus();
                    return;
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




