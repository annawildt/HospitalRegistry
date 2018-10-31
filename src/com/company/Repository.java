package com.company;

import com.company.Addresses.Address;
import com.company.Addresses.AddressType;
import com.company.Clinics.Clinic;
import com.company.Clinics.Clinics;
import com.company.People.MedicalStaff;
import com.company.People.MedicalStaffEnum;

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
                clinics.add(createNewClinicFromDB(res));
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
                connectedClinics.add(createNewClinicFromDB(res));
            }

            return connectedClinics;

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("getConnectedClinics repositoryError");
            throw new RuntimeException(e.getMessage());
        }

    }

    private Clinic createNewClinicFromDB (ResultSet res) {

        try {
            String clinicID = res.getString(1);
            String clinicName = res.getString(2);
            List<Address> clinicAddresses = getAddressesForClinic(res.getString(1));
            List<MedicalStaff> clinicStaff = getMedicalStaffList(clinicID);
            List<Clinic> connectedClinics = getConnectedClinics(clinicID);

            return new Clinic(clinicName, clinicAddresses, clinicStaff, connectedClinics);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("createNewClinicFromDB repositoryError");
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
                addresses.add(createAddressFromResultSet(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAddressForClinic repositoryError");
            throw new RuntimeException(e.getMessage());
        }

        return addresses;
    }

    private Address createAddressFromID(String addressID) {
        Address address = new Address("NULL", 0, "NULL", "NULL", AddressType.VISITING);

        String statement = "SELECT * dbo.Addresses WHERE AddressID = ?";

        try (PreparedStatement sth = dbconn.prepareStatement(statement)) {
            sth.setString(1, addressID);
            ResultSet res = sth.executeQuery();

            if (res.next()) {
                address = createAddressFromResultSet(res);
            }

            return address;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("createAddressFromID repositoryError");
            throw new RuntimeException(e.getMessage());
        }
    }

    private Address createAddressFromResultSet(ResultSet res) {
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
             System.out.println("createAddressFromResultSet repositoryError");
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
                medicalStaff.add(createNewStaffFromDB(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAddressForClinic repositoryError");
            throw new RuntimeException(e.getMessage());
        }

        return medicalStaff;
    }

    private MedicalStaff createNewStaffFromDB(ResultSet res) {
        try {
            MedicalStaffEnum title = MedicalStaffEnum.DOCTOR;
            if (res.getString(2).equals("NURSE")) {
                title = MedicalStaffEnum.NURSE;
            }
            String name = res.getString(3);
            List<Address> addresses = new ArrayList<>();
            addresses.add(createAddressFromID(res.getString(4)));
            List<Clinic> clinics = new ArrayList<>();

            return new MedicalStaff(title, name, addresses);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("createAddressFromResultSet repositoryError");
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
