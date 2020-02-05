package com.example.go4food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    TextView userEmail,username;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
     userEmail   = findViewById(R.id.editText2);
         username = findViewById(R.id.editText);


        Button btnreset = findViewById(R.id.button4);
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,ForgetActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {
            username.setText(acct.getDisplayName());
            userEmail.setText(acct.getEmail());

        }
        else if(firebaseAuth.getCurrentUser()!=null)
        {
           String s [] = firebaseAuth.getCurrentUser().getEmail().split("@");
            username.setText(s[0]);
            userEmail.setText(firebaseAuth.getCurrentUser().getEmail());
        }
    }
}
