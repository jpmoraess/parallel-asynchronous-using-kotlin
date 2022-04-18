package br.com.moraesit.apiclient

import br.com.moraesit.domain.movie.Movie
import br.com.moraesit.domain.movie.MovieInfo
import br.com.moraesit.domain.movie.Review
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

class MoviesClient(
    private val webClient: WebClient,
) {

    fun retrieveMovie(movieInfoId: Long): Movie {
        // movieInfo
        val movieInfo = invokeMovieInfoService(movieInfoId)

        // review
        val review = invokeReviewsService(movieInfoId)

        return Movie(movieInfo, review)
    }

    fun retrieveMovies(movieInfoIds: List<Long>): List<Movie> {
        return movieInfoIds
            .stream()
            .map { retrieveMovie(it) }
            .collect(Collectors.toList())
    }

    fun retrieveMovie_CF(movieInfoId: Long): CompletableFuture<Movie> {
        // movieInfo
        val movieInfoCF = CompletableFuture.supplyAsync { invokeMovieInfoService(movieInfoId) }

        // review
        val reviewCF = CompletableFuture.supplyAsync { invokeReviewsService(movieInfoId) }

        return movieInfoCF.thenCombine(reviewCF) { movieInfo, review ->
            return@thenCombine Movie(movieInfo, review)
        }
    }

    fun retrieveMovieList_CF(movieInfoIds: List<Long>): List<Movie> {
        val movieFutures = movieInfoIds
            .stream()
            .map { retrieveMovie_CF(it) }
            .collect(Collectors.toList())

        return movieFutures
            .stream()
            .map { it.join() }
            .collect(Collectors.toList())
    }

    private fun invokeMovieInfoService(movieInfoId: Long): MovieInfo {
        val moviesInfoUrlPath = "/v1/movie_infos/{movieInfoId}"
        return webClient
            .get()
            .uri(moviesInfoUrlPath, movieInfoId)
            .retrieve()
            .bodyToMono(MovieInfo::class.java)
            .block()
    }

    private fun invokeReviewsService(movieInfoId: Long): List<Review> {
        val reviewUri = UriComponentsBuilder.fromUriString("/v1/reviews")
            .queryParam("movieInfoId", movieInfoId)
            .buildAndExpand()
            .toUriString()

        return webClient
            .get()
            .uri(reviewUri)
            .retrieve()
            .bodyToFlux(Review::class.java)
            .collect(Collectors.toList())
            .block()
    }
}