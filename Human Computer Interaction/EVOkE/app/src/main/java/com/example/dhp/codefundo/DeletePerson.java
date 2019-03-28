package com.example.dhp.codefundo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Person;
import com.microsoft.projectoxford.face.rest.ClientException;

import java.io.IOException;
import java.util.UUID;

public class DeletePerson extends AppCompatActivity {
    static final String SERVER_HOST = "https://southeastasia.api.cognitive.microsoft.com/face/v1.0";
    static final String SUBSCRIPTION_KEY = "7d209a1ac7694c0d9f76e7d11531f454";
    private static FaceServiceClient faceServiceClient;
    ListView simpleList;
    UUID[] personID;
    String groupid;
    private String[] rollNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_person);
        faceServiceClient = new FaceServiceRestClient(SERVER_HOST, SUBSCRIPTION_KEY);

        if (getIntent().hasExtra("groupId")) {
            groupid = getIntent().getStringExtra("groupId");
            Log.d("print:", groupid);
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Person[] personarray = faceServiceClient.getPersons(groupid);

            rollNumber = new String[personarray.length];
            personID = new UUID[personarray.length];
            int i = 0;
            for (Person p : personarray) {
                rollNumber[i] = (i + 1) + ".)" + p.userData;
                personID[i] = p.personId;
                Log.d("names:", rollNumber[i]);
                i++;
            }

            simpleList = findViewById(R.id.simpleListView);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listview, R.id.textView, rollNumber);
            simpleList.setAdapter(arrayAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position,
                                    long id) {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    Person person = faceServiceClient.getPerson(groupid, personID[position]);
                    AttendanceDbHelper attendanceDbHelper = new AttendanceDbHelper(getApplicationContext());
                    SQLiteDatabase db = attendanceDbHelper.getWritableDatabase();
                    String query = "delete from " + groupid + " where rollNumber=\"" + person.userData + "\"";
                    db.execSQL(query);
                    db.close();

//                    SQLiteDatabase dbs = attendanceDbHelper.getReadableDatabase();
//                    String query1 = "select rollNumber from " + groupid;
//                    Cursor c = dbs.rawQuery(query1, null);
//                    c.moveToFirst();
//                    while (!c.isAfterLast()) {
//                        Log.v("rollNumber ", c.getString(0));
//                        c.moveToNext();
//                    }
//                    dbs.close();

                    faceServiceClient.deletePerson(groupid, personID[position]);

                    Toast.makeText(getApplicationContext(), "Person deleted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), PersonGroup.class);
                    i.putExtra("groupId", groupid);
                    startActivity(i);
                } catch (ClientException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), PersonGroup.class);
        i.putExtra("groupId", groupid);
        startActivity(i);

    }

}


