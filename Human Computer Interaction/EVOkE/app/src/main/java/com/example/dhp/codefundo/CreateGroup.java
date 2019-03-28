package com.example.dhp.codefundo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.PersonGroup;

public class CreateGroup extends AppCompatActivity {

    static final String SERVER_HOST = "https://southeastasia.api.cognitive.microsoft.com/face/v1.0";
    static final String SUBSCRIPTION_KEY = "7d209a1ac7694c0d9f76e7d11531f454";
    String persongroupId;
    String persongroupname;
    String persongroupuserdata;
    FaceServiceRestClient faceServiceClient;
    ProgressDialog createGroupDialog;
    EditText groupid;
    EditText groupname;
    EditText groupuserdata;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        groupid = findViewById(R.id.groupidname);
        groupname = findViewById(R.id.groupname);
        groupuserdata = findViewById(R.id.groupuserdata);
        createGroupDialog = new ProgressDialog(this);
        createGroupDialog.setMessage("Creating Group");
        createGroupDialog.setCanceledOnTouchOutside(false);


        Button create = findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                persongroupId = groupid.getText().toString().trim().replaceAll("\\s+", "").toLowerCase();
//                Toast.makeText(getApplicationContext(), "Group created with name:" + persongroupId, Toast.LENGTH_SHORT).show();
                persongroupname = groupname.getText().toString().trim();
                persongroupuserdata = groupuserdata.getText().toString().trim();
                if (persongroupId.length() == 0) {
                    groupid.setError("This field can't be null");
                    groupid.requestFocus();
                    return;
                }
                if (persongroupname.length() == 0) {
                    groupname.setError("This field can't be null");
                    groupname.requestFocus();
                    return;
                }
                if (persongroupuserdata.length() == 0) {
                    groupuserdata.setError("This field can't be null");
                    groupuserdata.requestFocus();
                    return;
                }
                createGroup(persongroupId, persongroupname, persongroupuserdata);
                createGroupDialog.show();
                if(flag == 1)
                    createGroupDialog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    private void createGroup(final String grpid, final String grpname, final String grpuserdata) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
        StrictMode.setThreadPolicy(policy);
        try {
            faceServiceClient = new FaceServiceRestClient(SERVER_HOST, SUBSCRIPTION_KEY);
            PersonGroup[] all_person_groups = faceServiceClient.getPersonGroups();
            for (PersonGroup p : all_person_groups) {
                if (p.personGroupId.equals(grpid)) {
                    createGroupDialog.dismiss();
                    groupid.setError("This group already exist");
                    groupid.requestFocus();
                    flag = 1;
                    return;
                }
            }

            faceServiceClient.createPersonGroup(grpid, grpname, grpuserdata);
            Toast.makeText(getApplicationContext(), "Person Group has been created", Toast.LENGTH_SHORT).show();
//            Log.v("Group id ",grpid);
            BatchEntry batchEntry = new BatchEntry(grpid);
            AttendanceDbHelper attendanceDbHelper = new AttendanceDbHelper(getApplicationContext());
            SQLiteDatabase db = attendanceDbHelper.getWritableDatabase();
            attendanceDbHelper.onCreate(db);
            db.close();
//            SQLiteDatabase dbs = attendanceDbHelper.getReadableDatabase();
//            Cursor c = dbs.rawQuery("SELECT name FROM sqlite_master where type = 'table'",null);
//            c.moveToFirst();
//            while(!c.isAfterLast()){
//                Log.v("table name ", c.getString(0));
//                c.moveToNext();
//            }
//            dbs.close();
//            Log.v("Table Created", "Table created");

            createGroupDialog.dismiss();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);

        } catch (Exception e) {
            e.printStackTrace();
            createGroupDialog.dismiss();
        }
    }
}

