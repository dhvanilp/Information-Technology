package com.example.dhp.codefundo;

import android.app.ProgressDialog;
import android.content.ContentValues;
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
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dhp.codefundo.Imageutils.ImageAttachmentListener;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.CreatePersonResult;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceRectangle;
import com.microsoft.projectoxford.face.contract.Person;
import com.microsoft.projectoxford.face.rest.ClientException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class CreatePerson extends AppCompatActivity implements ImageAttachmentListener {
    private final int PICK_IMAGE = 1;
    EditText personName;
    EditText personGroup;
    EditText personroll;
    Button submitPerson;
    ProgressDialog detectionProgressDialog;
    HashMap<String, Person> personsData;
    Person[] persons;
    Face[] result;
    static ImageView image;
    InputStream io;
    Imageutils imageutils;
    String groupid;
    String emailadd;
    EditText emailidstudent;
    private Bitmap bitmap;
    private String file_name;
    private FaceServiceClient faceServiceClient;
    static int isDetected = 2;

    private static Bitmap drawFaceRectanglesOnBitmap(Bitmap originalBitmap, Face[] faces) {
        Log.v("FACES:", faces.length + "");
        if(faces.length == 0)
        {
            isDetected = 0;
            image.setTag("None");
        }
        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        int stokeWidth = 2;
        paint.setStrokeWidth(stokeWidth);
        if (faces != null) {
            for (Face face : faces) {
                FaceRectangle faceRectangle = face.faceRectangle;
                canvas.drawRect(
                        faceRectangle.left,
                        faceRectangle.top,
                        faceRectangle.left + faceRectangle.width,
                        faceRectangle.top + faceRectangle.height,
                        paint);
            }
        }
        image.setTag("Final");
        return bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_person);
        if (getIntent().hasExtra("groupId")) {
            groupid = getIntent().getStringExtra("groupId");
            Log.d("print:", groupid);
        }
        detectionProgressDialog = new ProgressDialog(this);
        detectionProgressDialog.setMessage("Creating person");
        detectionProgressDialog.setCanceledOnTouchOutside(false);
        imageutils = new Imageutils(this);

        personName = findViewById(R.id.personName);
        personGroup = findViewById(R.id.personGroup);
        personroll = findViewById(R.id.personRoll);
        personsData = new HashMap<>();
        emailidstudent = findViewById(R.id.emailaddress);
        personGroup.setText(groupid);
        personGroup.setEnabled(false);
        faceServiceClient = new FaceServiceRestClient(PersonGroup.SERVER_HOST, PersonGroup.SUBSCRIPTION_KEY);

        image = findViewById(R.id.image);
        image.setTag("Initial");
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageutils.imagepicker(1);
            }
        });
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    persons = faceServiceClient.getPersons(groupid);
                    for (Person person : persons) {
                        personsData.put(person.userData, person);
                    }
                    Iterator it = personsData.entrySet().iterator();
                    while (it.hasNext()) {
                        HashMap.Entry pair = (HashMap.Entry) it.next();
                        Log.v("addddddddd", pair.getKey().toString());
//                Log.v("addddddddd", pair.getValue().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();


        submitPerson = findViewById(R.id.createPerson);
        submitPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createPerson();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void detectAndFrame(final Bitmap imageBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        io = new ByteArrayInputStream(outputStream.toByteArray());
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        AsyncTask<InputStream, String, Face[]> detectTask = new AsyncTask<InputStream, String, Face[]>() {
            @Override
            protected Face[] doInBackground(InputStream... params) {
                try {
                    publishProgress("Detecting...");
                    result = faceServiceClient.detect(
                            params[0],
                            true,         // returnFaceId
                            false,        // returnFaceLandmarks
                            null           // returnFaceAttributes: a string like "age, gender"
                    );
                    if (result == null) {
//                        isDetected = 0;
                        publishProgress("Detection Finished. Nothing detected");
                        return null;
                    } else {
                        Log.v("Face detected", "Face detected");
                        publishProgress(
                                String.format("Detection Finished. %d face(s) detected",
                                        result.length));
                        return result;
                    }
                } catch (Exception e) {
//                    isDetected = 0;
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
                if (result == null){
//                    isDetected = 0;
                    Log.v("OnPostExecute", "OnPostDetected");
                    return;
                }
                image.setImageBitmap(drawFaceRectanglesOnBitmap(imageBitmap, result));
            }
        };
        detectTask.execute(inputStream);
    }

    private void createPerson() throws IOException, ClientException {
        detectionProgressDialog = new ProgressDialog(this);
        detectionProgressDialog.setMessage("Creating person");
        detectionProgressDialog.show();
        detectionProgressDialog.setCanceledOnTouchOutside(false);

        final String name = personName.getText().toString();
        final String group = personGroup.getText().toString();
        final String rollNumber = personroll.getText().toString();
        emailadd = emailidstudent.getText().toString();

        if (name.length() == 0) {
            detectionProgressDialog.dismiss();
            personName.setError("This field can't be null");
            personName.requestFocus();
            detectionProgressDialog.dismiss();
            return;
        }
        if (group.length() == 0) {
            detectionProgressDialog.dismiss();
            personGroup.setError("This field can't be null");
            personGroup.requestFocus();
            detectionProgressDialog.dismiss();
            return;
        }
        if (rollNumber.length() == 0) {
            detectionProgressDialog.dismiss();
            personroll.setError("This field can't be null");
            personroll.requestFocus();
            detectionProgressDialog.dismiss();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailadd).matches()) {
            emailidstudent.setError("Enter  a valid email id");
            emailidstudent.requestFocus();
            detectionProgressDialog.dismiss();
            return;
        }

        if (image.getTag() == "Initial") {
            detectionProgressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Select an Image", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isDetected == 0) {
            detectionProgressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "No face detected", Toast.LENGTH_SHORT).show();
            return;
        }

        if (personsData.get(rollNumber) == null) {
            Thread background = new Thread() {
                @Override
                public void run() {
                    try {
                        CreatePersonResult res = faceServiceClient.createPerson(group, name, rollNumber);
                        AttendanceDbHelper attendanceDbHelper = new AttendanceDbHelper(getApplicationContext());
                        SQLiteDatabase db = attendanceDbHelper.getWritableDatabase();
//                        BatchEntry batchEntry = new BatchEntry(groupid);

                        ContentValues values = new ContentValues();
                        values.put(BatchEntry.rollNumber, rollNumber);
                        values.put(BatchEntry.studentName, name);
                        values.put(BatchEntry.emailAddress,emailadd);
                        values.put(BatchEntry.markedAttendence, 0);
                        values.put(BatchEntry.totalAttendence, 0);

                        long newRowId = db.insert(groupid, null, values);
                        db.close();

//                        SQLiteDatabase dbs = attendanceDbHelper.getReadableDatabase();
//                        String query = "select rollNumber from " + groupid;
//                        Cursor c = dbs.rawQuery(query, null);
//                        c.moveToFirst();
//                        while (!c.isAfterLast()) {
//                            Log.v("rollNumber ", c.getString(0));
//                            c.moveToNext();
//                        }
//                        dbs.close();

//                        detectionProgressDialog.dismiss();
                        calladdface(res.personId, rollNumber);

                    } catch (Exception e) {
                        e.printStackTrace();
                        detectionProgressDialog.dismiss();
                    }
                }
            };
            background.start();
        } else {
//            Toast.makeText(getApplicationContext(), "Person exists", Toast.LENGTH_SHORT).show();
            personroll.setError("Roll Number exists");
            personroll.requestFocus();
            detectionProgressDialog.dismiss();
        }
    }

    private void calladdface(UUID personId, String roll) {
        FaceRectangle faceRectangle = null;
        for (Face face : result) {
            faceRectangle = face.faceRectangle;
        }
        try {
            faceServiceClient.addPersonFace(groupid, personId, io, roll, faceRectangle);
            calltraing(groupid);
            detectionProgressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
            detectionProgressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), PersonGroup.class);
        i.putExtra("groupId", groupid);
        startActivity(i);
    }

    private void calltraing(String testing) {
        try {
            faceServiceClient.trainPersonGroup(testing);
            detectionProgressDialog.dismiss();
            Intent i = new Intent(getApplicationContext(), PersonGroup.class);
            i.putExtra("groupId", groupid);
            startActivity(i);
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        this.bitmap = file;
        this.file_name = filename;
        image.setImageBitmap(file);

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

}

