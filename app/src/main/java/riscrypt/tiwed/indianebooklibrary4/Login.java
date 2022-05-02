package riscrypt.tiwed.indianebooklibrary4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Button b1;
    EditText t1,t2;
    TextView tbtn;
    ProgressBar bar;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            startActivity(new Intent(getApplicationContext(),HomeScreen.class));
            finish();
        }else
        {
            Toast.makeText(getApplicationContext(),"Curren user not exit",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        t1=(EditText)findViewById(R.id.email);
        t2=(EditText)findViewById(R.id.pwd);
        tbtn=(TextView) findViewById(R.id.tbtn2);
        bar=(ProgressBar)findViewById(R.id.progressBar);
        bar.setVisibility(View.INVISIBLE);
        b1=findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailpwd();
            }
        });
        tbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Registration.class));
                finish();
            }
        });
    }



    private void emailpwd() {
        bar.setVisibility(View.VISIBLE);
        String email=t1.getText().toString();
        String password=t2.getText().toString();
        //  mAuth = FirebaseAuth.getInstance();


        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            bar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),"LOGIN SUCCESSFULL",Toast.LENGTH_LONG).show();
                            //   Intent  intent=new Intent(MainActivity.this,Dashboard.class);
                            //   intent.putExtra("email",mAuth.getCurrentUser().getEmail());
                            startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                            finish();
                        } else
                        {
                            bar.setVisibility(View.INVISIBLE);
                            t1.setText("");
                            t2.setText("");
                            Toast.makeText(getApplicationContext(),"FAILED",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}
