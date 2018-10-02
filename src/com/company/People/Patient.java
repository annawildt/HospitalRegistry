package com.company.People;

import com.company.Addresses.Address;
import com.company.Visit.Visit;

import java.util.List;

public class Patient extends Person{
    private Visit patientVisit;

    public Patient(String name, List<Address> addresses, Visit visit) {
        this.name = name;
        this.addresses = addresses;
        this.patientVisit = visit;
    }

    public Visit getPatientVisit() {
        return patientVisit;
    }
}
