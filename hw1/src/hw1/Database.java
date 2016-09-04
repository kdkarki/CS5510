package hw1;

import hw1.util.ErrorTypes;
import hw1.util.Errors;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author kdkarki
 */
public enum Database {
	
    INSTANCE;

    private final List<Movie> movieList = new ArrayList<>();

    public int getItemCount(){
            return movieList.size();
    }
    
    private Movie getMovieByTitleDirector(String title, String director){
    	for(Movie f: movieList){
    		if(f.getTitle().equals(title) && f.getDirector().equals(director))
    			return f;
    	}
    	
    	return null;
    }

    public boolean addMovie(Movie f) throws Exception{

            if(!movieList.contains(f)){
                    movieList.add(f);
                    return true;
            }
            else{
                    throw new Exception(Errors.INSTANCE.getErrorMessage(ErrorTypes.DUPLICATE_ENTRY, "ADD"));
            }
    }
    
    public boolean updateMovieWatchedStatus(String title, String director, boolean watchedState) throws Exception{
    	
    	Movie filmToUpdate = getMovieByTitleDirector(title, director);
    	if(filmToUpdate != null){
    		filmToUpdate.setIsWatched(watchedState);
    		return true;
    	}
    	else{
    		throw new Exception(Errors.INSTANCE.getErrorMessage(ErrorTypes.FILM_NOT_FOUND, "UPDATE"));
    	}
    }
    
    public boolean updateMovieReleaseDate(String title, String director, LocalDate releaseDate) throws Exception{
    	
    	Movie filmToUpdate = getMovieByTitleDirector(title, director);
    	if(filmToUpdate != null){
    		filmToUpdate.setReleaseDate(releaseDate);
    		return true;
    	}
    	else{
    		throw new Exception(Errors.INSTANCE.getErrorMessage(ErrorTypes.FILM_NOT_FOUND, "UPDATE"));
    	}
    }
    
    public boolean updateMovieTitleAndDirector(String oldTitle, String oldDirector, String newTitle, String newDirector) throws Exception{
    	
    	if(getMovieByTitleDirector(newTitle, newDirector) != null){
    		throw new Exception(Errors.INSTANCE.getErrorMessage(ErrorTypes.DUPLICATE_ENTRY, "UPDATE"));
    	}
    	Movie filmToUpdate = getMovieByTitleDirector(oldTitle, oldDirector);
    	if(filmToUpdate != null){
    		filmToUpdate.setTitle(newTitle);
    		filmToUpdate.setDirector(newDirector);
    		return true;
    	}
    	else{
    		throw new Exception(Errors.INSTANCE.getErrorMessage(ErrorTypes.FILM_NOT_FOUND, "UPDATE"));
    	}
    }

    public boolean clearDatabase(){
            movieList.clear();
            return true;
    }

    public List<Movie> getUnwatchedMovies(){
            @SuppressWarnings("unchecked")
			List<Movie> cloneList = (ArrayList<Movie>)((ArrayList<Movie>)movieList).clone();
            cloneList.removeIf(f -> f.getIsWatched());
            
            return cloneList;
    }
    
    public List<Movie> searchTitleDirector(String searchText){
    	
    	@SuppressWarnings("unchecked")
		List<Movie> cloneList = (ArrayList<Movie>)((ArrayList<Movie>)movieList).clone();
        cloneList.removeIf(f -> !f.getTitle().contains(searchText) && !f.getDirector().contains(searchText));
        
        return cloneList;
    	
    	/*StringBuilder sbSearchResult = new StringBuilder();
    	for(Movie f : movieList){
    		if(f.getTitle().contains(searchText) || f.getDirector().contains(searchText)){
    			if(sbSearchResult.length() > 0) sbSearchResult.append(System.lineSeparator());
    			sbSearchResult.append(f.getTitle() + "," + f.getDirector() + "," 
    									+ f.getReleaseDate() + "," + f.getIsWatched());
    		}
    	}
    	return sbSearchResult;*/
    	
    }
    
    public List<String[]> getAllMovies(){
        List<String[]> allMoviesList = new ArrayList<>();
        for(Movie m : movieList){
            allMoviesList.add(new String[]{m.getReleaseDate().format(DateTimeFormatter.ofPattern("M/d/yyyy", Locale.ENGLISH)),
                             m.getTitle(), m.getDirector(), String.valueOf(m.getIsWatched())});
        }
        return allMoviesList;
    }

}