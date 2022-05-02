package riscrypt.tiwed.indianebooklibrary4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLEncoder;

public class Main2Activity extends AppCompatActivity {

    WebView pdfview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        pdfview=(WebView)findViewById(R.id.pdfview);
        pdfview.getSettings().setJavaScriptEnabled(true);

        // String Name=getIntent().getStringExtra("Name");
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Pdf File");
        pd.setMessage("Opening.....");
        String Pdfurl=getIntent().getStringExtra("Pdfurl");

        pdfview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });

        String url="";
        try
        {

            url= URLEncoder.encode(Pdfurl,"UTF-8");

        }catch (Exception ex)
        {}

        pdfview.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);


    }
}
