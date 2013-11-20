/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.sql.*;
import connection.ConnectionPool;
import java.util.ArrayList;

/**
 *
 * @author ingokarn.2011
 */
public class ResearcherDataManager {
    
    public static Researcher getResearcherByID(int rID) throws Exception{
        Researcher r = null;
    	
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query="SELECT * FROM `researcher` WHERE `rID`='"+rID+"';";
        try {
            conn = ConnectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
    		int rID1 = rs.getInt("rID");
    		String rName = rs.getString("rName");
                String photoURL = rs.getString("photoURL");
                String qualification = rs.getString("qualification");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String cv = rs.getString("cv");
                int areaID = rs.getInt("areaID");
                String designation = rs.getString("designation");
                String school = rs.getString("school");
    		r = new Researcher(rID1, rName, photoURL, qualification, email, phoneNumber, cv, designation, school, areaID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(conn, stmt, rs);
        }
        return r;    	
    }
    
    //returns arraylist of all nodes
    public static ArrayList<Researcher> getAllResearchers() throws Exception{
        ArrayList<Researcher> rList = new ArrayList<Researcher>();
        Researcher r = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query="SELECT * FROM `researcher`;";
        try {
            conn = ConnectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
    		int rID = rs.getInt("rID");
    		String rName = rs.getString("rName");
                String photoURL = rs.getString("photoURL");
                String qualification = rs.getString("qualification");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String cv = rs.getString("cv");
                int areaID = rs.getInt("areaID");
    		String designation = rs.getString("designation");
                String school = rs.getString("school");
    		r = new Researcher(rID, rName, photoURL, qualification, email, phoneNumber, cv, designation, school, areaID);
                rList.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(conn, stmt, rs);
        }
        return rList;
    }
    
    public static ArrayList<Researcher> getAllResearchersBySchool(String school) throws Exception{
        ArrayList<Researcher> rList = new ArrayList<Researcher>();
        Researcher r = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query="SELECT * FROM `researcher` where school='"+school+"';";
        try {
            conn = ConnectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
    		int rID = rs.getInt("rID");
    		String rName = rs.getString("rName");
                String photoURL = rs.getString("photoURL");
                String qualification = rs.getString("qualification");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String cv = rs.getString("cv");
                int areaID = rs.getInt("areaID");
    		String designation = rs.getString("designation");
                String school1 = rs.getString("school");
    		r = new Researcher(rID, rName, photoURL, qualification, email, phoneNumber, cv, designation, school1, areaID);
                rList.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(conn, stmt, rs);
        }
        return rList;
    }

    public static int createResearcher(String rName, String photoURL, String qualification, String email, String phoneNumber, String cv, String designation, int areaID, String school)throws Exception {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;  
        int rID = 0;
        String query = "INSERT INTO `researcher` (`rName`, `photoURL`, `qualification`,`email`, `phoneNumber`, `cv`, `designation`, `school`, `areaID`) " +
                    "VALUES ('" + rName + "', '" + photoURL + "', '" + qualification + "', '"+ email + "', '" + phoneNumber + "', '" + cv + "', '" + designation + "', '"+ school + "', '"+ areaID +"');";

        try {
           conn = ConnectionPool.getConnection();
           stmt = conn.createStatement();
          stmt.execute(query,Statement.RETURN_GENERATED_KEYS);
                rs = stmt.getGeneratedKeys();
                while(rs.next()){
                    rID=rs.getInt(1);
                }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           ConnectionPool.close(conn, stmt, rs);
       }
       return rID;
    }

    public static Researcher getResearcherByName(String rName) throws Exception{
        Researcher r = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;  
        String query="SELECT * FROM `researcher` WHERE `rName`='"+rName+"';";

        try {
           conn = ConnectionPool.getConnection();
           stmt = conn.createStatement();
          rs = stmt.executeQuery(query);
          while(rs.next()){
                    int rID = rs.getInt("rID");
                    String rName1= rs.getString("rName");
                    String photoURL = rs.getString("photoURL");
                    String qualification = rs.getString("qualification");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    String cv = rs.getString("cv");
                    String designation = rs.getString("designation");
                    int areaID = rs.getInt("areaID");
                    String school = rs.getString("school");
                    r = new Researcher(rID, rName1, photoURL, qualification, email, phoneNumber, cv, designation, school, areaID);
            }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           ConnectionPool.close(conn, stmt, rs);
       }
       return r;
    }

    public static void addCollboration(int rID, int pubID) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionPool.getConnection();
            stmt = conn.createStatement();
            String query = "INSERT INTO `collaboration` (`rID`,`pubID`) " +
                "VALUES ('" + rID + "', '"+ pubID + "');";
        	stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(conn, stmt, rs);
        }
    }

    public static int checkIfExists(String name)throws Exception {
       String[] splitName = name.toLowerCase().split(" ");
       boolean found=false;
       int researchID = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;  
        String query="SELECT * FROM `researcher`;";

        try {
           conn = ConnectionPool.getConnection();
           stmt = conn.createStatement();
          rs = stmt.executeQuery(query);
          while(rs.next()){
              String foundName = rs.getString("rName").toLowerCase();
              found=true;
              int count=0;
              for(int i=0; i<splitName.length; i++){
                  if(foundName.contains(splitName[i])){
                      count++;
                  }
              }
              if(count>=2){
                  researchID = rs.getInt("rID");
              }
          }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           ConnectionPool.close(conn, stmt, rs);
       }
        return researchID;
    }
    
    public static ArrayList<Researcher> getCollaborators(int pubID, String school, String researcharea) throws Exception{
        ArrayList<Researcher> rList = new ArrayList<Researcher>();
        Researcher r = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;  
        String query=null;
        try {
                if(!school.equals("")&&!researcharea.equals("")){
                int areaID = ResearcherAreaDataManager.getAreaIDByName(researcharea);
                query="select * from researcher inner join collaboration on collaboration.`rID`=researcher.`rID` where collaboration.`pubID`='"+pubID+"' and researcher.`areaID`='"+areaID+"' and researcher.school='"+school+"';";
            }else if (!school.equals("")&&researcharea.equals("")){
                query="select * from researcher inner join collaboration on collaboration.`rID`=researcher.`rID` where collaboration.`pubID`='"+pubID+"' and researcher.school='"+school+"';";
            }else if(school.equals("")&&!researcharea.equals("")){
                int areaID = ResearcherAreaDataManager.getAreaIDByName(researcharea);
                query="select * from researcher inner join collaboration on collaboration.`rID`=researcher.`rID` where collaboration.`pubID`='"+pubID+"' and researcher.`areaID`='"+areaID+"';";
            }else if (school.equals("")&&researcharea.equals("")){
                query="select * from researcher inner join collaboration on collaboration.`rID`=researcher.`rID` where collaboration.`pubID`='"+pubID+"';";
            }
           conn = ConnectionPool.getConnection();
           stmt = conn.createStatement();
           rs = stmt.executeQuery(query);
           while(rs.next()){
                    int rID = rs.getInt("rID");
                    String rName1= rs.getString("rName");
                    String photoURL = rs.getString("photoURL");
                    String qualification = rs.getString("qualification");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    String cv = rs.getString("cv");
                    String designation = rs.getString("designation");
                    int areaID = rs.getInt("areaID");
                    String school1 = rs.getString("school");
                    r = new Researcher(rID, rName1, photoURL, qualification, email, phoneNumber, cv, designation, school1, areaID);
                    rList.add(r);
            }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           ConnectionPool.close(conn, stmt, rs);
       }
       return rList;
    }
    
    public static ArrayList<Researcher> getCollaborators(int pubID) throws Exception{
        ArrayList<Researcher> rList = new ArrayList<Researcher>();
        Researcher r = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;  
        String query=null;
        try {
                
           query="select * from researcher inner join collaboration on collaboration.`rID`=researcher.`rID` where collaboration.`pubID`='"+pubID+"';";
           conn = ConnectionPool.getConnection();
           stmt = conn.createStatement();
           rs = stmt.executeQuery(query);
           while(rs.next()){
                    int rID = rs.getInt("rID");
                    String rName1= rs.getString("rName");
                    String photoURL = rs.getString("photoURL");
                    String qualification = rs.getString("qualification");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    String cv = rs.getString("cv");
                    String designation = rs.getString("designation");
                    int areaID = rs.getInt("areaID");
                    String school1 = rs.getString("school");
                    r = new Researcher(rID, rName1, photoURL, qualification, email, phoneNumber, cv, designation, school1, areaID);
                    rList.add(r);
            }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           ConnectionPool.close(conn, stmt, rs);
       }
       return rList;
    }
    
    
    public static ArrayList<String> getSchools() throws Exception{
        ArrayList<String> schoolList = new ArrayList<String>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query="select distinct school from researcher;";
        try {
            conn = ConnectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
    		String school = rs.getString("school");
    		schoolList.add(school);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(conn, stmt, rs);
        }
        return schoolList;
    }
    
    
}
