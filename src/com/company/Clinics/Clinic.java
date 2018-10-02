package com.company.Clinics;

import com.company.Addresses.Address;
import com.company.People.MedicalStaff;

import java.util.ArrayList;
import java.util.List;

import static com.company.People.MedicalStaffEnum.DOCTOR;
import static com.company.People.MedicalStaffEnum.NURSE;

public class Clinic {
    private String clinicName;
    private List<Address> clinicAddresses;
    private List<MedicalStaff> clinicStaff;
    private List<Clinic> connectedClinics;

    public Clinic(String clinicName, List<Address> clinicAddresses, List<MedicalStaff> clinicStaff, List<Clinic> connectedClinics) {
        this.clinicName = clinicName;
        this.clinicAddresses = clinicAddresses;
        this.clinicStaff = clinicStaff;
        this.connectedClinics = connectedClinics;
    }

    public String getClinicName() {
        return clinicName;
    }

    public List<Address> getClinicAdresses() {
        return clinicAddresses;
    }

    public void addAddress(Address address) {
        clinicAddresses.add(address);
    }
    public void removeAddress(Address address) {
        clinicAddresses.remove(address);
    }

    public List<MedicalStaff> getClinicStaff() {
        return clinicStaff;
    }
    public void addClinicStaff(MedicalStaff staff) {
        clinicStaff.add(staff);
    }
    public void removeClinicStaff(MedicalStaff staff) {
        clinicStaff.remove(staff);
    }

    public List<MedicalStaff> getClinicDoctors() {
        List<MedicalStaff> doctors = new ArrayList<>();
        for (MedicalStaff staff : clinicStaff) {
            if(staff.getTitle() == DOCTOR) {
                doctors.add(staff);
            }
        }
        return doctors;
    }

    public List<MedicalStaff> getClinicNurses() {
        List<MedicalStaff> nurses = new ArrayList<>();
        for (MedicalStaff staff : clinicStaff) {
            if(staff.getTitle() == NURSE) {
                nurses.add(staff);
            }
        }
        return nurses;
    }

    public List<Clinic> getConnectedClinics() {
        return connectedClinics;
    }
    public void addConnectedClinic(Clinic clinic) {
        if(!(connectedClinics.contains(clinic))) {
            connectedClinics.add(clinic);
        }
    }
    public void removeConnectedClinic(Clinic clinic) {
        if(connectedClinics.contains(clinic)) {
            connectedClinics.remove(clinic);
        }
    }

}
