package com.example.healthslpatients;

public class Patients {

    String firstName;
    String lastName;
    String phoneNumber;
    String type;


    public Patients(){

    }

    public Patients(String firstName, String lastName, String phoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.type="1";
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }



    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getType() {
        return type;
    }
}


