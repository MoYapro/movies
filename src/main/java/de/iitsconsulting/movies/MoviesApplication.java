package de.iitsconsulting.movies;

import de.iitsconsulting.movies.repo.jpa.DirectorRepository;
import de.iitsconsulting.movies.repo.jpa.MovieRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MoviesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(MovieRepository movieRepository, DirectorRepository directorRepository) {
        return args -> {
        };
    }

}
