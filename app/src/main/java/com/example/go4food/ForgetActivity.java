package com.example.go4food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetActivity extends AppCompatActivity {

    EditText emailid;
    Button btnreset;
    FirebaseAuth  mfirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        btnreset=findViewById(R.id.Registeration);
        emailid=findViewById(R.id.Emailid);
        mfirebaseAuth=FirebaseAuth.getInstance();

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = emailid.getText().toString().trim();
                if(useremail.isEmpty())
                {
                    Toast.makeText(ForgetActivity.this,"Please Enter your registered email id",Toast.LENGTH_LONG).show();
                }
                else
                {
                    mfirebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful())
                           {
                               Toast.makeText(ForgetActivity.this,"Password reset email sent !",Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(ForgetActivity.this,SentActivity.class));
                               overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                           }
                           else
                           {
                               Toast.makeText(ForgetActivity.this,"Please check it again ,This email is not registered in the database  !",Toast.LENGTH_LONG).show();

                           }
                        }
                    });
                }
            }
        });
    }
}
