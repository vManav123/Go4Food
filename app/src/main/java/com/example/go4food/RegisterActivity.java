package com.example.go4food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

   ProgressBar progressBar_cyclic;
    private Button btnregister;
    private EditText Name, emailid, password, confirmpassword;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar_cyclic = findViewById(R.id.progressBar_cyclic);
        confirmpassword = findViewById(R.id.confirmpassword);
        btnregister = findViewById(R.id.Registeration);
        emailid = findViewById(R.id.Emailid);
        password = findViewById(R.id.password);


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnregister.setVisibility(View.GONE);
                progressBar_cyclic.setVisibility(View.VISIBLE);
                String email = emailid.getText().toString().trim();
                String passwprd = password.getText().toString().trim();
                if(email.isEmpty()) {
                    emailid.setError("Please enter email address");
                    emailid.requestFocus();
                    btnregister.setVisibility(View.VISIBLE);
                    progressBar_cyclic.setVisibility(View.GONE);
                }
                else if(passwprd.isEmpty())
                {
                    password.setError("Please enter email address");
                    password.requestFocus();
                    btnregister.setVisibility(View.VISIBLE);
                    progressBar_cyclic.setVisibility(View.GONE);
                }
                else if(email.isEmpty() && passwprd.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this,"Fields are empty",Toast.LENGTH_LONG).show();
                    btnregister.setVisibility(View.VISIBLE);
                    progressBar_cyclic.setVisibility(View.GONE);
                }
                else if(!passwprd.equals(confirmpassword.getText().toString()))
                {
                    Toast.makeText(RegisterActivity.this, "Password Not match", Toast.LENGTH_SHORT).show();
                    btnregister.setVisibility(View.VISIBLE);
                    progressBar_cyclic.setVisibility(View.GONE);

                }
                else if( !email.isEmpty() && !passwprd.isEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, passwprd)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "this email is already registered ", Toast.LENGTH_LONG).show();
                                        btnregister.setVisibility(View.VISIBLE);
                                        progressBar_cyclic.setVisibility(View.GONE);

                                    } else {
                                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                                    }
                                }
                            });

                }

                else
                {
                    Toast.makeText(RegisterActivity.this,"Error Occured!",Toast.LENGTH_LONG).show();
                    btnregister.setVisibility(View.VISIBLE);
                    progressBar_cyclic.setVisibility(View.GONE);
                }

            }
        });

    }
}