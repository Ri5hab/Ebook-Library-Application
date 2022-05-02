package riscrypt.tiwed.indianebooklibrary4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.sym.Name;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.jar.Attributes;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static java.security.AccessController.getContext;
import static riscrypt.tiwed.indianebooklibrary4.Downloadpage.downloadfile;
import static riscrypt.tiwed.indianebooklibrary4.myAdapter.*;
import static riscrypt.tiwed.indianebooklibrary4.myAdapter.myviewholder.*;

public class Razorpay extends AppCompatActivity  implements PaymentResultListener  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);
     //   String pPdfurl=getIntent().getStringExtra("Pdfurl");
     //   String pName=getIntent().getStringExtra("Name");


        makepayment();

      // Downloadpage.download(pName,pPdfurl);
      //  downloadfile(getApplicationContext(),pName, ".pdf", DIRECTORY_DOWNLOADS, pPdfurl);
    }

/*    public void download(String pName,String pPdfurl) {
        downloadfile(getApplicationContext(),pName, ".pdf", DIRECTORY_DOWNLOADS, pPdfurl);
    }
    public void downloadfile(Context context, String fileName, String fileExtention, String destinationDirectory, String url) {
        DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtention);
        dm.enqueue(request);
        Toast.makeText(getApplicationContext(), "PDF DOWNLOAD SUCCESSFULL", Toast.LENGTH_LONG).show();

    }*/

    private void makepayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_r6oxOQSXEYepDZ");

        checkout.setImage(R.drawable.iebllogo);


        try {
            JSONObject options = new JSONObject();

            options.put("name", "Indian E-book Library");
            options.put("description", "ebook charges");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //   options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "100");//pass amount in currency subunits
            options.put("prefill.email", "");
            options.put("prefill.contact","");

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(Razorpay.this, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }

    }

    @Override
    public void onPaymentSuccess(String s)  {
         Toast.makeText(getApplicationContext(), " PAYMENT SUCCESSFULL"+s, Toast.LENGTH_LONG).show();
      //  Toast.makeText(getApplicationContext(), "pdfDownloadingStep1", Toast.LENGTH_LONG).show();
      //  startActivity(new Intent(getApplicationContext(),Downloadpage.class));
       // finish();
        String pPdfurl=getIntent().getStringExtra("Pdfurl");
        String pName=getIntent().getStringExtra("Name");
        Intent intent4=new Intent(this,Downloadpage.class);
        intent4.putExtra("ZPdfurl", pPdfurl);
        intent4.putExtra("ZName",pName);
        intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent4);
        finish();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(), "PAYMENT FAILED"+s, Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(),HomeScreen.class));
        finish();
    }


}
