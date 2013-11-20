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
public class ResearchArea {
    private int areaID;
    private String areaName;
    
    public ResearchArea(String areaName){
        this.areaName=areaName;
    }

    public int getAreaID() {
        return areaID;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
       
}
