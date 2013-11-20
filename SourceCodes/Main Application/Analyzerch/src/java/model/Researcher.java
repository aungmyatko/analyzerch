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
public class Researcher {
    private int rID;
    private String rName;
    private String photoURL;
    private String qualification;
    private String email;
    private String phoneNumber;
    private String cv;
    private int areaID;
    private String designation;
    private String school;
    
    
    public Researcher(String rName, String photoURL, String qualification, String email, String phoneNumber, String cv, String designation, String school, int areaID){
        this.photoURL=photoURL;
        this.rName=rName;
        this.cv=cv;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.qualification=qualification;
        this.areaID=areaID;
        this.designation=designation;
        this.school=school;
    }
    
    public Researcher(int rID,String rName, String photoURL, String qualification, String email, String phoneNumber, String cv, String designation, String school, int areaID){
        this.rID = rID;
        this.photoURL=photoURL;
        this.rName=rName;
        this.cv=cv;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.qualification=qualification;
        this.areaID=areaID;
        this.designation=designation;
        this.school=school;
    }

    public String getQualification() {
        return qualification;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCv() {
        return cv;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public int getrID() {
        return rID;
    }

    public String getrName() {
        return rName;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public int getAreaID() {
        return areaID;
    }

    public String getDesignation() {
        return designation;
    }

    public String getSchool() {
        return school;
    }

    public void setAreaID(int areaID) {
        this.areaID = areaID;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    
    
}
