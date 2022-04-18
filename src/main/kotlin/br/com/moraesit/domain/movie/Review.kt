package br.com.moraesit.domain.movie

data class Review(
    var reviewId: String? = null,
    var movieInfoId: Long? = null,
    var comment: String? = null,
    var rating: Double? = null,
)
