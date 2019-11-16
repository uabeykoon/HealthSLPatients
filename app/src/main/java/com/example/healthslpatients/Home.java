package com.example.healthslpatients;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

public class Home extends AppCompatActivity {

    DatabaseReference databaseReference;
    String name;
    String doctorID;
    String doctorName;
    private DrawerLayout hDrawerlayout;
    private ActionBarDrawerToggle hToggle;
    TimePickerDialog timePickerDialog;
    List<Doctors> doctorNames;
    Button searchBtn;
    String userID;


    Spinner doctorsNameSpinner;

    DatabaseReference databaseReferenceDoctors;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);







        searchBtn = (Button)findViewById(R.id.searchBtn);
        doctorNames = new ArrayList<Doctors>();


        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userId");
        String abc = intent.getStringExtra("name");




        //Navigation drawer button


        hDrawerlayout = (DrawerLayout)findViewById(R.id.drawer);
        hToggle= new ActionBarDrawerToggle(this,hDrawerlayout,R.string.open,R.string.close);
        hDrawerlayout.addDrawerListener(hToggle);
        hToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        // picking a date

        final EditText date = (EditText) findViewById(R.id.chanelDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();


                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                final int weekdate = cal.get(Calendar.DAY_OF_WEEK);

                DatePickerDialog datepickerdialog = new DatePickerDialog(Home.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        //find day of week for specific date

                        String datein = Integer.toString(dayOfMonth) + "/"+Integer.toString(month+1)+"/"+Integer.toString(year);
                        //find day of week for specific date
                        //find day of week for specific date
                        SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
                        Date dt1= null;
                        try {
                            dt1 = format1.parse(datein);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        DateFormat format2=new SimpleDateFormat("EEEE");
                        String finalDay=format2.format(dt1);



                        date.setText(year+"/"+(month+1)+"/"+dayOfMonth+","+finalDay);

                    }
                },year,month,day);
                datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis());
                cal2.add(Calendar.DAY_OF_MONTH, 8);
                datepickerdialog.getDatePicker().setMaxDate(cal2.getTimeInMillis());
                datepickerdialog.show();



            }
        });







        databaseReferenceDoctors = FirebaseDatabase.getInstance().getReference("Doctors");

        databaseReferenceDoctors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //get doctors count

               /* doctorCount = (int)dataSnapshot.getChildrenCount();
                doctorsNameArray = new String[doctorCount];*/


                //getting all the element in doctors node

                for (DataSnapshot doctorsSnapshot : dataSnapshot.getChildren()){

                    //get doctors reference

                    Doctors doctors = doctorsSnapshot.getValue(Doctors.class);

                    //add that data to list
                    doctorNames.add(doctors);

                }


                    /*Doctors doctors = doctorNames.get(0);
                    String a = doctors.getFirstName();
                    doctorsNameArray[0] = doctors.getFirstName();*/

                //add details to arrays

                /* for (int i=0;i<doctorCount;i++) {
                    Doctors doctors = doctorNames.get(i);
                    doctorsNameArray[i] = doctors.getFirstName();
                }*/

                //Toast.makeText(AddChanal.this,doctorsNameArray[1],Toast.LENGTH_LONG).show();




                //Toast.makeText(AddChanal.this,doctorCount,Toast.LENGTH_LONG).show();

                //String[] array = doctorNames.toArray(new String[0]);

                spinner();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    //on button click search

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String doctorN = doctorsNameSpinner.getSelectedItem().toString();

                Toast.makeText(Home.this,doctorN,Toast.LENGTH_LONG).show();


                //querying the items

                    Query quer1 = FirebaseDatabase.getInstance().getReference("Channaling").orderByChild("doctorID").equalTo(doctorID);

                    Intent intent1 = new Intent(Home.this,SearchResultDoctors.class);
                    intent1.putExtra("doctorID",doctorID);
                    intent1.putExtra("userId",userID);
                    startActivity(intent1);


            }
        });





    }





    //Navigation on and off method



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (hToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }









    public void spinner(){
        // spinner


        doctorsNameSpinner = (Spinner)findViewById(R.id.doctorNameSpinner);
        ArrayAdapter<Doctors> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,doctorNames);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        doctorsNameSpinner.setAdapter(adapter);

        //on spinner selected

        doctorsNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(AddChanal.this,"hhhhh",Toast.LENGTH_LONG).show();


                Doctors doctors = (Doctors)parent.getSelectedItem();
                displayDoctorsdata(doctors);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getSelectedDoctors(View v){
        Doctors doctors = (Doctors)doctorsNameSpinner.getSelectedItem();
        displayDoctorsdata(doctors);

    }

    private void displayDoctorsdata(Doctors doctors){
        name = doctors.getFirstName().toString();
        doctorID = doctors.getDoctorID().toString();
        doctorName = doctors.getFirstName().toString() +" "+ doctors.getLastName().toString();

        //Toast.makeText(Home.this,name,Toast.LENGTH_LONG).show();
    }



    }

