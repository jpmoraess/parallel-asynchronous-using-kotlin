package br.com.moraesit.apiclient

import br.com.moraesit.util.CommonUtil.Companion.startTimer
import br.com.moraesit.util.CommonUtil.Companion.stopWatchReset
import br.com.moraesit.util.CommonUtil.Companion.timeTaken
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient

class MoviesClientTest {

    private val webClient = WebClient
        .builder()
        .baseUrl("http://localhost:8080/movies")
        .build()

    private val moviesClient = MoviesClient(webClient)


    @RepeatedTest(10)
    fun retrieveMovie() {
        startTimer()

        // given
        val movieInfoId = 1L

        // when
        val movie = moviesClient.retrieveMovie(movieInfoId)

        println("movie: $movie")

        timeTaken()
        stopWatchReset()

        // then
        assertNotNull(movie)
        assertEquals("Batman Begins", movie.movieInfo!!.name)
        assert(movie.reviewList!!.size == 1)
    }

    @RepeatedTest(10)
    fun retrieveMovies() {
        startTimer()

        // given
        val movieInfoIds = listOf(1L, 2L, 3L, 4L, 5L, 6L, 7L)

        // when
        val movies = moviesClient.retrieveMovies(movieInfoIds)

        println("movies: $movies")

        timeTaken()
        stopWatchReset()

        // then
        assertNotNull(movies)
        assertEquals(7, movies.size)
    }

    @RepeatedTest(10)
    fun retrieveMovie_CF() {
        startTimer()

        // given
        val movieInfoId = 1L

        // when
        val movie = moviesClient.retrieveMovie_CF(movieInfoId)
            .join()

        println("movie: $movie")

        timeTaken()
        stopWatchReset()

        // then
        assertNotNull(movie)
        assertEquals("Batman Begins", movie.movieInfo!!.name)
        assert(movie.reviewList!!.size == 1)
    }

    @RepeatedTest(10)
    fun retrieveMovieList_CF() {
        startTimer()

        // given
        val movieInfoIds = listOf(1L, 2L, 3L, 4L, 5L, 6L, 7L)

        // when
        val movies = moviesClient.retrieveMovieList_CF(movieInfoIds)

        println("movies: $movies")

        timeTaken()
        stopWatchReset()

        // then
        assertNotNull(movies)
        assertEquals(7, movies.size)
    }

    @RepeatedTest(10)
    fun retrieveMovieList_CF_allOf() {
        startTimer()

        // given
        val movieInfoIds = listOf(1L, 2L, 3L, 4L, 5L, 6L, 7L)

        // when
        val movies = moviesClient.retrieveMovieList_CF_allOf(movieInfoIds)

        println("movies: $movies")

        timeTaken()
        stopWatchReset()

        // then
        assertNotNull(movies)
        assertEquals(7, movies.size)
    }
}