package com.example.dhp.codefundo;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EmailActivity extends AppCompatActivity {
    Button send;
    String pathOfImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                // TODO Auto-generated method stub

                try {
                    GMailSender sender = new GMailSender(
                            "cfdwhitewalkers@gmail.com",
                            "whitewalkerscfd");

                    Log.d("get the path of storage", pathOfImage);
//                    File imgFile = new File(pathOfImage);
//                    if (imgFile.exists()) {
//                        Bitmap myBitmap = BitmapFactory.decodeFile(pathOfImage);
//                        Toast.makeText(getApplicationContext(), "Image found", Toast.LENGTH_LONG).show();
//                        imageView.setImageBitmap(myBitmap);
//                    }
//                    sender.addAttachment(pathOfImage, "Test mail");
                    sender.sendMail("Test mail", "This mail has been sent from android app along with attachment",
                            "cfdwhitewalkers@gmail.com",
                            "suyashghuge@gmail.com");
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
