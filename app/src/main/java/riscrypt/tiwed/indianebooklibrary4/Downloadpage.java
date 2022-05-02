package riscrypt.tiwed.indianebooklibrary4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static riscrypt.tiwed.indianebooklibrary4.Razorpay.*;

public class Downloadpage extends AppCompatActivity {

    Button b1;
    ProgressBar p1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloadpage);
        String Tpdfurl = getIntent().getStringExtra("ZPdfurl");
        String TName = getIntent().getStringExtra("ZName");
        b1 = findViewById(R.id.button);
        p1 = findViewById(R.id.progressBar3);
        p1.setVisibility(View.GONE);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                //finish();
                downloadfile(getApplicationContext(), TName, ".pdf", DIRECTORY_DOWNLOADS, Tpdfurl);
                p1.setVisibility(View.VISIBLE);
                Thread thread=new Thread()
                {
                    @Override
                    public void run() {
                        super.run();
                        for(int i=0;i<=100;)
                        {
                            try
                            {
                                sleep(50);
                            }catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            p1.setProgress(i);
                            i=i+10;

                        }
                        p1.setVisibility(View.INVISIBLE);

                    }

                };
                thread.start();
                showAlertDialog();

            }
        });
    }

    /* public static void download(String pName, String pPdfurl) {
         downloadfile(getApplicationContext(),pName, ".pdf", DIRECTORY_DOWNLOADS, pPdfurl);
     }*/
    public static void downloadfile(Context context, String fileName, String fileExtention, String destinationDirectory, String url) {
        DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtention);
        dm.enqueue(request);
        //   Toast.makeText(getApplicationContext(), "PDF DOWNLOAD SUCCESSFULL", Toast.LENGTH_LONG).show();

    }
    public void showAlertDialog()
    {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Download Successful");
        builder.setMessage("Your PDF File is Download Successfully.(check to your file manager) Then back to Home Page..");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                finish();
            }
        });

        builder.create().show();
    }

}



