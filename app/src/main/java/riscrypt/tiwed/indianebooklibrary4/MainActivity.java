package riscrypt.tiwed.indianebooklibrary4;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkConnection();

    }

    public void checkConnection()
    {
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=manager.getActiveNetworkInfo();

        if(null!=activeNetwork){
            if(activeNetwork.getType()==ConnectivityManager.TYPE_WIFI){
                Thread splash = new Thread() {
                    public void run() {
                        try {
                            sleep(2 * 1000);
                            Intent i = new Intent(getBaseContext(), Login.class);
                            startActivity(i);
                            finish();
                        } catch (Exception e) {

                        }
                    }
                };
                splash.start();

            }else if(activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE){
                Thread splash = new Thread() {
                    public void run() {
                        try {
                            sleep(2 * 1000);
                            Intent i = new Intent(getBaseContext(), Login.class);
                            startActivity(i);
                            finish();
                        } catch (Exception e) {

                        }
                    }
                };
                splash.start();

            }
        }else{
            showAlertDialog();
            //  Intent intent=new Intent(MainActivity.this,.class);
            //  startActivity(intent);
        }
    }


    public void showAlertDialog()
    {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Please Check Your Internet Connection");
        builder.setMessage("Please ON the Internet Connection then Enjoy the Application..");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which){

                {
                    Thread splash = new Thread() {
                        public void run() {
                            try {
                                sleep(1 * 1000);
                                Intent i = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(i);
                                finish();
                            } catch (Exception e) {

                            }
                        }
                    };
                    splash.start();

                    NetworkStatsManager manager=(NetworkStatsManager) getApplicationContext().getSystemService(Context.NETWORK_STATS_SERVICE);
                }}
        });

        builder.create().show();
    }
}
