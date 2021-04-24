package de.iitsconsulting.movies.service;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import de.iitsconsulting.movies.controller.MovieDtoResource;
import de.iitsconsulting.movies.model.Movie;
import de.iitsconsulting.movies.repo.MyMovieRepositoryImplementationImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MyMovieRepositoryImplementationImpl movieRepository;

    @Test
    public void saveMovie() {
        MovieDtoResource movieDtoResource = new MovieDtoResource();
        movieDtoResource.setTitle("test1");
        movieDtoResource.setYear(1999);
        movieDtoResource.setDirectorFirstName("TestName");
        movieDtoResource.setDirectorLastName("TestLastName");

        movieService.saveMovie(movieDtoResource.getDirectorFirstName(), movieDtoResource.getDirectorLastName(), movieDtoResource.getTitle(),
            movieDtoResource.getYear());

        List<Movie> found = movieRepository.findMoviesByTitle("test1");

        assertThat(found).hasSize(1);
        Movie actual = found.get(0);
        assertThat(actual.getYear()).isEqualTo(1999);
        assertThat(actual.getDirector().getFirstName()).isEqualTo("TestName");

    }
}