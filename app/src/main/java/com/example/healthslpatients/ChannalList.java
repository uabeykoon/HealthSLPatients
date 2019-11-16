package com.example.healthslpatients;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ChannalList extends ArrayAdapter<Chanalling> {


    private Activity contex;
    private List<Chanalling> chanallingList;

    public ChannalList(Activity contex,List<Chanalling> chanallingLists){
        super(contex,R.layout.chanallistsample,chanallingLists);
        this.contex=contex;
        this.chanallingList = chanallingLists;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=contex.getLayoutInflater();

        View listView = inflater.inflate(R.layout.chanallistsample,null,true);
        TextView textView1 = (TextView)listView.findViewById(R.id.aaa);
        TextView textView2 = (TextView)listView.findViewById(R.id.aaaa);
        TextView textView3 = (TextView)listView.findViewById(R.id.aaaaa);
        //TextView textView4 = (TextView)listView.findViewById(R.id.aaaaaa);
        TextView textView5 = (TextView)listView.findViewById(R.id.doctorName);
        TextView textView6 = (TextView)listView.findViewById(R.id.dayOfWeek);

        Chanalling chanalling = chanallingList.get(position);


        //textView4.setText(chanalling.getChanallingId());
        textView1.setText("Start Time       -  "+chanalling.getStartTime());
        textView2.setText("End Time         -  "+chanalling.getEndTime());
        textView3.setText("Maximum Patients -  "+chanalling.getMaxPtients());
        textView5.setText("Dr."+chanalling.getDoctorName());
        textView6.setText(chanalling.getSelectedDayOfWeek());



        return listView;
    }
}
