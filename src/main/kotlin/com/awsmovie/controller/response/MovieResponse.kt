package com.awsmovie.controller.response

import com.awsmovie.dto.movie.MovieDto
import org.springframework.http.HttpStatus

data class MovieResponse(
    override val code: Int,
    override val status: HttpStatus,
    override val message: String,
    val count: Int,
    val result: List<MovieDto>,
): BaseResponse(code, status, message)