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
import model.Researcher;
import model.ResearcherDataManager;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ingokarn.2011
 */
public class NetworkBuilderServlet extends HttpServlet{
    
    private ArrayList<Researcher> nodesList = null;
    private ArrayList<int[]> edgesList = null;
    private JSONArray eList = null;
    private JSONArray nList = null;
    private JSONObject edge = null;
    private JSONObject node = null;
    private JSONObject graph = null;

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
            throws Exception, ServletException, IOException  {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            nodesList = new ArrayList<Researcher>();
            edgesList = new ArrayList<int[]>();
            eList = new JSONArray();
            nList = new JSONArray();
            graph = new JSONObject();
            
            
           String school = (String) request.getParameter("school");
           String rArea = (String) request.getParameter("rArea");
           String pubType = (String) request.getParameter("pubType");
           String rName = (String) request.getParameter("rName");
           String sYear = (String) request.getParameter("startYear");
           //String eYear = (String) request.getParameter("endYear");
           Date startYear=null;
           if(!sYear.equals("All")){
               startYear = new Date(Integer.parseInt(sYear)-1900,1,1);
           }
           
           //Date endYear = new Date(Integer.parseInt(eYear)-1900,1,1);
          // System.out.println("school:"+school+"endl");
          // System.out.println("rArea:"+rArea+"endl");
           //System.out.println("pubType:"+pubType+"endl");
           //System.out.println("rName:"+rName+"endl");
           //System.out.println("startYear:"+startYear+"endl");
           //System.out.println("endYear:"+endYear+"endl");
           ArrayList<Publication> pubList = null;
           int pubTypeID = PublicationDataManager.getTypeID(pubType);
            if(rName.equals("")){
                pubList = PublicationDataManager.getPublications(pubType, startYear);
               // System.out.println(pubList);
            }else{
                int rID = ResearcherDataManager.getResearcherByName(rName).getrID();
                if(startYear!=null){
                    pubList = PublicationDataManager.getPublicationsByResearcher(pubTypeID, rID, startYear);
                }else{
                    pubList = PublicationDataManager.getPublicationsByResearcher(pubTypeID, rID);
                }
                
               //System.out.println(pubList);
            }
            
            for (int i=0; i<pubList.size(); i++){
                /*System.out.println("start of new loop");
               // System.out.println("nodesList till now");
                for(int r=0; r<nodesList.size(); r++){
                   // System.out.println(nodesList.get(r).getrName());
                }
              // System.out.println("edgesList till now");
                for(int z=0; z<edgesList.size(); z++){
                    for(int h=0; h<3; h++){
                      //  System.out.println(edgesList.get(z)[h]);
                    }
                }*/
                ArrayList<Researcher> rList = ResearcherDataManager.getCollaborators(pubList.get(i).getPubID(),school, rArea);
                int[] indexArray = new int[rList.size()];
                for (int j=0; j<rList.size(); j++){
                    boolean found = false;
                    for(int k=0; k<nodesList.size(); k++){
                        if(rList.get(j).getrName().equals(nodesList.get(k).getrName())){
                          // System.out.println("nodesList index found: "+k);
                            indexArray[j]=k;
                            found=true;
                        }
                    }
                    if(!found){
                        nodesList.add(rList.get(j));
                        indexArray[j]=nodesList.indexOf(rList.get(j));
                       // System.out.println("nodeList node created: "+rList.get(j));
                    }
                }
                for(int m=0; m<rList.size()-1; m++){
                    int source = indexArray[m];
                    for(int n=m+1; n<rList.size(); n++){
                        int target = indexArray[n];
                       // System.out.println("m: "+source);
                       // System.out.println("n: "+target);
                       // System.out.println("source: "+source);
                       // System.out.println("target: "+target);
                        if(source!=target){
                           // System.out.println("Edges list is empty? "+edgesList.isEmpty());
                            if(edgesList.isEmpty()){
                                int[] newLink = new int[3];
                                    newLink[0]=source;
                                    newLink[1]=target;
                                    newLink[2]=1;
                                  //  System.out.println("added new edge");
                                    edgesList.add(newLink);
                            }else{
                                boolean edgeFound = false;
                                int edgeIndex = 0;
                                for (int p=0; p<edgesList.size(); p++){
                                    int[] link = edgesList.get(p); 
                                  //  System.out.println("From edgesList: "+link[0]+" "+link[1]+" "+link[2]);
                                  //  System.out.println("Source: "+source+" and target: "+target);
                                    if(link[0]==source&&link[1]==target&&!edgeFound){
                                       // System.out.println("previous edge found");
                                        edgeIndex=p;
                                        edgeFound=true;
                                    }else if(link[0]==target&&link[1]==source&&!edgeFound){
                                       // System.out.println("previous edge found");
                                        edgeIndex=p;
                                        edgeFound=true;
                                    }
                                }
                                if(edgeFound){
                                    int[] existingLink = edgesList.get(edgeIndex);
                                    existingLink[2]=existingLink[2]+1;
                                  //  System.out.println("value++");
                                } else{
                                    int[] newLink = new int[3];
                                    newLink[0]=source;
                                    newLink[1]=target;
                                    newLink[2]=1;
                                  //  System.out.println(" not found in edgesList, added new edge");
                                    edgesList.add(newLink);
                                }
                            }
                        }
                    }
                }
            }
           /* System.out.println("final edges list");
            for(int s=0; s<edgesList.size(); s++){
                int[] link1 = edgesList.get(s);
               // System.out.println("From edgesList: "+link1[0]+" "+link1[1]+" "+link1[2]);
            }*/
            for(int q=0; q<edgesList.size(); q++){
                int[] link = edgesList.get(q);
                edge = new JSONObject();
                edge.put("source", link[0]);
                edge.put("target", link[1]);
                edge.put("value", link[2]);
                eList.put(edge);
            }
            for(int o=0; o<nodesList.size(); o++){
                node = new JSONObject();
                node.put("name", nodesList.get(o).getrName());
                node.put("size", PublicationDataManager.getPublicationsByResearcherCount(nodesList.get(o).getrID()));
                if(nodesList.get(o).getSchool().contains("Information")){
                    node.put("color", "#C69200");
                }else if(nodesList.get(o).getSchool().contains("Economics")){
                    node.put("color", "#006F71");
                } else if(nodesList.get(o).getSchool().contains("Business")){
                    node.put("color", "#007BC6");
                } else if(nodesList.get(o).getSchool().contains("Social")){
                    node.put("color", "#008442");
                } else if(nodesList.get(o).getSchool().contains("Accountancy")){
                    node.put("color", "#C1004E");
                } else if(nodesList.get(o).getSchool().contains("Law")){
                    node.put("color", "#4E009B");
                } else{
                    node.put("color", "#FFA500");
                }
                nList.put(node);
            }
            if(nList.length()==0&&eList.length()==0){
                graph.put("nodes", "empty");
                graph.put("links", "empty");
            }else{
                graph.put("nodes", nList);
                graph.put("links", eList);
            }
            String graphString = graph.toString(3);
            //System.out.println(graphString);
            out.println(graphString);
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
        return "Builds the network graph";
    }// </editor-fold>

}
