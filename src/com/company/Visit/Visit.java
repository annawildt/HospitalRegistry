package com.company.Visit;

import com.company.Clinics.Clinic;
import com.company.People.MedicalStaff;
import com.company.People.Patient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Visit {
    private Patient patient;
    private Date dateAndTimeOfVisit;
    private MedicalStaff medicalStaff;
    private Clinic clinic;
    private List<Issue> reasonsForVisit;

    public Visit(Patient patient, Date dateAndTimeOfVisit, MedicalStaff medicalStaff, Clinic clinic, List<Issue> reasonsForVisit) {
        this.patient = patient;
        this.dateAndTimeOfVisit = dateAndTimeOfVisit;
        this.medicalStaff = medicalStaff;
        this.clinic = clinic;
        this.reasonsForVisit = reasonsForVisit;
    }

    public void changeDateAndTime(Date date) {
        this.dateAndTimeOfVisit = date;
    }

    public MedicalStaff getMedicalStaff() {
        return medicalStaff;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public String getDateAndTimeOfVisit() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm E dd/MM yyyy");
        return dateFormat.format(dateAndTimeOfVisit);
    }

    public List<Issue> getReasonsForVisit() {
        return reasonsForVisit;
    }

    public Patient getPatient() {
        return patient;
    }

    public int getCostOfVisit() {
        int totalCost = 0;
        for (Issue issue : reasonsForVisit) {
            totalCost += issue.getPrice();
        }
        return totalCost;
    }
}
