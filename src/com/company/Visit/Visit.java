package com.company.Visit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Visit {
    private Date dateOfVisit;
    private String medicalStaffName;
    private String clinicName;
    private List<Issue> reasonsForVisit;

    public Visit(Date dateOfVisit, String medicalStaffName, String clinicName, List<Issue> reasonsForVisit) {
        this.dateOfVisit = dateOfVisit;
        this.medicalStaffName = medicalStaffName;
        this.clinicName = clinicName;
        this.reasonsForVisit = reasonsForVisit;
    }

    public void changeDateAndTime(Date date) {
        this.dateOfVisit = date;
    }

    public String getmedicalStaffName() {
        return medicalStaffName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public String getDateOfVisit() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm E dd/MM yyyy");
        return dateFormat.format(dateOfVisit);
    }

    public List<Issue> getReasonsForVisit() {
        return reasonsForVisit;
    }

    public int getCostOfVisit() {
        int totalCost = 0;
        for (Issue issue : reasonsForVisit) {
            totalCost += issue.getPrice();
        }
        return totalCost;
    }
}
