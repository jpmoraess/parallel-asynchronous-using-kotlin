package br.com.moraesit.domain.movie

import java.time.LocalDate

data class MovieInfo(
    var movieInfoId: String? = null,
    var name: String? = null,
    var year: Int? = null,
    var cast: List<String>? = null,
    var release_date: LocalDate? = null,
)
