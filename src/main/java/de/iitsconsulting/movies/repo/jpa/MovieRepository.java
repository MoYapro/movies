package de.iitsconsulting.movies.repo.jpa;

import java.util.List;

import de.iitsconsulting.movies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findMovieByYearBetween(Integer startYear, Integer endYear);

    List<Movie> findMoviesByTitle(String title);
}
