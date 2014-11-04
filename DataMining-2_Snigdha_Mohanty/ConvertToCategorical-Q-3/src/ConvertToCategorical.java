import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConvertToCategorical {

	
	public static void main(String[] args){
		ArrayList<String> attributeList = readAttributesFromFile("wine.names");
		System.out.println(attributeList.size());
		
		HashSet<String> classList = readClassesFromFile("wine.data");
		
		System.out.println(classList);
		
		writeFile("1.txt",attributeList,classList,"wine.data");
	}
	
	private static void writeFile(String fileName,ArrayList<String> attributeList,HashSet<String> classList,String dataFileName){
		
		String newLine = System.getProperty("line.separator");
		FileWriter f0 = null;
		Scanner sc = null;
		try {
			f0 = new FileWriter(fileName);
			
			f0.write("@RELATION wine"+newLine);
			f0.write(newLine);
			
			for (String string : attributeList) {
				
				f0.write("@ATTRIBUTE "+string+ " REAL" + newLine);
				
			}
			
			String classStr = "{";
			
			ArrayList<String> classListTemp = new ArrayList<String>(classList);
			
			for(int i=0;i<classListTemp.size();i++){
				if(i != classList.size() -1){
					classStr += classListTemp.get(i)+ ",";
				}else{
					classStr += classListTemp.get(i);
				}
			}
			
			classStr += "}";
			
			f0.write("@ATTRIBUTE class "+classStr + newLine);
			
			f0.write(newLine);
			
			f0.write("@DATA");
			f0.write(newLine);
			
		     sc = new Scanner(new FileInputStream(dataFileName));

		      while(sc.hasNextLine()) {
		         String line = sc.nextLine();
		         
		         String linTemp = line.substring(line.indexOf(",")+1);
		         
		         String[] vals = line.split(",");
		         
		         f0.write(linTemp + "," + vals[0] + newLine);
		         
		      }
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				f0.close();
				sc.close();
			}
			catch(Exception e){
			}
		}

		
	}
	
	  private static ArrayList<String> readAttributesFromFile(String filename) {
		  
		  ArrayList<String> attributesList = new ArrayList<String>();
		  Scanner sc = null;
		  
		    try {
		    	boolean attributesSection = false;
		    	
		      sc = new Scanner(new FileInputStream(filename));

		      while(sc.hasNextLine()) {
		         String line = sc.nextLine();
		         if(line.contains("The attributes are")){
		        	 attributesSection = true;
		         }
		         
		         if(line.contains("Number of Instances")){
		        	 attributesSection = false;
		         }
		         
		         if(attributesSection){
		        	 if(line.matches("^.*[0-9]+\\)\\s?.*")){
		        		 
		        		 Pattern pattern = Pattern.compile("^(.*?[0-9]+\\)\\s?).*");
		        		 Matcher matcher = pattern.matcher(line);
		        		 if (matcher.find())
		        		 {
		        		     String num = matcher.group(1);
		        		     attributesList.add(line.substring(line.indexOf(num)+num.length()));
		        		 }		        		 
		        	 }
		         }
		         
		        }
		      
		    
		    }
		    catch(IOException e) {
		      e.printStackTrace();
		    }finally{
				try{
					sc.close();
				}
				catch(Exception e){
				}
		    }
		    return attributesList;
		  }

	  private static HashSet<String> readClassesFromFile(String filename) {
		  
		  //ArrayList<String> attributesList = new ArrayList<String>();
		  HashSet<String> set = new HashSet<String>();
		  Scanner sc = null;
		    try {
		      sc = new Scanner(new FileInputStream(filename));

		      while(sc.hasNextLine()) {
		         String line = sc.nextLine();
		         String[] vals = line.split(",");
		         set.add(vals[0]);
		         
		      }
		    }
		    catch(IOException e) {
		      e.printStackTrace();
		    }finally{
				try{
					sc.close();
				}
				catch(Exception e){
				}
		    }
		    return set;
		  }	  
	  
}
