package com.example.foodieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CProfile extends AppCompatActivity {


    //Declaring variables
    Button lgout, upd, dlt;
    TextView nameIn,emailIn,contactIn;
    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference ref;
    ProgressDialog pd;

    FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener AuthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_profile);

        //Firebase Initialization
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Users");

        //Initialising variables
        lgout = findViewById(R.id.LogOut);
        nameIn = findViewById(R.id.INName);
        emailIn = findViewById(R.id.INEmail);
        contactIn = findViewById(R.id.INPhone);
        upd = findViewById(R.id.Update);
        dlt = findViewById(R.id.Delete);

        //Initialising the progress dialog
        pd = new ProgressDialog(this);

        Query query = ref.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds :  dataSnapshot.getChildren()){

                    String name = ""+ds.child("Name").getValue();
                    String email = ""+ds.child("email").getValue();
                    String contactNo = ""+ds.child("Contact No").getValue();


                    nameIn.setText(name);
                    emailIn.setText(email);
                    contactIn.setText(contactNo);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //Onclick listner for the update button
        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    EditProfileDialog();
            }
        });


        //delete on click listner
        dlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CProfile.this);
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleting your account will result in losing access to the foodies app");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    pd.setMessage("Deleting your account!");
                                    //Toast.makeText(CProfile.this,"Account Deleted",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(CProfile.this,MainActivity.class);
                                    startActivity(i);
                                }else{

                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });



        setuplistner();

        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(CProfile.this,MainActivity.class);
                startActivity(i);
            }
        });


    }

    private void EditProfileDialog() {

        String[] Options = {"Edit your Name","Edit your Phone number"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Item");
        builder.setItems(Options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    pd.setMessage("Updating your Name!");
                    showNamePhoneUpdateDialog("Name");
                }else if(i==1){
                    pd.setMessage("Updating your Phone number!");
                    showNamePhoneUpdateDialog("Contact No");
                }

            }
        });

        builder.create().show();
    }

    private void showNamePhoneUpdateDialog(final String key) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update "+key);

        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(10,10,10,10);


        final EditText e = new EditText(this);
        e.setHint("Enter "+key);
        l.addView(e);

        builder.setView(l);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String value = e.getText().toString().trim();
                if(!TextUtils.isEmpty(value)){
                    pd.show();
                    HashMap<String,Object> result = new HashMap<>();
                    result.put(key,value);

                    ref.child(user.getUid()).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            Toast.makeText(CProfile.this,"Update",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(CProfile.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(CProfile.this,"Please enter"+key,Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create().show();

    }

    //Method for checking whether the user is logged in
    private void setuplistner() {
        AuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null)
                    Toast.makeText(CProfile.this,"Logged In",Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(CProfile.this,"Signed Out!!!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CProfile.this,MainActivity.class);
                    startActivity(i);
                }
            }
        };

    }



    //On start and On stop methods
    /*@Override
    protected  void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(AuthListner);
    }*/


    /*@Override
    protected void onStop() {
        super.onStop();
        if (AuthListner != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(AuthListner);
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();

        }


    }*/
}