package com.example.healthslpatients;

public class PatientChannelDetails {
    String channelPatientId;
    String channelId;
    String doctorlId;
    String date;
    String channalNumber;
    String doctorName;
    String startTime;
    String endTime;
    String maxPatient;




    public PatientChannelDetails() {

    }

    public PatientChannelDetails(String channelPatientId,String doctorlId, String date, String channalNumber, String doctorName, String channelId,String startTime,String endTime,String maxPatient) {
        this.channelId = channelId;
        this.date = date;
        this.channalNumber = channalNumber;
        this.doctorName = doctorName;
        this.channelPatientId = channelPatientId;
        this.doctorlId=doctorlId;
        this.startTime=startTime;
        this.endTime=endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getMaxPatient() {
        return maxPatient;
    }

    public String getDoctorlId() {
        return doctorlId;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getDate() {
        return date;
    }

    public String getChannalNumber() {
        return channalNumber;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getChannelPatientId() {
        return channelPatientId;
    }
}
