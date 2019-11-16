package com.example.healthslpatients;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FirstFillData extends AppCompatActivity {

    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;


    EditText fName, lName, tpNumber;
    ProgressBar progressBar;
    Button addDetails;
    String userID;
    String patientName;

    String firstName,lastName,Specification,phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_fill_data);


        progressBar = (ProgressBar)findViewById(R.id.progressBar3);


        //get parsing userID

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");

        addDetails = (Button)findViewById(R.id.addDetails);

        addDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                addData();
                //retrieveData();






                //open another activity after filling


            }
        });


    }




    public void addData() {
        fName = (EditText) findViewById(R.id.firstName);
        lName = (EditText) findViewById(R.id.lastName);
        tpNumber = (EditText) findViewById(R.id.phoneNumber);







        firstName = fName.getText().toString();
        lastName = lName.getText().toString();
        phoneNumber = tpNumber.getText().toString();

        //Toast.makeText(FirstFillData.this, firstName, Toast.LENGTH_LONG).show();
        //get database reference



            databaseReference = FirebaseDatabase.getInstance().getReference("Patients");
            Patients patients = new Patients(firstName,lastName,phoneNumber);
            databaseReference.child(userID).setValue(patients);

            progressBar.setVisibility(View.GONE);




/*
        //mapping data before send
         Map sendData = new HashMap();
        sendData.put("First name", firstName);
        sendData.put("Last name", lastName);
        sendData.put("Specification", Specification);
        sendData.put("Phone", phoneNumber);
        sendData.put("type",1);

        //send values to database

        databaseReference.setValue(sendData);


*/

    }


    public void retrieveData(){


        databaseReference2=FirebaseDatabase.getInstance().getReference("Patients").child(userID);

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String patientFName = dataSnapshot.child("firstName").getValue().toString();
                String patientLName = dataSnapshot.child("lastName").getValue().toString();

                patientName = patientFName +" " +patientLName;


                Intent intent = new Intent(FirstFillData.this,MainHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("userId",userID);
                intent.putExtra("patientName",patientName);
                startActivity(intent);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

