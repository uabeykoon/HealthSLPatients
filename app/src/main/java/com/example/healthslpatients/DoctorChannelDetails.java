package com.example.healthslpatients;

public class DoctorChannelDetails {

    String channelPatientId;
    String doctorId;
    String channelId;
    String date;
    String channalNumber;
    String doctorName;
    String patientName;

    public DoctorChannelDetails(){

    }

    public DoctorChannelDetails(String channelPatientId, String doctorId, String channelId, String date, String channalNumber, String doctorName,String patientName) {
        this.channelPatientId = channelPatientId;
        this.doctorId = doctorId;
        this.channelId = channelId;
        this.date = date;
        this.channalNumber = channalNumber;
        this.doctorName = doctorName;
        this.patientName = patientName;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getChannelPatientId() {
        return channelPatientId;
    }

    public String getDoctorId() {
        return doctorId;
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
}
