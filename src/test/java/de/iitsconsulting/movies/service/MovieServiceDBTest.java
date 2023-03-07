package de.iitsconsulting.movies.service;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import de.iitsconsulting.movies.MoviesApplication;
import de.iitsconsulting.movies.controller.MovieDtoResource;
import de.iitsconsulting.movies.model.Movie;
import de.iitsconsulting.movies.repo.jpa.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = MoviesApplication.class)
@ActiveProfiles("db-test")
@ExtendWith(MockitoExtension.class)
class MovieServiceDBTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService sut;

    @Test
    public void findMovieByYearBetween() {
        List<Movie> result = sut.findMovieByYearBetween(2000, 2010);
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.stream().map(Movie::getTitle))
            .containsExactlyInAnyOrder("Inglorious Bastards", "Avatar", "Kill Bill");
    }

    @Test
    public void findMovieByYearBetween_zero() {
        List<Movie> result = sut.findMovieByYearBetween(0, 3000);
        assertThat(result).hasSize(10); // all movies
    }

    @Test
    public void findMovieByYearBetween_wrongOrder() {
        assertThrows(IllegalArgumentException.class, () -> sut.findMovieByYearBetween(2010, 1990));
    }

    @Test
    public void findMovieByYearBetween_negative() {
        assertThrows(IllegalArgumentException.class, () -> sut.findMovieByYearBetween(-1564, -999));
    }

    @Test
    public void getMoviesByDirector() {
        String result = sut.getMoviesByDirector("Spielberg");
        assertEquals("[\"ET - Spielberg\",\"Jurassic Park - Spielberg\",\"Titanic - Spielberg\"]", result);
    }

    @Test
    public void getMoviesByDirector_notFound() {
        String searchString = "not in database";
        String result = sut.getMoviesByDirector(searchString);
        assertEquals(result, String.format("could not find movie for '%s'", searchString));
    }

    @Test
    public void addMovie() {
        Integer year = 2010;
        String firstName = "Spielberg";
        String lastName = "Steven";
        String title = "A.i.";
        Movie addedMovie = sut.saveMovie(firstName, lastName, title, year);
        assertThat(addedMovie).isNotNull();
        assertThat(addedMovie.getDirector()).isNotNull();
    }

    @Test()
    public void addMovie_invalid_lastName() {
        Integer year = 2010;
        String firstName = "Spielberg";
        String lastName = "";
        String title = "A.i.";
        assertThrows(IllegalArgumentException.class, () -> sut.saveMovie(firstName, lastName, title, year));
    }

    @Test()
    public void addMovie_invalid_firstName() {
        Integer year = 2010;
        String firstName = "";
        String lastName = "Stefan";
        String title = "A.i.";
        assertThrows(IllegalArgumentException.class, () -> sut.saveMovie(firstName, lastName, title, year));
    }

    @Test()
    public void addMovie_invalid_title() {
        Integer year = 2010;
        String firstName = "";
        String lastName = "Stefan";
        String title = "";
        assertThrows(IllegalArgumentException.class, () -> sut.saveMovie(firstName, lastName, title, year));
    }

    @Test()
    public void addMovie_invalid_year() {
        Integer year = -1;
        String firstName = "";
        String lastName = "Stefan";
        String title = "";
        assertThrows(IllegalArgumentException.class, () -> sut.saveMovie(firstName, lastName, title, year));
    }

    @Test
    public void searchAllStuff() {
        List<Movie> result = sut.searchAllStuff("Spielberg 2009");
        assertThat(result.stream().map(Movie::getTitle))
            .containsExactlyInAnyOrder();
    }

    @Test
    public void saveMovie() {
        MovieDtoResource movieDtoResource = new MovieDtoResource();
        movieDtoResource.setTitle("test1");
        movieDtoResource.setYear(1999);
        movieDtoResource.setDirectorFirstName("TestName");
        movieDtoResource.setDirectorLastName("TestLastName");

        sut.saveMovie(movieDtoResource.getDirectorFirstName(), movieDtoResource.getDirectorLastName(), movieDtoResource.getTitle(), movieDtoResource.getYear());

        List<Movie> found = movieRepository.findMoviesByTitle("test1");

        assertThat(found).hasSize(1);
        Movie actual = found.get(0);
        assertThat(actual.getYear()).isEqualTo(1999);
        assertThat(actual.getDirector().getFirstName()).isEqualTo("TestName");
    }
}