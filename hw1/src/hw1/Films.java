package hw1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hw1.util.CSV;
import hw1.util.ErrorTypes;
import hw1.util.Errors;

/**
 *
 * @author kdkarki
 */

public class Films {

	/**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
       
       
       try {
    	   String inputCmd = new String(Files.readAllBytes(Paths.get("in.txt")));
    	   String[] cmdList = inputCmd.split(System.lineSeparator());
    	   if(cmdList.length > 0){
    		   for(String cmd : cmdList){
    			   try {
					System.out.println(executeCommand(cmd));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
    		   }
    	   }
		   
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   public static String executeCommand(String inputString) throws Exception{
	   String successMessage = "";
	   List<String> cmdTokens = new ArrayList<>();
	   Matcher matcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(inputString);
	   while(matcher.find()){
		   cmdTokens.add(matcher.group(1).replace("\"", ""));
	   }
	   
	   if(cmdTokens.size() > 0){
		   
		   switch(cmdTokens.get(0)){
		   
		   case "ADD":
			   if(cmdTokens.size() != 5)
				   throw new Exception(Errors.INSTANCE.getErrorMessage(ErrorTypes.WRONG_ARGUMENT_COUNT, "ADD"));
			   
			   try{
				   Movie m = new Movie(cmdTokens.get(3), cmdTokens.get(1), cmdTokens.get(2), cmdTokens.get(4));
				   if(Database.INSTANCE.addMovie(m)){
					   successMessage = "ADD: OK " + m.getTitle() + " " + m.getDirector();
				   }
			   } catch(DateTimeParseException dtEx){
				   successMessage = Errors.INSTANCE.getErrorMessage(ErrorTypes.INVALID_DATE, "ADD");
			   }
			   break;
		   case "CLEAR":
			   if(cmdTokens.size() != 1)
				   throw new Exception(Errors.INSTANCE.getErrorMessage(ErrorTypes.WRONG_ARGUMENT_COUNT, "CLEAR"));
			   if(Database.INSTANCE.clearDatabase()){
				   successMessage = "CLEAR: OK";
			   }
			   break;
		   case "LOAD":
			   if(cmdTokens.size() != 2)
				   throw new Exception(Errors.INSTANCE.getErrorMessage(ErrorTypes.WRONG_ARGUMENT_COUNT, "LOAD"));
			   List<String[]> movieList = CSV.loadFile(cmdTokens.get(1));
                           int ldMovieAdded = 0;
			   if(movieList != null && movieList.size() > 0){
                               Database.INSTANCE.clearDatabase();
                               for(String[] movie : movieList){
                                   if(movie.length < 4)
                                       System.out.println(Errors.INSTANCE.getErrorMessage(ErrorTypes.WRONG_ARGUMENT_COUNT, "LOAD"));
                                   try{
                                       Movie ldMovie = new Movie(movie[0], movie[1], movie[2], movie[3]);
                                       if(Database.INSTANCE.addMovie(ldMovie)){
                                           ldMovieAdded++;
                                       }
                                       
                                   }catch (DateTimeParseException dEx){
                                       System.out.println(Errors.INSTANCE.getErrorMessage(ErrorTypes.INVALID_DATE, "LOAD"));
                                   }
                                   catch (Exception ex){
                                       System.out.println(ex.getMessage().replace("ADD", "LOAD"));
                                   }
                               }
			   }
                           
                           successMessage = "LOAD: OK " + ldMovieAdded;
                           
			   break;
		   case "SEARCH":
			   if(cmdTokens.size() != 2)
				   throw new Exception(Errors.INSTANCE.getErrorMessage(ErrorTypes.WRONG_ARGUMENT_COUNT, "SEARCH"));
			   List<Movie> searchResult = Database.INSTANCE.searchTitleDirector(cmdTokens.get(1));
			   if(searchResult != null && searchResult.size() > 0){
				   StringBuilder sbSearchResult = new StringBuilder();
				   sbSearchResult.append("SEARCH: OK ").append(searchResult.size());				   
				   for(Movie f : searchResult){
					   sbSearchResult.append(System.lineSeparator()).append(f.getTitle()).append(",").append(f.getDirector()).append(",").append(f.getReleaseDate().format(DateTimeFormatter.ofPattern("M/d/yyyy", Locale.ENGLISH))).append(",")
					   				 .append(f.getIsWatched());			    		
				   }
				   successMessage = sbSearchResult.toString();
			   }
			   else
				   successMessage = "SEARCH: FAIL " + cmdTokens.get(1);
			   break;
		   case "STORE":
			   if(cmdTokens.size() != 2)
				   throw new Exception(Errors.INSTANCE.getErrorMessage(ErrorTypes.WRONG_ARGUMENT_COUNT, "SEARCH"));
                           successMessage = CSV.storeInCSVFile(Database.INSTANCE.getAllMovies(), cmdTokens.get(1));
			   break;
		   case "SHOW":
			   if(cmdTokens.size() != 1)
				   throw new Exception(Errors.INSTANCE.getErrorMessage(ErrorTypes.WRONG_ARGUMENT_COUNT, "SHOW"));
			   List<Movie> uMovies = Database.INSTANCE.getUnwatchedMovies();
			   if(uMovies != null && uMovies.size() > 0){
				   StringBuilder sbMovies = new StringBuilder();
				   sbMovies.append("SHOW: OK " + uMovies.size());
				   for(Movie movie : uMovies){
					   sbMovies.append(System.lineSeparator()).append(movie.getTitle() + "," 
							   											+ movie.getDirector() + ","
							   											+ movie.getReleaseDate().format(DateTimeFormatter.ofPattern("M/d/yyyy", Locale.ENGLISH)));
				   }
				   successMessage = sbMovies.toString();
			   }
			   else{
				   successMessage = "SHOW: OK 0";
			   }
			   break;
		   case "UPDATE":
			   if(cmdTokens.size() != 4 && cmdTokens.size() != 5)
				   throw new Exception(Errors.INSTANCE.getErrorMessage(ErrorTypes.WRONG_ARGUMENT_COUNT, "UPDATE"));
			   //if there are 4 arguments then it should be for title and director
			   if(cmdTokens.size() == 5){
				   //the argument order is: OldTime OldDirector NewTitle NewDirector
				   if(Database.INSTANCE.updateMovieTitleAndDirector(cmdTokens.get(1), cmdTokens.get(2), cmdTokens.get(3), cmdTokens.get(4))){
					   successMessage = "UPDATE: OK" + cmdTokens.get(3) + " " + cmdTokens.get(4);
				   }
			   }
			   else if (cmdTokens.size() == 4){
				   //This could be either update watchedstate or release date
				   if("true".equals(cmdTokens.get(3)) || "false".equals(cmdTokens.get(3))){
					   if(Database.INSTANCE.updateMovieWatchedStatus(cmdTokens.get(1), cmdTokens.get(2), Boolean.parseBoolean(cmdTokens.get(3)))){
						   successMessage = "UPDATE: OK " + cmdTokens.get(1) + " " + cmdTokens.get(2);
					   }
				   } else {
					   DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("M/d/yyyy", Locale.ENGLISH);
					   try{
						   LocalDate releaseDate = LocalDate.parse(cmdTokens.get(3), dtFormatter);
						   if(Database.INSTANCE.updateMovieReleaseDate(cmdTokens.get(1), cmdTokens.get(2), releaseDate)){
							   successMessage = "UPDATE: OK " + cmdTokens.get(1) + " " + cmdTokens.get(2);
						   }
					   } catch(Exception ex){
						   successMessage = Errors.INSTANCE.getErrorMessage(ErrorTypes.INVALID_DATE_OR_BOOL, "UPDATE");
					   }
				   }
			   }
			   break;
			   default:
				   throw new Exception(Errors.INSTANCE.getErrorMessage(ErrorTypes.UNKNOWN_COMMAND, cmdTokens.get(0)));
		   }
	   }
	   
	   return successMessage;
		   
   }

}
