package br.com.moraesit.domain.movie

data class Review(val reviewId: String, var movieInfoId: Long, var comment: String, var rating: Double)
