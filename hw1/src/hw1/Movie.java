/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw1;

import hw1.util.ErrorTypes;
import hw1.util.Errors;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 *
 * @author kdkarki
 */
public final class Movie {
	
	/*
	 * fields
	 */
	//
	private LocalDate releaseDate;
	private String title, director;
	private boolean isWatched;
	
	/*
	 * Accessors and Modifiers for the fields
	 */
	public LocalDate getReleaseDate(){
		return releaseDate;
	}
	public void setReleaseDate(LocalDate rDate){
		releaseDate =rDate;
	}
	public void setReleaseDate(String rDateString) throws ParseException{
		DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("M/d/yyyy", Locale.ENGLISH);
		releaseDate = LocalDate.parse(rDateString, dtFormatter);
	}
	
	public String getTitle(){
		return title;
	}
	public void setTitle(String filmName){
		if(filmName == null || "".equals(filmName)) 
			throw new InvalidParameterException(Errors.INSTANCE.getErrorMessage(ErrorTypes.EmptyArgument, "Film name", ""));
		if(filmName.contains(","))
			throw new InvalidParameterException(Errors.INSTANCE.getErrorMessage(ErrorTypes.InvalidParameter, ",", "Film name"));
		if(filmName.contains("\""))
			throw new InvalidParameterException(Errors.INSTANCE.getErrorMessage(ErrorTypes.InvalidParameter, "\"", "Film name"));
		title = filmName;
	}
	
	public String getDirector(){
		return director;
	}
	public void setDirector(String directorName) throws InvalidParameterException{
		if(directorName == null || "".equals(directorName)) 
			throw new InvalidParameterException(Errors.INSTANCE.getErrorMessage(ErrorTypes.EmptyArgument, "Director name", ""));
		if(directorName.contains(","))
			throw new InvalidParameterException(Errors.INSTANCE.getErrorMessage(ErrorTypes.InvalidParameter, ",", "Director name"));
		if(directorName.contains("\""))
			throw new InvalidParameterException(Errors.INSTANCE.getErrorMessage(ErrorTypes.InvalidParameter, "\"", "Director name"));
		director = directorName;
	}
	
	public boolean getIsWatched(){
		return isWatched;
	}
	public void setIsWatched(boolean watched){
		isWatched = watched;
	}
	public void setIsWatched(String watched) throws InvalidParameterException{
		if(watched == null || "".equals(watched) 
                        || (!watched.equals("true") && !watched.equals("false"))){
			throw new InvalidParameterException(Errors.INSTANCE.getErrorMessage(ErrorTypes.NotBool, watched, ""));
		}
		isWatched = Boolean.parseBoolean(watched);
	}
	
	
	/*
	 * Default constructor
	 */
	public Movie(){
		
	}
	
	/*
	 * Parameterized constructor
	 */
	public Movie(String rDate, String fTitle, String fDirector, String watched) throws ParseException{
		setReleaseDate(rDate);
		setTitle(fTitle);
		setDirector(fDirector);
		setIsWatched(watched);
	}
	
        @Override
	public boolean equals(Object otherFilm){
		
		if(this == otherFilm) return true;
		if(otherFilm == null) return false;
		if(this.getClass() != otherFilm.getClass()) return false;
		
		Movie oFilm = (Movie)otherFilm;
		return (this.getTitle().equals(oFilm.getTitle()) && this.getDirector().equals(oFilm.getDirector()));
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.releaseDate);
        hash = 67 * hash + Objects.hashCode(this.title);
        hash = 67 * hash + Objects.hashCode(this.director);
        hash = 67 * hash + (this.isWatched ? 1 : 0);
        return hash;
    }

}
