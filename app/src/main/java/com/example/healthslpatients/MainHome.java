package com.example.healthslpatients;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class MainHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {





    DatabaseReference databaseReference;
    String name;
    String doctorID;
    String doctorName;
    String patientName;

    private DrawerLayout hDrawerlayout;
    private ActionBarDrawerToggle hToggle;
    TimePickerDialog timePickerDialog;
    List<Doctors> doctorNames;
    Button searchBtn,searchBtnSpec;
    String userID;


    Spinner doctorsNameSpinner,sepecSpinner;
    ProgressBar progressBar;
    TextView nameNav;

    DatabaseReference databaseReferenceDoctors;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
//        nameNav= (TextView)findViewById(R.id.name_nav);
//        nameNav.setText(patientName);


    //casting  buttons

        searchBtn = (Button)findViewById(R.id.searchBtn);
        sepecSpinner = (Spinner)findViewById(R.id.specifiction);
        searchBtnSpec = (Button)findViewById(R.id.searchBtnSpec);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        doctorNames = new ArrayList<Doctors>();


        Intent intent = getIntent();
        userID = intent.getStringExtra("userId");
        patientName = intent.getStringExtra("patientName");

        Toast.makeText(MainHome.this,patientName,Toast.LENGTH_LONG).show();




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

                DatePickerDialog datepickerdialog = new DatePickerDialog(MainHome.this, new DatePickerDialog.OnDateSetListener() {
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
                cal2.add(Calendar.DAY_OF_MONTH, 6);
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
                if (dataSnapshot.exists()) {

                    for (DataSnapshot doctorsSnapshot : dataSnapshot.getChildren()) {

                        //get doctors reference

                        Doctors doctors = doctorsSnapshot.getValue(Doctors.class);

                        //add that data to list
                        doctorNames.add(doctors);

                    }
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

                Toast.makeText(MainHome.this,doctorN,Toast.LENGTH_LONG).show();


                //querying the items

                Query quer1 = FirebaseDatabase.getInstance().getReference("Channaling").orderByChild("doctorID").equalTo(doctorID);

                Intent intent1 = new Intent(MainHome.this,SearchResultDoctors.class);
                intent1.putExtra("doctorID",doctorID);
                intent1.putExtra("userId",userID);
                intent1.putExtra("patientName",patientName);
                startActivity(intent1);


            }
        });






        /*searchBtnSpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String doctorS = sepecSpinner.getSelectedItem().toString();

                Toast.makeText(MainHome.this,doctorS,Toast.LENGTH_LONG).show();


                //querying the items

                Query quer1 = FirebaseDatabase.getInstance().getReference("Channaling").orderByChild("doctorID").equalTo(doctorID);

                Intent intent1 = new Intent(MainHome.this,SearchResultDoctors.class);
                intent1.putExtra("doctorID",doctorID);
                intent1.putExtra("userId",userID);
                intent1.putExtra("patientName",patientName);
                startActivity(intent1);


            }
        });*/





















    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.finddoc) {
            // Handle the camera action
        } else if (id == R.id.nav_myChannal) {
            Intent intent2 = new Intent(MainHome.this,MyChanneling.class);
            intent2.putExtra("userId",userID);
            intent2.putExtra("patientName",patientName);
            startActivity(intent2);


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public void spinner(){
        // spinner


        doctorsNameSpinner = (Spinner)findViewById(R.id.doctorNameSpinner);
        ArrayAdapter<Doctors> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,doctorNames);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        doctorsNameSpinner.setAdapter(adapter);

        progressBar.setVisibility(View.GONE);

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
