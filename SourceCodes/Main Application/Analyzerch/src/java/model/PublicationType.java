/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author ingokarn.2011
 */
public class PublicationType {
    private int pubTypeID;
    private String pubTypeName;
    
    public PublicationType(String pubTypeName){
        this.pubTypeName=pubTypeName;
    }
    
    public PublicationType(int pubTypeID, String pubTypeName){
        this.pubTypeID=pubTypeID;
        this.pubTypeName=pubTypeName;
    }

    public int getPubTypeID() {
        return pubTypeID;
    }

    public String getPubTypeName() {
        return pubTypeName;
    }

    public void setPubTypeName(String pubTypeName) {
        this.pubTypeName = pubTypeName;
    }
    
}
