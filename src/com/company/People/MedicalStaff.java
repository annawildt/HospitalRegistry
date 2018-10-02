package com.company.People;

import com.company.Addresses.Address;

import java.util.List;

public class MedicalStaff extends Person{
    private MedicalStaffEnum title;

    public MedicalStaff(MedicalStaffEnum title, String name, List<Address> addresses) {
        this.title = title;
        this.name = name;
        this.addresses = addresses;
    }

    public MedicalStaffEnum getTitle() {
        return title;
    }

}
