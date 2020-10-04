package com.example.foodieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText emailID;
    EditText password;
    Button btnSignup,btnLogin;
    FirebaseDatabase db;
    Vibrator vib;
    DatabaseReference ref;
    String name, contactNo;

    ProgressDialog progressDialog;

    private FirebaseAuth Mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emailID = findViewById(R.id.Eemail);
        password = findViewById(R.id.EPw);
        btnSignup = findViewById(R.id.SIGNUP);
        btnLogin = findViewById(R.id.LOGIN);
        Mauth = FirebaseAuth.getInstance();
        vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);

       

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String eemail = emailID.getText().toString().trim();
                String pswd = password.getText().toString().trim();


                //validation
                if (!Patterns.EMAIL_ADDRESS.matcher(eemail).matches()) {
                    emailID.setError("Invalid Email");
                    emailID.setFocusable(true);
                } else if (pswd.length() < 8) {
                    password.setError("Password length should be more than 8 characters");
                    password.setFocusable(true);
                } else if (TextUtils.isEmpty(emailID.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(password.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();

                else{
                    UserLogIn(eemail,pswd);
                    vib.vibrate(200);
                }
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging In..");

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,SignUp.class);
                startActivity(i);
            }
        });



        }


    private void UserLogIn(final String email, String password){
        progressDialog.show();
        Mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Error!!! Please try again", Toast.LENGTH_SHORT);
                }
                else{
                    progressDialog.dismiss();
                    FirebaseUser user = Mauth.getCurrentUser();

                    String mail = user.getEmail();
                    String UID = user.getUid();


                    /*HashMap<Object, String> hashMap = new HashMap<>();
                    hashMap.put("email",mail);
                    hashMap.put("UID",UID);
                    hashMap.put("Name", name);
                    hashMap.put("Contact No",contactNo);*/

                    FirebaseDatabase db = FirebaseDatabase.getInstance();

                    DatabaseReference ref = db.getReference("Users");

                   // ref.child(UID).setValue(hashMap);

                    if(email.equals("admin@gmail.com")){
                        startActivity(new Intent(MainActivity.this,MManage.class));
                        finish();
                    }else {

                        startActivity(new Intent(MainActivity.this,TopEats.class));
                        finish();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Dismiss progress dialog
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}