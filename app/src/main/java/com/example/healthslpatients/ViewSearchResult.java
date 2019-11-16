package com.example.healthslpatients;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViewSearchResult extends AppCompatActivity {
    TextView startTime,endTime,chanalNumber,doctorNameViewChannal,startTimec,endTimec;
    EditText addChannalDate;
    //Spinner chanalNumber;
    Button addChanal;
    EditText date;
    String dateSelected="jj";
    String datein2="ll";
    String finalDay;

    int chanalNumber1;
    String maxPatients;

    String chanallingId;
    String userID;
    String doctorID;
    String doctorName;
    String selectedDayOfWeek;
    String patientName;
    String startTime1;
    String endTime1;



    DatabaseReference databaseReference,databaseReferenceGetCount;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;
    DatabaseReference databaseReference4;
    DatabaseReference databaseReference5;
    DatabaseReference databaseReferencePatientChannel;
    DatabaseReference databaseReferenceDoctorChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_result);

        final Button addChanal = (Button)findViewById(R.id.addChanal);
        chanalNumber = (TextView)findViewById(R.id.channalNumber) ;

        Intent intent = getIntent();

        chanallingId = intent.getStringExtra("chanallingId");
        startTime1 = intent.getStringExtra("startTime");
        endTime1 = intent.getStringExtra("endTime");
        maxPatients = intent.getStringExtra("maxPatients");
        doctorName = intent.getStringExtra("doctorName");
        selectedDayOfWeek = intent.getStringExtra("selectedDayOfWeek");
        userID = intent.getStringExtra("userId");
        doctorID = intent.getStringExtra("doctorId");
        patientName = intent.getStringExtra("patientName");

        String a = chanallingId + startTime + endTime +maxPatients + doctorName + selectedDayOfWeek;
        Toast.makeText(ViewSearchResult.this,patientName,Toast.LENGTH_LONG).show();



        doctorNameViewChannal = (TextView)findViewById(R.id.doctorNameViewChannal);
        startTimec = (TextView)findViewById(R.id.startTime);
        endTimec = (TextView)findViewById(R.id.endTime);
        doctorNameViewChannal.setText("Dr."+doctorName);
        startTimec.setText("Start Time\n"+startTime1);
        endTimec.setText("Start Time\n"+endTime1);






        // picking a date

        date = (EditText) findViewById(R.id.addChanelDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();


                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                final int weekdate = cal.get(Calendar.DAY_OF_WEEK);

                DatePickerDialog datepickerdialog = new DatePickerDialog(ViewSearchResult.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        //find day of week for specific date

                        final String datein = Integer.toString(dayOfMonth) + "/" + Integer.toString(month + 1) + "/" + Integer.toString(year);
                        //find day of week for specific date
                        //find day of week for specific date
                        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                        Date dt1 = null;
                        try {
                            dt1 = format1.parse(datein);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        DateFormat format2 = new SimpleDateFormat("EEEE");
                        finalDay = format2.format(dt1);


                        dateSelected = year + "/" + (month + 1) + "/" + dayOfMonth + "," + finalDay;
                        datein2 = Integer.toString(dayOfMonth) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(year) + "," + finalDay;


                        //check wether selected day is an specific day that channel available

                        if (finalDay.equals(selectedDayOfWeek)){


                            date.setText(dateSelected);
                        //Toast.makeText(ViewSearchResult.this,datein2,Toast.LENGTH_LONG).show();


                        databaseReferenceGetCount = FirebaseDatabase.getInstance().getReference().child("ChannalAdded");
                        databaseReferenceGetCount.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                if (dataSnapshot.hasChild(datein2)) {
                                                /*chanalNumber1 =(int)dataSnapshot.getChildrenCount()+1;
                                                chanalNumber.setText("Your Channeling Number is #" + Integer.toString(chanalNumber1));
                                                Toast.makeText(ViewSearchResult.this,""+(chanalNumber1),Toast.LENGTH_LONG).show();*/

                                    //Toast.makeText(ViewSearchResult.this, "innawa", Toast.LENGTH_LONG).show();

                                    databaseReference2 = FirebaseDatabase.getInstance().getReference().child("ChannalAdded").child(datein2);
                                    databaseReference2.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                            if (dataSnapshot.hasChild(chanallingId)) {

                                                databaseReference3 = FirebaseDatabase.getInstance().getReference().child("ChannalAdded").child(datein2).child(chanallingId);
                                                databaseReference3.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                        chanalNumber1 = (int) dataSnapshot.getChildrenCount();
                                                        chanalNumber.setText("Your Channeling Number is #" + Integer.toString(chanalNumber1));
                                                        //Toast.makeText(ViewSearchResult.this, "" + (chanalNumber1), Toast.LENGTH_LONG).show();


                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });


                                            } else {

                                                databaseReference4 = FirebaseDatabase.getInstance().getReference("ChannalAdded").child(datein2).child(chanallingId);
                                                MakeChannel makeChannel = new MakeChannel("0", "0","0", "0","0","0");
                                                databaseReference4.child("0").setValue(makeChannel);

                                                databaseReference4.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        chanalNumber1 = (int) dataSnapshot.getChildrenCount();
                                                        chanalNumber.setText("Your Channeling Number is #" + Integer.toString(chanalNumber1));
                                                        //Toast.makeText(ViewSearchResult.this, "" + (chanalNumber1), Toast.LENGTH_LONG).show();

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


                                } else {

                                    //Toast.makeText(ViewSearchResult.this, "naaa", Toast.LENGTH_LONG).show();

                                    databaseReference5 = FirebaseDatabase.getInstance().getReference("ChannalAdded").child(datein2).child(chanallingId);
                                    MakeChannel makeChannel = new MakeChannel("0","0","0", "0", "0","0");
                                    databaseReference5.child("0").setValue(makeChannel);

                                    databaseReference5.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            chanalNumber1 = (int) dataSnapshot.getChildrenCount();
                                            chanalNumber.setText("Your Channeling Number is #" + Integer.toString(chanalNumber1));
                                            //Toast.makeText(ViewSearchResult.this, "" + (chanalNumber1), Toast.LENGTH_LONG).show();

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

                        else {

                            Toast.makeText(ViewSearchResult.this,"Please Select "+selectedDayOfWeek+" Date",Toast.LENGTH_LONG).show();
                        }
                }
                },year,month,day);
                datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis());
                cal2.add(Calendar.DAY_OF_MONTH, 6);
                datepickerdialog.getDatePicker().setMaxDate(cal2.getTimeInMillis());
                datepickerdialog.show();



            }
        });



        //get count of child to get number








        //on add buttton clicked
            addChanal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String date1 =date.getText().toString().trim();

                    if (date1.isEmpty()){
                        date.setError("Please Select Channel Date");
                        date.requestFocus();
                        return;
                    }

            //check exsistent query


                    Query checkExistent = FirebaseDatabase.getInstance().getReference("ChannalAdded").child(datein2).child(chanallingId).orderByChild("userId").equalTo(userID);

            //retrieve and check is user already make same channel

                    checkExistent.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                Toast.makeText(ViewSearchResult.this,"You have Already made this Channel ",Toast.LENGTH_LONG).show();
                            }

            // if user doesnt make same channel before

                            else { int aa =(int)dataSnapshot.getChildrenCount();

                                    if (Integer.parseInt(maxPatients)>=aa) {

                                        //add dialog box to confirm

                                        AlertDialog.Builder confirmation = new AlertDialog.Builder(ViewSearchResult.this);
                                        confirmation.setMessage("Confirm Your Channel").setCancelable(false)
                                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        Toast.makeText(ViewSearchResult.this, "You have Already made this Channel ", Toast.LENGTH_LONG).show();


                                                        //make users channal

                                                        databaseReference = FirebaseDatabase.getInstance().getReference("ChannalAdded").child(datein2).child(chanallingId);
                                                        MakeChannel makeChannel = new MakeChannel(chanallingId, userID, doctorID, doctorName, Integer.toString(chanalNumber1), patientName);
                                                        databaseReference.child(Integer.toString(chanalNumber1)).setValue(makeChannel);


                                                        //send patient channeling data to database another patient information table

                                                        databaseReferencePatientChannel = FirebaseDatabase.getInstance().getReference("PatientChannelDetails").child(userID);

                                                        String id1 = databaseReferencePatientChannel.push().getKey();

                                                        PatientChannelDetails patientChannelDetails = new PatientChannelDetails(userID, doctorID, datein2, Integer.toString(chanalNumber1), doctorName, chanallingId, startTime1, endTime1, maxPatients);
                                                        databaseReferencePatientChannel.child(id1).setValue(patientChannelDetails);


                                                        //send patient channeling dat ato database another doctor information table


                                                        databaseReferenceDoctorChannel = FirebaseDatabase.getInstance().getReference("DoctorChannelDetails").child(doctorID);

                                                        String id2 = databaseReferenceDoctorChannel.push().getKey();

                                                        DoctorChannelDetails doctorChannelDetails = new DoctorChannelDetails(userID, doctorID, chanallingId, datein2, Integer.toString(chanalNumber1), doctorName, patientName);
                                                        databaseReferenceDoctorChannel.child(id2).setValue(doctorChannelDetails);


                                                        AlertDialog.Builder confirmation = new AlertDialog.Builder(ViewSearchResult.this);
                                                        confirmation.setMessage("Channeling Success")
                                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {

                                                                        finish();


                                                                    }
                                                                });


                                                        AlertDialog alertDialog = confirmation.create();
                                                        alertDialog.setTitle("Confirmation");
                                                        alertDialog.show();


                                                    }
                                                })


                                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();

                                                    }
                                                });

                                        AlertDialog alertDialog = confirmation.create();
                                        alertDialog.setTitle("Confirmation");
                                        alertDialog.show();

                                    }

                                    else {
                                        Toast.makeText(ViewSearchResult.this,"Maximum number of Patients are filled",Toast.LENGTH_LONG).show();
                                    }


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //String aa = date.getText().toString();

                    //Toast.makeText(ViewSearchResult.this,chanalNumber1,Toast.LENGTH_LONG).show();






                }
            });


    }

    public void onClickMakeChannel(){



    }
}
