package com.example.foodieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Manage extends AppCompatActivity {


    Button lgout;
    ImageButton mm;
    ChipNavigationBar buttonNah;
    FirebaseAuth fAuth;
    FirebaseUser user;
    private FirebaseAuth.AuthStateListener AuthListner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        lgout= findViewById(R.id.LogOut);
        mm = findViewById(R.id.MenuBtn);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        buttonNah = findViewById(R.id.bottom_nav);

        buttonNah.setItemSelected(R.id.MyP, true);


        //on-selected item listener for the bottonNav
        buttonNah.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                switch (id) {
                    case R.id.MyP:
                        startActivity(new Intent(getApplicationContext(), CProfile.class));
                        //overridePendingTransition(0, 0);
                        break;
                    case R.id.Menu:
                        startActivity(new Intent(getApplicationContext(), Menu.class));
                        //overridePendingTransition(0, 0);
                        break;
                    case R.id.Shopping:
                        startActivity(new Intent(getApplicationContext(), Cart.class));
                        //overridePendingTransition(0, 0);
                        break;
                }
            }
        });


        mm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Manage.this,MManage.class);
                startActivity(i);
            }
        });

        setuplistener();

        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(Manage.this,MainActivity.class);
                startActivity(i);
            }
        });





        }

    //Method for checking whether the user is logged in
    private void setuplistener() {
        AuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null)
                    Toast.makeText(Manage.this,"Logged In",Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(Manage.this,"Signed Out!!!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Manage.this,MainActivity.class);
                    startActivity(i);
                }
            }
        };


    }
}