/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import connection.ConnectionPool;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author ingokarn.2011
 */
public class PublicationDataManager {

    public static int getTypeID(String pubType) throws Exception {
        int pubTypeID = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;  
        String query="select * from publicationtype where publicationtype.`pubTypeName`='"+pubType+"';";

        try {
           conn = ConnectionPool.getConnection();
           stmt = conn.createStatement();
          rs = stmt.executeQuery(query);
          while(rs.next()){
                pubTypeID = rs.getInt("pubTypeID");
                    
            }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           ConnectionPool.close(conn, stmt, rs);
       }
       return pubTypeID;
    }

    public static int createPublication(String pubName, int pubTypeID, String year) throws Exception{
         Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;  
        int pubID = 0;
        String query = "INSERT INTO `publication` (`pubName`, `pubTypeID`, `pubYear`) " +
                    "VALUES ('" + pubName + "', '" + pubTypeID + "', '" + year +"');";

        try {
           conn = ConnectionPool.getConnection();
           stmt = conn.createStatement();
          stmt.execute(query,Statement.RETURN_GENERATED_KEYS);
                rs = stmt.getGeneratedKeys();
                while(rs.next()){
                    pubID=rs.getInt(1);
                }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           ConnectionPool.close(conn, stmt, rs);
       }
        return pubID;
    }

    public static void changeYear()throws Exception{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query="ALTER TABLE publication MODIFY pubYear YEAR;";
        try {
            conn = ConnectionPool.getConnection();
            stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(conn, stmt, rs);
        }
    }
    
    public static ArrayList<Publication> getAllPublications()throws Exception{
        ArrayList<Publication> pList = new ArrayList<Publication>();
        Publication p = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query="SELECT * FROM `publication`;";
        try {
            conn = ConnectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
    		int pubID = rs.getInt("pubID");
    		String pubName = rs.getString("pubName");
                int pubTypeID = rs.getInt("pubTypeID");
                String year = rs.getString("pubYear");
                p = new Publication(pubID, pubName, pubTypeID, year);
                pList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(conn, stmt, rs);
        }
        
        return pList;
    }
    
    public static ArrayList<Publication> getPublications(String pubType, Date startYear)throws Exception{
        ArrayList<Publication> pList = new ArrayList<Publication>();
        Publication p = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query="select publication.`pubID`, publication.`pubName`, publication.`pubTypeID`, publication.`pubYear` from publication inner join publicationtype on publication.`pubTypeID`=publicationtype.`pubTypeID` where publicationtype.`pubTypeName`='"+pubType+"' and publication.`pubYear`='"+startYear+"';";
        try {
            conn = ConnectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
    		int pubID = rs.getInt("pubID");
    		String pubName = rs.getString("pubName");
                int pubTypeID = rs.getInt("pubTypeID");
                String year = rs.getString("pubYear");
                p = new Publication(pubID, pubName, pubTypeID, year);
                pList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(conn, stmt, rs);
        }
        
        return pList;
    }
    public static ArrayList<Publication> getPublicationsByResearcher(int pubTypeID, int rID, Date startYear)throws Exception{
        ArrayList<Publication> pList = new ArrayList<Publication>();
        Publication p = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query="select * from publication inner join collaboration on publication.`pubID`=collaboration.`pubID` where collaboration.`rID`='"+rID+"' and publication.`pubTypeID`='"+pubTypeID+"' and publication.`pubYear`='"+startYear+"';";
        try {
            conn = ConnectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
    		int pubID = rs.getInt("pubID");
    		String pubName = rs.getString("pubName");
                int pubTypeID1 = rs.getInt("pubTypeID");
                String year = rs.getString("pubYear");
                p = new Publication(pubID, pubName, pubTypeID1, year);
                pList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(conn, stmt, rs);
        }
        
        return pList;
    }
    

    public static ArrayList<Publication> getPublicationsByResearcher(int pubTypeID, int rID)throws Exception{
        ArrayList<Publication> pList = new ArrayList<Publication>();
        Publication p = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query="select * from publication inner join collaboration on publication.`pubID`=collaboration.`pubID` where collaboration.`rID`='"+rID+"' and publication.`pubTypeID`='"+pubTypeID+"';";
        try {
            conn = ConnectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
    		int pubID = rs.getInt("pubID");
    		String pubName = rs.getString("pubName");
                int pubTypeID1 = rs.getInt("pubTypeID");
                String year = rs.getString("pubYear");
                p = new Publication(pubID, pubName, pubTypeID1, year);
                pList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(conn, stmt, rs);
        }
        
        return pList;
    }
    public static int getPublicationsByResearcherCount(int rID)throws Exception{
        int count=0;
        Publication p = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query="select * from publication inner join collaboration on publication.`pubID`=collaboration.`pubID` where collaboration.`rID`='"+rID+"';";
        try {
            conn = ConnectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
    		count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(conn, stmt, rs);
        }
        
        return count;
    }
   
    public static ArrayList<String> getAllPublicationTypes()throws Exception{
        ArrayList<String> pList = new ArrayList<String>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query="select distinct pubTypeName from publicationtype";
        try {
            conn = ConnectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
    		String pubTypeName = rs.getString("pubTypeName");
                pList.add(pubTypeName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(conn, stmt, rs);
        }
        
        return pList;
    }
}
