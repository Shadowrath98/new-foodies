package com.example.foodieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CProfile extends AppCompatActivity {

    Button lgout, upd, dlt;
    ProgressDialog progressDialog;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener AuthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_profile);

        fAuth = FirebaseAuth.getInstance();
        lgout = findViewById(R.id.LogOut);

        setuplistner();

        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
            }
        });


    }


    private void setuplistner() {
        AuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null)
                    Toast.makeText(CProfile.this,"Logged In",Toast.LENGTH_SHORT);
                else{
                    Toast.makeText(CProfile.this,"Signed Out!!!",Toast.LENGTH_SHORT);
                    Intent i = new Intent(CProfile.this,MainActivity.class);
                    startActivity(i);
                }
            }
        };


    }


    @Override
    protected  void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(AuthListner);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (AuthListner != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(AuthListner);
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();

        }


    }
}