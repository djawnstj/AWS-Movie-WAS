package com.awsmovie.dto.movie

import com.awsmovie.dto.movie.genre.GenreDto
import java.time.LocalDateTime

data class MovieDto(
    val movieId: Long? = null,
    val movieName: String,
    val runTime: Int,
    val openingDate: LocalDateTime,
    val summary: String,
    val genreCode: List<Int> = ArrayList(),
    val genres: List<GenreDto> = ArrayList(),
    val movieImagePath: String? = null,
    val rates: List<MovieRateDto> = ArrayList(),
)