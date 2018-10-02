package com.company.People;

import java.util.List;

public class PatientRegister {
    private List<Patient> patients;

    public List<Patient> getPatients() {
        return patients;
    }
    public void addPatient(Patient patient) {
        patients.add(patient);
    }
    public void removePatient(Patient patient) {
        patients.remove(patient);
    }
}