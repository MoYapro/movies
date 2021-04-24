package de.iitsconsulting.movies.controller;

import de.iitsconsulting.movies.model.Movie;
import de.iitsconsulting.movies.repo.MovieRepository;
import de.iitsconsulting.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MoviesController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    public String showMovies(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "index";
    }

    @GetMapping("/add-movie")
    public String showSignUpForm(Movie movie) {
        return "add-movie";
    }

    @PostMapping("/add-movie")
    public String addMovie(@Valid Movie movie, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-movie";
        }

        movieRepository.save(movie);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie Id:" + id));

        model.addAttribute("movie", movie);
        return "update-movie";
    }

    @PostMapping("/update/{id}")
    public String updateMovie(@PathVariable("id") long id, @Valid Movie movie, BindingResult result, Model model) {
        if (result.hasErrors()) {
            movie.setId(id);
            return "update-movie";
        }

        movieRepository.save(movie);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable("id") long id, Model model) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie Id:" + id));
        try {
            movieService.deleteMovie(movie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

}