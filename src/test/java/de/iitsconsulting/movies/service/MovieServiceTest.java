package de.iitsconsulting.movies.service;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.iitsconsulting.movies.controller.MovieDtoResource;
import de.iitsconsulting.movies.model.Director;
import de.iitsconsulting.movies.model.Movie;
import de.iitsconsulting.movies.repo.jpa.DirectorRepository;
import de.iitsconsulting.movies.repo.jpa.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private DirectorRepository directorRepository;

    @InjectMocks
    private MovieService sut;

    @AfterEach
    public void cleanup() {
        reset(movieRepository);
        reset(directorRepository);
    }

    @Test
    public void findMovieByYearBetween() {
        List<Movie> result = sut.findMovieByYearBetween(2000, 2010);
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.stream().map(Movie::getTitle).collect(Collectors.toList()))
            .containsExactlyInAnyOrder("Inglourious Basterds", "Avatar", "Kill Bill");
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
        assertEquals("[\"ET - Spielberg\",\"A.I. - Spielberg\",\"Titanic - Spielberg\"]", result);
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
        String lastName = "Stefan";
        String title = "A.i.";
        when(movieRepository.save(any())).thenReturn(new Movie(1L, title, year, new Director(1L, firstName, lastName, null)));
        when(directorRepository.save(any())).thenReturn(new Director(1L, firstName, lastName, null));
        Movie addedMovie = sut.saveMovie(firstName, lastName, title, year);
        assertThat(addedMovie).isNotNull();
        assertThat(addedMovie.getDirector()).isNotNull();
        verify(movieRepository, times(1)).save(any());
        verify(directorRepository, times(1)).save(any());
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
        assertThat(result.stream().map(Movie::getTitle).collect(Collectors.toList()))
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