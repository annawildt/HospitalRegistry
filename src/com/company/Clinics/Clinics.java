package com.company.Clinics;

import java.util.List;

public class Clinics {
    private List<Clinic> clinics;

    public Clinics (List<Clinic> clinics) {
        this.clinics = clinics;
    }

    public void add(Clinic clinic) {
        clinics.add(clinic);
    }

    public List<Clinic> getClinics() {
        return clinics;
    }
}
