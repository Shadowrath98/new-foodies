package com.example.foodieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
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
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    //assigning variables
    EditText txtemail,txtpassword;
    Button confirm;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;


    //Declare firebase instance
    private FirebaseAuth mAuth;


    //clera the inputfields
    public void clearControls() {

        txtemail.setText("");
        txtpassword.setText("");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //assigning the variables to the items

        txtemail = findViewById(R.id.Iemail);
        txtpassword = findViewById(R.id.Ipw);


        //progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating Your Account...");

        confirm = findViewById(R.id.Scon);





        //Enabling on-click for button register
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eemail = txtemail.getText().toString().trim();
                String pswd = txtpassword.getText().toString().trim();

                mAuth = FirebaseAuth.getInstance();

                //validation
                if (!Patterns.EMAIL_ADDRESS.matcher(eemail).matches()) {
                    txtemail.setError("Invalid Email");
                    txtemail.setFocusable(true);
                } else if (pswd.length() < 8) {
                    txtpassword.setError("Password length should be more than 8 characters");
                    txtpassword.setFocusable(true);
                } else if (TextUtils.isEmpty(txtemail.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(txtpassword.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                else{
                    Regcust(eemail, pswd);
                    startActivity(new Intent(SignUp.this, CProfile.class));
                    finish();
                    }

                }


        });
    }

    //create new user
    public void Regcust(String email,String password){
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    FirebaseUser user = mAuth.getCurrentUser();

                    String mail = user.getEmail();
                    String UID = user.getUid();
                    //String name = user.getDisplayName();
                    //String phn  = user.getPhoneNumber();

                    HashMap<Object, String> hashMap = new HashMap<>();
                    hashMap.put("email",mail);
                    hashMap.put("UID",UID);
                    hashMap.put("Name","");
                    hashMap.put("Contact No","");

                    FirebaseDatabase db = FirebaseDatabase.getInstance();

                    DatabaseReference ref = db.getReference("Users");

                    ref.child(UID).setValue(hashMap);



                    Toast.makeText(SignUp.this,"Your Account is created"+user.getEmail(),Toast.LENGTH_SHORT);

                }else
                    {
                    progressDialog.dismiss();
                    Toast.makeText(SignUp.this,"Failed",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SignUp.this,""+e.getMessage(),Toast.LENGTH_SHORT);
            }
        });
    }

}

        
