package de.iitsconsulting.movies.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import de.iitsconsulting.movies.controller.MovieDtoResource;
import de.iitsconsulting.movies.model.Director;
import de.iitsconsulting.movies.model.Movie;
import de.iitsconsulting.movies.repo.MyMovieRepositoryImplementationImpl;
import de.iitsconsulting.movies.repo.jpa.DirectorRepository;

public class MovieService {

    private MyMovieRepositoryImplementationImpl movieRepository;

    private DirectorRepository directorRepository;

    public MovieService(MyMovieRepositoryImplementationImpl movieRepository, DirectorRepository directorRepository) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
    }

    @Transactional
    public void saveMovie( final String directorFirstName, final String directorLastName, final String title,  final int year) {
        Movie movieEntity = new Movie();

        Director director = new Director();
        director.setFirstName(directorFirstName);
        director.setLastName(directorLastName);

        Director savedDirector = directorRepository.save(director);
        movieEntity.setDirector(savedDirector);
        movieEntity.setTitle(title);
        movieEntity.setYear(year);
        movieRepository.save(movieEntity);
    }

    public void updateMovie(MovieDtoResource theValueFromController) {
        saveMovie(theValueFromController.getDirectorFirstName(), theValueFromController.getDirectorLastName(),
            theValueFromController.getTitle(), theValueFromController.getYear());
    }

    @Transactional
    public void deleteMovie(Movie movie) {
        List<Director> allDirectors = directorRepository.findAll();
        Optional<Director> directorWithMovie = allDirectors.stream().filter(d -> d.getMovies().contains(movie)).findFirst();
        if (directorWithMovie.isPresent()) {
            directorWithMovie.get().getMovies().remove(movie);
        }
    }

//    public void playMovie(Movie movie) {
//        //Has to be implemented
//        String title = movie.getTitle();
//    }

    // Maybe needed in future
    private Movie createNewMovie(String title, String year, long id, String name1, String name2) {
        Movie movie = new Movie();
        // movie.setTitle(title);
        return movie;
    }
}
