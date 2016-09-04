package hw1.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kdkarki
 */
public class CSV {
	
	public static List<String[]> loadFile(String filePath) throws FileNotFoundException{
		List<String[]> inputList = new ArrayList<>();
		Scanner scn = new Scanner(new FileReader(filePath));
		//scn.useDelimiter(",");		
		while(scn.hasNextLine()){
			String inputLine = scn.nextLine();
                        if(inputLine.length() > 0){                            
                            inputList.add(inputLine.split(","));
                        }
		}
		scn.close();		
		
		
		return inputList;
	}
        
        public static String storeInCSVFile(List<String[]> movieList, String filePath) throws IOException{
            int moviesAdded = 0;
            FileWriter fw = null;
            try {
                fw = new FileWriter(new File(filePath));
                for(String[] movie : movieList){
                    if(movie.length < 1) continue;
                    try{
                        fw.write(String.join(",", movie) + System.lineSeparator());
                        moviesAdded++;
                    } catch(Exception ex){
                        
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                if(fw != null){
                    fw.close();
                }
            }          
            
            return "STORE: OK " + moviesAdded;
        }

}
