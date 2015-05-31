package tools;
/**
 * 
 * 
 */


import java.io.File;
import java.io.FileWriter;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author Ting Zhang
 * @since 2015-05-20
 */
public class FileManager {

	private String filepath;
	
	
	public FileManager(String path){
		filepath = path;
	}
	
	/** 
	 * function that returns the Root Element of the Xml file the user opens
	 * @throws Exception 
	 * 
	 */
	public Element Read() throws Exception {
		
			File tempfile = null;
			if(filepath != ""){
				tempfile =  new File(filepath); 			 
			}
			else{
				throw new Exception("Error reading file");
			}

			Element root = null;
			/** Make sure the user chose a valid path and file is .xml */
			if(tempfile.exists() && tempfile.getName().endsWith(".xml")){ /* Make sure the user chose a valid path and file is .xml */
		    	 SAXReader reader=new SAXReader();
				 File file=new File(tempfile.getPath());
				 Document document=reader.read(file);		
				 root=document.getRootElement();
			}
			else{
				throw new Exception("file does not exist or not a xml file");
				
			}
			return root;
		 }
		 


	/** 
	 * function that save the content of the XML file generated in the game
	 * @param xmltext has the XML string.
	 * @throws Exception 
	 */	
	public void Write(Document xmltext) throws Exception{
		
		if(xmltext==null){
			throw new Exception("the document is null!");
		} 
			
		File tempfile = new File(filepath);
		if((tempfile.getName() != "")){ 
			/* Make sure the user enters a valid name */
		    	
			//This variable filename will have access to write on the file chosen o especified by user.
			   	   
			File filename = null;
		    	
			if(!tempfile.getName().endsWith(".xml")){
		    		
				filename=new File(tempfile.getPath()+".xml");
		    		
			}else{
		    		
				filename=new File(tempfile.getPath());
			}
		    	
			//Use the Document.asXML() method somewhere?
		    	
			OutputFormat format=OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter writer=null;
			writer=new XMLWriter(new FileWriter(filename),format);
			writer.write(xmltext);
			writer.close();
//			System.out.println("Saved successfuly in: ".concat(tempfile.getPath()));
		}
		else{
			throw new Exception("error in file path!");
		}
	}
	
	

		 
 	
} 

