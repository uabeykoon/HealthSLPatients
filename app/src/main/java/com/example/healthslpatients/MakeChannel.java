package com.example.healthslpatients;

public class MakeChannel {

    String channelingId;
    String userId;
    String doctorId;
    String doctorName;
    String channelNumber;
    String patientName;

    public MakeChannel(String channelingId, String userId, String doctorId, String doctorName,String channelNumber,String patientName) {
        this.channelingId = channelingId;
        this.userId = userId;
        this.doctorName = doctorName;
        this.channelNumber=channelNumber;
        this.doctorId= doctorId;
        this.patientName= patientName;
    }
    public MakeChannel(){}

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getChannelNumber() {
        return channelNumber;
    }

    public String getChannelingId() {
        return channelingId;
    }

    public String getUserId() {
        return userId;
    }

    public String getDoctorName() {
        return doctorName;
    }
}
