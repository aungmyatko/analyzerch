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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Publication;
import model.PublicationDataManager;
import model.ResearcherDataManager;

/**
 *
 * @author ingokarn.2011
 */
public class ResearchTrendsService extends HttpServlet {

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
        response.setContentType("text/csv");
        PrintWriter out = response.getWriter();
        try {
            //System.out.println("inside trends servlet");
            String name = (String) request.getParameter("name");
            String output = "Name,Series ID";
            if(name.equals("all")){
                ArrayList<String> pubTypeList = PublicationDataManager.getAllPublicationTypes();
                for(int i=2000; i<=2013; i++){
                    output+=","+i;
                }
                output+="\n";
                for(int i=0; i<pubTypeList.size(); i++){
                    String pubType= pubTypeList.get(i);
                    output+=pubType+","+i;
                    for(int k=2000;k<=2013; k++){
                        ArrayList<Publication> pList = PublicationDataManager.getPublications(pubType, new Date(k-1900,1,1));
                        int count = pList.size();
                        output+=","+count;
                    }
                    output+="\n";
                }
                //System.out.println(output);
            }else{
                int rID = ResearcherDataManager.getResearcherByName(name).getrID();
                ArrayList<String> pubTypeList = PublicationDataManager.getAllPublicationTypes();
                for(int i=2000; i<=2013; i++){
                    output+=","+i;
                }
                output+="\n";
                for(int i=0; i<pubTypeList.size(); i++){
                    String pubType= pubTypeList.get(i);
                    output+=pubType+","+i;
                    int pubTypeID = PublicationDataManager.getTypeID(pubType);
                    for(int k=2000;k<=2013; k++){
                        ArrayList<Publication> pList = PublicationDataManager.getPublicationsByResearcher(pubTypeID, rID, new Date(k-1900,1,1));
                        int count = pList.size();
                        output+=","+count;
                    }
                    output+="\n";
                }
            }
            //System.out.println(output);
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
