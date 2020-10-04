package com.example.foodieapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class TopEats extends AppCompatActivity {


    ImageView imgBtnTopEats,imageTopEats,specialOffers,imageFavorites,imageOurMenu,imageDiatery,imageLatestDeals;
    Button lgout;
    ChipNavigationBar buttonNah;
    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference ref;

    FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener AuthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_eats);

        imgBtnTopEats = findViewById(R.id.imageTopEats);
        imageFavorites = findViewById(R.id.imageFavorites);
        imageOurMenu = findViewById(R.id.imageOurMenu);
        imageDiatery = findViewById(R.id.imageDiatery);
        specialOffers = findViewById(R.id.specialOffers);
        imageLatestDeals = findViewById(R.id.imageLatestDeals);
        lgout = findViewById(R.id.LogOut);
        buttonNah = findViewById(R.id.bottom_nav);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Users");

        buttonNah.setItemSelected(R.id.Menu, true);


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
                        startActivity(new Intent(getApplicationContext(), TopEats.class));
                        //overridePendingTransition(0, 0);
                        break;
                    case R.id.Shopping:
                        startActivity(new Intent(getApplicationContext(), Cart.class));
                        //overridePendingTransition(0, 0);
                        break;
                }
            }
        });

        imageFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TopEats.this, Favorites.class));
            }
        });
        imageOurMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TopEats.this, OurMenuCustomer.class));
            }
        });
        imageLatestDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TopEats.this, LatestDeals.class));
            }
        });
        imageDiatery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TopEats.this, Diatery.class));
            }
        });
        imgBtnTopEats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TopEats.this, TopCategories.class));
            }
        });
        specialOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TopEats.this, SpecialOffers.class));
            }
        });

        setuplistner();

        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(TopEats.this,MainActivity.class);
                startActivity(i);
            }
        });


    }





    private void setuplistner() {
        AuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null)
                    Toast.makeText(TopEats.this,"Logged In",Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(TopEats.this,"Signed Out!!!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(TopEats.this,MainActivity.class);
                    startActivity(i);
                }
            }
        };

    }
}