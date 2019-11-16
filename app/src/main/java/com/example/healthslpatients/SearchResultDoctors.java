package com.example.healthslpatients;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchResultDoctors extends AppCompatActivity {

    String doctorID;
    String userID;
    String patientName;
    Query query1;
    List<Chanalling> chanallingList;

    ListView listViewChanalling;
    String[] days={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        chanallingList = new ArrayList<>();

        listViewChanalling = (ListView)findViewById(R.id.listViewChanalling);


        //get string that parsing from before intent

        Intent intent = getIntent();
        doctorID=intent.getStringExtra("doctorID");
        userID=intent.getStringExtra("userId");
        patientName=intent.getStringExtra("patientName");

        Toast.makeText(SearchResultDoctors.this,patientName,Toast.LENGTH_LONG).show();


        //get all the related items to list

        for(int i = 0;i<6;i++) {


            //make query to retrieve


            query1 = FirebaseDatabase.getInstance().getReference("Channaling").child(days[i]).orderByChild("doctorID").equalTo(doctorID);

            //retrieve

            query1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    //chanallingList.clear();

                    for (DataSnapshot chanallingSnapshot : dataSnapshot.getChildren()) {
                        Chanalling chanalling = chanallingSnapshot.getValue(Chanalling.class);
                        chanallingList.add(chanalling);
                    }

                    //set value to the list view

                    ChannalList adapter = new ChannalList(SearchResultDoctors.this, chanallingList);
                    listViewChanalling.setAdapter(adapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        //add adapter

        ChannalList adapter = new ChannalList(SearchResultDoctors.this, chanallingList);
        listViewChanalling.setAdapter(adapter);



        //set on click on list items

        listViewChanalling.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //get chanalling details

                Chanalling chanalling = chanallingList.get(position);

                String chanallingId =chanalling.getChanallingId();
                String startTime =chanalling.getStartTime();
                String endTime =chanalling.getEndTime();
                String maxPatients =chanalling.getMaxPtients();
                String doctorName =chanalling.getDoctorName();
                String selectedDayOfWeek =chanalling.getSelectedDayOfWeek();


                //create intent to send data to view page

                Intent intent1 = new Intent(SearchResultDoctors.this,ViewSearchResult.class);
                intent1.putExtra("chanallingId",chanallingId);
                intent1.putExtra("startTime",startTime);
                intent1.putExtra("endTime",endTime);
                intent1.putExtra("maxPatients",maxPatients);
                intent1.putExtra("doctorName",doctorName);
                intent1.putExtra("selectedDayOfWeek",selectedDayOfWeek);
                intent1.putExtra("userId",userID);
                intent1.putExtra("doctorId",doctorID);
                intent1.putExtra("patientName",patientName);
                startActivity(intent1);

//                String a = chanallingId + startTime + endTime +maxPatients + doctorName + selectedDayOfWeek;
//                Toast.makeText(SearchResult.this,a,Toast.LENGTH_LONG).show();


            }
        });


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


}

