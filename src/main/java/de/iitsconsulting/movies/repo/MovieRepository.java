package de.iitsconsulting.movies.repo;

import de.iitsconsulting.movies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
