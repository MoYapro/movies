package de.iitsconsulting.movies.service;

import de.iitsconsulting.movies.controller.MovieDtoResource;
import de.iitsconsulting.movies.model.Director;
import de.iitsconsulting.movies.model.Movie;
import de.iitsconsulting.movies.repo.jpa.DirectorRepository;
import de.iitsconsulting.movies.repo.jpa.MovieRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceKotlin {

    private final MovieRepository movieRepository;
    private DirectorRepository directorRepository;

    public MovieServiceKotlin(MovieRepository movieRepository, DirectorRepository directorRepository) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
    }

    @Transactional
    public Movie saveMovie(final String directorFirstName, final String directorLastName, final String title, final int year) {

        Director savedDirector = getSavedDirector(directorFirstName, directorLastName);
        Movie movie = saveMovie(title, year, savedDirector);
        return movie;
    }

    private Movie saveMovie(String title, int year, Director savedDirector) {
        Movie movieEntity = new Movie();
        movieEntity.setDirector(savedDirector);
        movieEntity.setTitle(title);
        movieEntity.setYear(year);
        return movieRepository.save(movieEntity);
    }

    private Director getSavedDirector(String directorFirstName, String directorLastName) {
        Director director = new Director();
        director.setFirstName(directorFirstName);
        director.setLastName(directorLastName);
        Director savedDirector = directorRepository.save(director);
        return savedDirector;
    }

    public void updateMovie(MovieDtoResource theValueFromController) {
        saveMovie(theValueFromController.getDirectorFirstName(),
            theValueFromController.getDirectorLastName(),
            theValueFromController.getTitle(),
            theValueFromController.getYear());
    }

    @Transactional
    public void deleteMovie(Movie movie) {
        List<Director> allDirectors = directorRepository.findAll();
        Optional<Director> directorWithMovie = allDirectors.stream().filter(d -> d.getMovies().contains(movie)).findFirst();
        if (directorWithMovie.isPresent()) {
            directorWithMovie.get().getMovies().remove(movie);
        }
    }

    public List<Movie> findMovieByYearBetween(Integer valueOf, Integer valueOf1) {
        return null; //TODO implement me
    }

    public List<Movie> searchAllStuff(String searchString) {
        return null; //TODO implement me
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(long id) {
        return movieRepository.findById(id);
    }

    public String getMoviesByDirector(String directorName) {
        return null; //TODO implement me
    }

}
