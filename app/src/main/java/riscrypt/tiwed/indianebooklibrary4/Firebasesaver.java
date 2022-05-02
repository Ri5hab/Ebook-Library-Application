package riscrypt.tiwed.indianebooklibrary4;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;

public class Firebasesaver extends AppCompatActivity {

    ImageView img1,img2;
    Button brouseimg,brousepdf,upload;
    Uri imgpath,pdfpath;
    Bitmap bitmap;
    StorageReference storageReference;
    TextView tp;
    String objimg;
    String objpdf;
    EditText t1,t2,t3,t4;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==1 && resultCode==RESULT_OK)
        {
            imgpath=data.getData();
            try{
                int width=657;
                int height=1024;
                InputStream inputStream=getContentResolver().openInputStream(imgpath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                bitmap=Bitmap.createScaledBitmap(bitmap,width,height,true);
                img1.setImageBitmap(bitmap);
            }catch (Exception ex)
            {

            }
            uploadtofirebase();
        }else if(requestCode==2 && resultCode==RESULT_OK)
        {
            pdfpath=data.getData();
            if(pdfpath!=null) {
                //tp.setVisibility(View.VISIBLE);
                img2.setBackground(getDrawable(R.drawable.pdf));


            }
            uploadtopdf(pdfpath);
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebasesaver);

        Firebase.setAndroidContext(getApplicationContext());

        img1=(ImageView)findViewById(R.id.img1);
        img2=(ImageView)findViewById(R.id.img2);
        tp=(TextView)findViewById(R.id.tp);
        tp.setVisibility(View.INVISIBLE);
        brouseimg =(Button)findViewById(R.id.b1);
        brousepdf =(Button)findViewById(R.id.b2);
        //  uploadtopdf(pdfpath);
        //   upload=(Button)findViewById(R.id.b2);

     /*   upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadtofirebase();
            }
        });*/

        brouseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(Firebasesaver.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Please Select Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();


            }
        });

        brousepdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(Firebasesaver.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent=new Intent();
                                intent.setType("application/pdf");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,"Please Select Pdf"),2);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();


            }
        });


    }

    public void uploadtofirebase() {

        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("File Uploader");
        dialog.show();
        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference uploader=storage.getReference().child("img/"+System.currentTimeMillis()+".jpeg");
        uploader.putFile(imgpath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {


                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                            @Override
                            public void onSuccess(Uri uri) {

                                dialog.dismiss();
                                objimg =uri.toString();// uri.toString();

                                Toast.makeText(getApplicationContext(),"Image Upload",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });/*
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    }
                });*/
    }



    public void click(View view) {
        t1 = (EditText) findViewById(R.id.edittext1);
        t2 = (EditText) findViewById(R.id.edittext2);
        t3 = (EditText) findViewById(R.id.edittext3);
        t4 = (EditText) findViewById(R.id.edittext4);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        //        t2=(EditText) findViewById(R.id.edittext2);
        String Imgurl = objimg;

        String Name = t1.getText().toString().trim();
        String py=t2.getText().toString().trim();
        String pag=t3.getText().toString().trim();
        String pby=t4.getText().toString().trim();

        String Pdfurl = objpdf;//.toString();

        //  uploadtofirebase();
        //  uploadtopdf(pdfpath);

        User user = new User(Name, Imgurl, Pdfurl,py,pag,pby);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference node = db.getReference("Ebook");
        if (Imgurl != null&&Pdfurl!=null&& Name!=null&&py!=null&&pag!=null&&pby!=null) {
            node.push().setValue(user);
            Toast.makeText(getApplicationContext(),"Publish Your Ebook" , Toast.LENGTH_LONG).show();
            this.finish();
            //   Intent intent=new Intent(Firebasesaver.this,MainActivity.class);
            //   startActivity(intent);



        }else
        {
            Toast.makeText(getApplicationContext(),"Fill Prorper Information" , Toast.LENGTH_LONG).show();
        }

    }

    public void uploadtopdf(final Uri pdfpath)
    {
        final ProgressDialog pd1=new ProgressDialog(this);
        pd1.setTitle("Pdf Uploading...");
        pd1.show();


        FirebaseStorage storage1=FirebaseStorage.getInstance();
        final      StorageReference reference= storage1.getReference().child("pdf/"+System.currentTimeMillis()+".pdf");
        reference.putFile(pdfpath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {


                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onSuccess(Uri uri) {
                                objpdf=uri.toString();
                                //  User objpdf=new User(t1.getText().toString().uri.toString());
                                pd1.dismiss();
                                Toast.makeText(getApplicationContext(),"Pdf File Upload",Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                });
    }
}
