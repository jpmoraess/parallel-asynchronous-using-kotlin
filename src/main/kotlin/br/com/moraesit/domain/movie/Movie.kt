package br.com.moraesit.domain.movie

data class Movie(var movieInfo: MovieInfo? = null, var reviewList: List<Review>? = null)
