package riscrypt.tiwed.indianebooklibrary4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Registration extends AppCompatActivity {

    SignInButton btn;
    Button b1;
    EditText t1,t2;
    TextView tbtn;
    ProgressBar bar;
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth=FirebaseAuth.getInstance();

        t1=(EditText)findViewById(R.id.email);
        t2=(EditText)findViewById(R.id.pwd);
        tbtn=findViewById(R.id.tbtn2);
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
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

        btn=(SignInButton)findViewById(R.id.sbtn);
        processrequest();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processlogin();
            }
        });
    }

    private void processrequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void processlogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                Toast.makeText(getApplicationContext(),"getting information is completed",Toast.LENGTH_LONG).show();
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                Toast.makeText(getApplicationContext(),"error in getting google information",Toast.LENGTH_LONG).show();
            }
        }else
        {
            Toast.makeText(getApplicationContext(),"error after signin ",Toast.LENGTH_LONG).show();
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),"SUCCESSFULL",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(),"FAILED",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void emailpwd() {
        bar.setVisibility(View.VISIBLE);
        String email=t1.getText().toString();
        String password=t2.getText().toString();
        //  mAuth = FirebaseAuth.getInstance();


        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            bar.setVisibility(View.INVISIBLE);
                            t1.setText("");
                            t2.setText("");
                            Toast.makeText(getApplicationContext(),"Rigistered SUCCESSFULL",Toast.LENGTH_LONG).show();

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
