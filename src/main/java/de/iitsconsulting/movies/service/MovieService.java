package de.iitsconsulting.movies.service;

import de.iitsconsulting.movies.model.Director;
import de.iitsconsulting.movies.model.Movie;
import de.iitsconsulting.movies.repo.DirectorRepository;
import de.iitsconsulting.movies.repo.MovieRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    private DirectorRepository directorRepository;

    public MovieService(MovieRepository movieRepository, DirectorRepository directorRepository) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
    }

    @Transactional
    public void deleteMovie(Movie movie) {
        List<Director> allDirectors = directorRepository.findAll();
        var directorWithMovie = allDirectors.stream().filter(d -> d.getMovies().contains(movie)).findFirst();
        if (directorWithMovie.isPresent()) {
            directorWithMovie.get().getMovies().remove(movie);
        }
    }
}
