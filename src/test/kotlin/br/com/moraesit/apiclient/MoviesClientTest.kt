package br.com.moraesit.apiclient

import br.com.moraesit.domain.movie.Movie
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.web.reactive.function.client.WebClient

class MoviesClientTest {

    private val webClient = WebClient
        .builder()
        .baseUrl("http://localhost:8080/movies")
        .build()

    private val moviesClient = MoviesClient(webClient)


    @Test
    fun retrieveMovie() {
        // given
        val movieInfoId = 1L

        // when
        val movie = moviesClient.retrieveMovie(movieInfoId)

        println("movie: $movie")

        // then
        assertNotNull(movie)
        assertEquals("Batman Begins", movie.movieInfo!!.name)
        assert(movie.reviewList!!.size == 1)
    }
}