package com.awsmovie.dto.movie

import com.awsmovie.dto.user.UserDto

data class MovieRateDto(
    val user: UserDto,
    val movieId: Long,
    val rate: Double,
    val comment: String
)