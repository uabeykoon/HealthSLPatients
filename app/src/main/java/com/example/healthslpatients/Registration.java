package com.example.healthslpatients;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;

public class Registration extends AppCompatActivity {

    final int RequestPermissioncod = 0;
    EditText email,password,passwordConfirm;

    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        Button registerbtn = (Button)findViewById(R.id.registerbtn);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                userRegistration();
            }
        });


    }




    private void userRegistration(){
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        passwordConfirm=(EditText)findViewById(R.id.passwordConfirm);
        String email1 = email.getText().toString();
        String password1 = password.getText().toString();
        String password2 = passwordConfirm.getText().toString();
        progressBar=(ProgressBar)findViewById(R.id.progressBar4);

        //Toast.makeText(Registration.this,email1,Toast.LENGTH_LONG).show();
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

        if (password2.isEmpty()){

            passwordConfirm.setError("Enter Your Password here again");
            passwordConfirm.requestFocus();
            return;

        }

        if (!password2.equals(password1)){

            passwordConfirm.setError("Confirm Password does not Match");
            passwordConfirm.requestFocus();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);


        //create user to data base

        mAuth.createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);

                //check if the registration was successfull or not

                if(task.isSuccessful()) {
                    finish();
                    email.getText().clear();
                    password.getText().clear();
                    Toast.makeText(Registration.this, "User Registered Successfull", Toast.LENGTH_LONG).show();




                    //after completing the registration open login page

                    Intent intent = new Intent(Registration.this,MainActivity.class);
                    startActivity(intent);
                }
                else {

                    //check the error

                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(Registration.this,"You are already registered",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }




}
