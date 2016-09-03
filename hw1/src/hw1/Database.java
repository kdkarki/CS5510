/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw1;

import hw1.util.ErrorTypes;
import hw1.util.Errors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author kdkarki
 */
public enum Database {
	
    INSTANCE;

    private final List<Movie> filmList = new ArrayList<>();

    public int getItemCount(){
            return filmList.size();
    }

    public boolean addFilm(Movie f) throws Exception{

            if(!filmList.contains(f)){
                    filmList.add(f);
            }
            else{
                    throw new Exception(Errors.INSTANCE.getErrorMessage(ErrorTypes.DuplicateEntry, f.getTitle(), f.getDirector()));
            }

            return true;
    }

    public boolean clearDatabase(){
            filmList.clear();
            return true;
    }

    public List<Movie> getUnwatchedFilms(){
            List<Movie> cloneList = (ArrayList)((ArrayList)filmList).clone();
            cloneList.removeIf(f -> f.getIsWatched());
            
            return cloneList;
    }

}
