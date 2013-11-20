package controller;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import javazoom.upload.*;
import org.json.*;

/**
 * 
 * @author ingokarn.2011
 */

public class JSONBootstrapServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Over-ridden method for doGet
	 * @param request
	 * @param response
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
	}
	
	/**
	 * Over-ridden method for doPost
	 * @param request
	 * @param response
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		try {
			processBootstrap( request,response);
		}catch (JSONException e){
			e.printStackTrace();
		}
	}
	/**
	 * Method to process bootstrap
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws JSONException
	 */
	@SuppressWarnings({ "rawtypes" })
	public void processBootstrap(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JSONException{
		response.setContentType("application/json");
		try{
			MultipartFormDataRequest multiPartRequest = new MultipartFormDataRequest(
						request);
			
			Hashtable files = multiPartRequest.getFiles();
			UploadFile dataFile = (UploadFile) files.get("file");
			InputStream researcherStream = dataFile.getInpuStream();
                        InputStream publicationStream = dataFile.getInpuStream();
			JSONBootstrapService processJSONBootstrap = new JSONBootstrapService();
			try {
				processJSONBootstrap.processRequest(researcherStream, publicationStream);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JSONObject jObject = new JSONObject();
			
			JSONArray numRecordLoaded = processJSONBootstrap.getNumberOfRecordLoaded();
			
			if(numRecordLoaded.length()!=0){
				jObject.put("status", "success");
			}else{
                            JSONArray errorArray = processJSONBootstrap.getErrorArray();
                            jObject.put("status", "error");
                            jObject.put("server-response", errorArray);
                        }
			
			PrintWriter writer = response.getWriter();
			writer.println(jObject.toString(3));
                        writer.println("Click the back button on the browser to go back.");
                        
			
		}catch (UploadException e){
			e.printStackTrace();
		}
	}
	
	
}