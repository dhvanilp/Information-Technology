package com.example.dhp.codefundo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Candidate;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceRectangle;
import com.microsoft.projectoxford.face.contract.IdentifyResult;
import com.microsoft.projectoxford.face.rest.ClientException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class PersonGroup extends AppCompatActivity implements Imageutils.ImageAttachmentListener {

    static final String SERVER_HOST = "https://southeastasia.api.cognitive.microsoft.com/face/v1.0";
    static final String SUBSCRIPTION_KEY = "7d209a1ac7694c0d9f76e7d11531f454";
    static int i = 0;
    static String groupid;
    private static FaceServiceClient faceServiceClient;
    private static String[] personroll, personnames;
    private final int PICK_IMAGE = 1;
    ProgressDialog detectionProgressDialog;
    ImageView iv_attachment;
    Imageutils imageutils;
    Button markattendence;
    private Bitmap bitmap;
    private String file_name;

    private Bitmap drawFaceRectanglesOnBitmap(Bitmap originalBitmap, Face[] faces) {
        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        int stokeWidth = 2;
        paint.setStrokeWidth(stokeWidth);
        if (faces != null) {
            i = 0;
            UUID[] faceuuid = new UUID[faces.length];
            for (Face face : faces) {
                FaceRectangle faceRectangle = face.faceRectangle;
                faceuuid[i] = face.faceId;
                i += 1;
                canvas.drawRect(
                        faceRectangle.left,
                        faceRectangle.top,
                        faceRectangle.left + faceRectangle.width,
                        faceRectangle.top + faceRectangle.height,
                        paint);
            }
            Log.d("print", "*********************");
            findname(faceuuid, groupid);
        }
        return bitmap;
    }

    private void findname(final UUID[] faceofperson, final String groupname) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
        StrictMode.setThreadPolicy(policy);
        faceServiceClient = new FaceServiceRestClient(SERVER_HOST, SUBSCRIPTION_KEY);
        try {
            IdentifyResult[] identifyResults = faceServiceClient.identity(groupname, faceofperson, 1);
            personroll = new String[identifyResults.length];
            personnames = new String[identifyResults.length];
            int k = 0;
            for (IdentifyResult i : identifyResults) {
                for (Candidate candidate : i.candidates) {
                    personnames[k] = faceServiceClient.getPerson(groupname, candidate.personId).name;
                    personroll[k] = faceServiceClient.getPerson(groupname, candidate.personId).userData;
                    k++;
                }
            }
            if (k != 0) {
                markattendence.setEnabled(true);
            }
            if (markattendence.isEnabled() == false) {
                Toast.makeText(getApplicationContext(), "persons identity is not present in database please add identity of these persons", Toast.LENGTH_SHORT).show();
            }
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_group);
        markattendence = findViewById(R.id.markattendence);
        imageutils = new Imageutils(this);
        if (getIntent().hasExtra("groupId")) {
            groupid = getIntent().getStringExtra("groupId");
            Log.d("print:", groupid);
        }
        markattendence.setEnabled(false);
        detectionProgressDialog = new ProgressDialog(this);
        detectionProgressDialog.setMessage("Creating person");
        detectionProgressDialog.setCanceledOnTouchOutside(false);
        faceServiceClient = new FaceServiceRestClient(SERVER_HOST, SUBSCRIPTION_KEY);
        iv_attachment = findViewById(R.id.imageViewAttach);
        iv_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageutils.imagepicker(1);
            }
        });

        markattendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (personnames.length != 0) {
                    Intent i = new Intent(getApplicationContext(), MarkAttendence.class);
                    i.putExtra("personsnames", personnames);
                    i.putExtra("personrolls", personroll);
                    i.putExtra("groupId", groupid);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "No matches of this person found in our database", Toast.LENGTH_SHORT).show();
                }
            }
        });
        final Button deletePerson = findViewById(R.id.deletePerson);
        deletePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DeletePerson.class);
                i.putExtra("groupId", groupid);
                startActivity(i);
            }
        });

        final Button deletegroup = findViewById(R.id.deleteGroup);
        deletegroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteGroup(groupid);
            }
        });
        Button createPerson = findViewById(R.id.createPerson);

        createPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreatePerson.class);
                intent.putExtra("groupId", groupid);
                startActivity(intent);
            }
        });

    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.post_menu, menu);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.email:
//                startActivity(new Intent(this, EmailActivity.class));
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private void detectAndFrame(final Bitmap imageBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        AsyncTask<InputStream, String, Face[]> detectTask = new AsyncTask<InputStream, String, Face[]>() {
            @Override
            protected Face[] doInBackground(InputStream... params) {
                try {
                    publishProgress("Detecting...");
                    Face[] result = faceServiceClient.detect(
                            params[0],
                            true,         // returnFaceId
                            false,        // returnFaceLandmarks
                            null           // returnFaceAttributes: a string like "age, gender"
                    );
                    if (result == null) {
                        publishProgress("Detection Finished. Nothing detected");
                        return null;
                    }
                    publishProgress(
                            String.format("Detection Finished. %d face(s) detected",
                                    result.length));
                    return result;
                } catch (Exception e) {
                    publishProgress("Detection failed");
                    return null;
                }
            }

            @Override
            protected void onPreExecute() {
                //TODO: show progress dialog
                detectionProgressDialog.show();
                detectionProgressDialog.setCanceledOnTouchOutside(false);
            }

            @Override
            protected void onProgressUpdate(String... progress) {
                //TODO: update progress
                detectionProgressDialog.setMessage(progress[0]);
            }

            @Override
            protected void onPostExecute(Face[] result) {
                //TODO: update face frames
                detectionProgressDialog.dismiss();
                if (result == null) return;
                ImageView imageView = (ImageView) findViewById(R.id.imageViewAttach);
                imageView.setImageBitmap(drawFaceRectanglesOnBitmap(imageBitmap, result));
                imageBitmap.recycle();
            }
        };
        detectTask.execute(inputStream);
    }

    private void deleteGroup(final String groupName) {
        new AlertDialog.Builder(this).setTitle("Delete")
                .setMessage("Do you really want to delete this group")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Thread deletinggroup = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    faceServiceClient = new FaceServiceRestClient(SERVER_HOST, SUBSCRIPTION_KEY);
                                    faceServiceClient.deletePersonGroup(groupName);

                                    String query = "drop table if exists " + groupid;
                                    AttendanceDbHelper attendanceDbHelper = new AttendanceDbHelper(getApplicationContext());
                                    SQLiteDatabase db = attendanceDbHelper.getWritableDatabase();
                                    db.execSQL(query);
                                    db.close();
//                                    SQLiteDatabase dbs = attendanceDbHelper.getReadableDatabase();
//                                    Cursor c = dbs.rawQuery("SELECT name FROM sqlite_master where type = 'table'",null);
//                                    c.moveToFirst();
//                                    while(!c.isAfterLast()){
//                                        Log.v("table name ", c.getString(0));
//                                        c.moveToNext();
//                                    }
//                                    dbs.close();

                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        deletinggroup.start();
                    }
                })
                .setNegativeButton("NO", null)
                .show();

    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        this.bitmap = file;
        this.file_name = filename;
        iv_attachment.setImageBitmap(file);
        System.out.print(" fdfsdagkjhgfdsdfghjkjhgfdsdfghj" + file);

        String path = Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        imageutils.createImage(file, filename, path, false);
        detectAndFrame(file);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        imageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageutils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}
