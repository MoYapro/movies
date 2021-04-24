package de.iitsconsulting.movies.repo.jpa;

import de.iitsconsulting.movies.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Long> {
}
