package de.iitsconsulting.movies.controller;

import javax.validation.Valid;

import de.iitsconsulting.movies.model.Movie;
import de.iitsconsulting.movies.repo.MyMovieRepositoryImplementationImpl;
import de.iitsconsulting.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MoviesController {

    @Autowired
    private MyMovieRepositoryImplementationImpl myMovieRepository;

    @Autowired
    private MovieService movieService;

    private boolean isNotNullAndNotEmpty(String value) {
        return value != null && !value.equals("");
    }

    @GetMapping("/")
    public String showMovies(Model model, @ModelAttribute("searchParams") SearchParams searchparams) {

        if (isNotNullAndNotEmpty(searchparams.getYearFrom()) & isNotNullAndNotEmpty(searchparams.getYearTo())) {
            model.addAttribute("movies", myMovieRepository.findMovieByYear(Integer.valueOf(searchparams.getYearFrom()),
                    Integer.valueOf(searchparams.getYearTo())));
        } else {
            model.addAttribute("movies", myMovieRepository.findAll());
        }

        return "index";
    }

    @GetMapping("/add-movie")
    public String showAddMovieForm(@ModelAttribute("movieDto") MovieDtoResource movieDto) {
        return "add-movie";
    }

    @PostMapping("/add-movie")
    public String addMovie(@Valid @ModelAttribute("movieDto") MovieDtoResource movieDto, BindingResult result) {
        if (result.hasErrors()) {
            return "add-movie";
        }

        movieService.saveMovie(movieDto.getDirectorFirstName(), movieDto.getDirectorLastName(), movieDto.getTitle(), movieDto.getYear());
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Movie movie = myMovieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie Id:" + id));

        MovieDtoResource movieDtoResource = new MovieDtoResource();
        movieDtoResource.title = movie.getTitle();
        movieDtoResource.year = movie.getYear();
        movieDtoResource.directorFirstName = movie.getDirector().getFirstName();
        movieDtoResource.directorLastName = movie.getDirector().getLastName();

        model.addAttribute("movieDto", movieDtoResource);
        return "update-movie";
    }

    @PostMapping("/update/{id}")
    public String updateMovie(@PathVariable("id") long id, @Valid @ModelAttribute("movieDto") MovieDtoResource movie, BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "update-movie";
        }

        movieService.updateMovie(movie);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable("id") long id, Model model) {
        Movie movie = myMovieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie Id:" + id));
        try {
            movieService.deleteMovie(movie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

}