package de.iitsconsulting.movies.service

import de.iitsconsulting.movies.controller.MovieDtoResource
import de.iitsconsulting.movies.model.Director
import de.iitsconsulting.movies.model.Movie
import de.iitsconsulting.movies.repo.jpa.DirectorRepository
import de.iitsconsulting.movies.repo.jpa.MovieRepository
import org.springframework.stereotype.Service
import java.util.*
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

    private fun saveMovie(title: String, year: Int, savedDirector: Director): Movie {
        val movieEntity = Movie()
        movieEntity.director = savedDirector
        movieEntity.title = title
        movieEntity.year = year
        return movieRepository.save(movieEntity)
    }

    private fun getSavedDirector(directorFirstName: String, directorLastName: String): Director {
        val director = Director()
        director.firstName = directorFirstName
        director.lastName = directorLastName
        return directorRepository.save(director)
    }

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
        val directorWithMovie: Optional<Director> =
            allDirectors.stream().filter { d -> d.movies.contains(movie) }.findFirst()
        if (directorWithMovie.isPresent()) {
            directorWithMovie.get().movies.remove(movie)
        }
    }

    fun findMovieByYearBetween(valueOf: Integer?, valueOf1: Integer?): List<Movie>? {
        return null //TODO implement me
    }

    fun searchAllStuff(searchString: String?): List<Movie>? {
        return null //TODO implement me
    }

    fun findAll(): List<Movie> {
        return movieRepository.findAll().toList()
    }

    fun findById(id: Long): Optional<Movie> {
        return movieRepository.findById(id)
    }

    fun getMoviesByDirector(directorName: String?): String? {
        return null //TODO implement me
    }
}
