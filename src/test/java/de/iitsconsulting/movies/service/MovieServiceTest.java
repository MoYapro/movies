package de.iitsconsulting.movies.service;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.iitsconsulting.movies.controller.MovieDtoResource;
import de.iitsconsulting.movies.model.Director;
import de.iitsconsulting.movies.model.Movie;
import de.iitsconsulting.movies.repo.jpa.DirectorRepository;
import de.iitsconsulting.movies.repo.jpa.MovieRepository;
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

    @Test
    public void findMovieByYearBetween() {
        List<Movie> result = sut.findMovieByYearBetween(2000, 2010);
        verify(movieRepository).findMovieByYearBetween(2000, 2010);
    }

    @Test
    public void findMovieByYearBetween_zero() {
        MovieService movieService = new MovieService(mock(MovieRepository.class), mock(DirectorRepository.class));
        List<Movie> result = movieService.findMovieByYearBetween(0, 3000);
        // empty
    }

    @Test
    public void findMovieByYearBetween_wrongOrder() {
        MovieService movieService = new MovieService(mock(MovieRepository.class), mock(DirectorRepository.class));
        List<Movie> result = movieService.findMovieByYearBetween(2010, 1990);
        // empty
    }

    @Test
    public void findMovieByYearBetween_negative() {
        MovieService movieService = new MovieService(mock(MovieRepository.class), mock(DirectorRepository.class));
        List<Movie> result = movieService.findMovieByYearBetween(-1564, -999);
        // empty
    }

    @Test
    public void getMoviesByDirector() {
        MovieService movieService = new MovieService(mock(MovieRepository.class), mock(DirectorRepository.class));
        String result = movieService.getMoviesByDirector("Spielberg");
        assertEquals(result, "[\"ET - Spielberg\",\"A.I. - Spielberg\",\"Titanic - Spielberg\"]");
    }

    @Test
    public void getMoviesByDirector_notFound() {
        MovieService movieService = new MovieService(mock(MovieRepository.class), mock(DirectorRepository.class));
        String searchString = "not in database";
        String result = movieService.getMoviesByDirector(searchString);
        assertEquals(result, String.format("could not find movie for '%s'", searchString));
    }

    @Test
    public void addMovie() {
        Integer year = 2010;
        String firstName = "Spielberg";
        String lastName = "Stefan";
        String title = "A.i.";
        MovieRepository movieRepository = mock(MovieRepository.class);
        DirectorRepository directorRepository = mock(DirectorRepository.class);
        when(movieRepository.save(any())).thenReturn(new Movie(1L, title, year, new Director(1L, firstName, lastName, null)));
        when(directorRepository.save(any())).thenReturn(new Director(1L, firstName, lastName, null));
        MovieService movieService = new MovieService(movieRepository, directorRepository);
        Movie addedMovie = movieService.saveMovie(firstName, lastName, title, year);
        assertThat(addedMovie).isNotNull();
        assertThat(addedMovie.getDirector()).isNotNull();
        verify(movieRepository, times(1)).save(any());
    }

    @Test()
    public void addMovie_invalid_firstName() {
        MovieService movieService = new MovieService(mock(MovieRepository.class), mock(DirectorRepository.class));
        Integer year = 2010;
        String firstName = "Spielberg";
        String lastName = "";
        String title = "A.i.";
        assertThrows(IllegalArgumentException.class, () -> movieService.saveMovie(firstName, lastName, title, year));
    }

    @Test()
    public void addMovie_invalid_lastName() {
        MovieService movieService = new MovieService(mock(MovieRepository.class), mock(DirectorRepository.class));
        Integer year = 2010;
        String firstName = "";
        String lastName = "Stefan";
        String title = "A.i.";
        assertThrows(IllegalArgumentException.class, () -> movieService.saveMovie(firstName, lastName, title, year));
    }

    @Test()
    public void addMovie_invalid_title() {
        MovieService movieService = new MovieService(mock(MovieRepository.class), mock(DirectorRepository.class));
        Integer year = 2010;
        String firstName = "";
        String lastName = "Stefan";
        String title = "";
        assertThrows(IllegalArgumentException.class, () -> movieService.saveMovie(firstName, lastName, title, year));
    }

    @Test()
    public void addMovie_invalid_year() {
        MovieService movieService = new MovieService(mock(MovieRepository.class), mock(DirectorRepository.class));
        Integer year = -1;
        String firstName = "";
        String lastName = "Stefan";
        String title = "";
        assertThrows(IllegalArgumentException.class, () -> movieService.saveMovie(firstName, lastName, title, year));
    }

    @Test
    public void findStuff() {

    }

    @Test
    public void saveMovie() {
        MovieService movieService = new MovieService(mock(MovieRepository.class), mock(DirectorRepository.class));
        MovieRepository movieRepository = mock(MovieRepository.class);

        MovieDtoResource movieDtoResource = new MovieDtoResource();
        movieDtoResource.setTitle("test1");
        movieDtoResource.setYear(1999);
        movieDtoResource.setDirectorFirstName("TestName");
        movieDtoResource.setDirectorLastName("TestLastName");

        movieService.saveMovie(movieDtoResource.getDirectorFirstName(),
            movieDtoResource.getDirectorLastName(),
            movieDtoResource.getTitle(),
            movieDtoResource.getYear());

        List<Movie> found = movieRepository.findMoviesByTitle("test1");

        assertThat(found).hasSize(1);
        Movie actual = found.get(0);
        assertThat(actual.getYear()).isEqualTo(1999);
        assertThat(actual.getDirector().getFirstName()).isEqualTo("TestName");

    }
}