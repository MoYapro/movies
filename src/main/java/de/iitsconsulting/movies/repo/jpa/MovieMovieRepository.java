package de.iitsconsulting.movies.repo.jpa;

import de.iitsconsulting.movies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieMovieRepository extends JpaRepository<Movie, Long> {

}
