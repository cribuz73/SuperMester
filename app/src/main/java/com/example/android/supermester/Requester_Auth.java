package com.example.android.supermester;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Requester_Auth extends AppCompatActivity {

        private static final int RC_SIGN_IN = 1;
        private FirebaseAuth mFirebaseAuth;
        private FirebaseAuth.AuthStateListener mAuthStateListener;

    @BindView(R.id.textName) TextView name_tv;
    @BindView(R.id.textEmail) TextView email_tv;
    @BindView(R.id.textPhone) TextView phone_tv;
    @BindView(R.id.button2) Button add_new_job;


    List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("I need a tradesman");
        setContentView(R.layout.requester_account);
        ButterKnife.bind(this);


            //Initialize Firebase components
            mFirebaseAuth = FirebaseAuth.getInstance();

            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    if (user != null){
                        Toast.makeText(Requester_Auth.this, "User logat !!!", Toast.LENGTH_LONG).show();

                        name_tv.setText(user.getDisplayName());
                        email_tv.setText(user.getEmail());
                        phone_tv.setText(user.getPhoneNumber());


                    }else{
                        startActivityForResult(
                                AuthUI.getInstance()
                                        .createSignInIntentBuilder()
                                        .setIsSmartLockEnabled(false)
                                        .setAvailableProviders(providers)
                                        .setTheme(R.style.YellowTheme)
                                        .setTosAndPrivacyPolicyUrls("https://superapp.example.com/terms-of-service.html","https://superapp.example.com/privacy-policy.html")
                                        .build(),
                                RC_SIGN_IN);


                    }
                }
            };

            add_new_job.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Requester_Auth.this, JobRegistration.class);
                    startActivity(intent);
                }
            });

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "User signed in !", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Sign in cancelled !", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AuthUI.getInstance().signOut(this);
    }
}
