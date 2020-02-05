package com.example.go4food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TrueSDK;
import com.truecaller.android.sdk.TrueSdkScope;

import java.util.List;
import java.util.Locale;

import static java.security.AccessController.getContext;


public class FrontActivity extends AppCompatActivity {

    Button btnlogin, btnregister, googleSignin,truecaller;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        TrueSdkScope trueScope = new TrueSdkScope.Builder(FrontActivity.this, sdkCallback)
                .consentMode(TrueSdkScope.CONSENT_MODE_POPUP )
                .consentTitleOption( TrueSdkScope.SDK_CONSENT_TITLE_VERIFY )
                .footerType( TrueSdkScope.FOOTER_TYPE_SKIP )
                .build();
        firebaseAuth = FirebaseAuth.getInstance();
        TrueSDK.init(trueScope);
        truecaller = findViewById(R.id.truecaller);
        truecaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TrueSDK.getInstance().isUsable())
                {
                    Toast.makeText(FrontActivity.this, "True caller is not installed in your device", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Locale locale = new Locale("en");
                    TrueSDK.getInstance().setLocale(locale);
                    TrueSDK.getInstance().getUserProfile(FrontActivity.this);

                }
            }
        });
        btnregister = findViewById(R.id.register);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FrontActivity.this, RegisterActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        btnlogin = findViewById(R.id.login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(FrontActivity.this, LoginActivity.class);
                startActivity(j);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        googleSignin = findViewById(R.id.google);
        googleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.google:
                        signIn();
                        break;
                    // ...
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TrueSDK.getInstance().onActivityResultObtained( this,resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            startActivity(new Intent(FrontActivity.this,HomeActivity.class));
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            finish();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(FrontActivity.this,HomeActivity.class));
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            finish();
        }
        mGoogleSignInClient.silentSignIn()
                .addOnCompleteListener(
                        this,
                        new OnCompleteListener<GoogleSignInAccount>() {
                            @Override
                            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                                handleSignInResult(task);
                            }
                        });
    }

    //Truecaller

    private final ITrueCallback sdkCallback = new ITrueCallback() {

        @Override
        public void onSuccessProfileShared(@NonNull final TrueProfile trueProfile) {

            // This method is invoked when the truecaller app is installed on the device and the user gives his
            // consent to share his truecaller profile

            Log.d( "", "Verified Successfully : " + trueProfile.firstName );
            firebaseAuth.createUserWithEmailAndPassword(trueProfile.email,trueProfile.phoneNumber).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(FrontActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(FrontActivity.this,HomeActivity.class));
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();
                    }
                    else
                    {
                        firebaseAuth.signInWithEmailAndPassword(trueProfile.email,trueProfile.phoneNumber).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    startActivity(new Intent(FrontActivity.this,HomeActivity.class));
                                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(FrontActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

        @Override
        public void onFailureProfileShared(@NonNull final TrueError trueError) {
            // This method is invoked when some error occurs or if an invalid request for verification is made

            Log.d( "", "onFailureProfileShared: " + trueError.getErrorType() );
        }

        @Override
        public void onVerificationRequired() {

        }

    };



}
