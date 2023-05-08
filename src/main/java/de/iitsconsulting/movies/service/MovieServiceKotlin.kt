package de.iitsconsulting.movies.service

import de.iitsconsulting.movies.controller.MovieDtoResource
import de.iitsconsulting.movies.model.Director
import de.iitsconsulting.movies.model.Movie
import de.iitsconsulting.movies.repo.jpa.DirectorRepository
import de.iitsconsulting.movies.repo.jpa.MovieRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
open class MovieServiceKotlin(movieRepository: MovieRepository, directorRepository: DirectorRepository) {
    private val movieRepository: MovieRepository
    private val directorRepository: DirectorRepository

    init {
        this.movieRepository = movieRepository
        this.directorRepository = directorRepository
    }

    @Transactional
    open fun saveMovie(directorFirstName: String, directorLastName: String, title: String, year: Int): Movie {
        val savedDirector: Director = getSavedDirector(directorFirstName, directorLastName)
        return saveMovie(title, year, savedDirector)
    }

    private fun saveMovie(title: String, year: Int, savedDirector: Director): Movie =
        movieRepository.save(
            Movie().apply {
                director = savedDirector
                this.title = title
                this.year = year
            }
        )

    private fun getSavedDirector(directorFirstName: String, directorLastName: String): Director =
        directorRepository.save(
            Director().apply {
                firstName = directorFirstName
                lastName = directorLastName
            }
        )

    fun updateMovie(theValueFromController: MovieDtoResource) {
        saveMovie(
            theValueFromController.directorFirstName,
            theValueFromController.directorLastName,
            theValueFromController.title,
            theValueFromController.year
        )
    }

    @Transactional
    open fun deleteMovie(movie: Movie?) {
        val allDirectors: MutableList<Director> = directorRepository.findAll()
        val directorWithMovie: Director? =
            allDirectors.firstOrNull { d -> d.movies.contains(movie) }
        directorWithMovie?.movies?.remove(movie)
    }

    fun findMovieByYearBetween(valueOf: Int, valueOf1: Int): List<Movie> {
        return emptyList() //TODO implement me
    }

    fun searchAllStuff(searchString: String?): List<Movie>? {
        return null //TODO implement me
    }

    fun findAll(): List<Movie> {
        return movieRepository.findAll()
    }

    fun findById(id: Long): Movie? {
        return movieRepository.findByIdOrNull(id)
    }

    fun getMoviesByDirector(directorName: String?): String? {
        return null //TODO implement me
    }
}
