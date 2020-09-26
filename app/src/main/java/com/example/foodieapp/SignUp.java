package com.example.foodieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    EditText name,email,contact,password,cpw;
    DatabaseReference db;
    Admin fooder;
    User foodie;
    Button confirm;
    DatabaseReference databaseReference;
    int counter;

    AwesomeValidation awd;

    public void clearControls() {
        name.setText("");
        email.setText("");
        contact.setText("");
        password.setText("");
        cpw.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.Iname);
        email = findViewById(R.id.Iemail);
        contact = findViewById(R.id.Icont);
        password = findViewById(R.id.Ipw);
        cpw = findViewById(R.id.Icpw);

        confirm = findViewById(R.id.Scon);

        foodie = new User();

        //initialising validation style
        awd = new AwesomeValidation(ValidationStyle.BASIC);

        awd.addValidation(this,R.id.Icpw,R.id.Ipw,R.string.invalid_confirm_password);

        //real-time database connection
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    counter = (int) dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Enabling on-click for button register
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (TextUtils.isEmpty(name.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please Enter your name", Toast.LENGTH_SHORT).show();
                    }
                    else if (TextUtils.isEmpty(email.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please Enter your email", Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(contact.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please enter your Contact", Toast.LENGTH_SHORT).show();
                    }

                    else if(TextUtils.isEmpty(password.getText().toString())) {
                        Toast.makeText(getApplicationContext(),"Please Enter Password", Toast.LENGTH_SHORT).show();
                    }
                    else if (password.length()<8) {
                        password.setError("Password minimum 8 characters required");
                        password.setFocusable(true);
                    }
                    else {
                        awd.validate();
                        foodie.setName(name.getText().toString().trim());
                        foodie.setEmail(email.getText().toString().trim());
                        foodie.setContact(Integer.parseInt(contact.getText().toString().trim()));
                        foodie.setPw(password.getText().toString().trim());
                        databaseReference.child("U"+(counter + 1)).setValue(foodie);


                        Toast.makeText(getApplicationContext(),"Successfully Registered", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                }
                catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(),"Invalid Contact Number", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}