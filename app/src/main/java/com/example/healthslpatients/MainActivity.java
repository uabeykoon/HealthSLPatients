package com.example.healthslpatients;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    final int RequestPermissioncod = 0;
    FirebaseAuth mAuth;
    EditText email,password;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    String userID;
    String patientName;

    String days[]={"Sunday","Monday"};

    String name,type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        //EnableRuntimePermission();
        mAuth = FirebaseAuth.getInstance();


        Button login = (Button)findViewById(R.id.login);
        TextView register = (TextView) findViewById(R.id.register);
        progressBar = (ProgressBar)findViewById(R.id.progressBar1);





        //click on login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userLogin();
            }
        });




        //click on register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });












    }

    //login
    public void userLogin(){


        email=(EditText)findViewById(R.id.loginemail);
        password=(EditText)findViewById(R.id.loginpassword);


        String email1 = email.getText().toString().trim();
        String password1 = password.getText().toString().trim();


        //check is the email field empty

        if (email1.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        //check is the password field empty

        if (password1.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        //check email availability

        if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            email.setError("Enter a valid Email address");
            email.requestFocus();
            return;
        }

        //check password length

        if (password1.length()<6){
            password.setError("Minimum length is 6");
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //if email matches password

                if (task.isSuccessful()) {
                    //finish();

                    //check if the user is available or not

                    //get user id to pass it
                    userID = mAuth.getCurrentUser().getUid();
                    Toast.makeText(MainActivity.this,userID, Toast.LENGTH_LONG).show();
                    //String ad = "nLeRlww7i0Y6vDDZM7GOho7NcLx1";


                //check database to find this user exit or not     //choose is the user an admind

                            final DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("Patients").child(userID);

                            databaseReference3.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){

                                        String fName = dataSnapshot.child("firstName").getValue().toString();
                                        String lName = dataSnapshot.child("lastName").getValue().toString();
                                        String type = dataSnapshot.child("type").getValue().toString();

                                        patientName = fName+" "+lName;
                                        Toast.makeText(MainActivity.this,patientName,Toast.LENGTH_LONG).show();


                        //check the type is what
                                        if (type.equals("1")) {

                                            Intent intent4 = new Intent(MainActivity.this, MainHome.class);
                                            intent4.putExtra("userId", userID);
                                            intent4.putExtra("patientName", patientName);
                                            intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent4);
                                            progressBar.setVisibility(View.GONE);

                                            finish();
                                        }

                            }
                            else {


                            //check the user is a patient or a doctor os an admin


                                DatabaseReference databaseReference4 =FirebaseDatabase.getInstance().getReference("Doctors").child(userID);
                                databaseReference4.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.exists() ||userID.equals("nLeRlww7i0Y6vDDZM7GOho7NcLx1")) {
                                            progressBar.setVisibility(View.GONE);

                                            Toast.makeText(MainActivity.this,"Please use the doctor version app of HealthSL",Toast.LENGTH_LONG).show();


                                        }

                                        else {

                                            Intent intent = new Intent(MainActivity.this, FirstFillData.class);
                                            intent.putExtra("userID", userID);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            progressBar.setVisibility(View.GONE);

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }
                // if there is any error
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                }


            }
        });



    }

    //register saving data
    public void register(){
        finish();
        Intent intent = new Intent(MainActivity.this,Registration.class);
        startActivity(intent);
    }
    //check wether user is logged in or not

//            @Override
//            protected void onStart() {
//                super.onStart();
//                if (mAuth.getCurrentUser()!=null){
//                    finish();
//                    Intent intent = new Intent(MainActivity.this,Home.class);
//                    startActivity(intent);
//                }
//            }

    //requesting permission
    public void EnableRuntimePermission() {
        String[] permission = {Manifest.permission.INTERNET};
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.INTERNET)) {
            Toast.makeText(MainActivity.this, "Internet permission allows granted", Toast.LENGTH_LONG).show();
        } else {

            ActivityCompat.requestPermissions(MainActivity.this, permission, RequestPermissioncod);
        }
    }


}
