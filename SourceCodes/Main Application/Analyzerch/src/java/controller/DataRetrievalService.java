/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Publication;
import model.PublicationDataManager;
import model.Researcher;
import model.ResearcherAreaDataManager;
import model.ResearcherDataManager;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ingokarn.2011
 */
public class DataRetrievalService extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception, ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String output = null;
        try {
            String filter = (String)request.getParameter("filter");
            if(filter.equals("school")){
                JSONArray schoolArray = new JSONArray();
                JSONObject school = null;
                ArrayList<String> schoolList = ResearcherDataManager.getSchools();
                for(int i=0; i<schoolList.size(); i++){
                    if(!schoolList.get(i).equals("")){
                        school = new JSONObject();
                        school.put("name", schoolList.get(i));
                        schoolArray.put(school);
                    }
                }
                output = schoolArray.toString();
            }else if(filter.equals("rArea")){
                JSONArray areaArray = new JSONArray();
                JSONObject area = null;
                ArrayList<String> areaList = ResearcherAreaDataManager.getResearchAreas();
                for(int i=0; i<areaList.size(); i++){
                    area = new JSONObject();
                    area.put("name", areaList.get(i));
                    areaArray.put(area);
                }
                output = areaArray.toString();
            }else if(filter.equals("publicationtype")){
                JSONArray pubArray = new JSONArray();
                JSONObject pubType = null;
                ArrayList<String> pubList = PublicationDataManager.getAllPublicationTypes();
                for(int i=0; i<pubList.size(); i++){
                    pubType = new JSONObject();
                    pubType.put("name", pubList.get(i));
                    pubArray.put(pubType);
                }
                output = pubArray.toString();
            }else if(filter.equals("researcher")){
                String schoolFilter = request.getParameter("school");
                ArrayList<Researcher> rList = null;
                if(schoolFilter.equals("")){
                    rList = ResearcherDataManager.getAllResearchers();    
                }else{
                    rList = ResearcherDataManager.getAllResearchersBySchool(schoolFilter);
                }
                List<String> newRList = new ArrayList<String>();
                for(int j=0; j<rList.size(); j++){
                    newRList.add(rList.get(j).getrName());
                }
                Collections.sort(newRList);
                JSONArray rArray = new JSONArray();
                JSONObject researcher = null;
                for(int i=0; i<newRList.size(); i++){
                    if(!newRList.get(i).equals("")){
                        researcher = new JSONObject();
                        researcher.put("name", newRList.get(i));
                        rArray.put(researcher);
                    }
                }
                output = rArray.toString();
            }else if(filter.equals("individual")){
                String nameFilter = request.getParameter("name");
                Researcher r = ResearcherDataManager.getResearcherByName(nameFilter);
                //System.out.println("getting researcher's details for about box "+nameFilter);
                JSONObject researcher = new JSONObject();
                researcher.put("photoURL", r.getPhotoURL());
                researcher.put("designation", r.getDesignation());
                researcher.put("school", r.getSchool());
                researcher.put("qualification", r.getQualification());
                researcher.put("email", r.getEmail());
                researcher.put("phoneNumber", r.getPhoneNumber());
                researcher.put("cv", r.getCv());
                output = researcher.toString();
                //System.out.println(output);
            }else if(filter.equals("publications")){
                String nameFilter = request.getParameter("name");
                String pubType = request.getParameter("pubType");
                String year = request.getParameter("startYear");
                Date stYear = null;
                if(!year.equals("All")){
                    stYear = new Date(Integer.parseInt(year)-1900,1,1);
                }
                int pubTypeID = PublicationDataManager.getTypeID(pubType);
                int rID = ResearcherDataManager.getResearcherByName(nameFilter).getrID();
                ArrayList<Publication> pList =null;
                if(stYear!=null){
                    pList = PublicationDataManager.getPublicationsByResearcher(pubTypeID, rID, stYear);
                }else{
                    pList = PublicationDataManager.getPublicationsByResearcher(pubTypeID, rID);
                }
                JSONArray pArray = new JSONArray();
                JSONObject pub = null;
                for(int h=0; h<pList.size(); h++){
                    pub = new JSONObject();
                    int pID = pList.get(h).getPubID();
                    String publication = pList.get(h).getPubName()+" with ";
                    ArrayList<Researcher> rList = ResearcherDataManager.getCollaborators(pID);
                    for(int g=0; g<rList.size(); g++){
                        if(g==0){
                            publication+=rList.get(g).getrName();
                        }else{
                            publication+=", "+rList.get(g).getrName();
                        }
                    }
                    pub.put("publication", publication);
                    pArray.put(pub);
                }
                output = pArray.toString();
            }
            out.println(output);
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            processRequest(request, response);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            processRequest(request, response);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
