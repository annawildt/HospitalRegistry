package com.company;

import com.company.Addresses.Address;
import com.company.Addresses.AddressType;
import com.company.Clinics.Clinic;
import com.company.Clinics.Clinics;
import com.company.People.MedicalStaff;
import com.company.People.MedicalStaffEnum;
import com.company.People.Patient;
import com.company.Visit.Issue;
import com.company.Visit.IssueEnum;
import com.company.Visit.Visit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private Connection dbconn;
    public Repository (String connstr) {
        try {
            dbconn = DriverManager.getConnection(connstr);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Repository repositoryError");
            throw new RuntimeException(e.getMessage());
        }
    }

    /****** GET CLINICS ******/

    public Clinics allClinics() {
        String statement = "SELECT * FROM dbo.Clinics";

        List<Clinic> clinics = new ArrayList<>();

        try (PreparedStatement preparedStatement = dbconn.prepareStatement(statement)) {
            ResultSet res = preparedStatement.executeQuery();

            while(res.next()) {
                clinics.add(getClinicFromDB(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("allClinics repositoryError");
            throw new RuntimeException(e.getMessage());
        }
        return new Clinics(clinics);
    }

    private List<Clinic> getConnectedClinics(String clinicID) {
        String statement = "SELECT * FROM dbo.Clinics WHERE ConnectedClinicID = ?";
        List<Clinic> connectedClinics = new ArrayList<>();

        try (PreparedStatement preparedStatement = dbconn.prepareStatement(statement)) {
            preparedStatement.setString(1, clinicID);
            ResultSet res = preparedStatement.executeQuery();

            while(res.next()) {
                connectedClinics.add(getClinicFromDB(res));
            }

            return connectedClinics;

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("getConnectedClinics repositoryError");
            throw new RuntimeException(e.getMessage());
        }

    }

    private Clinic getClinicFromDB(ResultSet res) {
        try {
            String clinicID = res.getString(1);
            String clinicName = res.getString(2);
            List<Address> clinicAddresses = getAddressesForClinic(res.getString(1));
            List<MedicalStaff> clinicStaff = getMedicalStaffList(clinicID);
            List<Clinic> connectedClinics = getConnectedClinics(clinicID);

            return new Clinic(clinicName, clinicAddresses, clinicStaff, connectedClinics);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getClinicFromDB repositoryError");
            throw new RuntimeException(e.getMessage());
        }
    }

    private String getClinicNameFromID(String clinicID) {
        String statement = "SELECT * FROM dbo.Clinics WHERE ID = ?";
        try (PreparedStatement preparedStatement = dbconn.prepareStatement(statement)){
            preparedStatement.setString(1, clinicID);
            ResultSet res = preparedStatement.executeQuery();
            if(res.next()) {
                return res.getString(2);
            }
            return "No clinic found";
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getClinicFromDB repositoryError");
            throw new RuntimeException(e.getMessage());
        }
    }

    /****** GET ADDRESSES ******/

    private List<Address> getAddressesForClinic (String clinicID) {
        List<Address> addresses = new ArrayList<>();

        String statement = "SELECT * FROM dbo.Addresses " +
                "INNER JOIN Clinics ON Addresses.ID = Clinics.AddressID " +
                "WHERE Clinics.ID = ?";

        try (PreparedStatement sth = dbconn.prepareStatement(statement)) {
            sth.setString(1, clinicID);
            ResultSet res = sth.executeQuery();

            while (res.next()) {
                addresses.add(getAddressFromDB(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAddressForClinic repositoryError");
            throw new RuntimeException(e.getMessage());
        }

        return addresses;
    }

    private Address getAddressFromID(String addressID) {
        String statement = "SELECT * dbo.Addresses WHERE ID = ?";
        try (PreparedStatement sth = dbconn.prepareStatement(statement)) {
            sth.setString(1, addressID);
            ResultSet res = sth.executeQuery();
            if (res.next()) {
               return getAddressFromDB(res);
            }
            return new Address(null, 0, null, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAddressFromID repositoryError");
            throw new RuntimeException(e.getMessage());
        }
    }

    private Address getAddressFromDB(ResultSet res) {
         AddressType addressType;

         try {
             String name = res.getString(2);
             int zipcode = res.getInt(3);
             String area = res.getString(4);
             String country = res.getString(5);
             String testAdress = res.getString(6);

             if (testAdress.equals("INVOICE")) {
                 addressType = AddressType.INVOICE;
             } else {
                 addressType = AddressType.VISITING;
             }

             return new Address(name, zipcode, area, country, addressType);

         } catch (SQLException e) {
             e.printStackTrace();
             System.out.println("getAddressFromDB repositoryError");
             throw new RuntimeException(e.getMessage());
         }
    }

    /****** GET STAFF ******/

    private List<MedicalStaff> getMedicalStaffList(String clinicID) {
        List<MedicalStaff> medicalStaff = new ArrayList<>();

        String statement = "SELECT * FROM dbo.MedicalStaff " +
                "INNER JOIN Clinics ON Clinics.ID = MedicalStaff.ClinicID " +
                "WHERE Clinics.ID = ?";

        try (PreparedStatement sth = dbconn.prepareStatement(statement)) {
            sth.setString(1, clinicID);
            ResultSet res = sth.executeQuery();

            while (res.next()) {
                medicalStaff.add(getStaffFromDB(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAddressForClinic repositoryError");
            throw new RuntimeException(e.getMessage());
        }

        return medicalStaff;
    }

    private MedicalStaff getStaffFromDB(ResultSet res) {
        try {
            MedicalStaffEnum title = MedicalStaffEnum.DOCTOR;
            if (res.getString(2).equals("NURSE")) {
                title = MedicalStaffEnum.NURSE;
            }
            String name = res.getString(3);
            List<Address> addresses = new ArrayList<>();
            addresses.add(getAddressFromID(res.getString(4)));
            List<Clinic> clinics = new ArrayList<>();

            return new MedicalStaff(title, name, addresses);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAddressFromDB repositoryError");
            throw new RuntimeException(e.getMessage());
        }
    }

    private String getMedicalStaffNameFromID(String staffID) {
        String statement = "SELECT * FROM dbo.MedicalStaff WHERE ID = ?";
        try (PreparedStatement preparedStatement = dbconn.prepareStatement(statement)){
            preparedStatement.setString(1, staffID);
            ResultSet res = preparedStatement.executeQuery();
            if(res.next()) {
                return res.getString(2);
            }
            return "No staff found";
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getStaffNameFromID repositoryError");
            throw new RuntimeException(e.getMessage());
        }
    }

    /****** GET PATIENTS ******/

    public List<Patient> getPatientList() {
        List<Patient> patientList = new ArrayList<>();

        String statement = "SELECT * FROM dbo.Patients";
        try (PreparedStatement sth = dbconn.prepareStatement(statement)){
            ResultSet res = sth.executeQuery();
            while (res.next()) {
                patientList.add(getPatientFromDB(res));
            }
        } catch (SQLException e ) {
            e.printStackTrace();
            System.out.println("getPatientList repositoryError");
            throw new RuntimeException(e.getMessage());
        }
        return patientList;
    }

    private Patient getPatientFromDB(ResultSet res) {
        try {
            String name = res.getString(2);
            List<Address> addressList = new ArrayList<>();
            addressList.add(getAddressFromID(res.getString(3)));
            Visit visit = getVisitFromID(res.getString(1));
            return new Patient(name, addressList, visit);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getPatientFromDB repositoryError");
            throw new RuntimeException(e.getMessage());
        }
    }

    /****** GET VISITS ******/

    private Visit getVisitFromID(String patientID) {
        String statement = "SELECT * dbo.Visits WHERE patientID = ?";
        try (PreparedStatement sth = dbconn.prepareStatement(statement)) {
            sth.setString(1, patientID);
            ResultSet res = sth.executeQuery();
            if (res.next()) {
                return getVisitFromDB(res);
            }
            return new Visit(null, null, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAddressFromID repositoryError");
            throw new RuntimeException(e.getMessage());
        }
    }

    private Visit getVisitFromDB(ResultSet res) {
        try {
            java.sql.Date dbDate = res.getDate(3);
            java.util.Date dateOfVisit = new java.util.Date(dbDate.getTime());
            String clinicName = getClinicNameFromID(res.getString(5));
            String medicalStaffName = getMedicalStaffNameFromID(res.getString(4));
            List<Issue> reasonsForVisit = getIssueList(res.getString(6));

            return new Visit(dateOfVisit, clinicName, medicalStaffName, reasonsForVisit);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getVisitFromDB repositoryError");
            throw new RuntimeException(e.getMessage());
        }
    }

    /****** GET ISSUES ******/

    private List<Issue> getIssueList(String issueID) {
        List<Issue> issueList = new ArrayList<>();
        String statement = "SELECT * FROM dbo.Issues WHERE  = ?";
        try (PreparedStatement sth = dbconn.prepareStatement(statement)) {
            sth.setString(1, issueID);
            ResultSet res = sth.executeQuery();
            while (res.next()) {
                issueList.add(getIssueFromDB(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getIssueList repositoryError");
            throw new RuntimeException(e.getMessage());
        }
        return issueList;
    }

    private Issue getIssueFromDB(ResultSet res) {
        try {
            IssueEnum issueEnum = IssueEnum.EXAM;
            if (res.getString(2).equals("LAB_VISIT")) {
                issueEnum = IssueEnum.LAB_VISIT;
            } else if (res.getString(2).equals("OPERATION")) {
                issueEnum = IssueEnum.OPERATION;
            }
            String description = "No current description";
            int price = res.getInt(3);

            return new Issue(issueEnum, description, price);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getIssueFromDB repositoryError");
            throw new RuntimeException(e.getMessage());
        }
    }


    //    date conversion example
//    java.util.Date now = new java.util.Date();
//        System.out.println("Value of java.util.Date : " + now);
//
//    //converting java.util.Date to java.sql.Date in Java
//    java.sql.Date sqlDate = new java.sql.Date(now.getTime());
//        System.out.println("Converted value of java.sql.Date : " + sqlDate);
//
//    //converting java.sql.Date to java.util.Date back
//    java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
//        System.out.println("Converted value of java.util.Date : " + utilDate);
//}
}
