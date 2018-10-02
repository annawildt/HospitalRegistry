package com.company;

import java.sql.*;

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


    //Example
//    public List<Skill> getAllSkills() {
//        String stmt = "SELECT * FROM dbo.Skill";
//
//        ArrayList<Skill> skillMasterList = new ArrayList<>();
//
//        try (PreparedStatement sth = dbconn.prepareStatement(stmt)) {
//
//            ResultSet res = sth.executeQuery();
//
//            while (res.next()) {
//                skillMasterList.add(newSkill(res));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("getAllSkills repositoryError");
//
//            throw new RuntimeException(e.getMessage());
//        }
//        return skillMasterList;
//    }

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
