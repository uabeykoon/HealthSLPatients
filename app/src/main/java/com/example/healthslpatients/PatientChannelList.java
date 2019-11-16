package com.example.healthslpatients;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PatientChannelList extends ArrayAdapter<PatientChannelDetails> {
    private Activity contex;
    private List<PatientChannelDetails> patientChannelList;

    public PatientChannelList(Activity contex,List<PatientChannelDetails> patientChannelList){
        super(contex,R.layout.patient_channal_sample,patientChannelList);
        this.contex=contex;
        this.patientChannelList = patientChannelList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=contex.getLayoutInflater();

        View listView = inflater.inflate(R.layout.patient_channal_sample,null,true);
        TextView textView1 = (TextView)listView.findViewById(R.id.doctorNamep);
        TextView textView2 = (TextView)listView.findViewById(R.id.dateFull);
        TextView textView3 = (TextView)listView.findViewById(R.id.channalNumber);
        TextView textView4 = (TextView)listView.findViewById(R.id.startTimepc);
        TextView textView5 = (TextView)listView.findViewById(R.id.endTimepc);


        PatientChannelDetails patientChannelDetails = patientChannelList.get(position);



        textView1.setText(patientChannelDetails.getDoctorName());
        textView2.setText(patientChannelDetails.getDate());
        textView3.setText("#"+patientChannelDetails.getChannalNumber());
        textView4.setText("Start Time       -  "+patientChannelDetails.getStartTime());
        textView5.setText("End Time         -  "+patientChannelDetails.getEndTime());




        return listView;
    }
}
