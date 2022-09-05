package de.iitsconsulting.movies.repo.jpa;

import de.iitsconsulting.movies.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DirectorRepository extends JpaRepository<Director, Long> {
}
