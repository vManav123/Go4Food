package com.example.go4food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar loginProgress;
    Button btnforget, btnlogin;
    EditText txtemail,txtpassword;
    ProgressBar progressBar_cyclic;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar_cyclic = findViewById(R.id.progressBar_cyclic);

        btnforget = findViewById(R.id.forget_button);
        btnforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
            }
        });

        btnlogin = findViewById(R.id.Login);
        txtemail = findViewById(R.id.Emailid);
        txtpassword = findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
        FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if(mFirebaseUser!=null)
            {
                Toast.makeText(LoginActivity.this,"you are logged in",Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
            else
            {
                //sadsaf
            }
        }
    };
    btnlogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            btnlogin.setVisibility(View.GONE);
            progressBar_cyclic.setVisibility(View.VISIBLE);
            String email =txtemail.getText().toString().trim();
            String passwprd = txtpassword.getText().toString().trim();
            if(email.isEmpty()) {
                txtemail.setError("Please enter email address");
                txtemail.requestFocus();
                btnlogin.setVisibility(View.VISIBLE);
                progressBar_cyclic.setVisibility(View.GONE);
            }
            else if(passwprd.isEmpty())
            {
                txtpassword.setError("Please enter email address");
                txtpassword.requestFocus();
                btnlogin.setVisibility(View.VISIBLE);
                progressBar_cyclic.setVisibility(View.GONE);

            }
            else if(email.isEmpty() && passwprd.isEmpty())
            {
                Toast.makeText(LoginActivity.this,"Fields are empty",Toast.LENGTH_LONG).show();
                btnlogin.setVisibility(View.VISIBLE);
                progressBar_cyclic.setVisibility(View.GONE);

            }
            else if( !email.isEmpty() && !passwprd.isEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email,passwprd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Failed to Login the account", Toast.LENGTH_LONG).show();
                            btnlogin.setVisibility(View.VISIBLE);
                            progressBar_cyclic.setVisibility(View.GONE);
                        } else {
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                            finish();
                        }
                    }
                });

            }
            else
            {
                Toast.makeText(LoginActivity.this,"Error Occured!",Toast.LENGTH_LONG).show();
                btnlogin.setVisibility(View.VISIBLE);
                progressBar_cyclic.setVisibility(View.GONE);
            }

        }
    });
    }
    protected void onStart()
    {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }
}