package com.example.healthslpatients;

public class Chanalling {

    String chanallingId;
    String startTime;
    String endTime;
    String maxPtients;
    String doctorID;
    String doctorName;
    String selectedDayOfWeek;

    public Chanalling(){

    }

    public Chanalling(String chanallingId,String selectedDayOfWeek,String doctorID,String doctorName, String startTime,String endTime,String maxPtients){
        this.chanallingId=chanallingId;
        this.startTime=startTime;
        this.endTime=endTime;
        this.maxPtients=maxPtients;
        this.doctorID=doctorID;
        this.doctorName=doctorName;
        this.selectedDayOfWeek = selectedDayOfWeek;
    }

    public String getSelectedDayOfWeek() {
        return selectedDayOfWeek;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getChanallingId() {
        return chanallingId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getMaxPtients() {
        return maxPtients;
    }
}

