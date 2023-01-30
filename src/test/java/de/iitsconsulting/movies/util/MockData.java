package de.iitsconsulting.movies.util;

import de.iitsconsulting.movies.model.Director;
import de.iitsconsulting.movies.model.Movie;

import java.util.Arrays;
import java.util.List;

public class MockData {

    public static List<Director> getDirectors() {
        return Arrays.asList(getDirectorCameron(), getDirectorSpielberg(), getDirectorTarantino());
    }

    public static List<Movie> getMovies() {
        return Arrays.asList(getMovieAvatar(),
            getMovieET(),
            getMovieInglourious_Basterds(),
            getMovieJurassic_Park(),
            getMoviePulp_fiction(),
            getMovieKill_bill(),
            getMovieRaiders_of_the_lost_ark(),
            getMovieTerminator(),
            getMovieTerminator_2(),
            getMovieTitanic());
    }

    private static Director getDirectorCameron() {
        return new Director(1L, "James", "Cameron", Arrays.asList());
    }

    private static Director getDirectorSpielberg() {
        return new Director(2L, "Steven", "Spielberg", Arrays.asList());
    }

    private static Director getDirectorTarantino() {
        return new Director(2L, "Quentin", "Tarantino", Arrays.asList());
    }

    private static Movie getMovieAvatar() {
        return new Movie(1L, "Avatar", 2009, getDirectorCameron());
    }

    private static Movie getMovieTerminator() {
        return new Movie(1L, "Terminator", 1984, getDirectorCameron());
    }

    private static Movie getMovieTerminator_2() {
        return new Movie(1L, "Terminator 2", 1991, getDirectorCameron());
    }

    private static Movie getMovieTitanic() {
        return new Movie(1L, "Titanic", 1997, getDirectorCameron());
    }

    private static Movie getMovieRaiders_of_the_lost_ark() {
        return new Movie(1L, "Raiders of the Lost Ark", 1981, getDirectorSpielberg());
    }

    private static Movie getMovieET() {
        return new Movie(1L, "ET", 1984, getDirectorSpielberg());
    }

    private static Movie getMovieJurassic_Park() {
        return new Movie(1L, "Jurassic Park", 1993, getDirectorSpielberg());
    }

    private static Movie getMoviePulp_fiction() {
        return new Movie(1L, "Pulp Fiction", 1994, getDirectorTarantino());
    }

    private static Movie getMovieKill_bill() {
        return new Movie(1L, "Kill Bill", 2003, getDirectorTarantino());
    }

    private static Movie getMovieInglourious_Basterds() {
        return new Movie(1L, "Inglourious Basterds", 2009, getDirectorTarantino());
    }
}
