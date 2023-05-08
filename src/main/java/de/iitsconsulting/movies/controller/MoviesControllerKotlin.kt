package de.iitsconsulting.movies.controller

import de.iitsconsulting.movies.service.MovieServiceKotlin
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute

@Controller
class MoviesControllerKotlin(private val movieService: MovieServiceKotlin) {

    private fun isNotNullAndNotEmpty(value: String?): Boolean = value != null && value != ""

    @GetMapping("/v2")
    fun showMovies(model: Model, @ModelAttribute("searchParams") searchparams: SearchParams): String {
        if (isNotNullAndNotEmpty(searchparams.yearFrom) and isNotNullAndNotEmpty(searchparams.yearTo)) {
            model.addAttribute(
                "movies",
                movieService.findMovieByYearBetween(Integer.valueOf(searchparams.yearFrom), Integer.valueOf(searchparams.yearTo))
            )
        } else {
            model.addAttribute("movies", movieService.findAll())
        }
        return "index"
    }

}