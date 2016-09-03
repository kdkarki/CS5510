package hw1.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author kdkarki
 */
public class CSV {
	
	public static List<String> LoadFile(String filePath) throws FileNotFoundException{
		List<String> inputList = new ArrayList<>();
		Scanner scn = new Scanner(new FileReader(filePath));
		//scn.useDelimiter(",");		
		while(scn.hasNextLine()){
			String inputMovie = scn.nextLine();
			inputList.add(inputMovie.replace(",", " "));
		}
		scn.close();		
		
		
		return inputList;
	}

}
