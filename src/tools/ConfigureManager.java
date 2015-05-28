package tools;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.util.HashMap;

public class ConfigureManager {
	private static ConfigureManager instance = null;
	private HashMap<String, String> data;
	
	
	protected ConfigureManager(){
		data = new HashMap<String, String>();
		//loadFile("settings.conf");
	}
	
	public static ConfigureManager getInstance(){
		if(instance == null){
			instance = new ConfigureManager();
		}
		return instance;
	}
	
	public boolean loadFile(String fileName){
		boolean bRet = false;
		try {
			InputStream inputStream = new FileInputStream(fileName);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
			BufferedReader bufferReader = new BufferedReader(inputStreamReader);
			String line;
			String variableName;
			String value;
			while ((line = bufferReader.readLine()) != null) {
				if(line.startsWith("#")){	//comments line
					continue;
				}
				else{
					String []fields = line.split("=");
					
					if(fields.length < 2){
						System.out.println("This line does not contains '='!");
					}
					else {
						variableName = fields[0].trim();
						value = fields[1];
						if(fields.length > 2){
							for(int i = 2; i < fields.length; i++)
							{
								value = value + "=" + fields[i];
							}
						}
						value = value.trim();
						data.put(variableName, value);
					}			
				}
			}
			bufferReader.close();
			inputStreamReader.close();
			inputStream.close();
			bRet = true;
		} catch (FileNotFoundException e) {
			System.out.println(fileName + " cannot be found!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bRet;
	}

	public String getString(String variableName, String defaultValue){
		String value = data.get(variableName);
		if(value == null){
			return defaultValue;
		}
		else{
			return value;
		}
			
	}
	
	public boolean getBool(String variableName, Boolean defaultValue){
		String value = data.get(variableName);
		if(value == null){
			return defaultValue;
		}
		else{
			return value.equalsIgnoreCase("true");
		}
	}
	
	public int getInt(String variableName, int defaultValue){
		String value = data.get(variableName);
		if(value == null){
			return defaultValue;
		}
		else{
			try{
				return Integer.parseInt(value);
			}catch(NumberFormatException e){
				return defaultValue;
			}			
		}
	}
	
	public void display(){
		for (HashMap.Entry<String, String> entry : data.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    System.out.println(key + ":\t" + value);
		}
	}
}
