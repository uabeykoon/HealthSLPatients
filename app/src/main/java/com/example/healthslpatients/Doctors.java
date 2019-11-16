package com.example.healthslpatients;

public class Doctors {

    String firstName;
    String lastName;
    String specification;
    String phoneNumber;
    String type;
    String doctorID;


    public Doctors() {

    }

    public Doctors(String doctorID,String firstName, String lastName, String specification, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specification = specification;
        this.phoneNumber = phoneNumber;
        this.type="2";
        this.doctorID = doctorID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getSpecification() {
        return this.specification;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public String getType(){
        return type;
    }


    @Override
    public String toString() {
        return firstName+" "+lastName;
    }
}
