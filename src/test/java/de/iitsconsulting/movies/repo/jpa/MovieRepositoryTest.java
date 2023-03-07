package de.iitsconsulting.movies.repo.jpa;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import de.iitsconsulting.movies.MoviesApplication;
import de.iitsconsulting.movies.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = MoviesApplication.class)
@ActiveProfiles("db-test")
class MovieRepositoryTest {

    @Autowired
    private MovieRepository repo;

    @Test
    void findMovieByYearBetween() {
        List<Movie> result = repo.findMovieByYearBetween(2000, 2010);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).extracting(Movie::getTitle).containsExactlyInAnyOrder("Avatar", "Kill Bill", "Inglorious Bastards");
    }

    @Test
    void findMoviesByTitle() {
        List<Movie> result = repo.findMoviesByTitle("Titanic");

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).extracting(Movie::getTitle).containsExactly("Titanic");

    }
}