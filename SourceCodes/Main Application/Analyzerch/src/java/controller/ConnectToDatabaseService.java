/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import connection.ConnectionPool;

/**
 *
 * @author ingokarn.2011
 */
public class ConnectToDatabaseService {
    public static void connect(){
        ConnectionPool cp = new ConnectionPool();
            try{
                cp.setUp();
            }catch(Exception e){
                e.printStackTrace();
            }
    }
}
