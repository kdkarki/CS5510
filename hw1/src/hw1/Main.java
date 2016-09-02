/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw1;

import java.text.ParseException;
import java.util.List;

/**
 *
 * @author kdkarki
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
                Film f = new Film("11/35/2014", "Movie Title1", "Movie Director", "true");
                Film f2 = new Film("12/01/2013", "Movie Title", "Movie Director", "true");
                System.out.println(f.getDirector());
                System.out.println(f.getTitle());
                System.out.println(f.getReleaseDate());
                System.out.println(f.getIsWatched());
                System.out.println("The movies are equal: " + f.equals(f2));

                Database.INSTANCE.addFilm(f2);
                System.out.println(Database.INSTANCE.getItemCount());
                Database.INSTANCE.addFilm(f);
                System.out.println(Database.INSTANCE.getItemCount());
                //Database.INSTANCE.clearDatabase();
                //System.out.println(Database.INSTANCE.getItemCount());
                
                List<Film> unwatchedFilms = Database.INSTANCE.getUnwatchedFilms();

        } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
    }
    
}
