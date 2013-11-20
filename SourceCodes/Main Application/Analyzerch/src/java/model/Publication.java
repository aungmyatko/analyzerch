/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.sql.Date;

/**
 *
 * @author ingokarn.2011
 */
public class Publication {
    private int pubID;
    private String pubName;
    private String pubYear;
    private int pubTypeID;
    
    public Publication (String pubName, int pubTypeID, String pubYear){
        this.pubName=pubName;
        this.pubTypeID=pubTypeID;
        this.pubYear=pubYear;
    }
    
    public Publication (int pubID, String pubName, int pubTypeID, String pubYear){
        this.pubID=pubID;
        this.pubName=pubName;
        this.pubTypeID=pubTypeID;
        this.pubYear=pubYear;
    }

    public int getPubID() {
        return pubID;
    }

    public String getPubName() {
        return pubName;
    }

    public String getPubYear() {
        return pubYear;
    }

    public int getPubTypeID() {
        return pubTypeID;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public void setPubYear(String pubYear) {
        this.pubYear = pubYear;
    }

    public void setPubTypeID(int pubTypeID) {
        this.pubTypeID = pubTypeID;
    }
    
}
