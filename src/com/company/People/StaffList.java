package com.company.People;

import java.util.ArrayList;
import java.util.List;

public class StaffList {
    private List<MedicalStaff> staffList = new ArrayList<>();

    public List<MedicalStaff> getStaffList() {
        return staffList;
    }
    public void addStaff(MedicalStaff staff) {
        staffList.add(staff);
    }
    public void removeStaff(MedicalStaff staff) {
        staffList.remove(staff);
    }
}
