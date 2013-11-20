package controller;

import java.io.*;
import java.text.ParseException;
import java.util.zip.*;
import au.com.bytecode.opencsv.*;
import java.util.ArrayList;
import java.util.List;
import org.json.*;
import model.*;

/**
 * 
 * @author ingokarn.2011
 */
public class JSONBootstrapService {
	
	private final String researcherFile = "researchers.csv";
        private final String publicationsFile = "publications.csv";
	private JSONArray numRecordLoaded = null;
	private JSONArray errorArray = null;
        private JSONObject error = null;
        private ArrayList<Researcher> rList = null;
        private ArrayList<Publication> pList = null;
			
	/**
	 * public constructor for ProcessJSONBootstrap
	 */
	public JSONBootstrapService(){
		numRecordLoaded = new JSONArray();
                errorArray = new JSONArray();
                error = new JSONObject();
	}
	
	/**
	 * Method to process the Bootstrap request
	 * @param rStream
	 * @param pStream
	 * @param bookingStream
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParseException 
	 */
	public void processRequest(InputStream rStream, InputStream pStream) 
			throws IOException, JSONException, ParseException{
	
		ZipInputStream rZip = new ZipInputStream(rStream);
		ZipInputStream pZip = new ZipInputStream(pStream);
		ZipEntry zipEntry;
		
		CSVReader reader = null;
                
		ConnectToDatabaseService.connect();
                
		while ((zipEntry = rZip.getNextEntry())!=null){
			String entryName = zipEntry.getName();
			//System.out.println(entryName);
			reader = new CSVReader(new InputStreamReader(rZip), '|','\\','\'',1);
			if(entryName.equalsIgnoreCase(researcherFile)){
				rList = this.loadResearchersFile(reader);
                                //System.out.println(rList);
			} 
		}
		rZip.close();
		
		while ((zipEntry = pZip.getNextEntry())!=null){
			String entryName = zipEntry.getName();
			reader = new CSVReader(new InputStreamReader(pZip), '|','\\','\'',1);
			if (entryName.equalsIgnoreCase(publicationsFile)){
				pList = this.loadPublicationsFile(reader);
                                
                        } 
		}
		pZip.close();
                reader.close();
	}
	/**
	 * Method to load the researcher file with given reader
	 * @param reader
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	private ArrayList<Researcher> loadResearchersFile(CSVReader reader)throws IOException, JSONException {
		ArrayList<Researcher> rList = new ArrayList<Researcher>();
		//System.out.println("inside loadResearchersFile -- loading file now");
		int recordLoaded = 0;
					
		String[] columns;
		//reader.readNext();
		//System.out.println("skipped headings");
		while((columns = reader.readNext())!=null ){
			
			String rName = columns[0];
			String photoURL = columns[1];
			String cv = columns[2];
			String designation = columns[3];
			String qualification = columns[4];
			String school = columns[5];
			String email = columns[6];
                        String phoneNumber = columns[7];
			String researchAreaCode = columns[8];
			
			
                        String trimmedName = rName.trim();
			String trimmedPhotoURL = photoURL.trim();
			String trimmedCV = cv.trim();
			String trimmedDesignation = designation.trim();
			String trimmedQualification = qualification.trim();
			String trimmedSchool = school.trim();
			String trimmedEmail = email.trim();
                        String trimmedPhoneNumber = phoneNumber.trim();
			String trimmedResearchAreaCode = researchAreaCode.trim();
                        
                        /*System.out.println(trimmedName);
                        System.out.println(trimmedPhotoURL);
                        System.out.println(trimmedCV);
                        System.out.println(trimmedDesignation);
                        System.out.println(trimmedQualification);
                        System.out.println(trimmedSchool);
                        System.out.println(trimmedEmail);
                        System.out.println(trimmedPhoneNumber);
                        System.out.println(trimmedResearchAreaCode);
                        
			*/
			try{
                            int areaID = ResearcherAreaDataManager.getAreaByCode(trimmedResearchAreaCode);
                            if(ResearcherDataManager.checkIfExists(trimmedName)==0){
                                 int i = ResearcherDataManager.createResearcher(trimmedName, trimmedPhotoURL, trimmedQualification, trimmedEmail, trimmedPhoneNumber, trimmedCV, trimmedDesignation, areaID, trimmedSchool);
                                recordLoaded++;
                            }
                            }catch(Exception e){
                            e.printStackTrace();
                            recordLoaded--;
                            break;
                        }
                        
		}
		this.addSuccessLoadedArray(researcherFile, recordLoaded);
                try{
                    rList = ResearcherDataManager.getAllResearchers();
                }catch(Exception w){
                    w.printStackTrace();
                }
                return rList;
	}
	
        private ArrayList<Publication> loadPublicationsFile(CSVReader reader)throws IOException, JSONException {
		ArrayList<Publication> pList = new ArrayList<Publication>();
		//System.out.println("inside loadPublicationsFile -- loading file now");
		int recordLoaded = 0;
					
		String[] columns;
		//reader.readNext();
		//System.out.println("skipped headings");
                
                List<String[]> publications = reader.readAll();
                for(int p =0; p<publications.size(); p++){
                    columns = publications.get(p);
                    String pubName = columns[0];
                    String pubType = columns[1];
                    String collaborators = columns[2];
                    String year = columns[3];

                    String trimmedPubName = pubName.trim();
                    String trimmedPubType = pubType.trim();
                    String trimmedCollaborators = collaborators.trim();
                    String trimmedYear = year.trim();
                    int pubTypeID=0;
                    int pubID=0;
                    try{
                        pubTypeID = PublicationDataManager.getTypeID(trimmedPubType);
                    }catch(Exception e){
                        System.out.println("exception while getting pubtype");
                        e.printStackTrace();
                    }
                    System.out.println("pubTypeID"+pubTypeID);
                    try{
                        System.out.println(trimmedPubName);
                        System.out.println(trimmedYear);
                        pubID = PublicationDataManager.createPublication(trimmedPubName, pubTypeID, trimmedYear);
                    }catch(Exception e){
                        System.out.println("exception while adding new publication");
                        e.printStackTrace();
                    }try{
                        System.out.println("pubName"+trimmedPubName+" "+trimmedYear);
                        String cList[]=trimmedCollaborators.split(",");
                        for(int i=0; i<cList.length; i++){
                            if(ResearcherDataManager.checkIfExists(cList[i])!=0) {
                                int rID = ResearcherDataManager.getResearcherByName(cList[i]).getrID();
                                if(rID!=0){
                                    ResearcherDataManager.addCollboration(rID,pubID);
                                }
                            }else{
                                int rID = ResearcherDataManager.createResearcher(cList[i], "", "", "", "", "", "", 59, "");
                                ResearcherDataManager.addCollboration(rID,pubID);
                            }

                        }

                        recordLoaded++; 
                    }catch(Exception e){
                        e.printStackTrace();
                       }
                }
                
		
		/*while((columns = reader.readNext())!=null ){
			
			String pubName = columns[0];
			String pubType = columns[1];
			String collaborators = columns[2];
			String year = columns[3];
			
			String trimmedPubName = pubName.trim();
			String trimmedPubType = pubType.trim();
			String trimmedCollaborators = collaborators.trim();
			String trimmedYear = year.trim();
						
                        try{
                            int pubTypeID = PublicationDataManager.getTypeID(trimmedPubType);
                            System.out.println("pubTypeID"+pubTypeID);
                            int pubID = PublicationDataManager.createPublication(trimmedPubName, pubTypeID, trimmedYear);
                            System.out.println("pubName"+trimmedPubName+" "+trimmedYear);
                            String cList[]=trimmedCollaborators.split(",");
                            for(int i=0; i<cList.length; i++){
                                int returnedID = ResearcherDataManager.checkIfExists(cList[i]);
                                if(returnedID!=0){
                                    int rID = ResearcherDataManager.getResearcherByName(cList[i]).getrID();
                                    if(rID!=0){
                                        ResearcherDataManager.addCollboration(rID,pubID);
                                    }
                                }else{
                                    int rID = ResearcherDataManager.createResearcher(cList[i], "", "", "", "", "", "", 59, "");
                                    ResearcherDataManager.addCollboration(rID,pubID);
                                }
                                
                            }
                            
                            recordLoaded++;
                        }catch(Exception e){
                            recordLoaded--;
                            e.printStackTrace();
                            break;
                        }
                        
		}*/
                try{
                    PublicationDataManager.changeYear();
                    pList = PublicationDataManager.getAllPublications();
                }catch(Exception e){
                    e.printStackTrace();
                }
		this.addSuccessLoadedArray(publicationsFile, recordLoaded);
                return pList;
	}
	/**
	 * Method to add success message into a JSONArray
	 * @param fileName
	 * @param recordLoaded
	 * @throws JSONException
	 */
	private void addSuccessLoadedArray(String fileName, int recordLoaded) 
			throws JSONException {
		JSONObject successLoad = new JSONObject();
		successLoad.put("file", fileName);
		successLoad.put("numberOfRecords", recordLoaded);
		this.numRecordLoaded.put(successLoad);
	}
	
	
	/**
	 * getter method for NumRecordLoaded
	 * @return numRecordLoaded
	 * @throws JSONException
	 */
	public JSONArray getNumberOfRecordLoaded()throws JSONException {
		return this.numRecordLoaded;
	}
	public JSONArray getErrorArray()throws JSONException {
		return this.errorArray;
	}
}