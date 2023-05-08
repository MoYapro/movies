package de.iitsconsulting.movies.service

import de.iitsconsulting.movies.MoviesApplication
import de.iitsconsulting.movies.controller.MovieDtoResource
import de.iitsconsulting.movies.model.Movie
import de.iitsconsulting.movies.repo.jpa.MovieRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [MoviesApplication::class])
@ActiveProfiles("db-test")
@ExtendWith(MockitoExtension::class)
internal class MovieServiceDBKotlinTest {
    @Autowired
    private val movieRepository: MovieRepository? = null

    @Autowired
    private val sut: MovieServiceKotlin? = null
    @Test
    fun findMovieByYearBetween() {
        val result = sut!!.findMovieByYearBetween(2000, 2010)
        Assertions.assertThat(result).isNotNull()
        Assertions.assertThat(result.size).isEqualTo(3)
        Assertions.assertThat(result.stream().map { obj: Movie -> obj.title })
            .containsExactlyInAnyOrder("Inglorious Bastards", "Avatar", "Kill Bill")
    }

    @Test
    fun findMovieByYearBetween_zero() {
        val result = sut!!.findMovieByYearBetween(0, 3000)
        Assertions.assertThat(result).hasSize(10) // all movies
    }

    @Test
    fun findMovieByYearBetween_wrongOrder() {
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException::class.java) {
            sut!!.findMovieByYearBetween(
                2010,
                1990
            )
        }
    }

    @Test
    fun findMovieByYearBetween_negative() {
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException::class.java) {
            sut!!.findMovieByYearBetween(
                -1564,
                -999
            )
        }
    }

    @get:Test
    val moviesByDirector: Unit
        get() {
            val result = sut!!.getMoviesByDirector("Spielberg")
            org.junit.jupiter.api.Assertions.assertEquals(
                "[\"ET - Spielberg\",\"Jurassic Park - Spielberg\",\"Titanic - Spielberg\"]",
                result
            )
        }

    @get:Test
    val moviesByDirector_notFound: Unit
        get() {
            val searchString = "not in database"
            val result = sut!!.getMoviesByDirector(searchString)
            org.junit.jupiter.api.Assertions.assertEquals(
                result,
                String.format("could not find movie for '%s'", searchString)
            )
        }

    @Test
    fun addMovie() {
        val year = 1971
        val firstName = "Spielberg"
        val lastName = "Steven"
        val title = "Duel"
        val addedMovie = sut!!.saveMovie(firstName, lastName, title, year)
        Assertions.assertThat(addedMovie).isNotNull()
        Assertions.assertThat(addedMovie.director).isNotNull()
    }

    @Test
    fun addMovie_invalid_lastName() {
        val year = 2010
        val firstName = "Spielberg"
        val lastName = ""
        val title = "A.i."
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException::class.java) {
            sut!!.saveMovie(
                firstName,
                lastName,
                title,
                year
            )
        }
    }

    @Test
    fun addMovie_invalid_firstName() {
        val year = 2010
        val firstName = ""
        val lastName = "Stefan"
        val title = "A.i."
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException::class.java) {
            sut!!.saveMovie(
                firstName,
                lastName,
                title,
                year
            )
        }
    }

    @Test
    fun addMovie_invalid_title() {
        val year = 2010
        val firstName = ""
        val lastName = "Stefan"
        val title = ""
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException::class.java) {
            sut!!.saveMovie(
                firstName,
                lastName,
                title,
                year
            )
        }
    }

    @Test
    fun addMovie_invalid_year() {
        val year = -1
        val firstName = ""
        val lastName = "Stefan"
        val title = ""
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException::class.java) {
            sut!!.saveMovie(
                firstName,
                lastName,
                title,
                year
            )
        }
    }

    @Test
    fun searchAllStuff() {
        val result = sut!!.searchAllStuff("Spielberg 2009")
        Assertions.assertThat(result?.map { obj: Movie -> obj.title })
            .containsExactlyInAnyOrder()
    }

    @Test
    fun saveMovie() {
        val movieDtoResource = MovieDtoResource()
        movieDtoResource.setTitle("test1")
        movieDtoResource.setYear(1999)
        movieDtoResource.setDirectorFirstName("TestName")
        movieDtoResource.setDirectorLastName("TestLastName")
        sut!!.saveMovie(
            movieDtoResource.getDirectorFirstName(),
            movieDtoResource.getDirectorLastName(),
            movieDtoResource.getTitle(),
            movieDtoResource.getYear()
        )
        val found = movieRepository!!.findMoviesByTitle("test1")
        Assertions.assertThat(found).hasSize(1)
        val actual = found[0]
        Assertions.assertThat(actual.year).isEqualTo(1999)
        Assertions.assertThat(actual.director.firstName).isEqualTo("TestName")
    }
}