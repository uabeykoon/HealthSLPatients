package com.example.healthslpatients;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
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

public class MyChanneling extends AppCompatActivity {

    String[] dateSequence=new String[8];
    String finalDay;
    String userID;

    Query query1;

    List<PatientChannelDetails> list1;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;

    ListView patientchannellistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_channeling);

        //get details that parsing from previous activity

        Intent intent = getIntent();
        userID = intent.getStringExtra("userId");


        list1 = new ArrayList<>();
        patientchannellistView = (ListView) findViewById(R.id.myChannelList);

        //get todays days

        get7Days();
        //Toast.makeText(MyChanneling.this,dateSequence[3],Toast.LENGTH_LONG).show();


        //get database


        //query1 = FirebaseDatabase.getInstance().getReference("Channaling").child(dateSequence[1]);


        //make query to retrieve


        //make query to retrieve
        for (int i=0;i<8;i++) {


        query1 = FirebaseDatabase.getInstance().getReference("PatientChannelDetails").child(userID).orderByChild("date").equalTo(dateSequence[i]);
        Toast.makeText(MyChanneling.this, dateSequence[6], Toast.LENGTH_LONG).show();

        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    //list1.clear();

                    for (DataSnapshot patientChanallingSnapshot : dataSnapshot.getChildren()) {
                        PatientChannelDetails patientChanallingd = patientChanallingSnapshot.getValue(PatientChannelDetails.class);
                        list1.add(patientChanallingd);
                    }

                    //Toast.makeText(MyChanneling.this, list1.get(1).getDate(), Toast.LENGTH_LONG).show();

                    PatientChannelList adapter = new PatientChannelList(MyChanneling.this, list1);
                    patientchannellistView.setAdapter(adapter);

                    //Toast.makeText(MyChanneling.this,list1.get(1).getDate(),Toast.LENGTH_LONG).show();


                    //chanallingList.clear();


                    //set value to the list view

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

//make adapter


        PatientChannelList adapter = new PatientChannelList(MyChanneling.this, list1);
        patientchannellistView.setAdapter(adapter);

























    }

    public void get7Days(){


        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat msimpleDateFormat=new SimpleDateFormat("d-M-yyyy");
        dateSequence[0]=msimpleDateFormat.format(calendar.getTime());

                Date dt2 = null;
                try {
                    dt2 = msimpleDateFormat.parse(msimpleDateFormat.format(calendar.getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat format3 = new SimpleDateFormat("EEEE");
                finalDay = format3.format(dt2);
                dateSequence[0] = msimpleDateFormat.format(calendar.getTime())+","+finalDay;

     //get 7 date to future


        for (int i=1;i<7;i++) {
            calendar.add(Calendar.DATE, 1);
            //SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            Date dt1 = null;
            try {
                dt1 = msimpleDateFormat.parse(msimpleDateFormat.format(calendar.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat format2 = new SimpleDateFormat("EEEE");
            finalDay = format2.format(dt1);

            dateSequence[i] = msimpleDateFormat.format(calendar.getTime())+","+finalDay;
        }
        //Toast.makeText(MyChanneling.this,dateSequence[3],Toast.LENGTH_LONG).show();

    }


   /* @Override
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
            Toast.makeText(MyChanneling.this,"dssddsdd",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/




}
