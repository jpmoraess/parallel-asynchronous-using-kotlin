package br.com.moraesit.domain.movie

import java.time.LocalDate

data class MovieInfo(
    val movieInfoId: String,
    var name: String,
    var year: Int,
    var cast: List<String>,
    var releaseDate: LocalDate,
)
