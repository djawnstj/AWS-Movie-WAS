package com.awsmovie.dto.movie

import java.time.LocalDateTime

data class MovieDto(
    val movieName: String,
    val runTime: Int,
    val openingDate: LocalDateTime,
    val summary: String,
    val genreCode: List<Int> = ArrayList(),
    val movieImagePath: String? = null,
    val rates: List<MovieRateDto> = ArrayList(),
)