/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import connection.ConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author ingokarn.2011
 */
public class ResearcherAreaDataManager {
    
    public static int getAreaByCode(String researchCode)throws Exception{
        int areaID=0;
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;  
        String query="SELECT * FROM `researcharea` WHERE `researchCode`='"+researchCode+"';";

        try {
           conn = ConnectionPool.getConnection();
           stmt = conn.createStatement();
          rs = stmt.executeQuery(query);
          while(rs.next()){
                    areaID = rs.getInt("areaID");
                    
            }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           ConnectionPool.close(conn, stmt, rs);
       }
       return areaID;
    }
    
    public static int getAreaIDByName(String areaName)throws Exception{
        int areaID=0;
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;  
        String query="SELECT * FROM `researcharea` WHERE `areaName`='"+areaName+"';";

        try {
           conn = ConnectionPool.getConnection();
           stmt = conn.createStatement();
          rs = stmt.executeQuery(query);
          while(rs.next()){
                    areaID = rs.getInt("areaID");
                    
            }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           ConnectionPool.close(conn, stmt, rs);
       }
       return areaID;
    }
    
    public static ArrayList<String> getResearchAreas()throws Exception{
        ArrayList<String> areaList = new ArrayList<String>();
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;  
        String query="SELECT * FROM `researcharea`;";

        try {
           conn = ConnectionPool.getConnection();
           stmt = conn.createStatement();
          rs = stmt.executeQuery(query);
          while(rs.next()){
                    String area = rs.getString("areaName");
                    areaList.add(area);
            }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           ConnectionPool.close(conn, stmt, rs);
       }
       return areaList;
    }
}
