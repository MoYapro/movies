package de.iitsconsulting.movies;

import de.iitsconsulting.movies.repo.jpa.DirectorRepository;
import de.iitsconsulting.movies.repo.jpa.MovieMovieRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"de.iitsconsulting.movies.repo", "de.iitsconsulting.movies.controller"})
public class MoviesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(MovieMovieRepository movieRepository, DirectorRepository directorRepository) {
        return args -> {
        };
    }

}
